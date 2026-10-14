/*
 * #%L
 * metasfresh-material-dispo-commons
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

package de.metas.material.dispo.commons.candidate.businesscase;

import de.metas.material.dispo.commons.candidate.CandidateAtpReconciliationDetailId;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/**
 * Marks a correction candidate as written by an ATP reconciliation run, backed by the same
 * {@code MD_ATP_Reconciliation_Backup} row the run's audit trail uses - {@code qtyBefore} is {@code null} for this
 * candidate (it did not exist before the run), matching the "new candidate" convention that table already has.
 */
@Value
@Builder(toBuilder = true)
public class AtpReconciliationDetail implements BusinessCaseDetail
{
	@Nullable
	CandidateAtpReconciliationDetailId candidateAtpReconciliationDetailId;

	@Nullable
	CandidateId candidateId;

	@NonNull
	String reconciliationRunUUID;

	@Nullable
	BigDecimal qtyBefore;

	@NonNull
	BigDecimal qtyAfter;

	@Override
	public CandidateBusinessCase getCandidateBusinessCase()
	{
		return CandidateBusinessCase.ATP_RECONCILIATION;
	}

	@Override
	public BigDecimal getQty()
	{
		return qtyAfter;
	}

	@Nullable
	public static AtpReconciliationDetail castOrNull(@Nullable final BusinessCaseDetail businessCaseDetail)
	{
		if (!(businessCaseDetail instanceof AtpReconciliationDetail))
		{
			return null;
		}
		return cast(businessCaseDetail);
	}

	@NonNull
	public static AtpReconciliationDetail cast(@NonNull final BusinessCaseDetail businessCaseDetail)
	{
		return (AtpReconciliationDetail)businessCaseDetail;
	}
}
