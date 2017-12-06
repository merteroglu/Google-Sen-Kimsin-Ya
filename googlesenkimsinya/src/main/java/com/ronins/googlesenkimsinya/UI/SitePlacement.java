package com.ronins.googlesenkimsinya.UI;

import com.ronins.googlesenkimsinya.Backend.Pages;
import com.ronins.googlesenkimsinya.Backend.SearchURL;
import com.ronins.googlesenkimsinya.Backend.SearchWords;
import com.ronins.googlesenkimsinya.Backend.URL;
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




        btnSearch.addClickListener(clickEvent -> {
            log.info("Buton tıklandı");
            SearchURL searchURL = new SearchURL(links.getValue(),words.getValue());
            String[] urls = links.getValue().split(",");
            String[] kelimeler = words.getValue().split(",");

            URL pages[] = new URL[urls.length];

            for (int i = 0; i < urls.length ; i++) {
                pages[i] = searchURL.getWebSiteRank(words.getValue(),urls[i]);
            }

            for (int i = 0; i < pages.length ; i++) {
                pages[i].setSira(1);
            }

            for (int i = 0; i < pages.length ; i++) {
                for (int j = 0; j < pages.length ; j++) {
                    if(pages[i].getPuan() < pages[j].getPuan()){
                        //pages[i].setSira(pages[i].getSira()+1);
                        pages[i].sira++;
                    }
                }
            }

            for (int i = 0; i < urls.length ; i++) {
                VerticalLayout pagesLayout = new VerticalLayout();
                pagesLayout.setStyleName("pages-layout");
                pagesLayout.setWidth("40%");

                Label labelURL = new Label("URL : " + urls[i]);
                labelURL.setStyleName("textLink");

                Label labelSira = new Label("Sira : " + pages[i].getSira());
                labelSira.setStyleName("textPuan");

                Label labelPuan = new Label("Puan :"  + pages[i].getPuan() );
                labelPuan.setStyleName("textPuan");

                pagesLayout.addComponents(labelURL,labelSira,labelPuan);


                int tekrarSayilari[][] = pages[i].getTekrarSayilari();
                for (int j = 0; j < 3 ; j++) {
                    Label labelKelimeler = new Label("Derinlik :" + j+1);
                    for (int k = 0; k < kelimeler.length ; k++) {
                        labelKelimeler.setValue(labelKelimeler.getValue() + " ; " + kelimeler[k] + " : " + tekrarSayilari[j][k]);
                    }
                    pagesLayout.addComponent(labelKelimeler);
                }


                root.addComponent(pagesLayout);
            }
            log.info("İşlem tamamlandı");
        });

    }

}
