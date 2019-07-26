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

import com.paypal.orders.Authorization;
import com.paypal.orders.LinkDescription;
import com.paypal.orders.PaymentCollection;
import com.paypal.orders.PurchaseUnit;

import de.metas.logging.LogManager;
import de.metas.payment.paypal.model.I_PayPal_Order;
import de.metas.payment.reservation.PaymentReservationId;
import de.metas.util.Check;
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

	public PayPalOrder getById(@NonNull final PayPalOrderId id)
	{
		final I_PayPal_Order record = getRecordById(id);
		return toPayPalOrder(record);
	}

	private I_PayPal_Order getRecordById(@NonNull final PayPalOrderId id)
	{
		final I_PayPal_Order record = load(id, I_PayPal_Order.class);
		Check.assumeNotNull(record, "paypal order shall exist for {}", id);
		return record;
	}

	public Optional<PayPalOrder> getByReservationId(@NonNull final PaymentReservationId reservationId)
	{
		return getActiveRecordByReservationId(reservationId).map(PayPalOrderRepository::toPayPalOrder);
	}

	private Optional<I_PayPal_Order> getActiveRecordByReservationId(@NonNull final PaymentReservationId reservationId)
	{
		final I_PayPal_Order record = Services.get(IQueryBL.class).createQueryBuilder(I_PayPal_Order.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_PayPal_Order.COLUMN_C_Payment_Reservation_ID, reservationId)
				.create()
				.firstOnly(I_PayPal_Order.class);

		return Optional.ofNullable(record);
	}

	public PayPalOrder getByExternalId(@NonNull final PayPalOrderExternalId externalId)
	{
		final I_PayPal_Order record = getRecordByExternalId(externalId);
		return toPayPalOrder(record);
	}

	private I_PayPal_Order getRecordByExternalId(@NonNull final PayPalOrderExternalId externalId)
	{
		final I_PayPal_Order record = Services.get(IQueryBL.class).createQueryBuilder(I_PayPal_Order.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_PayPal_Order.COLUMN_ExternalId, externalId.getAsString())
				.create()
				.firstOnly(I_PayPal_Order.class);
		if (record == null)
		{
			throw new AdempiereException("@NotFound@ @PayPal_Order_ID@ (@ExternalId@: " + externalId + ")");
		}
		return record;
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
		final PayPalOrderStatus status = from.getStatus();
		final boolean active = !PayPalOrderStatus.REMOTE_DELETED.equals(status);

		order.setExternalId(PayPalOrderExternalId.toString(from.getExternalId()));
		order.setStatus(status.getCode());
		order.setIsActive(active);
		order.setPayPal_AuthorizationId(PayPalOrderAuthorizationId.toString(from.getAuthorizationId()));
		order.setPayPal_PayerApproveUrl(from.getPayerApproveUrlString());
		order.setPayPal_OrderJSON(from.getBodyAsJson());
	}

	public PayPalOrder create(@NonNull final PaymentReservationId reservationId)
	{
		final I_PayPal_Order record = newInstance(I_PayPal_Order.class);
		record.setC_Payment_Reservation_ID(reservationId.getRepoId());
		record.setStatus("-");
		record.setPayPal_OrderJSON("{}");
		record.setExternalId("-"); // need to set it because it's needed in PayPalOrder
		saveRecord(record);

		return toPayPalOrder(record);
	}

	public PayPalOrder save(
			@NonNull final PayPalOrderId id,
			@NonNull final com.paypal.orders.Order apiOrder)
	{
		final I_PayPal_Order existingRecord = getRecordById(id);
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

	private static String toJson(final com.paypal.orders.Order apiOrder)
	{
		if (apiOrder == null)
		{
			return "";
		}

		try
		{
			// IMPORTANT: we shall use paypal's JSON serializer, else we won't get any result
			return new com.braintreepayments.http.serializer.Json().serialize(apiOrder);
		}
		catch (final Exception ex)
		{
			logger.warn("Failed converting {} to JSON. Returning toString()", apiOrder, ex);
			return apiOrder.toString();
		}
	}

	public PayPalOrder markRemoteDeleted(@NonNull final PayPalOrderId id)
	{
		final I_PayPal_Order existingRecord = getRecordById(id);

		final PayPalOrder order = toPayPalOrder(existingRecord)
				.toBuilder()
				.status(PayPalOrderStatus.REMOTE_DELETED)
				.build();

		updateRecord(existingRecord, order);
		saveRecord(existingRecord);

		return order;
	}

}
