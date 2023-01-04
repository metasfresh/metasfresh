/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package org.adempiere.ad.window.process;

import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.util.Services;
import org.adempiere.ad.element.api.AdTabId;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.window.api.IADWindowDAO;

public class AD_Window_CopyTabFrom extends JavaProcess
{
	private final IADWindowDAO adWindowDAO = Services.get(IADWindowDAO.class);

	@Param(parameterName = "From_Tab_ID", mandatory = true)
	private AdTabId fromTabId;

	@Override
	protected String doIt()
	{
		final AdWindowId targetWindowId = AdWindowId.ofRepoId(getRecord_ID());
		adWindowDAO.copyTabToWindow(fromTabId, targetWindowId);
		return MSG_OK;
	}
}
