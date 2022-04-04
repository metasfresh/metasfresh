/*
 * #%L
 * de-metas-common-externalsystem
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

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.common.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
public class JsonExternalSystemEbayConfigMapping
{
	@NonNull
	Integer seqNo;

	@NonNull
	String docTypeOrder;

	@NonNull
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
	String ebayCustomerGroup;

	@Nullable
	String ebayPaymentMethod;

	@Nullable
	String description;

	@Builder
	@JsonCreator
	public JsonExternalSystemEbayConfigMapping(
			@JsonProperty("seqNo") @NonNull final Integer seqNo,
			@JsonProperty("docTypeOrder") @NonNull final String docTypeOrder,
			@JsonProperty("paymentRule") @NonNull final String paymentRule,
			@JsonProperty("bpartnerSyncAdvice") @NonNull final SyncAdvise bPartnerSyncAdvice,
			@JsonProperty("bpartnerLocationSyncAdvice") @NonNull final SyncAdvise bPartnerLocationSyncAdvice,
			@JsonProperty("invoiceEmailEnabled") @Nullable final Boolean invoiceEmailEnabled,
			@JsonProperty("paymentTerm") @Nullable final String paymentTermValue,
			@JsonProperty("ebayCustomerGroup") @Nullable final String ebayCustomerGroup,
			@JsonProperty("ebayPaymentMethod") @Nullable final String ebayPaymentMethod,
			@JsonProperty("description") @Nullable final String description)
	{
		this.seqNo = seqNo;
		this.docTypeOrder = docTypeOrder;
		this.paymentRule = paymentRule;
		this.bPartnerSyncAdvice = bPartnerSyncAdvice;
		this.bPartnerLocationSyncAdvice = bPartnerLocationSyncAdvice;
		this.invoiceEmailEnabled = invoiceEmailEnabled;
		this.paymentTermValue = paymentTermValue;
		this.ebayCustomerGroup = ebayCustomerGroup;
		this.ebayPaymentMethod = ebayPaymentMethod;
		this.description = description;
	}
	
	public boolean isGroupMatching(@NonNull final String sw6CustomerGroup)
	{
		return Check.isBlank(this.ebayCustomerGroup) || this.ebayCustomerGroup.equals(sw6CustomerGroup);
	}

	public boolean isPaymentMethodMatching(@NonNull final String sw6PaymentMethod)
	{
		return Check.isBlank(this.ebayPaymentMethod) || this.ebayPaymentMethod.equals(sw6PaymentMethod);
	}
	
}
