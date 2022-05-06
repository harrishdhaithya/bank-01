import java.util.Scanner;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;



import de.taimos.totp.TOTP;

public class AuthTest {
	public static String getTOTPCode(String secretKey) {
	    Base32 base32 = new Base32();
	    byte[] bytes = base32.decode(secretKey);
	    System.out.println(bytes.toString());
	    String hexKey = Hex.encodeHexString(bytes);
	    System.out.println(hexKey);
	    return TOTP.getOTP(hexKey);
	}
	
	public static void main(String[] args) {
		String secret = "QDWSM3OYBPGTEVSPB5FKVDM3CSNCWHVK";
		Scanner scanner = new Scanner(System.in);
		String code = scanner.nextLine();
		if (code.equals(getTOTPCode(secret))) {
		    System.out.println("Logged in successfully");
		} else {
		    System.out.println("Invalid 2FA Code");
		}
	}
}
