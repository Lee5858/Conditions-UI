package com.example.application.views;

import com.example.application.data.entity.AtomicCondition;
import com.example.application.data.service.AtomicConditionService;
import com.example.application.views.list.NewConditionForm;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "Add", layout = MainLayout.class)
@PageTitle("Add New Condition | Conditions Configure")
public class AddConditionView extends VerticalLayout {
    private final AtomicConditionService atomicConditionService;
    Grid<AtomicCondition> atomicConditionGrid = new Grid<>(AtomicCondition.class);
    NewConditionForm newConditionForm;

    public AddConditionView(AtomicConditionService atomicConditionService) {
        this.atomicConditionService = atomicConditionService;
        addClassName("addCondition-view");
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
        newConditionForm.setAtomicCondition(null);
        newConditionForm.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        atomicConditionGrid.setItems(atomicConditionService.findAllAtomicConditions());
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(atomicConditionGrid, newConditionForm);
        content.setFlexGrow(2, atomicConditionGrid);
        content.setFlexGrow(1, newConditionForm);
        content.addClassName("newContent");
        content.setSizeFull();
        return content;
    }

    private void configureForm() {
        newConditionForm = new NewConditionForm();
        newConditionForm.setWidth("25em");

        newConditionForm.addSaveListener(NewConditionForm.SaveEvent.class, this::saveCondition);
        newConditionForm.addDeleteListener(NewConditionForm.DeleteEvent.class, this::deleteCondition);
        newConditionForm.addCloseListener(NewConditionForm.CloseEvent.class, e -> closeEditor());
    }

    private void saveCondition(NewConditionForm.SaveEvent event) {
        atomicConditionService.saveAtomicCondition(event.getAtomicCondition());
        updateList();
        closeEditor();
    }

    private void deleteCondition(NewConditionForm.DeleteEvent event) {
        atomicConditionService.deleteAtomicCondition(event.getAtomicCondition());
        updateList();
        closeEditor();
    }

    private void addCondition() {
        atomicConditionGrid.asSingleSelect().clear();
        editCondition(new AtomicCondition());
    }

    private void editCondition(AtomicCondition atomicCondition) {
        if (atomicCondition == null) {
            closeEditor();
        } else {
            newConditionForm.setAtomicCondition(atomicCondition);
            newConditionForm.setVisible(true);
            addClassName("editing");
        }
    }

    private Component getToolBar() {
        Button addAtomicConditionButton = new Button("Add Atomic Condition");
        addAtomicConditionButton.addClickListener(e -> addCondition());
        HorizontalLayout toolBar = new HorizontalLayout(addAtomicConditionButton);
        toolBar.addClassName("toolBar");
        return toolBar;
    }

    private void configureGrid(){
        atomicConditionGrid.addClassName("atomicConditions-grid");
        atomicConditionGrid.setSizeFull();
        atomicConditionGrid.setColumns("milesField", "equalityOperator", "valueToCheck", "groupOperator");
        atomicConditionGrid.getColumns().forEach(atomicConditionColumn -> atomicConditionColumn.setAutoWidth(true));
        atomicConditionGrid.asSingleSelect().addValueChangeListener(e -> editCondition(e.getValue()));
    }

}

