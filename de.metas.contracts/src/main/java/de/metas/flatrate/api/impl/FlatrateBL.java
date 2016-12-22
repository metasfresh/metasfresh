package de.metas.flatrate.api.impl;

/*
 * #%L
 * de.metas.contracts
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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.bpartner.service.IBPartnerDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.IEditablePricingContext;
import org.adempiere.pricing.api.IPricingBL;
import org.adempiere.pricing.api.IPricingResult;
import org.adempiere.util.Check;
import org.adempiere.util.Loggables;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.adempiere.util.time.SystemTime;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_Activity;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_C_Year;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.Lookup;
import org.compiere.model.MNote;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MOrg;
import org.compiere.model.MRefList;
import org.compiere.model.POInfo;
import org.compiere.model.Query;
import org.compiere.model.X_C_DocType;
import org.compiere.model.X_C_Order;
import org.compiere.process.DocAction;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.compiere.util.TrxRunnable;
import org.slf4j.Logger;

import ch.qos.logback.classic.Level;
import de.metas.adempiere.model.I_C_Order;
import de.metas.adempiere.model.I_M_PriceList;
import de.metas.adempiere.model.I_M_Product;
import de.metas.adempiere.model.I_M_ProductPrice;
import de.metas.adempiere.service.ICalendarBL;
import de.metas.adempiere.service.ICalendarDAO;
import de.metas.contracts.subscription.ISubscriptionBL;
import de.metas.contracts.subscription.model.I_C_OrderLine;
import de.metas.document.IDocumentPA;
import de.metas.document.engine.IDocActionBL;
import de.metas.flatrate.api.IFlatrateBL;
import de.metas.flatrate.api.IFlatrateDAO;
import de.metas.flatrate.api.IFlatrateHandlersService;
import de.metas.flatrate.invoicecandidate.spi.impl.FlatrateDataEntryHandler;
import de.metas.flatrate.model.I_C_Flatrate_Conditions;
import de.metas.flatrate.model.I_C_Flatrate_Data;
import de.metas.flatrate.model.I_C_Flatrate_DataEntry;
import de.metas.flatrate.model.I_C_Flatrate_Term;
import de.metas.flatrate.model.I_C_Flatrate_Transition;
import de.metas.flatrate.model.I_C_Invoice_Clearing_Alloc;
import de.metas.flatrate.model.X_C_Flatrate_Conditions;
import de.metas.flatrate.model.X_C_Flatrate_DataEntry;
import de.metas.flatrate.model.X_C_Flatrate_Term;
import de.metas.flatrate.model.X_C_Flatrate_Transition;
import de.metas.flatrate.modelvalidator.C_Flatrate_Term;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerDAO;
import de.metas.invoicecandidate.model.I_C_ILCandHandler;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.logging.LogManager;
import de.metas.product.IProductPA;
import de.metas.product.acct.api.IProductAcctDAO;
import de.metas.tax.api.ITaxBL;
import de.metas.workflow.api.IWFExecutionFactory;

public class FlatrateBL implements IFlatrateBL
{

	private static final Logger logger = LogManager.getLogger(FlatrateBL.class);

	private static final String MSG_FLATRATEBL_INVOICING_ENTRY_NOT_CO_3P = "FlatrateBL_InvoicingEntry_Not_CO";

	private static final String MSG_FLATRATEBL_INVOICE_CANDIDATE_TO_RECOMPUTE_1P = "FlatrateBL_InvoicingCand_ToRecompute";

	private static final String MSG_FLATRATEBL_INVOICE_CANDIDATE_QTY_TO_INVOICE_1P = "FlatrateBL_InvoicingCand_QtyToInvoice";

	private static final String MSG_FLATRATEBL_PRICE_MISSING_2P = "FlatrateBL_Price_Missing";
	private static final String MSG_FLATRATEBL_PRICE_LIST_MISSING_2P = "FlatrateBL_PriceList_Missing";

	private static final String MSG_DATA_ENTRY_CREATE_FLAT_FEE_4P = "DataEntry_Create_FlatFee";

	private static final String MSG_DATA_ENTRY_CREATE_HOLDING_FEE_3P = "DataEntry_Create_HoldingFee";

	private static final String MSG_TERM_NEW_UNCOMPLETED_0P = "FlatrateTerm_New_Uncompleted_Term";
	private static final String MSG_TERM_NEW_COMPLETED_0P = "FlatrateTerm_New_Completed_Term";
	private static final String MSG_TERM_NO_NEW_0P = "FlatrateTerm_No_New_Term";

	private final IFlatrateDAO flatrateDB = Services.get(IFlatrateDAO.class);

	private final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);

	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	private final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);

	@Override
	public String beforeCompleteDataEntry(final I_C_Flatrate_DataEntry dataEntry)
	{
		Check.assume(!dataEntry.isSimulation(), dataEntry + " has IsSimulation='N'");

		final IFlatrateDAO flatrateDB = Services.get(IFlatrateDAO.class);

		final I_C_Flatrate_Term term = dataEntry.getC_Flatrate_Term();
		final I_C_Flatrate_Conditions fc = term.getC_Flatrate_Conditions();

		if (fc == null)
		{
			// nothing to do
			return null;
		}

		if (X_C_Flatrate_Conditions.TYPE_CONDITIONS_Pauschalengebuehr.equals(fc.getType_Conditions())
				&& fc.getC_UOM_ID() != dataEntry.getC_UOM_ID())
		{
			// nothing to do
			return null;
		}

		final BigDecimal qtyToInvoice;

		if (X_C_Flatrate_DataEntry.TYPE_Correction_PeriodBased.equals(dataEntry.getType()))
		{
			final List<I_C_Flatrate_DataEntry> invoicingEntries = flatrateDB.retrieveDataEntries(dataEntry.getC_Flatrate_Term(), X_C_Flatrate_DataEntry.TYPE_Invoicing_PeriodBased, dataEntry.getC_UOM());

			for (final I_C_Flatrate_DataEntry invoicingEntry : invoicingEntries)
			{
				// make sure that all invoicing entries are complete
				if (!X_C_Flatrate_DataEntry.DOCSTATUS_Completed.equals(invoicingEntry.getDocStatus()))
				{
					final Properties ctx = InterfaceWrapperHelper.getCtx(dataEntry);
					final String trxName = InterfaceWrapperHelper.getTrxName(dataEntry);

					return msgBL.getMsg(ctx,
							FlatrateBL.MSG_FLATRATEBL_INVOICING_ENTRY_NOT_CO_3P,
							new Object[] {
									invoicingEntry.getC_Period().getName(),
									invoicingEntry.getC_UOM().getName(),
									MRefList.get(ctx, X_C_Flatrate_DataEntry.DOCSTATUS_AD_Reference_ID, X_C_Flatrate_DataEntry.DOCSTATUS_Completed, trxName) });
				}
			}

			// Note: we won't invoice the complete Qty_Reported, but only the difference to the qtys that we already
			// invoiced
			qtyToInvoice = dataEntry.getQty_Reported().subtract(dataEntry.getQty_Planned());
		}
		else
		{
			if (fc.isClosingWithActualSum() && !fc.isSimulation())
			{
				//
				// make sure that all invoice candidates to clear are up to date
				final StringBuilder sb = new StringBuilder();
				for (final I_C_Invoice_Clearing_Alloc ica : flatrateDB.retrieveClearingAllocs(dataEntry))
				{
					final I_C_Invoice_Candidate invoiceCandToClear = ica.getC_Invoice_Cand_ToClear();
					if (invoiceCandToClear.isToRecompute())
					{
						if (sb.length() != 0)
						{
							sb.append(", ");
						}
						sb.append(ica.getC_Invoice_Cand_ToClear_ID());
					}
				}
				if (sb.length() > 0)
				{
					final Properties ctx = InterfaceWrapperHelper.getCtx(dataEntry);
					return msgBL.getMsg(ctx,
							FlatrateBL.MSG_FLATRATEBL_INVOICE_CANDIDATE_TO_RECOMPUTE_1P,
							new Object[] {
									sb.toString() });
				}

				//
				// Make sure that all invoice candidates to clear are ready to invoice
				// Note: If we allow 'dataEntry' to be completed now, there will be a problem later on
				// when QtyToInvoice is adjusted, because C_Flatrate_DataEntry.ActualQty cannot be updated
				// anymore after completing.
				for (final I_C_Invoice_Clearing_Alloc ica : flatrateDB.retrieveClearingAllocs(dataEntry))
				{
					final I_C_Invoice_Candidate invoiceCandToClear = ica.getC_Invoice_Cand_ToClear();
					if (invoiceCandToClear.getQtyOrdered().compareTo(invoiceCandToClear.getQtyToInvoice()) > 0)
					{
						if (sb.length() != 0)
						{
							sb.append(", ");
						}
						sb.append(ica.getC_Invoice_Cand_ToClear_ID());
					}
				}
				if (sb.length() > 0)
				{
					final Properties ctx = InterfaceWrapperHelper.getCtx(dataEntry);
					return msgBL.getMsg(ctx,
							FlatrateBL.MSG_FLATRATEBL_INVOICE_CANDIDATE_QTY_TO_INVOICE_1P,
							new Object[] {
									sb.toString() });
				}
			}
			Check.assume(X_C_Flatrate_DataEntry.TYPE_Invoicing_PeriodBased.equals(dataEntry.getType()),
					dataEntry + "has type=" + X_C_Flatrate_DataEntry.TYPE_Invoicing_PeriodBased);
			qtyToInvoice = dataEntry.getQty_Reported();
		}

		final List<I_C_Invoice_Candidate> newCands;
		if (X_C_Flatrate_Conditions.TYPE_CONDITIONS_Pauschalengebuehr.equals(fc.getType_Conditions()))
		{
			newCands = createCandForFlatFeeDataEntry(fc, dataEntry);
		}
		else
		{
			newCands = Collections.singletonList(createCandForDataEntry(term, dataEntry, qtyToInvoice));
		}

		if (X_C_Flatrate_DataEntry.TYPE_Invoicing_PeriodBased.equals(dataEntry.getType()))
		{
			for (final I_C_Invoice_Candidate newCand : newCands)
			{
				if (newCand.getM_Product_ID() != fc.getM_Product_Flatrate_ID())
				{
					continue;
				}

				final List<I_C_Invoice_Clearing_Alloc> allocLines = flatrateDB.retrieveClearingAllocs(dataEntry);

				for (final I_C_Invoice_Clearing_Alloc alloc : allocLines)
				{
					// consider the invoice candidate that awaits clearing as invoiced
					final I_C_Invoice_Candidate candToClear = alloc.getC_Invoice_Cand_ToClear();

					candToClear.setQtyInvoiced(candToClear.getQtyToInvoice());
					candToClear.setQtyToInvoice(BigDecimal.ZERO);

					InterfaceWrapperHelper.save(candToClear);

					// C_Flatrate_DataEntry_ID and QtyCleared have already been set by InvoiceCandidateValidator
					Check.assume(alloc.getC_Flatrate_DataEntry_ID() == dataEntry.getC_Flatrate_DataEntry_ID(),
							alloc + " sould have C_Flatrate_DataEntry_ID=" + dataEntry.getC_Flatrate_DataEntry_ID() + " but has " + alloc.getC_Flatrate_DataEntry_ID());

					// update the allocation record
					alloc.setC_Invoice_Candidate_ID(newCand.getC_Invoice_Candidate_ID());
					InterfaceWrapperHelper.save(alloc);
				}
			}
		}
		return null;
	}

	private List<I_C_Invoice_Candidate> createCandForFlatFeeDataEntry(
			final I_C_Flatrate_Conditions fc,
			final I_C_Flatrate_DataEntry dataEntry)
	{
		Check.assume(!dataEntry.isSimulation(), dataEntry + " has IsSimulation='N'");

		Check.assume(X_C_Flatrate_Conditions.TYPE_CONDITIONS_Pauschalengebuehr.equals(fc.getType_Conditions())
				|| X_C_Flatrate_Conditions.TYPE_CONDITIONS_Leergutverwaltung.equals(fc.getType_Conditions()),
				fc + " has Type_Conditions=" + X_C_Flatrate_Conditions.TYPE_CONDITIONS_Pauschalengebuehr
						+ " or " + X_C_Flatrate_Conditions.TYPE_CONDITIONS_Leergutverwaltung);

		final Properties ctx = InterfaceWrapperHelper.getCtx(fc);
		final String trxName = InterfaceWrapperHelper.getTrxName(fc);

		final I_C_Flatrate_Term term = dataEntry.getC_Flatrate_Term();

		final List<I_C_Invoice_Candidate> result = new ArrayList<>();

		final int productIdCand;
		final int productIdCorrCand = fc.getM_Product_Actual_ID();

		if (X_C_Flatrate_DataEntry.TYPE_Correction_PeriodBased.equals(dataEntry.getType()))
		{
			productIdCand = fc.getM_Product_Correction_ID();
		}
		else
		{
			Check.assume(X_C_Flatrate_DataEntry.TYPE_Invoicing_PeriodBased.equals(dataEntry.getType()), dataEntry + " has type " + X_C_Flatrate_DataEntry.TYPE_Invoicing_PeriodBased);
			productIdCand = fc.getM_Product_Flatrate_ID();
		}

		if (productIdCorrCand == productIdCand)
		{
			final BigDecimal priceActual;
			if (fc.isCorrectionAmtAtClosing() && X_C_Flatrate_DataEntry.TYPE_Invoicing_PeriodBased.equals(dataEntry.getType()))
			{
				// we will invoice only the flat rate amount (the correction will be done at closing)
				priceActual = dataEntry.getFlatrateAmt();
			}
			else
			{
				// we will invoice the flat rate amount plus the correction amount
				priceActual = dataEntry.getFlatrateAmt().add(dataEntry.getFlatrateAmtCorr());
			}

			final int productId = fc.getM_Product_Flatrate_ID();
			Check.assume(productId > 0,
					fc + " with Type_Conditions=" + X_C_Flatrate_Conditions.TYPE_CONDITIONS_Pauschalengebuehr + " has no M_Product_Flatrate");

			final I_C_Invoice_Candidate newIc = createCand(ctx, term, dataEntry, productId, priceActual, trxName);
			result.add(newIc);

			// note: we assume that 'dataEntry' is saved somewhere outside this method!
			dataEntry.setC_Invoice_Candidate_ID(newIc.getC_Invoice_Candidate_ID());
		}
		else
		{
			final I_C_Invoice_Candidate newFlatrateIc = createCand(ctx, term, dataEntry, productIdCand, dataEntry.getFlatrateAmt(), trxName);
			result.add(newFlatrateIc);

			// note: we assume that 'dataEntry' is saved somewhere outside this method!
			dataEntry.setC_Invoice_Candidate_ID(newFlatrateIc.getC_Invoice_Candidate_ID());

			if (dataEntry.getFlatrateAmtCorr().signum() != 0)
			{
				if (fc.isCorrectionAmtAtClosing() && X_C_Flatrate_DataEntry.TYPE_Correction_PeriodBased.equals(dataEntry.getType())
						|| !fc.isCorrectionAmtAtClosing() && X_C_Flatrate_DataEntry.TYPE_Invoicing_PeriodBased.equals(dataEntry.getType()))

				{
					final I_C_Invoice_Candidate newCorrectionIc = createCand(ctx, term, dataEntry, productIdCorrCand, dataEntry.getFlatrateAmtCorr(), trxName);
					result.add(newCorrectionIc);

					dataEntry.setC_Invoice_Candidate_Corr_ID(newCorrectionIc.getC_Invoice_Candidate_ID());
				}
			}
		}

		return result;
	}

	private I_C_Invoice_Candidate createCand(
			final Properties ctx,
			final I_C_Flatrate_Term term,
			final I_C_Flatrate_DataEntry dataEntry,
			final int productId,
			final BigDecimal priceActual,
			final String trxName)
	{

		final I_C_Invoice_Candidate newCand = InterfaceWrapperHelper.create(ctx, I_C_Invoice_Candidate.class, trxName);
		final int orgId = dataEntry.getAD_Org_ID();
		newCand.setAD_Org_ID(orgId);
		Check.assume(newCand.getAD_Client_ID() == dataEntry.getAD_Client_ID(), "ctx contains the correct AD_Client_ID");

		final I_C_Flatrate_Conditions fc = term.getC_Flatrate_Conditions();
		newCand.setM_PricingSystem_ID(fc.getM_PricingSystem_ID());

		newCand.setM_Product_ID(productId);
		newCand.setQtyOrdered(BigDecimal.ONE);

		newCand.setQtyToInvoice(BigDecimal.ZERO); // to be computed
		newCand.setPriceActual(priceActual);
		newCand.setPrice_UOM_ID(term.getC_UOM_ID()); // 07090 when we set PiceActual, we shall also set PriceUOM.
		newCand.setPriceEntered(priceActual); // cg : task 04917 -- same as price actual

		newCand.setDiscount(BigDecimal.ZERO);

		newCand.setInvoiceRule(fc.getInvoiceRule());
		newCand.setC_Currency_ID(term.getC_Currency_ID());
		newCand.setBill_BPartner_ID(term.getBill_BPartner_ID());

		final int billLocationID = term.getBill_Location_ID();
		newCand.setBill_Location_ID(billLocationID);

		newCand.setBill_User_ID(term.getBill_User_ID());

		newCand.setDateOrdered(dataEntry.getC_Period().getEndDate());

		newCand.setAD_Table_ID(adTableDAO.retrieveTableId(I_C_Flatrate_DataEntry.Table_Name));
		newCand.setRecord_ID(dataEntry.getC_Flatrate_DataEntry_ID());

		// using the current login date as dateInvoiced
		// newCand.setDateInvoiced(Env.getContextAsDate(ctx, "#Date"));

		// 07442 activity and tax
		final IContextAware contextProvider = InterfaceWrapperHelper.getContextAware(term);

		final I_M_Product product = InterfaceWrapperHelper.create(ctx, productId, I_M_Product.class, trxName);
		final I_C_Activity activity = Services.get(IProductAcctDAO.class).retrieveActivityForAcct(contextProvider, term.getAD_Org(), product);

		newCand.setC_Activity(activity);

		final int taxCategoryId = -1; // FIXME for accuracy, we will need the tax category
		final I_M_Warehouse warehouse = null;
		final boolean isSOTrx = true;

		final int taxId = Services.get(ITaxBL.class).getTax(
				ctx, term, taxCategoryId, productId, -1 // chargeId
				, dataEntry.getDate_Reported() // billDate
				, dataEntry.getDate_Reported() // shipDate
				, orgId, warehouse, billLocationID, -1 // ship location id
				, isSOTrx, trxName);

		newCand.setC_Tax_ID(taxId);

		setILCandHandler(ctx, newCand);

		InterfaceWrapperHelper.save(newCand);

		return newCand;
	}

	private void setILCandHandler(final Properties ctx, final I_C_Invoice_Candidate newCand)
	{
		boolean handlerFound = false;

		final List<I_C_ILCandHandler> handlers = Services.get(IInvoiceCandidateHandlerDAO.class).retrieveForClass(ctx, FlatrateDataEntryHandler.class);
		for (final I_C_ILCandHandler handler : handlers)
		{
			if (handler.isActive())
			{
				Check.assume(!handlerFound, "There is only one active handler for " + FlatrateDataEntryHandler.class);
				newCand.setC_ILCandHandler(handler);
				handlerFound = true;
			}
		}

		Check.assume(handlerFound, "There is an active handler for " + FlatrateDataEntryHandler.class);
	}

	private I_C_Invoice_Candidate createCandForDataEntry(
			final I_C_Flatrate_Term term,
			final I_C_Flatrate_DataEntry dataEntry,
			final BigDecimal qtyToInvoice)
	{
		Check.assume(!dataEntry.isSimulation(), dataEntry + " has IsSimulation='N'");

		final I_C_Flatrate_Conditions fc = term.getC_Flatrate_Conditions();

		final Properties ctx = InterfaceWrapperHelper.getCtx(fc);
		final String trxName = InterfaceWrapperHelper.getTrxName(fc);

		final I_C_Invoice_Candidate newCand = InterfaceWrapperHelper.create(ctx, I_C_Invoice_Candidate.class, trxName);
		newCand.setAD_Org_ID(dataEntry.getAD_Org_ID());

		newCand.setM_PricingSystem_ID(fc.getM_PricingSystem_ID());

		final int productIdForIc;
		final BigDecimal priceActual;

		if (X_C_Flatrate_Conditions.TYPE_CONDITIONS_Depotgebuehr.equals(fc.getType_Conditions())
				|| X_C_Flatrate_Conditions.TYPE_CONDITIONS_Leergutverwaltung.equals(fc.getType_Conditions()))
		{
			final I_M_Product product = InterfaceWrapperHelper.create(dataEntry.getM_Product_DataEntry(), I_M_Product.class);
			productIdForIc = product.getM_Product_ID();
			Check.assume(productIdForIc > 0,
					dataEntry + " has no M_Product_DataEntry, despite " + fc + "has Type_Conditions=" + fc.getType_Conditions());

			priceActual = getPriceForProduct(term, product, qtyToInvoice, dataEntry.getC_Period().getStartDate());
		}
		else
		{
			Check.assume(X_C_Flatrate_Conditions.TYPE_CONDITIONS_Pauschalengebuehr.equals(fc.getType_Conditions()),
					fc + " has Type_Conditions=" + X_C_Flatrate_Conditions.TYPE_CONDITIONS_Pauschalengebuehr);

			if (X_C_Flatrate_DataEntry.TYPE_Invoicing_PeriodBased.equals(dataEntry.getType()))
			{
				productIdForIc = fc.getM_Product_Flatrate_ID();
			}
			else
			{
				Check.assume(X_C_Flatrate_DataEntry.TYPE_Correction_PeriodBased.equals(dataEntry.getType()),
						"dataEntry has type " + X_C_Flatrate_DataEntry.TYPE_Correction_PeriodBased);
				productIdForIc = fc.getM_Product_Correction_ID();
			}
			Check.assume(productIdForIc > 0,
					fc + " with Type_Conditions=" + X_C_Flatrate_Conditions.TYPE_CONDITIONS_Pauschalengebuehr + " has no M_Product_Flatrate");

			priceActual = dataEntry.getFlatrateAmt();
		}

		newCand.setM_Product_ID(productIdForIc);
		newCand.setQtyOrdered(qtyToInvoice);

		newCand.setQtyToInvoice(BigDecimal.ZERO); // to be computed
		newCand.setPriceActual(priceActual);
		newCand.setPriceEntered(priceActual); // cg : task 04917 -- same as price actual
		newCand.setPrice_UOM_ID(term.getC_UOM_ID()); // 07090 when we set PiceActual, we shall also set PriceUOM.

		newCand.setDiscount(BigDecimal.ZERO);

		newCand.setInvoiceRule(fc.getInvoiceRule());
		newCand.setC_Currency_ID(term.getC_Currency_ID());
		newCand.setBill_BPartner_ID(term.getBill_BPartner_ID());
		newCand.setBill_Location_ID(term.getBill_Location_ID());
		newCand.setBill_User_ID(term.getBill_User_ID());

		// using the current login date as dateInvoiced
		// newCand.setDateInvoiced(Env.getContextAsDate(ctx, "#Date"));

		newCand.setAD_Table_ID(adTableDAO.retrieveTableId(I_C_Flatrate_DataEntry.Table_Name));
		newCand.setRecord_ID(dataEntry.getC_Flatrate_DataEntry_ID());

		// 07442 activity and tax
		final IContextAware contextProvider = InterfaceWrapperHelper.getContextAware(term);

		final I_M_Product product = InterfaceWrapperHelper.create(ctx, productIdForIc, I_M_Product.class, trxName);
		final I_C_Activity activity = Services.get(IProductAcctDAO.class).retrieveActivityForAcct(contextProvider, term.getAD_Org(), product);

		newCand.setC_Activity(activity);

		final int taxCategoryId = -1; // FIXME for accuracy, we will need the tax category
		final I_M_Warehouse warehouse = null;
		final boolean isSOTrx = true;

		final int taxId = Services.get(ITaxBL.class).getTax(
				ctx, term, taxCategoryId, productIdForIc, -1 // chargeId
				, dataEntry.getDate_Reported() // billDate
				, dataEntry.getDate_Reported() // shipDate
				, dataEntry.getAD_Org_ID(), warehouse, term.getBill_BPartner_ID(), -1 // ship location id
				, isSOTrx, trxName);

		newCand.setC_Tax_ID(taxId);

		setILCandHandler(ctx, newCand);

		return newCand;
	}

	/**
	 * Returns the price for one unit, given a flatrate term, qty (to consider discounts) and data entry.
	 *
	 * @param flatrateTerm
	 * @param qty
	 * @param dataEntry
	 * @return
	 */
	BigDecimal getFlatFeePricePerUnit(
			final I_C_Flatrate_Term flatrateTerm,
			final BigDecimal qty,
			final I_C_Flatrate_DataEntry dataEntry)
	{
		final I_C_Flatrate_Conditions flatrateCond = flatrateTerm.getC_Flatrate_Conditions();

		final I_M_Product flatrateProduct;
		if (X_C_Flatrate_DataEntry.TYPE_Invoicing_PeriodBased.equals(dataEntry.getType()))
		{
			flatrateProduct = InterfaceWrapperHelper.create(flatrateCond.getM_Product_Flatrate(), I_M_Product.class);
		}
		else
		{
			Check.assume(X_C_Flatrate_DataEntry.TYPE_Correction_PeriodBased.equals(dataEntry.getType()), "");
			flatrateProduct = InterfaceWrapperHelper.create(flatrateCond.getM_Product_Correction(), I_M_Product.class);
		}

		return getPriceForProduct(flatrateTerm, flatrateProduct, qty, dataEntry.getC_Period().getStartDate());
	}

	private BigDecimal getPriceForProduct(
			final I_C_Flatrate_Term flatrateTerm,
			final I_M_Product flatrateProduct,
			final BigDecimal qty,
			final Timestamp priceDate)
	{
		final I_C_Flatrate_Conditions flatrateCond = flatrateTerm.getC_Flatrate_Conditions();

		final I_M_PriceList priceList = retrievePriceList(flatrateCond, flatrateTerm);
		final IPricingResult pricingResult = retrievePricingInfo(flatrateCond, flatrateTerm, flatrateProduct, qty, priceList, priceDate);
		return pricingResult.getPriceStd();
	}

	private IPricingResult retrievePricingInfo(
			final I_C_Flatrate_Conditions flatrateCond,
			final I_C_Flatrate_Term flatrateTerm,
			final I_M_Product product,
			final BigDecimal qty,
			final I_M_PriceList priceList,
			final Timestamp priceDate)
	{
		final IPricingBL pricingBL = Services.get(IPricingBL.class);

		final boolean isSOTrx = true;
		final IEditablePricingContext pricingCtx = pricingBL.createInitialContext(product.getM_Product_ID(), flatrateTerm.getBill_BPartner_ID(), product.getC_UOM_ID(), qty, isSOTrx);
		pricingCtx.setPriceDate(priceDate);
		pricingCtx.setM_PriceList_ID(priceList.getM_PriceList_ID());

		final IPricingResult result = pricingBL.calculatePrice(pricingCtx);
		if (!result.isCalculated())
		{
			final Properties ctx = InterfaceWrapperHelper.getCtx(flatrateCond);
			throw new AdempiereException(
					msgBL.getMsg(ctx, FlatrateBL.MSG_FLATRATEBL_PRICE_MISSING_2P,
							new Object[] { priceList.getName(), product.getValue() }));
		}

		return result;
	}

	private I_M_PriceList retrievePriceList(
			final I_C_Flatrate_Conditions flatrateCond,
			final I_C_Flatrate_Term term)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(flatrateCond);
		final String trxName = InterfaceWrapperHelper.getTrxName(flatrateCond);

		final int pricingSystemIdToUse = term.getM_PricingSystem_ID() > 0
				? term.getM_PricingSystem_ID()
				: flatrateCond.getM_PricingSystem_ID();

		final IProductPA productPA = Services.get(IProductPA.class);

		final I_M_PriceList priceList = productPA.retrievePriceListByPricingSyst(ctx, pricingSystemIdToUse, term.getBill_Location_ID(), true, trxName);
		if (priceList == null)
		{
			throw new AdempiereException(
					msgBL.getMsg(ctx, FlatrateBL.MSG_FLATRATEBL_PRICE_LIST_MISSING_2P,
							new Object[] {
									InterfaceWrapperHelper.create(ctx, pricingSystemIdToUse, I_M_PricingSystem.class, trxName).getName(),
									term.getBill_Location().getName() }));
		}
		return priceList;
	}

	@Override
	public void validatePricing(final I_C_Flatrate_Term term)
	{
		final I_C_Flatrate_Conditions fc = term.getC_Flatrate_Conditions();

		final I_M_PriceList pl = retrievePriceList(fc, term);
		// Note C_Currency should be mandatory in a pl
		Check.assume(pl.getC_Currency_ID() > 0, pl + " has no C_Currency_ID");

		final Timestamp date = SystemTime.asTimestamp();

		final I_M_Product flatrateProduct = InterfaceWrapperHelper.create(fc.getM_Product_Flatrate(), I_M_Product.class);
		validatePricingForProduct(fc, term, pl, flatrateProduct, date);

		if (fc.isClosingWithActualSum() && X_C_Flatrate_Conditions.TYPE_FLATRATE_Korridor.equals(fc.getType_Flatrate()))
		{
			Check.assume(fc.getM_Product_Actual_ID() > 0, fc + " has no product to invoice the flatRateCorrectionAmt");
			final I_M_Product actualProduct = InterfaceWrapperHelper.create(fc.getM_Product_Actual(), I_M_Product.class);
			validatePricingForProduct(fc, term, pl, actualProduct, date);
		}
		if (fc.isClosingWithCorrectionSum())
		{
			Check.assume(fc.getM_Product_Correction_ID() > 0, fc + " has no product to invoice the corrected qty_reported");
			final I_M_Product correctionProduct = InterfaceWrapperHelper.create(fc.getM_Product_Correction(), I_M_Product.class);
			validatePricingForProduct(fc, term, pl, correctionProduct, date);
		}
	}

	private void validatePricingForProduct(
			final I_C_Flatrate_Conditions fc,
			final I_C_Flatrate_Term term,
			final I_M_PriceList pl,
			final I_M_Product product,
			final Timestamp date)
	{
		final IPricingResult pricingResult = retrievePricingInfo(fc, term, product, BigDecimal.ONE, pl, date);
		final int plvId = pricingResult.getM_PriceList_Version_ID();

		final Properties ctx = InterfaceWrapperHelper.getCtx(fc);
		final String trxName = InterfaceWrapperHelper.getTrxName(fc);

		final String wc = org.compiere.model.I_M_ProductPrice.COLUMNNAME_M_Product_ID + "=? AND " + org.compiere.model.I_M_ProductPrice.COLUMNNAME_M_PriceList_Version_ID + "=?";
		final I_M_ProductPrice pp = getProductPrice(fc, plvId, ctx, trxName, wc);
		if (pp == null || pp.getC_TaxCategory_ID() == 0)
		{
			throw new AdempiereException(
					msgBL.getMsg(ctx, FlatrateBL.MSG_FLATRATEBL_PRICE_MISSING_2P,
							new Object[] { pl.getName(), product.getValue() }));
		}
	}

	private final I_M_ProductPrice getProductPrice(final I_C_Flatrate_Conditions fc, final int plvId, final Properties ctx, final String trxName, final String wc)
	{
		return new Query(ctx, org.compiere.model.I_M_ProductPrice.Table_Name, wc, trxName)
				.setParameters(fc.getM_Product_Flatrate_ID(), plvId)
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.firstOnly(I_M_ProductPrice.class);
	}

	@Override
	public List<I_C_Flatrate_DataEntry> retrieveAndCheckInvoicingEntries(
			final I_C_Flatrate_Term flatrateTerm,
			final Timestamp startDate,
			final Timestamp endDate,
			final I_C_UOM uom,
			final List<String> errors)
	{
		final IFlatrateDAO flatrateDB = Services.get(IFlatrateDAO.class);

		final Properties ctx = InterfaceWrapperHelper.getCtx(flatrateTerm);
		final String trxName = InterfaceWrapperHelper.getTrxName(flatrateTerm);

		final boolean auxEntry = flatrateTerm.getC_Flatrate_Conditions().getC_UOM_ID() != uom.getC_UOM_ID();

		List<I_C_Flatrate_DataEntry> result = new ArrayList<>();

		final List<I_C_Flatrate_DataEntry> invoicingEntries = flatrateDB.retrieveInvoicingEntries(flatrateTerm, startDate, endDate, uom);

		final List<I_C_Period> periodsOfTerm = Services.get(ICalendarDAO.class).retrievePeriods(
				ctx, flatrateTerm.getC_Flatrate_Conditions().getC_Flatrate_Transition().getC_Calendar_Contract(), startDate, endDate, trxName);

		for (final I_C_Period periodOfTerm : periodsOfTerm)
		{
			boolean entryForPeriodMissing = true;
			for (final I_C_Flatrate_DataEntry invoicingEntry : invoicingEntries)
			{
				//
				// for each period of the term, we check if there is a completed data that has been fully invoiced
				if (invoicingEntry.getC_Period_ID() == periodOfTerm.getC_Period_ID())
				{
					entryForPeriodMissing = false;
					if (X_C_Flatrate_DataEntry.DOCSTATUS_Completed.equals(invoicingEntry.getDocStatus()))
					{
						// ok
						final I_C_Invoice_Candidate ic = invoicingEntry.getC_Invoice_Candidate();
						if (auxEntry
								|| invoicingEntry.isSimulation()
								|| ic != null && ic.getC_Invoice_Candidate_ID() > 0 && ic.isProcessed())
						{
							if (result != null)
							{
								result.add(invoicingEntry);
							}
						}
						else
						{
							final String msg = msgBL.getMsg(ctx, "FlatrateBL_InvoicingEntry_Not_Invoiced",
									new Object[] { periodOfTerm.getName(), uom.getName() });
							errors.add(msg);
							result = null;
						}
					}
					else
					{
						final String msg = msgBL.getMsg(ctx, "PrepareClosing_InvoicingEntry_Not_CO",
								new Object[] { uom.getName(), periodOfTerm.getName() });
						errors.add(msg);
						result = null;
					}
					break;
				}
			}
			if (entryForPeriodMissing)
			{
				result = null;

				final String msg = msgBL.getMsg(ctx, "PrepareClosing_InvoicingEntry_Missing", new Object[] { uom.getName(), periodOfTerm.getName() });
				errors.add(msg);
			}
		}
		return result;
	}

	@Override
	public void createDataEntriesForTerm(
			final I_C_Flatrate_Term flatrateTerm)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(flatrateTerm);
		final String trxName = InterfaceWrapperHelper.getTrxName(flatrateTerm);

		if (X_C_Flatrate_Term.TYPE_CONDITIONS_Pauschalengebuehr.equals(flatrateTerm.getType_Conditions()))
		{
			createEntriesForFlatFee(ctx, flatrateTerm, trxName);
		}
		else if (X_C_Flatrate_Term.TYPE_CONDITIONS_Abonnement.equals(flatrateTerm.getType_Conditions())
				|| X_C_Flatrate_Term.TYPE_CONDITIONS_Depotgebuehr.equals(flatrateTerm.getType_Conditions())
				|| X_C_Flatrate_Term.TYPE_CONDITIONS_Leergutverwaltung.equals(flatrateTerm.getType_Conditions()))
		{
			createEntriesForHoldingFee(ctx, flatrateTerm, trxName);
		}
	}

	private void createEntriesForHoldingFee(
			final Properties ctx,
			final I_C_Flatrate_Term flatrateTerm,
			final String trxName)
	{
		final IFlatrateDAO flatrateDB = Services.get(IFlatrateDAO.class);

		final List<I_M_Product> products = flatrateDB.retrieveHoldingFeeProducts(flatrateTerm.getC_Flatrate_Conditions());

		int counter = 0;

		final ICalendarDAO calendarDAO = Services.get(ICalendarDAO.class);

		final List<I_C_Period> periods = calendarDAO.retrievePeriods(
				ctx, flatrateTerm.getC_Flatrate_Conditions().getC_Flatrate_Transition().getC_Calendar_Contract(), flatrateTerm.getStartDate(), flatrateTerm.getEndDate(), trxName);
		for (final I_C_Period period : periods)
		{
			for (final I_M_Product product : products)
			{
				final I_C_UOM uom = product.getC_UOM();
				final I_C_Flatrate_DataEntry existingEntry = flatrateDB.retrieveDataEntryOrNull(flatrateTerm, period, X_C_Flatrate_DataEntry.TYPE_Invoicing_PeriodBased, uom);
				if (existingEntry != null)
				{
					continue;
				}

				final I_C_Flatrate_DataEntry newDataEntry = InterfaceWrapperHelper.create(ctx, I_C_Flatrate_DataEntry.class, trxName);
				newDataEntry.setAD_Org_ID(flatrateTerm.getAD_Org_ID());

				newDataEntry.setC_Flatrate_Term_ID(flatrateTerm.getC_Flatrate_Term_ID());

				newDataEntry.setC_Period_ID(period.getC_Period_ID());
				newDataEntry.setType(X_C_Flatrate_DataEntry.TYPE_Invoicing_PeriodBased);
				newDataEntry.setM_Product_DataEntry_ID(product.getM_Product_ID());
				newDataEntry.setC_UOM_ID(uom.getC_UOM_ID());

				InterfaceWrapperHelper.save(newDataEntry);
				counter++;
			}
		}

		final String msg = msgBL.getMsg(ctx, FlatrateBL.MSG_DATA_ENTRY_CREATE_HOLDING_FEE_3P,
				new Object[] {
						counter,
						flatrateTerm.getStartDate(),
						flatrateTerm.getEndDate() });
		Loggables.get().withLogger(logger, Level.INFO).addLog(msg);
	}

	private void createEntriesForFlatFee(
			final Properties ctx,
			final I_C_Flatrate_Term flatrateTerm,
			final String trxName)
	{
		int counter = 0;

		final ICalendarDAO calendarDAO = Services.get(ICalendarDAO.class);
		final IFlatrateDAO flatrateDB = Services.get(IFlatrateDAO.class);

		final List<I_C_UOM> uoms = flatrateDB.retrieveUOMs(ctx, flatrateTerm, trxName);

		final List<I_C_Period> periods = calendarDAO.retrievePeriods(
				ctx, flatrateTerm.getC_Flatrate_Conditions().getC_Flatrate_Transition().getC_Calendar_Contract(), flatrateTerm.getStartDate(), flatrateTerm.getEndDate(), trxName);
		for (final I_C_Period period : periods)
		{
			for (final I_C_UOM uom : uoms)
			{
				final I_C_Flatrate_DataEntry existingEntry = flatrateDB.retrieveDataEntryOrNull(flatrateTerm, period, X_C_Flatrate_DataEntry.TYPE_Invoicing_PeriodBased, uom);
				if (existingEntry != null)
				{
					continue;
				}

				final I_C_Flatrate_DataEntry newDataEntry = InterfaceWrapperHelper.create(ctx, I_C_Flatrate_DataEntry.class, trxName);
				newDataEntry.setAD_Org_ID(flatrateTerm.getAD_Org_ID());

				newDataEntry.setC_Flatrate_Term_ID(flatrateTerm.getC_Flatrate_Term_ID());

				newDataEntry.setC_Period_ID(period.getC_Period_ID());
				newDataEntry.setType(X_C_Flatrate_DataEntry.TYPE_Invoicing_PeriodBased);
				newDataEntry.setC_UOM_ID(uom.getC_UOM_ID());

				InterfaceWrapperHelper.save(newDataEntry);
				counter++;
			}
		}

		final POInfo poInfo = POInfo.getPOInfo(ctx, I_C_Flatrate_Term.Table_Name);
		final Lookup columnLookup = poInfo.getColumnLookup(ctx, poInfo.getColumnIndex(I_C_Flatrate_Term.COLUMNNAME_UOMType));

		final String msg = msgBL.getMsg(ctx, FlatrateBL.MSG_DATA_ENTRY_CREATE_FLAT_FEE_4P,
				new Object[] {
						counter,
						flatrateTerm.getStartDate(),
						flatrateTerm.getEndDate(),
						columnLookup.getDisplay(flatrateTerm.getUOMType()) });
		Loggables.get().withLogger(logger, Level.INFO).addLog(msg);
	}

	@Override
	public void updateEntry(final I_C_Flatrate_DataEntry dataEntry)
	{
		final I_C_Flatrate_Term term = dataEntry.getC_Flatrate_Term();
		final I_C_Flatrate_Conditions conditions = term.getC_Flatrate_Conditions();

		final int scale = 2;

		if (term.getC_Flatrate_Conditions_ID() < 0)
		{
			// nothing to do
			return;
		}

		final boolean auxEntry = dataEntry.getC_UOM_ID() != term.getC_UOM_ID();

		final BigDecimal actualQtyOfUnits;
		final boolean correctionWithoutActualQty;

		if (X_C_Flatrate_DataEntry.TYPE_Invoicing_PeriodBased.equals(dataEntry.getType()))
		{
			actualQtyOfUnits = dataEntry.getQty_Reported();
			correctionWithoutActualQty = false;
		}
		else
		{
			Check.assume(X_C_Flatrate_DataEntry.TYPE_Correction_PeriodBased.equals(dataEntry.getType()),
					dataEntry + " has Type=" + X_C_Flatrate_DataEntry.TYPE_Correction_PeriodBased);

			actualQtyOfUnits = dataEntry.getQty_Reported().subtract(dataEntry.getQty_Planned());
			correctionWithoutActualQty = !conditions.isCorrectionAmtAtClosing();
		}

		final BigDecimal actualQtyOfProducts = dataEntry.getActualQty();

		final BigDecimal plannedQtyPerUnit = term.getPlannedQtyPerUnit();

		final BigDecimal pricePerUnit;

		final BigDecimal actualQtyPerUnit;
		final BigDecimal diffPercent;

		final BigDecimal flatrateAmt;
		final BigDecimal expectedQtyOfProducts;
		final BigDecimal actualQtyDiffAbs;
		final BigDecimal actualDiffPerUnit;

		if (actualQtyOfProducts.signum() == 0 || actualQtyOfUnits.signum() == 0)
		{
			actualQtyPerUnit = BigDecimal.ZERO;
		}
		else
		{
			actualQtyPerUnit = actualQtyOfProducts.divide(actualQtyOfUnits, scale, RoundingMode.HALF_UP);
		}

		if (actualQtyOfUnits.signum() == 0)
		{
			pricePerUnit = auxEntry ? null : getFlatFeePricePerUnit(term, BigDecimal.ONE, dataEntry);

			flatrateAmt = auxEntry ? null : BigDecimal.ZERO;
		}
		else
		{
			pricePerUnit = auxEntry ? null : getFlatFeePricePerUnit(term, actualQtyOfUnits, dataEntry);

			flatrateAmt = auxEntry ? null : actualQtyOfUnits.multiply(pricePerUnit).setScale(scale, RoundingMode.HALF_UP);
		}

		actualDiffPerUnit = auxEntry || correctionWithoutActualQty ? null : actualQtyPerUnit.subtract(plannedQtyPerUnit).setScale(scale, RoundingMode.HALF_UP);

		if (auxEntry || correctionWithoutActualQty)
		{
			diffPercent = null;
		}
		else if (actualQtyOfUnits.signum() == 0)
		{
			diffPercent = BigDecimal.ZERO;
		}
		else
		{
			diffPercent = actualDiffPerUnit
					.divide(plannedQtyPerUnit, scale * 2, RoundingMode.HALF_UP)
					.multiply(Env.ONEHUNDRED)
					.setScale(scale, RoundingMode.HALF_UP);
		}

		expectedQtyOfProducts = auxEntry ? null : actualQtyOfUnits.multiply(plannedQtyPerUnit);

		actualQtyDiffAbs = auxEntry || correctionWithoutActualQty ? null : actualQtyOfProducts.subtract(expectedQtyOfProducts);

		dataEntry.setActualQtyPerUnit(actualQtyPerUnit);
		dataEntry.setActualQtyDiffPercent(diffPercent);
		dataEntry.setFlatrateAmt(flatrateAmt);
		dataEntry.setFlatrateAmtPerUOM(pricePerUnit);

		dataEntry.setActualQtyDiffAbs(actualQtyDiffAbs);
		dataEntry.setActualQtyDiffPerUOM(actualDiffPerUnit);

		final int currencyId;
		final BigDecimal amtCorrection;
		final BigDecimal effectiveDiffPercent;

		if (auxEntry)
		{
			currencyId = 0;
			amtCorrection = null;
			effectiveDiffPercent = null;
		}
		else
		{
			currencyId = term.getC_Currency_ID();

			if (X_C_Flatrate_Conditions.TYPE_FLATRATE_Korridor.equals(conditions.getType_Flatrate())
					&& !correctionWithoutActualQty)
			{

				final BigDecimal percentSubtrahent;
				final boolean marginExceeded;

				if (diffPercent.signum() > 0 && diffPercent.abs().compareTo(conditions.getMargin_Max()) > 0)
				{
					percentSubtrahent = conditions.getMargin_Max();
					marginExceeded = true;
				}
				else if (diffPercent.signum() < 0 && diffPercent.abs().compareTo(conditions.getMargin_Min()) > 0)
				{
					percentSubtrahent = conditions.getMargin_Min().negate();
					marginExceeded = true;
				}
				else
				{
					percentSubtrahent = BigDecimal.ZERO;
					marginExceeded = false;
				}

				if (marginExceeded)
				{
					if (X_C_Flatrate_Conditions.TYPE_CLEARING_Komplett.equals(conditions.getType_Clearing()))
					{
						effectiveDiffPercent = diffPercent;
					}
					else
					{
						Check.assume(X_C_Flatrate_Conditions.TYPE_CLEARING_Ueber_Unterschreitung.equals(conditions.getType_Clearing()),
								conditions + " has either Type_Clearing '" + X_C_Flatrate_Conditions.TYPE_CLEARING_Ueber_Unterschreitung + "' or '"
										+ X_C_Flatrate_Conditions.TYPE_CLEARING_Komplett
										+ "'");
						effectiveDiffPercent = diffPercent.subtract(percentSubtrahent);
					}

					// if
					// (X_C_Flatrate_Conditions.CLEARINGAMTBASEON_Pauschalenpreis.equals(conditions.getClearingAmtBaseOn()))
					// {
					Check.assume(X_C_Flatrate_Conditions.CLEARINGAMTBASEON_Pauschalenpreis.equals(conditions.getClearingAmtBaseOn()),
							conditions + " has ClearingAmtBaseOn='" + X_C_Flatrate_Conditions.CLEARINGAMTBASEON_Pauschalenpreis + "'");

					amtCorrection = flatrateAmt
							.multiply(effectiveDiffPercent.divide(Env.ONEHUNDRED, scale * 2, RoundingMode.HALF_UP))
							.setScale(scale, RoundingMode.HALF_UP);
					// }
					// else if
					// (X_C_Flatrate_Conditions.CLEARINGAMTBASEON_Produktpreis.equals(conditions.getClearingAmtBaseOn()))
					// {
					// throw new NotImplementedException();
					// }
					// else
					// {
					// throw new IllegalStateException(
					// "Expected '" + conditions + "' to have ClearingAmtBaseOn either'"
					// + X_C_Flatrate_Conditions.CLEARINGAMTBASEON_Pauschalenpreis
					// + "' or '" + X_C_Flatrate_Conditions.CLEARINGAMTBASEON_Produktpreis + "'");
					// }
				}
				else
				{
					amtCorrection = BigDecimal.ZERO;
					effectiveDiffPercent = BigDecimal.ZERO;
				}
			}
			else
			{
				amtCorrection = null;
				effectiveDiffPercent = null;
			}
		}

		dataEntry.setActualQtyDiffPercentEff(effectiveDiffPercent);
		dataEntry.setC_Currency_ID(currencyId);
		dataEntry.setFlatrateAmtCorr(amtCorrection);

		// System.out.println("--updateEntry--");
		// System.out.println("AuxEntry=" + auxEntry);
		// System.out.println("PricePerUnit=" + pricePerUnit);
		// System.out.println("ActualQtyDiffAbs=" + actualQtyDiffAbs);
		// System.out.println("ActualQtyPerUnit=" + actualQtyPerUnit);
		// System.out.println("Qty_Reported=" + dataEntry.getQty_Reported());
		// System.out.println("FlatrateAmt=" + flatrateAmt);
		// System.out.println("FlatrateAmtCorr=" + amtCorrection);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.metas.flatrate.api.IFlatrateBL#extendContract(de.metas.flatrate.model.I_C_Flatrate_Term, boolean, boolean, java.sql.Timestamp, de.metas.contracts.subscription.model.I_C_OrderLine)
	 * Note: OLD FUNCTIONALITY: optional,may be <code>null</code>. If not <code>null</code>, then this value will decide if the new term is completed.
	 * If <code>null</code>, then {@link I_C_Flatrate_Transition#isAutoCompleteNewTerm()} of the given <code>term</code> transition will decide.
	 * #712: This code was changed to always respect the flag. Leaving this here for legacy
	 */
	@Override
	public void extendContract(
			final I_C_Flatrate_Term currentTerm,
			final boolean forceExtend,
			final boolean forceComplete,
			final Timestamp nextTermStartDate,
			final I_C_OrderLine ol)
	{

		Services.get(ITrxManager.class).run(
				new TrxRunnable()
				{
					@Override
					public void run(final String localTrxName) throws Exception
					{
						extendContract0(currentTerm, forceExtend, forceComplete, nextTermStartDate, ol, localTrxName);
					}
				});
	}

	private void extendContract0(
			final I_C_Flatrate_Term currentTerm,
			final boolean forceExtend,
			final boolean forceComplete,
			final Timestamp nextTermStartDate,
			final I_C_OrderLine ol,
			final String trxName)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(currentTerm);

		final I_C_Flatrate_Conditions conditions = currentTerm.getC_Flatrate_Conditions();
		final I_C_Flatrate_Transition currentTransition = currentTerm.getC_Flatrate_Transition();

		I_C_Flatrate_Term termToReferenceInNote = null;
		String msgValue = null;

		if (currentTerm.isAutoRenew() || forceExtend)
		{
			Check.errorIf(currentTerm.getC_FlatrateTerm_Next_ID() > 0,
					"{} has C_FlatrateTerm_Next_ID = {} (should be <= 0)", currentTerm, currentTerm.getC_FlatrateTerm_Next_ID());

			final I_C_Flatrate_Conditions nextConditions;
			if (currentTransition.getC_Flatrate_Conditions_Next_ID() > 0)
			{
				nextConditions = currentTransition.getC_Flatrate_Conditions_Next();
			}
			else
			{
				nextConditions = currentTerm.getC_Flatrate_Conditions(); // just use the same conditions again
			}

			final I_C_Flatrate_Term nextTerm = InterfaceWrapperHelper.create(ctx, I_C_Flatrate_Term.class, trxName);
			nextTerm.setAD_Org_ID(currentTerm.getAD_Org_ID());

			nextTerm.setC_Flatrate_Data_ID(currentTerm.getC_Flatrate_Data_ID());
			nextTerm.setC_Flatrate_Conditions_ID(nextConditions.getC_Flatrate_Conditions_ID());
			nextTerm.setPlannedQtyPerUnit(currentTerm.getPlannedQtyPerUnit());
			nextTerm.setIsSimulation(currentTerm.isSimulation());
			nextTerm.setIsPostageFree(currentTerm.isPostageFree()); // 04836 : also copy the postage fee

			nextTerm.setBill_BPartner_ID(currentTerm.getBill_BPartner_ID());
			nextTerm.setBill_Location_ID(currentTerm.getBill_Location_ID());
			nextTerm.setBill_User_ID(currentTerm.getBill_User_ID());

			nextTerm.setAD_User_InCharge_ID(currentTerm.getAD_User_InCharge_ID());
			final I_C_Flatrate_Transition nextTransition = nextConditions.getC_Flatrate_Transition();

			// gh #549: if an explicit start date was given, then use it (if it is OK).
			final Timestamp dayAfterEndDate = TimeUtil.addDays(currentTerm.getEndDate(), 1);
			final Timestamp firstDayOfNewTerm;
			if (nextTermStartDate != null)
			{
				if (nextTermStartDate.before(currentTerm.getStartDate()) || nextTermStartDate.after(dayAfterEndDate))
				{
					Loggables.get().addLog(
							"Ignore nextTermStartDate={} because if is not between currentTerm's StartDate={} and DayAfterEndDate={}. Instead, use dayAfterEndDate",
							nextTermStartDate, currentTerm.getStartDate(), dayAfterEndDate);
					firstDayOfNewTerm = dayAfterEndDate;
				}
				else
				{
					firstDayOfNewTerm = nextTermStartDate;
				}
			}
			else
			{
				firstDayOfNewTerm = dayAfterEndDate;
			}

			nextTerm.setStartDate(firstDayOfNewTerm);

			if (ol != null)
			{
				nextTerm.setC_OrderLine_Term(ol);
			}
			updateEndDate(nextTransition, nextTerm);
			updateNoticeDate(nextTransition, nextTerm);

			// 03786: set the newly added fields
			// Preissystem, Lieferempfaenger, Lieferadresse, Produkt

			nextTerm.setM_PricingSystem(currentTerm.getM_PricingSystem());
			nextTerm.setDropShip_BPartner_ID(currentTerm.getDropShip_BPartner_ID());
			nextTerm.setDropShip_Location_ID(currentTerm.getDropShip_Location_ID());
			nextTerm.setM_Product_ID(currentTerm.getM_Product_ID());
			nextTerm.setDropShip_User_ID(currentTerm.getDropShip_User_ID());
			nextTerm.setDeliveryRule(currentTerm.getDeliveryRule());
			nextTerm.setDeliveryViaRule(currentTerm.getDeliveryViaRule());
			nextTerm.setPriceActual(currentTerm.getPriceActual());
			nextTerm.setC_UOM_ID(currentTerm.getC_UOM_ID());
			nextTerm.setC_Currency_ID(currentTerm.getC_Currency_ID());

			InterfaceWrapperHelper.save(nextTerm);

			// the conditions were set via de.metas.flatrate.modelvalidator.C_Flatrate_Term.copyFromConditions(term)
			Check.errorUnless(currentTerm.getType_Conditions().equals(nextTerm.getType_Conditions()),
					"currentTerm has Type_Conditions={} while nextTerm has Type_Conditions={}; currentTerm={}",
					currentTerm.getType_Conditions(), nextTerm.getType_Conditions(), currentTerm);

			currentTerm.setC_FlatrateTerm_Next_ID(nextTerm.getC_Flatrate_Term_ID());

			// gh #549: notify that handler so it might do additional things. In the case of this task, it shall create C_Flatrate_DataEntry records
			final IFlatrateHandlersService flatrateHandlersService = Services.get(IFlatrateHandlersService.class);
			flatrateHandlersService
					.getHandler(nextTerm.getType_Conditions())
					.afterExtendFlatrateTermCreated(currentTerm, nextTerm);

			// gh #549: if forceComplete was set, then use it; otherwise fall back to the setting of currentTransition
			// final boolean completeNextTerm = forceComplete != null ? forceComplete : currentTransition.isAutoCompleteNewTerm();
			// #712: Respect the value of the complete flag.
			if (forceComplete)
			{
				nextTerm.setDocAction(X_C_Flatrate_Term.DOCACTION_Complete);

				final IDocActionBL docActionBL = Services.get(IDocActionBL.class);
				docActionBL.processEx(nextTerm, DocAction.ACTION_Complete, DocAction.STATUS_Completed);

				InterfaceWrapperHelper.save(nextTerm);

				if (currentTransition.isNotifyUserInCharge())
				{
					msgValue = FlatrateBL.MSG_TERM_NEW_COMPLETED_0P;
					termToReferenceInNote = nextTerm;
				}
			}
			else
			{
				if (currentTransition.isNotifyUserInCharge())
				{
					msgValue = FlatrateBL.MSG_TERM_NEW_UNCOMPLETED_0P;
					termToReferenceInNote = nextTerm;
				}
			}

			final IWFExecutionFactory wfExecutionFactory = Services.get(IWFExecutionFactory.class);
			wfExecutionFactory.notifyActivityPerformed(currentTerm, nextTerm); // 03745
		}
		else
		{
			if (currentTransition.isNotifyUserInCharge())
			{
				msgValue = FlatrateBL.MSG_TERM_NO_NEW_0P;
				termToReferenceInNote = currentTerm;
			}
		}

		if (msgValue != null)
		{
			Check.assume(currentTerm.getAD_User_InCharge_ID() > 0, conditions + " has AD_User_InCharge_ID > 0");
			Check.assume(termToReferenceInNote != null, "");

			final MNote note = new MNote(
					ctx,
					msgValue,
					currentTerm.getAD_User_InCharge_ID(),
					trxName);
			note.setAD_Org_ID(currentTerm.getAD_Org_ID());
			note.setRecord(adTableDAO.retrieveTableId(I_C_Flatrate_Term.Table_Name), termToReferenceInNote.getC_Flatrate_Term_ID());
			note.setTextMsg(msgBL.getMsg(ctx, msgValue));
			note.saveEx();
		}
	}

	@Override
	public void updateNoticeDateAndEndDate(final I_C_Flatrate_Term term)
	{
		Check.assume(!X_C_Flatrate_Term.DOCSTATUS_Completed.equals(term.getDocStatus()), term + " is not yet completed");

		final I_C_Flatrate_Transition transition;
		if (term.getC_Flatrate_Transition_ID() > 0)
		{
			transition = term.getC_Flatrate_Transition();
		}
		else
		{
			// Note: C_Flatrate_Term.C_Flatrate_Transition_ID is a SQL column pointing to
			// C_Flatrate_Conditions.C_Flatrate_Transition_ID.
			// Before the first save, that value has not been loaded yet and is null.
			transition = term.getC_Flatrate_Conditions().getC_Flatrate_Transition();
		}

		Check.errorUnless(transition != null, "{} shall have transition set", term);

		term.setIsAutoRenew(transition.isAutoRenew());
		updateEndDate(transition, term);
		updateNoticeDate(transition, term);
	}

	private void updateEndDate(final I_C_Flatrate_Transition transition, final I_C_Flatrate_Term term)
	{
		final Timestamp firstDayOfNewTerm = term.getStartDate();

		Timestamp lastDayOfNewTerm = null;

		// metas: rc: start 03742
		// Condition added to handle "EnsWithCalendarYear" flag for transitions
		if (transition.isEndsWithCalendarYear())
		{
			final int termDuration; // term duration in years

			if (X_C_Flatrate_Transition.TERMDURATIONUNIT_JahrE.equals(transition.getTermDurationUnit()))
			{
				termDuration = transition.getTermDuration();
			}
			else
			// duration units = months
			{
				// just to make sure; this should never happen because of the model validator
				Check.errorUnless(X_C_Flatrate_Transition.TERMDURATIONUNIT_MonatE.equals(transition.getTermDurationUnit()),
						"The term duration unit is not suitable for a term that ends with calendar year");
				Check.errorUnless(transition.getTermDuration() % 12 == 0, "Term duration not suitable for a term that ends with calendar year");

				termDuration = transition.getTermDuration() / 12;
			}

			Timestamp currentFirstDay = firstDayOfNewTerm; // first day of term or first day of new year

			for (int i = 0; i < termDuration; i++)
			{
				final List<I_C_Period> periodContainingDay = Services.get(ICalendarDAO.class).retrievePeriods(
						InterfaceWrapperHelper.getCtx(transition), transition.getC_Calendar_Contract(), currentFirstDay, currentFirstDay, InterfaceWrapperHelper.getTrxName(transition));

				Check.errorUnless(periodContainingDay.size() != 0, "Date {} does not exist in calendar", currentFirstDay);
				Check.errorUnless(periodContainingDay.size() == 1, "Date {} is contained in more than one period: {}", currentFirstDay, periodContainingDay);
				final I_C_Period period = periodContainingDay.get(0);
				final I_C_Year year = period.getC_Year();

				lastDayOfNewTerm = Services.get(ICalendarBL.class).getLastDayOfYear(year);

				currentFirstDay = TimeUtil.addDays(lastDayOfNewTerm, 1);
			}
		}
		// Case: If TermDuration is ZERO, we shall not calculate the EndDate automatically,
		// but relly on what was set
		else if (transition.getTermDuration() == 0)
		{
			return;
		}
		else
		{
			if (X_C_Flatrate_Transition.TERMDURATIONUNIT_JahrE.equals(transition.getTermDurationUnit()))
			{
				lastDayOfNewTerm = TimeUtil.addDays(TimeUtil.addYears(firstDayOfNewTerm, transition.getTermDuration()), -1);
			}
			else if (X_C_Flatrate_Transition.TERMDURATIONUNIT_MonatE.equals(transition.getTermDurationUnit()))
			{
				lastDayOfNewTerm = TimeUtil.addDays(TimeUtil.addMonths(firstDayOfNewTerm, transition.getTermDuration()), -1);
			}
			else if (X_C_Flatrate_Transition.TERMDURATIONUNIT_WocheN.equals(transition.getTermDurationUnit()))
			{
				lastDayOfNewTerm = TimeUtil.addDays(TimeUtil.addWeeks(firstDayOfNewTerm, transition.getTermDuration()), -1);
			}
			else if (X_C_Flatrate_Transition.TERMDURATIONUNIT_TagE.equals(transition.getTermDurationUnit()))
			{
				lastDayOfNewTerm = TimeUtil.addDays(TimeUtil.addDays(firstDayOfNewTerm, transition.getTermDuration()), -1);
			}
			else
			{
				Check.assume(false, "TermDurationUnit " + transition.getTermDuration() + " doesn't exist");
				lastDayOfNewTerm = null; // code won't be reached
			}
		}

		term.setEndDate(lastDayOfNewTerm);
	}

	/**
	 * Update NoticeDate of the given term. Uses the given transition and the term's EndDate.
	 *
	 * @param transition
	 * @param term
	 */
	private void updateNoticeDate(final I_C_Flatrate_Transition transition, final I_C_Flatrate_Term term)
	{
		final Timestamp lastDayOfNewTerm = term.getEndDate();

		final Timestamp noticeDate;
		if (lastDayOfNewTerm == null)
		{
			// If Last day of new term is not set, don't calculate the noticeDate
			noticeDate = null;
		}
		else if (X_C_Flatrate_Transition.TERMOFNOTICEUNIT_MonatE.equals(transition.getTermOfNoticeUnit()))
		{
			noticeDate = TimeUtil.addMonths(lastDayOfNewTerm, transition.getTermOfNotice() * -1);
		}
		else if (X_C_Flatrate_Transition.TERMOFNOTICEUNIT_WocheN.equals(transition.getTermOfNoticeUnit()))
		{
			noticeDate = TimeUtil.addWeeks(lastDayOfNewTerm, transition.getTermOfNotice() * -1);
		}
		else if (X_C_Flatrate_Transition.TERMOFNOTICEUNIT_TagE.equals(transition.getTermOfNoticeUnit()))
		{
			noticeDate = TimeUtil.addDays(lastDayOfNewTerm, transition.getTermOfNotice() * -1);
		}
		else
		{
			Check.assume(false, "TermOfNoticeDuration " + transition.getTermOfNoticeUnit() + " doesn't exist");
			noticeDate = null; // code won't be reached
		}

		term.setNoticeDate(noticeDate);
	}

	/**
	 * @param term the contract term that the method retrieves the doc type for. Note that we can assume such a doc type exists, because it should have been made sure by {@link C_Flatrate_Term}.
	 *
	 * @returns the doc type for the given term
	 */
	@Override
	public I_C_DocType getDocTypeFor(final I_C_Flatrate_Term term)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(term);
		final String trxName = InterfaceWrapperHelper.getTrxName(term);

		final String subType;
		if (X_C_Flatrate_Term.TYPE_CONDITIONS_Abonnement.equals(term.getType_Conditions()))
		{
			subType = de.metas.flatrate.interfaces.I_C_DocType.DocSubType_Abonnement;
		}
		else if (X_C_Flatrate_Term.TYPE_CONDITIONS_Depotgebuehr.equals(term.getType_Conditions()))
		{
			subType = de.metas.flatrate.interfaces.I_C_DocType.DocSubType_Depotgebuehr;
		}
		else if (X_C_Flatrate_Term.TYPE_CONDITIONS_Pauschalengebuehr.equals(term.getType_Conditions()))
		{
			subType = de.metas.flatrate.interfaces.I_C_DocType.DocSubType_Pauschalengebuehr;
		}
		else
		{
			Check.assume(false, term + " has unexpected Type_Conditions=" + term.getType_Conditions());
			subType = null;
		}

		final IDocumentPA documentPA = Services.get(IDocumentPA.class);
		return documentPA.retrieve(ctx, term.getAD_Org_ID(), de.metas.flatrate.interfaces.I_C_DocType.DocBaseType_CustomerContract, subType, true, trxName);
	}

	@Override
	public I_C_Order createOrderForTerm(final I_C_Flatrate_Term term)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(term);
		final String trxName = InterfaceWrapperHelper.getTrxName(term);

		final I_C_Order order = InterfaceWrapperHelper.create(ctx, I_C_Order.class, trxName);
		order.setAD_Org_ID(term.getAD_Org_ID());

		final I_C_Flatrate_Data data = term.getC_Flatrate_Data();
		order.setC_BPartner_ID(data.getC_BPartner_ID());
		// order.setC_BPartner_Location_ID(C_BPartner_Location_ID)
		// order.setAD_User_ID(AD_User_ID)

		order.setBill_BPartner_ID(term.getBill_BPartner_ID());
		order.setBill_Location_ID(term.getBill_Location_ID());
		order.setBill_User_ID(term.getBill_User_ID());

		order.setDropShip_BPartner_ID(term.getDropShip_BPartner_ID());
		order.setDropShip_Location_ID(term.getDropShip_Location_ID());
		order.setDropShip_User_ID(term.getDropShip_User_ID());

		order.setC_Currency_ID(term.getC_Currency_ID());
		order.setC_DocTypeTarget_ID(
				Services.get(IDocumentPA.class).retrieve(ctx, term.getAD_Org_ID(), X_C_DocType.DOCBASETYPE_SalesOrder, X_C_DocType.DOCSUBTYPE_StandardOrder, true, trxName)
						.getC_DocType_ID());
		order.setDateOrdered(term.getStartDate());

		order.setDeliveryRule(term.getDeliveryRule());
		order.setDeliveryViaRule(term.getDeliveryViaRule());
		order.setDocAction(X_C_Order.DOCACTION_Complete);

		final I_C_Flatrate_Conditions conditions = term.getC_Flatrate_Conditions();
		order.setInvoiceRule(conditions.getInvoiceRule());
		order.setIsSOTrx(true);
		order.setM_PricingSystem_ID(conditions.getM_PricingSystem_ID());
		final int warehouseId = getWarehouse(term);
		order.setM_Warehouse_ID(warehouseId);

		InterfaceWrapperHelper.save(order);

		final I_C_OrderLine ol = InterfaceWrapperHelper.create(new MOrderLine((MOrder)InterfaceWrapperHelper.getPO(order)), I_C_OrderLine.class);
		ol.setM_Product_ID(term.getM_Product_ID());
		ol.setQtyEntered(term.getPlannedQtyPerUnit());
		Services.get(ISubscriptionBL.class).setSubscription(ol, conditions);

		InterfaceWrapperHelper.save(ol);

		// Note that we set the term's C_OrderLine_ID before trying to complete the order.
		// Otherwise the system would try to create another term on the order's completion
		term.setC_OrderLine_Term_ID(ol.getC_OrderLine_ID());
		InterfaceWrapperHelper.save(term);

		Services.get(IDocActionBL.class).processEx(order, X_C_Order.DOCACTION_Complete, X_C_Order.DOCSTATUS_Completed);

		return order;
	}

	@Override
	public int getWarehouse(final I_C_Flatrate_Term term)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(term);

		final int warehouseId;
		if (term.getC_OrderLine_Term_ID() > 0)
		{
			warehouseId = term.getC_OrderLine_Term().getM_Warehouse_ID();
		}
		else
		{
			final List<I_M_Warehouse> warehousesForOrg = Services.get(IWarehouseDAO.class).retrieveForOrg(ctx, term.getAD_Org_ID());
			if (warehousesForOrg.size() == 0)
			{
				throw new AdempiereException(
						"de.metas.flatrate.Org.Warehouse_Missing",
						Env.getAD_Language(ctx),
						new Object[] { msgBL.translate(ctx, I_AD_Org.COLUMNNAME_AD_Org_ID), MOrg.get(ctx, term.getAD_Org_ID()) });
			}
			warehouseId = warehousesForOrg.get(0).getM_Warehouse_ID();
		}
		return warehouseId;
	}

	@Override
	public void updateFromConditions(final I_C_Flatrate_Term term)
	{
		final I_C_Flatrate_Conditions conditions = term.getC_Flatrate_Conditions();
		if (conditions == null)
		{
			return;
		}

		term.setType_Conditions(conditions.getType_Conditions());
		// NOTE: do not save, it will be used in model validators and callouts too
	}

	@Override
	public void updateFlatrateDataEntryQty(
			final org.compiere.model.I_M_Product product,
			final BigDecimal qty,
			final I_M_InOutLine document,
			final boolean substract)
	{
		final I_C_BPartner partner = document.getM_InOut().getC_BPartner();
		final Timestamp movementDate = document.getM_InOut().getMovementDate();

		final I_C_Flatrate_DataEntry entry = flatrateDB.retrieveRefundableDataEntry(partner.getC_BPartner_ID(), movementDate, product);

		final BigDecimal documentAmount = substract ? qty.negate() : qty;

		if (null != entry)
		{
			entry.setActualQty(entry.getActualQty().add(documentAmount));
			InterfaceWrapperHelper.save(entry);
		}
	}

	@Override
	public I_C_Flatrate_Term createTerm(
			final IContextAware context,
			final I_C_BPartner bPartner,
			final I_C_Flatrate_Conditions conditions,
			final Timestamp startDate,
			final I_AD_User userInCharge,
			final org.compiere.model.I_M_Product product,
			final boolean completeIt)
	{
		final Properties ctx = context.getCtx();
		final String trxName = context.getTrxName();

		boolean dontCreateTerm = false;
		final StringBuilder notCreatedReason = new StringBuilder();
		if (userInCharge == null && bPartner.getSalesRep_ID() <= 0)
		{
			notCreatedReason.append(" has no salesrep and userInCharge not set;");
			dontCreateTerm = true;
		}
		if (!bPartner.isCustomer() && !bPartner.isVendor())
		{
			notCreatedReason.append(" is neither customer nor vendor;");
			dontCreateTerm = true;
		}

		final I_C_BPartner_Location billPartnerLocation = bPartnerDAO.retrieveBillToLocation(ctx, bPartner.getC_BPartner_ID(),
				true,            // alsoTryBPartnerRelation
				trxName);
		if (billPartnerLocation == null)
		{
			notCreatedReason.append(" has no billTo location;");
			dontCreateTerm = true;
		}

		if (product == null)
		{
			if (!flatrateDB.retrieveTerms(bPartner, conditions).isEmpty())
			{
				notCreatedReason.append(" already has a term;");
				dontCreateTerm = true;
			}
		}
		else
		{
			if (!flatrateDB.retrieveTerms(ctx,
					bPartner.getC_BPartner_ID(),
					null,
					product.getM_Product_Category_ID(),
					product.getM_Product_ID(),
					-1,
					trxName).isEmpty())
			{
				notCreatedReason.append(" already has a term;");
				dontCreateTerm = true;
			}
		}

		if (dontCreateTerm)
		{
			throw new AdempiereException("@NotCreated@ @C_Flatrate_Term_ID@ (@C_BPartner_ID@: " + bPartner.getValue() + "): " + notCreatedReason);
		}

		final I_C_Flatrate_Term newTerm = InterfaceWrapperHelper.newInstance(I_C_Flatrate_Term.class, bPartner);
		newTerm.setC_Flatrate_Conditions(conditions);
		newTerm.setC_UOM_ID(conditions.getC_UOM_ID());
		newTerm.setAD_Org_ID(bPartner.getAD_Org_ID());

		newTerm.setStartDate(startDate);
		newTerm.setEndDate(startDate); // will be updated by BL
		newTerm.setDropShip_BPartner(bPartner);

		newTerm.setBill_BPartner_ID(billPartnerLocation.getC_BPartner_ID()); // note that in case of bPartner relations, this might be a different partner than 'bPartner'.
		newTerm.setBill_Location(billPartnerLocation);

		if (userInCharge == null)
		{
			newTerm.setAD_User_InCharge(bPartner.getSalesRep());
		}
		else
		{
			newTerm.setAD_User_InCharge(userInCharge);
		}

		final I_C_Flatrate_Data data = flatrateDB.retriveOrCreateFlatrateData(bPartner);
		newTerm.setC_Flatrate_Data(data);

		InterfaceWrapperHelper.save(newTerm);

		if (completeIt)
		{
			complete(newTerm);
		}
		return newTerm;
	}

	@Override
	public void complete(final I_C_Flatrate_Term term)
	{
		// NOTE: the whole reason why we have this method is for readability ease of refactoring.
		Services.get(IDocActionBL.class).processEx(term, DocAction.ACTION_Complete, DocAction.STATUS_Completed);
	}

	@Override
	public void voidIt(final I_C_Flatrate_Term term)
	{
		// NOTE: the whole reason why we have this method is for readability ease of refactoring.
		Services.get(IDocActionBL.class).processEx(term, DocAction.ACTION_Void, DocAction.STATUS_Voided);

	}
}
