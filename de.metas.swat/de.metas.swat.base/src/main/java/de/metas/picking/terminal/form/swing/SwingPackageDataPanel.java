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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.Format;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.PrinterName;
import javax.swing.tree.DefaultMutableTreeNode;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Process;
import org.compiere.model.I_M_PackagingTree;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;

import de.metas.adempiere.form.IPackingItem;
import de.metas.adempiere.form.LegacyPackingItem;
import de.metas.adempiere.form.PackingDetailsMd;
import de.metas.adempiere.form.PackingDetailsV;
import de.metas.adempiere.form.PackingTreeModel;
import de.metas.adempiere.form.UsedBin;
import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.ITerminalKey;
import de.metas.adempiere.form.terminal.ITerminalKeyPanel;
import de.metas.adempiere.form.terminal.ITerminalLabel;
import de.metas.adempiere.form.terminal.ITerminalNumericField;
import de.metas.adempiere.form.terminal.ITerminalTextField;
import de.metas.adempiere.form.terminal.ITerminalTree;
import de.metas.adempiere.form.terminal.TerminalKeyPanel;
import de.metas.adempiere.report.jasper.client.JRClient;
import de.metas.adempiere.service.IPrinterRoutingBL;
import de.metas.adempiere.service.IPrintingService;
import de.metas.picking.terminal.BoxKey;
import de.metas.picking.terminal.BoxLayout;
import de.metas.picking.terminal.ProductKey;
import de.metas.picking.terminal.ProductLayout;
import de.metas.picking.terminal.Utils;
import de.metas.picking.terminal.Utils.PackingStates;
import de.metas.process.IADProcessDAO;
import de.metas.process.ProcessExecutor;
import de.metas.process.ProcessInfo;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;

/**
 * @author cg
 * 
 */
public class SwingPackageDataPanel extends AbstractPackageDataPanel
{
	/**
	 * Error retrieving Jasper print!
	 */
	private static final String ERROR_RETRIEVEING_JASPER_PRINT = "@ErrorRetrievingJasperPrint@";
	/**
	 * Unable to retrieve Picking report!
	 */
	private static final String UNABLE_TO_RETRIEVE_PICKING_REPORT = "@UnableToRetrievePickingReport@";
	/**
	 * Error printing the report!
	 */
	private static final String ERROR_PRINTING_THE_REPORT = "@ErrorPrintingTheReport@";

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

	@SuppressWarnings("unchecked")
	@Override
	public void propertyChange(final PropertyChangeEvent evt)
	{
		final Properties ctx = Env.getCtx();
		if (AbstractPackageDataPanel.ACTION_Delete.equals(evt.getNewValue()))
		{
			final ITerminalKey key = ((TerminalKeyPanel)packageTerminalPanel.getProductKeysPanel().getPickingSlotsKeyLayoutPanel()).getSelectedKey();
			if (key == null)
			{
				return;
			}
			final BoxKey bk = (BoxKey)key;

			if (PackingStates.open.name().equals(bk.getStatus().getName()) || PackingStates.closed.name().equals(bk.getStatus().getName()))
			{
				return;
			}

			final PackingDetailsMd model = (PackingDetailsMd)packageTerminalPanel.getModel();
			// delete from tree
			final Enumeration<DefaultMutableTreeNode> enu = bk.getNode().children();
			final List<DefaultMutableTreeNode> list = new ArrayList<DefaultMutableTreeNode>();
			while (enu.hasMoreElements())
			{
				final DefaultMutableTreeNode currentChild = enu.nextElement();
				list.add(currentChild);
				// get boxes
				final Object userObj = currentChild.getUserObject();
				if (userObj instanceof LegacyPackingItem)
				{
					final LegacyPackingItem item = (LegacyPackingItem)userObj;
					model.getPackingTreeModel().removePackedItem(ctx, currentChild, bk.getNode(), item.getQtySum(), true);
				}
			}
			if (list.size() == 0)
			{
				model.getPackingTreeModel().removeUsedBin(ctx, bk.getNode());
			}
			model.getPackingTreeModel().reload();
			getTree().expandTree(true);
			//
			packageTerminalPanel.refresh(true, true, true);
			// a change has been done
			getbSave().setEnabled(false);
		}
		else if (AbstractPackageDataPanel.ACTION_Undo.equals(evt.getNewValue()))
		{
			final PackingDetailsMd model = (PackingDetailsMd)packageTerminalPanel.getModel();
			final PackingTreeModel packingTreeModel = model.getPackingTreeModel();
			
			packingTreeModel.reset(ctx);
			if (packingTreeModel.getSavedTree() == null)
			{
				model.packer.pack(ctx, packingTreeModel, ITrx.TRXNAME_None);
			}
			else
			{
				model.avalaiableContainers = packingTreeModel.getSavedAvailableBins(ctx, packingTreeModel.getSavedTree());
			}
			getTree().expandTree(true);
			setDataValues("", "0", "0", "0", false, "");
			final SwingPackageBoxesItems productKeys = packageTerminalPanel.getProductKeysPanel();
			final BoxLayout boxLayout = (BoxLayout)productKeys.getPickingSlotsKeyLayout();
			boxLayout.resetKeys();
			final ProductLayout prodLayout = productKeys.getProductsKeyLayout();
			prodLayout.setSelectedBox(null);
			packingTreeModel.removeAllLockedNode();
			packageTerminalPanel.refresh(true, true, true);
			getbSave().setEnabled(false);
		}
		else if (AbstractPackageDataPanel.ACTION_Save.equals(evt.getNewValue()))
		{
			Utils.savePackingTree(packageTerminalPanel);
			getbSave().setEnabled(false);
		}
		else if (AbstractPackageDataPanel.ACTION_Print.equals(evt.getNewValue()))
		{
			printJasperReport();
		}
		else if (AbstractPackageDataPanel.ACTION_Logout.equals(evt.getNewValue()))
		{
			Utils.savePackingTree(packageTerminalPanel);
			packageTerminalPanel.logout();
		}
		else if (AbstractPackageDataPanel.ACTION_OK.equals(evt.getNewValue()))
		{
			final ProcessExecutor worker = packageTerminalPanel.processPackingDetails();
			if (worker != null)
			{
				worker.waitToComplete();
			}
			Utils.savePackingTree(packageTerminalPanel, true, false);
			packageTerminalPanel.getParent().dispose();
		}
		else if (AbstractPackageDataPanel.ACTION_Lock.equals(evt.getNewValue()))
		{
			final SwingPackageBoxesItems tp = packageTerminalPanel.getProductKeysPanel();
			final TerminalKeyPanel keypanel = (TerminalKeyPanel)tp.getPickingSlotsKeyLayoutPanel();
			final ProductLayout prodLayout = tp.getProductsKeyLayout();
			final BoxKey bkey = prodLayout.getSelectedBox();
			if (bkey == null)
			{
				return;
			}
			for (final ITerminalKey key : keypanel.getKeys())
			{
				if (key instanceof BoxKey)
				{
					final BoxKey boxKey = (BoxKey)key;
					if (bkey.getBoxNo() == boxKey.getBoxNo())
					{
						if (boxKey.isReady())
						{
							final BoxLayout boxLayout = (BoxLayout)tp.getPickingSlotsKeyLayout();
							final PackingStates state = boxLayout.getContainerState(boxKey.getContainer(), boxKey.getBoxNo());
							boxKey.setStatus(boxKey.updateBoxStatus(state));
							boxKey.setReady(false);
							((PackingDetailsMd)packageTerminalPanel.getModel()).getPackingTreeModel().removeLockedNode(boxKey.getNode());
							final boolean boxReady = bkey.isReady();
							prodLayout.setEnabledKeys(!boxReady);
							packageTerminalPanel.refresh(false, false, true);
							getbDelete().setEnabled(false);
						}
						else
						{
							boxKey.setStatus(boxKey.updateBoxStatus(PackingStates.ready));
							boxKey.setReady(true);
							final boolean boxReady = bkey.isReady();
							prodLayout.resetKeys();
							prodLayout.setEnabledKeys(!boxReady);
							((PackingDetailsMd)packageTerminalPanel.getModel()).getPackingTreeModel().addLockedNode(boxKey.getNode());
							packageTerminalPanel.refresh(false, false, true);
							getbDelete().setEnabled(false);
						}
						getbSave().setEnabled(false);
						break;
					}
				}

			}
		}
		else if (AbstractPackageDataPanel.ACTION_Close.equals(evt.getNewValue()))
		{
			// mark for packaging
			final SwingPackageBoxesItems tp = packageTerminalPanel.getProductKeysPanel();
			final TerminalKeyPanel keypanel = (TerminalKeyPanel)tp.getPickingSlotsKeyLayoutPanel();
			final ProductLayout prodLayout = tp.getProductsKeyLayout();
			final BoxKey bkey = prodLayout.getSelectedBox();
			if (bkey == null)
			{
				return;
			}

			//
			for (final ITerminalKey key : keypanel.getKeys())
			{
				if (key instanceof BoxKey)
				{
					final BoxKey boxKey = (BoxKey)key;
					if (bkey.getBoxNo() == boxKey.getBoxNo())
					{
						boxKey.setStatus(boxKey.updateBoxStatus(PackingStates.closed));
						((UsedBin)boxKey.getNode().getUserObject()).setMarkedForPacking(true);
						prodLayout.resetKeys();

						packageTerminalPanel.refresh(false, true, true);
						getbDelete().setEnabled(false);
						getbSave().setEnabled(false);
						getbClose().setEnabled(false);
						break;
					}
				}
			}
		}
	}

	private void saveTree()
	{
		Utils.savePackingTree(packageTerminalPanel, false, true);
	}

	private void deleteTree()
	{
		Utils.deletePackingTree(packageTerminalPanel);
	}

	private void printJasperReport()
	{
		// save tree
		saveTree();

		final PackingDetailsMd model = (PackingDetailsMd)packageTerminalPanel.getModel();
		final I_M_PackagingTree savedTree = model.getPackingTreeModel().getSavedTree();
		final int M_PackagingTree_ID = savedTree == null ? 0 : savedTree.getM_PackagingTree_ID();

		final JasperPrint jasperPrint;
		final int AD_Form_ID = packageTerminalPanel.getParent().getPickingFrame().getAD_Form_ID();
		final IADProcessDAO processPA = Services.get(IADProcessDAO.class);
		final I_AD_Process process = processPA.retrieveProcessByForm(getCtx(), AD_Form_ID);

		final ProcessInfo pi = ProcessInfo.builder()
				.setAD_Process(process)
				.addParameter("M_PackagingTree_ID", M_PackagingTree_ID)
				.addParameter("C_BPartner_ID", model.getPackingTreeModel().getBp_id())
				.addParameter("shipper", model.selectedShipperId)
				.build();

		try
		{
			jasperPrint = JRClient.get().createJasperPrint(pi);
		}
		catch (final Exception e)
		{
			throw new AdempiereException(SwingPackageDataPanel.ERROR_RETRIEVEING_JASPER_PRINT, e);
		}

		if (jasperPrint == null)
		{
			throw new AdempiereException(SwingPackageDataPanel.UNABLE_TO_RETRIEVE_PICKING_REPORT);
		}

		final JRPrintServiceExporter printExporter = new JRPrintServiceExporter();
		printExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);

		final PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
		printRequestAttributeSet.add(new Copies(1));

		printExporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);

		final IPrintingService printingService = Services.get(IPrinterRoutingBL.class).findPrintingService(
				Env.getCtx(),
				-1, // C_DocType_ID
				process.getAD_Process_ID(), // AD_Process_ID
				IPrinterRoutingBL.PRINTERTYPE_General);

		final PrintServiceAttributeSet printServiceAttributeSet = new HashPrintServiceAttributeSet();
		printServiceAttributeSet.add(new PrinterName(printingService.getPrinterName(), null));

		printExporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printServiceAttributeSet);
		printExporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, !printingService.isDirectPrint());
		printExporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);

		try
		{
			printExporter.exportReport();
		}
		catch (final JRException e)
		{
			final String msg = SwingPackageDataPanel.ERROR_PRINTING_THE_REPORT + " - " + e.getMessage();
			throw new AdempiereException(msg, e);
		}
		finally
		{
			deleteTree();
		}

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

	private ITerminalTree getTree()
	{
		final SwingPackageTerminalPanel panel = (SwingPackageTerminalPanel)packageTerminalPanel;
		return panel.getTree();
	}
}
