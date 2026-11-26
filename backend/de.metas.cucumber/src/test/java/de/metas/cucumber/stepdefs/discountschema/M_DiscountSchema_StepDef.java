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
import de.metas.cucumber.stepdefs.productCategory.M_Product_Category_StepDefData;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_ConversionType;
import org.compiere.model.I_M_DiscountSchema;
import org.compiere.model.I_M_DiscountSchemaBreak;
import org.compiere.model.I_M_DiscountSchemaLine;
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
	private final M_Product_Category_StepDefData productCategoryTable;

	public M_DiscountSchema_StepDef(
			@NonNull final M_DiscountSchema_StepDefData discountSchemaTable,
			@NonNull final M_Product_StepDefData productTable,
			@NonNull final M_PricingSystem_StepDefData pricingSystemTable,
			@NonNull final M_DiscountSchemaBreak_StepDefData discountSchemaBreakTable,
			@NonNull final M_Product_Category_StepDefData productCategoryTable)
	{
		this.discountSchemaTable = discountSchemaTable;
		this.productTable = productTable;
		this.pricingSystemTable = pricingSystemTable;
		this.discountSchemaBreakTable = discountSchemaBreakTable;
		this.productCategoryTable = productCategoryTable;
	}

	/**
	 * Creates {@code M_DiscountSchema} rows. No look-up / upsert is performed — re-running against an
	 * existing row is not supported.
	 * <p>
	 * Required DataTable columns:
	 * <ul>
	 * <li>{@code Identifier} — stored in {@link M_DiscountSchema_StepDefData} for later reference</li>
	 * <li>{@code DiscountType}</li>
	 * <li>{@code Name}</li>
	 * <li>{@code ValidFrom}</li>
	 * </ul>
	 * Optional columns:
	 * <ul>
	 * <li>{@code ValidTo} — end of the validity window (inclusive); when absent or blank, the schema
	 * is valid indefinitely from {@code ValidFrom} onward (identical to current/pre-existing behavior)</li>
	 * </ul>
	 * Example usage:
	 * <pre>{@code
	 * Given metasfresh contains M_DiscountSchemas:
	 *   | Identifier | DiscountType | Name         | ValidFrom  | ValidTo    |
	 *   | ds_1       | B            | ValidTo test | 2026-09-07 | 2026-09-11 |
	 * }</pre>
	 */
	@Given("metasfresh contains M_DiscountSchemas:")
	public void create_discount_schema(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps();
		for (final Map<String, String> tableRow : tableRows)
		{
			createDiscountSchema(tableRow);
		}
	}

	/**
	 * Creates {@code M_DiscountSchemaBreak} rows (discount breaks attached to an existing {@code M_DiscountSchema}).
	 * Each break is saved with {@code IsValid=true}; no look-up / upsert is performed — re-running against an
	 * existing row is not supported.
	 * <p>
	 * Required DataTable columns:
	 * <ul>
	 * <li>{@code Identifier} — stored in {@link M_DiscountSchemaBreak_StepDefData} for later reference</li>
	 * <li>{@code M_DiscountSchema_ID.Identifier} — parent schema (resolved from {@link M_DiscountSchema_StepDefData})</li>
	 * <li>{@code M_Product_ID.Identifier} — product the break applies to (resolved from {@link M_Product_StepDefData})</li>
	 * <li>{@code Base_PricingSystem_ID.Identifier} — base pricing system (resolved from {@link M_PricingSystem_StepDefData})</li>
	 * <li>{@code SeqNo} — sequence within the schema</li>
	 * </ul>
	 * Optional columns:
	 * <ul>
	 * <li>{@code OPT.IsBPartnerFlatDiscount} — defaults to {@code false}</li>
	 * <li>{@code OPT.PriceBase}</li>
	 * <li>{@code OPT.BreakValue}</li>
	 * <li>{@code OPT.BreakDiscount} — defaults to {@code 0}</li>
	 * </ul>
	 * Example usage:
	 * <pre>{@code
	 * Given metasfresh contains M_DiscountSchemaBreaks:
	 *   | Identifier | M_DiscountSchema_ID.Identifier | M_Product_ID.Identifier | Base_PricingSystem_ID.Identifier | SeqNo | OPT.IsBPartnerFlatDiscount | OPT.PriceBase | OPT.BreakValue | OPT.BreakDiscount |
	 *   | dsb_1      | ds_1                           | p_1                     | ps_1                             | 10    | Y                          | P             | 10             | 0                 |
	 * }</pre>
	 */
	@Given("metasfresh contains M_DiscountSchemaBreaks:")
	public void created_discount_schema_break(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.forEach(this::createDiscountSchemaBreak);
	}

	/**
	 * Creates {@code M_DiscountSchemaLine} rows of a pricing schema (for the derivative price list update).
	 * The three price bases default to the source price list's prices: {@code List_Base=L} (list price),
	 * {@code Std_Base=S} (standard price) and {@code Limit_Base=X} (limit price), where L/S/X are the codes of the
	 * M_DiscountSchemaLine price base reference. So a line only needs the surcharges it wants to test.
	 * <p>
	 * Required columns: {@code M_DiscountSchema_ID} (identifier), {@code SeqNo}.
	 * <br>Optional columns: {@code M_Product_ID} (identifier), {@code M_Product_Category_ID} (identifier),
	 * {@code Std_AddAmt}, {@code List_AddAmt}, {@code Limit_AddAmt} (all default 0).
	 * <pre>{@code
	 * And metasfresh contains M_DiscountSchemaLines:
	 *   | M_DiscountSchema_ID | SeqNo | M_Product_ID | Std_AddAmt |
	 *   | ds_1                | 10    | p_1          | 1.50       |
	 * }</pre>
	 */
	@Given("metasfresh contains M_DiscountSchemaLines:")
	public void create_discount_schema_lines(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createDiscountSchemaLine);
	}

	private void createDiscountSchemaLine(@NonNull final DataTableRow row)
	{
		final I_M_DiscountSchema schema = row.getAsIdentifier(I_M_DiscountSchemaLine.COLUMNNAME_M_DiscountSchema_ID).lookupNotNullIn(discountSchemaTable);

		final I_M_DiscountSchemaLine line = InterfaceWrapperHelper.newInstance(I_M_DiscountSchemaLine.class);
		line.setM_DiscountSchema_ID(schema.getM_DiscountSchema_ID());
		line.setSeqNo(row.getAsInt(I_M_DiscountSchemaLine.COLUMNNAME_SeqNo));

		row.getAsOptionalIdentifier(I_M_DiscountSchemaLine.COLUMNNAME_M_Product_ID)
				.map(productIdentifier -> productIdentifier.lookupNotNullIn(productTable))
				.ifPresent(product -> line.setM_Product_ID(product.getM_Product_ID()));
		row.getAsOptionalIdentifier(I_M_DiscountSchemaLine.COLUMNNAME_M_Product_Category_ID)
				.map(categoryIdentifier -> categoryIdentifier.lookupNotNullIn(productCategoryTable))
				.ifPresent(category -> line.setM_Product_Category_ID(category.getM_Product_Category_ID()));

		// same values as the schema lines the application creates by default
		line.setList_Base("L");
		line.setStd_Base("S");
		line.setLimit_Base("X");
		line.setList_Rounding("C");
		line.setStd_Rounding("C");
		line.setLimit_Rounding("C");
		line.setList_Discount(BigDecimal.ZERO);
		line.setStd_Discount(BigDecimal.ZERO);
		line.setLimit_Discount(BigDecimal.ZERO);
		// mandatory currency conversion columns, irrelevant here because the price lists share one currency
		line.setConversionDate(Timestamp.valueOf("2015-11-09 00:00:00"));
		line.setC_ConversionType_ID(Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_ConversionType.class)
				.addEqualsFilter(I_C_ConversionType.COLUMNNAME_Value, "S")
				.create()
				.firstIdOnly());

		line.setStd_AddAmt(row.getAsOptionalBigDecimal(I_M_DiscountSchemaLine.COLUMNNAME_Std_AddAmt).orElse(BigDecimal.ZERO));
		line.setList_AddAmt(row.getAsOptionalBigDecimal(I_M_DiscountSchemaLine.COLUMNNAME_List_AddAmt).orElse(BigDecimal.ZERO));
		line.setLimit_AddAmt(row.getAsOptionalBigDecimal(I_M_DiscountSchemaLine.COLUMNNAME_Limit_AddAmt).orElse(BigDecimal.ZERO));

		saveRecord(line);
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
		final Timestamp validTo = DataTableUtil.extractDateTimestampForColumnNameOrNull(tableRow, I_M_DiscountSchema.COLUMNNAME_ValidTo);

		final I_M_DiscountSchema discountSchemaRecord = InterfaceWrapperHelper.newInstance(I_M_DiscountSchema.class);

		discountSchemaRecord.setDiscountType(discountType);
		discountSchemaRecord.setName(name);
		discountSchemaRecord.setValidFrom(validFrom);
		discountSchemaRecord.setValidTo(validTo);

		saveRecord(discountSchemaRecord);

		discountSchemaTable.putOrReplace(DataTableUtil.extractRecordIdentifier(tableRow, I_M_DiscountSchema.Table_Name), discountSchemaRecord);
	}
}