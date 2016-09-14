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


import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.VetoableChangeListener;

import org.adempiere.ad.validationRule.IValidationRule;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.ConfirmPanel;
import org.compiere.apps.form.FormFrame;
import org.compiere.grid.ed.VCheckBox;
import org.compiere.grid.ed.VLookup;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.swing.CButton;
import org.compiere.swing.CLabel;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Msg;

public class PackingV extends MvcVGenPanel implements IPackingView
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8524757020191768834L;

	private VLookup fWarehouse;
	private VCheckBox fDisplayNonDeliverableItems;

	private final FormFrame frame;

	private final int windowNo;

	private final CButton refreshButton;

	private final CButton nextOneButton;

	public FormFrame getFrame()
	{
		return frame;
	}

	public PackingV(final PackingMd model, final FormFrame frame)
	{
		super(model, frame);

		this.windowNo = model.getWindowNo();
		this.frame = frame;

		refreshButton = ConfirmPanel.createRefreshButton(null);
		nextOneButton = ConfirmPanel.createNewButton(Msg.getMsg(Env.getCtx(), "Next"));
		init();

		frame.setPreferredSize(new Dimension(600, 500));
		frame.getRootPane().setDefaultButton(nextOneButton);
	}

	public final void init()
	{
		final MLookup warehouseL;
		try
		{
			warehouseL = MLookupFactory.get(
					Env.getCtx(), //ctx,
					windowNo,
					0, // Column_ID,
					DisplayType.TableDir, //AD_Reference_ID,
					Packing.PROP_M_WAREHOUSE_ID, //ColumnName,
					0, //AD_Reference_Value_ID,
					false, //IsParent,
					IValidationRule.AD_Val_Rule_ID_Null //ValidationCode
			);
		}
		catch (Exception e)
		{
			throw new AdempiereException(e);
		}
		fWarehouse = new VLookup(Packing.PROP_M_WAREHOUSE_ID, true, false, true, warehouseL);

		final CLabel lWarehouse = new CLabel();
		lWarehouse.setText(Msg.translate(Env.getCtx(), Packing.PROP_M_WAREHOUSE_ID));

		lWarehouse.setLabelFor(fWarehouse);

		getParameterPanel().add(lWarehouse);
		getParameterPanel().add(fWarehouse);
		
		//
		fDisplayNonDeliverableItems = new VCheckBox();
		fDisplayNonDeliverableItems.setText(Msg.translate(Env.getCtx(), "IsDisplayNonDeliverableItems"));
		fDisplayNonDeliverableItems.setSelected(false);
		getParameterPanel().add(fDisplayNonDeliverableItems);

		getConfirmPanelSel().addButton(refreshButton);
		getConfirmPanelSel().addButton(nextOneButton);
	}

	@Override
	public void modelPropertyChange(final PropertyChangeEvent e)
	{
		super.modelPropertyChange(e);

		if (Packing.PROP_INFO_TEXT.equalsIgnoreCase(e.getPropertyName()))
		{

			final String newVal = (String)e.getNewValue();
			setInfoText(newVal);
			showGentab();
		}
	}

	@Override
	public void addWarehouseListener(final VetoableChangeListener l)
	{
		fWarehouse.addVetoableChangeListener(l);
	}
	
	@Override
	public void addDisplayNonDeliverableItemsListener(final VetoableChangeListener l)
	{
		fDisplayNonDeliverableItems.addVetoableChangeListener(l);
	}

	@Override
	public void addRefreshListener(final ActionListener l)
	{
		refreshButton.addActionListener(l);
	}

	@Override
	public void addOpenNextOneListener(final ActionListener l)
	{
		nextOneButton.addActionListener(l);
	}

	@Override
	public void setSelectedWarehouseId(final int id)
	{
		fWarehouse.setValue(id);
	}

	@Override
	public void setDisplayNonDeliverableItems(final boolean display)
	{
		fDisplayNonDeliverableItems.setSelected(display);
	}

	@Override
	public void focusOnNextOneButton()
	{
		nextOneButton.requestFocus();
	}
}
