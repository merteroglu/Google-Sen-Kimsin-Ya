package com.ronins.googlesenkimsinya.UI;

import com.ronins.googlesenkimsinya.Backend.SearchWords;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;


@Title("Google Sen Kimsin Yaa")
@Theme("apptheme")
@SpringUI(path = "/asama1")
@UIScope
public class MainUI extends UI {

    VerticalLayout root;
    SearchWords searchWords;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        root = new VerticalLayout();
        setContent(root);

        VerticalLayout icerik = new VerticalLayout();

        Image logo = new Image("",new ThemeResource("img/logo.png"));

        logo.setStyleName("logo");
        icerik.addComponent(logo);
        icerik.setComponentAlignment(logo, Alignment.TOP_CENTER);
        icerik.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        TextField words = new TextField();
        words.setCaption("Word:");
        words.setWidth("50%");
        TextField links = new TextField();
        links.setCaption("Link:");
        links.setWidth("50%");

        icerik.addComponents(words,links);

        Button btnSearch = new Button("Ara");
        btnSearch.setWidth("50%");

        searchWords = new SearchWords();
        btnSearch.addClickListener(clickEvent -> {
           int count = searchWords.getWordsCount(links.getValue(),words.getValue());
            Label tekrarSayisi = new Label(words.getValue() + " tekrar Sayisi : " + count);
            icerik.addComponent(tekrarSayisi);
        });

        icerik.addComponent(btnSearch);

        root.addComponent(icerik);
    }
}
