package de.metas.payment.reservation;

import java.util.Optional;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Payment;
import org.springframework.stereotype.Service;

import de.metas.order.OrderId;
import de.metas.payment.PaymentId;
import de.metas.payment.PaymentRule;
import de.metas.payment.TenderType;
import de.metas.payment.api.IPaymentBL;
import de.metas.payment.processor.PaymentProcessor;
import de.metas.payment.processor.PaymentProcessorService;
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

@Service
public class PaymentReservationService
{
	private final PaymentReservationRepository reservationsRepo;
	private final PaymentReservationCaptureRepository capturesRepo;
	private final PaymentProcessorService paymentProcessors;

	public PaymentReservationService(
			@NonNull final PaymentReservationRepository reservationsRepo,
			@NonNull final PaymentReservationCaptureRepository capturesRepo,
			@NonNull final PaymentProcessorService paymentProcessors)
	{
		this.reservationsRepo = reservationsRepo;
		this.capturesRepo = capturesRepo;
		this.paymentProcessors = paymentProcessors;
	}

	public boolean isPaymentReservationRequired(@NonNull final PaymentRule paymentRule)
	{
		return getPaymentProcessorIfExists(paymentRule)
				.map(PaymentProcessor::canReserveMoney)
				.orElse(Boolean.FALSE);
	}

	public boolean hasPaymentReservation(@NonNull final OrderId salesOrderId)
	{
		return getBySalesOrderIdNotVoided(salesOrderId).isPresent();
	}

	public Optional<PaymentReservation> getBySalesOrderIdNotVoided(@NonNull final OrderId salesOrderId)
	{
		return reservationsRepo.getBySalesOrderIdNotVoided(salesOrderId);
	}

	public PaymentReservation getById(@NonNull final PaymentReservationId id)
	{
		return reservationsRepo.getById(id);
	}

	public void save(@NonNull final PaymentReservation paymentReservation)
	{
		reservationsRepo.save(paymentReservation);
	}

	public PaymentReservation createReservation(@NonNull final PaymentReservationCreateRequest createRequest)
	{
		//
		// Create & save
		final PaymentReservation paymentReservation = PaymentReservation.builder()
				.clientId(createRequest.getClientId())
				.orgId(createRequest.getOrgId())
				.amount(createRequest.getAmount())
				.payerContactId(createRequest.getPayerContactId())
				.payerEmail(createRequest.getPayerEmail())
				.salesOrderId(createRequest.getSalesOrderId())
				.dateTrx(createRequest.getDateTrx())
				.paymentRule(createRequest.getPaymentRule())
				.status(PaymentReservationStatus.WAITING_PAYER_APPROVAL)
				.build();
		save(paymentReservation);

		//
		// Process
		final PaymentProcessor paymentProcessor = getPaymentProcessor(paymentReservation.getPaymentRule());
		paymentProcessor.processReservation(paymentReservation);
		save(paymentReservation);

		return paymentReservation;
	}

	private PaymentProcessor getPaymentProcessor(final PaymentRule paymentRule)
	{
		return getPaymentProcessorIfExists(paymentRule)
				.orElseThrow(() -> new AdempiereException("No payment processor found for payment rule: " + paymentRule));
	}

	private Optional<PaymentProcessor> getPaymentProcessorIfExists(final PaymentRule paymentRule)
	{
		return paymentProcessors.getByPaymentRule(paymentRule);
	}

	public void captureAmount(@NonNull final PaymentReservationCaptureRequest request)
	{
		final PaymentReservation reservation = getBySalesOrderIdNotVoided(request.getSalesOrderId())
				.orElse(null);
		if (reservation == null)
		{
			return;
		}

		// eagerly fetching the processor to fail fast
		final PaymentProcessor paymentProcessor = getPaymentProcessor(reservation.getPaymentRule());

		final PaymentId paymentId = createPayment(request);

		final PaymentReservationCapture capture = PaymentReservationCapture.builder()
				.reservationId(reservation.getId())
				.status(PaymentReservationCaptureStatus.NEW)
				//
				.orgId(reservation.getOrgId())
				.salesOrderId(reservation.getSalesOrderId())
				.salesInvoiceId(request.getSalesInvoiceId())
				.paymentId(paymentId)
				//
				.amount(request.getAmount())
				//
				.build();
		capturesRepo.save(capture);

		paymentProcessor.processCapture(reservation, capture);
		reservationsRepo.save(reservation);

		capture.setStatusAsCompleted();
		capturesRepo.save(capture);
	}

	private PaymentId createPayment(@NonNull final PaymentReservationCaptureRequest request)
	{
		final I_C_Payment payment = Services.get(IPaymentBL.class).newInboundReceiptBuilder()
				.invoiceId(request.getSalesInvoiceId())
				.bpartnerId(request.getCustomerId())
				.payAmt(request.getAmount().toBigDecimal())
				.currencyId(request.getAmount().getCurrencyId())
				.tenderType(TenderType.DirectDeposit)
				.dateTrx(request.getDateTrx())
				.createAndProcess();
		return PaymentId.ofRepoId(payment.getC_Payment_ID());
	}
}
