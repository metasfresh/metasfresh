/*
 * #%L
 * de.metas.manufacturing
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

package org.eevolution.model.validator;

import de.metas.i18n.AdMessageKey;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.ModelValidator;
import org.eevolution.api.IProductBOMDAO;
import org.eevolution.api.ProductBOMId;
import org.eevolution.api.ProductBOMVersionsId;
import org.eevolution.model.I_PP_Product_BOMVersions;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Interceptor(I_PP_Product_BOMVersions.class)
@Component
public class PP_Product_BOMVersions
{
	private final IProductBOMDAO bomsRepo = Services.get(IProductBOMDAO.class);

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = { I_PP_Product_BOMVersions.COLUMNNAME_M_Product_ID })
	public void validateProductChange(final I_PP_Product_BOMVersions bomVersions)
	{
		final ProductBOMVersionsId bomVersionsId = ProductBOMVersionsId.ofRepoId(bomVersions.getPP_Product_BOMVersions_ID());

		final Optional<ProductBOMId> productBomId = bomsRepo.getLatestBOMByVersion(bomVersionsId);

		if (productBomId.isPresent())
		{
			throw new AdempiereException(AdMessageKey.of("PP_Product_BOMVersions_BOMs_Already_Linked"))
					.markAsUserValidationError();
		}
	}
}
