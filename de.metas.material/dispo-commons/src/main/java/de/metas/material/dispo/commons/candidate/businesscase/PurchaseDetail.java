package de.metas.material.dispo.commons.candidate.businesscase;

import java.math.BigDecimal;

import org.adempiere.util.Check;

import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-material-dispo-commons
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

@Value
public class PurchaseDetail implements BusinessCaseDetail
{
	BigDecimal plannedQty;

	BigDecimal orderedQty;

	/** also this field can be <= 0, if the purchase candidate was only advised but not created so far. */
	int purchaseCandidateRepoId;

	int orderLineRepoId;

	int receiptScheduleRepoId;

	int inoutLineRepoId;

	int vendorRepoId;

	Flag advised;

	@Override
	public CandidateBusinessCase getCandidateBusinessCase()
	{
		return CandidateBusinessCase.PURCHASE;
	}

	@Builder
	private PurchaseDetail(
			@NonNull final BigDecimal plannedQty,
			@NonNull final BigDecimal orderedQty,
			@NonNull Flag advised,
			final int orderLineRepoId,
			final int purchaseCandidateRepoId,
			final int receiptScheduleRepoId,
			final int inoutLineRepoId,
			final int vendorRepoId)
	{
		Check.errorIf(plannedQty.signum() < 0, "The given plannedQty={} needs to be >= 0", plannedQty);
		this.plannedQty = plannedQty;

		Check.errorIf(plannedQty.signum() < 0, "The given orderedQty={} needs to be >= 0", orderedQty);
		this.orderedQty = orderedQty;

		this.vendorRepoId = Check.assumeGreaterThanZero(vendorRepoId, "vendorRepoId");

		this.purchaseCandidateRepoId = purchaseCandidateRepoId;
		this.orderLineRepoId = orderLineRepoId;
		this.receiptScheduleRepoId = receiptScheduleRepoId;
		this.inoutLineRepoId = inoutLineRepoId;

		this.advised = advised;
	}
}
