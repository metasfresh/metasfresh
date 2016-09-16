/**
 * 
 */
package de.metas.form.swing;

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
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.text.JTextComponent;

import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.apps.ADialog;
import org.compiere.apps.AEnv;
import org.compiere.grid.ed.MDocNumber;
import org.compiere.grid.ed.VLookup;
import org.compiere.model.GridTab;
import org.compiere.model.I_AD_Val_Rule;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.Lookup;
import org.compiere.model.MColumn;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MTable;
import org.compiere.swing.CDialog;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.swing.CTextField;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Util;

import de.metas.logging.LogManager;

/**
 * @author Teo Sarca, teo.sarca@gmail.com
 * 
 */
public class OrderLineCreate extends CDialog implements VetoableChangeListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4930221599499941808L;

	private final int windowNo;
	private final GridTab orderTab;
	private final MOrder order;
	/** Data Changed */
	private boolean changed = false;

	private final CPanel panel = new CPanel();
	private final JTextArea fInfo = new JTextArea("");
	private VLookup fProduct;
	private Lookup fProductLookup;
	private CTextField fQtyOrdered;

	public OrderLineCreate(int windowNo, GridTab orderTab) {
		super(Env.getWindow(windowNo), true);

		final I_C_Order orderBean = InterfaceWrapperHelper.create(orderTab,
				I_C_Order.class);

		if (orderBean.getC_Order_ID() <= 0) {
			throw new IllegalStateException("@C_Order_ID@=0");
		}
		this.windowNo = windowNo;
		this.orderTab = orderTab;
		this.order = new MOrder(Env.getCtx(), orderBean.getC_Order_ID(), null);

		try {
			dynInit();
		} catch (Exception e) {
			ADialog.error(windowNo, this, "Error", e.getLocalizedMessage());
			if (LogManager.isLevelFine())
				e.printStackTrace();
			dispose();
			return;
		}
	}

	private void dynInit() throws Exception {
		this.setResizable(false);
		this.setTitle(Msg.translate(Env.getCtx(), "OrderLineQuickInput"));
		//
		fInfo.setPreferredSize(new Dimension(400, 25));
		fInfo.setEditable(false);
		fInfo.setFocusable(false);
		fInfo.setWrapStyleWord(true);
		//
		{
			final MColumn c = MTable
					.get(Env.getCtx(), I_C_OrderLine.Table_Name).getColumn(
							I_C_OrderLine.COLUMNNAME_M_Product_ID);
			fProductLookup = MLookupFactory.get(Env.getCtx(), windowNo, c
					.getAD_Column_ID(), DisplayType.Search,
					c.getColumnName(),
					c.getAD_Reference_Value_ID(), c.isParent(),
					c.getAD_Val_Rule_ID());
			fProduct = new VLookup(c.getColumnName(), false, true, false,
					fProductLookup);
			fProduct.setEnabled(true);
			fProduct.setReadWrite(true);
			fProduct.setMandatory(true);
			if (c.isAutocomplete()) {
				fProduct.enableLookupAutocomplete();
			}
			fProduct.addActionListener(this);
			SwingFieldsUtil.setSelectAllOnFocus(fProduct);
		}
		//
		fQtyOrdered = new CTextField(5);
		fQtyOrdered.setDocument(new MDocNumber(DisplayType.Quantity,
				DisplayType.getNumberFormat(DisplayType.Quantity), fQtyOrdered,
				"QtyOrdered"));
		SwingFieldsUtil.setSelectAllOnFocus(fQtyOrdered);
		//
		// Listeners
		reset(true);
		fProduct.addVetoableChangeListener(this);
		//
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		panel.setLayout(new GridBagLayout());
		panel.add(fInfo, new GridBagConstraints(0, m_gridLine++, 2, 1, 0, 0,
				GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(
						5, 5, 5, 5), 0, 0));
		addLine(I_C_OrderLine.COLUMNNAME_M_Product_ID, fProduct);
		addLine(I_C_OrderLine.COLUMNNAME_QtyOrdered, fQtyOrdered);
		addLine("", null); // separator
		this.getContentPane().add(panel, BorderLayout.CENTER);
		this.pack();
	}

	private void addLine(String label, Component field) {
		//
		panel.add(new CLabel(Msg.translate(Env.getCtx(), label)),
				new GridBagConstraints(0, m_gridLine, 1, 1, 0, 0,
						GridBagConstraints.EAST, GridBagConstraints.NONE,
						new Insets(5, 10, 0, 5), 0, 0));
		if (field != null) {
			panel.add(field, new GridBagConstraints(1, m_gridLine, 1, 1, 0, 0,
					GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
					new Insets(5, 0, 0, 10), 0, 0));
			m_editors.add(field);
		}
		m_gridLine++;
	}

	private int m_gridLine = 0;
	private final List<Component> m_editors = new ArrayList<Component>();

	private void setInfo(String msg, boolean isError, Component focusField) {
		fInfo.setText(msg);
		if (isError) {
			fInfo.setForeground(Color.RED);
		} else {
			fInfo.setForeground(Color.BLACK);
		}
		//
		if (focusField != null) {
			focusField.requestFocus();
			if (focusField instanceof JTextComponent) {
				((JTextComponent) focusField).selectAll();
			}
		}
	}

	private String getValidationCode(int AD_Val_Rule_ID, String whereClause) {
		if (AD_Val_Rule_ID <= 0)
			return null;
		String code = DB.getSQLValueStringEx(null, "SELECT "
				+ I_AD_Val_Rule.COLUMNNAME_Code + " FROM "
				+ I_AD_Val_Rule.Table_Name + " WHERE "
				+ I_AD_Val_Rule.COLUMNNAME_AD_Val_Rule_ID + "=?",
				AD_Val_Rule_ID);
		StringBuffer sb = new StringBuffer();
		if (!Util.isEmpty(code, true)) {
			sb.append("(").append(code).append(")");
		}
		if (!Util.isEmpty(whereClause, true)) {
			if (sb.length() > 0)
				sb.append(" AND ");
			sb.append("(").append(whereClause).append(")");
		}
		return sb.toString();
	}

	public int getM_Product_ID() {
		Object o = fProduct.getValue();
		return o == null ? 0 : ((Number) o).intValue();
	}

	public BigDecimal getQtyOrdered() {
		Object value = fQtyOrdered.getValue();
		if (value == null || value.toString().trim().length() == 0)
			return Env.ZERO;
		else if (value instanceof BigDecimal)
			return (BigDecimal) value;
		else if (value instanceof Integer)
			return BigDecimal.valueOf(((Integer) value));
		else
			return new BigDecimal(value.toString());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == fQtyOrdered) {
			try {
				setEnabled(false);
				commit();
			} catch (Exception ex) {
				setInfo(ex.getLocalizedMessage(), true, fQtyOrdered);
			} finally {
				setEnabled(true);
			}
		}
	}

	@Override
	public void vetoableChange(PropertyChangeEvent evt)
			throws PropertyVetoException {
		if (evt.getPropertyName().equals(I_C_OrderLine.COLUMNNAME_M_Product_ID)) {
			reset(false);
			fProduct.setValue(evt.getNewValue());
			updateData(true, fProduct);
		}
	}

	private void reset(boolean clearPrimaryField) {
		fQtyOrdered.removeActionListener(this);
		if (clearPrimaryField) {
			fProduct.setValue(null);
		}
		fQtyOrdered.setValue(Env.ZERO);
		fQtyOrdered.setReadWrite(false);
		fProduct.requestFocus();
	}

	private void updateData(boolean requestFocus, Component source) {
		if (requestFocus) {
			SwingFieldsUtil.focusNextNotEmpty(source, m_editors);
		}
		fQtyOrdered.setReadWrite(true);
		fQtyOrdered.addActionListener(this);
	}

	private void commit() {
		int M_Product_ID = getM_Product_ID();
		if (M_Product_ID <= 0)
			throw new FillMandatoryException(
					I_C_OrderLine.COLUMNNAME_M_Product_ID);
		String productStr = fProduct.getDisplay();
		if (productStr == null)
			productStr = "";
		BigDecimal qty = getQtyOrdered();
		if (qty == null || qty.signum() == 0)
			throw new FillMandatoryException(
					I_C_OrderLine.COLUMNNAME_QtyOrdered);
		//
		MOrderLine line = new MOrderLine(order);
		line.setM_Product_ID(getM_Product_ID(), true);
		line.setQty(qty);
		line.saveEx();
		//
		reset(true);
		changed = true;
		refreshIncludedTabs();
		setInfo(Msg.parseTranslation(Env.getCtx(),
				"@RecordSaved@ - @M_Product_ID@:" + productStr
						+ ", @QtyOrdered@:" + qty), false, null);
	}

	public void showCenter() {
		AEnv.showCenterWindow(getOwner(), this);
	}

	public boolean isChanged() {
		return changed;
	}

	public void refreshIncludedTabs() {
		for (GridTab includedTab : orderTab.getIncludedTabs()) {
			includedTab.dataRefreshAll();
		}

	}
}
