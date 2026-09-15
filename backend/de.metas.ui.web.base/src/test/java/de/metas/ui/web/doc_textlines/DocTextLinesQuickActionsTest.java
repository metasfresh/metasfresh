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
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.security.IUserRolePermissions;
import de.metas.ui.web.doc_textlines.process.WEBUI_DocTextLines_InsertAbove;
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
