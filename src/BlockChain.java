import java.security.NoSuchAlgorithmException;

public class BlockChain {
	private static class Node {
		public Block value;
		public Node next;

		public Node(Block value, Node next) {
			this.value = value;
			this.next = next;
		}
	}

	private Node first;
	private Node last;
	private int num = 0;
	private int netTransaction;
	private int initial;

	public BlockChain(int initial) throws NoSuchAlgorithmException {
		first = new Node(new Block(num, initial, null), null);
		last = first;
		netTransaction = initial;
		this.initial = initial;
	}

	public Block mine(int amount) throws NoSuchAlgorithmException {
		return new Block(num, amount, last.value.getHash());
	}

	public int getSize() {
		return num;
	}

	public void append(Block blk) {
		if (!blk.getHash().isValid() && !blk.getPrevHash().equals(last.value.getHash())) {
			throw new IllegalArgumentException();
		}
		Node lastNode = new Node(blk, null);
		last.next = lastNode;
		last = lastNode;
		num++;
		netTransaction += blk.getAmount();

	}

	public boolean removeLast() {
		if (first == last) {
			return false;
		} else {
			Node cur = first;
			while (cur.next.next != null) {
				cur = cur.next;
			}
			last = cur;
			num--;
			return true;
		}
	}

	public Hash getHash() {
		return last.value.getHash();
	}

	public boolean isValidBlockChain() {
		Node cur = first;
		while (cur.next != null) {
			if (!(cur.value.getHash().isValid() || cur.value.getHash().equals(cur.value.getPrevHash())
					|| netTransaction >= 0 || initial >= netTransaction
					|| cur.value.getNum() == cur.next.value.getNum() - 1)) {
				return false;
			}
			cur = cur.next;
		}
		return true;
	}

	public void printBalances() {
		System.out.println("Alice: " + netTransaction + ", Bob: " + (initial - netTransaction));
	}

	public String toString() {
		String ret = "";
		Node cur = first;
		ret += cur.value.toString();
		while (cur.next != null) {
			ret += "\n";
			cur = cur.next;
			ret += cur.value.toString();
		}
		return ret;
	}
}
