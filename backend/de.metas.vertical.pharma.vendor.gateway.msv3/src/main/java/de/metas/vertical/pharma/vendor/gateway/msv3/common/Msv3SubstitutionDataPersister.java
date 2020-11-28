package de.metas.vertical.pharma.vendor.gateway.msv3.common;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import javax.annotation.Nullable;

import de.metas.organization.OrgId;
import de.metas.vertical.pharma.msv3.protocol.order.OrderResponsePackageItemSubstitution;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilitySubstitution;
import de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_Substitution;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-pharma.vendor.gateway.msv3
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

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Msv3SubstitutionDataPersister
{
	public static Msv3SubstitutionDataPersister newInstanceWithOrgId(final OrgId orgId)
	{
		return new Msv3SubstitutionDataPersister(orgId);
	}

	@NonNull
	private final OrgId orgId;

	public I_MSV3_Substitution storeSubstitutionOrNull(@Nullable final OrderResponsePackageItemSubstitution itemSubstitution)
	{
		if (itemSubstitution == null)
		{
			return null;
		}

		final I_MSV3_Substitution substitutionRecord = newInstance(I_MSV3_Substitution.class);
		substitutionRecord.setAD_Org_ID(orgId.getRepoId());
		substitutionRecord.setMSV3_Grund(itemSubstitution.getDefectReason().value());
		substitutionRecord.setMSV3_LieferPzn(itemSubstitution.getPzn().getValueAsString());
		substitutionRecord.setMSV3_Substitutionsgrund(itemSubstitution.getSubstitutionReason().value());
		save(substitutionRecord);

		return substitutionRecord;
	}

	public I_MSV3_Substitution storeSubstitutionOrNull(@Nullable final StockAvailabilitySubstitution substitution)
	{
		if (substitution == null)
		{
			return null;
		}

		final I_MSV3_Substitution substitutionRecord = newInstance(I_MSV3_Substitution.class);
		substitutionRecord.setMSV3_Grund(substitution.getReason().value());
		substitutionRecord.setMSV3_LieferPzn(substitution.getPzn().getValueAsString());
		substitutionRecord.setMSV3_Substitutionsgrund(substitution.getType().value());
		save(substitutionRecord);

		return substitutionRecord;

	}
}
