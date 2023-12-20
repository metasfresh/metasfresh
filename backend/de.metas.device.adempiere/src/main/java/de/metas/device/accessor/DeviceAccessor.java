package de.metas.device.accessor;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.device.api.IDevice;
import de.metas.device.api.IDeviceRequest;
import de.metas.device.api.ISingleValueResponse;
import de.metas.device.api.hook.BeforeAcquireValueHook;
import de.metas.device.api.hook.RunParameters;
import de.metas.device.config.DeviceConfig;
import de.metas.i18n.ITranslatableString;
import de.metas.logging.LogManager;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.warehouse.WarehouseId;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
 */
@ToString
public final class DeviceAccessor
{
	private static final Logger logger = LogManager.getLogger(DeviceAccessor.class);
	
	@Getter
	private final DeviceId id;
	
	@Getter
	private final DeviceConfig deviceConfig;

	@Getter
	private final ITranslatableString displayName;
	private final IDevice device;
	private final IDeviceRequest<ISingleValueResponse> request;
	private final List<BeforeAcquireValueHook> beforeHooks;

	@Builder
	private DeviceAccessor(
			@NonNull final DeviceId id,
			@NonNull final DeviceConfig deviceConfig,
			@NonNull final ITranslatableString displayName,
			@NonNull final IDevice device,
			@NonNull final IDeviceRequest<ISingleValueResponse> request,
			@Nullable final List<BeforeAcquireValueHook> beforeHooks)
	{
		this.id = id;
		this.deviceConfig = deviceConfig;
		this.displayName = displayName;
		this.device = device;
		this.request = request;
		this.beforeHooks = Optional.ofNullable(beforeHooks).orElseGet(ImmutableList::of);
	}

	public boolean isAvailableForWarehouse(final WarehouseId warehouseId)
	{
		final ImmutableSet<WarehouseId> assignedWarehouseIds = deviceConfig.getAssignedWarehouseIds(); 
		return assignedWarehouseIds.isEmpty() || assignedWarehouseIds.contains(warehouseId);
	}

	public synchronized BigDecimal acquireValue()
	{
		logger.debug("This: {}, Device: {}; Request: {}", this, device, request);

		final ISingleValueResponse response = device.accessDevice(request);
		logger.debug("Device {}; Response: {}", device, response);

		return response.getSingleValue();
	}

	public synchronized void beforeAcquireValue(@NonNull final Map<String, List<String>> parameters)
	{
		final Map<String, List<String>> runParameters = Stream.concat(parameters.entrySet().stream(),
																	  getDeviceConfigParams().entrySet().stream())
				.collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, Map.Entry::getValue));

		for (final BeforeAcquireValueHook hook : beforeHooks)
		{
			hook.run(RunParameters.of(runParameters), device, request);
		}
	}

	public Optional<String> getConfigValue(@NonNull final String parameterName)
	{
		return deviceConfig.getDeviceConfigParamValue(parameterName);
	}

	@NonNull
	private Map<String, List<String>> getDeviceConfigParams()
	{
		return deviceConfig.getDeviceConfigParams()
				.entrySet()
				.stream()
				.collect(Collectors.toMap(
						Map.Entry::getKey,
						entry -> ImmutableList.of(entry.getValue())
				));
	}
}
