/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.cucumber.stepdefs.contract;

import de.metas.contracts.model.I_C_CompensationGroup_ContractSettings;
import de.metas.contracts.model.I_C_CompensationGroup_ContractSettings_DocType;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.doctype.C_DocType_StepDefData;
import de.metas.cucumber.stepdefs.order.C_CompensationGroup_Schema_StepDefData;
import de.metas.order.model.I_C_CompensationGroup_Schema;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.I_C_DocType;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/**
 * Creates {@link I_C_CompensationGroup_ContractSettings} records (which compensation-group schema a
 * {@code Type_Conditions=CompensationGroup} contract applies) and their {@link I_C_CompensationGroup_ContractSettings_DocType}
 * doc-type links (which order document types trigger the compensation group).
 */
@RequiredArgsConstructor
public class C_CompensationGroup_ContractSettings_StepDef
{
	private final @NonNull C_CompensationGroup_ContractSettings_StepDefData settingsTable;
	private final @NonNull C_CompensationGroup_Schema_StepDefData schemaTable;
	private final @NonNull C_DocType_StepDefData docTypeTable;

	/**
	 * Creates {@link I_C_CompensationGroup_ContractSettings} records.
	 * <p>
	 * DataTable columns:
	 * <ul>
	 *     <li>{@code Identifier} (required) — identifier for later reference</li>
	 *     <li>{@code Name} (required) — name of the settings record</li>
	 *     <li>{@code C_CompensationGroup_Schema_ID} (required, identifier-ref) — the compensation group schema
	 *         to apply when a contract using these settings triggers</li>
	 * </ul>
	 * <pre>
	 * And metasfresh contains C_CompensationGroup_ContractSettings:
	 *   | Identifier      | Name             | C_CompensationGroup_Schema_ID.Identifier |
	 *   | contractSchema1 | contract compGrp | compGroupSchema                          |
	 * </pre>
	 */
	@Given("metasfresh contains C_CompensationGroup_ContractSettings:")
	public void createSettings(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final I_C_CompensationGroup_Schema schema = row.getAsIdentifier(I_C_CompensationGroup_ContractSettings.COLUMNNAME_C_CompensationGroup_Schema_ID)
					.lookupNotNullIn(schemaTable);

			final I_C_CompensationGroup_ContractSettings record = newInstance(I_C_CompensationGroup_ContractSettings.class);
			record.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());
			record.setName(row.getAsString(I_C_CompensationGroup_ContractSettings.COLUMNNAME_Name));
			record.setC_CompensationGroup_Schema_ID(schema.getC_CompensationGroup_Schema_ID());

			saveRecord(record);

			settingsTable.putOrReplace(row.getAsIdentifier(), record);
		});
	}

	/**
	 * Creates {@link I_C_CompensationGroup_ContractSettings_DocType} links — the order document types for
	 * which a settings record's compensation group is triggered on order completion.
	 * <p>
	 * DataTable columns:
	 * <ul>
	 *     <li>{@code C_CompensationGroup_ContractSettings_ID} (required, identifier-ref) — the settings record</li>
	 *     <li>{@code C_DocType_ID} (required, identifier-ref) — the order doc type, loaded beforehand via
	 *         {@code load C_DocType:}</li>
	 * </ul>
	 * <pre>
	 * And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
	 *   | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
	 *   | contractSchema1                                    | docTypeSalesOrder       |
	 * </pre>
	 */
	@Given("metasfresh contains C_CompensationGroup_ContractSettings_DocType:")
	public void createSettingsDocTypes(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final I_C_CompensationGroup_ContractSettings settings = row.getAsIdentifier(I_C_CompensationGroup_ContractSettings_DocType.COLUMNNAME_C_CompensationGroup_ContractSettings_ID)
					.lookupNotNullIn(settingsTable);
			final I_C_DocType docType = row.getAsIdentifier(I_C_CompensationGroup_ContractSettings_DocType.COLUMNNAME_C_DocType_ID)
					.lookupNotNullIn(docTypeTable);

			final I_C_CompensationGroup_ContractSettings_DocType record = newInstance(I_C_CompensationGroup_ContractSettings_DocType.class);
			record.setAD_Org_ID(settings.getAD_Org_ID());
			record.setC_CompensationGroup_ContractSettings_ID(settings.getC_CompensationGroup_ContractSettings_ID());
			record.setC_DocType_ID(docType.getC_DocType_ID());

			saveRecord(record);
		});
	}
}
