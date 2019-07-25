package de.metas.payment.reservation;

import java.util.Optional;

import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import de.metas.order.OrderId;
import de.metas.payment.PaymentRule;
import de.metas.payment.processor.PaymentProcessor;
import de.metas.payment.processor.PaymentProcessorService;
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
	private final PaymentProcessorService paymentProcessors;

	public PaymentReservationService(
			@NonNull final PaymentReservationRepository reservationsRepo,
			@NonNull final PaymentProcessorService paymentProcessors)
	{
		this.reservationsRepo = reservationsRepo;
		this.paymentProcessors = paymentProcessors;
	}

	public boolean isPaymentReservationRequired(@NonNull final PaymentRule paymentRule)
	{
		return getPaymentProcessorIfExists(paymentRule)
				.map(PaymentProcessor::canReserveMoney)
				.orElse(Boolean.FALSE);
	}

	public boolean isPaymentCaptureRequired(@NonNull final PaymentRule paymentRule)
	{
		return getPaymentProcessorIfExists(paymentRule)
				.map(PaymentProcessor::canReserveMoney)
				.orElse(Boolean.FALSE);
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

	public PaymentReservation create(@NonNull final PaymentReservationCreateRequest createRequest)
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
		if(reservation == null)
		{
			return;
		}

		getPaymentProcessor(reservation.getPaymentRule())
				.captureMoney(reservation, request.getAmount());

		reservationsRepo.save(reservation);
	}
}
