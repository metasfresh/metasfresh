package de.metas.dlm.partitioner.impl;

import java.util.Date;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Loggables;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.time.SystemTime;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.slf4j.Logger;

import ch.qos.logback.classic.Level;
import de.metas.dlm.partitioner.IIterateResultHandler;
import de.metas.logging.LogManager;

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
	private static final transient Logger logger = LogManager.getLogger(SalesPurchaseWatchDog.class);

	private ITableRecordReference lastSalesReference;
	private Date lastSalesReferenceSeen;

	private ITableRecordReference lastPurchaseReference;
	private Date lastPurchaseReferenceSeen;

	private ITableRecordReference lastHUReference;
	private Date lastHUReferenceSeen;

	@Override
	public AddResult onRecordAdded(final ITableRecordReference r, final AddResult preliminaryResult)
	{
		final PlainContextAware ctx = PlainContextAware.newWithThreadInheritedTrx(Env.getCtx());
		final Object model = r.getModel(ctx);

		final String tableName = r.getTableName();
		if (tableName.startsWith("M_HU") && !tableName.startsWith("M_HU_Assign"))
		{
			lastHUReference = r;
			lastHUReferenceSeen = SystemTime.asDate();
		}
		else
		{
			setSalesOrPurchaseReference(r, model);
		}
		if (getNotNullReferenceCount() > 1)
		{
			Loggables.get().withLogger(logger, Level.WARN).addLog("Records which do not fit together are added to the same result.\n"
					+ "Signaling the crawler to stop! The records are:\n"
					+ "IsSOTrx=true, seen at {}: {}\n"
					+ "IsSOTrx=false, seen at {}: {}\n"
					+ "HU-record, seen at {}: {}\n",
					lastSalesReferenceSeen, lastSalesReference, lastPurchaseReferenceSeen, lastPurchaseReference, lastHUReferenceSeen, lastHUReference);

			return AddResult.STOP;
		}

		return preliminaryResult;
	}

	private int getNotNullReferenceCount()
	{
		int result = 0;
		if (lastHUReference != null)
		{
			result++;
		}
		if (lastSalesReference != null)
		{
			result++;
		}
		if (lastPurchaseReference != null)
		{
			result++;
		}
		return result;
	}

	/**
	 * If the given model has {@code IsSOTrx} or {@code SOTrx}, then this method set the respective members within this class.
	 *
	 * @param r
	 * @param model
	 */
	private void setSalesOrPurchaseReference(final ITableRecordReference r, final Object model)
	{
		final String soTrxColName1 = "IsSOTrx";
		final String soTrxColName2 = "SOTrx";

		if (!InterfaceWrapperHelper.hasModelColumnName(model, soTrxColName1) && !InterfaceWrapperHelper.hasModelColumnName(model, soTrxColName2))
		{
			return;
		}

		final Boolean soTrx = Util.coalesce(
				InterfaceWrapperHelper.getValueOrNull(model, soTrxColName1),
				InterfaceWrapperHelper.getValueOrNull(model, soTrxColName2));

		if (soTrx == null)
		{
			return;
		}

		if (soTrx)
		{
			lastSalesReference = r;
			lastSalesReferenceSeen = SystemTime.asDate();
		}
		else
		{
			lastPurchaseReference = r;
			lastPurchaseReferenceSeen = SystemTime.asDate();
		}
	}

	@Override
	public String toString()
	{
		return "SalesPurchaseWatchDog [lastSalesReference=" + lastSalesReference + ", lastPurchaseReference=" + lastPurchaseReference + "]";
	}

}
