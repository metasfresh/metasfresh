/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.cucumber.stepdefs.dunning;

import com.google.common.io.ByteStreams;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import de.metas.bpartner.BPartnerId;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.invoice.C_Invoice_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.dunning.DunningDocId;
import de.metas.dunning.model.I_C_DunningDoc;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.process.ProcessInfo;
import de.metas.report.server.OutputType;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.Value;
import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_C_Invoice;
import org.compiere.util.Env;
import org.springframework.core.io.Resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class C_DunningDoc_StepDef
{
	private final C_DunningLevel_StepDefData dunningLevelTable;
	private final C_BPartner_StepDefData bpartnerTable;
	private final C_DunningDoc_StepDefData dunningDocTable;
	private final C_Invoice_StepDefData invoiceTable;

	private final IADProcessDAO processDAO = Services.get(IADProcessDAO.class);
	private final IArchiveBL archiveBL = Services.get(IArchiveBL.class);

	private static final String PROCESS_VALUE_DUNNING_LETTER = "C_DunningDoc";
	private static final String PROCESS_VALUE_DUNNING_LETTER_WITH_INVOICE_PDFS = "C_DunningDoc_JasperWithInvoicePDFs";

	/** Print results of the current scenario, by C_DunningDoc identifier. */
	private final Map<StepDefDataIdentifier, DunningPrintResult> printResults = new HashMap<>();

	@Value
	private static class DunningPrintResult
	{
		byte[] letterOnly;
		byte[] withInvoicePdfs;
	}

	@And("validate C_DunningDoc:")
	public void validate_C_DunningDoc(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_C_DunningDoc.COLUMNNAME_C_DunningDoc_ID)
				.forEach(this::validateDunningDoc);
	}

	private void validateDunningDoc(@NonNull final DataTableRow row)
	{
		final I_C_DunningDoc dunningDoc = row.getAsIdentifier()
				.lookupIn(dunningDocTable);
		assertThat(dunningDoc).isNotNull();

		final SoftAssertions softly = new SoftAssertions();
		
		row.getAsOptionalIdentifier(I_C_DunningDoc.COLUMNNAME_C_DunningLevel_ID)
				.map(dunningLevelTable::get)
				.ifPresent(dunningLevel -> softly.assertThat(dunningDoc.getC_DunningLevel_ID())
						.as(I_C_DunningDoc.COLUMNNAME_C_DunningLevel_ID)
						.isEqualTo(dunningLevel.getC_DunningLevel_ID()));

		row.getAsOptionalBoolean(I_C_DunningDoc.COLUMNNAME_Processed)
				.ifPresent(processed -> softly.assertThat(dunningDoc.isProcessed())
						.isEqualTo(processed));

		row.getAsOptionalIdentifier(I_C_DunningDoc.COLUMNNAME_C_BPartner_ID)
				.map(bpartnerTable::getId)
				.ifPresent(bPartnerId -> softly.assertThat(BPartnerId.ofRepoIdOrNull(dunningDoc.getC_BPartner_ID()))
						.as(I_C_DunningDoc.COLUMNNAME_C_BPartner_ID)
						.isEqualTo(bPartnerId));
		
		softly.assertAll();
	}

	/**
	 * Runs the "dunning letter with attached invoice PDFs" print process for the dunning doc, the same way the user does from the dunning doc window.
	 * It also runs the plain dunning letter process, to know how many pages the letter alone has.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * When the dunning letter with attached invoice PDFs is printed for C_DunningDoc "dunningDoc_1"
	 * </pre>
	 */
	@When("the dunning letter with attached invoice PDFs is printed for C_DunningDoc {string}")
	public void print_dunningLetter_withInvoicePDFs(@NonNull final String dunningDocIdentifier)
	{
		final StepDefDataIdentifier identifier = StepDefDataIdentifier.ofString(dunningDocIdentifier);
		final DunningDocId dunningDocId = dunningDocTable.getId(identifier);

		final byte[] letterOnly = runPrintProcess(PROCESS_VALUE_DUNNING_LETTER, dunningDocId);
		final byte[] withInvoicePdfs = runPrintProcess(PROCESS_VALUE_DUNNING_LETTER_WITH_INVOICE_PDFS, dunningDocId);

		printResults.put(identifier, new DunningPrintResult(letterOnly, withInvoicePdfs));
	}

	/**
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>C_Invoice_ID</b> — (required, identifier-ref) invoice whose archived PDF is expected to be attached; row order = expected PDF order<br>
	 * @cucumber.example
	 * <pre>
	 * Then the printed PDF of C_DunningDoc "dunningDoc_1" is the dunning letter followed by the archived PDFs of the invoices:
	 *   | C_Invoice_ID  |
	 *   | salesInvoice1 |
	 * </pre>
	 */
	@Then("the printed PDF of C_DunningDoc {string} is the dunning letter followed by the archived PDFs of the invoices:")
	public void validate_printedPdf_withInvoicePdfs(@NonNull final String dunningDocIdentifier, @NonNull final DataTable dataTable)
	{
		final List<I_C_Invoice> invoices = new ArrayList<>();
		DataTableRows.of(dataTable).forEach(row -> invoices.add(row.getAsIdentifier(I_C_Invoice.COLUMNNAME_C_Invoice_ID).lookupNotNullIn(invoiceTable)));

		validatePrintedPdf(dunningDocIdentifier, invoices);
	}

	/**
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then the printed PDF of C_DunningDoc "dunningDoc_1" is the dunning letter only
	 * </pre>
	 */
	@Then("the printed PDF of C_DunningDoc {string} is the dunning letter only")
	public void validate_printedPdf_letterOnly(@NonNull final String dunningDocIdentifier)
	{
		validatePrintedPdf(dunningDocIdentifier, Collections.emptyList());
	}

	private void validatePrintedPdf(@NonNull final String dunningDocIdentifier, @NonNull final List<I_C_Invoice> expectedAttachedInvoices)
	{
		final DunningPrintResult printResult = printResults.get(StepDefDataIdentifier.ofString(dunningDocIdentifier));
		assertThat(printResult).as("print result of C_DunningDoc " + dunningDocIdentifier).isNotNull();

		final int letterPageCount = pageTexts(printResult.getLetterOnly()).size();
		assertThat(letterPageCount).as("page count of the dunning letter alone").isGreaterThan(0);

		final List<String> expectedAttachedPageTexts = new ArrayList<>();
		for (final I_C_Invoice invoice : expectedAttachedInvoices)
		{
			final Resource invoicePdf = archiveBL.getLastArchiveBinaryData(TableRecordReference.of(invoice))
					.orElseThrow(() -> new AssertionError("No AD_Archive for invoice " + invoice.getDocumentNo()));
			final List<String> invoicePageTexts = pageTexts(invoicePdf);
			assertThat(invoicePageTexts).as("pages of the archived PDF of invoice " + invoice.getDocumentNo()).isNotEmpty();
			assertThat(String.join("", invoicePageTexts)).as("text of the archived PDF of invoice " + invoice.getDocumentNo()).contains(invoice.getDocumentNo());
			expectedAttachedPageTexts.addAll(invoicePageTexts);
		}

		final List<String> actualPageTexts = pageTexts(printResult.getWithInvoicePdfs());

		assertThat(actualPageTexts)
				.as("page count of the printed PDF = dunning letter pages + archived invoice PDF pages")
				.hasSize(letterPageCount + expectedAttachedPageTexts.size());
		assertThat(actualPageTexts.subList(letterPageCount, actualPageTexts.size()))
				.as("pages after the dunning letter = archived invoice PDF pages, in invoice order")
				.isEqualTo(expectedAttachedPageTexts);
	}

	private byte[] runPrintProcess(@NonNull final String processValue, @NonNull final DunningDocId dunningDocId)
	{
		final AdProcessId processId = processDAO.retrieveProcessIdByValue(processValue);
		assertThat(processId).as("AD_Process " + processValue).isNotNull();

		final Resource reportData = ProcessInfo.builder()
				.setCtx(Env.getCtx())
				.setAD_Process_ID(processId)
				.setRecord(dunningDocId.toRecordRef())
				.setArchiveReportData(false) // just give us the PDF data
				.setPrintPreview(false)
				.setJRDesiredOutputType(OutputType.PDF)
				.buildAndPrepareExecution()
				.onErrorThrowException(true)
				.executeSync()
				.getResult()
				.getReportDataResource();
		assertThat(reportData).as("report data of process " + processValue).isNotNull();

		return toBytes(reportData);
	}

	@SneakyThrows
	private static byte[] toBytes(@NonNull final Resource resource)
	{
		return ByteStreams.toByteArray(resource.getInputStream());
	}

	private static List<String> pageTexts(@NonNull final Resource pdf)
	{
		return pageTexts(toBytes(pdf));
	}

	@SneakyThrows
	private static List<String> pageTexts(@NonNull final byte[] pdf)
	{
		final PdfReader reader = new PdfReader(pdf);
		try
		{
			final List<String> result = new ArrayList<>();
			for (int page = 1; page <= reader.getNumberOfPages(); page++)
			{
				result.add(PdfTextExtractor.getTextFromPage(reader, page));
			}
			return result;
		}
		finally
		{
			reader.close();
		}
	}
}
