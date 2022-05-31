/*
 * #%L
 * de.metas.business
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

package de.metas.project.workorder;

import de.metas.project.ProjectId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_Project_WO_Step;
import org.springframework.stereotype.Repository;

@Repository
public class WOProjectStepRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public int getNextSeqNo(@NonNull final ProjectId projectId)
	{
		final int lastSeqNo = queryBL.createQueryBuilder(I_C_Project_WO_Step.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.maxInt(I_C_Project_WO_Step.COLUMNNAME_SeqNo);

		return lastSeqNo > 0
				? lastSeqNo / 10 * 10 + 10
				: 10;
	}
}
