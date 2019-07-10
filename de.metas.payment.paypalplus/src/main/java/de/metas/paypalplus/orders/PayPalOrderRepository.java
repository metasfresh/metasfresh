package de.metas.paypalplus.orders;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.util.List;
import java.util.Optional;

import org.adempiere.ad.dao.IQueryBL;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.orders.Authorization;
import com.paypal.orders.LinkDescription;
import com.paypal.orders.Order;
import com.paypal.orders.PaymentCollection;
import com.paypal.orders.PurchaseUnit;

import de.metas.logging.LogManager;
import de.metas.payment.paypal.model.I_PayPal_Order;
import de.metas.payment.reservation.PaymentReservationId;
import de.metas.paypalplus.orders.PayPalOrder.PayPalOrderBuilder;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.payment.paypalplus
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

@Repository
public class PayPalOrderRepository
{
	private static final Logger logger = LogManager.getLogger(PayPalOrderRepository.class);

	private final ObjectMapper jsonObjectMapper;

	public PayPalOrderRepository(
			@NonNull final Optional<ObjectMapper> jsonObjectMapper)
	{
		if (jsonObjectMapper.isPresent())
		{
			this.jsonObjectMapper = jsonObjectMapper.get();
		}
		else
		{
			this.jsonObjectMapper = new ObjectMapper();
			logger.warn("Using internal JSON object mapper");
		}
	}

	public Optional<PayPalOrder> getByReservationId(@NonNull final PaymentReservationId reservationId)
	{
		return getRecordByReservationId(reservationId).map(PayPalOrderRepository::toPayPalOrder);
	}

	public Optional<I_PayPal_Order> getRecordByReservationId(@NonNull final PaymentReservationId reservationId)
	{
		final I_PayPal_Order record = Services.get(IQueryBL.class).createQueryBuilder(I_PayPal_Order.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_PayPal_Order.COLUMN_C_Payment_Reservation_ID, reservationId)
				.create()
				.firstOnly(I_PayPal_Order.class);

		return Optional.ofNullable(record);
	}

	private static PayPalOrder toPayPalOrder(final I_PayPal_Order record)
	{
		return preparePayPalOrderFromRecord(record).build();
	}

	private static PayPalOrder.PayPalOrderBuilder preparePayPalOrderFromRecord(final I_PayPal_Order record)
	{
		return PayPalOrder.builder()
				.paymentReservationId(PaymentReservationId.ofRepoId(record.getC_Payment_Reservation_ID()))
				.externalId(record.getExternalId())
				.authorizationId(record.getPayPal_AuthorizationId())
				.payerApproveUrlString(record.getPayPal_PayerApproveUrl())
				.bodyAsJson(record.getPayPal_OrderJSON());
	}

	private static void updateRecord(final I_PayPal_Order order, final PayPalOrder from)
	{
		order.setExternalId(from.getExternalId());
		order.setPayPal_AuthorizationId(from.getAuthorizationId());
		order.setPayPal_PayerApproveUrl(from.getPayerApproveUrlString());
		order.setPayPal_OrderJSON(from.getBodyAsJson());
	}

	public PayPalOrder save(
			@NonNull final PaymentReservationId reservationId,
			@NonNull final com.paypal.orders.Order apiOrder)
	{
		I_PayPal_Order record = getRecordByReservationId(reservationId).orElse(null);
		final PayPalOrderBuilder orderBuilder;
		if (record == null)
		{
			orderBuilder = PayPalOrder.builder().paymentReservationId(reservationId);
			record = newInstance(I_PayPal_Order.class);
		}
		else
		{
			orderBuilder = preparePayPalOrderFromRecord(record);
		}

		orderBuilder.externalId(apiOrder.id());
		orderBuilder.payerApproveUrlString(extractApproveUrlOrNull(apiOrder));
		orderBuilder.authorizationId(extractAuthorizationIdOrNull(apiOrder));
		orderBuilder.bodyAsJson(toJson(apiOrder));

		final PayPalOrder order = orderBuilder.build();
		updateRecord(record, order);
		saveRecord(record);

		return order;
	}

	private String extractAuthorizationIdOrNull(@NonNull final Order apiOrder)
	{
		final List<PurchaseUnit> purchaseUnits = apiOrder.purchaseUnits();
		if (purchaseUnits == null || purchaseUnits.isEmpty())
		{
			return null;
		}

		final PaymentCollection payments = purchaseUnits.get(0).payments();
		if (payments == null)
		{
			return null;
		}

		final List<Authorization> authorizations = payments.authorizations();
		if (authorizations == null || authorizations.isEmpty())
		{
			return null;
		}

		return authorizations.get(0).id();
	}

	private static String extractApproveUrlOrNull(@NonNull final com.paypal.orders.Order apiOrder)
	{
		return extractUrlOrNull(apiOrder, "approve");
	}

	private static String extractUrlOrNull(@NonNull final com.paypal.orders.Order apiOrder, @NonNull final String rel)
	{
		for (final LinkDescription link : apiOrder.links())
		{
			if (rel.contentEquals(link.rel()))
			{
				return link.href();
			}
		}

		return null;
	}

	private String toJson(final Object obj)
	{
		if (obj == null)
		{
			return "";
		}

		try
		{
			return jsonObjectMapper.writeValueAsString(obj);
		}
		catch (JsonProcessingException ex)
		{
			logger.warn("Failed converting object to JSON. Returning toString(): {}", obj, ex);
			return obj.toString();
		}
	}
}
