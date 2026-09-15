package de.metas.ui.web.doc_textlines;

import com.google.common.collect.ImmutableList;
import de.metas.doctextline.DocTextLine;
import de.metas.doctextline.DocTextLineDocumentRef;
import de.metas.doctextline.DocTextLineRepository;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import lombok.Builder;
import lombok.NonNull;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

/**
 * Loads and merges the two halves of {@link DocTextLinesView}: the order's article lines ({@code C_OrderLine},
 * read via {@link IOrderDAO}, which the persistence-layer boundary lets this view-layer class query directly)
 * and its text lines ({@code C_Doc_TextLine}, via {@link DocTextLineRepository}, which does NOT query article
 * lines itself -- see {@code InsertAboveRequest}'s javadoc). This is the one place that holds the full merged
 * ordering: article-line position and text-line position share one numeric space, so a plain "line ascending,
 * text-before-article on a tie" sort is the whole algorithm.
 */
final class DocTextLinesRowsLoader
{
	/**
	 * {@code ORDER BY line, sort_rank} with text ranked before article on a tie -- an article line created at
	 * the same position as an existing text line sorts into the run the text introduces.
	 */
	private static final Comparator<DocTextLinesRow> MERGED_ORDER =
			Comparator.comparing(DocTextLinesRow::getLine)
					.thenComparing(row -> row.isTextLine() ? 0 : 1);

	private final IOrderDAO orderDAO;
	private final DocTextLineRepository docTextLineRepository;
	private final LookupDataSource productsLookup;
	private final OrderId orderId;

	@Builder
	private DocTextLinesRowsLoader(
			@NonNull final IOrderDAO orderDAO,
			@NonNull final DocTextLineRepository docTextLineRepository,
			@NonNull final LookupDataSource productsLookup,
			@NonNull final OrderId orderId)
	{
		this.orderDAO = orderDAO;
		this.docTextLineRepository = docTextLineRepository;
		this.productsLookup = productsLookup;
		this.orderId = orderId;
	}

	public DocTextLinesRows load()
	{
		final List<I_C_OrderLine> orderLines = orderDAO.retrieveOrderLines(orderId);
		final List<DocTextLine> textLines = docTextLineRepository.getByDocument(DocTextLineDocumentRef.ofOrderId(orderId));

		final ImmutableList<DocTextLinesRow> rows = Stream.concat(
						orderLines.stream().map(this::toArticleRow),
						textLines.stream().map(DocTextLinesRowsLoader::toTextRow))
				.sorted(MERGED_ORDER)
				.collect(ImmutableList.toImmutableList());

		return DocTextLinesRows.builder()
				.rows(rows)
				.docTextLineRepository(docTextLineRepository)
				.build();
	}

	private DocTextLinesRow toArticleRow(@NonNull final I_C_OrderLine orderLine)
	{
		final OrderLineId orderLineId = OrderLineId.ofRepoId(orderLine.getC_OrderLine_ID());
		final ProductId productId = ProductId.ofRepoId(orderLine.getM_Product_ID());

		return DocTextLinesRow.builder()
				.rowType(DocTextLinesRow.RowType.ARTICLE)
				.line(BigDecimal.valueOf(orderLine.getLine()))
				.orderLineId(orderLineId)
				.product(productsLookup.findById(productId))
				.qty(orderLine.getQtyOrdered())
				.build();
	}

	private static DocTextLinesRow toTextRow(@NonNull final DocTextLine textLine)
	{
		return DocTextLinesRow.ofTextLine(textLine);
	}
}
