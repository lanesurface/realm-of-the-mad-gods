package main.game.sfx;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class AudioPlayer extends Thread	{
	
	private Clip clip;
	
	public AudioPlayer(String audioPath)	{
		try    {
			AudioInputStream ais = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(audioPath));
			AudioFormat baseFormat = ais.getFormat();
			AudioFormat decodeFormat = new AudioFormat(
				AudioFormat.Encoding.PCM_SIGNED,
				baseFormat.getSampleRate(),
				16,
				baseFormat.getChannels(),
				baseFormat.getChannels() * 2,
				baseFormat.getSampleRate(),
				false
			);
			AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat,ais);
			clip = AudioSystem.getClip();
			clip.open(dais);
		}
		catch(Exception e)	{
			e.printStackTrace();
		}
	}
	public void run() {
		if (clip == null) return;
		stopAudio();
		clip.setFramePosition(0);
		clip.start();
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	public void stopAudio()	{
		if (clip.isRunning()) clip.stop(); 
	}
	public void close()	{
		stopAudio();
		clip.close();
	}
}
