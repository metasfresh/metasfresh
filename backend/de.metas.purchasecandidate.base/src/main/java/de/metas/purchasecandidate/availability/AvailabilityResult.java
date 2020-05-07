package de.metas.purchasecandidate.availability;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Nullable;

import org.adempiere.util.Services;
import org.compiere.util.Env;

import de.metas.i18n.IMsgBL;
import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.vendor.gateway.api.VendorGatewayService;
import de.metas.vendor.gateway.api.availability.AvailabilityResponseItem;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.purchasecandidate.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Value
@Builder
public class AvailabilityResult
{
	public static AvailabilityResultBuilder prepareBuilderFor(
			@NonNull final AvailabilityResponseItem availabilityResponseItem)
	{
		final Type type = Type.ofAvailabilityResponseItemType(availabilityResponseItem.getType());

		return AvailabilityResult.builder()
				.type(type)
				.availabilityText(availabilityResponseItem.getAvailabilityText())
				.datePromised(availabilityResponseItem.getDatePromised())
				.qty(availabilityResponseItem.getAvailableQuantity());
	}

	public enum Type
	{
		AVAILABLE, NOT_AVAILABLE;

		public String translate()
		{
			final String msgValue = "de.metas.purchasecandidate.AvailabilityResult_" + this.toString();
			return Services.get(IMsgBL.class).translate(Env.getCtx(), msgValue);
		}

		public static Type ofAvailabilityResponseItemType(
				@NonNull final de.metas.vendor.gateway.api.availability.AvailabilityResponseItem.Type type)
		{
			if (de.metas.vendor.gateway.api.availability.AvailabilityResponseItem.Type.AVAILABLE.equals(type))
			{
				return Type.AVAILABLE;
			}
			return Type.NOT_AVAILABLE;
		}
	}

	PurchaseCandidate purchaseCandidate;

	Type type;

	BigDecimal qty;

	Date datePromised;

	String availabilityText;

	VendorGatewayService vendorGatewayServicethatWasUsed;

	private AvailabilityResult(
			@NonNull final PurchaseCandidate purchaseCandidate,
			@NonNull final Type type,
			@NonNull final BigDecimal qty,
			@Nullable final Date datePromised,
			@Nullable final String availabilityText,
			@Nullable final VendorGatewayService vendorGatewayServicethatWasUsed)
	{
		this.purchaseCandidate = purchaseCandidate;
		this.type = type;
		this.qty = qty;
		this.datePromised = datePromised;
		this.availabilityText = availabilityText;
		this.vendorGatewayServicethatWasUsed = vendorGatewayServicethatWasUsed;
	}
}
