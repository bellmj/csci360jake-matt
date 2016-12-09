package system.election;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class Security {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";


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
/*
Here are the general steps to encrypt/decrypt a file in Java:
Create a Key from a given byte array for a given algorithm.
Get an instance of Cipher class for a given algorithm transformation. See document of the Cipher class for more information regarding supported algorithms and transformations.
Initialize the Cipher with an appropriate mode (encrypt or decrypt) and the given Key.
Invoke doFinal(input_bytes) method of the Cipher class to perform encryption or decryption on the input_bytes, which returns an encrypted or decrypted byte array.
Read an input file to a byte array and write the encrypted/decrypted byte array to an output file accordingly.
 */

    /**
     * code adapted from open source provided at http://www.codejava.net/coding/file-encryption-and-decryption-simple-example
     * @param key
     * @param inputFile
     * @param outputFile
     * @throws CryptoException
     */
    public static void encrypt(byte[] key, File inputFile, File outputFile)
            throws CryptoException {
        doCrypto(Cipher.ENCRYPT_MODE, key, inputFile, outputFile);
    }

    /**
     * code adapted from open source provided at http://www.codejava.net/coding/file-encryption-and-decryption-simple-example
     * @param key
     * @param inputFile
     * @param outputFile
     * @throws CryptoException
     */
    public static void decrypt(byte[] key, File inputFile, File outputFile)
            throws CryptoException {
        doCrypto(Cipher.DECRYPT_MODE, key, inputFile, outputFile);
    }

    /**
     * code adapted from open source provided at http://www.codejava.net/coding/file-encryption-and-decryption-simple-example
     * @param cipherMode
     * @param key
     * @param inputFile
     * @param outputFile
     * @throws CryptoException
     */
    private static void doCrypto(int cipherMode,byte[] key, File inputFile,
                                 File outputFile) throws CryptoException {
        try {
            Key secretKey = new SecretKeySpec(key, ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(cipherMode, secretKey);

            FileInputStream inputStream = new FileInputStream(inputFile);
            byte[] inputBytes = new byte[(int) inputFile.length()];
            inputStream.read(inputBytes);

            byte[] outputBytes = cipher.doFinal(inputBytes);

            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(outputBytes);

            inputStream.close();
            outputStream.close();

        } catch (NoSuchPaddingException | NoSuchAlgorithmException
                | InvalidKeyException | BadPaddingException
                | IllegalBlockSizeException | IOException ex) {
            throw new CryptoException("Error encrypting/decrypting file", ex);
        }
    }
}
