package de.metas.invoicecandidate.form;

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

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.VetoableChangeListener;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.swing.JLabel;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.bpartner.service.IBPartnerDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.plaf.AdempierePLAF;
import org.adempiere.pricing.api.IPriceListBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.apps.ADialog;
import org.compiere.apps.ConfirmPanel;
import org.compiere.grid.ed.VLookup;
import org.compiere.grid.ed.VNumber;
import org.compiere.grid.ed.api.ISwingEditorFactory;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_Activity;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.Lookup;
import org.compiere.swing.CDialog;
import org.compiere.swing.CPanel;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.TrxRunnable2;
import org.slf4j.Logger;

import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerDAO;
import de.metas.invoicecandidate.api.InvoiceCandidate_Constants;
import de.metas.invoicecandidate.model.I_C_ILCandHandler;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.X_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.impl.ManualCandidateHandler;
import de.metas.pricing.ProductPrices;
import de.metas.pricing.exception.ProductPriceNotFoundException;
import de.metas.product.acct.api.IProductAcctDAO;
import de.metas.tax.api.ITaxBL;
import net.miginfocom.swing.MigLayout;

public class CreateInvoiceCandidateDialog
		extends CDialog
		implements ActionListener
{
	private static final long serialVersionUID = 5069654601970071033L;

	private static final Logger logger = InvoiceCandidate_Constants.getLogger(CreateInvoiceCandidateDialog.class);

	private final int windowNo;

	//
	// Form panels, fields & buttons
	//
	private final CPanel mainPanel = new CPanel();
	private final BorderLayout mainLayout = new BorderLayout();

	private final CPanel fieldPanel = new CPanel();
	private final MigLayout fieldLayout = new MigLayout();

	private final JLabel partnerLabel = new JLabel();
	private final VLookup partnerField;
	private final JLabel currencyLabel = new JLabel();
	private final VLookup currencyField;

	private final JLabel ilCandHandlerLabel = new JLabel();
	private final VLookup ilCandHandlerField;
	private final JLabel invoiceRuleLabel = new JLabel();
	private final VLookup invoiceRuleField;

	private final JLabel locationLabel = new JLabel();
	private final VLookup locationField;

	private final JLabel pricingSystemLabel = new JLabel();
	private final VLookup pricingSystemField;
	private final JLabel productLabel = new JLabel();
	private final VLookup productField;

	private final JLabel taxLabel = new JLabel();
	private final VLookup taxField;
	private final JLabel activityLabel = new JLabel();
	private final VLookup activityField;

	private final JLabel qtyOrderedLabel = new JLabel();
	private final VNumber qtyOrderedField;
	private final JLabel priceUOMLabel = new JLabel();
	private final VLookup priceUOMField;

	private final JLabel priceEnteredLabel = new JLabel();
	private final VNumber priceEnteredField;
	private final JLabel discountLabel = new JLabel();
	private final VNumber discountField;

	private final ConfirmPanel confirmPanel = ConfirmPanel.newWithOKAndCancel();

	private final int partnerId;
	private final int currencyId;
	private final Timestamp date;

	private final boolean isManual = true;
	private final boolean isSOTrx = false;

	public CreateInvoiceCandidateDialog(final Frame owner, final String title,
			final int partnerId,
			final int currencyId,
			final Timestamp date) throws HeadlessException
	{
		super(owner, title, true); // modal (always)

		//
		// Initialize window number
		windowNo = Env.createWindowNo(getContentPane());

		//
		// Initialize inherited values
		this.partnerId = partnerId;
		this.currencyId = currencyId;
		this.date = date;

		configureContextValues();

		//
		// Initialize field(s)
		final ISwingEditorFactory factory = Services.get(ISwingEditorFactory.class);

		final int tabNo = 0;
		final String tableName = I_C_Invoice_Candidate.Table_Name;
		final boolean mandatory = true;
		final boolean autocomplete = true;

		partnerField = factory.getVLookup(windowNo, tabNo, DisplayType.Search, tableName, I_C_Invoice_Candidate.COLUMNNAME_Bill_BPartner_ID, mandatory, autocomplete);
		locationField = factory.getVLookup(windowNo, tabNo, DisplayType.TableDir, tableName, I_C_Invoice_Candidate.COLUMNNAME_Bill_Location_ID, mandatory, autocomplete);
		currencyField = factory.getVLookup(windowNo, tabNo, DisplayType.Search, tableName, I_C_Invoice_Candidate.COLUMNNAME_C_Currency_ID, mandatory, autocomplete);
		invoiceRuleField = factory.getVLookup(windowNo, tabNo, DisplayType.List, tableName, I_C_Invoice_Candidate.COLUMNNAME_InvoiceRule, mandatory, autocomplete);

		ilCandHandlerField = factory.getVLookup(windowNo, tabNo, DisplayType.Search, tableName, I_C_Invoice_Candidate.COLUMNNAME_C_ILCandHandler_ID, mandatory, autocomplete);

		pricingSystemField = factory.getVLookup(windowNo, tabNo, DisplayType.Table, tableName, I_C_Invoice_Candidate.COLUMNNAME_M_PricingSystem_ID, mandatory, autocomplete);

		// TODO fix the lookup field with the validation rule
		// productField = factory.getVLookup(windowNo, tabNo, DisplayType.Search, tableName, I_C_Invoice_Candidate.COLUMNNAME_M_Product_ID, mandatory, autocomplete);
		productField = factory.getVLookup(windowNo, tabNo, DisplayType.Table, tableName, I_C_Invoice_Candidate.COLUMNNAME_M_Product_ID, mandatory, autocomplete);
		productField.addVetoableChangeListener(new VetoableChangeListener()
		{
			@Override
			public void vetoableChange(final PropertyChangeEvent e)
			{
				onProductChanged(e);
			}
		});

		taxField = factory.getVLookup(windowNo, tabNo, DisplayType.Table, tableName, I_C_Invoice_Candidate.COLUMNNAME_C_Tax_ID, mandatory, autocomplete);
		activityField = factory.getVLookup(windowNo, tabNo, DisplayType.Table, tableName, I_C_Invoice_Candidate.COLUMNNAME_C_Activity_ID, mandatory, autocomplete);

		qtyOrderedField = factory.getVNumber(I_C_Invoice_Candidate.COLUMNNAME_QtyOrdered, mandatory);
		priceUOMField = factory.getVLookup(windowNo, tabNo, DisplayType.Table, tableName, I_C_Invoice_Candidate.COLUMNNAME_Price_UOM_ID, mandatory, autocomplete);
		priceEnteredField = factory.getVNumber(I_C_Invoice_Candidate.COLUMNNAME_PriceEntered, mandatory);
		discountField = factory.getVNumber(I_C_Invoice_Candidate.COLUMNNAME_Discount, false); // mandatory

		init(factory);
	}

	/**
	 * Configure context values for current window for properties which are not displayed among the form fields.
	 */
	private final void configureContextValues()
	{
		final Properties ctx = Env.getCtx();

		Env.setContext(ctx, windowNo, I_C_Invoice_Candidate.COLUMNNAME_DateOrdered, date);

		Env.setContext(ctx, windowNo, I_C_Invoice_Candidate.COLUMNNAME_IsManual, isManual);
		Env.setContext(ctx, windowNo, I_C_Invoice_Candidate.COLUMNNAME_IsSOTrx, isSOTrx);
	}

	private final void init(final ISwingEditorFactory factory)
	{
		final Container contentPane = getContentPane();

		try
		{
			jbInit(factory);

			//
			// Init field values after window was opened
			addWindowListener(new WindowAdapter()
			{
				@Override
				public void windowOpened(final WindowEvent evt)
				{
					initFieldValues(factory);
				}
			});

			contentPane.add(mainPanel, BorderLayout.CENTER);
			contentPane.add(confirmPanel, BorderLayout.SOUTH);
		}
		catch (final Exception e)
		{
			logger.error("", e);
		}
	}

	private final void jbInit(final ISwingEditorFactory factory)
	{
		AdempierePLAF.setDefaultBackground(mainPanel);
		mainPanel.setLayout(mainLayout);

		//
		// Bind panels
		mainPanel.add(fieldPanel, BorderLayout.NORTH);

		//
		// Init fieldPanel
		fieldPanel.setLayout(fieldLayout);

		//
		// Inherited from previous window / not modifiable
		//
		// C_BPartner
		factory.bindFieldAndLabel(fieldPanel, partnerField, partnerLabel, I_C_Invoice_Candidate.COLUMNNAME_Bill_BPartner_ID);
		partnerField.setReadWrite(false);
		//
		// C_Currency
		factory.bindFieldAndLabel(fieldPanel, currencyField, currencyLabel, I_C_Invoice_Candidate.COLUMNNAME_C_Currency_ID);
		currencyField.setReadWrite(false);
		//
		// C_ILCand_Handler (Manual)
		factory.bindFieldAndLabel(fieldPanel, ilCandHandlerField, ilCandHandlerLabel, I_C_Invoice_Candidate.COLUMNNAME_C_ILCandHandler_ID);
		ilCandHandlerField.setReadWrite(false);

		factory.bindFieldAndLabel(fieldPanel, invoiceRuleField, invoiceRuleLabel, I_C_Invoice_Candidate.COLUMNNAME_InvoiceRule);

		factory.bindFieldAndLabel(fieldPanel, locationField, locationLabel, I_C_Invoice_Candidate.COLUMNNAME_Bill_Location_ID);

		factory.bindFieldAndLabel(fieldPanel, pricingSystemField, pricingSystemLabel, I_C_Invoice_Candidate.COLUMNNAME_M_PricingSystem_ID);
		pricingSystemField.setReadWrite(false);
		factory.bindFieldAndLabel(fieldPanel, productField, productLabel, I_C_Invoice_Candidate.COLUMNNAME_M_Product_ID);

		factory.bindFieldAndLabel(fieldPanel, taxField, taxLabel, I_C_Invoice_Candidate.COLUMNNAME_C_Tax_ID);
		factory.bindFieldAndLabel(fieldPanel, activityField, activityLabel, I_C_Invoice_Candidate.COLUMNNAME_C_Activity_ID);

		factory.bindFieldAndLabel(fieldPanel, qtyOrderedField, qtyOrderedLabel, I_C_Invoice_Candidate.COLUMNNAME_QtyOrdered);

		factory.bindFieldAndLabel(fieldPanel, priceUOMField, priceUOMLabel, I_C_Invoice_Candidate.COLUMNNAME_Price_UOM_ID);
		priceUOMField.setReadWrite(false);

		factory.bindFieldAndLabel(fieldPanel, priceEnteredField, priceEnteredLabel, I_C_Invoice_Candidate.COLUMNNAME_PriceEntered);
		factory.bindFieldAndLabel(fieldPanel, discountField, discountLabel, I_C_Invoice_Candidate.COLUMNNAME_Discount);

		//
		// Init confirmPanel
		confirmPanel.setActionListener(this);
	}

	private void initFieldValues(final ISwingEditorFactory factory)
	{
		final Properties ctx = Env.getCtx();

		final List<String> missingCollector = new ArrayList<>();

		partnerField.setValue(partnerId);
		currencyField.setValue(currencyId);

		//
		// Use manual candidate handler
		final I_C_ILCandHandler manualCandidateHandler = Services.get(IInvoiceCandidateHandlerDAO.class).retrieveForClassOneOnly(ctx, ManualCandidateHandler.class);
		ilCandHandlerField.setValue(manualCandidateHandler.getC_ILCandHandler_ID());

		invoiceRuleField.setValue(X_C_Invoice_Candidate.INVOICERULE_Sofort); // default

		//
		// Get pricing system (or dispose window if none was found)
		final int pricingSystemId = Services.get(IBPartnerDAO.class).retrievePricingSystemId(ctx, partnerId, isSOTrx, ITrx.TRXNAME_None);
		if (pricingSystemId <= 0)
		{
			missingCollector.add(I_C_Invoice_Candidate.COLUMNNAME_M_PricingSystem_ID);
		}
		pricingSystemField.setValue(pricingSystemId);

		// do not load UOMs; instead, they shall be loaded when the product is modified
		// priceUOMField.loadFirstItem();

		locationField.loadFirstItem();
		final Lookup locationLookup = locationField.getLookup();
		if (locationLookup == null || locationLookup.getSize() < 1)
		{
			missingCollector.add(I_C_Invoice_Candidate.COLUMNNAME_Bill_Location_ID);
		}

		//
		// If any of the required fields can't be populated, dispose the window and display all errors
		factory.disposeDialogForColumnData(windowNo, this, missingCollector);
	}

	private final void onProductChanged(final PropertyChangeEvent e)
	{
		final String name = e.getPropertyName();
		final Object value = e.getNewValue();

		logger.info(name + "=" + value);

		if (value == null)
		{
			priceUOMField.setValue(null);
			return;
		}

		if (!name.equals(productField.getColumnName())) // Product
		{
			return;
		}

		final int productId = productField.getValueAsInt();
		if (productId <= 0)
		{
			return;
		}

		final int pricingSystemId = pricingSystemField.getValueAsInt();
		Check.assume(pricingSystemId > 0, "pricingSystemId is set");

		final Properties ctx = Env.getCtx();
		final String trxName = ITrx.TRXNAME_None;

		final IContextAware contextProvider = new PlainContextAware(ctx, trxName);
		final I_M_PricingSystem pricingSystem = InterfaceWrapperHelper.create(ctx, pricingSystemId, I_M_PricingSystem.class, trxName);
		final I_M_Product product = InterfaceWrapperHelper.create(ctx, productId, I_M_Product.class, trxName);

		configureC_UOM_ID(contextProvider, product, pricingSystem);
		configureC_Tax_ID(contextProvider, product, pricingSystem);
		configureC_Activity_ID(contextProvider, product, pricingSystem);
	}

	private void configureC_UOM_ID(
			final IContextAware contextProvider,
			final I_M_Product product,
			final I_M_PricingSystem pricingSystem)
	{
		final I_C_UOM priceUOM;
		try
		{
			final IPriceListBL priceListBL = Services.get(IPriceListBL.class);

			I_M_ProductPrice productPrice = null;

			if (locationField.getValueAsInt() > 0)
			{
				final I_C_BPartner_Location location = InterfaceWrapperHelper.create(
						contextProvider.getCtx(),
						locationField.getValueAsInt(),
						I_C_BPartner_Location.class,
						InterfaceWrapperHelper.getTrxName(product));

				final I_M_PriceList_Version currentVersion = priceListBL.getCurrentPriceListVersionOrNull( //
						pricingSystem //
						, location.getC_Location().getC_Country() // country
						, SystemTime.asDayTimestamp() // date
						, isSOTrx //
						, (Boolean)null // processedPLVFiltering
				);

				productPrice = ProductPrices.retrieveMainProductPriceOrNull(currentVersion, product.getM_Product_ID());
			}
			if (productPrice == null)
			{
				throw new ProductPriceNotFoundException("@NotFound@: @M_ProductPrice_ID@");
			}
			priceUOM = productPrice.getC_UOM();
		}
		catch (final ProductPriceNotFoundException ppnfe)
		{
			ADialog.error(windowNo, getContentPane(), ppnfe);
			return;
		}
		Check.assumeNotNull(priceUOM, "priceUOM not null");

		final int uomId = priceUOM.getC_UOM_ID();
		priceUOMField.setValue(uomId);
	}

	private void configureC_Tax_ID(final IContextAware contextProvider, final I_M_Product product, final I_M_PricingSystem pricingSystem)
	{
		int priceTaxId = -1;
		try
		{
			final Properties ctx = contextProvider.getCtx();
			final String trxName = contextProvider.getTrxName();

			final int taxCategoryId = -1; // FIXME for accuracy, we will need the tax category
			final I_M_Warehouse warehouse = null;

			priceTaxId = Services.get(ITaxBL.class).getTax(
					ctx, null // no model
					, taxCategoryId, product.getM_Product_ID() // productId
					, -1 // chargeId
					, date // billDate
					, date // shipDate
					, product.getAD_Org_ID() // orgId
					, warehouse, locationField.getValueAsInt() // billC_BPartner_Location_ID
					, locationField.getValueAsInt() // shipC_BPartner_Location_ID
					, isSOTrx, trxName);
		}
		catch (final ProductPriceNotFoundException ppnfe)
		{
			// Do nothing
			//
			ADialog.error(windowNo, getContentPane(), ppnfe);
			return;
		}

		if (priceTaxId <= 0)
		{
			return;
		}
		taxField.setValue(priceTaxId);
	}

	private void configureC_Activity_ID(final IContextAware contextProvider, final I_M_Product product, final I_M_PricingSystem pricingSystem)
	{
		final int orgId = Env.getAD_Org_ID(contextProvider.getCtx());
		final I_AD_Org org = InterfaceWrapperHelper.create(contextProvider.getCtx(), orgId, I_AD_Org.class, contextProvider.getTrxName());

		int activityId = -1;
		try
		{
			final I_C_Activity activity = Services.get(IProductAcctDAO.class).retrieveActivityForAcct(contextProvider, org, product);
			if (activity != null)
			{
				activityId = activity.getC_Activity_ID();
			}
		}
		catch (final Exception e)
		{
			// Do nothing
			//
			// ADialog.error(windowNo, getContentPane(), e);
			return;
		}

		if (activityId <= 0)
		{
			return;
		}
		activityField.setValue(activityId);
	}

	@Override
	public void actionPerformed(final ActionEvent e)
	{
		if (e.getActionCommand().equals(ConfirmPanel.A_OK))
		{
			onDialogOk();
		}
		else if (e.getActionCommand().equals(ConfirmPanel.A_CANCEL))
		{
			dispose(); // exit
		}
	}

	/**
	 * When pressing OK, create & save the invoice candidate
	 */
	private void onDialogOk()
	{
		Services.get(ITrxManager.class).run(new TrxRunnable2()
		{
			@Override
			public void run(final String localTrxName) throws Exception
			{
				final Map<String, Integer> columns2Ids = new HashMap<>();

				final int productId = productField.getValueAsInt();
				columns2Ids.put(I_C_Invoice_Candidate.COLUMNNAME_M_Product_ID, productId);

				final int taxId = taxField.getValueAsInt();
				columns2Ids.put(I_C_Invoice_Candidate.COLUMNNAME_C_Tax_ID, taxId);

				final int activityId = activityField.getValueAsInt();
				columns2Ids.put(I_C_Invoice_Candidate.COLUMNNAME_C_Activity_ID, activityId);

				final Object qtyOrdered = qtyOrderedField.getValue();
				if (qtyOrdered == null)
				{
					columns2Ids.put(I_C_Invoice_Candidate.COLUMNNAME_QtyOrdered, -1);
				}

				final Object priceEntered = priceEnteredField.getValue();
				if (priceEntered == null)
				{
					columns2Ids.put(I_C_Invoice_Candidate.COLUMNNAME_PriceEntered, -1);
				}

				//
				// Collect and throw exceptions for missing
				throwExceptionIfNotFilled(columns2Ids);

				final Properties ctx = Env.getCtx();
				final IContextAware contextProvider = new PlainContextAware(ctx, localTrxName);

				final I_C_Invoice_Candidate ic = InterfaceWrapperHelper.newInstance(I_C_Invoice_Candidate.class, contextProvider);

				//
				// Retrieve model(s) for id(s)
				final I_M_Product product = InterfaceWrapperHelper.create(ctx, productId, I_M_Product.class, ITrx.TRXNAME_None);

				//
				// Set field values
				ic.setBill_BPartner_ID(partnerId);
				ic.setC_Currency_ID(currencyId);
				ic.setDateOrdered(date);

				ic.setC_ILCandHandler_ID(ilCandHandlerField.getValueAsInt());
				final Object invoiceRuleObj = invoiceRuleField.getValue();
				if (invoiceRuleObj != null)
				{
					ic.setInvoiceRule(invoiceRuleObj.toString());
				}
				ic.setBill_Location_ID(locationField.getValueAsInt());

				ic.setM_PricingSystem_ID(pricingSystemField.getValueAsInt());
				ic.setM_Product_ID(productId);

				ic.setC_Tax_ID(taxId);
				ic.setC_Activity_ID(activityId);

				ic.setQtyOrdered((BigDecimal)qtyOrdered);
				ic.setPrice_UOM_ID(priceUOMField.getValueAsInt());
				ic.setPriceEntered((BigDecimal)priceEntered);
				ic.setDiscount((BigDecimal)discountField.getValue());

				//
				// Configure automatic values
				ic.setIsManual(isManual);
				ic.setIsSOTrx(isSOTrx);

				// Copy
				ic.setDateToInvoice(ic.getDateOrdered());
				ic.setQtyToInvoice(ic.getQtyOrdered());
				ic.setPriceActual(ic.getPriceEntered());

				ic.setC_UOM_ID(product.getC_UOM_ID()); // use the product's UOM for the main IC/InvoiceLine C_UOM_ID

				//
				// Mock (not used, cannot be null)
				ic.setRecord_ID(0);

				//
				// Try save the invoice candidate
				InterfaceWrapperHelper.save(ic, localTrxName);

				//
				// Invalidate (re-process candidate on-fly)
				Services.get(IInvoiceCandBL.class).updateInvalid()
						.setContext(ctx, localTrxName)
						.setTaggedWithAnyTag()
						.setOnlyC_Invoice_Candidates(Collections.singleton(ic))
						.update();

				invoiceCandidateId = ic.getC_Invoice_Candidate_ID();

				//
				// Dispose of this dialog only if save was successful (so that the user does not get his/her data cleared out)
				dispose();
			}

			@Override
			public void doFinally()
			{
				// nothing
			}

			@Override
			public boolean doCatch(final Throwable e) throws Throwable
			{
				ADialog.error(windowNo, getContentPane(), e);
				return true; // rollback transaction
			}
		});
	}

	private void throwExceptionIfNotFilled(final Map<String, Integer> columns2Ids)
	{
		final List<String> missingMandatoryColumns = new ArrayList<>();

		for (final Entry<String, Integer> column2Id : columns2Ids.entrySet())
		{
			if (column2Id.getValue() > 0)
			{
				continue;
			}
			missingMandatoryColumns.add(column2Id.getKey());
		}

		if (missingMandatoryColumns.isEmpty())
		{
			return;
		}

		final StringBuilder missingColumnsBuilder = new StringBuilder();

		final Iterator<String> columnNamesIt = missingMandatoryColumns.iterator();
		while (columnNamesIt.hasNext())
		{
			final String columnName = columnNamesIt.next();
			missingColumnsBuilder.append("@").append(columnName).append("@");
			if (columnNamesIt.hasNext())
			{
				missingColumnsBuilder.append(", ");
			}
		}

		throw new AdempiereException("@FillMandatory@ " + missingColumnsBuilder.toString());
	}

	private int invoiceCandidateId = -1;

	public boolean hasCreatedInvoice()
	{
		return invoiceCandidateId > 0;
	}

	public int getC_Invoice_Candidate_ID()
	{
		return invoiceCandidateId;
	}

	@Override
	public void dispose()
	{
		super.dispose();
		Env.clearWinContext(windowNo);
	}
}
