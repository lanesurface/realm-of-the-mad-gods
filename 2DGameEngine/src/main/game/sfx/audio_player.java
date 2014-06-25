package main.game.sfx;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class audio_player extends Thread	{
	
	private Clip clip;
	
	public audio_player(String audio_path)	{
		try    {
			AudioInputStream ais = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(audio_path));
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
	public void playAudio() {
		if (clip == null) return;
		stopAudio();
		clip.setFramePosition(0);
		clip.start();
	}
	public void stopAudio()	{
		if (clip.isRunning()) clip.stop(); 
	}
	public void close()	{
		stopAudio();
		clip.close();
	}
}
