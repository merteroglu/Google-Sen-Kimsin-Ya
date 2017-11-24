package com.ronins.googlesenkimsinya.UI;

import com.ronins.googlesenkimsinya.Backend.SearchWords;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;

import java.util.ArrayList;
import java.util.List;

@Title("Site Sıralama")
@SpringUI(path = "/asama3")
@Theme("apptheme")
@UIScope
public class SitePlacement extends UI {

    VerticalLayout root;
    List<String> linksAna;
    List<String> linksIkinci;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        root = new VerticalLayout();
        root.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        setContent(root);

        TextField words = new TextField("Words");
        TextField links = new TextField("Links");
        Button btnSearch = new Button("Search");

        words.setWidth("70%");
        links.setWidth("70%");
        btnSearch.setWidth("70%");

        root.addComponents(words,links,btnSearch);

        SearchWords searchWords = new SearchWords();


    linksIkinci = new ArrayList<>();
        btnSearch.addClickListener(clickEvent -> {
          linksAna = searchWords.getLinks(links.getValue());
            for (int i = 0; i < linksAna.size() ; i++) {
                linksIkinci.addAll(searchWords.getLinks(linksAna.get(i)));
            }

            System.out.println("break point");


        });

    }
}
