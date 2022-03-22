package fr.uvsq.hal.pglp.rpg;

import static fr.uvsq.hal.pglp.rpg.Ability.*;

/**
 * La classe <code>Ability</code> représente une compétence d'un personnage.
 *
 * @author hal
 * @version 2022
 */
public enum Skill {
  Athletics(Strength),
  Acrobatics(Dexterity),
  SleightOfHand(Dexterity),
  Stealth(Dexterity),
  Arcana(Intelligence),
  History(Intelligence),
  Investigation(Intelligence),
  Nature(Intelligence),
  Religion(Intelligence),
  AnimalHandling(Wisdom),
  Insight(Wisdom),
  Medicine(Wisdom),
  Perception(Wisdom),
  Survival(Wisdom),
  Deception(Charisma),
  Intimidation(Charisma),
  Performance(Charisma),
  Persuasion(Charisma);

  private Ability relatedAbility;

  Skill(Ability ability) {
    relatedAbility = ability;
  }

  public Ability getRelatedAbility() {
    return relatedAbility;
  }
}
