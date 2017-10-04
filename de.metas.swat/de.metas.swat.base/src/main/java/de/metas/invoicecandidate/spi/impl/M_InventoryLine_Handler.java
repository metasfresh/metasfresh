package de.metas.invoicecandidate.spi.impl;

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
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Inventory;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;

import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.inout.IInOutBL;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_M_InventoryLine;
import de.metas.invoicecandidate.model.X_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.AbstractInvoiceCandidateHandler;
import de.metas.invoicecandidate.spi.IInventoryLine_HandlerDAO;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateRequest;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateResult;
import de.metas.product.acct.api.IProductAcctDAO;
import de.metas.tax.api.ITaxBL;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class M_InventoryLine_Handler extends AbstractInvoiceCandidateHandler
{
	//
	// Services
	private static final transient IInventoryLine_HandlerDAO inventoryLineHandlerDAO = Services.get(IInventoryLine_HandlerDAO.class);
	private final transient IInOutBL inOutBL = Services.get(IInOutBL.class);

	@Override
	public boolean isCreateMissingCandidatesAutomatically()
	{
		return false;
	}

	@Override
	public boolean isCreateMissingCandidatesAutomatically(final Object model)
	{
		return false;
	}

	@Override
	public Object getModelForInvoiceCandidateGenerateScheduling(final Object model)
	{
		//
		// Retrieve inventory
		final I_M_InventoryLine inventoryLine = InterfaceWrapperHelper.create(model, I_M_InventoryLine.class);
		final I_M_Inventory inventory = inventoryLine.getM_Inventory();
		return inventory;
	}

	@Override
	public Iterator<? extends Object> retrieveAllModelsWithMissingCandidates(final int limit)
	{
		return inventoryLineHandlerDAO.retrieveAllLinesWithoutIC(Env.getCtx(), limit, ITrx.TRXNAME_ThreadInherited);
	}

	@Override
	public InvoiceCandidateGenerateResult createCandidatesFor(final InvoiceCandidateGenerateRequest request)
	{
		final I_M_InventoryLine inventoryLine = request.getModel(I_M_InventoryLine.class);

		final I_C_Invoice_Candidate invoiceCandidate = createCandidateForInventoryLine(inventoryLine);
		return InvoiceCandidateGenerateResult.of(this, invoiceCandidate);
	}

	private I_C_Invoice_Candidate createCandidateForInventoryLine(final I_M_InventoryLine inventoryLine)
	{
		// Don't create any invoice candidate if already created
		if (inventoryLine.isInvoiceCandidate())
		{
			return null;
		}

		final I_M_Inventory inventory = InterfaceWrapperHelper.create(inventoryLine.getM_Inventory(), I_M_Inventory.class);

		final I_C_Invoice_Candidate ic = InterfaceWrapperHelper.newInstance(I_C_Invoice_Candidate.class, inventoryLine);

		final int adOrgId = inventoryLine.getAD_Org_ID();
		ic.setAD_Org_ID(adOrgId);

		ic.setC_ILCandHandler(getHandlerRecord());

		//
		// Document reference
		{
			setM_InventoryLine(ic, inventoryLine);
		}

		ic.setIsSOTrx(false); // make it like in the vendor return

		TableRecordCacheLocal.setReferencedValue(ic, inventoryLine);
		//
		// Set the bill related details
		{
			setBPartnerData(ic, inventoryLine);
		}

		//
		// Product
		final int productId = inventoryLine.getM_Product_ID();

		{
			ic.setM_Product_ID(productId);
			// for the time being, the material disposals do not have packing material lines because we only throw the products, not the boxes
			ic.setIsPackagingMaterial(false);

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
		final IPricingResult pricingResult = setPricingInfo(ic, inventoryLine);

		//
		// Description
		ic.setDescription(inventory.getDescription());

		final org.compiere.model.I_M_InOutLine originInOutLine = inventoryLine.getM_InOutLine();

		Check.assumeNotNull(originInOutLine, "InventoryLine {0} must have an origin inoutline set", inventoryLine);
		final I_M_InOut inOut = originInOutLine.getM_InOut();

		ic.setInvoiceRule(X_C_Invoice_Candidate.INVOICERULE_Sofort); // Immediate (until further requirements)

		//
		// Set C_Activity from Product (07442)
		final IContextAware contextProvider = InterfaceWrapperHelper.getContextAware(inventoryLine);
		final Properties ctx = InterfaceWrapperHelper.getCtx(inventoryLine);
		final String trxName = InterfaceWrapperHelper.getTrxName(inventoryLine);
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
		final int taxId = Services.get(ITaxBL.class).getTax(
				ctx, ic, taxCategoryId, productId, -1, billDate, shipDate, adOrgId, inOut.getM_Warehouse(), locationId // billC_BPartner_Location_ID
				, locationId // shipC_BPartner_Location_ID
				, false // isSOTrx same as in vendor return
				, trxName);
		ic.setC_Tax_ID(taxId);

		//
		// Save the Invoice Candidate, so that we can use it's ID further down
		InterfaceWrapperHelper.save(ic);

		// set Quality Issue Percentage Override

		final I_M_AttributeSetInstance asi = inventoryLine.getM_AttributeSetInstance();
		final List<I_M_AttributeInstance> instances = Services.get(IAttributeDAO.class).retrieveAttributeInstances(asi);

		Services.get(IInvoiceCandBL.class).setQualityDiscountPercent_Override(ic, instances);

		//
		// Update InOut Line and flag it as Invoice Candidate generated
		inventoryLine.setIsInvoiceCandidate(true);
		InterfaceWrapperHelper.save(inventoryLine);

		//
		// Create IC-IOL association (07969)
		// Even if our IC is directly linked to M_InOutLine (by AD_Table_ID/Record_ID),
		// we need this association in order to let our engine know this and create the M_MatchInv records.
		{
			final I_C_InvoiceCandidate_InOutLine iciol = InterfaceWrapperHelper.newInstance(I_C_InvoiceCandidate_InOutLine.class, ic);
			iciol.setC_Invoice_Candidate(ic);
			iciol.setM_InOutLine(originInOutLine);
			// iciol.setQtyInvoiced(QtyInvoiced); // will be set during invoicing to keep track of which movementQty is already invoiced in case of partial invoicing
			InterfaceWrapperHelper.save(iciol);
		}

		//
		return ic;
	}

	private IPricingResult setPricingInfo(final I_C_Invoice_Candidate ic, final I_M_InventoryLine inventoryLine)
	{
		IPricingResult pricingResult = null;
		final org.compiere.model.I_M_InOutLine originInOutLine = inventoryLine.getM_InOutLine();

		Check.assumeNotNull(originInOutLine, "InventoryLine {0} must have an origin inoutline set", inventoryLine);

		try
		{
			final IPricingContext pricingCtx = inOutBL.createPricingCtx(originInOutLine);
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

	@Override
	public void setBPartnerData(final I_C_Invoice_Candidate ic)
	{
		final I_M_InventoryLine inventoryLine = getM_InventoryLine(ic);
		setBPartnerData(ic, inventoryLine);
	}

	public static I_M_InventoryLine getM_InventoryLine(final I_C_Invoice_Candidate ic)
	{
		final I_M_InventoryLine inventoryLine = getM_InventoryLineOrNull(ic);
		Check.assumeNotNull(inventoryLine, "Error: no inout line found for candidate {}", ic);
		return inventoryLine;

	}

	public static I_M_InventoryLine getM_InventoryLineOrNull(final I_C_Invoice_Candidate ic)
	{
		return TableRecordCacheLocal.getReferencedValue(ic, I_M_InventoryLine.class);
	}

	private void setBPartnerData(final I_C_Invoice_Candidate ic, final I_M_InventoryLine inventoryLine)
	{
		Check.assumeNotNull(inventoryLine, "fromInOutLine not null");

		final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);
		final IBPartnerBL bPartnerBL = Services.get(IBPartnerBL.class);

		final org.compiere.model.I_M_InOutLine inOutLine = inventoryLine.getM_InOutLine();

		Check.assumeNotNull(inOutLine, "InventoryLine {0} must have an origin inoutline set", inventoryLine);
		final I_M_InOut inOut = inOutLine.getM_InOut();

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

	private void setM_InventoryLine(final I_C_Invoice_Candidate ic, final I_M_InventoryLine inventoryLine)
	{
		Check.assumeNotNull(ic, "ic not null");
		Check.assumeNotNull(inventoryLine, "inventoryLine not null");
		TableRecordCacheLocal.setReferencedValue(ic, inventoryLine);

	}

	@Override
	public void invalidateCandidatesFor(final Object model)
	{
		final I_M_InventoryLine inventoryLine = InterfaceWrapperHelper.create(model, I_M_InventoryLine.class);
		invalidateCandidateForInventoryLine(inventoryLine);

	}

	private void invalidateCandidateForInventoryLine(final I_M_InventoryLine inventoryLine)
	{
		final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);

		final IQueryBuilder<I_C_Invoice_Candidate> icQueryBuilder = invoiceCandDAO.retrieveInvoiceCandidatesForInventoryLineQuery(inventoryLine);

		invoiceCandDAO.invalidateCandsFor(icQueryBuilder);
	}

	@Override
	public String getSourceTable()
	{
		return I_M_InventoryLine.Table_Name;
	}

	@Override
	public boolean isUserInChargeUserEditable()
	{
		return false;
	}

	@Override
	public void setOrderedData(final I_C_Invoice_Candidate ic)
	{
		final I_M_InventoryLine inventoryLine = getM_InventoryLine(ic);

		final org.compiere.model.I_M_InOutLine originInOutLine = inventoryLine.getM_InOutLine();

		Check.assumeNotNull(originInOutLine, "InventoryLine {0} must have an origin inoutline set", inventoryLine);
		final I_M_InOut inOut = originInOutLine.getM_InOut();

		final I_C_Order order = inOut.getC_Order();

		if (inOut.getC_Order_ID() > 0)
		{
			ic.setC_Order(order);  // also set the order; even if the iol does not directly refer to an order line, it is there because of that order
			ic.setDateOrdered(order.getDateOrdered());
		}
		else if (ic.getC_Order_ID() <= 0)
		{
			// don't attempt to "clear" the order data if it is already set/known.
			ic.setC_Order(null);
			ic.setDateOrdered(inOut.getMovementDate());
		}

		final IDocumentBL docActionBL = Services.get(IDocumentBL.class);

		if (docActionBL.isDocumentStatusOneOf(inventoryLine.getM_Inventory(), IDocument.STATUS_Completed, IDocument.STATUS_Closed))
		{
			final BigDecimal qtyMultiplier = new BigDecimal(-1); // TODO: check if this is ok
			final BigDecimal qtyDelivered = inventoryLine.getQtyInternalUse().multiply(qtyMultiplier);
			ic.setQtyOrdered(qtyDelivered);
		}
		else
		{
			// Corrected, voided etc document. Set qty to zero.
			ic.setQtyOrdered(BigDecimal.ZERO);
		}

	}

	@Override
	public void setDeliveredData(final I_C_Invoice_Candidate ic)
	{
		final BigDecimal qtyDelivered = ic.getQtyOrdered();
		ic.setQtyDelivered(qtyDelivered);

	}

	@Override
	public void setPriceEntered(final I_C_Invoice_Candidate ic)
	{
		// nothing to do
	}

	@Override
	public void setPriceActual(final I_C_Invoice_Candidate ic)
	{
		final I_M_InventoryLine inventoryLine = getM_InventoryLine(ic);
		setPricingInfo(ic, inventoryLine);
	}

	@Override
	public void setC_UOM_ID(final I_C_Invoice_Candidate ic)
	{
		final I_M_InventoryLine inventoryLine = getM_InventoryLine(ic);
		ic.setC_UOM_ID(inventoryLine.getC_UOM_ID());
	}

	private final void setError(final I_C_Invoice_Candidate ic, final Exception ex, final boolean askForDeleteRegeneration)
	{
		ic.setIsInDispute(true); // 07193 - Mark's request

		final I_AD_Note note = null; // we don't have a note
		Services.get(IInvoiceCandBL.class).setError(ic, ex.getLocalizedMessage(), note, askForDeleteRegeneration);
	}

}
