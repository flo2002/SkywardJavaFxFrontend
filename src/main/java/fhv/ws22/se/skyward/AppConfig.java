package fhv.ws22.se.skyward;

import com.google.inject.AbstractModule;
import fhv.ws22.se.skyward.domain.ServiceGuiceProvider;
import fhv.ws22.se.skyward.domain.ServiceProviderService;
import fhv.ws22.se.skyward.view.util.ControllerNavigationUtil;
import fhv.ws22.se.skyward.view.util.FXMLLoaderProvider;
import fhv.ws22.se.skyward.view.util.NavigationService;
import javafx.fxml.FXMLLoader;

public class AppConfig extends AbstractModule {
    @Override
    protected void configure() {
        bind(ServiceProviderService.class).toProvider(ServiceGuiceProvider.class);
        bind(FXMLLoader.class).toProvider(FXMLLoaderProvider.class);
        bind(NavigationService.class).to(ControllerNavigationUtil.class);
    }
}
