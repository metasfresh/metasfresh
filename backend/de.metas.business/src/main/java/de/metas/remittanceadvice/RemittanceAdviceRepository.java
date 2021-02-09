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
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_RemittanceAdvice;
import org.compiere.model.I_C_RemittanceAdvice_Line;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class RemittanceAdviceRepository
{
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

		toRemittanceAdviceLine(record);
	}

	private I_C_RemittanceAdvice buildRemittanceAdviceRecord(@NonNull final CreateRemittanceAdviceRequest createRemittanceAdviceRequest)
	{
		final I_C_RemittanceAdvice record = InterfaceWrapperHelper.newInstance(I_C_RemittanceAdvice.class);

		record.setAD_Org_ID(OrgId.toRepoId(createRemittanceAdviceRequest.getOrgId()));
		record.setSource_BPartner_ID(BPartnerId.toRepoId(createRemittanceAdviceRequest.getSourceBPartnerId()));
		record.setSource_BP_BankAccount_ID(BPartnerBankAccountId.toRepoId(createRemittanceAdviceRequest.getSourceBPartnerBankAccountId()));
		record.setDestintion_BPartner_ID(BPartnerId.toRepoId(createRemittanceAdviceRequest.getDestinationBPartnerId()));
		record.setDestination_BP_BankAccount_ID(BPartnerBankAccountId.toRepoId(createRemittanceAdviceRequest.getDestinationBPartnerBankAccountId()));
		record.setExternalDocumentNo(createRemittanceAdviceRequest.getDocumentNumber());
		record.setSendAt(TimeUtil.asTimestamp(createRemittanceAdviceRequest.getSendDate()));
		record.setDateDoc(TimeUtil.asTimestamp(createRemittanceAdviceRequest.getDocumentDate()));
		record.setC_DocType_ID(DocTypeId.toRepoId(createRemittanceAdviceRequest.getDocTypeId()));
		record.setRemittanceAmt(createRemittanceAdviceRequest.getRemittedAmountSum());
		record.setRemittanceAmt_Currency_ID(CurrencyId.toRepoId(createRemittanceAdviceRequest.getRemittedAmountCurrencyId()));
		record.setServiceFeeAmount(createRemittanceAdviceRequest.getServiceFeeAmount());
		record.setServiceFeeAmount_Currency_ID(CurrencyId.toRepoId(createRemittanceAdviceRequest.getServiceFeeCurrencyId()));
		record.setAdditionalNotes(createRemittanceAdviceRequest.getAdditionalNotes());
		record.setPaymentDiscountAmountSum(createRemittanceAdviceRequest.getPaymentDiscountAmountSum());
		record.setC_Payment_Doctype_Target_ID(DocTypeId.toRepoId(createRemittanceAdviceRequest.getDocTypeId()));

		return record;
	}

	private I_C_RemittanceAdvice_Line buildRemittanceAdviceLineRecord(
			@NonNull final CreateRemittanceAdviceLineRequest remittanceAdviceLineRequest)
	{
		final I_C_RemittanceAdvice_Line record = InterfaceWrapperHelper.newInstance(I_C_RemittanceAdvice_Line.class);

		record.setC_RemittanceAdvice_ID(remittanceAdviceLineRequest.getRemittanceAdviceId().getRepoId());
		record.setInvoiceIdentifier(remittanceAdviceLineRequest.getInvoiceIdentifier());
		record.setRemittanceAmt(remittanceAdviceLineRequest.getRemittedAmount());
		record.setInvoiceDate(TimeUtil.asTimestamp(remittanceAdviceLineRequest.getDateInvoiced()));
		record.setC_BPartner_ID(BPartnerId.toRepoId(remittanceAdviceLineRequest.getBpartnerIdentifier()));
		record.setExternalInvoiceDocBaseType(remittanceAdviceLineRequest.getExternalInvoiceDocBaseType());
		record.setInvoiceAmt(remittanceAdviceLineRequest.getInvoiceGrossAmount());
		record.setPaymentDiscountAmt(remittanceAdviceLineRequest.getPaymentDiscountAmount());
		record.setServiceFeeAmount(remittanceAdviceLineRequest.getServiceFeeAmount());
		record.setServiceFeeVatRate(remittanceAdviceLineRequest.getServiceFeeVatRate());
		return record;
	}

	private RemittanceAdvice toRemittanceAdvice(@NonNull final I_C_RemittanceAdvice record)
	{
		return RemittanceAdvice.builder()
				.remittanceAdviceId(RemittanceAdviceId.ofRepoId(record.getC_RemittanceAdvice_ID()))
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.clientId(ClientId.ofRepoId(record.getAD_Client_ID()))
				.sourceBPartnerId(BPartnerId.ofRepoId(record.getSource_BPartner_ID()))
				.sourceBPartnerBankAccountId(BPartnerBankAccountId.ofRepoId(record.getSource_BPartner_ID(), record.getSource_BP_BankAccount_ID()))
				.destinationBPartnerId(BPartnerId.ofRepoId(record.getDestintion_BPartner_ID()))
				.destinationBPartnerBankAccountId(BPartnerBankAccountId.ofRepoId(record.getDestintion_BPartner_ID(), record.getDestination_BP_BankAccount_ID()))
				.documentNumber(record.getExternalDocumentNo())
				.sendDate(TimeUtil.asInstant(record.getSendAt()))
				.documentDate(TimeUtil.asInstant(record.getDateDoc()))
				.docTypeId(DocTypeId.ofRepoId(record.getC_DocType_ID()))
				.remittedAmountSum(record.getRemittanceAmt())
				.remittedAmountCurrencyId(CurrencyId.ofRepoId(record.getRemittanceAmt_Currency_ID()))
				.serviceFeeAmount(record.getServiceFeeAmount())
				.serviceFeeCurrencyId(CurrencyId.ofRepoIdOrNull(record.getServiceFeeAmount_Currency_ID()))
				.paymentDiscountAmountSum(record.getPaymentDiscountAmountSum())
				.additionalNotes(record.getAdditionalNotes())
				.build();
	}

	private RemittanceAdviceLine toRemittanceAdviceLine(@NonNull final I_C_RemittanceAdvice_Line record)
	{
		return RemittanceAdviceLine.builder()
				.remittanceAdviceLineId(RemittanceAdviceLineId.ofRepoId(record.getC_RemittanceAdvice_Line_ID()))
				.remittanceAdviceId(RemittanceAdviceId.ofRepoId(record.getC_RemittanceAdvice_ID()))
				.invoiceIdentifier(record.getInvoiceIdentifier())
				.remittedAmount(record.getRemittanceAmt())
				.dateInvoiced(TimeUtil.asInstant(record.getInvoiceDate()))
				.bpartnerIdentifier(BPartnerId.ofRepoId(record.getC_BPartner_ID()))
				.externalInvoiceDocBaseType(record.getExternalInvoiceDocBaseType())
				.invoiceGrossAmount(record.getInvoiceAmt())
				.paymentDiscountAmount(record.getPaymentDiscountAmt())
				.serviceFeeAmount(record.getServiceFeeAmount())
				.serviceFeeVatRate(record.getServiceFeeVatRate())
				.build();
	}
}
