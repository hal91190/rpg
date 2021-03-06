package fr.uvsq.hal.pglp.rpg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * La classe <code>CharacterBuilder</code> permet de créer un personnage.
 *
 * @author hal
 * @version 2022
 */
public class CharacterBuilder {
  /** Somme minimum des scores de caractéristiques pour la génération aléatoire. */
  public static final int MIN_SUM_SCORE = 60;

  /** Somme maximum des scores de caractéristiques pour la génération aléatoire. */
  public static final int MAX_SUM_SCORE = 80;

  /** Scores prédéfinis de caractéristiques pour la création non aléatoire. */
  private static final int[] PREDEFINED_SCORES = { 15, 14, 13, 12, 10, 8 };

  /** Bonus de maîtrise au niveau 1. */
  public static final int FIRST_LEVEL_PROFICIENCY_BONUS = 2;

  private static final String MSG_NAME_MANDATORY = "A character should have a name.";
  private static final String MSG_NAME_NOT_BLANK = "A character name should not be blank.";
  private static final String MSG_ORDER_MANDATORY = "The order between abilities have to be defined.";
  private static final String MSG_ORDER_VALID = "The abilities order should mention each ability once and only once.";
  private static final String MSG_SKILL_MANDATORY = "The skill is mandatory.";

  private final Logger logger = LoggerFactory.getLogger(CharacterBuilder.class);

  final String name;
  Map<Ability, AbilityScore> abilities;
  int proficiencyBonus;
  Set<Skill> skills;

  /**
   * Crée un personnage en générant les caractéristiques de manière aléatoire.
   * Les scores générés sont attribués en ordre décroissant dans les caractéristiques
   * (Force, Dextérité, Constitution, Intelligence, Sagesse, Charisme).
   *
   * @param name le nom du personnage
   */
  public CharacterBuilder(String name) {
    this(name, Ability.values());
  }

  /**
   * Crée un personnage en générant les caractéristiques de manière aléatoire.
   * Les scores générés sont attribués en ordre décroissant selon l'ordre
   * des caractéristiques fourni en paramètre.
   *
   * @param name le nom du personnage
   * @param abilitiesOrder l'ordre de préférence des caractéristiques
   *                       (chaque caractéristique doit être mentionnée une et une seule fois)
   */
  public CharacterBuilder(String name, Ability[] abilitiesOrder) {
    validateName(name);
    this.name = name;

    validateAbilitiesOrder(abilitiesOrder);
    int abilitySum;
    AbilityScore[] abilityScores = new AbilityScore[Ability.values().length];
    do {
      Arrays.setAll(abilityScores, i -> new AbilityScore());
      abilitySum = Arrays.stream(abilityScores).mapToInt(AbilityScore::getScore).sum();
    } while (abilitySum < MIN_SUM_SCORE || abilitySum > MAX_SUM_SCORE);
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

    this.proficiencyBonus = FIRST_LEVEL_PROFICIENCY_BONUS;
    this.skills = EnumSet.noneOf(Skill.class);
    logger.debug("{} : {}, {}", this.name, abilities, proficiencyBonus);
  }

  private void validateName(String name) {
    Objects.requireNonNull(name, MSG_NAME_MANDATORY);
    if (name.isBlank()) {
      throw new IllegalArgumentException(MSG_NAME_NOT_BLANK);
    }
  }

  private void validateAbilitiesOrder(Ability[] abilitiesOrder) {
    Objects.requireNonNull(abilitiesOrder, MSG_ORDER_MANDATORY);
    Set<Ability> abilitySet = Set.of(abilitiesOrder);
    if (abilitySet.size() != Ability.values().length || abilitiesOrder.length != Ability.values().length) {
      throw new IllegalArgumentException(MSG_ORDER_VALID);
    }
  }

  /**
   * Crée le personnage.
   *
   * @return le personnage
   */
  public Character build() {
    return new Character(this);
  }

  /**
   * Affecte les scores prédéfinis aux caractéristiques dans l'ordre précisé.
   *
   * @param abilitiesOrder l'ordre de préférence des caractéristiques
   *                       (chaque caractéristique doit être mentionnée une et une seule fois)
   *
   * @return le builder
   */
  public CharacterBuilder nonRamdomAbilities(Ability[] abilitiesOrder) {
    validateAbilitiesOrder(abilitiesOrder);
    AbilityScore[] abilityScores = new AbilityScore[Ability.values().length];
    Arrays.setAll(abilityScores, i -> new AbilityScore(PREDEFINED_SCORES[i]));
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

  /**
   * Fixe le score d'une caractéristique.
   *
   * @param ability la caractéristique concernée
   * @param score le score à affecter
   *
   * @return le builder
   */
  public CharacterBuilder setAbility(Ability ability, int score) {
    abilities.put(ability, new AbilityScore(score));
    logger.debug("{} : {}", ability, abilities.get(ability));
    return this;
  }

  /**
   * Fixe le bonus de maîtrise du personnage.
   *
   * @param proficiencyBonus le bonus de maîtrise
   *
   * @return le builder
   */
  public CharacterBuilder setProficiencyBonus(int proficiencyBonus) {
    this.proficiencyBonus = proficiencyBonus;
    return this;
  }

  /**
   * Indique les compétences que le personnage maîtrise.
   *
   * @param skills les compétences
   * @return le builder
   */
  public CharacterBuilder isProficientIn(Skill... skills) {
    Objects.requireNonNull(skills, MSG_SKILL_MANDATORY);
    this.skills = EnumSet.copyOf(Arrays.asList(skills));
    return this;
  }
}
