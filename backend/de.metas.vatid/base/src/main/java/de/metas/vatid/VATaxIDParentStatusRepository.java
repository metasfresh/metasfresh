/*
 * #%L
 * de.metas.vatid
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

package de.metas.vatid;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.organization.OrgId;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;

/**
 * Repository Tables: {@code C_BPartner}, {@code C_BPartner_Location}.
 *
 * <p>Repository Cluster: {@code VATaxIDParentStatusRepository} — sole owner of the three VAT-ID check
 * columns {@code VATaxIDStatus}, {@code VATaxIDCheckedAt} and {@code VATaxID_CheckLog_ID} on both tables.
 * The remaining columns of those two tables belong to {@code de.metas.bpartner}'s own persistence
 * ({@code BPartnerCompositeRepository}, {@code BPartnerDAO}), which never touches these three; this class
 * in turn touches no other column, so the two do not overlap despite sharing the tables.
 *
 * <p>Owns the parent side of the feature's denormalisation: {@code VATaxID_CheckLog} holds the evidence
 * (see {@link VATaxIDCheckRepository}), and these three columns are the copy of its latest relevant row
 * that tax determination and the windows actually read. Every write here therefore mirrors one existing
 * log row — which is why {@link #updateParentStatus(VATaxIDCheckRequest, VATaxIDLastCheck)} takes a
 * {@link VATaxIDLastCheck} and cannot express a status that no log row backs.
 *
 * <p><b>Both parent types are handled here, not by the caller.</b> A VAT-ID lives either on the partner
 * header or on one of its locations, and both tables carry the three columns under identical names
 * (DESIGN § 3) while sharing no model interface. That forces a branch on the record type somewhere; it
 * belongs on this side of the persistence boundary, so {@link VATaxIDCheckService} states <em>which
 * record</em> it means (by passing the {@link VATaxIDCheckRequest} that already identifies it) and never
 * how either table is loaded or saved.
 */
@Repository
public class VATaxIDParentStatusRepository
{
	/**
	 * Reads the organisation and the currently stored VAT-ID status of the record {@code request} names —
	 * the location when it carries one, else the partner header.
	 */
	@NonNull
	public VATaxIDParentStatus getParentStatus(@NonNull final VATaxIDCheckRequest request)
	{
		final BPartnerLocationId bpartnerLocationId = request.getBpartnerLocationId();
		if (bpartnerLocationId != null)
		{
			final I_C_BPartner_Location record = InterfaceWrapperHelper.load(bpartnerLocationId, I_C_BPartner_Location.class);
			return VATaxIDParentStatus.builder()
					.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
					.status(extractStatus(record.getVATaxIDStatus()))
					.build();
		}

		final I_C_BPartner record = InterfaceWrapperHelper.load(request.getBpartnerId(), I_C_BPartner.class);
		return VATaxIDParentStatus.builder()
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.status(extractStatus(record.getVATaxIDStatus()))
				.build();
	}

	/**
	 * Denormalises {@code lastCheck} onto the record {@code request} names — the location when it carries
	 * one, else the partner header — by writing all three columns together: the status, when it was
	 * obtained, and the {@code VATaxID_CheckLog} row that is its evidence. They are only ever written as a
	 * set, so the parent can never point at a log row whose status it does not mirror.
	 */
	public void updateParentStatus(
			@NonNull final VATaxIDCheckRequest request,
			@NonNull final VATaxIDLastCheck lastCheck)
	{
		final BPartnerLocationId bpartnerLocationId = request.getBpartnerLocationId();
		if (bpartnerLocationId != null)
		{
			final I_C_BPartner_Location record = InterfaceWrapperHelper.load(bpartnerLocationId, I_C_BPartner_Location.class);
			record.setVATaxIDStatus(lastCheck.getStatus().getCode());
			record.setVATaxIDCheckedAt(TimeUtil.asTimestampNotNull(lastCheck.getCheckedAt()));
			record.setVATaxID_CheckLog_ID(lastCheck.getCheckLogId().getRepoId());
			InterfaceWrapperHelper.saveRecord(record);
			return;
		}

		final I_C_BPartner record = InterfaceWrapperHelper.load(request.getBpartnerId(), I_C_BPartner.class);
		record.setVATaxIDStatus(lastCheck.getStatus().getCode());
		record.setVATaxIDCheckedAt(TimeUtil.asTimestampNotNull(lastCheck.getCheckedAt()));
		record.setVATaxID_CheckLog_ID(lastCheck.getCheckLogId().getRepoId());
		InterfaceWrapperHelper.saveRecord(record);
	}

	/**
	 * A blank status column (a record written before the column existed) reads as
	 * {@link VATaxIDStatus#NotChecked}, which is what it means.
	 */
	@NonNull
	private static VATaxIDStatus extractStatus(@Nullable final String statusCode)
	{
		return VATaxIDStatus.optionalOfNullableCode(statusCode).orElse(VATaxIDStatus.NotChecked);
	}
}
