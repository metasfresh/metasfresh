package de.metas.vertical.pharma.vendor.gateway.msv3.common;

import java.util.Collection;
import java.util.Map;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.i18n.TranslatableStrings;
import de.metas.vendor.gateway.api.availability.AvailabilityRequestException;
import de.metas.vendor.gateway.api.availability.AvailabilityRequestItem;
import de.metas.vertical.pharma.msv3.protocol.types.FaultInfo;
import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-pharma.vendor.gateway.msv3
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

public class Msv3ClientMultiException extends AdempiereException implements AvailabilityRequestException
{
	public static Msv3ClientMultiException createAllItemsSameThrowable(
			@NonNull final Collection<AvailabilityRequestItem> items,
			@NonNull final Throwable throwable)
	{
		final Map<AvailabilityRequestItem, Throwable> allItemsWithSameThrowable = Maps.toMap(items, requestItem -> throwable);

		return new Msv3ClientMultiException(allItemsWithSameThrowable, throwable);
	}

	public static Msv3ClientMultiException createAllItemsSameFaultInfo(
			@NonNull final Collection<AvailabilityRequestItem> items,
			@NonNull final FaultInfo msv3FaultInfo)
	{
		final Msv3ClientException msv3ClientException = Msv3ClientException.builder()
				.msv3FaultInfo(msv3FaultInfo)
				.build();

		final Map<AvailabilityRequestItem, Throwable> allItemsWithSameThrowable = Maps.toMap(items, requestItem -> msv3ClientException);

		return new Msv3ClientMultiException(allItemsWithSameThrowable, msv3ClientException);
	}

	private static final long serialVersionUID = -8058915938494697758L;

	@Getter
	private final Map<AvailabilityRequestItem, Throwable> requestItem2Exception;

	private Msv3ClientMultiException(@NonNull final Map<AvailabilityRequestItem, Throwable> requestItem2Exception, final Throwable cause)
	{
		super(TranslatableStrings.empty(), cause);
		this.requestItem2Exception = ImmutableMap.copyOf(requestItem2Exception);
	}
}
