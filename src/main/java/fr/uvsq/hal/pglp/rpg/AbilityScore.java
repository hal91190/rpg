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
  public static final byte MIN_SCORE = 1;
  public static final byte MAX_SCORE = 20;

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

  @Override
  public String toString() {
    return Byte.toString(score);
  }

  public AbilityScore(byte score) {
    this.score = score;
  }

  public boolean isValid() {
    return score >= MIN_SCORE && score <= MAX_SCORE;
  }

  public byte getScore() {
    return score;
  }

  public byte getModifier() {
    return (byte)floorDiv(score - 10, 2);
  }

  @Override
  public int compareTo(AbilityScore abilityScore) {
    return Byte.compare(score, abilityScore.score);
  }
}
