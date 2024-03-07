/*
 * #%L
 * de.metas.postfinance
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.postfinance.customerregistration.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

import static de.metas.postfinance.PostFinanceConstants.CUSTOM_FIELD_BPARTNEREXTERNALREFERENCE;

@JsonIgnoreProperties(ignoreUnknown = true)
public record XmlCustomerRegistration(
		@JacksonXmlProperty(localName = "SubscriptionType") String subscriptionType,
		@JacksonXmlProperty(localName = "BillerID") String billerId,
		@JacksonXmlProperty(localName = "RecipientID") String recipientId,
		@JacksonXmlProperty(localName = "RecipientType") String recipientType,
		@JacksonXmlProperty(localName = "Language") String language,
		@JacksonXmlProperty(localName = "CustomerNameAddress") XmlCustomerNameAddress customerNameAddress,
		@JacksonXmlProperty(localName = "Email") String email,
		@JacksonXmlProperty(localName = "UID") @JsonInclude(JsonInclude.Include.NON_EMPTY) String uid,
		@JacksonXmlProperty(localName = "CustomerSubscriptionFormField") @JacksonXmlElementWrapper(useWrapping = false) @JsonInclude(JsonInclude.Include.NON_EMPTY) List<XmlCustomerSubscriptionFormField> customerSubscriptionFormFields,
		@JacksonXmlProperty(localName = "CreditAccount") @JsonInclude(JsonInclude.Include.NON_EMPTY) String creditorAccount,
		@JacksonXmlProperty(localName = "CreditorReference") @JsonInclude(JsonInclude.Include.NON_EMPTY) String creditorReference)
{
	@NonNull
	@JsonIgnore
	public Optional<XmlCustomerSubscriptionFormField> getCustomerExternalId()
	{
		return Optional.ofNullable(customerSubscriptionFormFields)
				.flatMap(fields -> fields.stream()
						.filter(field -> CUSTOM_FIELD_BPARTNEREXTERNALREFERENCE.equals(field.technicalId()))
						.findFirst());
	}
}
