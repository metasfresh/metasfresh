package de.metas.dlm.partitioner.impl;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Loggables;
import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.util.Env;
import org.compiere.util.Util;

import de.metas.dlm.partitioner.IIterateResultHandler;

/*
 * #%L
 * metasfresh-dlm
 * %%
 * Copyright (C) 2017 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * This handler keeps track of the lad added records that have an {@code IsSOTrx} or {@code SOTrx} column.
 * If there was a record with {@code SOTrx='Y'} and then a record with {@code SOTrx='N'} is coming along (or vice versa, ofc), then it will use {@link Loggables} to log an informative message and return {@link AddResult#STOP}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class SalesPurchaseWatchDog implements IIterateResultHandler
{
	private ITableRecordReference lastSalesReference;
	private ITableRecordReference lastPurchaseReference;

	@Override
	public AddResult onRecordAdded(final ITableRecordReference r, final AddResult preliminaryResult)
	{
		final PlainContextAware ctx = PlainContextAware.newWithThreadInheritedTrx(Env.getCtx());
		final Object model = r.getModel(ctx);
		if (!InterfaceWrapperHelper.hasModelColumnName(model, "IsSOTrx") && !InterfaceWrapperHelper.hasModelColumnName(model, "SOTrx"))
		{
			return preliminaryResult;
		}

		final Boolean soTrx = Util.coalesce(InterfaceWrapperHelper.getValueOrNull(model, "IsSOTrx"), InterfaceWrapperHelper.getValueOrNull(model, "SOTrx"));
		if (soTrx == null)
		{
			return preliminaryResult;
		}

		if (soTrx)
		{
			lastSalesReference = r;
		}
		else
		{
			lastPurchaseReference = r;
		}

		if (lastPurchaseReference != null && lastSalesReference != null)
		{
			Loggables.get().addLog("Earlier a record with IsSOTrx={} was added and now a record with IsSOTrx={} is added.\n"
					+ "Signaling the crawler to stop! The two records are:\n"
					+ "IsSOTrx=true: {}\n"
					+ "IsSOTrx=false: {}\n",
					!soTrx, soTrx, lastSalesReference, lastPurchaseReference);
			return AddResult.STOP;
		}

		return preliminaryResult;
	}

}
