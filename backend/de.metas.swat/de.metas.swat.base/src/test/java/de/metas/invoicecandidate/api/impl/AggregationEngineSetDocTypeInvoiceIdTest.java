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
import org.adempiere.model.InterfaceWrapperHelper;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link AggregationEngine#setDocTypeInvoiceId(InvoiceHeaderImpl)}.
 * The method is package-private ({@code @VisibleForTesting}) so this test sits in the same package.
 * <p>
 * Iter-3 tri-state behaviour: {@code C_DocType.IsPartialInvoice} is nullable (Y / N / NULL).
 * When the resolved doctype's flag is NULL (NA) or already matches the caller's intent, no swap
 * is needed. When a swap is needed but no sibling doctype exists, the engine keeps the current
 * doctype and logs a warning — the invoice's {@code IsPartialInvoice} value is set directly via
 * {@code PlainInvoicingParams} by the {@code C_Invoice} interceptor's "skip if already set" branch.
 */
@ExtendWith(AdempiereTestWatcher.class)
class AggregationEngineSetDocTypeInvoiceIdTest
{
	private static final DocTypeId DOC_TYPE_ID_NORMAL = DocTypeId.ofRepoId(10);
	private static final DocTypeId DOC_TYPE_ID_PARTIAL = DocTypeId.ofRepoId(20);
	private static final DocTypeId DOC_TYPE_ID_NA = DocTypeId.ofRepoId(30);

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
	// Case (b): partialInvoice=true, resolved doctype already isPartialInvoice='Y' → no swap
	// -----------------------------------------------------------------------

	@Test
	void setDocTypeInvoiceId_whenAlreadyMatchesPartialInvoice_doesNotSwap()
	{
		// given
		final I_C_DocType partialDocType = buildDocType("Y");
		when(docTypeBLMock.getById(DOC_TYPE_ID_PARTIAL)).thenReturn(partialDocType);

		final AggregationEngine engine = buildEngine(true /* partialInvoice */);
		final InvoiceHeaderImpl header = buildHeader(DOC_TYPE_ID_PARTIAL);

		// when
		engine.setDocTypeInvoiceId(header);

		// then — original ID preserved, no alternative lookup
		assertThat(header.getDocTypeInvoiceId()).contains(DOC_TYPE_ID_PARTIAL);
		verify(docTypeBLMock, never()).getDocTypeIdOrNull(any(DocTypeQuery.class));
	}

	// -----------------------------------------------------------------------
	// Case (c): partialInvoice=true, resolved doctype isPartialInvoice='N' → swaps to alternative
	// -----------------------------------------------------------------------

	@Test
	void setDocTypeInvoiceId_whenMismatch_swapsToAlternative()
	{
		// given
		final I_C_DocType normalDocType = buildDocType("N");
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
	// Case (d): partialInvoice=true, resolved doctype isPartialInvoice='N', no alternative found
	// → does NOT throw; keeps the current doctype (iter-3 tri-state — invoice's IsPartialInvoice
	//   gets set explicitly by the caller via the C_Invoice interceptor)
	// -----------------------------------------------------------------------

	@Test
	void setDocTypeInvoiceId_whenMismatchAndNoAlternative_keepsCurrentDocType()
	{
		// given
		final I_C_DocType normalDocType = buildDocType("N");
		when(docTypeBLMock.getById(DOC_TYPE_ID_NORMAL)).thenReturn(normalDocType);
		when(docTypeBLMock.getDocTypeIdOrNull(any(DocTypeQuery.class))).thenReturn(null);

		final AggregationEngine engine = buildEngine(true /* partialInvoice */);
		final InvoiceHeaderImpl header = buildHeader(DOC_TYPE_ID_NORMAL);

		// when
		engine.setDocTypeInvoiceId(header);

		// then — original doctype preserved (no throw)
		assertThat(header.getDocTypeInvoiceId()).contains(DOC_TYPE_ID_NORMAL);
	}

	// -----------------------------------------------------------------------
	// Case (e): partialInvoice=true, resolved doctype IsPartialInvoice=NULL (NA) → no swap
	//           (NA means doctype is neutral; invoice's IsPartialInvoice is set directly)
	// -----------------------------------------------------------------------

	@Test
	void setDocTypeInvoiceId_whenDocTypeIsNA_doesNotSwap()
	{
		// given — IsPartialInvoice=null on the doctype (tri-state NA)
		final I_C_DocType naDocType = buildDocType(null);
		when(docTypeBLMock.getById(DOC_TYPE_ID_NA)).thenReturn(naDocType);

		final AggregationEngine engine = buildEngine(true /* partialInvoice */);
		final InvoiceHeaderImpl header = buildHeader(DOC_TYPE_ID_NA);

		// when
		engine.setDocTypeInvoiceId(header);

		// then — no swap attempted; original doctype preserved
		assertThat(header.getDocTypeInvoiceId()).contains(DOC_TYPE_ID_NA);
		verify(docTypeBLMock, never()).getDocTypeIdOrNull(any(DocTypeQuery.class));
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
	 * Builds a {@link I_C_DocType} POJO with the given raw {@code IsPartialInvoice} code
	 * ({@code "Y"}, {@code "N"}, or {@code null} for NA). Uses {@link InterfaceWrapperHelper}
	 * so {@code InterfaceWrapperHelper.getValue(...)} works (production code reads via the
	 * tri-state path, not the boolean accessor).
	 */
	private I_C_DocType buildDocType(@Nullable final String isPartialInvoiceCode)
	{
		final I_C_DocType docType = InterfaceWrapperHelper.newInstance(I_C_DocType.class);
		InterfaceWrapperHelper.setValue(docType, I_C_DocType.COLUMNNAME_IsPartialInvoice, isPartialInvoiceCode);
		docType.setDocBaseType("ARI"); // CustomerInvoice — any valid code
		docType.setAD_Org_ID(0);
		docType.setIsSOTrx(false);
		docType.setName("Test DocType " + (isPartialInvoiceCode == null ? "NA" : isPartialInvoiceCode));
		InterfaceWrapperHelper.save(docType);
		return docType;
	}
}
