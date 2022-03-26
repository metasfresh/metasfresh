package de.metas.product.model.interceptor;

import de.metas.i18n.AdMessageKey;
import de.metas.organization.OrgId;
import de.metas.product.IProductPlanningSchemaBL;
import de.metas.product.ProductId;
import de.metas.product.ProductPlanningSchemaSelector;
import de.metas.uom.IUOMConversionDAO;
import de.metas.uom.UOMConversionsMap;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_UOM_Conversion;
import org.compiere.model.I_M_Product;
import org.compiere.model.ModelValidator;
import org.compiere.util.Env;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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
@Interceptor(I_M_Product.class)
@Callout(I_M_Product.class)
@Component()
public class M_Product
{
	private final IProductPlanningSchemaBL productPlanningSchemaBL = Services.get(IProductPlanningSchemaBL.class);

	private final IUOMConversionDAO uomConversionsDAO = Services.get(IUOMConversionDAO.class);

	private static final AdMessageKey MSG_PRODUCT_UOM_CONVERSION_ALREADY_LINKED = AdMessageKey.of("de.metas.order.model.interceptor.M_Product.Product_UOM_Conversion_Already_Linked");

	@Init
	public void registerCallouts()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_NEW)
	public void afterNew(final @NonNull I_M_Product product, @NonNull final ModelChangeType changeType)
	{
		if (changeType.isNew())
		{
			createOrUpdateProductPlanningsForSelector(product);
		}
	}

	@CalloutMethod(columnNames = I_M_Product.COLUMNNAME_Discontinued)
	public void updateDiscontinuedFrom(@NonNull final I_M_Product product)
	{
		if (product.isDiscontinued())
		{
			product.setDiscontinuedFrom(Env.getDate());
		}
		else
		{
			product.setDiscontinuedFrom(null);
		}
	}

	private void createOrUpdateProductPlanningsForSelector(final @NonNull I_M_Product product)
	{
		final ProductPlanningSchemaSelector productPlanningSchemaSelector = ProductPlanningSchemaSelector.ofNullableCode(product.getM_ProductPlanningSchema_Selector());
		if (productPlanningSchemaSelector == null)
		{
			return;
		}

		final ProductId productId = ProductId.ofRepoId(product.getM_Product_ID());
		final OrgId orgId = OrgId.ofRepoId(product.getAD_Org_ID());

		productPlanningSchemaBL.createOrUpdateProductPlanningsForSelector(productId, orgId, productPlanningSchemaSelector);
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE ,
			ifColumnsChanged = { I_M_Product.COLUMNNAME_C_UOM_ID })
	@CalloutMethod(columnNames = I_M_Product.COLUMNNAME_C_UOM_ID)
	public void setUOM_ID(@NonNull final I_M_Product product)
	{
		final AdMessageKey errorMessage = checkExistingUOMConversions(product);
		if (errorMessage != null)
		{
			throw new AdempiereException(errorMessage);
		}
	}

	@Nullable
	private AdMessageKey checkExistingUOMConversions(@NonNull final I_M_Product product)
	{
		final ProductId productId = ProductId.ofRepoId(product.getM_Product_ID());

		final UOMConversionsMap conversionsMap = uomConversionsDAO.getProductConversions(productId);
		if (conversionsMap.isHasRatesForNonStockingUOMs())
		{
			return MSG_PRODUCT_UOM_CONVERSION_ALREADY_LINKED;
		}

		return null;
	}
}
