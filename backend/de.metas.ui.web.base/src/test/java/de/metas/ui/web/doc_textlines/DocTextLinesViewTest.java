package de.metas.ui.web.doc_textlines;

import com.google.common.collect.ImmutableList;
import de.metas.business.BusinessTestHelper;
import de.metas.currency.CurrencyCode;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.money.CurrencyId;
import de.metas.doctextline.DocTextLineDocumentRef;
import de.metas.doctextline.DocTextLineId;
import de.metas.doctextline.DocTextLineRepository;
import de.metas.doctextline.TextLineScope;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.security.IUserRolePermissions;
import de.metas.ui.web.shipment_candidates_editor.MockedLookupDataSource;
import de.metas.ui.web.view.IEditableView;
import de.metas.ui.web.view.IEditableView.RowEditingContext;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.LookupValuesList;
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
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

/**
 * A view built for an order with two article lines and one text line between them returns three rows in
 * merged order, with the article rows non-editable. Also pins the merged-ordering edge cases: a tie between
 * an article line and a text line at the exact same position, a text line before the first article line, and
 * a document with text lines and no article lines at all.
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
				.documentRef(DocTextLineDocumentRef.ofOrderId(orderId))
				.textLineScopeLookup(MockedTextLineScopeLookup.instance())
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
	 * Patching the text and the scope of a text row persists both, and patching an article row is rejected.
	 * Patching goes through the real production entry point -- {@code AbstractCustomView#patchViewRow}, the
	 * same method {@code ViewRowEditRestController} calls -- so a wrong implementation of the row-patch path
	 * (e.g. one that silently no-ops, or one that lets article rows through) is caught the same way it would
	 * be in production.
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

			// message must name the article-row rejection specifically -- a generic "view is not editable"
			// wording would also match an unrelated failure mode and give a false pass here. Asserted on the
			// AD_Message key, which is what the untranslated message renders to outside a database.
			assertThatThrownBy(() -> patch(view, rowId, JSONDocumentChangedEvent.replace(DocTextLinesRow.FIELD_TextLine, "hack")))
					.isInstanceOf(AdempiereException.class)
					.hasMessageContaining("DocTextLines_ArticleLineCannotBeEditedHere");

			// the article line's own DB row is untouched
			final I_C_OrderLine reloaded = load(article.getC_OrderLine_ID(), I_C_OrderLine.class);
			assertThat(reloaded.getLine()).isEqualTo(10);
		}

		@Test
		void multiLineTextWithBlankLine_roundTripsVerbatim()
		{
			final I_C_Doc_TextLine textLine = createTextLine("5", TextLineScope.Document, "original text");

			final DocTextLinesView view = loadView();
			final DocumentId rowId = DocTextLinesRow.textRowId(DocTextLineId.ofRepoId(textLine.getC_Doc_TextLine_ID()));

			final String multiLineText = "first line\n\nthird line after a blank one";
			patch(view, rowId, JSONDocumentChangedEvent.replace(DocTextLinesRow.FIELD_TextLine, multiLineText));

			final DocTextLinesRow persistedRow = rowsOf(loadView()).get(0);
			assertThat(persistedRow.getTextLine()).isEqualTo(multiLineText);
		}

		@Test
		void repositoryFailure_doesNotCorruptInMemoryRow()
		{
			final I_C_Doc_TextLine textLine = createTextLine("5", TextLineScope.Following, "original text");

			final DocTextLineRepository failingRepository = Mockito.spy(docTextLineRepository);
			Mockito.doThrow(new RuntimeException("simulated DB failure"))
					.when(failingRepository)
					.updateTextAndScope(any(), any(), any());

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
					.textLineScopeLookup(MockedTextLineScopeLookup.instance())
					.build();

			final DocumentId rowId = DocTextLinesRow.textRowId(DocTextLineId.ofRepoId(textLine.getC_Doc_TextLine_ID()));

			assertThatThrownBy(() -> patch(view, rowId, JSONDocumentChangedEvent.replace(DocTextLinesRow.FIELD_TextLine, "attempted update")))
					.isInstanceOf(RuntimeException.class)
					.hasMessage("simulated DB failure");

			// the in-memory row must still read the OLD value -- the DB write never happened, so nothing may
			// have gotten ahead of it, including the copy the same failed request's own error response reads
			final DocTextLinesRow inMemoryRow = view.getById(rowId);
			assertThat(inMemoryRow.getTextLine()).isEqualTo("original text");
		}

		/**
		 * Two field-level auto-save PATCHes of the SAME row, arriving close together (a real sequence: the user
		 * types the text, then changes the scope on the same row), must not interleave such that one edit is
		 * lost. Forces the interleaving deterministically with two independently-controlled latch pairs around
		 * a repository spy -- one per thread's repository call, identified by arrival order (the second call can
		 * only ever be thread B's, since thread A's call is still blocked when thread B is started) -- rather
		 * than exercising the two patches sequentially, which would not touch the race at all.
		 * <p>
		 * The orchestration tolerates both a correctly-serialising implementation (thread B cannot reach its
		 * repository call until thread A releases the row -- the 500ms poll below finds nothing, and the test
		 * then waits again, longer, after releasing A) and a non-serialising one (thread B reaches its
		 * repository call immediately, using the still-stale row) -- either way, the row must end up correct.
		 */
		@Test
		void concurrentPatchesOfTheSameRow_neitherEditIsLost() throws InterruptedException
		{
			final I_C_Doc_TextLine textLine = createTextLine("5", TextLineScope.Following, "original text");

			final AtomicInteger callIndex = new AtomicInteger(0);
			final CountDownLatch aReachedRepository = new CountDownLatch(1);
			final CountDownLatch releaseA = new CountDownLatch(1);
			final CountDownLatch bReachedRepository = new CountDownLatch(1);
			final CountDownLatch releaseB = new CountDownLatch(1);

			final DocTextLineRepository racingRepository = Mockito.spy(docTextLineRepository);
			Mockito.doAnswer(invocation -> {
						final int index = callIndex.getAndIncrement();
						if (index == 0)
						{
							aReachedRepository.countDown();
							if (!releaseA.await(5, TimeUnit.SECONDS))
							{
								throw new IllegalStateException("test bug: releaseA was never signalled");
							}
						}
						else if (index == 1)
						{
							bReachedRepository.countDown();
							if (!releaseB.await(5, TimeUnit.SECONDS))
							{
								throw new IllegalStateException("test bug: releaseB was never signalled");
							}
						}
						return invocation.callRealMethod();
					})
					.when(racingRepository)
					.updateTextAndScope(any(), any(), any());

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
					.textLineScopeLookup(MockedTextLineScopeLookup.instance())
					.build();

			final DocumentId rowId = DocTextLinesRow.textRowId(DocTextLineId.ofRepoId(textLine.getC_Doc_TextLine_ID()));

			final AtomicReference<Throwable> threadAFailure = new AtomicReference<>();
			final AtomicReference<Throwable> threadBFailure = new AtomicReference<>();

			// thread A: patches the text
			final Thread threadA = new Thread(() -> {
				try
				{
					patch(view, rowId, JSONDocumentChangedEvent.replace(DocTextLinesRow.FIELD_TextLine, "text from A"));
				}
				catch (final Throwable t)
				{
					threadAFailure.set(t);
				}
			});
			threadA.start();

			assertThat(aReachedRepository.await(5, TimeUnit.SECONDS))
					.as("thread A must reach its repository call")
					.isTrue();

			// thread B: patches the scope of the SAME row while A is still mid-flight, blocked on its own
			// repository call
			final Thread threadB = new Thread(() -> {
				try
				{
					patch(view, rowId, JSONDocumentChangedEvent.replace(DocTextLinesRow.FIELD_TextLineScope, TextLineScope.Document.getCode()));
				}
				catch (final Throwable t)
				{
					threadBFailure.set(t);
				}
			});
			threadB.start();

			// give thread B a real, bounded chance to race ahead of thread A -- true only for an implementation
			// with no per-row mutual exclusion; false is equally a valid (and expected, for the fix) outcome
			final boolean bRacedAheadOfA = bReachedRepository.await(500, TimeUnit.MILLISECONDS);

			releaseA.countDown();
			threadA.join(5_000);
			assertThat(threadA.isAlive()).as("thread A finished").isFalse();
			assertThat(threadAFailure.get()).isNull();

			if (!bRacedAheadOfA)
			{
				// thread B was genuinely blocked -- now that thread A released the row, thread B must be able
				// to proceed
				assertThat(bReachedRepository.await(5, TimeUnit.SECONDS))
						.as("thread B must reach its repository call once thread A released the row")
						.isTrue();
			}

			releaseB.countDown();
			threadB.join(5_000);
			assertThat(threadB.isAlive()).as("thread B finished").isFalse();
			assertThat(threadBFailure.get()).isNull();

			// both edits must be present -- if the two patches interleaved without per-row atomicity, whichever
			// one read the row first overwrites the other's change with a stale copy of the field it never
			// touched itself
			final DocTextLinesRow inMemoryRow = view.getById(rowId);
			assertThat(inMemoryRow.getTextLine()).isEqualTo("text from A");
			assertThat(inMemoryRow.getTextLineScope()).isEqualTo(TextLineScope.Document);

			final DocTextLinesRow persistedRow = rowsOf(loadView()).get(0);
			assertThat(persistedRow.getTextLine()).isEqualTo("text from A");
			assertThat(persistedRow.getTextLineScope()).isEqualTo(TextLineScope.Document);
		}
	}

	/**
	 * The scope column is rendered with {@code editor = ViewEditorRenderMode.ALWAYS}, so the frontend asks the
	 * view for that list's values as soon as the user opens the cell's dropdown -- {@code List/List.js} issues
	 * a {@code .../edit/textLineScope/dropdown} GET, which lands on {@link DocTextLinesView#getFieldDropdown}.
	 * A view that inherits {@link IEditableView}'s default there answers every such request with
	 * {@link UnsupportedOperationException} (HTTP 500), which makes the column un-editable in practice.
	 * <p>
	 * The dropdown's actual contents -- the real {@code TextLineScope} reference list -- are pinned by the
	 * browser spec {@code text-lines-modal.spec.js}, which opens the cell and picks a value for real; a plain
	 * unit test has no database and therefore no reference list to read. What is pinned here is what that spec
	 * cannot reach: that the field is answered from the view's own scope lookup at all, and that an unknown
	 * field name is refused rather than silently answered with the scope list.
	 */
	@Nested
	class fieldDropdown
	{
		private RowEditingContext editingContext(final DocTextLinesView view, final DocumentId rowId)
		{
			return RowEditingContext.builder()
					.viewId(view.getViewId())
					.rowId(rowId)
					.documentsCollection(mock(DocumentCollection.class))
					.userRolePermissions(mock(IUserRolePermissions.class))
					.build();
		}

		@Test
		void textLineScope_isAnsweredFromTheScopeLookup()
		{
			final I_C_Doc_TextLine textLine = createTextLine("10", TextLineScope.Following, "some text");

			final DocTextLinesView view = loadView();
			final DocumentId rowId = DocTextLinesRow.textRowId(DocTextLineId.ofRepoId(textLine.getC_Doc_TextLine_ID()));

			final LookupValuesList dropdown = view.getFieldDropdown(
					editingContext(view, rowId),
					DocTextLinesRow.FIELD_TextLineScope);

			assertThat(dropdown.getKeysAsString())
					.containsExactlyInAnyOrder(TextLineScope.Following.getCode(), TextLineScope.Document.getCode());
		}

		@Test
		void unknownFieldName_isRejected()
		{
			final I_C_Doc_TextLine textLine = createTextLine("10", TextLineScope.Following, "some text");

			final DocTextLinesView view = loadView();
			final DocumentId rowId = DocTextLinesRow.textRowId(DocTextLineId.ofRepoId(textLine.getC_Doc_TextLine_ID()));
			final RowEditingContext ctx = editingContext(view, rowId);

			// the plain text column is a LongText widget -- it has no dropdown at all, so asking for one is a
			// programming error, not an empty list
			assertThatThrownBy(() -> view.getFieldDropdown(ctx, DocTextLinesRow.FIELD_TextLine))
					.isInstanceOf(AdempiereException.class)
					.hasMessageContaining(DocTextLinesRow.FIELD_TextLine);
		}
	}
}
