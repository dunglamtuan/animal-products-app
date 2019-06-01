package sk.garwan.animal.shop.service.registration;

public interface PasswordProvider {

  boolean verifyUserPassword(String username, String password);
  String hashPassword(String inputPass);

}
