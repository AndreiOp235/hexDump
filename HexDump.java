import java.io.FileInputStream;
import java.io.IOException;

public class HexDumper {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java HexDumper <file>");
            System.exit(1);
        }

        String filePath = args[0];

        try (FileInputStream fis = new FileInputStream(filePath)) {
            int bytesRead;
            byte[] buffer = new byte[16];
            StringBuilder hexString = new StringBuilder();
            StringBuilder printableChars = new StringBuilder();

            while ((bytesRead = fis.read(buffer)) != -1) {
                hexString.setLength(0);
                printableChars.setLength(0);

                for (int i = 0; i < bytesRead; i++) {
                    byte b = buffer[i];
                    hexString.append(String.format("%02X ", b));
                    if (b >= 32 && b <= 126) {
                        printableChars.append((char) b);
                    } else {
                        printableChars.append(".");
                    }
                }

                // Adjust for alignment
                if (bytesRead < 16) {
                    int remaining = (16 - bytesRead) * 3;
                    hexString.append(" ".repeat(remaining));
                }

                System.out.printf("%-48s  %s%n", hexString.toString(), printableChars.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
