package system.election;

/**
 * Created by matt on 12/8/16.
 */
public class CryptoException extends Throwable {
    public CryptoException() {
    }

    public CryptoException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
