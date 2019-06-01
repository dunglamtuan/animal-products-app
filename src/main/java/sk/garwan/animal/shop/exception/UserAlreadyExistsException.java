package sk.garwan.animal.shop.exception;

public class UserAlreadyExistsException extends IllegalArgumentException {

  public UserAlreadyExistsException(String s) {
    super(s);
  }
}
