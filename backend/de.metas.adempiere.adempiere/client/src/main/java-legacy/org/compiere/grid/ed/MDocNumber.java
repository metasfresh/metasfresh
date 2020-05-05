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
package org.compiere.grid.ed;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.util.Check;

import javax.swing.UIManager;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.PlainDocument;

import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.compiere.util.DisplayType;

/**
 *  Number Document Model.
 *  Locale independent editing of numbers by removing thousands
 *  and treating ., as decimal separator. Final formatting in VNumber.setValue
 *
 *  @see VNumber
 * 	@author 	Jorg Janke
 * 	@version 	$Id: MDocNumber.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 * 
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL - BF [ 1759655 ]
 */
public final class MDocNumber extends PlainDocument
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2586631851842406102L;

	/**
	 *	Constructor
	 *  @param displayType
	 *  @param format
	 *  @param tc
	 *  @param title
	 */
	public MDocNumber(final int displayType, final DecimalFormat format, final JTextComponent tc, final String title)
	{
		super();
		if (format == null || tc == null || title == null)
			throw new IllegalArgumentException("Invalid argument");
		//
		m_displayType = displayType;
		m_format = format;
		m_tc = tc;
		m_title = title;
		//
		final DecimalFormatSymbols sym = m_format.getDecimalFormatSymbols();
		m_decimalSeparator = sym.getDecimalSeparator();
		m_groupingSeparator = sym.getGroupingSeparator();
		m_minusSign = sym.getMinusSign();
	}

	/** DisplayType used            */
	private final int m_displayType;
	/** Number Format               */
	private final DecimalFormat m_format;
	/** The 'owning' component      */
	private final JTextComponent m_tc;
	/** Title for calculator        */
	private final String m_title;
	/** Decimal Separator			*/
	private final char m_decimalSeparator; // = '.';
	/** Grouping Separator			*/
	private final char m_groupingSeparator; // = ',';
	/** Minus Sign					*/
	private final char m_minusSign; //  = '-';
	/**	Logger	*/
	private static final transient Logger log = LogManager.getLogger(MDocNumber.class);
	
	@Override
	public void insertString(final int origOffset, final String string, final AttributeSet attr) throws BadLocationException
	{
		if(log.isTraceEnabled())
			log.trace("Offset=" + origOffset + " String=" + string + " Length=" + string.length());
		
		if (origOffset < 0 || string == null)
			throw new IllegalArgumentException("Invalid argument");

		//
		// More then one character "typed".
		// It could be:
		// * From DataBinder
		// * from copy-paste
		int length = string.length();
		if (length != 1)
		{
			// Just call super to do the job
			// NOTE: we are assuming correct format
			super.insertString(origOffset, string, attr);
			return;
		}

		//
		// Manual Entry (i.e. one character typed)
		//

		//
		//	remove all Thousands (grouping) separator
		int offset = origOffset;
		String content = getText(); // initialize with current content
		if (content.indexOf(m_groupingSeparator) != -1)
		{
			final StringBuilder result = new StringBuilder();
			for (int i = 0; i < content.length(); i++)
			{
				if (content.charAt(i) == m_groupingSeparator)
				{
					if (i < offset)
						offset--;
				}
				else
					result.append(content.charAt(i));
			}
			
			// Replace the content with our new content
			super.remove(0, content.length());
			super.insertString(0, result.toString(), attr);
			//
			m_tc.setCaretPosition(offset);
			content = result.toString();
		}	//	remove Thousands

		//
		// User typed one character
		//
		// Digit
		final char ch = string.charAt(0);
		if (Character.isDigit(ch))
		{
			super.insertString(offset, string, attr);
			return;
		}
		//
		// Plus - remove minus sign
		else if (ch == '+')
		{
			//	only positive numbers
			if (m_displayType == DisplayType.Integer)
				return;
			if (content.length() > 0 && content.charAt(0) == '-')
				super.remove(0, 1);
		}
		//
		// Minus - toggle minus sign
		else if (ch == '-' || ch == m_minusSign)
		{
			//	no minus possible
			if (m_displayType == DisplayType.Integer)
				return;
			//	remove or add
			if (content.length() > 0 && content.charAt(0) == '-')
				super.remove(0, 1);
			else
				super.insertString(0, "-", attr);
		}
		//
		//	Decimal - remove other decimals
		//	Thousand - treat as Decimal
		else if (isDecimalOrGroupingSeparator(ch))
		{
			//  no decimal or thousands separator on integers
			if (m_displayType == DisplayType.Integer)
				return;
			final int pos = content.indexOf(m_decimalSeparator);

			//	put decimal in
			super.insertString(offset, String.valueOf(m_decimalSeparator), attr);

			//	remove other decimals
			if (pos >= 0)
			{
				content = getText();
				final StringBuilder result = new StringBuilder();
				int correction = 0;
				for (int i = 0; i < content.length(); i++)
				{
					if (content.charAt(i) == m_decimalSeparator)
					{
						if (i == offset)
							result.append(content.charAt(i));
						else if (i < offset)
							correction++;
					}
					else
						result.append(content.charAt(i));
				}
				super.remove(0, content.length());
				super.insertString(0, result.toString(), attr);
				m_tc.setCaretPosition(offset - correction + 1);
			}	//	remove other decimals
		}	//	decimal or thousand
		//
		//	Something else - open calculator popup?
		else if (VNumber.AUTO_POPUP)
		{
			if (log.isDebugEnabled())
				log.debug("Input=" + ch + " (" + (int)ch + ")");
			
			final String result = VNumber.startCalculator(m_tc, getText(), m_format, m_displayType, m_title);
			super.remove(0, content.length());
			super.insertString(0, result, attr);
		}
		//
		// Something else => provide error feedback
		else
		{
			provideErrorFeedback();
		}
	}	//	insertString

	
	@Override
	public void remove (final int origOffset, final int length) throws BadLocationException
	{
		if(log.isTraceEnabled())
			log.trace("Offset=" + origOffset + " Length=" + length);
		
		if (origOffset < 0 || length < 0)
			throw new IllegalArgumentException("MDocNumber.remove - invalid argument");

		if (length != 1)
		{
			super.remove(origOffset, length);
			return;
		}
		
		//
		// Manual Entry
		//
		
		//
		//	remove all Thousands (grouping) separator
		int offset = origOffset;
		String content = getText();
		if (content.indexOf(m_groupingSeparator) != -1)
		{
			StringBuilder result = new StringBuilder();
			for (int i = 0; i < content.length(); i++)
			{
				if (content.charAt(i) == m_groupingSeparator && i != origOffset)
				{
					if (i < offset)
						offset--;
				}
				else
					result.append(content.charAt(i));
			}
			super.remove(0, content.length());
			super.insertString(0, result.toString(), null);
			m_tc.setCaretPosition(offset);
		}	//	remove Thousands
		super.remove(offset, length);
	}	//	remove
	
	@Override
	public void replace(final int offset, final int length, String text, final AttributeSet attrs) throws BadLocationException
	{
		//
		// Manual entry (i.e. single char typed)
		// If the given char is not valid, do nothing but just provide an error feedback
		if (text != null && text.length() == 1)
		{
			final char ch = text.charAt(0);
			if (!isValidChar(ch))
			{
				provideErrorFeedback();
				return;
			}
		}
		
		if(Check.isEmpty(text, true))
		{
			text = "";
		}
		
		
		// Case: completely replacing current content with an not empty text
		// => make sure the new text is a valid number (this would prevent copy-pasting wrong things)
		if (text.length() > 1 && offset == 0 && length == getLength())
		{
			try
			{
				m_format.parse(text);
			}
			catch (final ParseException e)
			{
				// not a valid number
				provideErrorFeedback();
				return;
			}
		}

		
		// Fallback
		super.replace(offset, length, text, attrs);
	}
	
	private final boolean isValidChar(final char ch)
	{
		return Character.isDigit(ch)
				|| (ch == '-' || ch == m_minusSign)
				|| (ch == '+')
				|| isDecimalOrGroupingSeparator(ch);
		
	}
	
	private final boolean isDecimalOrGroupingSeparator(final char ch)
	{
		return ch == m_decimalSeparator || ch == m_groupingSeparator || ch == '.' || ch == ',';		
	}
	
	/**
	 * Get Full Text
	 * 
	 * @return text or empty string; never returns null
	 */
	private final String getText()
	{
		final Content content = getContent();
		if (content == null)
		{
			return "";
		}
		
		final int length = content.length();
		if (length <= 0)
		{
			return "";
		}
		
		String str = "";
		try
		{
			str = content.getString(0, length - 1);		// cr at end
		}
		catch (BadLocationException e)
		{
			// ignore it, shall not happen
			log.debug("Error while getting the string content", e);
		}
		catch (Exception e)
		{
			log.debug("Error while getting the string content", e);
		}
		
		return str;
	}	//	getString

	private final void provideErrorFeedback()
	{
		UIManager.getLookAndFeel().provideErrorFeedback(m_tc);
	}
}	//	MDocNumber
