/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.apps.form;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.VetoableChangeListener;
import java.sql.Timestamp;
import java.util.Properties;

import javax.swing.JLabel;

import org.adempiere.plaf.AdempierePLAF;
import org.adempiere.util.Services;
import org.compiere.apps.AEnv;
import org.compiere.apps.AWindow;
import org.compiere.apps.ConfirmPanel;
import org.compiere.apps.StatusBar;
import org.compiere.grid.GridController;
import org.compiere.grid.ed.VDate;
import org.compiere.grid.ed.VLocator;
import org.compiere.grid.ed.VLookup;
import org.compiere.model.I_M_Transaction;
import org.compiere.model.Lookup;
import org.compiere.model.MLocatorLookup;
import org.compiere.swing.CPanel;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;

import de.metas.adempiere.form.IClientUI;

/**
 * Material Transaction History
 *
 * @author Jorg Janke
 * @version $Id: VTrxMaterial.java,v 1.3 2006/07/30 00:51:28 jjanke Exp $
 */
public class VTrxMaterial extends TrxMaterial
	implements FormPanel, ActionListener, VetoableChangeListener
{
	private CPanel panel = new CPanel();

	/**	FormFrame			*/
	private FormFrame 		m_frame;
	/** Grid Controller		*/
	private GridController m_gridController;

	//
	private CPanel mainPanel = new CPanel();
	private BorderLayout mainLayout = new BorderLayout();
	private CPanel parameterPanel = new CPanel();
	private JLabel orgLabel = new JLabel();
	private VLookup orgField;
	private JLabel locatorLabel = new JLabel();
	private VLocator locatorField;
	private JLabel productLabel = new JLabel();
	private VLookup productField;
	private JLabel dateFLabel = new JLabel();
	private VDate dateFField;
	private JLabel dateTLabel = new JLabel();
	private VDate dateTField;
	private JLabel mtypeLabel = new JLabel();
	private VLookup mtypeField;
	private JLabel bpartnerLabel = new JLabel();
	private VLookup bpartnerField;
	//
	private GridBagLayout parameterLayout = new GridBagLayout();
	private CPanel southPanel = new CPanel();
	private BorderLayout southLayout = new BorderLayout();
	private ConfirmPanel confirmPanel = ConfirmPanel.builder()
			.withCancelButton(true)
			.withRefreshButton(true)
			.withZoomButton(true)
			.build();
	private StatusBar statusBar = new StatusBar();
	
	//
	//  Hard coded. Need a way to make it automatic
	public static String MOVEMENT_TITLE = "MOVEMENT_TITLE";

	/**
	 *	Initialize Panel
	 *  @param WindowNo window
	 *  @param frame frame
	 */
	@Override
	public void init (final int WindowNo, final FormFrame frame) throws Exception
	{
		log.info("");
		m_WindowNo = WindowNo;
		m_frame = frame;
		
		dynParameter();
		jbInit();
		dynInit();
		frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
		frame.getContentPane().add(statusBar, BorderLayout.SOUTH);
	}	//	init


	/**
	 *  Static Init
	 *  @throws Exception
	 */
	void jbInit() throws Exception
	{
		AdempierePLAF.setDefaultBackground(panel);
		mainPanel.setLayout(mainLayout);
		mainLayout.setVgap(10);
		parameterPanel.setLayout(parameterLayout);
		//
		orgLabel.setText(msgBL.translate(Env.getCtx(), I_M_Transaction.COLUMNNAME_AD_Org_ID));
		locatorLabel.setText(msgBL.translate(Env.getCtx(), I_M_Transaction.COLUMNNAME_M_Locator_ID));
		productLabel.setText(msgBL.translate(Env.getCtx(), I_M_Transaction.COLUMNNAME_M_Product_ID));
		dateFLabel.setText(msgBL.translate(Env.getCtx(), "DateFrom"));
		dateTLabel.setText(msgBL.translate(Env.getCtx(), "DateTo"));
		mtypeLabel.setText(msgBL.translate(Env.getCtx(), I_M_Transaction.COLUMNNAME_MovementType));
		bpartnerLabel.setText(msgBL.translate(Env.getCtx(), I_M_Transaction.COLUMNNAME_C_BPartner_ID));
		//
		mainPanel.add(parameterPanel, BorderLayout.NORTH);
		
		// First row
		parameterPanel.add(orgLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 0, 5), 0, 0));
		parameterPanel.add(orgField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 0, 5), 0, 0));
		parameterPanel.add(mtypeLabel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 0, 5), 0, 0));
		parameterPanel.add(mtypeField, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 0, 5), 0, 0));
		parameterPanel.add(dateFLabel, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0
			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 0, 5), 0, 0));
		parameterPanel.add(dateFField, new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 0, 5), 0, 0));

		// Second row
		parameterPanel.add(locatorLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 0, 5), 0, 0));
		parameterPanel.add(locatorField, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 0, 5), 0, 0));
		parameterPanel.add(productLabel, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 0, 5), 0, 0));
		parameterPanel.add(productField, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 0, 5), 0, 0));
		parameterPanel.add(dateTLabel, new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0
			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 0, 5), 0, 0));
		parameterPanel.add(dateTField, new GridBagConstraints(5, 1, 1, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 0, 5), 0, 0));
		
		// Third row
		parameterPanel.add(bpartnerLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
				,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 0, 5), 0, 0));
		parameterPanel.add(bpartnerField, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
			,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 0, 5), 0, 0));
		
		//
		southPanel.setLayout(southLayout);
		southPanel.add(confirmPanel, BorderLayout.NORTH);
		southPanel.add(statusBar, BorderLayout.SOUTH);
		mainPanel.add(southPanel, BorderLayout.SOUTH);
	}   //  jbInit

	/**
	 *  Initialize Parameter fields
	 *  @throws Exception if Lookups cannot be initialized
	 */
	private void dynParameter() throws Exception
	{
		Properties ctx = Env.getCtx();
		//  Organization
		final Lookup orgLookup = getLookup(I_M_Transaction.COLUMNNAME_AD_Org_ID);
		orgField = new VLookup(I_M_Transaction.COLUMNNAME_AD_Org_ID, false, false, true, orgLookup);
	//	orgField.addVetoableChangeListener(this);
		//  Locator
		final MLocatorLookup locatorLookup = new MLocatorLookup(ctx, getWindowNo());
		locatorField = new VLocator (I_M_Transaction.COLUMNNAME_M_Locator_ID, false, false, true, locatorLookup, m_WindowNo);
	//	locatorField.addVetoableChangeListener(this);
		//  Product
		final Lookup productLookup = getLookup(I_M_Transaction.COLUMNNAME_M_Product_ID);
		productField = new VLookup(I_M_Transaction.COLUMNNAME_M_Product_ID, false, false, true, productLookup);
		productField.addVetoableChangeListener(this);
		//  Movement Type
		final Lookup mtypeLookup = getLookup(I_M_Transaction.COLUMNNAME_MovementType);
		mtypeField = new VLookup(I_M_Transaction.COLUMNNAME_MovementType, false, false, true, mtypeLookup);
		//  Dates
		dateFField = new VDate("DateFrom", false, false, true, DisplayType.Date, msgBL.getMsg(Env.getCtx(), "DateFrom"));
		dateTField = new VDate("DateTo", false, false, true, DisplayType.Date, msgBL.getMsg(Env.getCtx(), "DateTo"));
		// C_BPartner_ID
		final Lookup bpartnerLookup = getLookup(I_M_Transaction.COLUMNNAME_C_BPartner_ID);
		bpartnerField = new VLookup(I_M_Transaction.COLUMNNAME_C_BPartner_ID, false, false, true, bpartnerLookup);
		//
		confirmPanel.setActionListener(this);
		statusBar.setStatusLine("");
	}   //  dynParameter

	/**
	 *  Dynamic Layout (Grid).
	 * 	Based on AD_Window: Material Transactions
	 */
	private void dynInit()
	{
		super.dynInit(statusBar);
		
		//
		// Initialize GridController
		final GridController gridController = new GridController();
		gridController.initGrid(getGridTab(), true, getWindowNo(), null, null);
		this.m_gridController = gridController;
		
		//
		// Layout: add grid controller to main panel
		mainPanel.add(gridController, BorderLayout.CENTER);
	}   //  dynInit


	/**
	 * 	Dispose
	 */
	@Override
	public void dispose()
	{
		if (m_gridController != null)
		{
			m_gridController.dispose();
		}
		m_gridController = null;
		
		super.dispose();

		orgField = null;
		locatorField = null;
		productField = null;
		mtypeField = null;
		dateFField = null;
		dateTField = null;
		//
		if (m_frame != null)
		{
			m_frame.dispose();
		}
		m_frame = null;
	}	//	dispose

	
	/**************************************************************************
	 *  Action Listener
	 *  @param e event
	 */
	@Override
	public void actionPerformed (ActionEvent e)
	{
		if (e.getActionCommand().equals(ConfirmPanel.A_CANCEL))
			dispose();
		else if (e.getActionCommand().equals(ConfirmPanel.A_REFRESH)
				|| e.getActionCommand().equals(ConfirmPanel.A_OK))
			refresh();
		else if (e.getActionCommand().equals(ConfirmPanel.A_ZOOM))
			zoom();
	}   //  actionPerformed

	
	/**************************************************************************
	 *  Property Listener
	 *  @param e event
	 */
	@Override
	public void vetoableChange (PropertyChangeEvent e)
	{
		final String propertyName = e.getPropertyName();
		if (I_M_Transaction.COLUMNNAME_M_Product_ID.equals(propertyName))
		{
			productField.setValue(e.getNewValue());
		}
		else if (I_M_Transaction.COLUMNNAME_C_BPartner_ID.equals(propertyName))
		{
			bpartnerField.setValue(e.getNewValue());
		}
	}   //  vetoableChange

	public void setProductFieldValue(final Object value)
	{
		productField.setValue(value);
		refresh();
	}

	public void setDate(final Timestamp date)
	{
		dateFField.setValue(date);
		dateTField.setValue(date);
		refresh();
	}

	/**************************************************************************
	 *  Refresh - Create Query and refresh grid
	 */
	private void refresh()
	{
		final Object organization = orgField.getValue();
		final Object locator = locatorField.getValue();
		final Object product = productField.getValue();
		final Object movementType = mtypeField.getValue();
		final Timestamp movementDateFrom = dateFField.getValue();
		final Timestamp movementDateTo = dateTField.getValue();
		final Object bpartnerId = bpartnerField.getValue();
		
		
		Services.get(IClientUI.class).executeLongOperation(panel, new Runnable()
		{
			@Override
			public void run()
			{
				refresh(organization, locator, product, movementType, movementDateFrom, movementDateTo, bpartnerId, statusBar);
			}
		});
	}   //  refresh

	/**
	 *  Zoom
	 */
	@Override
	public void zoom()
	{
		super.zoom();

		//  Zoom
		panel.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		AWindow frame = new AWindow();
		if (!frame.initWindow(AD_Window_ID, query))
		{
			panel.setCursor(Cursor.getDefaultCursor());
			return;
		}
		AEnv.addToWindowManager(frame);
		AEnv.showCenterScreen(frame);
		frame = null;
		panel.setCursor(Cursor.getDefaultCursor());
	}   //  zoom

}   //  VTrxMaterial
