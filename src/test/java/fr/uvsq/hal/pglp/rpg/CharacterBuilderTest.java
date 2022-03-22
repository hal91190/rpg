package fr.uvsq.hal.pglp.rpg;

import org.junit.jupiter.api.Test;

import static fr.uvsq.hal.pglp.rpg.Ability.*;
import static fr.uvsq.hal.pglp.rpg.CharacterBuilder.predefinedScores;
import static org.junit.jupiter.api.Assertions.*;

public class CharacterBuilderTest {
  @Test
  public void aCharacterShouldHaveMandatoryAttributes() {
    Character frodon = new CharacterBuilder("Frodon").build();
    assertCharacter(frodon, "Frodon");
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
    Ability[] abilitiesOrder = { Strength, Dexterity, Constitution, Intelligence, Wisdom, Charisma };
    Character frodon = new CharacterBuilder("Frodon", abilitiesOrder).build();
    assertCharacter(frodon, "Frodon", abilitiesOrder);
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
    Ability[] abilitiesOrder = { Strength, Dexterity, Constitution, Intelligence, Wisdom, Charisma };
    Character frodon = new CharacterBuilder("Frodon")
      .nonRamdomAbilities(abilitiesOrder)
      .build();
    assertCharacter(frodon, "Frodon", abilitiesOrder);
    assertEquals(predefinedScores[0], frodon.get(Strength).getScore());
    assertEquals(predefinedScores[1], frodon.get(Dexterity).getScore());
    assertEquals(predefinedScores[2], frodon.get(Constitution).getScore());
    assertEquals(predefinedScores[3], frodon.get(Intelligence).getScore());
    assertEquals(predefinedScores[4], frodon.get(Wisdom).getScore());
    assertEquals(predefinedScores[5], frodon.get(Charisma).getScore());
  }

  @Test
  public void anAbilityCouldBeDefinedIndividually() {
    Character frodon = new CharacterBuilder("Frodon")
      .setAbility(Strength, (byte) 20)
      .build();
    assertEquals(20, frodon.get(Strength).getScore());
  }

  @Test
  public void anOutOfBoundScoreShouldThrowAnException() {
    Exception exception = assertThrows(
      IllegalArgumentException.class,
      () -> new CharacterBuilder("Frodon").setAbility(Strength, (byte) 30).build());
    assertEquals("The score is invalid.", exception.getMessage());
  }

  @Test
  public void aCharacterCouldHaveAProficiencyBonus() {
    Character frodon = new CharacterBuilder("Frodon")
      .setProficiencyBonus((byte) 4)
      .build();
    assertEquals(4, frodon.getProficiencyBonus());
  }

  private static void assertCharacter(final Character character, final String expectedName) {
    assertEquals(expectedName, character.getName());
    int abilitySum = 0;
    for (Ability ability : Ability.values()) {
      AbilityScore abilityScore = character.get(ability);
      assertTrue(abilityScore.isValid());
      abilitySum += abilityScore.getScore();
    }
    assertTrue(abilitySum >= CharacterBuilder.MIN_SUM_SCORE && abilitySum <= CharacterBuilder.MAX_SUM_SCORE);
    assertEquals(CharacterBuilder.FIRST_LEVEL_PROFICIENCY_BONUS, character.getProficiencyBonus());
  }

  private static void assertCharacter(final Character character, final String expectedName, final Ability[] abilitiesOrder) {
    assertCharacter(character, expectedName);

    Ability previousAbility = null;
    for (Ability ability : abilitiesOrder) {
      if (previousAbility != null) {
        assertTrue(character.get(previousAbility).compareTo(character.get(ability)) >= 0,
          previousAbility + " have to be larger than " + ability);
      }
      previousAbility = ability;
    }
  }
}
