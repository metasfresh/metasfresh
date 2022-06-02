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

package de.metas.project.workorder.callout;

import de.metas.project.ProjectId;
import de.metas.project.workorder.WOProjectStepRepository;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.api.ICalloutRecord;
import org.adempiere.ad.ui.spi.ITabCallout;
import org.adempiere.ad.ui.spi.TabCallout;
import org.compiere.model.I_C_Project_WO_Step;
import org.springframework.stereotype.Component;

@Callout(I_C_Project_WO_Step.class)
@TabCallout(I_C_Project_WO_Step.class)
@Component
public class C_Project_WO_Step implements ITabCallout
{
	private final WOProjectStepRepository woProjectStepRepository;

	public C_Project_WO_Step(final WOProjectStepRepository woProjectStepRepository) {this.woProjectStepRepository = woProjectStepRepository;}

	@Override
	public void onNew(final ICalloutRecord calloutRecord)
	{
		final I_C_Project_WO_Step stepRecord = calloutRecord.getModel(I_C_Project_WO_Step.class);
		final ProjectId projectId = ProjectId.ofRepoId(stepRecord.getC_Project_ID());

		stepRecord.setSeqNo(woProjectStepRepository.getNextSeqNo(projectId));
	}
}
