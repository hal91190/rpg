package fr.uvsq.hal.pglp.rpg;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static fr.uvsq.hal.pglp.rpg.Ability.Dexterity;
import static fr.uvsq.hal.pglp.rpg.Ability.Strength;
import static fr.uvsq.hal.pglp.rpg.DifficultyClass.*;
import static fr.uvsq.hal.pglp.rpg.Skill.*;
import static org.junit.jupiter.api.Assertions.*;

public class CharacterTest {
  private Character frodon;

  @BeforeEach
  public void setup() {
    Dice.setSeed(1L);

    frodon = new CharacterBuilder("Frodon")
      .nonRamdomAbilities(Ability.values())
      .isProficientIn(Acrobatics, History, Medicine)
      .build();
  }

  @Test
  public void aCharacterShouldPerformAnAbilityCheck() {
    // Strength = 15 (2)
    // d20 rolled = 13
    assertTrue(frodon.checks(Strength, VeryEasy));
    // d20 rolled : 17
    assertTrue(frodon.checks(Strength, Easy));
    // d20 rolled : 1
    assertFalse(frodon.checks(Strength, Medium));
    // d20 rolled : 19
    assertTrue(frodon.checks(Strength, Hard));
    // d20 rolled : 1
    assertFalse(frodon.checks(Strength, VeryHard));
    // d20 rolled : 10
    assertFalse(frodon.checks(Strength, NearlyImpossible));
  }

  @Test
  public void aCharacterShouldHaveACorrectSkillProficiency() {
    assertEquals(frodon.get(Strength).getModifier(), frodon.getProficiencyBonusIn(Athletics));
    assertEquals(frodon.get(Dexterity).getModifier() + frodon.getProficiencyBonus(), frodon.getProficiencyBonusIn(Acrobatics));
  }

  @Test
  public void aCharacterShouldPerformASkillCheck() {
    // Dexterity = 14 (2)
    // d20 rolled = 13
    assertTrue(frodon.checks(Acrobatics, VeryEasy));
    // d20 rolled : 17
    assertTrue(frodon.checks(Acrobatics, Easy));
    // d20 rolled : 1
    assertFalse(frodon.checks(Acrobatics, Medium));
    // d20 rolled : 19
    assertTrue(frodon.checks(Acrobatics, Hard));
    // d20 rolled : 1
    assertFalse(frodon.checks(Acrobatics, VeryHard));
    // d20 rolled : 10
    assertFalse(frodon.checks(Acrobatics, NearlyImpossible));
  }

  @Test
  public void aCharacterShouldPerformVariousChecks() {
    // Dexterity = 14 (2)
    // d20 rolled = 13
    assertFalse(frodon.checks(Strength, VeryHard));
    // d20 rolled : 17
    // d20 rolled : 1
    assertTrue(frodon.checks(Strength, VeryEasy, Character.Advantage.Advantage));
    // d20 rolled : 19
    // d20 rolled : 1
    assertFalse(frodon.checks(Strength, Medium, Character.Advantage.Disadvantage));
    // d20 rolled : 10
    assertFalse(frodon.checks(Acrobatics, Medium));
    // d20 rolled : 7
    // d20 rolled : 15
    assertFalse(frodon.checks(Acrobatics, Hard, Character.Advantage.Advantage));
    // d20 rolled : 9
    // d20 rolled : 17
    assertFalse(frodon.checks(Acrobatics, NearlyImpossible, Character.Advantage.Disadvantage));
  }
}
