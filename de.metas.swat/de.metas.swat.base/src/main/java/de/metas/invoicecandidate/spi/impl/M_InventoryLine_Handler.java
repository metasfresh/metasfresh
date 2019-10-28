package de.metas.invoicecandidate.spi.impl;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Inventory;
import org.compiere.util.Env;

import de.metas.acct.api.IProductAcctDAO;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner.service.IBPartnerBL.RetrieveContactRequest;
import de.metas.cache.model.impl.TableRecordCacheLocal;
import de.metas.document.engine.DocStatus;
import de.metas.inout.invoicecandidate.M_InOutLine_Handler;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_InvoiceCandidate_InOutLine;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_M_InventoryLine;
import de.metas.invoicecandidate.model.X_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.AbstractInvoiceCandidateHandler;
import de.metas.invoicecandidate.spi.IInventoryLine_HandlerDAO;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateRequest;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateResult;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.user.User;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

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
	// Services
	private static final transient IInventoryLine_HandlerDAO inventoryLineHandlerDAO = Services.get(IInventoryLine_HandlerDAO.class);

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

		final ClientId clientId = ClientId.ofRepoId(inventoryLine.getAD_Client_ID());

		final OrgId orgId = OrgId.ofRepoId(inventoryLine.getAD_Org_ID());
		ic.setAD_Org_ID(orgId.getRepoId());

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
		final ProductId productId = ProductId.ofRepoId(inventoryLine.getM_Product_ID());

		{
			ic.setM_Product_ID(productId.getRepoId());
			// for the time being, the material disposals do not have packing material lines because we only throw the products, not the boxes
			ic.setIsPackagingMaterial(false);
			ic.setQtyToInvoice(ZERO); // to be computed
		}

		//
		// order & delivery stuff
		{
			setOrderedData(ic);
			setDeliveredData(ic);
		}

		//
		// Pricing Informations
		final PriceAndTax priceAndQty = calculatePriceAndQuantityAndUpdate(ic, inventoryLine);

		//
		// Description
		ic.setDescription(inventory.getDescription());

		final org.compiere.model.I_M_InOutLine originInOutLine = inventoryLine.getM_InOutLine();

		Check.assumeNotNull(originInOutLine, "InventoryLine {0} must have an origin inoutline set", inventoryLine);
		final I_M_InOut inOut = originInOutLine.getM_InOut();

		ic.setInvoiceRule(X_C_Invoice_Candidate.INVOICERULE_Immediate); // Immediate (until further requirements)

		//
		// Set C_Activity from Product (07442)
		final ActivityId activityId = Services.get(IProductAcctDAO.class).retrieveActivityForAcct(clientId, orgId, productId);
		ic.setC_Activity_ID(ActivityId.toRepoId(activityId));

		//
		// Set C_Tax from Product (07442)
		final Properties ctx = InterfaceWrapperHelper.getCtx(inventoryLine);
		final TaxCategoryId taxCategoryId = priceAndQty != null ? priceAndQty.getTaxCategoryId() : null;
		final Timestamp shipDate = inOut.getMovementDate();
		final int locationId = inOut.getC_BPartner_Location_ID();

		final int taxId = Services.get(ITaxBL.class).getTax(
				ctx,
				ic,
				taxCategoryId,
				productId.getRepoId(),
				shipDate,
				orgId,
				WarehouseId.ofRepoId(inOut.getM_Warehouse_ID()),
				locationId, // shipC_BPartner_Location_ID
				false); // isSOTrx same as in vendor return
		ic.setC_Tax_ID(taxId);

		//
		// Save the Invoice Candidate, so that we can use it's ID further down
		InterfaceWrapperHelper.save(ic);

		// set Quality Issue Percentage Override

		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNone(inventoryLine.getM_AttributeSetInstance_ID());
		final ImmutableAttributeSet attributes = Services.get(IAttributeDAO.class).getImmutableAttributeSetById(asiId);

		final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);

		invoiceCandBL.setQualityDiscountPercent_Override(ic, attributes);

		//
		// Update InOut Line and flag it as Invoice Candidate generated
		inventoryLine.setIsInvoiceCandidate(true);
		InterfaceWrapperHelper.save(inventoryLine);

		//
		// Create IC-IOL association (07969)
		// Even if our IC is directly linked to M_InOutLine (by AD_Table_ID/Record_ID),
		// we need this association in order to let our engine know this and create the M_MatchInv records.
		final I_C_InvoiceCandidate_InOutLine iciol = InterfaceWrapperHelper.newInstance(I_C_InvoiceCandidate_InOutLine.class, ic);
		// iciol.setQtyInvoiced(QtyInvoiced); // will be set during invoicing to keep track of which movementQty is already invoiced in case of partial invoicing
		iciol.setC_Invoice_Candidate(ic);
		invoiceCandBL.updateICIOLAssociationFromIOL(iciol, originInOutLine);

		return ic;
	}

	private PriceAndTax calculatePriceAndQuantityAndUpdate(final I_C_Invoice_Candidate ic, final I_M_InventoryLine inventoryLine)
	{
		final org.compiere.model.I_M_InOutLine originInOutLine = inventoryLine.getM_InOutLine();
		Check.assumeNotNull(originInOutLine, "InventoryLine {0} must have an origin inoutline set", inventoryLine);

		return M_InOutLine_Handler.calculatePriceAndQuantityAndUpdate(ic, originInOutLine);
	}

	@Override
	public PriceAndTax calculatePriceAndTax(final I_C_Invoice_Candidate ic)
	{
		final I_M_InventoryLine inventoryLine = getM_InventoryLine(ic);

		final org.compiere.model.I_M_InOutLine originInOutLine = inventoryLine.getM_InOutLine();
		Check.assumeNotNull(originInOutLine, "InventoryLine {0} must have an origin inoutline set", inventoryLine);

		return M_InOutLine_Handler.calculatePriceAndTax(ic, originInOutLine);
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
		Check.assumeNotNull(inventoryLine, "Error: no M_InventoryLine found for candidate {}", ic);
		return inventoryLine;
	}

	public static I_M_InventoryLine getM_InventoryLineOrNull(final I_C_Invoice_Candidate ic)
	{
		return TableRecordCacheLocal.getReferencedValue(ic, I_M_InventoryLine.class);
	}

	private void setBPartnerData(
			final I_C_Invoice_Candidate ic,
			@NonNull final I_M_InventoryLine inventoryLine)
	{
		final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);
		final IBPartnerBL bPartnerBL = Services.get(IBPartnerBL.class);

		final org.compiere.model.I_M_InOutLine inOutLine = inventoryLine.getM_InOutLine();

		Check.assumeNotNull(inOutLine, "InventoryLine {0} must have an origin inoutline set", inventoryLine);
		final I_M_InOut inOut = inOutLine.getM_InOut();

		final BPartnerLocationId billBPLocationId;
		final BPartnerContactId billBPContactId;
		// The bill related info cannot be changed in the schedule
		// Therefore, it's safe to set them in the invoice candidate directly from the order (if we have it)
		final I_C_Order inoutOrder = inOut.getC_Order();
		if (inoutOrder != null && inoutOrder.getC_Order_ID() > 0)
		{
			billBPLocationId = BPartnerLocationId.ofRepoIdOrNull(inoutOrder.getBill_BPartner_ID(), inoutOrder.getBill_Location_ID());
			billBPContactId = BPartnerContactId.ofRepoIdOrNull(inoutOrder.getBill_BPartner_ID(), inoutOrder.getBill_User_ID());
		}
		// Otherwise, take it from the inout, but don't use the inout's location and user. They might not be "billto" after all.
		else
		{
			final boolean alsoTryBilltoRelation = true;
			final Properties ctx = InterfaceWrapperHelper.getCtx(ic);

			final I_C_BPartner_Location billBPLocation = bPartnerDAO.retrieveBillToLocation(ctx, inOut.getC_BPartner_ID(), alsoTryBilltoRelation, ITrx.TRXNAME_None);
			billBPLocationId = BPartnerLocationId.ofRepoId(billBPLocation.getC_BPartner_ID(), billBPLocation.getC_BPartner_Location_ID());

			final User billBPContact = bPartnerBL
					.retrieveContactOrNull(RetrieveContactRequest.builder()
							.bpartnerId(billBPLocationId.getBpartnerId())
							.bPartnerLocationId(billBPLocationId)
							.build());
			billBPContactId = billBPContact != null
					? BPartnerContactId.of(billBPLocationId.getBpartnerId(), billBPContact.getId())
					: null;
		}

		Check.assumeNotNull(billBPLocationId, "billBPLocation not null");
		// Bill_User_ID isn't mandatory in C_Order, and isn't considered a must in OLHandler either
		// Check.assumeNotNull(billBPContactId, "billBPContact not null");

		//
		// Set BPartner / Location / Contact
		ic.setBill_BPartner_ID(billBPLocationId.getBpartnerId().getRepoId());
		ic.setBill_Location_ID(billBPLocationId.getRepoId());
		ic.setBill_User_ID(BPartnerContactId.toRepoId(billBPContactId));
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
		final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
		invoiceCandDAO.invalidateCandsThatReference(TableRecordReference.of(model));
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

		final I_M_Inventory inventory = inventoryLine.getM_Inventory();
		final DocStatus inventoryDocStatus = DocStatus.ofCode(inventory.getDocStatus());
		if (inventoryDocStatus.isCompletedOrClosed())
		{
			final BigDecimal qtyMultiplier = ONE.negate();
			final BigDecimal qtyDelivered = inventoryLine.getQtyInternalUse().multiply(qtyMultiplier);
			ic.setQtyEntered(qtyDelivered);
			ic.setQtyOrdered(qtyDelivered);
		}
		else
		{
			// Corrected, voided etc document. Set qty to zero.
			ic.setQtyOrdered(ZERO);
			ic.setQtyEntered(ZERO);
		}

		final IProductBL productBL = Services.get(IProductBL.class);
		final UomId stockingUOMId = productBL.getStockUOMId(inventoryLine.getM_Product_ID());

		ic.setC_UOM_ID(UomId.toRepoId(stockingUOMId));
	}

	@Override
	public void setDeliveredData(final I_C_Invoice_Candidate ic)
	{
		final BigDecimal qtyDelivered = ic.getQtyOrdered();
		ic.setQtyDelivered(qtyDelivered); // when changing this, make sure to threat ProductType.Service specially

		final BigDecimal qtyInUOM = Services.get(IUOMConversionBL.class)
				.convertFromProductUOM(
						ProductId.ofRepoId(ic.getM_Product_ID()),
						UomId.ofRepoId(ic.getC_UOM_ID()),
						qtyDelivered);
		ic.setQtyDeliveredInUOM(qtyInUOM);

		ic.setDeliveryDate(ic.getDateOrdered());

	}
}
