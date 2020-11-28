package de.metas.purchasecandidate.availability;

import java.util.List;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.collect.ImmutableList;

import de.metas.i18n.TranslatableStrings;
import de.metas.vendor.gateway.api.availability.TrackingId;
import groovy.transform.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

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

@EqualsAndHashCode(callSuper = false)
@SuppressWarnings("serial")
public class AvailabilityException extends AdempiereException
{
	public static AvailabilityException cast(final Throwable throwable)
	{
		return (AvailabilityException)throwable;
	}

	@Getter
	private final List<ErrorItem> errorItems;

	public AvailabilityException(@NonNull final List<ErrorItem> errorItems)
	{
		super(TranslatableStrings.empty());
		this.errorItems = ImmutableList.copyOf(errorItems);
	}

	@lombok.Value
	@lombok.Builder
	public static class ErrorItem
	{
		@NonNull
		TrackingId trackingId;
		@NonNull
		Throwable error;
	}
}
