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
import de.metas.util.Check;
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
 *   <b>OPT.CustomSequenceNoProvider_JavaClass_ID.Classname</b> — (optional) fully-qualified class name of the provider;
 *     when present the step looks up {@code AD_JavaClass} by classname and sets the FK (fails if no such class exists)<br>
 *   <b>OPT.StartNo</b> — (optional) starting value; also seeds CurrentNext (the number actually issued), default 1,000,000<br>
 *   <b>OPT.CurrentNext</b> — (optional) explicit next-issued number; applied after the StartNo seed, so it wins if both are present<br>
 * @cucumber.example
 * <pre>
 * And metasfresh contains AD_Sequence:
 *   | AD_Sequence_ID | Name        | OPT.CustomSequenceNoProvider_JavaClass_ID.Classname                 |
 *   | seq_provider   | TestLotSeq  | de.metas.document.sequenceno.DBFunctionSequenceNoProvider           |
 * </pre>
 */
@RequiredArgsConstructor
public class AD_Sequence_StepDef
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull private final AD_Sequence_StepDefData adSequenceTable;

	@And("metasfresh contains AD_Sequence:")
	public void createOrUpdate_AD_Sequence(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_AD_Sequence.COLUMNNAME_AD_Sequence_ID)
				.forEach(this::createOrUpdateAdSequence);
	}

	private void createOrUpdateAdSequence(@NonNull final DataTableRow row)
	{
		final String name = row.getAsString(I_AD_Sequence.COLUMNNAME_Name);

		// upsert: find existing active record by name, or create new
		final I_AD_Sequence seqRecord = queryBL.createQueryBuilder(I_AD_Sequence.class)
				.addEqualsFilter(I_AD_Sequence.COLUMNNAME_Name, name)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnlyOptional(I_AD_Sequence.class)
				.orElseGet(() -> newInstance(I_AD_Sequence.class));

		seqRecord.setName(name);
		seqRecord.setIsTableID(false);
		seqRecord.setIsAutoSequence(true);

		// StartNo seeds CurrentNext as well: the number actually handed out by the document-no builder is CurrentNext
		// (a freshly created sequence defaults CurrentNext to 1,000,000), so a test that sets StartNo expects to start there.
		row.getAsOptionalInt(I_AD_Sequence.COLUMNNAME_StartNo)
				.ifPresent(startNo -> {
					seqRecord.setStartNo(startNo);
					seqRecord.setCurrentNext(startNo);
				});

		row.getAsOptionalInt(I_AD_Sequence.COLUMNNAME_CurrentNext)
				.ifPresent(seqRecord::setCurrentNext);

		row.getAsOptionalString(I_AD_Sequence.COLUMNNAME_CustomSequenceNoProvider_JavaClass_ID + ".Classname")
				.ifPresent(classname -> {
					final I_AD_JavaClass javaClass = queryBL.createQueryBuilder(I_AD_JavaClass.class)
							.addEqualsFilter(I_AD_JavaClass.COLUMNNAME_Classname, classname)
							.addOnlyActiveRecordsFilter()
							.create()
							.firstOnlyOrNull(I_AD_JavaClass.class);
					Check.assumeNotNull(javaClass, "No active AD_JavaClass found for classname={}", classname);
					seqRecord.setCustomSequenceNoProvider_JavaClass_ID(javaClass.getAD_JavaClass_ID());
				});

		saveRecord(seqRecord);

		row.getAsIdentifier(I_AD_Sequence.COLUMNNAME_AD_Sequence_ID).putOrReplace(adSequenceTable, seqRecord);
	}
}
