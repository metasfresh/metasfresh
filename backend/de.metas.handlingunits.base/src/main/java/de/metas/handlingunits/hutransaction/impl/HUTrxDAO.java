package de.metas.handlingunits.hutransaction.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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

import de.metas.handlingunits.HuId;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.IQuery;

import de.metas.handlingunits.HUConstants;
import de.metas.handlingunits.hutransaction.IHUTrxDAO;
import de.metas.handlingunits.hutransaction.IHUTrxQuery;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_Trx_Hdr;
import de.metas.handlingunits.model.I_M_HU_Trx_Line;
import de.metas.util.Check;
import de.metas.util.Services;
import org.compiere.util.Env;

public class HUTrxDAO implements IHUTrxDAO
{
	@Override
	public IHUTrxQuery createHUTrxQuery()
	{
		return new HUTrxQuery();
	}

	@Override
	public List<I_M_HU_Trx_Line> retrieveTrxLines(final Properties ctx, final IHUTrxQuery huTrxQuery, final String trxName)
	{
		final IQuery<I_M_HU_Trx_Line> query = createTrxLineQuery(ctx, huTrxQuery, trxName);

		return query
				.setOnlyActiveRecords(true)
				.list(I_M_HU_Trx_Line.class);
	}

	private IQuery<I_M_HU_Trx_Line> createTrxLineQuery(final Properties ctx, final IHUTrxQuery huTrxQuery, final String trxName)
	{
		Check.assumeNotNull(huTrxQuery, "huTrxQuery not null");

		final IQueryBuilder<I_M_HU_Trx_Line> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_M_HU_Trx_Line.class, ctx, trxName);

		final ICompositeQueryFilter<I_M_HU_Trx_Line> filters = queryBuilder.getCompositeFilter();

		if (huTrxQuery.getAD_Table_ID() > 0)
		{
			Check.assume(huTrxQuery.getRecord_ID() > 0, "Record_ID > 0 for {}", huTrxQuery);
			filters.addEqualsFilter(I_M_HU_Trx_Line.COLUMNNAME_AD_Table_ID, huTrxQuery.getAD_Table_ID());
		}
		if (huTrxQuery.getRecord_ID() > 0)
		{
			Check.assume(huTrxQuery.getAD_Table_ID() > 0, "AD_Table_ID > 0 for {}", huTrxQuery);
			filters.addEqualsFilter(I_M_HU_Trx_Line.COLUMNNAME_Record_ID, huTrxQuery.getRecord_ID());
		}

		if (huTrxQuery.getM_HU_Trx_Hdr_ID() > 0)
		{
			filters.addEqualsFilter(I_M_HU_Trx_Line.COLUMNNAME_M_HU_Trx_Hdr_ID, huTrxQuery.getM_HU_Trx_Hdr_ID());
		}
		if (huTrxQuery.getExclude_M_HU_Trx_Line_ID() > 0)
		{
			filters.addNotEqualsFilter(I_M_HU_Trx_Line.COLUMNNAME_M_HU_Trx_Line_ID, huTrxQuery.getExclude_M_HU_Trx_Line_ID());
		}
		if (huTrxQuery.getParent_M_HU_Trx_Line_ID() > 0)
		{
			filters.addEqualsFilter(I_M_HU_Trx_Line.COLUMNNAME_Parent_HU_Trx_Line_ID, huTrxQuery.getParent_M_HU_Trx_Line_ID());
		}
		if (huTrxQuery.getM_HU_Item_ID() > 0)
		{
			filters.addEqualsFilter(I_M_HU_Trx_Line.COLUMNNAME_M_HU_Item_ID, huTrxQuery.getM_HU_Item_ID());
		}

		if(huTrxQuery.getM_HU_ID() > 0)
		{
			filters.addEqualsFilter(I_M_HU_Trx_Line.COLUMN_M_HU_ID, huTrxQuery.getM_HU_ID());
		}
		queryBuilder.orderBy()
				.addColumn(I_M_HU_Trx_Line.COLUMNNAME_M_HU_Trx_Line_ID);

		return queryBuilder.create();
	}

	@Override
	public List<I_M_HU_Trx_Line> retrieveReferencingTrxLines(final Object referencedModel)
	{
		Check.assumeNotNull(referencedModel, "referencedModel not null");
		final Properties ctx = InterfaceWrapperHelper.getCtx(referencedModel);
		final String trxName = InterfaceWrapperHelper.getTrxName(referencedModel);
		final int adTableId = InterfaceWrapperHelper.getModelTableId(referencedModel);
		final int recordId = InterfaceWrapperHelper.getId(referencedModel);

		return retrieveReferencingTrxLines(ctx, adTableId, recordId, trxName);
	}

	@Override
	public List<I_M_HU_Trx_Line> retrieveReferencingTrxLines(final Properties ctx,
			final int adTableId, final int recordId,
			final String trxName)
	{
		Check.assume(adTableId > 0, "adTableId > 0");
		Check.assume(recordId > 0, "recordId > 0");

		final IHUTrxQuery huTrxQuery = createHUTrxQuery();
		huTrxQuery.setAD_Table_ID(adTableId);
		huTrxQuery.setRecord_ID(recordId);

		return retrieveTrxLines(ctx, huTrxQuery, trxName);
	}

	@Override
	public List<I_M_HU_Trx_Line> retrieveTrxLines(final I_M_HU_Trx_Hdr trxHdr)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(trxHdr);
		final String trxName = InterfaceWrapperHelper.getTrxName(trxHdr);
		final IHUTrxQuery huTrxQuery = createHUTrxQuery();
		huTrxQuery.setM_HU_Trx_Hdr_ID(trxHdr.getM_HU_Trx_Hdr_ID());

		return retrieveTrxLines(ctx, huTrxQuery, trxName);
	}

	@Override
	public List<I_M_HU_Trx_Line> retrieveTrxLines(final I_M_HU_Item huItem)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(huItem);
		final String trxName = InterfaceWrapperHelper.getTrxName(huItem);
		final IHUTrxQuery huTrxQuery = createHUTrxQuery();
		huTrxQuery.setM_HU_Item_ID(huItem.getM_HU_Item_ID());

		return retrieveTrxLines(ctx, huTrxQuery, trxName);
	}

	@Override
	public I_M_HU_Trx_Line retrieveCounterpartTrxLine(final I_M_HU_Trx_Line trxLine)
	{
		final I_M_HU_Trx_Line trxLineCounterpart = trxLine.getParent_HU_Trx_Line();

		if (trxLineCounterpart == null)
		{
			throw new AdempiereException("Counterpart transaction was not found for " + trxLine);
		}

		if (HUConstants.DEBUG_07277_saveHUTrxLine && trxLineCounterpart.getM_HU_Trx_Line_ID() <= 0)
		{
			throw new AdempiereException("Counterpart transaction was not saved for " + trxLine
					+ "\nCounterpart trx: " + trxLineCounterpart);
		}

		return trxLineCounterpart;
	}

	@Override
	public List<I_M_HU_Trx_Line> retrieveReferencingTrxLinesForHuId(@NonNull final HuId huId)
	{
		final IHUTrxQuery huTrxQuery = createHUTrxQuery();
		huTrxQuery.setM_HU_ID(huId.getRepoId());

		return retrieveTrxLines(Env.getCtx(), huTrxQuery, ITrx.TRXNAME_ThreadInherited);
	}
	
	@Override
	@Deprecated
	public List<I_M_HU_Trx_Line> retrieveReferencingTrxLinesForHU(final I_M_HU hu)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(hu);
		final String trxName = InterfaceWrapperHelper.getTrxName(hu);
		final IHUTrxQuery huTrxQuery = createHUTrxQuery();
		huTrxQuery.setM_HU_ID(hu.getM_HU_ID());
		
		return retrieveTrxLines(ctx, huTrxQuery, trxName);
	}
}
