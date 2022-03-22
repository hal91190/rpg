package fr.uvsq.hal.pglp.rpg;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.random.RandomGenerator;

import static java.lang.Math.floorDiv;

/**
 * La classe <code>AbilityScore</code> représente la valeur d'une caractéristique.
 *
 * @author hal
 * @version 2022
 */
public class AbilityScore implements Comparable<AbilityScore> {
  /** Valeur minimale d'une caractéristique d'un personnage */
  public static final byte MIN_SCORE = 1;

  /** Valeur maximale d'une caractéristique d'un personnage */
  public static final byte MAX_SCORE = 20;

  private static final String MSG_SCORE_INVALID = "The score is invalid.";

  /** Valeur de la caractéristique */
  private byte score;

  /**
   * Génère une valeur de caractéristique de manière aléatoire.
   * Quatre nombres aléatoires entre 1 et 6 sont tirés.
   * La valeur est la somme des trois meilleurs tirages.
   */
  public AbilityScore() {
    RandomGenerator generator = RandomGenerator.getDefault();
    List<Integer> values = generator.ints(1, 7).limit(4).boxed().toList();
    int minValues = values.stream().mapToInt(Integer::intValue).min().orElseThrow(NoSuchElementException::new);
    score = (byte)(values.stream().mapToInt(Integer::intValue).sum() - minValues);
  }

  /**
   * Fixe la valeur d'une caractéristique.
   *
   * @param score la valeur
   */
  public AbilityScore(byte score) {
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
  public byte getScore() {
    return score;
  }

  /**
   * Retourne le modificateur correspondant.
   *
   * @return le modificateur
   */
  public byte getModifier() {
    return (byte)floorDiv(score - 10, 2);
  }

  @Override
  public String toString() {
    return String.format("%d (%d)", score, getModifier());
  }

  @Override
  public int compareTo(AbilityScore abilityScore) {
    return Byte.compare(score, abilityScore.score);
  }
}
