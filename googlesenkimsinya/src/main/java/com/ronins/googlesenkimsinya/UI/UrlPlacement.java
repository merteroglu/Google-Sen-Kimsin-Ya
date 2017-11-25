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
         /*  int counts[] = searchWords.getURLWordsCount(links.getValue().split(",")[0],words.getValue());
            for (int i = 0; i < counts.length; i++) {
                Label label = new Label("i :" + i + " -> " + counts[i]);
                root.addComponent(label);
            }*/

            String kelimeler[] = words.getValue().split(",");
            List<Pages> pages = searchWords.topSecretAlgorithm(links.getValue(),words.getValue());

            for (int i = 0; i < pages.size() ; i++) {
                VerticalLayout pagesLayout = new VerticalLayout();
                pagesLayout.setStyleName("pages-layout");
                pagesLayout.setWidth("40%");
                 Label labelURL = new Label("URL : " + pages.get(i).getURL());
                 labelURL.setStyleName("textLink");
                 Label labelRank = new Label("Rank :" + pages.get(i).getRank());
                labelRank.setStyleName("textRank");
                 Label labelWordsCount = new Label(" ");
                labelWordsCount.setStyleName("textWords");
                for (int j = 0; j < pages.get(i).getWordsCount().length ; j++) {
                    labelWordsCount.setValue(labelWordsCount.getValue() +   kelimeler[j] + " : " + pages.get(i).getWordsCount()[j] + " ");
                }

                pagesLayout.addComponents(labelURL,labelRank,labelWordsCount);
                root.addComponent(pagesLayout);
            }

        });

    }
}
