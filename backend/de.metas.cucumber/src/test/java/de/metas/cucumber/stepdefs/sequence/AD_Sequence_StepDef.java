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

package de.metas.cucumber.stepdefs.sequence;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import de.metas.javaclasses.model.I_AD_JavaClass;
import org.compiere.model.I_AD_Sequence;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/**
 * Step definitions for {@link I_AD_Sequence} — creates or updates lot-number sequences used in manufacturing.
 *
 * @cucumber.stepdef
 * @cucumber.columns
 *   <b>AD_Sequence_ID</b> — (required) identifier alias for cross-step reference<br>
 *   <b>Name</b> — (required) unique sequence name; used by the provider SysConfig key<br>
 *   <b>OPT.CustomSequenceNoProvider_JavaClass_Classname</b> — (optional) fully-qualified class name of the provider;
 *     when present the step looks up {@code AD_JavaClass} by classname and sets the FK<br>
 *   <b>OPT.StartNo</b> — (optional) starting value, default 1<br>
 * @cucumber.example
 * <pre>
 * And metasfresh contains AD_Sequence:
 *   | AD_Sequence_ID | Name        | OPT.CustomSequenceNoProvider_JavaClass_Classname                    |
 *   | seq_provider   | TestLotSeq  | de.metas.document.sequenceno.DBFunctionSequenceNoProvider           |
 * </pre>
 */
@RequiredArgsConstructor
public class AD_Sequence_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull private final AD_Sequence_StepDefData adSequenceTable;

	@And("metasfresh contains AD_Sequence:")
	public void createOrUpdate_AD_Sequence(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createOrUpdateAdSequence);
	}

	private void createOrUpdateAdSequence(@NonNull final DataTableRow row)
	{
		final String name = row.getAsString(I_AD_Sequence.COLUMNNAME_Name);

		// upsert: find existing by name, or create new
		final I_AD_Sequence seqRecord = queryBL.createQueryBuilder(I_AD_Sequence.class)
				.addEqualsFilter(I_AD_Sequence.COLUMNNAME_Name, name)
				.create()
				.firstOnlyOptional(I_AD_Sequence.class)
				.orElseGet(() -> newInstance(I_AD_Sequence.class));

		seqRecord.setName(name);
		seqRecord.setIsTableID(false);
		seqRecord.setIsAutoSequence(true);

		row.getAsOptionalInt(I_AD_Sequence.COLUMNNAME_StartNo)
				.ifPresent(seqRecord::setStartNo);

		row.getAsOptionalString(I_AD_Sequence.COLUMNNAME_CustomSequenceNoProvider_JavaClass_ID + ".Classname")
				.ifPresent(classname -> {
					final I_AD_JavaClass javaClass = queryBL.createQueryBuilder(I_AD_JavaClass.class)
							.addEqualsFilter(I_AD_JavaClass.COLUMNNAME_Classname, classname)
							.addOnlyActiveRecordsFilter()
							.create()
							.firstOnly(I_AD_JavaClass.class);
					if (javaClass != null)
					{
						seqRecord.setCustomSequenceNoProvider_JavaClass_ID(javaClass.getAD_JavaClass_ID());
					}
				});

		saveRecord(seqRecord);

		row.getAsOptionalIdentifier().ifPresent(identifier -> adSequenceTable.putOrReplace(identifier, seqRecord));
	}
}
