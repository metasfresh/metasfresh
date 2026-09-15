package de.metas.ui.web.doc_textlines;

import de.metas.business.BusinessTestHelper;
import de.metas.currency.CurrencyCode;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.money.CurrencyId;
import de.metas.doctextline.DocTextLineRepository;
import de.metas.doctextline.TextLineScope;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.ui.web.shipment_candidates_editor.MockedLookupDataSource;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.descriptor.ViewEditorRenderMode;
import de.metas.util.Services;
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

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

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
			createTextLine("5", TextLineScope.Document, "text");

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
		}
	}
}
