/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.cucumber.stepdefs.costing;

import de.metas.acct.api.AcctSchemaId;
import de.metas.costing.CostElementId;
import de.metas.costrevaluation.CostRevaluationId;
import de.metas.costrevaluation.CostRevaluationService;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.acctschema.C_AcctSchema_StepDefData;
import de.metas.document.DocBaseType;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.product.ProductId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_CostRevaluation;
import org.compiere.model.I_M_CostRevaluationLine;
import org.compiere.util.Env;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for the {@code M_CostRevaluation} ("Kosten Neubewertung") document:
 * create the header, generate the lines via the real "Create Lines" process, set a line's target price,
 * and complete the document.
 */
@RequiredArgsConstructor
public class M_CostRevaluation_StepDef
{
	@NonNull private final CostRevaluationService costRevaluationService = SpringContextHolder.instance.getBean(CostRevaluationService.class);
	@NonNull private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	@NonNull private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull private final C_AcctSchema_StepDefData acctSchemaTable;
	@NonNull private final M_CostElement_StepDefData costElementTable;
	@NonNull private final M_Product_StepDefData productTable;
	@NonNull private final M_CostRevaluation_StepDefData costRevaluationTable;

	/**
	 * Creates one drafted {@code M_CostRevaluation} document header per row.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Identifier</b> — (required) alias for the created header<br>
	 *   <b>C_AcctSchema_ID</b> — (required, identifier-ref) accounting schema<br>
	 *   <b>M_CostElement_ID</b> — (required) costing-method name/code resolving to a single material cost element (e.g. AveragePO)<br>
	 *   <b>EvaluationStartDate</b> — (required) date from which cost details are replayed<br>
	 *   <b>DateAcct</b> — (required) posting date<br>
	 * @cucumber.depends StepDefData: C_AcctSchema_StepDefData, M_CostElement_StepDefData, M_CostRevaluation_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And metasfresh contains M_CostRevaluation:
	 *   | Identifier  | C_AcctSchema_ID | M_CostElement_ID | EvaluationStartDate | DateAcct   |
	 *   | revaluation | acctSchema      | AveragePO        | 2024-03-06          | 2024-03-06 |
	 * </pre>
	 */
	@And("metasfresh contains M_CostRevaluation:")
	public void createCostRevaluations(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createCostRevaluation);
	}

	private void createCostRevaluation(@NonNull final DataTableRow row)
	{
		final AcctSchemaId acctSchemaId = row.getAsIdentifier(I_M_CostRevaluation.COLUMNNAME_C_AcctSchema_ID).lookupIdIn(acctSchemaTable);
		final CostElementId costElementId = resolveSingleCostElementId(row.getAsString(I_M_CostRevaluation.COLUMNNAME_M_CostElement_ID));

		final DocTypeId docTypeId = docTypeDAO.getDocTypeId(DocTypeQuery.builder()
				.docBaseType(DocBaseType.CostRevaluation)
				.adClientId(Env.getClientId().getRepoId())
				.build());

		final I_M_CostRevaluation header = InterfaceWrapperHelper.newInstance(I_M_CostRevaluation.class);
		header.setAD_Org_ID(Env.getOrgId().getRepoId());
		header.setC_DocType_ID(docTypeId.getRepoId());
		header.setC_AcctSchema_ID(acctSchemaId.getRepoId());
		header.setM_CostElement_ID(costElementId.getRepoId());
		header.setEvaluationStartDate(row.getAsLocalDateTimestamp(I_M_CostRevaluation.COLUMNNAME_EvaluationStartDate));
		header.setDateAcct(row.getAsLocalDateTimestamp(I_M_CostRevaluation.COLUMNNAME_DateAcct));
		header.setDocumentNo(StepDefDataIdentifier.nextUnnamed("costRevaluation").getAsString());
		header.setDocStatus(IDocument.STATUS_Drafted);
		header.setDocAction(IDocument.ACTION_Complete);
		header.setProcessed(false);
		InterfaceWrapperHelper.save(header);

		costRevaluationTable.putOrReplace(row.getAsIdentifier(), header);
	}

	/**
	 * Runs the real "Create Lines" process ({@link CostRevaluationService#createLines}) for the given header,
	 * generating one {@code M_CostRevaluationLine} per stocked product at its current cost (NewCostPrice defaulted
	 * to the current price).
	 */
	@And("^cost revaluation lines are created for (.*)$")
	public void createLines(@NonNull final String identifier)
	{
		final I_M_CostRevaluation header = costRevaluationTable.get(identifier);
		costRevaluationService.createLines(CostRevaluationId.ofRepoId(header.getM_CostRevaluation_ID()));
	}

	/**
	 * Sets the target {@code NewCostPrice} on the already-generated line of the given product.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_CostRevaluation_ID</b> — (required, identifier-ref) the header whose line to update<br>
	 *   <b>M_Product_ID</b> — (required, identifier-ref) the product whose line to update<br>
	 *   <b>NewCostPrice</b> — (required) the target cost price<br>
	 * @cucumber.depends StepDefData: M_CostRevaluation_StepDefData, M_Product_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And update M_CostRevaluationLine:
	 *   | M_CostRevaluation_ID | M_Product_ID | NewCostPrice |
	 *   | revaluation          | product      | 15           |
	 * </pre>
	 */
	@And("update M_CostRevaluationLine:")
	public void updateCostRevaluationLines(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::updateCostRevaluationLine);
	}

	private void updateCostRevaluationLine(@NonNull final DataTableRow row)
	{
		final I_M_CostRevaluation header = row.getAsIdentifier(I_M_CostRevaluationLine.COLUMNNAME_M_CostRevaluation_ID).lookupNotNullIn(costRevaluationTable);
		final ProductId productId = row.getAsIdentifier(I_M_CostRevaluationLine.COLUMNNAME_M_Product_ID).lookupIdIn(productTable);

		// Test-side lookup of the line generated by createLines(); kept here so no production query has to be added.
		final I_M_CostRevaluationLine line = queryBL.createQueryBuilder(I_M_CostRevaluationLine.class)
				.addEqualsFilter(I_M_CostRevaluationLine.COLUMNNAME_M_CostRevaluation_ID, header.getM_CostRevaluation_ID())
				.addEqualsFilter(I_M_CostRevaluationLine.COLUMNNAME_M_Product_ID, productId.getRepoId())
				.create()
				.firstOnlyNotNull(I_M_CostRevaluationLine.class);

		line.setNewCostPrice(row.getAsBigDecimal(I_M_CostRevaluationLine.COLUMNNAME_NewCostPrice));
		InterfaceWrapperHelper.save(line);
	}

	/**
	 * Completes the given {@code M_CostRevaluation} document (DocAction Complete).
	 *
	 * @see IDocumentBL#processEx
	 */
	@And("^the cost revaluation identified by (.*) is completed$")
	public void complete(@NonNull final String identifier)
	{
		final I_M_CostRevaluation header = costRevaluationTable.get(identifier);
		documentBL.processEx(header, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
		InterfaceWrapperHelper.refresh(header);
		costRevaluationTable.putOrReplace(StepDefDataIdentifier.ofString(identifier), header);
	}

	private CostElementId resolveSingleCostElementId(@NonNull final String costElementIdentifier)
	{
		final Set<CostElementId> costElementIds = costElementTable.getIdsOfCommaSeparatedString(costElementIdentifier);
		assertThat(costElementIds).as("exactly one cost element for '%s'", costElementIdentifier).hasSize(1);
		return costElementIds.iterator().next();
	}
}
