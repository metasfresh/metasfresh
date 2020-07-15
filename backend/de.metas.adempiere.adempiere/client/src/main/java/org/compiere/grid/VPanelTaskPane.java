package org.compiere.grid;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
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


import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JPanel;

import org.adempiere.plaf.AdempiereTaskPaneUI;
import org.adempiere.plaf.IUISubClassIDAware;
import org.jdesktop.swingx.JXTaskPane;

/**
 * A collapsible panel used to group the fields in a {@link VPanel}.
 * 
 * This is just a swing component without any logic.
 * 
 * @author tsa
 *
 */
final class VPanelTaskPane extends JXTaskPane implements IUISubClassIDAware
{
	private static final long serialVersionUID = 1054350358867570709L;

	public VPanelTaskPane()
	{
		super();
	}

	@Override
	public String getUISubClassID()
	{
		return AdempiereTaskPaneUI.UISUBCLASSID_VPanel_FieldGroup;
	}

	public void setContentPanel(final JPanel contentPanel)
	{
		final Container contentPane = getContentPane();
		contentPane.removeAll();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(contentPanel, BorderLayout.CENTER);
	}

	public void setCollapsible(boolean collapsible)
	{
		if (!collapsible)
		{
			setCollapsed(false);
		}
	}
}
