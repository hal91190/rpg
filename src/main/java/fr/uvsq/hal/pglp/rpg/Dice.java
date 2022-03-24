package fr.uvsq.hal.pglp.rpg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

/**
 * L'énumération' <code>Dice</code> représente un dé.
 *
 * @author hal
 * @version 2022
 */
public enum Dice {
  d4(4), d6(6), d8(8), d10(10), d12(12), d20(20);

  private static RandomGenerator generator;
  static {
    final RandomGeneratorFactory<RandomGenerator> factory = RandomGeneratorFactory.getDefault();
    generator = factory.create();
  }

  private final Logger logger = LoggerFactory.getLogger(Dice.class);

  private final int numberOfSides;

  Dice(int numberOfSides) {
    this.numberOfSides = numberOfSides;
  }

  /**
   * Lance le dé.
   *
   * @return la valeur du lancé
   */
  public int roll() {
    int value = generator.nextInt(numberOfSides) + 1;
    logger.debug("{} rolled : {}", this, value);
    return value;
  }

  /**
   * Fixe la graine du générateur aléatoire.
   *
   * @param seed la graine
   */
  public static void setSeed(long seed) {
    final RandomGeneratorFactory<RandomGenerator> factory = RandomGeneratorFactory.getDefault();
    generator = factory.create(seed);
  }

  /**
   * Fixe la graine du générateur aléatoire à partir du temps système.
   */
  public static void setSeed() {
    setSeed(System.nanoTime());
  }
}
