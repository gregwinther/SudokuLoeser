/**
* Main for sudokuloser
*
* @author Sebastian G. Winther-Larsen (sebastwi)
*
*/

class Main {

	public static void main(String[] args) {

		GUIThread GUI = new GUIThread();
		GUI.start();
	}

	public static class GUIThread extends Thread {
		
		public void run() {
			GUI.launch(GUI.class);
		}
	}
}

