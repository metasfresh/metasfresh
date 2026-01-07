/*
 * #%L
 * de.metas.contracts
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

package de.metas.contracts.location;

import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_SubscriptionProgress;
import de.metas.document.location.DocumentLocation;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ContractLocationHelper
{
	@NonNull
	public static DocumentLocation extractBillLocation(@NonNull final I_C_Flatrate_Term contract)
	{
		final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(contract.getBill_BPartner_ID());

		final BPartnerLocationAndCaptureId billToLocationId = extractBillToLocationId(contract);

		return DocumentLocation.builder()
				.bpartnerId(bpartnerId)
				.bpartnerLocationId(billToLocationId.getBpartnerLocationId())
				.locationId(billToLocationId.getLocationCaptureId())
				.contactId(BPartnerContactId.ofRepoIdOrNull(bpartnerId, contract.getBill_User_ID()))
				.build();
	}

	public static DocumentLocation extractDropShipLocation(final @NonNull I_C_SubscriptionProgress subscriptionLine)
	{
		final BPartnerId dropShipBPartnerId = BPartnerId.ofRepoId(subscriptionLine.getDropShip_BPartner_ID());
		return DocumentLocation.builder()
				.bpartnerId(dropShipBPartnerId)
				.bpartnerLocationId(BPartnerLocationId.ofRepoIdOrNull(dropShipBPartnerId, subscriptionLine.getDropShip_Location_ID()))
				.contactId(BPartnerContactId.ofRepoIdOrNull(dropShipBPartnerId, subscriptionLine.getDropShip_User_ID()))
				.build();
	}

	public static BPartnerLocationAndCaptureId extractBillToLocationId(@NonNull final I_C_Flatrate_Term term)
	{
		return BPartnerLocationAndCaptureId.ofRepoIdOrNull(
				term.getBill_BPartner_ID(),
				term.getBill_Location_ID(),
				term.getBill_Location_Value_ID());
	}


	public static BPartnerLocationAndCaptureId extractDropshipLocationId(@NonNull final I_C_Flatrate_Term term)
	{
		return BPartnerLocationAndCaptureId.ofRepoIdOrNull(
				term.getDropShip_BPartner_ID(),
				term.getDropShip_Location_ID(),
				term.getDropShip_Location_Value_ID());
	}
}
