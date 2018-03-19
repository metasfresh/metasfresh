package de.metas.handlingunits.process;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.sql.Timestamp;
import java.util.Iterator;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.dao.impl.DateTruncQueryFilterModifier;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.util.Services;
import org.adempiere.util.collections.IteratorUtils;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_M_Attribute;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.attribute.Constants;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_BestBefore_V;
import de.metas.process.JavaProcess;
import de.metas.process.RunOutOfTrx;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class M_HU_Update_HU_Expired_Attribute extends JavaProcess
{
	private final IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);

	private I_M_Attribute _huExpiredAttribute;

	private int countUpdated;

	@Override
	@RunOutOfTrx
	protected String doIt() throws Exception
	{
		final Iterator<I_M_HU_BestBefore_V> husToChange = retrieveHUsToChange();
		try
		{
			while (husToChange.hasNext())
			{
				final I_M_HU_BestBefore_V huToChange = husToChange.next();
				process(huToChange);
			}
			return "@Updated@ #" + countUpdated;
		}
		finally
		{
			IteratorUtils.close(husToChange);
		}
	}

	private Iterator<I_M_HU_BestBefore_V> retrieveHUsToChange()
	{
		final Timestamp today = SystemTime.asDayTimestamp();
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_HU_BestBefore_V.class)
				.addOnlyContextClient()
				.addCompareFilter(I_M_HU_BestBefore_V.COLUMN_HU_ExpiredWarnDate, Operator.LESS_OR_EQUAL, today, DateTruncQueryFilterModifier.DAY)
				.addNotEqualsFilter(I_M_HU_BestBefore_V.COLUMN_HU_Expired, Constants.ATTR_Expired_Value_Expired)
				.orderBy(I_M_HU_BestBefore_V.COLUMN_M_HU_ID)
				.create()
				.iterate(I_M_HU_BestBefore_V.class);
	}

	private void process(final I_M_HU_BestBefore_V huToChange)
	{
		final int huId = huToChange.getM_HU_ID();
		try
		{
			huTrxBL.process(huContext -> {
				markExpiredByHUId(huContext, huId);
			});

			countUpdated++;
		}
		catch (final Exception ex)
		{
			addLog("Failed processing M_HU_ID={}: {}", huId, ex.getLocalizedMessage());
			log.warn("Failed processing M_HU_ID={}. Skipped", huId, ex);
		}
	}

	private void markExpiredByHUId(final IHUContext huContext, final int huId)
	{
		final I_M_HU hu = load(huId, I_M_HU.class);

		final IAttributeStorage huAttributes = huContext.getHUAttributeStorageFactory()
				.getAttributeStorage(hu);

		huAttributes.setSaveOnChange(true);

		final I_M_Attribute huExpiredAttribute = getHU_Expired_Attribute();
		huAttributes.setValue(huExpiredAttribute, Constants.ATTR_Expired_Value_Expired);
	}

	private I_M_Attribute getHU_Expired_Attribute()
	{
		if (_huExpiredAttribute == null)
		{
			_huExpiredAttribute = Services.get(IAttributeDAO.class).retrieveAttributeByValue(Constants.ATTR_Expired);
		}
		return _huExpiredAttribute;
	}
}
