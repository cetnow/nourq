package com.cetnow.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;

public class MP3Util {
	private File mp3File;
	
	public MP3Util(String mp3File){
		this.mp3File = new File(mp3File);
	}
	
	public static boolean generateFile(File file, List<byte[]> datas){
		BufferedOutputStream bos = null;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(file));
			for(byte[] data:datas){
				bos.write(data);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(bos != null){
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	
	public byte[] getDataByBitRate(int beginTime, int endTime){
		byte[] result = null;
		RandomAccessFile rMp3File = null;
		try{
			MP3File mp3 = new MP3File(mp3File);
			MP3AudioHeader header = (MP3AudioHeader) mp3.getAudioHeader();
			if(header.isVariableBitRate()){
				result = null;
			} else {
				long mp3StartIndex = header.getMp3StartByte();	
				int trackLengthMs = header.getTrackLength()*1000;
				long bitRate = header.getBitRateAsNumber();
				long beginIndex = bitRate*1024/8/1000*beginTime+mp3StartIndex;
				long endIndex = beginIndex + bitRate*1024/8/1000*(endTime-beginTime);
				if(endTime > trackLengthMs){
					endIndex = mp3File.length()-1;
				}
				
				rMp3File = new RandomAccessFile (mp3File,"r");
				rMp3File.seek(beginIndex);
				int size = (int)(endIndex - beginIndex);
				result = new byte[size];
				rMp3File.read(result);
			}
		}catch(Exception e){
			e.printStackTrace();
		} finally {
			if(rMp3File!=null){
				try {
					rMp3File.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}	
	
	public void generateNewMp3ByTime(File newMp3, int beginTime, int endTime){
		byte[] frames = getDataByTime(beginTime,endTime);
		if(frames == null || frames.length < 1){
			return;
		}
		List<byte[]> mp3datas = new ArrayList<byte[]>();
		mp3datas.add(frames);
		generateFile(newMp3, mp3datas);
	}
	
	public byte[] getDataByTime(int beginTime, int endTime){
		return getDataByBitRate(beginTime,endTime);
	}

}