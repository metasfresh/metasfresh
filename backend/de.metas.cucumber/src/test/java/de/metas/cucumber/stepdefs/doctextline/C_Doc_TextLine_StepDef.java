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

package de.metas.cucumber.stepdefs.doctextline;

import de.metas.cucumber.stepdefs.order.C_OrderLine_StepDefData;
import de.metas.doctextline.DocTextLine;
import de.metas.doctextline.DocTextLineDocumentRef;
import de.metas.doctextline.DocTextLineRepository;
import de.metas.doctextline.InsertAboveRequest;
import de.metas.doctextline.TextLineScope;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.util.Services;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Doc_TextLine;
import org.compiere.model.I_C_OrderLine;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Creates and validates {@code C_Doc_TextLine} records belonging to a {@code C_Order} -- the free-text lines a
 * user can place between a sales order's article lines.
 */
@RequiredArgsConstructor
public class C_Doc_TextLine_StepDef
{
	@NonNull private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	@NonNull private final DocTextLineRepository docTextLineRepository = SpringContextHolder.instance.getBean(DocTextLineRepository.class);

	@NonNull private final C_OrderLine_StepDefData orderLineTable;
	@NonNull private final C_Doc_TextLine_StepDefData textLineTable;

	/**
	 * Inserts a new text line immediately above the given order line, via {@link DocTextLineRepository#insertAbove}
	 * -- the same repository method the WebUI's insert-above quick action ({@code WEBUI_DocTextLines_InsertAbove},
	 * via {@code DocTextLinesView#insertRowAbove}) calls.
	 *
	 * <p><b>Direct-repository invocation (documented exemption).</b> In production, a user selects a row in the
	 * "Freitextzeilen" modal and invokes insert-above; that view/process pair lives in {@code de.metas.ui.web.base},
	 * which this cucumber module does not (and should not) depend on. This step therefore reproduces the two
	 * inputs the view derives from the merged article/text-line order -- the position immediately preceding the
	 * reference line, and whether any article line precedes it -- from the actual persisted {@code C_OrderLine}
	 * and {@code C_Doc_TextLine} rows (via {@link IOrderDAO#retrieveOrderLines(OrderId)}, the same call
	 * {@code DocTextLinesRowsLoader} makes), and passes them to the repository. The scope-driving predicate
	 * itself -- whether any article line precedes the reference position -- is answered by
	 * {@link DocTextLineRepository#articleLineExistsBefore}, the same method the WebUI's merged-row scan
	 * (production) calls, so this step and production share that one implementation rather than each
	 * re-deriving it. The repository is what then derives and persists {@code TextLineScope}; this step never
	 * sets it.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>textLineIdentifier</b> -- (required) alias the created {@code C_Doc_TextLine} is stored under<br>
	 *   <b>orderLineIdentifier</b> -- (required, identifier-ref) the order line to insert above<br>
	 * @cucumber.depends StepDefData: C_OrderLine_StepDefData, C_Doc_TextLine_StepDefData
	 * @cucumber.example
	 * <pre>
	 * When a text line "topBlock" is inserted above the order line identified by "line10" with text:
	 *   """
	 *   Line one
	 *   Line two
	 *   """
	 * </pre>
	 */
	@When("a text line {string} is inserted above the order line identified by {string} with text:")
	public void insertTextLineAboveOrderLine(
			@NonNull final String textLineIdentifier,
			@NonNull final String orderLineIdentifier,
			@NonNull final String text)
	{
		final I_C_OrderLine referenceOrderLine = orderLineTable.get(orderLineIdentifier);
		final OrderId orderId = OrderId.ofRepoId(referenceOrderLine.getC_Order_ID());
		final DocTextLineDocumentRef documentRef = DocTextLineDocumentRef.ofOrderId(orderId);

		final BigDecimal referencePosition = BigDecimal.valueOf(referenceOrderLine.getLine());

		// matches DocTextLinesRowsLoader.load()'s own article-line source
		final List<de.metas.interfaces.I_C_OrderLine> orderLines = orderDAO.retrieveOrderLines(orderId);

		final List<BigDecimal> articleLinePositionsBeforeReference = orderLines.stream()
				.filter(orderLine -> orderLine.getLine() < referenceOrderLine.getLine())
				.map(orderLine -> BigDecimal.valueOf(orderLine.getLine()))
				.collect(Collectors.toList());

		final boolean articleLineExistsBeforeReferencePosition =
				DocTextLineRepository.articleLineExistsBefore(articleLinePositionsBeforeReference, referencePosition);

		final BigDecimal previousTextLinePosition = docTextLineRepository.getByDocument(documentRef).stream()
				.map(DocTextLine::getLine)
				.filter(line -> line.compareTo(referencePosition) < 0)
				.max(Comparator.naturalOrder())
				.orElse(null);

		final BigDecimal previousPosition = Stream.concat(articleLinePositionsBeforeReference.stream(), Stream.of(previousTextLinePosition))
				.filter(Objects::nonNull)
				.max(Comparator.naturalOrder())
				.orElse(null);

		final DocTextLine inserted = docTextLineRepository.insertAbove(InsertAboveRequest.builder()
				.documentRef(documentRef)
				.textLine(text)
				.referencePosition(referencePosition)
				.previousPosition(previousPosition)
				.articleLineExistsBeforeReferencePosition(articleLineExistsBeforeReferencePosition)
				.build());

		final I_C_Doc_TextLine record = InterfaceWrapperHelper.load(inserted.getId().getRepoId(), I_C_Doc_TextLine.class);
		textLineTable.putOrReplace(textLineIdentifier, record);
	}

	/**
	 * Asserts the {@code TextLineScope} the repository derived and persisted for a text line -- never a value
	 * this suite sets itself.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then the text line identified by "topBlock" has TextLineScope "Document"
	 * </pre>
	 */
	@Then("the text line identified by {string} has TextLineScope {string}")
	public void validateTextLineScope(
			@NonNull final String textLineIdentifier,
			@NonNull final String expectedScope)
	{
		final I_C_Doc_TextLine record = textLineTable.get(textLineIdentifier);
		InterfaceWrapperHelper.refresh(record);

		final TextLineScope actualScope = TextLineScope.ofCode(record.getTextLineScope());
		assertThat(actualScope.name()).as("TextLineScope of text line %s", textLineIdentifier).isEqualTo(expectedScope);
	}

	/**
	 * Asserts the persisted {@code TextLine} value read back from the database -- including any blank lines it
	 * carries -- rather than assuming the Gherkin doc-string round-tripped unchanged.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then the text line identified by "topBlock" has text:
	 *   """
	 *   Line one
	 *   Line two
	 *   """
	 * </pre>
	 */
	@Then("the text line identified by {string} has text:")
	public void validateTextLineText(
			@NonNull final String textLineIdentifier,
			@NonNull final String expectedText)
	{
		final I_C_Doc_TextLine record = textLineTable.get(textLineIdentifier);
		InterfaceWrapperHelper.refresh(record);

		assertThat(record.getTextLine()).as("TextLine of text line %s", textLineIdentifier).isEqualTo(expectedText);
	}

	/**
	 * Asserts the persisted {@code TextLine} value's line count against a literal expectation -- independent of
	 * the Gherkin doc-string that created it. {@link #validateTextLineText} re-reads from the database, but the
	 * SAME doc-string supplies both the value written and the value expected, so a collapse at the Gherkin layer
	 * (not the database) would pass there undetected; a literal integer here cannot collapse the same way.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then the text line identified by "topBlock" has 4 lines
	 * </pre>
	 */
	@Then("the text line identified by {string} has {int} lines")
	public void validateTextLineLineCount(
			@NonNull final String textLineIdentifier,
			final int expectedLineCount)
	{
		final I_C_Doc_TextLine record = textLineTable.get(textLineIdentifier);
		InterfaceWrapperHelper.refresh(record);

		final String[] lines = splitLines(record.getTextLine());
		assertThat(lines.length).as("Line count of text line %s", textLineIdentifier).isEqualTo(expectedLineCount);
	}

	/**
	 * Asserts that one specific 1-based line of the persisted {@code TextLine} value is blank -- the companion
	 * of {@link #validateTextLineLineCount}: not just the right number of lines, but the blank one at the right
	 * position, both independent of the doc-string that created the value.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then the text line identified by "topBlock" has a blank line at position 3
	 * </pre>
	 */
	@Then("the text line identified by {string} has a blank line at position {int}")
	public void validateTextLineBlankLineAtPosition(
			@NonNull final String textLineIdentifier,
			final int position)
	{
		final I_C_Doc_TextLine record = textLineTable.get(textLineIdentifier);
		InterfaceWrapperHelper.refresh(record);

		final String[] lines = splitLines(record.getTextLine());
		assertThat(position).as("Position must be within 1..%s for text line %s", lines.length, textLineIdentifier).isBetween(1, lines.length);
		assertThat(lines[position - 1]).as("Line %s of text line %s", position, textLineIdentifier).isEmpty();
	}

	private static String[] splitLines(@Nullable final String textLine)
	{
		return (textLine != null ? textLine : "").split("\n", -1);
	}

	/**
	 * Asserts that inserting text lines never renumbered the article lines around them.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then the order line identified by "line10" still has Line 10
	 * </pre>
	 */
	@Then("the order line identified by {string} still has Line {int}")
	public void validateOrderLineStillHasLine(
			@NonNull final String orderLineIdentifier,
			final int expectedLine)
	{
		final I_C_OrderLine orderLine = orderLineTable.get(orderLineIdentifier);
		InterfaceWrapperHelper.refresh(orderLine);

		assertThat(orderLine.getLine()).as("Line of order line %s", orderLineIdentifier).isEqualTo(expectedLine);
	}
}
