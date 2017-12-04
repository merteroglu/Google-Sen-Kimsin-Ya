package com.ronins.googlesenkimsinya.UI;

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

@Title("Google Sen Kimsin Ya")
@SpringUI(path = "/asama4")
@Theme("apptheme")
@UIScope
public class EsAnlam extends UI {

    Logger log = Logger.getLogger(EsAnlam.class.getName());
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



        List<String> esAnlamliKelimeler = new ArrayList<>();

        btnSearch.addClickListener(clickEvent -> {
            String[] kelimeler = words.getValue().split(",");
            for (int i = 0; i < kelimeler.length ; i++) {
                esAnlamliKelimeler.add(kelimeler[i]);
            }

            for (int i = 0; i < kelimeler.length ; i++) {
                String esAnlam = searchWords.getEsAnlam(kelimeler[i]);
                Label label = new Label(  kelimeler[i] + " -->" + esAnlam);
                root.addComponent(label);

                String[] esAnlams = esAnlam.split(",");
                for (int j = 0; j < esAnlams.length ; j++) {
                    if(!esAnlams[j].equals("BULUNAMADI !")){
                        esAnlamliKelimeler.add(esAnlams[j].trim());
                    }
                }

            }

            SearchURL searchURL = new SearchURL(links.getValue(),words.getValue());
            String[] urls = links.getValue().split(",");

            URL pages[] = new URL[urls.length];
            String allWords = "";

            for (int i = 0; i < esAnlamliKelimeler.size() ; i++) {
                allWords = allWords + esAnlamliKelimeler.get(i)  + ",";
            }

            for (int i = 0; i < urls.length ; i++) {
                pages[i] = searchURL.getWebSiteRank(allWords,urls[i]);
            }

            for (int i = 0; i < pages.length ; i++) {
                pages[i].setSira(1);
            }

            for (int i = 0; i < pages.length ; i++) {
                for (int j = i+1; j < pages.length ; j++) {
                    if(pages[i].getPuan() < pages[j].getPuan()){
                        pages[i].setSira(pages[i].getSira()+1);
                    }
                }
            }

            for (int i = 0; i < urls.length ; i++) {
                VerticalLayout pagesLayout = new VerticalLayout();
                pagesLayout.setStyleName("pages-layout");
                pagesLayout.setWidth("41%");

                Label labelURL = new Label("URL : " + urls[i]);
                labelURL.setStyleName("textLink");

                Label labelSira = new Label("Sira : " + pages[i].getSira());
                labelSira.setStyleName("textPuan");

                Label labelPuan = new Label("Puan :"  + pages[i].getPuan() );
                labelPuan.setStyleName("textPuan");

                pagesLayout.addComponents(labelURL,labelSira,labelPuan);

                int tekrarSayilari[][] = pages[i].getTekrarSayilari();
                for (int j = 0; j < 3 ; j++) {
                    Label labelKelimeler = new Label("Derinlik :" + j);
                    for (int k = 0; k < esAnlamliKelimeler.size() ; k++) {
                        labelKelimeler.setValue(labelKelimeler.getValue() + " ; " + esAnlamliKelimeler.get(k) + " : " + tekrarSayilari[j][k]);
                    }
                    pagesLayout.addComponent(labelKelimeler);
                }

                root.addComponent(pagesLayout);
            }

            log.info("İşlem tamamlandı");

        });

    }
}
