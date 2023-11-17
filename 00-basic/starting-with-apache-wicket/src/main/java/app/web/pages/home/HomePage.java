package app.web.pages.home;

import app.web.pages.BasePage;
import com.giffing.wicket.spring.boot.context.scan.WicketHomePage;
import lombok.extern.slf4j.Slf4j;
import org.apache.wicket.markup.html.basic.Label;
import org.wicketstuff.annotation.mount.MountPath;

@WicketHomePage
@MountPath(value = "home", alt = {"home2"})
@Slf4j
public class HomePage extends BasePage {

    public HomePage() {
        Label label = new Label("label", "My custom page title from Wicket!");
        add(label);
    }
}