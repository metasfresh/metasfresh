/*
 * #%L
 * de.metas.business
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

package de.metas.invoice.export;

import de.metas.bpartner.BPartnerId;
import de.metas.business.BusinessTestHelper;
import de.metas.business.TestInvoice;
import de.metas.invoice.detail.InvoiceWithDetailsRepository;
import de.metas.lang.SOTrx;
import de.metas.organization.OrgId;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.Invoice440RequestConversionService;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.XmlRequest;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Invoice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

public class HealthcareXMLAttachedToInvoiceListenerTest
{
	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();



	}

	@Test
	void extractInvoiceDetails()
	{
		final InputStream inputStream = getClass().getResourceAsStream("/de/metas/invoice/export/md_440_tp_kvg_de.xml");
		//final RequestType xmlInvoice = JaxbUtil.unmarshalToJaxbElement(inputStream, RequestType.class).getValue();

		final Invoice440RequestConversionService requestConverter = new Invoice440RequestConversionService();
		final XmlRequest xRequest = requestConverter.toCrossVersionRequest(inputStream);

		final OrgId orgId = BusinessTestHelper.createOrgWithTimeZone();
		final I_C_Invoice invoiceRecord = TestInvoice.builder()
				.orgId(orgId)
				.soTrx(SOTrx.SALES)
				.customerId(BPartnerId.ofRepoId(10)).build()
				.createInvoiceRecord().getInvoiceRecord();

		// when
		new HealthcareXMLToInvoiceDetailPersister(new InvoiceWithDetailsRepository()).extractInvoiceDetails(xRequest, invoiceRecord);
	}
}
