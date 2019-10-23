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
package org.compiere.cm;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * @author Yves Sandfort
 * @version $Revision$
 */

/**
 * StringUtil is a helper class to run different String managements not implemented in Java Core
 * 
 * @author Yves Sandfort
 * @version $id$
 *
 */
public class StringUtil {
	/**
	 * Replace all occurences of search with replace in original
	 * @param original the original String
	 * @param search the string to look for
	 * @param replace the string we will replace search with
	 * @return StringBuffer with result
	 */
	public static StringBuffer replace(StringBuffer original, String search, String replace) {
		return doReplace (original, search, replace, false, true);
	}
	
	/**
	 * Replace all occurences of search with replace in original
	 * @param original the original String
	 * @param search the string to look for
	 * @param replace the string we will replace search with
	 * @return String with result, null will get replaced by ""
	 */
	public static String replace (String original, String search, String replace) {
		if (original==null) original = "";
		return doReplace (new StringBuffer(original), search, replace, false, true).toString();
	}
	
	/**
	 * Replace all occurences of search with replace in original
	 * @param original the original String
	 * @param search the string to look for
	 * @param replace the string we will replace search with
	 * @return String with result, null will get replaced by ""
	 */
	public static String replace (String original, char search, String replace) {
		if (original==null) original = "";
		return doReplace (new StringBuffer(original), search, replace, true).toString();
	}
	
    /**
     * Replace all or one occurence of search with replace in original
     * @param original the original StringBuffer
     * @param search String to get replaced
     * @param replace String to replace
     * @param ignoreCase should we ignore cases
     * @param allOccurences should all Occurences get replaced
     * @return StringBuffer with result
     */
    public static StringBuffer replace(StringBuffer original, String search, String replace, boolean ignoreCase, boolean allOccurences) {
		return doReplace (original, search, replace, ignoreCase, allOccurences);
    }

    /**
     * Replace all or one occurence of search with replace in original
     * @param original the original StringBuffer
     * @param search String to get replaced
     * @param replace String to replace
     * @param ignoreCase should we ignore cases
     * @param allOccurences should all Occurences get replaced
     * @return StringBuffer with result
     */
    public static String replace(String original, String search, String replace, boolean ignoreCase, boolean allOccurences) {
		if (original==null) original="";
		return replace(new StringBuffer(original), search, replace, ignoreCase, allOccurences).toString();
    }

    /**
     * Replace one occurence of search with replace in original
     * @param original StringBuffer
     * @param search String to look for
     * @param replace String to replace search with
     * @return new StringBuffer with result
     */
    public static StringBuffer replaceOne(StringBuffer original, String search, String replace) {
        if (original.toString().indexOf(search)>=0) {
            original.replace(original.toString().indexOf(search),original.toString().indexOf(search)+search.length(),replace);
        }
        return original;
    }

    /**
     * Replace one occurence of search with replace in original
     * @param original String to look in
     * @param search String to search for
     * @param replace String to replace search with
     * @return new String with result
     */
    public static String replaceOne(String original, String search, String replace) {
        StringBuffer toriginal = new StringBuffer(original);
        if (toriginal.toString().indexOf(search)>=0) {
            toriginal.replace(toriginal.toString().indexOf(search),toriginal.toString().indexOf(search)+search.length(),replace);
        }
        return toriginal.toString();
    }

    /**
     * Run RegEx Expression against original String 
     * @param original StringBuffer with original context
     * @param regex Regular Expression to run as query
     * @param replace Replace String
     * @param CASE_INSENSITIVE whether we should take care of case or not 
     * @return StringBuffer with result
     */
    public static StringBuffer replaceRegex(StringBuffer original, String regex, String replace, boolean CASE_INSENSITIVE) {
		int flags=0;
		if (CASE_INSENSITIVE) flags=Pattern.CASE_INSENSITIVE;
		Pattern p = Pattern.compile(regex, flags);
		Matcher m = p.matcher(original);
		StringBuffer newSB = new StringBuffer();
		boolean result = m.find();
		while(result) {
			m.appendReplacement(newSB, replace);
			result = m.find();
		}
		m.appendTail(newSB);
		return newSB;
	}

    /**
     * Run RegEx Expression against original String 
     * @param original StringBuffer with original context
     * @param regex Regular Expression to run as query
     * @param replace Replace String
     * @param CASE_INSENSITIVE whether we should take care of case or not 
     * @return String with result
     */
	public static String replaceRegex(String original, String regex, String replace, boolean CASE_INSENSITIVE) {
		return replaceRegex(new StringBuffer(original), regex, replace, CASE_INSENSITIVE).toString();
	}

    private static StringBuffer doReplace(StringBuffer original, String alt, String neu, boolean ignoreCase, boolean allOccurences) {
		int position = -1;
		if (alt==null) alt="";
		if (neu==null) neu="";
		if (original==null) original = new StringBuffer("");
		int altNeuDiff = neu.length()-alt.length();
		if (neu.lastIndexOf(alt)>=altNeuDiff) altNeuDiff = neu.lastIndexOf(alt)+1;
		if (ignoreCase) {
			position = original.toString().toLowerCase().indexOf(alt.toLowerCase());
			while (position>=0) {
				original.replace(position,position + alt.length(),neu);
				position = original.toString().toLowerCase().indexOf(alt.toLowerCase(),position+altNeuDiff);
			}
		} else {
			position = original.toString().indexOf(alt);
			while (position>=0) {
				original.replace(position,position + alt.length(),neu);
				position = original.toString().indexOf(alt,position+altNeuDiff);
			}
		}
        return original;
    }
	
	private static StringBuffer doReplace(StringBuffer original, char alt, String neu, boolean allOccurences) {
		int position = -1;
		if (original==null) original = new StringBuffer("");
		int altNeuDiff = neu.length()-1;
		if (neu.lastIndexOf(alt)>=altNeuDiff) altNeuDiff = neu.lastIndexOf(alt)+1;
		position = original.toString().indexOf(alt);
		while (position>=0) {
			original.replace(position,position + 1,neu);
			position = original.toString().indexOf(alt,position+altNeuDiff);
		}
		return original;
	}

	/**
	 * This function will split a string based on a split character.
	 * @param searchIn The string to split
	 * @param splitter The separator
	 * @return String array of split values
	 */
	public static String[] split(String searchIn, String splitter) {
		String[] results = new String[count(searchIn,splitter)+1];
		int position=0;
		int i=0;
		while (searchIn.indexOf(splitter,position)>=0) {
			results[i]=searchIn.substring(position,searchIn.indexOf(splitter,position+2));
			position = searchIn.indexOf(splitter,position)+1;
			i++;
		}
		results[(results.length-1)]=searchIn.substring(position);
		return results;
	}
	
	/**
	 * Remove String toBeRemoved from oroginal
	 * @param original String to look in
	 * @param toBeRemoved String to get removed
	 * @param ignoreCase should we take care of case
	 * @return String without toBeRemoved
	 */
	public static String remove(String original, String toBeRemoved, boolean ignoreCase) {
		String thisResult = null;
		if (!toBeRemoved.equals("") && toBeRemoved!=null) {
			thisResult = replace(original, toBeRemoved, "", ignoreCase, true);
		}
		return thisResult;
	}

	/**
	 * To split for indexes we will look for the next Word in tempstr
	 * @param tempStr to look into
	 * @return nextWord in String
	 */
	public static String getNextWord(String tempStr) {
		String word = "";
		if (tempStr.indexOf(' ')>=0) {
			word=tempStr.substring(0,tempStr.indexOf(' '));
		} else {
			word=tempStr;
		}
		return word;
	}

	/**
	 * For some save scnearios and analysis we should remove special characters, i.e. HTML
	 * @param tempStr to remove Special Char
	 * @return new String without special chars
	 */
	public static String removeSpecialChar(String tempStr) {
		if (tempStr!=null) {
			tempStr=replace(tempStr,",","", true, true);
			tempStr=replace(tempStr,".","", true, true);
			tempStr=replace(tempStr,"!","", true, true);
			tempStr=replace(tempStr,"?","", true, true);
			tempStr=replace(tempStr,"'","", true, true);
			tempStr=replace(tempStr,":","", true, true);
			tempStr=replace(tempStr,"(","", true, true);
			tempStr=replace(tempStr,")","", true, true);
			tempStr=replace(tempStr,"+","", true, true);
			tempStr=replace(tempStr,"-","", true, true);
			tempStr=replace(tempStr,">","", true, true);
			tempStr=replace(tempStr,"<","", true, true);
			tempStr=replace(tempStr,"/","", true, true);
			while (tempStr.indexOf("  ")>0) {
				tempStr=replace(tempStr,"  "," ", true, true);
			}
			tempStr=replace(tempStr,"	"," ", true, true);
		}
		return tempStr;
	}
	
	/**
	 * Should return you the number of occurences of "find" in "orig
	 * @param orig The String to look in
	 * @param find The String to look for
	 * @return Number of occurences, 0 if none
	 */
	
	public static int count(String orig, String find) {
		int retVal = 0;
		int pos = 0;
		while (orig.indexOf(find,pos)>0) {
			pos = orig.indexOf(find,pos)+1;
			retVal++;
		}
		return retVal;
	}
	
	/**
	 * Gnerate CRC String for tempStr
	 * @param tempStr
	 * @return CRC Code for tempStr
	 * @throws IOException
	 */
	public static String crc(String tempStr) throws IOException {
		java.util.zip.Adler32 inChecker = new java.util.zip.Adler32();
		java.util.zip.CheckedInputStream in = null;
		in = new java.util.zip.CheckedInputStream(new java.io.ByteArrayInputStream(tempStr.getBytes()), inChecker);
		@SuppressWarnings("unused") int c;
		while ((c = in.read()) != -1) c=0;
		String myCheckSum = "" + inChecker.getValue();
		return myCheckSum;
	}
}
