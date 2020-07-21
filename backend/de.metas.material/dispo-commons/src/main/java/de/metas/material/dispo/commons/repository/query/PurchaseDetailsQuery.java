package de.metas.material.dispo.commons.repository.query;

import javax.annotation.Nullable;

import de.metas.material.dispo.commons.candidate.businesscase.PurchaseDetail;
import de.metas.util.Check;
import de.metas.common.util.CoalesceUtil;
import lombok.Builder;
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
public class PurchaseDetailsQuery
{
	public static PurchaseDetailsQuery ofPurchaseDetailOrNull(@Nullable final PurchaseDetail purchaseDetail)
	{
		if (purchaseDetail == null)
		{
			return null;
		}
		return PurchaseDetailsQuery.builder()
				.productPlanningRepoId(purchaseDetail.getProductPlanningRepoId())
				.purchaseCandidateRepoId(purchaseDetail.getPurchaseCandidateRepoId())
				.receiptScheduleRepoId(purchaseDetail.getReceiptScheduleRepoId())
				.build();
	}

	/** a value <= 0 means "unspecified" */
	int purchaseCandidateRepoId;

	/** a value <= 0 means "unspecified" */
	int receiptScheduleRepoId;

	/** a value <= 0 means "unspecified" */
	int productPlanningRepoId;

	/**	default = false */
	boolean orderLineRepoIdMustBeNull;

	@Builder
	private PurchaseDetailsQuery(
			final int productPlanningRepoId,
			final int purchaseCandidateRepoId,
			final int receiptScheduleRepoId,
			final Boolean orderLineRepoIdMustBeNull)
	{
		this.productPlanningRepoId = productPlanningRepoId;
		this.purchaseCandidateRepoId = purchaseCandidateRepoId;
		this.receiptScheduleRepoId = receiptScheduleRepoId;

		this.orderLineRepoIdMustBeNull = CoalesceUtil.coalesce(orderLineRepoIdMustBeNull, false);

		Check.errorIf(
				purchaseCandidateRepoId <= 0 && receiptScheduleRepoId <= 0 && productPlanningRepoId <= 0,
				"At least one of productPlanningRepoId, purchaseCandidateRepoId and receiptScheduleRepoId needs to be > 1");
	}

}
