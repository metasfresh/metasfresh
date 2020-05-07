/**
 *
 */
package de.metas.handlingunits.inout.impl;

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


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Product;
import org.compiere.model.Query;

import de.metas.handlingunits.inout.IHUPackingMaterialDAO;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.handlingunits.model.X_M_HU_PI_Item;

/**
 * @author cg
 *
 */
public class HUPackingMaterialDAO implements IHUPackingMaterialDAO
{
	@Override
	public List<I_M_HU_PackingMaterial> retrievePackingMaterials(final I_M_HU_PI_Item_Product pip)
	{
		if (pip == null)
		{
			return Collections.emptyList();
		}

		final String whereClause = I_M_HU_PackingMaterial.COLUMNNAME_M_HU_PackingMaterial_ID + " IN "
				+ " ( SELECT " + I_M_HU_PI_Item.COLUMNNAME_M_HU_PackingMaterial_ID + " FROM "
				+ I_M_HU_PI_Item.Table_Name + " WHERE " + I_M_HU_PI_Item.COLUMNNAME_M_HU_PI_Version_ID
				+ " = ? AND " + I_M_HU_PI_Item.COLUMNNAME_ItemType + " = ? AND " + I_M_HU_PI_Item.COLUMNNAME_IsActive + " = ? )";

		final List<Object> params = new ArrayList<>();
		params.add(pip.getM_HU_PI_Item().getM_HU_PI_Version_ID());
		params.add(X_M_HU_PI_Item.ITEMTYPE_PackingMaterial);
		params.add(true);

		final Properties ctx = InterfaceWrapperHelper.getCtx(pip);
		final String trxName = InterfaceWrapperHelper.getTrxName(pip);

		return new Query(ctx, I_M_HU_PackingMaterial.Table_Name, whereClause, trxName)
				.setParameters(params)
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.list(I_M_HU_PackingMaterial.class);
	}

	@Override
	public I_M_HU_PackingMaterial retrivePackingMaterialOfProduct(final I_M_Product product)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_HU_PackingMaterial.class, product)
				.filter(new EqualsQueryFilter<I_M_HU_PackingMaterial>(I_M_HU_PackingMaterial.COLUMNNAME_M_Product_ID, product.getM_Product_ID()))
				.create()
				.setOnlyActiveRecords(true)
				.firstOnly(I_M_HU_PackingMaterial.class);
	}
}
