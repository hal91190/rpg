package fr.uvsq.hal.pglp.rpg;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AbilityScoreTest {
  @ParameterizedTest
  @CsvSource({
    " 1,  -5", " 2,  -4", " 3,  -4", " 4,  -3", " 5,  -3",
    " 6,  -2", " 7,  -2", " 8,  -1", " 9,  -1", "10,   0",
    "11,   0", "12,   1", "13,   1", "14,   2", "15,   2",
    "16,   3", "17,   3", "18,   4", "19,   4", "20,   5",
    "21,   5", "22,   6", "23,   6", "24,   7", "25,   7",
    "26,   8", "27,   8", "28,   9", "29,   9", "30,  10"
  })
  void anAbilityScoreShouldHabeAModifier(byte score, byte modifier) {
    AbilityScore as = new AbilityScore(score);
    assertEquals(modifier, as.getModifier(),
      "The modifier " + as.getModifier() + " does not correspond to score " + score + " (expected: " + modifier + ")");
  }
}
