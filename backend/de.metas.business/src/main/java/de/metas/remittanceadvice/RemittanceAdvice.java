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

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerBankAccountId;
import de.metas.bpartner.BPartnerId;
import de.metas.currency.Amount;
import de.metas.document.DocTypeId;
import de.metas.i18n.BooleanWithReason;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentId;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Getter
@EqualsAndHashCode
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

	@Nullable
	private final Instant paymentDate;
	
	@Nullable
	private final String externalDocumentNumber;

	@NonNull
	private final String docStatus;

	@NonNull
	private final DocTypeId docTypeId;

	@NonNull
	private final CurrencyId remittedAmountCurrencyId;

	@Nullable
	private final Instant sendDate;

	@Nullable
	private final CurrencyId serviceFeeCurrencyId;

	@Nullable
	private final String additionalNotes;

	private final boolean isImported;

	private final boolean isSOTrx;

	@NonNull
	private BigDecimal remittedAmountSum;
	@Nullable
	private BigDecimal serviceFeeAmount;
	@Nullable
	private BigDecimal paymentDiscountAmountSum;
	@Nullable
	private PaymentId paymentId;

	private boolean isDocumentAcknowledged;

	private boolean currenciesReadOnlyFlag;

	private boolean processed;

	@NonNull
	private final List<RemittanceAdviceLine> lines;

	@Builder
	public RemittanceAdvice(@NonNull final RemittanceAdviceId remittanceAdviceId, @NonNull final OrgId orgId, @NonNull final ClientId clientId, @NonNull final BPartnerId sourceBPartnerId, @NonNull final BPartnerBankAccountId sourceBPartnerBankAccountId, @NonNull final BPartnerId destinationBPartnerId, @NonNull final BPartnerBankAccountId destinationBPartnerBankAccountId,
			@NonNull final String documentNumber,
			@NonNull final Instant documentDate, @Nullable final String externalDocumentNumber, @NonNull final String docStatus, @NonNull final DocTypeId docTypeId, @NonNull final CurrencyId remittedAmountCurrencyId, @Nullable final Instant sendDate, @Nullable final CurrencyId serviceFeeCurrencyId, @Nullable final String additionalNotes, final boolean isImported,
			@Nullable final Instant paymentDate,
			@Nullable final BigDecimal serviceFeeAmount,
			@NonNull final BigDecimal remittedAmountSum, @Nullable final BigDecimal paymentDiscountAmountSum, @Nullable final PaymentId paymentId, final boolean isSOTrx,
			final boolean isDocumentAcknowledged, final boolean currenciesReadOnlyFlag, final boolean processed, @NonNull final List<RemittanceAdviceLine> lines)
	{
		if (serviceFeeAmount != null && serviceFeeAmount.signum() != 0 && serviceFeeCurrencyId == null)
		{
			throw new AdempiereException("Missing ServiceFeeCurrencyID!");
		}


		this.remittanceAdviceId = remittanceAdviceId;
		this.orgId = orgId;
		this.clientId = clientId;
		this.sourceBPartnerId = sourceBPartnerId;
		this.sourceBPartnerBankAccountId = sourceBPartnerBankAccountId;
		this.destinationBPartnerId = destinationBPartnerId;
		this.destinationBPartnerBankAccountId = destinationBPartnerBankAccountId;
		this.documentNumber = documentNumber;
		this.documentDate = documentDate;
		this.externalDocumentNumber = externalDocumentNumber;
		this.docStatus = docStatus;
		this.docTypeId = docTypeId;
		this.remittedAmountCurrencyId = remittedAmountCurrencyId;
		this.sendDate = sendDate;
		this.serviceFeeCurrencyId = serviceFeeCurrencyId;
		this.additionalNotes = additionalNotes;
		this.isImported = isImported;
		this.serviceFeeAmount = serviceFeeAmount;
		this.remittedAmountSum = remittedAmountSum;
		this.paymentDiscountAmountSum = paymentDiscountAmountSum;
		this.paymentId = paymentId;
		this.isSOTrx = isSOTrx;
		this.lines = lines;
		this.processed = processed;
		this.isDocumentAcknowledged = isDocumentAcknowledged;
		this.currenciesReadOnlyFlag = currenciesReadOnlyFlag;
		this.paymentDate = paymentDate;
	}

	@NonNull
	public Optional<RemittanceAdviceLine> getLine(@NonNull final RemittanceAdviceLineId remittanceAdviceLineId)
	{
		return lines.stream()
				.filter(line -> line.getRemittanceAdviceLineId().equals(remittanceAdviceLineId))
				.findFirst();
	}

	public void validateCompleteAction()
	{
		final ImmutableList<Integer> lineIdsWithProblems = lines.stream()
				.filter(line -> !line.isReadyForCompletion())
				.map(RemittanceAdviceLine::getRemittanceAdviceLineId)
				.map(RemittanceAdviceLineId::getRepoId)
				.collect(ImmutableList.toImmutableList());

		if (lineIdsWithProblems.size() > 0)
		{
			throw new AdempiereException("There is a number of lines which cannot be completed! Lines with problems:")
					.appendParametersToMessage()
					.setParameter("Lines", lineIdsWithProblems);
		}
	}

	/**
	 * @return true, if any recompute logic was applied, false otherwise
	 */
	@NonNull
	public BooleanWithReason recomputeSumsFromLines()
	{
		if (isImported)
		{
			//leave the values as is if the record was imported
			return BooleanWithReason.falseBecause("The remittance advice is imported!");
		}

		Amount remittedAmountSumAmount = null;
		Amount paymentDiscountAmountSumAmount = null;
		Amount serviceFeeSumAmount = null;

		for (final RemittanceAdviceLine line : lines)
		{
			remittedAmountSumAmount = remittedAmountSumAmount == null
					? line.getRemittedAmount()
					: remittedAmountSumAmount.add(line.getRemittedAmount());

			if (line.getPaymentDiscountAmount() != null)
			{
				paymentDiscountAmountSumAmount = paymentDiscountAmountSumAmount == null
						? line.getPaymentDiscountAmount()
						: paymentDiscountAmountSumAmount.add(line.getPaymentDiscountAmount());
			}

			if (line.getServiceFeeAmount() != null)
			{
				serviceFeeSumAmount = serviceFeeSumAmount == null
						? line.getServiceFeeAmount()
						: serviceFeeSumAmount.add(line.getServiceFeeAmount());
			}
		}

		remittedAmountSum = remittedAmountSumAmount != null ? remittedAmountSumAmount.toBigDecimal() : BigDecimal.ZERO;
		paymentDiscountAmountSum = paymentDiscountAmountSumAmount != null ? paymentDiscountAmountSumAmount.toBigDecimal() : null;
		serviceFeeAmount = serviceFeeSumAmount != null ? serviceFeeSumAmount.toBigDecimal() : null;

		if (serviceFeeAmount != null && serviceFeeAmount.signum() != 0 && serviceFeeCurrencyId == null)
		{
			throw new AdempiereException("Missing ServiceFeeCurrencyID!");
		}

		return BooleanWithReason.TRUE;
	}

	public void setPaymentId(@NonNull final PaymentId paymentId)
	{
		this.paymentId = paymentId;
	}

	/**
	 * @return true, if acknowledged status changed, false otherwise
	 */
	public boolean recomputeIsDocumentAcknowledged()
	{
		final boolean isDocumentAcknowledged_refreshedValue = lines.stream().allMatch(RemittanceAdviceLine::isReadyForCompletion);

		final boolean hasAcknowledgedStatusChanged = isDocumentAcknowledged != isDocumentAcknowledged_refreshedValue;

		this.isDocumentAcknowledged = isDocumentAcknowledged_refreshedValue;

		return hasAcknowledgedStatusChanged;
	}

	/**
	 * @return true, if read only currencies flag changed, false otherwise
	 */
	public boolean recomputeCurrenciesReadOnlyFlag()
	{
		final boolean currenciesReadOnlyFlag_refreshedValue = lines.stream().anyMatch(RemittanceAdviceLine::isInvoiceResolved);

		final boolean currenciesReadOnlyFlagChanged = currenciesReadOnlyFlag != currenciesReadOnlyFlag_refreshedValue;

		this.currenciesReadOnlyFlag = currenciesReadOnlyFlag_refreshedValue;

		return currenciesReadOnlyFlagChanged;
	}

	public void setProcessedFlag(final boolean processed)
	{
		this.processed = processed;

		getLines().forEach(line -> line.setProcessed(processed));
	}
}
