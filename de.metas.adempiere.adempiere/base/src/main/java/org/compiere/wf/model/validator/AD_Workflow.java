package org.compiere.wf.model.validator;

import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_AD_Workflow;
import org.compiere.model.ModelValidator;
import org.compiere.wf.api.IADWorkflowBL;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;

@Interceptor(I_AD_Workflow.class)
public class AD_Workflow
{
	private final Logger logger = LogManager.getLogger(getClass());

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void validate(final I_AD_Workflow workflow, final ModelChangeType changeType)
	{
		final String errorMsg = Services.get(IADWorkflowBL.class).validateAndGetErrorMsg(workflow);

		// NOTE: don't prevent workflow from saving, just let the IsValid flag to be reset
		// Don't log warning if new, because new WFs are not valid (no start node can be set because there is none).
		if (!Check.isEmpty(errorMsg, true) && !changeType.isNew())
		{
			logger.warn("Workflow {} is marked as invalid because {}", workflow, errorMsg);
		}
	}
}
