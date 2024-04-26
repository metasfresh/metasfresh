package de.metas.invoicecandidate.spi;

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

import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeBL;
import de.metas.invoice.InvoiceDocBaseType;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidateRecordService;
import de.metas.invoicecandidate.internalbusinesslogic.ToInvoiceData;
import de.metas.invoicecandidate.model.I_C_ILCandHandler;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.order.InvoiceRule;
import de.metas.product.IProductBL;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.product.ProductType;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Product;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/**
 * Simple abstract base class that implements {@link #setHandlerRecord(I_C_ILCandHandler)} and {@link #setNetAmtToInvoice(I_C_Invoice_Candidate)}.
 */
public abstract class AbstractInvoiceCandidateHandler implements IInvoiceCandidateHandler
{
	private final IDocTypeBL docTypeBL = Services.get(IDocTypeBL.class);

	private I_C_ILCandHandler record;

	@Override
	public final I_C_ILCandHandler getHandlerRecord()
	{
		return record;
	}

	@Override
	public final void setHandlerRecord(final I_C_ILCandHandler record)
	{
		this.record = record;
	}

	@Override
	public void setNetAmtToInvoice(@NonNull final I_C_Invoice_Candidate ic)
	{
		// task 08507: ic.getQtyToInvoice() is already the "effective" qty.
		// Even if QtyToInvoice_Override is set, the system will decide what to invoice (e.g. based on InvoiceRule and QtyDelivered)
		// and update QtyToInvoice accordingly, possibly to a value that is different from QtyToInvoice_Override. Therefore, we don't use invoiceCandBL.getQtyToInvoice(ic), but the getter directly

		final Quantity qtyToInvoiceInUOM = Quantitys.of(ic.getQtyToInvoiceInUOM(), UomId.ofRepoId(ic.getC_UOM_ID()));
		final Money netAmtToInvoice = computeNetAmtUsingQty(ic, qtyToInvoiceInUOM);

		ic.setNetAmtToInvoice(netAmtToInvoice.toBigDecimal());
		ic.setSplitAmt(BigDecimal.ZERO);
	}

	@Override
	public void setLineNetAmt(@NonNull final I_C_Invoice_Candidate icRecord)
	{
		final InvoiceCandidateRecordService invoiceCandidateRecordService = SpringContextHolder.instance.getBean(InvoiceCandidateRecordService.class);

		// get the quantity that would/could be invoiced "right now"
		// (note: for negative qtyOrdered the result is negative)
		final ToInvoiceData imediateInvoiceData = invoiceCandidateRecordService
				.ofRecord(icRecord)
				.changeInvoiceRule(InvoiceRule.Immediate)
				.computeToInvoiceData();
		final Quantity openQty = imediateInvoiceData.getQtysEffective().getUOMQtyNotNull();

		final Money netAmtToInvoice = computeNetAmtUsingQty(icRecord, openQty);

		icRecord.setLineNetAmt(netAmtToInvoice.toBigDecimal());
	}

	private Money computeNetAmtUsingQty(
			@NonNull final I_C_Invoice_Candidate ic,
			@NonNull final Quantity qty)
	{
		final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
		final ProductPrice priceActual = invoiceCandBL.getPriceActual(ic);

		final MoneyService moneyService = SpringContextHolder.instance.getBean(MoneyService.class);

		return moneyService.multiply(qty, priceActual);
	}

	/**
	 * Checks if the underlying product is a service which will never ever be received.
	 *
	 * @return true if we deal with a service which will never ever be received
	 * task http://dewiki908/mediawiki/index.php/08408_Transporte_auf_Rechnungsstellung_sofort_setzen_in_Rechnungsdispo_%28107611160033%29
	 */
	protected final boolean isNotReceivebleService(final I_C_Invoice_Candidate ic)
	{
		final IProductDAO productDAO = Services.get(IProductDAO.class);
		final IProductBL productBL = Services.get(IProductBL.class);

		final I_M_Product product = productDAO.getById(ic.getM_Product_ID());

		// If no product, consider it as a non receivable service (maybe it's a charge?!)
		if (product == null)
		{
			return true;
		}

		final ProductType productType = productBL.getProductType(ProductId.ofRepoId(product.getM_Product_ID()));

		if (productType.isFreightCost())
		{
			return false;
		}

		// If the product is not a service
		// => return false
		if (!productType.isService())
		{
			return false;
		}

		// NOTE: at this point i think we shall extend the business logic and check when a service will be received and when not
		// but for now we are considering that it will never ever be received
		return true;
	}

	/**
	 * Sets delivery data from first shipment/receipt that was created for this invoice candidate.
	 *
	 * @param ic         invoice candidate
	 * @param firstInOut first shipment/receipt or <code>null</code>
	 */
	protected final void setDeliveredDataFromFirstInOut(@NonNull final I_C_Invoice_Candidate ic, @Nullable final I_M_InOut firstInOut)
	{
		ic.setM_InOut(firstInOut);

		if (firstInOut == null)
		{
			ic.setDeliveryDate(null);
			ic.setFirst_Ship_BPLocation_ID(-1);
			ic.setC_Shipping_Location_ID(-1);
		}
		else
		{
			ic.setDeliveryDate(firstInOut.getMovementDate());
			ic.setFirst_Ship_BPLocation_ID(firstInOut.getC_BPartner_Location_ID()); // C_BPartner_Location
			ic.setC_Shipping_Location_ID(firstInOut.getC_BPartner_Location_Value_ID()); // C_Location
		}
	}

	/**
	 * Assumes that the given {@code icRecord}'s {@code AD_Client_ID}, {@code AD_Org_ID} and {@code IsSOTrx} are already set at this point
	 */
	protected final void setDefaultInvoiceDocType(final @NonNull I_C_Invoice_Candidate icRecord)
	{
		final DocTypeQuery docTypeQuery = DocTypeQuery.builder()
				.adClientId(icRecord.getAD_Client_ID())
				.adOrgId(icRecord.getAD_Org_ID())
				.isSOTrx(icRecord.isSOTrx())
				.docBaseType(icRecord.isSOTrx()
									 ? InvoiceDocBaseType.CustomerInvoice.getDocBaseType()
									 : InvoiceDocBaseType.VendorInvoice.getDocBaseType())
				.build();
		final DocTypeId docTypeIdOrNull = docTypeBL.getDocTypeIdOrNull(docTypeQuery);
		if (docTypeIdOrNull != null)
		{
			icRecord.setC_DocTypeInvoice_ID(docTypeIdOrNull.getRepoId());
		}
	}
}
