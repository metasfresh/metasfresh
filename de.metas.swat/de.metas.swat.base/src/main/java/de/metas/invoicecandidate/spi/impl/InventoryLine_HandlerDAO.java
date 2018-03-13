package de.metas.invoicecandidate.spi.impl;

import java.util.Iterator;
import java.util.Properties;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_Inventory;
import org.compiere.model.X_C_DocType;

import de.metas.document.engine.IDocument;
import de.metas.interfaces.I_C_DocType;
import de.metas.invoicecandidate.model.I_M_InventoryLine;
import de.metas.invoicecandidate.spi.IInventoryLine_HandlerDAO;

/*
 * #%L
 * de.metas.swat.base
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

public class InventoryLine_HandlerDAO implements IInventoryLine_HandlerDAO
{

	@Override
	public Iterator<I_M_InventoryLine> retrieveAllLinesWithoutIC(final Properties ctx, final int limit, final String trxName)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final ICompositeQueryFilter<I_M_InventoryLine> filters = queryBL.createCompositeQueryFilter(I_M_InventoryLine.class);

		filters.addEqualsFilter(I_M_InventoryLine.COLUMNNAME_Processed, true);
		filters.addEqualsFilter(I_M_InventoryLine.COLUMNNAME_IsInvoiceCandidate, false); // which don't have invoice candidates already generated
		filters.addOnlyActiveRecordsFilter();

		//
		// Filter M_Inventory
		{
			final IQueryBuilder<I_M_Inventory> inventoryQueryBuilder = queryBL.createQueryBuilder(I_M_Inventory.class, ctx, trxName);

			// if the inventory was reversed, and there is no IC yet, don't bother creating one
			inventoryQueryBuilder.addNotEqualsFilter(I_M_Inventory.COLUMNNAME_DocStatus, IDocument.STATUS_Reversed);

			// Only create invoice candidates for material disposal
			{
				final IQuery<I_C_DocType> validDocTypesQuery = queryBL.createQueryBuilder(I_C_DocType.class, ctx, trxName)

						.addEqualsFilter(I_C_DocType.COLUMNNAME_DocBaseType, X_C_DocType.DOCBASETYPE_MaterialPhysicalInventory)
						.addEqualsFilter(I_C_DocType.COLUMNNAME_DocSubType, X_C_DocType.DOCSUBTYPE_InternalUseInventory)
						//
						.create();

				inventoryQueryBuilder.addInSubQueryFilter(I_M_Inventory.COLUMNNAME_C_DocType_ID, I_C_DocType.COLUMNNAME_C_DocType_ID, validDocTypesQuery);
			}

			filters.addInSubQueryFilter(I_M_InventoryLine.COLUMNNAME_M_Inventory_ID, I_M_Inventory.COLUMNNAME_M_Inventory_ID, inventoryQueryBuilder.create());
		}

		final IQueryBuilder<I_M_InventoryLine> queryBuilder = queryBL.createQueryBuilder(I_M_InventoryLine.class, ctx, trxName)
				.filter(filters)
				.filterByClientId();

		queryBuilder.orderBy()
				.addColumn(I_M_InventoryLine.COLUMNNAME_M_InventoryLine_ID);

		queryBuilder.setLimit(limit);

		return queryBuilder.create()
				.iterate(I_M_InventoryLine.class);
	}

}
