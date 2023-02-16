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

package de.metas.cucumber.stepdefs;

import de.metas.acct.model.I_C_VAT_Code;
import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.acctschema.C_AcctSchema_StepDefData;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class C_Vat_Code_StepDef
{
	private final C_Tax_StepDefData taxTable;
	private final C_AcctSchema_StepDefData acctSchemaTable;
	private final C_Vat_Code_StepDefData vatCodeTable;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public C_Vat_Code_StepDef(
			@NonNull final C_Tax_StepDefData taxTable,
			@NonNull final C_AcctSchema_StepDefData acctSchemaTable,
			@NonNull final C_Vat_Code_StepDefData vatCodeTable)
	{
		this.taxTable = taxTable;
		this.acctSchemaTable = acctSchemaTable;
		this.vatCodeTable = vatCodeTable;
	}

	@And("metasfresh contains C_Vat_Code:")
	public void add_C_Vat_Code(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps();
		for (final Map<String, String> tableRow : tableRows)
		{
			createVatCode(tableRow);
		}
	}

	private void createVatCode(@NonNull final Map<String, String> tableRow)
	{
		final String taxIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_VAT_Code.COLUMNNAME_C_Tax_ID + "." + TABLECOLUMN_IDENTIFIER);
		final int taxId = taxTable.get(taxIdentifier).getC_Tax_ID();

		final String acctSchemaIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_VAT_Code.COLUMNNAME_C_AcctSchema_ID + "." + TABLECOLUMN_IDENTIFIER);
		final int acctSchemaId = acctSchemaTable.get(acctSchemaIdentifier).getC_AcctSchema_ID();

		final String vatCode = DataTableUtil.extractStringForColumnName(tableRow, I_C_VAT_Code.COLUMNNAME_VATCode);
		final Timestamp validFrom = DataTableUtil.extractDateTimestampForColumnName(tableRow, I_C_VAT_Code.COLUMNNAME_ValidFrom);

		final boolean soTrx = DataTableUtil.extractBooleanForColumnNameOr(tableRow, "OPT." + I_C_VAT_Code.COLUMNNAME_IsSOTrx, true);
		final String description = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_VAT_Code.COLUMNNAME_Description);

		final I_C_VAT_Code vatCodeRecord = CoalesceUtil.coalesceSuppliersNotNull(
				() -> queryBL.createQueryBuilder(I_C_VAT_Code.class)
						.addEqualsFilter(I_C_VAT_Code.COLUMNNAME_VATCode, vatCode)
						.create()
						.firstOnly(I_C_VAT_Code.class),
				() -> InterfaceWrapperHelper.newInstance(I_C_VAT_Code.class));

		vatCodeRecord.setC_Tax_ID(taxId);
		vatCodeRecord.setC_AcctSchema_ID(acctSchemaId);
		vatCodeRecord.setVATCode(vatCode);
		vatCodeRecord.setValidFrom(validFrom);
		vatCodeRecord.setIsSOTrx(soTrx ? "Y" : "N");
		vatCodeRecord.setDescription(description);

		final Timestamp validTo = DataTableUtil.extractDateTimestampForColumnNameOrNull(tableRow, "OPT." + I_C_VAT_Code.COLUMNNAME_ValidTo);
		if (validTo != null)
		{
			vatCodeRecord.setValidTo(validTo);
		}

		saveRecord(vatCodeRecord);

		final String identifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_VAT_Code.COLUMNNAME_C_VAT_Code_ID + "." + TABLECOLUMN_IDENTIFIER);
		vatCodeTable.putOrReplace(identifier, vatCodeRecord);
	}
}
