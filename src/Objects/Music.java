package Objects;
import interfaces.Device;

import java.util.Scanner;

public class Music implements Device {
    private String[] playlist = {"Жить в кайф", "Birthday", "Bipl", "Ait"};
    private int curentSongIndex = 0;

    public void play() {
        System.out.println("Playing song- " + playlist[curentSongIndex]);
    }

    public void nextSong() {
        curentSongIndex = (curentSongIndex + 1) % playlist.length;
        play();
    }

    public void prevSong() {
        curentSongIndex = (curentSongIndex - 1 + playlist.length) % playlist.length;
        play();
    }

    public void stop() {
        System.out.println("Stopping " + playlist[curentSongIndex]);
    }

    public void chooseSong(String songName) {
        for (int i = 0; i <playlist.length;i++){
            if (playlist[i].equalsIgnoreCase(songName)) {
                curentSongIndex = i;
                play();
                return;
            }
        }
        System.out.println("Song not found: " + songName);
    }

    public String[] getPlaylist(){
        return playlist;
    }
    @Override
    public void operation(){
        System.out.println("Music play");
    }
    public String getName(){
        return ("Music");
    }
    public String getCurrentSong(){
        return playlist[curentSongIndex];
    }
    public void startWithSong(Scanner scanner){
        System.out.println("Available songs: ");
        for (String song : playlist){
            System.out.println(" - "+ song);
        }
        System.out.println("Enter the song name to play ");
        String song =scanner.nextLine();
        chooseSong(song);
    }
}
