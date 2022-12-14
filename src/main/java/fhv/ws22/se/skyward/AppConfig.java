package fhv.ws22.se.skyward;

import com.google.inject.AbstractModule;
import fhv.ws22.se.skyward.domain.SessionProvider;
import fhv.ws22.se.skyward.domain.SessionService;
import fhv.ws22.se.skyward.domain.TmpDataService;
import fhv.ws22.se.skyward.view.util.ControllerNavigationUtil;
import fhv.ws22.se.skyward.view.util.FXMLLoaderProvider;
import fhv.ws22.se.skyward.view.util.NavigationService;
import javafx.fxml.FXMLLoader;

public class AppConfig extends AbstractModule {
    @Override
    protected void configure() {
        bind(SessionService.class).toProvider(SessionProvider.class);
        bind(FXMLLoader.class).toProvider(FXMLLoaderProvider.class);
        bind(NavigationService.class).to(ControllerNavigationUtil.class);
    }
}
