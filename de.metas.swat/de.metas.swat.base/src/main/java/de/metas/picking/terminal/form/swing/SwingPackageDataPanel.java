/**
 * 
 */
package de.metas.picking.terminal.form.swing;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.Format;

import javax.swing.tree.DefaultMutableTreeNode;

import org.compiere.util.DisplayType;
import org.compiere.util.Env;

import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.ITerminalKey;
import de.metas.adempiere.form.terminal.ITerminalKeyPanel;
import de.metas.adempiere.form.terminal.ITerminalLabel;
import de.metas.adempiere.form.terminal.ITerminalNumericField;
import de.metas.adempiere.form.terminal.ITerminalTextField;
import de.metas.picking.legacy.form.IPackingItem;
import de.metas.picking.legacy.form.PackingDetailsMd;
import de.metas.picking.legacy.form.PackingDetailsV;
import de.metas.picking.terminal.BoxLayout;
import de.metas.picking.terminal.ProductKey;
import de.metas.util.Check;

/**
 * @author cg
 * 
 */
public class SwingPackageDataPanel extends AbstractPackageDataPanel
{
	/**
	 * Error retrieving Jasper print!
	 */
	// private static final String ERROR_RETRIEVEING_JASPER_PRINT = "@ErrorRetrievingJasperPrint@";
	/**
	 * Unable to retrieve Picking report!
	 */
	// private static final String UNABLE_TO_RETRIEVE_PICKING_REPORT = "@UnableToRetrievePickingReport@";
	/**
	 * Error printing the report!
	 */
	// private static final String ERROR_PRINTING_THE_REPORT = "@ErrorPrintingTheReport@";

	/* package */class WeightListener implements PropertyChangeListener
	{
		@Override
		public void propertyChange(final PropertyChangeEvent evt)
		{
			final Object value = evt.getNewValue();
			BigDecimal weight = value == null ? Env.ZERO : new BigDecimal(value.toString());
			final SwingPackageBoxesItems tp = packageTerminalPanel.getProductKeysPanel();
			final ITerminalKeyPanel keypanel = tp.getProductsKeyLayoutPanel();
			final ProductKey pkey = tp.getSelectedProduct();
			if (keypanel.getKeys() != null && pkey != null)
			{
				for (final ITerminalKey key : keypanel.getKeys())
				{
					if (key instanceof ProductKey)
					{
						final ProductKey productKey = (ProductKey)key;
						if (Check.equals(pkey.getBoxNo(), productKey.getBoxNo()) && Check.equals(pkey.getId(), productKey.getId()))
						{
							keypanel.onKeySelected(productKey);
							final PackingDetailsMd model = (PackingDetailsMd)packageTerminalPanel.getModel();
							weight = weight.setScale(2, RoundingMode.HALF_UP);
							if (!weight.equals(BigDecimal.ZERO))
							{
								final IPackingItem pi = productKey.getPackingItem();
								if (model.getPiQty() != null && model.getPiQty().signum() != 0)
								{
									pi.setWeightSingle(weight.divide(model.getPiQty(), RoundingMode.HALF_UP));
								}
								else
								{
									pi.setWeightSingle(BigDecimal.ZERO);
								}
								model.setPiWeight(weight);
							}
							getbSave().setEnabled(false);
							validateModel();
						}
					}
				}
			}
			if (ITerminalTextField.PROPERTY_ActionPerformed.equals(evt.getPropertyName()))
			{
				fProduct.requestFocus();
			}

			final BoxLayout boxLayout = (BoxLayout)packageTerminalPanel.getProductKeysPanel().getPickingSlotsKeyLayout();
			if (boxLayout != null)
			{
				boxLayout.checkKeyState();
			}
		}
	}

	private ITerminalTextField fProduct;
	private ITerminalNumericField fWeight;
	private ITerminalTextField fVolume;
	private ITerminalTextField fQty;
	private ITerminalTextField fOlProdDesc;

	public SwingPackageDataPanel(final AbstractPackageTerminalPanel basePanel)
	{
		super(basePanel);
	}

	@Override
	public void setupPackingItemPanel()
	{
		final ITerminalFactory factory = getTerminalFactory();

		final ITerminalLabel lProduct = factory.createLabel(PackingDetailsV.MSG_PACKAGE);
		fProduct = factory.createTerminalTextField(lProduct.getLabel());
		fProduct.setEditable(false);

		add(factory.createLabel(""), "wrap");

		add(lProduct, "shrink 10");
		add(fProduct, "h 20:50:80, w 40:150:200, growx, pushx, wrap, shrink 10");

		final ITerminalLabel lWeight = factory.createLabel(PackingDetailsV.MSG_WEIGHT);
		fWeight = factory.createTerminalNumericField(lWeight.getLabel(), DisplayType.Number, false, false);
		fWeight.addListener(new WeightListener());
		fWeight.setEditable(true);
		add(lWeight, "shrink 10");
		add(fWeight, "h 20:50:80, w 40:150:200, wrap, shrink 10");

		final ITerminalLabel lVolume = factory.createLabel(PackingDetailsV.MSG_VOLUME);
		fVolume = factory.createTerminalTextField(lVolume.getLabel());
		fVolume.setEditable(false);
		fVolume.addListener(this);
		add(lVolume, "shrink 10");
		add(fVolume, "h 20:50:80, w 40:150:200, wrap, shrink 10");

		final ITerminalLabel lQty = factory.createLabel(PackingDetailsV.MSG_QTY);
		fQty = factory.createTerminalTextField(lQty.getLabel());
		fQty.setEditable(false);
		add(lQty, "shrink 10");
		add(fQty, "h 20:50:80, w 40:150:200, wrap, shrink 10");

		final ITerminalLabel lOlProdDesc = factory.createLabel(PackingDetailsV.MSG_OL_PROD_DESC);
		fOlProdDesc = factory.createTerminalTextField(lOlProdDesc.getLabel());
		fOlProdDesc.setEditable(false);
		add(lOlProdDesc, "shrink 10");
		add(fOlProdDesc, "h 20:50:80, w 40:150:200, wrap,shrink 10");
	}

	@Override
	public void requestFocus()
	{
		fProduct.requestFocus();
	}

	public void setDataValues(final IPackingItem pi, final DefaultMutableTreeNode usedBin, final String product, final String qty, final String volume, final String weight,
			final boolean editableWeight, final String desc)
	{
		packageTerminalPanel.getProductKeysPanel().setPck(pi);
		packageTerminalPanel.getProductKeysPanel().setOldUsedBin(usedBin);
		setDataValues(product, qty, volume, weight, editableWeight, desc);
	}

	public void setDataValues(final String product, final String qty, final String volume, final String weight, final boolean editableWeight, final String desc)
	{
		final Format fqty = DisplayType.getNumberFormat(DisplayType.Quantity, Env.getLanguage(Env.getCtx()));
		fProduct.setText(product);
		fQty.setText(qty);
		if (qty != null)
		{
			packageTerminalPanel.getProductKeysPanel().setQtyData(qty);
		}
		fVolume.setText(fqty.format(new BigDecimal(volume)));
		fWeight.setValue(new BigDecimal(weight).setScale(4, BigDecimal.ROUND_HALF_UP));
		fWeight.setEditable(!editableWeight);
		fOlProdDesc.setText(desc);
	}

	@Override
	public void propertyChange(final PropertyChangeEvent evt)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void validateModel()
	{
		final PackingDetailsMd model = (PackingDetailsMd)packageTerminalPanel.getModel();
		final boolean shipperOk = !model.useShipper || model.selectedShipperId > 0;
		setConfirmButtonEnabled(shipperOk);
	}

	public void setConfirmButtonEnabled(final boolean enabled)
	{
		getbOK().setEnabled(enabled);
	}
}
