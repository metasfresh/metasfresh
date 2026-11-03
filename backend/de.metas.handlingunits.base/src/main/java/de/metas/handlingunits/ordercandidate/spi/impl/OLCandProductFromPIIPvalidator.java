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
import de.metas.bpartner.BPartnerId;
import de.metas.common.util.CoalesceUtil;
import de.metas.gs1.GTIN;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.ProductAndHUPIItemProductId;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.logging.LogManager;
import de.metas.ordercandidate.api.IOLCandEffectiveValuesBL;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.ordercandidate.spi.IOLCandValidator;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class OLCandProductFromPIIPvalidator implements IOLCandValidator
{
	private final static transient Logger logger = LogManager.getLogger(OLCandProductFromPIIPvalidator.class);

	@NonNull private final IOLCandEffectiveValuesBL olCandEffectiveValuesBL = Services.get(IOLCandEffectiveValuesBL.class);
	@NonNull private final IHUPIItemProductDAO huPIItemProductDAO = Services.get(IHUPIItemProductDAO.class);

	@Override
	public int getSeqNo()
	{
		return 10;
	}

	@Override
	public void validate(@NonNull final I_C_OLCand olCand)
	{
		resolveValidPackingInstructionForDatePromised(olCand);

		final ProductId productId = olCandEffectiveValuesBL.getM_Product_Effective_ID(olCand);

		final I_M_HU_PI_Item_Product huPIItemProduct = OLCandPIIPUtil.extractHUPIItemProductOrNull(olCand);
		if (huPIItemProduct == null)
		{
			return;
		}
		final boolean virtualHU = HUPIItemProductId.ofRepoId(huPIItemProduct.getM_HU_PI_Item_Product_ID()).isVirtualHU();
		if (virtualHU)
		{
			return;
		}

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

	/**
	 * Re-resolves the (non-override) packing instruction to the one valid on the OLCand's effective
	 * {@code DatePromised}. The EDI-XML import stamps {@code M_HU_PI_Item_Product_ID} via a date-blind
	 * barcode-lookup view that picks the newest-created row regardless of its validity window, so a
	 * future-dated instruction can otherwise be applied before its start date. This validator has the
	 * lowest {@link #getSeqNo() seqNo}, so the corrected instruction is in place before pricing reads it.
	 * <p>
	 * A user-set {@code M_HU_PI_Item_Product_Override_ID} is never touched. Idempotent for the REST path
	 * (already resolved on {@code DatePromised}); a no-op when no row is valid on the date.
	 */
	private void resolveValidPackingInstructionForDatePromised(@NonNull final I_C_OLCand olCand)
	{
		if (HUPIItemProductId.ofRepoIdOrNull(olCand.getM_HU_PI_Item_Product_Override_ID()) != null)
		{
			return; // an explicit override is the user's choice — do not re-resolve it
		}

		final HUPIItemProductId currentId = HUPIItemProductId.ofRepoIdOrNull(olCand.getM_HU_PI_Item_Product_ID());
		if (currentId == null || currentId.isVirtualHU())
		{
			return;
		}

		final I_M_HU_PI_Item_Product current = huPIItemProductDAO.getRecordById(currentId);
		final String barcode = CoalesceUtil.firstNotBlank(current.getGTIN(), current.getEAN_TU(), current.getUPC());
		if (Check.isBlank(barcode))
		{
			return; // no barcode to re-resolve by — nothing date-dependent to do (and no DatePromised needed)
		}

		final ZonedDateTime datePromised = olCandEffectiveValuesBL.getDatePromised_Effective(olCand);

		// Re-resolve to the LATEST packing instruction valid on DatePromised, staying within the SAME business
		// partner the barcode-lookup view already resolved — i.e. the current row's own C_BPartner_ID, which
		// the view derived from the incoming StoreGLN. This preserves the per-partner selection when a barcode
		// is shared across partners (re-deriving the order's ship partner can differ when partners share a
		// location), while still switching to a newer version once its ValidFrom is reached: "current is valid"
		// is not enough — a superseded row stays valid forever without a ValidTo, so we always ask for the
		// latest valid version and switch only if it differs (the filter below skips the no-op write).
		//
		// currentPartner == null means the stamped row is a generic (partner-less) one; findFirstByGtin then
		// re-resolves across any partner (its pre-existing behaviour). In the EDI flow this path is not
		// reached: the barcode-lookup view always stamps the partner-specific row derived from the StoreGLN,
		// so a non-virtual generic incumbent does not occur here (the virtual fallback returned above).
		final BPartnerId currentPartner = BPartnerId.ofRepoIdOrNull(current.getC_BPartner_ID());

		huPIItemProductDAO.findFirstByGtin(GTIN.ofString(barcode), currentPartner, datePromised)
				.map(ProductAndHUPIItemProductId::getHupiItemProductId)
				.filter(validId -> !HUPIItemProductId.equals(validId, currentId))
				.ifPresent(validId -> olCand.setM_HU_PI_Item_Product_ID(validId.getRepoId()));
	}
}
