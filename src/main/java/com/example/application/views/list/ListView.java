package com.example.application.views.list;

import com.example.application.data.entity.Conditions;
import com.example.application.data.service.ConditionService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@PageTitle("Conditions | Conditions Configure")
@Route(value = "", layout = MainLayout.class)
public class ListView extends VerticalLayout {

    private final ConditionService service;
    Grid<Conditions> grid = new Grid<>(Conditions.class);
    TextField filterText = new TextField();
    ConditionForm form;

    public ListView(ConditionService service) {
        this.service = service;
        addClassName("list-view");
        setSizeFull();

        configureGrid();
        configureForm();

        add(
                getToolBar(),
                getContent()
        );

        updateList();
        closeEditor();

    }

    private void closeEditor() {
        form.setCondition(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(service.findAllConditions(filterText.getValue()));
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.addClassName("content");
        content.setSizeFull();

        return content;
    }

    private void configureForm() {
        form = new ConditionForm();
        form.setWidth("25em");

        form.addSaveListener(ConditionForm.SaveEvent.class, this::saveCondition);
        form.addDeleteListener(ConditionForm.DeleteEvent.class, this::deleteCondition);
        form.addCloseListener(ConditionForm.CloseEvent.class, e -> closeEditor());
    }

    private void saveCondition(ConditionForm.SaveEvent event){
        service.saveCondition(event.getCondition());
        updateList();
        closeEditor();
    }

    private void deleteCondition(ConditionForm.DeleteEvent event){
        service.deleteCondition(event.getCondition());
        updateList();
        closeEditor();
    }

    private void editCondition(Conditions conditions) {
        if (conditions == null){
            closeEditor();
        }else {
            form.setCondition(conditions);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private Component getToolBar() {
        filterText.setPlaceholder("Filter by Accounting Line Code...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());
        filterText.setWidth("300px");

        HorizontalLayout toolBar = new HorizontalLayout(filterText);
        toolBar.addClassName("toolBar");
        return toolBar;
    }

    private void configureGrid() {
        grid.addClassName("conditions-grid");
        grid.setSizeFull();
        grid.setColumns("entityTypeName", "eventTypeCode", "accountingLineCode", "sourceName", "conditionField");
        grid.getColumns().forEach(conditionsColumn -> conditionsColumn.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(e -> editCondition(e.getValue()));

    }

}
