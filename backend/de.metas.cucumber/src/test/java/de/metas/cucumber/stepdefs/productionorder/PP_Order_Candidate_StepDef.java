/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.cucumber.stepdefs.productionorder;

import com.google.common.collect.ImmutableList;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_S_Resource;
import org.compiere.util.TimeUtil;
import org.eevolution.model.I_PP_Order_Candidate;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_Planning;
import org.eevolution.productioncandidate.async.PPOrderCandidateEnqueuer;
import org.eevolution.productioncandidate.model.PPOrderCandidateId;
import org.eevolution.productioncandidate.service.PPOrderCandidateService;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

public class PP_Order_Candidate_StepDef
{
	private final StepDefData<I_M_Product> productTable;
	private final StepDefData<I_PP_Product_BOM> productBOMTable;
	private final StepDefData<I_PP_Product_Planning> productPlanningTable;
	private final StepDefData<I_PP_Order_Candidate> ppOrderCandidateTable;

	private final PPOrderCandidateEnqueuer ppOrderCandidateEnqueuer;
	private final PPOrderCandidateService ppOrderCandidateService;

	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public PP_Order_Candidate_StepDef(
			@NonNull final StepDefData<I_M_Product> productTable,
			@NonNull final StepDefData<I_PP_Product_BOM> productBOMTable,
			@NonNull final StepDefData<I_PP_Product_Planning> productPlanningTable,
			@NonNull final StepDefData<I_PP_Order_Candidate> ppOrderCandidateTable)
	{
		this.ppOrderCandidateEnqueuer = SpringContextHolder.instance.getBean(PPOrderCandidateEnqueuer.class);
		this.ppOrderCandidateService = SpringContextHolder.instance.getBean(PPOrderCandidateService.class);

		this.productTable = productTable;
		this.productBOMTable = productBOMTable;
		this.productPlanningTable = productPlanningTable;
		this.ppOrderCandidateTable = ppOrderCandidateTable;
	}

	@And("^after not more than (.*)s, PP_Order_Candidates are found$")
	public void validatePP_Order_Candidate(
			final int timeoutSec,
			@NonNull final DataTable dataTable) throws InterruptedException
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> row : tableRows)
		{
			validatePP_Order_Candidate(timeoutSec, row);
		}
	}

	@And("update PP_Order_Candidates")
	public void updatePPOrderCandidate(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> row : tableRows)
		{
			updatePPOrderCandidate(row);
		}
	}

	@And("the following PP_Order_Candidates are enqueued for generating PP_Orders")
	public void enqueuePP_Order_Candidate(@NonNull final DataTable dataTable)
	{
		final List<I_PP_Order_Candidate> ppOrderCandidates = new ArrayList<>();

		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> row : tableRows)
		{
			ppOrderCandidates.add(getPPOrderCandidate(row));
		}

		final ImmutableList<PPOrderCandidateId> ppOrderCandidateIds = ppOrderCandidates
				.stream()
				.map(I_PP_Order_Candidate::getPP_Order_Candidate_ID)
				.map(PPOrderCandidateId::ofRepoId)
				.collect(ImmutableList.toImmutableList());

		final PPOrderCandidateEnqueuer.Result result = ppOrderCandidateEnqueuer
				.enqueueCandidateIds(ppOrderCandidateIds);

		assertThat(result.getEnqueuedPackagesCount()).isEqualTo(tableRows.size());
	}

	@And("the following PP_Order_Candidates are re-opened")
	public void reOpenPP_Order_Candidate(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> row : tableRows)
		{
			ppOrderCandidateService.reopenCandidate(getPPOrderCandidate(row));
		}
	}

	private void updatePPOrderCandidate(@NonNull final Map<String, String> tableRow)
	{
		final String ppOrderCandidateIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_PP_Order_Candidate.COLUMNNAME_PP_Order_Candidate_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_PP_Order_Candidate ppOrderCandidateRecord = ppOrderCandidateTable.get(ppOrderCandidateIdentifier);

		final ZonedDateTime dateStartSchedule = DataTableUtil.extractZonedDateTimeOrNullForColumnName(tableRow, I_PP_Order_Candidate.COLUMNNAME_DateStartSchedule);
		final BigDecimal qtyEntered = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, I_PP_Order_Candidate.COLUMNNAME_QtyEntered);

		if (dateStartSchedule != null)
		{
			ppOrderCandidateRecord.setDateStartSchedule(TimeUtil.asTimestamp(dateStartSchedule));
		}

		if (qtyEntered != null)
		{
			ppOrderCandidateRecord.setQtyEntered(qtyEntered);
		}

		saveRecord(ppOrderCandidateRecord);
	}

	private I_PP_Order_Candidate getPPOrderCandidate(@NonNull final Map<String, String> tableRow)
	{
		final String ppOrderCandidateIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_PP_Order_Candidate.COLUMNNAME_PP_Order_Candidate_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		return ppOrderCandidateTable.get(ppOrderCandidateIdentifier);
	}

	private void validatePP_Order_Candidate(
			final int timeoutSec,
			@NonNull final Map<String, String> tableRow) throws InterruptedException
	{
		final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_Product.COLUMNNAME_M_Product_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_M_Product productRecord = productTable.get(productIdentifier);

		final String productBOMIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_PP_Product_BOM.COLUMNNAME_PP_Product_BOM_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_PP_Product_BOM productBOMRecord = productBOMTable.get(productBOMIdentifier);

		final String productPlanningIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_PP_Product_Planning.COLUMNNAME_PP_Product_Planning_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_PP_Product_Planning productPlanningRecord = productPlanningTable.get(productPlanningIdentifier);

		final int resourceId = DataTableUtil.extractIntForColumnName(tableRow, I_S_Resource.COLUMNNAME_S_Resource_ID);
		final int qtyEntered = DataTableUtil.extractIntForColumnName(tableRow, I_PP_Order_Candidate.COLUMNNAME_QtyEntered);
		final int qtyToProcess = DataTableUtil.extractIntForColumnName(tableRow, I_PP_Order_Candidate.COLUMNNAME_QtyToProcess);
		final int qtyProcessed = DataTableUtil.extractIntForColumnName(tableRow, I_PP_Order_Candidate.COLUMNNAME_QtyProcessed);

		final String x12de355Code = DataTableUtil.extractStringForColumnName(tableRow, I_C_UOM.COLUMNNAME_C_UOM_ID + "." + X12DE355.class.getSimpleName());
		final UomId uomId = uomDAO.getUomIdByX12DE355(X12DE355.ofCode(x12de355Code));

		final Instant datePromised = DataTableUtil.extractInstantForColumnName(tableRow, I_PP_Order_Candidate.COLUMNNAME_DatePromised);
		final Instant dateStartSchedule = DataTableUtil.extractInstantForColumnName(tableRow, I_PP_Order_Candidate.COLUMNNAME_DateStartSchedule);
		final Boolean isProcessed = DataTableUtil.extractBooleanForColumnName(tableRow, I_PP_Order_Candidate.COLUMNNAME_Processed);
		final Boolean isClosed = DataTableUtil.extractBooleanForColumnName(tableRow, I_PP_Order_Candidate.COLUMNNAME_IsClosed);

		final String orderCandidateRecordIdentifier = DataTableUtil.extractRecordIdentifier(tableRow, I_PP_Order_Candidate.Table_Name);
		final Supplier<Boolean> ppOrderCandidateQueryExecutor = () -> {

			final I_PP_Order_Candidate orderCandidateRecord = queryBL.createQueryBuilder(I_PP_Order_Candidate.class)
					.addEqualsFilter(I_PP_Order_Candidate.COLUMNNAME_M_Product_ID, productRecord.getM_Product_ID())
					.addEqualsFilter(I_PP_Order_Candidate.COLUMNNAME_PP_Product_BOM_ID, productBOMRecord.getPP_Product_BOM_ID())
					.addEqualsFilter(I_PP_Order_Candidate.COLUMNNAME_PP_Product_Planning_ID, productPlanningRecord.getPP_Product_Planning_ID())
					.addEqualsFilter(I_PP_Order_Candidate.COLUMNNAME_S_Resource_ID, resourceId)
					.addEqualsFilter(I_PP_Order_Candidate.COLUMNNAME_QtyEntered, qtyEntered)
					.addEqualsFilter(I_PP_Order_Candidate.COLUMNNAME_QtyToProcess, qtyToProcess)
					.addEqualsFilter(I_PP_Order_Candidate.COLUMNNAME_QtyProcessed, qtyProcessed)
					.addEqualsFilter(I_PP_Order_Candidate.COLUMNNAME_C_UOM_ID, uomId.getRepoId())
					.addEqualsFilter(I_PP_Order_Candidate.COLUMNNAME_DatePromised, datePromised)
					.addEqualsFilter(I_PP_Order_Candidate.COLUMNNAME_DateStartSchedule, dateStartSchedule)
					.addEqualsFilter(I_PP_Order_Candidate.COLUMNNAME_Processed, isProcessed)
					.addEqualsFilter(I_PP_Order_Candidate.COLUMNNAME_IsClosed, isClosed)
					.create()
					.firstOnly(I_PP_Order_Candidate.class);

			if (orderCandidateRecord != null)
			{
				ppOrderCandidateTable.putOrReplace(orderCandidateRecordIdentifier, orderCandidateRecord);
				return true;
			}
			else
			{
				return false;
			}
		};

		final boolean candidateFound = StepDefUtil.tryAndWait(timeoutSec, 500, ppOrderCandidateQueryExecutor);

		assertThat(candidateFound).isTrue();
	}
}