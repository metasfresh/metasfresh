/******************************************************************************
 * Copyright (C) 2009 Metas                                                   *
 * Copyright (C) 2009 Carlos Ruiz                                             *
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
package de.metas.shipping.compiere.form;

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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Vector;
import org.slf4j.Logger;

import de.metas.i18n.Msg;
import de.metas.logging.LogManager;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;

import org.compiere.apps.ADialog;
import org.compiere.apps.AEnv;
import org.compiere.apps.ConfirmPanel;
import org.compiere.grid.VCreateFromDialog;
import org.compiere.grid.ed.VDate;
import org.compiere.grid.ed.VLookup;
import org.compiere.model.GridTab;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.swing.CButton;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;

/**
 *  Create Shipping Packages for Shipper Transportation (UI)
 *  Allow select one package or several
 *  Filter fields -> Shipper, Package Date from/ to, Country deliver to (from m_inout)
 *
 *  @author Carlos Ruiz
 */
public class VCreateFromPackage extends CreateFromPackage implements ActionListener
{
	private VCreateFromDialog dialog;

	public VCreateFromPackage(GridTab mTab)
	{
		super(mTab);
		log.info(getGridTab().toString());
		
		dialog = new VCreateFromDialog(this, getGridTab().getWindowNo(), true);
		
		p_WindowNo = getGridTab().getWindowNo();

		try
		{
			if (!dynInit())
				return;
			jbInit();

			setInitOK(true);
		}
		catch(Exception e)
		{
			log.error("", e);
			setInitOK(false);
		}
		AEnv.positionCenterWindow(Env.getWindow(p_WindowNo), dialog);
	}   //  VCreateFrom
	
	/** Window No               */
	private int p_WindowNo;

	/**	Logger			*/
	private Logger log = LogManager.getLogger(getClass());
	
	private JLabel shipperLabel = new JLabel();
	protected VLookup shipperField;
	
	protected CLabel countryLabel = new CLabel(Msg.translate(Env.getCtx(), "C_Country_ID"));
	protected VLookup countryLookup;
	
	private CLabel dateFromLabel = new CLabel(Msg.translate(Env.getCtx(), "ShipDate"));
	protected VDate dateFromField = new VDate("DateFrom", false, false, true, DisplayType.Date, Msg.translate(Env.getCtx(), "DateFrom"));
	private CLabel dateToLabel = new CLabel("-");
	protected VDate dateToField = new VDate("DateTo", false, false, true, DisplayType.Date, Msg.translate(Env.getCtx(), "DateTo"));
	
	
	/**
	 *  Dynamic Init
	 *  @throws Exception if Lookups cannot be initialized
	 *  @return true if initialized
	 */
	public boolean dynInit() throws Exception
	{
		log.info("");
		
		super.dynInit();
		
		//Refresh button
		CButton refreshButton = ConfirmPanel.createRefreshButton(false);
		refreshButton.setMargin(new Insets (1, 10, 0, 10));
		refreshButton.setDefaultCapable(true);
		refreshButton.addActionListener(this);
		dialog.getConfirmPanel().addButton(refreshButton);
		dialog.getRootPane().setDefaultButton(refreshButton);
				
		if (getGridTab().getValue("M_ShipperTransportation_ID") == null)
		{
			ADialog.error(0, dialog, "SaveErrorRowNotFound");
			return false;
		}
		
		dialog.setTitle(getTitle());

		int AD_Column_ID = 10879;        //  M_Package.M_Shipper_ID
		MLookup lookup = MLookupFactory.get (Env.getCtx(), p_WindowNo, 0, AD_Column_ID, DisplayType.TableDir);
		shipperField = new VLookup ("M_Shipper_ID", false, false, true, lookup);
		
		countryLookup = new VLookup("C_Country_ID", false, false, true,
				MLookupFactory.get (Env.getCtx(), p_WindowNo, 0, 9109, DisplayType.Search));
		countryLabel.setLabelFor(countryLookup);
		
		return true;
	}   //  dynInit
    
	/**
	 *  Static Init.
	 *  <pre>
	 *  parameterPanel
	 *      parameterPackagePanel
	 *      parameterStdPanel
	 *          bPartner/order/invoice/shopment/licator Label/Field
	 *  dataPane
	 *  southPanel
	 *      confirmPanel
	 *      statusBar
	 *  </pre>
	 *  @throws Exception
	 */
    private void jbInit() throws Exception
    {
    	shipperLabel.setText(Msg.translate(Env.getCtx(), "M_Shipper_ID"));
    	dateFromLabel.setLabelFor(dateFromField);
    	dateFromField.setToolTipText(Msg.translate(Env.getCtx(), "DateFrom"));
    	dateToLabel.setLabelFor(dateToField);
    	dateToField.setToolTipText(Msg.translate(Env.getCtx(), "DateTo"));
    	
    	CPanel parameterPanel = dialog.getParameterPanel();
    	parameterPanel.setLayout(new BorderLayout());
    	
    	CPanel parameterPackagePanel = new CPanel();
    	parameterPackagePanel.setLayout(new GridBagLayout());
    	parameterPanel.add(parameterPackagePanel, BorderLayout.CENTER);
    	
    	parameterPackagePanel.add(shipperLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
    			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    	if (shipperField != null)
    		parameterPackagePanel.add(shipperField, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
    				,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));

    	parameterPackagePanel.add(countryLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
    			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    		parameterPackagePanel.add(countryLookup, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
    				,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));   	
    
    	parameterPackagePanel.add(dateFromLabel, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
    			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    	parameterPackagePanel.add(dateFromField, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0
    			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));
    	parameterPackagePanel.add(dateToLabel, new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0
    			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    	parameterPackagePanel.add(dateToField, new GridBagConstraints(5, 1, 1, 1, 0.0, 0.0
    			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 5, 5), 0, 0));

    }   //  jbInit

	/*************************************************************************/
	
	/**
	 *  Action Listener
	 *  @param e event
	 */
	public void actionPerformed(ActionEvent e)
	{
		log.info("Action=" + e.getActionCommand());
//		Object source = e.getSource();
		if ( e.getActionCommand().equals(ConfirmPanel.A_REFRESH) )
		{
			Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR);
			loadPackages();
			dialog.tableChanged(null);
			Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
		}
	}   //  actionPerformed
	
	protected void loadPackages()
	{
		loadTableOIS(getPackageData(shipperField.getValue(), countryLookup.getValue(), dateFromField.getValue(), dateToField.getValue()));
	}
	
	protected void loadTableOIS (Vector<?> data)
	{
		//  Remove previous listeners
		dialog.getMiniTable().getModel().removeTableModelListener(dialog);
		//  Set Model
		DefaultTableModel model = new DefaultTableModel(data, getOISColumnNames());
		model.addTableModelListener(dialog);
		dialog.getMiniTable().setModel(model);
		// 
		
		configureMiniTable(dialog.getMiniTable());
	}
	
	/**
	 *  List total amount
	 */
	public void info()
	{
		DecimalFormat format = DisplayType.getNumberFormat(DisplayType.Amount);

		BigDecimal total = new BigDecimal(0.0);
		int rows = dialog.getMiniTable().getRowCount();
		int count = 0;
		for (int i = 0; i < rows; i++)
		{
			if (((Boolean)dialog.getMiniTable().getValueAt(i, 0)).booleanValue())
			{
				total = total.add((BigDecimal)dialog.getMiniTable().getValueAt(i, 6)); // PackageNetTotal
				count++;
			}
		}
		dialog.setStatusLine(count, Msg.getMsg(Env.getCtx(), "Sum") + "  " + format.format(total));
	}   //  infoStatement
	
	public void showWindow()
	{
		dialog.setVisible(true);
	}
	
	public void closeWindow()
	{
		dialog.dispose();
	}
}
