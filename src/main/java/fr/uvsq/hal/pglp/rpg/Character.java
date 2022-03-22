package fr.uvsq.hal.pglp.rpg;

import java.util.*;

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

  /** Bonus de maîtrise */
  private final byte proficiencyBonus;

  /**
   * Construit un personnage à partir d'un builder.
   *
   * @param builder le builder
   */
  Character(CharacterBuilder builder) {
    this.name = builder.name;
    this.abilities = builder.abilities;
    this.proficiencyBonus = builder.proficiencyBonus;
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
  public byte getProficiencyBonus() {
    return proficiencyBonus;
  }
}
