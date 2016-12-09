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


import java.sql.Timestamp;
import java.util.Iterator;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.process.JavaProcess;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.service.IADInfoWindowDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.user.api.IUserSortPrefDAO;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.apache.commons.collections4.IteratorUtils;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_InfoColumn;
import org.compiere.model.I_AD_InfoWindow;
import org.compiere.model.I_AD_User_SortPref_Hdr;
import org.compiere.model.I_AD_User_SortPref_Line;
import org.compiere.model.I_AD_User_SortPref_Line_Product;
import org.compiere.model.X_AD_User_SortPref_Hdr;

import de.metas.fresh.model.I_Fresh_QtyOnHand;
import de.metas.fresh.model.I_Fresh_QtyOnHand_Line;
import de.metas.fresh.model.I_X_MRP_ProductInfo_V;

/**
 * @task http://dewiki908/mediawiki/index.php/08924_Sortierung_Zählliste-Sortierbegriffe_(101643853730)
 */
public class Fresh_QtyOnHand_UpdateSeqNo_And_Export_SortPref extends JavaProcess
{

	private static final transient Logger logger = LogManager.getLogger(Fresh_QtyOnHand_UpdateSeqNo_And_Export_SortPref.class);

	private static final int SEQNO_SPACING = 10;

	private static final String MSG_AD_USER_SORT_PREF_DEFAULT_FOR_MRP_PRODUCT_INFO_NOT_FOUND = "AD_User_SortPref_Default_For_MRP_Product_Info_Not_Found";
	private static final String MSG_AD_USER_SORT_PREF_AUTO_GEN_BY_FRESH_QTY_ON_HAND = "AD_User_SortPref_AutoGen_By_Fresh_QtyOnHand";

	//
	// services
	private final IUserSortPrefDAO userSortPrefDAO = Services.get(IUserSortPrefDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IADInfoWindowDAO adInfoWindowDAO = Services.get(IADInfoWindowDAO.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);

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

		final PlainContextAware contextProviderForNewRecords = new PlainContextAware(getCtx(), getTrxName());

		final I_AD_User_SortPref_Line newLine = resetAndRetrieveSortPrefLineOrNull(contextProviderForNewRecords, dateDoc);
		if (newLine == null)
		{
			addLog(msgBL.getMsg(getCtx(), MSG_AD_USER_SORT_PREF_DEFAULT_FOR_MRP_PRODUCT_INFO_NOT_FOUND));
		}

		final Iterator<I_Fresh_QtyOnHand_Line> linesWithDateDoc = retrieveLinesWithDateDoc(dateDoc);

		int seqNo = 0;
		int count = 0;
		int lastProductId = 0;
		for (final I_Fresh_QtyOnHand_Line qtyOnHandLine : IteratorUtils.asIterable(linesWithDateDoc))
		{
			seqNo += SEQNO_SPACING;
			count++;

			qtyOnHandLine.setSeqNo(seqNo);
			InterfaceWrapperHelper.save(qtyOnHandLine);

			if (newLine == null
					|| lastProductId == qtyOnHandLine.getM_Product_ID())
			{
				// there can be multiple Fresh_QtyOnHand_Lines with the same product (and different ASIs), but in
				// AD_User_SortPref_Line_Product there can be only one line per M_Product_ID.
				// We can assume that two Fresh_QtyOnHand_Line with the same M_Product_ID are coming right behind each other, due to our ordering in the mehtod retrieveLinesWithDateDoc().
				continue;
			}

			final I_AD_User_SortPref_Line_Product newSortPrefLineProduct = InterfaceWrapperHelper.newInstance(I_AD_User_SortPref_Line_Product.class, contextProviderForNewRecords);
			newSortPrefLineProduct.setAD_User_SortPref_Line(newLine);
			newSortPrefLineProduct.setM_Product_ID(qtyOnHandLine.getM_Product_ID());
			newSortPrefLineProduct.setSeqNo(seqNo);
			InterfaceWrapperHelper.save(newSortPrefLineProduct);

			lastProductId = qtyOnHandLine.getM_Product_ID();
		}
		return "@Success@: @Updated@ " + count + " @Fresh_QtyOnHand_Line@";
	}

	/**
	 * 
	 * @param contextProviderForNewRecords used when creating the new {@link I_AD_User_SortPref_Line}.
	 * @param dateDoc needed for the localized description in the {@link I_AD_User_SortPref_Hdr} that we change in this method.
	 * @return
	 */
	private I_AD_User_SortPref_Line resetAndRetrieveSortPrefLineOrNull(final PlainContextAware contextProviderForNewRecords,
			final Timestamp dateDoc)
	{
		final I_AD_User_SortPref_Line newLine;

		final I_AD_InfoWindow mrpInfoWindow = adInfoWindowDAO.retrieveInfoWindowByTableName(getCtx(), I_X_MRP_ProductInfo_V.Table_Name);
		if (mrpInfoWindow == null)
		{
			logger.debug("Found no AD_InfoWindow for tableName {}", I_X_MRP_ProductInfo_V.Table_Name);
			return null;
		}
		final I_AD_User_SortPref_Hdr mrpProductInfoSortPrefs = userSortPrefDAO.retrieveDefaultSortPreferenceHdrOrNull(getCtx(),
				X_AD_User_SortPref_Hdr.ACTION_Info_Fenster,
				mrpInfoWindow.getAD_InfoWindow_ID());

		if (mrpProductInfoSortPrefs == null)
		{
			logger.debug("Found no default AD_User_SortPref_Hdr for AD_InfoWindow_ID={} (tableName {})",
					new Object[] { mrpInfoWindow.getAD_InfoWindow_ID(), I_X_MRP_ProductInfo_V.Table_Name });
			return null;
		}

		final I_AD_InfoColumn nameColumn = adInfoWindowDAO.retrieveInfoColumnByColumnName(mrpInfoWindow, I_X_MRP_ProductInfo_V.COLUMNNAME_ProductName);
		if (nameColumn == null)
		{
			logger.debug("Found no default AD_InfoColumn for AD_InfoWindow_ID={} (tableName {}) and columnName {}",
					new Object[] { mrpInfoWindow.getAD_InfoWindow_ID(), I_X_MRP_ProductInfo_V.Table_Name, I_X_MRP_ProductInfo_V.COLUMNNAME_ProductName });
			return null;
		}
		userSortPrefDAO.clearSortPreferenceLines(mrpProductInfoSortPrefs);

		mrpProductInfoSortPrefs.setDescription(msgBL.getMsg(getCtx(), MSG_AD_USER_SORT_PREF_AUTO_GEN_BY_FRESH_QTY_ON_HAND, new Object[] { dateDoc }));
		InterfaceWrapperHelper.save(mrpProductInfoSortPrefs);

		newLine = InterfaceWrapperHelper.newInstance(I_AD_User_SortPref_Line.class, contextProviderForNewRecords);
		newLine.setAD_User_SortPref_Hdr(mrpProductInfoSortPrefs);
		newLine.setSeqNo(SEQNO_SPACING);
		newLine.setIsAscending(true);
		newLine.setAD_InfoColumn_ID(nameColumn.getAD_InfoColumn_ID());
		InterfaceWrapperHelper.save(newLine);
		logger.debug("Created new AD_User_SortPref_Line {}", newLine);

		return newLine;
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

		final Iterator<I_Fresh_QtyOnHand_Line> linesWithDateDoc = linesWithDateDocQueryBuilder
				.create()
				.setOption(IQuery.OPTION_GuaranteedIteratorRequired, false)
				.setOption(IQuery.OPTION_IteratorBufferSize, 1000)
				.iterate(I_Fresh_QtyOnHand_Line.class);
		return linesWithDateDoc;
	}
}
