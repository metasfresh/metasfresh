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
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Table;

import java.util.List;
import java.util.Map;

import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;

/**
 * Creates / loads {@code C_Aggregation} header records — the aggregation config referenced by
 * {@code PP_Product_Planning.C_Manufacturing_Aggregation_ID} (manufacturing candidate grouping) and by
 * invoice-candidate aggregation. Items (the grouped columns/attributes) are added via {@link C_AggregationItem_StepDef}.
 */
public class C_Aggregation_StepDef
{
	private final IADTableDAO tableDAO = Services.get(IADTableDAO.class);

	private final C_Aggregation_StepDefData aggregationTable;

	public C_Aggregation_StepDef(@NonNull final C_Aggregation_StepDefData aggregationTable)
	{
		this.aggregationTable = aggregationTable;
	}

	/**
	 * Creates one {@code C_Aggregation} header per DataTable row.
	 * <p>
	 * Required columns: {@code TableName} (the table this aggregation groups, e.g. {@code PP_Order_Candidate}),
	 * {@code EntityType}.
	 * <p>
	 * Optional columns:
	 * <ul>
	 * <li>{@code Name} — omit it to auto-generate a unique name via {@code suggestValueAndName()} (avoids the
	 * {@code C_Aggregation_UniqueName} unique-index collision across scenarios / re-runs); supply an explicit
	 * {@code Name} only when the test needs a known value.</li>
	 * <li>{@code AggregationUsageLevel} — e.g. {@code H} (header).</li>
	 * </ul>
	 *
	 * <pre>
	 * And metasfresh contains C_Aggregations:
	 *   | Identifier | TableName          | EntityType | AggregationUsageLevel |
	 *   | bioAgg     | PP_Order_Candidate | EE01       | H                     |
	 * </pre>
	 */
	@Given("metasfresh contains C_Aggregations:")
	public void metasfresh_contains_c_aggregation(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createC_Aggregation);
	}

	@Given("load C_Aggregations:")
	public void load_C_Aggregation(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			loadC_Aggregation(tableRow);
		}
	}

	private void loadC_Aggregation(@NonNull final Map<String, String> tableRow)
	{
		final int aggregationId = DataTableUtil.extractIntForColumnName(tableRow, I_C_Aggregation.COLUMNNAME_C_Aggregation_ID);
		final I_C_Aggregation aggregationRecord = InterfaceWrapperHelper.load(aggregationId, I_C_Aggregation.class);

		final String aggregationIdentifier = DataTableUtil.extractRecordIdentifier(tableRow, "C_Aggregation");
		aggregationTable.putOrReplace(aggregationIdentifier, aggregationRecord);
	}

	private void createC_Aggregation(@NonNull final DataTableRow row)
	{
		final String tableName = row.getAsString(I_AD_Table.COLUMNNAME_TableName);
		final AdTableId adTableId = tableDAO.retrieveAdTableId(tableName);

		final I_C_Aggregation aggregationRecord = newInstanceOutOfTrx(I_C_Aggregation.class);

		// Name has a unique DB index (C_Aggregation_UniqueName). When the test does not need a known Name,
		// omit it and let suggestValueAndName() auto-generate a unique one, avoiding collisions across
		// scenarios (which share one DB per executor) and across re-runs. An explicit Name is honored.
		aggregationRecord.setName(row.getAsOptionalString(I_C_Aggregation.COLUMNNAME_Name)
				.orElseGet(() -> row.suggestValueAndName().getName()));
		aggregationRecord.setAD_Table_ID(AdTableId.toRepoId(adTableId));
		aggregationRecord.setEntityType(row.getAsString(I_C_Aggregation.COLUMNNAME_EntityType));

		row.getAsOptionalString(I_C_Aggregation.COLUMNNAME_AggregationUsageLevel)
				.ifPresent(aggregationRecord::setAggregationUsageLevel);

		InterfaceWrapperHelper.saveRecord(aggregationRecord);

		row.getAsOptionalIdentifier().ifPresent(identifier -> aggregationTable.putOrReplace(identifier, aggregationRecord));
	}
}
