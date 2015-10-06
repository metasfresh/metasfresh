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

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.PlainDocument;

import org.compiere.apps.ADialog;
import org.compiere.util.CLogger;
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
	public MDocNumber(int displayType, DecimalFormat format,
		JTextComponent tc, String title)
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
		DecimalFormatSymbols sym = m_format.getDecimalFormatSymbols();
		m_decimalSeparator = sym.getDecimalSeparator();
		m_groupingSeparator = sym.getGroupingSeparator();
		m_minusSign = sym.getMinusSign();
	//	log.finest("Decimal=" + m_decimalSeparator + "(" + (int)m_decimalSeparator
	//		+ ") - Group=" + m_groupingSeparator + "(" + (int)m_groupingSeparator +")");
	}	//	MDocNumber

	/** DisplayType used            */
	private int    			        m_displayType = 0;
	/** Number Format               */
	private DecimalFormat	        m_format = null;
	/** The 'owning' component      */
	private JTextComponent	        m_tc = null;
	/** Title for calculator        */
	private String			        m_title = null;
	/** Decimal Separator			*/
	private char					m_decimalSeparator = '.';
	/** Grouping Separator			*/
	private char					m_groupingSeparator = ',';
	/** Minus Sign					*/
	private char					m_minusSign = '-';
	/**	Logger	*/
	private static CLogger 			log = CLogger.getCLogger (MDocNumber.class);
	
	/**************************************************************************
	 *	Insert String
	 *  @param origOffset
	 *  @param string
	 *  @param attr
	 *  @throws BadLocationException
	 */
	@Override
	public void insertString(int origOffset, String string, AttributeSet attr)
		throws BadLocationException
	{
		log.finest("Offset=" + origOffset + " String=" + string + " Length=" + string.length());
		if (origOffset < 0 || string == null)
			throw new IllegalArgumentException("Invalid argument");

		int offset = origOffset;
		int length = string.length();
		//	From DataBinder (assuming correct format)
		if (length != 1)
		{
			super.insertString(offset, string, attr);
			return;
		}

		/**
		 *	Manual Entry
		 */
		String content = getText();
		//	remove all Thousands
		if (content.indexOf(m_groupingSeparator) != -1)
		{
			StringBuffer result = new StringBuffer();
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
			super.remove(0, content.length());
			super.insertString(0, result.toString(), attr);
			//
			m_tc.setCaretPosition(offset);
		//	ADebug.trace(ADebug.l6_Database, "Clear Thousands (" + m_format.toPattern() + ")" + content + " -> " + result.toString());
			content = result.toString();
		}	//	remove Thousands

		/**********************************************************************
		 *	Check Character entered
		 */
		char c = string.charAt(0);
		if (Character.isDigit(c))   // c >= '0' && c <= '9')
		{
		//	ADebug.trace(ADebug.l6_Database, "Digit=" + c);
			super.insertString(offset, string, attr);
			return;
		}

		//	Plus - remove minus sign
		if (c == '+')
		{
		//	ADebug.trace(ADebug.l6_Database, "Plus=" + c);
			//	only positive numbers
			if (m_displayType == DisplayType.Integer)
				return;
			if (content.length() > 0 && content.charAt(0) == '-')
				super.remove(0, 1);
		}

		//	Toggle Minus - put minus on start of string
		else if (c == '-' || c == m_minusSign)
		{
		//	ADebug.trace(ADebug.l6_Database, "Minus=" + c);
			//	no minus possible
			if (m_displayType == DisplayType.Integer)
				return;
			//	remove or add
			if (content.length() > 0 && content.charAt(0) == '-')
				super.remove(0, 1);
			else
				super.insertString(0, "-", attr);
		}

		//	Decimal - remove other decimals
		//	Thousand - treat as Decimal
		else if (c == m_decimalSeparator || c == m_groupingSeparator || c == '.' || c == ',')
		{
		//	log.info("Decimal=" + c + " (ds=" + m_decimalSeparator + "; gs=" + m_groupingSeparator + ")");
			//  no decimals on integers
			if (m_displayType == DisplayType.Integer)
				return;
			int pos = content.indexOf(m_decimalSeparator);

			//	put decimal in
			String decimal = String.valueOf(m_decimalSeparator);
			super.insertString(offset, decimal, attr);

			//	remove other decimals
			if (pos != 0)
			{
				content = getText();
				StringBuffer result = new StringBuffer();
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
				m_tc.setCaretPosition(offset-correction+1);
			}	//	remove other decimals
		}	//	decimal or thousand

		//	something else
		else if (VNumber.AUTO_POPUP)
		{
			log.fine("Input=" + c + " (" + (int)c + ")");
			String result = VNumber.startCalculator(m_tc, getText(),
				m_format, m_displayType, m_title);
			super.remove(0, content.length());
			super.insertString(0, result, attr);
		}
		else
			ADialog.beep();
	}	//	insertString

	
	/**************************************************************************
	 *	Delete String
	 *  @param origOffset Offeset
	 *  @param length length
	 *  @throws BadLocationException
	 */
	@Override
	public void remove (int origOffset, int length)
		throws BadLocationException
	{
		log.finest("Offset=" + origOffset + " Length=" + length);
		if (origOffset < 0 || length < 0)
			throw new IllegalArgumentException("MDocNumber.remove - invalid argument");

		int offset = origOffset;
		if (length != 1)
		{
			super.remove(offset, length);
			return;
		}
		/**
		 *	Manual Entry
		 */
		String content = getText();
		//	remove all Thousands
		if (content.indexOf(m_groupingSeparator) != -1)
		{
			StringBuffer result = new StringBuffer();
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

	/**
	 *	Get Full Text
	 *  @return text
	 */
	private String getText()
	{
		Content c = getContent();
		String str = "";
		try
		{
			str = c.getString(0, c.length()-1);		//	cr at end
		}
		catch (Exception e)
		{}
		return str;
	}	//	getString

}	//	MDocNumber
