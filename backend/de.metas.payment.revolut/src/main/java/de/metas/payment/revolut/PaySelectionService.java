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

package de.metas.payment.revolut;

import com.google.common.collect.ImmutableList;
import de.metas.banking.Bank;
import de.metas.banking.BankAccount;
import de.metas.banking.BankAccountId;
import de.metas.banking.api.BankRepository;
import de.metas.banking.api.IBPBankAccountDAO;
import de.metas.banking.payment.IPaySelectionDAO;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyDAO;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.location.CountryId;
import de.metas.location.ILocationDAO;
import de.metas.location.LocationId;
import de.metas.organization.OrgId;
import de.metas.payment.revolut.model.RecipientType;
import de.metas.payment.revolut.model.RevolutPaymentExport;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_PaySelection;
import org.compiere.model.I_C_PaySelectionLine;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaySelectionService
{
	private final ILocationDAO locationDAO = Services.get(ILocationDAO.class);
	private final ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);
	private final IBPBankAccountDAO bpBankAccountDAO = Services.get(IBPBankAccountDAO.class);
	private final IPaySelectionDAO paySelectionDAO = Services.get(IPaySelectionDAO.class);
	private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);

	private final IBPartnerBL bpartnerBL;
	private final BankRepository bankRepository;

	PaySelectionService(
			@NonNull final BankRepository bankRepository,
			@NonNull final IBPartnerBL bpartnerBL)
	{
		this.bankRepository = bankRepository;
		this.bpartnerBL = bpartnerBL;
	}

	@NonNull
	public List<RevolutPaymentExport> computeRevolutPaymentExportList(@NonNull final I_C_PaySelection paySelection)
	{
		return paySelectionDAO.retrievePaySelectionLines(paySelection)
				.stream()
				.map(this::toRevolutExportRequest)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private RevolutPaymentExport toRevolutExportRequest(@NonNull final I_C_PaySelectionLine line)
	{
		final RevolutPaymentExport.RevolutPaymentExportBuilder revolutPaymentExportBuilder = RevolutPaymentExport.builder();

		final I_C_Invoice invoice = invoiceDAO.getByIdInTrx(InvoiceId.ofRepoId(line.getC_Invoice_ID()));

		final BPartnerId bPartnerId = BPartnerId.ofRepoId(invoice.getC_BPartner_ID());
		final I_C_BPartner bPartner = bpartnerBL.getById(bPartnerId);

		revolutPaymentExportBuilder.name(bPartner.getName());
		revolutPaymentExportBuilder.recipientType(bPartner.isCompany() ? RecipientType.COMPANY : RecipientType.INDIVIDUAL);

		final BPartnerLocationId bPartnerLocationId = BPartnerLocationId.ofRepoId(bPartnerId, invoice.getC_BPartner_Location_ID());

		final I_C_BPartner_Location bPartnerLocation = Optional.ofNullable(bpartnerDAO.getBPartnerLocationByIdInTrx(bPartnerLocationId))
				.orElseThrow(() -> new AdempiereException("No bPartnerLocation found for bPartnerLocationId")
						.appendParametersToMessage()
						.setParameter("bPartnerLocationId", bPartnerLocationId));

		final I_C_Location location = locationDAO.getById(LocationId.ofRepoId(bPartnerLocation.getC_Location_ID()));
		attachLocation(revolutPaymentExportBuilder, location);

		final BankAccountId bankAccountId = BankAccountId.ofRepoIdOrNull((line.getC_BP_BankAccount_ID()));

		if (bankAccountId == null)
		{
			throw new AdempiereException("Bank account missing from paySelectionLine")
					.appendParametersToMessage()
					.setParameter("paySelectionLineId", line.getC_PaySelectionLine_ID());
		}

		final BankAccount bpBankAccount = Optional.ofNullable(bpBankAccountDAO.getById(bankAccountId))
				.orElseThrow(() -> new AdempiereException("No bankAccount found for id")
						.appendParametersToMessage()
						.setParameter("bankAccountId", bankAccountId));

		if(bpBankAccount.getBankId() != null)
		{
			final Bank bank = bankRepository.getById(bpBankAccount.getBankId());
			if (bank.getLocationId() != null)
			{
				final CountryId countryId = locationDAO.getCountryIdByLocationId(bank.getLocationId());
				revolutPaymentExportBuilder.recipientBankCountryId(countryId);
			}
		}
		final CurrencyCode currencyCode = currencyDAO.getCurrencyCodeById(bpBankAccount.getCurrencyId());
		final Amount amount = Amount.of(line.getPayAmt(), currencyCode);

		final TableRecordReference recordRef = TableRecordReference.of(I_C_PaySelectionLine.Table_Name, line.getC_PaySelection_ID());

		return revolutPaymentExportBuilder
				.recordReference(recordRef)
				.orgId(OrgId.ofRepoId(line.getAD_Org_ID()))
				.amount(amount)
				.paymentReference(line.getReference())
				.accountNo(bpBankAccount.getAccountNo())
				.routingNo(bpBankAccount.getRoutingNo())
				.IBAN(bpBankAccount.getIBAN())
				.SwiftCode(bpBankAccount.getRoutingNo())
				.build();
	}

	private void attachLocation(
			@NonNull final RevolutPaymentExport.RevolutPaymentExportBuilder builder,
			@NonNull final I_C_Location location)
	{
		builder.recipientCountryId(CountryId.ofRepoId(location.getC_Country_ID()));
		builder.regionName(location.getRegionName());
		builder.addressLine1(location.getAddress1());
		builder.addressLine2(location.getAddress2());
		builder.city(location.getCity());
		builder.postalCode(location.getPostal());
	}
}
