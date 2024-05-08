package de.metas.ui.web.globalaction.process;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.Adempiere;

import de.metas.printing.esb.base.util.Check;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessExecutionResult.ViewOpenTarget;
import de.metas.process.ProcessExecutionResult.WebuiViewToOpen;
import de.metas.ui.web.globalaction.GlobalActionEvent;
import de.metas.ui.web.globalaction.GlobalActionHandlerResult;
import de.metas.ui.web.globalaction.GlobalActionsDispatcher;
import de.metas.ui.web.globalaction.OpenViewGlobalActionHandlerResult;
import de.metas.ui.web.process.adprocess.WebuiProcess;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewProfileId;
import de.metas.ui.web.window.datatypes.PanelLayoutType;

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

@WebuiProcess(layoutType = PanelLayoutType.SingleOverlayField)
public class GlobalActionReadProcess extends JavaProcess
{
	private final GlobalActionsDispatcher globalActionsDispatcher = Adempiere.getBean(GlobalActionsDispatcher.class);

	private static final String PARAM_Barcode = "Barcode";
	@Param(parameterName = PARAM_Barcode, mandatory = true)
	String barcode;

	@Override
	protected String doIt()
	{
		if (Check.isEmpty(barcode, true))
		{
			throw new FillMandatoryException(PARAM_Barcode);
		}

		final GlobalActionEvent event = GlobalActionEvent.parseQRCode(barcode);
		final GlobalActionHandlerResult result = globalActionsDispatcher.dispatchEvent(event);
		updateProcessResult(result);

		return MSG_OK;
	}

	private void updateProcessResult(final GlobalActionHandlerResult result)
	{
		// Tolerate null result but do nothing
		if (result == null)
		{
			return;
		}

		if (result instanceof OpenViewGlobalActionHandlerResult)
		{
			final OpenViewGlobalActionHandlerResult openViewResult = (OpenViewGlobalActionHandlerResult)result;
			final ViewId viewId = openViewResult.getViewId();
			final ViewProfileId viewProfileId = openViewResult.getViewProfileId();
			getResult().setWebuiViewToOpen(WebuiViewToOpen.builder()
					.viewId(viewId.toJson())
					.profileId(viewProfileId != null ? viewProfileId.toJson() : null)
					.target(ViewOpenTarget.ModalOverlay)
					.build());
		}
		else
		{
			throw new AdempiereException("Unknown result: " + result);
		}
	}

}
