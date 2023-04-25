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

package de.metas.cucumber.stepdefs.distributionorder;

import de.metas.cucumber.stepdefs.C_OrderLine_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.distribution.DD_NetworkDistributionLine_StepDefData;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Product;
import org.eevolution.model.I_DD_NetworkDistributionLine;
import org.eevolution.model.I_DD_OrderLine;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

public class DD_OrderLine_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final C_OrderLine_StepDefData orderLineTable;
	private final DD_OrderLine_StepDefData ddOrderLineTable;
	private final DD_NetworkDistributionLine_StepDefData ddNetworkLineTable;
	private final M_Product_StepDefData productTable;

	public DD_OrderLine_StepDef(
			@NonNull final C_OrderLine_StepDefData orderLineTable,
			@NonNull final DD_OrderLine_StepDefData ddOrderLineTable,
			@NonNull final DD_NetworkDistributionLine_StepDefData ddNetworkLineTable,
			@NonNull final M_Product_StepDefData productTable)
	{
		this.orderLineTable = orderLineTable;
		this.ddOrderLineTable = ddOrderLineTable;
		this.ddNetworkLineTable = ddNetworkLineTable;
		this.productTable = productTable;
	}

	@And("^after not more than (.*)s, DD_OrderLines are found:$")
	public void find_DDOrderLines(
			final int timeoutSec,
			@NonNull final DataTable dataTable) throws InterruptedException
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			findDDOrderLine(timeoutSec, tableRow);
		}
	}

	@And("DD_OrderLines are validated:")
	public void validate_DD_OrderLines(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			validateDDOrderLine(tableRow);
		}
	}
	
	@And("DD_OrderLines are updated:")
	public void update_DD_OrderLines(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			updateDDOrderLine(tableRow);
		}
	}
	
	private void updateDDOrderLine(@NonNull final Map<String, String> tableRow)
	{
		final String ddOrderLineIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_DD_OrderLine.COLUMNNAME_DD_OrderLine_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_DD_OrderLine ddOrderLineRecord = ddOrderLineTable.get(ddOrderLineIdentifier);
		assertThat(ddOrderLineRecord).isNotNull();

		final BigDecimal qtyOrderedAndEntered = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_DD_OrderLine.COLUMNNAME_QtyOrdered);
		if (qtyOrderedAndEntered != null)
		{
			ddOrderLineRecord.setQtyOrdered(qtyOrderedAndEntered);
			ddOrderLineRecord.setQtyEntered(qtyOrderedAndEntered);
		}
		
		saveRecord(ddOrderLineRecord);
	}
	
	private void validateDDOrderLine(@NonNull final Map<String, String> tableRow)
	{
		final SoftAssertions softly = new SoftAssertions();
		
		final String ddOrderLineIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_DD_OrderLine.COLUMNNAME_DD_OrderLine_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_DD_OrderLine ddOrderLineRecord = ddOrderLineTable.get(ddOrderLineIdentifier);
		softly.assertThat(ddOrderLineRecord).isNotNull();
		
		final BigDecimal qtyOrdered = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_DD_OrderLine.COLUMNNAME_QtyOrdered);
		if (qtyOrdered != null)
		{
			softly.assertThat(ddOrderLineRecord.getQtyOrdered()).isEqualByComparingTo(qtyOrdered);
		}

		final BigDecimal qtyEntered = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_DD_OrderLine.COLUMNNAME_QtyEntered);
		if (qtyEntered != null)
		{
			softly.assertThat(ddOrderLineRecord.getQtyEntered()).isEqualByComparingTo(qtyEntered);
		}
		
		final BigDecimal qtyDelivered = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_DD_OrderLine.COLUMNNAME_QtyDelivered);
		if (qtyDelivered != null)
		{
			softly.assertThat(ddOrderLineRecord.getQtyDelivered()).isEqualByComparingTo(qtyDelivered);
		}

		final Integer line = DataTableUtil.extractIntegerOrNullForColumnName(tableRow, "OPT." + I_DD_OrderLine.COLUMNNAME_Line);
		if (line != null)
		{
			softly.assertThat(ddOrderLineRecord.getLine()).isEqualByComparingTo(line);
		}

		final String ddNetworkDistributionLineIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_DD_OrderLine.COLUMNNAME_DD_NetworkDistributionLine_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(ddNetworkDistributionLineIdentifier))
		{
			final I_DD_NetworkDistributionLine ddNetworkDistributionLineRecord = ddNetworkLineTable.get(ddNetworkDistributionLineIdentifier);
			softly.assertThat(ddOrderLineRecord.getDD_NetworkDistributionLine_ID()).isEqualTo(ddNetworkDistributionLineRecord.getDD_NetworkDistributionLine_ID());
		}

		final String productIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_DD_OrderLine.COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(productIdentifier))
		{
			final I_M_Product productRecord = productTable.get(productIdentifier);
			softly.assertThat(ddOrderLineRecord.getM_Product_ID()).isEqualTo(productRecord.getM_Product_ID());
		}
		
		final BigDecimal qtyReserved = DataTableUtil.extractBigDecimalOrNullForColumnName(tableRow, "OPT." + I_DD_OrderLine.COLUMNNAME_QtyReserved);
		if (qtyReserved != null)
		{
			softly.assertThat(ddOrderLineRecord.getQtyReserved()).isEqualByComparingTo(qtyReserved);
		}
		
		softly.assertAll();
	}
	
	private void findDDOrderLine(
			final int timeoutSec,
			@NonNull final Map<String, String> tableRow) throws InterruptedException
	{
		final String soOrderLineIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_DD_OrderLine.COLUMNNAME_C_OrderLineSO_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_C_OrderLine soOrderLineRecord = orderLineTable.get(soOrderLineIdentifier);
		assertThat(soOrderLineRecord).isNotNull();

		final Supplier<Optional<I_DD_OrderLine>> ddOrderLineSupplier = () -> queryBL.createQueryBuilder(I_DD_OrderLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DD_OrderLine.COLUMNNAME_C_OrderLineSO_ID, soOrderLineRecord.getC_OrderLine_ID())
				.create()
				.firstOnlyOptional(I_DD_OrderLine.class);

		final I_DD_OrderLine ddOrderLineRecord = StepDefUtil.tryAndWaitForItem(timeoutSec, 500, ddOrderLineSupplier);

		final String ddOrderLineIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_DD_OrderLine.COLUMNNAME_DD_OrderLine_ID + "." + TABLECOLUMN_IDENTIFIER);
		ddOrderLineTable.putOrReplace(ddOrderLineIdentifier, ddOrderLineRecord);
	}
}
