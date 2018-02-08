package de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem;

import javax.annotation.Nullable;

import org.adempiere.util.Check;
import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.model.I_AD_Issue;

import lombok.Builder;
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

	ITableRecordReference transactionReference;

	int purchaseCandidateId;

	int orgId;

	Throwable throwable;

	I_AD_Issue issue;

	@Builder
	private PurchaseErrorItem(
			@Nullable final Throwable throwable,
			@Nullable final I_AD_Issue issue,
			final int purchaseCandidateId,
			final int orgId,
			@Nullable final ITableRecordReference transactionReference)
	{
		Check.assume(purchaseCandidateId > 0, "Given parameter purchaseCandidateId > 0");
		Check.assume(orgId > 0, "Given parameter orgId > 0");
		Check.assume(issue != null || throwable != null, "At least one of the given issue or thorwable need to be non-null");

		this.throwable = throwable;
		this.issue = issue;

		this.purchaseCandidateId = purchaseCandidateId;
		this.orgId = orgId;

		this.transactionReference = transactionReference;
	}
}
