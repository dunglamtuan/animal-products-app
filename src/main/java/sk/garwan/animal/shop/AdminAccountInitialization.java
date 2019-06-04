package sk.garwan.animal.shop;

import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import sk.garwan.animal.shop.model.User;
import sk.garwan.animal.shop.service.DefaultUserService;

@AllArgsConstructor
@Component
public class AdminAccountInitialization {

  private DefaultUserService userService;

  @EventListener
  public void appReady(ApplicationReadyEvent event) {

    userService.registerNewUser(
        User.builder().username("admin").password("admin").isAdmin(true).email("admin@admin.com")
            .build());
  }

}
