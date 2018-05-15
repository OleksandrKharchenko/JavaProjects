/**
 *
 *  @author Kharchenko Oleksandr S15638
 *
 */

package zad1;


public class Main {
  public static void main(String[] args) {
    Service s = new Service("Poland");
    String weatherJson = s.getWeather("Warsaw");
    Double rate1 = s.getRateFor("USD");
    Double rate2 = s.getNBPRate();
    
    GUInterface gui = new GUInterface(s);
  }
}
