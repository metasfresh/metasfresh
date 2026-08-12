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
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryUpdater;
import org.adempiere.ad.dao.IQueryBL;
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
 * <p>Persists the individual VAT-ID online check attempts that are the feature's legal evidence. The
 * table is <b>append-only, with exactly one exception</b>: a row is first written at
 * {@link VATaxIDStatus#RequestSent} and, later, that same row — and only that row — is updated to its
 * final status when the VIES answer arrives. This repository exposes exactly the two moves that
 * lifecycle allows — {@link #writeRequestSent(VATaxIDCheckRequest)} appends,
 * {@link #completeCheck(VATaxIDCheckLogId, VATaxIDCheckResult)} completes — and no general "update any
 * field of any row" method, so <b>no bypass path is exposed by this repository's own API</b>: every
 * caller reaching the table through this class can only append or complete, never issue an arbitrary
 * update. That guarantee is scoped to this class's API only — {@code I_VATaxID_CheckLog} is still a
 * public generated model interface, so the invariant's actual enforcement against a caller that skips
 * this repository entirely (e.g. loading and saving the model directly) is not the type system but the
 * codebase-wide review convention that persistence primitives for a model live only in that model's
 * authoritative repository.
 */
@Repository
public class VATaxIDCheckRepository
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

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
	 * it never touches any other row, and it refuses to run twice on the same row, because once completed
	 * a row is immutable evidence.
	 *
	 * <p>The guard is an atomic conditional {@code UPDATE ... WHERE VATaxID_CheckLog_ID = ? AND
	 * VATaxIDStatus = 'RequestSent'}, not a load-then-save check, so two concurrent calls for the same
	 * {@code checkLogId} cannot both succeed: at most one {@code UPDATE} matches the row, the other
	 * affects zero rows and throws.
	 *
	 * @throws org.adempiere.exceptions.AdempiereException if {@code checkLogId} does not currently point
	 * at a {@link VATaxIDStatus#RequestSent} row — either it was already completed (including by a
	 * concurrent caller that won the race), or it never was a request-sent row to begin with.
	 */
	public void completeCheck(@NonNull final VATaxIDCheckLogId checkLogId, @NonNull final VATaxIDCheckResult result)
	{
		Check.assume(
				result.getStatus() != VATaxIDStatus.RequestSent,
				"A check can only be completed with a final status, not {}; checkLogId={}", VATaxIDStatus.RequestSent, checkLogId);

		final ICompositeQueryUpdater<I_VATaxID_CheckLog> updater = queryBL
				.createCompositeQueryUpdater(I_VATaxID_CheckLog.class)
				.addSetColumnValue(I_VATaxID_CheckLog.COLUMNNAME_VATaxIDStatus, result.getStatus().getCode())
				.addSetColumnValue(I_VATaxID_CheckLog.COLUMNNAME_ResponseDate, TimeUtil.asTimestampNotNull(SystemTime.asInstant()))
				.addSetColumnValue(I_VATaxID_CheckLog.COLUMNNAME_RequestIdentifier, result.getRequestIdentifier())
				.addSetColumnValue(I_VATaxID_CheckLog.COLUMNNAME_RawResponse, result.getRawResponse());

		final int updatedCount = queryBL
				.createQueryBuilder(I_VATaxID_CheckLog.class)
				.addEqualsFilter(I_VATaxID_CheckLog.COLUMNNAME_VATaxID_CheckLog_ID, checkLogId.getRepoId())
				.addEqualsFilter(I_VATaxID_CheckLog.COLUMNNAME_VATaxIDStatus, VATaxIDStatus.RequestSent.getCode())
				.create()
				.updateDirectly(updater);

		Check.assume(
				updatedCount == 1,
				"Only a row still at {} can be completed, but checkLogId={} is not — either it was already completed or it does not exist",
				VATaxIDStatus.RequestSent, checkLogId);
	}
}
