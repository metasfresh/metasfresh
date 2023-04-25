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

package de.metas.cucumber.stepdefs.pporder;

import com.google.common.collect.ImmutableList;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.attribute.M_AttributeSetInstance_StepDefData;
import de.metas.cucumber.stepdefs.billofmaterial.PP_Product_BOM_StepDefData;
import de.metas.cucumber.stepdefs.productplanning.PP_Product_Planning_StepDefData;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.material.event.commons.AttributesKey;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.AttributesKeys;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.eevolution.model.I_PP_Order_Candidate;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_Planning;
import org.eevolution.productioncandidate.async.PPOrderCandidateEnqueuer;
import org.eevolution.productioncandidate.model.PPOrderCandidateId;
import org.eevolution.productioncandidate.service.PPOrderCandidateService;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.I_M_Warehouse.COLUMNNAME_M_Warehouse_ID;
import static org.eevolution.model.I_PP_Order_Candidate.COLUMNNAME_PP_Order_Candidate_ID;
import static org.eevolution.model.I_PP_Product_Planning.COLUMNNAME_M_AttributeSetInstance_ID;

public class PP_Order_Candidate_StepDef
{
	private final M_Product_StepDefData productTable;
	private final PP_Product_BOM_StepDefData productBOMTable;
	private final PP_Product_Planning_StepDefData productPlanningTable;
	private final PP_Order_Candidate_StepDefData ppOrderCandidateTable;
	private final M_AttributeSetInstance_StepDefData attributeSetInstanceTable;
	private final M_Warehouse_StepDefData warehouseTable;

	private final PPOrderCandidateEnqueuer ppOrderCandidateEnqueuer;
	private final PPOrderCandidateService ppOrderCandidateService;

	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	private static final AdMessageKey MSG_QTY_ENTERED_LOWER_THAN_QTY_PROCESSED = AdMessageKey.of("org.eevolution.productioncandidate.model.interceptor.QtyEnteredLowerThanQtyProcessed");
	private static final AdMessageKey MSG_QTY_TO_PROCESS_GREATER_THAN_QTY_LEFT = AdMessageKey.of("org.eevolution.productioncandidate.model.interceptor.QtyToProcessGreaterThanQtyLeftToBeProcessed");

	public PP_Order_Candidate_StepDef(
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final PP_Product_BOM_StepDefData productBOMTable,
			@NonNull final PP_Product_Planning_StepDefData productPlanningTable,
			@NonNull final PP_Order_Candidate_StepDefData ppOrderCandidateTable,
			@NonNull final M_AttributeSetInstance_StepDefData attributeSetInstanceTable,
			@NonNull final M_Warehouse_StepDefData warehouseTable)
	{
		this.attributeSetInstanceTable = attributeSetInstanceTable;
		this.ppOrderCandidateEnqueuer = SpringContextHolder.instance.getBean(PPOrderCandidateEnqueuer.class);
		this.ppOrderCandidateService = SpringContextHolder.instance.getBean(PPOrderCandidateService.class);

		this.productTable = productTable;
		this.productBOMTable = productBOMTable;
		this.productPlanningTable = productPlanningTable;
		this.ppOrderCandidateTable = ppOrderCandidateTable;
		this.warehouseTable = warehouseTable;
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

	@When("the following PP_Order_Candidates are closed")
	public void close_PP_Order_Candidate(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> row : tableRows)
		{
			ppOrderCandidateService.closeCandidate(getPPOrderCandidate(row));
		}
	}

	@Then("the following PP_Order_Candidates are closed and cannot be re-opened")
	public void validate_closed_PP_Order_Candidate_cannot_be_reopened(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> row : tableRows)
		{
			try
			{
				final I_PP_Order_Candidate orderCandidateRecord = getPPOrderCandidate(row);

				assertThat(orderCandidateRecord.isClosed()).isTrue();

				ppOrderCandidateService.reopenCandidate(orderCandidateRecord);

				assertThat(false).as("reopenCandidate should throw error").isTrue();
			}
			catch (final AdempiereException adempiereException)
			{
				assertThat(adempiereException.getMessage()).contains("Cannot reopen a closed candidate!");
			}
		}
	}

	@Then("update PP_Order_Candidate's qty to process to more than left to be processed expecting exception")
	public void update_qtyToProcess_to_invalid_qty(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> row : tableRows)
		{
			updateQtyToProcessAndExpectForException(row);
		}
	}

	@Then("update PP_Order_Candidate's qty entered to less than processed expecting exception")
	public void update_qty_to_invalid_qty(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> row : tableRows)
		{
			updateQtyEnteredAndExpectForException(row);
		}
	}

	@Given("metasfresh contains PP_Order_Candidates")
	public void metasfresh_contains_PP_Order_Candidate(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> row : tableRows)
		{
			createPPOrderCandidate(row);
		}
	}

	private void updatePPOrderCandidate(@NonNull final Map<String, String> tableRow)
	{
		final String ppOrderCandidateIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_PP_Order_Candidate_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_PP_Order_Candidate ppOrderCandidateRecord = ppOrderCandidateTable.get(ppOrderCandidateIdentifier);

		final ZonedDateTime dateStartSchedule = DataTableUtil.extractZonedDateTimeOrNullForColumnName(tableRow, I_PP_Order_Candidate.COLUMNNAME_DateStartSchedule);
		final BigDecimal qtyEntered = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, I_PP_Order_Candidate.COLUMNNAME_QtyEntered);

		if (dateStartSchedule != null)
		{
			ppOrderCandidateRecord.setDateStartSchedule(TimeUtil.asTimestampNotNull(dateStartSchedule));
		}

		if (qtyEntered != null)
		{
			ppOrderCandidateRecord.setQtyEntered(qtyEntered);
		}

		saveRecord(ppOrderCandidateRecord);
	}

	private I_PP_Order_Candidate getPPOrderCandidate(@NonNull final Map<String, String> tableRow)
	{
		final String ppOrderCandidateIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_PP_Order_Candidate_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
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

		StepDefUtil.tryAndWait(timeoutSec, 500, ppOrderCandidateQueryExecutor);

		final I_PP_Order_Candidate ppOrderCandidate = ppOrderCandidateTable.get(orderCandidateRecordIdentifier);

		//validate asi
		final String attributeSetInstanceIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_M_AttributeSetInstance_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(attributeSetInstanceIdentifier))
		{
			final I_M_AttributeSetInstance expectedASI = attributeSetInstanceTable.get(attributeSetInstanceIdentifier);
			assertThat(expectedASI).isNotNull();

			final AttributesKey expectedAttributesKeys = AttributesKeys.createAttributesKeyFromASIStorageAttributes(AttributeSetInstanceId.ofRepoId(expectedASI.getM_AttributeSetInstance_ID()))
					.orElse(AttributesKey.NONE);
			final AttributesKey ppOrderCandAttributesKeys = AttributesKeys.createAttributesKeyFromASIStorageAttributes(AttributeSetInstanceId.ofRepoId(ppOrderCandidate.getM_AttributeSetInstance_ID()))
					.orElse(AttributesKey.NONE);

			assertThat(ppOrderCandAttributesKeys).isEqualTo(expectedAttributesKeys);
		}
	}

	private void updateQtyEnteredAndExpectForException(@NonNull final Map<String, String> tableRow)
	{
		final I_PP_Order_Candidate orderCandidateRecord = getPPOrderCandidate(tableRow);

		final BigDecimal qtyEntered = DataTableUtil.extractBigDecimalForColumnName(tableRow, I_PP_Order_Candidate.COLUMNNAME_QtyEntered);

		assertThat(qtyEntered).isLessThan(orderCandidateRecord.getQtyProcessed());

		orderCandidateRecord.setQtyEntered(qtyEntered);

		try
		{
			InterfaceWrapperHelper.save(orderCandidateRecord);

			assertThat(false).as("InterfaceWrapperHelper.save should throw error!").isTrue();
		}
		catch (final AdempiereException adempiereException)
		{
			final String adLanguage = Env.getAD_Language();

			final String qtyEnteredColumnTrl = msgBL.translatable(I_PP_Order_Candidate.COLUMNNAME_QtyEntered).translate(adLanguage);
			final String qtyProcessedColumnTrl = msgBL.translatable(I_PP_Order_Candidate.COLUMNNAME_QtyProcessed).translate(adLanguage);

			final ITranslatableString message = msgBL.getTranslatableMsgText(MSG_QTY_ENTERED_LOWER_THAN_QTY_PROCESSED,
																			 qtyEnteredColumnTrl,
																			 qtyProcessedColumnTrl);

			assertThat(adempiereException.getMessage()).contains(message.translate(adLanguage));
		}
	}

	private void updateQtyToProcessAndExpectForException(@NonNull final Map<String, String> tableRow)
	{
		final I_PP_Order_Candidate orderCandidateRecord = getPPOrderCandidate(tableRow);

		final BigDecimal qtyToProcess = DataTableUtil.extractBigDecimalForColumnName(tableRow, I_PP_Order_Candidate.COLUMNNAME_QtyToProcess);

		final BigDecimal actualQtyLeft = orderCandidateRecord.getQtyEntered().subtract(orderCandidateRecord.getQtyProcessed());
		assertThat(qtyToProcess).isGreaterThan(actualQtyLeft);

		orderCandidateRecord.setQtyToProcess(qtyToProcess);

		try
		{
			InterfaceWrapperHelper.save(orderCandidateRecord);

			assertThat(false).as("InterfaceWrapperHelper.save should throw error!").isTrue();
		}
		catch (final AdempiereException adempiereException)
		{
			final String adLanguage = Env.getAD_Language();

			final String qtyToProcessColumnTrl = msgBL.translatable(I_PP_Order_Candidate.COLUMNNAME_QtyToProcess).translate(adLanguage);

			final ITranslatableString message = msgBL.getTranslatableMsgText(MSG_QTY_TO_PROCESS_GREATER_THAN_QTY_LEFT, qtyToProcessColumnTrl);

			assertThat(adempiereException.getMessage()).contains(message.translate(adLanguage));
		}
	}

	private void createPPOrderCandidate(@NonNull final Map<String, String> tableRow)
	{
		final I_PP_Order_Candidate ppOrderCandidateRecord = InterfaceWrapperHelper.newInstance(I_PP_Order_Candidate.class);

		final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_M_Product.COLUMNNAME_M_Product_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_M_Product productRecord = productTable.get(productIdentifier);

		ppOrderCandidateRecord.setM_Product_ID(productRecord.getM_Product_ID());

		final String productBOMIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_PP_Product_BOM.COLUMNNAME_PP_Product_BOM_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_PP_Product_BOM productBOMRecord = productBOMTable.get(productBOMIdentifier);

		ppOrderCandidateRecord.setPP_Product_BOM_ID(productBOMRecord.getPP_Product_BOM_ID());

		final String productPlanningIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_PP_Product_Planning.COLUMNNAME_PP_Product_Planning_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_PP_Product_Planning productPlanningRecord = productPlanningTable.get(productPlanningIdentifier);

		ppOrderCandidateRecord.setPP_Product_Planning_ID(productPlanningRecord.getPP_Product_Planning_ID());

		final int resourceId = DataTableUtil.extractIntForColumnName(tableRow, I_S_Resource.COLUMNNAME_S_Resource_ID);

		ppOrderCandidateRecord.setS_Resource_ID(resourceId);

		final BigDecimal qtyEntered = DataTableUtil.extractBigDecimalForColumnName(tableRow, I_PP_Order_Candidate.COLUMNNAME_QtyEntered);

		ppOrderCandidateRecord.setQtyEntered(qtyEntered);

		final BigDecimal qtyToProcess = DataTableUtil.extractBigDecimalForColumnName(tableRow, I_PP_Order_Candidate.COLUMNNAME_QtyToProcess);

		ppOrderCandidateRecord.setQtyToProcess(qtyToProcess);

		final BigDecimal qtyProcessed = DataTableUtil.extractBigDecimalForColumnName(tableRow, I_PP_Order_Candidate.COLUMNNAME_QtyProcessed);

		ppOrderCandidateRecord.setQtyProcessed(qtyProcessed);

		final String x12de355Code = DataTableUtil.extractStringForColumnName(tableRow, I_C_UOM.COLUMNNAME_C_UOM_ID + "." + X12DE355.class.getSimpleName());
		final UomId uomId = uomDAO.getUomIdByX12DE355(X12DE355.ofCode(x12de355Code));

		ppOrderCandidateRecord.setC_UOM_ID(uomId.getRepoId());

		final Instant datePromised = DataTableUtil.extractInstantForColumnName(tableRow, I_PP_Order_Candidate.COLUMNNAME_DatePromised);
		final Instant dateStartSchedule = DataTableUtil.extractInstantForColumnName(tableRow, I_PP_Order_Candidate.COLUMNNAME_DateStartSchedule);
		final boolean isProcessed = DataTableUtil.extractBooleanForColumnNameOr(tableRow, "OPT." + I_PP_Order_Candidate.COLUMNNAME_Processed, false);
		final boolean isClosed = DataTableUtil.extractBooleanForColumnNameOr(tableRow, "OPT." + I_PP_Order_Candidate.COLUMNNAME_IsClosed, false);

		ppOrderCandidateRecord.setDatePromised(Timestamp.from(datePromised));
		ppOrderCandidateRecord.setDateStartSchedule(Timestamp.from(dateStartSchedule));
		ppOrderCandidateRecord.setProcessed(isProcessed);
		ppOrderCandidateRecord.setIsClosed(isClosed);

		final String asiIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_M_AttributeSetInstance_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(asiIdentifier))
		{
			final I_M_AttributeSetInstance asiRecord = attributeSetInstanceTable.get(asiIdentifier);
			assertThat(asiRecord).isNotNull();

			ppOrderCandidateRecord.setM_AttributeSetInstance_ID(asiRecord.getM_AttributeSetInstance_ID());
		}
		else
		{
			ppOrderCandidateRecord.setM_AttributeSetInstance_ID(AttributeSetInstanceId.NONE.getRepoId());
		}

		final String warehouseIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_M_Warehouse_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_M_Warehouse warehouseRecord = warehouseTable.get(warehouseIdentifier);

		ppOrderCandidateRecord.setM_Warehouse_ID(warehouseRecord.getM_Warehouse_ID());

		InterfaceWrapperHelper.save(ppOrderCandidateRecord);

		ppOrderCandidateTable.putOrReplace(DataTableUtil.extractRecordIdentifier(tableRow, I_PP_Order_Candidate.Table_Name), ppOrderCandidateRecord);
	}
}