package de.metas.ui.web.globalaction.process;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.Adempiere;

import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessExecutionResult.ViewOpenTarget;
import de.metas.process.ProcessExecutionResult.WebuiViewToOpen;
import de.metas.ui.web.globalaction.GlobalActionEvent;
import de.metas.ui.web.globalaction.GlobalActionHandlerResult;
import de.metas.ui.web.globalaction.GlobalActionsDispatcher;
import de.metas.ui.web.globalaction.OpenViewGlobalActionHandlerResult;

/*
 * #%L
 * metasfresh-webui-api
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

public class GlobalActionReadProcess extends JavaProcess
{
	private final GlobalActionsDispatcher globalActionsDispatcher = Adempiere.getBean(GlobalActionsDispatcher.class);

	@Param(parameterName = "Barcode", mandatory = true)
	String barcode;

	@Override
	protected String doIt() throws Exception
	{
		final GlobalActionEvent event = GlobalActionEvent.parseQRCode(barcode);
		final GlobalActionHandlerResult result = globalActionsDispatcher.dispatchEvent(event);

		if (result == null)
		{
			// do nothing ?!
		}
		else if (result instanceof OpenViewGlobalActionHandlerResult)
		{
			final OpenViewGlobalActionHandlerResult openViewResult = (OpenViewGlobalActionHandlerResult)result;
			getResult().setWebuiViewToOpen(WebuiViewToOpen.builder()
					.viewId(openViewResult.getViewId().toJson())
					.target(ViewOpenTarget.ModalOverlay)
					.build());
		}
		else
		{
			throw new AdempiereException("Unknown result: " + result);
		}

		return MSG_OK;
	}

}
