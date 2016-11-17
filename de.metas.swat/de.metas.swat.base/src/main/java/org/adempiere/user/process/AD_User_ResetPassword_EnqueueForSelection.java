package org.adempiere.user.process;

/*
 * #%L
 * de.metas.swat.base
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


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.user.spi.impl.PasswordResetWorkpackageProcessor;
import org.adempiere.util.Services;
import org.compiere.model.Query;
import org.compiere.util.Util;

import de.metas.adempiere.model.I_AD_User;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.model.I_C_Queue_Block;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.process.JavaProcess;

public class AD_User_ResetPassword_EnqueueForSelection extends JavaProcess
{
	private IWorkPackageQueue queue;
	private I_C_Queue_Block queueBlock = null;

	@Override
	protected void prepare()
	{
	}

	@Override
	protected String doIt() throws Exception
	{
		queue = Services.get(IWorkPackageQueueFactory.class).getQueueForEnqueuing(getCtx(), PasswordResetWorkpackageProcessor.class);

		int countEnqueued = 0;

		final Iterator<I_AD_User> users = retrieveUsers();
		while (users.hasNext())
		{
			final I_AD_User user = users.next();
			enqueUser(user);
			countEnqueued++;
		}
		
		if (countEnqueued == 0)
		{
			throw new AdempiereException("@NotFound@ @AD_User_ID@");
		}
		return "Enqueued " + countEnqueued + " users to queue block " + queueBlock.getC_Queue_Block_ID();
	}

	private void enqueUser(final I_AD_User user)
	{
		if (queueBlock == null)
		{
			queueBlock = queue.enqueueBlock(getCtx());
		}

		final I_C_Queue_WorkPackage queueWorkpackage = queue.enqueueWorkPackage(queueBlock, null); // priority=null=Auto/Default
		queue.enqueueElement(queueWorkpackage, user);
		queue.markReadyForProcessing(queueWorkpackage);
	}

	public Iterator<I_AD_User> retrieveUsers()
	{
		final StringBuilder whereClause = new StringBuilder("1=1");
		final List<Object> params = new ArrayList<Object>();

		final String gtWhereClause = getProcessInfo().getWhereClause();
		if (!Util.isEmpty(gtWhereClause))
		{
			whereClause.append(" AND ").append("(").append(gtWhereClause).append(")");
		}

		// Only those who have an EMail Address
		whereClause.append(" AND ").append(I_AD_User.COLUMNNAME_EMail).append(" IS NOT NULL");

		// Only those who are configured to have access to our system (i.e. IsSystemUser=Y)
		whereClause.append(" AND ").append(I_AD_User.COLUMNNAME_IsSystemUser).append("=?");
		params.add(true);

		return new Query(getCtx(), I_AD_User.Table_Name, whereClause.toString(), ITrx.TRXNAME_None)
				.setParameters(params)
				.setApplyAccessFilterRW(true)
				.setOnlyActiveRecords(true)
				.setOrderBy(I_AD_User.COLUMNNAME_AD_User_ID)
				.iterate(I_AD_User.class, false) // guaranteed=false
		;
	}
}
