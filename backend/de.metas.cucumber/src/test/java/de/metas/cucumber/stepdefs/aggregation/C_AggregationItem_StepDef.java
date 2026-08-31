/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.cucumber.stepdefs.aggregation;

import de.metas.aggregation.model.I_C_Aggregation;
import de.metas.aggregation.model.I_C_AggregationItem;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.ad.column.AdColumnId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;

public class C_AggregationItem_StepDef
{
	private final IADTableDAO tableDAO = Services.get(IADTableDAO.class);

	private final C_Aggregation_StepDefData aggregationTable;
	private final C_AggregationItem_StepDefData aggregationItemTable;
	private final C_Aggregation_Attribute_StepDefData aggregationAttributeTable;

	public C_AggregationItem_StepDef(
			@NonNull final C_Aggregation_StepDefData aggregationTable,
			@NonNull final C_AggregationItem_StepDefData aggregationItemTable,
			@NonNull final C_Aggregation_Attribute_StepDefData aggregationAttributeTable)
	{
		this.aggregationTable = aggregationTable;
		this.aggregationItemTable = aggregationItemTable;
		this.aggregationAttributeTable = aggregationAttributeTable;
	}

	/**
	 * Creates one {@code C_AggregationItem} per DataTable row.
	 * <p>
	 * Required columns: {@code Type} ({@code COL}/{@code INC}/{@code ATR}, see {@code X_C_AggregationItem.TYPE_*}),
	 * {@code EntityType}, {@code C_Aggregation_ID} (the parent header aggregation).
	 * <p>
	 * Type-specific optional columns:
	 * <ul>
	 * <li>{@code ColumnName} — for {@code Type=COL}: the name of a column on the parent aggregation's own
	 * {@code AD_Table_ID}; resolved inline (via {@link IADTableDAO#retrieveColumnId(String, String)}) to
	 * {@code AD_Column_ID}. Reference the column by its natural name here — no separate {@code AD_Column} setup step.</li>
	 * <li>{@code Included_Aggregation_ID} — for {@code Type=INC}.</li>
	 * <li>{@code C_Aggregation_Attribute_ID} — for {@code Type=ATR}.</li>
	 * </ul>
	 *
	 * <pre>
	 * And metasfresh contains C_AggregationItems:
	 *   | C_Aggregation_ID | EntityType | Type | ColumnName     |
	 *   | bioAgg           | EE01       | COL  | M_Warehouse_ID |
	 * </pre>
	 */
	@Given("metasfresh contains C_AggregationItems:")
	public void metasfresh_contains_c_aggregation_item(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createC_Aggregation_Item);
	}

	private void createC_Aggregation_Item(@NonNull final DataTableRow row)
	{
		final I_C_Aggregation aggregationRecord = row.getAsIdentifier(I_C_AggregationItem.COLUMNNAME_C_Aggregation_ID).lookupNotNullIn(aggregationTable);

		final I_C_AggregationItem aggregationItemRecord = InterfaceWrapperHelper.newInstanceOutOfTrx(I_C_AggregationItem.class);
		aggregationItemRecord.setType(row.getAsString(I_C_AggregationItem.COLUMNNAME_Type));
		aggregationItemRecord.setEntityType(row.getAsString(I_C_AggregationItem.COLUMNNAME_EntityType));
		aggregationItemRecord.setC_Aggregation_ID(aggregationRecord.getC_Aggregation_ID());

		row.getAsOptionalIdentifier(I_C_AggregationItem.COLUMNNAME_Included_Aggregation_ID)
				.filter(StepDefDataIdentifier::isNotNullPlaceholder)
				.map(id -> id.lookupNotNullIn(aggregationTable))
				.ifPresent(included -> aggregationItemRecord.setIncluded_Aggregation_ID(included.getC_Aggregation_ID()));

		row.getAsOptionalIdentifier(I_C_AggregationItem.COLUMNNAME_C_Aggregation_Attribute_ID)
				.filter(StepDefDataIdentifier::isNotNullPlaceholder)
				.map(id -> id.lookupNotNullIn(aggregationAttributeTable))
				.ifPresent(attribute -> aggregationItemRecord.setC_Aggregation_Attribute_ID(attribute.getC_Aggregation_Attribute_ID()));

		// For a Type=COL item, resolve AD_Column_ID from the parent aggregation's own table + the given ColumnName.
		row.getAsOptionalString("ColumnName").ifPresent(columnName -> {
			final String aggregationTableName = aggregationRecord.getAD_Table().getTableName();
			final AdColumnId adColumnId = tableDAO.retrieveColumnId(aggregationTableName, columnName);
			aggregationItemRecord.setAD_Column_ID(AdColumnId.toRepoId(adColumnId));
		});

		InterfaceWrapperHelper.saveRecord(aggregationItemRecord);

		row.getAsOptionalIdentifier().ifPresent(identifier -> aggregationItemTable.putOrReplace(identifier, aggregationItemRecord));
	}
}
