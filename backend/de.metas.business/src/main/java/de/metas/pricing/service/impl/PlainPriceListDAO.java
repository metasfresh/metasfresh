package de.metas.pricing.service.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_DiscountSchemaLine;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_ProductPrice;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.pricing.PriceListId;
import de.metas.pricing.PriceListVersionId;
import de.metas.product.IProductDAO;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;

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

public class PlainPriceListDAO extends PriceListDAO
{

	@Override
	protected void createProductPricesForPLV(final UserId userId, @NonNull final PriceListVersionId newCustomerPLVId)
	{
		final I_M_PriceList_Version newCustomerPLV = getPriceListVersionById(newCustomerPLVId);

		final PriceListVersionId basePLVId = PriceListVersionId.ofRepoIdOrNull(newCustomerPLV.getM_Pricelist_Version_Base_ID());

		if (basePLVId == null)
		{
			// nothing to do
			return;
		}
<<<<<<< HEAD
		int discountSchemaId = newCustomerPLV.getM_DiscountSchema_ID();
=======
		final int discountSchemaId = newCustomerPLV.getM_DiscountSchema_ID();
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

		if (discountSchemaId <= 0)
		{
			// nothing to do
			return;
		}

		final Stream<I_M_ProductPrice> productPrices = retrieveProductPrices(basePLVId, ImmutableSet.of());

		final List<I_M_DiscountSchemaLine> schemaLines = getSchemaLines(discountSchemaId);

		productPrices.forEach(productPrice -> createProductPriceIfNeeded(productPrice, schemaLines, newCustomerPLV));

	}

	private void createProductPriceIfNeeded(final I_M_ProductPrice productPrice,
			final List<I_M_DiscountSchemaLine> schemaLines,
			final I_M_PriceList_Version newCustomerPLV)
	{
		final I_M_DiscountSchemaLine schemaLineForProductId = retrieveSchemaLineForProductIdOrNull(productPrice.getM_Product_ID(), schemaLines);

		if (schemaLineForProductId == null)
		{
			// don't create the product price
			return;
		}

		final I_M_ProductPrice newProductPrice = newInstance(I_M_ProductPrice.class);
		newProductPrice.setM_Product_ID(productPrice.getM_Product_ID());
		newProductPrice.setM_PriceList_Version_ID(newCustomerPLV.getM_PriceList_Version_ID());

		final BigDecimal price = productPrice.getPriceStd().add(schemaLineForProductId.getStd_AddAmt());
		newProductPrice.setPriceStd(price);

		save(newProductPrice);

	}

	private I_M_DiscountSchemaLine retrieveSchemaLineForProductIdOrNull(final int productId, final List<I_M_DiscountSchemaLine> schemaLines)
	{
		return schemaLines.stream()
				.filter(schemaLine -> productMatchesSchemaLine(productId, schemaLine))
				.findFirst()
				.orElse(null);
	}

<<<<<<< HEAD
	private boolean productMatchesSchemaLine(int productId, final I_M_DiscountSchemaLine schemaLine)
=======
	private boolean productMatchesSchemaLine(final int productId, final I_M_DiscountSchemaLine schemaLine)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		final IProductDAO productDAO = Services.get(IProductDAO.class);

		final boolean sameProduct = schemaLine.getM_Product_ID() == productId;

		if (sameProduct)
		{
			return true;
		}

		final boolean differentProduct = schemaLine.getM_Product_ID() > 0;

		if (differentProduct)
		{
			return false;
		}

		final I_M_Product product = productDAO.getById(productId);

<<<<<<< HEAD
		final boolean sameCategory = product.getM_Product_Category_ID() == schemaLine.getM_Product_Category_ID();

		return sameCategory;
=======
		return product.getM_Product_Category_ID() == schemaLine.getM_Product_Category_ID();
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	}

	private List<I_M_DiscountSchemaLine> getSchemaLines(final int discountSchemaId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		return queryBL.createQueryBuilder(I_M_DiscountSchemaLine.class)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(I_M_DiscountSchemaLine.COLUMNNAME_M_DiscountSchema_ID, discountSchemaId)
				.orderBy(I_M_DiscountSchemaLine.COLUMNNAME_SeqNo)
				.orderBy(I_M_DiscountSchemaLine.COLUMNNAME_M_DiscountSchemaLine_ID)
				.create()
				.list(I_M_DiscountSchemaLine.class);

	}

	@Override
	protected List<I_M_PriceList_Version> retrieveCustomPLVsToMutate(@NonNull final PriceListId basePricelistId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQuery<I_C_BPartner> customerQuery = queryBL.createQueryBuilder(I_C_BPartner.class)

				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BPartner.COLUMNNAME_IsCustomer, true)
				.addEqualsFilter(I_C_BPartner.COLUMNNAME_IsAllowPriceMutation, true)
				.create();

		final List<I_M_PriceList_Version> customerVersions = queryBL.createQueryBuilder(I_M_PriceList.class)
				.addEqualsFilter(I_M_PriceList.COLUMN_BasePriceList_ID, basePricelistId)

				.addInSubQueryFilter()
				.matchingColumnNames(I_M_PriceList.COLUMNNAME_M_PricingSystem_ID, I_C_BPartner.COLUMNNAME_M_PricingSystem_ID)
				.subQuery(customerQuery)
				.end()
<<<<<<< HEAD
				.andCollectChildren(I_M_PriceList_Version.COLUMN_M_PriceList_ID)
=======
				.andCollectChildren(I_M_PriceList_Version.COLUMNNAME_M_PriceList_ID, I_M_PriceList_Version.class)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
				.addOnlyActiveRecordsFilter()
				.addNotEqualsFilter(I_M_PriceList_Version.COLUMNNAME_M_PriceList_ID, basePricelistId)
				.create()

				.list(I_M_PriceList_Version.class);

<<<<<<< HEAD
		final ImmutableList<I_M_PriceList_Version> newestVersions = customerVersions.stream()
				.filter(version -> retrieveNextVersionOrNull(version, false) == null)
				.collect(ImmutableList.toImmutableList());

		return newestVersions;
=======
		return customerVersions.stream()
				.filter(version -> retrieveNextVersionOrNull(version, false) == null)
				.collect(ImmutableList.toImmutableList());
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

}
