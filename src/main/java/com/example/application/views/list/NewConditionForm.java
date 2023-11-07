package com.example.application.views.list;

import com.example.application.data.entity.AtomicCondition;
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

public class NewConditionForm extends FormLayout {

    Binder<AtomicCondition> binder = new BeanValidationBinder<>(AtomicCondition.class);

    TextField milesField = new TextField("Miles Attribute");
    TextField equalityOperator = new TextField("Equality Operator");
    TextField valueToCheck = new TextField("Value");
    TextArea groupOperator = new TextArea("Group Operator");
    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");
    private AtomicCondition atomicCondition;

    public void setAtomicCondition(AtomicCondition atomicCondition) {
        this.atomicCondition = atomicCondition;
        binder.readBean(atomicCondition);
    }

    public NewConditionForm() {
        addClassName("newConditions-form");
        binder.bindInstanceFields(this);

        add(
                milesField,
                equalityOperator,
                valueToCheck,
                groupOperator,
                createButtonLayout()
        );
    }

    private Component createButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, atomicCondition)));
        cancel.addClickListener(event -> fireEvent(new CloseEvent(this)));

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(save, delete, cancel);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(atomicCondition);
            fireEvent(new SaveEvent(this, atomicCondition));
        } catch (ValidationException e) {
            System.out.println("Invalid Input");
            e.printStackTrace();
        }
    }

    public static abstract class NewConditionFormEvent extends ComponentEvent<NewConditionForm> {

        private AtomicCondition atomicCondition;

        protected NewConditionFormEvent(NewConditionForm source, AtomicCondition atomicCondition) {
            super(source, false);
            this.atomicCondition = atomicCondition;
        }

        public AtomicCondition getAtomicCondition() {
            return atomicCondition;
        }
    }

    public static class SaveEvent extends NewConditionFormEvent {
        SaveEvent(NewConditionForm source, AtomicCondition atomicCondition) {
            super(source, atomicCondition);
        }
    }

    public static class DeleteEvent extends NewConditionFormEvent {
        DeleteEvent(NewConditionForm source, AtomicCondition atomicCondition) {
            super(source, atomicCondition);
        }

    }

    public static class CloseEvent extends NewConditionFormEvent {
        CloseEvent(NewConditionForm source) {
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
