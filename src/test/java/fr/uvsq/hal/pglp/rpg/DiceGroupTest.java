package fr.uvsq.hal.pglp.rpg;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static fr.uvsq.hal.pglp.rpg.Dice.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DiceGroupTest {
  @BeforeEach
  public void setup() {
    Dice.setSeed(1L);
  }

  @Test
  public void aDiceGroupShouldReturnCorrectValues() {
    DiceGroup twoD20 = new DiceGroup.Builder(2, d20).build();
    int[] values = twoD20.roll();
    assertEquals(13, values[0]);
    assertEquals(9, values[1]);
  }

  @Test
  public void aMoreComplicatedDiceGroupShouldReturnCorrectValues() {
    DiceGroup diceGroup = new DiceGroup.Builder(2, d20)
      .add(3, d4)
      .add(4, d6)
      .add(1, d12)
      .build();
    int[] values = diceGroup.roll();
    assertEquals(10, values.length);
    assertEquals(2, values[0]);
    assertEquals(1, values[1]);
    assertEquals(2, values[2]);
    assertEquals(1, values[3]);
    assertEquals(5, values[4]);
    assertEquals(4, values[5]);
    assertEquals(3, values[6]);
    assertEquals(11, values[7]);
    assertEquals(4, values[8]);
    assertEquals(17, values[9]);
  }
}