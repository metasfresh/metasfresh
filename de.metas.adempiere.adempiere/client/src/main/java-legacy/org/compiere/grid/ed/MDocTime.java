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

import java.util.logging.Level;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import org.compiere.util.CLogger;

/**
 *	Time Model.
 *		Validates input for hour or minute field
 *  @see VDate
 *
 *  @author Jorg Janke
 *  @version  $Id: MDocTime.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 */
public final class MDocTime extends PlainDocument
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5359545674957190259L;

	/**
	 *	Constructor
	 *  @param isHour Hour field
	 *  @param is12Hour 12 hour format
	 */
	public MDocTime(boolean isHour, boolean is12Hour)
	{
		super();
		m_isHour = isHour;
		m_is12Hour = is12Hour;
	}	//	MDocTime

	private boolean		m_isHour;
	private boolean		m_is12Hour;
	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(MDocTime.class);

	/**
	 *	Insert String
	 *  @param offset offset
	 *  @param string string
	 *  @param attr attributes
	 *  @throws BadLocationException
	 */
	public void insertString (int offset, String string, AttributeSet attr)
		throws BadLocationException
	{
	//	log.fine( "MDocTime.insertString - Offset=" + offset
	//		+ ", String=" + string + ", Attr=" + attr	+ ", Text=" + getText() + ", Length=" + getText().length());

		//	manual entry
		//	DBTextDataBinder.updateText sends stuff at once
		if (string != null && string.length() == 1)
		{
			//	ignore if too long
			if (offset > 2)
				return;

			//	is it a digit ?
			if (!Character.isDigit(string.charAt(0)))
			{
				log.config("No Digit=" + string);
				return;
			}

			//	resulting string
			char[] cc = getText().toCharArray();
			cc[offset] = string.charAt(0);
			String result = new String(cc);

			int i = 0;
			try
			{
				i = Integer.parseInt(result.trim());
			}
			catch (Exception e)
			{
				log.log(Level.SEVERE, e.toString());
			}
			if (i < 0)
			{
				log.config("Invalid value: " + i);
				return;
			}
			//	Minutes
			if (!m_isHour && i > 59)
			{
				log.config("Invalid minute value: " + i);
				return;
			}
			//	Hour
			if (m_isHour && m_is12Hour && i > 12)
			{
				log.config("Invalid 12 hour value: " + i);
				return;
			}
			if (m_isHour && !m_is12Hour && i > 24)
			{
				log.config("Invalid 24 hour value: " + i);
				return;
			}
			//
		//	super.remove(offset, 1);	//	replace current position
		}
		//	Set new character
		super.insertString(offset, string, attr);
	}	//	insertString

	/**
	 *	Delete String
	 *  @param offset offset
	 *  @param length length
	 *  @throws BadLocationException
	 */
	public void remove (int offset, int length)
		throws BadLocationException
	{
	//	log.fine( "MDocTime.remove - Offset=" + offset + ", Length=" + length);

		super.remove(offset, length);
	}	//	deleteString

	/**
	 *	Get Full Text (always two character)
	 *  @return text
	 */
	private String getText()
	{
		StringBuffer sb = new StringBuffer();
		try
		{
			sb.append(getContent().getString(0, getContent().length()-1));		//	cr at end
		}
		catch (Exception e)
		{
		}
		while (sb.length() < 2)
			sb.insert(0, ' ');
		return sb.toString();
	}	//	getString

}	//	MDocTime
