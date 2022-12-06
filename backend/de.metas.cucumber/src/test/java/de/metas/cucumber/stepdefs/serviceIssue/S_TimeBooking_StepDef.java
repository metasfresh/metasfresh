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

package de.metas.cucumber.stepdefs.serviceIssue;

import de.metas.cucumber.stepdefs.AD_User_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.serviceprovider.model.I_S_Issue;
import de.metas.serviceprovider.model.I_S_TimeBooking;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.compiere.model.I_AD_User;

import java.sql.Timestamp;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.serviceprovider.model.I_S_TimeBooking.COLUMNNAME_AD_User_Performing_ID;
import static de.metas.serviceprovider.model.I_S_TimeBooking.COLUMNNAME_BookedDate;
import static de.metas.serviceprovider.model.I_S_TimeBooking.COLUMNNAME_HoursAndMinutes;
import static de.metas.serviceprovider.model.I_S_TimeBooking.COLUMNNAME_S_Issue_ID;
import static de.metas.serviceprovider.model.I_S_TimeBooking.COLUMNNAME_S_TimeBooking_ID;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class S_TimeBooking_StepDef
{
	private final S_Issue_StepDefData sIssueTable;
	private final S_TimeBooking_StepDefData timeBookingTable;
	private final AD_User_StepDefData userTable;

	public S_TimeBooking_StepDef(
			@NonNull final S_Issue_StepDefData sIssueTable,
			@NonNull final S_TimeBooking_StepDefData timeBookingTable,
			@NonNull final AD_User_StepDefData userTable)
	{
		this.sIssueTable = sIssueTable;
		this.timeBookingTable = timeBookingTable;
		this.userTable = userTable;
	}

	@And("metasfresh contains S_TimeBooking:")
	public void create_S_TimeBooking(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String issueIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_S_Issue_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_S_Issue issue = sIssueTable.get(issueIdentifier);

			final String hoursAndMinutes = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_HoursAndMinutes);

			final String userPerformingIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_AD_User_Performing_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_AD_User userPerforming = userTable.get(userPerformingIdentifier);

			final Timestamp bookedDate = DataTableUtil.extractDateTimestampForColumnName(row, COLUMNNAME_BookedDate);

			final String timeBookingIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_S_TimeBooking_ID + "." + TABLECOLUMN_IDENTIFIER);

			final I_S_TimeBooking timeBooking = timeBookingTable.getOptional(timeBookingIdentifier)
					.orElseGet(() -> newInstance(I_S_TimeBooking.class));

			timeBooking.setS_Issue_ID(issue.getS_Issue_ID());
			timeBooking.setHoursAndMinutes(hoursAndMinutes);
			timeBooking.setAD_User_Performing_ID(userPerforming.getAD_User_ID());
			timeBooking.setBookedDate(bookedDate);

			saveRecord(timeBooking);

			timeBookingTable.putOrReplace(timeBookingIdentifier, timeBooking);
		}
	}
}
