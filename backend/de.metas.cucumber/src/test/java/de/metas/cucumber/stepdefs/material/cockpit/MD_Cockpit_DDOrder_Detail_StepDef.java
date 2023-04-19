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

package de.metas.cucumber.stepdefs.material.cockpit;

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.ItemProvider;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.distributionorder.DD_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.distribution.ddorder.DDOrderLineId;
import de.metas.logging.LogManager;
import de.metas.material.cockpit.CockpitId;
import de.metas.material.cockpit.model.I_MD_Cockpit;
import de.metas.material.cockpit.model.I_MD_Cockpit_DDOrder_Detail;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_M_Warehouse;
import org.eevolution.model.I_DD_OrderLine;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;

public class MD_Cockpit_DDOrder_Detail_StepDef
{
	private final static Logger logger = LogManager.getLogger(MD_Cockpit_DDOrder_Detail_StepDef.class);

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final DD_OrderLine_StepDefData ddOrderLineTable;
	private final M_Warehouse_StepDefData warehouseTable;
	private final MD_Cockpit_StepDefData cockpitTable;

	public MD_Cockpit_DDOrder_Detail_StepDef(
			@NonNull final DD_OrderLine_StepDefData ddOrderLineTable,
			@NonNull final M_Warehouse_StepDefData warehouseTable,
			@NonNull final MD_Cockpit_StepDefData cockpitTable)
	{
		this.ddOrderLineTable = ddOrderLineTable;
		this.warehouseTable = warehouseTable;
		this.cockpitTable = cockpitTable;
	}

	@And("^after not more than (.*)s, metasfresh has this MD_Cockpit_DDOrder_Detail data$")
	public void metasfresh_has_this_md_cockpit_ddOrder_Detail(
			final int timeoutSec,
			@NonNull final DataTable dataTable) throws InterruptedException
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			validateCockpitDDOrderDetail(timeoutSec, tableRow);
		}
	}

	private void validateCockpitDDOrderDetail(
			final int timeoutSec,
			@NonNull final Map<String, String> tableRow) throws InterruptedException
	{
		final SoftAssertions softly = new SoftAssertions();

		final String ddOrderLineIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_MD_Cockpit_DDOrder_Detail.COLUMNNAME_DD_OrderLine_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_DD_OrderLine ddOrderLineRecord = ddOrderLineTable.get(ddOrderLineIdentifier);
		softly.assertThat(ddOrderLineRecord).isNotNull();

		final String warehouseIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_MD_Cockpit_DDOrder_Detail.COLUMNNAME_M_Warehouse_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_M_Warehouse warehouseRecord = warehouseTable.get(warehouseIdentifier);
		softly.assertThat(warehouseRecord).isNotNull();

		final String cockpitIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_MD_Cockpit_DDOrder_Detail.COLUMNNAME_MD_Cockpit_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_MD_Cockpit cockpitRecord = cockpitTable.get(cockpitIdentifier);
		softly.assertThat(cockpitRecord).isNotNull();

		softly.assertAll();

		final CockpitDDOrderDetailQuery cockpitDDOrderDetailQuery = CockpitDDOrderDetailQuery.builder()
				.ddOrderLineId(DDOrderLineId.ofRepoId(ddOrderLineRecord.getDD_OrderLine_ID()))
				.warehouseId(WarehouseId.ofRepoId(warehouseRecord.getM_Warehouse_ID()))
				.cockpitId(CockpitId.ofRepoId(cockpitRecord.getMD_Cockpit_ID()))
				.build();

		final I_MD_Cockpit_DDOrder_Detail mdCockpitDDOrderDetailRecord = getCockpitDDOrderDetailBy(timeoutSec, cockpitDDOrderDetailQuery);

		final ItemProvider<I_MD_Cockpit_DDOrder_Detail> getValidCockpitDDOrderDetail = () -> {
			InterfaceWrapperHelper.refresh(mdCockpitDDOrderDetailRecord);

			return validateCockpitDDOrderDetailRecord(tableRow, mdCockpitDDOrderDetailRecord);
		};

		StepDefUtil.tryAndWaitForItem(timeoutSec, 500, getValidCockpitDDOrderDetail, () -> logCurrentContext(cockpitDDOrderDetailQuery));
	}

	@NonNull
	private I_MD_Cockpit_DDOrder_Detail getCockpitDDOrderDetailBy(
			final int timeoutSec,
			@NonNull final MD_Cockpit_DDOrder_Detail_StepDef.CockpitDDOrderDetailQuery cockpitDDOrderDetailQuery) throws InterruptedException
	{
		return StepDefUtil.tryAndWaitForItem(timeoutSec, 500, getCockpitDDOrderDetailSupplier(cockpitDDOrderDetailQuery), () -> logCurrentContext(cockpitDDOrderDetailQuery));
	}

	@NonNull
	private Supplier<Optional<I_MD_Cockpit_DDOrder_Detail>> getCockpitDDOrderDetailSupplier(
			@NonNull final MD_Cockpit_DDOrder_Detail_StepDef.CockpitDDOrderDetailQuery cockpitDDOrderDetailQuery)
	{
		return () -> queryBL.createQueryBuilder(I_MD_Cockpit_DDOrder_Detail.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MD_Cockpit_DDOrder_Detail.COLUMNNAME_DD_OrderLine_ID, cockpitDDOrderDetailQuery.getDdOrderLineId())
				.addEqualsFilter(I_MD_Cockpit_DDOrder_Detail.COLUMNNAME_M_Warehouse_ID, cockpitDDOrderDetailQuery.getWarehouseId())
				.addEqualsFilter(I_MD_Cockpit_DDOrder_Detail.COLUMNNAME_MD_Cockpit_ID, cockpitDDOrderDetailQuery.getCockpitId())
				.create()
				.firstOnlyOptional(I_MD_Cockpit_DDOrder_Detail.class);
	}

	private void logCurrentContext(@NonNull final MD_Cockpit_DDOrder_Detail_StepDef.CockpitDDOrderDetailQuery cockpitDDOrderDetailQuery)
	{
		final StringBuilder message = new StringBuilder();

		message.append("Looking for instance with:").append("\n")
				.append(I_MD_Cockpit_DDOrder_Detail.COLUMNNAME_DD_OrderLine_ID).append(" : ").append(cockpitDDOrderDetailQuery.getDdOrderLineId().getRepoId()).append("\n")
				.append(I_MD_Cockpit_DDOrder_Detail.COLUMNNAME_M_Warehouse_ID).append(" : ").append(cockpitDDOrderDetailQuery.getWarehouseId().getRepoId()).append("\n")
				.append(I_MD_Cockpit_DDOrder_Detail.COLUMNNAME_MD_Cockpit_ID).append(" : ").append(cockpitDDOrderDetailQuery.getCockpitId().getRepoId()).append("\n");

		message.append("MD_Cockpit_DDOrder_Detail records:").append("\n");

		queryBL.createQueryBuilder(I_MD_Cockpit_DDOrder_Detail.class)
				.create()
				.stream(I_MD_Cockpit_DDOrder_Detail.class)
				.forEach(cockpitDDOrderDetail -> message
						.append(I_MD_Cockpit_DDOrder_Detail.COLUMNNAME_MD_Cockpit_ID).append(" : ").append(cockpitDDOrderDetail.getMD_Cockpit_ID()).append(" ; ")
						.append(I_MD_Cockpit_DDOrder_Detail.COLUMNNAME_DD_OrderLine_ID).append(" : ").append(cockpitDDOrderDetail.getDD_OrderLine_ID()).append(" ; ")
						.append(I_MD_Cockpit_DDOrder_Detail.COLUMNNAME_M_Warehouse_ID).append(" : ").append(cockpitDDOrderDetail.getM_Warehouse_ID()).append(" ; ")
						.append(I_MD_Cockpit_DDOrder_Detail.COLUMNNAME_QtyPending).append(" : ").append(cockpitDDOrderDetail.getQtyPending()).append(" ; ")
						.append("\n"));

		logger.error("*** Error while looking for MD_Cockpit_DDOrder_Detail records, see current context: \n" + message);
	}

	@NonNull
	private ItemProvider.ProviderResult<I_MD_Cockpit_DDOrder_Detail> validateCockpitDDOrderDetailRecord(
			@NonNull final Map<String, String> tableRow,
			@NonNull final I_MD_Cockpit_DDOrder_Detail cockpitDDOrderDetailRecord)
	{
		final List<String> errorCollector = new ArrayList<>();

		final String identifier = DataTableUtil.extractStringForColumnName(tableRow, I_MD_Cockpit_DDOrder_Detail.COLUMNNAME_MD_Cockpit_DDOrder_Detail_ID + "." + TABLECOLUMN_IDENTIFIER);

		final BigDecimal qtyPending = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_MD_Cockpit_DDOrder_Detail.COLUMNNAME_QtyPending);
		if (qtyPending != null && qtyPending.compareTo(cockpitDDOrderDetailRecord.getQtyPending()) != 0)
		{
			errorCollector.add(MessageFormat.format("MD_Cockpit_DDOrder_Detail.Identifier={0}; Expecting QtyPending={1} but actual is {2}",
													identifier, qtyPending, cockpitDDOrderDetailRecord.getQtyPending()));
		}

		final String ddOrderDetailType = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_MD_Cockpit_DDOrder_Detail.COLUMNNAME_DDOrderDetailType);
		if (Check.isNotBlank(ddOrderDetailType) && !ddOrderDetailType.equals(cockpitDDOrderDetailRecord.getDDOrderDetailType()))
		{
			errorCollector.add(MessageFormat.format("MD_Cockpit_DDOrder_Detail.Identifier={0}; Expecting DDOrderDetailType={1} but actual is {2}",
													identifier, ddOrderDetailType, cockpitDDOrderDetailRecord.getDDOrderDetailType()));
		}

		if (errorCollector.size() > 0)
		{
			final String errorMessages = String.join(" && \n", errorCollector);
			return ItemProvider.ProviderResult.resultWasNotFound(errorMessages);
		}

		return ItemProvider.ProviderResult.resultWasFound(cockpitDDOrderDetailRecord);
	}

	@Builder
	@Value
	private static class CockpitDDOrderDetailQuery
	{
		@NonNull
		DDOrderLineId ddOrderLineId;

		@NonNull
		WarehouseId warehouseId;

		@NonNull
		CockpitId cockpitId;
	}
}
