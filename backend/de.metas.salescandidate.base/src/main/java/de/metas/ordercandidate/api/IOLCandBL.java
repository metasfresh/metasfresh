package de.metas.ordercandidate.api;

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

import de.metas.async.AsyncBatchId;
import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryCreateRequest;
import de.metas.document.DocTypeId;
import de.metas.freighcost.FreightCostRule;
import de.metas.order.BPartnerOrderParams;
import de.metas.order.DeliveryRule;
import de.metas.order.DeliveryViaRule;
import de.metas.order.InvoiceRule;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.ordercandidate.spi.IOLCandCreator;
import de.metas.payment.PaymentRule;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.PricingSystemId;
import de.metas.shipping.ShipperId;
import de.metas.user.UserId;
import de.metas.util.ISingletonService;
import org.compiere.model.PO;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author RC
 */
public interface IOLCandBL extends ISingletonService
{
	String MSG_OL_CAND_PROCESSOR_PROCESSING_ERROR_0P = "OLCandProcessor.ProcessingError";

	/**
	 * Creates and updates orders.
	 */
	void process(OLCandProcessorDescriptor processor, @Nullable AsyncBatchId asyncBatchId);

	I_C_OLCand invokeOLCandCreator(PO po, IOLCandCreator olCandCreator);

	/**
	 * Computes the actual price for the given <code>olc</code>. The outcome depends on
	 * <ul>
	 * <li>{@link I_C_OLCand#isManualDiscount()}</li>
	 * <li>{@link I_C_OLCand#isManualPrice()}</li>
	 * <li>{@link I_C_OLCand#getQtyEntered()}</li>
	 * <li>{@link I_C_OLCand#getPriceEntered()}</li>
	 * <li>{@link I_C_OLCand#getDiscount()}</li>
	 * <li>etc</li>
	 * </ul>
	 *
	 * @param olCand                  the order line candidate for which we compute the priceActual
	 * @param qtyOverride             if not <code>null</code>, then this value is used instead of {@link I_C_OLCand#getQtyEntered()}
	 * @param pricingSystemIdOverride if not <code>null</code>, then this value is used instead of {@link I_C_OLCand#getM_PricingSystem_ID()}
	 * @param date to be used in retrieving the actual price
	 */
	IPricingResult computePriceActual(I_C_OLCand olCand, @Nullable BigDecimal qtyOverride, PricingSystemId pricingSystemIdOverride, LocalDate date);

	AttachmentEntry addAttachment(OLCandQuery olCandQuery, AttachmentEntryCreateRequest attachmentEntryCreateRequest);

	DeliveryRule getDeliveryRule(I_C_OLCand record, BPartnerOrderParams bPartnerOrderParams, OLCandOrderDefaults orderDefaults);

	DeliveryViaRule getDeliveryViaRule(I_C_OLCand record, BPartnerOrderParams bPartnerOrderParams, OLCandOrderDefaults orderDefaults);

	FreightCostRule getFreightCostRule(BPartnerOrderParams params, OLCandOrderDefaults orderDefaults);

	InvoiceRule getInvoiceRule(BPartnerOrderParams params, OLCandOrderDefaults orderDefaults);

	PaymentRule getPaymentRule(BPartnerOrderParams params, OLCandOrderDefaults orderDefaults, I_C_OLCand olCandRecord);

	PaymentTermId getPaymentTermId(BPartnerOrderParams params, OLCandOrderDefaults orderDefaults, I_C_OLCand olCandRecord);

	/**
	 * Return the pricing system to use for the given {@code olCand}.
	 * <ul>
	 * <li>if C_OLCand.M_PricingSystem_ID > 0, then return that</li>
	 * <li>else return the bPartnerOrderParams's pricing system</li>
	 * <li>else, if the processor has a pricing system set, then return that</li>
	 * </ul>
	 */
	PricingSystemId getPricingSystemId(I_C_OLCand olCand, BPartnerOrderParams bPartnerOrderParams, OLCandOrderDefaults orderDefaults);

	ShipperId getShipperId(BPartnerOrderParams bPartnerOrderParams, OLCandOrderDefaults orderDefaults, I_C_OLCand olCandRecord);

	BPartnerOrderParams getBPartnerOrderParams(I_C_OLCand olCandRecord);

	DocTypeId getOrderDocTypeId(OLCandOrderDefaults orderDefaults, I_C_OLCand orderCandidateRecord);

	void markAsProcessed(final OLCand olCand);

	void markAsError(final UserId userInChargeId, final OLCand olCand, final Exception ex);
}
