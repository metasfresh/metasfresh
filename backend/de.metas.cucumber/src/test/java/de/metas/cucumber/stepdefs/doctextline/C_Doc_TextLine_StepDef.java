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
import de.metas.doctextline.DocTextLineId;
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
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Doc_TextLine;
import org.compiere.model.I_C_OrderLine;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
	 * {@code DocTextLinesRowsLoader} makes). The scope-driving predicate itself is answered by
	 * {@link DocTextLineRepository#articleLineExistsBefore}, and every article line's position is handed to it
	 * <b>unfiltered</b> -- the {@code "< referencePosition"} comparison happens inside that shared method, not in
	 * a pre-applied filter here, so this step and production genuinely exercise the same comparison rather than
	 * one of them merely reducing to "is the list non-empty". The repository is what then derives and persists
	 * {@code TextLineScope}; this step never sets it.
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

		// unfiltered: articleLineExistsBefore's own "< referencePosition" comparison must be the thing that
		// decides the outcome, not a pre-applied filter that leaves the call unable to see a mutation of it
		final List<BigDecimal> articleLinePositions = orderLines.stream()
				.map(orderLine -> BigDecimal.valueOf(orderLine.getLine()))
				.collect(Collectors.toList());

		final boolean articleLineExistsBeforeReferencePosition =
				DocTextLineRepository.articleLineExistsBefore(articleLinePositions, referencePosition);

		final BigDecimal previousArticleLinePosition = articleLinePositions.stream()
				.filter(line -> line.compareTo(referencePosition) < 0)
				.max(Comparator.naturalOrder())
				.orElse(null);

		final BigDecimal previousTextLinePosition = docTextLineRepository.getByDocument(documentRef).stream()
				.map(DocTextLine::getLine)
				.filter(line -> line.compareTo(referencePosition) < 0)
				.max(Comparator.naturalOrder())
				.orElse(null);

		final BigDecimal previousPosition = Stream.of(previousArticleLinePosition, previousTextLinePosition)
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
	 * Moves the given text line to the position immediately after the row currently following it in the merged
	 * article/text-line order -- the state the WebUI's "move down" quick action ({@code
	 * WEBUI_DocTextLines_MoveDown}) would produce. Since the requirements provide no insert-below, this is the
	 * step this suite uses to get a text line <b>after</b> the last article line: insert it above that article
	 * line, then move it down once -- the article line's own position is never touched, only the text line's.
	 *
	 * <p><b>This step does NOT exercise the quick action.</b> The decision logic -- which of the two candidate
	 * neighbours (next article line, next text line) is nearer, and what lies beyond it -- lives in {@code
	 * DocTextLinesRows#moveRow} ({@code de.metas.ui.web.base}), a class that reasons over the WebUI's merged-row
	 * view and is not on this module's classpath; {@code de.metas.cucumber} does not, and should not, depend on
	 * it, and that decision logic is not mechanical enough to move down into a shared, dependency-free location
	 * the way {@link DocTextLineRepository#articleLineExistsBefore} was for {@link #insertTextLineAboveOrderLine}
	 * -- it was evaluated and rejected; no cross-module dependency is added here and {@code moveRow} is not
	 * refactored. This method instead <b>re-implements</b> that branch choice and the far-side-bound lookup
	 * itself (from the actually-persisted {@code C_OrderLine}/{@code C_Doc_TextLine} rows), then hands the result
	 * to {@link DocTextLineRepository#computePositionBetween} and {@link DocTextLineRepository#updatePosition} --
	 * the same shared arithmetic and the same persistence call production uses, so the position it fabricates is
	 * arithmetically identical to what {@code moveRow} would produce, and the write itself is a genuine
	 * production call. {@code TextLineScope} is left untouched, matching production: moving a text line never
	 * changes its stored scope, even once it has crossed to the far side of an article line.
	 *
	 * <p><b>Coverage / drift risk.</b> The real move decision ({@code DocTextLinesRows#moveRow}) is unit-tested
	 * directly in {@code DocTextLinesQuickActionsTest} (e.g. {@code moveDown_acceptsWhenAnArticleRowFollowsImmediately},
	 * {@code moveUp_thenMoveDown_pastAnArticleRow_roundTripsWithoutTouchingEitherArticle}) -- this scenario's print
	 * assertions are genuine coverage of the resulting, reachable print state, but a mutation of {@code moveRow}
	 * itself could never turn this scenario red: if a future change to {@code moveRow}'s neighbour-selection logic
	 * changes what position it actually produces, this step keeps fabricating the OLD position and the scenario
	 * stays green while asserting a state the product no longer produces. The Line-value pin assertion right
	 * after this step tracks the shared LEAF arithmetic ({@link DocTextLineRepository#computePositionBetween})
	 * this step calls -- it changes if that arithmetic changes. It cannot track {@code moveRow}'s own
	 * neighbour-selection and far-side-bound decisions, because this step supplies those as arguments itself,
	 * independently of {@code moveRow}; the blind spot described above remains exactly that -- a blind spot,
	 * not one the pin closes.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * When the text line identified by "trailing" is moved down
	 * </pre>
	 */
	@When("the text line identified by {string} is moved down")
	public void moveTextLineDown(@NonNull final String textLineIdentifier)
	{
		final I_C_Doc_TextLine record = textLineTable.get(textLineIdentifier);
		InterfaceWrapperHelper.refresh(record);

		final OrderId orderId = OrderId.ofRepoId(record.getC_Order_ID());
		final DocTextLineDocumentRef documentRef = DocTextLineDocumentRef.ofOrderId(orderId);
		final BigDecimal currentPosition = record.getLine();

		final List<BigDecimal> articleLinePositions = orderDAO.retrieveOrderLines(orderId).stream()
				.map(orderLine -> BigDecimal.valueOf(orderLine.getLine()))
				.collect(Collectors.toList());
		final List<DocTextLine> textLines = docTextLineRepository.getByDocument(documentRef);

		final Optional<BigDecimal> nextArticlePosition = articleLinePositions.stream()
				.filter(position -> position.compareTo(currentPosition) > 0)
				.min(Comparator.naturalOrder());
		final Optional<DocTextLine> nextTextLine = textLines.stream()
				.filter(textLine -> textLine.getLine().compareTo(currentPosition) > 0)
				.min(Comparator.comparing(DocTextLine::getLine));

		if (!nextArticlePosition.isPresent() && !nextTextLine.isPresent())
		{
			throw new AdempiereException("Cannot move text line down: it is already the last row")
					.appendParametersToMessage()
					.setParameter("textLineIdentifier", textLineIdentifier);
		}

		// the nearer of the two candidate neighbours (by position) is the one that would actually be exchanged
		// with; only the article-line case is implemented below -- no scenario in this suite needs the
		// text-line-neighbour case, and untested arithmetic left in place merely LOOKS covered, so it is not
		// carried here. Extend this method (and its Javadoc) when a scenario actually needs it.
		final boolean neighbourIsArticleLine = nextTextLine
				.map(textLine -> nextArticlePosition.map(articlePos -> articlePos.compareTo(textLine.getLine()) < 0).orElse(false))
				.orElse(true);
		if (!neighbourIsArticleLine)
		{
			throw new AdempiereException("Move-down past a text-line neighbour is not covered by any scenario yet")
					.appendParametersToMessage()
					.setParameter("textLineIdentifier", textLineIdentifier);
		}

		final BigDecimal neighbourPosition = nextArticlePosition.get();

		// the next merged-order row after the neighbour article, of either kind -- null when the article
		// line is itself the last row, letting computePositionBetween place the text line past everything
		final Optional<BigDecimal> beyondArticle = articleLinePositions.stream()
				.filter(position -> position.compareTo(neighbourPosition) > 0)
				.min(Comparator.naturalOrder());
		final Optional<BigDecimal> beyondText = textLines.stream()
				.map(DocTextLine::getLine)
				.filter(position -> position.compareTo(neighbourPosition) > 0)
				.min(Comparator.naturalOrder());
		final BigDecimal beyondPosition = Stream.of(beyondArticle, beyondText)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.min(Comparator.naturalOrder())
				.orElse(null);

		final BigDecimal newPosition = DocTextLineRepository.computePositionBetween(neighbourPosition, beyondPosition);
		docTextLineRepository.updatePosition(DocTextLineId.ofRepoId(record.getC_Doc_TextLine_ID()), newPosition);

		InterfaceWrapperHelper.refresh(record);
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
	 * Asserts the persisted {@code Line} value of a text line -- a pin, not a derivation the step itself
	 * computes. Related to the drift risk documented on {@link #moveTextLineDown}, but narrower than it may
	 * look: this pin tracks the shared LEAF arithmetic ({@link DocTextLineRepository#computePositionBetween})
	 * that step calls -- a change to that arithmetic changes the pinned value. It does NOT track the WebUI's
	 * own {@code DocTextLinesRows#moveRow} neighbour-selection/far-side-bound decisions, because {@link
	 * #moveTextLineDown} supplies those as arguments itself, never by running {@code moveRow}; a change to
	 * THAT logic leaves this pin exactly where it was, still green. Compared via {@link BigDecimal#compareTo},
	 * not {@code equals}: the persisted value carries the column's own scale (e.g. {@code 21.0000}), which a
	 * plain string/int comparison would never match.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then the text line identified by "trailing" has Line "21"
	 * </pre>
	 */
	@Then("the text line identified by {string} has Line {string}")
	public void validateTextLineLine(
			@NonNull final String textLineIdentifier,
			@NonNull final String expectedLine)
	{
		final I_C_Doc_TextLine record = textLineTable.get(textLineIdentifier);
		InterfaceWrapperHelper.refresh(record);

		final BigDecimal actualLine = record.getLine();
		final BigDecimal expected = new BigDecimal(expectedLine);
		assertThat(actualLine.compareTo(expected))
				.as("Line of text line %s: expected %s, got %s", textLineIdentifier, expected, actualLine)
				.isZero();
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
