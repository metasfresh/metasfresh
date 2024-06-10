package de.metas.product.model.interceptor;

import com.google.common.collect.Iterators;
import de.metas.i18n.AdMessageKey;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.IProductPlanningSchemaBL;
import de.metas.product.ProductId;
import de.metas.product.ProductPlanningSchemaSelector;
import de.metas.product.impl.ProductDAO;
import de.metas.uom.IUOMConversionDAO;
import de.metas.uom.UOMConversionsMap;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_Product;
import org.compiere.model.ModelValidator;
import org.compiere.util.Env;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

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
@Component
public class M_Product
{

	private static final AdMessageKey MSG_PRODUCT_UOM_CONVERSION_ALREADY_LINKED = AdMessageKey.of("de.metas.order.model.interceptor.M_Product.Product_UOM_Conversion_Already_Linked");

	private static final AdMessageKey MSG_PRODUCT_ALREADY_USED = AdMessageKey.of("de.metas.order.model.interceptor.M_Product.MSG_PRODUCT_ALREADY_USED");

	private final IProductPlanningSchemaBL productPlanningSchemaBL = Services.get(IProductPlanningSchemaBL.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);

	private final IUOMConversionDAO uomConversionsDAO = Services.get(IUOMConversionDAO.class);

	private final IProductBL productBL = Services.get(IProductBL.class);



	@Init
	public void registerCallouts()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void beforeSave(final @NonNull I_M_Product product)
	{
		ProductDAO.extractIssuingToleranceSpec(product); // validate
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

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = { I_M_Product.COLUMNNAME_C_UOM_ID })
	public void setUOM_ID(@NonNull final I_M_Product product)
	{
		final ProductId productId = ProductId.ofRepoId(product.getM_Product_ID());

		final Optional<AdMessageKey> uomConversionsExistsMessage = checkExistingUOMConversions(productId);
		if (uomConversionsExistsMessage.isPresent())
		{
			throw new AdempiereException(uomConversionsExistsMessage.get()).markAsUserValidationError();
		}

		final Optional<AdMessageKey> productUsedMessage = isProductUsed(productId);
		if (productUsedMessage.isPresent())
		{
			throw new AdempiereException(productUsedMessage.get()).markAsUserValidationError();
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = { I_M_Product.COLUMNNAME_Weight })
	public void updateOrderWeight(@NonNull final I_M_Product product)
	{
		final List<OrderId> orderIdsToBeUpdated = orderBL.getUnprocessedIdsBy(ProductId.ofRepoId(product.getM_Product_ID()));

		Iterators.partition(orderIdsToBeUpdated.iterator(), 100)
				.forEachRemaining(this::setWeightFromLines);
	}

	private void setWeightFromLines(@NonNull final List<OrderId> orderIds)
	{
		orderBL.getByIds(orderIds)
				.forEach(order -> {
					orderBL.setWeightFromLines(order);

					saveRecord(order);
				});
	}

	private Optional<AdMessageKey> checkExistingUOMConversions(@NonNull final ProductId productId)
	{
		final UOMConversionsMap conversionsMap = uomConversionsDAO.getProductConversions(productId);

		if (conversionsMap.isHasRatesForNonStockingUOMs())
		{
			return Optional.of(MSG_PRODUCT_UOM_CONVERSION_ALREADY_LINKED);
		}

		return Optional.empty();
	}


	private Optional<AdMessageKey> isProductUsed(@NonNull final ProductId productId)
	{
		if (productBL.isProductUsed(productId))
		{
			return Optional.of(MSG_PRODUCT_ALREADY_USED);
		}

		return Optional.empty();
	}
}
