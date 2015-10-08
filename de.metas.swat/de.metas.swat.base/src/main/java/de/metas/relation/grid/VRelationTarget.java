package de.metas.relation.grid;

/*
 * #%L
 * de.metas.swat.base
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
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.adempiere.util.MiscUtils;
import org.compiere.apps.ADialog;
import org.compiere.apps.AEnv;
import org.compiere.apps.ConfirmPanel;
import org.compiere.grid.GridController;
import org.compiere.model.GridTab;
import org.compiere.model.MQuery;
import org.compiere.swing.CDialog;
import org.compiere.swing.CPanel;
import org.compiere.util.Env;

public class VRelationTarget implements IViewRelationTarget
{

	private final CDialog dialog;

	private GridTab gridTab;

	private ConfirmPanel confirmPanel = ConfirmPanel.builder()
			.withCancelButton(true)
			.build();

	private final int windowNo;

	public VRelationTarget(final int windowNo, final ModelRelationTarget model)
	{
		if (Env.isRegularOrMainWindowNo(windowNo))
		{
			dialog = new CDialog(Env.getWindow(windowNo), true);
		}
		else
		{
			dialog = new CDialog();
		}
		this.windowNo = windowNo;
		init(windowNo, model);
		new CtrlRelationTarget(model, this);
	}

	public void init(final int WindowNo, final ModelRelationTarget model)
	{
		dialog.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosed(WindowEvent e)
			{
				closeWindow();
			}
		});

		dialog.setLayout(new BorderLayout());

		//
		// Center Panel - contains Top Panel and Main Panel
		final CPanel centerPanel = new CPanel();
		centerPanel.setLayout(new BorderLayout());
		dialog.add(centerPanel, BorderLayout.CENTER);

		gridTab = MiscUtils.getGridTabForTableAndWindow(Env.getCtx(), WindowNo, model.getAdWindowTargetId(), model.getAdTableTargetId(), false);

		final GridController gc = createGridController(gridTab, WindowNo, 800, 600);

		centerPanel.add(gc, BorderLayout.CENTER);

		centerPanel.add(confirmPanel, BorderLayout.SOUTH);

		AEnv.positionCenterWindow(Env.getWindow(WindowNo), dialog);
	}

	private GridController createGridController(
			final GridTab tab,
			final int windowNo,
			final int width,
			final int height)
	{
		final GridController gc = new GridController();
		gc.initGrid(tab, true, windowNo, null, null);
		// gc.addPropertyChangeListener(propertyChangeListener);
		// gc.addDataStatusListener(mkDataStatusListener());

		if (width > 0 && height > 0)
		{
			gc.setPreferredSize(new Dimension(width, height));
		}
		return gc;
	}

	// private DataStatusListener mkDataStatusListener()
	// {
	// return new DataStatusListener()
	// {
	// @Override
	// public void dataStatusChanged(DataStatusEvent e)
	// {
	// System.out.println(e.toString());
	// }
	// };
	// }

	@Override
	public void showWindow()
	{
		dialog.setVisible(true);
	}

	@Override
	public void closeWindow()
	{
		dialog.dispose();
	}

	@Override
	public void addOKButtonListener(final ActionListener l)
	{
		confirmPanel.getOKButton().addActionListener(l);
	}

	@Override
	public void addCancelButtonListener(final ActionListener l)
	{
		confirmPanel.getCancelButton().addActionListener(l);
	}

	@Override
	public void addWhereClauseChangedListener(final PropertyChangeListener l)
	{
		gridTab.addPropertyChangeListener(new PropertyChangeListener()
		{
			@Override
			public void propertyChange(final PropertyChangeEvent evt)
			{
				if (evt.getSource() == gridTab && "query".equals(evt.getPropertyName()))
				{
					final MQuery newQuery = (MQuery)evt.getNewValue();
					final MQuery oldQuery = (MQuery)evt.getOldValue();
					final PropertyChangeEvent newEvt = new PropertyChangeEvent(VRelationTarget.this, "whereClause", mkWhereClause(oldQuery), mkWhereClause(newQuery));

					l.propertyChange(newEvt);
				}
				else
				{
					System.out.println(evt.toString());
				}
			}
		});
	}

	private String mkWhereClause(final MQuery query)
	{
		if (query == null)
		{
			return null;
		}
		return query.getWhereClause(true);
	}

	@Override
	public void showError(String message)
	{
		ADialog.error(windowNo, dialog, message);
	}
}
