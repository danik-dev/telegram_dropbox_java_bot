package com.example.entity;

import lombok.*;

import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLJsonPGObjectJsonbType;

import org.telegram.telegrambots.meta.api.objects.Update;

import jakarta.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "raw_data")
public class RawData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JdbcType(PostgreSQLJsonPGObjectJsonbType.class)
    @Convert(converter = JsonConverter.class)
    private Update event;
}
