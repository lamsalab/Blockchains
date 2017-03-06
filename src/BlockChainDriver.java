
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class BlockChainDriver {

	public static void main(String[] args) throws NoSuchAlgorithmException {
		int initial = Integer.parseInt(args[0]);
		BlockChain blockChain = new BlockChain(initial);
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println(blockChain.toString());
			System.out.println("Command?");
			String command = scanner.nextLine();

			if (command.equals("mine")) {
				System.out.println("Amount transferred?");
				String amount = scanner.nextLine();
				Block mineBlk = blockChain.mine(Integer.parseInt(amount));
				System.out.println("amount = " + mineBlk.getAmount() + ", nonce = " + mineBlk.getNonce());
			}

			if (command.equals("append")) {
				System.out.println("Amount transferred?");
				String amount = scanner.nextLine();
				System.out.println("Nonce?");
				String nonce = scanner.nextLine();
				Block appendBlk = new Block(blockChain.getSize() + 1, Integer.parseInt(amount), blockChain.getHash(),
						Long.parseLong(nonce));
				blockChain.append(appendBlk);
			}

			if (command.equals("check")) {
				if (blockChain.isValidBlockChain()) {
					System.out.println("Chain is valid!");
				} else {
					System.out.println("Chain is invalid!");
				}
			}

			if (command.equals("remove")) {
				blockChain.removeLast();
			}

			if (command.equals("report")) {
				blockChain.printBalances();
			}

			if (command.equals("help")) {
				System.out.println("Valid commands:\n" + "	mine: discovers the nonce for a given transaction\n"
						+ "	append: appends a new block onto the end of the chain\n"
						+ "	remove: removes the last block from the end of the chain\n"
						+ "	check: checks that the block chain is valid \n"
						+ "	report: reports the balances of Alice and Bob \n"
						+ "	help: prints this list of commands\n" + "	quit: quits the program");
			}

			if (command.equals("quit")) {
				scanner.close();
				System.exit(0);
			}

		}

	}

}
