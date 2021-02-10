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
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyDAO;
import de.metas.document.DocTypeId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_RemittanceAdvice;
import org.compiere.model.I_C_RemittanceAdvice_Line;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class RemittanceAdviceRepository
{
	private final ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	public RemittanceAdvice createRemittanceAdvice(@NonNull final CreateRemittanceAdviceRequest remittanceAdviceRequest)
	{
		final I_C_RemittanceAdvice record = buildRemittanceAdviceRecord(remittanceAdviceRequest);
		saveRecord(record);
		return toRemittanceAdvice(record);
	}

	public void createRemittanceAdviceLine(
			@NonNull final CreateRemittanceAdviceLineRequest remittanceAdviceLineRequest)
	{
		final I_C_RemittanceAdvice_Line record = buildRemittanceAdviceLineRecord(remittanceAdviceLineRequest);

		saveRecord(record);
	}

	@NonNull
	private I_C_RemittanceAdvice buildRemittanceAdviceRecord(@NonNull final CreateRemittanceAdviceRequest createRemittanceAdviceRequest)
	{
		final I_C_RemittanceAdvice record = InterfaceWrapperHelper.newInstance(I_C_RemittanceAdvice.class);

		record.setAD_Org_ID(createRemittanceAdviceRequest.getOrgId().getRepoId());

		record.setSource_BPartner_ID(createRemittanceAdviceRequest.getSourceBPartnerId().getRepoId());
		record.setSource_BP_BankAccount_ID(BPartnerBankAccountId.toRepoId(createRemittanceAdviceRequest.getSourceBPartnerBankAccountId()));

		record.setDestintion_BPartner_ID(createRemittanceAdviceRequest.getDestinationBPartnerId().getRepoId());
		record.setDestination_BP_BankAccount_ID(createRemittanceAdviceRequest.getDestinationBPartnerBankAccountId().getRepoId());

		record.setExternalDocumentNo(createRemittanceAdviceRequest.getExternalDocumentNumber());
		record.setDateDoc(TimeUtil.asTimestamp(createRemittanceAdviceRequest.getDocumentDate()));
		record.setC_DocType_ID(createRemittanceAdviceRequest.getDocTypeId().getRepoId());

		record.setPaymentDiscountAmountSum(createRemittanceAdviceRequest.getPaymentDiscountAmountSum());
		record.setRemittanceAmt(createRemittanceAdviceRequest.getRemittedAmountSum());
		record.setRemittanceAmt_Currency_ID(createRemittanceAdviceRequest.getRemittedAmountCurrencyId().getRepoId());

		record.setServiceFeeAmount(createRemittanceAdviceRequest.getServiceFeeAmount());
		record.setServiceFeeAmount_Currency_ID(CurrencyId.toRepoId(createRemittanceAdviceRequest.getServiceFeeCurrencyId()));

		record.setC_Payment_Doctype_Target_ID(createRemittanceAdviceRequest.getTargetPaymentDocTypeId().getRepoId());

		record.setAdditionalNotes(createRemittanceAdviceRequest.getAdditionalNotes());
		record.setSendAt(TimeUtil.asTimestamp(createRemittanceAdviceRequest.getSendDate()));

		return record;
	}

	@NonNull
	private I_C_RemittanceAdvice_Line buildRemittanceAdviceLineRecord(
			@NonNull final CreateRemittanceAdviceLineRequest remittanceAdviceLineRequest)
	{
		final I_C_RemittanceAdvice_Line record = InterfaceWrapperHelper.newInstance(I_C_RemittanceAdvice_Line.class);

		record.setC_RemittanceAdvice_ID(remittanceAdviceLineRequest.getRemittanceAdviceId().getRepoId());
		record.setInvoiceIdentifier(remittanceAdviceLineRequest.getInvoiceIdentifier());
		record.setC_BPartner_ID(BPartnerId.toRepoId(remittanceAdviceLineRequest.getBpartnerIdentifier()));

		record.setExternalInvoiceDocBaseType(remittanceAdviceLineRequest.getExternalInvoiceDocBaseType());

		record.setInvoiceDate(TimeUtil.asTimestamp(remittanceAdviceLineRequest.getDateInvoiced()));
		record.setInvoiceAmt(remittanceAdviceLineRequest.getInvoiceGrossAmount());
		record.setPaymentDiscountAmt(remittanceAdviceLineRequest.getPaymentDiscountAmount());

		record.setRemittanceAmt(remittanceAdviceLineRequest.getRemittedAmount());

		record.setServiceFeeAmount(remittanceAdviceLineRequest.getServiceFeeAmount());
		record.setServiceFeeVatRate(remittanceAdviceLineRequest.getServiceFeeVatRate());
		return record;
	}

	@NonNull
	private RemittanceAdvice toRemittanceAdvice(@NonNull final I_C_RemittanceAdvice record)
	{
		final BPartnerBankAccountId sourceBPBankAccountId = BPartnerBankAccountId
				.ofRepoId(record.getSource_BPartner_ID(), record.getSource_BP_BankAccount_ID());

		final BPartnerBankAccountId destinationBPBankAccountId = BPartnerBankAccountId
				.ofRepoId(record.getDestintion_BPartner_ID(), record.getDestination_BP_BankAccount_ID());

		final RemittanceAdviceId remittanceAdviceId = RemittanceAdviceId.ofRepoId(record.getC_RemittanceAdvice_ID());
		final CurrencyId remittanceCurrencyId = CurrencyId.ofRepoId(record.getRemittanceAmt_Currency_ID());

		return RemittanceAdvice.builder()
				.remittanceAdviceId(remittanceAdviceId)

				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.clientId(ClientId.ofRepoId(record.getAD_Client_ID()))

				.sourceBPartnerId(BPartnerId.ofRepoId(record.getSource_BPartner_ID()))
				.sourceBPartnerBankAccountId(sourceBPBankAccountId)

				.destinationBPartnerId(BPartnerId.ofRepoId(record.getDestintion_BPartner_ID()))
				.destinationBPartnerBankAccountId(destinationBPBankAccountId)

				.documentNumber(record.getDocumentNo())
				.documentDate(TimeUtil.asInstant(record.getDateDoc()))
				.docTypeId(DocTypeId.ofRepoId(record.getC_DocType_ID()))

				.paymentDiscountAmountSum(record.getPaymentDiscountAmountSum())
				.remittedAmountSum(record.getRemittanceAmt())
				.remittedAmountCurrencyId(remittanceCurrencyId)

				.serviceFeeAmount(record.getServiceFeeAmount())
				.serviceFeeCurrencyId(CurrencyId.ofRepoIdOrNull(record.getServiceFeeAmount_Currency_ID()))

				.sendDate(TimeUtil.asInstant(record.getSendAt()))
				.additionalNotes(record.getAdditionalNotes())
				.lines(retrieveLines(remittanceAdviceId, remittanceCurrencyId))
				.build();
	}

	@NonNull
	private RemittanceAdviceLine toRemittanceAdviceLine(@NonNull final I_C_RemittanceAdvice_Line record, @NonNull final CurrencyId remittanceCurrencyId)
	{
		final CurrencyCode remittanceCurrencyCode = currencyDAO.getCurrencyCodeById(remittanceCurrencyId);

		final Function<BigDecimal, Amount> toAmountOrNull =
				(amountValue) -> amountValue != null ? Amount.of(amountValue, remittanceCurrencyCode) : null;

		return RemittanceAdviceLine.builder()
				.remittanceAdviceLineId(RemittanceAdviceLineId.ofRepoId(record.getC_RemittanceAdvice_Line_ID()))

				.remittanceAdviceId(RemittanceAdviceId.ofRepoId(record.getC_RemittanceAdvice_ID()))

				.invoiceIdentifier(record.getInvoiceIdentifier())

				.remittedAmount(Amount.of(record.getRemittanceAmt(), remittanceCurrencyCode))

				.invoiceGrossAmount(toAmountOrNull.apply(record.getInvoiceAmt()))

				.paymentDiscountAmount(toAmountOrNull.apply(record.getPaymentDiscountAmt()))

				.serviceFeeAmount(toAmountOrNull.apply(record.getServiceFeeAmount()))

				.serviceFeeVatRate(record.getServiceFeeVatRate())

				.dateInvoiced(TimeUtil.asInstant(record.getInvoiceDate()))

				.bpartnerIdentifier(BPartnerId.ofRepoIdOrNull(record.getC_BPartner_ID()))

				.externalInvoiceDocBaseType(record.getExternalInvoiceDocBaseType())
				.build();
	}

	@NonNull
	private List<RemittanceAdviceLine> retrieveLines(@NonNull final RemittanceAdviceId remittanceAdviceId, @NonNull final CurrencyId currencyId)
	{
		return queryBL.createQueryBuilder(I_C_RemittanceAdvice_Line.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_RemittanceAdvice_Line.COLUMN_C_RemittanceAdvice_ID, remittanceAdviceId.getRepoId())
				.create()
				.list()
				.stream()
				.map(line -> toRemittanceAdviceLine(line, currencyId))
				.collect(Collectors.toList());
	}
}
