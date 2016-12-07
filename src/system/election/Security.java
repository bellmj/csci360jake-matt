package system.election;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class Security {


    public Security() {

    }

    /**
     * takes a password entry and hashes it according to a salt generating a byte[] as an output
     * adapted from code provided in this guide https://www.owasp.org/index.php/Hashing_Java
     * @param password password to be hashed
     * @param salt a salt to hash the password with should be random data at least 32 bytes and very by each user
     * @param iterations number of iterations of algorithm to be run a higher number is safer
     * @param keyLength length of key to be used in password hash 256 is safe
     * @return a byte[] of the hashed password.
     */
    public static byte[] hashPassword( final char[] password, final byte[] salt, final int iterations, final int keyLength ) {

        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance( "PBKDF2WithHmacSHA512" );
            PBEKeySpec spec = new PBEKeySpec( password, salt, iterations, keyLength );
            SecretKey key = skf.generateSecret( spec );
            return key.getEncoded();

        } catch( NoSuchAlgorithmException | InvalidKeySpecException e ) {
            throw new RuntimeException( e );
        }
    }
}
