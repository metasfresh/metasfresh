/**
 *
 */
package de.metas.handlingunits.client.terminal.pporder.view;

/*
 * #%L
 * de.metas.handlingunits.client
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
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.adempiere.util.beans.WeakPropertyChangeSupport;
import org.compiere.apps.form.FormFrame;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Product;
import org.compiere.util.DisplayType;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.model.I_PP_Order;

import de.metas.adempiere.beans.impl.UILoadingPropertyChangeListener;
import de.metas.adempiere.form.terminal.IComponent;
import de.metas.adempiere.form.terminal.IConfirmPanel;
import de.metas.adempiere.form.terminal.IContainer;
import de.metas.adempiere.form.terminal.IKeyLayout;
import de.metas.adempiere.form.terminal.ITerminalButton;
import de.metas.adempiere.form.terminal.ITerminalDialog;
import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.ITerminalKey;
import de.metas.adempiere.form.terminal.ITerminalKeyPanel;
import de.metas.adempiere.form.terminal.ITerminalLabel;
import de.metas.adempiere.form.terminal.ITerminalScrollPane;
import de.metas.adempiere.form.terminal.TerminalKeyListenerAdapter;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.form.terminal.context.ITerminalContextReferences;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.client.terminal.ddorder.form.DDOrderHUSelectForm;
import de.metas.handlingunits.client.terminal.editor.model.IHUKey;
import de.metas.handlingunits.client.terminal.editor.model.IHUKeyFactory;
import de.metas.handlingunits.client.terminal.editor.model.impl.HUEditorModel;
import de.metas.handlingunits.client.terminal.editor.view.HUEditorPanel;
import de.metas.handlingunits.client.terminal.lutuconfig.model.CUKey;
import de.metas.handlingunits.client.terminal.lutuconfig.view.LUTUConfigurationEditorPanel;
import de.metas.handlingunits.client.terminal.pporder.model.HUIssueModel;
import de.metas.handlingunits.client.terminal.pporder.model.ManufacturingOrderKey;
import de.metas.handlingunits.client.terminal.pporder.receipt.model.HUPPOrderReceiptHUEditorModel;
import de.metas.handlingunits.client.terminal.pporder.receipt.model.HUPPOrderReceiptModel;
import de.metas.handlingunits.client.terminal.pporder.receipt.view.HUPPOrderReceiptHUEditorPanel;
import de.metas.handlingunits.client.terminal.select.model.HUEditorCallbackAdapter;
import de.metas.handlingunits.client.terminal.select.view.IHUSelectPanel;
import de.metas.handlingunits.document.IHUDocumentLine;
import de.metas.handlingunits.inout.IHUPackingMaterialDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.purchasing.api.IBPartnerProductDAO;

/**
 * @author cg
 *
 */
public class HUIssuePanel implements IHUSelectPanel
{
	private ITerminalContext terminalContext;
	private WeakPropertyChangeSupport pcs;
	private final HUIssueModel model;

	public static final String PROPERTY_Disposed = "Disposed";
	public static final String ACTION_Receipt = "de.metas.handlingunits.client.ACTION_Receipt";
	public static final String ACTION_Receipt_HUEditor = "de.metas.handlingunits.client.ACTION_Receipt_HUEditor";
	public static final String ACTION_DDOrderPanel = "ACTION_DDOrderPanel";
	public static final String ACTION_ClosePPOrder = "de.metas.handlingunits.client.ACTION_ClosePPOrder";
	public static final String ACTION_Issue = "de.metas.handlingunits.client.ACTION_Issue";

	private IContainer panel;
	private ITerminalKeyPanel warehousePanel;
	private ITerminalKeyPanel manufacturingOrderPanel;
	private ITerminalKeyPanel bomProductsPanel;

	private IConfirmPanel confirmPanel;
	private ITerminalButton ddOrderButton;
	private ITerminalButton issueOrderButton;
	private ITerminalButton receiptOrderButton;
	private ITerminalButton receiptHUEditorButton;
	private ITerminalButton closeOrderBUtton;
	private ITerminalLabel detailsLabel;

	public HUIssuePanel(final ITerminalContext terminalContext)
	{
		super();

		Check.assumeNotNull(terminalContext, "terminalContext not null");
		this.terminalContext = terminalContext;
		pcs = terminalContext.createPropertyChangeSupport(this);
		model = new HUIssueModel(terminalContext);

		initComponents();
		initLayout();

		//
		// Model to View Synchronization
		model.addPropertyChangeListener(new UILoadingPropertyChangeListener(getComponent())
		{
			@Override
			public void propertyChange0(final PropertyChangeEvent evt)
			{
				load();
			}
		});

		terminalContext.addToDisposableComponents(this);
	}

	private void initComponents()
	{
		final ITerminalFactory factory = getTerminalFactory();

		//
		// Main Panel
		panel = terminalContext.getTerminalFactory().createContainer("fill, ins 0 0");

		//
		// warehouse
		final IKeyLayout warehouseKeyLayout = model.getWarehouseKeyLayout();
		warehouseKeyLayout.addTerminalKeyListener(new TerminalKeyListenerAdapter()
		{
			@Override
			public void keyReturned(final ITerminalKey key)
			{
				updateDetailsLabels();
			}
		});
		warehousePanel = factory.createTerminalKeyPanel(warehouseKeyLayout);

		//
		// manufacturing orders
		final IKeyLayout manufacturingOrderKeyLayout = model.getManufacturingOrderKeyLayout();
		manufacturingOrderKeyLayout.addTerminalKeyListener(new TerminalKeyListenerAdapter()
		{
			@Override
			public void keyReturned(final ITerminalKey key)
			{
				updateDetailsLabels();
			}
		});
		manufacturingOrderPanel = factory.createTerminalKeyPanel(manufacturingOrderKeyLayout);

		//
		// BOMLines
		final IKeyLayout orderBOMLineKeyLayout = model.getOrderBOMLineKeyLayout();
		orderBOMLineKeyLayout.setRows(2);
		orderBOMLineKeyLayout.setEnabledKeys(false); // we don't want to be able to select them; are here only for info
		bomProductsPanel = factory.createTerminalKeyPanel(orderBOMLineKeyLayout);

		//
		// Confirm panel & actions
		confirmPanel = factory.createConfirmPanel(true, "");
		ddOrderButton = confirmPanel.addButton(HUIssuePanel.ACTION_DDOrderPanel);
		issueOrderButton = confirmPanel.addButton(ACTION_Issue);
		receiptOrderButton = confirmPanel.addButton(HUIssuePanel.ACTION_Receipt);
		receiptHUEditorButton = confirmPanel.addButton(HUIssuePanel.ACTION_Receipt_HUEditor);
		closeOrderBUtton = confirmPanel.addButton(ACTION_ClosePPOrder);
		ddOrderButton.setEnabled(false);
		issueOrderButton.setEnabled(false);
		receiptOrderButton.setEnabled(false);
		receiptHUEditorButton.setEnabled(false);
		closeOrderBUtton.setEnabled(false);

		//
		// Confirm panel details label
		{
			detailsLabel = factory.createLabel("");
			detailsLabel.setFont(12f);
			// TODO: fix layout
			// NOTE: setting wmin will also influence the width of the whole panel and we can get following issue: 07176
			confirmPanel.addComponent(detailsLabel, "dock west, pad 0 10 0 0, growx, wmin 300px");
		}

		confirmPanel.addListener(new UILoadingPropertyChangeListener(getComponent())
		{
			@Override
			public void propertyChange0(final PropertyChangeEvent evt)
			{
				if (!IConfirmPanel.PROP_Action.equals(evt.getPropertyName()))
				{
					return;
				}

				final String action = String.valueOf(evt.getNewValue());
				if (IConfirmPanel.ACTION_OK.equals(action) || IConfirmPanel.ACTION_Cancel.equals(action))
				{
					// 06946: OK and Cancel now both close the window. Issuing moved to its own button.
					dispose();
				}
				else if (HUIssuePanel.ACTION_Issue.equals(action))
				{
					doIssueHUs();
				}
				else if (HUIssuePanel.ACTION_DDOrderPanel.equals(action))
				{
					doOpenDDOrderForm();
				}
				else if (HUIssuePanel.ACTION_Receipt.equals(action))
				{
					doReceipt();

					model.loadOrderBOMLineKeyLayout();

				}
				else if (HUIssuePanel.ACTION_Receipt_HUEditor.equals(action))
				{
					doReceiptHUEditor();
				}
				else if (HUIssuePanel.ACTION_ClosePPOrder.equals(action))
				{
					model.closeSelectedOrder();

					updateDetailsLabels();
				}
			}

		});
	}

	private void initLayout()
	{
		createPanel(panel, warehousePanel, "dock north, growx, hmin 15%");

		createPanel(panel, manufacturingOrderPanel, "dock north, growx, hmin 45%");
		createPanel(panel, bomProductsPanel, "dock north, growx, hmin 28%");

		createPanel(panel, confirmPanel, "dock south, hmax 12%");
	}

	/**
	 * Load Model to View
	 */
	private void load()
	{
		final boolean enabled = model.isActionButtonsEnabled();
		ddOrderButton.setEnabled(enabled);
		issueOrderButton.setEnabled(enabled);
		receiptOrderButton.setEnabled(enabled);
		receiptHUEditorButton.setEnabled(enabled);
		closeOrderBUtton.setEnabled(enabled);

		updateDetailsLabels();
	}

	private void updateDetailsLabels()
	{
		final NumberFormat qtyFormat = DisplayType.getNumberFormat(DisplayType.Quantity);

		final ManufacturingOrderKey moKey = (ManufacturingOrderKey)manufacturingOrderPanel.getSelectedKey();
		if (moKey == null)
		{
			//
			// Nothing to do here
			detailsLabel.setLabel(null);
			return;
		}

		final I_PP_Order ppOrder = moKey.getPP_Order();

		final StringBuilder details = new StringBuilder("<html>")
				.append("<head>")
				.append("<style type='text/css'>")
				.append("td {")
				.append("   text-align: left;")
				.append("   width: 300px;")
				.append("}")
				.append("</style>")
				.append("</head>")
				.append("<body>")
				.append("<table border=0 padding=0 magins=0>");

		//
		// BPP data
		{
			final I_C_BPartner partner = ppOrder.getC_BPartner();

			String productNo = null;
			String productEAN = null; // i.e. CU EAN
			if (partner != null)
			{
				final I_M_Product product = ppOrder.getM_Product();

				final int orgId = product.getAD_Org_ID();

				final I_C_BPartner_Product bppRaw = Services.get(IBPartnerProductDAO.class).retrieveBPartnerProductAssociation(partner, product, orgId);
				if (bppRaw != null)
				{
					final de.metas.interfaces.I_C_BPartner_Product bpp = InterfaceWrapperHelper.create(bppRaw, de.metas.interfaces.I_C_BPartner_Product.class);
					productNo = bpp.getProductNo();
					productEAN = bpp.getUPC();
				}
			}

			//
			// BPP Product No
			details.append("<tr>");

			details.append("<td>")
					.append("@").append(de.metas.interfaces.I_C_BPartner_Product.COLUMNNAME_ProductNo).append("@")
					.append(": ")
					.append(Check.isEmpty(productNo, true) ? "-" : productNo.trim())
					.append("</td>");

			//
			// BPP CU EAN
			details.append("<td>")
					.append("@").append(de.metas.interfaces.I_C_BPartner_Product.COLUMNNAME_UPC).append("@")
					.append(": ")
					.append(Check.isEmpty(productEAN, true) ? "-" : productEAN.trim())
					.append("</td>");

			details.append("</tr>");
		}

		//
		// HU PI Info
		final IHUPPOrderBL huPPOrderBL = Services.get(IHUPPOrderBL.class);
		{
			final I_M_HU_PI_Item_Product pip = huPPOrderBL
					.createReceiptLUTUConfigurationManager(ppOrder)
					.getM_HU_PI_Item_Product();

			//
			// HU PI Name
			final I_M_HU_PI huPI = pip.getM_HU_PI_Item().getM_HU_PI_Version().getM_HU_PI();

			details.append("<tr>");

			details.append("<td>")
					.append("@").append(I_M_HU_PI.COLUMNNAME_M_HU_PI_ID).append("@")
					.append(": ")
					.append(huPI.getName());

			//
			// HU PI Capacity
			final BigDecimal huPICapacity;
			if (pip != null && !pip.isInfiniteCapacity())
			{
				huPICapacity = pip.getQty();
			}
			else
			{
				huPICapacity = null;
			}

			if (huPICapacity != null)
			{
				details.append(" (")
						.append(qtyFormat.format(huPICapacity))
						.append(")");
			}

			details.append("</td>");

			//
			// HU PI Packing materials
			final Iterator<I_M_HU_PackingMaterial> packingMaterialIterator = Services.get(IHUPackingMaterialDAO.class).retrievePackingMaterials(pip)
					.iterator();

			details.append("<td>")
					.append("@").append(I_M_HU_PackingMaterial.COLUMNNAME_M_HU_PackingMaterial_ID).append("@")
					.append(": ");

			if (packingMaterialIterator.hasNext())
			{
				final I_M_HU_PackingMaterial firstPackingMaterial = packingMaterialIterator.next();
				final I_M_Product firstPMProduct = firstPackingMaterial.getM_Product();
				details.append(firstPMProduct.getName());
				while (packingMaterialIterator.hasNext())
				{
					final I_M_HU_PackingMaterial packingMaterial = packingMaterialIterator.next();
					final I_M_Product product = packingMaterial.getM_Product();
					details.append(", ").append(product.getName());
				}
			}
			else
			{
				details.append("-");
			}
			details.append("</td>");
			details.append("</tr>");
		}

		details.append("<tr>");

		//
		// Attribute set instance description
		{
			final I_M_AttributeSetInstance attributeSetInstance = ppOrder.getM_AttributeSetInstance();
			final String asiDescription;
			if (attributeSetInstance != null)
			{
				asiDescription = attributeSetInstance.getDescription();
			}
			else
			{
				asiDescription = null;
			}

			details.append("<td>")
					.append("@").append(I_M_AttributeSetInstance.COLUMNNAME_M_AttributeSetInstance_ID).append("@")
					.append(": ")
					.append(asiDescription == null ? "-" : asiDescription)
					.append("</td>");
		}

		//
		// ProductPrice
		{
			final I_C_OrderLine orderLine = Services.get(IPPOrderBL.class).getDirectOrderLine(ppOrder);

			details.append("<td>")
					.append("@").append(I_C_OrderLine.COLUMNNAME_PriceActual).append("@")
					.append(": ");

			if (orderLine != null)
			{
				details.append(orderLine.getPriceActual());
			}
			else
			{
				details.append("-");
			}
			details.append("</td>");
		}

		details.append("</tr>");

		details.append("</table>")
				.append("</body>")
				.append("</html>");

		final Properties ctx = getTerminalContext().getCtx();
		final String detailsTrl = Services.get(IMsgBL.class).parseTranslation(ctx, details.toString());
		detailsLabel.setLabel(detailsTrl);
	}

	private IComponent createPanel(final IContainer content, final IComponent component, final Object constraints)
	{
		content.add(component, constraints);

		final ITerminalScrollPane scroll = getTerminalFactory().createScrollPane(content);
		scroll.setUnitIncrementVSB(16);
		final IContainer card = getTerminalFactory().createContainer();
		card.add(scroll, "growx, growy");
		return card;
	}

	@Override
	public void addPropertyChangeListener(final PropertyChangeListener listener)
	{
		pcs.addPropertyChangeListener(listener);
	}

	@Override
	public void addPropertyChangeListener(final String propertyName, final PropertyChangeListener listener)
	{
		pcs.addPropertyChangeListener(propertyName, listener);
	}

	@Override
	public Object getComponent()
	{
		return panel.getComponent();
	}

	@Override
	public ITerminalFactory getTerminalFactory()
	{
		return terminalContext.getTerminalFactory();
	}

	@Override
	public ITerminalContext getTerminalContext()
	{
		return terminalContext;
	}

	/**
	 * Ask user to select which HUs he/she wants to issue to selected manufacturing order and then issue them.
	 */
	private void doIssueHUs()
	{
		model.doIssueHUs(new HUEditorCallbackAdapter<HUEditorModel>()
		{
			@Override
			public boolean editHUs(final HUEditorModel huEditorModel)
			{
				return HUIssuePanel.this.editHUs(huEditorModel);
			}
		});
	}

	@Override
	public boolean editHUs(final HUEditorModel huEditorModel)
	{
		final HUEditorPanel editorPanel = new HUEditorPanel(huEditorModel);
		editorPanel.setAskUserWhenCancelingChanges(true); // 07729

		// this editor's results (selected HU-Keys) are needed on this panel, because we want to issue those HUs.
		// therefore we don't want to disposed them when the modal dialog finishes and maintain the references in HUIssueModel.doIssueHUs()
		final ITerminalDialog editorDialog = getTerminalFactory().createModalDialog(this, "Edit", editorPanel); // TODO: TRL
		editorDialog.setSize(getTerminalContext().getScreenResolution());

		// Activate editor dialog and wait until user closes the window
		editorDialog.activate();

		// Return true if user actually edited the given HUs (i.e. he did not pressed Cancel)
		final boolean edited = !editorDialog.isCanceled();
		return edited;
	}

	/**
	 * Do receive products from manufacturing order
	 */
	private void doReceipt()
	{
		final ITerminalKey selectedKey = manufacturingOrderPanel.getSelectedKey();
		if (null == selectedKey)
		{
			throw new AdempiereException("No selected order");
		}
		Check.assumeInstanceOf(selectedKey, ManufacturingOrderKey.class, "selectedKey");
		final ManufacturingOrderKey manufacturingOrderKey = (ManufacturingOrderKey)selectedKey;

		final List<I_M_HU> hus;
		final CUKey cuKey;

		try (final ITerminalContextReferences references = getTerminalContext().newReferences())
		{
			final HUPPOrderReceiptModel receiptModel = model.createReceiptModel();

			final LUTUConfigurationEditorPanel receiptPanel = new LUTUConfigurationEditorPanel(receiptModel);

			final ITerminalDialog receiptDialog = getTerminalFactory().createModalDialog(this, "Receipt", receiptPanel); // TODO: trl

			// Activate editor dialog and wait until user closes the window
			receiptDialog.activate();
			if (receiptDialog.isCanceled())
			{
				return;
			}

			//
			// Refresh manufacturing order key and lines
			manufacturingOrderKey.reload();

			model.loadOrderBOMLineKeyLayout();

			hus = receiptModel.getCreatedHUs();
			cuKey = receiptModel.getSelectedCUKey();
		}
		//
		// Open HUEditor on Receipt warehouses letting the user to do further editing
		// and move the HUs forward through DD Order Line

		// 08077
		// We must associate the HUs with the transaction None because their original transaction is no more
		hus.stream().forEach(hu -> InterfaceWrapperHelper.setTrxName(hu, ITrx.TRXNAME_None));

		doReceiptHUEditor(hus, cuKey);

		// after receipt
		afterReceipt(hus);

		model.refreshAfterReceipt();
	}

	/**
	 * Open DD Order POS (Bereitsteller POS) from moving raw materials to this manufacturing order
	 */
	private void doOpenDDOrderForm()
	{
		final ITerminalKey selectedKey = manufacturingOrderPanel.getSelectedKey();
		if (null == selectedKey)
		{
			throw new AdempiereException("No selected order");
		}

		Check.assumeInstanceOf(selectedKey, ManufacturingOrderKey.class, "selectedKey");
		final DDOrderHUSelectForm form = new DDOrderHUSelectForm();
		form.init(terminalContext.getWindowNo(), new FormFrame()); // FIXME: swing specific
		final ManufacturingOrderKey selectedTerminalKey = (ManufacturingOrderKey)selectedKey;
		form.setContextManufacturingOrder(selectedTerminalKey.getPP_Order());
		form.show();
	}

	/**
	 * Open HUEditor on Receipt warehouse letting the user to do further editing and move the HUs forward through DD Order Line.
	 */
	private void doReceiptHUEditor(final List<I_M_HU> hus, final CUKey cuKey)
	{
		final ITerminalContext terminalContext = getTerminalContext();

		try (final ITerminalContextReferences references = getTerminalContext().newReferences())
		{
			//
			// Create a Root HU Key from HUs that were created
			final IHUKeyFactory keyFactory = terminalContext.getService(IHUKeyFactory.class);
			final IHUKey rootHUKey = keyFactory.createRootKey();

			//
			// Create HU Editor Model
			final HUEditorModel huEditorModel = new HUEditorModel(terminalContext);
			huEditorModel.setRootHUKey(rootHUKey);

			// We don't want any document line for the HUKeys
			final IHUDocumentLine nullDocumentLine = null;
			final List<IHUKey> huKeys = keyFactory.createKeys(hus, nullDocumentLine);
			rootHUKey.addChildren(huKeys);

			//
			// Create HU Editor Model

			// we don't need the already existing HUs to be loaded because we only want the ones to be created according with the lu tu configuration
			final boolean loadHUs = false;
			final HUPPOrderReceiptHUEditorModel editorModel = model.createReceiptHUEditorModel(cuKey, loadHUs);
			editorModel.setRootHUKey(rootHUKey);
			final HUPPOrderReceiptHUEditorPanel editorPanel = new HUPPOrderReceiptHUEditorPanel(editorModel);

			final ITerminalDialog editorDialog = getTerminalFactory().createModalDialog(this, "Edit", editorPanel);
			editorDialog.setSize(getTerminalContext().getScreenResolution());

			// Activate editor dialog
			editorDialog.activate();
		}
	}

	private void doReceiptHUEditor()
	{
		// we don't have a certain cuKey selected so we will pass null
		final CUKey cuKey = null;

		// we want the already existing HUs to be all loaded.
		final boolean loadHUs = true;

		try (final ITerminalContextReferences references = getTerminalContext().newReferences())
		{
			final HUPPOrderReceiptHUEditorModel editorModel = model.createReceiptHUEditorModel(cuKey, loadHUs);
			final HUPPOrderReceiptHUEditorPanel editorPanel = new HUPPOrderReceiptHUEditorPanel(editorModel);

			final ITerminalDialog editorDialog = getTerminalFactory().createModalDialog(this, "Edit", editorPanel);
			editorDialog.setSize(getTerminalContext().getScreenResolution());

			// Activate editor dialog
			editorDialog.activate();
		}
	}

	private boolean _disposed = false;

	@Override
	public final void dispose()
	{
		if (_disposed)
		{
			return;
		}

		terminalContext = null;
		_disposed = true;

		pcs.firePropertyChange(IHUSelectPanel.PROPERTY_Disposed, false, true);
	}

	@Override
	public boolean isDisposed()
	{
		return _disposed;
	}

	private void afterReceipt(final List<I_M_HU> hus)
	{
		if (hus == null || hus.size() <= 0)
		{
			return;
		}

		//
		// Destroy those HUs which were not selected by user to be received.
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		final IContextAware contextAware = getTerminalContext();
		final IHUContext huContext = handlingUnitsBL.createMutableHUContext(contextAware);

		for (final I_M_HU hu : hus)
		{
			if (X_M_HU.HUSTATUS_Planning.equals(hu.getHUStatus()))
			{
				handlingUnitsBL.markDestroyed(huContext, hu);
			}
		}
	}

}
