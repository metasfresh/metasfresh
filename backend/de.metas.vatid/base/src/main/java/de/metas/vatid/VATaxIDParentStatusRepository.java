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
import de.metas.tax.api.VATIdentifier;
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
 * <p>Repository Cluster: sole owner of the three VAT-ID check columns {@code VATaxIDStatus},
 * {@code VATaxIDCheckedAt} and {@code VATaxID_CheckLog_ID} on both tables. The rest of those tables belongs
 * to {@code de.metas.bpartner}'s own persistence, which never touches these three, and this class WRITES no
 * other column — so the two do not overlap despite sharing the tables. It additionally READS {@code VATaxID}
 * ({@link #getCurrentVATaxID(VATaxIDCheckRequest)}), which {@code de.metas.bpartner} owns and this class
 * never writes.
 *
 * <p>Owns the parent side of the feature's denormalisation: {@code VATaxID_CheckLog} holds the evidence, and
 * these three columns are the copy of its latest relevant row that tax determination and the windows read.
 * Every write mirrors one existing log row, which is why
 * {@link #updateParentStatus(VATaxIDCheckRequest, VATaxIDLastCheck)} takes a {@link VATaxIDLastCheck} and
 * cannot express a status no log row backs.
 *
 * <p>Both parent types are handled here rather than by the caller: the two tables carry the columns under
 * identical names but share no model interface, so the branch on record type belongs on this side of the
 * persistence boundary.
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
	 * Reads the {@code VATaxID} currently stored on the record {@code request} names — the location when it
	 * carries one, else the partner header.
	 *
	 * <p>A plain read, deliberately: the decision it feeds — whether an answer obtained for
	 * {@link VATaxIDCheckRequest#getVataxID()} may still be written onto that record — belongs to
	 * {@link VATaxIDCheckService}, not here ({@code docs/coding-rules/architecture.md} §8, "no conditional
	 * writes based on business state"). What DOES belong here is the branch on parent type, which is why the
	 * caller gets one value rather than having to reproduce that branch.
	 *
	 * <p>Separate from {@link #getParentStatus(VATaxIDCheckRequest)} rather than folded into it because the
	 * two are read at different moments: the status before the check, this one immediately before the write,
	 * with a third party's response time in between.
	 *
	 * @return {@code null} when the record's {@code VATaxID} is empty — e.g. cleared by the user while the
	 * check was in flight.
	 */
	@Nullable
	public VATIdentifier getCurrentVATaxID(@NonNull final VATaxIDCheckRequest request)
	{
		final BPartnerLocationId bpartnerLocationId = request.getBpartnerLocationId();
		if (bpartnerLocationId != null)
		{
			final I_C_BPartner_Location record = InterfaceWrapperHelper.load(bpartnerLocationId, I_C_BPartner_Location.class);
			return VATIdentifier.ofNullable(record.getVATaxID());
		}

		final I_C_BPartner record = InterfaceWrapperHelper.load(request.getBpartnerId(), I_C_BPartner.class);
		return VATIdentifier.ofNullable(record.getVATaxID());
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
