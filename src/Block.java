import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Block {
	private int num;
	private int amount;
	private Hash prevHash;
	private long nonce;
	private Hash curHash;

	public Block(int num, int amount, Hash prevHash) throws NoSuchAlgorithmException {
		this.num = num;
		this.amount = amount;
		this.prevHash = prevHash;
		this.nonce = miningNonce(num, amount, prevHash);
		this.curHash = calculateHash(num, amount, prevHash, nonce);
	}

	public Block(int num, int amount, Hash prevHash, long nonce) throws NoSuchAlgorithmException {
		this.num = num;
		this.amount = amount;
		this.prevHash = prevHash;
		this.nonce = nonce;
		this.curHash = calculateHash(num, amount, prevHash, nonce);

	}

	public int getNum() {
		return num;
	}

	public int getAmount() {
		return amount;
	}

	public long getNonce() {
		return nonce;
	}

	public Hash getPrevHash() {
		return prevHash;
	}

	public Hash getHash() {
		return curHash;
	}

	/**
	 * @return String
	 */
	public String toString() {
		return "Block " + this.num + "(Amount: " + this.amount + ", Nonce: " + this.nonce + ", prevHash: "
				+ this.prevHash + ", hash: " + this.curHash + ")";
	}

	/**
	 * 
	 * @param num a positive integer
	 * @param amount a positive integer
	 * @param prevHash a Hash
	 * @param nonce a long
	 * @return Hash
	 * @throws NoSuchAlgorithmException
	 */
	public static Hash calculateHash(int num, int amount, Hash prevHash, long nonce) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("sha-256");
		if (prevHash == null) {
			prevHash = new Hash(new byte[] { 0, 0, 0 });
		}
		md.update(ByteBuffer.allocate(4 + 4 + prevHash.getData().length + 8).putInt(num).putInt(amount)
				.put(prevHash.getData()).putLong(nonce).array());
		Hash hash = new Hash(md.digest());
		return hash;
	}

	/**
	 * 
	 * @param num a positive integer
	 * @param amount a positive integer
	 * @param prevHash a Hash
	 * @return long
	 * @throws NoSuchAlgorithmException
	 */
	public static long miningNonce(int num, int amount, Hash prevHash) throws NoSuchAlgorithmException {
		for (long nonce = 0; nonce <= Long.MAX_VALUE; nonce++) {
			if (calculateHash(num, amount, prevHash, nonce).isValid()) {
				return nonce;
			}
		}
		return 0;
	}
}
