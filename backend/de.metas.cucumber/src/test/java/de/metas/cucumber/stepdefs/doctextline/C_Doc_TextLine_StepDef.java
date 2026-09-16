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
import de.metas.order.OrderId;
import de.metas.util.Services;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Doc_TextLine;
import org.compiere.model.I_C_OrderLine;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Creates and validates {@code C_Doc_TextLine} records belonging to a {@code C_Order} -- the free-text lines a
 * user can place between a sales order's article lines.
 */
@RequiredArgsConstructor
public class C_Doc_TextLine_StepDef
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
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
	 * and {@code C_Doc_TextLine} rows, and passes them to the repository. The repository is what then derives and
	 * persists {@code TextLineScope}; this step never sets it.
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

		final List<I_C_OrderLine> orderLines = queryBL.createQueryBuilder(I_C_OrderLine.class)
				.addEqualsFilter(I_C_OrderLine.COLUMNNAME_C_Order_ID, orderId.getRepoId())
				.create()
				.list();

		final boolean articleLineExistsBeforeReferencePosition = orderLines.stream()
				.anyMatch(orderLine -> orderLine.getLine() < referenceOrderLine.getLine());

		final BigDecimal previousArticleLinePosition = orderLines.stream()
				.filter(orderLine -> orderLine.getLine() < referenceOrderLine.getLine())
				.map(orderLine -> BigDecimal.valueOf(orderLine.getLine()))
				.max(Comparator.naturalOrder())
				.orElse(null);

		final BigDecimal previousTextLinePosition = docTextLineRepository.getByDocument(documentRef).stream()
				.map(DocTextLine::getLine)
				.filter(line -> line.compareTo(referencePosition) < 0)
				.max(Comparator.naturalOrder())
				.orElse(null);

		final BigDecimal previousPosition = maxOrNull(previousArticleLinePosition, previousTextLinePosition);

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

	@Nullable
	private static BigDecimal maxOrNull(@Nullable final BigDecimal a, @Nullable final BigDecimal b)
	{
		if (a == null)
		{
			return b;
		}
		if (b == null)
		{
			return a;
		}
		return a.compareTo(b) >= 0 ? a : b;
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
