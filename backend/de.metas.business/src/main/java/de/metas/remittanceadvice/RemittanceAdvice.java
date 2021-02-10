/*
 * #%L
 * de.metas.business
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

package de.metas.remittanceadvice;

import de.metas.bpartner.BPartnerBankAccountId;
import de.metas.bpartner.BPartnerId;
import de.metas.document.DocTypeId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentId;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.adempiere.service.ClientId;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
@Builder
public class RemittanceAdvice
{
	@NonNull
	private final RemittanceAdviceId remittanceAdviceId;

	@NonNull
	private final OrgId orgId;

	@NonNull
	private final ClientId clientId;

	@NonNull
	private final BPartnerId sourceBPartnerId;

	@NonNull
	private final BPartnerBankAccountId sourceBPartnerBankAccountId;

	@NonNull
	private final BPartnerId destinationBPartnerId;

	@NonNull
	private final BPartnerBankAccountId destinationBPartnerBankAccountId;

	@NonNull
	private final String documentNumber;

	@NonNull
	private final Instant documentDate;

	@NonNull
	private final String externalDocumentNumber;

	@NonNull
	private final String docStatus;

	@NonNull
	private final DocTypeId docTypeId;

	@NonNull
	private final BigDecimal remittedAmountSum;

	@NonNull
	private final CurrencyId remittedAmountCurrencyId;

	@Nullable
	private final Instant sendDate;

	@Nullable
	private final BigDecimal serviceFeeAmount;

	@Nullable
	private final CurrencyId serviceFeeCurrencyId;

	@Nullable
	private final BigDecimal paymentDiscountAmountSum;

	@Nullable
	private final String additionalNotes;

	@Nullable
	private PaymentId paymentId;

	private boolean isSOTrx;

	private List<RemittanceAdviceLine> lines;

	public BPartnerId getRemittanceAdviceBPartnerId()
	{
		return isSOTrx ?
				sourceBPartnerId : destinationBPartnerId;
	}
}
