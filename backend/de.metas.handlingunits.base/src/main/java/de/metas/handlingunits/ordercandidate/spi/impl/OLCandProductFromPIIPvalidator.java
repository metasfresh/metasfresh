/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.handlingunits.ordercandidate.spi.impl;

import ch.qos.logback.classic.Level;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.logging.LogManager;
import de.metas.ordercandidate.api.IOLCandEffectiveValuesBL;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.ordercandidate.spi.IOLCandValidator;
import de.metas.product.ProductId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class OLCandProductFromPIIPvalidator implements IOLCandValidator
{
	private final static transient Logger logger = LogManager.getLogger(OLCandProductFromPIIPvalidator.class);

	private final IOLCandEffectiveValuesBL olCandEffectiveValuesBL = Services.get(IOLCandEffectiveValuesBL.class);

	@Override
	public int getSeqNo()
	{
		return 10;
	}

	@Override
	public void validate(@NonNull final I_C_OLCand olCand)
	{
		final ProductId productId = olCandEffectiveValuesBL.getM_Product_Effective_ID(olCand);

		final I_M_HU_PI_Item_Product huPIItemProduct = OLCandPIIPUtil.extractHUPIItemProductOrNull(olCand);
		if (huPIItemProduct != null)
		{
			if (productId == null)
			{
				Loggables.withLogger(logger, Level.DEBUG).addLog("Supplement missing C_OLCand.M_Product_ID = {} from M_HU_PI_Item_Product_ID={}", huPIItemProduct.getM_Product_ID(), huPIItemProduct.getM_HU_PI_Item_Product_ID());
				olCand.setM_Product_ID(huPIItemProduct.getM_Product_ID());
			}
			else if (productId.getRepoId() != huPIItemProduct.getM_Product_ID())
			{
				throw new AdempiereException("Effective C_OLCand.M_Product_ID is inconsistent with effective C_OLCand.M_HU_PI_Item_Product.M_Product_ID")
						.appendParametersToMessage()
						.setParameter("C_OLCand.M_Product_ID (eff)", productId.getRepoId())
						.setParameter("C_OLCand.M_HU_PI_Item_Product.M_Product_ID (eff)", huPIItemProduct.getM_Product_ID());
			}
		}
	}
}
