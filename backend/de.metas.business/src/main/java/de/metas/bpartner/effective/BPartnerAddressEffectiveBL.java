/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.bpartner.effective;

import com.google.common.annotations.VisibleForTesting;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.common.util.CoalesceUtil;
import de.metas.document.location.adapter.DocumentLocationAdaptersRegistry;
import de.metas.document.location.adapter.IDocumentDeliveryLocationAdapter;
import de.metas.document.location.adapter.IDocumentLocationAdapter;
import de.metas.shipping.ShipperId;
import de.metas.util.Services;
import de.metas.util.StringUtils;

import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner_Location;
import org.springframework.stereotype.Service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.function.Function;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class BPartnerAddressEffectiveBL
{
	@NonNull private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	@NonNull private final BPartnerEffectiveBL bpartnerEffectiveBL;
	@NonNull private final DocumentLocationAdaptersRegistry documentLocationAdaptersRegistry;

	@VisibleForTesting
	public static BPartnerAddressEffectiveBL newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		//noinspection DataFlowIssue
		return SpringContextHolder.getBeanOrSupply(BPartnerAddressEffectiveBL.class, () -> new BPartnerAddressEffectiveBL(
				BPartnerEffectiveBL.newInstanceForUnitTesting(),
				new DocumentLocationAdaptersRegistry(Collections.emptyList())));
	}

	/**
	 * Resolves effective address values for any document model using the universal adapter registry.
	 */
	@NonNull
	public BPartnerAddressEffective getDeliveryEffective(@NonNull final Object documentRecord)
	{
		final IDocumentLocationAdapter mainAdapter = documentLocationAdaptersRegistry
				.getDocumentLocationAdapterIfHandled(documentRecord)
				.orElse(null);
		if (mainAdapter == null)
		{
			return BPartnerAddressEffective.builder().build();
		}

		final BPartnerLocationId mainLocationId = mainAdapter.toDocumentLocation().getBpartnerLocationId();
		if (mainLocationId == null)
		{
			return BPartnerAddressEffective.builder().build();
		}

		final BPartnerLocationId dropShipLocationId = documentLocationAdaptersRegistry
				.getDocumentDeliveryLocationAdapter(documentRecord)
				.filter(IDocumentDeliveryLocationAdapter::isDropShip)
				.map(a -> BPartnerLocationId.ofRepoIdOrNull(a.getDropShip_BPartner_ID(), a.getDropShip_Location_ID()))
				.orElse(null);

		return getDeliveryEffective(dropShipLocationId, mainLocationId);
	}

	@NonNull
	public BPartnerAddressEffective getDeliveryEffective(
			@Nullable final BPartnerLocationId dropShipLocationId,
			@NonNull final BPartnerLocationId locationId)
	{
		final BPartnerLocationId effectiveLocationId = dropShipLocationId != null ? dropShipLocationId : locationId;
		final BPartnerEffective effectivePartner = bpartnerEffectiveBL.getById(effectiveLocationId.getBpartnerId());

		return BPartnerAddressEffective.builder()
				.shipperId(resolveLocationThenPartner(effectiveLocationId,
						loc -> ShipperId.ofRepoIdOrNull(loc.getM_Shipper_ID()),
						effectivePartner::getShipperId))
				.isPreAdviceRequired(Boolean.TRUE.equals(resolveLocationThenPartner(effectiveLocationId,
						loc -> StringUtils.toBoolean(loc.getIsPreAdviceRequired(), null),
						effectivePartner::isPreAdviceRequired)))
				.build();
	}

	/**
	 * Loads the location by ID (fails fast if not found), then returns the first non-null value
	 * from the location record (via {@code locationValueExtractor}), falling back to the partner supplier.
	 */
	@Nullable
	private <T> T resolveLocationThenPartner(
			@NonNull final BPartnerLocationId locationId,
			@NonNull final Function<I_C_BPartner_Location, T> locationValueExtractor,
			@NonNull final Supplier<T> partnerValueSupplier)
	{
		final I_C_BPartner_Location location = bpartnerDAO.getBPartnerLocationByIdEvenInactiveNotNull(locationId);
		return CoalesceUtil.coalesceSuppliers(
				() -> locationValueExtractor.apply(location),
				partnerValueSupplier);
	}
}
