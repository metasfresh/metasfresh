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


/**
 *	Adempiere User Error.
 *	Cuased by (lack of) user input/selection.
 * 	(No program error)
 *	
 *  @author Jorg Janke
 *  @version $Id: AdempiereUserError.java,v 1.2 2006/07/30 00:54:35 jjanke Exp $
 */
public class AdempiereUserError extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5318376918072817705L;

	/**
	 * 	Adempiere User Error
	 *	@param message message
	 */
	public AdempiereUserError (String message)
	{
		super (message);
	}	//	AdempiereUserError

	/**
	 * 	Adempiere User Error
	 *	@param message message
	 *	@param fixHint fix hint
	 */
	public AdempiereUserError (String message, String fixHint)
	{
		super (message);
		setFixHint(fixHint);
	}	//	AdempiereUserError

	/**
	 * 	AdempiereUserError
	 *	@param message
	 *	@param cause
	 */
	public AdempiereUserError (String message, Throwable cause)
	{
		super (message, cause);
	}	//	AdempiereUserError

	/**	Additional Info how to fix	**/
	private String	m_fixHint = null;
	
	/**
	 * @return Returns the fixHint.
	 */
	public String getFixHint ()
	{
		return m_fixHint;
	}	//	getFixHint
	
	/**
	 * 	Set Fix Hint
	 *	@param fixHint fix hint
	 */
	public void setFixHint (String fixHint)
	{
		m_fixHint = fixHint;
	}	//	setFixHint
	
	
	/**
	 * 	String Representation
	 *	@return info
	 */
	public String toString ()
	{
		super.toString();
		StringBuffer sb = new StringBuffer ("UserError: ");
		sb.append(getLocalizedMessage());
		if (m_fixHint != null && m_fixHint.length() > 0)
			sb.append(" (").append(m_fixHint).append (")");
		return sb.toString ();
	}	//	toString
	
}	//	AdempiereUserError
