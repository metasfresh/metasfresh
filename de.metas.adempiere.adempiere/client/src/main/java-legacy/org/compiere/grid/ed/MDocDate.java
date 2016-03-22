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

import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import javax.swing.UIManager;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.PlainDocument;

import org.slf4j.Logger;
import de.metas.logging.LogManager;

/**
 *	Date Model.
 *		Validates input based on date pattern
 *  @see VDate
 *
 *  @author Jorg Janke
 *  @version  $Id: MDocDate.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 */
final class MDocDate extends PlainDocument implements CaretListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8453168098223574265L;

	/**
	 *	Constructor
	 *  @param format format
	 *  @param tc text component
	 */
	public MDocDate (final SimpleDateFormat format, final JTextComponent tc)
	{
		super();
		
		m_tc = tc;
		m_tc.addCaretListener(this);
		
		//
		// Set format
		if (format == null)
		{
			m_format = new SimpleDateFormat();
		}
		else
		{
			m_format = format;
		}
		m_format.setLenient(false);

		//
		// Build the mask
		//	Mark delimiters as '^' in Pattern
		final char[] pattern = m_format.toPattern().toCharArray();
		for (int i = 0; i < pattern.length; i++)
		{
			//	do we have a delimiter?
			if ("dMyHms".indexOf(pattern[i]) < 0)
			{
				pattern[i] = DELIMITER;
			}
		}
		m_mask = new String(pattern);
	}

	private final JTextComponent m_tc;
	private final SimpleDateFormat m_format;
	private final String m_mask;
	private static final char DELIMITER = '^';
	private int	m_lastDot = 0;		//	last dot position
	/**	Logger			*/
	private static final Logger log = LogManager.getLogger(MDocDate.class);
	
	/**
	 *	Insert String
	 *  @param offset offset
	 *  @param string string
	 *  @param attr attributes
	 *  @throws BadLocationException
	 */
	@Override
	public void insertString (int offset, final String string, final AttributeSet attr) throws BadLocationException
	{
		if (log.isTraceEnabled())
			log.trace("Offset=" + offset + ",String=" + string + ",Attr=" + attr + ",OldText=" + getText() + ",OldLength=" + getText().length());
		
		//
		// Empty string => do nothing
		if (string == null || string.isEmpty())
		{
			return;
		}
		//
		//	Manual entry (one char string)
		else if (string.length() == 1)
		{
			//	ignore if too long
			if (offset >= m_mask.length())
				return;
			
			//	is it a digit ?
			final boolean isDigit = Character.isDigit(string.charAt(0));
			
			//	is it an empty field?
			int length = getText().length();
			if (offset == 0 && length == 0)
			{
				final Date today = new Date(System.currentTimeMillis());
				String dateStr = m_format.format(today);
				int caretPosition = 0;
				if (isDigit)
				{
					dateStr = string + dateStr.substring(1);
					caretPosition = 1;
				}
				
				super.insertString(0, dateStr, attr);
				m_tc.setCaretPosition(caretPosition);
				return;
			}

			//	is it a digit ?
			if (!isDigit)
			{
				provideErrorFeedback();
				return;
			}

			//	positioned before the delimiter - jump over delimiter
			if (offset != m_mask.length() - 1 && m_mask.charAt(offset + 1) == DELIMITER)
			{
				m_tc.setCaretPosition(offset + 2);
			}

			//	positioned at the delimiter
			if (m_mask.charAt(offset) == DELIMITER)
			{
				offset++;
				m_tc.setCaretPosition(offset + 1);
			}
			super.remove(offset, 1);	//	replace current position
			super.insertString(offset, string, attr); // set the new char
		}
		//
		// More then one character (setting the whole value or copy paste)
		else
		{
			// Make sure the resulting text content would be valid
			try
			{
				final String textOld = getText();
				final String textNew = textOld.isEmpty() ? string : textOld.substring(0, offset) + string + textOld.substring(offset);
				m_format.parse(textNew);
			}
			catch (Exception e)
			{
				log.debug("Cannot insert " + string + " because the resulting text won't be valid anymore", e);
				provideErrorFeedback();
				return;
			}
			
			//	Set new character
			super.insertString(offset, string, attr);
			
			//	New value set Cursor
			if (offset == 0)
			{
				m_tc.setCaretPosition(0);
			}
		}
	}	//	insertString

	/**
	 *	Delete String
	 *  @param offset offset
	 *  @param length length
	 *  @throws BadLocationException
	 */
	@Override
	public void remove (final int offset, final int length) throws BadLocationException
	{
		if (log.isTraceEnabled())
			log.trace("Offset=" + offset + ",Length=" + length);

		// Case: clear the field (i.e removing the whole content)
		if (offset == 0 && length == getLength())
		{
			super.remove(offset, length);
			return;
		}

		//	begin of string
		if (offset == 0 || length == 0)
		{
			//	empty the field
			//  if the length is 0 or greater or equal with the mask length - teo_sarca, [ 1660595 ] Date field: incorrect functionality on paste
			if (length >= m_mask.length() || length == 0)
				super.remove(offset, length);
			return;
		}
		
		//	one position behind delimiter
		if (offset-1 >= 0 && offset-1 < m_mask.length()
			&& m_mask.charAt(offset-1) == DELIMITER)
		{
			if (offset-2 >= 0)
				m_tc.setCaretPosition(offset-2);
			else
				return;
		}
		else
			m_tc.setCaretPosition(offset-1);
	}	//	deleteString
	
	@Override
	public void replace(final int offset, final int length, final String text, final AttributeSet attrs) throws BadLocationException
	{
		super.replace(offset, length, text, attrs);
	}

	/**
	 *	Caret Listener
	 *  @param e event
	 */
	@Override
	public void caretUpdate(CaretEvent e)
	{
		if(log.isTraceEnabled())
			log.trace("Dot=" + e.getDot() + ",Last=" + m_lastDot + ", Mark=" + e.getMark());
		
		//	Selection
		if (e.getDot() != e.getMark())
		{
			m_lastDot = e.getDot();
			return;
		}
		//

		//	Is the current position a fixed character?
		if (e.getDot()+1 > m_mask.length() 
			|| m_mask.charAt(e.getDot()) != DELIMITER)
		{
			m_lastDot = e.getDot();
			return;
		}

		//	Direction?
		int newDot = -1;
		if (m_lastDot > e.getDot())			//	<-
			newDot = e.getDot() - 1;
		else								//	-> (or same)
			newDot = e.getDot() + 1;
		if (e.getDot() == 0)						//	first
			newDot = 1;
		else if (e.getDot() == m_mask.length()-1)	//	last
			newDot = e.getDot() - 1;
		//
		if (log.isDebugEnabled())
				log.debug("OnFixedChar=" + m_mask.charAt(e.getDot()) + ", newDot=" + newDot + ", last=" + m_lastDot);
		//
		m_lastDot = e.getDot();
		if (newDot >= 0 && newDot < getText().length())
			m_tc.setCaretPosition(newDot);
	}	//	caretUpdate

	/**
	 *	Get Full Text
	 *  @return text
	 */
	private String getText()
	{
		String str = "";
		try
		{
			str = getContent().getString(0, getContent().length() - 1);		// cr at end
		}
		catch (Exception e)
		{
			str = "";
		}
		return str;
	}
	
	private final void provideErrorFeedback()
	{
		UIManager.getLookAndFeel().provideErrorFeedback(m_tc);
	}

}	//	MDocDate
