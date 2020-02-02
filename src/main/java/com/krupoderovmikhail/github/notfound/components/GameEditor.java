package com.krupoderovmikhail.github.notfound.components;

import com.krupoderovmikhail.github.notfound.model.Game;
import com.krupoderovmikhail.github.notfound.repository.GameRepository;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Setter;


@SpringComponent
@UIScope
public class GameEditor extends VerticalLayout implements KeyNotifier {

    private final GameRepository gameRepository;

    private Game game;

    private TextField name = new TextField("Name");
    private TextField description = new TextField("Description");
    private TextField platform = new TextField("Platform");
    private TextField year = new TextField("Year");
    private TextField price = new TextField("Price");
    private TextField additionally = new TextField("Additionally");

    private Button save = new Button("Save", VaadinIcon.CHECK.create());
    private Button cancel = new Button("Cancel");
    private Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    private HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    private Binder<Game> binder = new Binder<>(Game.class);
    @Setter
    private ChangeHandler changeHandler;

    public interface ChangeHandler {
        void onChange();
    }

    public GameEditor(GameRepository gameRepository) {
        this.gameRepository = gameRepository;

        add(name, description, platform, year, price, additionally, actions);

        binder.bindInstanceFields(this);

        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> editGame(game));
        setVisible(false);
    }

    private void delete() {
        gameRepository.delete(game);
        changeHandler.onChange();
    }

    private void save() {
        gameRepository.save(game);
        changeHandler.onChange();
    }

    public void editGame(Game newGame) {

        if (newGame == null) {
            setVisible(false);
            return;
        }

        if (newGame.getId() != null) {
            this.game = gameRepository.findById(newGame.getId()).orElse(newGame);
        } else {
            this.game = newGame;
        }

        binder.setBean(game);
        setVisible(true);

        name.focus();
    }
}
