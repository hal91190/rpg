package fr.uvsq.hal.pglp.rpg;

import java.util.Arrays;
import java.util.NoSuchElementException;

import static fr.uvsq.hal.pglp.rpg.Dice.d6;
import static java.lang.Math.floorDiv;

/**
 * La classe <code>AbilityScore</code> représente la valeur d'une caractéristique.
 *
 * @author hal
 * @version 2022
 */
public class AbilityScore implements Comparable<AbilityScore> {
  /** Valeur minimale d'une caractéristique d'un personnage. */
  public static final int MIN_SCORE = 1;

  /** Valeur maximale d'une caractéristique d'un personnage. */
  public static final int MAX_SCORE = 20;

  private static final String MSG_SCORE_INVALID = "The score is invalid.";

  /** Valeur de la caractéristique. */
  private int score;

  /**
   * Génère une valeur de caractéristique de manière aléatoire.
   * Quatre nombres aléatoires entre 1 et 6 sont tirés.
   * La valeur est la somme des trois meilleurs tirages.
   */
  public AbilityScore() {
    DiceGroup fourD6 = new DiceGroup.Builder(4, d6).build();
    int[] values = fourD6.roll();
    int minValues = Arrays.stream(values).min().orElseThrow(NoSuchElementException::new);
    score = Arrays.stream(values).sum() - minValues;
  }

  /**
   * Fixe la valeur d'une caractéristique.
   *
   * @param score la valeur
   */
  public AbilityScore(int score) {
    this.score = score;
    if (!isValid()) {
      throw new IllegalArgumentException(MSG_SCORE_INVALID);
    }
  }

  /**
   * Vérifie la validité de la valeur.
   *
   * @return true si la valeur est acceptable pour un personnage, false sinon
   */
  public boolean isValid() {
    return score >= MIN_SCORE && score <= MAX_SCORE;
  }

  /**
   * Retourne la valeur de caractéristiques.
   *
   * @return la valeur
   */
  public int getScore() {
    return score;
  }

  /**
   * Retourne le modificateur correspondant.
   *
   * @return le modificateur
   */
  public int getModifier() {
    return floorDiv(score - 10, 2);
  }

  @Override
  public String toString() {
    return String.format("%d (%d)", score, getModifier());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    AbilityScore that = (AbilityScore) o;

    return score == that.score;
  }

  @Override
  public int hashCode() {
    return score;
  }

  @Override
  public int compareTo(AbilityScore abilityScore) {
    return Integer.compare(score, abilityScore.score);
  }
}
