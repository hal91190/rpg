package fr.uvsq.hal.pglp.rpg;

import java.util.EnumMap;
import java.util.Map;

/**
 * La classe <code>DiceGroup</code> représente un groupe de dé.
 *
 * @author hal
 * @version 2022
 */
public class DiceGroup {
  private final Map<Dice, Integer> diceGroup;

  private DiceGroup(Builder builder) {
    diceGroup = builder.diceGroup;
  }

  /**
   * Lance les dés.
   *
   * @return les valeurs obtenues dans l'ordre croissant du nombre de faces (d4, d6, ...)
   */
  public int[] roll() {
    int totalNumberOfDices = diceGroup.values().stream().mapToInt(Integer::intValue).sum();
    int[] diceValues = new int[totalNumberOfDices];
    int index = 0;
    for (Map.Entry<Dice, Integer> entry : diceGroup.entrySet()) {
      int numberOfDices = entry.getValue();
      for (int i = 0; i < numberOfDices; i++) {
        diceValues[index + i] = entry.getKey().roll();
      }
      index += numberOfDices;
    }
    return diceValues;
  }

  /**
   * Builder pour la classe DiceGroupe.
   */
  public static class Builder {
    private final Map<Dice, Integer> diceGroup;

    /**
     * Crée un groupe avec un certain nombre de dés du type choisi.
     *
     * @param numberOfDices le nombre de dés
     * @param dice le type de dé
     */
    public Builder(int numberOfDices, Dice dice) {
      diceGroup = new EnumMap<>(Dice.class);
      diceGroup.put(dice, numberOfDices);
    }

    /**
     * Ajoute dans le groupe un certain nombre de dés du type choisi.
     *
     * @param numberOfDices le nombre de dé
     * @param dice le type de dé
     * @return le builder
     */
    public Builder add(int numberOfDices, Dice dice) {
      diceGroup.put(dice, numberOfDices);
      return this;
    }

    /**
     * Construit le groupe de dés.
     *
     * @return le groupe
     */
    public DiceGroup build() {
      return new DiceGroup(this);
    }
  }
}
