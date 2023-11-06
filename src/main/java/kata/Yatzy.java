package kata;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class Yatzy {

    public Yatzy() {
    }

    public static int fours(Integer... dice) {
        return getSumOfGivenDiceNumber(dice, 4);
    }

    private static int getSumOfGivenDiceNumber(Integer[] dice, int diceNumber) {
        return getSumOfDice(getDiceWithSameNumber(dice, diceNumber));
    }

    private static List<Integer> getDiceWithSameNumber(Integer[] dice, int diceNumber) {
        return Arrays.stream(dice).filter(d -> isSameDice(d, diceNumber)).collect(toList());
    }

    private static boolean isSameDice(int dice, int diceToCompare) {
        return dice == diceToCompare;
    }

    private static int getSumOfDice(List<Integer> dice) {
        return dice.stream().mapToInt(Integer::intValue).sum();
    }

    public static int fives(Integer... dice) {
        return getSumOfGivenDiceNumber(dice, 5);
    }

    public static int sixes(Integer... dice) {
        return getSumOfGivenDiceNumber(dice, 6);
    }

    public static int chance(Integer... dice) {
        return getSumOfDice(List.of(dice));
    }

    public static int yatzy(Integer... dice) {
        return Set.copyOf(List.of(dice)).size() == 1 ? 50 : 0;
    }

    public static int ones(Integer... dice) {
        return getSumOfGivenDiceNumber(dice, 1);
    }

    public static int twos(Integer... dice) {
        return getSumOfGivenDiceNumber(dice, 2);
    }

    public static int threes(Integer... dice) {
        return getSumOfGivenDiceNumber(dice, 3);
    }

    public static int onePair(Integer... dice) {
        List<Integer> diceWithTwoOccurrence = getMapOfDiceToOccurrence(dice).entrySet().stream().filter(entry -> entry.getValue() == 2).map(Map.Entry::getKey).collect(toList());
        return diceWithTwoOccurrence.stream().max(Integer::compareTo).orElse(0) * 2;
    }


    public static int twoPairs(Integer... dice) {
        List<Integer> diceWithMoreThanTwoOccurrence = getDiceOfEqualOrGreaterThanGivenOccurrence(dice, 2);
        if (diceWithMoreThanTwoOccurrence.size() != 2) {
            return 0;
        }
        return getSumOfDice(getMultiplicationOfDice(diceWithMoreThanTwoOccurrence, 2));
    }

    private static List<Integer> getMultiplicationOfDice(List<Integer> dice, int multiplicand) {
        return dice.stream().map(currDice -> currDice * multiplicand).collect(Collectors.toList());
    }

    public static int threeOfAKind(Integer... dice) {
        List<Integer> diceWithThreeOccurrence = getDiceOfEqualOrGreaterThanGivenOccurrence(dice, 3);
        return getSumOfDice(getMultiplicationOfDice(diceWithThreeOccurrence, 3));
    }

    private static List<Integer> getDiceOfEqualOrGreaterThanGivenOccurrence(Integer[] dice, int timesOccurred) {
        return getMapOfDiceToOccurrence(dice).entrySet().stream().filter(entry -> entry.getValue() >= timesOccurred).map(Map.Entry::getKey).collect(toList());
    }

    public static int fourOfAKind(Integer... dice) {
        List<Integer> diceWithFourOccurrence = getDiceOfEqualOrGreaterThanGivenOccurrence(dice, 4);
        return getSumOfDice(getMultiplicationOfDice(diceWithFourOccurrence, 4));
    }

    public static int smallStraight(Integer... dice) {
        List<Integer> validDiceForSmallStraight = List.of(1, 2, 3, 4, 5);
        return hasGivenDiceWithOneOccurrence(validDiceForSmallStraight, getMapOfDiceToOccurrence(dice)) ? 15 : 0;
    }

    private static boolean hasGivenDiceWithOneOccurrence(List<Integer> validDice, Map<Integer, Integer> diceWithOccurrence) {
        return validDice.stream().allMatch(dice -> Objects.equals(diceWithOccurrence.get(dice), 1));
    }

    public static int largeStraight(Integer... dice) {
        List<Integer> validDiceForLargeStraight = List.of(2, 3, 4, 5, 6);
        return hasGivenDiceWithOneOccurrence(validDiceForLargeStraight, getMapOfDiceToOccurrence(dice)) ? 20 : 0;
    }

    public static int fullHouse(Integer... dice) {
        Map<Integer, Integer> diceWithOccurrence = getMapOfDiceToOccurrence(dice);
        boolean hasTwosPair = diceWithOccurrence.containsValue(2);
        boolean hasThreesPair = diceWithOccurrence.containsValue(3);
        if (!hasTwosPair || !hasThreesPair) {
            return 0;
        }
        return diceWithOccurrence
            .entrySet()
            .stream()
            .filter(entry -> entry.getValue().equals(2) || entry.getValue().equals(3))
            .mapToInt(entry -> entry.getKey() * entry.getValue())
            .sum();
    }

    private static Map<Integer, Integer> getMapOfDiceToOccurrence(Integer[] dice) {
        return Stream.of(dice).collect(groupingBy(Function.identity(), collectingAndThen(counting(), Long::intValue)));
    }
}
