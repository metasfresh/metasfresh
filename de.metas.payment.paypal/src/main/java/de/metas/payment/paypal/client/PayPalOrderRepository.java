package de.metas.payment.paypal.client;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.util.List;
import java.util.Optional;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.orders.Authorization;
import com.paypal.orders.LinkDescription;
import com.paypal.orders.PaymentCollection;
import com.paypal.orders.PurchaseUnit;

import de.metas.logging.LogManager;
import de.metas.payment.paypal.model.I_PayPal_Order;
import de.metas.payment.reservation.PaymentReservationId;
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

	public PayPalOrder getById(@NonNull final PayPalOrderId id)
	{
		final I_PayPal_Order record = load(id, I_PayPal_Order.class);
		return toPayPalOrder(record);
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

	public Optional<I_PayPal_Order> getRecordByExternalId(@NonNull final PayPalOrderExternalId externalId)
	{
		final I_PayPal_Order record = Services.get(IQueryBL.class).createQueryBuilder(I_PayPal_Order.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_PayPal_Order.COLUMN_ExternalId, externalId.getAsString())
				.create()
				.firstOnly(I_PayPal_Order.class);

		return Optional.ofNullable(record);
	}

	private static PayPalOrder toPayPalOrder(final I_PayPal_Order record)
	{
		return PayPalOrder.builder()
				.id(PayPalOrderId.ofRepoIdOrNull(record.getPayPal_Order_ID()))
				.paymentReservationId(PaymentReservationId.ofRepoId(record.getC_Payment_Reservation_ID()))
				.externalId(PayPalOrderExternalId.ofNullableString(record.getExternalId()))
				.status(PayPalOrderStatus.ofCode(record.getStatus()))
				.authorizationId(PayPalOrderAuthorizationId.ofNullableString(record.getPayPal_AuthorizationId()))
				.payerApproveUrlString(record.getPayPal_PayerApproveUrl())
				.bodyAsJson(record.getPayPal_OrderJSON())
				.build();
	}

	private PayPalOrder updateFromAPIOrder(
			@NonNull final PayPalOrder order,
			@NonNull final com.paypal.orders.Order apiOrder)
	{
		return order.toBuilder()
				.externalId(PayPalOrderExternalId.ofString(apiOrder.id()))
				.status(PayPalOrderStatus.ofCode(apiOrder.status()))
				.payerApproveUrlString(extractApproveUrlOrNull(apiOrder))
				.authorizationId(extractAuthorizationIdOrNull(apiOrder))
				.bodyAsJson(toJson(apiOrder))
				.build();
	}

	private static void updateRecord(final I_PayPal_Order order, final PayPalOrder from)
	{
		order.setExternalId(PayPalOrderExternalId.toString(from.getExternalId()));
		order.setPayPal_AuthorizationId(PayPalOrderAuthorizationId.toString(from.getAuthorizationId()));
		order.setPayPal_PayerApproveUrl(from.getPayerApproveUrlString());
		order.setPayPal_OrderJSON(from.getBodyAsJson());
	}

	public PayPalOrder save(
			@NonNull final PaymentReservationId reservationId,
			@NonNull final com.paypal.orders.Order apiOrder)
	{
		I_PayPal_Order record = getRecordByReservationId(reservationId).orElse(null);
		if (record == null)
		{
			record = newInstance(I_PayPal_Order.class);
			record.setC_Payment_Reservation_ID(reservationId.getRepoId());
			record.setStatus(apiOrder.status());
			record.setExternalId(apiOrder.id());
		}

		return updateFromAPIOrderAndSave(record, apiOrder);
	}

	public PayPalOrder save(
			@NonNull final PayPalOrderExternalId externalId,
			@NonNull final com.paypal.orders.Order apiOrder)
	{
		final I_PayPal_Order existingRecord = getRecordByExternalId(externalId).orElse(null);
		if (existingRecord == null)
		{
			throw new AdempiereException("@NotFound@ @PayPal_Order_ID@ (@ExternalId@: " + externalId + ")");
		}

		return updateFromAPIOrderAndSave(existingRecord, apiOrder);
	}

	private PayPalOrder updateFromAPIOrderAndSave(
			@NonNull final I_PayPal_Order record,
			@NonNull final com.paypal.orders.Order apiOrder)
	{
		PayPalOrder order = toPayPalOrder(record);
		order = updateFromAPIOrder(order, apiOrder);

		updateRecord(record, order);
		saveRecord(record);

		order = order.withId(PayPalOrderId.ofRepoId(record.getPayPal_Order_ID()));

		return order;
	}

	private static PayPalOrderAuthorizationId extractAuthorizationIdOrNull(@NonNull final com.paypal.orders.Order apiOrder)
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

		return PayPalOrderAuthorizationId.ofString(authorizations.get(0).id());
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
