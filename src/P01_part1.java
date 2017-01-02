import com.sun.org.apache.xpath.internal.SourceTree;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.*;

/**
 * Created by Dionis on 25/03/2016.
 */
public class P01_part1 {

    public static final String PRIVATE_KEY_FILE = "private.key";

    public static final String FITXER_PLA = "planets.xml";
    public static final String FITXER_SIGNAT = "firmat.xml";
    public static final String filePublic= "public.key";
    public static final String filePrivate= "private.key";

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, ClassNotFoundException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchPaddingException {

        KeyPair keyPair = null;
        PrivateKey prik = null;

        File f = new File(FITXER_PLA);


        /**
         * Utils areKeysPresents:
         * comprueba que existen los ficheros de la clave pública
         * y clave privada
         */

        if(!Utils.areKeysPresent(filePublic, filePrivate )){

            /**
             * Si no existe, las general las guarda en el fichero
             * y devuelve el par. Luego nos quedamos con la pública
             */

            keyPair = Utils.generateKey( filePrivate, filePublic);
            prik = keyPair.getPrivate();
        }else{

            /**
             * Si existen las claves, las leemos del fichero
             * y las guardamos en una variable
             */

            ObjectInputStream inputStream = null;
            inputStream = new ObjectInputStream(new FileInputStream(PRIVATE_KEY_FILE));
            prik = (PrivateKey) inputStream.readObject();
            System.out.println(prik.toString());
        }


        /**
         * Aquí llegamos conlas claves privadas.
         * Utils digestiona coge el fichero f,
         * y devuelve el array de bits correspondiente al
         * hash del fichero en MD5
         */
        byte[] digestionat = Utils.digestiona(f,"MD5");

        /**
         * en signar, cogemos la clave privada y el hash
         * y encriptamos el hash
         */
        byte[] encryptdigestionat = Utils.signar(digestionat,prik);

        /**
         * read: passa el fichero a bytes
         * concatenateByteArrays: añadimos al final del fichero los bytes de la firma
         * write: vuelve a guardar los bytes en fichero.
         * **/


        //Utils.write(FITXER_SIGNAT,Utils.concatenateByteArrays(Utils.read(f),encryptdigestionat));
    }


}
