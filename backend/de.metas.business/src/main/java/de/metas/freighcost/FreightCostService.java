package de.metas.freighcost;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.i18n.AdMessageKey;
import de.metas.location.CountryId;
import de.metas.logging.LogManager;
import de.metas.product.ProductId;
import de.metas.shipping.ShipperId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

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
 * S * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Service
public class FreightCostService
{
	private static final Logger logger = LogManager.getLogger(FreightCostService.class);

	private final FreightCostRepository freightCostRepo;

	private final static AdMessageKey MSG_Order_No_Shipper = AdMessageKey.of("C_Order_NoShipper");

	public FreightCostService(
			@NonNull final FreightCostRepository freightCostRepo)
	{
		this.freightCostRepo = freightCostRepo;
	}

	public boolean isFreightCostProduct(@NonNull final ProductId productId)
	{
		return freightCostRepo.existsByFreightCostProductId(productId);
	}

	public boolean checkIfFree(@NonNull final FreightCostContext context)
	{
		if (!context.getDeliveryViaRule().isShipper())
		{
			logger.debug("No freight cost because DeliveryViaRule is not shipper: ", context);
			return true;
		}

		if (context.getShipToBPartnerId() == null)
		{
			logger.debug("No freight cost because ShipToBPartner is not set: {}", context);
			return true;
		}

		if (context.getFreightCostRule() == FreightCostRule.FreightIncluded)
		{
			logger.debug("No freight cost because the freight is included: {}", context);
			return true;
		}

		if (context.getShipperId() == null)
		{
			logger.debug("No freightcost because no shipper is set: {}", context);
			return true;
		}

		return false;
	}

	/**
	 * Attempts to load the freight costs for a given business partner and location (actually the location's country).
	 * Looks at different freight cost records in this context:
	 * <ul>
	 * <li>Freight cost attached to the bParter</li>
	 * <li>Freight cost attached to the bParter's bPartnerGroup</li>
	 * <li>Default freight cost</li>
	 * </ul>
	 *
	 * @throws AdempiereException if there is no freight cost record for the given inOut
	 */
	public FreightCost findBestMatchingFreightCost(final FreightCostContext context)
	{

		final BPartnerId shipToBPartnerId = context.getShipToBPartnerId();
		if (shipToBPartnerId == null)
		{
			throw new AdempiereException("ShipToBPartner not set");
		}

		final CountryId shipToCountryId = context.getShipToCountryId();
		if (shipToCountryId == null)
		{
			throw new AdempiereException("ShipToCountryId not set");
		}

		final ShipperId shipperId = context.getShipperId();
		if (shipperId == null)
		{
			throw new AdempiereException(MSG_Order_No_Shipper);
		}

		//
		//
		final FreightCost freightCost = getFreightCostByBPartnerId(shipToBPartnerId);
		final FreightCostShipper shipper = freightCost.getShipper(shipperId, context.getDate());
		if (!shipper.isShipToCountry(shipToCountryId))
		{
			throw new AdempiereException("@NotFound@ @M_FreightCost_ID@ (@M_Shipper_ID@:" + shipperId + ", @C_Country_ID@:" + shipToCountryId + ")");
		}

		return freightCost;
	}

	public FreightCost getFreightCostByBPartnerId(final BPartnerId bpartnerId)
	{
		final IBPartnerBL bpartnerBL = Services.get(IBPartnerBL.class);

		final FreightCostId bpFreightCostId = FreightCostId.ofRepoIdOrNull(bpartnerBL.getFreightCostIdByBPartnerId(bpartnerId));
		if (bpFreightCostId != null)
		{
			return freightCostRepo.getById(bpFreightCostId);
		}

		final FreightCost defaultFreightCost = freightCostRepo.getDefaultFreightCost().orElse(null);
		if (defaultFreightCost != null)
		{
			return defaultFreightCost;
		}

		throw new AdempiereException("@NotFound@ @M_FreightCost_ID@: " + bpartnerId);
	}
}
