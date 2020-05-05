package de.metas.purchasecandidate.availability;

import java.time.ZonedDateTime;

import javax.annotation.Nullable;

import org.compiere.model.I_C_UOM;
import org.compiere.util.Env;

import de.metas.i18n.IMsgBL;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import de.metas.vendor.gateway.api.VendorGatewayService;
import de.metas.vendor.gateway.api.availability.AvailabilityResponseItem;
import de.metas.vendor.gateway.api.availability.TrackingId;
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
public class AvailabilityResult
{
	public static AvailabilityResultBuilder prepareBuilderFor(@NonNull final AvailabilityResponseItem responseItem, final I_C_UOM uom)
	{
		return AvailabilityResult.builder()
				.trackingId(responseItem.getTrackingId())
				.type(Type.ofAvailabilityResponseItemType(responseItem.getType()))
				.availabilityText(responseItem.getAvailabilityText())
				.datePromised(responseItem.getDatePromised())
				.qty(Quantity.of(responseItem.getAvailableQuantity(), uom));
	}

	public enum Type
	{
		AVAILABLE, NOT_AVAILABLE;

		public String translate()
		{
			final String msgValue = "de.metas.purchasecandidate.AvailabilityResult_" + this.toString();
			return Services.get(IMsgBL.class).translate(Env.getCtx(), msgValue);
		}

		public static Type ofAvailabilityResponseItemType(@NonNull final AvailabilityResponseItem.Type type)
		{
			if (AvailabilityResponseItem.Type.AVAILABLE == type)
			{
				return Type.AVAILABLE;
			}
			else
			{
				return Type.NOT_AVAILABLE;
			}
		}
	}

	TrackingId trackingId;

	Type type;

	Quantity qty;

	ZonedDateTime datePromised;

	String availabilityText;

	VendorGatewayService vendorGatewayServicethatWasUsed;

	@Builder
	private AvailabilityResult(
			@Nullable TrackingId trackingId,
			@NonNull final Type type,
			@NonNull final Quantity qty,
			@Nullable final ZonedDateTime datePromised,
			@Nullable final String availabilityText,
			@Nullable final VendorGatewayService vendorGatewayServicethatWasUsed)
	{
		this.trackingId = trackingId;
		this.type = type;
		this.qty = qty;
		this.datePromised = datePromised;
		this.availabilityText = availabilityText;
		this.vendorGatewayServicethatWasUsed = vendorGatewayServicethatWasUsed;
	}
}
