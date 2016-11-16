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

import java.util.Properties;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.process.SvrProcess;

import org.adempiere.ad.service.ISequenceDAO;
import org.adempiere.ad.service.ITableSequenceChecker;
import org.adempiere.service.IClientDAO;
import org.adempiere.util.ILoggable;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Client;
import org.compiere.model.MSequence;
import org.compiere.util.Env;

/**
 *	System + Document Sequence Check
 *	
 *  @author Jorg Janke
 *  @version $Id: SequenceCheck.java,v 1.3 2006/07/30 00:54:44 jjanke Exp $
 */
public class SequenceCheck extends SvrProcess
{
	/**	Static Logger	*/
	private static Logger	s_log	= LogManager.getLogger(SequenceCheck.class);
	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	@Override
	protected void prepare()
	{
	}	//	prepare
	
	/**
	 *  Perform process.
	 *  (see also MSequenve.validate)
	 *  @return Message to be translated
	 *  @throws Exception
	 */
	@Override
	protected String doIt() throws java.lang.Exception
	{
		log.info("");
		//
		checkSequences(Env.getCtx(), this);
		return "Sequence Check";
	}	//	doIt
	
	public static final void checkSequences(final Properties ctx, final ILoggable logger)
	{
		final ITableSequenceChecker tableSequenceChecker = Services.get(ISequenceDAO.class).createTableSequenceChecker(ctx);
		tableSequenceChecker.setSequenceRangeCheck(true);
		tableSequenceChecker.setFailOnFirstError(false);
		tableSequenceChecker.setLogger(logger);
		tableSequenceChecker.run();
		
		checkClientSequences (ctx, logger);
		
	}
	
	/**
	 *	Validate Sequences
	 *	@param ctx context
	 */
	public static void validate(Properties ctx)
	{
		try
		{
			final SvrProcess process = null;
			checkSequences(ctx, process);
		}
		catch (Exception e)
		{
			s_log.error("validate", e);
		}
	}	//	validate
	
	
	
	/**
	 *	Check/Initialize DocumentNo/Value Sequences for all Clients 	
	 *	@param ctx context
	 *	@param sp server process or null
	 */
	private static void checkClientSequences (Properties ctx, ILoggable logger)
	{
		String trxName = null;
		
		//	Sequence for DocumentNo/Value
		for (final I_AD_Client client : Services.get(IClientDAO.class).retrieveAllClients(ctx))
		{
			if (!client.isActive())
				continue;
			MSequence.checkClientSequences (ctx, client.getAD_Client_ID(), trxName);
		}	//	for all clients
		
	}	//	checkClientSequences
}	//	SequenceCheck
