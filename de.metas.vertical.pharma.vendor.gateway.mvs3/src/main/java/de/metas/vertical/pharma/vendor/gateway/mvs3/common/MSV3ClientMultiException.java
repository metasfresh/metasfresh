package de.metas.vertical.pharma.vendor.gateway.mvs3.common;

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

public class MSV3ClientMultiException extends AdempiereException implements AvailabilityRequestException
{
	public static MSV3ClientMultiException createAllItemsSameThrowable(
			@NonNull final Collection<ProductAndQuantity> items,
			@NonNull final Throwable throwable)
	{
		final ImmutableMap<ProductAndQuantity, Throwable> allItemsWithSameThrowable = //
				Maps.toMap(items, requestItem -> throwable);

		return new MSV3ClientMultiException(allItemsWithSameThrowable);
	}

	public static MSV3ClientMultiException createAllItemsSameFaultInfo(
			@NonNull final Collection<ProductAndQuantity> items,
			@NonNull final Msv3FaultInfo msv3FaultInfo)
	{
		final Msv3ClientException msv3ClientException = //
				Msv3ClientException.createForFaultInfo(msv3FaultInfo);

		final ImmutableMap<ProductAndQuantity, Throwable> allItemsWithSameThrowable = //
				Maps.toMap(items, requestItem -> msv3ClientException);

		return new MSV3ClientMultiException(allItemsWithSameThrowable);
	}

	private static final long serialVersionUID = -8058915938494697758L;

	@Getter
	private final Map<ProductAndQuantity, Throwable> requestItem2Exception;

	private MSV3ClientMultiException(@NonNull final Map<ProductAndQuantity, Throwable> requestItem2Exception)
	{
		this.requestItem2Exception = ImmutableMap.copyOf(requestItem2Exception);
	}
}
