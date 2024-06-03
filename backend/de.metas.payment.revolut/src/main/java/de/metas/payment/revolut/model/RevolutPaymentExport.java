/*
 * #%L
 * de.metas.payment.revolut
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

package de.metas.payment.revolut.model;

import de.metas.currency.Amount;
import de.metas.location.CountryId;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;

import javax.annotation.Nullable;

@Value
public class RevolutPaymentExport
{
	@Nullable
	RevolutPaymentExportId revolutPaymentExportId;

	@NonNull
	TableRecordReference recordReference;

	@NonNull
	String name;

	@NonNull
	OrgId orgId;

	@NonNull
	Amount amount;

	@NonNull
	RecipientType recipientType;

	@Nullable
	String accountNo;

	@Nullable
	String routingNo;

	@Nullable
	String IBAN;

	@Nullable
	String SwiftCode;

	@Nullable
	String paymentReference;

	@Nullable
	String regionName;

	@Nullable
	String addressLine1;

	@Nullable
	String addressLine2;

	@Nullable
	String city;

	@Nullable
	String postalCode;

	@Nullable
	CountryId recipientCountryId;

	@Nullable
	CountryId recipientBankCountryId;


	@Builder
	RevolutPaymentExport(
			@Nullable final RevolutPaymentExportId revolutPaymentExportId,
			@NonNull final TableRecordReference recordReference,
			@NonNull final String name,
			@NonNull final OrgId orgId,
			@NonNull final Amount amount,
			@NonNull final RecipientType recipientType,
			@Nullable final CountryId recipientBankCountryId,
			@Nullable final String accountNo,
			@Nullable final String routingNo,
			@Nullable final String IBAN,
			@Nullable final String SwiftCode,
			@Nullable final String paymentReference,
			@Nullable final String regionName,
			@Nullable final String addressLine1,
			@Nullable final String addressLine2,
			@Nullable final String city,
			@Nullable final String postalCode,
			@Nullable final CountryId recipientCountryId)
	{
		if (IBAN == null && accountNo == null)
		{
			throw new AdempiereException("No IBAN or accountNo found for paySelectionLine")
					.appendParametersToMessage()
					.setParameter("paySelectionLineId", recordReference.getRecord_ID());
		}

		this.revolutPaymentExportId = revolutPaymentExportId;
		this.recordReference = recordReference;
		this.name = name;
		this.orgId = orgId;
		this.amount = amount;
		this.accountNo = accountNo;
		this.routingNo = routingNo;
		this.IBAN = IBAN;
		this.SwiftCode = SwiftCode;
		this.paymentReference = paymentReference;
		this.regionName = regionName;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.city = city;
		this.postalCode = postalCode;
		this.recipientCountryId = recipientCountryId;
		this.recipientBankCountryId = recipientBankCountryId;
		this.recipientType = recipientType;
	}

	@NonNull
	public RevolutPaymentExportId getIdNotNull()
	{
		if (revolutPaymentExportId == null)
		{
			throw new AdempiereException("getIdNotNull() should be called only for already persisted RevolutExport record!")
					.appendParametersToMessage()
					.setParameter("RevolutPaymentExportId", this);
		}
		return revolutPaymentExportId;
	}
}
