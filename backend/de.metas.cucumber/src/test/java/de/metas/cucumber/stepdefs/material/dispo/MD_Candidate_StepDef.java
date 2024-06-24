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
import com.google.common.collect.ImmutableSet;
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
import de.metas.cucumber.stepdefs.material.dispo.MD_Candidate_StepDefTable.MaterialDispoTableRow;
import de.metas.i18n.BooleanWithReason;
import de.metas.logging.LogManager;
import de.metas.material.dispo.commons.SimulatedCandidateService;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateId;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.MaterialDispoDataItem;
import de.metas.material.dispo.commons.candidate.MaterialDispoRecordRepository;
import de.metas.material.dispo.commons.candidate.businesscase.BusinessCaseDetail;
import de.metas.material.dispo.commons.candidate.businesscase.DemandDetail;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.model.I_MD_Candidate_Demand_Detail;
import de.metas.material.dispo.model.I_MD_Candidate_StockChange_Detail;
import de.metas.material.dispo.model.X_MD_Candidate;
import de.metas.material.event.MaterialEventObserver;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.simulation.DeactivateAllSimulatedCandidatesEvent;
import de.metas.material.event.stockestimate.AbstractStockEstimateEvent;
import de.metas.material.event.stockestimate.StockEstimateCreatedEvent;
import de.metas.material.event.stockestimate.StockEstimateDeletedEvent;
import de.metas.organization.ClientAndOrgId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.keys.AttributesKeys;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.assertj.core.api.SoftAssertions;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static de.metas.material.dispo.model.I_MD_Candidate.COLUMNNAME_DateProjected;
import static de.metas.material.dispo.model.I_MD_Candidate.COLUMNNAME_MD_Candidate_BusinessCase;
import static de.metas.material.dispo.model.I_MD_Candidate.COLUMNNAME_MD_Candidate_ID;
import static de.metas.material.dispo.model.I_MD_Candidate.COLUMNNAME_MD_Candidate_Type;
import static de.metas.material.dispo.model.I_MD_Candidate.COLUMNNAME_M_AttributeSetInstance_ID;
import static de.metas.material.dispo.model.I_MD_Candidate.COLUMNNAME_M_Product_ID;
import static de.metas.material.dispo.model.I_MD_Candidate.COLUMNNAME_Qty;
import static de.metas.material.dispo.model.I_MD_Candidate.COLUMNNAME_Qty_AvailableToPromise;
import static org.assertj.core.api.Assertions.*;

public class MD_Candidate_StepDef
{
	private final static Logger logger = LogManager.getLogger(MD_Candidate_StepDef.class);

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final PostMaterialEventService postMaterialEventService;
	private final MaterialDispoRecordRepository materialDispoRecordRepository;
	private final CandidateRepositoryRetrieval candidateRepositoryRetrieval;
	private final MaterialEventObserver materialEventObserver;
	private final MaterialDispoDataItem_StepDefData materialDispoDataItemStepDefData;
	private final SimulatedCandidateService simulatedCandidateService;
	private final M_Product_StepDefData productTable;
	private final MD_Candidate_StepDefData stockCandidateTable;
	private final MD_Candidate_StockChange_Detail_StepDefData stockChangeDetailStepDefData;
	private final C_OrderLine_StepDefData orderLineTable;
	private final M_AttributeSetInstance_StepDefData attributeSetInstanceTable;

	public MD_Candidate_StepDef(
			@NonNull final MaterialDispoDataItem_StepDefData materialDispoDataItemStepDefData,
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final MD_Candidate_StepDefData stockCandidateTable,
			@NonNull final C_OrderLine_StepDefData orderLineTable,
			@NonNull final M_AttributeSetInstance_StepDefData attributeSetInstanceTable,
			@NonNull final MD_Candidate_StockChange_Detail_StepDefData stockChangeDetailStepDefData)
	{
		this.materialDispoDataItemStepDefData = materialDispoDataItemStepDefData;
		this.productTable = productTable;
		this.stockCandidateTable = stockCandidateTable;
		this.orderLineTable = orderLineTable;
		this.attributeSetInstanceTable = attributeSetInstanceTable;
		this.stockChangeDetailStepDefData = stockChangeDetailStepDefData;

		postMaterialEventService = SpringContextHolder.instance.getBean(PostMaterialEventService.class);
		materialDispoRecordRepository = SpringContextHolder.instance.getBean(MaterialDispoRecordRepository.class);
		candidateRepositoryRetrieval = SpringContextHolder.instance.getBean(CandidateRepositoryRetrieval.class);
		materialEventObserver = SpringContextHolder.instance.getBean(MaterialEventObserver.class);
		simulatedCandidateService = SpringContextHolder.instance.getBean(SimulatedCandidateService.class);
	}

	@When("metasfresh initially has this MD_Candidate data")
	public void metasfresh_has_this_md_candidate_data1(@NonNull final MD_Candidate_StepDefTable table) throws Throwable
	{
		table.forEach((tableRow) -> {
			final I_MD_Candidate mdCandidateRecord = InterfaceWrapperHelper.newInstance(I_MD_Candidate.class);
			mdCandidateRecord.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());
			mdCandidateRecord.setM_Product_ID(tableRow.getProductId().getRepoId());
			mdCandidateRecord.setM_Warehouse_ID(StepDefConstants.WAREHOUSE_ID.getRepoId());
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
			mdStockCandidateRecord.setM_Warehouse_ID(StepDefConstants.WAREHOUSE_ID.getRepoId());
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
			final MaterialDispoDataItem materialDispoRecord = materialDispoRecordRepository.getBy(tableRow.createQuery());
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
			final MaterialDispoDataItem materialDispoDataItem = materialDispoRecordRepository.getBy(tableRow.createQuery());

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
				throw new AdempiereException("Event type not handeled: " + eventType);
		}

		postMaterialEventService.postEventNow(event, null);
	}

	@And("metasfresh has no MD_Candidate for identifier {string}")
	public void metasfresh_has_no_md_cand_for_identifier(@NonNull final String identifier)
	{
		final MaterialDispoDataItem materialDispoDataItem = materialDispoDataItemStepDefData.get(identifier);
		final I_MD_Candidate candidateRecord = MaterialDispoUtils.getCandidateRecordById(materialDispoDataItem.getCandidateId());

		assertThat(candidateRecord).isNull();
	}

	@And("metasfresh has no MD_Candidate_StockChange_Detail data for identifier {string}")
	public void metasfresh_has_no_md_cand_stockChange_detail_for_identifier(@NonNull final String identifier)
	{
		final TableRecordReference stockChangeDetail = stockChangeDetailStepDefData.getRecordDataItem(identifier)
				.getTableRecordReference();
		assertThat(stockChangeDetail).isNotNull();
		final I_MD_Candidate_StockChange_Detail stockChangeDetailFromDB = queryBL.createQueryBuilder(I_MD_Candidate_StockChange_Detail.class)
				.addEqualsFilter(I_MD_Candidate_StockChange_Detail.COLUMNNAME_MD_Candidate_StockChange_Detail_ID, stockChangeDetail.getRecord_ID())
				.orderBy(I_MD_Candidate_StockChange_Detail.COLUMNNAME_MD_Candidate_StockChange_Detail_ID)
				.create()
				.firstOnly(I_MD_Candidate_StockChange_Detail.class);

		assertThat(stockChangeDetailFromDB).isNull();
	}

	@And("^after not more than (.*)s, the MD_Candidate table has only the following records$")
	public void validate_md_candidate_records(final int timeoutSec, @NonNull final MD_Candidate_StepDefTable table) throws Throwable
	{
		validate_md_candidates(timeoutSec, table);

		final ImmutableSet<ProductId> productIdSet = table.getProductIds();

		final int storedCandidatesSize = queryBL.createQueryBuilder(I_MD_Candidate.class)
				.addInArrayFilter(I_MD_Candidate.COLUMNNAME_M_Product_ID, productIdSet)
				.create()
				.count();

		// expected count is twice the number of rows bc we integrated the stock md_candidate as a column in step def
		final int expectedCandidateAndStocks = table.size() * 2;

		if (expectedCandidateAndStocks != storedCandidatesSize)
		{
			final StringBuilder message = new StringBuilder();
			message.append("Expected to find: ").append(expectedCandidateAndStocks)
					.append(" MD_Candidate records, but got: ").append(storedCandidatesSize)
					.append(" See:\n");

			logCandidateRecords(message, productIdSet, table);
		}

		assertThat(storedCandidatesSize).isEqualTo(expectedCandidateAndStocks);
	}

	@And("^after not more than (.*)s, MD_Candidates are found$")
	public void validate_md_candidates(
			final int timeoutSec,
			@NonNull final MD_Candidate_StepDefTable table) throws Throwable
	{
		final Stopwatch stopwatch = Stopwatch.createStarted();

		table.forEach((tableRow) -> {
			// make sure the given md_candidate has been created
			final MaterialDispoDataItem materialDispoRecord = tryAndWaitForCandidate(timeoutSec, tableRow);
			SharedTestContext.put("candidateId", materialDispoRecord.getCandidateId().getRepoId());
			SharedTestContext.put("materialDispoRecord", materialDispoRecord);

			final SoftAssertions softly = new SoftAssertions();
			softly.assertThat(materialDispoRecord.getType()).as("type").isEqualTo(tableRow.getType());
			softly.assertThat(materialDispoRecord.getBusinessCase()).as("businessCase").isEqualTo(tableRow.getBusinessCase());
			softly.assertThat(materialDispoRecord.getMaterialDescriptor().getProductId()).as("productId").isEqualTo(tableRow.getProductId().getRepoId());
			softly.assertThat(materialDispoRecord.getMaterialDescriptor().getDate()).as("date").isEqualTo(tableRow.getTime());
			softly.assertThat(materialDispoRecord.getMaterialDescriptor().getQuantity().abs()).as("quantity.abs").isEqualByComparingTo(tableRow.getQty().abs()); // using .abs() because MaterialDispoDataItem qty is negated for demand and inventory_down
			softly.assertThat(materialDispoRecord.getAtp()).as("atp").isEqualByComparingTo(tableRow.getAtp());
			softly.assertThat(materialDispoRecord.isSimulated()).isEqualTo(tableRow.isSimulated());

			final StepDefDataIdentifier attributeSetInstanceIdentifier = tableRow.getAttributeSetInstanceId();
			if (attributeSetInstanceIdentifier != null)
			{
				final AttributeSetInstanceId expectedAsiId = attributeSetInstanceTable.getId(attributeSetInstanceIdentifier);
				final AttributesKey expectedAttributesKey = AttributesKeys.createAttributesKeyFromASIStorageAttributes(expectedAsiId).orElse(AttributesKey.NONE);

				final AttributeSetInstanceId actualAsiId = AttributeSetInstanceId.ofRepoId(materialDispoRecord.getMaterialDescriptor().getAttributeSetInstanceId());
				final AttributesKey actualAttributesKeys = AttributesKeys.createAttributesKeyFromASIStorageAttributes(actualAsiId).orElse(AttributesKey.NONE);

				softly.assertThat(actualAttributesKeys).as("asi").isEqualTo(expectedAttributesKey);
			}

			final WarehouseId warehouseId = tableRow.getWarehouseId();
			if (warehouseId != null)
			{
				softly.assertThat(materialDispoRecord.getMaterialDescriptor().getWarehouseId()).as("warehouseId").isEqualTo(tableRow.getWarehouseId());
			}

			softly.assertAll();

			materialDispoDataItemStepDefData.putOrReplace(tableRow.getIdentifier(), materialDispoRecord);
		});

		stopwatch.stop();
		logger.info("All candidates were created after {}", stopwatch);
	}

	@And("the following MD_Candidates are validated")
	public void validate_md_candidate_by_id(@NonNull final DataTable dataTable)
	{
		final long timeoutSec = 60L; //FIXME: add to stepdef
		DataTableRows.of(dataTable).forEach((row) -> validate_md_candidate_with_stock(row, timeoutSec));
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
			final @NonNull MaterialDispoTableRow row) throws InterruptedException
	{
		final CandidatesQuery candidatesQuery = row.createQuery();
		SharedTestContext.put("candidatesQuery", candidatesQuery);
		SharedTestContext.put("query result candidates", () -> materialDispoRecordRepository.getAllByQueryAsString(candidatesQuery));
		SharedTestContext.put("all product related candidates", () -> materialDispoRecordRepository.getAllAsString(row.getProductId()));

		return StepDefUtil.tryAndWaitForItem(
				timeoutSec,
				1000,
				() -> retrieveMaterialDispoDataItem(row)
		);
	}

	private ProviderResult<MaterialDispoDataItem> retrieveMaterialDispoDataItem(final @NonNull MaterialDispoTableRow tableRow)
	{
		final CandidatesQuery candidatesQuery = tableRow.createQuery();

		final StringBuilder resultNotFoundLog = new StringBuilder();
		final ImmutableList<MaterialDispoDataItem> items = materialDispoRecordRepository.getAllBy(candidatesQuery);
		if (items.isEmpty())
		{
			resultNotFoundLog.append(candidatesQuery).append(" returned no results");
		}
		else
		{
			for (final MaterialDispoDataItem item : items)
			{
				//
				// Exclude this item if it's already associated to a different identifier.
				final StepDefDataIdentifier otherIdentifierOfCandidate = materialDispoDataItemStepDefData.getFirstIdentifierById(item.getCandidateId(), tableRow.getIdentifier()).orElse(null);
				if (otherIdentifierOfCandidate != null)
				{
					resultNotFoundLog.append("Excluded ").append(item.getCandidateId().getRepoId()).append(" because it was already loaded for ").append(otherIdentifierOfCandidate).append("\n");
					continue;
				}

				final BooleanWithReason matching = checkMatching(item, tableRow);
				if (matching.isFalse())
				{
					resultNotFoundLog.append(matching.getReasonAsString()).append("\n");
					continue;
				}

				return ProviderResult.resultWasFound(item);
			}
		}

		return ProviderResult.resultWasNotFound(resultNotFoundLog.toString());
	}

	private BooleanWithReason checkMatching(final MaterialDispoDataItem item, final @NonNull MaterialDispoTableRow tableRow)
	{
		if (item.getMaterialDescriptor().getQuantity().abs().compareTo(tableRow.getQty().abs()) != 0) // using .abs() because MaterialDispoDataItem qty is negated for demand and inventory_down
		{
			return BooleanWithReason.falseBecause("item with id=" + item.getCandidateId().getRepoId()
					+ " does not match tableRow with Identifier " + tableRow.getIdentifier()
					+ " because the qty values are different"
					+ " Expected=" + tableRow.getQty().abs() + " (abs), Actual= " + item.getMaterialDescriptor().getQuantity().abs() + " (abs)"
			);
		}
		if (item.getAtp().compareTo(tableRow.getAtp()) != 0)
		{
			return BooleanWithReason.falseBecause("item with id=" + item.getCandidateId().getRepoId()
					+ " does not match tableRow with Identifier " + tableRow.getIdentifier()
					+ " because the atp values are different"
					+ " Expected=" + tableRow.getAtp() + ", Actual= " + item.getAtp()
			);
		}
		if (!item.getMaterialDescriptor().getDate().equals(tableRow.getTime()))
		{
			return BooleanWithReason.falseBecause("item with id=" + item.getCandidateId().getRepoId()
					+ " does not match tableRow with Identifier " + tableRow.getIdentifier()
					+ " because the time (resp. materialDecription.date) values are different"
					+ " Expected=" + tableRow.getTime() + ", Actual= " + item.getMaterialDescriptor().getDate()
			);
		}
		if (!Objects.equals(item.getBusinessCase(), tableRow.getBusinessCase()))
		{
			return BooleanWithReason.falseBecause("item with id=" + item.getCandidateId().getRepoId() + " does not match tableRow with Identifier " + tableRow.getIdentifier() + " because the business case values are different");
		}

		return BooleanWithReason.TRUE;
	}

	@And("post DeactivateAllSimulatedCandidatesEvent and wait for processing")
	public void deactivate_simulated_md_candidates()
	{
		final String traceId = UUID.randomUUID().toString();

		final ClientAndOrgId clientAndOrgId = ClientAndOrgId.ofClientAndOrg(Env.getClientId(), Env.getOrgId());

		postMaterialEventService.postEventNow(DeactivateAllSimulatedCandidatesEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientOrgAndTraceId(clientAndOrgId, traceId))
				.build(), null);

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
		final StepDefDataIdentifier materialDispoDataIdentifier = row.getAsIdentifier(COLUMNNAME_MD_Candidate_ID);
		final CandidateBusinessCase businessCase = row.getAsOptionalEnum(COLUMNNAME_MD_Candidate_BusinessCase, CandidateBusinessCase.class).orElse(null);
		final ProductId productId = productTable.getId(row.getAsIdentifier(I_PP_Order_BOMLine.COLUMNNAME_M_Product_ID));
		final String dateProjected = row.getAsString(COLUMNNAME_DateProjected);
		final BigDecimal qty = row.getAsBigDecimal(COLUMNNAME_Qty);
		final BigDecimal atp = row.getAsBigDecimal(COLUMNNAME_Qty_AvailableToPromise);
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
				.as(description.newWithMessage("Qty_AvailableToPromise"))
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

	private void logCandidateRecords(
			@NonNull final StringBuilder message,
			@NonNull final ImmutableSet<ProductId> productIds,
			@NonNull final MD_Candidate_StepDefTable table)
	{
		message.append("MD_Candidate records:").append("\n");

		final String mdCandidateRecordsStr = queryBL.createQueryBuilder(I_MD_Candidate.class)
				.addInArrayFilter(COLUMNNAME_M_Product_ID, productIds)
				.create()
				.stream(I_MD_Candidate.class)
				.map(MD_Candidate_StepDef::toString)
				.collect(Collectors.joining("\n"));

		final String rowsStr = table.stream()
				.map(MaterialDispoTableRow::toString)
				.collect(Collectors.joining("\n"));

		logger.error("*** Error while looking for MD_Candidate records, see current context:"
				+ "\nMD_Candidate records:"
				+ "\n" + mdCandidateRecordsStr
				+ "\nRows:"
				+ "\n" + rowsStr);
	}

	private static String toString(I_MD_Candidate candidateRecord)
	{
		return COLUMNNAME_MD_Candidate_ID + " : " + candidateRecord.getMD_Candidate_ID() + " ; "
				+ COLUMNNAME_MD_Candidate_Type + " : " + candidateRecord.getMD_Candidate_Type() + " ; "
				+ COLUMNNAME_M_Product_ID + " : " + candidateRecord.getM_Product_ID() + " ; "
				+ COLUMNNAME_DateProjected + " : " + candidateRecord.getDateProjected() + " ; "
				+ COLUMNNAME_Qty + " : " + candidateRecord.getQty() + " ; "
				+ COLUMNNAME_Qty_AvailableToPromise + " : " + candidateRecord.getQty_AvailableToPromise() + " ; "
				+ COLUMNNAME_MD_Candidate_BusinessCase + " : " + candidateRecord.getMD_Candidate_BusinessCase() + " ; ";
	}
}