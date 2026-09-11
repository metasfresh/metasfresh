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
import io.qameta.allure.Allure;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;
import org.compiere.model.I_AD_Archive;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

/**
 * Verifies that an {@code AD_Archive} record (the generated document PDF) exists for a given document, and
 * inspects the actual rendered PDF content (via Apache PDFBox) of that archive.
 */
@RequiredArgsConstructor
public class AD_Archive_StepDef
{
	@NonNull private final IdentifiersResolver identifiersResolver;

	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final IArchiveBL archiveBL = Services.get(IArchiveBL.class);

	/** AD_Archive_IDs already attached to the report in THIS scenario — see {@link #attachToAllureReportOnce}. */
	@NonNull private final Set<Integer> attachedArchiveIds = new HashSet<>();

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
	 * <p>
	 * Prefer a single distinctive word over a phrase when the text under test can wrap: a line break falls
	 * between two words, so a multi-word needle fails as soon as the layout wraps between them.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then the PDF archived for the record identified by "order" contains text "Teillieferungsavis"
	 * </pre>
	 */
	@Then("the PDF archived for the record identified by {string} contains text {string}")
	public void assert_archived_pdf_contains_text(
			@NonNull final String recordIdentifier,
			@NonNull final String expectedText)
	{
		final String pdfText = extractPdfVisualLines(recordIdentifier).stream()
				.map(PdfLine::getText)
				.collect(Collectors.joining("\n"));

		assertThat(pdfText)
				.as("Text extracted from the PDF archived for record %s", recordIdentifier)
				.contains(expectedText);
	}

	/**
	 * The negative of {@link #assert_archived_pdf_contains_text(String, String)}: asserts that the text does
	 * not appear ANYWHERE in the archived PDF. This is the assertion for "the text is gone", which no
	 * positional step can make — those only ever say what is not between two anchors, while an orphaned
	 * value could have landed above another line or at the end of the document.
	 * <p>
	 * Use a single distinctive word, for the same reason as the positive step: a needle that the layout can
	 * wrap in the middle would be absent from the extracted text even when the value did print.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then the PDF archived for the record identified by "order" does not contain text "Zwischenpalette"
	 * </pre>
	 */
	@Then("the PDF archived for the record identified by {string} does not contain text {string}")
	public void assert_archived_pdf_does_not_contain_text(
			@NonNull final String recordIdentifier,
			@NonNull final String unexpectedText)
	{
		final String pdfText = extractPdfVisualLines(recordIdentifier).stream()
				.map(PdfLine::getText)
				.collect(Collectors.joining("\n"));

		assertThat(pdfText)
				.as("Text extracted from the PDF archived for record %s", recordIdentifier)
				.doesNotContain(unexpectedText);
	}

	/**
	 * Verifies that, in the visual (top-to-bottom, left-to-right) reading order of the archived PDF, exactly
	 * {@code linesBetween} other text lines separate {@code earlierText} from {@code laterText} — no more,
	 * no fewer. Pass 0 to pin adjacency: the two texts then sit on consecutive lines, so nothing was printed
	 * between them.
	 * <p>
	 * Both anchors should be text you control, so that a failure names the two rows it happened between.
	 * A non-zero expected count means the anchors have something unidentified between them, which makes the
	 * assertion composite — prefer moving the anchors over raising the count.
	 * <p>
	 * Limitation: text extraction sees glyphs, not layout. This proves no extra TEXT was printed; it says
	 * nothing about empty vertical space, which carries no text of its own. To rule that out as well, use
	 * the vertical-distance step below.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then in the PDF archived for the record identified by "order", exactly 0 lines appear between text "ALPHA-NR" and text "BetaItem"
	 * </pre>
	 */
	@Then("in the PDF archived for the record identified by {string}, exactly {int} lines appear between text {string} and text {string}")
	public void assert_archived_pdf_exactly_lines_between(
			@NonNull final String recordIdentifier,
			final int linesBetween,
			@NonNull final String earlierText,
			@NonNull final String laterText)
	{
		final List<PdfLine> lines = extractPdfVisualLines(recordIdentifier);
		assertLinesBetween(lines, earlierText, laterText, linesBetween, linesBetween);
	}

	/**
	 * Verifies that at least {@code minLinesBetween} other visual lines separate {@code earlierText} from
	 * {@code laterText} in the archived PDF — used to prove a long value actually wrapped onto several visual
	 * lines rather than being clipped onto (or squeezed into) a single one. The exact number of wrapped lines
	 * depends on font metrics, so it is deliberately not pinned.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then in the PDF archived for the record identified by "order", at least 2 lines appear between text "Diese Position" and text "BetaItem"
	 * </pre>
	 */
	@Then("in the PDF archived for the record identified by {string}, at least {int} lines appear between text {string} and text {string}")
	public void assert_archived_pdf_at_least_lines_between(
			@NonNull final String recordIdentifier,
			final int minLinesBetween,
			@NonNull final String earlierText,
			@NonNull final String laterText)
	{
		final List<PdfLine> lines = extractPdfVisualLines(recordIdentifier);
		assertLinesBetween(lines, earlierText, laterText, minLinesBetween, Integer.MAX_VALUE);
	}

	/**
	 * Verifies that the two text pairs are the same vertical distance apart on the page.
	 * <p>
	 * This is the assertion for "nothing was printed AND nothing took up space". A band that renders a
	 * blank value emits no glyphs at all, so no text-based assertion can tell it apart from a band that was
	 * suppressed — but it still consumes its own height, which shows up here as a larger distance.
	 * <p>
	 * Pick the reference pair (the second one) so that the band under test CANNOT fall inside it — a leg
	 * that the same band also inflates makes the comparison pass again once every row grows equally, which
	 * is exactly what happens when a guard is removed outright rather than mis-evaluated. A pair that stays
	 * within one row, below the band in question, is such a fixed reference.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then in the PDF archived for the record identified by "order", the vertical distance from text "ALPHA-NR" to text "BetaItem" equals the distance from text "BETA-NR" to text "GammaItem"
	 * </pre>
	 */
	@Then("in the PDF archived for the record identified by {string}, the vertical distance from text {string} to text {string} equals the distance from text {string} to text {string}")
	public void assert_archived_pdf_vertical_distances_equal(
			@NonNull final String recordIdentifier,
			@NonNull final String firstFromText,
			@NonNull final String firstToText,
			@NonNull final String secondFromText,
			@NonNull final String secondToText)
	{
		final List<PdfLine> lines = extractPdfVisualLines(recordIdentifier);

		final float firstDistance = verticalDistance(lines, firstFromText, firstToText);
		final float secondDistance = verticalDistance(lines, secondFromText, secondToText);

		// The smallest difference this has to detect is one suppressed-vs-printed band, which is whole
		// points tall, so a fraction of a point of float noise in the glyph coordinates is not a difference.
		assertThat(firstDistance)
				.as("Vertical distance '%s'->'%s' vs '%s'->'%s' in extracted PDF text %s",
						firstFromText, firstToText, secondFromText, secondToText, textsOf(lines))
				.isCloseTo(secondDistance, within(0.5f));
	}

	/**
	 * Asserts that no two words in the archived PDF are printed on top of each other.
	 * <p>
	 * A generic layout net: it needs no knowledge of the document, so it can be added to any scenario that
	 * already prints one. It catches the failure where an element stretches or is positioned into its
	 * neighbour — a long product name running into the quantity column, a block that grew into the row
	 * beneath.
	 * <p>
	 * It does NOT catch CLIPPING, which is the more common Jasper failure: an element too small for its
	 * content with {@code isStretchWithOverflow} off does not overlap anything, it silently truncates. That
	 * shows up as MISSING text, so assert on a word you expect near the end of the content instead.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then the PDF archived for the record identified by "order" has no overlapping text
	 * </pre>
	 */
	@Then("the PDF archived for the record identified by {string} has no overlapping text")
	public void assert_archived_pdf_has_no_overlapping_text(@NonNull final String recordIdentifier)
	{
		assert_archived_pdf_has_no_overlapping_text_within(recordIdentifier, DEFAULT_OVERLAP_TOLERANCE_POINTS);
	}

	/**
	 * Same as {@link #assert_archived_pdf_has_no_overlapping_text(String)} with an explicit tolerance.
	 * <p>
	 * The unit is PDF user-space POINTS, not pixels. Two boxes count as overlapping only when they intersect
	 * by more than the tolerance in BOTH dimensions, so words merely sharing a column or a baseline are not
	 * flagged; only a true two-dimensional collision is.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then the PDF archived for the record identified by "order" has no overlapping text within 2 points
	 * </pre>
	 */
	@Then("the PDF archived for the record identified by {string} has no overlapping text within {int} points")
	public void assert_archived_pdf_has_no_overlapping_text_within(
			@NonNull final String recordIdentifier,
			final int tolerancePoints)
	{
		final List<PdfWord> words = extractPdfWords(recordIdentifier);
		final List<String> overlaps = detectOverlaps(words, tolerancePoints);

		assertThat(overlaps)
				.as("Overlapping text in the PDF archived for record %s (tolerance %s points, %s words examined)",
						recordIdentifier, tolerancePoints, words.size())
				.isEmpty();
	}


	private static float verticalDistance(
			@NonNull final List<PdfLine> lines,
			@NonNull final String fromText,
			@NonNull final String toText)
	{
		final int fromIdx = indexOfLineContaining(lines, fromText, 0);
		assertThat(fromIdx)
				.as("Line containing '%s' in extracted PDF text %s", fromText, textsOf(lines))
				.isGreaterThanOrEqualTo(0);

		final int toIdx = indexOfLineContaining(lines, toText, fromIdx + 1);
		assertThat(toIdx)
				.as("Line containing '%s' after '%s' in extracted PDF text %s", toText, fromText, textsOf(lines))
				.isGreaterThanOrEqualTo(0);

		// Page coordinates restart on every page, so a distance measured across a page break is a
		// meaningless number that would silently satisfy or break the comparison.
		assertThat(lines.get(toIdx).getPageIndex())
				.as("Page of '%s' vs page of '%s': a vertical distance is only meaningful within one page",
						toText, fromText)
				.isEqualTo(lines.get(fromIdx).getPageIndex());

		return lines.get(toIdx).getY() - lines.get(fromIdx).getY();
	}

	private static void assertLinesBetween(
			@NonNull final List<PdfLine> lines,
			@NonNull final String earlierText,
			@NonNull final String laterText,
			final int minLinesBetween,
			final int maxLinesBetween)
	{
		final int earlierIdx = indexOfLineContaining(lines, earlierText, 0);
		assertThat(earlierIdx)
				.as("Line containing '%s' in extracted PDF text %s", earlierText, textsOf(lines))
				.isGreaterThanOrEqualTo(0);

		final int laterIdx = indexOfLineContaining(lines, laterText, earlierIdx + 1);
		assertThat(laterIdx)
				.as("Line containing '%s' after '%s' in extracted PDF text %s", laterText, earlierText, textsOf(lines))
				.isGreaterThanOrEqualTo(0);

		final int linesBetween = laterIdx - earlierIdx - 1;
		assertThat(linesBetween)
				.as("Lines between '%s' and '%s' in extracted PDF text %s", earlierText, laterText, textsOf(lines))
				.isBetween(minLinesBetween, maxLinesBetween);
	}

	private static int indexOfLineContaining(@NonNull final List<PdfLine> lines, @NonNull final String needle, final int fromIndex)
	{
		for (int i = fromIndex; i < lines.size(); i++)
		{
			if (lines.get(i).getText().contains(needle))
			{
				return i;
			}
		}
		return -1;
	}

	private static List<String> textsOf(@NonNull final List<PdfLine> lines)
	{
		return lines.stream().map(PdfLine::getText).collect(Collectors.toList());
	}

	/**
	 * Extracts the most recently archived PDF for the given record as an ordered list of visual text lines
	 * (top-to-bottom, left-to-right) — the order a human reads on the page, which is what a reviewer would
	 * check by eye — each carrying the page it sits on and its vertical position.
	 * <p>
	 * {@link PDFTextStripper#setSortByPosition(boolean)} is what makes the order VISUAL rather than the raw
	 * content-stream order: a Jasper band placed above another one may well be written to the content stream
	 * after it.
	 * <p>
	 * The grouping is PDFBox's own. {@code writeString} fires once per WORD, not once per line (PDFBox 2.x
	 * {@code PDFTextStripper.writeLine} calls it per {@code WordWithTextPositions}), so {@link PdfLineStripper}
	 * accumulates words and flushes a line only on {@code writeLineSeparator} — which reproduces exactly what
	 * {@code getText()} would have returned, while also keeping the coordinates {@code getText()} discards.
	 */
	private List<PdfLine> extractPdfVisualLines(@NonNull final String recordIdentifier)
	{
		final byte[] pdfBytes = getLatestArchivedPdfBytes(recordIdentifier);

		final List<PdfLine> lines;
		try (final PDDocument document = PDDocument.load(pdfBytes))
		{
			final PdfLineStripper stripper = new PdfLineStripper();
			stripper.getText(document); // the captured lines, not the return value, are what we use
			lines = stripper.getLines();
		}
		catch (final IOException e)
		{
			throw new AdempiereException("Failed to extract text from the PDF archived for record " + recordIdentifier, e);
		}

		// A rendered document PDF always carries text. If it carries none, the render or the archiving went
		// wrong; fail here rather than let a text assertion pass against an empty extraction.
		assertThat(lines)
				.as("Text lines extracted from the PDF archived for record %s", recordIdentifier)
				.isNotEmpty();

		return lines;
	}

	private byte[] getLatestArchivedPdfBytes(@NonNull final String recordIdentifier)
	{
		final TableRecordReference recordRef = identifiersResolver.getTableRecordReference(StepDefDataIdentifier.ofString(recordIdentifier));

		// Deliberately not IArchiveDAO.retrieveLastArchives: that orders by Created descending
		// (ArchiveDAO.java:87). A scenario that prints the same record twice can produce both archives
		// within one clock tick, and then "last" is whichever row the DB happens to return first.
		// AD_Archive_ID is monotonic and never ties, so ordering by it picks the newest archive
		// deterministically -- which is what an assertion about "the PDF just printed" needs.
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

		// Read via IArchiveBL, not AD_Archive.BinaryData: DBArchiveStorage stores the document ZIPPED in
		// that column, and with AD_Client.StoreArchiveOnFileSystem='Y' it is not in the column at all.
		// Only the archive storage knows how to hand back the plain document bytes.
		final byte[] binaryData = archiveBL.getBinaryData(archive);
		assertThat(binaryData)
				.as("Archived PDF data of AD_Archive_ID=%s", archive.getAD_Archive_ID())
				.isNotNull()
				.isNotEmpty();

		attachToAllureReportOnce(archive.getAD_Archive_ID(), recordIdentifier, binaryData);

		return binaryData;
	}

	/**
	 * Attaches the rendered PDF to the Allure report, so a reviewer can open the actual document a
	 * scenario asserted against instead of inferring it from extracted text.
	 * <p>
	 * Deduplicated per archive: several assertion steps in one scenario read the same document, and each
	 * would otherwise attach another copy of it. This step-def instance lives for exactly one scenario
	 * (picocontainer builds a fresh one per scenario), so the set needs no clearing.
	 */
	private void attachToAllureReportOnce(
			final int archiveId,
			@NonNull final String recordIdentifier,
			@NonNull final byte[] pdfBytes)
	{
		if (!attachedArchiveIds.add(archiveId))
		{
			return;
		}

		Allure.addAttachment(
				"PDF archived for " + recordIdentifier + " (AD_Archive_ID=" + archiveId + ")",
				"application/pdf",
				new ByteArrayInputStream(pdfBytes),
				".pdf");
	}

	/** One visual line of the PDF: the page it sits on, its vertical position, and its text. */
	@Value
	private static class PdfLine
	{
		int pageIndex;
		float y;
		@NonNull String text;
	}

	/**
	 * Collects one {@link PdfLine} per visual line PDFBox emits, in top-to-bottom reading order, keeping the
	 * vertical position that {@link PDFTextStripper#getText(PDDocument)} throws away.
	 */
	private static final class PdfLineStripper extends PDFTextStripper
	{
		private final List<PdfLine> lines = new ArrayList<>();
		private final StringBuilder currentLine = new StringBuilder();
		private Float currentY = null;
		private int currentPageIndex = 0;

		PdfLineStripper() throws IOException
		{
			super();
			setSortByPosition(true);
			setLineSeparator("\n");
		}

		@Override
		protected void writeString(final String text, final List<TextPosition> textPositions)
		{
			currentLine.append(text);
			if (currentY == null && !textPositions.isEmpty())
			{
				currentY = textPositions.get(0).getYDirAdj();
			}
		}

		@Override
		protected void writeWordSeparator()
		{
			currentLine.append(getWordSeparator());
		}

		@Override
		protected void writeLineSeparator()
		{
			flushCurrentLine();
		}

		@Override
		protected void startPage(final PDPage page)
		{
			currentPageIndex = getCurrentPageNo();
		}

		@Override
		protected void endPage(final PDPage page)
		{
			flushCurrentLine(); // the last line of a page is not followed by a line separator
		}

		private void flushCurrentLine()
		{
			final String text = currentLine.toString().trim();
			if (!text.isEmpty() && currentY != null)
			{
				lines.add(new PdfLine(currentPageIndex, currentY, text));
			}
			currentLine.setLength(0);
			currentY = null;
		}

		List<PdfLine> getLines()
		{
			return lines;
		}
	}

	/** Tolerance in PDF user-space points, matching the frontend PdfLayoutValidator default. */
	private static final int DEFAULT_OVERLAP_TOLERANCE_POINTS = 2;

	/**
	 * Every pair of words whose bounding boxes intersect by more than {@code tolerancePoints} in BOTH
	 * dimensions, described for the failure message.
	 * <p>
	 * Requiring both dimensions is what keeps this quiet on a normal document: words in the same column share
	 * an x-range and words on the same line share a y-range, and neither alone is a collision. Pairwise is
	 * O(n^2), which is fine at document scale.
	 */
	private static List<String> detectOverlaps(@NonNull final List<PdfWord> words, final int tolerancePoints)
	{
		final List<String> overlaps = new ArrayList<>();

		for (int i = 0; i < words.size(); i++)
		{
			final PdfWord one = words.get(i);
			for (int j = i + 1; j < words.size(); j++)
			{
				final PdfWord other = words.get(j);
				if (one.getPageIndex() != other.getPageIndex())
				{
					continue;
				}

				final float overlapX = Math.min(one.getRight(), other.getRight()) - Math.max(one.getLeft(), other.getLeft());
				final float overlapY = Math.min(one.getBottom(), other.getBottom()) - Math.max(one.getTop(), other.getTop());

				if (overlapX > tolerancePoints && overlapY > tolerancePoints)
				{
					overlaps.add(String.format(
							"page %s: '%s' and '%s' overlap by %.1f x %.1f points",
							one.getPageIndex(), one.getText(), other.getText(), overlapX, overlapY));
				}
			}
		}

		return overlaps;
	}

	/**
	 * Extracts the archived PDF as one bounding box per WORD.
	 * <p>
	 * Word granularity comes free: PDFBox 2.x calls {@code writeString} once per word (see
	 * {@link #extractPdfVisualLines(String)}). It is also the right unit here — per glyph would flag normal
	 * kerning, per line would be too coarse to see a collision within a row.
	 */
	private List<PdfWord> extractPdfWords(@NonNull final String recordIdentifier)
	{
		final byte[] pdfBytes = getLatestArchivedPdfBytes(recordIdentifier);

		final List<PdfWord> words;
		try (final PDDocument document = PDDocument.load(pdfBytes))
		{
			final PdfWordStripper stripper = new PdfWordStripper();
			stripper.getText(document);
			words = stripper.getWords();
		}
		catch (final IOException e)
		{
			throw new AdempiereException("Failed to extract words from the PDF archived for record " + recordIdentifier, e);
		}

		assertThat(words)
				.as("Words extracted from the PDF archived for record %s", recordIdentifier)
				.isNotEmpty();

		return words;
	}

	/** One word of the PDF with the box it occupies, in PDF user-space points, y growing downwards. */
	@Value
	private static class PdfWord
	{
		int pageIndex;
		float left;
		float right;
		float top;
		float bottom;
		@NonNull String text;
	}

	/**
	 * Collects one {@link PdfWord} per GLYPH, with its bounding box.
	 * <p>
	 * Glyph level, not word level, and the reason is measured rather than theoretical. Two higher-level
	 * groupings were tried first and both fail:
	 * <ul>
	 * <li><b>Unsorted runs</b> — PDFBox hands back raw content-stream runs, and a run in these documents spans
	 * several visually separate columns: one came back as {@code "10,00AlphaItem"}. Boxes built from those
	 * span the whole row and collide with everything on it, so every row reports overlaps a reader cannot see.
	 * <li><b>Sorted words</b> ({@code setSortByPosition(true)}, which makes {@code writeString} fire per word)
	 * — the sorting that produces words also FUSES colliding glyphs into one word. With a field deliberately
	 * moved on top of the product name, the two texts came back as the single word {@code "ASltpkhaItem"}, so
	 * there were no longer two boxes to compare and the collision was invisible.
	 * </ul>
	 * Per glyph, neither happens: characters within a word merely abut (the advance width puts the next glyph
	 * exactly where the previous ends, so their horizontal overlap is ~0 and stays under the tolerance), while
	 * two texts printed on top of each other overlap by most of a glyph width and are flagged.
	 */
	private static final class PdfWordStripper extends PDFTextStripper
	{
		private final List<PdfWord> words = new ArrayList<>();

		PdfWordStripper() throws IOException
		{
			super();
		}

		List<PdfWord> getWords()
		{
			return words;
		}

		@Override
		protected void writeString(final String text, final List<TextPosition> textPositions)
		{
			for (final TextPosition position : textPositions)
			{
				final String glyph = position.getUnicode();
				if (glyph == null || glyph.trim().isEmpty())
				{
					continue; // whitespace lays down no ink and cannot collide with anything
				}

				final float x = position.getXDirAdj();
				final float y = position.getYDirAdj();
				final float height = position.getHeightDir();

				words.add(new PdfWord(
						getCurrentPageNo(),
						x,
						x + position.getWidthDirAdj(),
						y - height,
						y,
						glyph));
			}
		}
	}

}
