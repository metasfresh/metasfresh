/*
 * #%L
 * de.metas.business
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

package de.metas.document.dimension;

import de.metas.product.acct.api.ActivityId;
import de.metas.project.ProjectId;
import lombok.NonNull;
import org.compiere.model.I_C_Invoice;
import org.springframework.stereotype.Component;

@Component
public class InvoiceDimensionFactory implements DimensionFactory<I_C_Invoice>
{
	@Override
	public String getHandledTableName()
	{
		return I_C_Invoice.Table_Name;
	}

	@Override
	@NonNull
	public Dimension getFromRecord(@NonNull final I_C_Invoice record)
	{
		return Dimension.builder()
				.projectId(ProjectId.ofRepoIdOrNull(record.getC_Project_ID()))
				.campaignId(record.getC_Campaign_ID())
				.activityId(ActivityId.ofRepoIdOrNull(record.getC_Activity_ID()))
				.user1_ID(record.getUser1_ID())
				.user2_ID(record.getUser2_ID())
				.build();
	}

	@Override
	public void updateRecord(@NonNull final I_C_Invoice record, @NonNull final Dimension from)
	{
		record.setC_Project_ID(ProjectId.toRepoId(from.getProjectId()));
		record.setC_Campaign_ID(from.getCampaignId());
		record.setC_Activity_ID(ActivityId.toRepoId(from.getActivityId()));
		record.setUser1_ID(from.getUser1_ID());
		record.setUser2_ID(from.getUser2_ID());
	}

}
