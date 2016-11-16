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
package de.metas.process;

import org.adempiere.ad.trx.api.ITrx;

/**
 *  Interface for user started processes.
 *
 *  ProcessCtrl.startClass creates the Object and calls startProcess
 *  before executing the optional SQL procedure and Report.
 *
 *  see ProcessCtl#startClass
 *  @author     Jorg Janke
 *  @version    $Id: ProcessCall.java,v 1.3 2006/07/30 00:54:44 jjanke Exp $
 */
public interface ProcessCall
{
	/**
	 *  Start the process.
	 *  Called when pressing the ... button in ...
	 *  It should only return false, if the function could not be performed
	 *  as this causes the process to abort.
	 *
	 *  @param pi	Process Info
	 *  @param trx	transaction
	 *  @throws Exception in case of any failure
	 */
	public void startProcess (ProcessInfo pi, ITrx trx) throws Exception;

}   //  ProcessCall
