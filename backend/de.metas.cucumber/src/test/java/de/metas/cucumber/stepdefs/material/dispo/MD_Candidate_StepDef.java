/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.cucumber.stepdefs.material.dispo;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.ItemProvider.ProviderResult;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.attribute.M_AttributeSetInstance_StepDefData;
import de.metas.cucumber.stepdefs.context.ContextAwareDescription;
import de.metas.cucumber.stepdefs.context.SharedTestContext;
import de.metas.cucumber.stepdefs.ddordercandidate.DD_Order_Candidate_StepDefData;
import de.metas.cucumber.stepdefs.distributionorder.DD_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.distributionorder.DD_Order_StepDefData;
import de.metas.cucumber.stepdefs.pporder.PP_OrderLine_Candidate_StepDefData;
import de.metas.cucumber.stepdefs.pporder.PP_Order_BOMLine_StepDefData;
import de.metas.cucumber.stepdefs.pporder.PP_Order_Candidate_StepDefData;
import de.metas.cucumber.stepdefs.pporder.PP_Order_StepDefData;
import de.metas.cucumber.stepdefs.rabbitMQ.RabbitMQ_StepDef;
import de.metas.i18n.Language;
import de.metas.logging.LogManager;
import de.metas.material.cockpit.model.I_MD_Stock;
import de.metas.material.dispo.commons.SimulatedCandidateService;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateId;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.businesscase.BusinessCaseDetail;
import de.metas.material.dispo.commons.candidate.businesscase.DemandDetail;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.commons.repository.repohelpers.RepositoryCommons;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.model.I_MD_Candidate_Demand_Detail;
import de.metas.material.dispo.model.I_MD_Candidate_StockChange_Detail;
import de.metas.material.dispo.model.X_MD_Candidate;
import de.metas.material.event.MaterialEventObserver;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.simulation.DeactivateAllSimulatedCandidatesEvent;
import de.metas.material.event.stock.ResetStockPInstanceId;
import de.metas.material.event.stock.StockChangedEvent;
import de.metas.material.event.stockestimate.AbstractStockEstimateEvent;
import de.metas.material.event.stockestimate.StockEstimateCreatedEvent;
import de.metas.material.event.stockestimate.StockEstimateDeletedEvent;
import de.metas.organization.ClientAndOrgId;
import de.metas.process.AdProcessId;
import de.metas.process.IADPInstanceDAO;
import de.metas.process.IADProcessDAO;
import de.metas.process.ProcessCalledFrom;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessExecutor;
import de.metas.process.ProcessInfo;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.util.text.tabular.Table;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.keys.AttributesKeys;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.assertj.core.api.SoftAssertions;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Process;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import static de.metas.cucumber.stepdefs.material.dispo.CandidatesToTabularStringConverter.toTabularStringFromCandidateRows;
import static de.metas.material.dispo.model.I_MD_Candidate.COLUMNNAME_DateProjected;
import static de.metas.material.dispo.model.I_MD_Candidate.COLUMNNAME_MD_Candidate_BusinessCase;
import static de.metas.material.dispo.model.I_MD_Candidate.COLUMNNAME_MD_Candidate_ID;
import static de.metas.material.dispo.model.I_MD_Candidate.COLUMNNAME_MD_Candidate_Type;
import static de.metas.material.dispo.model.I_MD_Candidate.COLUMNNAME_M_AttributeSetInstance_ID;
import static de.metas.material.dispo.model.I_MD_Candidate.COLUMNNAME_M_Product_ID;
import static de.metas.material.dispo.model.I_MD_Candidate.COLUMNNAME_Qty;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

/**
 * Step definitions for {@code MD_Candidate} — the material-disposition candidates that carry the
 * projected ATP. Covers creating and asserting candidates and their detail rows, posting material
 * events that make the dispo engine build a candidate chain, and reading the resulting running ATP
 * off the {@code STOCK} candidates.
 */
@RequiredArgsConstructor
public class MD_Candidate_StepDef
{
	@NonNull private final static Logger logger = LogManager.getLogger(MD_Candidate_StepDef.class);
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final IADProcessDAO processDAO = Services.get(IADProcessDAO.class);
	@NonNull private final IADPInstanceDAO pInstanceDAO = Services.get(IADPInstanceDAO.class);
	@NonNull private final PostMaterialEventService postMaterialEventService = SpringContextHolder.instance.getBean(PostMaterialEventService.class);
	@NonNull private final MaterialDispoRecordRepository materialDispoRecordRepository = SpringContextHolder.instance.getBean(MaterialDispoRecordRepository.class);
	@NonNull private final CandidateRepositoryRetrieval candidateRepositoryRetrieval = SpringContextHolder.instance.getBean(CandidateRepositoryRetrieval.class);
	@NonNull private final MaterialEventObserver materialEventObserver = SpringContextHolder.instance.getBean(MaterialEventObserver.class);
	@NonNull private final SimulatedCandidateService simulatedCandidateService = SpringContextHolder.instance.getBean(SimulatedCandidateService.class);
	@NonNull private final MaterialDispoDataItem_StepDefData materialDispoDataItemStepDefData;
	@NonNull private final M_Product_StepDefData productTable;
	@NonNull private final MD_Candidate_StepDefData stockCandidateTable;
	@NonNull private final MD_Candidate_StockChange_Detail_StepDefData stockChangeDetailStepDefData;
	@NonNull private final C_OrderLine_StepDefData orderLineTable;
	@NonNull private final M_AttributeSetInstance_StepDefData attributeSetInstanceTable;

	@NonNull private final DD_Order_Candidate_StepDefData ddOrderCandidateTable;
	@NonNull private final DD_Order_StepDefData ddOrderTable;
	@NonNull private final DD_OrderLine_StepDefData ddOrderLineTable;

	@NonNull private final PP_Order_Candidate_StepDefData ppOrderCandidateTable;
	@NonNull private final PP_OrderLine_Candidate_StepDefData ppOrderLineCandidateTable;
	@NonNull private final PP_Order_StepDefData ppOrderTable;
	@NonNull private final PP_Order_BOMLine_StepDefData ppOrderBOMLineTable;

	@NonNull private final RabbitMQ_StepDef rabbitMQStepDef;

	@When("metasfresh initially has this MD_Candidate data")
	public void metasfresh_has_this_md_candidate_data1(@NonNull final MD_Candidate_StepDefTable table) throws Throwable
	{
		table.forEach((tableRow) -> {
			final WarehouseId warehouseId = CoalesceUtil.coalesceNotNull(tableRow.getWarehouseId(), StepDefConstants.WAREHOUSE_ID);

			final I_MD_Candidate mdCandidateRecord = InterfaceWrapperHelper.newInstance(I_MD_Candidate.class);
			mdCandidateRecord.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());
			mdCandidateRecord.setM_Product_ID(tableRow.getProductId().getRepoId());
			mdCandidateRecord.setM_Warehouse_ID(warehouseId.getRepoId());
			mdCandidateRecord.setMD_Candidate_Type(tableRow.getType().getCode());
			mdCandidateRecord.setMD_Candidate_BusinessCase(CandidateBusinessCase.toCode(tableRow.getBusinessCase()));
			mdCandidateRecord.setQty(tableRow.getQty());
			mdCandidateRecord.setDateProjected(TimeUtil.asTimestamp(tableRow.getTime()));

			setAttributeSetInstance(mdCandidateRecord, tableRow);
			InterfaceWrapperHelper.saveRecord(mdCandidateRecord);

			mdCandidateRecord.setSeqNo(mdCandidateRecord.getMD_Candidate_ID());
			InterfaceWrapperHelper.saveRecord(mdCandidateRecord);

			final I_MD_Candidate mdStockCandidateRecord = InterfaceWrapperHelper.newInstance(I_MD_Candidate.class);
			mdStockCandidateRecord.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());
			mdStockCandidateRecord.setM_Product_ID(tableRow.getProductId().getRepoId());
			mdStockCandidateRecord.setM_Warehouse_ID(warehouseId.getRepoId());
			mdStockCandidateRecord.setMD_Candidate_Type(CandidateType.STOCK.getCode());
			mdStockCandidateRecord.setSeqNo(mdCandidateRecord.getMD_Candidate_ID());
			final boolean isDemand = CandidateType.DEMAND.equals(tableRow.getType()) || CandidateType.INVENTORY_DOWN.equals(tableRow.getType());

			setAttributeSetInstance(mdStockCandidateRecord, tableRow);

			if (isDemand)
			{
				mdStockCandidateRecord.setMD_Candidate_Parent_ID(mdCandidateRecord.getMD_Candidate_ID());
			}
			mdStockCandidateRecord.setQty(tableRow.getAtp());
			mdStockCandidateRecord.setDateProjected(TimeUtil.asTimestamp(tableRow.getTime()));
			InterfaceWrapperHelper.saveRecord(mdStockCandidateRecord);

			final boolean isSupply = CandidateType.SUPPLY.equals(tableRow.getType()) || CandidateType.INVENTORY_UP.equals(tableRow.getType());
			if (isSupply)
			{
				mdCandidateRecord.setMD_Candidate_Parent_ID(mdStockCandidateRecord.getMD_Candidate_ID());
				InterfaceWrapperHelper.saveRecord(mdCandidateRecord);
			}

			if (!isDemand && !isSupply)
			{
				fail("Unsupported type " + tableRow.getType());
			}

			stockCandidateTable.putOrReplace(tableRow.getIdentifier(), mdStockCandidateRecord);
		});
	}

	@Then("metasfresh has this MD_Candidate data")
	public void metasfresh_has_this_md_candidate_data(@NonNull final MD_Candidate_StepDefTable table) throws Throwable
	{
		table.forEach((tableRow) -> {
			final MaterialDispoDataItem materialDispoRecord = materialDispoRecordRepository.getBy(tableRow.toCandidatesQuery());
			assertThat(materialDispoRecord).isNotNull(); // add message

			assertThat(materialDispoRecord.getType()).isEqualTo(tableRow.getType());
			assertThat(materialDispoRecord.getBusinessCase()).isEqualTo(tableRow.getBusinessCase());
			assertThat(materialDispoRecord.getMaterialDescriptor().getProductId()).isEqualTo(tableRow.getProductId().getRepoId());
			assertThat(materialDispoRecord.getMaterialDescriptor().getDate()).isEqualTo(tableRow.getTime());
			assertThat(materialDispoRecord.getMaterialDescriptor().getQuantity()).isEqualByComparingTo(tableRow.getQty());
			assertThat(materialDispoRecord.getAtp()).isEqualByComparingTo(tableRow.getAtp());

			materialDispoDataItemStepDefData.putIfMissing(tableRow.getIdentifier(), materialDispoRecord);
		});
	}

	@Then("^after not more than (.*)s, metasfresh has this MD_Candidate data$")
	public void metasfresh_has_this_md_candidate_data(final int timeoutSec, @NonNull final MD_Candidate_StepDefTable table) throws Throwable
	{
		final Supplier<Boolean> mdCandidateDemandDetailRecordsCounterChecker = () ->
				queryBL.createQueryBuilderOutOfTrx(I_MD_Candidate_Demand_Detail.class)
						.addOnlyActiveRecordsFilter()
						.create()
						.count() > 0;

		StepDefUtil.tryAndWait(timeoutSec, 500, mdCandidateDemandDetailRecordsCounterChecker);

		table.forEach((tableRow) -> {
			final MaterialDispoDataItem materialDispoDataItem = materialDispoRecordRepository.getBy(tableRow.toCandidatesQuery());

			assertThat(materialDispoDataItem).isNotNull(); // add message
			assertThat(materialDispoDataItem.getType()).isEqualTo(tableRow.getType());
			assertThat(materialDispoDataItem.getBusinessCase()).isEqualTo(tableRow.getBusinessCase());
			assertThat(materialDispoDataItem.getMaterialDescriptor().getProductId()).isEqualTo(tableRow.getProductId().getRepoId());
			assertThat(materialDispoDataItem.getMaterialDescriptor().getQuantity()).isEqualByComparingTo(tableRow.getQty());
			assertThat(materialDispoDataItem.getAtp()).isEqualByComparingTo(tableRow.getAtp());

			materialDispoDataItemStepDefData.putIfMissing(tableRow.getIdentifier(), materialDispoDataItem);
		});
	}

	@And("metasfresh generates this MD_Candidate_Demand_Detail data")
	public void metasfresh_generates_this_MD_Candidate_Demand_Detail_data(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final String materialDispoItemIdentifier = tableRow.get(I_MD_Candidate_Demand_Detail.COLUMNNAME_MD_Candidate_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final MaterialDispoDataItem materialDispoDataItem = materialDispoDataItemStepDefData.get(materialDispoItemIdentifier);

			assertThat(materialDispoDataItem).isNotNull();    // add message

			final BusinessCaseDetail businessCaseDetail = materialDispoDataItem.getBusinessCaseDetail();
			assertThat(businessCaseDetail)
					.as("Missing BusinessCaseDetail of MaterialDispoDataItem %s", materialDispoDataItem.toString())
					.isNotNull();

			final String orderLineIdentifier = tableRow.get(I_C_OrderLine.COLUMNNAME_C_OrderLine_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final BigDecimal plannedQty = DataTableUtil.extractBigDecimalForColumnName(tableRow, I_MD_Candidate_Demand_Detail.COLUMNNAME_PlannedQty);
			final int orderLineId = StepDefUtil.extractId(orderLineIdentifier, orderLineTable);

			assertThat(DemandDetail.cast(businessCaseDetail).getOrderLineId()).isEqualTo(orderLineId);
			assertThat(DemandDetail.cast(businessCaseDetail).getQty()).isEqualByComparingTo(plannedQty);
			assertThat(DemandDetail.cast(businessCaseDetail).getShipmentScheduleId()).isGreaterThan(0);
		}
	}

	@Then("metasfresh has this MD_Candidate_Demand_Detail data")
	public void metasfresh_has_this_md_candidate_demand_detail_data(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final String materialDispoItemIdentifier = tableRow.get(I_MD_Candidate_Demand_Detail.COLUMNNAME_MD_Candidate_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final MaterialDispoDataItem materialDispoDataItem = materialDispoDataItemStepDefData.get(materialDispoItemIdentifier);
			final BusinessCaseDetail businessCaseDetail = materialDispoDataItem.getBusinessCaseDetail();

			assertThat(businessCaseDetail)
					.as("Missing BusinessCaseDetail of MaterialDispoDataItem %s", materialDispoDataItem.toString())
					.isNotNull();

			final int shipmentScheduleId = DataTableUtil.extractIntForColumnName(tableRow, I_MD_Candidate_Demand_Detail.COLUMNNAME_M_ShipmentSchedule_ID);
			assertThat(DemandDetail.cast(businessCaseDetail).getShipmentScheduleId()).isEqualTo(shipmentScheduleId);
		}
	}

	@And("metasfresh has this MD_Candidate_StockChange_Detail data")
	public void metasfresh_has_this_md_candidate_stockChange_detail_data(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		final SoftAssertions softly = new SoftAssertions();
		for (final Map<String, String> row : tableRows)
		{
			final String stockChangeDetailIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, I_MD_Candidate_StockChange_Detail.COLUMNNAME_MD_Candidate_StockChange_Detail_ID + ".Identifier");
			final String candidateIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, COLUMNNAME_MD_Candidate_ID + ".Identifier");
			final int freshQtyOnHandId = DataTableUtil.extractIntForColumnName(row, "Fresh_QtyOnHand_ID");
			final int freshQtyOnHandLineId = DataTableUtil.extractIntForColumnName(row, "Fresh_QtyOnHand_Line_ID");
			final boolean isReverted = DataTableUtil.extractBooleanForColumnName(row, "IsReverted");

			final I_MD_Candidate_StockChange_Detail stockChangeDetail = queryBL.createQueryBuilder(I_MD_Candidate_StockChange_Detail.class)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_MD_Candidate_StockChange_Detail.COLUMNNAME_Fresh_QtyOnHand_Line_ID, freshQtyOnHandLineId)
					.create()
					.firstOnly(I_MD_Candidate_StockChange_Detail.class);

			softly.assertThat(stockChangeDetail).as("MD_Candidate_StockChange_Detail with Fresh_QtyOnHand_Line_ID=%s", freshQtyOnHandLineId).isNotNull();
			softly.assertThat(stockChangeDetail.getFresh_QtyOnHand_ID()).as("MD_Candidate_StockChange_Detail with Fresh_QtyOnHand_Line_ID=%s - Fresh_QtyOnHand_ID", freshQtyOnHandLineId).isEqualTo(freshQtyOnHandId);
			softly.assertThat(stockChangeDetail.isReverted()).as("MD_Candidate_StockChange_Detail with Fresh_QtyOnHand_Line_ID=%s - isReverted", freshQtyOnHandLineId).isEqualTo(isReverted);

			if (candidateIdentifier != null)
			{
				final MaterialDispoDataItem materialDispoDataItem = materialDispoDataItemStepDefData.get(candidateIdentifier);
				softly.assertThat(materialDispoDataItem.getCandidateId().getRepoId()).as("MD_Candidate_StockChange_Detail with Fresh_QtyOnHand_Line_ID=%s - MD_Candidate_ID", freshQtyOnHandLineId).isEqualTo(stockChangeDetail.getMD_Candidate_ID());
			}
			if (stockChangeDetailIdentifier != null)
			{
				stockChangeDetailStepDefData.putOrReplace(stockChangeDetailIdentifier, stockChangeDetail);
			}
		}
		softly.assertAll();
	}

	@And("^metasfresh receives a (StockEstimateCreatedEvent|StockEstimateDeletedEvent)$")
	public void metasfresh_receives_StockEstimateEvent(@NonNull final String eventType, @NonNull final DataTable dataTable)
	{
		final Map<String, String> row = dataTable.asMaps().get(0);

		final String productIdentifier = DataTableUtil.extractStringForColumnName(row, "M_Product_ID");

		final int productId = productTable.getOptional(productIdentifier)
				.map(I_M_Product::getM_Product_ID)
				.orElseGet(() -> Integer.parseInt(productIdentifier));

		final int freshQtyOnHandId = DataTableUtil.extractIntForColumnName(row, "Fresh_QtyOnHand_ID");
		final int freshQtyOnHandLineId = DataTableUtil.extractIntForColumnName(row, "Fresh_QtyOnHand_Line_ID");
		final Instant dateDoc = DataTableUtil.extractInstantForColumnName(row, "DateDoc");
		final BigDecimal qty = DataTableUtil.extractBigDecimalForColumnName(row, "Qty");

		final AbstractStockEstimateEvent event;

		switch (eventType)
		{
			case StockEstimateCreatedEvent.TYPE:
				event = MaterialDispoUtils.createStockEstimateCreatedEvent(productId, freshQtyOnHandId, freshQtyOnHandLineId, dateDoc, qty);
				break;
			case StockEstimateDeletedEvent.TYPE:
				event = MaterialDispoUtils.createStockEstimateDeletedEvent(productId, freshQtyOnHandId, freshQtyOnHandLineId, dateDoc, qty);
				break;
			default:
				throw new AdempiereException("Event type not handled: " + eventType);
		}

		postMaterialEventService.enqueueEventNow(event);
	}

	@And("^after not more than (.*)s, metasfresh has no MD_Candidate for identifier (.*)$")
	public void metasfresh_has_no_md_candidate_for_identifier(final int timeoutSec, @NonNull final String identifier) throws InterruptedException
	{
		final MaterialDispoDataItem materialDispoDataItem = materialDispoDataItemStepDefData.get(identifier);

		final Supplier<Boolean> candidateWasDeleted = () -> MaterialDispoUtils.getCandidateRecordById(materialDispoDataItem.getCandidateId()) == null;

		StepDefUtil.tryAndWait(timeoutSec, 500, candidateWasDeleted);
	}

	@And("^after not more than (.*)s, metasfresh has no MD_Candidate_StockChange_Detail data for identifier (.*)$")
	public void metasfresh_has_no_md_candidate_stockChange_detail_for_identifier(final int timeoutSec, @NonNull final String identifier) throws InterruptedException
	{
		final TableRecordReference stockChangeDetail = stockChangeDetailStepDefData.getRecordDataItem(identifier)
				.getTableRecordReference();
		assertThat(stockChangeDetail).isNotNull();

		final Supplier<Boolean> candidateDetailWasDeleted = () ->
		{
			final I_MD_Candidate_StockChange_Detail stockChangeDetailFromDB = queryBL.createQueryBuilder(I_MD_Candidate_StockChange_Detail.class)
					.addEqualsFilter(I_MD_Candidate_StockChange_Detail.COLUMNNAME_MD_Candidate_StockChange_Detail_ID, stockChangeDetail.getRecord_ID())
					.orderBy(I_MD_Candidate_StockChange_Detail.COLUMNNAME_MD_Candidate_StockChange_Detail_ID)
					.create()
					.firstOnly(I_MD_Candidate_StockChange_Detail.class);
			return stockChangeDetailFromDB == null;
		};

		StepDefUtil.tryAndWait(timeoutSec, 500, candidateDetailWasDeleted);
	}

	@And("^after not more than (.*)s, the MD_Candidate table has only the following records$")
	public void validate_md_candidate_records(final int timeoutSec, @NonNull final MD_Candidate_StepDefTable table) throws Throwable
	{
		validate_md_candidates(timeoutSec, table);

		StepDefUtil.<Boolean>tryAndWaitForItem()
				.worker(() -> {
					final int expectedCandidatesCount = table.size();

					final List<I_MD_Candidate> actualCandidates = queryBL.createQueryBuilder(I_MD_Candidate.class)
							.addInArrayFilter(COLUMNNAME_M_Product_ID, table.getProductIds())
							.addNotEqualsFilter(COLUMNNAME_MD_Candidate_Type, CandidateType.STOCK) // skip STOCK candidates
							.orderBy(COLUMNNAME_MD_Candidate_ID)
							.create()
							.list();
					final int actualCandidatesCount = actualCandidates.size();

					if (actualCandidatesCount != expectedCandidatesCount)
					{
						return ProviderResult.resultWasNotFound(
								"Expected " + expectedCandidatesCount + " MD_Candidate(s) but found " + actualCandidatesCount + " MD_Candidate(s)"
										+ "\nActual MD_Candidate(s) - " + actualCandidatesCount + " records:"
										+ "\n" + toCandidatesTabularStringFromRecords(actualCandidates)
										+ "\nExpected Row(s) - " + expectedCandidatesCount + " rows:"
										+ "\n" + toTabularStringFromCandidateRows(table)
						);
					}

					return ProviderResult.resultWasFound(true);
				})
				.maxWaitSeconds(timeoutSec)
				.execute();
	}

	/**
	 * Waits (up to {@code timeoutSec} seconds) for each expected {@code MD_Candidate} row to appear, then
	 * asserts it against the given DataTable row.
	 * <p>
	 * Drains the {@code de.metas.material} RabbitMQ queue before validating, rather than leaving that as a
	 * standalone feature-file step (see {@code de.metas.cucumber/CLAUDE.md} rule 7). This consumer's
	 * {@link #tryAndWaitForCandidate} already matches a <em>complete</em> candidate row (type, business case,
	 * product, date, qty, ATP) that no later event revises, so draining first only adds a settled read on top
	 * of that poll - it never masks a real failure.
	 * <p>
	 * DataTable columns (one row per expected candidate):
	 * <ul>
	 * <li>{@code Identifier} - registers the matched candidate under this name for later steps</li>
	 * <li>{@code MD_Candidate_Type} - required; a {@link CandidateType} (e.g. {@code DEMAND}, {@code SUPPLY},
	 * {@code INVENTORY_UP})</li>
	 * <li>{@code OPT.MD_Candidate_BusinessCase} - a {@link CandidateBusinessCase} (e.g. {@code SHIPMENT},
	 * {@code PURCHASE}, {@code PRODUCTION}); omit for a plain {@code STOCK}/inventory candidate with no
	 * business case</li>
	 * <li>{@code M_Product_ID} - required; a product identifier</li>
	 * <li>{@code DateProjected} - required (or {@code OPT.DateProjected_LocalTimeZone} as a local-timezone
	 * alternative)</li>
	 * <li>{@code Qty} - required; the candidate's own quantity (sign is normalized internally for
	 * decreasing-stock candidate types, e.g. {@code DEMAND})</li>
	 * <li>{@code OPT.ATP} (or the older {@code OPT.Qty_AvailableToPromise}) - the running ATP on the
	 * candidate's STOCK parent/child; defaults to {@code 0} if neither is given</li>
	 * <li>{@code OPT.M_Warehouse_ID}, {@code OPT.M_AttributeSetInstance_ID}, {@code OPT.simulated} - optional
	 * refinements</li>
	 * <li>{@code OPT.DD_Order_Candidate_ID} / {@code DD_Order_ID} / {@code DD_OrderLine_ID}, and the
	 * {@code Forward_PP_*} / {@code PP_Order_Candidate_ID} family - optional distribution/production linkage
	 * columns</li>
	 * </ul>
	 * <p>
	 * Gherkin:
	 * <pre>
	 * {@code
	 * Then after not more than 60s, MD_Candidates are found
	 *   | Identifier | MD_Candidate_Type | MD_Candidate_BusinessCase | M_Product_ID | DateProjected        | Qty | ATP | M_Warehouse_ID |
	 *   | d_1        | DEMAND            | SHIPMENT                  | p_1          | 2024-09-21T21:00:00Z | -30 | 70  | WH_1           |
	 * }
	 * </pre>
	 */
	@And("^after not more than (.*)s, MD_Candidates are found$")
	public void validate_md_candidates(final int timeoutSec, @NonNull final MD_Candidate_StepDefTable table) throws Throwable
	{
		final Stopwatch stopwatch = Stopwatch.createStarted();

		rabbitMQStepDef.waitEmptyMaterialQueue();

		final HashMap<CandidateId, StepDefDataIdentifier> candidateIdsAlreadyMatched = new HashMap<>();
		table.forEach((row) -> {
			// make sure the given md_candidate has been created
			final MaterialDispoDataItem materialDispoRecord = tryAndWaitForCandidate(timeoutSec, row, candidateIdsAlreadyMatched);
			SharedTestContext.put("candidateId", materialDispoRecord.getCandidateId().getRepoId());
			SharedTestContext.put("materialDispoRecord", materialDispoRecord);

			validate_md_candidate(row, materialDispoRecord);

			materialDispoDataItemStepDefData.putOrReplace(row.getIdentifier(), materialDispoRecord);
			candidateIdsAlreadyMatched.put(materialDispoRecord.getCandidateId(), row.getIdentifier());
		});

		stopwatch.stop();
		logger.info("All candidates were created after {}", stopwatch);
	}

	private void validate_md_candidate(final MaterialDispoTableRow expected, final MaterialDispoDataItem actual)
	{
		newValidator().validate(expected, actual);
	}

	private MaterialDispoTableRowValidator newValidator()
	{
		return MaterialDispoTableRowValidator.builder()
				.tabularConverter(getTabularConverter())
				.materialDispoDataItemStepDefData(materialDispoDataItemStepDefData)
				.attributeSetInstanceTable(attributeSetInstanceTable)
				.ddOrderCandidateTable(ddOrderCandidateTable)
				.ddOrderTable(ddOrderTable)
				.ddOrderLineTable(ddOrderLineTable)
				.ppOrderCandidateTable(ppOrderCandidateTable)
				.ppOrderLineCandidateTable(ppOrderLineCandidateTable)
				.ppOrderTable(ppOrderTable)
				.ppOrderBOMLineTable(ppOrderBOMLineTable)
				.build();
	}

	@And("the following MD_Candidates are validated")
	public void validate_md_candidate_by_id(@NonNull final DataTable dataTable)
	{
		final long timeoutSec = 60L; //FIXME: add to stepdef
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(COLUMNNAME_MD_Candidate_ID)
				.forEach((row) -> validate_md_candidate_with_stock(row, timeoutSec));
	}

	@And("the following stock MD_Candidates are validated")
	public void validate_md_candidate_stock(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			validate_md_candidate_stock(tableRow);
		}
	}

	private MaterialDispoDataItem tryAndWaitForCandidate(
			final int timeoutSec,
			final @NonNull MaterialDispoTableRow row,
			final Map<CandidateId, StepDefDataIdentifier> candidateIdsToExclude) throws InterruptedException
	{

		final CandidatesQuery candidatesQuery = row.toCandidatesQuery();
		SharedTestContext.put("expected", () -> toCandidatesTabularFromRow(row).toTabularString());
		SharedTestContext.put("actual candidates", () -> toCandidatesTabularString(candidatesQuery));
		SharedTestContext.put("all actual candidates related to product", () -> toCandidatesTabularString(row.getProductId()));
		SharedTestContext.put("actual candidates query", candidatesQuery);
		SharedTestContext.put("actual candidates query SQL", () -> RepositoryCommons.mkQueryBuilder(candidatesQuery).create());

		return StepDefUtil.<MaterialDispoDataItem>tryAndWaitForItem()
				.worker(() -> retrieveMaterialDispoDataItem(row, candidateIdsToExclude))
				.maxWaitSeconds(timeoutSec)
				.checkingIntervalMs(1000L)
				.execute();
	}

	private ProviderResult<MaterialDispoDataItem> retrieveMaterialDispoDataItem(
			final @NonNull MaterialDispoTableRow row,
			final Map<CandidateId, StepDefDataIdentifier> candidateIdsToExclude)
	{
		final CandidatesQuery candidatesQuery = row.toCandidatesQuery();
		final ImmutableList<MaterialDispoDataItem> items = materialDispoRecordRepository.getAllBy(candidatesQuery);
		return newValidator().findValidItem(items, row, candidateIdsToExclude);
	}

	@And("post DeactivateAllSimulatedCandidatesEvent and wait for processing")
	public void deactivate_simulated_md_candidates()
	{
		final String traceId = UUID.randomUUID().toString();

		final ClientAndOrgId clientAndOrgId = ClientAndOrgId.ofClientAndOrg(Env.getClientId(), Env.getOrgId());

		postMaterialEventService.enqueueEventNow(DeactivateAllSimulatedCandidatesEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientOrgAndTraceId(clientAndOrgId, traceId))
				.build());

		materialEventObserver.awaitProcessing(traceId);
	}

	@And("delete all simulated candidates")
	public void delete_simulated_candidates()
	{
		simulatedCandidateService.deleteAllSimulatedCandidates();
	}

	@And("validate there is no simulated md_candidate")
	public void validate_no_simulated_md_candidate()
	{
		final int noOfRecords = queryBL.createQueryBuilder(I_MD_Candidate.class)
				.addEqualsFilter(I_MD_Candidate.COLUMNNAME_MD_Candidate_Status, X_MD_Candidate.MD_CANDIDATE_STATUS_Simulated)
				.create()
				.count();

		assertThat(noOfRecords).isZero();
	}

	private void validate_md_candidate_stock(@NonNull final Map<String, String> tableRow)
	{
		final String stockCandidateIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_MD_Candidate_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_MD_Candidate stockCandidateRecord = stockCandidateTable.get(stockCandidateIdentifier);

		final Candidate stockCandidate = candidateRepositoryRetrieval.retrieveLatestMatchOrNull(CandidatesQuery.fromId(CandidateId.ofRepoId(stockCandidateRecord.getMD_Candidate_ID())));
		assertThat(stockCandidate).isNotNull();

		final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_PP_Order_BOMLine.COLUMNNAME_M_Product_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_M_Product productRecord = productTable.get(productIdentifier);

		final String dateProjected = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_DateProjected);
		final BigDecimal qty = DataTableUtil.extractBigDecimalForColumnName(tableRow, COLUMNNAME_Qty);

		assertThat(stockCandidate.getType()).isEqualTo(CandidateType.STOCK);
		assertThat(stockCandidate.getMaterialDescriptor().getProductId()).isEqualTo(productRecord.getM_Product_ID());
		assertThat(stockCandidate.getMaterialDescriptor().getDate()).isEqualTo(dateProjected);
		assertThat(stockCandidate.getMaterialDescriptor().getQuantity()).isEqualByComparingTo(qty);
	}

	@SuppressWarnings("SameParameterValue")
	private void validate_md_candidate_with_stock(@NonNull final DataTableRow row, final long timeoutSec) throws InterruptedException
	{
		final StepDefDataIdentifier materialDispoDataIdentifier = row.getAsIdentifier();
		final CandidateBusinessCase businessCase = row.getAsOptionalEnum(COLUMNNAME_MD_Candidate_BusinessCase, CandidateBusinessCase.class).orElse(null);
		final ProductId productId = productTable.getId(row.getAsIdentifier(I_PP_Order_BOMLine.COLUMNNAME_M_Product_ID));
		final String dateProjected = row.getAsString(COLUMNNAME_DateProjected);
		final BigDecimal qty = row.getAsBigDecimal(COLUMNNAME_Qty);
		final BigDecimal atp = MD_Candidate_StepDefTableTransformer.extractATP(row);
		final CandidateType type = row.getAsEnum(COLUMNNAME_MD_Candidate_Type, CandidateType.class);

		final MaterialDispoDataItem materialDispoDataItem = getFreshMaterialDispoItem(materialDispoDataIdentifier, qty, timeoutSec);

		final ContextAwareDescription description = ContextAwareDescription.newInstance();
		description.put("materialDispoDataIdentifier", materialDispoDataIdentifier);
		description.put("MD_Candidate_ID", materialDispoDataItem.getCandidateId().getRepoId());
		description.put("materialDispoDataItem", materialDispoDataItem);

		final SoftAssertions softly = new SoftAssertions();
		if (businessCase == null)
		{
			softly.assertThat(materialDispoDataItem.getBusinessCase())
					.as(description.newWithMessage("businessCase"))
					.isNull();
		}
		else
		{
			softly.assertThat(materialDispoDataItem.getBusinessCase())
					.as(description.newWithMessage("businessCase"))
					.isEqualTo(businessCase);
		}

		softly.assertThat(materialDispoDataItem.getType())
				.as(description.newWithMessage("MD_Candidate_Type"))
				.isEqualTo(type);
		softly.assertThat(materialDispoDataItem.getMaterialDescriptor().getProductId())
				.as(description.newWithMessage("M_Product_ID"))
				.isEqualTo(productId.getRepoId());
		softly.assertThat(materialDispoDataItem.getMaterialDescriptor().getDate())
				.as(description.newWithMessage("DateProjected"))
				.isEqualTo(dateProjected);
		softly.assertThat(materialDispoDataItem.getMaterialDescriptor().getQuantity().abs())
				.as(description.newWithMessage("Qty.abs"))
				.isEqualByComparingTo(qty.abs()); // using .abs() because MaterialDispoDataItem qty is negated for demand and inventory_down
		softly.assertThat(materialDispoDataItem.getAtp())
				.as(description.newWithMessage("ATP"))
				.isEqualByComparingTo(atp);

		final AttributeSetInstanceId expectedAsiId = row.getAsOptionalIdentifier(COLUMNNAME_M_AttributeSetInstance_ID)
				.map(attributeSetInstanceTable::getId)
				.orElse(null);
		if (expectedAsiId != null)
		{
			final AttributesKey expectedAttributesKey = AttributesKeys.createAttributesKeyFromASIStorageAttributes(expectedAsiId).orElse(AttributesKey.NONE);

			final AttributeSetInstanceId materialCandASI = AttributeSetInstanceId.ofRepoId(materialDispoDataItem.getMaterialDescriptor().getAttributeSetInstanceId());
			final AttributesKey mdAttributesKeys = AttributesKeys.createAttributesKeyFromASIStorageAttributes(materialCandASI).orElse(AttributesKey.NONE);

			softly.assertThat(mdAttributesKeys)
					.as(description.newWithMessage("M_AttributeSetInstance_ID"))
					.isEqualTo(expectedAttributesKey);
		}

		softly.assertAll();
	}

	private MaterialDispoDataItem getFreshMaterialDispoItem(
			@NonNull final StepDefDataIdentifier materialDispoDataIdentifier,
			@Nullable final BigDecimal expectedQty,
			final long timeoutSec
	) throws InterruptedException
	{
		final MaterialDispoDataItem materialDispoDataItem = materialDispoDataItemStepDefData.get(materialDispoDataIdentifier);
		final CandidatesQuery candidatesQuery = CandidatesQuery.builder()
				.id(materialDispoDataItem.getCandidateId())
				.type(materialDispoDataItem.getType())
				.build();

		return StepDefUtil.tryAndWaitForItem(timeoutSec, 500, () -> {
			final MaterialDispoDataItem item = materialDispoRecordRepository.getBy(candidatesQuery);

			if (expectedQty != null)
			{
				final BigDecimal actualQty = item.getMaterialDescriptor().getQuantity();
				if (actualQty.abs().compareTo(expectedQty.abs()) != 0) // using .abs() because MaterialDispoDataItem qty is negated for demand and inventory_down
				{
					return ProviderResult.resultWasNotFound("Qty is not matching the expected quantity - expectedQty=" + expectedQty + ", actualQty=" + actualQty + ", item=" + item);
				}
			}

			System.out.println("Found " + item + " for " + candidatesQuery + ", expectedQty=" + expectedQty);
			return ProviderResult.resultWasFound(item);
		});
	}

	private void setAttributeSetInstance(
			@NonNull final I_MD_Candidate mdCandidateRecord,
			@NonNull final MaterialDispoTableRow tableRow)
	{
		if (tableRow.getAttributeSetInstanceId() != null)
		{
			final AttributeSetInstanceId attributeSetInstanceId = attributeSetInstanceTable.getId(tableRow.getAttributeSetInstanceId());

			mdCandidateRecord.setM_AttributeSetInstance_ID(attributeSetInstanceId.getRepoId());

			final AttributesKey storageAttributesKey = AttributesKeys.createAttributesKeyFromASIStorageAttributes(attributeSetInstanceId).orElse(AttributesKey.NONE);

			if (AttributesKey.NONE.equals(storageAttributesKey)
					|| AttributesKey.ALL.equals(storageAttributesKey)
					|| AttributesKey.OTHER.equals(storageAttributesKey))
			{
				// discard the given attributeSetInstanceId if it is not about a "real" ASI.
				mdCandidateRecord.setM_AttributeSetInstance_ID(AttributeSetInstanceId.NONE.getRepoId());
			}

			mdCandidateRecord.setStorageAttributesKey(storageAttributesKey.getAsString());
		}
		else
		{
			mdCandidateRecord.setM_AttributeSetInstance_ID(AttributeSetInstanceId.NONE.getRepoId());
			mdCandidateRecord.setStorageAttributesKey(AttributesKey.NONE.getAsString());
		}
	}

	private String toCandidatesTabularString(@NonNull final CandidatesQuery candidatesQuery)
	{
		final List<Candidate> candidates = materialDispoRecordRepository.getAllByQuery(candidatesQuery);
		return getTabularConverter().toTabularStringFromCandidates(candidates);
	}

	private String toCandidatesTabularString(@NonNull final ProductId productId)
	{
		final List<Candidate> candidates = materialDispoRecordRepository.getAllByProduct(productId);
		return getTabularConverter().toTabularStringFromCandidates(candidates);
	}

	private String toCandidatesTabularStringFromRecords(@NonNull final List<I_MD_Candidate> candidates)
	{
		return getTabularConverter().toTabularStringFromCandidateRecords(candidates);
	}

	private Table toCandidatesTabularFromRow(@NonNull final MaterialDispoTableRow tableRow)
	{
		return CandidatesToTabularStringConverter.toTable(tableRow);
	}

	private CandidatesToTabularStringConverter getTabularConverter()
	{
		return CandidatesToTabularStringConverter.builder()
				.materialDispoRecordRepository(materialDispoRecordRepository)
				.mdCandidateTable(stockCandidateTable)
				.productTable(productTable)
				.attributeSetInstanceTable(attributeSetInstanceTable)
				.build();
	}

	/**
	 * Executes the MD_Candidate_RemoveFromATP process for the specified candidate.
	 * This removes the candidate from ATP calculations by setting its qty to 0
	 * and recalculating subsequent ATP values.
	 *
	 * @param row DataTable row containing MD_Candidate_ID and optional process value
	 */
	@And("the MD_Candidate_Remove_From_ATP process is run")
	public void run_md_candidate_remove_from_atp_processes(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::run_md_candidate_remove_from_atp_process);
	}

	public void run_md_candidate_remove_from_atp_process(@NonNull final DataTableRow row)
	{
		final String value = row.getAsOptionalString(I_AD_Process.COLUMNNAME_Value)
				.orElse("MD_Candidate_RemoveFromATP");
		final AdProcessId processId = processDAO.retrieveProcessIdByValue(value);
		assertThat(processId).isNotNull();

		// Get the MD_Candidate_ID from either the MD_Candidate table or the MaterialDispoDataItem table
		final CandidateId candidateId;
		if (row.getAsOptionalIdentifier(COLUMNNAME_MD_Candidate_ID).isPresent())
		{
			// Check if it's in the MD_Candidate table first
			candidateId = row.getAsOptionalIdentifier(COLUMNNAME_MD_Candidate_ID)
					.flatMap(stockCandidateTable::getIdOptional)
					// Otherwise check the MaterialDispoDataItem table
					.orElseGet(() -> materialDispoDataItemStepDefData.get(row.getAsIdentifier(COLUMNNAME_MD_Candidate_ID)).getCandidateId());
		}
		else
		{
			throw new IllegalArgumentException("MD_Candidate_ID is required");
		}

		final TableRecordReference recordRef = TableRecordReference.of("MD_Candidate", candidateId.getRepoId());

		final ProcessInfo processInfo = ProcessInfo.builder()
				.setCtx(Env.getCtx())
				.setProcessCalledFrom(ProcessCalledFrom.Unknown)
				.setAD_Process_ID(processId)
				.setAD_PInstance(pInstanceDAO.createAD_PInstance(processId))
				.setReportLanguage(Language.getBaseLanguage())
				.setRecord(recordRef)
				.build();

		pInstanceDAO.saveProcessInfoOnly(processInfo);

		final ProcessExecutionResult result = ProcessExecutor.builder(processInfo)
				.executeSync()
				.getResult();
		assertThat(result).isNotNull();
		assertThat(result.isError()).isFalse();
	}

	/**
	 * Deletes the {@code MD_Candidate_Demand_Detail} row(s) of the given candidate, leaving its
	 * {@code M_ShipmentSchedule} referenced by no active candidate at all - simulating the drift the
	 * divergence report's uncovered-document check exists to find (e.g. a candidate purged by a cleanup job
	 * while its source document survives).
	 * <p>
	 * Gherkin: {@code the MD_Candidate_Demand_Detail of <candidateIdentifier> is deleted}
	 */
	@And("^the MD_Candidate_Demand_Detail of (.*) is deleted$")
	public void delete_md_candidate_demand_detail(@NonNull final String candidateIdentifier)
	{
		final CandidateId candidateId = materialDispoDataItemStepDefData.get(candidateIdentifier).getCandidateId();

		final List<I_MD_Candidate_Demand_Detail> detailRecords = queryBL.createQueryBuilder(I_MD_Candidate_Demand_Detail.class)
				.addEqualsFilter(I_MD_Candidate_Demand_Detail.COLUMNNAME_MD_Candidate_ID, candidateId.getRepoId())
				.create()
				.list();
		assertThat(detailRecords).as("MD_Candidate_Demand_Detail of %s", candidateIdentifier).isNotEmpty();

		detailRecords.forEach(InterfaceWrapperHelper::delete);
	}

	/**
	 * Sets the ATP baseline from MD_Stock: for the given product, posts one reset-stock
	 * {@link StockChangedEvent} per MD_Stock row,
	 * carrying that row's current QtyOnHand. Mirrors what
	 * StockDataUpdateRequestHandler.fireStockChangedEvent builds, but WITHOUT the old==new guard.
	 * <p>
	 * Note: <code>qtyOnHandOld</code> is the quantity the refresh found <em>stored</em> before it wrote the one the
	 * event now carries. It defaults to the very <code>MD_Stock.QtyOnHand</code> the event's <code>qtyOnHand</code>
	 * is taken from, because this step never changes <code>MD_Stock</code>: a scenario that says nothing therefore
	 * posts a refresh that found the stored quantity exactly where it was, and the event's physical movement
	 * (<code>qtyOnHand - qtyOnHandOld</code>) is genuinely zero. Use the optional <code>QtyOnHandOld</code> column
	 * to model a refresh that found the stored quantity off by that much - which is the only shape the reset-stock
	 * process actually emits, since it recomputes <code>MD_Stock</code> from the HUs and skips every key whose
	 * quantity did not move.
	 * <p>
	 * That difference is load-bearing, not cosmetic. Two consumers read it:
	 * <ul>
	 * <li><code>StockChangedEventHandler.computeQtyDifference</code>: for a chain that carries an unfulfilled
	 * <code>DEMAND</code>/<code>SUPPLY</code> at or before the event date, the created candidate's <b>type and
	 * quantity</b> are that difference - <code>INVENTORY_UP</code>/<code>INVENTORY_DOWN</code> of its absolute
	 * value, or no candidate at all when it is zero;
	 * <li>the <code>MovementQty</code> persisted on the resulting <code>MD_Candidate_Transaction_Detail</code> row;
	 * no scenario asserts that column, but do not rely on it.
	 * </ul>
	 * <p>
	 * Gherkin:
	 * <pre>
	 * When metasfresh receives a StockChangedEvent for the current MD_Stock
	 *   | M_Product_ID | OPT.ChangeDate       | OPT.QtyOnHandOld |
	 *   | p_od_1       | 2024-09-23T06:00:00Z | 150              |
	 * </pre>
	 */
	@And("^metasfresh receives a StockChangedEvent for the current MD_Stock$")
	public void metasfresh_receives_stock_changed_event(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::postStockChangedEventsForCurrentStock);
	}

	/**
	 * Posts one reset-stock {@link StockChangedEvent} per {@code MD_Stock} row of the row's product.
	 * See {@link #metasfresh_receives_stock_changed_event(DataTable)} for the {@code qtyOnHandOld} caveat.
	 */
	private void postStockChangedEventsForCurrentStock(@NonNull final DataTableRow row)
	{
		final StepDefDataIdentifier productIdentifier = row.getAsIdentifier(I_M_Product.COLUMNNAME_M_Product_ID);
		final int productId = productTable.get(productIdentifier).getM_Product_ID();

		final Instant changeDate = row.getAsOptionalInstant("ChangeDate").orElse(null);
		final BigDecimal qtyOnHandOldOverride = row.getAsOptionalBigDecimal("QtyOnHandOld").orElse(null);

		final List<I_MD_Stock> stockRecords = queryBL.createQueryBuilderOutOfTrx(I_MD_Stock.class)
				.addEqualsFilter(I_MD_Stock.COLUMNNAME_M_Product_ID, productId)
				.create()
				.list(I_MD_Stock.class);
		assertThat(stockRecords).as("MD_Stock rows for product %s", productIdentifier).isNotEmpty();

		for (final I_MD_Stock stockRecord : stockRecords)
		{
			final AttributesKey attributesKey = AttributesKeys.pruneEmptyParts(AttributesKey.ofString(stockRecord.getAttributesKey()));
			final AttributeSetInstanceId asiId = AttributesKeys.createAttributeSetInstanceFromAttributesKey(attributesKey);

			final StockChangedEvent event = StockChangedEvent.builder()
					.eventDescriptor(EventDescriptor.ofClientAndOrg(stockRecord.getAD_Client_ID(), stockRecord.getAD_Org_ID()))
					.productDescriptor(ProductDescriptor.forProductAndAttributes(productId, attributesKey, asiId.getRepoId()))
					.warehouseId(WarehouseId.ofRepoId(stockRecord.getM_Warehouse_ID()))
					.qtyOnHand(stockRecord.getQtyOnHand())
					// A ZERO default measured ATP 1200 instead of 170 on the open-demand-before-the-baseline scenario.
					.qtyOnHandOld(CoalesceUtil.coalesce(qtyOnHandOldOverride, stockRecord.getQtyOnHand()))
					.changeDate(changeDate)
					.stockChangeDetails(StockChangedEvent.StockChangeDetails.builder()
							.resetStockPInstanceId(ResetStockPInstanceId.ofRepoId(nextResetStockPInstanceRepoId()))
							.stockId(stockRecord.getMD_Stock_ID())
							.build())
					.build();

			postMaterialEventService.enqueueEventNow(event);
		}
	}

	/**
	 * Overwrites the running ATP of one candidate's STOCK record, to set up a chain that has drifted
	 * away from the physical stock.
	 * <p>
	 * Gherkin: {@code the ATP of the STOCK candidate of <candidateIdentifier> is manually set to <newAtp>}
	 */
	@And("^the ATP of the STOCK candidate of (.*) is manually set to (.*)$")
	public void set_stock_candidate_atp(@NonNull final String candidateIdentifier, @NonNull final String newAtpStr)
	{
		final BigDecimal newAtp = new BigDecimal(newAtpStr.trim());

		final CandidateId candidateId = materialDispoDataItemStepDefData.get(candidateIdentifier).getCandidateId();
		final I_MD_Candidate candidateRecord = InterfaceWrapperHelper.load(candidateId.getRepoId(), I_MD_Candidate.class);
		assertThat(candidateRecord).isNotNull();

		I_MD_Candidate stockRecord = null;

		final int parentId = candidateRecord.getMD_Candidate_Parent_ID();
		if (parentId > 0)
		{
			final I_MD_Candidate parentRecord = InterfaceWrapperHelper.load(parentId, I_MD_Candidate.class);
			if (parentRecord != null && CandidateType.STOCK.getCode().equals(parentRecord.getMD_Candidate_Type()))
			{
				stockRecord = parentRecord;
			}
		}
		if (stockRecord == null)
		{
			stockRecord = queryBL.createQueryBuilderOutOfTrx(I_MD_Candidate.class)
					.addEqualsFilter(I_MD_Candidate.COLUMNNAME_MD_Candidate_Parent_ID, candidateId.getRepoId())
					.addEqualsFilter(I_MD_Candidate.COLUMNNAME_MD_Candidate_Type, CandidateType.STOCK.getCode())
					.create()
					.firstOnly(I_MD_Candidate.class);
		}
		assertThat(stockRecord).as("STOCK candidate of %s", candidateIdentifier).isNotNull();

		stockRecord.setQty(newAtp);
		InterfaceWrapperHelper.save(stockRecord);
	}

	/**
	 * A UNIQUE synthetic reset-stock pinstance id per posted event. It must be unique: the engine looks
	 * its own MD_Candidate_Transaction_Detail up by AD_PInstance_ResetStock_ID, so a constant makes that
	 * lookup throw QueryMoreThanOneRecordsFound on the second event and the STOCK chain is never built.
	 */
	private static final AtomicInteger RESET_STOCK_PINSTANCE_SEQ =
			new AtomicInteger((int)(System.currentTimeMillis() / 1000L));

	private static int nextResetStockPInstanceRepoId()
	{
		return RESET_STOCK_PINSTANCE_SEQ.incrementAndGet();
	}
}
