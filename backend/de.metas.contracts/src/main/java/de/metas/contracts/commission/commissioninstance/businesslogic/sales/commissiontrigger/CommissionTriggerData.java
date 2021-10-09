package de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionPoints;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.Instant;
import java.time.LocalDate;

import static de.metas.common.util.CoalesceUtil.coalesce;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2019 metas GmbH
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

/**
 * Contains data from a commission trigger. Not deltas, just the base points which the trigger currently has
 */
@Value
public class CommissionTriggerData
{
	OrgId orgId;

	CommissionTriggerDocumentId triggerDocumentId;

	CommissionTriggerType triggerType;

	boolean invoiceCandidateWasDeleted;

	/**
	 * Exact timestamp of this trigger. E.g. if one invoice candidate is changed, there might be two triggers wit different timestamps that relate to the same IC.
	 */
	Instant timestamp;

	LocalDate triggerDocumentDate;

	CommissionPoints forecastedBasePoints;

	CommissionPoints invoiceableBasePoints;

	CommissionPoints invoicedBasePoints;

	ProductId productId;

	Quantity totalQtyInvolved;

	CurrencyId documentCurrencyId;

	@Builder
	@JsonCreator
	private CommissionTriggerData(
			@JsonProperty("orgId") @NonNull final OrgId orgId,
			@JsonProperty("triggerDocumentId") @Nullable final CommissionTriggerDocumentId triggerDocumentId,
			@JsonProperty("triggerType") @NonNull final CommissionTriggerType triggerType,
			@JsonProperty("invoiceCandidateWasDeleted") final boolean invoiceCandidateWasDeleted,
			@JsonProperty("timestamp") @NonNull final Instant timestamp,
			@JsonProperty("triggerDocumentDate") @NonNull final LocalDate triggerDocumentDate,
			@JsonProperty("forecastedBasePoints") @NonNull final CommissionPoints forecastedBasePoints,
			@JsonProperty("invoiceableBasePoints") @NonNull final CommissionPoints invoiceableBasePoints,
			@JsonProperty("invoicedBasePoints") @NonNull final CommissionPoints invoicedBasePoints,
			@JsonProperty("productId") @NonNull final ProductId productId,
			@JsonProperty("totalQtyInvolved") @NonNull final Quantity totalQtyInvolved,
			@JsonProperty("documentCurrencyId") @NonNull final CurrencyId documentCurrencyId)
	{
		this.timestamp = timestamp;
		this.triggerDocumentDate = triggerDocumentDate;

		this.orgId = orgId;
		this.triggerDocumentId = triggerDocumentId;
		this.triggerType = triggerType;
		this.invoiceCandidateWasDeleted = invoiceCandidateWasDeleted;

		this.forecastedBasePoints = coalesce(forecastedBasePoints, CommissionPoints.ZERO);
		this.invoiceableBasePoints = coalesce(invoiceableBasePoints, CommissionPoints.ZERO);
		this.invoicedBasePoints = coalesce(invoicedBasePoints, CommissionPoints.ZERO);
		this.productId = productId;
		this.totalQtyInvolved = totalQtyInvolved;
		this.documentCurrencyId = documentCurrencyId;
	}

	public CommissionPoints getCommissionBase()
	{
		return forecastedBasePoints
				.add(invoiceableBasePoints)
				.add(invoicedBasePoints);
	}
}
