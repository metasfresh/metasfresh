package de.metas.cucumber.stepdefs.costing;

import de.metas.acct.api.AcctSchemaId;
import de.metas.costing.CostElementId;
import de.metas.costrevaluation.CostRevaluationId;
import de.metas.costrevaluation.CostRevaluationService;
import de.metas.document.DocBaseType;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.acctschema.C_AcctSchema_StepDefData;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_CostRevaluation;
import org.compiere.util.Env;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Creates, seeds and completes {@link I_M_CostRevaluation} cost-revaluation documents.
 */
@RequiredArgsConstructor
public class M_CostRevaluation_StepDef
{
	@NonNull private final CostRevaluationService costRevaluationService = SpringContextHolder.instance.getBean(CostRevaluationService.class);
	@NonNull private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	@NonNull private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);

	@NonNull private final M_CostRevaluation_StepDefData costRevaluationTable;
	@NonNull private final C_AcctSchema_StepDefData acctSchemaTable;
	@NonNull private final M_CostElement_StepDefData costElementTable;

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
}
