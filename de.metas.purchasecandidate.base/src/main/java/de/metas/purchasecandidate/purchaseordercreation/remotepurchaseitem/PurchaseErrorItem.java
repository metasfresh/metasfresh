package de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem;

import javax.annotation.Nullable;

import org.adempiere.util.lang.ITableRecordReference;

import de.metas.error.AdIssueId;
import de.metas.organization.OrgId;
import de.metas.purchasecandidate.PurchaseCandidateId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.purchasecandidate.base
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
public class PurchaseErrorItem implements PurchaseItem
{
	public static PurchaseErrorItem cast(final PurchaseItem purchaseItem)
	{
		return (PurchaseErrorItem)purchaseItem;
	}

	PurchaseItemId purchaseItemId;

	ITableRecordReference transactionReference;

	PurchaseCandidateId purchaseCandidateId;

	OrgId orgId;

	Throwable throwable;

	AdIssueId adIssueId;

	@Builder
	private PurchaseErrorItem(
			final PurchaseItemId purchaseItemId,
			@Nullable final Throwable throwable,
			@Nullable final AdIssueId adIssueId,
			@NonNull final PurchaseCandidateId purchaseCandidateId,
			@NonNull final OrgId orgId,
			@Nullable final ITableRecordReference transactionReference)
	{
		this.purchaseItemId = purchaseItemId;

		Check.assume(adIssueId != null || throwable != null, "At least one of the given issue or thorwable need to be non-null");

		this.throwable = throwable;
		this.adIssueId = adIssueId;

		this.purchaseCandidateId = purchaseCandidateId;
		this.orgId = orgId;

		this.transactionReference = transactionReference;
	}
}
