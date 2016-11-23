/**
 * 
 */
package de.metas.process.ui;

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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.JPopupMenu;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.apps.APanel;
import org.compiere.apps.AppsAction;
import org.compiere.grid.ed.VButton;
import org.compiere.model.I_AD_Process;
import org.compiere.swing.CMenuItem;
import org.compiere.util.Env;

/**
 * Additional record processes
 * 
 * @author tsa
 * 
 */
public class AProcess
{
	private final AProcessModel model = new AProcessModel();
	private final APanel parent;
	private AppsAction action;

	/**
	 * 
	 * @param parent
	 * @param small if <code>true</code> then use a small icon
	 * @return
	 */
	public static AppsAction createAppsAction(final APanel parent, final boolean small)
	{
		AProcess app = new AProcess(parent, small);
		return app.action;
	}

	private AProcess(final APanel parent, final boolean small)
	{
		this.parent = parent;
		initAction(small);
	}

	private void initAction(final boolean small)
	{
		this.action = AppsAction.builder()
				.setAction(model.getActionName())
				.setSmallSize(small)
				.build();
		action.setDelegate(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				showPopup();
			}
		});
	}

	private JPopupMenu getPopupMenu()
	{
		JPopupMenu popup = new JPopupMenu("ProcessMenu");

		List<I_AD_Process> processes = model.fetchProcesses(Env.getCtx(), parent.getCurrentTab());
		if (processes.size() == 0)
			return null;

		for (I_AD_Process process : processes)
		{
			CMenuItem mi = createProcessMenuItem(process);
			popup.add(mi);
		}

		return popup;
	}

	public void showPopup()
	{
		JPopupMenu popup = getPopupMenu();
		if (popup == null)
			return;

		final AbstractButton button = action.getButton();
		if (button.isShowing())
			popup.show(button, 0, button.getHeight()); // below button
	}

	private CMenuItem createProcessMenuItem(final I_AD_Process process)
	{
		final CMenuItem mi = new CMenuItem(model.getDisplayName(process));
		mi.setIcon(model.getIcon(process));
		mi.setToolTipText(model.getDescription(process));
		mi.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				startProcess(process);
			}
		});
		return mi;
	}

	private void startProcess(I_AD_Process process)
	{
		final I_AD_Process processTrl = InterfaceWrapperHelper.translate(process, I_AD_Process.class);
		final VButton button = new VButton(
				"StartProcess", // columnName,
				false, // mandatory,
				false, // isReadOnly,
				true, // isUpdateable,
				processTrl.getName(),
				processTrl.getDescription(),
				processTrl.getHelp(),
				process.getAD_Process_ID());
		parent.actionButton(button);
	}
}
