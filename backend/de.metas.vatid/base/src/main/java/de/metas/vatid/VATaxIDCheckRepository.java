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

import com.google.common.annotations.VisibleForTesting;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.common.util.time.SystemTime;
import de.metas.process.PInstanceId;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_VATaxID_CheckLog;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

/**
 * Repository Tables: {@code VATaxID_CheckLog}.
 *
 * <p>Repository Cluster: sole owner of {@code VATaxID_CheckLog}.
 *
 * <p>Persists the individual VAT-ID online check attempts that are the feature's legal evidence
 * (AC12, {@code DESIGN.md} § 3). The table is <b>append-only, with exactly one exception</b>: a row is
 * first written at {@link VATaxIDStatus#RequestSent} and, later, that same row — and only that row — is
 * updated to its final status when the VIES answer arrives. No other update path exists on purpose:
 * offering a general "update any field of any row" method would invite bypassing that single-transition
 * rule and corrupting the evidence trail. The two methods below are exactly the two moves the lifecycle
 * allows: {@link #writeRequestSent(VATaxIDCheckRequest)} appends, {@link #completeCheck(VATaxIDCheckLogId, VATaxIDCheckResult)}
 * completes.
 */
@Repository
public class VATaxIDCheckRepository
{
	@VisibleForTesting
	public static VATaxIDCheckRepository newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		//noinspection DataFlowIssue
		return SpringContextHolder.getBeanOrSupply(VATaxIDCheckRepository.class, VATaxIDCheckRepository::new);
	}

	/**
	 * Appends a new {@code VATaxID_CheckLog} row at {@link VATaxIDStatus#RequestSent}, i.e. records that a
	 * VIES check for {@code request.getVataxID()} was just sent. This is the only way a row is created;
	 * there is no separate "already known" final-status creation path, because every check — successful,
	 * failed or never answered — starts life as a sent request.
	 *
	 * @return the id of the newly written row, to be passed to {@link #completeCheck(VATaxIDCheckLogId, VATaxIDCheckResult)}
	 * once (or if) the answer arrives.
	 */
	@NonNull
	public VATaxIDCheckLogId writeRequestSent(@NonNull final VATaxIDCheckRequest request)
	{
		final I_VATaxID_CheckLog record = InterfaceWrapperHelper.newInstance(I_VATaxID_CheckLog.class);
		record.setC_BPartner_ID(BPartnerId.toRepoId(request.getBpartnerId()));
		record.setC_BPartner_Location_ID(BPartnerLocationId.toRepoId(request.getBpartnerLocationId()));
		record.setVATaxID(request.getVataxID());
		record.setVATaxIDStatus(VATaxIDStatus.RequestSent.getCode());
		record.setRequestDate(TimeUtil.asTimestampNotNull(SystemTime.asInstant()));
		record.setAD_PInstance_ID(PInstanceId.toRepoId(request.getPinstanceId()));
		record.setAD_Session_ID(request.getAdSessionId() != null ? request.getAdSessionId() : 0);

		InterfaceWrapperHelper.saveRecord(record);

		return VATaxIDCheckLogId.ofRepoId(record.getVATaxID_CheckLog_ID());
	}

	/**
	 * Completes the one row identified by {@code checkLogId} — which must currently be at
	 * {@link VATaxIDStatus#RequestSent} — with the VIES outcome. This is the log's single allowed update:
	 * it never touches any other row, and it refuses to run twice on the same row (once completed, a row
	 * is immutable evidence, per {@code DESIGN.md} § 3's "nothing else ever updates a row").
	 *
	 * @throws org.adempiere.exceptions.AdempiereException if {@code checkLogId} does not currently point
	 * at a {@link VATaxIDStatus#RequestSent} row — either it was already completed, or it never was a
	 * request-sent row to begin with.
	 */
	public void completeCheck(@NonNull final VATaxIDCheckLogId checkLogId, @NonNull final VATaxIDCheckResult result)
	{
		Check.assume(
				result.getStatus() != VATaxIDStatus.RequestSent,
				"A check can only be completed with a final status, not {}; checkLogId={}", VATaxIDStatus.RequestSent, checkLogId);

		final I_VATaxID_CheckLog record = InterfaceWrapperHelper.load(checkLogId, I_VATaxID_CheckLog.class);
		Check.assume(
				VATaxIDStatus.RequestSent.getCode().equals(record.getVATaxIDStatus()),
				"Only a row still at {} can be completed, but checkLogId={} is already at {}",
				VATaxIDStatus.RequestSent, checkLogId, record.getVATaxIDStatus());

		record.setVATaxIDStatus(result.getStatus().getCode());
		record.setResponseDate(TimeUtil.asTimestampNotNull(SystemTime.asInstant()));
		record.setRequestIdentifier(result.getRequestIdentifier());
		record.setRawResponse(result.getRawResponse());

		InterfaceWrapperHelper.saveRecord(record);
	}
}
