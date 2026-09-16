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
import org.adempiere.exceptions.AdempiereException;
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
import org.mockito.InOrder;
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
import static org.assertj.core.api.Assertions.catchThrowable;
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

	private DocTextLinesRows loadRows()
	{
		return DocTextLinesRowsLoader.builder()
				.orderDAO(orderDAO)
				.docTextLineRepository(docTextLineRepository)
				.productsLookup(MockedLookupDataSource.withNamePrefix("product"))
				.orderId(orderId)
				.build()
				.load();
	}

	private DocTextLinesView viewOf(final DocTextLinesRows rows)
	{
		return DocTextLinesView.builder()
				.viewId(ViewId.random(DocTextLinesViewFactory.WINDOW_ID))
				.rows(rows)
				.documentRef(DocTextLineDocumentRef.ofOrderId(orderId))
				.build();
	}

	private DocTextLinesView loadView()
	{
		return viewOf(loadRows());
	}

	private static DocumentId textRowIdOf(final I_C_Doc_TextLine record)
	{
		return DocTextLinesRow.textRowId(DocTextLineId.ofRepoId(record.getC_Doc_TextLine_ID()));
	}

	private static DocumentId articleRowIdOf(final I_C_OrderLine orderLine)
	{
		return DocTextLinesRow.articleRowId(OrderLineId.ofRepoId(orderLine.getC_OrderLine_ID()));
	}

	/** The position a row actually has on disk right now -- read back through a freshly loaded view, so it is the stored value and not an in-memory copy. */
	private BigDecimal storedPositionOf(final DocumentId rowId)
	{
		return loadView().getById(rowId).getLine();
	}

	private static List<DocTextLinesRow> rowsOf(final DocTextLinesView view)
	{
		return view.streamByIds(DocumentIdsSelection.ALL).collect(Collectors.toList());
	}

	/** The merged order as it is actually stored right now -- read back through a freshly loaded view, so it is what a newly opened modal would show. */
	private List<DocumentId> storedMergedOrder()
	{
		return rowsOf(loadView()).stream().map(DocTextLinesRow::getId).collect(Collectors.toList());
	}

	private List<BigDecimal> storedTextLinePositions()
	{
		return docTextLineRepository.getByDocument(DocTextLineDocumentRef.ofOrderId(orderId))
				.stream()
				.map(DocTextLine::getLine)
				.collect(Collectors.toList());
	}

	/**
	 * Polls (bounded) until {@code thread} is either blocked/waiting on a monitor or has already
	 * terminated -- used to give a genuinely concurrent thread B a real chance to contend for the same lock
	 * thread A holds, without a fixed sleep racing the JVM's own scheduling.
	 */
	private static boolean waitUntilBlockedOrTerminated(final Thread thread, final long timeoutMillis) throws InterruptedException
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
	 * A delete runs as two sequential critical sections: the first persists the delete and drops the row from
	 * the in-memory row map, the second trims the merged order. Between them the row's id is still listed in
	 * the merged order with no row behind it -- momentary in production, but a state every reader of the
	 * merged order can observe.
	 * <p>
	 * These tests drive those two sections explicitly, one at a time, on the calling thread. The window is
	 * therefore entered on purpose and every assertion below is reached on every run, on any JVM: nothing here
	 * depends on which of two threads a monitor happens to admit first, which Java does not specify and which
	 * would let these tests quietly stop exercising the branch they exist for.
	 * <p>
	 * What the window means to a WRITE changed once writes started re-deriving the merged order from the
	 * database: the first section has already persisted the delete, so a write re-reading the document finds
	 * the row genuinely gone and computes against the rows that remain -- it no longer has to refuse, because
	 * there is nothing left to guess at. The window still matters to every reader that does NOT re-derive, and
	 * {@link DocTextLinesRows#boundPositionAt}'s refusal is still what protects those; the last test here is
	 * the one that pins it.
	 */
	@Nested
	class duringTheDeleteWindow
	{
		/**
		 * The position bound one step beyond the moved row's neighbour is the row currently being deleted.
		 * Guessing it as "no bound at all" would halve down from the neighbour rather than land between two
		 * bounds -- here straight onto 5, the position of the row before it: a duplicate the collision guard
		 * cannot catch, because it only compares the result against the two bounds it was handed.
		 * <p>
		 * The move neither guesses nor refuses: it re-reads the document, where the first delete section has
		 * already removed that row, and lands strictly between the two bounds that are actually there. The
		 * stored result is the same one this move used to produce only after the delete had finished --
		 * asserted below both before and after the second section runs, because the row is gone from the
		 * merged order by then either way.
		 */
		@Test
		void aMoveWhosePositionBoundIsBeingDeleted_landsBetweenTheBoundsTheDatabaseActuallyHas()
		{
			final I_C_Doc_TextLine rowZ = createTextLine(5, TextLineScope.Document);
			final I_C_Doc_TextLine rowA = createTextLine(7, TextLineScope.Document);
			final I_C_OrderLine articleC = createArticleLine(10);
			final I_C_Doc_TextLine rowB = createTextLine(15, TextLineScope.Document);

			final DocTextLinesRows rows = loadRows();
			final DocTextLinesView view = viewOf(rows);
			final DocumentId rowAId = textRowIdOf(rowA);
			final DocumentId rowBId = textRowIdOf(rowB);
			final DocumentId rowZId = textRowIdOf(rowZ);
			final DocumentId articleCId = articleRowIdOf(articleC);

			rows.deleteRowPersistAndUnpublish(rowAId);

			assertThat(rowsOf(view))
					.as("the deleted row is already out of the view, even though the merged order still lists its id")
					.extracting(DocTextLinesRow::getId)
					.doesNotContain(rowAId);
			assertThat(view.hasNeighbor(rowAId, true))
					.as("the window really is open: the merged order still lists the deleted row's id, so it still has a predecessor")
					.isTrue();

			new WEBUI_DocTextLines_MoveUp().moveUp(view, rowBId);

			assertThat(storedPositionOf(rowBId))
					.as("the move lands strictly between its two real bounds -- the row that used to sit between them is already deleted")
					.isStrictlyBetween(new BigDecimal("5"), new BigDecimal("10"));
			assertThat(storedPositionOf(rowZId)).isEqualByComparingTo("5");
			assertThat(storedPositionOf(articleCId)).isEqualByComparingTo("10");

			assertThat(view.hasNeighbor(rowAId, true))
					.as("the write re-read the document and dropped the id it no longer contains -- it is not merely skipped, it is gone from the order")
					.isFalse();
		}

		/**
		 * The same on the insert-above path: the row immediately before the reference row -- the lower bound of
		 * the midpoint arithmetic -- is the one being deleted. Guessed as "no previous row", the midpoint would
		 * be half of the reference position, landing exactly on the row before that. Re-read from the database
		 * instead, where that row is already gone, so the new row lands between the two bounds that remain.
		 */
		@Test
		void anInsertAboveWhosePreviousBoundIsBeingDeleted_landsBetweenTheBoundsTheDatabaseActuallyHas()
		{
			final I_C_Doc_TextLine rowZ = createTextLine(5, TextLineScope.Document);
			final I_C_Doc_TextLine rowA = createTextLine(7, TextLineScope.Document);
			final I_C_OrderLine referenceArticle = createArticleLine(10);

			final DocTextLinesRows rows = loadRows();
			final DocTextLinesView view = viewOf(rows);
			final DocumentId rowAId = textRowIdOf(rowA);
			final DocumentId rowZId = textRowIdOf(rowZ);
			final DocumentId referenceRowId = articleRowIdOf(referenceArticle);

			rows.deleteRowPersistAndUnpublish(rowAId);

			assertThat(view.hasNeighbor(rowAId, true))
					.as("the window really is open: the merged order still lists the deleted row's id")
					.isTrue();

			new WEBUI_DocTextLines_InsertAbove().insertAbove(view, referenceRowId);

			assertThat(docTextLineRepository.getByDocument(DocTextLineDocumentRef.ofOrderId(orderId)))
					.as("the deleted row is gone and exactly one new text line was persisted")
					.hasSize(2)
					.extracting(DocTextLine::getId)
					.contains(DocTextLineId.ofRepoId(rowZ.getC_Doc_TextLine_ID()));
			assertThat(view.hasNeighbor(rowAId, true))
					.as("the write re-read the document and dropped the id it no longer contains")
					.isFalse();

			final List<DocTextLinesRow> after = rowsOf(view);
			assertThat(after).extracting(DocTextLinesRow::getId).containsExactly(rowZId, after.get(1).getId(), referenceRowId);
			assertThat(after.get(1).isTextLine()).isTrue();
			assertThat(after.get(1).getLine())
					.as("the new row lands strictly between its two real bounds")
					.isStrictlyBetween(new BigDecimal("5"), new BigDecimal("10"));
		}

		/**
		 * The row the move is about to exchange with is itself being deleted, so it is no longer an exchange
		 * partner at all. Re-reading the document finds that out and exchanges with the row that is genuinely
		 * adjacent instead -- the same outcome this move used to produce only once the delete had finished.
		 */
		@Test
		void aMoveWhoseExchangePartnerIsBeingDeleted_exchangesWithTheRowThatIsActuallyAdjacent()
		{
			final I_C_Doc_TextLine rowX = createTextLine(5, TextLineScope.Document);
			final I_C_Doc_TextLine rowA = createTextLine(7, TextLineScope.Document);
			final I_C_Doc_TextLine rowB = createTextLine(10, TextLineScope.Document);

			final DocTextLinesRows rows = loadRows();
			final DocTextLinesView view = viewOf(rows);
			final DocumentId rowAId = textRowIdOf(rowA);
			final DocumentId rowBId = textRowIdOf(rowB);
			final DocumentId rowXId = textRowIdOf(rowX);

			rows.deleteRowPersistAndUnpublish(rowAId);

			assertThat(view.hasNeighbor(rowAId, true))
					.as("the window really is open: the merged order still lists the deleted row's id")
					.isTrue();

			new WEBUI_DocTextLines_MoveUp().moveUp(view, rowBId);

			assertThat(storedPositionOf(rowBId)).as("exchanged with the row that is actually above it now").isEqualByComparingTo("5");
			assertThat(storedPositionOf(rowXId)).isEqualByComparingTo("10");
			assertThat(view.hasNeighbor(rowAId, true))
					.as("the write re-read the document and dropped the id it no longer contains")
					.isFalse();
		}

		/**
		 * The other half of the distinction, and the reason it has to be a distinction rather than a blanket
		 * refusal: an index that is simply past the start of the merged order means there genuinely is no
		 * bound on that side, which is a perfectly computable situation and must keep working -- even while an
		 * unrelated row elsewhere is mid-delete.
		 */
		@Test
		void aMoveWithAGenuinelyAbsentBound_stillCompletes()
		{
			final I_C_OrderLine articleC = createArticleLine(10);
			final I_C_Doc_TextLine rowB = createTextLine(15, TextLineScope.Document);
			final I_C_Doc_TextLine rowA = createTextLine(20, TextLineScope.Document);

			final DocTextLinesRows rows = loadRows();
			final DocTextLinesView view = viewOf(rows);
			final DocumentId rowBId = textRowIdOf(rowB);
			final DocumentId articleCId = articleRowIdOf(articleC);

			rows.deleteRowPersistAndUnpublish(textRowIdOf(rowA));

			new WEBUI_DocTextLines_MoveUp().moveUp(view, rowBId);

			assertThat(storedPositionOf(rowBId))
					.as("nothing precedes the article, so the moved row simply lands before it")
					.isLessThan(new BigDecimal("10"));
			assertThat(storedPositionOf(articleCId)).isEqualByComparingTo("10");
		}

		/**
		 * The refusal that used to protect the writes still protects the readers, and this is where it now
		 * lives. {@link DocTextLinesView#getInsertAbovePositions} answers about the view's own merged order and
		 * does NOT re-read the document -- so it, unlike a write, really can be looking at an id with no row
		 * behind it, and really cannot tell an absent bound from an unreadable one. It refuses.
		 * <p>
		 * Without this test nothing would fail if that refusal were deleted, because every other caller of it
		 * re-derives first.
		 */
		@Test
		void aPositionQueryThatDoesNotReDerive_refusesABoundThatIsMidDelete()
		{
			createTextLine(5, TextLineScope.Document);
			final I_C_Doc_TextLine rowA = createTextLine(7, TextLineScope.Document);
			final I_C_OrderLine referenceArticle = createArticleLine(10);

			final DocTextLinesRows rows = loadRows();
			final DocTextLinesView view = viewOf(rows);

			rows.deleteRowPersistAndUnpublish(textRowIdOf(rowA));

			assertThatThrownBy(() -> view.getInsertAbovePositions(articleRowIdOf(referenceArticle)))
					.as("a bound that is listed but unreadable is unknowable, not absent -- refuse rather than answer about the wrong neighbour")
					.isInstanceOf(org.adempiere.exceptions.AdempiereException.class);
		}

		/**
		 * A row that is only scanned -- never used as a position bound -- is skipped rather than refused. Only
		 * text rows are ever deleted, and the scan behind {@code insertAbove}'s scope default asks whether an
		 * ARTICLE row precedes the insert position, a question no text row can answer either way. So a
		 * mid-delete row there changes nothing, and refusing over it would reject an operation that is in fact
		 * perfectly computable.
		 */
		@Test
		void anInsertAboveScanningPastARowBeingDeleted_stillCompletes()
		{
			createArticleLine(3);
			final I_C_Doc_TextLine rowA = createTextLine(5, TextLineScope.Document);
			createTextLine(7, TextLineScope.Following);
			final I_C_Doc_TextLine referenceText = createTextLine(10, TextLineScope.Following);

			final DocTextLinesRows rows = loadRows();
			final DocTextLinesView view = viewOf(rows);
			final DocumentId referenceRowId = textRowIdOf(referenceText);

			rows.deleteRowPersistAndUnpublish(textRowIdOf(rowA));

			new WEBUI_DocTextLines_InsertAbove().insertAbove(view, referenceRowId);

			final List<DocTextLinesRow> after = rowsOf(view);
			assertThat(after).hasSize(4);
			final DocTextLinesRow inserted = after.get(2);
			assertThat(inserted.isTextLine()).isTrue();
			assertThat(inserted.getLine()).isEqualByComparingTo("8.5");
			assertThat(inserted.getTextLineScope())
					.as("the article earlier in the scanned range is still found, the skipped row could never have contributed")
					.isEqualTo(TextLineScope.Following);
		}
	}

	/**
	 * Two concurrent insert-above requests against the SAME reference row, sharing ONE rows holder -- i.e. two
	 * requests into one open modal -- must not both read the same reference/previous positions and persist the
	 * same midpoint {@code Line}. The two-rows-holder case (two open modals over the same order, which is what
	 * a second browser tab actually is) is {@link acrossTwoOpenModalsOfTheSameOrder}.
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

	}

	/**
	 * TWO open modals over the same order -- one user with a second browser tab is enough. Each modal has its
	 * own rows holder, holding the merged order as it stood when that modal was opened, and nothing refreshes
	 * that snapshot while the modal stays open. So the second modal computes its next stored position against
	 * an order the first one has already changed underneath it.
	 * <p>
	 * No timing luck is involved and the first three of these tests use no threads at all: the two writes are
	 * strictly sequential, and the second one still computes against the state the first one replaced. A test
	 * that drives only one rows holder cannot fail on this -- which is why the single-holder tests in
	 * {@link concurrency} pass even when a second modal can persist a duplicate position. A duplicate matters
	 * because both report functions order by position and then by "text before article", which is no tiebreak
	 * at all between two text rows: an unrelated later edit can silently swap them in the printed document,
	 * and a text line scoped to the following lines then applies to different article lines than it did.
	 */
	@Nested
	class acrossTwoOpenModalsOfTheSameOrder
	{
		/**
		 * Both modals insert above the SAME article row, each halving that article's position because it is
		 * the document's first row -- the second modal cannot see the row the first one put there, so both
		 * arrive at the same midpoint.
		 */
		@Test
		void twoInsertsAboveTheSameArticleRow_doNotShareAPosition()
		{
			final I_C_OrderLine article10 = createArticleLine(10);
			final DocumentId referenceRowId = articleRowIdOf(article10);

			final DocTextLinesView firstModal = loadView();
			final DocTextLinesView secondModal = loadView();

			new WEBUI_DocTextLines_InsertAbove().insertAbove(firstModal, referenceRowId);
			new WEBUI_DocTextLines_InsertAbove().insertAbove(secondModal, referenceRowId);

			final List<BigDecimal> positions = storedTextLinePositions();
			assertThat(positions).hasSize(2);
			assertThat(positions.get(0))
					.as("the second modal must not persist the position the first one already took")
					.isNotEqualByComparingTo(positions.get(1));
			assertThat(positions).allSatisfy(position -> assertThat(position).isLessThan(BigDecimal.TEN));
		}

		/**
		 * The second interleaving: an insert-above and a move-up that land on the same midpoint. The first
		 * modal inserts between the two articles; the second modal moves its own text row up past the second
		 * article, and -- computed against a snapshot that does not contain the inserted row -- that
		 * reposition targets the very same midpoint.
		 */
		@Test
		void anInsertAboveAndAMoveUpPastAnArticle_doNotLandOnTheSameMidpoint()
		{
			final I_C_OrderLine article10 = createArticleLine(10);
			final I_C_OrderLine article20 = createArticleLine(20);
			final I_C_Doc_TextLine text25 = createTextLine(25, TextLineScope.Document);

			final DocTextLinesView firstModal = loadView();
			final DocTextLinesView secondModal = loadView();

			new WEBUI_DocTextLines_InsertAbove().insertAbove(firstModal, articleRowIdOf(article20));
			final DocumentId insertedRowId = rowsOf(firstModal).get(1).getId();

			new WEBUI_DocTextLines_MoveUp().moveUp(secondModal, textRowIdOf(text25));

			final List<BigDecimal> positions = storedTextLinePositions();
			assertThat(positions).hasSize(2);
			assertThat(positions.get(0))
					.as("the moved row must not land on the position the other modal's insert already took")
					.isNotEqualByComparingTo(positions.get(1));

			assertThat(storedMergedOrder())
					.as("the moved row ends up one place earlier in the order that actually exists, i.e. between the inserted row and the article it moved past")
					.containsExactly(
							articleRowIdOf(article10),
							insertedRowId,
							textRowIdOf(text25),
							articleRowIdOf(article20));
		}

		/**
		 * The swap shape of a move, which trades two stored positions and so cannot produce a tie by itself --
		 * but exchanges the wrong pair when the snapshot it picks the exchange partner from is stale, which
		 * scrambles the order just as badly. The first modal swaps the first two text rows; the second modal
		 * then moves the last row up, and must exchange it with the row that is above it NOW, not with the one
		 * that was above it when the modal was opened.
		 */
		@Test
		void aMoveUpSwappingWithATextNeighbour_exchangesWithTheCurrentNeighbourNotTheRememberedOne()
		{
			final I_C_Doc_TextLine text10 = createTextLine(10, TextLineScope.Document);
			final I_C_Doc_TextLine text20 = createTextLine(20, TextLineScope.Document);
			final I_C_Doc_TextLine text30 = createTextLine(30, TextLineScope.Document);

			final DocTextLinesView firstModal = loadView();
			final DocTextLinesView secondModal = loadView();

			// first modal: text10 and text20 trade places, so text20 is now the first row and text10 the second
			new WEBUI_DocTextLines_MoveDown().moveDown(firstModal, textRowIdOf(text10));
			// second modal: the row above text30 is text10 by now -- the remembered neighbour text20 is two
			// places away, and exchanging with it would move text30 up by two places instead of one
			new WEBUI_DocTextLines_MoveUp().moveUp(secondModal, textRowIdOf(text30));

			assertThat(storedMergedOrder()).containsExactly(
					textRowIdOf(text20),
					textRowIdOf(text30),
					textRowIdOf(text10));
			assertThat(storedTextLinePositions()).doesNotHaveDuplicates();
		}

		/**
		 * The same two modals, genuinely concurrent this time: re-deriving the order inside the write only
		 * removes the duplicate if the second write cannot re-derive while the first one is still between its
		 * own derivation and its own persist. Both modals insert above the same article; the first is held
		 * inside the repository call while the second starts, so the second must wait rather than compute
		 * against an order that is about to change.
		 */
		@Test
		void concurrentInsertsAboveTheSameArticleRow_fromTwoModals_areSerialised() throws InterruptedException
		{
			final I_C_OrderLine article10 = createArticleLine(10);
			final DocumentId referenceRowId = articleRowIdOf(article10);

			final AtomicInteger callIndex = new AtomicInteger(0);
			final CountDownLatch firstReachedPersist = new CountDownLatch(1);
			final CountDownLatch releaseFirst = new CountDownLatch(1);

			final DocTextLineRepository racingRepository = Mockito.spy(docTextLineRepository);
			Mockito.doAnswer(invocation -> {
						final int index = callIndex.getAndIncrement();
						if (index == 0)
						{
							firstReachedPersist.countDown();
							if (!releaseFirst.await(5, TimeUnit.SECONDS))
							{
								throw new IllegalStateException("test bug: releaseFirst was never signalled");
							}
						}
						return invocation.callRealMethod();
					})
					.when(racingRepository)
					.insertAbove(any());

			final DocTextLinesView firstModal = viewOf(loadRowsWith(racingRepository));
			final DocTextLinesView secondModal = viewOf(loadRowsWith(racingRepository));

			final AtomicReference<Throwable> firstFailure = new AtomicReference<>();
			final AtomicReference<Throwable> secondFailure = new AtomicReference<>();

			final Thread firstThread = new Thread(() -> {
				try
				{
					new WEBUI_DocTextLines_InsertAbove().insertAbove(firstModal, referenceRowId);
				}
				catch (final Throwable t)
				{
					firstFailure.set(t);
				}
			});
			firstThread.start();

			assertThat(firstReachedPersist.await(5, TimeUnit.SECONDS))
					.as("the first modal's insert must reach its repository call")
					.isTrue();

			final Thread secondThread = new Thread(() -> {
				try
				{
					new WEBUI_DocTextLines_InsertAbove().insertAbove(secondModal, referenceRowId);
				}
				catch (final Throwable t)
				{
					secondFailure.set(t);
				}
			});
			secondThread.start();

			// a real, bounded chance for the second modal to reach the same repository call while the first
			// one is still holding it -- with the two writes serialised on the order they share, it blocks
			// instead, and the value here is the expected false
			final boolean secondRacedAhead = waitUntilBlockedOrTerminated(secondThread, 500);

			releaseFirst.countDown();
			firstThread.join(5_000);
			secondThread.join(5_000);
			assertThat(firstThread.isAlive()).as("the first modal's insert finished").isFalse();
			assertThat(secondThread.isAlive()).as("the second modal's insert finished").isFalse();
			assertThat(firstFailure.get()).isNull();
			assertThat(secondFailure.get()).isNull();

			assertThat(secondRacedAhead)
					.as("two modals of the same order must not both be inside the compute-and-persist sequence at once")
					.isFalse();

			final List<BigDecimal> positions = storedTextLinePositions();
			assertThat(positions).hasSize(2);
			assertThat(positions.get(0)).isNotEqualByComparingTo(positions.get(1));
		}

		/**
		 * The row the user selected is gone from the database by the time they act on it -- another modal
		 * deleted it -- and the order has moved on around it.
		 * <p>
		 * Placing the new line relative to a row that no longer exists is not a near-miss: the remembered row
		 * carries the position it had, while the rows still around it carry the positions they have NOW, so
		 * the midpoint between them can land in an entirely different article gap. Here the user asks for a
		 * line above a remark that used to sit between the first and second article; computed against the
		 * vanished row's old position it lands AFTER the second article instead, and because the new line's
		 * scope is "the lines that follow", it then applies to a different run of articles than the one the
		 * user was pointing at. Nothing reports any of this.
		 * <p>
		 * The only honest answer is to refuse: the user's reference point is gone, and no position can stand
		 * in for it.
		 */
		@Test
		void anInsertAboveARowAnotherModalDeleted_isRefused_ratherThanPlacedAgainstTheVanishedRow()
		{
			final I_C_OrderLine firstArticle = createArticleLine(10);
			final I_C_Doc_TextLine movedText = createTextLine(12, TextLineScope.Following);
			final I_C_Doc_TextLine deletedText = createTextLine(15, TextLineScope.Following);
			final I_C_OrderLine secondArticle = createArticleLine(20);
			final I_C_OrderLine thirdArticle = createArticleLine(40);

			final DocTextLinesView staleModal = loadView();
			final DocTextLinesView currentModal = loadView();

			// the other modal removes the row the stale one is about to reference, and moves the row above it
			// past the second article -- so the vanished row's remembered position and its neighbour's real
			// position now sit on opposite sides of that article
			new WEBUI_DocTextLines_Delete().delete(currentModal, textRowIdOf(deletedText));
			new WEBUI_DocTextLines_MoveDown().moveDown(currentModal, textRowIdOf(movedText));
			assertThat(storedTextLinePositions())
					.as("fixture: the surviving text line now sits between the second and third article")
					.containsExactly(new BigDecimal("30.0000"));

			final Throwable refusal = catchThrowable(
					() -> new WEBUI_DocTextLines_InsertAbove().insertAbove(staleModal, textRowIdOf(deletedText)));

			assertThat(storedTextLinePositions())
					.as("no line may be stored against a row that no longer exists -- a position derived from it lands in the wrong article gap")
					.containsExactly(new BigDecimal("30.0000"));
			assertThat(refusal)
					.as("the user must be told their reference line is gone, not silently given a different place")
					.isInstanceOf(AdempiereException.class)
					.hasMessageContaining("select a line again");

			assertThat(storedMergedOrder())
					.as("the document is exactly as the other modal left it")
					.containsExactly(
							articleRowIdOf(firstArticle),
							articleRowIdOf(secondArticle),
							textRowIdOf(movedText),
							articleRowIdOf(thirdArticle));
		}

		/**
		 * The same situation on the move path: the row the user selected is gone, deleted in the other modal.
		 * The move cannot fall back on anything either -- "one place earlier" is meaningless for a row that is
		 * not in the order any more -- so it gets the same answer as the insert.
		 * <p>
		 * Without the guard the operation is still refused, but by the not-found path deeper in, whose message
		 * is an internal row id. That is why the assertion below is on the MESSAGE and not merely on the
		 * exception type: what this pins is that the user is told what happened, in the same words the insert
		 * path uses.
		 */
		@Test
		void aMoveOfARowAnotherModalDeleted_isRefused_inTheSameWordsAsTheInsertPath()
		{
			final I_C_OrderLine article = createArticleLine(10);
			final I_C_Doc_TextLine deletedText = createTextLine(12, TextLineScope.Following);
			final I_C_Doc_TextLine survivingText = createTextLine(15, TextLineScope.Following);

			final DocTextLinesView staleModal = loadView();
			final DocTextLinesView currentModal = loadView();

			new WEBUI_DocTextLines_Delete().delete(currentModal, textRowIdOf(deletedText));

			final Throwable refusal = catchThrowable(
					() -> new WEBUI_DocTextLines_MoveDown().moveDown(staleModal, textRowIdOf(deletedText)));

			assertThat(refusal)
					.as("the user must be told their line is gone, not handed an internal row id")
					.isInstanceOf(AdempiereException.class)
					.hasMessageContaining("select a line again");
			final List<BigDecimal> positionsAfterTheRefusal = storedTextLinePositions();
			assertThat(positionsAfterTheRefusal).as("the refused move changed no position").hasSize(1);
			assertThat(positionsAfterTheRefusal.get(0)).isEqualByComparingTo("15");
			assertThat(storedMergedOrder()).containsExactly(
					articleRowIdOf(article),
					textRowIdOf(survivingText));
		}

		/**
		 * The invariant the whole re-derivation rests on, and the reason two guards further down this class are
		 * unreachable: after a structural write, this modal's rows ARE the document's rows -- same set, same
		 * order -- however far behind it had fallen. Rows another modal added appear, rows it deleted are gone,
		 * and positions it changed are current.
		 * <p>
		 * Worth pinning on its own rather than leaving it implied by the placement tests: they would still pass
		 * if the refresh left a deleted row behind and merely avoided computing against it, and that leftover
		 * is exactly what the arithmetic used to trip over.
		 */
		@Test
		void afterAStructuralWrite_theStaleModalShowsExactlyTheDocumentsRows()
		{
			final I_C_OrderLine firstArticle = createArticleLine(10);
			final I_C_Doc_TextLine deletedText = createTextLine(12, TextLineScope.Following);
			final I_C_Doc_TextLine movedText = createTextLine(15, TextLineScope.Following);
			final I_C_OrderLine secondArticle = createArticleLine(20);

			final DocTextLinesView staleModal = loadView();
			final DocTextLinesView currentModal = loadView();

			new WEBUI_DocTextLines_Delete().delete(currentModal, textRowIdOf(deletedText));
			new WEBUI_DocTextLines_InsertAbove().insertAbove(currentModal, articleRowIdOf(secondArticle));
			final DocumentId insertedRowId = rowsOf(currentModal).get(2).getId();

			// the stale modal has seen none of that, and now writes
			new WEBUI_DocTextLines_MoveDown().moveDown(staleModal, textRowIdOf(movedText));

			assertThat(rowsOf(staleModal))
					.as("the modal lists exactly the document's rows, in the document's order")
					.extracting(DocTextLinesRow::getId)
					.containsExactlyElementsOf(storedMergedOrder());
			assertThat(storedMergedOrder()).containsExactly(
					articleRowIdOf(firstArticle),
					insertedRowId,
					textRowIdOf(movedText),
					articleRowIdOf(secondArticle));
		}

		/**
		 * The serialisation these tests rely on is a row lock on the order's own record, taken for the rest of
		 * the transaction the write runs in. That is what reaches a second modal being served by a SECOND
		 * application instance, which no in-process lock can see.
		 * <p>
		 * What this test can show is the half that lives in this code: every structural write asks the order's
		 * DAO to lock the document, and does so BEFORE it re-derives the merged order -- a lock taken after the
		 * read would leave exactly the window it exists to close. That the lock then makes a concurrent writer
		 * wait is PostgreSQL's contract for {@code FOR UPDATE}, and this harness has no database to
		 * demonstrate it against; it rests on reading, and is stated as such rather than mimicked with a mock.
		 */
		@Test
		void insertMoveAndDelete_eachLockTheOrderRecord()
		{
			final I_C_OrderLine article10 = createArticleLine(10);
			final I_C_Doc_TextLine text5 = createTextLine(5, TextLineScope.Document);

			final IOrderDAO lockRecordingOrderDAO = Mockito.spy(orderDAO);
			final DocTextLinesView view = viewOf(loadRowsWith(lockRecordingOrderDAO));

			new WEBUI_DocTextLines_InsertAbove().insertAbove(view, articleRowIdOf(article10));
			new WEBUI_DocTextLines_MoveDown().moveDown(view, textRowIdOf(text5));
			new WEBUI_DocTextLines_Delete().delete(view, textRowIdOf(text5));

			// insert, move and delete: each is a structural write, and a delete frees a position the others
			// may compute against, so none of the three may run unserialised
			Mockito.verify(lockRecordingOrderDAO, Mockito.times(3)).lockByIdForUpdate(orderId);
		}

		/**
		 * The other half, and the one with an ordering in it: the two writes that re-derive the merged order
		 * must hold the lock BEFORE they read. A lock taken after the read would leave exactly the window it
		 * exists to close -- another writer could commit in between, and the position would be computed
		 * against an order that no longer exists by the time it is stored.
		 */
		@Test
		void insertAndMove_lockTheOrderRecord_beforeTheyReadTheOrderLines()
		{
			final I_C_OrderLine article10 = createArticleLine(10);
			final I_C_Doc_TextLine text5 = createTextLine(5, TextLineScope.Document);

			final IOrderDAO lockRecordingOrderDAO = Mockito.spy(orderDAO);
			final DocTextLinesView view = viewOf(loadRowsWith(lockRecordingOrderDAO));

			new WEBUI_DocTextLines_InsertAbove().insertAbove(view, articleRowIdOf(article10));
			new WEBUI_DocTextLines_MoveDown().moveDown(view, textRowIdOf(text5));

			final InOrder lockThenRead = Mockito.inOrder(lockRecordingOrderDAO);
			lockThenRead.verify(lockRecordingOrderDAO).lockByIdForUpdate(orderId);
			lockThenRead.verify(lockRecordingOrderDAO).retrieveOrderLines(orderId);
			lockThenRead.verify(lockRecordingOrderDAO).lockByIdForUpdate(orderId);
			lockThenRead.verify(lockRecordingOrderDAO).retrieveOrderLines(orderId);
		}

		private DocTextLinesRows loadRowsWith(final DocTextLineRepository repository)
		{
			return loadRowsWith(orderDAO, repository);
		}

		private DocTextLinesRows loadRowsWith(final IOrderDAO orderDAO)
		{
			return loadRowsWith(orderDAO, docTextLineRepository);
		}

		private DocTextLinesRows loadRowsWith(final IOrderDAO orderDAO, final DocTextLineRepository repository)
		{
			return DocTextLinesRowsLoader.builder()
					.orderDAO(orderDAO)
					.docTextLineRepository(repository)
					.productsLookup(MockedLookupDataSource.withNamePrefix("product"))
					.orderId(orderId)
					.build()
					.load();
		}
	}
}
