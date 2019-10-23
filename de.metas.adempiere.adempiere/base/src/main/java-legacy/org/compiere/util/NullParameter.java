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

import java.io.Serializable;

/**
 *  Null Parameter for CPreparedStatement
 *
 *  @author Jorg Janke
 *  @version $Id: NullParameter.java,v 1.2 2006/07/30 00:54:36 jjanke Exp $
 */
public class NullParameter implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5098594046699488306L;

	/**
	 * 	Cosntructor
	 *	@param type SQL Type java.sql.Types.*
	 */
	public NullParameter(int type)
	{
		m_type = type;
	}	//	NullParameter

	private int		m_type = -1;

	/**
	 * 	Get Type
	 *	@return type
	 */
	public int getType()
	{
		return m_type;
	}	//	getType

	/**
	 * 	String representation
	 * 	@return	info
	 */
	public String toString()
	{
		return "NullParameter Type=" + m_type;
	}	//	toString

}	//	NullParameter
