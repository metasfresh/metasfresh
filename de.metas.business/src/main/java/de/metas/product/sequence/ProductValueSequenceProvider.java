package de.metas.product.sequence;

import static org.adempiere.model.InterfaceWrapperHelper.create;
import static org.adempiere.model.InterfaceWrapperHelper.isInstanceOf;

import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.springframework.stereotype.Component;

import de.metas.document.DocumentSequenceInfo;
import de.metas.document.IDocumentSequenceDAO;
import de.metas.document.sequence.ValueSequenceInfoProvider;
import de.metas.util.Services;
import lombok.NonNull;

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
	@Override
	public ProviderResult computeValueInfo(@NonNull final Object modelRecord)
	{
		if (!isInstanceOf(modelRecord, I_M_Product.class))
		{
			return ProviderResult.EMPTY;
		}
		final I_M_Product product = create(modelRecord, I_M_Product.class);

		final I_M_Product_Category productCategory = product.getM_Product_Category();
		final int adSequenceId = productCategory.getAD_Sequence_ProductValue_ID();
		if (adSequenceId <= 0)
		{
			return ProviderResult.EMPTY;
		}

		final IDocumentSequenceDAO documentSequenceDAO = Services.get(IDocumentSequenceDAO.class);
		final DocumentSequenceInfo documentSequenceInfo = documentSequenceDAO.retriveDocumentSequenceInfo(adSequenceId);

		return ProviderResult.of(documentSequenceInfo);
	}
}
