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
package org.compiere.process;

import org.compiere.model.MDocTypeCounter;

import de.metas.process.SvrProcess;

/**
 *	Validate Counter Document
 *	
 *  @author Jorg Janke
 *  @version $Id: DocTypeCounterValidate.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 */
public class DocTypeCounterValidate extends SvrProcess
{
	/**	Counter Document		*/
	private int					p_C_DocTypeCounter_ID = 0;
	/**	Document Relationship	*/
	private MDocTypeCounter		m_counter = null;
	
	/**
	 * 	Prepare
	 */
	protected void prepare ()
	{
		p_C_DocTypeCounter_ID = getRecord_ID();
	}	//	prepare

	/**
	 * 	Do It
	 *	@return message
	 */
	protected String doIt () throws Exception
	{
		log.info("C_DocTypeCounter_ID=" + p_C_DocTypeCounter_ID);
		m_counter = new MDocTypeCounter (getCtx(), p_C_DocTypeCounter_ID, get_TrxName());
		if (m_counter == null || m_counter.get_ID() == 0)
			throw new IllegalArgumentException("Not found C_DocTypeCounter_ID=" + p_C_DocTypeCounter_ID);
		//
		String error = m_counter.validate();
		m_counter.save();
		if (error != null)
			throw new Exception(error);
		
		return "OK";
	}	//	doIt
	
}	//	DocTypeCounterValidate
