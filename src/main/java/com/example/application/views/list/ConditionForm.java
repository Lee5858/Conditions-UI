package com.example.application.views.list;

import com.example.application.data.entity.Conditions;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

public class ConditionForm extends FormLayout {
    Binder<Conditions> binder = new BeanValidationBinder<>(Conditions.class);

    TextField entityTypeName = new TextField("Entity Type Name");
    TextField accountingLineCode = new TextField("Accounting Line Code");
    TextField sourceName = new TextField("Source Name");
    TextArea conditionField = new TextArea("Condition");
    TextField eventTypeCode = new TextField("Event Type Code");
    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");
    private Conditions conditions;


    public void setCondition(Conditions conditions){
        this.conditions = conditions;
        binder.readBean(conditions);
    }

    public ConditionForm() {
        addClassName("conditions-form");
        binder.bindInstanceFields(this);

        add(
                entityTypeName,
                eventTypeCode,
                accountingLineCode,
                sourceName,
                conditionField,
                createButtonLayout()
        );
    }

    private Component createButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, conditions)));
        cancel.addClickListener(event -> fireEvent(new CloseEvent(this)));

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(save, delete, cancel);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(conditions);
            fireEvent(new SaveEvent(this, conditions));
        }catch (ValidationException e){
            System.out.println("Invalid Input");
            e.printStackTrace();
        }
    }

    // Events
    public static abstract class ConditionFormEvent extends ComponentEvent<ConditionForm> {
        private Conditions conditions;

        protected ConditionFormEvent(ConditionForm source, Conditions conditions) {
            super(source, false);
            this.conditions = conditions;
        }

        public Conditions getCondition() {
            return conditions;
        }
    }

    public static class SaveEvent extends ConditionFormEvent {
        SaveEvent(ConditionForm source, Conditions conditions) {
            super(source, conditions);
        }
    }

    public static class DeleteEvent extends ConditionFormEvent {
        DeleteEvent(ConditionForm source, Conditions conditions) {
            super(source, conditions);
        }

    }

    public static class CloseEvent extends ConditionFormEvent {
        CloseEvent(ConditionForm source) {
            super(source, null);
        }
    }

    public Registration addDeleteListener(Class<DeleteEvent> deleteEventClass, ComponentEventListener<DeleteEvent> listener) {
        return addListener(DeleteEvent.class, listener);
    }

    public Registration addSaveListener(Class<SaveEvent> saveEventClass, ComponentEventListener<SaveEvent> listener) {
        return addListener(SaveEvent.class, listener);
    }
    public Registration addCloseListener(Class<CloseEvent> closeEventClass, ComponentEventListener<CloseEvent> listener) {
        return addListener(CloseEvent.class, listener);
    }


}
