package fr.uvsq.hal.pglp.rpg;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.EnumSet;

import static fr.uvsq.hal.pglp.rpg.Ability.*;
import static fr.uvsq.hal.pglp.rpg.CharacterBuilder.predefinedScores;
import static fr.uvsq.hal.pglp.rpg.Skill.*;
import static org.junit.jupiter.api.Assertions.*;

public class CharacterBuilderTest {
  private final int[] expectedRandomAbilitiesScores = { 14, 13, 11, 11, 10, 7 };

  @BeforeEach
  public void setup() {
    Dice.setSeed(1L);
  }

  @Test
  public void aCharacterShouldHaveMandatoryAttributes() {
    Character frodon = new CharacterBuilder("Frodon").build();
    assertRandomCharacter(frodon, "Frodon", Ability.values(), expectedRandomAbilitiesScores, CharacterBuilder.FIRST_LEVEL_PROFICIENCY_BONUS);
  }

  @Test
  public void aCharacterNameShouldNotBeNull() {
    Exception exception = assertThrows(NullPointerException.class, () -> new CharacterBuilder(null).build());
    assertEquals("A character should have a name.", exception.getMessage());
  }

  @Test
  public void aCharacterNameShouldNotBeBlank() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> new CharacterBuilder("  ").build());
    assertEquals("A character name should not be blank.", exception.getMessage());
  }

  @Test
  public void aCharacterShouldHaveAnOrderOnAbilities() {
    Ability[] abilitiesOrder = { Charisma, Wisdom, Intelligence, Constitution, Dexterity, Strength };
    Character frodon = new CharacterBuilder("Frodon", abilitiesOrder).build();
    assertRandomCharacter(frodon, "Frodon", abilitiesOrder, expectedRandomAbilitiesScores, CharacterBuilder.FIRST_LEVEL_PROFICIENCY_BONUS);
  }

  @Test
  public void anAbilitiesOrderShouldMentionAllAbilities() {
    Ability[] abilitiesOrder = { Strength, Constitution, Intelligence, Wisdom, Charisma };
    Exception exception = assertThrows(IllegalArgumentException.class, () -> new CharacterBuilder("Frodon", abilitiesOrder).build());
    assertEquals("The abilities order should mention each ability once and only once.", exception.getMessage());
  }

  @Test
  public void anAbilitiesOrderShouldHaveNoDuplicate() {
    Ability[] abilitiesOrder = { Strength, Strength, Dexterity, Constitution, Intelligence, Wisdom, Charisma };
    Exception exception = assertThrows(IllegalArgumentException.class, () -> new CharacterBuilder("Frodon", abilitiesOrder).build());
    assertEquals("duplicate element: Strength", exception.getMessage());
  }

  @Test
  public void aCharacterCouldHaveNonRandomAbilityScores() {
    Ability[] abilitiesOrder = { Charisma, Wisdom, Intelligence, Constitution, Dexterity, Strength };
    Character frodon = new CharacterBuilder("Frodon")
      .nonRamdomAbilities(abilitiesOrder)
      .build();
    assertCharacter(frodon, "Frodon", abilitiesOrder, predefinedScores, CharacterBuilder.FIRST_LEVEL_PROFICIENCY_BONUS);
  }

  @Test
  public void anAbilityCouldBeDefinedIndividually() {
    Character frodon = new CharacterBuilder("Frodon")
      .setAbility(Strength, 20)
      .build();
    assertEquals(20, frodon.get(Strength).getScore());
    int[] expectedScores = { 20, 13, 11, 11, 10, 7 };
    assertCharacter(frodon, "Frodon", Ability.values(), expectedScores, CharacterBuilder.FIRST_LEVEL_PROFICIENCY_BONUS);
  }

  @Test
  public void anOutOfBoundScoreShouldThrowAnException() {
    Exception exception = assertThrows(
      IllegalArgumentException.class,
      () -> new CharacterBuilder("Frodon").setAbility(Strength, 30).build());
    assertEquals("The score is invalid.", exception.getMessage());
  }

  @Test
  public void aCharacterCouldHaveAProficiencyBonus() {
    Character frodon = new CharacterBuilder("Frodon")
      .setProficiencyBonus(4)
      .build();
    assertRandomCharacter(frodon, "Frodon", Ability.values(), expectedRandomAbilitiesScores, 4);
  }

  @Test
  public void aCharacterCouldHaveSkills() {
    Character frodon = new CharacterBuilder("Frodon")
      .isProficientIn(Acrobatics, Perception)
      .build();
    assertRandomCharacter(frodon, "Frodon", Ability.values(), expectedRandomAbilitiesScores, CharacterBuilder.FIRST_LEVEL_PROFICIENCY_BONUS);
    assertSkills(frodon, Acrobatics, Perception);
  }

  private static void assertCharacter(
    final Character character,
    final String name,
    final Ability[] abilitiesOrder,
    final int[] abilitiesScores,
    final int proficiencyBonus) {
    assertEquals(name, character.getName());

    for (int i = 0; i < abilitiesScores.length; i++) {
      Ability ability = abilitiesOrder[i];
      int expectedScore = abilitiesScores[i];
      int abilityScore = character.get(ability).getScore();
      assertEquals(expectedScore, abilityScore);
    }

    assertEquals(proficiencyBonus, character.getProficiencyBonus());
  }

  private static void assertRandomCharacter(
    final Character character,
    final String name,
    final Ability[] abilitiesOrder,
    final int[] abilitiesScores,
    final int proficiencyBonus) {
    assertCharacter(character, name, abilitiesOrder, abilitiesScores, proficiencyBonus);

    int abilitySum = 0;
    Ability previousAbility = null;
    for (int i = 0; i < abilitiesScores.length; i++) {
      Ability ability = abilitiesOrder[i];
      abilitySum += character.get(ability).getScore();
      if (previousAbility != null) {
        assertTrue(character.get(previousAbility).compareTo(character.get(ability)) >= 0,
          previousAbility + " have to be larger than " + ability);
      }
      previousAbility = ability;
    }
    assertTrue(abilitySum >= CharacterBuilder.MIN_SUM_SCORE && abilitySum <= CharacterBuilder.MAX_SUM_SCORE);
  }

  private static void assertSkills(final Character character, final Skill... proficientSkills) {
    EnumSet<Skill> proficientSkillsAsSet = EnumSet.copyOf(Arrays.asList(proficientSkills));
    for (Skill skill : Skill.values()) {
      if (proficientSkillsAsSet.contains(skill)) {
        assertTrue(character.isProficientIn(skill));
      } else {
        assertFalse(character.isProficientIn(skill));
      }
    }
  }
}
