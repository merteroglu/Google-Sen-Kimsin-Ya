package com.ronins.googlesenkimsinya.UI;

import com.ronins.googlesenkimsinya.Backend.SearchWords;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;

@Title("Google Sen Kimsin Ya")
@SpringUI(path = "/asama4")
@Theme("apptheme")
@UIScope
public class EsAnlam extends UI {

    VerticalLayout root;
    SearchWords searchWords;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        root = new VerticalLayout();
        root.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        setContent(root);

        Image logo = new Image("",new ThemeResource("img/logo.png"));
        logo.setStyleName("imgLogo");
        TextArea words = new TextArea("Words");
        words.setWidth("40%");
        TextArea links = new TextArea("Links");
        links.setWidth("40%");
        links.setHeight(100,Unit.PIXELS);
        Button btnSearch = new Button("Search");
        btnSearch.setWidth("40%");

        root.addComponents(logo,words,links,btnSearch);

        searchWords = new SearchWords();

        btnSearch.addClickListener(clickEvent -> {
          String esAnlam = searchWords.getEsAnlam(words.getValue());
          Label label = new Label(esAnlam);
          root.addComponent(label);
        });

    }
}
