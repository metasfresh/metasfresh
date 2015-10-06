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

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.PlainDocument;

import org.compiere.apps.ADialog;
import org.compiere.util.CLogger;

/**
 *	Date Model.
 *		Validates input based on date pattern
 *  @see VDate
 *
 *  @author Jorg Janke
 *  @version  $Id: MDocDate.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 */
public final class MDocDate extends PlainDocument implements CaretListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8453168098223574265L;

	/**
	 *	Constructor
	 *  @param displayType display type
	 *  @param format format
	 *  @param tc text component
	 *  @param title title
	 */
	public MDocDate (int displayType, SimpleDateFormat format,
		JTextComponent tc, String title)
	{
		super();
		m_displayType = displayType;
		m_tc = tc;
		m_tc.addCaretListener(this);
		//
		m_format = format;
		if (m_format == null)
			m_format = new SimpleDateFormat();
		m_format.setLenient(false);

		//	Mark delimiters as '^' in Pattern
		char[] pattern = m_format.toPattern().toCharArray();
		for (int i = 0; i < pattern.length; i++)
		{
			//	do we have a delimiter?
			if ("dMyHms".indexOf(pattern[i]) == -1)
				pattern[i] = DELIMITER;
		}
		m_mask = new String(pattern);
		//
		m_title = title;
		if (m_title == null)
			m_title = "";
	}	//	MDocDate

	private JTextComponent 		m_tc;
	private SimpleDateFormat	m_format;
	private String				m_mask;
	private static final char	DELIMITER = '^';
	//	for Calendar
	private String				m_title;
	private int					m_displayType;
	private int					m_lastDot = 0;		//	last dot position
	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(MDocDate.class);
	
	/**
	 *	Insert String
	 *  @param offset offset
	 *  @param string string
	 *  @param attr attributes
	 *  @throws BadLocationException
	 */
	@Override
	public void insertString (int offset, String string, AttributeSet attr)
		throws BadLocationException
	{
		log.finest("Offset=" + offset + ",String=" + string + ",Attr=" + attr
			+ ",OldText=" + getText() + ",OldLength=" + getText().length());

		//	manual entry
		//	DBTextDataBinder.updateText sends stuff at once - length=8
		if (string != null && string.length() == 1)
		{
			//	ignore if too long
			if (offset >= m_mask.length())
				return;

			//	is it an empty field?
			int length = getText().length();
			if (offset == 0 && length == 0)
			{
				Date today = new Date(System.currentTimeMillis());
				String dateStr = m_format.format(today);
				super.insertString(0, string + dateStr.substring(1), attr);
				m_tc.setCaretPosition(1);
				return;
			}

			//	is it a digit ?
			try
			{
				Integer.parseInt(string);
			}
			catch (Exception pe)
			{
				//hengsin, [ 1660175 ] Date field - anoying popup
				//startDateDialog();
				ADialog.beep();
				return;
			}

			//	try to get date in field, if invalid, get today's
			/*try
			{
				char[] cc = getText().toCharArray();
				cc[offset] = string.charAt(0);
				m_format.parse(new String(cc));
			}
			catch (ParseException pe)
			{
				startDateDialog();
				return;
			}*/

			//	positioned before the delimiter - jump over delimiter
			if (offset != m_mask.length()-1 && m_mask.charAt(offset+1) == DELIMITER)
				m_tc.setCaretPosition(offset+2);

			//	positioned at the delimiter
			if (m_mask.charAt(offset) == DELIMITER)
			{
				offset++;
				m_tc.setCaretPosition(offset+1);
			}
			super.remove(offset, 1);	//	replace current position
		}

		//	Set new character
		super.insertString(offset, string, attr);
		//	New value set Cursor
		if (offset == 0 && string != null && string.length() > 1)
			m_tc.setCaretPosition(0);
	}	//	insertString

	/**
	 *	Delete String
	 *  @param offset offset
	 *  @param length length
	 *  @throws BadLocationException
	 */
	@Override
	public void remove (int offset, int length)
		throws BadLocationException
	{
		log.finest("Offset=" + offset + ",Length=" + length);

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

	/**
	 *	Caret Listener
	 *  @param e event
	 */
	public void caretUpdate(CaretEvent e)
	{
		log.finest("Dot=" + e.getDot() + ",Last=" + m_lastDot
			+ ", Mark=" + e.getMark());
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
		log.fine("OnFixedChar=" + m_mask.charAt(e.getDot())
			+ ", newDot=" + newDot + ", last=" + m_lastDot);
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
			str = getContent().getString(0, getContent().length()-1);		//	cr at end
		}
		catch (Exception e)
		{
			str = "";
		}
		return str;
	}	//	getString

	/**
	 *	Call Calendar Dialog
	 */
	private void startDateDialog()
	{
		log.config("");

		//	Date Dialog
		String result = getText();
		Timestamp ts = null;
		try
		{
			ts = new Timestamp(m_format.parse(result).getTime());
		}
		catch (Exception pe)
		{
			ts = new Timestamp(System.currentTimeMillis());
		}
		ts = VDate.startCalendar(m_tc, ts, m_format, m_displayType, m_title);
		result = m_format.format(ts);

		//	move to field
		try
		{
			super.remove(0, getText().length());
			super.insertString(0, result, null);
		}
		catch (BadLocationException ble)
		{
			log.log(Level.SEVERE, "", ble);
		}
	}	//	startDateDialog

}	//	MDocDate
