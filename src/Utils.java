import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.*;

/**
 * Created by Taha on 02/01/2017.
 */
public class Utils {


    public static boolean areKeysPresent(String filePublic, String filePrivate) {
        File filePublico = new File(filePublic);
        File filePrivado = new File(filePrivate);
        if (filePublico.exists()) {
            if (filePrivado.exists()) {
                return true;//los fitxeros existen
            } else {
                return false;//los fitxeros no existen
            }
        }else{
            return false;
        }
    }

    public static KeyPair generateKey(String privadaKey, String publicaKey ) throws NoSuchAlgorithmException, IOException {
            KeyPair clave = null;//La clase KeyPair soporta una clave privada y una pública.
            byte[] publica, privada;


        //GENERAR Y ALMACENAR EL PAR DE CLAVES
                KeyPairGenerator generador = KeyPairGenerator.getInstance("RSA");
                generador.initialize(2048);//Tamaño de la clave.
                clave = generador.genKeyPair();

        //OBTENGO CLAVE PUBLICA Y PRIVADA A PARATIR DE KEYPAIRGENERATOR Y KEYPAIR
                PublicKey publicKey=  clave.getPublic();
                PrivateKey privateKey= clave.getPrivate();

        //CONVIERTO LAS CLAVES EN BYTES PARA GUARDAR EN FICHERO
              /*publica = publicKey.getEncoded();
                privada = privateKey.getEncoded();*/

        //GUARDO LA CLAVE PUBLICA EN UN FICHERO EXTERNO

                File publicaFile= new File(String.valueOf(publicaKey));
                publicaFile.createNewFile();
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(publicaFile));
                oos.writeObject(publicKey);
                oos.close();

                File privadaFile= new File(String.valueOf(privadaKey));
                publicaFile.createNewFile();
                ObjectOutputStream oos2 = new ObjectOutputStream(new FileOutputStream(privadaFile));
                oos2.writeObject(privateKey);
                oos2.close();



        System.out.println("Clave privada: "+clave.getPrivate().toString());
        System.out.println("Clave pública: "+clave.getPublic().toString());


        return clave;
        }

    public static byte[] digestiona(File f, String algoritme) throws NoSuchAlgorithmException, IOException{
        //Hem de encriptar la clau

        StringBuffer hexString = new StringBuffer();
        InputStream inputStream = new FileInputStream(f);
        //MessageDigest md = MessageDigest.getInstance(algoritme);
        byte[] hash = new byte[(int) f.length()];

        for (int i = 0; i < hash.length; i++) {
            if ((0xff & hash[i]) < 0x10) {
                hexString.append("0"
                        + Integer.toHexString((0xFF & hash[i])));
            } else {
                hexString.append(Integer.toHexString(0xFF & hash[i]));
            }
        }

        return new byte[0];
    }

    public static byte[] signar(byte[] text, PrivateKey key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher clau = Cipher.getInstance("RSA");
        clau.init(Cipher.ENCRYPT_MODE, key);
        byte[] hash= clau.doFinal(text);

        return hash;
    }

    public static void write(String f, byte[] byteArray) throws IOException {

        FileOutputStream fileOutputStream = new FileOutputStream(f);
        fileOutputStream.write(byteArray);

    }

    public static byte[] verificar(byte[] text, PublicKey key) throws InvalidKeyException{

        return text;
    }

}
