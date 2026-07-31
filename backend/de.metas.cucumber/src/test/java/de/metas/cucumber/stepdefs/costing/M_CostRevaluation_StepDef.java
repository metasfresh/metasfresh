package de.metas.cucumber.stepdefs.costing;

import com.google.common.collect.ImmutableList;
import de.metas.acct.api.AcctSchemaId;
import de.metas.costing.CostDetail;
import de.metas.costing.CostDetailQuery;
import de.metas.costing.CostElementId;
import de.metas.costing.CostingDocumentRef;
import de.metas.costing.impl.CostDetailRepository;
import de.metas.costrevaluation.CostRevaluationId;
import de.metas.costrevaluation.CostRevaluationLine;
import de.metas.costrevaluation.CostRevaluationRepository;
import de.metas.costrevaluation.CostRevaluationService;
import de.metas.document.DocBaseType;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.acctschema.C_AcctSchema_StepDefData;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.money.MoneyService;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.SoftAssertions;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_CostDetail;
import org.compiere.model.I_M_CostRevaluation;
import org.compiere.util.Env;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Creates, seeds and completes {@link I_M_CostRevaluation} cost-revaluation documents, and validates the cost details
 * they wrote.
 */
@RequiredArgsConstructor
public class M_CostRevaluation_StepDef
{
	@NonNull private final CostRevaluationService costRevaluationService = SpringContextHolder.instance.getBean(CostRevaluationService.class);
	@NonNull private final CostRevaluationRepository costRevaluationRepository = SpringContextHolder.instance.getBean(CostRevaluationRepository.class);
	@NonNull private final CostDetailRepository costDetailRepository = SpringContextHolder.instance.getBean(CostDetailRepository.class);
	@NonNull private final MoneyService moneyService = SpringContextHolder.instance.getBean(MoneyService.class);
	@NonNull private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	@NonNull private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	@NonNull private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

	@NonNull private final M_CostRevaluation_StepDefData costRevaluationTable;
	@NonNull private final C_AcctSchema_StepDefData acctSchemaTable;
	@NonNull private final M_CostElement_StepDefData costElementTable;
	@NonNull private final M_Product_StepDefData productTable;

	/**
	 * Creates a drafted cost-revaluation header.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Identifier</b> — (required) alias for later reference<br>
	 *   <b>C_AcctSchema_ID</b> — (required, identifier-ref) accounting schema<br>
	 *   <b>M_CostElement_ID</b> — (required) target cost element (costing-method name, e.g. MovingAverageInvoice)<br>
	 *   <b>RevaluationSource</b> — (required) e.g. CopyFromCostElement<br>
	 *   <b>CopyFrom_M_CostElement_ID</b> — (optional) source cost element (costing-method name, e.g. AveragePO)<br>
	 *   <b>EvaluationStartDate</b> — (required) revaluation cut-off date<br>
	 *   <b>DateAcct</b> — (required) accounting date<br>
	 * @cucumber.depends StepDefData: M_CostRevaluation_StepDefData, C_AcctSchema_StepDefData, M_CostElement_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And metasfresh contains M_CostRevaluation:
	 *   | Identifier    | C_AcctSchema_ID | M_CostElement_ID     | RevaluationSource   | CopyFrom_M_CostElement_ID | EvaluationStartDate | DateAcct   |
	 *   | costRevalMAI  | acctSchema      | MovingAverageInvoice | CopyFromCostElement | AveragePO                 | 2025-12-31          | 2025-12-31 |
	 * </pre>
	 */
	@Given("^metasfresh contains M_CostRevaluation:$")
	public void createCostRevaluation(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createCostRevaluation);
	}

	private void createCostRevaluation(@NonNull final DataTableRow row)
	{
		final AcctSchemaId acctSchemaId = row.getAsIdentifier(I_M_CostRevaluation.COLUMNNAME_C_AcctSchema_ID).lookupIdIn(acctSchemaTable);
		final CostElementId targetCostElementId = costElementTable.getSingleId(row.getAsString(I_M_CostRevaluation.COLUMNNAME_M_CostElement_ID));

		final I_M_CostRevaluation record = InterfaceWrapperHelper.newInstance(I_M_CostRevaluation.class);
		record.setAD_Org_ID(Env.getOrgId().getRepoId());
		// The C_DocType_ID column is mandatory; in the UI its onNew callout sets it. A programmatic save
		// bypasses the callout, so resolve the CostRevaluation doc type here (mirrors the callout).
		final DocTypeId docTypeId = docTypeDAO.getDocTypeId(DocTypeQuery.builder()
				.docBaseType(DocBaseType.CostRevaluation)
				.docSubType(DocTypeQuery.DOCSUBTYPE_Any)
				.adClientId(record.getAD_Client_ID())
				.adOrgId(record.getAD_Org_ID())
				.build());
		record.setC_DocType_ID(docTypeId.getRepoId());
		record.setC_AcctSchema_ID(acctSchemaId.getRepoId());
		record.setM_CostElement_ID(targetCostElementId.getRepoId());
		record.setRevaluationSource(row.getAsString(I_M_CostRevaluation.COLUMNNAME_RevaluationSource));

		row.getAsOptionalString(I_M_CostRevaluation.COLUMNNAME_CopyFrom_M_CostElement_ID)
				.map(costElementTable::getSingleId)
				.ifPresent(sourceCostElementId -> record.setCopyFrom_M_CostElement_ID(sourceCostElementId.getRepoId()));

		record.setEvaluationStartDate(row.getAsLocalDateTimestamp(I_M_CostRevaluation.COLUMNNAME_EvaluationStartDate));
		record.setDateAcct(row.getAsLocalDateTimestamp(I_M_CostRevaluation.COLUMNNAME_DateAcct));
		record.setDocStatus(IDocument.STATUS_Drafted);
		record.setDocAction(IDocument.ACTION_Complete);

		InterfaceWrapperHelper.save(record);

		costRevaluationTable.putOrReplace(row.getAsIdentifier(), record);
	}

	/**
	 * Creates the revaluation lines by invoking {@link CostRevaluationService#createLines} directly.
	 * <p>
	 * Real-world trigger: the user runs the {@code M_CostRevaluation_CreateLines} process from the document.
	 * Direct invocation is safe because that process is thin glue — {@code doIt()} only performs precondition checks
	 * (single-selection, drafted, no active lines) and then delegates to this same {@code CostRevaluationService#createLines};
	 * it adds no logic of its own, so calling the service directly exercises the identical business path.
	 *
	 * @cucumber.stepdef
	 * @cucumber.depends StepDefData: M_CostRevaluation_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And create lines for cost revaluation costRevalMAI
	 * </pre>
	 */
	@And("^create lines for cost revaluation ([^ ]+)$")
	public void createLines(@NonNull final String identifier)
	{
		final I_M_CostRevaluation record = costRevaluationTable.get(identifier);
		costRevaluationService.createLines(CostRevaluationId.ofRepoId(record.getM_CostRevaluation_ID()));
	}

	/**
	 * Completes the cost-revaluation document via the real DocAction pipeline
	 * ({@code completeIt} → {@code createDetails} → seed target cost).
	 *
	 * @cucumber.stepdef
	 * @cucumber.depends StepDefData: M_CostRevaluation_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And the cost revaluation identified by costRevalMAI is completed
	 * </pre>
	 */
	@And("^the cost revaluation identified by (.*) is completed$")
	public void costRevaluation_is_completed(@NonNull final String identifier)
	{
		final I_M_CostRevaluation record = costRevaluationTable.get(identifier);
		record.setDocAction(IDocument.ACTION_Complete);
		documentBL.processEx(record, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
	}

	/**
	 * Reverses the cost-revaluation document via the real DocAction pipeline
	 * ({@code reverseCorrectIt} → undo the seeded target cost, value-neutral).
	 *
	 * @cucumber.stepdef
	 * @cucumber.depends StepDefData: M_CostRevaluation_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And the cost revaluation identified by costRevalMAI is reversed
	 * </pre>
	 */
	@And("^the cost revaluation identified by (.*) is reversed$")
	public void costRevaluation_is_reversed(@NonNull final String identifier)
	{
		final I_M_CostRevaluation record = costRevaluationTable.get(identifier);
		record.setDocAction(IDocument.ACTION_Reverse_Correct);
		documentBL.processEx(record, IDocument.ACTION_Reverse_Correct, IDocument.STATUS_Reversed);
	}

	/**
	 * Attempts to create the revaluation lines and asserts it is refused because the switch copies a cost
	 * element onto itself (source == target), which is a nonsensical no-op.
	 * <p>
	 * Real-world trigger: the user runs the {@code M_CostRevaluation_CreateLines} process. Direct invocation is safe
	 * because that process is thin glue — {@code doIt()} only runs precondition checks and delegates to this same
	 * {@code CostRevaluationService#createLines}, adding no logic — so the refusal exercised here is the identical path.
	 *
	 * @cucumber.stepdef
	 * @cucumber.depends StepDefData: M_CostRevaluation_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then create lines for cost revaluation selfCopy expecting error
	 * </pre>
	 */
	@And("^create lines for cost revaluation (.*) expecting error$")
	public void createLines_expectingError(@NonNull final String identifier)
	{
		final I_M_CostRevaluation record = costRevaluationTable.get(identifier);
		assertThatThrownBy(() -> costRevaluationService.createLines(CostRevaluationId.ofRepoId(record.getM_CostRevaluation_ID())))
				.hasMessageContaining("onto itself");
	}

	/**
	 * Asserts the opening-anchor {@code M_CostDetail} that a completed {@code CopyFromCostElement} revaluation wrote for
	 * a product's target cost element — the anchor at which the target's moving average starts.
	 * <p>
	 * Scoped to the given document's own line, so an anchor left by another scenario or another switch cannot satisfy it.
	 * <p>
	 * Deliberately does NOT reuse {@code M_CostDetail_StepDef}'s matcher machinery: {@link CostDetailMatcher} never
	 * asserts {@code DateAcct} (the whole point here — the anchor must sit AT the cut-off), and
	 * {@link CostingDocumentRefResolver} cannot resolve a {@code M_CostRevaluationLine}-scoped document ref because
	 * revaluation lines are created by the document itself and so are held in no {@code StepDefData} registry.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_Product_ID</b> — (required, identifier-ref) the product whose line seeded the anchor<br>
	 *   <b>M_CostElement_ID</b> — (required) target cost element (costing-method name, e.g. MovingAverageInvoice)<br>
	 *   <b>DateAcct</b> — (required) expected accounting date of the anchor<br>
	 *   <b>Qty</b> — (required) expected anchor quantity with UOM (e.g. "0 PCE")<br>
	 *   <b>Amt</b> — (required) expected anchor amount with currency (e.g. "0 CHF")<br>
	 * @cucumber.depends StepDefData: M_CostRevaluation_StepDefData, M_Product_StepDefData, M_CostElement_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then the cost revaluation identified by costRevalMAI seeded opening cost details:
	 *   | M_Product_ID | M_CostElement_ID     | DateAcct   | Qty   | Amt   |
	 *   | product      | MovingAverageInvoice | 2025-12-31 | 0 PCE | 0 CHF |
	 * </pre>
	 */
	@Then("^the cost revaluation identified by (.*) seeded opening cost details:$")
	public void costRevaluation_seededOpeningCostDetails(@NonNull final String identifier, @NonNull final DataTable dataTable)
	{
		final I_M_CostRevaluation record = costRevaluationTable.get(identifier);
		final List<CostRevaluationLine> lines = costRevaluationRepository.getLinesByCostRevaluationId(
				CostRevaluationId.ofRepoId(record.getM_CostRevaluation_ID()));

		DataTableRows.of(dataTable).forEach(row -> {
			final ProductId productId = row.getAsIdentifier(I_M_CostDetail.COLUMNNAME_M_Product_ID).lookupIdIn(productTable);
			final CostElementId costElementId = costElementTable.getSingleId(row.getAsString(I_M_CostDetail.COLUMNNAME_M_CostElement_ID));

			final CostDetail anchor = getSingleAnchor(lines, productId, costElementId);

			final Quantity expectedQty = row.getAsQuantity(I_M_CostDetail.COLUMNNAME_Qty, null, uomDAO::getByX12DE355);

			final SoftAssertions softly = new SoftAssertions();
			softly.assertThat(anchor.getDateAcct()).as("DateAcct")
					.isEqualTo(row.getAsLocalDateTimestamp(I_M_CostDetail.COLUMNNAME_DateAcct).toInstant());
			softly.assertThat(anchor.getQty().getUomId()).as("Qty UOM").isEqualTo(expectedQty.getUomId());
			softly.assertThat(anchor.getQty().toBigDecimal()).as("Qty").isEqualByComparingTo(expectedQty.toBigDecimal());
			softly.assertThat(anchor.getAmt().toMoney()).as("Amt")
					.isEqualTo(row.getAsMoney(I_M_CostDetail.COLUMNNAME_Amt, moneyService::getCurrencyIdByCurrencyCode));
			softly.assertAll();
		});
	}

	private CostDetail getSingleAnchor(
			@NonNull final List<CostRevaluationLine> lines,
			@NonNull final ProductId productId,
			@NonNull final CostElementId costElementId)
	{
		final ImmutableList<CostRevaluationLine> matchingLines = lines.stream()
				.filter(line -> productId.equals(line.getCostSegmentAndElement().getProductId()))
				.filter(line -> costElementId.equals(line.getCostSegmentAndElement().getCostElementId()))
				.collect(ImmutableList.toImmutableList());
		assertThat(matchingLines).as("active cost revaluation lines for %s / %s", productId, costElementId).hasSize(1);

		final ImmutableList<CostDetail> anchors = costDetailRepository.stream(CostDetailQuery.builder()
						.productId(productId)
						.costElementId(costElementId)
						.documentRef(CostingDocumentRef.ofCostRevaluationLineId(matchingLines.get(0).getId()))
						.build())
				.collect(ImmutableList.toImmutableList());
		assertThat(anchors).as("opening anchor cost details of %s", matchingLines.get(0).getId()).hasSize(1);

		return anchors.get(0);
	}
}
