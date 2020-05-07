package org.adempiere.process.rpl.requesthandler.spi.impl;

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
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.process.rpl.requesthandler.api.IReplRequestHandlerBL;
import org.adempiere.process.rpl.requesthandler.api.IReplRequestHandlerCtx;
import org.adempiere.process.rpl.requesthandler.api.IReplRequestHandlerResult;
import org.adempiere.process.rpl.requesthandler.spi.ReplRequestHandlerAdapter;
import org.adempiere.server.rpl.exceptions.ReplicationException;
import org.adempiere.util.Services;
import org.compiere.model.PO;
import org.compiere.util.Env;
import org.compiere.util.Util;

/**
 * This handler responds by simply sending the given PO with the given format, if permitted. It can be used in this scenario:
 * <ul>
 * <li>An external client sends a "query" XML string, using a simple lookup format
 * <li>that format references the <code>IMP_RequestHandler</code> connected to this class
 * <li>The standard replication interface loads the PO specified by the lookup format and then invokes and instance of this class
 * <li>this instance check if the caller is allowed to see the complete class and, if yes, returns it
 * <li>the loaded class is send back via replication interface, using the export format specified in the IMP_RequestHandler
 * </ul>
 * 
 * 
 * @author ts
 * 
 */
public class LoadPORequestHandler extends ReplRequestHandlerAdapter
{
	protected final transient Logger logger = LogManager.getLogger(getClass());

	/**
	 * Returns the given <code>po</code> and the <code>EXP_Format</code> specified by the <code>IMP_RequestHandler</code> that we are called with.
	 * 
	 * @param po
	 * @param ctx
	 * @return
	 */
	@Override
	public IReplRequestHandlerResult handleRequest(final PO po, IReplRequestHandlerCtx ctx)
	{
		final Properties ctxToUse = ctx.getEnvCtxToUse();

		if (!Util.same(po.getCtx(), ctxToUse))
		{
			// this shall not happen, it's an internal error
			throw new ReplicationException("PO does not have same context as we need it to use")
					.setParameter("PO", po)
					.setParameter("Ctx", po.getCtx())
					.setParameter("CtxToUse", ctxToUse);
			
			// Alternative: reload the given PO with 'ctxToUse' and therefore see if the current role really has access
			// final PO poToSend = MTable.get(ctxToUse, po.get_Table_ID()).getPO(po.get_ID(), po.get_TrxName());
		}
		
		final IReplRequestHandlerResult result = Services.get(IReplRequestHandlerBL.class).createInitialRequestHandlerResult();

		final IUserRolePermissions role = Env.getUserRolePermissions(ctxToUse);
		final boolean allowResponse = role.canUpdate(po.getAD_Client_ID(), po.getAD_Org_ID(),
				po.get_Table_ID(), po.get_ID(),
				false // createError
				);
		
		if (!allowResponse)
		{
			logger.warn("Response not allowed because there is no access to '{}'", po);
			return result;
		}

		final PO poToSend = createResponse(ctx, po);

		result.setPOToExport(poToSend);
		
		return result;
	}
	
	protected PO createResponse(IReplRequestHandlerCtx ctx, PO requestPO)
	{
		final PO responsePO = requestPO;
		return responsePO;
	}
}
