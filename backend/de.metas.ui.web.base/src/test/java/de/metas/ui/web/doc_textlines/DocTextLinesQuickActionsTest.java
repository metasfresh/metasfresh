package de.metas.ui.web.doc_textlines;

import com.google.common.collect.ImmutableList;
import de.metas.business.BusinessTestHelper;
import de.metas.currency.CurrencyCode;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.doctextline.DocTextLine;
import de.metas.doctextline.DocTextLineDocumentRef;
import de.metas.doctextline.DocTextLineRepository;
import de.metas.doctextline.TextLineScope;
import de.metas.money.CurrencyId;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.doctextline.DocTextLineId;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.security.IUserRolePermissions;
import de.metas.ui.web.doc_textlines.process.WEBUI_DocTextLines_Delete;
import de.metas.ui.web.doc_textlines.process.WEBUI_DocTextLines_InsertAbove;
import de.metas.ui.web.doc_textlines.process.WEBUI_DocTextLines_MoveDown;
import de.metas.ui.web.doc_textlines.process.WEBUI_DocTextLines_MoveUp;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.shipment_candidates_editor.MockedLookupDataSource;
import de.metas.ui.web.view.IEditableView.RowEditingContext;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.model.DocumentCollection;
import de.metas.util.Services;
import de.metas.websocket.sender.WebsocketSender;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Doc_TextLine;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

/**
 * {@link WEBUI_DocTextLines_InsertAbove} -- the quick action that inserts a new, empty text row above the
 * selected row, deriving its position from {@link DocTextLinesView#getInsertAbovePositions} and persisting it
 * via {@link DocTextLineRepository#insertAbove}.
 */
class DocTextLinesQuickActionsTest
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

		// WEBUI_DocTextLines_InsertAbove extends ViewBasedProcessTemplate, whose own field initializer does an
		// eager SpringContextHolder.instance.getBean() lookup -- register that bean type, same pattern as
		// WEBUI_Order_DocTextLines_LauncherTest. DocTextLineRepository is NOT looked up via Spring by the
		// process -- it is constructor-injected into DocTextLinesRowsLoader below, per view/rows instance.
		SpringContextHolder.registerJUnitBean(IViewsRepository.class, mock(IViewsRepository.class));
		// insertRowAbove() invalidates the view, which publishes a change event over the websocket bus
		SpringContextHolder.registerJUnitBean(WebsocketSender.class, mock(WebsocketSender.class));

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

	/** Creates a text row directly at a chosen position/scope -- bypassing insertAbove's midpoint arithmetic so fixtures can use round, readable positions. */
	private I_C_Doc_TextLine createTextLine(final int line, final TextLineScope scope)
	{
		final I_C_Doc_TextLine record = newInstance(I_C_Doc_TextLine.class);
		record.setC_Order_ID(orderId.getRepoId());
		record.setLine(BigDecimal.valueOf(line));
		record.setTextLineScope(scope.getCode());
		record.setTextLine("text at " + line);
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
				.documentRef(DocTextLineDocumentRef.ofOrderId(orderId))
				.build();
	}

	private static List<DocTextLinesRow> rowsOf(final DocTextLinesView view)
	{
		return view.streamByIds(DocumentIdsSelection.ALL).collect(Collectors.toList());
	}

	@Nested
	class testInsertAbove
	{
		@Test
		void aboveAMiddleRow()
		{
			final I_C_OrderLine article10 = createArticleLine(10);
			final I_C_OrderLine article20 = createArticleLine(20);
			createArticleLine(30);

			final DocTextLinesView view = loadView();
			final DocumentId referenceRowId = DocTextLinesRow.articleRowId(OrderLineId.ofRepoId(article20.getC_OrderLine_ID()));

			new WEBUI_DocTextLines_InsertAbove().insertAbove(view, referenceRowId);

			final List<DocTextLinesRow> rows = rowsOf(view);
			assertThat(rows).hasSize(4);

			final DocTextLinesRow newRow = rows.get(1);
			assertThat(newRow.isTextLine()).isTrue();
			assertThat(newRow.getLine()).isEqualByComparingTo("15"); // midpoint of 10 and 20
			assertThat(newRow.getTextLine()).isEqualTo("");
			// article10 sits before position 15 -- the new line joins the run beneath it
			assertThat(newRow.getTextLineScope()).isEqualTo(TextLineScope.Following);

			assertThat(rows.get(0).getOrderLineId()).isEqualTo(OrderLineId.ofRepoId(article10.getC_OrderLine_ID()));
			assertThat(rows.get(2).getOrderLineId()).isEqualTo(OrderLineId.ofRepoId(article20.getC_OrderLine_ID()));

			// persisted -- a fresh view reload sees it too, not just the in-memory one
			assertThat(rowsOf(loadView())).hasSize(4);
		}

		@Test
		void aboveTheFirstRow()
		{
			final I_C_OrderLine article10 = createArticleLine(10);
			createArticleLine(20);

			final DocTextLinesView view = loadView();
			final DocumentId referenceRowId = DocTextLinesRow.articleRowId(OrderLineId.ofRepoId(article10.getC_OrderLine_ID()));

			new WEBUI_DocTextLines_InsertAbove().insertAbove(view, referenceRowId);

			final List<DocTextLinesRow> rows = rowsOf(view);
			assertThat(rows).hasSize(3);

			final DocTextLinesRow newRow = rows.get(0);
			assertThat(newRow.isTextLine()).isTrue();
			assertThat(newRow.getLine()).isEqualByComparingTo("5"); // half of 10, nothing precedes it
			// no article line precedes the first row -- the new line captures a standing instruction
			assertThat(newRow.getTextLineScope()).isEqualTo(TextLineScope.Document);

			assertThat(rowsOf(loadView())).hasSize(3);
		}

		@Test
		void onADocumentWithNoRowsAtAll()
		{
			final DocTextLinesView view = loadView();
			assertThat(rowsOf(view)).isEmpty();

			new WEBUI_DocTextLines_InsertAbove().insertAbove(view, null);

			final List<DocTextLinesRow> rows = rowsOf(view);
			assertThat(rows).hasSize(1);

			final DocTextLinesRow newRow = rows.get(0);
			assertThat(newRow.isTextLine()).isTrue();
			assertThat(newRow.getLine()).isEqualByComparingTo("1");
			assertThat(newRow.getTextLineScope()).isEqualTo(TextLineScope.Document);

			assertThat(rowsOf(loadView())).hasSize(1);
		}

		/**
		 * {@link DocTextLinesRows#rowLocksById} is built once at construction; an insert that widens
		 * {@code rowsById} without widening {@code rowLocksById} in lockstep would make the new row's very
		 * first patch throw {@code EntityNotFoundException} instead of persisting the edit.
		 */
		@Test
		void insertedRow_isImmediatelyPatchable()
		{
			final I_C_OrderLine article10 = createArticleLine(10);

			final DocTextLinesView view = loadView();
			final DocumentId referenceRowId = DocTextLinesRow.articleRowId(OrderLineId.ofRepoId(article10.getC_OrderLine_ID()));

			new WEBUI_DocTextLines_InsertAbove().insertAbove(view, referenceRowId);

			final DocTextLinesRow newRow = rowsOf(view).stream()
					.filter(DocTextLinesRow::isTextLine)
					.findFirst()
					.orElseThrow(IllegalStateException::new);
			final DocumentId newRowId = newRow.getId();

			final RowEditingContext ctx = RowEditingContext.builder()
					.viewId(view.getViewId())
					.rowId(newRowId)
					.documentsCollection(mock(DocumentCollection.class))
					.userRolePermissions(mock(IUserRolePermissions.class))
					.build();
			view.patchViewRow(ctx, ImmutableList.of(JSONDocumentChangedEvent.replace(DocTextLinesRow.FIELD_TextLine, "typed after insert")));

			assertThat(view.getById(newRowId).getTextLine()).isEqualTo("typed after insert");
		}
	}

	@Nested
	class testDelete
	{
		@Test
		void removesTheSelectedTextRow()
		{
			createArticleLine(10);
			final I_C_Doc_TextLine textLine = createTextLine(5, TextLineScope.Document);

			final DocTextLinesView view = loadView();
			final DocumentId rowId = DocTextLinesRow.textRowId(DocTextLineId.ofRepoId(textLine.getC_Doc_TextLine_ID()));

			new WEBUI_DocTextLines_Delete().delete(view, rowId);

			assertThat(rowsOf(view)).hasSize(1);
			assertThat(rowsOf(view).get(0).isArticleLine()).isTrue();

			// persisted -- a fresh view reload sees it too, not just the in-memory one
			assertThat(rowsOf(loadView())).hasSize(1);
		}

		@Test
		void doesNotAffectOtherRows()
		{
			final I_C_Doc_TextLine toDelete = createTextLine(5, TextLineScope.Document);
			final I_C_Doc_TextLine toKeep = createTextLine(15, TextLineScope.Document);

			final DocTextLinesView view = loadView();
			final DocumentId rowIdToDelete = DocTextLinesRow.textRowId(DocTextLineId.ofRepoId(toDelete.getC_Doc_TextLine_ID()));

			new WEBUI_DocTextLines_Delete().delete(view, rowIdToDelete);

			final List<DocTextLinesRow> rows = rowsOf(loadView());
			assertThat(rows).hasSize(1);
			assertThat(rows.get(0).getTextLineId()).isEqualTo(DocTextLineId.ofRepoId(toKeep.getC_Doc_TextLine_ID()));
		}

		/**
		 * {@link DocTextLinesRows#deleteRow} clears {@link DocTextLinesRows#rowLocksById} in lockstep with
		 * {@link DocTextLinesRows#rowsById} -- a follow-up patch of the deleted row must fail cleanly rather than
		 * silently resurrecting it.
		 */
		@Test
		void aFollowUpPatchOfTheDeletedRowFailsCleanly()
		{
			final I_C_Doc_TextLine textLine = createTextLine(5, TextLineScope.Document);
			final DocTextLinesView view = loadView();
			final DocumentId rowId = DocTextLinesRow.textRowId(DocTextLineId.ofRepoId(textLine.getC_Doc_TextLine_ID()));

			new WEBUI_DocTextLines_Delete().delete(view, rowId);

			final RowEditingContext ctx = RowEditingContext.builder()
					.viewId(view.getViewId())
					.rowId(rowId)
					.documentsCollection(mock(DocumentCollection.class))
					.userRolePermissions(mock(IUserRolePermissions.class))
					.build();

			assertThatThrownBy(() -> view.patchViewRow(ctx, ImmutableList.of(JSONDocumentChangedEvent.replace(DocTextLinesRow.FIELD_TextLine, "too late"))))
					.isInstanceOf(EntityNotFoundException.class);
		}
	}

	@Nested
	class testMove
	{
		/**
		 * article10, T1(15, scope Document), article20, T2(25, scope Following), article30 -- moving T1 down
		 * must jump over article20 (whose {@code Line} is never touched) and swap positions with T2, the
		 * nearest text-row neighbour. The two text rows' scopes travel WITH their row identity, unchanged --
		 * only their {@code Line} swaps.
		 */
		@Test
		void moveDown_jumpsOverAnArticleRowToSwapWithTheNextTextRow()
		{
			final I_C_OrderLine article10 = createArticleLine(10);
			final I_C_Doc_TextLine t1 = createTextLine(15, TextLineScope.Document);
			final I_C_OrderLine article20 = createArticleLine(20);
			final I_C_Doc_TextLine t2 = createTextLine(25, TextLineScope.Following);
			final I_C_OrderLine article30 = createArticleLine(30);

			final DocTextLinesView view = loadView();
			final DocumentId t1RowId = DocTextLinesRow.textRowId(DocTextLineId.ofRepoId(t1.getC_Doc_TextLine_ID()));
			final DocumentId t2RowId = DocTextLinesRow.textRowId(DocTextLineId.ofRepoId(t2.getC_Doc_TextLine_ID()));

			new WEBUI_DocTextLines_MoveDown().moveDown(view, t1RowId);

			// merged order: article10, T2(now first, line 15), article20, T1(now second, line 25), article30
			final List<DocTextLinesRow> rows = rowsOf(view);
			assertThat(rows).extracting(DocTextLinesRow::getId)
					.containsExactly(
							DocTextLinesRow.articleRowId(OrderLineId.ofRepoId(article10.getC_OrderLine_ID())),
							t2RowId,
							DocTextLinesRow.articleRowId(OrderLineId.ofRepoId(article20.getC_OrderLine_ID())),
							t1RowId,
							DocTextLinesRow.articleRowId(OrderLineId.ofRepoId(article30.getC_OrderLine_ID())));

			assertThat(view.getById(t1RowId).getLine()).isEqualByComparingTo("25");
			assertThat(view.getById(t1RowId).getTextLineScope()).as("moving must not change the moved row's stored scope").isEqualTo(TextLineScope.Document);
			assertThat(view.getById(t2RowId).getLine()).isEqualByComparingTo("15");
			assertThat(view.getById(t2RowId).getTextLineScope()).as("the swapped-with row's scope is untouched too").isEqualTo(TextLineScope.Following);

			// persisted -- a fresh view reload sees the same order and scopes, not just the in-memory one
			final List<DocTextLinesRow> reloaded = rowsOf(loadView());
			assertThat(reloaded).extracting(DocTextLinesRow::getId).containsExactly(
					DocTextLinesRow.articleRowId(OrderLineId.ofRepoId(article10.getC_OrderLine_ID())),
					t2RowId,
					DocTextLinesRow.articleRowId(OrderLineId.ofRepoId(article20.getC_OrderLine_ID())),
					t1RowId,
					DocTextLinesRow.articleRowId(OrderLineId.ofRepoId(article30.getC_OrderLine_ID())));
		}

		/** The mirror action, invoked from the other end: moving T2 up reaches the identical end state. */
		@Test
		void moveUp_jumpsOverAnArticleRowToSwapWithThePreviousTextRow()
		{
			createArticleLine(10);
			final I_C_Doc_TextLine t1 = createTextLine(15, TextLineScope.Document);
			createArticleLine(20);
			final I_C_Doc_TextLine t2 = createTextLine(25, TextLineScope.Following);
			createArticleLine(30);

			final DocTextLinesView view = loadView();
			final DocumentId t1RowId = DocTextLinesRow.textRowId(DocTextLineId.ofRepoId(t1.getC_Doc_TextLine_ID()));
			final DocumentId t2RowId = DocTextLinesRow.textRowId(DocTextLineId.ofRepoId(t2.getC_Doc_TextLine_ID()));

			new WEBUI_DocTextLines_MoveUp().moveUp(view, t2RowId);

			assertThat(view.getById(t1RowId).getLine()).isEqualByComparingTo("25");
			assertThat(view.getById(t1RowId).getTextLineScope()).isEqualTo(TextLineScope.Document);
			assertThat(view.getById(t2RowId).getLine()).isEqualByComparingTo("15");
			assertThat(view.getById(t2RowId).getTextLineScope()).isEqualTo(TextLineScope.Following);
		}

		@Test
		void moveUp_onTheFirstTextRow_throws()
		{
			createArticleLine(10);
			final I_C_Doc_TextLine t1 = createTextLine(15, TextLineScope.Document);
			createArticleLine(20);

			final DocTextLinesView view = loadView();
			final DocumentId t1RowId = DocTextLinesRow.textRowId(DocTextLineId.ofRepoId(t1.getC_Doc_TextLine_ID()));

			assertThatThrownBy(() -> new WEBUI_DocTextLines_MoveUp().moveUp(view, t1RowId))
					.isInstanceOf(org.adempiere.exceptions.AdempiereException.class);
		}

		@Test
		void moveDown_onTheLastTextRow_throws()
		{
			createArticleLine(10);
			final I_C_Doc_TextLine t1 = createTextLine(15, TextLineScope.Document);
			createArticleLine(20);

			final DocTextLinesView view = loadView();
			final DocumentId t1RowId = DocTextLinesRow.textRowId(DocTextLineId.ofRepoId(t1.getC_Doc_TextLine_ID()));

			assertThatThrownBy(() -> new WEBUI_DocTextLines_MoveDown().moveDown(view, t1RowId))
					.isInstanceOf(org.adempiere.exceptions.AdempiereException.class);
		}
	}

	/**
	 * The frozen requirement is: a text line is added by selecting a row in the modal, appearing directly
	 * above it; a separate requirement covers the empty-document case, where the action must still work with
	 * nothing to select. So zero selection is only legal when the view itself has no rows to select from.
	 */
	@Nested
	class checkPreconditionsApplicable
	{
		@Test
		void rejectsZeroSelectionOnANonEmptyView()
		{
			createArticleLine(10);
			final DocTextLinesView view = loadView();

			final ProcessPreconditionsResolution resolution =
					WEBUI_DocTextLines_InsertAbove.checkInsertAbovePreconditions(view, DocumentIdsSelection.EMPTY);

			assertThat(resolution.isAccepted()).isFalse();
		}

		@Test
		void acceptsZeroSelectionOnAnEmptyView()
		{
			final DocTextLinesView view = loadView();
			assertThat(rowsOf(view)).isEmpty();

			final ProcessPreconditionsResolution resolution =
					WEBUI_DocTextLines_InsertAbove.checkInsertAbovePreconditions(view, DocumentIdsSelection.EMPTY);

			assertThat(resolution.isAccepted()).isTrue();
		}

		@Test
		void acceptsASingleSelectionOnANonEmptyView()
		{
			final I_C_OrderLine article10 = createArticleLine(10);
			final DocTextLinesView view = loadView();
			final DocumentId rowId = DocTextLinesRow.articleRowId(OrderLineId.ofRepoId(article10.getC_OrderLine_ID()));

			final ProcessPreconditionsResolution resolution =
					WEBUI_DocTextLines_InsertAbove.checkInsertAbovePreconditions(view, DocumentIdsSelection.fromNullable(rowId));

			assertThat(resolution.isAccepted()).isTrue();
		}

		@Test
		void rejectsMoreThanOneSelection()
		{
			final I_C_OrderLine article10 = createArticleLine(10);
			final I_C_OrderLine article20 = createArticleLine(20);
			final DocTextLinesView view = loadView();
			final DocumentId rowId10 = DocTextLinesRow.articleRowId(OrderLineId.ofRepoId(article10.getC_OrderLine_ID()));
			final DocumentId rowId20 = DocTextLinesRow.articleRowId(OrderLineId.ofRepoId(article20.getC_OrderLine_ID()));

			final ProcessPreconditionsResolution resolution = WEBUI_DocTextLines_InsertAbove.checkInsertAbovePreconditions(
					view, DocumentIdsSelection.of(ImmutableList.of(rowId10, rowId20)));

			assertThat(resolution.isAccepted()).isFalse();
		}
	}

	@Nested
	class checkDeletePreconditionsApplicable
	{
		@Test
		void rejectsZeroSelection()
		{
			createArticleLine(10);
			final DocTextLinesView view = loadView();

			final ProcessPreconditionsResolution resolution = WEBUI_DocTextLines_Delete.checkDeletePreconditions(view, DocumentIdsSelection.EMPTY);

			assertThat(resolution.isAccepted()).isFalse();
		}

		@Test
		void rejectsMoreThanOneSelection()
		{
			final I_C_Doc_TextLine t1 = createTextLine(5, TextLineScope.Document);
			final I_C_Doc_TextLine t2 = createTextLine(15, TextLineScope.Document);
			final DocTextLinesView view = loadView();
			final DocumentId rowId1 = DocTextLinesRow.textRowId(DocTextLineId.ofRepoId(t1.getC_Doc_TextLine_ID()));
			final DocumentId rowId2 = DocTextLinesRow.textRowId(DocTextLineId.ofRepoId(t2.getC_Doc_TextLine_ID()));

			final ProcessPreconditionsResolution resolution = WEBUI_DocTextLines_Delete.checkDeletePreconditions(
					view, DocumentIdsSelection.of(ImmutableList.of(rowId1, rowId2)));

			assertThat(resolution.isAccepted()).isFalse();
		}

		@Test
		void rejectsAnArticleRowSelection()
		{
			final I_C_OrderLine article10 = createArticleLine(10);
			final DocTextLinesView view = loadView();
			final DocumentId rowId = DocTextLinesRow.articleRowId(OrderLineId.ofRepoId(article10.getC_OrderLine_ID()));

			final ProcessPreconditionsResolution resolution =
					WEBUI_DocTextLines_Delete.checkDeletePreconditions(view, DocumentIdsSelection.fromNullable(rowId));

			assertThat(resolution.isAccepted()).isFalse();
		}

		@Test
		void acceptsASingleTextRowSelection()
		{
			final I_C_Doc_TextLine t1 = createTextLine(5, TextLineScope.Document);
			final DocTextLinesView view = loadView();
			final DocumentId rowId = DocTextLinesRow.textRowId(DocTextLineId.ofRepoId(t1.getC_Doc_TextLine_ID()));

			final ProcessPreconditionsResolution resolution =
					WEBUI_DocTextLines_Delete.checkDeletePreconditions(view, DocumentIdsSelection.fromNullable(rowId));

			assertThat(resolution.isAccepted()).isTrue();
		}
	}

	@Nested
	class checkMovePreconditionsApplicable
	{
		@Test
		void moveUp_rejectsZeroSelection()
		{
			createArticleLine(10);
			final DocTextLinesView view = loadView();

			final ProcessPreconditionsResolution resolution = WEBUI_DocTextLines_MoveUp.checkMoveUpPreconditions(view, DocumentIdsSelection.EMPTY);

			assertThat(resolution.isAccepted()).isFalse();
		}

		@Test
		void moveDown_rejectsZeroSelection()
		{
			createArticleLine(10);
			final DocTextLinesView view = loadView();

			final ProcessPreconditionsResolution resolution = WEBUI_DocTextLines_MoveDown.checkMoveDownPreconditions(view, DocumentIdsSelection.EMPTY);

			assertThat(resolution.isAccepted()).isFalse();
		}

		@Test
		void moveUp_rejectsAnArticleRowSelection()
		{
			final I_C_OrderLine article10 = createArticleLine(10);
			final DocTextLinesView view = loadView();
			final DocumentId rowId = DocTextLinesRow.articleRowId(OrderLineId.ofRepoId(article10.getC_OrderLine_ID()));

			final ProcessPreconditionsResolution resolution =
					WEBUI_DocTextLines_MoveUp.checkMoveUpPreconditions(view, DocumentIdsSelection.fromNullable(rowId));

			assertThat(resolution.isAccepted()).isFalse();
		}

		@Test
		void moveUp_rejectsTheFirstTextRow()
		{
			createArticleLine(10);
			final I_C_Doc_TextLine t1 = createTextLine(15, TextLineScope.Document);
			createArticleLine(20);
			final DocTextLinesView view = loadView();
			final DocumentId rowId = DocTextLinesRow.textRowId(DocTextLineId.ofRepoId(t1.getC_Doc_TextLine_ID()));

			final ProcessPreconditionsResolution resolution =
					WEBUI_DocTextLines_MoveUp.checkMoveUpPreconditions(view, DocumentIdsSelection.fromNullable(rowId));

			assertThat(resolution.isAccepted()).isFalse();
		}

		@Test
		void moveDown_rejectsTheLastTextRow()
		{
			createArticleLine(10);
			final I_C_Doc_TextLine t1 = createTextLine(15, TextLineScope.Document);
			createArticleLine(20);
			final DocTextLinesView view = loadView();
			final DocumentId rowId = DocTextLinesRow.textRowId(DocTextLineId.ofRepoId(t1.getC_Doc_TextLine_ID()));

			final ProcessPreconditionsResolution resolution =
					WEBUI_DocTextLines_MoveDown.checkMoveDownPreconditions(view, DocumentIdsSelection.fromNullable(rowId));

			assertThat(resolution.isAccepted()).isFalse();
		}

		@Test
		void moveDown_acceptsATextRowWithAFollowingTextRow()
		{
			createArticleLine(10);
			final I_C_Doc_TextLine t1 = createTextLine(15, TextLineScope.Document);
			createArticleLine(20);
			createTextLine(25, TextLineScope.Following);
			createArticleLine(30);
			final DocTextLinesView view = loadView();
			final DocumentId rowId = DocTextLinesRow.textRowId(DocTextLineId.ofRepoId(t1.getC_Doc_TextLine_ID()));

			final ProcessPreconditionsResolution resolution =
					WEBUI_DocTextLines_MoveDown.checkMoveDownPreconditions(view, DocumentIdsSelection.fromNullable(rowId));

			assertThat(resolution.isAccepted()).isTrue();
		}
	}

	/**
	 * Two concurrent insert-above requests against the SAME reference row -- two browser tabs open on one
	 * order, which is exactly the scenario {@code patchRow}'s own per-row locking already treats as real --
	 * must not both read the same reference/previous positions and persist the same midpoint {@code Line}.
	 */
	@Nested
	class concurrency
	{
		@Test
		void concurrentInsertsAboveTheSameReferenceRow_doNotProduceDuplicatePositions() throws InterruptedException
		{
			final I_C_OrderLine article10 = createArticleLine(10);
			final DocumentId referenceRowId = DocTextLinesRow.articleRowId(OrderLineId.ofRepoId(article10.getC_OrderLine_ID()));

			final AtomicInteger callIndex = new AtomicInteger(0);
			final CountDownLatch aReachedPersist = new CountDownLatch(1);
			final CountDownLatch releaseA = new CountDownLatch(1);

			final DocTextLineRepository racingRepository = Mockito.spy(docTextLineRepository);
			Mockito.doAnswer(invocation -> {
						final int index = callIndex.getAndIncrement();
						if (index == 0)
						{
							aReachedPersist.countDown();
							if (!releaseA.await(5, TimeUnit.SECONDS))
							{
								throw new IllegalStateException("test bug: releaseA was never signalled");
							}
						}
						return invocation.callRealMethod();
					})
					.when(racingRepository)
					.insertAbove(any());

			final DocTextLinesRows rows = DocTextLinesRowsLoader.builder()
					.orderDAO(orderDAO)
					.docTextLineRepository(racingRepository)
					.productsLookup(MockedLookupDataSource.withNamePrefix("product"))
					.orderId(orderId)
					.build()
					.load();
			final DocTextLinesView view = DocTextLinesView.builder()
					.viewId(ViewId.random(DocTextLinesViewFactory.WINDOW_ID))
					.rows(rows)
					.documentRef(DocTextLineDocumentRef.ofOrderId(orderId))
					.build();

			final AtomicReference<Throwable> threadAFailure = new AtomicReference<>();
			final AtomicReference<Throwable> threadBFailure = new AtomicReference<>();

			// thread A: reaches the repository call first and is held there by the latch
			final Thread threadA = new Thread(() -> {
				try
				{
					new WEBUI_DocTextLines_InsertAbove().insertAbove(view, referenceRowId);
				}
				catch (final Throwable t)
				{
					threadAFailure.set(t);
				}
			});
			threadA.start();

			assertThat(aReachedPersist.await(5, TimeUnit.SECONDS))
					.as("thread A must reach its repository call")
					.isTrue();

			// thread B: a second insert-above of the SAME reference row, started while A is still mid-flight
			final Thread threadB = new Thread(() -> {
				try
				{
					new WEBUI_DocTextLines_InsertAbove().insertAbove(view, referenceRowId);
				}
				catch (final Throwable t)
				{
					threadBFailure.set(t);
				}
			});
			threadB.start();

			// give thread B a real, bounded chance to reach the same repository call while A still holds it --
			// true only for a not-yet-atomic implementation; false is the expected (fixed) outcome, where B
			// blocks trying to enter the same critical section A is inside
			final boolean bRacedAheadOfA = waitUntilBlockedOrTerminated(threadB, 500);

			releaseA.countDown();
			threadA.join(5_000);
			assertThat(threadA.isAlive()).as("thread A finished").isFalse();
			assertThat(threadAFailure.get()).isNull();

			threadB.join(5_000);
			assertThat(threadB.isAlive()).as("thread B finished").isFalse();
			assertThat(threadBFailure.get()).isNull();

			if (bRacedAheadOfA)
			{
				// documents that the implementation under test is NOT atomic -- kept so a regression shows up
				// as a clear assertion message rather than a hang
				System.out.println("WARNING: thread B was not blocked by thread A -- insert-above is not atomic");
			}

			final List<BigDecimal> textLinePositions = docTextLineRepository.getByDocument(DocTextLineDocumentRef.ofOrderId(orderId))
					.stream()
					.map(DocTextLine::getLine)
					.collect(Collectors.toList());
			assertThat(textLinePositions).hasSize(2);
			assertThat(textLinePositions.get(0))
					.as("the two concurrently-inserted text lines must not land on the same position")
					.isNotEqualByComparingTo(textLinePositions.get(1));
		}

		/**
		 * The trap named in the design: {@link DocTextLinesRows#deleteRow} clears {@link DocTextLinesRows#rowLocksById}
		 * for the deleted row. A {@link DocTextLinesRows#patchRow} of that SAME row, in flight concurrently, must
		 * never be able to finish its edit and resurrect the row into {@code rowsById} after {@code deleteRow} has
		 * already removed it -- which is exactly why {@code deleteRow}'s second critical section shares
		 * {@code patchRow}'s own per-row monitor.
		 */
		@Test
		void deleteRacingPatchRow_doesNotResurrectTheDeletedRow() throws InterruptedException
		{
			final I_C_Doc_TextLine textLine = createTextLine(5, TextLineScope.Document);
			final DocumentId rowId = DocTextLinesRow.textRowId(DocTextLineId.ofRepoId(textLine.getC_Doc_TextLine_ID()));

			final CountDownLatch deleteReachedPersist = new CountDownLatch(1);
			final CountDownLatch releaseDelete = new CountDownLatch(1);

			final DocTextLineRepository racingRepository = Mockito.spy(docTextLineRepository);
			Mockito.doAnswer(invocation -> {
						deleteReachedPersist.countDown();
						if (!releaseDelete.await(5, TimeUnit.SECONDS))
						{
							throw new IllegalStateException("test bug: releaseDelete was never signalled");
						}
						return invocation.callRealMethod();
					})
					.when(racingRepository)
					.deleteById(any());

			final DocTextLinesRows rows = DocTextLinesRowsLoader.builder()
					.orderDAO(orderDAO)
					.docTextLineRepository(racingRepository)
					.productsLookup(MockedLookupDataSource.withNamePrefix("product"))
					.orderId(orderId)
					.build()
					.load();
			final DocTextLinesView view = DocTextLinesView.builder()
					.viewId(ViewId.random(DocTextLinesViewFactory.WINDOW_ID))
					.rows(rows)
					.documentRef(DocTextLineDocumentRef.ofOrderId(orderId))
					.build();

			final AtomicReference<Throwable> deleteFailure = new AtomicReference<>();
			final Thread deleteThread = new Thread(() -> {
				try
				{
					new WEBUI_DocTextLines_Delete().delete(view, rowId);
				}
				catch (final Throwable t)
				{
					deleteFailure.set(t);
				}
			});
			deleteThread.start();

			assertThat(deleteReachedPersist.await(5, TimeUnit.SECONDS))
					.as("delete must reach its repository call")
					.isTrue();

			// a patch of the SAME row, started while delete is (deliberately held) mid-flight inside the row's
			// own monitor -- give it a real, bounded chance to reach and block on that same monitor before
			// releasing delete
			final AtomicReference<Throwable> patchFailure = new AtomicReference<>();
			final Thread patchThread = new Thread(() -> {
				try
				{
					final RowEditingContext ctx = RowEditingContext.builder()
							.viewId(view.getViewId())
							.rowId(rowId)
							.documentsCollection(mock(DocumentCollection.class))
							.userRolePermissions(mock(IUserRolePermissions.class))
							.build();
					view.patchViewRow(ctx, ImmutableList.of(JSONDocumentChangedEvent.replace(DocTextLinesRow.FIELD_TextLine, "raced edit")));
				}
				catch (final Throwable t)
				{
					patchFailure.set(t);
				}
			});
			patchThread.start();
			waitUntilBlockedOrTerminated(patchThread, 500);

			releaseDelete.countDown();
			deleteThread.join(5_000);
			assertThat(deleteThread.isAlive()).as("delete thread finished").isFalse();
			assertThat(deleteFailure.get()).isNull();

			patchThread.join(5_000);
			assertThat(patchThread.isAlive()).as("patch thread finished").isFalse();

			// whichever way the two threads actually interleaved, the row must end up deleted -- never
			// resurrected by a patch that slipped its rowsById.put in after deleteRow had already removed it
			assertThat(docTextLineRepository.getByDocument(DocTextLineDocumentRef.ofOrderId(orderId))).isEmpty();
		}

		/**
		 * The other trap named in the design: two concurrent moves must not corrupt the merged ordering. Two
		 * independent, disjoint swaps (T1<->T2 and T3<->T4) fired at the same time must both land correctly --
		 * {@link DocTextLinesRows#moveRow} runs its whole read-neighbour/persist/mutate sequence under the same
		 * {@link DocTextLinesRows#structuralLock} {@link DocTextLinesRows#insertRowAbove} uses, so the two calls
		 * are fully serialized regardless of scheduling.
		 */
		@Test
		void concurrentMovesOnDisjointPairs_doNotCorruptTheOrdering() throws InterruptedException
		{
			createArticleLine(10);
			final I_C_Doc_TextLine t1 = createTextLine(15, TextLineScope.Document);
			createArticleLine(20);
			final I_C_Doc_TextLine t2 = createTextLine(25, TextLineScope.Following);
			createArticleLine(30);
			final I_C_Doc_TextLine t3 = createTextLine(35, TextLineScope.Document);
			createArticleLine(40);
			final I_C_Doc_TextLine t4 = createTextLine(45, TextLineScope.Following);
			createArticleLine(50);

			final DocTextLinesView view = loadView();
			final DocumentId t1RowId = DocTextLinesRow.textRowId(DocTextLineId.ofRepoId(t1.getC_Doc_TextLine_ID()));
			final DocumentId t3RowId = DocTextLinesRow.textRowId(DocTextLineId.ofRepoId(t3.getC_Doc_TextLine_ID()));

			final AtomicReference<Throwable> failureA = new AtomicReference<>();
			final AtomicReference<Throwable> failureB = new AtomicReference<>();
			final Thread threadA = new Thread(() -> {
				try
				{
					new WEBUI_DocTextLines_MoveDown().moveDown(view, t1RowId);
				}
				catch (final Throwable t)
				{
					failureA.set(t);
				}
			});
			final Thread threadB = new Thread(() -> {
				try
				{
					new WEBUI_DocTextLines_MoveDown().moveDown(view, t3RowId);
				}
				catch (final Throwable t)
				{
					failureB.set(t);
				}
			});

			threadA.start();
			threadB.start();
			threadA.join(5_000);
			threadB.join(5_000);

			assertThat(threadA.isAlive()).isFalse();
			assertThat(threadB.isAlive()).isFalse();
			assertThat(failureA.get()).isNull();
			assertThat(failureB.get()).isNull();

			final List<DocTextLinesRow> textRows = rowsOf(view).stream().filter(DocTextLinesRow::isTextLine).collect(Collectors.toList());
			assertThat(textRows).extracting(DocTextLinesRow::getId).containsExactlyInAnyOrder(
					DocTextLinesRow.textRowId(DocTextLineId.ofRepoId(t1.getC_Doc_TextLine_ID())),
					DocTextLinesRow.textRowId(DocTextLineId.ofRepoId(t2.getC_Doc_TextLine_ID())),
					DocTextLinesRow.textRowId(DocTextLineId.ofRepoId(t3.getC_Doc_TextLine_ID())),
					DocTextLinesRow.textRowId(DocTextLineId.ofRepoId(t4.getC_Doc_TextLine_ID())));
			// no duplicate positions -- the same failure class already fixed once for concurrent inserts
			final List<BigDecimal> positions = textRows.stream().map(DocTextLinesRow::getLine).collect(Collectors.toList());
			assertThat(positions).doesNotHaveDuplicates();

			assertThat(view.getById(t1RowId).getLine()).isEqualByComparingTo("25");
			assertThat(view.getById(t3RowId).getLine()).isEqualByComparingTo("45");
		}

		/**
		 * Polls (bounded) until {@code thread} is either blocked/waiting on a monitor or has already
		 * terminated -- used to give a genuinely concurrent thread B a real chance to contend for the same lock
		 * thread A holds, without a fixed sleep racing the JVM's own scheduling.
		 */
		private boolean waitUntilBlockedOrTerminated(final Thread thread, final long timeoutMillis) throws InterruptedException
		{
			final long deadline = System.currentTimeMillis() + timeoutMillis;
			while (System.currentTimeMillis() < deadline)
			{
				final Thread.State state = thread.getState();
				if (state == Thread.State.BLOCKED || state == Thread.State.WAITING || state == Thread.State.TIMED_WAITING)
				{
					return false; // genuinely contending for a lock -- did not race ahead
				}
				if (state == Thread.State.TERMINATED)
				{
					return true; // ran to completion without ever blocking -- raced ahead
				}
				Thread.sleep(10);
			}
			return true; // never observed blocked within the window -- treat as raced ahead
		}
	}
}
