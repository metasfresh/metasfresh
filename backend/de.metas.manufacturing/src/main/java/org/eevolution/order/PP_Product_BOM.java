/*
 * #%L
 * de.metas.manufacturing
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

package org.eevolution.order;

import com.google.common.collect.ImmutableSet;
import de.metas.document.engine.DocStatus;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.trx.api.ITrxManager;
import org.compiere.model.ModelValidator;
import org.eevolution.api.BOMType;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.IProductBOMDAO;
import org.eevolution.api.ProductBOMId;
import org.eevolution.api.ProductBOMVersionsId;
import org.eevolution.model.I_PP_Product_BOM;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Interceptor(I_PP_Product_BOM.class)
public class PP_Product_BOM
{
	private final IProductBOMDAO productBOMDAO = Services.get(IProductBOMDAO.class);
	private final IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_COMPLETE })
	public void onComplete(@NonNull final I_PP_Product_BOM productBOMRecord)
	{
		final BOMType bomType = Optional.ofNullable(BOMType.ofNullableCode(productBOMRecord.getBOMType()))
				.orElse(BOMType.CurrentActive);

		final Optional<I_PP_Product_BOM> previousBOMVersion = productBOMDAO.getPreviousVersion(productBOMRecord,
																							   ImmutableSet.of(bomType), DocStatus.Completed);

		if (!previousBOMVersion.isPresent())
		{
			return;
		}

		final ProductBOMVersionsId versionsId = ProductBOMVersionsId.ofRepoId(productBOMRecord.getPP_Product_BOMVersions_ID());
		final ProductBOMId newProductBOMVersionId = ProductBOMId.ofRepoId(productBOMRecord.getPP_Product_BOM_ID());

		trxManager.runAfterCommit(() -> ppOrderBL.updateDraftedOrdersMatchingBOM(versionsId, newProductBOMVersionId));
	}
}
