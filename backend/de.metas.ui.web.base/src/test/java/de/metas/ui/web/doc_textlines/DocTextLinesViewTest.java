package de.metas.ui.web.doc_textlines;

import com.google.common.collect.ImmutableList;
import de.metas.business.BusinessTestHelper;
import de.metas.currency.CurrencyCode;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.money.CurrencyId;
import de.metas.doctextline.DocTextLineId;
import de.metas.doctextline.DocTextLineRepository;
import de.metas.doctextline.TextLineScope;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.security.IUserRolePermissions;
import de.metas.ui.web.shipment_candidates_editor.MockedLookupDataSource;
import de.metas.ui.web.view.IEditableView.RowEditingContext;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.descriptor.ViewEditorRenderMode;
import de.metas.ui.web.window.model.DocumentCollection;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Doc_TextLine;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

/**
 * Covers task 5's Done-when: a view built for an order with two article lines and one text line between them
 * returns three rows in merged order, with the article rows non-editable (DESIGN.md § D-E, § D-C). Also pins
 * the merged-ordering edge cases named in the task brief: a tie between an article line and a text line at the
 * exact same position, a text line before the first article line, and a document with text lines and no
 * article lines at all.
 */
class DocTextLinesViewTest
{
	private IOrderDAO orderDAO;
	private DocTextLineRepository docTextLineRepository;
	private OrderId orderId;
	private CurrencyId currencyId;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		orderDAO = Services.get(IOrderDAO.class);
		docTextLineRepository = new DocTextLineRepository();
		currencyId = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);

		final I_C_Order order = newInstance(I_C_Order.class);
		saveRecord(order);
		orderId = OrderId.ofRepoId(order.getC_Order_ID());
	}

	private I_C_OrderLine createArticleLine(final int line)
	{
		final I_C_UOM uom = BusinessTestHelper.createUomEach();
		final I_M_Product product = BusinessTestHelper.createProduct("product" + line, uom);

		final I_C_OrderLine orderLine = newInstance(I_C_OrderLine.class);
		orderLine.setC_Order_ID(orderId.getRepoId());
		orderLine.setM_Product_ID(product.getM_Product_ID());
		orderLine.setC_UOM_ID(uom.getC_UOM_ID());
		orderLine.setC_Currency_ID(currencyId.getRepoId());
		orderLine.setLine(line);
		orderLine.setQtyEntered(BigDecimal.TEN);
		orderLine.setQtyOrdered(BigDecimal.TEN);
		saveRecord(orderLine);
		return orderLine;
	}

	/** Bypasses {@link DocTextLineRepository#insertAbove} so the test controls the exact position -- the point
	 * of these tests is the merge, not the insert-above arithmetic already pinned by {@code DocTextLineRepositoryTest}. */
	private I_C_Doc_TextLine createTextLine(final String line, final TextLineScope scope, final String text)
	{
		final I_C_Doc_TextLine record = newInstance(I_C_Doc_TextLine.class);
		record.setC_Order_ID(orderId.getRepoId());
		record.setLine(new BigDecimal(line));
		record.setTextLineScope(scope.getCode());
		record.setTextLine(text);
		saveRecord(record);
		return record;
	}

	private DocTextLinesView loadView()
	{
		final DocTextLinesRows rows = DocTextLinesRowsLoader.builder()
				.orderDAO(orderDAO)
				.docTextLineRepository(docTextLineRepository)
				.productsLookup(MockedLookupDataSource.withNamePrefix("product"))
				.orderId(orderId)
				.build()
				.load();

		return DocTextLinesView.builder()
				.viewId(ViewId.random(DocTextLinesViewFactory.WINDOW_ID))
				.rows(rows)
				.build();
	}

	private static List<DocTextLinesRow> rowsOf(final DocTextLinesView view)
	{
		return view.streamByIds(DocumentIdsSelection.ALL).collect(Collectors.toList());
	}

	@Nested
	class mergedOrder
	{
		@Test
		void twoArticleLines_oneTextLineBetweenThem()
		{
			final I_C_OrderLine article10 = createArticleLine(10);
			final I_C_OrderLine article20 = createArticleLine(20);
			createTextLine("15", TextLineScope.Following, "some text");

			final List<DocTextLinesRow> rows = rowsOf(loadView());

			assertThat(rows).hasSize(3);

			assertThat(rows.get(0).isArticleLine()).isTrue();
			assertThat(rows.get(0).getOrderLineId().getRepoId()).isEqualTo(article10.getC_OrderLine_ID());

			assertThat(rows.get(1).isTextLine()).isTrue();
			assertThat(rows.get(1).getTextLine()).isEqualTo("some text");

			assertThat(rows.get(2).isArticleLine()).isTrue();
			assertThat(rows.get(2).getOrderLineId().getRepoId()).isEqualTo(article20.getC_OrderLine_ID());
		}

		@Test
		void tieAtTheSamePosition_textLineSortsBeforeTheArticleLine()
		{
			final I_C_OrderLine article15 = createArticleLine(15);
			createTextLine("15", TextLineScope.Following, "text at the same position");

			final List<DocTextLinesRow> rows = rowsOf(loadView());

			assertThat(rows).hasSize(2);
			assertThat(rows.get(0).isTextLine()).isTrue();
			assertThat(rows.get(1).isArticleLine()).isTrue();
			assertThat(rows.get(1).getOrderLineId().getRepoId()).isEqualTo(article15.getC_OrderLine_ID());
		}

		@Test
		void textLineBeforeTheFirstArticleLine()
		{
			createTextLine("1", TextLineScope.Document, "standing instruction");
			final I_C_OrderLine article10 = createArticleLine(10);

			final List<DocTextLinesRow> rows = rowsOf(loadView());

			assertThat(rows).hasSize(2);
			assertThat(rows.get(0).isTextLine()).isTrue();
			assertThat(rows.get(0).getTextLine()).isEqualTo("standing instruction");
			assertThat(rows.get(1).isArticleLine()).isTrue();
			assertThat(rows.get(1).getOrderLineId().getRepoId()).isEqualTo(article10.getC_OrderLine_ID());
		}

		@Test
		void textLinesOnly_noArticleLinesAtAll()
		{
			createTextLine("5", TextLineScope.Document, "first");
			createTextLine("10", TextLineScope.Document, "second");

			final List<DocTextLinesRow> rows = rowsOf(loadView());

			assertThat(rows).hasSize(2);
			assertThat(rows).allMatch(DocTextLinesRow::isTextLine);
			assertThat(rows.get(0).getTextLine()).isEqualTo("first");
			assertThat(rows.get(1).getTextLine()).isEqualTo("second");
		}

		@Test
		void noLinesAtAll_emptyView()
		{
			final List<DocTextLinesRow> rows = rowsOf(loadView());

			assertThat(rows).isEmpty();
		}
	}

	@Nested
	class editability
	{
		@Test
		void articleRows_areNeverEditable()
		{
			createArticleLine(10);
			createTextLine("15", TextLineScope.Following, "text");
			createArticleLine(20);

			final DocTextLinesRow articleRow = rowsOf(loadView()).stream()
					.filter(DocTextLinesRow::isArticleLine)
					.findFirst()
					.orElseThrow(IllegalStateException::new);

			assertThat(articleRow.getViewEditorRenderModeByFieldName().get(DocTextLinesRow.FIELD_TextLine))
					.isEqualTo(ViewEditorRenderMode.NEVER);
			assertThat(articleRow.getViewEditorRenderModeByFieldName().get(DocTextLinesRow.FIELD_TextLineScope))
					.isEqualTo(ViewEditorRenderMode.NEVER);
		}

		@Test
		void textRows_areEditable()
		{
			createArticleLine(10);
			createTextLine("5", TextLineScope.Document, "text");

			final DocTextLinesRow textRow = rowsOf(loadView()).stream()
					.filter(DocTextLinesRow::isTextLine)
					.findFirst()
					.orElseThrow(IllegalStateException::new);

			assertThat(textRow.getViewEditorRenderModeByFieldName().get(DocTextLinesRow.FIELD_TextLine))
					.isEqualTo(ViewEditorRenderMode.ALWAYS);
			assertThat(textRow.getViewEditorRenderModeByFieldName().get(DocTextLinesRow.FIELD_TextLineScope))
					.isEqualTo(ViewEditorRenderMode.ALWAYS);
		}
	}

	@Nested
	class rowIds
	{
		@Test
		void articleAndTextRowIds_areDistinctAndStable()
		{
			final I_C_OrderLine article10 = createArticleLine(10);
			final I_C_Doc_TextLine text5 = createTextLine("5", TextLineScope.Document, "text");

			final List<DocTextLinesRow> rows = rowsOf(loadView());
			final List<DocumentId> ids = rows.stream().map(DocTextLinesRow::getId).collect(Collectors.toList());

			assertThat(ids).doesNotHaveDuplicates();

			final DocTextLinesRow articleRow = rows.stream()
					.filter(DocTextLinesRow::isArticleLine)
					.findFirst()
					.orElseThrow(IllegalStateException::new);
			final OrderLineId article10Id = OrderLineId.ofRepoId(article10.getC_OrderLine_ID());
			assertThat(articleRow.getId()).isEqualTo(DocTextLinesRow.articleRowId(article10Id));
			assertThat(articleRow.getOrderLineId()).isEqualTo(article10Id);

			final DocTextLinesRow textRow = rows.stream()
					.filter(DocTextLinesRow::isTextLine)
					.findFirst()
					.orElseThrow(IllegalStateException::new);
			final DocTextLineId text5Id = DocTextLineId.ofRepoId(text5.getC_Doc_TextLine_ID());
			assertThat(textRow.getId()).isEqualTo(DocTextLinesRow.textRowId(text5Id));
			assertThat(textRow.getTextLineId()).isEqualTo(text5Id);
		}
	}

	/**
	 * Task-5 fix round 1, I-1: the seam a future insert-above quick-action (tasks 8/9) needs to derive
	 * {@code InsertAboveRequest}'s {@code referencePosition}/{@code previousPosition}/
	 * {@code articleLineExistsBeforeReferencePosition} from a selected row, without hand-rolling an index scan
	 * in the process class itself.
	 */
	@Nested
	class insertAbovePositions
	{
		@Test
		void middleTextRow_hasAnArticleLineBeforeIt()
		{
			final I_C_OrderLine article10 = createArticleLine(10);
			final I_C_Doc_TextLine text15 = createTextLine("15", TextLineScope.Following, "text");
			createArticleLine(20);

			final DocTextLinesView view = loadView();
			final DocumentId referenceRowId = DocTextLinesRow.textRowId(DocTextLineId.ofRepoId(text15.getC_Doc_TextLine_ID()));

			final InsertAbovePositions positions = view.getInsertAbovePositions(referenceRowId);

			assertThat(positions.getReferencePosition()).isEqualByComparingTo("15");
			assertThat(positions.getPreviousPosition()).isEqualByComparingTo(BigDecimal.valueOf(article10.getLine()));
			assertThat(positions.isArticleLineExistsBeforeReferencePosition()).isTrue();
		}

		@Test
		void firstRow_hasNoPreviousPositionAndNoArticleLineBeforeIt()
		{
			final I_C_OrderLine article10 = createArticleLine(10);
			createArticleLine(20);

			final DocTextLinesView view = loadView();
			final DocumentId referenceRowId = DocTextLinesRow.articleRowId(OrderLineId.ofRepoId(article10.getC_OrderLine_ID()));

			final InsertAbovePositions positions = view.getInsertAbovePositions(referenceRowId);

			assertThat(positions.getReferencePosition()).isEqualByComparingTo("10");
			assertThat(positions.getPreviousPosition()).isNull();
			assertThat(positions.isArticleLineExistsBeforeReferencePosition()).isFalse();
		}

		@Test
		void textLinesOnly_referencingTheSecondOne_hasNoArticleLineBeforeIt()
		{
			createTextLine("5", TextLineScope.Document, "first");
			final I_C_Doc_TextLine text10 = createTextLine("10", TextLineScope.Document, "second");

			final DocTextLinesView view = loadView();
			final DocumentId referenceRowId = DocTextLinesRow.textRowId(DocTextLineId.ofRepoId(text10.getC_Doc_TextLine_ID()));

			final InsertAbovePositions positions = view.getInsertAbovePositions(referenceRowId);

			assertThat(positions.getReferencePosition()).isEqualByComparingTo("10");
			assertThat(positions.getPreviousPosition()).isEqualByComparingTo("5");
			assertThat(positions.isArticleLineExistsBeforeReferencePosition()).isFalse();
		}

		@Test
		void emptyDocument_hasNoReferenceRowToSelect()
		{
			final DocTextLinesView view = loadView();

			final InsertAbovePositions positions = view.getInsertAbovePositions(null);

			assertThat(positions).isEqualTo(InsertAbovePositions.EMPTY_DOCUMENT);
		}
	}

	/**
	 * Task 6's Done-when (task-6-brief.md): patching the text and the scope of a text row persists both,
	 * and patching an article row is rejected (DESIGN.md § D-E / D-F). Patching goes through the real
	 * production entry point -- {@code AbstractCustomView#patchViewRow}, the same method
	 * {@code ViewRowEditRestController} calls -- so a wrong implementation of the row-patch path (e.g. one
	 * that silently no-ops, or one that lets article rows through) is caught the same way it would be in
	 * production.
	 */
	@Nested
	class patchRow
	{
		private void patch(final DocTextLinesView view, final DocumentId rowId, final JSONDocumentChangedEvent... events)
		{
			final RowEditingContext ctx = RowEditingContext.builder()
					.viewId(view.getViewId())
					.rowId(rowId)
					.documentsCollection(mock(DocumentCollection.class))
					.userRolePermissions(mock(IUserRolePermissions.class))
					.build();
			view.patchViewRow(ctx, ImmutableList.copyOf(events));
		}

		@Test
		void testPatchRow()
		{
			createArticleLine(10);
			final I_C_Doc_TextLine textLine = createTextLine("5", TextLineScope.Following, "original text");

			final DocTextLinesView view = loadView();
			final DocumentId rowId = DocTextLinesRow.textRowId(DocTextLineId.ofRepoId(textLine.getC_Doc_TextLine_ID()));

			patch(view, rowId,
					JSONDocumentChangedEvent.replace(DocTextLinesRow.FIELD_TextLine, "updated text"),
					JSONDocumentChangedEvent.replace(DocTextLinesRow.FIELD_TextLineScope, TextLineScope.Document.getCode()));

			// reload from DB via a fresh view -- proves the patch was persisted, not just held in memory
			final DocTextLinesRow persistedRow = rowsOf(loadView()).stream()
					.filter(DocTextLinesRow::isTextLine)
					.findFirst()
					.orElseThrow(IllegalStateException::new);

			assertThat(persistedRow.getTextLine()).isEqualTo("updated text");
			assertThat(persistedRow.getTextLineScope()).isEqualTo(TextLineScope.Document);
		}

		@Test
		void emptyText_remainsValid()
		{
			final I_C_Doc_TextLine textLine = createTextLine("5", TextLineScope.Document, "some text");

			final DocTextLinesView view = loadView();
			final DocumentId rowId = DocTextLinesRow.textRowId(DocTextLineId.ofRepoId(textLine.getC_Doc_TextLine_ID()));

			patch(view, rowId, JSONDocumentChangedEvent.replace(DocTextLinesRow.FIELD_TextLine, ""));

			final DocTextLinesRow persistedRow = rowsOf(loadView()).get(0);
			assertThat(persistedRow.getTextLine()).isEqualTo("");
			assertThat(persistedRow.getTextLineScope()).isEqualTo(TextLineScope.Document); // untouched field survives the patch
		}

		@Test
		void articleRow_isRejected()
		{
			final I_C_OrderLine article = createArticleLine(10);

			final DocTextLinesView view = loadView();
			final DocumentId rowId = DocTextLinesRow.articleRowId(OrderLineId.ofRepoId(article.getC_OrderLine_ID()));

			// message must name the article-row rejection specifically -- not just any "view is not editable"
			// wording, which is also what AbstractCustomView throws pre-implementation (a weaker assertion here
			// would pass vacuously against the unimplemented feature)
			assertThatThrownBy(() -> patch(view, rowId, JSONDocumentChangedEvent.replace(DocTextLinesRow.FIELD_TextLine, "hack")))
					.isInstanceOf(AdempiereException.class)
					.hasMessageContaining("Article");

			// the article line's own DB row is untouched
			final I_C_OrderLine reloaded = load(article.getC_OrderLine_ID(), I_C_OrderLine.class);
			assertThat(reloaded.getLine()).isEqualTo(10);
		}
	}
}
