package de.metas.contracts.commission.salesrep;

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

class DocumentSalesRepDescriptorFactoryTest_Invoice
{
	private DocumentSalesRepDescriptorFactory documentSalesRepDescriptorFactory;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		documentSalesRepDescriptorFactory = new DocumentSalesRepDescriptorFactory();
	}

	@ParameterizedTest(name = "Descriptor should match C_Order with soTrx={0}, existing-salesrep={1}, salesrep-required={2} and salesRepcode={3}")
	@CsvSource({
			"false, false, false, salesRepcode123",
			"false, false, true, salesRepcode123",
			"false, true, false, salesRepcode123",
			"false, true, true, ", // salesPartnerCode == null
			"true, false, false, salesRepcode123",
			"true, false, true, salesRepcode123",
			"true, true, false, salesRepcode123",
			"true, true, true, ", // salesPartnerCode == null
	})
	void forDocumentRecord_C_Invoice(
			boolean soTrx,
			boolean createSalesRep,
			boolean invoiceSalesPartnerRequired,
			String invoiceSalesPartnerCode)
	{
		final BPartnerTestRecords testRecords = BPartnerTestRecords.builder().build();

		final InvoiceTestRecord invoiceTestRecord = InvoiceTestRecord.builder()
				.soTrx(SOTrx.ofBoolean(soTrx))
				.customerBPartnerId(testRecords.getCustomerBPartnerId())
				.salesRepBPartnerId(testRecords.getSalesRepBPartnerId())
				.salesPartnerRequired(invoiceSalesPartnerRequired)
				.salesPartnerCode(invoiceSalesPartnerCode)
				.build();

		final DocumentSalesRepDescriptor result = documentSalesRepDescriptorFactory.forDocumentRecord(invoiceTestRecord.getInvoiceRecord());
		invoiceTestRecord.assertMatches(result);
	}

}
