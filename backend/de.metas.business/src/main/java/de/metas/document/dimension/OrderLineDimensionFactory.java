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
import org.compiere.model.I_C_OrderLine;
import org.springframework.stereotype.Component;

@Component
public class OrderLineDimensionFactory implements DimensionFactory<I_C_OrderLine>
{
	@Override
	public String getHandledTableName()
	{
		return I_C_OrderLine.Table_Name;
	}

	@Override
	public Dimension getFromRecord(final I_C_OrderLine record)
	{
		return Dimension.builder()
				.projectId(ProjectId.ofRepoIdOrNull(record.getC_Project_ID()))
				.campaignId(record.getC_Campaign_ID())
				.activityId(ActivityId.ofRepoIdOrNull(record.getC_Activity_ID()))
				//.userElement1Id(record.getUser)
				.build();
	}

	@Override
	public void updateRecord(final I_C_OrderLine record, final Dimension from)
	{
		record.setC_Project_ID(ProjectId.toRepoId(from.getProjectId()));
		record.setC_Campaign_ID(from.getCampaignId());
		record.setC_Activity_ID(ActivityId.toRepoId(from.getActivityId()));
		//...
	}
}
