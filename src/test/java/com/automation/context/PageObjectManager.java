package com.automation.context;

import com.automation.pages.IzertisHomePage;
import com.microsoft.playwright.Page;

public class PageObjectManager {

    private Page page;
    private IzertisHomePage izertisHomePage;

    public PageObjectManager(Page page) {
        this.page = page;
    }

    public IzertisHomePage getIzertisHomePage() {
        return (izertisHomePage == null) ? izertisHomePage = new IzertisHomePage(page) : izertisHomePage;
    }
}
