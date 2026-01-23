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

package de.metas.cucumber.stepdefs.discountschema;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.pricing.M_PricingSystem_StepDefData;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_DiscountSchema;
import org.compiere.model.I_M_DiscountSchemaBreak;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.I_M_Product;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class M_DiscountSchema_StepDef
{
	private final M_DiscountSchema_StepDefData discountSchemaTable;
	private final M_Product_StepDefData productTable;
	private final M_PricingSystem_StepDefData pricingSystemTable;
	private final M_DiscountSchemaBreak_StepDefData discountSchemaBreakTable;

	public M_DiscountSchema_StepDef(
			@NonNull final M_DiscountSchema_StepDefData discountSchemaTable,
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final M_PricingSystem_StepDefData pricingSystemTable,
			@NonNull final M_DiscountSchemaBreak_StepDefData discountSchemaBreakTable)
	{
		this.discountSchemaTable = discountSchemaTable;
		this.productTable = productTable;
		this.pricingSystemTable = pricingSystemTable;
		this.discountSchemaBreakTable = discountSchemaBreakTable;
	}

	@Given("metasfresh contains M_DiscountSchemas:")
	public void create_discount_schema(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps();
		for (final Map<String, String> tableRow : tableRows)
		{
			createDiscountSchema(tableRow);
		}
	}

	@Given("metasfresh contains M_DiscountSchemaBreaks:")
	public void created_discount_schema_break(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.forEach(this::createDiscountSchemaBreak);
	}

	private void createDiscountSchemaBreak(@NonNull final DataTableRow tableRow)
	{
		final boolean isBPartnerFlatDiscount = tableRow.getAsOptionalBoolean(I_M_DiscountSchemaBreak.COLUMNNAME_IsBPartnerFlatDiscount).orElse(false);

		final int seqNo = tableRow.getAsInt(I_M_DiscountSchemaBreak.COLUMNNAME_SeqNo);

		final I_M_Product productRecord = tableRow.getAsIdentifier(I_M_DiscountSchemaBreak.COLUMNNAME_M_Product_ID).lookupNotNullIn(productTable);
		final I_M_PricingSystem pricingSystemRecord = tableRow.getAsIdentifier(I_M_DiscountSchemaBreak.COLUMNNAME_Base_PricingSystem_ID).lookupNotNullIn(pricingSystemTable);
		final I_M_DiscountSchema discountSchemaRecord = tableRow.getAsIdentifier(I_M_DiscountSchemaBreak.COLUMNNAME_M_DiscountSchema_ID).lookupNotNullIn(discountSchemaTable);

		final I_M_DiscountSchemaBreak discountSchemaBreakRecord = InterfaceWrapperHelper.newInstance(I_M_DiscountSchemaBreak.class);
		discountSchemaBreakRecord.setIsBPartnerFlatDiscount(isBPartnerFlatDiscount);
		discountSchemaBreakRecord.setSeqNo(seqNo);
		discountSchemaBreakRecord.setM_Product_ID(productRecord.getM_Product_ID());
		discountSchemaBreakRecord.setBase_PricingSystem_ID(pricingSystemRecord.getM_PricingSystem_ID());
		discountSchemaBreakRecord.setM_DiscountSchema_ID(discountSchemaRecord.getM_DiscountSchema_ID());
		discountSchemaBreakRecord.setIsValid(true);

		tableRow.getAsOptionalString(I_M_DiscountSchemaBreak.COLUMNNAME_PriceBase)
				.ifPresent(discountSchemaBreakRecord::setPriceBase);

		tableRow.getAsOptionalBigDecimal( I_M_DiscountSchemaBreak.COLUMNNAME_BreakValue)
				.ifPresent(discountSchemaBreakRecord::setBreakValue);

		final BigDecimal breakDiscount = tableRow.getAsOptionalBigDecimal(I_M_DiscountSchemaBreak.COLUMNNAME_BreakDiscount)
				.orElse(BigDecimal.ZERO);

		discountSchemaBreakRecord.setBreakDiscount(breakDiscount);

		saveRecord(discountSchemaBreakRecord);

		discountSchemaBreakTable.putOrReplace(tableRow.getAsIdentifier(), discountSchemaBreakRecord);
	}

	private void createDiscountSchema(@NonNull final Map<String, String> tableRow)
	{
		final String discountType = DataTableUtil.extractStringForColumnName(tableRow, I_M_DiscountSchema.COLUMNNAME_DiscountType);
		final String name = DataTableUtil.extractStringForColumnName(tableRow, I_M_DiscountSchema.COLUMNNAME_Name);
		final Timestamp validFrom = DataTableUtil.extractDateTimestampForColumnName(tableRow, I_M_DiscountSchema.COLUMNNAME_ValidFrom);

		final I_M_DiscountSchema discountSchemaRecord = InterfaceWrapperHelper.newInstance(I_M_DiscountSchema.class);

		discountSchemaRecord.setDiscountType(discountType);
		discountSchemaRecord.setName(name);
		discountSchemaRecord.setValidFrom(validFrom);

		saveRecord(discountSchemaRecord);

		discountSchemaTable.putOrReplace(DataTableUtil.extractRecordIdentifier(tableRow, I_M_DiscountSchema.Table_Name), discountSchemaRecord);
	}
}