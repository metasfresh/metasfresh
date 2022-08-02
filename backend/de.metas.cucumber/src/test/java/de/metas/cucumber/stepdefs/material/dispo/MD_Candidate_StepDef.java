/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2020 metas GmbH
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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.cucumber.stepdefs.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.ItemProvider;
import de.metas.cucumber.stepdefs.ItemProvider.ProviderResult;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefData.RecordDataItem;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.attribute.M_AttributeSetInstance_StepDefData;
import de.metas.cucumber.stepdefs.material.dispo.MD_Candidate_StepDefTable.MaterialDispoTableRow;
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
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.model.I_MD_Candidate_Demand_Detail;
import de.metas.material.dispo.model.I_MD_Candidate_StockChange_Detail;
import de.metas.material.dispo.model.X_MD_Candidate;
import de.metas.material.event.MaterialEventObserver;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.OrderLineDescriptor;
import de.metas.material.event.shipmentschedule.ShipmentScheduleCreatedEvent;
import de.metas.material.event.simulation.DeactivateAllSimulatedCandidatesEvent;
import de.metas.material.event.stockestimate.AbstractStockEstimateEvent;
import de.metas.material.event.stockestimate.StockEstimateCreatedEvent;
import de.metas.material.event.stockestimate.StockEstimateDeletedEvent;
import de.metas.organization.ClientAndOrgId;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.AttributesKeys;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Product;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.material.dispo.model.I_MD_Candidate.COLUMNNAME_DateProjected;
import static de.metas.material.dispo.model.I_MD_Candidate.COLUMNNAME_MD_Candidate_BusinessCase;
import static de.metas.material.dispo.model.I_MD_Candidate.COLUMNNAME_MD_Candidate_ID;
import static de.metas.material.dispo.model.I_MD_Candidate.COLUMNNAME_MD_Candidate_Type;
import static de.metas.material.dispo.model.I_MD_Candidate.COLUMNNAME_M_Product_ID;
import static de.metas.material.dispo.model.I_MD_Candidate.COLUMNNAME_Qty;
import static de.metas.material.dispo.model.I_MD_Candidate.COLUMNNAME_Qty_AvailableToPromise;
import static org.assertj.core.api.Assertions.*;
import static org.eevolution.model.I_PP_Product_Planning.COLUMNNAME_M_AttributeSetInstance_ID;

public class MD_Candidate_StepDef
{
	private final static transient Logger logger = LogManager.getLogger(MD_Candidate_StepDef.class);

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private PostMaterialEventService postMaterialEventService;
	private MaterialDispoRecordRepository materialDispoRecordRepository;
	private CandidateRepositoryRetrieval candidateRepositoryRetrieval;
	private CandidateRepositoryWriteService candidateWriteService;
	private MaterialEventObserver materialEventObserver;
	private final MaterialDispoDataItem_StepDefData materialDispoDataItemStepDefData;
	private SimulatedCandidateService simulatedCandidateService;
	private final M_Product_StepDefData productTable;
	private final MD_Candidate_StepDefData stockCandidateTable;
	private final C_OrderLine_StepDefData orderLineTable;
	private final M_AttributeSetInstance_StepDefData attributeSetInstanceTable;

	public MD_Candidate_StepDef(
			@NonNull final MaterialDispoDataItem_StepDefData materialDispoDataItemStepDefData,
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final MD_Candidate_StepDefData stockCandidateTable,
			@NonNull final C_OrderLine_StepDefData orderLineTable,
			@NonNull final M_AttributeSetInstance_StepDefData attributeSetInstanceTable)
	{
		this.materialDispoDataItemStepDefData = materialDispoDataItemStepDefData;
		this.productTable = productTable;
		this.stockCandidateTable = stockCandidateTable;
		this.orderLineTable = orderLineTable;
		this.attributeSetInstanceTable = attributeSetInstanceTable;
	}

	@Before
	public void beforeEach()
	{
		postMaterialEventService = SpringContextHolder.instance.getBean(PostMaterialEventService.class);
		materialDispoRecordRepository = SpringContextHolder.instance.getBean(MaterialDispoRecordRepository.class);
		candidateRepositoryRetrieval = SpringContextHolder.instance.getBean(CandidateRepositoryRetrieval.class);
		candidateWriteService = SpringContextHolder.instance.getBean(CandidateRepositoryWriteService.class);
		materialEventObserver = SpringContextHolder.instance.getBean(MaterialEventObserver.class);
		simulatedCandidateService = SpringContextHolder.instance.getBean(SimulatedCandidateService.class);
		Env.setClientId(Env.getCtx(), ClientId.METASFRESH);
	}

	@Given("metasfresh initially has no MD_Candidate data")
	public void setupMD_Candidate_Data()
	{
		truncateMDCandidateData();
	}

	private void truncateMDCandidateData()
	{
		DB.executeUpdateAndThrowExceptionOnFail("TRUNCATE TABLE MD_Candidate cascade", ITrx.TRXNAME_None);
	}

	@And("metasfresh initially has no MD_Candidate_StockChange_detail data")
	public void setupMD_Candidate_StockChange_detail_Data()
	{
		DB.executeUpdateAndThrowExceptionOnFail("TRUNCATE TABLE md_candidate_stockChange_detail cascade", ITrx.TRXNAME_None);
	}

	@When("metasfresh receives a ShipmentScheduleCreatedEvent")
	public void shipmentScheduleCreatedEvent(@NonNull final DataTable dataTable)
	{
		final Map<String, String> map = dataTable.asMaps().get(0);

		final int shipmentScheduleId = Integer.parseInt(map.get("M_ShipmentSchedule_ID"));
		final int productId = Integer.parseInt(map.get("M_Product_ID"));
		final Instant preparationDate = Instant.parse(map.get("PreparationDate"));
		final BigDecimal qty = new BigDecimal(map.get("Qty"));

		final MaterialDescriptor descriptor = MaterialDispoUtils.createMaterialDescriptor(productId, preparationDate, qty);

		final ShipmentScheduleCreatedEvent shipmentScheduleCreatedEvent = ShipmentScheduleCreatedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(ClientId.METASFRESH.getRepoId(), StepDefConstants.ORG_ID.getRepoId()))
				.materialDescriptor(descriptor)
				.shipmentScheduleId(shipmentScheduleId)
				.reservedQuantity(qty)
				.documentLineDescriptor(OrderLineDescriptor.builder().orderId(10).orderLineId(20).docTypeId(30).orderBPartnerId(40).build())
				.build();

		postMaterialEventService.enqueueEventNow(shipmentScheduleCreatedEvent);
	}

	@When("metasfresh initially has this MD_Candidate data")
	public void metasfresh_has_this_md_candidate_data1(@NonNull final MD_Candidate_StepDefTable table)
	{
		truncateMDCandidateData();
		for (final MaterialDispoTableRow tableRow : table.getRows())
		{
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
		}
	}

	@Then("metasfresh has this MD_Candidate data")
	public void metasfresh_has_this_md_candidate_data(@NonNull final MD_Candidate_StepDefTable table)
	{
		for (final MaterialDispoTableRow tableRow : table.getRows())
		{
			final MaterialDispoDataItem materialDispoRecord = materialDispoRecordRepository.getBy(tableRow.createQuery());
			assertThat(materialDispoRecord).isNotNull(); // add message

			assertThat(materialDispoRecord.getType()).isEqualTo(tableRow.getType());
			assertThat(materialDispoRecord.getBusinessCase()).isEqualTo(tableRow.getBusinessCase());
			assertThat(materialDispoRecord.getMaterialDescriptor().getProductId()).isEqualTo(tableRow.getProductId().getRepoId());
			assertThat(materialDispoRecord.getMaterialDescriptor().getDate()).isEqualTo(tableRow.getTime());
			assertThat(materialDispoRecord.getMaterialDescriptor().getQuantity()).isEqualByComparingTo(tableRow.getQty());
			assertThat(materialDispoRecord.getAtp()).isEqualByComparingTo(tableRow.getAtp());

			materialDispoDataItemStepDefData.putIfMissing(tableRow.getIdentifier(), materialDispoRecord);
		}
	}

	@Then("^after not more than (.*)s, metasfresh has this MD_Candidate data$")
	public void metasfresh_has_this_md_candidate_data(final int timeoutSec, @NonNull final MD_Candidate_StepDefTable table) throws InterruptedException
	{
		final Supplier<Boolean> mdCandidateDemandDetailRecordsCounterChecker = () ->
				queryBL.createQueryBuilderOutOfTrx(I_MD_Candidate_Demand_Detail.class)
						.addOnlyActiveRecordsFilter()
						.create()
						.count() > 0;

		StepDefUtil.tryAndWait(timeoutSec, 500, mdCandidateDemandDetailRecordsCounterChecker);

		for (final MaterialDispoTableRow tableRow : table.getRows())
		{
			final MaterialDispoDataItem materialDispoDataItem = materialDispoRecordRepository.getBy(tableRow.createQuery());

			assertThat(materialDispoDataItem).isNotNull(); // add message
			assertThat(materialDispoDataItem.getType()).isEqualTo(tableRow.getType());
			assertThat(materialDispoDataItem.getBusinessCase()).isEqualTo(tableRow.getBusinessCase());
			assertThat(materialDispoDataItem.getMaterialDescriptor().getProductId()).isEqualTo(tableRow.getProductId().getRepoId());
			assertThat(materialDispoDataItem.getMaterialDescriptor().getQuantity()).isEqualByComparingTo(tableRow.getQty());
			assertThat(materialDispoDataItem.getAtp()).isEqualByComparingTo(tableRow.getAtp());

			materialDispoDataItemStepDefData.putIfMissing(tableRow.getIdentifier(), materialDispoDataItem);
		}
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
			assertThat(DemandDetail.cast(businessCaseDetail).getShipmentScheduleId()).isNotNull();
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
		for (final Map<String, String> row : tableRows)
		{
			final String candidateIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "MD_Candidate_ID.Identifier");
			final int freshQtyOnHandId = DataTableUtil.extractIntForColumnName(row, "Fresh_QtyOnHand_ID");
			final int freshQtyOnHandLineId = DataTableUtil.extractIntForColumnName(row, "Fresh_QtyOnHand_Line_ID");
			final boolean isReverted = DataTableUtil.extractBooleanForColumnName(row, "IsReverted");

			final I_MD_Candidate_StockChange_Detail stockChangeDetail = queryBL.createQueryBuilder(I_MD_Candidate_StockChange_Detail.class)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_MD_Candidate_StockChange_Detail.COLUMNNAME_Fresh_QtyOnHand_Line_ID, freshQtyOnHandLineId)
					.create()
					.firstOnly(I_MD_Candidate_StockChange_Detail.class);

			assertThat(stockChangeDetail).isNotNull();
			assertThat(stockChangeDetail.getFresh_QtyOnHand_ID()).isEqualTo(freshQtyOnHandId);
			assertThat(stockChangeDetail.isReverted()).isEqualTo(isReverted);

			if (candidateIdentifier != null)
			{
				final MaterialDispoDataItem materialDispoDataItem = materialDispoDataItemStepDefData.get(candidateIdentifier);
				assertThat(materialDispoDataItem.getCandidateId().getRepoId()).isEqualTo(stockChangeDetail.getMD_Candidate_ID());
			}
		}
	}

	@And("^metasfresh receives a (StockEstimateCreatedEvent|StockEstimateDeletedEvent)$")
	public void metasfresh_receives_StockEstimateEvent(@NonNull final String eventType, @NonNull final DataTable dataTable)
	{
		final Map<String, String> row = dataTable.asMaps().get(0);

		final int productId = DataTableUtil.extractIntForColumnName(row, "M_Product_ID");
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

		postMaterialEventService.enqueueEventNow(event);
	}

	@And("^after not more than (.*)s, metasfresh has no MD_Candidate for identifier (.*)$")
	public void metasfresh_has_no_md_cand_for_identifier(final int timeoutSec, @NonNull final String identifier) throws InterruptedException
	{
		final MaterialDispoDataItem materialDispoDataItem = materialDispoDataItemStepDefData.get(identifier);

		final Supplier<Boolean> candidateWasDeleted = () -> MaterialDispoUtils.getCandidateRecordById(materialDispoDataItem.getCandidateId()) == null;

		StepDefUtil.tryAndWait(timeoutSec, 500, candidateWasDeleted);
	}

	@And("^after not more than (.*)s, the MD_Candidate table has only the following records$")
	public void validate_md_candidate_records(final int timeoutSec, @NonNull final MD_Candidate_StepDefTable table) throws InterruptedException
	{
		validate_md_candidates(timeoutSec, table);

		final ImmutableSet<ProductId> productIdSet = table.getRows()
				.stream()
				.map(MaterialDispoTableRow::getProductId)
				.collect(ImmutableSet.toImmutableSet());

		final int storedCandidatesSize = queryBL.createQueryBuilder(I_MD_Candidate.class)
				.addInArrayFilter(I_MD_Candidate.COLUMNNAME_M_Product_ID, productIdSet)
				.create()
				.count();

		// expected count is twice the number of rows bc we integrated the stock md_candidate as a column in step def
		final int expectedCandidateAndStocks = table.getRows().size() * 2;

		if (expectedCandidateAndStocks != storedCandidatesSize)
		{
			final StringBuilder message = new StringBuilder();
			message.append("Expected to find: ").append(expectedCandidateAndStocks)
					.append(" MD_Candidate records, but got: ").append(storedCandidatesSize)
					.append(" See:\n");

			logCandidateRecords(message);
		}

		assertThat(storedCandidatesSize).isEqualTo(expectedCandidateAndStocks);
	}

	@And("^after not more than (.*)s, MD_Candidates are found$")
	public void validate_md_candidates(
			final int timeoutSec,
			@NonNull final MD_Candidate_StepDefTable table) throws InterruptedException
	{
		for (final MaterialDispoTableRow tableRow : table.getRows())
		{
			// make sure the given md_candidate has been created
			final MaterialDispoDataItem materialDispoRecord = tryAndWaitforCandidate(timeoutSec, tableRow);

			assertThat(materialDispoRecord).isNotNull();

			assertThat(materialDispoRecord.getType()).as("type of MD_Candidate_ID=%s", materialDispoRecord.getCandidateId().getRepoId()).isEqualTo(tableRow.getType());
			assertThat(materialDispoRecord.getBusinessCase()).as("businessCase of MD_Candidate_ID=%s", materialDispoRecord.getCandidateId().getRepoId()).isEqualTo(tableRow.getBusinessCase());
			assertThat(materialDispoRecord.getMaterialDescriptor().getProductId()).as("productId of MD_Candidate_ID=%s", materialDispoRecord.getCandidateId().getRepoId()).isEqualTo(tableRow.getProductId().getRepoId());
			assertThat(materialDispoRecord.getMaterialDescriptor().getDate()).as("date  of MD_Candidate_ID=%s", materialDispoRecord.getCandidateId().getRepoId()).isEqualTo(tableRow.getTime());
			assertThat(materialDispoRecord.getMaterialDescriptor().getQuantity().abs()).as("quantity of MD_Candidate_ID=%s", materialDispoRecord.getCandidateId().getRepoId()).isEqualByComparingTo(tableRow.getQty().abs()); // using .abs() because MaterialDispoDataItem qty is negated for demand and inventory_down
			assertThat(materialDispoRecord.getAtp()).as("atp of MD_Candidate_ID=%s", materialDispoRecord.getCandidateId().getRepoId()).isEqualByComparingTo(tableRow.getAtp());
			assertThat(materialDispoRecord.isSimulated()).isEqualTo(tableRow.isSimulated());

			final String attributeSetInstanceIdentifier = tableRow.getAttributeSetInstanceId();
			if (Check.isNotBlank(attributeSetInstanceIdentifier))
			{
				final I_M_AttributeSetInstance expectedASI = attributeSetInstanceTable.get(attributeSetInstanceIdentifier);
				assertThat(expectedASI).isNotNull();

				final AttributesKey expectedAttributesKey = AttributesKeys.createAttributesKeyFromASIStorageAttributes(AttributeSetInstanceId.ofRepoId(expectedASI.getM_AttributeSetInstance_ID()))
						.orElse(AttributesKey.NONE);

				final int materialCandASI = materialDispoRecord.getMaterialDescriptor().getAttributeSetInstanceId();
				final AttributesKey mdAttributesKeys = AttributesKeys.createAttributesKeyFromASIStorageAttributes(AttributeSetInstanceId.ofRepoId(materialCandASI))
						.orElse(AttributesKey.NONE);

				assertThat(mdAttributesKeys).isEqualTo(expectedAttributesKey);
			}

			materialDispoDataItemStepDefData.putOrReplace(tableRow.getIdentifier(), materialDispoRecord);
		}
	}

	@And("the following MD_Candidates are validated")
	public void validate_md_candidate_by_id(@NonNull final DataTable dataTable) throws InterruptedException
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			validate_md_candidate_with_stock(tableRow, 60L); //FIXME: add to stepdef
		}
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

	private MaterialDispoDataItem tryAndWaitforCandidate(
			final int timeoutSec,
			final @NonNull MaterialDispoTableRow tableRow) throws InterruptedException
	{
		final CandidatesQuery candidatesQuery = tableRow.createQuery();

		// The provider gets the matching items and then does some matching of its own.
		final ItemProvider<MaterialDispoDataItem> itemProvider = () -> {

			final StringBuilder sb = new StringBuilder();
			final ImmutableList<MaterialDispoDataItem> allByQuery = materialDispoRecordRepository.getAllBy(candidatesQuery);
			for (final MaterialDispoDataItem item : allByQuery)
			{
				if (item.getMaterialDescriptor().getQuantity().abs().compareTo(tableRow.getQty().abs()) != 0) // using .abs() because MaterialDispoDataItem qty is negated for demand and inventory_down
				{
					sb.append("item with id=" + item.getCandidateId().getRepoId() + " does not match tableRow with Identifier " + tableRow.getIdentifier() + " because the qty values are different\n");
					continue;
				}
				if (item.getAtp().compareTo(tableRow.getAtp()) != 0)
				{
					sb.append("item with id=" + item.getCandidateId().getRepoId() + " does not match tableRow with Identifier " + tableRow.getIdentifier() + " because the atp values are different\n");
					continue;
				}
				if (!item.getMaterialDescriptor().getDate().equals(tableRow.getTime()))
				{
					sb.append("item with id=" + item.getCandidateId().getRepoId() + " does not match tableRow with Identifier " + tableRow.getIdentifier() + " because the time (resp. materialDecription.date) values are different\n");
					continue;
				}

				if (!Objects.equals(item.getBusinessCase(), tableRow.getBusinessCase()))
				{
					sb.append("item with id=" + item.getCandidateId().getRepoId() + " does not match tableRow with Identifier " + tableRow.getIdentifier() + " because the business case values are different\n");
					continue;
				}
				return ProviderResult.resultWasFound(item);
			}
			return ProviderResult.resultWasNotFound(sb.toString());
		};

		final Runnable logContext = () -> logger.error("MD_Candidate not found\n"
															   + "**tableRow:**\n{}\n" + "**candidatesQuery:**\n{}\n"
															   + "**query result candidates:**\n{}\n"
															   + "**all candidates:**\n{}",
													   tableRow, candidatesQuery,
													   materialDispoRecordRepository.getAllByQueryAsString(candidatesQuery),
													   materialDispoRecordRepository.getAllAsString());

		final MaterialDispoDataItem materialDispoRecord = StepDefUtil
				.tryAndWaitForItem(timeoutSec, 1000,
								   itemProvider,
								   logContext);
		return materialDispoRecord;
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

		assertThat(noOfRecords).isEqualTo(0);
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

	private void validate_md_candidate_with_stock(
			@NonNull final Map<String, String> tableRow,
			final long timeoutSec) throws InterruptedException
	{
		final String materialDispoDataIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_MD_Candidate_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final RecordDataItem<MaterialDispoDataItem> materialDispoDataItem = materialDispoDataItemStepDefData.getRecordDataItem(materialDispoDataIdentifier);

		final CandidatesQuery candidatesQuery = CandidatesQuery.builder()
				.id(materialDispoDataItem.getRecord().getCandidateId())
				.type(materialDispoDataItem.getRecord().getType())
				.build();

		final ItemProvider<MaterialDispoDataItem> itemProvider = () -> {

			final MaterialDispoDataItem result = materialDispoRecordRepository.getBy(candidatesQuery);
			return ProviderResult.resultWasFound(result);
		};
		final MaterialDispoDataItem freshMaterialDispoItemInfo = StepDefUtil.tryAndWaitForItem(timeoutSec, 500, itemProvider);

		final String businessCase = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_MD_Candidate_BusinessCase);

		final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_PP_Order_BOMLine.COLUMNNAME_M_Product_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final I_M_Product productRecord = productTable.get(productIdentifier);

		final String dateProjected = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_DateProjected);
		final BigDecimal qty = DataTableUtil.extractBigDecimalForColumnName(tableRow, COLUMNNAME_Qty);
		final BigDecimal atp = DataTableUtil.extractBigDecimalForColumnName(tableRow, COLUMNNAME_Qty_AvailableToPromise);
		final String type = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_MD_Candidate_Type);

		if (businessCase == null)
		{
			assertThat(freshMaterialDispoItemInfo.getBusinessCase()).isNull();
		}
		else
		{
			assertThat(freshMaterialDispoItemInfo.getBusinessCase()).isNotNull();
			assertThat(freshMaterialDispoItemInfo.getBusinessCase().getCode()).as("MD_Candidate_BusinessCase for Identifier=%s; MD_Candidate_ID=%s", materialDispoDataIdentifier, freshMaterialDispoItemInfo.getCandidateId().getRepoId()).isEqualTo(businessCase);
		}

		assertThat(freshMaterialDispoItemInfo.getType().getCode()).as("MD_Candidate_Type for Identifier=%s; MD_Candidate_ID=%s", materialDispoDataIdentifier, freshMaterialDispoItemInfo.getCandidateId().getRepoId())
				.isEqualTo(type);
		assertThat(freshMaterialDispoItemInfo.getMaterialDescriptor().getProductId()).as("M_Product_ID for Identifier=%s; MD_Candidate_ID=%s", materialDispoDataIdentifier, freshMaterialDispoItemInfo.getCandidateId().getRepoId())
				.isEqualTo(productRecord.getM_Product_ID());
		assertThat(freshMaterialDispoItemInfo.getMaterialDescriptor().getDate()).as("DateProjected for Identifier=%s; MD_Candidate_ID=%s", materialDispoDataIdentifier, freshMaterialDispoItemInfo.getCandidateId().getRepoId())
				.isEqualTo(dateProjected);
		assertThat(freshMaterialDispoItemInfo.getMaterialDescriptor().getQuantity().abs()).as("Qty for Identifier=%s; MD_Candidate_ID=%s", materialDispoDataIdentifier, freshMaterialDispoItemInfo.getCandidateId().getRepoId())
				.isEqualByComparingTo(qty.abs()); // using .abs() because MaterialDispoDataItem qty is negated for demand and inventory_down
		assertThat(freshMaterialDispoItemInfo.getAtp()).as("Qty_AvailableToPromise for Identifier=%s; MD_Candidate_ID=%s", materialDispoDataIdentifier, freshMaterialDispoItemInfo.getCandidateId().getRepoId())
				.isEqualByComparingTo(atp);

		final String expectedASIIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_M_AttributeSetInstance_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(expectedASIIdentifier))
		{
			final I_M_AttributeSetInstance expectedASI = attributeSetInstanceTable.get(expectedASIIdentifier);
			assertThat(expectedASI).isNotNull();

			final AttributesKey expectedAttributesKey = AttributesKeys.createAttributesKeyFromASIStorageAttributes(AttributeSetInstanceId.ofRepoId(expectedASI.getM_AttributeSetInstance_ID()))
					.orElse(AttributesKey.NONE);

			final int materialCandASI = freshMaterialDispoItemInfo.getMaterialDescriptor().getAttributeSetInstanceId();
			final AttributesKey mdAttributesKeys = AttributesKeys.createAttributesKeyFromASIStorageAttributes(AttributeSetInstanceId.ofRepoId(materialCandASI))
					.orElse(AttributesKey.NONE);

			assertThat(mdAttributesKeys)
					.as("M_AttributeSetInstance_ID for Identifier=%s; MD_Candidate_ID=%s", materialDispoDataIdentifier, freshMaterialDispoItemInfo.getCandidateId().getRepoId())
					.isEqualTo(expectedAttributesKey);
		}
	}

	private void setAttributeSetInstance(
			@NonNull final I_MD_Candidate mdCandidateRecord,
			@NonNull final MaterialDispoTableRow tableRow)
	{
		if (Check.isNotBlank(tableRow.getAttributeSetInstanceId()))
		{
			final I_M_AttributeSetInstance attributeSetInstance = attributeSetInstanceTable.get(tableRow.getAttributeSetInstanceId());
			assertThat(attributeSetInstance).isNotNull();

			mdCandidateRecord.setM_AttributeSetInstance_ID(attributeSetInstance.getM_AttributeSetInstance_ID());

			final AttributesKey storageAttributesKey = AttributesKeys
					.createAttributesKeyFromASIStorageAttributes(AttributeSetInstanceId.ofRepoId(attributeSetInstance.getM_AttributeSetInstance_ID()))
					.orElse(AttributesKey.NONE);

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

	private void logCurrentContext(@NonNull final MaterialDispoTableRow tableRow)
	{
		final StringBuilder message = new StringBuilder();

		message.append("Looking for instance with:").append("\n")
				.append(COLUMNNAME_MD_Candidate_Type).append(" : ").append(tableRow.getType().getCode()).append("\n")
				.append(COLUMNNAME_M_Product_ID).append(" : ").append(tableRow.getProductId().getRepoId()).append("\n")
				.append(COLUMNNAME_DateProjected).append(" : ").append(tableRow.getTime()).append("\n")
				.append(COLUMNNAME_Qty).append(" : ").append(tableRow.getQty()).append("\n")
				.append(COLUMNNAME_Qty_AvailableToPromise).append(" : ").append(tableRow.getAtp()).append("\n")
				.append(COLUMNNAME_MD_Candidate_BusinessCase).append(" : ").append(tableRow.getBusinessCase()).append("\n");

		logCandidateRecords(message);
	}

	private void logCandidateRecords(@NonNull final StringBuilder message)
	{
		message.append("MD_Candidate records:").append("\n");

		queryBL.createQueryBuilder(I_MD_Candidate.class)
				.create()
				.stream(I_MD_Candidate.class)
				.forEach(candidateRecord -> message
						.append(COLUMNNAME_MD_Candidate_ID).append(" : ").append(candidateRecord.getMD_Candidate_ID()).append(" ; ")
						.append(COLUMNNAME_MD_Candidate_Type).append(" : ").append(candidateRecord.getMD_Candidate_Type()).append(" ; ")
						.append(COLUMNNAME_M_Product_ID).append(" : ").append(candidateRecord.getM_Product_ID()).append(" ; ")
						.append(COLUMNNAME_DateProjected).append(" : ").append(candidateRecord.getDateProjected()).append(" ; ")
						.append(COLUMNNAME_Qty).append(" : ").append(candidateRecord.getQty()).append(" ; ")
						.append(COLUMNNAME_Qty_AvailableToPromise).append(" : ").append(candidateRecord.getQty_AvailableToPromise()).append(" ; ")
						.append(COLUMNNAME_MD_Candidate_BusinessCase).append(" : ").append(candidateRecord.getMD_Candidate_BusinessCase()).append(" ; ")
						.append("\n"));

		logger.error("*** Error while looking for MD_Candidate records, see current context: \n" + message);
	}
}