/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs.archive;

import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.util.IdentifiersResolver;
import de.metas.util.Services;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.compiere.model.I_AD_Archive;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Verifies that an {@code AD_Archive} record (the generated document PDF) exists for a given document, and
 * inspects the actual rendered PDF content (via Apache PDFBox) of that archive.
 */
@RequiredArgsConstructor
public class AD_Archive_StepDef
{
	@NonNull private final IdentifiersResolver identifiersResolver;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IArchiveBL archiveBL = Services.get(IArchiveBL.class);

	/**
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then an AD_Archive exists for the record identified by shipment
	 * </pre>
	 */
	@Then("an AD_Archive exists for the record identified by {string}")
	public void assert_archive_exists(@NonNull final String recordIdentifier)
	{
		final TableRecordReference recordRef = identifiersResolver.getTableRecordReference(StepDefDataIdentifier.ofString(recordIdentifier));

		final int count = queryBL.createQueryBuilder(I_AD_Archive.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Archive.COLUMNNAME_AD_Table_ID, recordRef.getAD_Table_ID())
				.addEqualsFilter(I_AD_Archive.COLUMNNAME_Record_ID, recordRef.getRecord_ID())
				.create()
				.count();

		assertThat(count)
				.as("AD_Archive count for record %s", recordRef)
				.isGreaterThanOrEqualTo(1);
	}

	/**
	 * Extracts the text of the most recently archived PDF for the given record (via PDFBox) and asserts it
	 * contains the given text.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then the PDF archived for the record identified by "order" contains text "Bitte beachten Sie"
	 * </pre>
	 */
	@Then("the PDF archived for the record identified by {string} contains text {string}")
	public void assert_archived_pdf_contains_text(
			@NonNull final String recordIdentifier,
			@NonNull final String expectedText)
	{
		final String pdfText = String.join("\n", extractPdfVisualLines(recordIdentifier));
		assertThat(pdfText)
				.as("Text extracted from the PDF archived for record %s", recordIdentifier)
				.contains(expectedText);
	}

	/**
	 * Verifies that, in the visual (top-to-bottom, left-to-right) reading order of the archived PDF, exactly
	 * {@code linesBetween} other text lines separate {@code earlierText} from {@code laterText} — no more,
	 * no fewer. Pass 0 to pin adjacency: the two texts then sit on consecutive lines, so nothing was printed
	 * between them. That is how a suppressed block is proven absent — the row keeps the very neighbour it
	 * has when no free text is set at all.
	 * <p>
	 * Limitation: text extraction sees glyphs, not layout. It proves no extra text was printed; it cannot
	 * prove the absence of empty vertical space, which carries no text of its own.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then in the PDF archived for the record identified by "order", exactly 1 lines appear between text "Product Alpha" and text "Product Beta"
	 * </pre>
	 */
	@Then("in the PDF archived for the record identified by {string}, exactly {int} lines appear between text {string} and text {string}")
	public void assert_archived_pdf_exactly_lines_between(
			@NonNull final String recordIdentifier,
			final int linesBetween,
			@NonNull final String earlierText,
			@NonNull final String laterText)
	{
		final List<String> lines = extractPdfVisualLines(recordIdentifier);
		assertLinesBetween(lines, earlierText, laterText, linesBetween, linesBetween);
	}

	/**
	 * Verifies that at least {@code minLinesBetween} other visual lines separate {@code earlierText} from
	 * {@code laterText} in the archived PDF — used to prove a long value actually wrapped onto several visual
	 * lines rather than being clipped onto (or squeezed into) a single one.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then in the PDF archived for the record identified by "order", at least 3 lines appear between text "Bitte beachten" and text "Product Delta"
	 * </pre>
	 */
	@Then("in the PDF archived for the record identified by {string}, at least {int} lines appear between text {string} and text {string}")
	public void assert_archived_pdf_at_least_lines_between(
			@NonNull final String recordIdentifier,
			final int minLinesBetween,
			@NonNull final String earlierText,
			@NonNull final String laterText)
	{
		final List<String> lines = extractPdfVisualLines(recordIdentifier);
		assertLinesBetween(lines, earlierText, laterText, minLinesBetween, Integer.MAX_VALUE);
	}

	private static void assertLinesBetween(
			@NonNull final List<String> lines,
			@NonNull final String earlierText,
			@NonNull final String laterText,
			final int minLinesBetween,
			final int maxLinesBetween)
	{
		final int earlierIdx = indexOfLineContaining(lines, earlierText, 0);
		assertThat(earlierIdx)
				.as("Line containing '%s' in extracted PDF text %s", earlierText, lines)
				.isGreaterThanOrEqualTo(0);

		final int laterIdx = indexOfLineContaining(lines, laterText, earlierIdx + 1);
		assertThat(laterIdx)
				.as("Line containing '%s' after '%s' in extracted PDF text %s", laterText, earlierText, lines)
				.isGreaterThanOrEqualTo(0);

		final int linesBetween = laterIdx - earlierIdx - 1;
		assertThat(linesBetween)
				.as("Lines between '%s' and '%s' in extracted PDF text %s", earlierText, laterText, lines)
				.isBetween(minLinesBetween, maxLinesBetween);
	}

	private static int indexOfLineContaining(@NonNull final List<String> lines, @NonNull final String needle, final int fromIndex)
	{
		for (int i = fromIndex; i < lines.size(); i++)
		{
			if (lines.get(i).contains(needle))
			{
				return i;
			}
		}
		return -1;
	}

	/**
	 * Extracts the most recently archived PDF for the given record as an ordered list of visual text lines
	 * (top-to-bottom, left-to-right) — the order a human reads on the page, which is what a reviewer would
	 * check by eye.
	 * <p>
	 * {@link PDFTextStripper#setSortByPosition(boolean)} is what makes the order VISUAL rather than the raw
	 * content-stream order: a Jasper band placed above another one may well be written to the content stream
	 * after it.
	 * <p>
	 * We split the stripper's own {@code getText} output on the line separator instead of overriding
	 * {@code writeString}: that callback fires once per WORD, not once per line (PDFBox 2.x
	 * {@code PDFTextStripper.writeLine} calls it per {@code WordWithTextPositions}), so collecting it would
	 * count words and the word grouping itself depends on the horizontal gaps in the layout.
	 */
	private List<String> extractPdfVisualLines(@NonNull final String recordIdentifier)
	{
		final byte[] pdfBytes = getLatestArchivedPdfBytes(recordIdentifier);

		final String pdfText;
		try (final PDDocument document = PDDocument.load(pdfBytes))
		{
			final PDFTextStripper stripper = new PDFTextStripper();
			stripper.setSortByPosition(true);
			stripper.setLineSeparator("\n");
			pdfText = stripper.getText(document);
		}
		catch (final IOException e)
		{
			throw new AdempiereException("Failed to extract text from the PDF archived for record " + recordIdentifier, e);
		}

		final List<String> lines = new ArrayList<>();
		for (final String line : pdfText.split("\n"))
		{
			final String lineTrimmed = line.trim();
			if (!lineTrimmed.isEmpty())
			{
				lines.add(lineTrimmed);
			}
		}

		// A rendered document PDF always carries text. If it carries none, the render or the archiving went
		// wrong; fail here rather than let a "does not contain text" assertion pass against an empty extraction.
		assertThat(lines)
				.as("Text lines extracted from the PDF archived for record %s", recordIdentifier)
				.isNotEmpty();

		return lines;
	}

	private byte[] getLatestArchivedPdfBytes(@NonNull final String recordIdentifier)
	{
		final TableRecordReference recordRef = identifiersResolver.getTableRecordReference(StepDefDataIdentifier.ofString(recordIdentifier));

		final I_AD_Archive archive = queryBL.createQueryBuilder(I_AD_Archive.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Archive.COLUMNNAME_AD_Table_ID, recordRef.getAD_Table_ID())
				.addEqualsFilter(I_AD_Archive.COLUMNNAME_Record_ID, recordRef.getRecord_ID())
				.orderByDescending(I_AD_Archive.COLUMNNAME_AD_Archive_ID)
				.create()
				.first();

		if (archive == null)
		{
			throw new AdempiereException("No AD_Archive found for record " + recordRef);
		}

		// Read via IArchiveBL, not AD_Archive.BinaryData: with AD_Client.StoreArchiveOnFileSystem='Y' that
		// column stays empty and the bytes live on the file system — only the archive storage knows where.
		final byte[] binaryData = archiveBL.getBinaryData(archive);
		assertThat(binaryData)
				.as("Archived PDF data of AD_Archive_ID=%s", archive.getAD_Archive_ID())
				.isNotNull()
				.isNotEmpty();

		return binaryData;
	}
}
