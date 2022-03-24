package fr.uvsq.hal.pglp.rpg;

/**
 * L'énumération <code>DifficultyClass</code> représente le degré de difficulté d'un test.
 *
 * @author hal
 * @version 2022
 */
public enum DifficultyClass {
  VeryEasy(5), Easy(10), Medium(15),
  Hard(20), VeryHard(25), NearlyImpossible(30);

  /** Le degré de difficulté d'un test. */
  private final int difficultyClass;

  /**
   * Crée un degré de difficulté.
   *
   * @param difficultyClass le degré de difficulté
   */
  DifficultyClass(int difficultyClass) {
    this.difficultyClass = difficultyClass;
  }

  /**
   * Retourne le degré de difficulté.
   *
   * @return le degré de difficulté
   */
  public int getDifficultyClass() {
    return difficultyClass;
  }
}
