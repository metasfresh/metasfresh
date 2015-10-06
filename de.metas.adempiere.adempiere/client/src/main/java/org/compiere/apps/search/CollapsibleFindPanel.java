/**
 *
 */
package org.compiere.apps.search;

/*
 * #%L
 * ADempiere ERP - Desktop Client
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.awt.Dimension;

import org.adempiere.plaf.AdempiereTaskPaneUI;
import org.adempiere.plaf.IUISubClassIDAware;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.util.Env;
import org.jdesktop.swingx.JXTaskPane;

/**
 * @author teo_sarca
 *
 */
public class CollapsibleFindPanel extends JXTaskPane implements IUISubClassIDAware
{
	/**
	 *
	 */
	private static final long serialVersionUID = 3551117375483478694L;

	public static final FindPanelBuilder builder()
	{
		return new FindPanelBuilder();
	}

	// services
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);

	private final FindPanel findPanel;

	CollapsibleFindPanel(final FindPanelBuilder builder)
	{
		super();
		setTitle(msgBL.getMsg(Env.getCtx(), "Find"));

		findPanel = builder.buildFindPanel();
		findPanel.setPreferredSize(new Dimension(500, 230)); // task 08592: the width prior to this task was 160; changing it in order to add yet another search field to the invoice candidate window.
		this.add(findPanel);

		setExpanded(!builder.isSearchPanelCollapsed());
	}
	
	@Override
	public String getUISubClassID()
	{
		return AdempiereTaskPaneUI.UISUBCLASSID_VPanel_FindPanel;
	}

	public boolean isExpanded()
	{
		return !super.isCollapsed();
	}

	public void setExpanded(final boolean expanded)
	{
		setCollapsed(!expanded);
	}

	@Override
	public final void requestFocus()
	{
		findPanel.requestFocus();
	}

	@Override
	public final boolean requestFocusInWindow()
	{
		return findPanel.requestFocusInWindow();
	}
	
	/**
	 * @return true if it's expanded and the underlying {@link FindPanel} allows focus.
	 */
	@Override
	public boolean isFocusable()
	{
		if (!isExpanded())
		{
			return false;
		}
		
		return findPanel.isFocusable();
	}
}
