package app.web.pages;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public abstract class BasePage extends WebPage {
	private static final long serialVersionUID = 1L;


	public BasePage(PageParameters params){
        super(params);
        Injector.get().inject(this);
        initPage();
    }

    public BasePage(){
        Injector.get().inject(this);
        initPage();
    }

    private void initPage(){
    }


    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);

        response.render(JavaScriptHeaderItem.forReference(getApplication().getJavaScriptLibrarySettings().getJQueryReference()));
        response.render(JavaScriptHeaderItem.forReference(getApplication().getJavaScriptLibrarySettings().getWicketAjaxReference()));

        response.render(JavaScriptHeaderItem.forUrl("https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"));
        response.render(CssHeaderItem.forUrl("https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"));
    }
}