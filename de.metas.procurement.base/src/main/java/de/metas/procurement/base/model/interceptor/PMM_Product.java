package de.metas.procurement.base.model.interceptor;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidator;

import de.metas.procurement.base.IPMMContractsDAO;
import de.metas.procurement.base.IPMMProductBL;
import de.metas.procurement.base.IWebuiPush;
import de.metas.procurement.base.model.I_PMM_Product;

/*
 * #%L
 * de.metas.procurement.base
 * %%
 * Copyright (C) 2016 metas GmbH
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
@Callout(I_PMM_Product.class)
@Interceptor(I_PMM_Product.class)
public class PMM_Product
{
	public static final PMM_Product instance = new PMM_Product();

	private static final String MSG_ProductChangeNotAllowedForRunningContracts = "de.metas.procurement.ProductChangeNotAllowedForRunningContracts";

	private PMM_Product()
	{
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = {
			I_PMM_Product.COLUMNNAME_M_Product_ID,
			I_PMM_Product.COLUMNNAME_M_HU_PI_Item_Product_ID,
			I_PMM_Product.COLUMNNAME_M_AttributeSetInstance_ID
	})
	public void preventChangesIfContractActive(final I_PMM_Product pmmProduct)
	{
		if (Services.get(IPMMContractsDAO.class).hasRunningContracts(pmmProduct))
		{
			throw new AdempiereException("@" + MSG_ProductChangeNotAllowedForRunningContracts + "@");
		}
	}

	// NOTE: Always allow deactivating an PMM_Product even if it has a running contract.
	// See: https://github.com/metasfresh/metasfresh/issues/1817
	// @ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = { I_PMM_Product.COLUMNNAME_IsActive })
	// public void preventDeactivateIfContractActive(final I_PMM_Product pmmProduct)
	// {
	// if (!pmmProduct.isActive() && Services.get(IPMMContractsDAO.class).hasRunningContracts(pmmProduct))
	// {
	// throw new AdempiereException("@" + MSG_ProductChangeNotAllowedForRunningContracts + "@");
	// }
	// }

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_NEW }, ifColumnsChanged = {
			I_PMM_Product.COLUMNNAME_M_Product_ID,
			I_PMM_Product.COLUMNNAME_M_AttributeSetInstance_ID,
			I_PMM_Product.COLUMNNAME_M_HU_PI_Item_Product_ID
	})
	public void updateReadOnlyFields(final I_PMM_Product pmmProduct)
	{
		Services.get(IPMMProductBL.class).update(pmmProduct);
	}

	@CalloutMethod(columnNames = {
			I_PMM_Product.COLUMNNAME_M_Product_ID,
			I_PMM_Product.COLUMNNAME_M_AttributeSetInstance_ID,
			I_PMM_Product.COLUMNNAME_M_HU_PI_Item_Product_ID
	})
	public void updateReadOnlyFields(final I_PMM_Product pmmProduct, final ICalloutField unused)
	{
		Services.get(IPMMProductBL.class).update(pmmProduct);
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_NEW }, //
			ifColumnsChanged = {
					I_PMM_Product.COLUMNNAME_IsActive,
					I_PMM_Product.COLUMNNAME_M_Product_ID,
					I_PMM_Product.COLUMNNAME_M_AttributeSetInstance_ID,
					I_PMM_Product.COLUMNNAME_M_HU_PI_Item_Product_ID,
					I_PMM_Product.COLUMNNAME_M_Warehouse_ID, } //
			, afterCommit = true)
	public void pushToWebUI(final I_PMM_Product pmmProduct)
	{
		Services.get(IWebuiPush.class).pushProduct(pmmProduct);
	}
}
