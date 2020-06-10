/*
 * #%L
 * de.metas.serviceprovider.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.serviceprovider.issue;

import de.metas.serviceprovider.model.I_S_Issue;
import de.metas.util.NumberUtils;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.time.Instant;

import static de.metas.serviceprovider.TestConstants.MOCK_EFFORT_1_30;
import static de.metas.serviceprovider.TestConstants.MOCK_NAME;
import static de.metas.serviceprovider.TestConstants.MOCK_ORG_ID;
import static de.metas.serviceprovider.TestConstants.MOCK_SEARCH_KEY;
import static de.metas.serviceprovider.TestConstants.MOCK_UOM_ID;

public class IssueTestHelper
{
	public static void buildAndStoreIssueRecord(@NonNull final IssueId issueId,
			@Nullable final IssueId parentIssueId,
			@Nullable final String issueEffort,
			@Nullable final Instant latestActivityOnIssue,
			@Nullable final Instant latestActivityOnSubIssues)
	{
		final I_S_Issue record = InterfaceWrapperHelper.newInstance(I_S_Issue.class);

		record.setS_Issue_ID(issueId.getRepoId());
		record.setS_Parent_Issue_ID(NumberUtils.asInt(parentIssueId, -1));

		record.setAD_Org_ID(MOCK_ORG_ID.getRepoId());
		record.setName(MOCK_NAME);
		record.setValue(MOCK_SEARCH_KEY);
		record.setIssueType(IssueType.EXTERNAL.getValue());
		record.setStatus(Status.IN_PROGRESS.getCode());
		record.setEffort_UOM_ID(MOCK_UOM_ID.getRepoId());
		record.setAggregatedEffort(MOCK_EFFORT_1_30);
		record.setIssueEffort(issueEffort);

		record.setLatestActivityOnSubIssues(latestActivityOnSubIssues != null ? Timestamp.from(latestActivityOnSubIssues) : null);
		record.setLatestActivity(latestActivityOnIssue != null ? Timestamp.from(latestActivityOnIssue) : null);


		InterfaceWrapperHelper.saveRecord(record);
	}
}
