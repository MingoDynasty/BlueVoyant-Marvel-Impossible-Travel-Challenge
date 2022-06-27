package com.example.springboot;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "character", schema = "marvel")
public class Character {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "marvel_id")
    private int marvelId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "thumbnail_path")
    private String thumbnailPath;

    @Column(name = "last_retrieved")
    private OffsetDateTime lastRetrieved;
}
