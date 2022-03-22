package fr.uvsq.hal.pglp.rpg;

import org.junit.jupiter.api.Test;

import static fr.uvsq.hal.pglp.rpg.Ability.*;
import static org.junit.jupiter.api.Assertions.*;

public class CharacterBuilderTest {
  @Test
  public void aCharacterShouldHaveMandatoryAttributes() {
    Character frodon = new Character.Builder("Frodon").build();
    assertCharacter(frodon, "Frodon");
  }

  @Test
  public void aCharacterNameShouldNotBeNull() {
    Exception exception = assertThrows(NullPointerException.class, () -> new Character.Builder(null).build());
    assertEquals("A character should have a name.", exception.getMessage());
  }

  @Test
  public void aCharacterNameShouldNotBeBlank() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> new Character.Builder("  ").build());
    assertEquals("A character name should not be blank.", exception.getMessage());
  }

  @Test
  public void aCharacterShouldHaveAnOrderOnAbalities() {
    Ability[] abilitiesOrder = { Strength, Dexterity, Constitution, Intelligence, Wisdom, Charisma };
    Character frodon = new Character.Builder("Frodon", abilitiesOrder).build();
    assertCharacter(frodon, "Frodon", abilitiesOrder);
  }

  @Test
  public void anAbilitiesOrderShouldMentionAllAbilities() {
    Ability[] abilitiesOrder = { Strength, Constitution, Intelligence, Wisdom, Charisma };
    Exception exception = assertThrows(IllegalArgumentException.class, () -> new Character.Builder("Frodon", abilitiesOrder).build());
    assertEquals("The abilities order should mention each ability once and only once.", exception.getMessage());
  }

  @Test
  public void anAbilitiesOrderShouldHaveNoDuplicate() {
    Ability[] abilitiesOrder = { Strength, Strength, Dexterity, Constitution, Intelligence, Wisdom, Charisma };
    Exception exception = assertThrows(IllegalArgumentException.class, () -> new Character.Builder("Frodon", abilitiesOrder).build());
    assertEquals("duplicate element: Strength", exception.getMessage());
  }

  @Test
  public void aCharacterCouldHaveNonRandomAbilityScores() {
    Ability[] abilitiesOrder = { Strength, Dexterity, Constitution, Intelligence, Wisdom, Charisma };
    Character frodon = new Character.Builder("Frodon")
      .nonRamdomAbilities(abilitiesOrder)
      .build();
    assertCharacter(frodon, "Frodon", abilitiesOrder);
    byte[] expectedScores = { 15, 14, 13, 12, 10, 8 };
    assertEquals(expectedScores[0], frodon.get(Strength).getScore());
    assertEquals(expectedScores[1], frodon.get(Dexterity).getScore());
    assertEquals(expectedScores[2], frodon.get(Constitution).getScore());
    assertEquals(expectedScores[3], frodon.get(Intelligence).getScore());
    assertEquals(expectedScores[4], frodon.get(Wisdom).getScore());
    assertEquals(expectedScores[5], frodon.get(Charisma).getScore());
  }

  @Test
  public void anAbilityCouldBeDefinedIndividually() {
    Character frodon = new Character.Builder("Frodon")
      .setAbility(Strength, (byte) 20)
      .build();
    assertEquals(20, frodon.get(Strength).getScore());
  }

  @Test
  public void aCharacterCouldHaveAProficiencyBonus() {
    Character frodon = new Character.Builder("Frodon")
      .setProficiencyBonus((byte) 2)
      .build();
    assertEquals(2, frodon.getProficiencyBonus());
  }

  private static void assertCharacter(final Character character, final String expectedName) {
    assertEquals(expectedName, character.getName());
    int abilitySum = 0;
    for (Ability ability : Ability.values()) {
      AbilityScore abilityScore = character.get(ability);
      assertTrue(abilityScore.isValid());
      abilitySum += abilityScore.getScore();
    }
    assertTrue(abilitySum >= Character.Builder.MIN_SCORE && abilitySum <= Character.Builder.MAX_SCORE);
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
