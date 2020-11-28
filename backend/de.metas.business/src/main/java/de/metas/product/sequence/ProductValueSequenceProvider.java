package de.metas.product.sequence;

import static org.adempiere.model.InterfaceWrapperHelper.create;
import static org.adempiere.model.InterfaceWrapperHelper.isInstanceOf;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import lombok.NonNull;

import java.util.HashSet;
import java.util.Set;

import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.springframework.stereotype.Component;

import de.metas.cache.CCache;
import de.metas.document.DocumentSequenceInfo;
import de.metas.document.IDocumentSequenceDAO;
import de.metas.document.sequence.ValueSequenceInfoProvider;
import de.metas.product.ProductCategoryId;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Component
public class ProductValueSequenceProvider implements ValueSequenceInfoProvider
{
	private final CCache<ProductCategoryId, Integer> productCategoryId2AD_Sequence_ID = CCache.newCache(I_M_Product_Category.Table_Name, 10, CCache.EXPIREMINUTES_Never);

	@Override
	public ProviderResult computeValueInfo(@NonNull final Object modelRecord)
	{
		if (!isInstanceOf(modelRecord, I_M_Product.class))
		{
			return ProviderResult.EMPTY;
		}
		final I_M_Product product = create(modelRecord, I_M_Product.class);

		final ProductCategoryId productCategoryId = ProductCategoryId.ofRepoId(product.getM_Product_Category_ID());

		final int adSequenceId = getSequenceId(productCategoryId);
		if (adSequenceId <= 0)
		{
			return ProviderResult.EMPTY;
		}

		final IDocumentSequenceDAO documentSequenceDAO = Services.get(IDocumentSequenceDAO.class);
		final DocumentSequenceInfo documentSequenceInfo = documentSequenceDAO.retriveDocumentSequenceInfo(adSequenceId);

		return ProviderResult.of(documentSequenceInfo);
	}

	private int getSequenceId(@NonNull final ProductCategoryId productCategoryId)
	{
		return productCategoryId2AD_Sequence_ID.getOrLoad(
				productCategoryId,
				() -> extractSequenceId(productCategoryId, new HashSet<>()));
	}

	private int extractSequenceId(
			@NonNull final ProductCategoryId productCategoryId,
			@NonNull final Set<ProductCategoryId> seenIds)
	{
		final I_M_Product_Category productCategory = loadOutOfTrx(productCategoryId, I_M_Product_Category.class);

		final int adSequenceId = productCategory.getAD_Sequence_ProductValue_ID();
		if (adSequenceId > 0)
		{
			// return our result
			return adSequenceId;
		}

		if (productCategory.getM_Product_Category_Parent_ID() > 0)
		{
			final ProductCategoryId productCategoryParentId = ProductCategoryId.ofRepoId(productCategory.getM_Product_Category_Parent_ID());

			// first, guard against a loop
			if (!seenIds.add(productCategoryParentId))
			{
				// there is no AD_Sequence_ID for us
				return -1;
			}

			// recurse
			return extractSequenceId(productCategoryParentId, seenIds);
		}

		// there is no AD_Sequence_ID for us
		return -1;
	}

}
