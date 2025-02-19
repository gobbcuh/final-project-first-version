package paul_version;

public class MainApplication {
    public static void main(String[] args) {
        // Create and show the loading screen
        LoadingScreen loadingScreen = new LoadingScreen();
        loadingScreen.showLoadingScreen();



        // Simulate loading process (e.g., loading resources)
        try {
            Thread.sleep(3000); // Simulate a loading delay of 3 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Hide the loading screen
        loadingScreen.hideLoadingScreen();

    }
}
