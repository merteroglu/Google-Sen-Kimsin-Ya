package com.ronins.googlesenkimsinya.UI;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@Title("Anasayfa")
@SpringUI
@Theme("apptheme")
public class MainPage extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        VerticalLayout root = new VerticalLayout();


        root.setDefaultComponentAlignment(Alignment.TOP_CENTER);

        VerticalLayout buttons = new VerticalLayout();
        buttons.setStyleName("mainPage");
        buttons.setWidth(30,Unit.PERCENTAGE);
        buttons.setDefaultComponentAlignment(Alignment.TOP_CENTER);

        Button btnAsama1 = new Button("Asama 1", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                getUI().getPage().open("/asama1","Asama 1",false);
            }
        });

        Button btnAsama2 = new Button("Asama 2", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                getUI().getPage().open("/asama2","Asama 2",false);
            }
        });

        Button btnAsama3 = new Button("Asama 3", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                getUI().getPage().open("/asama3","Asama 3",false);
            }
        });

        Button btnAsama4 = new Button("Asama 4", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                getUI().getPage().open("/asama4","Asama 4",false);
            }
        });

        buttons.addComponents(btnAsama1,btnAsama2,btnAsama3,btnAsama4);
        root.addComponent(buttons);
        setContent(root);

    }
}
