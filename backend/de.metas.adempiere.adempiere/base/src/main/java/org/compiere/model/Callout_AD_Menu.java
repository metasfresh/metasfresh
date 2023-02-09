package org.compiere.model;

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

import de.metas.util.Services;
import de.metas.workflow.Workflow;
import de.metas.workflow.WorkflowId;
import de.metas.workflow.service.IADWorkflowDAO;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.window.api.IADWindowDAO;

public class Callout_AD_Menu extends CalloutEngine
{
	public String onAD_Window_ID(final ICalloutField calloutField)
	{
		final I_AD_Menu menu = calloutField.getModel(I_AD_Menu.class);

		final AdWindowId windowId = AdWindowId.ofRepoIdOrNull(menu.getAD_Window_ID());
		if (windowId == null)
		{
			return NO_ERROR;
		}

		final I_AD_Window w = Services.get(IADWindowDAO.class).getWindowByIdInTrx(windowId);

		menu.setAD_Element_ID(w.getAD_Element_ID());
		menu.setName(w.getName());
		menu.setDescription(w.getDescription());
		menu.setEntityType(w.getEntityType());
		menu.setInternalName(w.getInternalName());

		return NO_ERROR;
	}

	public String onAD_Process_ID(final ICalloutField calloutField)
	{
		final I_AD_Menu menu = calloutField.getModel(I_AD_Menu.class);
		if (menu.getAD_Process_ID() <= 0)
			return "";

		I_AD_Process p = menu.getAD_Process();
		menu.setName(p.getName());
		menu.setDescription(p.getDescription());
		if (!"D".equals(p.getEntityType()))
			menu.setEntityType(p.getEntityType());
		menu.setInternalName(p.getValue());
		return "";
	}

	public String onAD_Form_ID(final ICalloutField calloutField)
	{
		final I_AD_Menu menu = calloutField.getModel(I_AD_Menu.class);
		if (menu.getAD_Form_ID() <= 0)
			return "";

		I_AD_Form f = menu.getAD_Form();
		menu.setName(f.getName());
		menu.setDescription(f.getDescription());
		if (!"D".equals(f.getEntityType()))
			menu.setEntityType(f.getEntityType());
		return "";
	}

	public String onAD_Task_ID(final ICalloutField calloutField)
	{
		final I_AD_Menu menu = calloutField.getModel(I_AD_Menu.class);
		if (menu.getAD_Task_ID() <= 0)
			return "";

		I_AD_Task t = menu.getAD_Task();
		menu.setName(t.getName());
		menu.setDescription(t.getDescription());
		if (!"D".equals(t.getEntityType()))
			menu.setEntityType(t.getEntityType());
		return "";
	}

	public String onAD_Workflow_ID(final ICalloutField calloutField)
	{
		final I_AD_Menu menu = calloutField.getModel(I_AD_Menu.class);
		final WorkflowId workflowId = WorkflowId.ofRepoIdOrNull(menu.getAD_Workflow_ID());
		if (workflowId == null)
			return "";


		final IADWorkflowDAO workflowDAO = Services.get(IADWorkflowDAO.class);
		final Workflow wf = workflowDAO.getById(workflowId);

		menu.setName(wf.getName().getDefaultValue());
		menu.setDescription(wf.getDescription().getDefaultValue());
		//menu.setEntityType(wf.getEntityType());
		//menu.setInternalName(wf.getValue());
		return "";
	}

}
