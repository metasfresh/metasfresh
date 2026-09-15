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

		/** The requirement is: deleting changes no article line and no other text line -- both halves asserted, not just the row count. */
		@Test
		void doesNotAffectSurvivingArticleLinesOrOtherTextRows()
		{
			final I_C_OrderLine article10 = createArticleLine(10);
			final I_C_Doc_TextLine toDelete = createTextLine(15, TextLineScope.Document);
			final I_C_Doc_TextLine toKeep = createTextLine(25, TextLineScope.Following);

			final DocTextLinesView view = loadView();
			final DocumentId rowIdToDelete = DocTextLinesRow.textRowId(DocTextLineId.ofRepoId(toDelete.getC_Doc_TextLine_ID()));
			final DocumentId article10RowId = DocTextLinesRow.articleRowId(OrderLineId.ofRepoId(article10.getC_OrderLine_ID()));
			final DocumentId toKeepRowId = DocTextLinesRow.textRowId(DocTextLineId.ofRepoId(toKeep.getC_Doc_TextLine_ID()));

			new WEBUI_DocTextLines_Delete().delete(view, rowIdToDelete);

			final List<DocTextLinesRow> rows = rowsOf(loadView());
			assertThat(rows).extracting(DocTextLinesRow::getId).containsExactly(article10RowId, toKeepRowId);
			assertThat(view.getById(article10RowId).getLine()).as("a surviving article line's own position is never touched by a delete").isEqualByComparingTo("10");
			assertThat(view.getById(toKeepRowId).getLine()).isEqualByComparingTo("25");
			assertThat(view.getById(toKeepRowId).getTextLineScope()).isEqualTo(TextLineScope.Following);
		}

		/**
		 * {@link DocTextLinesRows#deleteRow} must not un-publish the row before the database delete has actually
		 * succeeded: when the persist fails, the row must stay fully intact -- still in the merged view, still
		 * retrievable, still in the database.
		 */
		@Test
		void whenThePersistFails_theRowStaysFullyIntact()
		{
			final I_C_Doc_TextLine textLine = createTextLine(5, TextLineScope.Document);
			final DocumentId rowId = DocTextLinesRow.textRowId(DocTextLineId.ofRepoId(textLine.getC_Doc_TextLine_ID()));

			final DocTextLineRepository failingRepository = Mockito.spy(docTextLineRepository);
			Mockito.doThrow(new org.adempiere.exceptions.AdempiereException("simulated persist failure"))
					.when(failingRepository)
					.deleteById(any());

			final DocTextLinesRows rows = DocTextLinesRowsLoader.builder()
					.orderDAO(orderDAO)
					.docTextLineRepository(failingRepository)
					.productsLookup(MockedLookupDataSource.withNamePrefix("product"))
					.orderId(orderId)
					.build()
					.load();
			final DocTextLinesView view = DocTextLinesView.builder()
					.viewId(ViewId.random(DocTextLinesViewFactory.WINDOW_ID))
					.rows(rows)
					.documentRef(DocTextLineDocumentRef.ofOrderId(orderId))
					.build();

			assertThatThrownBy(() -> new WEBUI_DocTextLines_Delete().delete(view, rowId))
					.isInstanceOf(org.adempiere.exceptions.AdempiereException.class);

			// still visible in the merged list -- the failed persist must not have un-published it
			assertThat(rowsOf(view)).extracting(DocTextLinesRow::getId).containsExactly(rowId);
			assertThat(view.getById(rowId).getLine()).isEqualByComparingTo("5");
			// still in the database too -- the delete never actually persisted
			assertThat(docTextLineRepository.getByDocument(DocTextLineDocumentRef.ofOrderId(orderId)))
					.extracting(DocTextLine::getId)
					.containsExactly(DocTextLineId.ofRepoId(textLine.getC_Doc_TextLine_ID()));
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
		 * Two article lines at ADJACENT integer positions (10 and 11) -- the tightest case, since there is no
		 * integer gap between them to fall back on -- with a text line inserted between them. Moving the text
		 * line up must place it strictly before article 10, and moving it back down must return it to exactly
		 * where it was, while both articles keep exactly 10 and 11 throughout.
		 */
		@Test
		void moveUp_thenMoveDown_pastAnArticleRow_roundTripsWithoutTouchingEitherArticle()
		{
			final I_C_OrderLine article10 = createArticleLine(10);
			final I_C_OrderLine article11 = createArticleLine(11);
			final DocumentId article11RowId = DocTextLinesRow.articleRowId(OrderLineId.ofRepoId(article11.getC_OrderLine_ID()));
			new WEBUI_DocTextLines_InsertAbove().insertAbove(loadView(), article11RowId);

			final DocTextLinesView view = loadView();
			final List<DocTextLinesRow> initial = rowsOf(view);
			assertThat(initial).hasSize(3);
			final DocTextLinesRow textRow = initial.get(1);
			assertThat(textRow.isTextLine()).isTrue();
			assertThat(textRow.getLine()).isEqualByComparingTo("10.5");
			final DocumentId textRowId = textRow.getId();
			final DocumentId article10RowId = DocTextLinesRow.articleRowId(OrderLineId.ofRepoId(article10.getC_OrderLine_ID()));

			new WEBUI_DocTextLines_MoveUp().moveUp(view, textRowId);

			final List<DocTextLinesRow> afterUp = rowsOf(view);
			assertThat(afterUp).extracting(DocTextLinesRow::getId).containsExactly(textRowId, article10RowId, article11RowId);
			assertThat(view.getById(textRowId).getLine()).isLessThan(BigDecimal.TEN);
			assertThat(view.getById(article10RowId).getLine()).as("article 10's own position is never touched by the move").isEqualByComparingTo("10");
			assertThat(view.getById(article11RowId).getLine()).as("article 11's own position is never touched by the move").isEqualByComparingTo("11");

			new WEBUI_DocTextLines_MoveDown().moveDown(view, textRowId);

			final List<DocTextLinesRow> afterDown = rowsOf(view);
			assertThat(afterDown).extracting(DocTextLinesRow::getId).containsExactly(article10RowId, textRowId, article11RowId);
			assertThat(view.getById(textRowId).getLine()).isEqualByComparingTo("10.5");
			assertThat(view.getById(article10RowId).getLine()).isEqualByComparingTo("10");
			assertThat(view.getById(article11RowId).getLine()).isEqualByComparingTo("11");
		}

		/**
		 * Moving a text line past a neighbouring article must never recompute the text line's stored scope --
		 * the scope stays exactly what the user set, even though the naive default derived from the row's NEW
		 * position would differ (an article precedes the new position too, which the derivation rule would read
		 * as {@code Following}).
		 */
		@Test
		void moveUp_pastAnArticleRow_doesNotChangeStoredScope()
		{
			createArticleLine(10);
			final I_C_Doc_TextLine text = createTextLine(15, TextLineScope.Document);
			createArticleLine(20);

			final DocTextLinesView view = loadView();
			final DocumentId textRowId = DocTextLinesRow.textRowId(DocTextLineId.ofRepoId(text.getC_Doc_TextLine_ID()));

			new WEBUI_DocTextLines_MoveUp().moveUp(view, textRowId);

			assertThat(view.getById(textRowId).getTextLineScope())
					.as("moving must not recompute the stored scope from the new position")
					.isEqualTo(TextLineScope.Document);
			// persisted -- a fresh view reload sees the same scope, not just the in-memory one
			assertThat(loadView().getById(textRowId).getTextLineScope()).isEqualTo(TextLineScope.Document);
		}

		/**
		 * When the row immediately adjacent is itself a text row (no article between them), the exchange is a
		 * genuine two-way swap of both rows' stored positions -- the pre-existing, already-tested
		 * {@link de.metas.doctextline.DocTextLineRepository#swapPositions} path.
		 */
		@Test
		void move_whereTheNeighborIsAnotherTextRow_exchangesBothStoredPositions()
		{
			final I_C_Doc_TextLine t1 = createTextLine(14, TextLineScope.Document);
			final I_C_Doc_TextLine t2 = createTextLine(15, TextLineScope.Following);
			final I_C_OrderLine article20 = createArticleLine(20);

			final DocTextLinesView view = loadView();
			final DocumentId t1RowId = DocTextLinesRow.textRowId(DocTextLineId.ofRepoId(t1.getC_Doc_TextLine_ID()));
			final DocumentId t2RowId = DocTextLinesRow.textRowId(DocTextLineId.ofRepoId(t2.getC_Doc_TextLine_ID()));
			final DocumentId article20RowId = DocTextLinesRow.articleRowId(OrderLineId.ofRepoId(article20.getC_OrderLine_ID()));

			new WEBUI_DocTextLines_MoveDown().moveDown(view, t1RowId);

			assertThat(rowsOf(view)).extracting(DocTextLinesRow::getId).containsExactly(t2RowId, t1RowId, article20RowId);
			assertThat(view.getById(t1RowId).getLine()).isEqualByComparingTo("15");
			assertThat(view.getById(t1RowId).getTextLineScope()).isEqualTo(TextLineScope.Document);
			assertThat(view.getById(t2RowId).getLine()).isEqualByComparingTo("14");
			assertThat(view.getById(t2RowId).getTextLineScope()).isEqualTo(TextLineScope.Following);
			assertThat(view.getById(article20RowId).getLine()).isEqualByComparingTo("20");
		}

		/** The selected row must be literally the first row of the WHOLE merged order -- an article preceding it is still a valid neighbour to exchange with, see the round-trip test above. */
		@Test
		void moveUp_onTheVeryFirstRowOfTheMergedOrder_throws()
		{
			final I_C_Doc_TextLine text = createTextLine(5, TextLineScope.Document);
			createArticleLine(10);

			final DocTextLinesView view = loadView();
			final DocumentId textRowId = DocTextLinesRow.textRowId(DocTextLineId.ofRepoId(text.getC_Doc_TextLine_ID()));

			assertThatThrownBy(() -> new WEBUI_DocTextLines_MoveUp().moveUp(view, textRowId))
					.isInstanceOf(org.adempiere.exceptions.AdempiereException.class);
		}

		/** The selected row must be literally the last row of the WHOLE merged order. */
		@Test
		void moveDown_onTheVeryLastRowOfTheMergedOrder_throws()
		{
			createArticleLine(10);
			final I_C_Doc_TextLine text = createTextLine(15, TextLineScope.Document);

			final DocTextLinesView view = loadView();
			final DocumentId textRowId = DocTextLinesRow.textRowId(DocTextLineId.ofRepoId(text.getC_Doc_TextLine_ID()));

			assertThatThrownBy(() -> new WEBUI_DocTextLines_MoveDown().moveDown(view, textRowId))
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
		void moveUp_rejectsWhenSelectedRowIsFirstInTheMergedOrder()
		{
			final I_C_Doc_TextLine t1 = createTextLine(5, TextLineScope.Document);
			createArticleLine(10);
			final DocTextLinesView view = loadView();
			final DocumentId rowId = DocTextLinesRow.textRowId(DocTextLineId.ofRepoId(t1.getC_Doc_TextLine_ID()));

			final ProcessPreconditionsResolution resolution =
					WEBUI_DocTextLines_MoveUp.checkMoveUpPreconditions(view, DocumentIdsSelection.fromNullable(rowId));

			assertThat(resolution.isAccepted()).isFalse();
		}

		@Test
		void moveDown_rejectsWhenSelectedRowIsLastInTheMergedOrder()
		{
			createArticleLine(10);
			final I_C_Doc_TextLine t1 = createTextLine(15, TextLineScope.Document);
			final DocTextLinesView view = loadView();
			final DocumentId rowId = DocTextLinesRow.textRowId(DocTextLineId.ofRepoId(t1.getC_Doc_TextLine_ID()));

			final ProcessPreconditionsResolution resolution =
					WEBUI_DocTextLines_MoveDown.checkMoveDownPreconditions(view, DocumentIdsSelection.fromNullable(rowId));

			assertThat(resolution.isAccepted()).isFalse();
		}

		/** An article row immediately following the selection is still a valid neighbour to exchange with. */
		@Test
		void moveDown_acceptsWhenAnArticleRowFollowsImmediately()
		{
			createArticleLine(10);
			final I_C_Doc_TextLine t1 = createTextLine(15, TextLineScope.Document);
			createArticleLine(20);
			final DocTextLinesView view = loadView();
			final DocumentId rowId = DocTextLinesRow.textRowId(DocTextLineId.ofRepoId(t1.getC_Doc_TextLine_ID()));

			final ProcessPreconditionsResolution resolution =
					WEBUI_DocTextLines_MoveDown.checkMoveDownPreconditions(view, DocumentIdsSelection.fromNullable(rowId));

			assertThat(resolution.isAccepted()).isTrue();
		}

		/**
		 * A precondition check must resolve to a rejection, never throw -- unlike {@code moveRow}'s own use of
		 * the throwing index lookup, a row that is simply no longer in the merged order (selected, then removed
		 * by a concurrent delete before the check runs) must make {@code hasNeighbor} return {@code false}, not
		 * propagate a server error.
		 */
		@Test
		void hasNeighbor_returnsFalse_forARowNotInTheMergedOrder()
		{
			createArticleLine(10);
			final DocTextLinesView view = loadView();
			final DocumentId vanishedRowId = DocTextLinesRow.textRowId(DocTextLineId.ofRepoId(999999));

			assertThat(view.hasNeighbor(vanishedRowId, true)).isFalse();
			assertThat(view.hasNeighbor(vanishedRowId, false)).isFalse();
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
		 * already removed it -- which is exactly why {@code deleteRow}'s first critical section (persist, then
		 * clear {@code rowsById}/{@code rowLocksById}, all before releasing the lock) shares {@code patchRow}'s
		 * own per-row monitor.
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

			// deleteRow clears rowsById/rowLocksById in the SAME critical section as the persist, before
			// releasing the row's monitor -- so a patch that was blocked on that monitor is only ever admitted
			// once the row is already gone, and its own lookup deterministically throws (either from
			// getRowLockOrThrow, if rowLocksById was cleared before the patch even reached the lock, or from
			// getRowOrThrow inside patchRow, if it reached the lock a moment earlier) -- never a successful
			// rowsById.put that would resurrect the row
			assertThat(patchFailure.get()).isInstanceOf(EntityNotFoundException.class);

			// whichever way the two threads actually interleaved, the row must end up deleted -- never
			// resurrected by a patch that slipped its rowsById.put in after deleteRow had already removed it
			assertThat(docTextLineRepository.getByDocument(DocTextLineDocumentRef.ofOrderId(orderId))).isEmpty();
		}

		/**
		 * The other trap named in the design: two concurrent moves must not corrupt the merged ordering. Two
		 * independent, disjoint two-way swaps (T1<->T2 and T3<->T4, each pair with no article between them)
		 * fired at the same time must both land correctly -- {@link DocTextLinesRows#moveRow} runs its whole
		 * read-neighbour/persist/mutate sequence under the same {@link DocTextLinesRows#structuralLock}
		 * {@link DocTextLinesRows#insertRowAbove} uses, so the two calls are fully serialized regardless of
		 * scheduling.
		 */
		@Test
		void concurrentMovesOnDisjointTextRowPairs_doNotCorruptTheOrdering() throws InterruptedException
		{
			final I_C_Doc_TextLine t1 = createTextLine(14, TextLineScope.Document);
			final I_C_Doc_TextLine t2 = createTextLine(15, TextLineScope.Following);
			createArticleLine(20);
			final I_C_Doc_TextLine t3 = createTextLine(54, TextLineScope.Document);
			final I_C_Doc_TextLine t4 = createTextLine(55, TextLineScope.Following);
			createArticleLine(60);

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

			// each pair swapped -- deterministic regardless of scheduling, the two pairs share no row
			assertThat(view.getById(t1RowId).getLine()).isEqualByComparingTo("15");
			assertThat(view.getById(t3RowId).getLine()).isEqualByComparingTo("55");
		}

		/**
		 * Same guarantee, exercised on the OTHER persistence shape {@link DocTextLinesRows#moveRow} can take: a
		 * reposition past a neighbouring article row, computed via
		 * {@link de.metas.doctextline.DocTextLineRepository#computePositionBetween}. Two independent, disjoint
		 * repositions (each with no bound on the far side, so each result is deterministic on its own) fired at
		 * the same time must both land correctly and produce no duplicate position.
		 */
		@Test
		void concurrentMovesRepositioningPastDisjointArticleRows_doNotCorruptTheOrdering() throws InterruptedException
		{
			createArticleLine(5);
			final I_C_Doc_TextLine t1 = createTextLine(7, TextLineScope.Document);
			createArticleLine(10);
			createArticleLine(50);
			final I_C_Doc_TextLine t2 = createTextLine(57, TextLineScope.Following);
			createArticleLine(60);

			final DocTextLinesView view = loadView();
			final DocumentId t1RowId = DocTextLinesRow.textRowId(DocTextLineId.ofRepoId(t1.getC_Doc_TextLine_ID()));
			final DocumentId t2RowId = DocTextLinesRow.textRowId(DocTextLineId.ofRepoId(t2.getC_Doc_TextLine_ID()));

			final AtomicReference<Throwable> failureA = new AtomicReference<>();
			final AtomicReference<Throwable> failureB = new AtomicReference<>();
			final Thread threadA = new Thread(() -> {
				try
				{
					new WEBUI_DocTextLines_MoveUp().moveUp(view, t1RowId);
				}
				catch (final Throwable t)
				{
					failureA.set(t);
				}
			});
			final Thread threadB = new Thread(() -> {
				try
				{
					new WEBUI_DocTextLines_MoveUp().moveUp(view, t2RowId);
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

			// each repositioned strictly below its own article's original position -- no bound on the far side,
			// so deterministic regardless of scheduling; the two groups share no row
			assertThat(view.getById(t1RowId).getLine()).isLessThan(BigDecimal.valueOf(5));
			assertThat(view.getById(t2RowId).getLine()).isLessThan(BigDecimal.valueOf(50));

			final List<BigDecimal> positions = rowsOf(view).stream()
					.filter(DocTextLinesRow::isTextLine)
					.map(DocTextLinesRow::getLine)
					.collect(Collectors.toList());
			assertThat(positions).doesNotHaveDuplicates();
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
