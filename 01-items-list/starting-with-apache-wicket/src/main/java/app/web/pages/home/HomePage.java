package app.web.pages.home;


import app.model.Todo;
import app.services.MongoDBService;
import app.web.pages.BasePage;
import com.giffing.wicket.spring.boot.context.scan.WicketHomePage;
import lombok.extern.slf4j.Slf4j;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.annotation.mount.MountPath;

import java.util.List;

@WicketHomePage
@MountPath(value = "home", alt = {"home2"})
@Slf4j
public class HomePage extends BasePage {
    private static final long serialVersionUID = 1L;

    @SpringBean
    private MongoDBService mongoDBService;

    public HomePage() {
        Label label = new Label("label", "My custom page title from Wicket! " + mongoDBService.getRepo().count());
        add(label);

        List<Todo> todos = mongoDBService.getAllItems();
        ListView<Todo> todosList = new ListView<>("todosList", todos) {
            @Override
            protected void populateItem(ListItem<Todo> item) {
                item.add(new Label("title", new PropertyModel<>(item.getModel(), "title")));
                item.add(new Label("body", new PropertyModel<>(item.getModel(), "body")));
            }
        };
        add(todosList);
    }
}