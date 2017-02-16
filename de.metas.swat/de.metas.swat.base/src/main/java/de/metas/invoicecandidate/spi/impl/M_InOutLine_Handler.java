package de.metas.invoicecandidate.spi.impl;

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

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.cache.impl.TableRecordCacheLocal;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.bpartner.service.IBPartnerBL;
import org.adempiere.bpartner.service.IBPartnerDAO;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.IPricingContext;
import org.adempiere.pricing.api.IPricingResult;
import org.adempiere.pricing.exceptions.ProductNotOnPriceListException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Note;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_Activity;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_InvoiceCandidate_InOutLine;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Product;
import org.compiere.process.DocAction;

import de.metas.document.engine.IDocActionBL;
import de.metas.inout.IInOutBL;
import de.metas.inout.model.I_M_InOut;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_M_InOutLine;
import de.metas.invoicecandidate.model.X_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.AbstractInvoiceCandidateHandler;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateRequest;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateResult;
import de.metas.product.acct.api.IProductAcctDAO;
import de.metas.tax.api.ITaxBL;

/**
 * Creates {@link I_C_Invoice_Candidate}s from {@link I_M_InOutLine}s which do not reference an order line.
 *
 * @author tsa
 *
 */
public class M_InOutLine_Handler extends AbstractInvoiceCandidateHandler
{
	//
	// Services
	private static final transient M_InOutLine_HandlerDAO dao = new M_InOutLine_HandlerDAO();
	private final transient IInOutBL inOutBL = Services.get(IInOutBL.class);

	@Override
	public boolean isCreateMissingCandidatesAutomatically()
	{
		return false;
	}

	@Override
	public boolean isCreateMissingCandidatesAutomatically(Object model)
	{
		return false;
	}

	/**
	 * @see M_InOut_Handler#expandRequest(InvoiceCandidateGenerateRequest)
	 */
	@Override
	public Object getModelForInvoiceCandidateGenerateScheduling(final Object model)
	{
		//
		// Retrieve inout
		final I_M_InOutLine inoutLine = InterfaceWrapperHelper.create(model, I_M_InOutLine.class);
		final org.compiere.model.I_M_InOut inout = inoutLine.getM_InOut();
		return inout;
	}

	@Override
	public Iterator<I_M_InOutLine> retrieveAllModelsWithMissingCandidates(final Properties ctx, final int limit, final String trxName)
	{
		return dao.retrieveAllLinesWithoutOrderLine(ctx, limit, trxName);
	}

	@Override
	public InvoiceCandidateGenerateResult createCandidatesFor(final InvoiceCandidateGenerateRequest request)
	{
		final I_M_InOutLine inOutLine = request.getModel(I_M_InOutLine.class);

		final I_C_Invoice_Candidate invoiceCandidate = createCandidateForInOutLine(inOutLine);
		return InvoiceCandidateGenerateResult.of(this, invoiceCandidate);
	}

	/**
	 *
	 * @param inOutLine
	 * @return created invoice candidate or <code>null</code> if there was no need to create an invoice candidate
	 */
	private I_C_Invoice_Candidate createCandidateForInOutLine(final I_M_InOutLine inOutLine)
	{
		// Don't create any invoice candidate if already created
		if (inOutLine.isInvoiceCandidate())
		{
			return null;
		}

		final I_M_InOut inOut = InterfaceWrapperHelper.create(inOutLine.getM_InOut(), I_M_InOut.class);

		final I_C_Invoice_Candidate ic = InterfaceWrapperHelper.newInstance(I_C_Invoice_Candidate.class, inOutLine);

		final int adOrgId = inOutLine.getAD_Org_ID();
		ic.setAD_Org_ID(adOrgId);

		ic.setC_ILCandHandler(getHandlerRecord());

		//
		// Handle Transaction Type: Shipment / Receipt
		final boolean isSOTrx = inOut.isSOTrx();
		ic.setIsSOTrx(isSOTrx); // 05265

		//
		// Handler Customer/Verdor Returns
		BigDecimal qtyMultiplier = BigDecimal.ONE;
		if (inOutBL.isReturnMovementType(inOut.getMovementType()))
		{
			qtyMultiplier = qtyMultiplier.negate();
		}

		//
		// Document reference
		{
			setM_InOutLine(ic, inOutLine);

			// ic.setC_Order(inoutOrder); // is set further down

			// if this inOutLine had an order line, this handler would not be in charge to start with
			// ic.setC_OrderLine_ID(inOutLine.getC_OrderLine_ID());
		}

		//
		// Set the bill related details
		{
			setBPartnerData(ic, inOutLine);
		}

		//
		// Product & Charge
		final int productId = inOutLine.getM_Product_ID();
		final int chargeId = inOutLine.getC_Charge_ID();
		{
			ic.setM_Product_ID(productId);
			ic.setIsPackagingMaterial(inOutLine.isPackagingMaterial());
			ic.setC_Charge_ID(chargeId);

			setC_UOM_ID(ic);
			ic.setQtyToInvoice(BigDecimal.ZERO); // to be computed
		}

		//
		// order & delivery stuff
		{
			setOrderedData(ic);
			setDeliveredData(ic);
		}

		//
		// Pricing Informations
		final IPricingResult pricingResult = setPricingInfo(ic, inOutLine);

		//
		// Description
		ic.setDescription(inOut.getDescription());

		//
		// Set invoice rule form linked order (if exists)
		if (inOut.getC_Order_ID() > 0)
		{
			ic.setInvoiceRule(inOut.getC_Order().getInvoiceRule()); // the rule set in order
		}
		// Set Invoice Rule from BPartner
		else
		{
			final String invoiceRule = ic.getBill_BPartner().getInvoiceRule();
			if (!Check.isEmpty(invoiceRule))
			{
				ic.setInvoiceRule(invoiceRule);
			}
			else
			{
				ic.setInvoiceRule(X_C_Invoice_Candidate.INVOICERULE_Sofort); // Immediate
			}
		}

		//
		// Set C_Activity from Product (07442)
		final IContextAware contextProvider = InterfaceWrapperHelper.getContextAware(inOutLine);
		final Properties ctx = InterfaceWrapperHelper.getCtx(inOutLine);
		final String trxName = InterfaceWrapperHelper.getTrxName(inOutLine);
		final I_AD_Org org = InterfaceWrapperHelper.create(ctx, adOrgId, I_AD_Org.class, trxName);
		final I_M_Product product = InterfaceWrapperHelper.create(ctx, productId, I_M_Product.class, trxName);
		final I_C_Activity activity = Services.get(IProductAcctDAO.class).retrieveActivityForAcct(contextProvider, org, product);
		ic.setC_Activity(activity);

		//
		// Set C_Tax from Product (07442)
		final int taxCategoryId = pricingResult != null ? pricingResult.getC_TaxCategory_ID() : -1;
		final Timestamp shipDate = inOut.getMovementDate();
		final Timestamp billDate = inOut.getDateAcct();
		final int locationId = inOut.getC_BPartner_Location_ID();
		final int taxId = Services.get(ITaxBL.class).getTax(ctx, ic, taxCategoryId, productId, chargeId, billDate, shipDate, adOrgId, inOut.getM_Warehouse(), locationId // billC_BPartner_Location_ID
				, locationId // shipC_BPartner_Location_ID
				, isSOTrx, trxName);
		ic.setC_Tax_ID(taxId);

		//
		// Save the Invoice Candidate, so that we can use it's ID further down
		InterfaceWrapperHelper.save(ic);

		// set Quality Issue Percentage Override

		final I_M_AttributeSetInstance asi = inOutLine.getM_AttributeSetInstance();
		final List<I_M_AttributeInstance> instances = Services.get(IAttributeDAO.class).retrieveAttributeInstances(asi);

		Services.get(IInvoiceCandBL.class).setQualityDiscountPercent_Override(ic, instances);

		//
		// Update InOut Line and flag it as Invoice Candidate generated
		inOutLine.setIsInvoiceCandidate(true);
		InterfaceWrapperHelper.save(inOutLine);

		//
		// Create IC-IOL association (07969)
		// Even if our IC is directly linked to M_InOutLine (by AD_Table_ID/Record_ID),
		// we need this association in order to let our engine know this and create the M_MatchInv records.
		{
			final I_C_InvoiceCandidate_InOutLine iciol = InterfaceWrapperHelper.newInstance(I_C_InvoiceCandidate_InOutLine.class, ic);
			iciol.setC_Invoice_Candidate(ic);
			iciol.setM_InOutLine(inOutLine);
			// iciol.setQtyInvoiced(QtyInvoiced); // will be set during invoicing to keep track of which movementQty is already invoiced in case of partial invoicing
			InterfaceWrapperHelper.save(iciol);
		}

		//
		return ic;
	}

	@Override
	public void invalidateCandidatesFor(final Object model)
	{
		final I_M_InOutLine inoutLine = InterfaceWrapperHelper.create(model, I_M_InOutLine.class);
		invalidateCandidateForInOutLine(inoutLine);
	}

	private void invalidateCandidateForInOutLine(final I_M_InOutLine inoutLine)
	{
		final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);

		final IQueryBuilder<I_C_Invoice_Candidate> icQueryBuilder = invoiceCandDAO.retrieveInvoiceCandidatesForInOutLineQuery(inoutLine);

		invoiceCandDAO.invalidateCandsFor(icQueryBuilder);
	}

	@Override
	public String getSourceTable()
	{
		return org.compiere.model.I_M_InOutLine.Table_Name;
	}

	@Override
	public boolean isUserInChargeUserEditable()
	{
		return false;
	}

	/**
	 * Qty Sign Multiplier
	 *
	 * @param ic
	 * @return
	 * 		<ul>
	 *         <li>+1 on regular shipment/receipt
	 *         <li>-1 on material returns
	 *         </ul>
	 */
	private BigDecimal getQtyMultiplier(final I_C_Invoice_Candidate ic)
	{
		final I_M_InOutLine inoutLine = getM_InOutLine(ic);
		final org.compiere.model.I_M_InOut inout = inoutLine.getM_InOut();
		final String movementType = inout.getMovementType();

		BigDecimal multiplier = BigDecimal.ONE;
		if (inOutBL.isReturnMovementType(movementType))
		{
			multiplier = multiplier.negate();
		}

		return multiplier;
	}

	/**
	 * <ul>
	 * <li>QtyOrdered := M_InOutLine.QtyDelivered
	 * <li>DateOrdered := if the inOutLine's M_InOut references an order, then that order's DateOrdered. Otherwise the MovementDate of inOutLine's M_InOut.
	 * <li>C_Order_ID: the C_Order_ID (if any) of the inOutLine's M_InOut
	 * </ul>
	 *
	 * @see IInvoiceCandidateHandler#setOrderedData(I_C_Invoice_Candidate)
	 */
	@Override
	public void setOrderedData(final I_C_Invoice_Candidate ic)
	{
		final I_M_InOutLine inOutLine = getM_InOutLine(ic);
		final org.compiere.model.I_M_InOut inOut = inOutLine.getM_InOut();

		final I_C_Order order = inOut.getC_Order();

		if (inOut.getC_Order_ID() > 0)
		{
			ic.setC_Order(order);  // also set the order; even if the iol does not directly refer to an order line, it is there because of that order
			ic.setDateOrdered(order.getDateOrdered());
		}
		else
		{
			ic.setC_Order(null);
			ic.setDateOrdered(inOut.getMovementDate());
		}

		final IDocActionBL docActionBL = Services.get(IDocActionBL.class);

		if (docActionBL.isStatusOneOf(inOut, DocAction.STATUS_Completed, DocAction.STATUS_Closed))
		{
			final BigDecimal qtyMultiplier = getQtyMultiplier(ic);
			final BigDecimal qtyDelivered = inOutLine.getMovementQty().multiply(qtyMultiplier);
			ic.setQtyOrdered(qtyDelivered);
		}
		else
		{
			// Corrected, voiced etc document. Set qty to zero.
			ic.setQtyOrdered(BigDecimal.ZERO);
		}
	}

	/**
	 * <ul>
	 * <li>QtyDelivered := QtyOrdered
	 * <li>DeliveryDate := MovementDate of the inOutLine's M_InOut
	 * <li>M_InOut_ID: the ID of the inOutLine's M_InOut
	 * </ul>
	 *
	 * @see IInvoiceCandidateHandler#setDeliveredData(I_C_Invoice_Candidate)
	 */
	@Override
	public void setDeliveredData(final I_C_Invoice_Candidate ic)
	{
		//
		// Get delivered quantity and then set it to IC
		// NOTE: please check setOrderedData() method which is setting QtyOrdered as inout lines' movement quantity,
		// so that's why, here, we consider the QtyDelivered as QtyOrdered.
		final BigDecimal qtyDelivered = ic.getQtyOrdered();
		ic.setQtyDelivered(qtyDelivered);

		//
		// Set other delivery informations by fetching them from first shipment/receipt.
		final I_M_InOutLine inOutLine = getM_InOutLine(ic);
		final org.compiere.model.I_M_InOut inOut = inOutLine.getM_InOut();
		setDeliveredDataFromFirstInOut(ic, inOut);
	}

	public static I_M_InOutLine getM_InOutLine(final I_C_Invoice_Candidate ic)
	{
		final I_M_InOutLine inoutLine = getM_InOutLineOrNull(ic);
		Check.assumeNotNull(inoutLine, "Error: no inout line found for candidate {}", ic);
		return inoutLine;

	}

	public static I_M_InOutLine getM_InOutLineOrNull(final I_C_Invoice_Candidate ic)
	{
		return TableRecordCacheLocal.getReferencedValue(ic, I_M_InOutLine.class);
	}

	private static void setM_InOutLine(final I_C_Invoice_Candidate ic, final I_M_InOutLine inoutLine)
	{
		Check.assumeNotNull(ic, "ic not null");
		Check.assumeNotNull(inoutLine, "inoutLine not null");
		TableRecordCacheLocal.setReferencedValue(ic, inoutLine);
	}

	@Override
	public void setBPartnerData(final I_C_Invoice_Candidate ic)
	{
		final I_M_InOutLine inOutLine = getM_InOutLine(ic);
		setBPartnerData(ic, inOutLine);
	}

	private void setBPartnerData(final I_C_Invoice_Candidate ic, final I_M_InOutLine fromInOutLine)
	{
		Check.assumeNotNull(fromInOutLine, "fromInOutLine not null");

		final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);
		final IBPartnerBL bPartnerBL = Services.get(IBPartnerBL.class);

		final I_M_InOut inOut = InterfaceWrapperHelper.create(fromInOutLine.getM_InOut(), I_M_InOut.class);

		final I_C_BPartner billBPartner;
		final I_C_BPartner_Location billBPLocation;
		final I_AD_User billBPContact;
		// The bill related info cannot be changed in the schedule
		// Therefore, it's safe to set them in the invoice candidate directly from the order (if we have it)
		final I_C_Order inoutOrder = inOut.getC_Order();
		if (inoutOrder != null && inoutOrder.getC_Order_ID() > 0)
		{
			billBPartner = inoutOrder.getBill_BPartner();
			billBPLocation = inoutOrder.getBill_Location();
			billBPContact = inoutOrder.getBill_User();
		}
		// Otherwise, take it from the inout, but don't use the inout's location and user. They might not be "billto" after all.
		else
		{
			final boolean alsoTryBilltoRelation = true;
			final Properties ctx = InterfaceWrapperHelper.getCtx(ic);

			billBPLocation = bPartnerDAO.retrieveBillToLocation(ctx, inOut.getC_BPartner_ID(), alsoTryBilltoRelation, ITrx.TRXNAME_None);
			billBPartner = billBPLocation.getC_BPartner(); // task 08585: might be different from inOut.getC_BPartner(), because it might be the billTo-relation's BPartner.
			billBPContact = bPartnerBL.retrieveBillContact(ctx, billBPartner.getC_BPartner_ID(), ITrx.TRXNAME_None);
		}

		Check.assumeNotNull(billBPartner, "billBPartner not null");
		Check.assumeNotNull(billBPLocation, "billBPLocation not null");
		// Bill_User_ID isn't mandatory in C_Order, and isn't considered a must in OLHandler either
		// Check.assumeNotNull(billBPContact, "billBPContact not null");

		//
		// Set BPartner / Location / Contact
		ic.setBill_BPartner(billBPartner);
		ic.setBill_Location(billBPLocation);
		ic.setBill_User(billBPContact);
	}

	@Override
	public void setC_UOM_ID(final I_C_Invoice_Candidate ic)
	{
		final I_M_InOutLine inOutLine = getM_InOutLine(ic);
		ic.setC_UOM_ID(inOutLine.getC_UOM_ID());
	}

	@Override
	public void setPriceEntered(final I_C_Invoice_Candidate ic)
	{
		// nothing to do
	}

	@Override
	public void setPriceActual(final I_C_Invoice_Candidate ic)
	{
		final I_M_InOutLine inOutLine = getM_InOutLine(ic);
		setPricingInfo(ic, inOutLine);
	}

	/**
	 * Set pricing info on invoice candidate
	 *
	 * @param ic
	 * @param fromInOutLine
	 *
	 * @return pricing result (computed) or null if exception occurred
	 */
	private IPricingResult setPricingInfo(final I_C_Invoice_Candidate ic, final I_M_InOutLine fromInOutLine)
	{
		IPricingResult pricingResult = null;
		try
		{
			final IPricingContext pricingCtx = inOutBL.createPricingCtx(fromInOutLine);
			pricingResult = inOutBL.getProductPrice(pricingCtx);

			ic.setM_PricingSystem_ID(pricingResult.getM_PricingSystem_ID());

			// #367: there is a corner case where we need to know the PLV is order to later know the correct M_PriceList_ID.
			// also see the javadoc of inOutBL.createPricingCtx(fromInOutLine)
			ic.setM_PriceList_Version_ID(pricingResult.getM_PriceList_Version_ID());

			ic.setPriceEntered(pricingResult.getPriceStd());
			ic.setPriceActual(pricingResult.getPriceStd());
			ic.setPrice_UOM_ID(pricingResult.getPrice_UOM_ID()); // 07090 when we set PriceActual, we shall also set PriceUOM.
			ic.setDiscount(pricingResult.getDiscount());
			ic.setC_Currency_ID(pricingResult.getC_Currency_ID());

			if (ic.getC_Order_ID() > 0)
			{
				// task 08451: if the ic has an order, we use the order's IsTaxIncuded value, to make sure that we will be able to invoice them together
				ic.setIsTaxIncluded(ic.getC_Order().isTaxIncluded());
			}
			else
			{
				ic.setIsTaxIncluded(pricingResult.isTaxIncluded());
			}
		}
		catch (final ProductNotOnPriceListException e)
		{
			final boolean askForDeleteRegeneration = true; // ask for re-generation
			setError(ic, e, askForDeleteRegeneration);
		}
		catch (final Exception e)
		{
			final boolean askForDeleteRegeneration = false; // default; don't ask for re-generation
			setError(ic, e, askForDeleteRegeneration);
		}
		return pricingResult;
	}

	private final void setError(final I_C_Invoice_Candidate ic, final Exception ex, final boolean askForDeleteRegeneration)
	{
		ic.setIsInDispute(true); // 07193 - Mark's request

		final I_AD_Note note = null; // we don't have a note
		Services.get(IInvoiceCandBL.class).setError(ic, ex.getLocalizedMessage(), note, askForDeleteRegeneration);
	}
}
