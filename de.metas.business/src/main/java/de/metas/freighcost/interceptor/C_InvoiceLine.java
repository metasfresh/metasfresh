package de.metas.freighcost.interceptor;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.invoice.service.IInvoiceDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.ModelValidator;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.freighcost.FreightCost;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutFreightCostsService;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.invoice.IInvoiceLineBL;
import de.metas.invoice.InvoiceId;
import de.metas.logging.LogManager;
import de.metas.money.Money;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderFreightCostsService;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.tax.api.TaxCategoryId;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Interceptor(I_C_InvoiceLine.class)
@Component("de.metas.freighcost.interceptor.C_InvoiceLine")
public class C_InvoiceLine
{
	private static final Logger logger = LogManager.getLogger(C_InvoiceLine.class);
	private final IInvoiceDAO invoicesRepo = Services.get(IInvoiceDAO.class);
	private final IInOutDAO inoutsRepo = Services.get(IInOutDAO.class);
	private final OrderFreightCostsService orderFreightCostsService;
	private final InOutFreightCostsService shipmentFreightCostsService;

	public C_InvoiceLine(
			@NonNull final OrderFreightCostsService orderFreightCostsService,
			@NonNull final InOutFreightCostsService shipmentFreightCostsService)
	{
		this.orderFreightCostsService = orderFreightCostsService;
		this.shipmentFreightCostsService = shipmentFreightCostsService;
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_NEW)
	public void invoiceLineChanged(final I_C_InvoiceLine line)
	{
		final InOutLineId inoutLineId = InOutLineId.ofRepoIdOrNull(line.getM_InOutLine_ID());
		if (inoutLineId == null)
		{
			logger.debug("{} has M_InOutLine_ID=0. Returning.", line);
			return;
		}

		final InvoiceId invoiceId = InvoiceId.ofRepoId(line.getC_Invoice_ID());
		final I_C_Invoice invoice = invoicesRepo.getByIdInTrx(invoiceId);
		if (!invoice.isSOTrx())
		{
			logger.debug("{} belongs to a purchase invoice. Returning.", line);
			return;
		}

		final OrderLineId orderLineId = OrderLineId.ofRepoIdOrNull(line.getC_OrderLine_ID());
		if (orderLineId != null)
		{
			final I_C_OrderLine orderLine = Services.get(IOrderDAO.class).getOrderLineById(orderLineId);
			final OrderId orderId = OrderId.ofRepoId(orderLine.getC_Order_ID());
			if (orderFreightCostsService.hasFreightCostLine(orderId))
			{
				logger.debug("{}, which we are invoicing here, has an explicit freight cost line. Returning.", orderId);
				return;
			}
		}

		Set<InOutId> inOutIds = invoiceId2inOutIds.get(invoiceId);
		if (inOutIds == null)
		{
			inOutIds = new HashSet<>();
			invoiceId2inOutIds.put(invoiceId, inOutIds);
		}

		final I_M_InOutLine inoutLine = inoutsRepo.getLineById(inoutLineId);
		final InOutId inoutId = InOutId.ofRepoId(inoutLine.getM_InOut_ID());
		if (!inOutIds.add(inoutId))
		{
			logger.debug("There is already a freight cost invoice line for M_InOut_ID={} and M_Invoice_ID={}. Returning.", inoutId, invoiceId);
			return;
		}

		final I_M_InOut inout = inoutsRepo.getById(inoutId);
		if (shipmentFreightCostsService.hasFreightCostLine(inout))
		{
			logger.debug("{}, which we are invoicing here has an explicit freight cost line. Returning.", inout);
			return;
		}

		final Money freightRate = shipmentFreightCostsService.computeFreightRate(inoutId).orElse(null);
		if (freightRate == null || freightRate.signum() <= 0)
		{
			logger.debug("Freight cost for M_InOut_ID={} is {}. Returning", inoutId, freightRate);
			return;
		}

		final MInvoiceLine freightCostLine = new MInvoiceLine(invoice);
		freightCostLine.setPriceEntered(freightRate.getAsBigDecimal());
		freightCostLine.setPriceActual(freightRate.getAsBigDecimal());
		freightCostLine.setPriceList(freightRate.getAsBigDecimal());

		// set the iol-ID so this new freightCostLine can be grouped with the respective shipment's invoice lines.
		freightCostLine.setM_InOutLine_ID(inoutLineId.getRepoId());

		final FreightCost freightCost = shipmentFreightCostsService.retrieveFor(inout);

		freightCostLine.setM_Product_ID(freightCost.getFreightCostProductId().getRepoId());
		freightCostLine.setQtyEntered(BigDecimal.ONE);
		freightCostLine.setQtyInvoiced(BigDecimal.ONE);

		final TaxCategoryId taxCategoryId = Services.get(IInvoiceLineBL.class).getTaxCategoryId(freightCostLine);
		freightCostLine.setC_TaxCategory_ID(TaxCategoryId.toRepoId(taxCategoryId));

		InterfaceWrapperHelper.save(freightCostLine);

		logger.debug("Created new freight cost invoice line with price {} for M_InOut_ID {} and invoice {}", freightRate, inoutId, invoice);
	}

}
