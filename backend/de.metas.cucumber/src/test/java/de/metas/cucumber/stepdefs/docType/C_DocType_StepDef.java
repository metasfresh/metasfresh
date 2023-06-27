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

package de.metas.cucumber.stepdefs.docType;

import de.metas.common.util.Check;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.seqNo.AD_Sequence_StepDefData;
import de.metas.document.DocBaseType;
import de.metas.document.IDocTypeBL;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Sequence;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_GL_Category;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.compiere.model.I_C_DocType.COLUMNNAME_GL_Category_ID;
import static org.compiere.model.I_C_DocType.COLUMNNAME_DocNoSequence_ID;

public class C_DocType_StepDef
{
	private final C_DocType_StepDefData docTypeTable;
	private final AD_Sequence_StepDefData sequenceTable;


	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public C_DocType_StepDef(
			@NonNull final C_DocType_StepDefData docTypeTable,
			@NonNull final AD_Sequence_StepDefData sequenceTable
	)
	{
		this.docTypeTable = docTypeTable;
		this.sequenceTable = sequenceTable;
	}

	@Then("load C_DocType:")
	public void load_C_DocType(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String docBaseType = DataTableUtil.extractStringForColumnName(row, I_C_DocType.COLUMNNAME_DocBaseType);

			final IQueryBuilder<I_C_DocType> queryBuilder = queryBL.createQueryBuilder(I_C_DocType.class)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_C_DocType.COLUMNNAME_DocBaseType, docBaseType);

			final Boolean isDefault = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT." + I_C_DocType.COLUMNNAME_IsDefault, null);
			if (isDefault != null)
			{
				queryBuilder.addEqualsFilter(I_C_DocType.COLUMNNAME_IsDefault, isDefault);
			}

			final String docSubType = DataTableUtil.extractNullableStringForColumnName(row, "OPT." + I_C_DocType.COLUMNNAME_DocSubType);
			if (Check.isNotBlank(docSubType))
			{
				queryBuilder.addEqualsFilter(I_C_DocType.COLUMNNAME_DocSubType, DataTableUtil.nullToken2Null(docSubType));
			}

			final I_C_DocType docType = queryBuilder.create().firstOnlyOrNull(I_C_DocType.class);

			assertThat(docType).isNotNull();

			final String docTypeIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_DocType.COLUMNNAME_C_DocType_ID + "." + TABLECOLUMN_IDENTIFIER);
			docTypeTable.putOrReplace(docTypeIdentifier, docType);
		}
	}

	@Given("metasfresh contains C_DocType:")
	public void metasfresh_contains_c_doctype(@NonNull final io.cucumber.datatable.DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			createC_DocType(tableRow);
		}
	}

	private void createC_DocType(@NonNull final Map<String, String> tableRow)
	{
		final I_C_DocType docTypeRecord = InterfaceWrapperHelper.newInstance(I_C_DocType.class);

		final DocBaseType docBaseType = DocBaseType.ofCode(DataTableUtil.extractStringForColumnName(tableRow, I_C_DocType.COLUMNNAME_DocBaseType));
		docTypeRecord.setDocBaseType(docBaseType.getCode());
		docTypeRecord.setIsSOTrx(docBaseType.isSOTrx());

		final String name = DataTableUtil.extractStringForColumnName(tableRow, I_C_DocType.COLUMNNAME_Name);
		docTypeRecord.setName(name);
		docTypeRecord.setPrintName(name);

		final String glCategoryName = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_GL_Category_ID + "." + I_GL_Category.COLUMNNAME_Name);
		final int glCategoryId = queryBL.createQueryBuilder(I_GL_Category.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_GL_Category.COLUMNNAME_Name, glCategoryName)
				.orderBy(I_GL_Category.COLUMNNAME_Name)
				.create()
				.firstId();
		docTypeRecord.setGL_Category_ID(glCategoryId);

		final String sequenceIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_DocNoSequence_ID + "." + TABLECOLUMN_IDENTIFIER);
		final Integer sequenceId = sequenceTable.getOptional(sequenceIdentifier)
				.map(I_AD_Sequence::getAD_Sequence_ID)
				.orElseGet(() -> Integer.parseInt(sequenceIdentifier));

		docTypeRecord.setDocNoSequence_ID(sequenceId);

		final String description = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_C_DocType.COLUMNNAME_Description);
		if (de.metas.util.Check.isNotBlank(description))
		{
			docTypeRecord.setDescription(description);
		}
		

		docTypeRecord.setEntityType("D");
		docTypeRecord.setIsDocNoControlled(true);
		InterfaceWrapperHelper.saveRecord(docTypeRecord);

		final String recordIdentifier = DataTableUtil.extractRecordIdentifier(tableRow, "C_DocType");
		docTypeTable.putOrReplace(recordIdentifier, docTypeRecord);
	}
}
