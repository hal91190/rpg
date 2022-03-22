package fr.uvsq.hal.pglp.rpg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * La classe <code>Character</code> représente un personnage de JdR.
 *
 * @author hal
 * @version 2022
 */
public class Character {
  //TODO Race
  //TODO Class

  /** Nom */
  private final String name;

  /** Caractéristiques */
  private final Map<Ability, AbilityScore> abilities;
  private byte proficiencyBonus;

  private Character(Builder builder) {
    this.name = builder.name;
    this.abilities = builder.abilities;
    this.proficiencyBonus = builder.proficiencyBonus;
  }

  public String getName() {
    return name;
  }

  public AbilityScore get(Ability ability) {
    return abilities.get(ability);
  }

  public byte getProficiencyBonus() {
    return proficiencyBonus;
  }

  public static class Builder {
    public static final int MIN_SCORE = 60;
    public static final int MAX_SCORE = 80;
    public static final byte[] predefinedScores = { 15, 14, 13, 12, 10, 8 };

    final Logger logger = LoggerFactory.getLogger(Builder.class);

    private final String name;
    private Map<Ability, AbilityScore> abilities;
    private byte proficiencyBonus;

    public Builder(String name) {
      this(name, Ability.values());
    }

    public Builder(String name, Ability[] abilitiesOrder) {
      validateName(name);
      this.name = name;

      validateAbilitiesOrder(abilitiesOrder);
      int abilitySum;
      AbilityScore[] abilityScores = new AbilityScore[Ability.values().length];
      do {
        Arrays.setAll(abilityScores, i -> new AbilityScore());
        abilitySum = Arrays.stream(abilityScores).mapToInt(AbilityScore::getScore).sum();
      } while (abilitySum < MIN_SCORE || abilitySum > MAX_SCORE);
      Arrays.sort(abilityScores, Comparator.reverseOrder());
      abilities = IntStream
        .range(0, Ability.values().length)
        .boxed()
        .collect(
          Collectors.toMap(
            i -> abilitiesOrder[i],
            i -> abilityScores[i],
            (a1, a2) -> a1 /* never used */,
            () -> new EnumMap<>(Ability.class)));
      logger.debug("{} : {}", this.name, abilities);
    }

    private void validateName(String name) {
      Objects.requireNonNull(name, "A character should have a name.");
      if (name.isBlank()) {
        throw new IllegalArgumentException("A character name should not be blank.");
      }
    }

    private void validateAbilitiesOrder(Ability[] abilitiesOrder) {
      Objects.requireNonNull(abilitiesOrder, "The order between abilities have to be defined.");
      Set<Ability> abilitySet = Set.of(abilitiesOrder);
      if (abilitySet.size() != Ability.values().length || abilitiesOrder.length != Ability.values().length) {
        throw new IllegalArgumentException("The abilities order should mention each ability once and only once.");
      }
    }

    public Character build() {
      return new Character(this);
    }

    public Builder nonRamdomAbilities(Ability[] abilitiesOrder) {
      validateAbilitiesOrder(abilitiesOrder);
      AbilityScore[] abilityScores = new AbilityScore[Ability.values().length];
      Arrays.setAll(abilityScores, i -> new AbilityScore(predefinedScores[i]));
      Arrays.sort(abilityScores, Comparator.reverseOrder());
      abilities = IntStream
        .range(0, Ability.values().length)
        .boxed()
        .collect(
          Collectors.toMap(
            i -> abilitiesOrder[i],
            i -> abilityScores[i],
            (a1, a2) -> a1 /* never used */,
            () -> new EnumMap<>(Ability.class)));
      logger.debug("{} : {}", this.name, abilities);
      return this;
    }

    public Builder setAbility(Ability ability, byte score) {
      abilities.put(ability, new AbilityScore(score));
      logger.debug("{} : {}", ability, abilities.get(ability));
      return this;
    }

    public Builder setProficiencyBonus(byte proficiencyBonus) {
      this.proficiencyBonus = proficiencyBonus;
      return this;
    }
  }
}
