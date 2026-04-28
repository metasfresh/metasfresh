/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2024 metas GmbH
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

import de.metas.common.pricing.v2.productprice.TaxCategory;
import de.metas.tax.api.TaxCategoryId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_TaxCategory;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.load;

@Repository
public class TaxCategoryDAO
{
	@NonNull
	public TaxCategory getTaxCategory(@NonNull final TaxCategoryId id)
	{
		final I_C_TaxCategory taxCategoryRecord = Optional.ofNullable(load(id, I_C_TaxCategory.class))
				.orElseThrow(() -> new AdempiereException("No Tax Category found for ID!")
						.appendParametersToMessage()
						.setParameter("TaxCategoryId", id));

		return Optional.ofNullable(taxCategoryRecord.getInternalName())
				.map(TaxCategory::ofInternalName)
				.orElseThrow(() -> new AdempiereException("Tax Category has no internal name !")
						.appendParametersToMessage()
						.setParameter("TaxCategoryId", id));
	}
}
