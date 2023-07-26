package de.metas.rest_api.v1.product.command;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Product_Category;

import com.google.common.collect.ImmutableList;

import de.metas.i18n.IModelTranslationMap;
import de.metas.product.ProductCategoryId;
import de.metas.rest_api.v1.product.ProductsServicesFacade;
import de.metas.rest_api.product.response.JsonGetProductCategoriesResponse;
import de.metas.rest_api.product.response.JsonProductCategory;
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

public class GetProductCategoriesCommand
{
	private final ProductsServicesFacade servicesFacade;
	private String adLanguage;

	@Builder(buildMethodName = "_build")
	private GetProductCategoriesCommand(
			@NonNull final ProductsServicesFacade servicesFacade,
			@NonNull final String adLanguage)
	{
		this.servicesFacade = servicesFacade;
		this.adLanguage = adLanguage;
	}

	public static class GetProductCategoriesCommandBuilder
	{
		public JsonGetProductCategoriesResponse execute()
		{
			return _build().execute();
		}
	}

	public JsonGetProductCategoriesResponse execute()
	{
		final ImmutableList<JsonProductCategory> productCategories = servicesFacade.streamAllProductCategories()
				.map(this::toJsonProductCategory)
				.collect(ImmutableList.toImmutableList());

		return JsonGetProductCategoriesResponse.builder()
				.productCategories(productCategories)
				.build();
	}

	private JsonProductCategory toJsonProductCategory(final I_M_Product_Category record)
	{
		final IModelTranslationMap trls = InterfaceWrapperHelper.getModelTranslationMap(record);

		return JsonProductCategory.builder()
				.id(ProductCategoryId.ofRepoId(record.getM_Product_Category_ID()))
				.value(record.getValue())
				.name(trls.getColumnTrl(I_M_Product_Category.COLUMNNAME_Name, record.getName()).translate(adLanguage))
				.description(trls.getColumnTrl(I_M_Product_Category.COLUMNNAME_Description, record.getDescription()).translate(adLanguage))
				.parentProductCategoryId(ProductCategoryId.ofRepoIdOrNull(record.getM_Product_Category_Parent_ID()))
				.defaultCategory(record.isDefault())
				.createdUpdatedInfo(servicesFacade.extractCreatedUpdatedInfo(record))
				.build();
	}
}
