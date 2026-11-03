/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.cucumber.stepdefs.doctype;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.document.DocBaseType;
import de.metas.document.DocSubType;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_DocType_Invoicing_Pool;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.compiere.model.I_C_DocType.COLUMNNAME_C_DocTypeInvoice_ID;
import static org.compiere.model.I_C_DocType.COLUMNNAME_C_DocType_ID;
import static org.compiere.model.I_C_DocType_Invoicing_Pool.COLUMNNAME_C_DocType_Invoicing_Pool_ID;

public class C_DocType_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);

	private final C_DocType_Invoicing_Pool_StepDefData invoicingPoolTable;
	private final C_DocType_StepDefData docTypeTable;

	public C_DocType_StepDef(
			@NonNull final C_DocType_Invoicing_Pool_StepDefData invoicingPoolTable,
			@NonNull final C_DocType_StepDefData docTypeTable)
	{
		this.invoicingPoolTable = invoicingPoolTable;
		this.docTypeTable = docTypeTable;
	}

	/**
	 * {@code C_DocType_ID} -> the {@code IsActive} the doc type had before {@link #update_C_DocType(DataTable)} changed it, so {@link #restoreIsActive()} can put it back.
	 */
	private final Map<Integer, Boolean> isActiveBeforeScenario = new LinkedHashMap<>();

	/**
	 * Updates existing {@code C_DocType}s that were registered before, e.g. with {@code load C_DocType:}.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>C_DocType_ID</b> — (required) identifier of the doc type<br>
	 *   <b>C_DocTypeInvoice_ID</b> — (optional) identifier of the doc type's invoice doc type; {@code null} removes it<br>
	 *   <b>C_DocType_Invoicing_Pool_ID</b> — (optional) identifier of the doc type's invoicing pool; {@code null} removes it<br>
	 *   <b>IsActive</b> — (optional) activates or deactivates the doc type; the doc type is set back to its former value after the scenario, also when a step failed
	 * @cucumber.depends StepDefData: C_DocType_StepDefData, C_DocType_Invoicing_Pool_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And update C_DocType:
	 *   | C_DocType_ID        | IsActive |
	 *   | paymentBonusDocType | false    |
	 * </pre>
	 */
	@And("update C_DocType:")
	public void update_C_DocType(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::updateDocType);
	}

	/**
	 * Registers an existing {@code C_DocType} under an identifier, found either by its {@code Name} or by its
	 * {@code DocBaseType} (+ optional {@code DocSubType}). The {@code DocBaseType} path goes through
	 * {@link IDocTypeDAO#getDocTypeId(DocTypeQuery)} -- the same lookup production code uses -- so it resolves the
	 * default document type of that base type for the standard client and org.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>C_DocType_ID</b> — (required) alias for cross-step reference<br>
	 *   <b>Name</b> — (optional) exact {@code C_DocType.Name}; required when {@code DocBaseType} is omitted<br>
	 *   <b>DocBaseType</b> — (optional) {@code C_DocType.DocBaseType} code; required when {@code Name} is omitted<br>
	 *   <b>DocSubType</b> — (optional) {@code C_DocType.DocSubType} code, only with {@code DocBaseType};
	 *       omitted means no sub type<br>
	 * @cucumber.depends StepDefData: C_DocType_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And load C_DocType:
	 *   | DocBaseType | C_DocType_ID      |
	 *   | BKP         | docTypeProduktion |
	 * </pre>
	 */
	@And("load C_DocType:")
	public void load_C_DocType(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::loadDocType);
	}

	private void loadDocType(@NonNull final DataTableRow tableRow)
	{
		final I_C_DocType docTypeRecord = tableRow.getAsOptionalEnum(I_C_DocType.COLUMNNAME_DocBaseType, DocBaseType.class)
				.map(docBaseType -> docTypeDAO.getById(docTypeDAO.getDocTypeId(DocTypeQuery.builder()
						.docBaseType(docBaseType)
						.docSubType(tableRow.getAsOptionalEnum(I_C_DocType.COLUMNNAME_DocSubType, DocSubType.class).orElse(DocSubType.NONE))
						.clientAndOrgId(StepDefConstants.CLIENT_ID, StepDefConstants.ORG_ID)
						.build())))
				.orElseGet(() -> queryBL.createQueryBuilder(I_C_DocType.class)
						.addOnlyActiveRecordsFilter()
						.addEqualsFilter(I_C_DocType.COLUMNNAME_Name, tableRow.getAsString(I_C_DocType.COLUMNNAME_Name))
						.create()
						.firstOnlyNotNull(I_C_DocType.class));

		tableRow.getAsIdentifier(COLUMNNAME_C_DocType_ID).put(docTypeTable, docTypeRecord);
	}

	private void updateDocType(@NonNull final DataTableRow tableRow)
	{
		final StepDefDataIdentifier docTypeIdentifier = tableRow.getAsIdentifier(COLUMNNAME_C_DocType_ID);
		final I_C_DocType docTypeRecord = docTypeIdentifier.lookupNotNullIn(docTypeTable);

		tableRow.getAsOptionalIdentifier(COLUMNNAME_C_DocTypeInvoice_ID)
				.ifPresent(invoiceDocTypeIdentifier -> {
					final I_C_DocType invoiceDocTypeRecord = invoiceDocTypeIdentifier.lookupIn(docTypeTable);
					docTypeRecord.setC_DocTypeInvoice_ID(invoiceDocTypeRecord != null ? invoiceDocTypeRecord.getC_DocType_ID() : 0);
				});

		tableRow.getAsOptionalIdentifier(COLUMNNAME_C_DocType_Invoicing_Pool_ID)
				.ifPresent(invoicingPoolIdentifier -> {
					final I_C_DocType_Invoicing_Pool invoicingPoolRecord = invoicingPoolIdentifier.lookupIn(invoicingPoolTable);
					docTypeRecord.setC_DocType_Invoicing_Pool_ID(invoicingPoolRecord != null ? invoicingPoolRecord.getC_DocType_Invoicing_Pool_ID() : 0);
				});

		tableRow.getAsOptionalBoolean(I_C_DocType.COLUMNNAME_IsActive)
				.ifPresent(isActive -> {
					isActiveBeforeScenario.putIfAbsent(docTypeRecord.getC_DocType_ID(), docTypeRecord.isActive());
					docTypeRecord.setIsActive(isActive);
				});

		saveRecord(docTypeRecord);

		docTypeTable.putOrReplace(docTypeIdentifier, docTypeRecord);
	}

	/**
	 * Sets back the {@code IsActive} of every doc type that {@link #update_C_DocType(DataTable)} changed.
	 * <p>
	 * Doc types are shared by every scenario on the executor, so a doc type left inactive would break whatever runs next;
	 * an {@code @After} hook also runs when a step failed, a trailing Gherkin step would not.
	 */
	@After
	public void restoreIsActive()
	{
		isActiveBeforeScenario.forEach((docTypeId, isActive) -> {
			final I_C_DocType docTypeRecord = InterfaceWrapperHelper.load(docTypeId, I_C_DocType.class);
			docTypeRecord.setIsActive(isActive);
			saveRecord(docTypeRecord);
		});
		isActiveBeforeScenario.clear();
	}
}
