import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static final List<Character> signs = Arrays.asList('*','/','+','-');

    public static void main(String[] args)
    {
        String calc;
        List<String> quitWords = Arrays.asList("quit", "Quit", "q", "exit", "close", "Close");

        while(true) {
            System.out.println("Enter calculation here: (To close enter quit) ");
            Scanner calcScanner = new Scanner(System.in);
            calc = calcScanner.nextLine();

            if (quitWords.contains(calc)) break;

            var spacedCalc = spaceCalculation(calc.replace(' ', '\0'));
            System.out.println("result :" + Calculate(spacedCalc));
        }
    }

    private static double Calculate(ArrayList<String> calc)
    {
        double result = 0;
        while (calc.size() > 1) {
            int workingIndex = 0;
            var firstMulti = !calc.contains("*") ? Double.POSITIVE_INFINITY : calc.indexOf("*");
            var firstDiv = !calc.contains("/") ? Double.POSITIVE_INFINITY : calc.indexOf("/");
            var firstAdd = !calc.contains("+") ? Double.POSITIVE_INFINITY : calc.indexOf("+");
            var firstMin = !calc.contains("-") ? Double.POSITIVE_INFINITY : calc.indexOf("-");

            if (firstDiv != firstMulti) {
                workingIndex = (int) Math.min(firstDiv, firstMulti);
            } else if (firstAdd != firstMin) {
                workingIndex = (int) Math.min(firstAdd, firstMin);
            }

            String sign = calc.get(workingIndex);
            var numberB = Double.parseDouble(calc.get(workingIndex+1));

            var numberA = Double.parseDouble(calc.get(workingIndex-1));

            result = switch (sign) {
                case "+" -> numberA + numberB;
                case "-" -> numberA - numberB;
                case "*" -> numberA * numberB;
                case "/" -> (double) numberA / numberB;
                default -> throw new ArithmeticException("Invalid operation");
            };

            calc.remove(workingIndex + 1);
            calc.remove(workingIndex);
            calc.remove(workingIndex - 1);
            calc.add(workingIndex - 1, Double.toString(result));
        }

        return result;
    }

    private static ArrayList<String> spaceCalculation(String calc) {
        char[] calcChars = calc.toCharArray();
        ArrayList<Character> spacedCalc = new ArrayList<Character>();

        for (char calcChar : calcChars) {
            if (signs.contains(calcChar)) {
                spacedCalc.add(' ');
                spacedCalc.add(calcChar);
                spacedCalc.add(' ');
                continue;
            }

            spacedCalc.add(calcChar);
        }

        return new ArrayList<String>(Arrays.stream(spacedCalc
                                            .stream()
                                            .map(String::valueOf)
                                            .collect(Collectors.joining())
                                            .split(" "))
                                            .toList());
    }
}