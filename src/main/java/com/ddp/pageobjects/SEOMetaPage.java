package com.ddp.pageobjects;

import org.jsoup.select.Elements;

import java.util.ArrayList;

public class SEOMetaPage {
    private Elements pageTitleElements; //elements1
    private Elements canonicalElements; //elements2
    private Elements metaElements; //elements3
    public SEOMetaPage(Elements elements1,Elements elements2,Elements elements3 )
    {
        this.pageTitleElements = elements1;
        this.canonicalElements=elements2;
        this.metaElements=elements3;
    }

    public SEOMetaPage() {

    }

    public Elements getPageTitleElements() {
        return pageTitleElements;
    }

    public Elements getCanonicalElements() {
        return canonicalElements;
    }

    public Elements getMetaElements() {
        return metaElements;
    }
}
