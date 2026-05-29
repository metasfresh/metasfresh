/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.pricing.tax;

import de.metas.location.CountryId;
import de.metas.product.ProductId;
import de.metas.tax.api.TaxCategoryId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_Product_TaxCategory;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.stream.Stream;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class ProductTaxCategoryRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	public Optional<TaxCategoryId> getTaxCategoryId(@NonNull final LookupTaxCategoryRequest lookupTaxCategoryRequest)
	{
		return queryBL
				.createQueryBuilder(I_M_Product_TaxCategory.class)
				.addOnlyActiveRecordsFilter()
				.filter(getCountryFilter(lookupTaxCategoryRequest))
				.addEqualsFilter(I_M_Product_TaxCategory.COLUMNNAME_M_Product_ID, lookupTaxCategoryRequest.getProductId())
				.addCompareFilter(I_M_Product_TaxCategory.COLUMNNAME_ValidFrom, CompareQueryFilter.Operator.LESS_OR_EQUAL, lookupTaxCategoryRequest.getTargetDate())
				.orderBy()
				.addColumnDescending(I_M_Product_TaxCategory.COLUMNNAME_C_Country_ID)
				.addColumnDescending(I_M_Product_TaxCategory.COLUMNNAME_ValidFrom)
				.endOrderBy()
				.create()
				.firstOptional(I_M_Product_TaxCategory.class)
				.map(I_M_Product_TaxCategory::getC_TaxCategory_ID)
				.map(TaxCategoryId::ofRepoId);
	}

	@NonNull
	public Stream<ProductTaxCategory> findAllFor(@NonNull final LookupTaxCategoryRequest lookupTaxCategoryRequest)
	{
		return queryBL
				.createQueryBuilder(I_M_Product_TaxCategory.class)
				.addOnlyActiveRecordsFilter()
				.filter(getCountryFilter(lookupTaxCategoryRequest))
				.addEqualsFilter(I_M_Product_TaxCategory.COLUMNNAME_M_Product_ID, lookupTaxCategoryRequest.getProductId())
				.addCompareFilter(I_M_Product_TaxCategory.COLUMNNAME_ValidFrom, CompareQueryFilter.Operator.LESS_OR_EQUAL, lookupTaxCategoryRequest.getTargetDate())
				.create()
				.stream(I_M_Product_TaxCategory.class)
				.map(ProductTaxCategoryRepository::ofRecord);
	}

	@NonNull
	public Optional<ProductTaxCategory> getProductTaxCategoryByUniqueKey(
			@NonNull final ProductId productId,
			@NonNull final CountryId countryId)
	{
		return queryBL
				.createQueryBuilder(I_M_Product_TaxCategory.class)
				.addEqualsFilter(I_M_Product_TaxCategory.COLUMNNAME_M_Product_ID, productId)
				.addEqualsFilter(I_M_Product_TaxCategory.COLUMNNAME_C_Country_ID, countryId)
				.create()
				.firstOnlyOptional(I_M_Product_TaxCategory.class)
				.map(ProductTaxCategoryRepository::ofRecord);
	}

	public void save(@NonNull final ProductTaxCategory request)
	{
		final I_M_Product_TaxCategory record = toRecord(request);
		saveRecord(record);
	}

	@NonNull
	public ProductTaxCategory createProductTaxCategory(@NonNull final CreateProductTaxCategoryRequest request)
	{
		final I_M_Product_TaxCategory productTaxCategoryRecord = newInstance(I_M_Product_TaxCategory.class);

		productTaxCategoryRecord.setM_Product_ID(request.getProductId().getRepoId());
		productTaxCategoryRecord.setC_TaxCategory_ID(request.getTaxCategoryId().getRepoId());
		productTaxCategoryRecord.setC_Country_ID(request.getCountryId().getRepoId());
		productTaxCategoryRecord.setValidFrom(TimeUtil.asTimestamp(request.getValidFrom()));
		productTaxCategoryRecord.setIsActive(request.isActive());

		saveRecord(productTaxCategoryRecord);

		return ofRecord(productTaxCategoryRecord);
	}

	@NonNull
	private static ProductTaxCategory ofRecord(@NonNull final I_M_Product_TaxCategory productTaxCategory)
	{
		return ProductTaxCategory.builder()
				.productTaxCategoryId(ProductTaxCategoryId.ofRepoId(productTaxCategory.getM_Product_TaxCategory_ID()))
				.productId(ProductId.ofRepoId(productTaxCategory.getM_Product_ID()))
				.taxCategoryId(TaxCategoryId.ofRepoId(productTaxCategory.getC_TaxCategory_ID()))
				.validFrom(TimeUtil.asInstantNonNull(productTaxCategory.getValidFrom()))
				.countryId(CountryId.ofRepoIdOrNull(productTaxCategory.getC_Country_ID()))
				.active(productTaxCategory.isActive())
				.build();
	}

	@NonNull
	private IQueryFilter<I_M_Product_TaxCategory> getCountryFilter(@NonNull final LookupTaxCategoryRequest lookupTaxCategoryRequest)
	{
		final ICompositeQueryFilter<I_M_Product_TaxCategory> taxCategoryCountryFilter = queryBL
				.createCompositeQueryFilter(I_M_Product_TaxCategory.class)
				.setJoinOr()
				.addEqualsFilter(I_M_Product_TaxCategory.COLUMNNAME_C_Country_ID, lookupTaxCategoryRequest.getCountryId());

		if (lookupTaxCategoryRequest.getCountryId() != null)
		{
			taxCategoryCountryFilter.addEqualsFilter(I_M_Product_TaxCategory.COLUMNNAME_C_Country_ID, null);
		}

		return taxCategoryCountryFilter;
	}
	
	@NonNull
	private I_M_Product_TaxCategory toRecord(@NonNull final ProductTaxCategory productTaxCategory)
	{
		final I_M_Product_TaxCategory record = Optional.ofNullable(getRecordById(productTaxCategory.getProductTaxCategoryId()))
						.orElseThrow(() -> new AdempiereException("No Product Tax Category found for ID!")
								.appendParametersToMessage()
								.setParameter("ProductTaxCategoryId", productTaxCategory.getProductTaxCategoryId()));

		record.setValidFrom(TimeUtil.asTimestamp(productTaxCategory.getValidFrom()));
		record.setC_Country_ID(CountryId.toRepoId(productTaxCategory.getCountryId()));
		record.setM_Product_ID(ProductId.toRepoId(productTaxCategory.getProductId()));
		record.setC_TaxCategory_ID(TaxCategoryId.toRepoId(productTaxCategory.getTaxCategoryId()));
		record.setIsActive(productTaxCategory.isActive());

		return record;
	}

	@Nullable
	private I_M_Product_TaxCategory getRecordById(@NonNull final ProductTaxCategoryId id)
	{
		return load(id, I_M_Product_TaxCategory.class);
	}
}
