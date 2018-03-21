package de.metas.contracts.impl;

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
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.bpartner.service.IBPartnerDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Loggables;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_Activity;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_C_Year;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.Lookup;
import org.compiere.model.MNote;
import org.compiere.model.MRefList;
import org.compiere.model.POInfo;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import ch.qos.logback.classic.Level;
import de.metas.adempiere.service.ICalendarBL;
import de.metas.adempiere.service.ICalendarDAO;
import de.metas.contracts.FlatrateTermPricing;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.IFlatrateTermEventService;
import de.metas.contracts.interceptor.C_Flatrate_Term;
import de.metas.contracts.invoicecandidate.FlatrateDataEntryHandler;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Data;
import de.metas.contracts.model.I_C_Flatrate_DataEntry;
import de.metas.contracts.model.I_C_Flatrate_Matching;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_Flatrate_Transition;
import de.metas.contracts.model.I_C_Invoice_Clearing_Alloc;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.contracts.model.X_C_Flatrate_DataEntry;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Transition;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.i18n.IMsgBL;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerDAO;
import de.metas.invoicecandidate.model.I_C_ILCandHandler;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.logging.LogManager;
import de.metas.product.acct.api.IProductAcctDAO;
import de.metas.tax.api.ITaxBL;
import de.metas.workflow.api.IWFExecutionFactory;
import lombok.NonNull;

public class FlatrateBL implements IFlatrateBL
{

	private static final Logger logger = LogManager.getLogger(FlatrateBL.class);

	private static final String MSG_FLATRATEBL_INVOICING_ENTRY_NOT_CO_3P = "FlatrateBL_InvoicingEntry_Not_CO";

	private static final String MSG_FLATRATEBL_INVOICE_CANDIDATE_TO_RECOMPUTE_1P = "FlatrateBL_InvoicingCand_ToRecompute";

	private static final String MSG_FLATRATEBL_INVOICE_CANDIDATE_QTY_TO_INVOICE_1P = "FlatrateBL_InvoicingCand_QtyToInvoice";

	private static final String MSG_DATA_ENTRY_CREATE_FLAT_FEE_4P = "DataEntry_Create_FlatFee";

	private static final String MSG_DATA_ENTRY_CREATE_HOLDING_FEE_3P = "DataEntry_Create_HoldingFee";

	private static final String MSG_TERM_NEW_UNCOMPLETED_0P = "FlatrateTerm_New_Uncompleted_Term";
	private static final String MSG_TERM_NEW_COMPLETED_0P = "FlatrateTerm_New_Completed_Term";
	private static final String MSG_TERM_NO_NEW_0P = "FlatrateTerm_No_New_Term";

	/**
	 * Message for announcing the user that there are overlapping terms for the term they want to complete
	 */
	public static final String MSG_HasOverlapping_Term = "de.metas.flatrate.process.C_Flatrate_Term_Create.OverlappingTerm";

	private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);

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

		if (X_C_Flatrate_Conditions.TYPE_CONDITIONS_FlatFee.equals(fc.getType_Conditions())
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
		if (X_C_Flatrate_Conditions.TYPE_CONDITIONS_FlatFee.equals(fc.getType_Conditions()))
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

		Check.assume(X_C_Flatrate_Conditions.TYPE_CONDITIONS_FlatFee.equals(fc.getType_Conditions())
				|| X_C_Flatrate_Conditions.TYPE_CONDITIONS_Refundable.equals(fc.getType_Conditions()),
				fc + " has Type_Conditions=" + X_C_Flatrate_Conditions.TYPE_CONDITIONS_FlatFee
						+ " or " + X_C_Flatrate_Conditions.TYPE_CONDITIONS_Refundable);

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
					fc + " with Type_Conditions=" + X_C_Flatrate_Conditions.TYPE_CONDITIONS_FlatFee + " has no M_Product_Flatrate");

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

		// 07442 activity and tax
		final IContextAware contextProvider = InterfaceWrapperHelper.getContextAware(term);

		final I_M_Product product = InterfaceWrapperHelper.create(ctx, productId, I_M_Product.class, trxName);
		final I_C_Activity activity = Services.get(IProductAcctDAO.class).retrieveActivityForAcct(contextProvider, term.getAD_Org(), product);

		newCand.setC_Activity(activity);
		newCand.setIsTaxIncluded(term.isTaxIncluded());

		final int taxCategoryId = term.getC_TaxCategory_ID();
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
				Check.errorIf(handlerFound, "The handler list contains more than one active handler for the class {}; list={}", FlatrateDataEntryHandler.class, handlers);
				newCand.setC_ILCandHandler(handler);
				handlerFound = true;
			}
		}

		Check.errorIf(!handlerFound, "There is no (aktive) active C_ILCandHandler record for the class {}", FlatrateDataEntryHandler.class);
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

		if (X_C_Flatrate_Conditions.TYPE_CONDITIONS_HoldingFee.equals(fc.getType_Conditions())
				|| X_C_Flatrate_Conditions.TYPE_CONDITIONS_Refundable.equals(fc.getType_Conditions()))
		{
			final I_M_Product product = InterfaceWrapperHelper.create(dataEntry.getM_Product_DataEntry(), I_M_Product.class);
			productIdForIc = product.getM_Product_ID();
			Check.assume(productIdForIc > 0,
					dataEntry + " has no M_Product_DataEntry, despite " + fc + "has Type_Conditions=" + fc.getType_Conditions());

			priceActual = FlatrateTermPricing.builder()
					.term(term)
					.termRelatedProduct(product)
					.priceDate(dataEntry.getC_Period().getStartDate())
					.qty(qtyToInvoice)
					.build().computeOrThrowEx().getPriceStd();
		}
		else
		{
			Check.assume(X_C_Flatrate_Conditions.TYPE_CONDITIONS_FlatFee.equals(fc.getType_Conditions()),
					fc + " has Type_Conditions=" + X_C_Flatrate_Conditions.TYPE_CONDITIONS_FlatFee);

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
					fc + " with Type_Conditions=" + X_C_Flatrate_Conditions.TYPE_CONDITIONS_FlatFee + " has no M_Product_Flatrate");

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

		newCand.setAD_Table_ID(adTableDAO.retrieveTableId(I_C_Flatrate_DataEntry.Table_Name));
		newCand.setRecord_ID(dataEntry.getC_Flatrate_DataEntry_ID());

		// 07442 activity and tax
		final IContextAware contextProvider = InterfaceWrapperHelper.getContextAware(term);

		final I_M_Product product = InterfaceWrapperHelper.create(ctx, productIdForIc, I_M_Product.class, trxName);
		final I_C_Activity activity = Services.get(IProductAcctDAO.class).retrieveActivityForAcct(contextProvider, term.getAD_Org(), product);

		newCand.setC_Activity(activity);
		newCand.setIsTaxIncluded(term.isTaxIncluded());

		final int taxCategoryId = term.getC_TaxCategory_ID();
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
			flatrateProduct = flatrateCond.getM_Product_Flatrate();
		}
		else
		{
			Check.assume(X_C_Flatrate_DataEntry.TYPE_Correction_PeriodBased.equals(dataEntry.getType()), "");
			flatrateProduct = flatrateCond.getM_Product_Correction();
		}

		return FlatrateTermPricing.builder()
				.term(flatrateTerm)
				.termRelatedProduct(flatrateProduct)
				.priceDate(dataEntry.getC_Period().getStartDate())
				.qty(qty)
				.build().computeOrThrowEx().getPriceStd();
	}

	@Override
	public void validatePricing(final I_C_Flatrate_Term term)
	{
		final I_C_Flatrate_Conditions fc = term.getC_Flatrate_Conditions();

		final Timestamp date = SystemTime.asTimestamp();

		final I_M_Product flatrateProduct = fc.getM_Product_Flatrate();
		validatePricingForProduct(term, flatrateProduct, date);

		if (fc.isClosingWithActualSum() && X_C_Flatrate_Conditions.TYPE_FLATRATE_Corridor_Percent.equals(fc.getType_Flatrate()))
		{
			Check.assume(fc.getM_Product_Actual_ID() > 0, fc + " has no product to invoice the flatRateCorrectionAmt");
			final I_M_Product actualProduct = fc.getM_Product_Actual();
			validatePricingForProduct(term, actualProduct, date);
		}
		if (fc.isClosingWithCorrectionSum())
		{
			Check.assume(fc.getM_Product_Correction_ID() > 0, fc + " has no product to invoice the corrected qty_reported");
			final I_M_Product correctionProduct = fc.getM_Product_Correction();
			validatePricingForProduct(term, correctionProduct, date);
		}
	}

	private void validatePricingForProduct(
			@NonNull final I_C_Flatrate_Term term,
			@NonNull final I_M_Product product,
			@NonNull final Timestamp date)
	{
		FlatrateTermPricing.builder()
				.term(term)
				.termRelatedProduct(product)
				.priceDate(date)
				.qty(BigDecimal.ONE)
				.build()
				.computeOrThrowEx();
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

		if (X_C_Flatrate_Term.TYPE_CONDITIONS_FlatFee.equals(flatrateTerm.getType_Conditions()))
		{
			createEntriesForFlatFee(ctx, flatrateTerm, trxName);
		}
		else if (X_C_Flatrate_Term.TYPE_CONDITIONS_Subscription.equals(flatrateTerm.getType_Conditions())
				|| X_C_Flatrate_Term.TYPE_CONDITIONS_HoldingFee.equals(flatrateTerm.getType_Conditions())
				|| X_C_Flatrate_Term.TYPE_CONDITIONS_Refundable.equals(flatrateTerm.getType_Conditions()))
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

			if (X_C_Flatrate_Conditions.TYPE_FLATRATE_Corridor_Percent.equals(conditions.getType_Flatrate())
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
					if (X_C_Flatrate_Conditions.TYPE_CLEARING_Complete.equals(conditions.getType_Clearing()))
					{
						effectiveDiffPercent = diffPercent;
					}
					else
					{
						Check.assume(X_C_Flatrate_Conditions.TYPE_CLEARING_Exceeding.equals(conditions.getType_Clearing()),
								conditions + " has either Type_Clearing '" + X_C_Flatrate_Conditions.TYPE_CLEARING_Exceeding + "' or '"
										+ X_C_Flatrate_Conditions.TYPE_CLEARING_Complete
										+ "'");
						effectiveDiffPercent = diffPercent.subtract(percentSubtrahent);
					}

					// if
					// (X_C_Flatrate_Conditions.CLEARINGAMTBASEON_Pauschalenpreis.equals(conditions.getClearingAmtBaseOn()))
					// {
					Check.assume(X_C_Flatrate_Conditions.CLEARINGAMTBASEON_FlatrateAmount.equals(conditions.getClearingAmtBaseOn()),
							conditions + " has ClearingAmtBaseOn='" + X_C_Flatrate_Conditions.CLEARINGAMTBASEON_FlatrateAmount + "'");

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
	}

	@Override
	public void extendContract(final @NonNull ContractExtendingRequest context)
	{
		Services.get(ITrxManager.class).run(ITrx.TRXNAME_ThreadInherited, localTrxName -> {

			ContractExtendingRequest contextUsed = context;
			I_C_Flatrate_Transition nextTransition = null;
			final List<I_C_Flatrate_Term> contracts = new ArrayList<>();
			contracts.add(contextUsed.getContract());
			do
			{
				extendContract0(contextUsed, localTrxName);

				final I_C_Flatrate_Term currentTerm = contextUsed.getContract();
				currentTerm.setAD_PInstance_EndOfTerm_ID(contextUsed.getAD_PInstance_ID());
				InterfaceWrapperHelper.save(currentTerm);

				final I_C_Flatrate_Term nextTerm = currentTerm.getC_FlatrateTerm_Next();
				final I_C_Flatrate_Conditions nextConditions = nextTerm.getC_Flatrate_Conditions();
				Check.assumeNotNull(nextConditions, "C_Flatrate_Conditions shall not be null!");

				nextTransition = nextConditions.getC_Flatrate_Transition();
				Check.assumeNotNull(nextTransition, "C_Flatrate_Transition shall not be null!");

				contextUsed = contextUsed.toBuilder()
						  .AD_PInstance_ID(context.getAD_PInstance_ID())
						  .contract(nextTerm).build();

				contracts.add(nextTerm);
			}
			while (X_C_Flatrate_Transition.EXTENSIONTYPE_ExtendAll.equals(nextTransition.getExtensionType()) && nextTransition.getC_Flatrate_Conditions_Next_ID() > 0);

			updateMasterEndDateIfNeeded(contracts, context.getContract());
		});
	}

	/**
	 * Update <code>masterenddate</code> only for contract of which we know the entire period
	 * @param contracts
	 * @param initialContract
	 */
	private void updateMasterEndDateIfNeeded(final List<I_C_Flatrate_Term> contracts, final I_C_Flatrate_Term initialContract)
	{
		final I_C_Flatrate_Conditions initialConditions = initialContract.getC_Flatrate_Conditions();
		final I_C_Flatrate_Transition initialTransition = initialConditions.getC_Flatrate_Transition();
		if (X_C_Flatrate_Transition.EXTENSIONTYPE_ExtendAll.equals(initialTransition.getExtensionType()))
		{
			final Timestamp endDate = contracts.stream()
					.sorted(Comparator.comparing(I_C_Flatrate_Term::getEndDate).reversed())
					.findFirst()
					.map(I_C_Flatrate_Term::getEndDate)
					.orElse(null);

			contracts.forEach(contract -> {
				contract.setMasterEndDate(endDate);
				InterfaceWrapperHelper.save(contract);
			});
		}
	}

	private void extendContract0(final @NonNull ContractExtendingRequest request, final String trxName)
	{
		final I_C_Flatrate_Term currentTerm = request.getContract();
		final boolean forceExtend = request.isForceExtend();
		final Boolean forceComplete = request.getForceComplete();

		final I_C_Flatrate_Conditions conditions = currentTerm.getC_Flatrate_Conditions();
		final I_C_Flatrate_Transition currentTransition = conditions.getC_Flatrate_Transition();

		I_C_Flatrate_Term termToReferenceInNote = null;
		String msgValue = null;

		if (currentTerm.isAutoRenew() || forceExtend)
		{
			final I_C_Flatrate_Term nextTerm = createNewTerm(request, trxName);

			// the conditions were set via de.metas.flatrate.modelvalidator.C_Flatrate_Term.copyFromConditions(term)
			Check.errorUnless(currentTerm.getType_Conditions().equals(nextTerm.getType_Conditions()),
					"currentTerm has Type_Conditions={} while nextTerm has Type_Conditions={}; currentTerm={}",
					currentTerm.getType_Conditions(), nextTerm.getType_Conditions(), currentTerm);

			currentTerm.setC_FlatrateTerm_Next_ID(nextTerm.getC_Flatrate_Term_ID());

			// gh #549: notify that handler so it might do additional things. In the case of this task, it shall create C_Flatrate_DataEntry records
			final IFlatrateTermEventService flatrateHandlersService = Services.get(IFlatrateTermEventService.class);
			flatrateHandlersService
					.getHandler(nextTerm.getType_Conditions())
					.afterSaveOfNextTermForPredecessor(nextTerm, currentTerm);

			// gh #549: if forceComplete was set, then use it; otherwise fall back to the setting of currentTransition
			final boolean completeNextTerm = forceComplete != null ? forceComplete : currentTransition.isAutoCompleteNewTerm();

			if (completeNextTerm)
			{
				completeIfValid(nextTerm);

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
			final IFlatrateTermEventService flatrateHandlersService = Services.get(IFlatrateTermEventService.class);
			flatrateHandlersService
					.getHandler(currentTerm.getType_Conditions())
					.afterFlatrateTermEnded(currentTerm);

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

			final Properties ctx = InterfaceWrapperHelper.getCtx(currentTerm);
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

	private I_C_Flatrate_Term createNewTerm(final @NonNull ContractExtendingRequest context, final String trxName)
	{
		final I_C_Flatrate_Term currentTerm = context.getContract();
		final Timestamp nextTermStartDate = context.getNextTermStartDate();
		final I_C_OrderLine ol = context.getOrderLine();

		Check.errorIf(currentTerm.getC_FlatrateTerm_Next_ID() > 0,
				"{} has C_FlatrateTerm_Next_ID = {} (should be <= 0)", currentTerm, currentTerm.getC_FlatrateTerm_Next_ID());

		final I_C_Flatrate_Conditions conditions = currentTerm.getC_Flatrate_Conditions();
		final I_C_Flatrate_Transition currentTransition = conditions.getC_Flatrate_Transition();

		final I_C_Flatrate_Conditions nextConditions;
		if (currentTransition.getC_Flatrate_Conditions_Next_ID() > 0)
		{
			nextConditions = currentTransition.getC_Flatrate_Conditions_Next();
		}
		else
		{
			nextConditions = currentTerm.getC_Flatrate_Conditions();
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(currentTerm);
		final I_C_Flatrate_Term nextTerm = InterfaceWrapperHelper.create(ctx, I_C_Flatrate_Term.class, trxName);
		nextTerm.setAD_Org_ID(currentTerm.getAD_Org_ID());

		nextTerm.setC_Flatrate_Data_ID(currentTerm.getC_Flatrate_Data_ID());
		nextTerm.setC_Flatrate_Conditions_ID(nextConditions.getC_Flatrate_Conditions_ID());
		nextTerm.setPlannedQtyPerUnit(currentTerm.getPlannedQtyPerUnit());
		nextTerm.setIsSimulation(currentTerm.isSimulation());

		nextTerm.setBill_BPartner_ID(currentTerm.getBill_BPartner_ID());
		nextTerm.setBill_Location_ID(currentTerm.getBill_Location_ID());
		nextTerm.setBill_User_ID(currentTerm.getBill_User_ID());

		nextTerm.setAD_User_InCharge_ID(currentTerm.getAD_User_InCharge_ID());
		final I_C_Flatrate_Transition nextTransition = nextConditions.getC_Flatrate_Transition();

		final Timestamp firstDayOfNewTerm = computeStartDate(currentTerm, nextTermStartDate);
		nextTerm.setStartDate(firstDayOfNewTerm);
		nextTerm.setMasterStartDate(currentTerm.getMasterStartDate());

		if (ol != null)
		{
			nextTerm.setC_OrderLine_Term(ol);
		}
		updateEndDate(nextTransition, nextTerm);
		updateNoticeDate(nextTransition, nextTerm);

		nextTerm.setM_PricingSystem(currentTerm.getM_PricingSystem());
		nextTerm.setDropShip_BPartner_ID(currentTerm.getDropShip_BPartner_ID());
		nextTerm.setDropShip_Location_ID(currentTerm.getDropShip_Location_ID());

		nextTerm.setM_Product_ID(currentTerm.getM_Product_ID());
		Services.get(IAttributeSetInstanceBL.class).cloneASI(currentTerm, nextTerm);

		nextTerm.setDropShip_User_ID(currentTerm.getDropShip_User_ID());
		nextTerm.setDeliveryRule(currentTerm.getDeliveryRule());
		nextTerm.setDeliveryViaRule(currentTerm.getDeliveryViaRule());

		nextTerm.setC_UOM_ID(currentTerm.getC_UOM_ID());

		final IFlatrateTermEventService flatrateHandlersService = Services.get(IFlatrateTermEventService.class);
		flatrateHandlersService
				.getHandler(nextConditions.getType_Conditions()) // nextterm is not saved yet, so type will be null in this moment
				.beforeSaveOfNextTermForPredecessor(nextTerm, currentTerm);

		nextTerm.setDocAction(X_C_Flatrate_Term.DOCACTION_Prepare);
		nextTerm.setDocStatus(X_C_Flatrate_Term.DOCSTATUS_Drafted);
		InterfaceWrapperHelper.save(nextTerm);

		return nextTerm;
	}

	private Timestamp computeStartDate(@NonNull final I_C_Flatrate_Term contract, final Timestamp nextTermStartDate)
	{

		// gh #549: if an explicit start date was given, then use it (if it is OK).
		final Timestamp dayAfterEndDate = TimeUtil.addDays(contract.getEndDate(), 1);
		if (nextTermStartDate != null)
		{
			if (nextTermStartDate.before(contract.getStartDate()) || nextTermStartDate.after(dayAfterEndDate))
			{
				Loggables.get().addLog(
						"Ignore nextTermStartDate={} because if is not between currentTerm's StartDate={} and DayAfterEndDate={}. Instead, use dayAfterEndDate",
						nextTermStartDate, contract.getStartDate(), dayAfterEndDate);
				return dayAfterEndDate;
			}

			return nextTermStartDate;
		}

		return dayAfterEndDate;
	}

	@Override
	public void updateNoticeDateAndEndDate(final I_C_Flatrate_Term term)
	{
		Check.assume(!X_C_Flatrate_Term.DOCSTATUS_Completed.equals(term.getDocStatus()), term + " is not yet completed");

		final I_C_Flatrate_Transition transition = getTransitionForTerm(term);

		final boolean isAutorenew = X_C_Flatrate_Transition.EXTENSIONTYPE_ExtendOne.equals(transition.getExtensionType())
				|| X_C_Flatrate_Transition.EXTENSIONTYPE_ExtendAll.equals(transition.getExtensionType());
		term.setIsAutoRenew(isAutorenew);
		updateEndDate(transition, term);
		updateNoticeDate(transition, term);
	}

	private I_C_Flatrate_Transition getTransitionForTerm(I_C_Flatrate_Term term)
	{
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

		return transition;

	}

	private void updateEndDate(final I_C_Flatrate_Transition transition, final I_C_Flatrate_Term term)
	{
		final Timestamp endDate = computeEndDate(transition, term);
		term.setEndDate(endDate);
	}

	private Timestamp computeEndDate(final I_C_Flatrate_Transition transition, final I_C_Flatrate_Term term)
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
			return null;
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

		return lastDayOfNewTerm;
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
		final String subType;
		if (X_C_Flatrate_Term.TYPE_CONDITIONS_Subscription.equals(term.getType_Conditions()))
		{
			subType = de.metas.contracts.flatrate.interfaces.I_C_DocType.DocSubType_Abonnement;
		}
		else if (X_C_Flatrate_Term.TYPE_CONDITIONS_HoldingFee.equals(term.getType_Conditions()))
		{
			subType = de.metas.contracts.flatrate.interfaces.I_C_DocType.DocSubType_Depotgebuehr;
		}
		else if (X_C_Flatrate_Term.TYPE_CONDITIONS_FlatFee.equals(term.getType_Conditions()))
		{
			subType = de.metas.contracts.flatrate.interfaces.I_C_DocType.DocSubType_Pauschalengebuehr;
		}
		else
		{
			Check.assume(false, term + " has unexpected Type_Conditions=" + term.getType_Conditions());
			subType = null;
		}

		final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
		return docTypeDAO.getDocType(de.metas.contracts.flatrate.interfaces.I_C_DocType.DocBaseType_CustomerContract, subType, term.getAD_Client_ID(), term.getAD_Org_ID());
	}

	@Override
	public int getWarehouseId(final I_C_Flatrate_Term term)
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
						new Object[] { msgBL.translate(ctx, I_AD_Org.COLUMNNAME_AD_Org_ID), InterfaceWrapperHelper.loadOutOfTrx(term.getAD_Org_ID(), I_AD_Org.class) });
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

		final I_C_Flatrate_DataEntry entry = flatrateDAO.retrieveRefundableDataEntry(partner.getC_BPartner_ID(), movementDate, product);

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
			if (!flatrateDAO.retrieveTerms(bPartner, conditions).isEmpty())
			{
				notCreatedReason.append(" already has a term;");
				dontCreateTerm = true;
			}
		}
		else
		{
			if (!flatrateDAO.retrieveTerms(ctx,
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

		final I_C_Flatrate_Data data = flatrateDAO.retriveOrCreateFlatrateData(bPartner);
		newTerm.setC_Flatrate_Data(data);

		newTerm.setDocAction(X_C_Flatrate_Term.DOCACTION_Prepare);
		newTerm.setDocStatus(X_C_Flatrate_Term.DOCSTATUS_Drafted);

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
		Services.get(IDocumentBL.class).processEx(term, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
	}

	@Override
	public void completeIfValid(final I_C_Flatrate_Term term)
	{
		final boolean hasOverlappingTerms = hasOverlappingTerms(term);
		if (hasOverlappingTerms)
		{

			Loggables.get().addLog(Services.get(IMsgBL.class).getMsg(
					Env.getCtx(),
					MSG_HasOverlapping_Term,
					new Object[] { term.getC_Flatrate_Term_ID(), term.getBill_BPartner().getValue() }));
			return;
		}

		complete(term);
	}

	@Override
	public void voidIt(final I_C_Flatrate_Term term)
	{
		// NOTE: the whole reason why we have this method is for readability ease of refactoring.
		Services.get(IDocumentBL.class).processEx(term, IDocument.ACTION_Void, IDocument.STATUS_Voided);

	}

	@Override
	public boolean hasOverlappingTerms(final I_C_Flatrate_Term newTerm)
	{
		// overlapping terms must be for the same flatrate data
		final I_C_Flatrate_Data flatrateData = newTerm.getC_Flatrate_Data();

		final List<I_C_Flatrate_Term> terms = flatrateDAO.retrieveTerms(flatrateData);

		// Note: This list also contains the given term. It will have to be excluded from the list when searching for overlapping terms.
		terms.remove(newTerm);

		// there are no other terms for the given flatrate data
		if (terms.isEmpty())
		{
			return false;
		}

		for (final I_C_Flatrate_Term term : terms)
		{

			// Only consider completed terms
			if (!X_C_Flatrate_Term.DOCSTATUS_Completed.equals(term.getDocStatus()))
			{
				continue;
			}

			if (periodsOverlap(newTerm, term) && productsOverlap(newTerm, term))
			{
				return true;
			}

		}

		return false;

	}

	/**
	 * Check if 2 flatrate terms overlap in product or product category
	 *
	 * @param newTerm
	 * @param term
	 * @return
	 */
	private boolean productsOverlap(final I_C_Flatrate_Term newTerm, final I_C_Flatrate_Term term)
	{
		// services
		final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);

		final org.compiere.model.I_M_Product newProduct = newTerm.getM_Product();
		final org.compiere.model.I_M_Product product = term.getM_Product();

		if (newProduct != null)
		{

			final List<I_C_Flatrate_Matching> flatrateMatchings = flatrateDAO.retrieveFlatrateMatchings(term.getC_Flatrate_Conditions());

			for (final I_C_Flatrate_Matching matching : flatrateMatchings)
			{
				if (newProduct.getM_Product_ID() == matching.getM_Product_ID())
				{
					// there is one matching for the same product as the one from the new term
					return true;
				}

				if (matching.getM_Product() == null && (newProduct.getM_Product_Category_ID() == matching.getM_Product_Category_Matching_ID()))
				{
					// there is one matching with the same category as the given product
					return true;
				}
			}
		}

		// there is no product in the first term but there is a product set in the second term
		else if (product != null)
		{
			final List<I_C_Flatrate_Matching> flatrateMatchings = flatrateDAO.retrieveFlatrateMatchings(newTerm.getC_Flatrate_Conditions());

			for (final I_C_Flatrate_Matching matching : flatrateMatchings)
			{
				if (product.getM_Product_ID() == matching.getM_Product_ID())
				{
					// there is one matching for the same product as the one from the second term
					return true;
				}

				if (matching.getM_Product() == null && (product.getM_Product_Category_ID() == matching.getM_Product_Category_Matching_ID()))
				{

					// there is one matching with the same category as the given product
					return true;
				}
			}
		}

		// both terms have no product
		else
		{
			final List<I_C_Flatrate_Matching> newFlatrateMatchings = flatrateDAO.retrieveFlatrateMatchings(newTerm.getC_Flatrate_Conditions());
			final List<I_C_Flatrate_Matching> flatrateMatchings = flatrateDAO.retrieveFlatrateMatchings(term.getC_Flatrate_Conditions());

			for (final I_C_Flatrate_Matching newFlatrateMatching : newFlatrateMatchings)
			{
				final org.compiere.model.I_M_Product newFMProduct = newFlatrateMatching.getM_Product();

				if (newFMProduct != null)
				{
					for (final I_C_Flatrate_Matching flatrateMatching : flatrateMatchings)
					{
						if (newFMProduct.getM_Product_ID() == flatrateMatching.getM_Product_ID())
						{
							// matchings with the same product
							return true;
						}

						if (flatrateMatching.getM_Product() == null && (newFMProduct.getM_Product_Category_ID() == flatrateMatching.getM_Product_Category_Matching_ID()))
						{
							// there is a matching for the category if the given products

							return true;
						}
					}
				}
				// product is null. Check the product category
				else
				{
					final org.compiere.model.I_M_Product_Category newFMProductCategory = newFlatrateMatching.getM_Product_Category_Matching();

					for (final I_C_Flatrate_Matching flatrateMatching : flatrateMatchings)
					{
						final org.compiere.model.I_M_Product matchingProduct = flatrateMatching.getM_Product();

						if (flatrateMatching.getM_Product() != null && (matchingProduct.getM_Product_Category_ID() == newFMProductCategory.getM_Product_Category_ID()))
						{
							// the term is for a product that matches the given product category
							return true;
						}

						if (flatrateMatching.getM_Product() != null)
						{
							// the matching is only for the product set. Doesn't fit the new matching product.
							continue;
						}

						if (flatrateMatching.getM_Product_Category_Matching_ID() == newFMProductCategory.getM_Product_Category_ID())
						{
							// the terms have the same product category and no product. => overlap
							return true;
						}
					}
				}
			}
		}

		return false;

	}

	/**
	 * Check if the startDate and endDate of 2 terms overlap.
	 *
	 * @param term1
	 * @param term2
	 * @return
	 */
	private boolean periodsOverlap(final I_C_Flatrate_Term term1, I_C_Flatrate_Term term2)
	{
		final Timestamp startDate1 = term1.getStartDate();
		final Timestamp startDate2 = term2.getStartDate();

		final Timestamp endDate1 = term1.getEndDate();
		final Timestamp endDate2 = term2.getEndDate();

		final boolean overlaps = TimeUtil.inRange(startDate1, endDate1, startDate2, endDate2);

		return overlaps;
	}

	@Override
	public I_C_Flatrate_Term getInitialFlatrateTerm(@NonNull final I_C_Flatrate_Term term)
	{
		I_C_Flatrate_Term ancestor = flatrateDAO.retrieveAncestorFlatrateTerm(term);

		if (ancestor != null)
		{
			I_C_Flatrate_Term nextAncestor = getInitialFlatrateTerm(ancestor);
			if (nextAncestor != null)
			{
				ancestor = nextAncestor;
			}
		}

		return ancestor;
	}
}
