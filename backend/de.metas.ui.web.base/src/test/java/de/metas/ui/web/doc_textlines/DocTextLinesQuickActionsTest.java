package de.metas.ui.web.doc_textlines;

import com.google.common.collect.ImmutableList;
import de.metas.business.BusinessTestHelper;
import de.metas.currency.CurrencyCode;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.doctextline.DocTextLineDocumentRef;
import de.metas.doctextline.DocTextLineRepository;
import de.metas.doctextline.TextLineScope;
import de.metas.money.CurrencyId;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
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

		// WEBUI_DocTextLines_InsertAbove extends ViewBasedProcessTemplate, whose field initializers do eager
		// SpringContextHolder.instance.getBean() lookups -- register exactly those two bean types, same pattern
		// as WEBUI_Order_DocTextLines_LauncherTest.
		SpringContextHolder.registerJUnitBean(IViewsRepository.class, mock(IViewsRepository.class));
		SpringContextHolder.registerJUnitBean(DocTextLineRepository.class, docTextLineRepository);
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
}
