/*
 * #%L
 * de-metas-common-product
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.common.product.v2.request;

import com.google.common.collect.ImmutableList;
import de.metas.common.pricing.v2.productprice.TaxCategory;
import de.metas.common.rest_api.v2.SyncAdvise;
import lombok.NonNull;

import java.time.Instant;
import java.util.Collections;

public class JsonRequestUtil
{
	public static JsonRequestBPartnerProductUpsert getJsonRequestBPartnerProductUpsert()
	{
		final JsonRequestBPartnerProductUpsert jsonRequestBPartnerProductUpsert = new JsonRequestBPartnerProductUpsert();

		jsonRequestBPartnerProductUpsert.setBpartnerIdentifier("test");
		jsonRequestBPartnerProductUpsert.setActive(true);
		jsonRequestBPartnerProductUpsert.setSeqNo(10);
		jsonRequestBPartnerProductUpsert.setProductNo("test");
		jsonRequestBPartnerProductUpsert.setDescription("test");
		jsonRequestBPartnerProductUpsert.setCuEAN("test");
		jsonRequestBPartnerProductUpsert.setGtin("test");
		jsonRequestBPartnerProductUpsert.setCustomerLabelName("test");
		jsonRequestBPartnerProductUpsert.setIngredients("test");
		jsonRequestBPartnerProductUpsert.setCurrentVendor(true);
		jsonRequestBPartnerProductUpsert.setExcludedFromSales(true);
		jsonRequestBPartnerProductUpsert.setCurrentVendor(true);

		return jsonRequestBPartnerProductUpsert;
	}

	public static JsonRequestProduct getJsonRequestProduct()
	{
		final JsonRequestProduct jsonRequestProduct = new JsonRequestProduct();

		jsonRequestProduct.setCode("test");
		jsonRequestProduct.setActive(true);
		jsonRequestProduct.setName("test");
		jsonRequestProduct.setType(JsonRequestProduct.Type.ITEM);
		jsonRequestProduct.setDescription("test");
		jsonRequestProduct.setEan("test");
		jsonRequestProduct.setGtin("test");
		jsonRequestProduct.setUomCode("test");
		jsonRequestProduct.setDiscontinued(true);
		jsonRequestProduct.setStocked(true);
		jsonRequestProduct.setProductCategoryIdentifier("test");
		jsonRequestProduct.setSyncAdvise(SyncAdvise.CREATE_OR_MERGE);
		jsonRequestProduct.setBpartnerProductItems(Collections.singletonList(getJsonRequestBPartnerProductUpsert()));
		jsonRequestProduct.setSAPProductHierarchy("SAPProductHierarchy");

		return jsonRequestProduct;
	}

	public static JsonRequestProductUpsert getJsonRequestProductUpsert()
	{
		return JsonRequestProductUpsert.builder()
				.requestItem(getJsonRequestProductUpsertItem())
				.syncAdvise(SyncAdvise.CREATE_OR_MERGE)
				.build();
	}

	public static JsonRequestProductUpsertItem getJsonRequestProductUpsertItem()
	{
		return JsonRequestProductUpsertItem.builder()
				.requestProduct(getJsonRequestProduct())
				.productIdentifier("test")
				.build();
	}

	@NonNull
	public static JsonRequestProductTaxCategoryUpsert getJsonRequestProductTaxCategoryUpsert()
	{
		final JsonRequestProductTaxCategoryUpsert jsonRequestProductTaxCategoryUpsert = new JsonRequestProductTaxCategoryUpsert();
		
		jsonRequestProductTaxCategoryUpsert.setTaxCategory(TaxCategory.NORMAL.getInternalName());
		jsonRequestProductTaxCategoryUpsert.setCountryCode("DE");
		jsonRequestProductTaxCategoryUpsert.setValidFrom(Instant.parse("2019-11-22T00:00:00Z"));

		return jsonRequestProductTaxCategoryUpsert;
	}

	@NonNull
	public static JsonRequestProductWarehouseAssignmentSave getJsonRequestWarehouseAssignmentUpsert()
	{
		return JsonRequestProductWarehouseAssignmentSave.builder()
				.warehouseIdentifiers(ImmutableList.of("name"))
				.syncAdvise(SyncAdvise.REPLACE)
				.build();
	}
}
