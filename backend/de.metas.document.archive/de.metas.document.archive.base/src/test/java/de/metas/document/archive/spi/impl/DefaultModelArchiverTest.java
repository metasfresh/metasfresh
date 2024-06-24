/*
 * #%L
 * de.metas.document.archive.base
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

package de.metas.document.archive.spi.impl;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.BPartnerPrintFormatRepository;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.document.DocTypeId;
import de.metas.invoice.service.InvoiceDocumentReportAdvisor;
import de.metas.invoicecandidate.model.I_C_Invoice;
import de.metas.report.DefaultPrintFormatsRepository;
import de.metas.report.DocTypePrintOptionsRepository;
import de.metas.report.DocumentPrintOptionDescriptorsRepository;
import de.metas.report.DocumentReportAdvisorUtil;
import de.metas.report.PrintFormatRepository;
import de.metas.user.UserRepository;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.archive.api.ArchiveResult;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.Assertions;
import org.compiere.model.I_AD_Archive;
import org.compiere.model.I_Test;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static de.metas.document.archive.spi.impl.MockedDocumentReportService.MOCKED_REPORT_FILENAME;

class DefaultModelArchiverTest
{
	private DefaultModelArchiverTestHelper helper;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		helper = new DefaultModelArchiverTestHelper();

		Env.setClientId(Env.getCtx(), helper.createClient());
	}

	private MockedDocumentReportService createMockedDocumentReportService()
	{
		final DocumentReportAdvisorUtil util = new DocumentReportAdvisorUtil(
				new BPartnerBL(new UserRepository()),
				new PrintFormatRepository(),
				new DefaultPrintFormatsRepository(), new BPartnerPrintFormatRepository());

		return new MockedDocumentReportService(
				ImmutableList.of(
						new InvoiceDocumentReportAdvisor(util)
				),
				new DocumentPrintOptionDescriptorsRepository(),
				new DocTypePrintOptionsRepository(),
				util);

	}

	private ArchiveResult archive(final Object record)
	{
		final DefaultModelArchiver archiver = DefaultModelArchiver.of(record);
		archiver.setDocumentReportService(createMockedDocumentReportService());
		return archiver.archive();
	}

	@Test
	public void archiveInvoice()
	{
		final BPartnerId bpartnerId = helper.createBPartner("ro_RO");
		final DocTypeId docTypeId = helper.docType()
				.printFormatId(helper.printFormat()
						.printProcessId(helper.process().build())
						.build())
				.build();

		final I_C_Invoice invoice = InterfaceWrapperHelper.create(Env.getCtx(), I_C_Invoice.class, ITrx.TRXNAME_None);
		invoice.setDocumentNo("ExpectedDocumentNo");
		invoice.setC_BPartner_ID(bpartnerId.getRepoId());
		invoice.setC_DocType_ID(docTypeId.getRepoId());
		invoice.setC_Async_Batch_ID(1);
		invoice.setC_BPartner_Location_ID(1);
		InterfaceWrapperHelper.save(invoice);

		final ArchiveResult archiveResult = archive(invoice);
		final I_AD_Archive archiveRecord = archiveResult.getArchiveRecord();
		Assertions.assertThat(archiveRecord.getAD_Table_ID()).isEqualTo(InterfaceWrapperHelper.getTableId(I_C_Invoice.class));
		Assertions.assertThat(archiveRecord.getRecord_ID()).isEqualTo(invoice.getC_Invoice_ID());
		Assertions.assertThat(archiveRecord.getC_BPartner_ID()).isEqualTo(invoice.getC_BPartner_ID());
		Assertions.assertThat(archiveRecord.getAD_Language()).isEqualTo("ro_RO");
		Assertions.assertThat(archiveRecord.isReport()).isFalse();
		Assertions.assertThat(archiveRecord.getC_Async_Batch_ID()).isEqualTo(invoice.getC_Async_Batch_ID());
		Assertions.assertThat(archiveRecord.getC_Async_Batch_ID()).isEqualTo(invoice.getC_Async_Batch_ID());
	}

	@Test
	public void archiveTestRecord()
	{
		final BPartnerId bpartnerId = helper.createBPartner("ro_RO");
		helper.docOutboundConfig()
				.tableName(I_Test.Table_Name)
				.printFormatId(helper.printFormat()
						.printProcessId(helper.process().build())
						.build())
				.build();

		final I_Test record = InterfaceWrapperHelper.newInstance(I_Test.class);
		record.setC_BPartner_ID(bpartnerId.getRepoId());
		record.setC_Async_Batch_ID(1);
		InterfaceWrapperHelper.save(record);

		final ArchiveResult archiveResult = archive(record);
		final I_AD_Archive archiveRecord = archiveResult.getArchiveRecord();
		Assertions.assertThat(archiveRecord.getAD_Table_ID()).isEqualTo(InterfaceWrapperHelper.getTableId(I_Test.class));
		Assertions.assertThat(archiveRecord.getRecord_ID()).isEqualTo(record.getTest_ID());
		Assertions.assertThat(archiveRecord.getC_BPartner_ID()).isEqualTo(record.getC_BPartner_ID());
		Assertions.assertThat(archiveRecord.getAD_Language()).isEqualTo("ro_RO");
		Assertions.assertThat(archiveRecord.isReport()).isFalse();
		Assertions.assertThat(archiveRecord.getName()).isEqualTo(MOCKED_REPORT_FILENAME);
		Assertions.assertThat(archiveRecord.getC_Async_Batch_ID()).isEqualTo(record.getC_Async_Batch_ID());
	}
}