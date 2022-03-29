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
    assertTrue(frodon.abilityCheck(Strength, VeryEasy));
    // d20 rolled : 17
    assertTrue(frodon.abilityCheck(Strength, Easy));
    // d20 rolled : 1
    assertFalse(frodon.abilityCheck(Strength, Medium));
    // d20 rolled : 19
    assertTrue(frodon.abilityCheck(Strength, Hard));
    // d20 rolled : 1
    assertFalse(frodon.abilityCheck(Strength, VeryHard));
    // d20 rolled : 10
    assertFalse(frodon.abilityCheck(Strength, NearlyImpossible));
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
    assertTrue(frodon.skillCheck(Acrobatics, VeryEasy));
    // d20 rolled : 17
    assertTrue(frodon.skillCheck(Acrobatics, Easy));
    // d20 rolled : 1
    assertFalse(frodon.skillCheck(Acrobatics, Medium));
    // d20 rolled : 19
    assertTrue(frodon.skillCheck(Acrobatics, Hard));
    // d20 rolled : 1
    assertFalse(frodon.skillCheck(Acrobatics, VeryHard));
    // d20 rolled : 10
    assertFalse(frodon.skillCheck(Acrobatics, NearlyImpossible));
  }
}
