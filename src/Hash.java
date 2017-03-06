import java.util.Arrays;

public class Hash {
	private byte[] data;

	public Hash(byte[] data) {
		this.data = data;
	}

	public byte[] getData() {
		return data;
	}

	/**
	 * @return boolean
	 */
	public boolean isValid() {
		if (this.data[0] == 0 && this.data[1] == 0 && this.data[2] == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @return String
	 */
	public String toString() {
		String ret = "";
		for (int i = 0; i < data.length; i++) {
			int temp = Byte.toUnsignedInt(data[i]);
			ret += String.format("%02x", temp);
		}
		return ret;
	}

	/**
	 * @param other an Object
	 * @return boolean
	 */
	public boolean equals(Object other) {
		if (other instanceof Hash) {
			Hash o = (Hash) other;
			if (Arrays.equals(o.data, this.data)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

}
