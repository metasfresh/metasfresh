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
package org.compiere.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.util.DB;

/**
 * 	Text Index Model
 *	
 *  @author Yves Sandfort
 *  @version $Id: MIndex.java,v 1.6 2006/08/09 16:38:25 jjanke Exp $
 */
public class MIndex extends X_K_Index
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7835438766016462027L;

	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param K_Index_ID index
	 *	@param trxName transaction
	 */
	public MIndex (Properties ctx, int K_Index_ID, String trxName)
	{
		super (ctx, K_Index_ID, trxName);
	}	//	MIndex

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MIndex (Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
	}	//	MIndex
	
	/**
	 * 	cleanUp
	 *	@param trxName
	 *	@param AD_Client_ID
	 *	@param AD_Table_ID
	 *	@param Record_ID
	 *	@return Number of records cleaned
	 */
	public static int cleanUp (String trxName, int AD_Client_ID, int AD_Table_ID, int Record_ID)
	{
		StringBuffer sb = new StringBuffer ("DELETE FROM K_Index "
			+ "WHERE AD_Client_ID=" + AD_Client_ID + " AND "
			+ "AD_Table_ID=" + AD_Table_ID + " AND "
			+ "Record_ID=" + Record_ID);
		int no = DB.executeUpdateAndSaveErrorOnFail(sb.toString(), trxName);
		return no;
	}
	
	/**
	 * 	Set Keyword & standardize
	 *	@param Keyword
	 */
	@Override
	public void setKeyword (String Keyword)
	{
		String kw = standardizeKeyword(Keyword);
		if (kw == null)
			kw = "?";
		super.setKeyword (kw);
	}	//	setKeyword
	
	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true
	 */
	@Override
	protected boolean beforeSave (boolean newRecord)
	{
		if (newRecord || is_ValueChanged("Keyword"))
			setKeyword(getKeyword());
		if (getKeyword().equals("?"))
		{
			throw new FillMandatoryException("Keyword");
		}
		return true;
	}	//	beforeSave
	
	/**
	 *  Index/Keyword a StringBuffer
	 * @param thisText The String to convert into Hash
	 * @return Hashtable with String
	 *  
	 */
	public static Hashtable indexStringBuffer(StringBuffer thisText)
	{
		return indexString(thisText.toString());
	}
	
	/**
	 *  Index/Keyword a String
	 * @param thisText The String to convert into Hash
	 * @return Hashtable with String
	 *  
	 */
	public static Hashtable<String,String> indexString(String thisText)
	{
 		thisText = removeHTML(thisText);
		if (thisText!=null) {
			String [] keywords = thisText.toUpperCase().split("\\t|\\p{Punct}|\\p{Space}");
			Hashtable<String,String> keyhash = new Hashtable<String,String>(keywords.length);
			int currentPos = 0;
			for (int i=0;i<keywords.length;i++)
			{	
				String thisExcerpt = "";
				if (keywords[i].length()>=2) 
				{
					if (!keyhash.contains(keywords[i]))
					{
						int startExcerpt = thisText.toUpperCase().indexOf(keywords[i],currentPos);
						if (startExcerpt>50)
							startExcerpt = startExcerpt - 50;
						if (startExcerpt>thisText.length () && startExcerpt>50 && thisText.length()>50) 
							startExcerpt = thisText.length()-50;
						int endExcerpt = thisText.toUpperCase().indexOf(keywords[i],currentPos) + keywords[i].length();
						if (endExcerpt>currentPos)
							currentPos = endExcerpt;
						if (endExcerpt<thisText.length()-50)
							endExcerpt = endExcerpt + 50;
						if (endExcerpt>thisText.length()) 
							endExcerpt = thisText.length ();
						thisExcerpt = thisText.substring(startExcerpt,endExcerpt);
						keyhash.put(keywords[i],thisExcerpt);
					}
				}
			}
			return keyhash;
		} else {
			return null;
		}
			
	}
	
	/**
	 * 	runIndex
	 *	@param thisText The text to be indexed
	 *	@param ctx context
	 *	@param trxName Transaction if needed
	 *	@param tableID Table ID
	 *	@param recordID Record ID
	 *	@param CMWebProjectID Web Project ID
	 *	@param sourceUpdated Update Date of Source
	 *	@return true if successfully indexed
	 */
	public static boolean runIndex(String thisText, Properties ctx, String trxName, 
		int tableID, int recordID, int CMWebProjectID, Timestamp sourceUpdated) 
	{
		if (thisText!=null) {
			Hashtable keyHash = indexString(thisText);
	        for (Enumeration e=keyHash.keys(); e.hasMoreElements();) {
	            String name = (String)e.nextElement();
	            String value = (String)keyHash.get(name);
	            MIndex thisIndex = new MIndex(ctx, 0, trxName);
	            thisIndex.setAD_Table_ID(tableID);
	            if (CMWebProjectID>0)
	                thisIndex.setCM_WebProject_ID(CMWebProjectID);
	            thisIndex.setExcerpt(value);
	            thisIndex.setKeyword(name);
	            thisIndex.setRecord_ID(recordID);
	            thisIndex.setSourceUpdated(sourceUpdated);
	            thisIndex.save();
	        }
	        return true;
		} else {
			return false;
		}
	}
	
	
	/**
	 * 	reIndex Document
	 *	@param runCleanUp clean old records
	 *	@param toBeIndexed Array of Strings to be indexed
	 *	@param ctx Context
	 *	@param AD_Client_ID Client
	 *	@param AD_Table_ID Table
	 *	@param Record_ID Record
	 *	@param CM_WebProject_ID Web Project
	 *	@param lastUpdated Date of last update
	 */
	public static void reIndex(boolean runCleanUp, String[] toBeIndexed, Properties ctx, 
		int AD_Client_ID, int AD_Table_ID, int Record_ID, int CM_WebProject_ID, Timestamp lastUpdated) 
	{
		String trxName = "ReIndex_" + AD_Table_ID + "_" + Record_ID;
		try {
			if (!runCleanUp)
			{
				MIndex.cleanUp(trxName, AD_Client_ID, AD_Table_ID, Record_ID);
			}
			for (int i=0;i<toBeIndexed.length;i++) {
				MIndex.runIndex(toBeIndexed[i], ctx, trxName, AD_Table_ID, Record_ID, 
					CM_WebProject_ID, lastUpdated);
			}
			DB.commit (true, trxName);
		} catch (SQLException sqlE) {
			try {
				DB.rollback (true, trxName);
			} catch (SQLException sqlE2) {
			}
		}
	}
	
	/**
	 * 	remove HTML Tags from content...
	 *	@param textElement
	 *	@return cleanText
	 */
	protected static String removeHTML(String textElement) {
		String retValue = textElement.replaceAll ("<?\\w+((\\s+\\w+(\\s*=\\s*(?:\"b(.|\\n)*?\"|'(.|\\n)*?'|[^'\">\\s]+))?)+\\s*|\\s*)/?>", " ");
		retValue = retValue.replaceAll ("</", " ");
		retValue = retValue.replaceAll ("\\\\n", " ");
		return retValue.toString();
	}
	
	
	/**
	 * 	Clean up & standardize Keyword
	 *	@param keyword keyword
	 *	@return keyword or null
	 */
	protected static String standardizeKeyword (String keyword)
	{
		if (keyword == null)
			return null;
		keyword = keyword.trim();
		if (keyword.length() == 0)
			return null;
		//
		keyword = keyword.toUpperCase();	//	default locale
		StringBuffer sb = new StringBuffer();
		char[] chars = keyword.toCharArray();
		for (int i = 0; i < chars.length; i++)
		{
			char c = chars[i];
			sb.append(standardizeCharacter(c));
		}
		return sb.toString();
	}	//	standardizeKeyword
	
	/**
	 * 	Standardize Character
	 *	@param c character
	 *	@return string
	 */
	private static String standardizeCharacter (char c)
	{
		if (c >= '!' && c <= 'Z')		//	includes Digits
			return String.valueOf(c);
		//
		int index = Arrays.binarySearch(s_char, c);
		if (index < 0)
			return String.valueOf(c);
		return s_string[index];
	}	//	standardizeKeyword

	/**
	 * 	Foreign upper case characters ascending for binary search.
	 * 	Make sure that you use native2ascii to convert
	 * 	(note Eclipse, etc. actually save the content as utf-8 - so the command is
	 * 	native2ascii -encoding utf-8 filename)
	 */
	private static final char[] s_char 
		= new char[] {
			'\u00c4', '\u00d6', '\u00dc', '\u00df'	//	ÄÖÜß - German stuff
		};
	
	/**
	 * 	Foreign character string linked to s_char position
	 */
	private static final String[] s_string 
		= new String[] {
			"AE", "OE", "UE", "SS"
		};
	
}	//	MIndex
