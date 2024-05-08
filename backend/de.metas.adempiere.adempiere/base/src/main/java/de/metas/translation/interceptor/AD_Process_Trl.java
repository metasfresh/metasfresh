/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.translation.interceptor;

import de.metas.i18n.Language;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_AD_Process;
import org.compiere.model.I_AD_Process_Trl;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_AD_Process_Trl.class)
@Component
public class AD_Process_Trl
{
	final IADProcessDAO processDAO = Services.get(IADProcessDAO.class);

	@SuppressWarnings("ConstantConditions")
	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE)
	public void updateProcessNameFromTrl(final I_AD_Process_Trl processTrl)
	{
		if (!Language.isBaseLanguage(processTrl.getAD_Language()))
		{
			return;
		}

		final I_AD_Process process = processDAO.getById(AdProcessId.ofRepoId(processTrl.getAD_Process_ID()));
		process.setName(processTrl.getName());
		process.setDescription(processTrl.getDescription());
		process.setHelp(processTrl.getHelp());
		processDAO.save(process);
	}
}
