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
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.ItemProvider;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.material.dispo.MD_Candidate_StepDefTable.MaterialDispoTableRow;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.MaterialDispoDataItem;
import de.metas.material.dispo.commons.candidate.MaterialDispoRecordRepository;
import de.metas.material.dispo.commons.candidate.businesscase.BusinessCaseDetail;
import de.metas.material.dispo.commons.candidate.businesscase.DemandDetail;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.model.I_MD_Candidate_Demand_Detail;
import de.metas.material.dispo.model.I_MD_Candidate_StockChange_Detail;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.OrderLineDescriptor;
import de.metas.material.event.shipmentschedule.ShipmentScheduleCreatedEvent;
import de.metas.material.event.stockestimate.AbstractStockEstimateEvent;
import de.metas.material.event.stockestimate.StockEstimateCreatedEvent;
import de.metas.material.event.stockestimate.StockEstimateDeletedEvent;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.SpringContextHolder;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.*;

public class MD_Candidate_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final PostMaterialEventService postMaterialEventService;
	private final MaterialDispoRecordRepository materialDispoRecordRepository;

	private final MaterialDispoDataItem_StepDefData materialDispoDataItemStepDefData;

	public MD_Candidate_StepDef(@NonNull final MaterialDispoDataItem_StepDefData materialDispoDataItemStepDefData)
	{
		this.materialDispoDataItemStepDefData = materialDispoDataItemStepDefData;
		this.postMaterialEventService = SpringContextHolder.instance.getBean(PostMaterialEventService.class);
		this.materialDispoRecordRepository = SpringContextHolder.instance.getBean(MaterialDispoRecordRepository.class);

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
			mdCandidateRecord.setM_AttributeSetInstance_ID(AttributeSetInstanceId.NONE.getRepoId());
			mdCandidateRecord.setStorageAttributesKey(AttributesKey.NONE.getAsString());
			mdCandidateRecord.setM_Warehouse_ID(StepDefConstants.WAREHOUSE_ID.getRepoId());
			mdCandidateRecord.setMD_Candidate_Type(tableRow.getType().getCode());
			mdCandidateRecord.setMD_Candidate_BusinessCase(CandidateBusinessCase.toCode(tableRow.getBusinessCase()));
			mdCandidateRecord.setQty(tableRow.getQty());
			mdCandidateRecord.setDateProjected(TimeUtil.asTimestamp(tableRow.getTime()));
			InterfaceWrapperHelper.saveRecord(mdCandidateRecord);

			final I_MD_Candidate mdStockCandidateRecord = InterfaceWrapperHelper.newInstance(I_MD_Candidate.class);
			mdStockCandidateRecord.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());
			mdStockCandidateRecord.setM_Product_ID(tableRow.getProductId().getRepoId());
			mdStockCandidateRecord.setM_AttributeSetInstance_ID(AttributeSetInstanceId.NONE.getRepoId());
			mdStockCandidateRecord.setStorageAttributesKey(AttributesKey.NONE.getAsString());
			mdStockCandidateRecord.setM_Warehouse_ID(StepDefConstants.WAREHOUSE_ID.getRepoId());
			mdStockCandidateRecord.setMD_Candidate_Type(CandidateType.STOCK.getCode());
			final boolean isDemand = CandidateType.DEMAND.equals(tableRow.getType()) || CandidateType.INVENTORY_DOWN.equals(tableRow.getType());
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

			materialDispoDataItemStepDefData.putOrReplace(tableRow.getIdentifier(), materialDispoRecord);
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
					sb.append("item with id=" + item.getCandidateId().getRepoId()
									  + " does not match tableRow with Identifier " + tableRow.getIdentifier()
									  + " because the qty values are different"
									  + " Expected=" + tableRow.getQty().abs() + " (abs), Actual= " + item.getMaterialDescriptor().getQuantity().abs() + " (abs)"
									  + "\n");
					continue;
				}
				if (item.getAtp().compareTo(tableRow.getAtp()) != 0)
				{
					sb.append("item with id=" + item.getCandidateId().getRepoId()
									  + " does not match tableRow with Identifier " + tableRow.getIdentifier()
									  + " because the atp values are different"
									  + " Expected=" + tableRow.getAtp() + ", Actual= " + item.getAtp()
									  + "\n");
					continue;
				}
				if (!item.getMaterialDescriptor().getDate().equals(tableRow.getTime()))
				{
					sb.append("item with id=" + item.getCandidateId().getRepoId()
									  + " does not match tableRow with Identifier " + tableRow.getIdentifier()
									  + " because the time (resp. materialDecription.date) values are different"
									  + " Expected=" + tableRow.getTime() + ", Actual= " + item.getMaterialDescriptor().getDate()
									  + "\n");
					continue;
				}

				if (!Objects.equals(item.getBusinessCase(), tableRow.getBusinessCase()))
				{
					sb.append("item with id=" + item.getCandidateId().getRepoId() + " does not match tableRow with Identifier " + tableRow.getIdentifier() + " because the business case values are different\n");
					continue;
				}
				return ItemProvider.ProviderResult.resultWasFound(item);
			}
			return ItemProvider.ProviderResult.resultWasNotFound(sb.toString());
		};

		final Supplier<String> contextSupplier = () -> {

			final StringBuilder context = new StringBuilder("MD_Candidate not found\n");
			context.append("**tableRow:** \n").append(tableRow).append("\n");
			context.append("**candidatesQuery:** \n").append(candidatesQuery).append("\n");
			context.append("**query result candidates:** \n").append(materialDispoRecordRepository.getAllByQueryAsString(candidatesQuery)).append("\n");
			context.append("**all product related candidates:** \n").append(materialDispoRecordRepository.getAllAsString(tableRow.getProductId()));

			return context.toString();
		};

		return StepDefUtil
				.tryAndWaitForItem(timeoutSec, 1000,
								   itemProvider,
								   contextSupplier);
	}
}
