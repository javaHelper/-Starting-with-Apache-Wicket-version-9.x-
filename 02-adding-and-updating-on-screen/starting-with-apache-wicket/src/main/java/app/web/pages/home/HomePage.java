package app.web.pages.home;


import app.model.Todo;
import app.services.MongoDBService;
import app.web.pages.BasePage;
import com.giffing.wicket.spring.boot.context.scan.WicketHomePage;
import lombok.extern.slf4j.Slf4j;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
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
    FeedbackPanel fp;
    List<Todo> todos;

    public HomePage() {
        Label label = new Label("label", "My custom page title from Wicket! " + mongoDBService.getRepo().count());
        add(label);

        fp = new FeedbackPanel("feedbackPanel");
        //fp.setOutputMarkupPlaceholderTag(true);
        fp.setOutputMarkupId(true);
        add(fp);

        WebMarkupContainer sectionForm = new WebMarkupContainer("sectionForm");
        sectionForm.setOutputMarkupId(true);
        add(sectionForm);

        Form<Void> form = new Form<>("form");
        sectionForm.add(form);

        WebMarkupContainer formNew = new WebMarkupContainer("formNew");
        AjaxLink<Void> btnAdd = new AjaxLink<Void>("addItemLink") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                formNew.setVisible(!formNew.isVisible());
                target.add(formNew);
            }
        };
        form.add(btnAdd);


        formNew.setOutputMarkupPlaceholderTag(true);
        formNew.setVisible(true);
        form.add(formNew);

        Todo todoItem = new Todo();
        form.setDefaultModel(new CompoundPropertyModel<>(todoItem));

        TextField<String> title = new TextField<>("title");
        TextField<String> body = new TextField<>("body");
        formNew.add(title,body);

        AjaxLink<Void> btnSave = new AjaxLink<Void>("save") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                Todo todo = new Todo();
                todo.setTitle(title.getValue());
                todo.setBody(todoItem.getBody());
                mongoDBService.save(todo);

                todoItem.setTitle("");
                todoItem.setBody("");

                formNew.setVisible(false);
                todos.clear();
                todos.addAll(mongoDBService.getAllItems());

                showInfo(target, "Todo saved into database");

                target.add(sectionForm);
            }
        };
        btnSave.add(new AjaxFormSubmitBehavior(form, "click") {
			private static final long serialVersionUID = 1L; 
		});
        
        formNew.add(title,body,btnSave);

        todos = mongoDBService.getAllItems();
        ListView<Todo> todosList = new ListView<>("todosList", todos) {
			private static final long serialVersionUID = 1L;

			@Override
            protected void populateItem(ListItem<Todo> item) {
                item.add(new Label("title", new PropertyModel<>(item.getModel(), "title")));
                item.add(new Label("body", new PropertyModel<>(item.getModel(), "body")));
            }
        };
        form.add(todosList);
    }

    protected void showInfo(AjaxRequestTarget target, String msg) {
        info(msg);
        target.add(fp);
    }
}