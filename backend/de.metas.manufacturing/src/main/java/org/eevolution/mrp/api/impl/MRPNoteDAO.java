package org.eevolution.mrp.api.impl;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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


import de.metas.material.planning.IMaterialPlanningContext;
import de.metas.util.Services;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.service.ClientId;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.eevolution.model.I_AD_Note;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.mrp.api.IMRPNoteDAO;

import java.util.List;
import java.util.Properties;

public class MRPNoteDAO implements IMRPNoteDAO
{
	@Override
	public int deleteMRPNotes(final IMaterialPlanningContext mrpContext)
	{
		final IQueryBuilder<I_AD_Note> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Note.class, mrpContext);

		final ICompositeQueryFilter<I_AD_Note> filters = queryBuilder.getCompositeFilter();

		//
		// Only MRP related notes
		final int adTableId = Services.get(IADTableDAO.class).retrieveTableId(I_PP_MRP.Table_Name);
		filters.addEqualsFilter(I_AD_Note.COLUMNNAME_AD_Table_ID, adTableId);

		//
		// Filter by AD_Client_ID
		final ClientId clientId = mrpContext.getClientId();
		filters.addEqualsFilter(I_AD_Note.COLUMNNAME_AD_Client_ID, clientId);

		//
		// Filter by AD_Org_ID
		final I_AD_Org org = mrpContext.getAD_Org();
		final int adOrgId = org.getAD_Org_ID();
		filters.addEqualsFilter(I_AD_Note.COLUMNNAME_AD_Org_ID, adOrgId);

		//
		// Filter by Warehouse
		final I_M_Warehouse warehouse = mrpContext.getM_Warehouse();
		if (warehouse != null)
		{
			final int warehouseId = warehouse == null ? -1 : warehouse.getM_Warehouse_ID();
			filters.addEqualsFilter(I_AD_Note.COLUMNNAME_M_Warehouse_ID, warehouseId);
		}

		//
		// Filter by Plant
		final I_S_Resource plant = mrpContext.getPlant();
		if (plant != null)
		{
			final int plantId = plant == null ? -1 : plant.getS_Resource_ID();
			filters.addEqualsFilter(I_AD_Note.COLUMNNAME_PP_Plant_ID, plantId);
		}

		//
		// Filter by Product
		final I_M_Product product = mrpContext.getM_Product();
		if (product != null)
		{
			final int productId = product == null ? -1 : product.getM_Product_ID();
			filters.addEqualsFilter(I_AD_Note.COLUMNNAME_M_Product_ID, productId);
		}

		return queryBuilder.create()
				.deleteDirectly();
	}

	@Override
	public List<I_AD_Note> retrieveMRPNotesForMRPRecord(final Properties ctx, final int PP_MRP_ID)
	{
		final int adTableId = Services.get(IADTableDAO.class).retrieveTableId(I_PP_MRP.Table_Name);

		final IQueryBuilder<I_AD_Note> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Note.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_AD_Note.COLUMNNAME_AD_Table_ID, adTableId)
				.addEqualsFilter(I_AD_Note.COLUMNNAME_Record_ID, PP_MRP_ID)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient();

		queryBuilder.orderBy()
				.addColumn(I_AD_Note.COLUMNNAME_AD_Note_ID);

		return queryBuilder.create()
				.list();
	}

}
