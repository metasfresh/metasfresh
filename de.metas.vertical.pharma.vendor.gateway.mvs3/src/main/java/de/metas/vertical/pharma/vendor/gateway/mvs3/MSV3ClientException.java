package de.metas.vertical.pharma.vendor.gateway.mvs3;

import java.util.Collection;
import java.util.Map;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.vendor.gateway.api.ProductAndQuantity;
import de.metas.vendor.gateway.api.availability.AvailabilityRequestException;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.Msv3FaultInfo;
import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-pharma.vendor.gateway.mvs3
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

public class MSV3ClientException extends AdempiereException implements AvailabilityRequestException
{
	public static MSV3ClientException createAllItemsSameThrowable(
			@NonNull final Collection<ProductAndQuantity> items,
			@NonNull final Throwable throwable)
	{
		final ImmutableMap<ProductAndQuantity, Throwable> allItemsWithSameThrowable = //
				Maps.toMap(items, requestItem -> throwable);
		return new MSV3ClientException(allItemsWithSameThrowable);
	}

	public static MSV3ClientException createAllItemsSameFaultInfo(
			@NonNull final Collection<ProductAndQuantity> items,
			@NonNull final Msv3FaultInfo msv3FaultInfo)
	{
		final Msv3FaultException msv3FaultException = new Msv3FaultException(msv3FaultInfo);

		final ImmutableMap<ProductAndQuantity, Throwable> allItemsWithSameThrowable = //
				Maps.toMap(items, requestItem -> msv3FaultException);
		return new MSV3ClientException(allItemsWithSameThrowable);
	}

	private static final long serialVersionUID = -8058915938494697758L;

	@Getter
	private final Map<ProductAndQuantity, Throwable> requestItem2Exception;

	private MSV3ClientException(@NonNull final Map<ProductAndQuantity, Throwable> requestItem2Exception)
	{
		this.requestItem2Exception = ImmutableMap.copyOf(requestItem2Exception);
	}

	public static class Msv3FaultException extends RuntimeException
	{
		private static final long serialVersionUID = -8587023660085593406L;

		private final String message;
		private final String localizedMessage;

		@Getter
		private final String errorCode;

		private Msv3FaultException(@NonNull final Msv3FaultInfo msv3FaultInfo)
		{
			message = msv3FaultInfo.getTechnischerFehlertext();
			localizedMessage = msv3FaultInfo.getEndanwenderFehlertext();
			errorCode = msv3FaultInfo.getErrorCode();
		}

		@Override
		public String getMessage()
		{
			return message;
		}

		@Override
		public String getLocalizedMessage()
		{
			return localizedMessage;
		}

	}
}
