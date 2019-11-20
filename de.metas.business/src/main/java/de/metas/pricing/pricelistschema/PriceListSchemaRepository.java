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

package de.metas.pricing.pricelistschema;

import de.metas.pricing.conditions.PricingConditionsId;
import de.metas.pricing.conditions.service.CreatePriceListSchemaRequest;
import de.metas.util.Services;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_DiscountSchemaLine;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;

@Repository
public class PriceListSchemaRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public void createPriceListSchemaLine(@NonNull final CreatePriceListSchemaRequest request)
	{
		final I_M_DiscountSchemaLine schemaLine = InterfaceWrapperHelper.newInstance(I_M_DiscountSchemaLine.class);
		schemaLine.setM_DiscountSchema_ID(getRepoIdOrDefault(request.getDiscountSchemaId()));
		schemaLine.setSeqNo(request.getSeqNo());
		schemaLine.setM_Product_ID(getRepoIdOrDefault(request.getProductId()));
		schemaLine.setM_Product_Category_ID(getRepoIdOrDefault(request.getProductCategoryId()));
		schemaLine.setC_BPartner_ID(getRepoIdOrDefault(request.getBPartnerId()));
		schemaLine.setC_TaxCategory_ID(getRepoIdOrDefault(request.getTaxCategoryId()));
		schemaLine.setStd_AddAmt(request.getStd_AddAmt());
		schemaLine.setStd_Rounding(request.getStd_Rounding());
		schemaLine.setC_TaxCategory_Target_ID(getRepoIdOrDefault(request.getTaxCategoryTargetId()));

		schemaLine.setConversionDate(request.getConversionDate());
		schemaLine.setList_Base(request.getList_Base());

		schemaLine.setC_ConversionType_ID(getRepoIdOrDefault(request.getC_ConversionType_ID()));
		schemaLine.setLimit_AddAmt(request.getLimit_AddAmt());
		schemaLine.setLimit_Base(request.getLimit_Base());
		schemaLine.setLimit_Discount(request.getLimit_Discount());
		schemaLine.setLimit_MaxAmt(request.getLimit_MaxAmt());
		schemaLine.setLimit_MinAmt(request.getLimit_MinAmt());
		schemaLine.setLimit_Rounding(request.getLimit_Rounding());

		schemaLine.setList_AddAmt(request.getList_AddAmt());
		schemaLine.setList_Discount(request.getList_Discount());
		schemaLine.setList_MaxAmt(request.getList_MaxAmt());
		schemaLine.setList_MinAmt(request.getList_MinAmt());
		schemaLine.setList_Rounding(request.getList_Rounding());

		schemaLine.setStd_Base(request.getStd_Base());
		schemaLine.setStd_Discount(request.getStd_Discount());
		schemaLine.setStd_MaxAmt(request.getStd_MaxAmt());
		schemaLine.setStd_MinAmt(request.getStd_MinAmt());

		InterfaceWrapperHelper.save(schemaLine);
	}

	private int getRepoIdOrDefault(@Nullable final RepoIdAware repoId)
	{
		if (repoId == null)
		{
			return 0;
		}
		return repoId.getRepoId();
	}

	public boolean hasAnyLines(final PricingConditionsId priceListSchemaId)
	{
		return queryBL.createQueryBuilder(I_M_DiscountSchemaLine.class)
				.addEqualsFilter(I_M_DiscountSchemaLine.COLUMNNAME_M_DiscountSchema_ID, priceListSchemaId)
				.addOnlyActiveRecordsFilter()
				.create()
				.anyExist();
	}
}
