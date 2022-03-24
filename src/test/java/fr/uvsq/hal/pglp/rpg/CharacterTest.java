package fr.uvsq.hal.pglp.rpg;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static fr.uvsq.hal.pglp.rpg.Ability.Strength;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CharacterTest {
  private Character frodon;

  @BeforeEach
  public void setup() {
    Dice.setSeed(1L);

    frodon = new CharacterBuilder("Frodon")
      .nonRamdomAbilities(Ability.values())
      .build();
  }

  @Test
  public void aCharacterShouldPerformAnAbilityCheck() {
    // Strength = 15 (2)
    // d20 rolled = 13
    assertTrue(frodon.abilityCheck(Strength, DifficultyClass.VeryEasy));
    // d20 rolled : 17
    assertTrue(frodon.abilityCheck(Strength, DifficultyClass.Easy));
    // d20 rolled : 1
    assertFalse(frodon.abilityCheck(Strength, DifficultyClass.Medium));
    // d20 rolled : 19
    assertTrue(frodon.abilityCheck(Strength, DifficultyClass.Hard));
    // d20 rolled : 1
    assertFalse(frodon.abilityCheck(Strength, DifficultyClass.VeryHard));
    // d20 rolled : 10
    assertFalse(frodon.abilityCheck(Strength, DifficultyClass.NearlyImpossible));
  }
}
