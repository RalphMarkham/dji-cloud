package ca.ralphsplace.react;

public final class ClientId {

  private ClientId() {
    throw new IllegalStateException("Utility class");
  }

  public static final String ID = "clientId";
  public static final String HEADER = "X-Client_Id";
}
