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
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.message.AD_Message_StepDefData;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.I_AD_Message;
import org.compiere.model.I_AD_Note;
import org.compiere.model.I_AD_UserGroup_User_Assign;
import org.compiere.util.DB;

import java.util.Map;
import java.util.function.Supplier;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;

public class AD_Note_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final AD_Message_StepDefData messageTable;
	private final AD_User_StepDefData userTable;
	private final AD_Note_StepDefData noteTable;

	public AD_Note_StepDef(
			@NonNull final AD_Message_StepDefData messageTable,
			@NonNull final AD_User_StepDefData userTable,
			@NonNull final AD_Note_StepDefData noteTable)
	{
		this.messageTable = messageTable;
		this.userTable = userTable;
		this.noteTable = noteTable;
	}

	@And("AD_Note table is reset")
	public void reset_ad_note()
	{
		DB.executeUpdateEx("DELETE FROM AD_Note", ITrx.TRXNAME_None);
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
}
