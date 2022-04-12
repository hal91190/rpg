package fr.uvsq.hal.pglp.rpg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static fr.uvsq.hal.pglp.rpg.Dice.d20;

/**
 * La classe <code>Character</code> représente un personnage de JdR.
 *
 * @author hal
 * @version 2022
 */
public class Character {
  /** Avantage ou désavantage pour les tests. */
  public enum Advantage { Advantage, None, Disadvantage }

  private final Logger logger = LoggerFactory.getLogger(Character.class);

  //TODO Race
  //TODO Class

  /** Nom. */
  private final String name;

  /** Caractéristiques. */
  private final Map<Ability, AbilityScore> abilities;

  /** Bonus de maîtrise. */
  private final int proficiencyBonus;

  /** Compétences. */
  private final Set<Skill> skills;

  /**
   * Construit un personnage à partir d'un builder.
   *
   * @param builder le builder
   */
  Character(CharacterBuilder builder) {
    this.name = builder.name;
    this.abilities = builder.abilities;
    this.proficiencyBonus = builder.proficiencyBonus;
    this.skills = builder.skills;
  }

  /**
   * Retourne le nom du personnage.
   *
   * @return le nom
   */
  public String getName() {
    return name;
  }

  /**
   * Retourne le score d'une caractéristique.
   *
   * @param ability la caractéristique
   *
   * @return le score
   */
  public AbilityScore get(Ability ability) {
    return abilities.get(ability);
  }

  /**
   * Retourne le bonus de maîtrise.
   *
   * @return le bonus de maîtrise
   */
  public int getProficiencyBonus() {
    return proficiencyBonus;
  }

  /**
   * Vérifie si le personnage maîtrise la compétence.
   *
   * @param skill la compétence
   * @return true si le personnage maîtrise la compétence, false sinon
   */
  public boolean isProficientIn(Skill skill) {
    return skills.contains(skill);
  }

  /**
   * Retourne le bonus de maîtrise spécifique à une compétence.
   *
   * @param skill la compétence
   * @return le bonus
   */
  public int getProficiencyBonusIn(Skill skill) {
    Ability ability = skill.getAbility();
    int bonus = get(ability).getModifier();
    return isProficientIn(skill) ? bonus + getProficiencyBonus() : bonus;
  }

  /**
   * Réalise un test de caractéristique.
   *
   * @param ability la caractéristique impliquée
   * @param difficultyClass le degré de difficulté du test
   * @return true si le test est réussi
   */
  public boolean checks(Ability ability, DifficultyClass difficultyClass) {
    return check(roll20WithAdvantage(Advantage.None), get(ability).getModifier(), difficultyClass);
  }

  /**
   * Réalise un test de caractéristique avec avantage/désavantage.
   *
   * @param ability la caractéristique impliquée
   * @param difficultyClass le degré de difficulté du test
   * @param advantage avantage ou désavantage
   * @return true si le test est réussi
   */
  public boolean checks(Ability ability, DifficultyClass difficultyClass, Advantage advantage) {
    return check(roll20WithAdvantage(advantage), get(ability).getModifier(), difficultyClass);
  }

  /**
   * Réalise un test de compétence.
   *
   * @param skill la compétence impliquée
   * @param difficultyClass le degré de difficulté du test
   * @return true si le test est réussi
   */
  public boolean checks(Skill skill, DifficultyClass difficultyClass) {
    return check(roll20WithAdvantage(Advantage.None), getProficiencyBonusIn(skill), difficultyClass);
  }

  /**
   * Réalise un test de compétence avec avantage/désavantage.
   *
   * @param skill la compétence impliquée
   * @param difficultyClass le degré de difficulté du test
   * @param advantage avantage ou désavantage
   * @return true si le test est réussi
   */
  public boolean checks(Skill skill, DifficultyClass difficultyClass, Advantage advantage) {
    return check(roll20WithAdvantage(advantage), getProficiencyBonusIn(skill), difficultyClass);
  }

  private boolean check(final int d20Rolled, int modifier, DifficultyClass difficultyClass) {
    logger.info("{} rolled = {}, modifier = {} , DC = {} ({})",
      d20, d20Rolled, modifier, difficultyClass, difficultyClass.getDifficultyClass());
    return d20Rolled + modifier >= difficultyClass.getDifficultyClass();
  }

  private int roll20WithAdvantage(final Advantage advantage) {
    return switch (advantage) {
      case Advantage -> new DiceGroup.Builder(2, d20).build().rollnMax();
      case None -> d20.roll();
      case Disadvantage -> new DiceGroup.Builder(2, d20).build().rollnMin();
    };
  }
}
