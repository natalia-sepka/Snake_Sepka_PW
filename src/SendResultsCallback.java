/**interfejs obsługujący aktualne punkty i poziom gry*/
public interface SendResultsCallback {
    /**metoda do uaktualniania punktów i poziomu gry*/
    public void sendResults(int score, int level);
}
