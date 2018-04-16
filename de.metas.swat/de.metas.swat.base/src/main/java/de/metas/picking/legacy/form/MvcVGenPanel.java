/******************************************************************************
 * Copyright (C) 2009 Low Heng Sin                                            *
 * Copyright (C) 2009 Idalica Corporation                                     *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package de.metas.picking.legacy.form;

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
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;

import javax.swing.JScrollPane;
import javax.swing.event.ChangeListener;

import org.adempiere.plaf.AdempierePLAF;
import org.compiere.apps.ConfirmPanel;
import org.compiere.apps.StatusBar;
import org.compiere.apps.Waiting;
import org.compiere.apps.form.FormFrame;
import org.compiere.apps.form.GenForm;
import org.compiere.apps.form.VInOutGen;
import org.compiere.minigrid.MiniTable;
import org.compiere.swing.CPanel;
import org.compiere.swing.CTabbedPane;
import org.compiere.swing.CTextPane;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.i18n.Msg;
import de.metas.logging.LogManager;

/**
 * Custom form view (panel)
 * 
 */
public abstract class MvcVGenPanel extends CPanel implements IFormView {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8154208229173738517L;

	private Waiting waitIndicator;

	private final MvcMdGenForm model;

	/** FormFrame */
	private FormFrame m_frame;

	/** Logger */
	private static Logger log = LogManager.getLogger(VInOutGen.class);
	//

	private CTabbedPane tabbedPane = new CTabbedPane();
	private CPanel selPanel = new CPanel();
	private CPanel selNorthPanel = new CPanel();
	private BorderLayout selPanelLayout = new BorderLayout();

	private FlowLayout northPanelLayout = new FlowLayout();
	private ConfirmPanel confirmPanelSel = ConfirmPanel.newWithOKAndCancel();
	private ConfirmPanel confirmPanelGen = ConfirmPanel.builder()
			.withRefreshButton(true)
			.build();
	private StatusBar statusBar = new StatusBar();
	private CPanel genPanel = new CPanel();
	private BorderLayout genLayout = new BorderLayout();
	private CTextPane info = new CTextPane();
	private JScrollPane scrollPane = new JScrollPane();

	/**
	 * 
	 * @param model
	 *            we currently need a reference to the model because it contains
	 *            the miniTable.
	 * @param frame
	 */
	public MvcVGenPanel(final MvcMdGenForm model, FormFrame frame) {
		log.info("");
		this.model = model;
		m_frame = frame;

		try {
			jbInit();
			dynInit();
			frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
			frame.getContentPane().add(statusBar, BorderLayout.SOUTH);
		} catch (Exception ex) {
			log.error("init", ex);
		}
	} // init

	/**
	 * Static Init.
	 * 
	 * <pre>
	 *  selPanel (tabbed)
	 *      fOrg, fBPartner
	 *      scrollPane & miniTable
	 *  genPanel
	 *      info
	 * </pre>
	 * 
	 * @throws Exception
	 */
	final void jbInit() {
		AdempierePLAF.setDefaultBackground(this);
		//
		selPanel.setLayout(selPanelLayout);

		selNorthPanel.setLayout(northPanelLayout);
		northPanelLayout.setAlignment(FlowLayout.LEFT);
		tabbedPane.add(selPanel, Msg.getMsg(Env.getCtx(), "Select"));
		selPanel.add(selNorthPanel, BorderLayout.NORTH);
		selPanel.setName("selPanel");
		selPanel.add(confirmPanelSel, BorderLayout.SOUTH);
		selPanel.add(scrollPane, BorderLayout.CENTER);
		scrollPane.getViewport().add((MiniTable) model.getMiniTable(), null);
		//
		tabbedPane.add(genPanel, Msg.getMsg(Env.getCtx(), "Generate"));
		genPanel.setLayout(genLayout);
		genPanel.add(info, BorderLayout.CENTER);
		genPanel.setEnabled(false);
		info.setBackground(AdempierePLAF.getFieldBackground_Inactive());
		info.setEditable(false);
		genPanel.add(confirmPanelGen, BorderLayout.SOUTH);

	} // jbInit

	/**
	 * Dynamic Init. - Create GridController & Panel - AD_Column_ID from C_Order
	 */
	private void dynInit() {
		// Info
		statusBar.setStatusDB(" ");

	} // dynInit

	/**
	 * Dispose
	 */
	@Override
	public void dispose() {
		if (m_frame != null)
			m_frame.dispose();
		m_frame = null;
	} // dispose

	public final CPanel getParameterPanel() {
		return selNorthPanel;
	}

	public final StatusBar getStatusBar() {
		return statusBar;
	}

	public final void setConfirmPanelSelListener(final ActionListener l) {
		confirmPanelSel.setActionListener(l);
	}

	public final void setConfirmPanelGenListener(final ActionListener l) {
		confirmPanelGen.setActionListener(l);
	}

	public final void addTabbedPaneListener(final ChangeListener l) {
		tabbedPane.addChangeListener(l);
	}

	public final void showGentab() {
		tabbedPane.setSelectedIndex(1);
	}

	public final void setInfoText(final String text) {
		info.setText(text);
	}

	/**
	 * Reacts to {@link GenForm#PROP_UI_LOCKED} by locking/unlocking the view
	 * and displaying/undisplaying a progress bar.
	 */
	@Override
	public void modelPropertyChange(PropertyChangeEvent e) {

		if (MvcGenForm.PROP_UI_LOCKED.equalsIgnoreCase(e.getPropertyName())) {

			final boolean locked = (Boolean) e.getNewValue();
			if (locked) {

				if (waitIndicator != null) {
					waitIndicator.dispose();
				}
				waitIndicator = new Waiting(m_frame, Msg.getMsg(Env.getCtx(), "Processing"), false, 0);
				waitIndicator.toFront();
				waitIndicator.setVisible(true);

				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

			} else {

				if (waitIndicator != null) {
					waitIndicator.dispose();
					waitIndicator.setVisible(false);
				}
				waitIndicator = null;
				setCursor(Cursor.getDefaultCursor());
			}
			m_frame.setEnabled(!locked);

		} else if (MvcGenForm.PROP_SELPANEL_OKBUTTON_ENABLED.equalsIgnoreCase(e.getPropertyName())) {

			final Boolean enabled = (Boolean) e.getNewValue();
			confirmPanelSel.getOKButton().setEnabled(enabled == null ? false : enabled);

		} else if (MvcGenForm.PROP_GENPANEL_OKBUTTON_ENABLED.equalsIgnoreCase(e.getPropertyName())) {

			final Boolean enabled = (Boolean) e.getNewValue();
			confirmPanelGen.getOKButton().setEnabled(enabled == null ? false : enabled);

		} else if (MvcGenForm.PROP_GENPANEL_REFRESHBUTTON_ENABLED.equalsIgnoreCase(e.getPropertyName())) {

			final Boolean enabled = (Boolean) e.getNewValue();
			confirmPanelGen.getRefreshButton().setEnabled(enabled == null ? false : enabled);
		}
	}

	protected ConfirmPanel getConfirmPanelSel() {
		return confirmPanelSel;
	}
}
