package de.metas.dunning.process;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import de.metas.dunning.DunningDocId;
import de.metas.dunning.invoice.DunningService;
import de.metas.common.util.time.SystemTime;
import de.metas.dunning.model.I_C_DunningDoc;
import de.metas.util.Services;
import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Archive;
import org.compiere.model.I_C_Invoice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

import java.io.ByteArrayOutputStream;
import javax.annotation.Nullable;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import static de.metas.dunning.invoice.DunningDocTestFixtures.addInvoiceLine;
import static de.metas.dunning.invoice.DunningDocTestFixtures.createDunningDoc;
import static de.metas.dunning.invoice.DunningDocTestFixtures.createInvoice;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.dunning
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

/**
 * Tests the part of the "dunning letter with attached invoice PDFs" action that follows the Jasper run:
 * appending the last archived PDF of every dunned invoice to the dunning letter PDF.
 * <p>
 * The PDFs are real (generated with iText); each source document is recognizable by the page width of its pages.
 */
class C_DunningDoc_JasperWithInvoicePDFsStrategyTest
{
	/** The tests call {@code appendDunnedInvoicePdfs}, which does not run the Jasper process. */
	private static final int UNUSED_JASPER_PROCESS_ID = -1;
	private static final float DUNNING_PAGE_WIDTH = 300;
	private static final float INVOICE1_PAGE_WIDTH = 400;
	private static final float INVOICE2_PAGE_WIDTH = 500;
	private static final float INVOICE3_PAGE_WIDTH = 600;

	private C_DunningDoc_JasperWithInvoicePDFsStrategy strategy;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		SpringContextHolder.registerJUnitBean(new DunningService());

		strategy = new C_DunningDoc_JasperWithInvoicePDFsStrategy(UNUSED_JASPER_PROCESS_ID);
	}

	@Test
	void invoicesWithArchive_areAppendedAfterTheDunningPdf() throws IOException
	{
		final I_C_Invoice invoice1 = createInvoiceWithArchive(INVOICE1_PAGE_WIDTH, 2);
		final I_C_Invoice invoice2 = createInvoiceWithArchive(INVOICE2_PAGE_WIDTH, 1);
		final DunningDocId dunningDocId = createDunningDocFor(invoice2, invoice1);

		final Resource result = strategy.appendDunnedInvoicePdfs(pdf(DUNNING_PAGE_WIDTH, 1), dunningDocId);

		// DunningService.retrieveDunnedInvoices does not define an order, so only the dunning PDF's position is pinned
		final List<Float> pageWidths = pageWidths(result);
		assertThat(pageWidths.get(0)).isEqualTo(DUNNING_PAGE_WIDTH);
		assertThat(pageWidths.subList(1, pageWidths.size())).containsExactlyInAnyOrder(INVOICE1_PAGE_WIDTH, INVOICE1_PAGE_WIDTH, INVOICE2_PAGE_WIDTH);
	}

	@Test
	void invoiceWithoutArchive_isSkipped_otherInvoicesAreStillAttached() throws IOException
	{
		final I_C_Invoice invoice1 = createInvoiceWithArchive(INVOICE1_PAGE_WIDTH, 1);
		final I_C_Invoice invoiceWithoutArchive = createInvoice();
		final I_C_Invoice invoice3 = createInvoiceWithArchive(INVOICE3_PAGE_WIDTH, 1);
		final DunningDocId dunningDocId = createDunningDocFor(invoice1, invoiceWithoutArchive, invoice3);

		final Resource result = strategy.appendDunnedInvoicePdfs(pdf(DUNNING_PAGE_WIDTH, 1), dunningDocId);

		assertThat(pageWidths(result)).containsExactly(DUNNING_PAGE_WIDTH, INVOICE1_PAGE_WIDTH, INVOICE3_PAGE_WIDTH);
	}

	@Test
	void noInvoiceHasAnArchive_resultIsTheDunningPdfOnly() throws IOException
	{
		final DunningDocId dunningDocId = createDunningDocFor(createInvoice());

		final Resource result = strategy.appendDunnedInvoicePdfs(pdf(DUNNING_PAGE_WIDTH, 2), dunningDocId);

		assertThat(pageWidths(result)).containsExactly(DUNNING_PAGE_WIDTH, DUNNING_PAGE_WIDTH);
	}

	@Test
	void invoiceWithSeveralArchives_onlyTheLastArchiveIsAttached() throws IOException
	{
		final I_C_Invoice invoice = createInvoice();
		final Instant now = Instant.parse("2026-10-28T10:00:00Z");
		// the newest archive is created in the middle, so neither insertion order nor id order is "last"
		createArchive(invoice, INVOICE2_PAGE_WIDTH, 1, now);
		createArchive(invoice, INVOICE3_PAGE_WIDTH, 1, now.plusSeconds(60));
		createArchive(invoice, INVOICE1_PAGE_WIDTH, 1, now.minusSeconds(60));
		final DunningDocId dunningDocId = createDunningDocFor(invoice);

		final Resource result = strategy.appendDunnedInvoicePdfs(pdf(DUNNING_PAGE_WIDTH, 1), dunningDocId);

		assertThat(pageWidths(result)).containsExactly(DUNNING_PAGE_WIDTH, INVOICE3_PAGE_WIDTH);
	}

	@Test
	void archiveOfAnInvoiceThatIsNotDunned_isNotAttached() throws IOException
	{
		final I_C_Invoice invoice1 = createInvoiceWithArchive(INVOICE1_PAGE_WIDTH, 1);
		createInvoiceWithArchive(INVOICE2_PAGE_WIDTH, 1); // not part of the dunning doc
		final DunningDocId dunningDocId = createDunningDocFor(invoice1);

		final Resource result = strategy.appendDunnedInvoicePdfs(pdf(DUNNING_PAGE_WIDTH, 1), dunningDocId);

		assertThat(pageWidths(result)).containsExactly(DUNNING_PAGE_WIDTH, INVOICE1_PAGE_WIDTH);
	}

	private static I_C_Invoice createInvoiceWithArchive(final float pageWidth, final int pages)
	{
		final I_C_Invoice invoice = createInvoice();
		createArchive(invoice, pageWidth, pages, null);
		return invoice;
	}

	/** @param created the archive's Created timestamp, or {@code null} for the default */
	private static void createArchive(final I_C_Invoice invoice, final float pageWidth, final int pages, @Nullable final Instant created)
	{
		final TableRecordReference invoiceRef = TableRecordReference.of(invoice);
		final I_AD_Archive archive = newInstance(I_AD_Archive.class);
		archive.setAD_Table_ID(invoiceRef.getAD_Table_ID());
		archive.setRecord_ID(invoiceRef.getRecord_ID());
		// the DB archive storage keeps the data zipped, so let it write the data instead of setting the column directly
		Services.get(IArchiveBL.class).setBinaryData(archive, pdf(pageWidth, pages).getByteArray());
		try
		{
			if (created != null)
			{
				// the POJO DB sets Created from the system time on insert
				SystemTime.setFixedTimeSource(created.atZone(ZoneOffset.UTC));
			}
			saveRecord(archive);
		}
		finally
		{
			SystemTime.resetTimeSource();
		}
	}

	private static DunningDocId createDunningDocFor(final I_C_Invoice... invoices)
	{
		final I_C_DunningDoc dunningDoc = createDunningDoc();
		for (final I_C_Invoice invoice : invoices)
		{
			addInvoiceLine(dunningDoc, invoice);
		}
		return DunningDocId.ofRepoId(dunningDoc.getC_DunningDoc_ID());
	}

	/** A PDF whose {@code pages} pages are all {@code pageWidth} wide, which is how the tests tell the source documents apart after concatenation. */
	private static ByteArrayResource pdf(final float pageWidth, final int pages)
	{
		try
		{
			final ByteArrayOutputStream out = new ByteArrayOutputStream();
			final Document document = new Document(new Rectangle(pageWidth, 400));
			PdfWriter.getInstance(document, out);
			document.open();
			for (int page = 1; page <= pages; page++)
			{
				if (page > 1)
				{
					document.newPage();
				}
				document.add(new Paragraph("page " + page + " of width " + pageWidth));
			}
			document.close();
			return new ByteArrayResource(out.toByteArray());
		}
		catch (final Exception e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}

	private static List<Float> pageWidths(final Resource pdf) throws IOException
	{
		final PdfReader reader = new PdfReader(pdf.getInputStream());
		try
		{
			final List<Float> widths = new ArrayList<>();
			for (int page = 1; page <= reader.getNumberOfPages(); page++)
			{
				widths.add(reader.getPageSize(page).getWidth());
			}
			return widths;
		}
		finally
		{
			reader.close();
		}
	}
}
