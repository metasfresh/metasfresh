/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.common.externalsystem;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.common.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class JsonExternalSystemAmazonConfigMapping
{
	@NonNull
	Integer seqNo;

	@Nullable
	String docTypeOrder;

	@Nullable
	String paymentRule;

	@NonNull
	SyncAdvise bPartnerSyncAdvice;

	@NonNull
	SyncAdvise bPartnerLocationSyncAdvice;

	@Nullable
	Boolean invoiceEmailEnabled;

	@Nullable
	String paymentTermValue;

	@Nullable
	String amzCustomerGroup;

	@Nullable
	String amzPaymentMethod;

	@Nullable
	String description;

	@Builder
	@JsonCreator
	public JsonExternalSystemAmazonConfigMapping(
			@JsonProperty("seqNo") @NonNull final Integer seqNo,
			@JsonProperty("docTypeOrder") @Nullable final String docTypeOrder,
			@JsonProperty("paymentRule") @Nullable final String paymentRule,
			@JsonProperty("bpartnerSyncAdvice") @NonNull final SyncAdvise bPartnerSyncAdvice,
			@JsonProperty("bpartnerLocationSyncAdvice") @NonNull final SyncAdvise bPartnerLocationSyncAdvice,
			@JsonProperty("invoiceEmailEnabled") @Nullable final Boolean invoiceEmailEnabled,
			@JsonProperty("paymentTerm") @Nullable final String paymentTermValue,
			@JsonProperty("amzCustomerGroup") @Nullable final String amzCustomerGroup,
			@JsonProperty("amzPaymentMethod") @Nullable final String amzPaymentMethod,
			@JsonProperty("description") @Nullable final String description)
	{
		this.seqNo = seqNo;
		this.docTypeOrder = docTypeOrder;
		this.paymentRule = paymentRule;
		this.bPartnerSyncAdvice = bPartnerSyncAdvice;
		this.bPartnerLocationSyncAdvice = bPartnerLocationSyncAdvice;
		this.invoiceEmailEnabled = invoiceEmailEnabled;
		this.paymentTermValue = paymentTermValue;
		this.amzCustomerGroup = amzCustomerGroup;
		this.amzPaymentMethod = amzPaymentMethod;
		this.description = description;
	}

	public boolean isGroupMatching(@NonNull final String amzCustomerGroup)
	{
		return Check.isBlank(this.amzCustomerGroup) || this.amzCustomerGroup.trim().equals(amzCustomerGroup.trim());
	}

	public boolean isPaymentMethodMatching(@NonNull final String amzPaymentMethod)
	{
		return Check.isBlank(this.amzPaymentMethod) || this.amzPaymentMethod.trim().equals(amzPaymentMethod.trim());
	}
}
