package Objects;

import interfaces.Device;
import java.util.Scanner;

public class Music implements Device {
    private String[] playlist = {"Жить в кайф", "Birthday", "Bipl", "Ait","Disco"};
    private int currentSongIndex = 0;
    private boolean isOn = false;

    public void play() {
        if (!isOn) {
            System.out.println("Music system is off please turn it");
            return;
        }
        System.out.println("Playing song — " + playlist[currentSongIndex]);
    }

    public void nextSong() {
        if (!isOn) {
            System.out.println("Music system is off");
            turnOn();
        }
        currentSongIndex = (currentSongIndex + 1) % playlist.length;
        play();
    }

    public void prevSong() {
        if (!isOn) {
            System.out.println("Music system is off.");
            return;
        }
        currentSongIndex = (currentSongIndex - 1 + playlist.length) % playlist.length;
        play();
    }

    public void stop() {
        if (!isOn) {
            System.out.println("Music system is already off.");
            return;
        }
        System.out.println("Stopping " + playlist[currentSongIndex]);
    }

    public void chooseSong(String songName) {
        if (!isOn) {
            System.out.println("Music system is off.");
            return;
        }
        for (int i = 0; i < playlist.length; i++) {
            if (playlist[i].equalsIgnoreCase(songName)) {
                currentSongIndex = i;
                play();
                return;
            }
        }
        System.out.println("Song not found: " + songName);
    }

    public String[] getPlaylist() {
        return playlist;
    }

    @Override
    public void operation() {
        if (isOn) {
            play();
        } else {
            System.out.println("Music system is off.");
        }
    }

    @Override
    public void turnOn() {
        isOn = true;
        System.out.println("Music system now turned ON");
    }

    @Override
    public void turnOff() {
        isOn = false;
        System.out.println("Music system turned OFF");
    }

    @Override
    public boolean isOn() {
        return isOn;
    }

    @Override
    public String getName() {
        return "Music";
    }

    public String getCurrentSong() {
        return playlist[currentSongIndex];
    }

    public void startWithSong(Scanner scanner) {
        System.out.println("Available songs:");
        for (String song : playlist) {
            System.out.println(" - " + song);
        }
        System.out.print("Enter the song name to play: ");
        String song = scanner.nextLine();
        turnOn();
        chooseSong(song);
    }
}
