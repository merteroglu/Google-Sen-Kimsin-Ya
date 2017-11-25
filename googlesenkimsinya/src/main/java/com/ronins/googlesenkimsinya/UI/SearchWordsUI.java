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
public class SearchWordsUI extends UI {

    VerticalLayout root;
    SearchWords searchWords;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        root = new VerticalLayout();
        setContent(root);
        root.setDefaultComponentAlignment(Alignment.TOP_CENTER);



        Image logo = new Image("",new ThemeResource("img/logo.png"));
        logo.setStyleName("imgLogo");
        TextArea words = new TextArea();
        words.setCaption("Word:");
        words.setWidth("50%");
        words.setHeight(100,Unit.PIXELS);
        TextArea links = new TextArea();
        links.setCaption("Link:");
        links.setWidth("50%");
        links.setHeight(100,Unit.PIXELS);

        root.addComponents(logo,words,links);

        Button btnSearch = new Button("Ara");
        btnSearch.setWidth("50%");

        searchWords = new SearchWords();

        btnSearch.addClickListener(clickEvent -> {
          // int count = searchWords.getWordsCount(links.getValue(),words.getValue());
            VerticalLayout vLayout = new VerticalLayout();
            vLayout.setStyleName("pages-layout");
            vLayout.setWidth("40%");
            int count[] = searchWords.getURLWordsCount(links.getValue(),words.getValue());
            for (int i = 0; i < count.length ; i++) {
                Label tekrarSayisi = new Label(words.getValue().split(",")[i] + " tekrar Sayisi : " + count[i]);
                tekrarSayisi.setStyleName("textWords");
                vLayout.addComponent(tekrarSayisi);
            }
            root.addComponent(vLayout);
        });

        root.addComponent(btnSearch);

    }
}
