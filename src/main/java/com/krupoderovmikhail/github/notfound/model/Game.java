package com.krupoderovmikhail.github.notfound.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "game")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "game_name")
    private String name;

    @Column(name = "game_description")
    private String description;

    @Column(name = "game_platform")
    private String platform;

    @Column(name = "game_release_year")
    private String year;

    @Column(name = "game_price")
    private String price;

    @Column(name = "game_additionally")
    private String additionally;

    public Game(String name, String description, String platform, String year, String price, String additionally) {
        this.name = name;
        this.description = description;
        this.platform = platform;
        this.year = year;
        this.price = price;
        this.additionally = additionally;
    }
}
