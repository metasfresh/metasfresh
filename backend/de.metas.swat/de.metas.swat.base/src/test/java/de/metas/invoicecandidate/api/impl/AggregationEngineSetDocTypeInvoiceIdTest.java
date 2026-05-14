package de.metas.invoicecandidate.api.impl;

import de.metas.currency.CurrencyRepository;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeBL;
import de.metas.document.IDocTypeDAO;
import de.metas.document.impl.DocTypeBL;
import de.metas.document.impl.DocTypeDAO;
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
	private static final String DOC_BASE_TYPE_INVOICE = "ARI"; // CustomerInvoice — any valid code

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
		SpringContextHolder.registerJUnitBean(DocTypeInvoicingPoolService.newInstanceForUnitTesting());
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		SpringContextHolder.registerJUnitBean(new OrderEmailPropagationSysConfigRepository(sysConfigBL));
		SpringContextHolder.registerJUnitBean(new InvoiceCandidateRecordService());
		SpringContextHolder.registerJUnitBean(new MoneyService(new CurrencyRepository()));

		// Register the real DocTypeDAO + DocTypeBL instead of mocking IDocTypeBL.
		// Each test creates the I_C_DocType records it needs via InterfaceWrapperHelper.newInstance + save;
		// the saved records get their own IDs, which the test then sets on the InvoiceHeaderImpl.
		Services.registerService(IDocTypeDAO.class, new DocTypeDAO());
		Services.registerService(IDocTypeBL.class, new DocTypeBL());
	}

	// -----------------------------------------------------------------------
	// Case (a): partialInvoice=null → no-op
	// -----------------------------------------------------------------------

	@Test
	void setDocTypeInvoiceId_whenPartialInvoiceNull_doesNotSwap()
	{
		// given — partialInvoice=null skips the swap block entirely; docType lookup is not exercised
		final DocTypeId normalDocTypeId = createDocType("N");

		final AggregationEngine engine = buildEngine(null /* partialInvoice */);
		final InvoiceHeaderImpl header = buildHeader(normalDocTypeId);

		// when
		engine.setDocTypeInvoiceId(header);

		// then
		assertThat(header.getDocTypeInvoiceId()).contains(normalDocTypeId);
		// no caller intent → header.isPartialInvoice stays null
		assertThat(header.getIsPartialInvoice()).isNull();
	}

	// -----------------------------------------------------------------------
	// Case (b): partialInvoice=true, resolved doctype already isPartialInvoice='Y' → no swap
	// -----------------------------------------------------------------------

	@Test
	void setDocTypeInvoiceId_whenAlreadyMatchesPartialInvoice_doesNotSwap()
	{
		// given — doctype already has IsPartialInvoice='Y', matches caller's intent
		final DocTypeId partialDocTypeId = createDocType("Y");

		final AggregationEngine engine = buildEngine(true /* partialInvoice */);
		final InvoiceHeaderImpl header = buildHeader(partialDocTypeId);

		// when
		engine.setDocTypeInvoiceId(header);

		// then — original ID preserved, no alternative lookup needed
		assertThat(header.getDocTypeInvoiceId()).contains(partialDocTypeId);
		// caller's intent (true) propagated to header for direct C_Invoice propagation
		assertThat(header.getIsPartialInvoice()).isTrue();
	}

	// -----------------------------------------------------------------------
	// Case (c): partialInvoice=true, resolved doctype isPartialInvoice='N' → swaps to alternative
	// -----------------------------------------------------------------------

	@Test
	void setDocTypeInvoiceId_whenMismatch_swapsToAlternative()
	{
		// given — sibling pair: normal ('N') currently on the header, partial ('Y') discoverable via query
		final DocTypeId normalDocTypeId = createDocType("N");
		final DocTypeId partialDocTypeId = createDocType("Y");

		final AggregationEngine engine = buildEngine(true /* partialInvoice */);
		final InvoiceHeaderImpl header = buildHeader(normalDocTypeId);

		// when
		engine.setDocTypeInvoiceId(header);

		// then — swapped to alternative
		assertThat(header.getDocTypeInvoiceId()).contains(partialDocTypeId);
		// caller's intent (true) propagated to header
		assertThat(header.getIsPartialInvoice()).isTrue();
	}

	// -----------------------------------------------------------------------
	// Case (d): partialInvoice=true, resolved doctype isPartialInvoice='N', no alternative found
	// → does NOT throw; keeps the current doctype (iter-3 tri-state — invoice's IsPartialInvoice
	//   gets set explicitly by the caller via the C_Invoice interceptor)
	// -----------------------------------------------------------------------

	@Test
	void setDocTypeInvoiceId_whenMismatchAndNoAlternative_keepsCurrentDocType()
	{
		// given — only the normal ('N') doctype exists; no partial sibling
		final DocTypeId normalDocTypeId = createDocType("N");

		final AggregationEngine engine = buildEngine(true /* partialInvoice */);
		final InvoiceHeaderImpl header = buildHeader(normalDocTypeId);

		// when
		engine.setDocTypeInvoiceId(header);

		// then — original doctype preserved (no throw)
		assertThat(header.getDocTypeInvoiceId()).contains(normalDocTypeId);
		// caller's intent (true) still propagated to header — this is the direct path that
		// makes the doctype-swap optional (the C_Invoice gets IsPartialInvoice='Y' regardless
		// of whether a sibling doctype exists). See me03 #29369.
		assertThat(header.getIsPartialInvoice()).isTrue();
	}

	// -----------------------------------------------------------------------
	// Case (e): partialInvoice=true, resolved doctype IsPartialInvoice=NULL (NA) → no swap
	//           (NA means doctype is neutral; invoice's IsPartialInvoice is set directly)
	// -----------------------------------------------------------------------

	@Test
	void setDocTypeInvoiceId_whenDocTypeIsNA_doesNotSwap()
	{
		// given — IsPartialInvoice=null on the doctype (tri-state NA)
		final DocTypeId naDocTypeId = createDocType(null);

		final AggregationEngine engine = buildEngine(true /* partialInvoice */);
		final InvoiceHeaderImpl header = buildHeader(naDocTypeId);

		// when
		engine.setDocTypeInvoiceId(header);

		// then — no swap attempted; original doctype preserved
		assertThat(header.getDocTypeInvoiceId()).contains(naDocTypeId);
		// caller's intent (true) still propagated to header
		assertThat(header.getIsPartialInvoice()).isTrue();
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
	 * Creates and persists a real {@link I_C_DocType} record with the given raw {@code IsPartialInvoice}
	 * code ({@code "Y"}, {@code "N"}, or {@code null} for NA), and returns the saved record's
	 * {@link DocTypeId}. Each call yields a distinct ID assigned at save time.
	 * <p>
	 * Stores {@code IsPartialInvoice} as a {@link Boolean} (or null) via {@link InterfaceWrapperHelper#setValue}
	 * to match how the production PO layer materialises {@code YesNo} columns. The
	 * {@code IsPartialInvoice.fromValue(...)} type-safe factory used by the engine handles both
	 * {@code Boolean} and {@code String} representations, and the {@link de.metas.document.DocTypeQuery}
	 * sibling-doctype lookup compares as {@code Boolean} — so this representation is needed for the
	 * in-memory query path to find the alternative doctype.
	 */
	private DocTypeId createDocType(@Nullable final String isPartialInvoiceCode)
	{
		final I_C_DocType docType = InterfaceWrapperHelper.newInstance(I_C_DocType.class);
		final Boolean isPartialInvoiceValue = isPartialInvoiceCode == null ? null : "Y".equals(isPartialInvoiceCode);
		InterfaceWrapperHelper.setValue(docType, I_C_DocType.COLUMNNAME_IsPartialInvoice, isPartialInvoiceValue);
		docType.setDocBaseType(DOC_BASE_TYPE_INVOICE);
		docType.setAD_Org_ID(0);
		docType.setIsSOTrx(false);
		docType.setName("Test DocType " + (isPartialInvoiceCode == null ? "NA" : isPartialInvoiceCode));
		InterfaceWrapperHelper.save(docType);
		return DocTypeId.ofRepoId(docType.getC_DocType_ID());
	}
}
