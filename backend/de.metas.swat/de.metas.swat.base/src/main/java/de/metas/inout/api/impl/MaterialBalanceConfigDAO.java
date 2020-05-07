package de.metas.inout.api.impl;

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


import java.util.concurrent.CopyOnWriteArrayList;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Product;

import de.metas.inout.api.IMaterialBalanceConfigBL;
import de.metas.inout.api.IMaterialBalanceConfigDAO;
import de.metas.inout.model.I_M_Material_Balance_Config;
import de.metas.inout.spi.IMaterialBalanceConfigMatcher;

public class MaterialBalanceConfigDAO implements IMaterialBalanceConfigDAO
{

	@Override
	public I_M_Material_Balance_Config retrieveFitBalanceConfig(final I_M_InOutLine line)
	{
		// Services
		final IMaterialBalanceConfigBL materialConfigBL = Services.get(IMaterialBalanceConfigBL.class);

		// header
		final I_M_InOut inout = line.getM_InOut();

		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<I_M_Material_Balance_Config> queryBuilder = queryBL.createQueryBuilder(I_M_Material_Balance_Config.class, line);

		// product id
		final I_M_Product product = line.getM_Product();
		queryBuilder.addInArrayOrAllFilter(I_M_Material_Balance_Config.COLUMNNAME_M_Product_ID, product.getM_Product_ID(), null);

		// product category
		queryBuilder.addInArrayOrAllFilter(I_M_Material_Balance_Config.COLUMNNAME_M_Product_Category_ID, product.getM_Product_Category_ID(), null);

		// partner
		final I_C_BPartner partner = inout.getC_BPartner();
		queryBuilder.addInArrayOrAllFilter(I_M_Material_Balance_Config.COLUMNNAME_C_BPartner_ID, partner.getC_BPartner_ID(), null);

		// partner group
		queryBuilder.addInArrayOrAllFilter(I_M_Material_Balance_Config.COLUMNNAME_C_BP_Group_ID, partner.getC_BP_Group_ID(), null);

		// warehouse
		queryBuilder.addInArrayOrAllFilter(I_M_Material_Balance_Config.COLUMNNAME_M_Warehouse_ID, inout.getM_Warehouse_ID(), null);

		// Do not allow a config to be fetched if the vendor/customer flags don't fit the partner's vendor/customer flags

		// only for vendor
		if (partner.isCustomer())
		{
			queryBuilder.addInArrayOrAllFilter(I_M_Material_Balance_Config.COLUMNNAME_IsCustomer, true, null);
		}

		// only for customer
		if (partner.isVendor())
		{
			queryBuilder.addInArrayOrAllFilter(I_M_Material_Balance_Config.COLUMNNAME_IsVendor, true, null);
		}

		// only for flat rate
		// make sure that is the config is only for flatrate, the inoutline is also for flatrate

		final CopyOnWriteArrayList<IMaterialBalanceConfigMatcher> matchers = materialConfigBL.retrieveMatchers();

		boolean isUseForFlatrate = true;

		for (final IMaterialBalanceConfigMatcher matcher : matchers)
		{
			if (!matcher.matches(line))
			{
				isUseForFlatrate = false;
				break;
			}
		}

		if (isUseForFlatrate)
		{
			queryBuilder.addInArrayOrAllFilter(I_M_Material_Balance_Config.COLUMNNAME_IsForFlatrate, true, null);
		}
		else
		{
			queryBuilder.addInArrayOrAllFilter(I_M_Material_Balance_Config.COLUMNNAME_IsForFlatrate, false, null);
		}

		// ORDER BY
		queryBuilder
				.orderBy()
				.addColumn(I_M_Material_Balance_Config.COLUMNNAME_M_Product_ID, Direction.Descending, Nulls.Last)
				.addColumn(I_M_Material_Balance_Config.COLUMNNAME_M_Product_Category_ID, Direction.Descending, Nulls.Last)
				.addColumn(I_M_Material_Balance_Config.COLUMNNAME_C_BPartner_ID, Direction.Descending, Nulls.Last)
				.addColumn(I_M_Material_Balance_Config.COLUMNNAME_C_BP_Group_ID, Direction.Descending, Nulls.Last)
				.addColumn(I_M_Material_Balance_Config.COLUMNNAME_M_Warehouse_ID, Direction.Descending, Nulls.Last)
				.addColumn(I_M_Material_Balance_Config.COLUMNNAME_IsVendor, Direction.Descending, Nulls.Last)
				.addColumn(I_M_Material_Balance_Config.COLUMNNAME_IsCustomer, Direction.Descending, Nulls.Last)
				.addColumn(I_M_Material_Balance_Config.COLUMNNAME_IsForFlatrate, Direction.Descending, Nulls.Last);
		
		return queryBuilder
				.create()
				.first();
	}
}
