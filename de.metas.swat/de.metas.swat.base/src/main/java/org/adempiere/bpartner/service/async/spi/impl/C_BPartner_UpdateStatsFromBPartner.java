package org.adempiere.bpartner.service.async.spi.impl;

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


import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.LegacyAdapters;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.MBPartner;

import de.metas.async.api.IQueueDAO;
import de.metas.async.api.IWorkPackageBuilder;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.async.spi.WorkpackageProcessorAdapter;

/**
 * Update BPartner's TotalOpenBalance and SO_CreditUsed fields.
 * 
 * @author tsa
 *
 */
public class C_BPartner_UpdateStatsFromBPartner extends WorkpackageProcessorAdapter
{
	public static void createWorkpackage(final Properties ctx, final Set<Integer> bpartnerIds, final String trxName)
	{
		if (bpartnerIds == null || bpartnerIds.isEmpty())
		{
			return;
		}

		final IWorkPackageBuilder newWorkpackage = Services.get(IWorkPackageQueueFactory.class)
				.getQueueForEnqueuing(ctx, C_BPartner_UpdateStatsFromBPartner.class)
				.newBlock()
				.newWorkpackage();

		for (final int bpartnerId : bpartnerIds)
		{
			final ITableRecordReference bpartnerRef = new TableRecordReference(I_C_BPartner.Table_Name, bpartnerId);
			newWorkpackage.addElement(bpartnerRef);
		}
		newWorkpackage
				.bindToTrxName(trxName)
				.build();
	}

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workpackage, final String localTrxName)
	{
		final IQueueDAO queueDAO = Services.get(IQueueDAO.class);

		final List<I_C_BPartner> bpartners = queueDAO.retrieveItems(workpackage, I_C_BPartner.class, localTrxName);
		for (final I_C_BPartner bpartner : bpartners)
		{
			final MBPartner bpartnerPO = LegacyAdapters.convertToPO(bpartner);
			bpartnerPO.setTotalOpenBalance();
			InterfaceWrapperHelper.save(bpartnerPO);
		}

		return Result.SUCCESS;
	}
}
