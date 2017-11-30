package com.ronins.googlesenkimsinya.UI;

import com.ronins.googlesenkimsinya.Backend.SearchWords;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Title("Site Sıralama")
@SpringUI(path = "/asama3")
@Theme("apptheme")
@UIScope
public class SitePlacement extends UI {

    Logger log = Logger.getLogger(SitePlacement.class.getName());

    VerticalLayout root;
    List<String> linksAna = new ArrayList<>();
    List<String> linksIkinci = new ArrayList<>();

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        root = new VerticalLayout();
        root.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        setContent(root);

        Image logo = new Image("",new ThemeResource("img/logo.png"));
        logo.setStyleName("imgLogo");
        TextArea words = new TextArea("Words");
        words.setWidth("40%");
        words.setHeight(100,Unit.PIXELS);
        TextArea links = new TextArea("Links");
        links.setWidth("40%");
        links.setHeight(100,Unit.PIXELS);
        Button btnSearch = new Button("Search");
        btnSearch.setWidth("40%");


        root.addComponents(logo,words,links,btnSearch);

        SearchWords searchWords = new SearchWords();


        btnSearch.addClickListener(clickEvent -> {
          linksAna = searchWords.getLinks(links.getValue());
            for (int i = 0; i < linksAna.size() ; i++) {
                linksIkinci = searchWords.getLinks(linksAna);
            }
            log.info("Derinlik 2 :" + linksAna.size());
            log.info("Derinlik 3 :" + linksIkinci.size());
        });

    }
}
