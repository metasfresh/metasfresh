/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.rest_api.v2.bpartner.bpartnercomposite;

import de.metas.common.bpartner.v2.common.JsonDeliveryRule;
import de.metas.common.bpartner.v2.common.JsonDeliveryViaRule;
import de.metas.common.bpartner.v2.common.JsonPaymentRule;
import de.metas.order.DeliveryRule;
import de.metas.order.DeliveryViaRule;
import de.metas.payment.PaymentRule;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ValueMappingHelper
{
	@NonNull
	public static DeliveryRule getDeliveryRule(final @NonNull JsonDeliveryRule jsonRequestBPartnerDeliveryRule)
	{
		final DeliveryRule deliveryRule;
		switch (jsonRequestBPartnerDeliveryRule)
		{
			case AfterReceipt:
				deliveryRule = DeliveryRule.AFTER_RECEIPT;
				break;
			case Availability:
				deliveryRule = DeliveryRule.AVAILABILITY;
				break;
			case CompleteLine:
				deliveryRule = DeliveryRule.COMPLETE_LINE;
				break;
			case CompleteOrder:
				deliveryRule = DeliveryRule.COMPLETE_ORDER;
				break;
			case Force:
				deliveryRule = DeliveryRule.FORCE;
				break;
			case Manual:
				deliveryRule = DeliveryRule.MANUAL;
				break;
			case WithNextSubscriptionDelivery:
				deliveryRule = DeliveryRule.WITH_NEXT_SUBSCRIPTION_DELIVERY;
				break;
			default:
				throw Check.fail("Unexpected deliveryRule={}", jsonRequestBPartnerDeliveryRule);
		}
		return deliveryRule;
	}

	@NonNull
	public static DeliveryViaRule getDeliveryViaRule(final @NonNull JsonDeliveryViaRule jsonRequestBPartnerDeliveryViaRule)
	{
		final DeliveryViaRule deliveryViaRule;
		switch (jsonRequestBPartnerDeliveryViaRule)
		{
			case PickUp:
				deliveryViaRule = DeliveryViaRule.Pickup;
				break;
			case Delivery:
				deliveryViaRule = DeliveryViaRule.Delivery;
				break;
			case Shipper:
				deliveryViaRule = DeliveryViaRule.Shipper;
				break;
			case Normalpost:
				deliveryViaRule = DeliveryViaRule.NormalPost;
				break;
			case Luftpost:
				deliveryViaRule = DeliveryViaRule.LuftPost;
				break;
			default:
				throw Check.fail("Unexpected deliveryViaRule={}", jsonRequestBPartnerDeliveryViaRule);
		}
		return deliveryViaRule;
	}

	@NonNull
	public static PaymentRule getPaymentRule(final @NonNull JsonPaymentRule jsonRequestBPartnerPaymentRule)
	{
		final PaymentRule paymentRule;
		switch (jsonRequestBPartnerPaymentRule)
		{
			case Cash:
				paymentRule = PaymentRule.Cash;
				break;
			case CreditCard:
				paymentRule = PaymentRule.CreditCard;
				break;
			case DirectDeposit:
				paymentRule = PaymentRule.DirectDeposit;
				break;
			case Check:
				paymentRule = PaymentRule.Check;
				break;
			case OnCredit:
				paymentRule = PaymentRule.OnCredit;
				break;
			case DirectDebit:
				paymentRule = PaymentRule.DirectDebit;
				break;
			case Mixed:
				paymentRule = PaymentRule.Mixed;
				break;
			case Paypal:
				paymentRule = PaymentRule.PayPal;
				break;
			case PaypalExtern:
				paymentRule = PaymentRule.PayPalExtern;
				break;
			case CreditCardExtern:
				paymentRule = PaymentRule.CreditCardExtern;
				break;
			case InstantBankTransfer:
				paymentRule = PaymentRule.InstantBankTransfer;
				break;
			default:
				throw Check.fail("Unexpected paymentRule={};", jsonRequestBPartnerPaymentRule);
		}
		return paymentRule;
	}

	@NonNull
	public static JsonDeliveryRule getJsonDeliveryRule(final @NonNull DeliveryRule deliveryRule)
	{
		final JsonDeliveryRule jsonDeliveryRule;
		switch (deliveryRule)
		{
			case AFTER_RECEIPT:
				jsonDeliveryRule = JsonDeliveryRule.AfterReceipt;
				break;
			case AVAILABILITY:
				jsonDeliveryRule = JsonDeliveryRule.Availability;
				break;
			case COMPLETE_LINE:
				jsonDeliveryRule = JsonDeliveryRule.CompleteLine;
				break;
			case COMPLETE_ORDER:
				jsonDeliveryRule = JsonDeliveryRule.CompleteOrder;
				break;
			case FORCE:
				jsonDeliveryRule = JsonDeliveryRule.Force;
				break;
			case MANUAL:
				jsonDeliveryRule = JsonDeliveryRule.Manual;
				break;
			case WITH_NEXT_SUBSCRIPTION_DELIVERY:
				jsonDeliveryRule = JsonDeliveryRule.WithNextSubscriptionDelivery;
				break;
			default:
				throw Check.fail("Unexpected deliveryRule={}", deliveryRule);
		}
		return jsonDeliveryRule;
	}

	@NonNull
	public static JsonDeliveryViaRule getJsonDeliveryViaRule(final @NonNull DeliveryViaRule deliveryViaRule)
	{
		final JsonDeliveryViaRule jsonDeliveryViaRule;
		switch (deliveryViaRule)
		{
			case Pickup:
				jsonDeliveryViaRule = JsonDeliveryViaRule.PickUp;
				break;
			case Delivery:
				jsonDeliveryViaRule = JsonDeliveryViaRule.Delivery;
				break;
			case Shipper:
				jsonDeliveryViaRule = JsonDeliveryViaRule.Shipper;
				break;
			case NormalPost:
				jsonDeliveryViaRule = JsonDeliveryViaRule.Normalpost;
				break;
			case LuftPost:
				jsonDeliveryViaRule = JsonDeliveryViaRule.Luftpost;
				break;
			default:
				throw Check.fail("Unexpected deliveryViaRule={}", deliveryViaRule);
		}
		return jsonDeliveryViaRule;
	}

	@NonNull
	public static JsonPaymentRule getJsonPaymentRule(final @NonNull PaymentRule paymentRule)
	{
		final JsonPaymentRule jsonPaymentRule;
		switch (paymentRule)
		{
			case PayPal:
				jsonPaymentRule = JsonPaymentRule.Paypal;
				break;
			case PayPalExtern:
				jsonPaymentRule = JsonPaymentRule.PaypalExtern;
				break;
			case OnCredit:
				jsonPaymentRule = JsonPaymentRule.OnCredit;
				break;
			case Cash:
				jsonPaymentRule = JsonPaymentRule.Cash;
				break;
			case CreditCard:
				jsonPaymentRule = JsonPaymentRule.CreditCard;
				break;
			case CreditCardExtern:
				jsonPaymentRule = JsonPaymentRule.CreditCardExtern;
				break;
			case DirectDeposit:
				jsonPaymentRule = JsonPaymentRule.DirectDeposit;
				break;
			case Check:
				jsonPaymentRule = JsonPaymentRule.Check;
				break;
			case Mixed:
				jsonPaymentRule = JsonPaymentRule.Mixed;
				break;
			case DirectDebit:
				jsonPaymentRule = JsonPaymentRule.DirectDebit;
				break;
			case InstantBankTransfer:
				jsonPaymentRule = JsonPaymentRule.InstantBankTransfer;
				break;
			default:
				throw Check.fail("Unexpected paymentRule={};", paymentRule);
		}
		return jsonPaymentRule;
	}
}
