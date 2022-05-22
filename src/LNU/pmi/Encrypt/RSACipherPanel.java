package LNU.pmi.Encrypt;

import LNU.pmi.Helpers.Resources;

import javax.swing.*;
import java.awt.*;
import java.math.*;
import java.util.*;


/*
Generating Public Key

1. Select two prime no's. Suppose P = 53 and Q = 59.
Now First part of the Public key  : n = P*Q = 3127.

2. We also need a small exponent say e :
   But e Must be

    -An integer.

    -Not be a factor of n.

    -1 < e < Φ(n) [Φ(n) is discussed below],
     Let us now consider it to be equal to 3.

The public key has been made of n and e

Generating Private Key

1. We need to calculate Φ(n) :
   Such that Φ(n) = (P-1)(Q-1)
      so,  Φ(n) = 3016


2. Now calculate Private Key, d :
   d = (k*Φ(n) + 1) / e for some integer k

3. For k = 2, value of d is 2011.

The private key has been made of d
 */

public class RSACipherPanel extends ICipherPanel {
    private JTextArea publicKeyTextArea;
    private JButton generateKeysButton;
    private JTextArea privateKeyTextArea;
    private JPanel mainPanel;
    private JButton pGenerateButton;
    private JButton qGenerateButton;
    private JTextArea pTextArea;
    private JTextArea qTextArea;

    BigInteger n_;
    BigInteger e_;
    BigInteger d_;

    RSACipherPanel()
    {
        setupPanel();
    }

    @Override
    public String encode(String data) {
        // Convert string to numbers using a cipher
        BigInteger cipherMessage = stringCipher(data);

        // Encrypt the ciphered message
        BigInteger encrypted = cipherMessage.modPow(e_, n_);

        return encrypted.toString();
    }

    @Override
    public String decode(String data) {
        // Convert string to numbers using a cipher
        BigInteger cipherMessage = new BigInteger(data);

        // Decrypt the encrypted message
        BigInteger decrypted = cipherMessage.modPow(d_, n_);

        // Uncipher the decrypted message to text
        String restoredMessage = cipherToString(decrypted);

        return restoredMessage;
    }


    @Override
    public void test() {

    }

    private void setupPanel()
    {
        pGenerateButton.setBackground(Color.LIGHT_GRAY);
        pGenerateButton.addActionListener(e ->
        {
            pTextArea.setText(largePrime(512).toString());
        });

        qGenerateButton.setBackground(Color.LIGHT_GRAY);
        qGenerateButton.addActionListener(e ->
        {
            qTextArea.setText(largePrime(512).toString());
        });

        generateKeysButton.setBackground(Color.LIGHT_GRAY);
        generateKeysButton.addActionListener(e ->
        {
            generateKeys();
        });

        pTextArea.setFont(Resources.TEXT_AREA_FONT);
        qTextArea.setFont(Resources.TEXT_AREA_FONT);
        publicKeyTextArea.setFont(Resources.TEXT_AREA_FONT);
        privateKeyTextArea.setFont(Resources.TEXT_AREA_FONT);

        // Set up panel
        this.setLayout(new GridLayout(0, 1));
        this.add(mainPanel);
    }

    private void generateKeys()
    {
        // 1. Get primes p and q
        BigInteger p = new BigInteger(pTextArea.getText());
        BigInteger q = new BigInteger(qTextArea.getText());

        // 2. Compute n from p and q
        n_ = p.multiply(q);

        // 3. Compute Phi(n) (Euler's totient function)
        // Phi(n) = (p-1)(q-1)
        BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

        // 4. Find an int e such that 1 < e < Phi(n) and gcd(e,Phi) = 1
        e_ = generateE(phi);

        // 5. Calculate d where  d ≡ e^(-1) (mod Phi(n))
        d_ = generateG(e_, phi);

        // Print results
        publicKeyTextArea.setText(String.format("e = %d", e_));
        privateKeyTextArea.setText(String.format("d = %d", d_));

        System.out.println("p = " + p);
        System.out.println("q = " + q);
        System.out.println("n = " + n_);

    }

    /**
     * generate e by finding a Phi such that they are coprimes (gcd = 1)
     */
    public static BigInteger generateE(BigInteger phi) {
        BigInteger e = BigInteger.ZERO;

        for (e = BigInteger.TWO; e.compareTo(phi) == -1; e = e.add(BigInteger.ONE)) {

            // e is for public key exponent
            if (gcd(e, phi).equals(BigInteger.ONE)) {
                break;
            }
        }

        return e;
    }

    public static BigInteger generateG(BigInteger e, BigInteger phi)
    {
        BigInteger d = BigInteger.ZERO;

        for (int i = 0; i <= 9; i++) {
            BigInteger x = BigInteger.valueOf(i).multiply(phi);
            x = x.add(BigInteger.ONE);

            if (x.mod(e).equals(BigInteger.ZERO))
            {
                d = x.divide(e);
                break;
            }
        }

        return d;
    }

    /** Recursive EXTENDED Euclidean algorithm, solves Bezout's identity (ax + by = gcd(a,b))
     * and finds the multiplicative inverse which is the solution to ax ≡ 1 (mod m)
     * returns [d, p, q] where d = gcd(a,b) and ap + bq = d
     * Note: Uses BigInteger operations
     */
    public static BigInteger[] extendedEuclidAlgorithm(BigInteger a, BigInteger b) {
        if (b.equals(BigInteger.ZERO)) return new BigInteger[] {
                a, BigInteger.ONE, BigInteger.ZERO
        }; // { a, 1, 0 }
        BigInteger[] vals = extendedEuclidAlgorithm(b, a.mod(b));
        BigInteger d = vals[0];
        BigInteger p = vals[2];
        BigInteger q = vals[1].subtract(a.divide(b).multiply(vals[2]));
        return new BigInteger[] {
                d, p, q
        };
    }

    /**
     * Takes a string and converts each character to an ASCII decimal value
     * Returns BigInteger
     */
    public static BigInteger stringCipher(String message) {
        message = message.toUpperCase();                //---->>>> DIRTY
        String cipherString = "";

        for (int i = 0; i < message.length() ; i++) {
            int ch = (int) message.charAt(i);

            cipherString += ch;

        }
        BigInteger cipherBig = new BigInteger(String.valueOf(cipherString));
        return cipherBig;
    }


    /**
     * Takes a BigInteger that is ciphered and converts it back to plain text
     *	returns a String
     */
    public static String cipherToString(BigInteger message) {
        String cipherString = message.toString();
        String output = "";

        for (int i = 0; i < cipherString.length() ; i+=2) {
            int temp = Integer.parseInt(cipherString.substring(i, i + 2));
            char ch = (char) temp;
            output += ch;

        }
        return output;
    }

    public static BigInteger gcd(BigInteger a, BigInteger b) {
        if (b.equals(BigInteger.ZERO)) {
            return a;
        } else {
            return gcd(b, a.mod(b));
        }
    }

    /**
     * Generates a random large prime number of specified bitlength
     *
     */
    public static BigInteger largePrime(int bits) {
        Random randomInteger = new Random();
        BigInteger largePrime = BigInteger.probablePrime(bits, randomInteger);
        return largePrime;
    }
}
