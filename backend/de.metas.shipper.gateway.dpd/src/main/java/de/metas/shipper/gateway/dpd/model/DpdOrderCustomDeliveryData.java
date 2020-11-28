/*
 * #%L
 * de.metas.shipper.gateway.dpd
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

package de.metas.shipper.gateway.dpd.model;

import de.metas.shipper.gateway.spi.model.CustomDeliveryData;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@Builder(toBuilder = true)
@Value
public class DpdOrderCustomDeliveryData implements CustomDeliveryData
{
	String orderType;
	String sendingDepot;
	String printerLanguage;
	@NonNull
	DpdPaperFormat paperFormat;
	@NonNull
	DpdNotificationChannel notificationChannel;

	@Nullable
	byte[] pdfData;

	@NonNull
	public static DpdOrderCustomDeliveryData cast(@Nullable final CustomDeliveryData customDeliveryData)
	{
		if (customDeliveryData == null)
		{
			throw new AdempiereException("DPD custom delivery data should not be null");
		}
		return (DpdOrderCustomDeliveryData)customDeliveryData;
	}
}
