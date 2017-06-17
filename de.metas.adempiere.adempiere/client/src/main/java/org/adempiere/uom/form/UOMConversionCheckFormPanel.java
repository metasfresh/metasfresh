package org.adempiere.uom.form;

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
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.math.BigDecimal;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.uom.api.IUOMConversionContext;
import org.adempiere.util.NumberUtils;
import org.adempiere.util.Services;
import org.compiere.apps.ConfirmPanel;
import org.compiere.apps.ConfirmPanelListener;
import org.compiere.apps.form.FormFrame;
import org.compiere.apps.form.FormPanel;
import org.compiere.grid.VPanel;
import org.compiere.model.GridField;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_C_UOM_Conversion;
import org.compiere.util.CacheMgt;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.ITableAwareCacheInterface;

import com.google.common.collect.ImmutableSet;

import de.metas.adempiere.model.I_M_Product;
import de.metas.i18n.IMsgBL;

/**
 * Form panel used to check and test UOM conversions.
 * 
 * @author tsa
 *
 */
public class UOMConversionCheckFormPanel implements FormPanel, VetoableChangeListener
{
	// services
	private final transient IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);

	private GridField fieldProduct;
	private GridField fieldUOM;
	private GridField fieldUOMTo;
	private GridField fieldQty;
	private GridField fieldQtyConv;
	private GridField fieldDescription;

	private Set<ConvertOnCacheResetListener> cacheListeners = ImmutableSet.<ConvertOnCacheResetListener> builder()
			.add(new ConvertOnCacheResetListener(I_C_UOM.Table_Name))
			.add(new ConvertOnCacheResetListener(I_C_UOM_Conversion.Table_Name))
			.build();

	@Override
	public void init(final int windowNo, final FormFrame frame) throws Exception
	{
		initUI(windowNo, frame);

		// Register cache listeners
		for (final ConvertOnCacheResetListener cacheListener : cacheListeners)
		{
			CacheMgt.get().register(cacheListener);
		}
	}

	private final void initUI(final int windowNo, final FormFrame frame)
	{
		final VPanel mainPanel = VPanel.newCustomFormPanel("", windowNo);
		fieldProduct = mainPanel.newFormField()
				.setColumnName("M_Product_ID")
				.setHeader(msgBL.translate(getCtx(), "M_Product_ID"))
				.setAutocomplete(true)
				.setDisplayType(DisplayType.Search)
				.setSameLine(false)
				.setBindEditorToModel(true)
				.setEditorListener(this)
				.add();

		fieldUOM = mainPanel.newFormField()
				.setColumnName("C_UOM_ID")
				.setHeader(msgBL.translate(getCtx(), "C_UOM_ID"))
				.setAutocomplete(true)
				.setDisplayType(DisplayType.TableDir)
				.setSameLine(false)
				.setBindEditorToModel(true)
				.setEditorListener(this)
				.add();

		fieldUOMTo = mainPanel.newFormField()
				.setColumnName("C_UOM_To_ID")
				.setHeader(msgBL.translate(getCtx(), "C_UOM_To_ID"))
				.setAD_Column_ID(I_C_UOM_Conversion.Table_Name, I_C_UOM_Conversion.COLUMNNAME_C_UOM_To_ID)
				.setAutocomplete(true)
				.setDisplayType(DisplayType.Table)
				.setBindEditorToModel(true)
				.setEditorListener(this)
				.add();

		fieldQty = mainPanel.newFormField()
				.setColumnName("Qty")
				.setHeader(msgBL.translate(getCtx(), "Qty"))
				.setDisplayType(DisplayType.Quantity)
				.setSameLine(false)
				.setBindEditorToModel(true)
				.setEditorListener(this)
				.add();

		fieldQtyConv = mainPanel.newFormField()
				.setColumnName("QtyConv")
				.setHeader(msgBL.translate(getCtx(), "QtyConv"))
				.setDisplayType(DisplayType.Quantity)
				.setReadOnly(true)
				.setBindEditorToModel(false) // no need, it's an output field
				.add();

		fieldDescription = mainPanel.newFormField()
				.setColumnName("Description")
				.setHeader(msgBL.translate(getCtx(), "Description"))
				.setDisplayType(DisplayType.TextLong)
				.setReadOnly(true)
				.setSameLine(false)
				.setBindEditorToModel(false) // no need, it's an output field
				.add();

		//
		// Bottom: buttons
		ConfirmPanel confirmPanel = ConfirmPanel.builder()
				.withCancelButton(false)
				.withRefreshButton(true)
				.build();
		confirmPanel.getOKButton().setVisible(false);
		confirmPanel.setConfirmPanelListener(new ConfirmPanelListener()
		{
			@Override
			public void refreshButtonPressed(ActionEvent e)
			{
				doConvert("refresh button pressed");
			}
		});

		//
		// Layout
		{
			frame.setMinimumSize(new Dimension(900, 300));
			frame.setLayout(new BorderLayout());
			frame.add(mainPanel, BorderLayout.CENTER);
			frame.add(confirmPanel, BorderLayout.SOUTH);
		}
	}

	@Override
	public void dispose()
	{
		// Unregister cache listeners
		for (final ConvertOnCacheResetListener cacheListener : cacheListeners)
		{
			CacheMgt.get().unregister(cacheListener);
		}
	}

	private final Properties getCtx()
	{
		return Env.getCtx();
	}

	@Override
	public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException
	{
		// Try apply UOM conversion
		doConvert("" + evt.getPropertyName() + " field changed");
	}

	private I_M_Product getM_Product()
	{
		return toModel(I_M_Product.class, fieldProduct.getValue());
	}

	private I_C_UOM getC_UOM()
	{
		return toModel(I_C_UOM.class, fieldUOM.getValue());
	}

	private I_C_UOM getC_UOM_To()
	{
		return toModel(I_C_UOM.class, fieldUOMTo.getValue());
	}

	private BigDecimal getQty()
	{
		final BigDecimal qty = (BigDecimal)fieldQty.getValue();
		return qty == null ? BigDecimal.ZERO : qty;
	}

	private void setQtyConv(final BigDecimal qtyConv)
	{
		fieldQtyConv.setValue(qtyConv, true);
	}

	private void setDescription(final String description)
	{
		fieldDescription.setValue(description, true);
	}

	private <T> T toModel(final Class<T> modelClass, final Object idObj)
	{
		final int id = toID(idObj);
		if (id <= 0)
		{
			return null;
		}
		return InterfaceWrapperHelper.create(getCtx(), id, modelClass, ITrx.TRXNAME_None);
	}

	private static final int toID(final Object idObj)
	{
		if (idObj == null)
		{
			return -1;
		}
		else if (idObj instanceof Number)
		{
			return ((Number)idObj).intValue();
		}
		else
		{
			return -1;
		}
	}

	private void doConvert(final String reason)
	{
		// Reset
		setDescription(null);
		setQtyConv(null);

		final I_C_UOM uomFrom = getC_UOM();
		final I_C_UOM uomTo = getC_UOM_To();
		if (uomFrom == null || uomTo == null)
		{
			return;
		}

		final I_M_Product product = getM_Product();
		final BigDecimal qty = getQty();

		try
		{
			final BigDecimal qtyConv;
			if (product != null)
			{
				final IUOMConversionContext conversionCtx = uomConversionBL.createConversionContext(product);
				qtyConv = uomConversionBL.convertQty(conversionCtx, qty, uomFrom, uomTo);
			}
			else
			{
				qtyConv = uomConversionBL.convert(getCtx(), uomFrom, uomTo, qty);
			}
			setQtyConv(qtyConv);
			setDescription("Converted "
					+ NumberUtils.stripTrailingDecimalZeros(qty) + " " + uomFrom.getUOMSymbol()
					+ " to "
					+ NumberUtils.stripTrailingDecimalZeros(qtyConv) + " " + uomTo.getUOMSymbol()
					+ "<br>Product: " + (product == null ? "-" : product.getName())
					+ "<br>Reason: " + reason);
		}
		catch (Exception e)
		{
			setDescription(e.getLocalizedMessage());

			// because this is a test form, printing the exception directly to console it's totally fine.
			// More, if we would log it as WARNING/SEVERE, an AD_Issue would be created, but we don't want that.
			e.printStackTrace();
		}
	}

	private class ConvertOnCacheResetListener implements ITableAwareCacheInterface
	{
		private final String tableName;

		public ConvertOnCacheResetListener(final String tableName)
		{
			super();
			this.tableName = tableName;
		}

		@Override
		public int reset()
		{
			doConvert("cache reset on " + tableName);
			return 1;
		}

		@Override
		public int resetForRecordId(String tableName, Object key)
		{
			doConvert("cache reset on " + tableName + ", key=" + key);
			return 1;
		}

		@Override
		public int size()
		{
			return 1;
		}

		@Override
		public String getName()
		{
			return tableName;
		}

		@Override
		public String getTableName()
		{
			return tableName;
		}
	}
}
