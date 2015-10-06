/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.util;

import java.math.BigDecimal;
import java.security.AlgorithmParameters;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * Security Services.
 * <p>
 * Change log:
 * <ul>
 * <li>2007-01-27 - teo_sarca - [ 1598095 ] class Secure is not working with UTF8
 * </ul>
 *  
 *  @author     Jorg Janke
 *  @version    $Id: Secure.java,v 1.2 2006/07/30 00:52:23 jjanke Exp $
 */
public class Secure implements SecureInterface
{
	/**************************************************************************
	 *	Hash checksum number
	 *  @param key key
	 *  @return checksum number
	 */
	public static int hash (String key)
	{
		long tableSize = 2147483647;	// one less than max int
		long hashValue = 0;

		for (int i = 0; i < key.length(); i++)
			hashValue = (37 * hashValue) + (key.charAt(i) -31);

		hashValue %= tableSize;
		if (hashValue < 0)
			hashValue += tableSize;

		int retValue = (int)hashValue;
		return retValue;
	}	//	hash

	
	/**************************************************************************
	 *  Convert Byte Array to Hex String
	 *  @param bytes bytes
	 *  @return HexString
	 */
	public static String convertToHexString (byte[] bytes)
	{
		//	see also Util.toHex
		int size = bytes.length;
		StringBuffer buffer = new StringBuffer(size*2);
		for(int i=0; i<size; i++)
		{
			// convert byte to an int
			int x = bytes[i];
			// account for int being a signed type and byte being unsigned
			if (x < 0)
				x += 256;
			String tmp = Integer.toHexString(x);
			// pad out "1" to "01" etc.
			if (tmp.length() == 1)
				buffer.append("0");
			buffer.append(tmp);
		}
		return buffer.toString();
	}   //  convertToHexString


	/**
	 *  Convert Hex String to Byte Array
	 *  @param hexString hex string
	 *  @return byte array
	 */
	public static byte[] convertHexString (String hexString)
	{
		if (hexString == null || hexString.length() == 0)
			return null;
		int size = hexString.length()/2;
		byte[] retValue = new byte[size];
		String inString = hexString.toLowerCase();

		try
		{
			for (int i = 0; i < size; i++)
			{
				int index = i*2;
				int ii = Integer.parseInt(inString.substring(index, index+2), 16);
				retValue[i] = (byte)ii;
			}
			return retValue;
		}
		catch (Exception e)
		{
			log.finest(hexString + " - " + e.getLocalizedMessage());
		}
		return null;
	}   //  convertToHexString


	/**************************************************************************
	 * 	Adempiere Security
	 */
	public Secure()
	{
		initCipher();
	}	//	Secure

	/** Adempiere Cipher				*/
	private Cipher 			m_cipher = null;
	/** Adempiere Key				*/
	private SecretKey 		m_key = null;
	/** Message Digest				*/
	private MessageDigest	m_md = null;

	/**	Logger						*/
	private static Logger	log	= Logger.getLogger (Secure.class.getName());

	/**
	 * 	Initialize Cipher & Key
	 */
	private synchronized void initCipher()
	{
		if (m_cipher != null)
			return;
		Cipher cc = null;
		try
		{
			cc = Cipher.getInstance("DES/ECB/PKCS5Padding");
			//	Key
			if (false)
			{
				KeyGenerator keygen = KeyGenerator.getInstance("DES");
				m_key = keygen.generateKey();
				byte[] key = m_key.getEncoded();
				StringBuffer sb = new StringBuffer ("Key ")
					.append(m_key.getAlgorithm())
					.append("(").append(key.length).append(")= ");
				for (int i = 0; i < key.length; i++)
					sb.append(key[i]).append(",");
				log.info(sb.toString());
			}
			else
				m_key = new javax.crypto.spec.SecretKeySpec
					(new byte[] {100,25,28,-122,-26,94,-3,-26}, "DES");
		}
		catch (Exception ex)
		{
			log.log(Level.SEVERE, "", ex);
		}
		m_cipher = cc;
	}	//	initCipher

	
	
	/**
	 *	Encryption.
	 *  @param value clear value
	 *  @return encrypted String
	 */
	public String encrypt (String value)
	{
		String clearText = value;
		if (clearText == null)
			clearText = "";
		//	Init
		if (m_cipher == null)
			initCipher();
		//	Encrypt
		if (m_cipher != null)
		{
			try
			{
				m_cipher.init(Cipher.ENCRYPT_MODE, m_key);
				byte[] encBytes = m_cipher.doFinal(clearText.getBytes("UTF8"));
				String encString = convertToHexString(encBytes);
				// globalqss - [ 1577737 ] Security Breach - show database password
				// log.log (Level.ALL, value + " => " + encString);
				return encString;
			}
			catch (Exception ex)
			{
				// log.log(Level.INFO, value, ex);
				log.log(Level.INFO, "Problem encrypting string", ex);
			}
		}
		//	Fallback
		return CLEARVALUE_START + value + CLEARVALUE_END;
	}	//	encrypt

	/**
	 *	Decryption.
	 * 	The methods must recognize clear text values
	 *  @param value encrypted value
	 *  @return decrypted String
	 */
	public String decrypt (String value)
	{
		if (value == null || value.length() == 0)
			return value;
		boolean isEncrypted = value.startsWith(ENCRYPTEDVALUE_START) && value.endsWith(ENCRYPTEDVALUE_END);
		if (isEncrypted)
			value = value.substring(ENCRYPTEDVALUE_START.length(), value.length()-ENCRYPTEDVALUE_END.length());
		//	Needs to be hex String	
		byte[] data = convertHexString(value);
		if (data == null)	//	cannot decrypt
		{
			if (isEncrypted)
			{
				// log.info("Failed: " + value);
				log.info("Failed");
				return null;
			}
			//	assume not encrypted
			return value;
		}
		//	Init
		if (m_cipher == null)
			initCipher();

		//	Encrypt
		if (m_cipher != null && value != null && value.length() > 0)
		{
			try
			{
				AlgorithmParameters ap = m_cipher.getParameters();
				m_cipher.init(Cipher.DECRYPT_MODE, m_key, ap);
				byte[] out = m_cipher.doFinal(data);
				String retValue = new String(out, "UTF8");
				// globalqss - [ 1577737 ] Security Breach - show database password
				// log.log (Level.ALL, value + " => " + retValue);
				return retValue;
			}
			catch (Exception ex)
			{
				// log.info("Failed: " + value + " - " + ex.toString());
				log.info("Failed decrypting " + ex.toString());
			}
		}
		return null;
	}	//	decrypt

	/**
	 *	Encryption.
	 * 	The methods must recognize clear text values
	 *  @param value clear value
	 *  @return encrypted String
	 */
	public Integer encrypt (Integer value)
	{
		return value;
	}	//	encrypt

	/**
	 *	Decryption.
	 * 	The methods must recognize clear text values
	 *  @param value encrypted value
	 *  @return decrypted String
	 */
	public Integer decrypt (Integer value)
	{
		return value;
	}	//	decrypt
	
	/**
	 *	Encryption.
	 * 	The methods must recognize clear text values
	 *  @param value clear value
	 *  @return encrypted String
	 */
	public BigDecimal encrypt (BigDecimal value)
	{
		return value;
	}	//	encrypt

	/**
	 *	Decryption.
	 * 	The methods must recognize clear text values
	 *  @param value encrypted value
	 *  @return decrypted String
	 */
	public BigDecimal decrypt (BigDecimal value)
	{
		return value;
	}	//	decrypt

	/**
	 *	Encryption.
	 * 	The methods must recognize clear text values
	 *  @param value clear value
	 *  @return encrypted String
	 */
	public Timestamp encrypt (Timestamp value)
	{
		return value;
	}	//	encrypt

	/**
	 *	Decryption.
	 * 	The methods must recognize clear text values
	 *  @param value encrypted value
	 *  @return decrypted String
	 */
	public Timestamp decrypt (Timestamp value)
	{
		return value;
	}	//	decrypt
	
	
	/**
	 *  Convert String to Digest.
	 *  JavaScript version see - http://pajhome.org.uk/crypt/md5/index.html
	 *
	 *  @param value message
	 *  @return HexString of message (length = 32 characters)
	 */
	public String getDigest (String value)
	{
		if (m_md == null)
		{
			try
			{
				m_md = MessageDigest.getInstance("MD5");
			//	m_md = MessageDigest.getInstance("SHA-1");
			}
			catch (NoSuchAlgorithmException nsae)
			{
				nsae.printStackTrace();
			}
		}
		//	Reset MessageDigest object
		m_md.reset();
		//	Convert String to array of bytes
		byte[] input = value.getBytes();
		//	feed this array of bytes to the MessageDigest object
		m_md.update(input);
		//	 Get the resulting bytes after the encryption process
		byte[] output = m_md.digest();
		m_md.reset();
		//
		return convertToHexString(output);
	}	//	getDigest


	/**
	 * 	Checks, if value is a valid digest
	 *  @param value digest string
	 *  @return true if valid digest
	 */
	public boolean isDigest (String value)
	{
		if (value == null || value.length() != 32)
			return false;
		//	needs to be a hex string, so try to convert it
		return (convertHexString(value) != null);
	}	//	isDigest

	/**
	 * 	String Representation
	 *	@return info
	 */
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("Secure[");
		sb.append(m_cipher)
			.append ("]");
		return sb.toString ();
	}	//	toString
	
}   //  Secure
