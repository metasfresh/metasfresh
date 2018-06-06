/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.util;

import java.awt.Color;
import java.awt.font.TextAttribute;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.AttributedCharacterIterator;
import java.text.AttributedCharacterIterator.Attribute;
import java.text.AttributedString;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;

import javax.annotation.concurrent.Immutable;
import javax.mail.internet.MimeUtility;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.StringUtils;
import org.adempiere.util.reflect.ClassInstanceProvider;
import org.adempiere.util.reflect.IClassInstanceProvider;
import org.slf4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.common.base.Predicates;

import de.metas.logging.LogManager;
import lombok.NonNull;

/**
 * General Utilities
 *
 * @author Jorg Janke
 * @version $Id: Util.java,v 1.3 2006/07/30 00:52:23 jjanke Exp $
 *
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL - BF [ 1748346 ]
 */
public class Util
{
	/** Logger */
	private static Logger log = LogManager.getLogger(Util.class.getName());

	/**
	 * Replace String values.
	 *
	 * @param value string to be processed
	 * @param oldPart old part
	 * @param newPart replacement - can be null or ""
	 * @return String with replaced values
	 */
	public static String replace(String value, String oldPart, String newPart)
	{
		if (value == null || value.length() == 0
				|| oldPart == null || oldPart.length() == 0)
			return value;
		//
		final int oldPartLength = oldPart.length();
		String oldValue = value;
		final StringBuffer retValue = new StringBuffer();
		int pos = oldValue.indexOf(oldPart);
		while (pos != -1)
		{
			retValue.append(oldValue.substring(0, pos));
			if (newPart != null && newPart.length() > 0)
				retValue.append(newPart);
			oldValue = oldValue.substring(pos + oldPartLength);
			pos = oldValue.indexOf(oldPart);
		}
		retValue.append(oldValue);
		// log.debug( "Env.replace - " + value + " - Old=" + oldPart + ", New=" + newPart + ", Result=" + retValue.toString());
		return retValue.toString();
	}	// replace

	/**
	 * Remove CR / LF from String
	 *
	 * @param in input
	 * @return cleaned string
	 */
	public static String removeCRLF(String in)
	{
		final char[] inArray = in.toCharArray();
		final StringBuffer out = new StringBuffer(inArray.length);
		for (final char c : inArray)
		{
			if (c == '\n' || c == '\r')
				;
			else
				out.append(c);
		}
		return out.toString();
	}	// removeCRLF

	/**
	 * Fetch the numbers from a string
	 *
	 * @param text
	 * @return string which contains all digits, or null if text is null
	 */
	public static String getDigits(final String text)
	{
		if (text == null)
		{
			return null;
		}
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < text.length(); i++)
		{
			if (Character.isDigit(text.charAt(i)))
				sb.append(text.charAt(i));
		}
		return sb.toString();
	}

	/**
	 * Fetch only the text from a given string <br>
	 * E.g. text= '9000 St. Gallen'<br>
	 * This method will return test St. Gallen
	 *
	 * @param text
	 * @return string which contains all letters not digits, or null if text is null
	 */
	public static String stripDigits(final String text)
	{
		if (text == null)
		{
			return null;
		}
		final char[] inArray = text.toCharArray();
		final StringBuilder out = new StringBuilder(inArray.length);
		for (final char element : inArray)
		{
			final char c = element;
			if (Character.isLetter(c) || !Character.isDigit(c))
				out.append(c);
		}
		return out.toString();
	}

	/**
	 * Clean - Remove all white spaces
	 *
	 * @param in in
	 * @return cleaned string
	 */
	public static String cleanWhitespace(String in)
	{
		final char[] inArray = in.toCharArray();
		final StringBuffer out = new StringBuffer(inArray.length);
		boolean lastWasSpace = false;
		for (final char c : inArray)
		{
			if (Character.isWhitespace(c))
			{
				if (!lastWasSpace)
					out.append(' ');
				lastWasSpace = true;
			}
			else
			{
				out.append(c);
				lastWasSpace = false;
			}
		}
		return out.toString();
	}	// cleanWhitespace

	/**
	 * remove white space from the begin
	 *
	 * @param in
	 * @return
	 */
	public static String cleanBeginWhitespace(String in)
	{
		final int len = in.length();
		int st = 0;
		final int off = 0;
		final char[] val = in.toCharArray();

		while ((st < len) && (val[off + st] <= ' '))
		{
			st++;
		}
		return ((st > 0) || (len < in.length())) ? in.substring(st, len) : in;
	}

	/**
	 *
	 * @param value note: <code>null</code> is threaded like ""
	 * @param size
	 * @param description
	 * @return
	 */
	public static String lpadZero(final String value, final int size, final String description)
	{
		final String valueFixed = prepareValueForPadding(value, size, description);

		final String s = "0000000000000000000" + valueFixed;
		return s.substring(s.length() - size);
	}

	/**
	 * Add 0 digits at the end of a String until it gets to the size given as paraameter.
	 *
	 * @param value
	 * @param size
	 * @param description
	 * @return
	 */
	public static String rpadZero(final String value, final int size, final String description)
	{
		final String valueFixed = prepareValueForPadding(value, size, description);

		final String s = valueFixed + "0000000000000000000";
		return s.substring(0, size);
	}

	private static String prepareValueForPadding(String value, int size, String description)
	{
		final String valueFixed;

		if (value == null)
		{
			valueFixed = "";
		}
		else
		{
			valueFixed = value.trim();
		}
		if (valueFixed.length() > size)
		{
			throw new AdempiereException("value='" + valueFixed + "' of '" + description + "' is bigger than " + size + " characters");
		}

		return valueFixed;
	}

	/**
	 * Mask HTML content. i.e. replace characters with &values; CR is not masked
	 *
	 * @param content content
	 * @return masked content
	 * @see #maskHTML(String, boolean)
	 */
	public static String maskHTML(String content)
	{
		return maskHTML(content, false);
	}	// maskHTML

	/**
	 * Mask HTML content. i.e. replace characters with &values;
	 *
	 * @param content content
	 * @param maskCR convert CR into <br>
	 * @return masked content or null if the <code>content</code> is null
	 */
	public static String maskHTML(String content, boolean maskCR)
	{
		// If the content is null, then return null - teo_sarca [ 1748346 ]
		if (content == null || content.isEmpty())
			return content;
		//
		final StringBuilder out = new StringBuilder();
		final char[] chars = content.toCharArray();
		for (final char c : chars)
		{
			switch (c)
			{
				case '<':
					out.append("&lt;");
					break;
				case '>':
					out.append("&gt;");
					break;
				case '&':
					out.append("&amp;");
					break;
				case '"':
					out.append("&quot;");
					break;
				case '\'':
					out.append("&#039;");
					break;
				case '\n':
					if (maskCR)
						out.append("<br>");
					//
				default:
					final int ii = c;
					if (ii > 255)		// Write Unicode
						out.append("&#").append(ii).append(";");
					else
						out.append(c);
					break;
			}
		}
		return out.toString();
	}	// maskHTML

	/**
	 * Get the number of occurances of countChar in string.
	 *
	 * @param string String to be searched
	 * @param countChar to be counted character
	 * @return number of occurances
	 */
	public static int getCount(String string, char countChar)
	{
		if (string == null || string.length() == 0)
			return 0;
		int counter = 0;
		final char[] array = string.toCharArray();
		for (final char element : array)
		{
			if (element == countChar)
				counter++;
		}
		return counter;
	}	// getCount

	/**
	 * Is String Empty
	 *
	 * @param str string
	 * @return true if >= 1 char
	 *
	 * @deprecated Please use {@link Check#isEmpty(String)}
	 */
	@Deprecated
	public static boolean isEmpty(String str)
	{
		return Check.isEmpty(str);
	}	// isEmpty

	/**
	 * Is String Empty
	 *
	 * @param str string
	 * @param trimWhitespaces trim whitespaces
	 * @return true if >= 1 char
	 *
	 * @deprecated Please use {@link Check#isEmpty(String, boolean)}
	 */
	@Deprecated
	public static boolean isEmpty(String str, boolean trimWhitespaces)
	{
		return Check.isEmpty(str, trimWhitespaces);
	}	// isEmpty

	/**
	 *
	 * @param bd
	 * @return true if bd is null or bd.signum() is zero
	 */
	@Deprecated
	public static boolean isEmpty(BigDecimal bd)
	{
		return Check.isEmpty(bd);
	}

	@Deprecated
	public static <T> boolean isEmpty(T[] arr)
	{
		return Check.isEmpty(arr);
	}

	/**************************************************************************
	 * Find index of search character in str. This ignores content in () and 'texts'
	 *
	 * @param str string
	 * @param search search character
	 * @return index or -1 if not found
	 */
	public static int findIndexOf(String str, char search)
	{
		return findIndexOf(str, search, search);
	}   // findIndexOf

	/**
	 * Find index of search characters in str. This ignores content in () and 'texts'
	 *
	 * @param str string
	 * @param search1 first search character
	 * @param search2 second search character (or)
	 * @return index or -1 if not found
	 */
	public static int findIndexOf(String str, char search1, char search2)
	{
		if (str == null)
			return -1;
		//
		int endIndex = -1;
		int parCount = 0;
		boolean ignoringText = false;
		final int size = str.length();
		while (++endIndex < size)
		{
			final char c = str.charAt(endIndex);
			if (c == '\'')
				ignoringText = !ignoringText;
			else if (!ignoringText)
			{
				if (parCount == 0 && (c == search1 || c == search2))
					return endIndex;
				else if (c == ')')
					parCount--;
				else if (c == '(')
					parCount++;
			}
		}
		return -1;
	}   // findIndexOf

	/**
	 * Find index of search character in str. This ignores content in () and 'texts'
	 *
	 * @param str string
	 * @param search search character
	 * @return index or -1 if not found
	 */
	public static int findIndexOf(String str, String search)
	{
		if (str == null || search == null || search.length() == 0)
			return -1;
		//
		int endIndex = -1;
		int parCount = 0;
		boolean ignoringText = false;
		final int size = str.length();
		while (++endIndex < size)
		{
			final char c = str.charAt(endIndex);
			if (c == '\'')
				ignoringText = !ignoringText;
			else if (!ignoringText)
			{
				if (parCount == 0 && c == search.charAt(0))
				{
					if (str.substring(endIndex).startsWith(search))
						return endIndex;
				}
				else if (c == ')')
					parCount--;
				else if (c == '(')
					parCount++;
			}
		}
		return -1;
	}   // findIndexOf

	/**************************************************************************
	 * Return Hex String representation of byte b
	 *
	 * @param b byte
	 * @return Hex
	 */
	static public String toHex(byte b)
	{
		final char hexDigit[] = {
				'0', '1', '2', '3', '4', '5', '6', '7',
				'8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
		};
		final char[] array = { hexDigit[(b >> 4) & 0x0f], hexDigit[b & 0x0f] };
		return new String(array);
	}

	/**
	 * Return Hex String representation of char c
	 *
	 * @param c character
	 * @return Hex
	 */
	static public String toHex(char c)
	{
		final byte hi = (byte)(c >>> 8);
		final byte lo = (byte)(c & 0xff);
		return toHex(hi) + toHex(lo);
	}   // toHex

	/**************************************************************************
	 * Init Cap Words With Spaces
	 *
	 * @param in string
	 * @return init cap
	 */
	public static String initCap(String in)
	{
		if (in == null || in.length() == 0)
			return in;
		//
		boolean capitalize = true;
		final char[] data = in.toCharArray();
		for (int i = 0; i < data.length; i++)
		{
			if (data[i] == ' ' || Character.isWhitespace(data[i]))
				capitalize = true;
			else if (capitalize)
			{
				data[i] = Character.toUpperCase(data[i]);
				capitalize = false;
			}
			else
				data[i] = Character.toLowerCase(data[i]);
		}
		return new String(data);
	}	// initCap

	/**************************************************************************
	 * Return a Iterator with only the relevant attributes. Fixes implementation in AttributedString, which returns everything
	 *
	 * @param aString attributed string
	 * @param relevantAttributes relevant attributes
	 * @return iterator
	 */
	static public AttributedCharacterIterator getIterator(AttributedString aString,
			AttributedCharacterIterator.Attribute[] relevantAttributes)
	{
		final AttributedCharacterIterator iter = aString.getIterator();
		final Set<Attribute> set = iter.getAllAttributeKeys();
		// System.out.println("AllAttributeKeys=" + set);
		if (set.size() == 0)
			return iter;
		// Check, if there are unwanted attributes
		final Set<AttributedCharacterIterator.Attribute> unwanted = new HashSet<>(iter.getAllAttributeKeys());
		for (final Attribute relevantAttribute : relevantAttributes)
			unwanted.remove(relevantAttribute);
		if (unwanted.size() == 0)
			return iter;

		// Create new String
		final StringBuffer sb = new StringBuffer();
		for (char c = iter.first(); c != AttributedCharacterIterator.DONE; c = iter.next())
			sb.append(c);
		aString = new AttributedString(sb.toString());

		// copy relevant attributes
		final Iterator<Attribute> it = iter.getAllAttributeKeys().iterator();
		while (it.hasNext())
		{
			final AttributedCharacterIterator.Attribute att = it.next();
			if (!unwanted.contains(att))
			{
				for (char c = iter.first(); c != AttributedCharacterIterator.DONE; c = iter.next())
				{
					final Object value = iter.getAttribute(att);
					if (value != null)
					{
						final int start = iter.getRunStart(att);
						final int limit = iter.getRunLimit(att);
						// System.out.println("Attribute=" + att + " Value=" + value + " Start=" + start + " Limit=" + limit);
						aString.addAttribute(att, value, start, limit);
						iter.setIndex(limit);
					}
				}
			}
			// else
			// System.out.println("Unwanted: " + att);
		}
		return aString.getIterator();
	}	// getIterator

	/**
	 * Dump a Map (key=value) to out
	 *
	 * @param map Map
	 */
	@SuppressWarnings("rawtypes")
	static public void dump(Map map)
	{
		System.out.println("Dump Map - size=" + map.size());
		final Iterator it = map.keySet().iterator();
		while (it.hasNext())
		{
			final Object key = it.next();
			final Object value = map.get(key);
			System.out.println(key + "=" + value);
		}
	}	// dump (Map)

	/**
	 * Print Action and Input Map for component
	 *
	 * @param comp Component with ActionMap
	 */
	public static void printActionInputMap(JComponent comp)
	{
		// Action Map
		final ActionMap am = comp.getActionMap();
		final Object[] amKeys = am.allKeys(); // including Parents
		if (amKeys != null)
		{
			System.out.println("-------------------------");
			System.out.println("ActionMap for Component " + comp.toString());
			for (final Object amKey : amKeys)
			{
				final Action a = am.get(amKey);

				final StringBuffer sb = new StringBuffer("- ");
				sb.append(a.getValue(Action.NAME));
				if (a.getValue(Action.ACTION_COMMAND_KEY) != null)
					sb.append(", Cmd=").append(a.getValue(Action.ACTION_COMMAND_KEY));
				if (a.getValue(Action.SHORT_DESCRIPTION) != null)
					sb.append(" - ").append(a.getValue(Action.SHORT_DESCRIPTION));
				System.out.println(sb.toString() + " - " + a);
			}
		}
		/**
		 * Same as below KeyStroke[] kStrokes = comp.getRegisteredKeyStrokes(); if (kStrokes != null) { System.out.println("-------------------------"); System.out.println("Registered Key Strokes - "
		 * + comp.toString()); for (int i = 0; i < kStrokes.length; i++) { System.out.println("- " + kStrokes[i].toString()); } } /** Focused
		 */
		InputMap im = comp.getInputMap(JComponent.WHEN_FOCUSED);
		KeyStroke[] kStrokes = im.allKeys();
		if (kStrokes != null)
		{
			System.out.println("-------------------------");
			System.out.println("InputMap for Component When Focused - " + comp.toString());
			for (final KeyStroke kStroke : kStrokes)
			{
				System.out.println("- " + kStroke.toString() + " - "
						+ im.get(kStroke).toString());
			}
		}
		/** Focused in Window */
		im = comp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		kStrokes = im.allKeys();
		if (kStrokes != null)
		{
			System.out.println("-------------------------");
			System.out.println("InputMap for Component When Focused in Window - " + comp.toString());
			for (final KeyStroke kStroke : kStrokes)
			{
				System.out.println("- " + kStroke.toString() + " - "
						+ im.get(kStroke).toString());
			}
		}
		/** Focused when Ancester */
		im = comp.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		kStrokes = im.allKeys();
		if (kStrokes != null)
		{
			System.out.println("-------------------------");
			System.out.println("InputMap for Component When Ancestor - " + comp.toString());
			for (final KeyStroke kStroke : kStrokes)
			{
				System.out.println("- " + kStroke.toString() + " - "
						+ im.get(kStroke).toString());
			}
		}
		System.out.println("-------------------------");
	}   // printActionInputMap

	/**
	 * Is 8 Bit
	 *
	 * @param str string
	 * @return true if string contains chars > 255
	 */
	public static boolean is8Bit(String str)
	{
		if (str == null || str.length() == 0)
			return true;
		final char[] cc = str.toCharArray();
		for (final char element : cc)
		{
			if (element > 255)
			{
				// System.out.println("Not 8 Bit - " + str);
				return false;
			}
		}
		return true;
	}	// is8Bit

	/**
	 * Clean Ampersand (used to indicate shortcut)
	 *
	 * @param in input
	 * @return cleaned string
	 */
	public static String cleanAmp(String in)
	{
		if (in == null || in.length() == 0)
			return in;
		final int pos = in.indexOf('&');
		if (pos == -1)
			return in;
		//
		if (pos + 1 < in.length() && in.charAt(pos + 1) != ' ')
			in = in.substring(0, pos) + in.substring(pos + 1);
		return in;
	}	// cleanAmp

	/**
	 * Trim to max character length
	 *
	 * @param str string
	 * @param length max (incl) character length
	 * @return string
	 */
	public static String trimLength(String str, int length)
	{
		if (str == null)
			return str;
		if (length <= 0)
			throw new IllegalArgumentException("Trim length invalid: " + length);
		if (str.length() > length)
			return str.substring(0, length);
		return str;
	}	// trimLength

	/**
	 * Size of String in bytes
	 *
	 * @param str string
	 * @return size in bytes
	 */
	public static int size(String str)
	{
		if (str == null)
			return 0;
		final int length = str.length();
		int size = length;
		try
		{
			size = str.getBytes("UTF-8").length;
		}
		catch (final UnsupportedEncodingException e)
		{
			log.error(str, e);
		}
		return size;
	}	// size

	/**
	 * Trim to max byte size
	 *
	 * @param str string
	 * @param size max size in bytes
	 * @return string
	 */
	public static String trimSize(String str, int size)
	{
		if (str == null)
			return str;
		if (size <= 0)
			throw new IllegalArgumentException("Trim size invalid: " + size);
		// Assume two byte code
		final int length = str.length();
		if (length < size / 2)
			return str;
		try
		{
			final byte[] bytes = str.getBytes("UTF-8");
			if (bytes.length <= size)
				return str;
			// create new - may cut last character in half
			final byte[] result = new byte[size];
			System.arraycopy(bytes, 0, result, 0, size);
			return new String(result, "UTF-8");
		}
		catch (final UnsupportedEncodingException e)
		{
			log.error(str, e);
		}
		return str;
	}	// trimSize

	/**************************************************************************
	 * Test
	 *
	 * @param args args
	 */
	public static void main(String[] args)
	{
		final String str = "a�b�c?d?e?f?g?";
		System.out.println(str + " = " + str.length() + " - " + size(str));
		final String str1 = trimLength(str, 10);
		System.out.println(str1 + " = " + str1.length() + " - " + size(str1));
		final String str2 = trimSize(str, 10);
		System.out.println(str2 + " = " + str2.length() + " - " + size(str2));
		//
		final AttributedString aString = new AttributedString("test test");
		aString.addAttribute(TextAttribute.FOREGROUND, Color.blue);
		aString.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON, 2, 4);
		getIterator(aString, new AttributedCharacterIterator.Attribute[] { TextAttribute.UNDERLINE });
	}	// main

	private static IClassInstanceProvider classInstanceProvider = ClassInstanceProvider.instance; // default/production implementation.

	/**
	 * Sets an alternative {@link IClassInstanceProvider} implementation. Intended use is for testing. This method is called by {@link org.adempiere.test.AdempiereTestHelper#init()}.
	 * Also see {@link org.adempiere.util.reflect.TestingClassInstanceProvider}.
	 *
	 * @param classInstanceProvider
	 */
	public static void setClassInstanceProvider(final IClassInstanceProvider classInstanceProvider)
	{
		Util.classInstanceProvider = classInstanceProvider;
	}

	/**
	 * Loads the class with <code>classname</code> and makes sure that it's implementing given <code>interfaceClazz</code>.
	 *
	 * @param interfaceClazz
	 * @param classname
	 * @return loaded class
	 *
	 * @see #setClassInstanceProvider(IClassInstanceProvider)
	 */
	public static final <T> Class<? extends T> loadClass(final Class<T> interfaceClazz, final String classname)
	{
		Check.assumeNotNull(classname, "className is not null");
		try
		{
			final Class<?> instanceClazz = classInstanceProvider.provideClass(classname);

			Check.errorUnless(interfaceClazz.isAssignableFrom(instanceClazz), "Class {} doesn't implement {}", instanceClazz, interfaceClazz);

			@SuppressWarnings("unchecked")
			final Class<? extends T> instanceClassCasted = (Class<? extends T>)instanceClazz;
			return instanceClassCasted;
		}
		catch (final Exception e)
		{
			throw new AdempiereException("Unable to instantiate '" + classname + "' implementing " + interfaceClazz, e);
		}
	}

	/**
	 * Creates a new instance of given <code>instanceClazz</code>.
	 * Also it makes sure that it's implementing given <code>interfaceClass</code>.
	 *
	 * @param interfaceClazz
	 * @param instanceClazz
	 * @return instance
	 * @see #setClassInstanceProvider(IClassInstanceProvider)
	 */
	public static final <T> T newInstance(final Class<T> interfaceClazz, final Class<?> instanceClazz)
	{
		try
		{
			return classInstanceProvider.provideInstance(interfaceClazz, instanceClazz);
		}
		catch (final ReflectiveOperationException e)
		{
			throw new AdempiereException("Unable to instantiate '" + instanceClazz + "' implementing " + interfaceClazz, e);
		}
	}

	/**
	 * Create an instance of given className.
	 * <p>
	 * This method works exactly like {@link #getInstanceOrNull(Class, String)} but it also throws and {@link AdempiereException} if class was not found.
	 * <p>
	 * For unit testing, see {@link org.adempiere.util.reflect.TestingClassInstanceProvider#throwExceptionForClassName(String, RuntimeException)}.
	 *
	 * @param interfaceClazz interface class or super class that needs to be implemented by class. May be <code>NULL</code>. If set, then the method will check if the given class name extends this
	 *            param value.
	 * @param className class name
	 * @return instance
	 * @throws AdempiereException if class does not implement given interface or if there is an error on instantiation or if class was not found
	 */
	public static <T> T getInstance(final Class<T> interfaceClazz, final String className)
	{
		Check.assumeNotNull(className, "className is not null");
		try
		{
			final Class<?> clazz = classInstanceProvider.provideClass(className);

			if (interfaceClazz != null)
			{
				return classInstanceProvider.provideInstance(interfaceClazz, clazz);
			}
			else
			{
				final Object instanceObj = clazz.newInstance();
				@SuppressWarnings("unchecked")
				final T instance = (T)instanceObj;
				return instance;
			}
		}
		catch (final ReflectiveOperationException e)
		{
			throw new AdempiereException("Unable to instantiate '" + className + "' implementing " + interfaceClazz, e);
		}
	}

	/**
	 * Create an instance of given className.
	 * <p>
	 * For unit testing, see {@link org.adempiere.util.reflect.TestingClassInstanceProvider#throwExceptionForClassName(String, RuntimeException)}.
	 *
	 * @param interfaceClazz interface class that needs to be implemented by class
	 * @param className class name
	 * @return instance or null if class was not found
	 * @throws AdempiereException if class does not implement given interface or if there is an error on instantiation
	 */
	public static <T> T getInstanceOrNull(final Class<T> interfaceClazz, final String className)
	{
		Check.assumeNotNull(className, "className may not be null");
		Check.assumeNotNull(interfaceClazz, "interfaceClazz may not be null");
		try
		{
			final Class<?> clazz = classInstanceProvider.provideClass(className);
			return classInstanceProvider.provideInstance(interfaceClazz, clazz);
		}
		catch (final ClassNotFoundException e)
		{
			return null;
		}
		catch (final ReflectiveOperationException e)
		{
			throw new AdempiereException("Unable to instantiate '" + className + "'", e);
		}
	}

	/**
	 * Little method that throws an {@link AdempiereException} if the given boolean condition is false. It might be a good idea to use "assume" instead of the assert keyword, because
	 * <li>assert is
	 * globally switched on and off and you never know what else libs are using assert</li>
	 * <li>there are critical assumptions that should always be validated. Not only during development time or when
	 * someone minds to use the -ea cmdline parameter</li>
	 *
	 * @param cond
	 * @param errMsg the error message to pass to the assertion error, if the condition is <code>false</code>
	 * @param params message parameters (@see {@link MessageFormat})
	 */
	@Deprecated
	public static void assume(final boolean cond, final String errMsg, Object... params)
	{
		Check.assume(cond, errMsg, params);
	}

	/**
	 * Assumes that given <code>object</code> is not null
	 *
	 * @param object
	 * @param assumptionMessage message
	 * @param params message parameters (@see {@link MessageFormat})
	 * @see #assume(boolean, String, Object...)
	 */
	@Deprecated
	public static void assumeNotNull(Object object, final String assumptionMessage, Object... params)
	{
		Check.assumeNotNull(object, assumptionMessage, params);
	}

	/**
	 * This method similar to {@link #assume(boolean, String, Object...)}, but the message should be formulated in terms of an error message instead of an assumption.
	 * <p>
	 * Example: instead of "parameter 'xy' is not null" (description of the assumption that was violated), one should write "parameter 'xy' is null" (description of the error).
	 *
	 * @param cond
	 * @param errMsg
	 * @param params
	 */
	@Deprecated
	public static void errorUnless(final boolean cond, final String errMsg, Object... params)
	{
		Check.errorUnless(cond, errMsg, params);
	}

	/**
	 * This method similar to {@link #assume(boolean, String, Object...)}, the error is throw <b>if the condition is true</b> and the message should be formulated in terms of an error message instead
	 * of an assumption.
	 * <p>
	 * Example: instead of "parameter 'xy' is not null" (description of the assumption that was violated), one should write "parameter 'xy' is null" (description of the error).
	 *
	 * @param cond
	 * @param errMsg
	 * @param params
	 */
	@Deprecated
	public static void errorIf(final boolean cond, final String errMsg, Object... params)
	{
		Check.errorIf(cond, errMsg, params);
	}

	/**
	 *
	 * @param message
	 * @param params
	 * @return
	 * @deprecated use {@link StringUtils#formatMessage(String, Object...)} instead
	 */
	@Deprecated
	public static String formatMessage(final String message, Object... params)
	{
		return StringUtils.formatMessage(message, params);
	}

	/**
	 * Returns an instance of {@link ArrayKey} that can be used as a key in HashSets and HashMaps.
	 *
	 * @param input
	 * @return
	 */
	public static ArrayKey mkKey(final Object... input)
	{
		return ArrayKey.of(input);
	}

	/**
	 * @return {@link ArrayKey} builder
	 */
	public static ArrayKeyBuilder mkKey()
	{
		return ArrayKey.builder();
	}

	/**
	 * Immutable wrapper for arrays that uses {@link Arrays#hashCode(Object[]))} and {@link Arrays#equals(Object)}. Instances of this class are obtained by {@link Util#mkKey(Object...)} and can be
	 * used as keys in hashmaps and hash sets.
	 *
	 * Thanks to http://stackoverflow.com/questions/1595588/java-how-to-be-sure-to-store-unique-arrays-based-on -its-values-on-a-list
	 *
	 * @author ts
	 *
	 */
	@Immutable
	public static class ArrayKey implements Comparable<ArrayKey>
	{
		public static final ArrayKey of(final Object... input)
		{
			return new ArrayKey(input);
		}

		public static final ArrayKeyBuilder builder()
		{
			return new ArrayKeyBuilder();
		}

		private final Object[] array;
		private String _stringBuilt = null;

		public ArrayKey(final Object... input)
		{
			this.array = input;
		}

		public Object[] getArray()
		{
			final Object[] newArray = new Object[array.length];
			System.arraycopy(array, 0, newArray, 0, array.length);
			return newArray;
		}

		@Override
		public int hashCode()
		{
			return Arrays.hashCode(array);
		}

		@Override
		public boolean equals(final Object other)
		{
			if (this == other)
			{
				return true;
			}
			if (other instanceof ArrayKey)
			{
				return Arrays.equals(this.array, ((ArrayKey)other).getArray());
			}
			return false;
		}

		@Override
		public String toString()
		{
			if (_stringBuilt != null)
			{
				return _stringBuilt;
			}

			final StringBuilder sb = new StringBuilder();
			for (final Object k : array)
			{
				if (sb.length() > 0)
				{
					sb.append("#");
				}
				if (k == null)
				{
					sb.append("NULL");
				}
				else
				{
					sb.append(k.toString());
				}
			}

			_stringBuilt = sb.toString();
			return _stringBuilt;
		}

		@Override
		public int compareTo(final ArrayKey other)
		{
			if (this == other)
			{
				return 0;
			}

			if (other == null)
			{
				return -1; // NULLs last
			}

			// NOTE: ideally we would compare each array element, using natural order,
			// but for now, comparing the strings is sufficient
			return toString().compareTo(other.toString());
		}

		public static int compare(final ArrayKey key1, final ArrayKey key2)
		{
			if (key1 == key2)
			{
				return 0;
			}
			if (key1 == null)
			{
				return +1; // NULLs last
			}
			if (key2 == null)
			{
				return -1; // NULLs last
			}
			return key1.compareTo(key2);
		}
	}

	/**
	 * Tests whether two objects are equals.
	 *
	 * @deprecated please use {@link Check#equals(Object, Object)}
	 */
	@Deprecated
	public static final boolean equals(Object a, Object b)
	{
		return Check.equals(a, b);
	}

	/**
	 * Tests whether two objects refer to the same object.
	 *
	 * It's advisable to use this method instead of directly comparing those 2 objects by o1 == o2, because in this way you are telling to static analyzer tool that comparing by reference was your
	 * intention.
	 *
	 * @param o1
	 * @param o2
	 * @return true if objects are the same (i.e. o1 == o2)
	 */
	public static final boolean same(Object o1, Object o2)
	{
		return o1 == o2;
	}

	/**
	 * Read given file and returns it as byte array
	 *
	 * @param file
	 * @return file contents as byte array
	 * @throws AdempiereException on any {@link IOException}
	 */
	public static byte[] readBytes(File file)
	{
		FileInputStream in = null;
		try
		{
			in = new FileInputStream(file);
			final byte[] data = readBytes(in);
			in = null; // stream was closed by readBytes(InputStream)

			return data;
		}
		catch (final Exception e)
		{
			throw new AdempiereException("Error reading file: " + file, e);
		}
		finally
		{
			if (in != null)
			{
				try
				{
					in.close();
				}
				catch (final IOException e)
				{
					e.printStackTrace();
				}
				in = null;
			}
		}
	}

	/**
	 * Read bytes from given InputStream. This method closes the stream.
	 *
	 * @param in
	 * @return stream contents as byte array
	 * @throws AdempiereException on error
	 */
	public static byte[] readBytes(final InputStream in)
	{
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		final byte[] buf = new byte[4096];

		try
		{
			int len = -1;
			while ((len = in.read(buf)) > 0)
			{
				out.write(buf, 0, len);
			}
		}
		catch (final IOException e)
		{
			throw new AdempiereException("Error reading stream", e);
		}
		finally
		{
			try
			{
				in.close();
			}
			catch (final IOException e)
			{
				e.printStackTrace();
			}
		}

		return out.toByteArray();
	}

	/***
	 * insert selection into DB
	 *
	 * @deprecated Please use {@link DB#createT_Selection(int, java.util.Collection, String)}.
	 */
	@Deprecated
	static public void insertSelection(final int[] selection, final int AD_PInstance_ID, final String trxName)
	{
		final ArrayList<Integer> results = new ArrayList<>(selection.length);

		for (final int element : selection)
		{
			results.add(element);
		}

		if (results.size() == 0)
			return;
		log.info("Selected #" + results.size());

		// insert selection
		// use the same pinstance id as the process
		DB.createT_Selection(AD_PInstance_ID, results, trxName);
	}

	/***
	 * encode base64
	 *
	 * @param b
	 * @return
	 * @throws Exception
	 */
	// metas: 03749
	public static byte[] encodeBase64(final byte[] b)
	{
		try
		{
			final ByteArrayOutputStream baos = new ByteArrayOutputStream();
			final OutputStream b64os = MimeUtility.encode(baos, "base64");
			b64os.write(b);
			b64os.close();
			return baos.toByteArray();
		}
		catch (final Exception e)
		{
			throw new AdempiereException(e);
		}
	}

	/***
	 * decode base64
	 *
	 * @param b
	 * @return
	 * @throws Exception
	 */
	// metas: 03749
	public static byte[] decodeBase64(byte[] b)
	{
		try
		{
			final ByteArrayInputStream bais = new ByteArrayInputStream(b);
			final InputStream b64is = MimeUtility.decode(bais, "base64");
			final byte[] tmp = new byte[b.length];
			final int n = b64is.read(tmp);
			final byte[] res = new byte[n];
			System.arraycopy(tmp, 0, res, 0, n);
			return res;
		}
		catch (final Exception e)
		{
			throw new AdempiereException(e);
		}
	}

	// 03743
	public static void writeBytes(final File file, final byte[] data)
	{
		FileOutputStream out = null;
		try
		{
			out = new FileOutputStream(file, false);
			out.write(data);
		}
		catch (final IOException e)
		{
			throw new AdempiereException("Cannot write file " + file + "."
					+ "\n " + e.getLocalizedMessage() // also append the original error message because it could be helpful for user.
					, e);
		}
		finally
		{
			if (out != null)
			{
				close(out);
				out = null;
			}
		}
	}

	public static final void close(Closeable c)
	{
		try
		{
			c.close();
		}
		catch (final IOException e)
		{
			// e.printStackTrace();
		}
	}

	/**
	 * Writes the given {@link Throwable}s stack trace into a string.
	 *
	 * @param e
	 * @return
	 */
	public static String dumpStackTraceToString(Throwable e)
	{
		final StringWriter sw = new StringWriter();
		final PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();
	}

	/**
	 * Smart converting given exception to string
	 *
	 * @param e
	 * @return
	 */
	public static String getErrorMsg(Throwable e)
	{
		// save the exception for displaying to user
		String msg = e.getLocalizedMessage();
		if (Check.isEmpty(msg, true))
		{
			msg = e.getMessage();
		}
		if (Check.isEmpty(msg, true))
		{
			// note that e.g. a NullPointerException doesn't have a nice message
			msg = dumpStackTraceToString(e);
		}

		return msg;
	}

	/**
	 * @return first not null value from list
	 * @see #coalesce(Object...)
	 */
	// NOTE: this method is optimized for common usage
	public static final <T> T coalesce(final T value1, final T value2)
	{
		return value1 == null ? value2 : value1;
	}

	/**
	 * @return first not null value from list
	 * @see #coalesce(Object...)
	 */
	// NOTE: this method is optimized for common usage
	public static final <T> T coalesce(final T value1, final T value2, final T value3)
	{
		return value1 != null ? value1 : (value2 != null ? value2 : value3);
	}

	/**
	 *
	 * @param values
	 * @return first not null value from list
	 */
	@SafeVarargs
	public static final <T> T coalesce(final T... values)
	{
		if (values == null || values.length == 0)
		{
			return null;
		}
		for (final T value : values)
		{
			if (value != null)
			{
				return value;
			}
		}
		return null;
	}

	/**
	 * Similar to {@link #coalesce(Object...)}, but invokes the given suppliers' get methods one by one.
	 *
	 * @param values
	 * @return
	 */
	@SafeVarargs
	public static final <T> T coalesceSuppliers(final Supplier<T>... values)
	{
		return firstValidValue(Predicates.notNull(), values);
	}

	@SafeVarargs
	public static final <T> T firstValidValue(@NonNull final Predicate<T> isValidPredicate, final Supplier<T>... values)
	{
		if (values == null || values.length == 0)
		{
			return null;
		}
		for (final Supplier<T> supplier : values)
		{
			final T value = supplier.get();
			if (isValidPredicate.test(value))
			{
				return value;
			}
		}
		return null;
	}

	/**
	 * Analog to {@link #coalesce(Object...)}, returns the first <code>int</code> value that is greater than 0.
	 *
	 * @param values
	 * @return first greater than zero value or zero
	 */
	public static final int firstGreaterThanZero(int... values)
	{
		if (values == null || values.length == 0)
		{
			return 0;
		}
		for (final int value : values)
		{
			if (value > 0)
			{
				return value;
			}
		}
		return 0;
	}

	@SafeVarargs
	public static final int firstGreaterThanZeroSupplier(@NonNull final Supplier<Integer>... suppliers)
	{
		if (suppliers == null || suppliers.length == 0)
		{
			return 0;
		}
		for (final Supplier<Integer> supplier : suppliers)
		{
			final Integer value = supplier.get();
			if (value > 0)
			{
				return value;
			}
		}
		return 0;
	}

	public static final String firstNotEmptyTrimmed(@NonNull final String... values)
	{
		for (int i = 0; i < values.length; i++)
		{
			if (!Check.isEmpty(values[i], true))
			{
				return values[i].trim();
			}
		}
		return null;
	}

	public static String replaceNonDigitCharsWithZero(String stringToModify)
	{
		final int size = stringToModify.length();

		final StringBuilder stringWithZeros = new StringBuilder();

		for (int i = 0; i < size; i++)
		{
			final char currentChar = stringToModify.charAt(i);

			if (!Character.isDigit(currentChar))
			{
				stringWithZeros.append('0');
			}
			else
			{
				stringWithZeros.append(currentChar);
			}
		}

		return stringWithZeros.toString();
	}

	// thx to http://www.java2s.com/Code/Java/XML/DOMUtilgetElementText.htm
	public static String getElementText(Node element)
	{
		final StringBuffer buf = new StringBuffer();
		final NodeList list = element.getChildNodes();
		boolean found = false;
		for (int i = 0; i < list.getLength(); i++)
		{
			final Node node = list.item(i);
			if (node.getNodeType() == Node.TEXT_NODE)
			{
				buf.append(node.getNodeValue());
				found = true;
			}
		}
		return found ? buf.toString() : null;
	}

	public static int getMinimumOfThree(final int no1, final int no2, final int no3)
	{
		return no1 < no2 ? (no1 < no3 ? no1 : no3) : (no2 < no3 ? no2 : no3);
	}
}   // Util
