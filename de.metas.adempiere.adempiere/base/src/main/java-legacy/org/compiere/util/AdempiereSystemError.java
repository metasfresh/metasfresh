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
 * 	Adempiere System Error.
 * 	Error caused by invalid configurations, etc.
 * 	(No program error)
 *	
 *  @author Jorg Janke
 *  @version $Id: AdempiereSystemError.java,v 1.2 2006/07/30 00:54:36 jjanke Exp $
 */
public class AdempiereSystemError extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9111445784263763938L;

	/**
	 * 	Adempiere System Error
	 *	@param message message
	 */
	public AdempiereSystemError (String message)
	{
		super (message);
	}	//	AdempiereSystemError

	/**
	 * 	Adempiere System Error
	 *	@param message message
	 *	@param detail detail
	 */
	public AdempiereSystemError (String message, Object detail)
	{
		super (message);
		setDetail (detail);
	}	//	AdempiereSystemError

	/**
	 * 	Adempiere System Error
	 *	@param message
	 *	@param cause
	 */
	public AdempiereSystemError (String message, Throwable cause)
	{
		super (message, cause);
	}	//	AdempiereSystemError

	/**	Details					*/
	private Object	m_detail = null;
	
	/**
	 * @return Returns the detail.
	 */
	public Object getDetail ()
	{
		return m_detail;
	}
	
	/**
	 * @param detail The detail to set.
	 */
	public void setDetail (Object detail)
	{
		m_detail = detail;
	}
	
	/**
	 * 	String Representation
	 *	@return info
	 */
	public String toString ()
	{
		super.toString();
		StringBuffer sb = new StringBuffer ("SystemError: ");
		sb.append(getLocalizedMessage());
		if (m_detail != null)
			sb.append(" (").append(m_detail).append (")");
		return sb.toString ();
	}	//	toString

}	//	AdempiereSystemError
