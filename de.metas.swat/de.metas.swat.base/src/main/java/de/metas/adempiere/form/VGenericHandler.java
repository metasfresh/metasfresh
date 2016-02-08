package de.metas.adempiere.form;

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


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.adempiere.util.Services;
import org.compiere.apps.ConfirmPanel;
import org.compiere.apps.form.FormPanel;
import org.compiere.minigrid.IDColumn;
import org.compiere.minigrid.MiniTable;
import org.compiere.swing.CTabbedPane;
import org.compiere.util.CLogger;

/**
 * Generic event handler for swing form views. Handles events of the confirm
 * panels and miniTable changes.
 * 
 * @author ts
 * 
 */
public class VGenericHandler {

	private static final CLogger logger = CLogger.getCLogger(VGenericHandler.class);

	private final MvcVGenPanel view;

	private final MvcMdGenForm model;

	private final FormPanel parent;

	public VGenericHandler(final MvcVGenPanel view, final MvcMdGenForm model, final FormPanel parent) {

		assert parent instanceof MvcGenForm;

		this.view = view;
		this.model = model;
		this.parent = parent;

		view.setConfirmPanelSelListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleConfirmPanelSelChange(e);
			}
		});
		view.setConfirmPanelGenListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleConfirmPanelGenChange(e);
			}
		});
		view.addTabbedPaneListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				handleTabbedPaneChanged(e);
			}
		});

		view
				.modelPropertyChange(new PropertyChangeEvent(this, MvcGenForm.PROP_GENPANEL_REFRESHBUTTON_ENABLED, null,
						false));

		final MiniTable miniTable = model.getMiniTable();

		miniTable.setRowSelectionAllowed(true);
		miniTable.getModel().addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent e) {
				handleMiniTableChanged(e);
			}
		});
	}

	private void handleConfirmPanelSelChange(ActionEvent e) {

		logger.info("Cmd=" + e.getActionCommand());
		//
		if (e.getActionCommand().equals(ConfirmPanel.A_CANCEL)) {
			parent.dispose();
			return;

		} else if (e.getActionCommand().equals(ConfirmPanel.A_OK)) {

			// it's the first tab's OK button. -> validate the user input
			// and generate our documents and stuff.
			try
			{
				((MvcGenForm) parent).validate();
			}
			catch (Exception ex)
			{
				Services.get(IClientUI.class).warn(model.getWindowNo(), ex);
			}
		}
	}

	private void handleConfirmPanelGenChange(ActionEvent e) {

		logger.info("Cmd=" + e.getActionCommand());
		//
		if (e.getActionCommand().equals(ConfirmPanel.A_CANCEL)) {
			parent.dispose();
			return;

		} else if (e.getActionCommand().equals(ConfirmPanel.A_OK)) {

			// it's the second tab's OK button. -> close the form
			parent.dispose();
		}

	}

	private void handleTabbedPaneChanged(final ChangeEvent e) {

		final CTabbedPane tabbedPane = (CTabbedPane) e.getSource();
		final int index = tabbedPane.getSelectedIndex();

		model.setSelectionActive(index == 0);
	} // stateChanged

	private void handleMiniTableChanged(final TableModelEvent e) {

		int rowsSelected = 0;

		final int rows = model.getMiniTable().getRowCount();
		for (int i = 0; i < rows; i++) {

			final IDColumn id = (IDColumn) model.getMiniTable().getValueAt(i, 0); // ID
			// is
			// in
			// column
			// 0
			if (id != null && id.isSelected())
				rowsSelected++;
		}

		// TODO find a way to be notified on table selection events
		// enable the OK button only if there are selected columns
		// view.modelPropertyChange(new PropertyChangeEvent(this,
		// GenForm.PROP_SELPANEL_OKBUTTON_ENABLED, null,
		// rowsSelected > 0));
		view.getStatusBar().setStatusDB(" " + rowsSelected + " ");
	}
}
