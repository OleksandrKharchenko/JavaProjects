/**
 *
 *  @author Kharchenko Oleksandr S15638
 *
 */

package zad1;


public class Main {

  public static void main(String[] args) {
	  new Thread(new Server()).start();
	  new Thread(new Client()).start();
	  new Thread(new Client()).start();
  }
}
