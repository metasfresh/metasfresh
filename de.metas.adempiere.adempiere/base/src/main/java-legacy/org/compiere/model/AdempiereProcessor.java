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
import java.util.Properties;

/**
 *	Processor Interface
 *
 *  @author Jorg Janke
 *  @version $Id: AdempiereProcessor.java,v 1.2 2006/07/30 00:51:03 jjanke Exp $
 */
public interface AdempiereProcessor
{
	/**
	 * 	Get Client
	 *	@return AD_Client_ID
	 */
	public int getAD_Client_ID();

	/**
	 * 	Get Name
	 *	@return Name
	 */
	public String getName();

	/**
	 * 	Get Description
	 *	@return Description
	 */
	public String getDescription();

	/**
	 * 	Get Context
	 *	@return context
	 */
	public Properties getCtx();

	/**
	 * 	Get the frequency type
	 * 	@return frequency type
	 */
	public String getFrequencyType();

	/**
	 * 	Get the frequency
	 * 	@return frequency
	 */
	public int getFrequency();


	/**
	 * 	Get Unique ID
	 *	@return Unique ID
	 */
	public String getServerID();

	/**
	 * 	Get the date Next run
	 * 	@param requery requery database
	 * 	@return date next run
	 */
	public Timestamp getDateNextRun (boolean requery);

	/**
	 * 	Set Date Next Run
	 *	@param dateNextWork next work
	 */
	public void setDateNextRun(Timestamp dateNextWork);

	/**
	 * 	Get the date Last run
	 * 	@return date lext run
	 */
	public Timestamp getDateLastRun ();

	/**
	 * 	Set Date Last Run
	 *	@param dateLastRun last run
	 */
	public void setDateLastRun(Timestamp dateLastRun);

	/**
	 * 	Save out of transaction.
	 *	@return true if saved
	 */
	public boolean saveOutOfTrx();


	/**
	 * 	Get Processor Logs
	 *	@return logs
	 */
	public AdempiereProcessorLog[] getLogs();

	/**
	 * Gets model table name (e.g. IMP_Processor, R_RequestProcessor etc)
	 * @return model's table name
	 */
	public String get_TableName();
}	//	AdempiereProcessor
