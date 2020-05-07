package org.adempiere.process.rpl.requesthandler.api.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.process.rpl.exp.ExportHelper;
import org.adempiere.process.rpl.requesthandler.api.IReplRequestHandlerBL;
import org.adempiere.process.rpl.requesthandler.api.IReplRequestHandlerCtx;
import org.adempiere.process.rpl.requesthandler.api.IReplRequestHandlerResult;
import org.adempiere.process.rpl.requesthandler.model.I_IMP_RequestHandler;
import org.adempiere.process.rpl.requesthandler.model.I_IMP_RequestHandlerType;
import org.adempiere.process.rpl.requesthandler.spi.IReplRequestHandler;
import org.adempiere.server.rpl.interfaces.I_EXP_Format;
import org.compiere.model.MEXPFormat;
import org.compiere.model.PO;
import org.compiere.model.X_AD_ReplicationTable;
import org.compiere.util.Util;
import org.slf4j.Logger;
import org.w3c.dom.Document;

import de.metas.logging.LogManager;

public class ReplRequestHandlerBL implements IReplRequestHandlerBL
{
	private final transient Logger logger = LogManager.getLogger(getClass());

	@Override
	public IReplRequestHandler getRequestHandlerInstance(I_IMP_RequestHandler requestHandlerDef)
	{
		Util.assume(requestHandlerDef != null, "requestHandlerDef is not null");
		Util.assume(requestHandlerDef.isActive(), "Request handler {} is active", requestHandlerDef);

		final I_IMP_RequestHandlerType requestHandlerType = requestHandlerDef.getIMP_RequestHandlerType();
		Util.assume(requestHandlerType.isActive(), "Request handler type {} is active", requestHandlerType);

		final String classname = requestHandlerType.getClassname();
		final IReplRequestHandler requestHandler = Util.getInstance(IReplRequestHandler.class, classname);

		return requestHandler;
	}

	@Override
	public Document processRequestHandlerResult(final IReplRequestHandlerResult result)
	{
		final PO poToExport = result.getPOToExport();
		if (poToExport == null)
		{
			logger.debug("Skip because poToExport is null");
			return null;
		}

		final I_EXP_Format formatToUse = result.getFormatToUse();
		Util.assume(formatToUse != null, "formatToUse shall be set");
		
		final MEXPFormat formatToUsePO = (MEXPFormat)InterfaceWrapperHelper.getStrictPO(formatToUse);

		final ExportHelper exportHelper = new ExportHelper(poToExport.getCtx(), formatToUse.getAD_Client_ID());

		return exportHelper.createExportDOM(poToExport,
				formatToUsePO,
				0, // ReplicationMode
				X_AD_ReplicationTable.REPLICATIONTYPE_Merge,
				0 // ReplicationEvent
				);
	}

	@Override
	public IReplRequestHandlerResult createInitialRequestHandlerResult()
	{
		return new ReplRequestHandlerResult();
	}

	@Override
	public I_IMP_RequestHandlerType registerHandlerType(
			final String name,
			final Class<? extends IReplRequestHandler> clazz,
			final String entityType)
	{
		// TODO check if it already exists (by clazz and entityType).
		// If it exists, then Util.assume that AD_Client_ID=0 and return it
		// if it doesn't yet exist, then create it, save and return

		// final String classname = clazz.getName();
		// final int adClientId = 0;
		//
		// final String whereClause = I_IMP_RequestHandlerType.COLUMNNAME_Classname+"=?"
		// +" AND "+I_IMP_RequestHandlerType.COLUMNNAME_EntityType+"=?"
		// +" AND "+I_IMP_RequestHandlerType.COLUMNNAME_AD_Client_ID+"=?"
		// new Query(Env.getCtx(), I_IMP_RequestHandlerType.Table_Name, whereClause, Trx.TRXNAME_None)
		// .setParameters(classname, entityType, adClientId)

		return null;
	}

	@Override
	public IReplRequestHandlerCtx createCtx(
			final Properties ctxForHandler,
			final I_EXP_Format importFormat,
			final I_IMP_RequestHandler requestHandlerRecord)
	{
		final ReplRequestHandlerCtx ctx = new ReplRequestHandlerCtx();
		ctx.setEnvCtxToUse(ctxForHandler);
		ctx.setImportFormat(importFormat);
		ctx.setRequestHandlerRecord(requestHandlerRecord);

		return ctx;
	}

}
