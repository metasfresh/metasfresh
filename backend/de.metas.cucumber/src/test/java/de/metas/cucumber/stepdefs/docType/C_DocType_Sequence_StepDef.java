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

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.seqNo.AD_Sequence_StepDefData;
import de.metas.util.Services;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Sequence;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_DocType_Sequence;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.compiere.model.I_C_DocType_Sequence.COLUMNNAME_C_Country_ID;
import static org.compiere.model.I_C_DocType_Sequence.COLUMNNAME_C_DocType_ID;
import static org.compiere.model.I_C_DocType_Sequence.COLUMNNAME_DocNoSequence_ID;

public class C_DocType_Sequence_StepDef
{
	private final C_DocType_Sequence_StepDefData docTypeSequenceTable;
	private final AD_Sequence_StepDefData sequenceTable;
	private final C_DocType_StepDefData docTypeTable;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public C_DocType_Sequence_StepDef(
			@NonNull final C_DocType_Sequence_StepDefData docTypeSequenceTable,
			@NonNull final AD_Sequence_StepDefData sequenceTable,
			@NonNull final C_DocType_StepDefData docTypeTable)
	{
		this.docTypeSequenceTable = docTypeSequenceTable;
		this.sequenceTable = sequenceTable;
		this.docTypeTable = docTypeTable;
	}


	@Given("metasfresh contains C_DocType_Sequence:")
	public void metasfresh_contains_C_DocType_Sequence(@NonNull final io.cucumber.datatable.DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			createC_DocType_Sequence(tableRow);
		}
	}

	private void createC_DocType_Sequence(@NonNull final Map<String, String> tableRow)
	{
		final I_C_DocType_Sequence docTypeSequenceRecord = InterfaceWrapperHelper.newInstance(I_C_DocType_Sequence.class);

		final String docTypeIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_C_DocType_ID + "." + TABLECOLUMN_IDENTIFIER);
		final int docTypeId = docTypeTable.getOptional(docTypeIdentifier)
				.map(I_C_DocType::getC_DocType_ID)
				.orElseGet(() -> Integer.parseInt(docTypeIdentifier));

		docTypeSequenceRecord.setC_DocType_ID(docTypeId);

		final String sequenceIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_DocNoSequence_ID + "." + TABLECOLUMN_IDENTIFIER);
		final Integer sequenceId = sequenceTable.getOptional(sequenceIdentifier)
				.map(I_AD_Sequence::getAD_Sequence_ID)
				.orElseGet(() -> Integer.parseInt(sequenceIdentifier));

		docTypeSequenceRecord.setDocNoSequence_ID(sequenceId);

		final int countryId = DataTableUtil.extractIntOrMinusOneForColumnName(tableRow, "OPT." + COLUMNNAME_C_Country_ID);
		if(countryId > 0)
		{
			docTypeSequenceRecord.setC_Country_ID(countryId);
		}

		InterfaceWrapperHelper.saveRecord(docTypeSequenceRecord);

		final String recordIdentifier = DataTableUtil.extractRecordIdentifier(tableRow, "C_DocType_Sequence");
		docTypeSequenceTable.putOrReplace(recordIdentifier, docTypeSequenceRecord);
	}
}
