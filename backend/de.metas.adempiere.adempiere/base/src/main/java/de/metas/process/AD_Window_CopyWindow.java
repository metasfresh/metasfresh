package de.metas.process;

import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.window.api.ADWindowService;
import org.adempiere.ad.window.api.WindowCopyRequest;
import org.compiere.SpringContextHolder;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class AD_Window_CopyWindow extends JavaProcess
{
	private static final String PARAM_Source_AD_Window_ID = "AD_Window_ID";
	@Param(parameterName = PARAM_Source_AD_Window_ID, mandatory = true)
	private AdWindowId sourceWindowId;

	@Param(parameterName = "IsCustomizationWindow", mandatory = true)
	private boolean isCustomizationWindow;

	private final ADWindowService adWindowService = SpringContextHolder.instance.getBean(ADWindowService.class);

	@Override
	protected String doIt()
	{
		adWindowService.copyWindow(WindowCopyRequest.builder()
				.sourceWindowId(sourceWindowId)
				.targetWindowId(AdWindowId.ofRepoId(getProcessInfo().getRecord_ID()))
				.isCustomizationWindow(isCustomizationWindow)
				.build());

		return MSG_OK;
	}

}
