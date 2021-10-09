/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.document.dimension;

import de.metas.product.acct.api.ActivityId;
import de.metas.project.ProjectId;
import lombok.NonNull;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_OrderLine;
import org.springframework.stereotype.Component;

@Component
public class InvoiceLineDimensionFactory implements DimensionFactory<I_C_InvoiceLine>
{
	@Override
	public String getHandledTableName()
	{
		return I_C_InvoiceLine.Table_Name;
	}

	@Override
	@NonNull
	public Dimension getFromRecord(@NonNull final I_C_InvoiceLine record)
	{
		return Dimension.builder()
				.projectId(ProjectId.ofRepoIdOrNull(record.getC_Project_ID()))
				.campaignId(record.getC_Campaign_ID())
				.activityId(ActivityId.ofRepoIdOrNull(record.getC_Activity_ID()))
				.userElementString1(record.getUserElementString1())
				.userElementString2(record.getUserElementString2())
				.userElementString3(record.getUserElementString3())
				.userElementString4(record.getUserElementString4())
				.userElementString5(record.getUserElementString5())
				.userElementString6(record.getUserElementString6())
				.userElementString7(record.getUserElementString7())
				.user1_ID(record.getUser1_ID())
				.user2_ID(record.getUser2_ID())
				.build();
	}

	@Override
	public void updateRecord(final I_C_InvoiceLine record, final Dimension from)
	{
		record.setC_Project_ID(ProjectId.toRepoId(from.getProjectId()));
		record.setC_Campaign_ID(from.getCampaignId());
		record.setC_Activity_ID(ActivityId.toRepoId(from.getActivityId()));
		record.setUserElementString1(from.getUserElementString1());
		record.setUserElementString2(from.getUserElementString2());
		record.setUserElementString3(from.getUserElementString3());
		record.setUserElementString4(from.getUserElementString4());
		record.setUserElementString5(from.getUserElementString5());
		record.setUserElementString6(from.getUserElementString6());
		record.setUserElementString7(from.getUserElementString7());
		record.setUser1_ID(from.getUser1_ID());
		record.setUser2_ID(from.getUser2_ID());

	}
}
