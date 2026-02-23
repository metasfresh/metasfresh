/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.ui.process.model.interceptor;

import de.metas.process.ProcessType;
import de.metas.ui.web.view.RelationTypeInOverlayProcess;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_AD_Process;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import static de.metas.process.ProcessType.RelationTypeInOverlay;

@Interceptor(I_AD_Process.class)
@Component
public class AD_Process
{

	/**
	 * This is an extension of de.metas.process.model.interceptor.AD_Process#setClassnameForProcessType(org.compiere.model.I_AD_Process), because the RelationTypeInOverlayProcess class is not visible there
	 *
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = I_AD_Process.COLUMNNAME_Type)
	@CalloutMethod(columnNames = I_AD_Process.COLUMNNAME_Type)
	public void setClassnameForProcessType(@NonNull final I_AD_Process process)
	{
		if (RelationTypeInOverlay.equals(ProcessType.ofCode(process.getType())))
		{
			process.setClassname(RelationTypeInOverlayProcess.class.getName());
		}
	}
}
