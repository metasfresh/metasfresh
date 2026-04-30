package de.metas.invoicecandidate.api.impl;

import de.metas.currency.CurrencyRepository;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeBL;
import de.metas.document.invoicingpool.DocTypeInvoicingPoolService;
import de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidateRecordService;
import de.metas.money.MoneyService;
import de.metas.order.impl.OrderEmailPropagationSysConfigRepository;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_DocType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.annotation.Nullable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link AggregationEngine#setDocTypeInvoiceId(InvoiceHeaderImpl)}.
 * The method is package-private ({@code @VisibleForTesting}) so this test sits in the same package.
 */
@ExtendWith(AdempiereTestWatcher.class)
class AggregationEngineSetDocTypeInvoiceIdTest
{
	private static final DocTypeId DOC_TYPE_ID_NORMAL = DocTypeId.ofRepoId(10);
	private static final DocTypeId DOC_TYPE_ID_PARTIAL = DocTypeId.ofRepoId(20);

	private IDocTypeBL docTypeBLMock;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
		SpringContextHolder.registerJUnitBean(DocTypeInvoicingPoolService.newInstanceForUnitTesting());
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		SpringContextHolder.registerJUnitBean(new OrderEmailPropagationSysConfigRepository(sysConfigBL));
		SpringContextHolder.registerJUnitBean(new InvoiceCandidateRecordService());
		SpringContextHolder.registerJUnitBean(new MoneyService(new CurrencyRepository()));

		docTypeBLMock = mock(IDocTypeBL.class);
		Services.registerService(IDocTypeBL.class, docTypeBLMock);
	}

	// -----------------------------------------------------------------------
	// Case (a): partialInvoice=null → no-op
	// -----------------------------------------------------------------------

	@Test
	void setDocTypeInvoiceId_whenPartialInvoiceNull_doesNotSwap()
	{
		// given — no docTypeBL stubs needed: partialInvoice=null skips the swap block entirely
		final AggregationEngine engine = buildEngine(null /* partialInvoice */);
		final InvoiceHeaderImpl header = buildHeader(DOC_TYPE_ID_NORMAL);

		// when
		engine.setDocTypeInvoiceId(header);

		// then
		assertThat(header.getDocTypeInvoiceId()).contains(DOC_TYPE_ID_NORMAL);
	}

	// -----------------------------------------------------------------------
	// Case (b): partialInvoice=true, resolved doctype already isPartialInvoice=true → no swap
	// -----------------------------------------------------------------------

	@Test
	void setDocTypeInvoiceId_whenAlreadyMatchesPartialInvoice_doesNotSwap()
	{
		// given
		final I_C_DocType partialDocType = mockDocType(true);
		when(docTypeBLMock.getById(DOC_TYPE_ID_PARTIAL)).thenReturn(partialDocType);

		final AggregationEngine engine = buildEngine(true /* partialInvoice */);
		final InvoiceHeaderImpl header = buildHeader(DOC_TYPE_ID_PARTIAL);

		// when
		engine.setDocTypeInvoiceId(header);

		// then — original ID preserved, no alternative lookup
		assertThat(header.getDocTypeInvoiceId()).contains(DOC_TYPE_ID_PARTIAL);
	}

	// -----------------------------------------------------------------------
	// Case (c): partialInvoice=true, resolved doctype isPartialInvoice=false → swaps to alternative
	// -----------------------------------------------------------------------

	@Test
	void setDocTypeInvoiceId_whenMismatch_swapsToAlternative()
	{
		// given
		final I_C_DocType normalDocType = mockDocType(false);
		when(docTypeBLMock.getById(DOC_TYPE_ID_NORMAL)).thenReturn(normalDocType);
		when(docTypeBLMock.getDocTypeIdOrNull(any(DocTypeQuery.class))).thenReturn(DOC_TYPE_ID_PARTIAL);

		final AggregationEngine engine = buildEngine(true /* partialInvoice */);
		final InvoiceHeaderImpl header = buildHeader(DOC_TYPE_ID_NORMAL);

		// when
		engine.setDocTypeInvoiceId(header);

		// then — swapped to alternative
		assertThat(header.getDocTypeInvoiceId()).contains(DOC_TYPE_ID_PARTIAL);
	}

	// -----------------------------------------------------------------------
	// Case (d): partialInvoice=true, no alternative found → throws AdempiereException
	// -----------------------------------------------------------------------

	@Test
	void setDocTypeInvoiceId_whenMismatchAndNoAlternative_throwsException()
	{
		// given
		final I_C_DocType normalDocType = mockDocType(false);
		when(docTypeBLMock.getById(DOC_TYPE_ID_NORMAL)).thenReturn(normalDocType);
		when(docTypeBLMock.getDocTypeIdOrNull(any(DocTypeQuery.class))).thenReturn(null);

		final AggregationEngine engine = buildEngine(true /* partialInvoice */);
		final InvoiceHeaderImpl header = buildHeader(DOC_TYPE_ID_NORMAL);

		// when / then
		assertThatThrownBy(() -> engine.setDocTypeInvoiceId(header))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("isPartialInvoice=true");
	}

	// -----------------------------------------------------------------------
	// Helpers
	// -----------------------------------------------------------------------

	private AggregationEngine buildEngine(@Nullable final Boolean partialInvoice)
	{
		return AggregationEngine.newInstanceForUnitTesting()
				.partialInvoice(partialInvoice)
				.build();
	}

	/**
	 * Builds an {@link InvoiceHeaderImpl} with the given docTypeId pre-set (branch-1 of resolution logic).
	 */
	private InvoiceHeaderImpl buildHeader(@Nullable final DocTypeId docTypeId)
	{
		final InvoiceHeaderImpl header = new InvoiceHeaderImpl();
		header.setDocTypeInvoiceId(docTypeId);
		header.setIsSOTrx(false);
		header.setIsTakeDocTypeFromPool(false);
		return header;
	}

	/**
	 * Creates a minimal {@link I_C_DocType} mock with the given {@code isPartialInvoice} value.
	 */
	private I_C_DocType mockDocType(final boolean isPartialInvoice)
	{
		final I_C_DocType docType = mock(I_C_DocType.class);
		when(docType.isPartialInvoice()).thenReturn(isPartialInvoice);
		when(docType.getDocBaseType()).thenReturn("ARI"); // CustomerInvoice — any valid code
		when(docType.getAD_Client_ID()).thenReturn(1);
		when(docType.getAD_Org_ID()).thenReturn(0);
		when(docType.isSOTrx()).thenReturn(false);
		when(docType.getName()).thenReturn("Test DocType");
		return docType;
	}
}
