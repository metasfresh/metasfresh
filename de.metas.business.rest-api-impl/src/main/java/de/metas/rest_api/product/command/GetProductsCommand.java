package de.metas.rest_api.product.command;

import java.util.Collection;
import java.util.Set;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.model.I_M_Product;
import org.compiere.util.TimeUtil;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;

import de.metas.bpartner.BPartnerId;
import de.metas.dataentry.data.DataEntryCreatedUpdatedInfo;
import de.metas.i18n.IModelTranslationMap;
import de.metas.product.ProductId;
import de.metas.rest_api.product.ProductsServicesFacade;
import de.metas.rest_api.product.response.JsonGetProductsResponse;
import de.metas.rest_api.product.response.JsonProduct;
import de.metas.rest_api.product.response.JsonProductVendor;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import de.metas.util.lang.CoalesceUtil;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business.rest-api-impl
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

public class GetProductsCommand
{
	private final ProductsServicesFacade servicesFacade;
	private String adLanguage;

	private ImmutableListMultimap<ProductId, JsonProductVendor> productVendors;

	@Builder(buildMethodName = "_build")
	private GetProductsCommand(
			@NonNull final ProductsServicesFacade servicesFacade,
			@NonNull final String adLanguage)
	{
		this.servicesFacade = servicesFacade;
		this.adLanguage = adLanguage;
	}

	public static class GetProductsCommandBuilder
	{
		public JsonGetProductsResponse execute()
		{
			return _build().execute();
		}
	}

	public JsonGetProductsResponse execute()
	{
		final ImmutableList<I_M_Product> productRecords = servicesFacade.streamAllProducts()
				.collect(ImmutableList.toImmutableList());

		final ImmutableSet<ProductId> productIds = extractProductIds(productRecords);
		productVendors = retrieveJsonProductVendors(productIds);

		final ImmutableList<JsonProduct> products = productRecords.stream()
				.map(this::toJsonProduct)
				.collect(ImmutableList.toImmutableList());

		return JsonGetProductsResponse.builder()
				.products(products)
				.build();
	}

	private static ImmutableSet<ProductId> extractProductIds(final Collection<I_M_Product> records)
	{
		return records.stream()
				.mapToInt(I_M_Product::getM_Product_ID)
				.distinct()
				.mapToObj(ProductId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
	}

	private JsonProduct toJsonProduct(final I_M_Product productRecord)
	{
		final IModelTranslationMap trls = InterfaceWrapperHelper.getModelTranslationMap(productRecord);

		final ProductId productId = ProductId.ofRepoId(productRecord.getM_Product_ID());
		final UomId uomId = UomId.ofRepoId(productRecord.getC_UOM_ID());

		return JsonProduct.builder()
				.id(productId)
				.productNo(productRecord.getValue())
				.name(trls.getColumnTrl(I_M_Product.COLUMNNAME_Name, productRecord.getName()).translate(adLanguage))
				.description(trls.getColumnTrl(I_M_Product.COLUMNNAME_Description, productRecord.getDescription()).translate(adLanguage))
				.ean(productRecord.getUPC())
				.uom(servicesFacade.getUOMSymbol(uomId))
				.vendors(productVendors.get(productId))
				.createdUpdatedInfo(toCreatedUpdatedInfo(productRecord))
				.build();
	}

	private static DataEntryCreatedUpdatedInfo toCreatedUpdatedInfo(final I_M_Product productRecord)
	{
		return DataEntryCreatedUpdatedInfo.builder()
				.created(TimeUtil.asZonedDateTime(productRecord.getCreated()))
				.createdBy(UserId.optionalOfRepoId(productRecord.getCreatedBy()).orElse(UserId.SYSTEM))
				.updated(TimeUtil.asZonedDateTime(productRecord.getUpdated()))
				.updatedBy(UserId.optionalOfRepoId(productRecord.getUpdatedBy()).orElse(UserId.SYSTEM))
				.build();
	}

	private ImmutableListMultimap<ProductId, JsonProductVendor> retrieveJsonProductVendors(final Set<ProductId> productIds)
	{
		return servicesFacade.retrieveAllProductVendors(productIds)
				.stream()
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						record -> ProductId.ofRepoId(record.getM_Product_ID()),
						record -> toJsonProductVendor(record)));
	}

	private JsonProductVendor toJsonProductVendor(final I_C_BPartner_Product record)
	{
		final String vendorProductNo = CoalesceUtil.coalesceSuppliers(
				() -> record.getVendorProductNo(),
				() -> record.getProductNo());

		String vendorProductName = record.getProductName();

		return JsonProductVendor.builder()
				.vendorId(BPartnerId.ofRepoId(record.getC_BPartner_ID()))
				.productNo(vendorProductNo)
				.productName(vendorProductName)
				.currentVendor(record.isCurrentVendor())
				.build();
	}
}
