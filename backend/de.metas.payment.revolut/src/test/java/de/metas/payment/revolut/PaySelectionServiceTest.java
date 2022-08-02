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

import de.metas.banking.api.BankRepository;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.payment.revolut.model.RecipientType;
import de.metas.payment.revolut.model.RevolutPaymentExport;
import de.metas.user.UserRepository;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Bank;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Currency;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_PaySelection;
import org.compiere.model.I_C_PaySelectionLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

public class PaySelectionServiceTest
{
	PaySelectionService paySelectionService;

	I_C_Location location;
	I_C_BPartner bPartner;
	I_C_BP_BankAccount bp_bankAccount;
	I_C_Bank bank;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		final BPartnerBL partnerBL = new BPartnerBL(new UserRepository());
		final BankRepository bankRepository = new BankRepository();

		paySelectionService = new PaySelectionService(bankRepository, partnerBL);
	}

	@Test
	public void compute()
	{
		//given
		final I_C_PaySelection paySelection = createPaySelectionMockData();

		//when
		final List<RevolutPaymentExport> mappedData = paySelectionService.computeRevolutPaymentExportList(paySelection);

		//then
		assertThat(mappedData.size()).isEqualTo(1);

		final RevolutPaymentExport export = mappedData.get(0);

		assertThat(export.getName()).isEqualTo(bPartner.getName());
		assertThat(export.getRecipientType()).isEqualTo(RecipientType.COMPANY);

		assertThat(export.getIBAN()).isEqualTo(bp_bankAccount.getIBAN());
		assertThat(export.getSwiftCode()).isEqualTo(bank.getSwiftCode());

		assertThat(export.getAmount()).isEqualTo(Amount.of(BigDecimal.TEN, CurrencyCode.EUR));

		assertThat(export.getAddressLine1()).isEqualTo(location.getAddress1());
		assertThat(export.getAddressLine2()).isEqualTo(location.getAddress2());
		assertThat(export.getRegionName()).isEqualTo(location.getRegionName());
		assertThat(export.getPostalCode()).isEqualTo(location.getPostal());
	}

	private I_C_PaySelection createPaySelectionMockData()
	{
		final I_C_Currency currency = newInstance(I_C_Currency.class);
		currency.setISO_Code(CurrencyCode.EUR.toThreeLetterCode());
		save(currency);

		final I_C_Country country = newInstance(I_C_Country.class);
		save(country);

		location = newInstance(I_C_Location.class);
		location.setC_Country_ID(country.getC_Country_ID());
		location.setRegionName("regionName");
		location.setAddress1("addr1");
		location.setAddress2("addr2");
		location.setCity("city");
		location.setPostal("postal");
		save(location);

		bPartner = newInstance(I_C_BPartner.class);
		bPartner.setName("bpName");
		bPartner.setIsCompany(true);
		save(bPartner);

		final I_C_BPartner_Location bPartnerLocation = newInstance(I_C_BPartner_Location.class);
		bPartnerLocation.setC_BPartner_ID(bPartner.getC_BPartner_ID());
		bPartnerLocation.setC_Location_ID(location.getC_Location_ID());
		save(bPartnerLocation);

		bank = newInstance(I_C_Bank.class);
		bank.setName("bankName");
		bank.setSwiftCode("swiftCode");
		save(bank);

		bp_bankAccount = newInstance(I_C_BP_BankAccount.class);
		bp_bankAccount.setC_BPartner_ID(bPartner.getC_BPartner_ID());
		bp_bankAccount.setC_Bank_ID(bank.getC_Bank_ID());
		bp_bankAccount.setC_Currency_ID(currency.getC_Currency_ID());
		bp_bankAccount.setIBAN("iban");
		bp_bankAccount.setRoutingNo("routingNo");
		save(bp_bankAccount);

		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setC_BPartner_ID(bPartner.getC_BPartner_ID());
		invoice.setC_BPartner_Location_ID(bPartnerLocation.getC_BPartner_Location_ID());
		save(invoice);

		final I_C_PaySelection paySelection = newInstance(I_C_PaySelection.class);
		saveRecord(paySelection);

		final I_C_PaySelectionLine paySelectionLine = newInstance(I_C_PaySelectionLine.class);
		paySelectionLine.setC_PaySelection_ID(paySelection.getC_PaySelection_ID());
		paySelectionLine.setC_Invoice_ID(invoice.getC_Invoice_ID());
		paySelectionLine.setC_BP_BankAccount_ID(bp_bankAccount.getC_BP_BankAccount_ID());
		paySelectionLine.setPayAmt(BigDecimal.TEN);
		save(paySelectionLine);

		return paySelection;
	}
}
