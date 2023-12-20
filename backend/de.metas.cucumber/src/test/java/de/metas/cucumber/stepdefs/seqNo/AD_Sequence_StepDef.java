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

package de.metas.cucumber.stepdefs.seqNo;

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.util.Services;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Sequence;

import java.util.List;
import java.util.Map;

public class AD_Sequence_StepDef
{
	private final AD_Sequence_StepDefData sequenceTable;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public AD_Sequence_StepDef(
			@NonNull final AD_Sequence_StepDefData sequenceTable
	)
	{
		this.sequenceTable = sequenceTable;
	}

	@Given("metasfresh contains AD_Sequence:")
	public void metasfresh_contains_AD_Sequence(@NonNull final io.cucumber.datatable.DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			createAD_Sequence(tableRow);
		}
	}

	private void createAD_Sequence(@NonNull final Map<String, String> tableRow)
	{
		final I_AD_Sequence adSequenceRecord = InterfaceWrapperHelper.newInstance(I_AD_Sequence.class);

		final String name = DataTableUtil.extractStringForColumnName(tableRow, I_AD_Sequence.COLUMNNAME_Name);
		adSequenceRecord.setName(name);

		final String prefix = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_AD_Sequence.COLUMNNAME_Prefix);
		if (de.metas.util.Check.isNotBlank(prefix))
		{
			adSequenceRecord.setPrefix(prefix);
		}
		final int currentNext = DataTableUtil.extractIntOrMinusOneForColumnName(tableRow, "OPT." + I_AD_Sequence.COLUMNNAME_CurrentNext);
		if (currentNext > 0)
		{
			adSequenceRecord.setCurrentNext(currentNext);
		}


		adSequenceRecord.setIsAutoSequence(true);

		InterfaceWrapperHelper.saveRecord(adSequenceRecord);

		final String recordIdentifier = DataTableUtil.extractRecordIdentifier(tableRow, "AD_Sequence");
		sequenceTable.putOrReplace(recordIdentifier, adSequenceRecord);
	}
}
