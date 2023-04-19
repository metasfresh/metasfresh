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

import de.metas.cucumber.stepdefs.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.ItemProvider;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.logging.LogManager;
import de.metas.material.cockpit.CockpitId;
import de.metas.material.cockpit.model.I_MD_Cockpit;
import de.metas.material.cockpit.model.I_MD_Cockpit_DocumentDetail;
import de.metas.order.OrderLineId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_C_OrderLine;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;

public class MD_Cockpit_DocumentDetail_StepDef
{
	private final static Logger logger = LogManager.getLogger(MD_Cockpit_DocumentDetail_StepDef.class);

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final MD_Cockpit_StepDefData cockpitTable;
	private final C_OrderLine_StepDefData orderLineTable;

	public MD_Cockpit_DocumentDetail_StepDef(
			@NonNull final MD_Cockpit_StepDefData cockpitTable,
			@NonNull final C_OrderLine_StepDefData orderLineTable)
	{
		this.cockpitTable = cockpitTable;
		this.orderLineTable = orderLineTable;
	}

	@And("^after not more than (.*)s, metasfresh has this MD_Cockpit_DocumentDetail data$")
	public void metasfresh_has_this_md_cockpit_data(
			final int timeoutSec,
			@NonNull final DataTable dataTable) throws InterruptedException
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			validateCockpitDocumentDetailRecord(timeoutSec, tableRow);
		}
	}

	@And("metasfresh has no MD_Cockpit_DocumentDetail data")
	public void no_md_cockpit_documentDetail_data(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			final SoftAssertions softly = new SoftAssertions();

			final String cockpitIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_MD_Cockpit_DocumentDetail.COLUMNNAME_MD_Cockpit_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_MD_Cockpit cockpitRecord = cockpitTable.get(cockpitIdentifier);
			softly.assertThat(cockpitRecord).isNotNull();

			final String orderLineIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_MD_Cockpit_DocumentDetail.COLUMNNAME_C_OrderLine_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_OrderLine orderLineRecord = orderLineTable.get(orderLineIdentifier);
			softly.assertThat(orderLineRecord).isNotNull();

			final CockpitId cockpitId = CockpitId.ofRepoId(cockpitRecord.getMD_Cockpit_ID());
			final OrderLineId orderLineId = OrderLineId.ofRepoId(orderLineRecord.getC_OrderLine_ID());
			
			softly.assertThat(getCockpitDocumentDetailSupplier(cockpitId, orderLineId).get().isPresent()).isFalse();

			softly.assertAll();
		}
	}

	private void validateCockpitDocumentDetailRecord(
			final int timeoutSec,
			@NonNull final Map<String, String> tableRow) throws InterruptedException
	{
		final SoftAssertions softly = new SoftAssertions();

		final String cockpitIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_MD_Cockpit_DocumentDetail.COLUMNNAME_MD_Cockpit_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_MD_Cockpit cockpitRecord = cockpitTable.get(cockpitIdentifier);
		softly.assertThat(cockpitRecord).isNotNull();

		final String orderLineIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_MD_Cockpit_DocumentDetail.COLUMNNAME_C_OrderLine_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_OrderLine orderLineRecord = orderLineTable.get(orderLineIdentifier);
		softly.assertThat(orderLineRecord).isNotNull();

		softly.assertAll();
		
		final CockpitId cockpitId = CockpitId.ofRepoId(cockpitRecord.getMD_Cockpit_ID());
		final OrderLineId orderLineId = OrderLineId.ofRepoId(orderLineRecord.getC_OrderLine_ID());
		
		final I_MD_Cockpit_DocumentDetail mdCockpitDocumentDetailRecord = getCockpitDocumentDetailByCockpitIdAndOrderLineId(timeoutSec,
																															cockpitId,
																															orderLineId);

		final ItemProvider<I_MD_Cockpit_DocumentDetail> getValidCockpitDocumentDetail = () -> {
			InterfaceWrapperHelper.refresh(mdCockpitDocumentDetailRecord);

			return validateCockpitDocumentDetailRecord(tableRow, mdCockpitDocumentDetailRecord);
		};

		StepDefUtil.tryAndWaitForItem(timeoutSec, 500, getValidCockpitDocumentDetail, () -> logCurrentContext(cockpitId, orderLineId));
	}

	@NonNull
	private ItemProvider.ProviderResult<I_MD_Cockpit_DocumentDetail> validateCockpitDocumentDetailRecord(
			@NonNull final Map<String, String> tableRow,
			@NonNull final I_MD_Cockpit_DocumentDetail cockpitDocumentDetailRecord)
	{
		final List<String> errorCollector = new ArrayList<>();

		final String identifier = DataTableUtil.extractStringForColumnName(tableRow, I_MD_Cockpit_DocumentDetail.COLUMNNAME_MD_Cockpit_DocumentDetail_ID + "." + TABLECOLUMN_IDENTIFIER);

		final BigDecimal qtyOrdered = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_MD_Cockpit_DocumentDetail.COLUMNNAME_QtyOrdered);
		if (qtyOrdered != null && qtyOrdered.compareTo(cockpitDocumentDetailRecord.getQtyOrdered()) != 0)
		{
			errorCollector.add(MessageFormat.format("MD_Cockpit_DocumentDetail.Identifier={0}; Expecting QtyOrdered={1} but actual is {2}",
													identifier, qtyOrdered, cockpitDocumentDetailRecord.getQtyOrdered()));
		}

		final BigDecimal qtyReserved = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_MD_Cockpit_DocumentDetail.COLUMNNAME_QtyReserved);
		if (qtyReserved != null && qtyReserved.compareTo(cockpitDocumentDetailRecord.getQtyReserved()) != 0)
		{
			errorCollector.add(MessageFormat.format("MD_Cockpit_DocumentDetail.Identifier={0}; Expecting QtyReserved={1} but actual is {2}",
													identifier, qtyReserved, cockpitDocumentDetailRecord.getQtyReserved()));
		}

		if (errorCollector.size() > 0)
		{
			final String errorMessages = String.join(" && \n", errorCollector);
			return ItemProvider.ProviderResult.resultWasNotFound(errorMessages);
		}

		return ItemProvider.ProviderResult.resultWasFound(cockpitDocumentDetailRecord);
	}

	@NonNull
	private I_MD_Cockpit_DocumentDetail getCockpitDocumentDetailByCockpitIdAndOrderLineId(
			final int timeoutSec,
			@NonNull final CockpitId cockpitId,
			@NonNull final OrderLineId orderLineId) throws InterruptedException
	{
		return StepDefUtil.tryAndWaitForItem(timeoutSec, 500, getCockpitDocumentDetailSupplier(cockpitId, orderLineId), () -> logCurrentContext(cockpitId, orderLineId));
	}

	@NonNull
	private Supplier<Optional<I_MD_Cockpit_DocumentDetail>> getCockpitDocumentDetailSupplier(
			@NonNull final CockpitId cockpitId,
			@NonNull final OrderLineId orderLineId)
	{
		return () -> queryBL.createQueryBuilder(I_MD_Cockpit_DocumentDetail.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MD_Cockpit_DocumentDetail.COLUMNNAME_MD_Cockpit_ID, cockpitId)
				.addEqualsFilter(I_MD_Cockpit_DocumentDetail.COLUMNNAME_C_OrderLine_ID, orderLineId)
				.create()
				.firstOnlyOptional(I_MD_Cockpit_DocumentDetail.class);
	}

	private void logCurrentContext(
			@NonNull final CockpitId cockpitId,
			@NonNull final OrderLineId orderLineId)
	{
		final StringBuilder message = new StringBuilder();

		message.append("Looking for instance with:").append("\n")
				.append(I_MD_Cockpit_DocumentDetail.COLUMNNAME_MD_Cockpit_ID).append(" : ").append(cockpitId.getRepoId()).append("\n")
				.append(I_MD_Cockpit_DocumentDetail.COLUMNNAME_C_OrderLine_ID).append(" : ").append(orderLineId.getRepoId()).append("\n");

		message.append("MD_Cockpit_DocumentDetail records:").append("\n");

		queryBL.createQueryBuilder(I_MD_Cockpit_DocumentDetail.class)
				.create()
				.stream(I_MD_Cockpit_DocumentDetail.class)
				.forEach(cockpitDocumentDetailEntry -> message
						.append(I_MD_Cockpit_DocumentDetail.COLUMNNAME_MD_Cockpit_ID).append(" : ").append(cockpitDocumentDetailEntry.getMD_Cockpit_ID()).append(" ; ")
						.append(I_MD_Cockpit_DocumentDetail.COLUMNNAME_C_OrderLine_ID).append(" : ").append(cockpitDocumentDetailEntry.getC_OrderLine_ID()).append(" ; ")
						.append(I_MD_Cockpit_DocumentDetail.COLUMNNAME_QtyOrdered).append(" : ").append(cockpitDocumentDetailEntry.getQtyOrdered()).append(" ; ")
						.append(I_MD_Cockpit_DocumentDetail.COLUMNNAME_QtyReserved).append(" : ").append(cockpitDocumentDetailEntry.getQtyReserved()).append(" ; ")
						.append("\n"));

		logger.error("*** Error while looking for MD_Cockpit_DocumentDetail records, see current context: \n" + message);
	}
}
