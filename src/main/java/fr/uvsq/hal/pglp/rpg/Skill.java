package fr.uvsq.hal.pglp.rpg;

import static fr.uvsq.hal.pglp.rpg.Ability.*;

/**
 * La classe <code>Ability</code> représente une compétence d'un personnage.
 *
 * @author hal
 * @version 2022
 */
public enum Skill {
  /** Athlétisme. */
  Athletics(Strength),
  /** Acrobaties. */
  Acrobatics(Dexterity),
  /** Escamotage. */
  SleightOfHand(Dexterity),
  /** Discrétion. */
  Stealth(Dexterity),
  /** Arcanes. */
  Arcana(Intelligence),
  /** Histoire. */
  History(Intelligence),
  /** Investigation. */
  Investigation(Intelligence),
  /** Nature. */
  Nature(Intelligence),
  /** Religion. */
  Religion(Intelligence),
  /** Dressage. */
  AnimalHandling(Wisdom),
  /** Intuition. */
  Insight(Wisdom),
  /** Médecine. */
  Medicine(Wisdom),
  /** Perception. */
  Perception(Wisdom),
  /** Survie. */
  Survival(Wisdom),
  /** Tromperie. */
  Deception(Charisma),
  /** Intimidation. */
  Intimidation(Charisma),
  /** Représentation. */
  Performance(Charisma),
  /** Persuasion. */
  Persuasion(Charisma);

  private Ability relatedAbility;

  Skill(Ability ability) {
    relatedAbility = ability;
  }

  public Ability getAbility() {
    return relatedAbility;
  }
}
