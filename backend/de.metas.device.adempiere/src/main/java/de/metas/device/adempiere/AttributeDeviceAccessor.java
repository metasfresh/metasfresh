package de.metas.device.adempiere;

import java.util.Set;

import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.warehouse.WarehouseId;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableSet;

import de.metas.device.api.IDevice;
import de.metas.device.api.IDeviceRequest;
import de.metas.device.api.ISingleValueResponse;
import de.metas.i18n.ITranslatableString;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * de.metas.device.adempiere
 * %%
 * Copyright (C) 2020 metas GmbH
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

/**
 * Facade used to access a given preconfigured device using a preconfigured {@link IDeviceRequest}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@SuppressWarnings("rawtypes")
@ToString
public final class AttributeDeviceAccessor
{
	private static final Logger logger = LogManager.getLogger(AttributeDeviceAccessor.class);

	@Getter
	private final ITranslatableString displayName;
	private final IDevice device;
	private final ImmutableSet<WarehouseId> assignedWarehouseIds;
	private final IDeviceRequest<ISingleValueResponse> request;

	@Getter
	private final String publicId;

	@Builder
	private AttributeDeviceAccessor(
			@NonNull final ITranslatableString displayName,
			@NonNull final IDevice device,
			@NonNull final String deviceName,
			@NonNull final AttributeCode attributeCode,
			@NonNull final Set<WarehouseId> assignedWarehouseIds,
			@NonNull final IDeviceRequest<ISingleValueResponse> request)
	{
		Check.assumeNotEmpty(deviceName, "deviceName is not empty");

		this.displayName = displayName;
		this.device = device;
		this.assignedWarehouseIds = ImmutableSet.copyOf(assignedWarehouseIds);
		this.request = request;

		publicId = deviceName + "-" + attributeCode.getCode() + "-" + request.getClass().getSimpleName();
	}

	public boolean isAvailableForWarehouse(final WarehouseId warehouseId)
	{
		if (assignedWarehouseIds.isEmpty())
		{
			return true;
		}

		return assignedWarehouseIds.contains(warehouseId);
	}

	public synchronized Object acquireValue()
	{
		logger.debug("This: {}, Device: {}; Request: {}", this, device, request);

		final ISingleValueResponse response = device.accessDevice(request);
		logger.debug("Device {}; Response: {}", device, response);

		return response.getSingleValue();
	}
}
