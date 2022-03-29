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
  private final Logger logger = LoggerFactory.getLogger(Character.class);

  //TODO Race
  //TODO Class

  /** Nom. */
  private final String name;

  /** Caractéristiques. */
  private final Map<Ability, AbilityScore> abilities;

  /** Bonus de maîtrise. */
  private final int proficiencyBonus;

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
   * Réalise un test de caractéristique.
   *
   * @param ability la caractéristique impliquée
   * @param difficultyClass le degré de difficulté du test
   * @return true si le test est réussi
   */
  public boolean abilityCheck(Ability ability, DifficultyClass difficultyClass) {
    int d20Rolled = d20.roll();
    logger.info("{} rolled = {}, {} = {}, DC = {} ({})", d20, d20Rolled, ability, get(ability), difficultyClass, difficultyClass.getDifficultyClass());
    return d20Rolled + get(ability).getModifier() >= difficultyClass.getDifficultyClass();
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
   * Réalise un test de compétence.
   *
   * @param skill la compétence impliquée
   * @param difficultyClass le degré de difficulté du test
   * @return true si le test est réussi
   */
  public boolean skillCheck(Skill skill, DifficultyClass difficultyClass) {
    int d20Rolled = d20.roll();
    logger.info("{} rolled = {}, {} ({}), DC = {} ({})", d20, d20Rolled, skill, getProficiencyBonusIn(skill), difficultyClass, difficultyClass.getDifficultyClass());
    return d20Rolled + getProficiencyBonusIn(skill) >= difficultyClass.getDifficultyClass();
  }
}
