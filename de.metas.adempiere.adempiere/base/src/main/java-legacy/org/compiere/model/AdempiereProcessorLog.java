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
package org.compiere.model;

import java.sql.Timestamp;

/**
 *	Processor Log Interface
 *	
 *  @author Jorg Janke
 *  @version $Id: AdempiereProcessorLog.java,v 1.3 2006/07/30 00:51:04 jjanke Exp $
 */
public interface AdempiereProcessorLog
{
	/**
	 * 	Get Created Date
	 *	@return created
	 */
	public Timestamp getCreated();
	
	/**
	 * 	Get Summary. Textual summary of this request
	 * 	@return Summary
	 */
	public String getSummary ();

	/**
	 *	Get Description. Optional short description of the record
	 *	@return description
	 */
	public String getDescription ();

	/**
	 * 	Get Error. An Error occured in the execution
	 * 	@return true if error
	 */
	public boolean isError ();

	/**
	 * 	Get Reference. Reference for this record
	 * 	@return reference
	 */
	public String getReference ();

	/**
	 *	Get Text Message. Text Message
	 * 	@return text message
	 */
	public String getTextMsg (); 
	
}	//	AdempiereProcessorLog
