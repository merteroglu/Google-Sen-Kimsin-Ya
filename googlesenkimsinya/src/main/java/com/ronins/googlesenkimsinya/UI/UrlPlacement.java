package com.ronins.googlesenkimsinya.UI;

import com.ronins.googlesenkimsinya.Backend.Pages;
import com.ronins.googlesenkimsinya.Backend.SearchWords;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;

import java.util.List;

@Title("URL Sıralama")
@SpringUI(path = "/asama2")
@Theme("apptheme")
@UIScope
public class UrlPlacement extends UI {

    VerticalLayout root;
    SearchWords searchWords;

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

        searchWords = new SearchWords();



        btnSearch.addClickListener(clickEvent -> {
            List<Pages> pages = searchWords.rankPages(links.getValue(),words.getValue());
            for (int i = 0; i < pages.size() ; i++) {
                 Label label = new Label(pages.get(i).getURL() + " \n" + pages.get(i).getRank() + "\n" );
                for (int j = 0; j < pages.get(i).getWordsCount().length ; j++) {
                    label.setValue(label.getValue() +  pages.get(i).getWordsCount()[j] + " \n ");
                }
                root.addComponent(label);
            }
        });

    }
}
