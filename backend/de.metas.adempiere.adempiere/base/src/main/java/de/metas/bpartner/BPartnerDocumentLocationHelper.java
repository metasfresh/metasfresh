/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.bpartner;

import de.metas.document.location.DocumentLocation;
import de.metas.location.LocationId;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.compiere.model.I_C_BPartner_Location;

@UtilityClass
public class BPartnerDocumentLocationHelper
{
	public static DocumentLocation extractDocumentLocation(@NonNull final I_C_BPartner_Location bpl)
	{
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(bpl.getC_BPartner_ID());
		return DocumentLocation.builder()
				.bpartnerId(bpartnerId)
				.bpartnerLocationId(BPartnerLocationId.ofRepoIdOrNull(bpartnerId, bpl.getC_BPartner_Location_ID()))
				.locationId(LocationId.ofRepoIdOrNull(bpl.getC_Location_ID()))
				.bpartnerAddress(bpl.getAddress())
				.contactId(null) // N/A
				.build();
	}
}
