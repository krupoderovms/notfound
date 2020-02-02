package com.krupoderovmikhail.github.notfound.view;

import com.krupoderovmikhail.github.notfound.components.GameEditor;
import com.krupoderovmikhail.github.notfound.model.Game;
import com.krupoderovmikhail.github.notfound.repository.GameRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;


@Route
public class MainView extends VerticalLayout {
    private final GameRepository gameRepository;

    private Grid<Game> grid = new Grid<>(Game.class);

    private final TextField filter = new TextField("", "Type to filter");
    private final Button addNewBtn = new Button("Add new");
    private final HorizontalLayout toolbar = new HorizontalLayout(filter, addNewBtn);

    private final GameEditor editor;
    public MainView(GameRepository gameRepository, GameEditor editor) {
        this.gameRepository = gameRepository;
        this.editor = editor;

        add(toolbar, grid, editor);

        filter.setValueChangeMode(ValueChangeMode.EAGER); // Обновление после каждого нажатия
        filter.addValueChangeListener(e -> showGame(e.getValue()));

        /* При каждом выборе строки, вызываем редактор */
        grid.asSingleSelect().addValueChangeListener(e -> {
            editor.editGame(e.getValue());
        });

        addNewBtn.addClickListener(e -> editor.editGame(new Game()));

        /* При нажатии происходит фильтрация */
        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            showGame(filter.getValue());
        });

        showGame("");
    }

    private void showGame(String name) {
        if (name.isEmpty()) {
            grid.setItems(gameRepository.findAll());
        } else {
            grid.setItems(gameRepository.findByName(name));
        }
    }
}
