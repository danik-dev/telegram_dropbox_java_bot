package com.example.service.impl;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import com.example.dao.AppUserDAO;
import com.example.dao.RawDataDAO;
import com.example.entity.AppDocument;
import com.example.entity.AppPhoto;
import com.example.entity.AppUser;
import com.example.entity.RawData;
import com.example.entity.enums.UserState;
import com.example.service.AppUserService;
import com.example.service.FileService;
import com.example.service.MainService;
import com.example.service.ProducerService;
import com.example.service.enums.LinkType;
import com.example.service.enums.ServiceCommand;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
@RequiredArgsConstructor
public class MainServiceImpl implements MainService {

    private final RawDataDAO rawDataDAO;
    private final ProducerService producerService;
    private final AppUserDAO appUserDAO;
    private final FileService fileService;
    private final AppUserService appUserService;

    @Override
    public void processTextMessage(Update update) {
        saveRawData(update);
        var appUser = findOrSaveAppUser(update);
        var userState = appUser.getState();
        var text = update.getMessage().getText();
        var output = "";

        var serviceCommand = ServiceCommand.fromValue(text);
        if(ServiceCommand.CANCEL.equals(serviceCommand)) {
        // if (ServiceCommands.CANCEL.equals(text)) {
            output = cancelProcess(appUser);
        } else if (UserState.BASIC_STATE.equals(userState)) {
            output = processServiceCommand(appUser, text);
        } else if (UserState.WAIT_FOR_EMAIL_STATE.equals(userState)) {
            output = appUserService.setEmail(appUser, text);
        } else {
            log.error("Unknown user state: " + userState);
            output = "Неизвестная ошибка! Введите /cancel и попробуйте снова";
        }

        var chatId = update.getMessage().getChatId();
        sendAnswer(output, chatId);
    }

    private String cancelProcess(AppUser appUser) {
        appUser.setState(UserState.BASIC_STATE);
        appUserDAO.save(appUser);
        return "Команда отменена";
    }

    private String processServiceCommand(AppUser appUser, String cmd) {
        var serviceCommand = ServiceCommand.fromValue(cmd);
        if (ServiceCommand.REGISTRATION.equals(serviceCommand)) {
            return appUserService.registerUser(appUser);
        } else if (ServiceCommand.HELP.equals(serviceCommand)) {
            return help();
        } else if (ServiceCommand.START.equals(serviceCommand)) {
            return "Приветствую! Чтобы посмотреть список доступных комманд введите /help";
        } else {
            return "Неизвестная команда! Чтобы посмотреть список команд введите /help";
        }
    }

    private String help() {
        return "Список доступных команд:\n"
                + "/cancel - отмена выполнения текущей команды\n"
                + "/registration - регистрация пользовтеля";
    }

    private AppUser findOrSaveAppUser(Update update) {
        User telegramUser = update.getMessage().getFrom();
        var optional = appUserDAO.findByTelegramUserId(telegramUser.getId());
        if (optional.isEmpty()) {
            AppUser transientAppUser = AppUser.builder()
                    .telegramUserId(telegramUser.getId())
                    .username(telegramUser.getUserName())
                    .firstName(telegramUser.getFirstName())
                    .lastName(telegramUser.getLastName())
                    .isActive(false)
                    .state(UserState.BASIC_STATE)
                    .build();
            return appUserDAO.save(transientAppUser);
        }
        return optional.get();
    }

    

    @Override
    public void processDocMessage(Update update) {
        saveRawData(update);
        var appUser = findOrSaveAppUser(update);
        var chatId = update.getMessage().getChatId();
        if (isNotAllowToSendContent(chatId, appUser)) {
            return;
        }

        try {
            AppDocument doc = fileService.processDoc(update.getMessage());
            String link = fileService.generateLink(doc.getId(), LinkType.GET_DOC);
            var answer = "Документ успешно загружен! Ссылка для скачивания: " + link;
            sendAnswer(answer, chatId);
        } catch (UploadFileException e) {
            log.error(e);
            String error = "К сожалению загрузка файла не удаласью Повторите попытку позже";
            sendAnswer(error, chatId);
        }
    }

    @Override
    public void processPhotoMessage(Update update) {
        saveRawData(update);
        var appUser = findOrSaveAppUser(update);
        var chatId = update.getMessage().getChatId();
        if (isNotAllowToSendContent(chatId, appUser)) {
            return;
        }

        try {
            AppPhoto photo = fileService.processPhoto(update.getMessage());
            String link = fileService.generateLink(photo.getId(), LinkType.GET_PHOTO);
            var answer = "Фото успешно загружено! Ссылка для скачивания: " + link;
            sendAnswer(answer, chatId);
        } catch (UploadFileException e) {
            log.error(e);
            String error = "К сожалению загрузка файла не удаласью Повторите попытку позже";
            sendAnswer(error, chatId);
        }
    }

    private boolean isNotAllowToSendContent(Long chatId, AppUser appUser) {
        var userState = appUser.getState();
        if (!appUser.getIsActive()) {
            var error = "Зарегистрируйтесь или активируйте свою учётную запись для загрузки контента";
            sendAnswer(error, chatId);
            return true;
        } else if (!UserState.BASIC_STATE.equals(userState)) {
            var error = "Отмените текущую команду с помощью /cancel для отправки файлов.";
            sendAnswer(error, chatId);
            return true;
        }

        return false;
    }

     private void saveRawData(Update update) {
        RawData rawData = RawData.builder()
                .event(update).build();
        rawDataDAO.save(rawData);
    }

    private void sendAnswer(String output, Long chatId) {
        var sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(output);
        producerService.producerAnswer(sendMessage);
    }

}
