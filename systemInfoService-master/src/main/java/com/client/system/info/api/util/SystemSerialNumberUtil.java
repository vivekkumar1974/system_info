package com.client.system.info.api.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import com.client.system.info.api.controller.SystemInfoController;

/**
 * Program to get System Motherboard Serial Number for Windows and Linux Machine
 */
public class SystemSerialNumberUtil {

	static Logger logger = Logger.getLogger(SystemInfoController.class);

	/**
	 * Method for get System Motherboard Serial Number
	 * 
	 * @return Serial Number
	 */
	public static String GetSystemMotherBoard_SerialNumber() {
		try {
			String OSName = GetOSName();
			if (OSName.contains("Windows")) {
				return (GetWindowsMotherboard_SerialNumber());
			} else {
				return (GetLinuxMotherBoard_serialNumber());
			}
		} catch (Exception E) {
			logger.error("System MotherBoard Exp : " + E.getMessage(), E);
			return null;
		}
	}

	public static String GetOSName() {
		return System.getProperty("os.name");
	}

	/**
	 * Method for get Windows Machine MotherBoard Serial Number
	 * 
	 * @return
	 */
	private static String GetWindowsMotherboard_SerialNumber() {
		String result = "";
		try {
			File file = File.createTempFile("realhowto", ".vbs");
			file.deleteOnExit();
			FileWriter fw = new java.io.FileWriter(file);

			String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
					+ "Set colItems = objWMIService.ExecQuery _ \n" + "   (\"Select * from Win32_BaseBoard\") \n"
					+ "For Each objItem in colItems \n" + "    Wscript.Echo objItem.SerialNumber \n"
					+ "    exit for  ' do the first cpu only! \n" + "Next \n";

			fw.write(vbs);
			fw.close();

			Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			while ((line = input.readLine()) != null) {
				result += line;
			}
			input.close();
		} catch (Exception E) {
			logger.error("Windows MotherBoard Exp : " + E.getMessage(), E);
		}
		return result.trim();
	}

	/**
	 * Method for get Linux Machine MotherBoard Serial Number
	 * 
	 * @return
	 */
	private static String GetLinuxMotherBoard_serialNumber() {
		String command = "sudo dmidecode -s baseboard-serial-number";
		String sNum = null;
		try {
			Process SerNumProcess = Runtime.getRuntime().exec(command);
			BufferedReader sNumReader = new BufferedReader(new InputStreamReader(SerNumProcess.getInputStream()));
			sNum = sNumReader.readLine().trim();
			SerNumProcess.waitFor();
			sNumReader.close();
		} catch (Exception E) {
			logger.error("Linux Motherboard Exp : " + E.getMessage(), E);
			sNum = null;
		}
		return sNum;
	}

}