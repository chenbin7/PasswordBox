package com.cb.passwdbox.utils;

import java.security.MessageDigest;
import java.util.UUID;

public class Utils {

	/**
	 * byte[] ת HEX
	 * 
	 * @param buf
	 * @return
	 */
	public static String formatBytes2Hex(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * MD5����
	 * 
	 * @param str
	 * @return
	 */
	public static String getMD5(String str) {
		String result = null;
		if (str == null || str.equals(""))
			return null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] temp = md.digest(str.getBytes());

			result = formatBytes2Hex(temp);
		} catch (Exception ex) {
			ex.printStackTrace();
			result = null;
		}
		return result;
	}

	/**
	 * UUID
	 * 
	 * @return
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString();
	}

	/**
	 * �����ַ����������ַ�������
	 * 
	 * @param passwd
	 * @return
	 */
	public static long getVariance(String passwd) {
		if (passwd == null || passwd.equals(""))
			return 0;
		if (passwd.length() > 2048)
			return 0;
		byte[] temp = passwd.getBytes();
		long sum = 0;
		for (int i = 0; i < temp.length; i++) {
			sum += (int) (temp[i] & 0x00ff);
		}
		double average = (sum * 1.0) / temp.length;
		long inequality = 0;
		for (int i = 0; i < temp.length; i++) {
			double x = average - (int) (temp[i] & 0x00ff);
			inequality += x * x;
		}
		long variance = (long) Math.sqrt(inequality);
		return variance;
	}

	private static int[] Str2IntArray(String str) {
		if (str == null || str.equals(""))
			return null;
		byte[] temp = str.getBytes();
		int[] result = new int[temp.length];
		for (int i = 0; i < temp.length; i++) {
			result[i] = temp[i] & 0x00ff;
		}
		return result;
	}

	private static int compareIntArray(int[] src, int srcIndex,
			int[] needComapre, int needIndex) {
		int a = src.length - srcIndex;
		int b = needComapre.length - needIndex;
		if (a <= 0 || b <= 0)
			return 0;
		int compareLen = a < b ? a : b;
		int i = 0;
		for (i = 0; i < compareLen; i++) {
			if (src[srcIndex + i] != needComapre[needIndex + i])
				break;
		}
		return i;
	}

	/**
	 * ��ȡ�����ַ����У���ͬ���ַ�������󳤶�
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static int compareString(String str1, String str2) {
		if(str1 == null || str1.equals("") || str2 == null || str2.equals(""))
			return 0;
		int[] temp1, temp2;
		temp1 = Str2IntArray(str1);
		temp2 = Str2IntArray(str2);
		int max = 0;
		for (int i = 0; i < temp1.length - 3; i++) {
			int tempMax = 0;
			for (int j = 0; j < temp2.length - 3; j++) {
				if(temp1[i] == temp2[j] && temp1[i+1]==temp2[j+2]){
					int len = compareIntArray(temp1, i, temp2, j);
					tempMax = tempMax>len?tempMax:len;
				}
			}
			max = max>tempMax?max:tempMax;
		}
		return max;
	}
	
	/**
	 * һ���ַ�����һ���ַ����Ƚϣ����������� ��ͬ���ַ��� ����>=3 �ĸ������Լ����һ����ͬ���ַ����ĳ���
	 * @param str
	 * @param src
	 * @return int[]{������ͬ�ַ����ĸ��������ͬ���ַ�������}
	 */
	public static int[] compareStrSame(String str, String[] src){
		int sameSum = 0;
		int sameCount = 0;
		int temp = 0;
		for (int i = 0; i < src.length; i++) {
			temp = compareString(str, src[i]);
			if(temp>=3)
				sameSum++;
			sameCount = sameCount>temp?sameCount:temp;
		}
		if(sameCount < 3)
			sameCount = 0;
		return new int[]{sameSum,sameCount};
	}

}
