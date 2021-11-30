package de.metas.fresh.freshQtyOnHand.process;

/*
 * #%L
 * de.metas.fresh.base
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

import de.metas.fresh.model.I_Fresh_QtyOnHand;
import de.metas.fresh.model.I_Fresh_QtyOnHand_Line;
import de.metas.logging.LogManager;
import de.metas.process.JavaProcess;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.apache.commons.collections4.IteratorUtils;
import org.compiere.model.IQuery;
import org.slf4j.Logger;

import java.sql.Timestamp;
import java.util.Iterator;

/**
 * task http://dewiki908/mediawiki/index.php/08924_Sortierung_Zählliste-Sortierbegriffe_(101643853730)
 */
public class Fresh_QtyOnHand_UpdateSeqNo_And_Export_SortPref extends JavaProcess
{

	private static final transient Logger logger = LogManager.getLogger(Fresh_QtyOnHand_UpdateSeqNo_And_Export_SortPref.class);

	private static final int SEQNO_SPACING = 10;

	private static final String MSG_AD_USER_SORT_PREF_DEFAULT_FOR_MRP_PRODUCT_INFO_NOT_FOUND = "AD_User_SortPref_Default_For_MRP_Product_Info_Not_Found";
	private static final String MSG_AD_USER_SORT_PREF_AUTO_GEN_BY_FRESH_QTY_ON_HAND = "AD_User_SortPref_AutoGen_By_Fresh_QtyOnHand";

	//
	// services
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	
	@Override
	protected void prepare()
	{
		// nothing to prepare
	}

	@Override
	protected String doIt() throws Exception
	{
		final I_Fresh_QtyOnHand onHand = getProcessInfo().getRecord(I_Fresh_QtyOnHand.class);
		final Timestamp dateDoc = onHand.getDateDoc();

		final PlainContextAware contextProviderForNewRecords = PlainContextAware.newWithTrxName(getCtx(), getTrxName());

		final Iterator<I_Fresh_QtyOnHand_Line> linesWithDateDoc = retrieveLinesWithDateDoc(dateDoc);

		int seqNo = 0;
		int count = 0;
		for (final I_Fresh_QtyOnHand_Line qtyOnHandLine : IteratorUtils.asIterable(linesWithDateDoc))
		{
			seqNo += SEQNO_SPACING;
			count++;

			qtyOnHandLine.setSeqNo(seqNo);
			InterfaceWrapperHelper.save(qtyOnHandLine);
		}
		return "@Success@: @Updated@ " + count + " @Fresh_QtyOnHand_Line@";
	}


	private Iterator<I_Fresh_QtyOnHand_Line> retrieveLinesWithDateDoc(final Timestamp dateDoc)
	{
		final IQueryBuilder<I_Fresh_QtyOnHand_Line> linesWithDateDocQueryBuilder =
				queryBL.createQueryBuilder(I_Fresh_QtyOnHand.class, getCtx(), ITrx.TRXNAME_None)
						.addOnlyActiveRecordsFilter()
						.addEqualsFilter(I_Fresh_QtyOnHand.COLUMN_Processed, true)
						.addEqualsFilter(I_Fresh_QtyOnHand.COLUMN_DateDoc, dateDoc)
						.andCollectChildren(I_Fresh_QtyOnHand_Line.COLUMN_Fresh_QtyOnHand_ID, I_Fresh_QtyOnHand_Line.class);

		linesWithDateDocQueryBuilder.orderBy()
				.addColumn(I_Fresh_QtyOnHand_Line.COLUMNNAME_ProductGroup)
				.addColumn(I_Fresh_QtyOnHand_Line.COLUMNNAME_ProductName)
				.addColumn(I_Fresh_QtyOnHand_Line.COLUMNNAME_M_Product_ID); // note: same M_Product_ID => same Group and Name

		return linesWithDateDocQueryBuilder
				.create()
				.setOption(IQuery.OPTION_GuaranteedIteratorRequired, false)
				.setOption(IQuery.OPTION_IteratorBufferSize, 1000)
				.iterate(I_Fresh_QtyOnHand_Line.class);
	}
}
