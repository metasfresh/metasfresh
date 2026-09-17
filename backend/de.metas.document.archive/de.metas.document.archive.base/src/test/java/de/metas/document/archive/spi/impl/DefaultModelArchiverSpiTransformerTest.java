/*
 * #%L
 * de.metas.document.archive.base
 * %%
 * Copyright (C) 2026 metas GmbH
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
import de.metas.document.DocBaseType;
import de.metas.document.DocTypeId;
import de.metas.document.archive.config.DocOutboundConfigService;
import de.metas.document.archive.spi.IArchiveReportBytesTransformer;
import de.metas.invoice.service.InvoiceDocumentReportAdvisor;
import de.metas.report.DefaultPrintFormatsRepository;
import de.metas.report.DocTypePrintOptionsRepository;
import de.metas.report.DocumentPrintOptionDescriptorsRepository;
import de.metas.report.DocumentReportAdvisorUtil;
import de.metas.report.PrintFormatRepository;
import de.metas.user.UserRepository;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.util.Env;
import de.metas.invoicecandidate.model.I_C_Invoice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests that {@link DefaultModelArchiver} invokes the registered {@link IArchiveReportBytesTransformer}
 * SPI at the correct seam: after report bytes are produced and before {@code ArchiveBL.archive()} persists them.
 *
 * <p>The test registers a recording transformer via
 * {@link SpringContextHolder#registerJUnitBean(Object)}, archives a C_Invoice,
 * and asserts that the transformer was called exactly once with the correct
 * {@link TableRecordReference} (pointing to the C_Invoice row).
 *
 * <p>The transformer returns the input bytes unchanged so the rest of the archive
 * flow works normally (this test is not about ZUGFeRD embedding; that is covered by
 * {@code ZugferdArchiveReportBytesTransformerTest} in de.metas.einvoice.base).
 */
class DefaultModelArchiverSpiTransformerTest
{
	private DefaultModelArchiverTestHelper helper;

	/**
	 * A transformer that records every invocation for later assertion.
	 * Returns the input bytes unchanged.
	 */
	private static class RecordingTransformer implements IArchiveReportBytesTransformer
	{
		final List<TableRecordReference> capturedRefs = new ArrayList<>();
		/** Lengths of the byte arrays passed in — proves the report engine produced actual data. */
		final List<Integer> capturedByteLengths = new ArrayList<>();

		@Override
		public byte[] transform(@NonNull final TableRecordReference recordRef, final byte[] reportBytes)
		{
			capturedRefs.add(recordRef);
			capturedByteLengths.add(reportBytes.length);
			return reportBytes; // pass-through
		}
	}

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		helper = new DefaultModelArchiverTestHelper();
		Env.setClientId(Env.getCtx(), helper.createClient());
		SpringContextHolder.registerJUnitBean(DocOutboundConfigService.newInstanceForUnitTesting());
	}

	private MockedDocumentReportService createMockedDocumentReportService()
	{
		final DocumentReportAdvisorUtil util = new DocumentReportAdvisorUtil(
				new BPartnerBL(new UserRepository()),
				new PrintFormatRepository(),
				new DefaultPrintFormatsRepository(), new BPartnerPrintFormatRepository());

		return new MockedDocumentReportService(
				ImmutableList.of(new InvoiceDocumentReportAdvisor(util)),
				new DocumentPrintOptionDescriptorsRepository(),
				new DocTypePrintOptionsRepository(),
				util);
	}

	@Test
	void archive_invokesRegisteredTransformerWithCorrectRecordRef()
	{
		// Given: a RecordingTransformer registered in the Spring context
		final RecordingTransformer transformer = new RecordingTransformer();
		SpringContextHolder.registerJUnitBean(IArchiveReportBytesTransformer.class, transformer);

		// And: a C_Invoice record
		final BPartnerId bpartnerId = helper.createBPartner("de_DE");
		final DocTypeId docTypeId = helper.docType()
				.printFormatId(helper.printFormat()
						.printProcessId(helper.process().build())
						.build())
				.docBaseType(DocBaseType.SalesInvoice)
				.build();

		final I_C_Invoice invoice = InterfaceWrapperHelper.create(Env.getCtx(), I_C_Invoice.class, ITrx.TRXNAME_None);
		invoice.setDocumentNo("SPT-TRANSFORMER-TEST");
		invoice.setC_BPartner_ID(bpartnerId.getRepoId());
		invoice.setC_DocType_ID(docTypeId.getRepoId());
		invoice.setC_Async_Batch_ID(1);
		invoice.setC_BPartner_Location_ID(1);
		InterfaceWrapperHelper.save(invoice);

		// When: archive() is called
		final DefaultModelArchiver archiver = DefaultModelArchiver.of(invoice);
		archiver.setDocumentReportService(createMockedDocumentReportService());
		archiver.archive();

		// Then: the transformer was invoked exactly once
		assertThat(transformer.capturedRefs)
				.as("IArchiveReportBytesTransformer must be invoked exactly once by DefaultModelArchiver")
				.hasSize(1);

		// And: with the correct C_Invoice record ref
		final TableRecordReference capturedRef = transformer.capturedRefs.get(0);
		assertThat(capturedRef.getTableName())
				.as("Transformer must receive C_Invoice table name")
				.isEqualTo(de.metas.invoicecandidate.model.I_C_Invoice.Table_Name);
		assertThat(capturedRef.getRecord_ID())
				.as("Transformer must receive the correct record ID")
				.isEqualTo(invoice.getC_Invoice_ID());

		// And: the report engine produced non-empty bytes (the SPI seam sits AFTER byte production)
		assertThat(transformer.capturedByteLengths.get(0))
				.as("Report bytes passed to transformer must be non-empty")
				.isGreaterThan(0);
	}

	@Test
	void archive_withoutTransformerBean_worksNormally()
	{
		// Given: no IArchiveReportBytesTransformer registered (use the default no-op path)
		// (nothing to register — SpringContextHolder returns null for unregistered beans)

		// And: a C_Invoice record
		final BPartnerId bpartnerId = helper.createBPartner("de_DE");
		final DocTypeId docTypeId = helper.docType()
				.printFormatId(helper.printFormat()
						.printProcessId(helper.process().build())
						.build())
				.docBaseType(DocBaseType.SalesInvoice)
				.build();

		final I_C_Invoice invoice = InterfaceWrapperHelper.create(Env.getCtx(), I_C_Invoice.class, ITrx.TRXNAME_None);
		invoice.setDocumentNo("SPT-NOTRANSFORMER-TEST");
		invoice.setC_BPartner_ID(bpartnerId.getRepoId());
		invoice.setC_DocType_ID(docTypeId.getRepoId());
		invoice.setC_Async_Batch_ID(1);
		invoice.setC_BPartner_Location_ID(1);
		InterfaceWrapperHelper.save(invoice);

		// When + Then: archive() must complete without error even with no transformer
		final DefaultModelArchiver archiver = DefaultModelArchiver.of(invoice);
		archiver.setDocumentReportService(createMockedDocumentReportService());
		archiver.archive();
		// No assertion needed — the point is it doesn't throw
	}
}
