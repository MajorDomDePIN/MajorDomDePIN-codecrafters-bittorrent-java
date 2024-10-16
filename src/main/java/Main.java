import com.google.gson.Gson;
// import com.dampcake.bencode.Bencode; - available if you need it!

public class Main {
  private static final Gson gson = new Gson();

  public static void main(String[] args) throws Exception {
    String command = args[0];
    if ("decode".equals(command)) {
      String bencodedValue = args[1];
      String decoded;
      try {
        decoded = decodeBencode(bencodedValue);
        System.out.println(gson.toJson(decoded));
      } catch (RuntimeException e) {
        System.out.println(e.getMessage());
        return;
      }
      try {
        Integer decodedInt = decodeBencodeInt(bencodedValue);
        System.out.println(gson.toJson(decodedInt));
      } catch (RuntimeException e) {
        System.out.println(e.getMessage());
        return;
      }

    } else {
      System.out.println("Unknown command: " + command);
    }
  }

  static String decodeBencode(String bencodedString) {
    if (Character.isDigit(bencodedString.charAt(0))) {
      int firstColonIndex = 0;
      for (int i = 0; i < bencodedString.length(); i++) {
        if (bencodedString.charAt(i) == ':') {
          firstColonIndex = i;
          break;
        }
      }
      int length = Integer.parseInt(bencodedString.substring(0, firstColonIndex));
      return bencodedString.substring(firstColonIndex + 1, firstColonIndex + 1 + length);
    } else {
      throw new RuntimeException("Only strings are supported at the moment");
    }
  }

  static Integer decodeBencodeInt(String bencodedString) {
    if (Character.isLetter(bencodedString.charAt(0))
        && Character.isLetter(bencodedString.charAt(bencodedString.length() - 1))) {
      String encoded = bencodedString.substring(1, bencodedString.length() - 2);
      return Integer.parseInt(encoded);
    } else {
      throw new RuntimeException("There was an error with your integer value");
    }
  }
}