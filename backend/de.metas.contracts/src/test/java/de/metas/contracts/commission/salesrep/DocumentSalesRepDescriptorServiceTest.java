package de.metas.contracts.commission.salesrep;

import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import de.metas.lang.SOTrx;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2020 metas GmbH
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

class DocumentSalesRepDescriptorServiceTest
{
	private DocumentSalesRepDescriptorFactory documentSalesRepDescriptorFactory;
	private DocumentSalesRepDescriptorService documentSalesRepDescriptorService;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		documentSalesRepDescriptorService = new DocumentSalesRepDescriptorService();
		documentSalesRepDescriptorFactory = new DocumentSalesRepDescriptorFactory();
	}

	@ParameterizedTest(name = "BPartners should be correctly synced and valudated ")
	@CsvSource({
			"false, false, false, false, true",
			"false, false, false, true, true",
			"false, false, true, false, true",
			"false, false, true, true, true",
			"false, true, false, false, true",
			"false, true, false, true, false", // no sales rep existing in master data
			"false, true, true, false, true",
			"false, true, true, true, false", // no sales rep existing in master data
			"true, false, false, false, true",
			"true, false, false, true, true ",
			"true, false, true, false, true",
			"true, false, true, true, true",
			"true, true, false, false, true",
			"true, true, false, true, false", // sales rep requred but not connected in masterdata
			"true, true, true, false, true",
			"true, true, true, true, true",
	})
	void updateFromCustomer_validatesOK(
			boolean createSalesRep,
			boolean customerHasSalesRepRequired,
			boolean connectSalesRep,
			boolean invoiceSoTrx,
			boolean expectValid)
	{
		BPartnerTestRecords bpartnerTestRecords = BPartnerTestRecords.builder()
				.createSalesRep(createSalesRep)
				.connectSalesRep(connectSalesRep)
				.customerHasSalesRepRequired(customerHasSalesRepRequired)
				.build();

		final InvoiceTestRecord invoiceTestRecord = InvoiceTestRecord.builder()
				.soTrx(SOTrx.ofBoolean(invoiceSoTrx))
				.customerBPartnerId(bpartnerTestRecords.getCustomerBPartnerId())
				.salesRepBPartnerId(bpartnerTestRecords.getSalesRepBPartnerId())
				.salesPartnerRequired(false) // might be updated to true by method under test
				.salesPartnerCode("unrelatedSalesrepcode")
				.build();

		final DocumentSalesRepDescriptor invoiceDescriptor = documentSalesRepDescriptorFactory.forDocumentRecord(invoiceTestRecord.getInvoiceRecord());

		// invoke the method under test
		documentSalesRepDescriptorService.updateFromCustomer(invoiceDescriptor);

		bpartnerTestRecords.assertMatches(invoiceDescriptor);

		// also test validatesOK
		assertThat(invoiceDescriptor.validatesOK()).isEqualTo(expectValid);
	}

}
