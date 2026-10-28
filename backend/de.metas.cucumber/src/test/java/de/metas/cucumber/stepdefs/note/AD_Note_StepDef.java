/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.cucumber.stepdefs.note;

import de.metas.cucumber.stepdefs.AD_User_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.message.AD_Message_StepDefData;
import de.metas.cucumber.stepdefs.util.IdentifiersResolver;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Message;
import org.compiere.model.I_AD_Note;
import org.compiere.model.I_AD_UserGroup_User_Assign;
import org.compiere.util.DB;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.assertThat;

public class AD_Note_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final AD_Message_StepDefData messageTable;
	private final AD_User_StepDefData userTable;
	private final AD_Note_StepDefData noteTable;
	private final IdentifiersResolver identifiersResolver;
	private final Map<String, TableRecordReference> rememberedRecordReferences = new HashMap<>();

	public AD_Note_StepDef(
			@NonNull final AD_Message_StepDefData messageTable,
			@NonNull final AD_User_StepDefData userTable,
			@NonNull final AD_Note_StepDefData noteTable,
			@NonNull final IdentifiersResolver identifiersResolver)
	{
		this.messageTable = messageTable;
		this.userTable = userTable;
		this.noteTable = noteTable;
		this.identifiersResolver = identifiersResolver;
	}

	/**
	 * Remembers which table and record an identifier refers to, so that AD_Notes can still be looked up by it after the record was deleted.
	 *
	 * @cucumber.stepdef
	 * @cucumber.depends StepDefData: the StepDefData of the referenced record
	 * @cucumber.example
	 * <pre>
	 * And the record reference of ppOrder is remembered as ppOrderRef
	 * </pre>
	 */
	@And("^the record reference of (.*) is remembered as (.*)$")
	public void remember_record_reference(@NonNull final String identifier, @NonNull final String rememberedAs)
	{
		rememberedRecordReferences.put(rememberedAs, identifiersResolver.getTableRecordReference(StepDefDataIdentifier.ofString(identifier)));
	}

	@And("AD_Note table is reset")
	public void reset_ad_note()
	{
		DB.executeUpdateAndThrowExceptionOnFail("DELETE FROM AD_Note", ITrx.TRXNAME_None);
	}

	@And("after not more than (.*)s, validate AD_Note:$")
	public void validate_AD_Note(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String messageIdentifier = DataTableUtil.extractStringForColumnName(row, I_AD_Note.COLUMNNAME_AD_Message_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final I_AD_Message message = messageTable.get(messageIdentifier);

			final String noteIdentifier = DataTableUtil.extractRecordIdentifier(row, I_AD_Note.COLUMNNAME_AD_Note_ID);

			final Supplier<Boolean> adNoteRecordFound = () -> {
				final IQueryBuilder<I_AD_Note> noteQueryBuilder = queryBL.createQueryBuilder(I_AD_Note.class)
						.addEqualsFilter(I_AD_Note.COLUMNNAME_AD_Message_ID, message.getAD_Message_ID());

				final String userIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_AD_UserGroup_User_Assign.COLUMNNAME_AD_User_ID + "." + TABLECOLUMN_IDENTIFIER);
				if (Check.isNotBlank(userIdentifier))
				{
					final Integer userId = userTable.get(userIdentifier).getAD_User_ID();

					noteQueryBuilder.addEqualsFilter(I_AD_Note.COLUMNNAME_AD_User_ID, userId);
				}

				final I_AD_Note noteRecord = noteQueryBuilder.create().firstOnly(I_AD_Note.class);

				if (noteRecord == null)
				{
					return false;
				}

				noteTable.putOrReplace(noteIdentifier, noteRecord);
				return true;
			};

			//dev-note: tryAndWait will fail if ad_note record is not found
			StepDefUtil.tryAndWait(timeoutSec, 1000, adNoteRecordFound);
		}
	}

	/**
	 * Waits until an AD_Note with the given message exists for the referenced record (and contains the given text, if any).
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>AD_Message_ID</b> — (required, identifier-ref, see {@code load AD_Message:}) the note's AD_Message<br>
	 *   <b>Record_ID</b> — (required, identifier-ref) the record the note refers to (AD_Table_ID + Record_ID), e.g. a PP_Order<br>
	 *   <b>TextContains</b> — (optional) number/text, matched as a whole token ("10" does not match "100"), that the note's TextMsg or message parameters must contain, e.g. a qty<br>
	 *   <b>Identifier</b> — (optional) alias under which the found AD_Note is stored<br>
	 * @cucumber.depends StepDefData: AD_Message_StepDefData, AD_Note_StepDefData, the StepDefData of the referenced record
	 * @cucumber.example
	 * <pre>
	 * And after not more than 60s, AD_Note records exist for the referenced record:
	 *   | AD_Message_ID | Record_ID | TextContains |
	 *   | msg_1         | ppOrder   | 8            |
	 * </pre>
	 */
	@And("after not more than {int}s, AD_Note records exist for the referenced record:")
	public void AD_Note_records_exist_for_referenced_record(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		for (final DataTableRow row : DataTableRows.of(dataTable).stream().collect(Collectors.toList()))
		{
			final Supplier<Boolean> noteFound = () -> {
				final Optional<I_AD_Note> note = retrieveNotesForReferencedRecord(row).stream().findFirst();
				note.ifPresent(n -> row.getAsOptionalIdentifier().ifPresent(id -> noteTable.putOrReplace(id, n)));
				return note.isPresent();
			};

			//dev-note: tryAndWait will fail if no matching AD_Note is found
			StepDefUtil.tryAndWait(timeoutSec, 1000, noteFound);
		}
	}

	/**
	 * Asserts that no AD_Note matches during the whole given time. Notes are created asynchronously, so a single immediate
	 * check would pass before the note could appear; this step therefore polls for the full duration and fails as soon as one shows up.
	 * Columns are the same as in {@code after not more than Ns, AD_Note records exist for the referenced record:}.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>AD_Message_ID</b> — (required, identifier-ref) the note's AD_Message<br>
	 *   <b>Record_ID</b> — (required, identifier-ref) the record the note refers to<br>
	 *   <b>TextContains</b> — (optional) only notes containing this number/text count<br>
	 * @cucumber.depends StepDefData: AD_Message_StepDefData, the StepDefData of the referenced record
	 * @cucumber.example
	 * <pre>
	 * And after not more than 10s, no AD_Note records exist for the referenced record:
	 *   | AD_Message_ID | Record_ID |
	 *   | msg_1         | ppOrder   |
	 * </pre>
	 */
	@And("after not more than {int}s, no AD_Note records exist for the referenced record:")
	public void no_AD_Note_records_exist_for_referenced_record(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		final List<DataTableRow> rows = DataTableRows.of(dataTable).stream().collect(Collectors.toList());
		final long deadline = System.currentTimeMillis() + timeoutSec * 1000L;
		do
		{
			for (final DataTableRow row : rows)
			{
				assertThat(retrieveNotesForReferencedRecord(row))
						.as("AD_Note records for " + row.getAsString(I_AD_Note.COLUMNNAME_Record_ID))
						.isEmpty();
			}
			Thread.sleep(1000);
		}
		while (System.currentTimeMillis() < deadline);
	}

	private List<I_AD_Note> retrieveNotesForReferencedRecord(@NonNull final DataTableRow row)
	{
		final I_AD_Message message = row.getAsIdentifier(I_AD_Note.COLUMNNAME_AD_Message_ID).lookupNotNullIn(messageTable);
		final StepDefDataIdentifier recordIdentifier = row.getAsIdentifier(I_AD_Note.COLUMNNAME_Record_ID);
		final TableRecordReference referencedRecord = rememberedRecordReferences.containsKey(recordIdentifier.getAsString())
				? rememberedRecordReferences.get(recordIdentifier.getAsString())
				: identifiersResolver.getTableRecordReference(recordIdentifier);
		final Optional<String> textContains = row.getAsOptionalString("TextContains").filter(Check::isNotBlank);

		return queryBL.createQueryBuilder(I_AD_Note.class)
				.addEqualsFilter(I_AD_Note.COLUMNNAME_AD_Message_ID, message.getAD_Message_ID())
				.addEqualsFilter(I_AD_Note.COLUMNNAME_AD_Table_ID, referencedRecord.getAD_Table_ID())
				.addEqualsFilter(I_AD_Note.COLUMNNAME_Record_ID, referencedRecord.getRecord_ID())
				.create()
				.stream(I_AD_Note.class)
				.filter(note -> textContains.map(text -> containsText(note, text)).orElse(true))
				.collect(Collectors.toList());
	}

	/**
	 * Matches {@code text} as a whole number/token: "10" does not match "100", "1.10" or "10.5".
	 */
	private static boolean containsText(@NonNull final I_AD_Note note, @NonNull final String text)
	{
		final Pattern wholeToken = Pattern.compile("(?<!\\d)(?<!\\d[.,])" + Pattern.quote(text) + "(?!\\d)(?![.,]\\d)");
		return matches(wholeToken, note.getTextMsg()) || matches(wholeToken, note.getAD_Message_ParamsJSON());
	}

	private static boolean matches(@NonNull final Pattern pattern, final String value)
	{
		return value != null && pattern.matcher(value).find();
	}
}
