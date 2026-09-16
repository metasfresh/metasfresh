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
final class DocTextLinesRowsLoader implements DocTextLinesDocumentAccess
{
	/**
	 * {@code ORDER BY line, sort_rank} with text ranked before article on a tie -- an article line created at
	 * the same position as an existing text line sorts into the run the text introduces.
	 */
	static final Comparator<DocTextLinesRow> MERGED_ORDER =
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
		return DocTextLinesRows.builder()
				.rows(loadMergedRows())
				.documentRef(DocTextLineDocumentRef.ofOrderId(orderId))
				.docTextLineRepository(docTextLineRepository)
				.documentAccess(this)
				.build();
	}

	/**
	 * Locks the order's own record for the rest of the current transaction, through the DAO that owns
	 * {@code C_Order} -- the same DAO this class already reads the article lines from, so the rows holder
	 * still reaches nothing it does not own.
	 */
	@Override
	public void lockDocumentForUpdate()
	{
		orderDAO.lockByIdForUpdate(orderId);
	}

	/**
	 * Reads both halves and merges them, in merged order. Called once to build the view's rows, and again by
	 * {@link DocTextLinesRows} inside each structural write, which must place a row against the order as it
	 * stands in the database at that moment rather than as it stood when the view was created -- see
	 * {@code DocTextLinesRows#refreshMergedOrderFromDatabase}. Keeping that re-derivation here rather than
	 * spelling the merge out a second time in the writer is what keeps the two orderings the same ordering:
	 * the merge rule lives in exactly one place, and the repository stays out of the article-line tables it
	 * does not own.
	 */
	@Override
	public ImmutableList<DocTextLinesRow> loadMergedRows()
	{
		final List<I_C_OrderLine> orderLines = orderDAO.retrieveOrderLines(orderId);
		final List<DocTextLine> textLines = docTextLineRepository.getByDocument(DocTextLineDocumentRef.ofOrderId(orderId));

		return Stream.concat(
						orderLines.stream().map(this::toArticleRow),
						textLines.stream().map(DocTextLinesRowsLoader::toTextRow))
				.sorted(MERGED_ORDER)
				.collect(ImmutableList.toImmutableList());
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
