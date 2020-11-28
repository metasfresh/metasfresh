package de.metas.payment.esr.invoice_gateway;

import static org.adempiere.model.InterfaceWrapperHelper.create;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Location;
import org.slf4j.Logger;
import org.slf4j.MDC.MDCCloseable;
import org.springframework.stereotype.Component;

import de.metas.banking.Bank;
import de.metas.banking.BankId;
import de.metas.banking.api.BankRepository;
import de.metas.banking.model.I_C_Payment_Request;
import de.metas.invoice_gateway.spi.esr.ESRPaymentInfoProvider;
import de.metas.invoice_gateway.spi.esr.model.ESRPaymentInfo;
import de.metas.invoice_gateway.spi.model.AddressInfo;
import de.metas.invoice_gateway.spi.model.InvoiceId;
import de.metas.invoice_gateway.spi.model.export.InvoiceToExport;
import de.metas.location.ILocationDAO;
import de.metas.logging.LogManager;
import de.metas.logging.TableRecordMDC;
import de.metas.payment.esr.ESRStringUtil;
import de.metas.payment.esr.model.I_C_BP_BankAccount;
import de.metas.util.Services;
import de.metas.common.util.CoalesceUtil;
import lombok.NonNull;

/*
 * #%L
 * de.metas.payment.esr
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

@Component
public class ESRInfoProviderImpl implements ESRPaymentInfoProvider
{
	private static final Logger logger = LogManager.getLogger(ESRInfoProviderImpl.class);
	private final ILocationDAO locationDAO = Services.get(ILocationDAO.class);
	private final BankRepository bankRepo;

	public ESRInfoProviderImpl(
			@NonNull final BankRepository bankRepo)
	{
		this.bankRepo = bankRepo;
	}

	@Override
	public ESRPaymentInfo provideCustomPayload(@NonNull final InvoiceToExport invoiceWithoutEsrInfo)
	{
		final InvoiceId invoiceId = invoiceWithoutEsrInfo.getId();

		final I_C_BPartner bpartnerRecord = loadOutOfTrx(invoiceWithoutEsrInfo.getBiller().getId(), I_C_BPartner.class);

		final String companyName = CoalesceUtil.coalesce(bpartnerRecord.getCompanyName(), bpartnerRecord.getName());

		final I_C_Payment_Request paymentRequestRecord = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Payment_Request.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Payment_Request.COLUMN_C_Invoice_ID, invoiceId)
				.create()
				.firstOnly(I_C_Payment_Request.class);

		if (paymentRequestRecord == null)
		{
			logger.debug("C_Invoice_ID has no C_Payment_Request; -> returning null");
			return null;
		}
		try (final MDCCloseable paymentRequestRecordMDC = TableRecordMDC.putTableRecordReference(paymentRequestRecord))
		{
			if (paymentRequestRecord.getC_BP_BankAccount_ID() <= 0)
			{
				logger.debug("C_Payment_Request for C_Invoice_ID has C_BP_BankAccount_ID={}; -> returning null", paymentRequestRecord.getC_BP_BankAccount_ID());
				return null;
			}

			final I_C_BP_BankAccount esrBankAccount = create(paymentRequestRecord.getC_BP_BankAccount(), I_C_BP_BankAccount.class);
			if (!esrBankAccount.isEsrAccount())
			{
				logger.debug("C_BP_BankAccount.isErsAccount=N; -> returning null", paymentRequestRecord.getC_BP_BankAccount_ID());
				return null;
			}

			final BankId esrBankId = BankId.ofRepoIdOrNull(esrBankAccount.getC_Bank_ID());

			final ESRPaymentInfo esrPaymentInfo = ESRPaymentInfo.builder()
					.referenceNumber(ESRStringUtil.formatReferenceNumber(paymentRequestRecord.getReference()))
					.codingLine(paymentRequestRecord.getFullPaymentString())
					.companyName(companyName)
					.addressInfo(createAddressInfo(esrBankId))
					.participantNumber(esrBankAccount.getESR_RenderedAccountNo())
					.build();

			return esrPaymentInfo;
		}
	}

	private AddressInfo createAddressInfo(@Nullable final BankId esrBankId)
	{
		if(esrBankId == null)
		{
			return null;
		}
		
		final Bank esrBank = bankRepo.getById(esrBankId);
		
		if(esrBank.getLocationId() == null)
		{
			return null;
		}
		
		final I_C_Location bankLocation = locationDAO.getById(esrBank.getLocationId());
		if(bankLocation == null)
		{
			return null;
		}

		return AddressInfo
				.builder()
				.street(bankLocation.getAddress1())
				.pobox(bankLocation.getPOBox())
				.city(bankLocation.getCity())
				.zip(bankLocation.getPostal())
				.state(bankLocation.getRegionName())
				.isoCountryCode(bankLocation.getC_Country().getCountryCode()) // C_Location.C_Country_ID is mandatory
				.build();
	}
}
