package de.metas.device.pool.dummy;

import java.util.List;
import java.util.Map;

import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.warehouse.WarehouseId;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.device.adempiere.IDevicesHubFactory;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import de.metas.util.lang.RepoIdAwares;

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

public abstract class DummyDevicesRestControllerTemplate
{
	protected abstract void assertLoggedIn();

	@GetMapping
	public Map<String, Object> getDummyScalesInfo()
	{
		assertLoggedIn();

		final ImmutableList<String> devicesInfo = DummyDeviceConfigPool.getAllDevices()
				.stream()
				.map(device -> device.toString())
				.collect(ImmutableList.toImmutableList());

		return ImmutableMap.<String, Object> builder()
				.put("config", getConfigInfo())
				.put("devices", devicesInfo)
				.build();
	}

	@GetMapping("/config")
	public Map<String, Object> configScales(
			@RequestParam(name = "enabled", required = true) final boolean enabled,
			@RequestParam(name = "responseMinValue", required = false, defaultValue = "-1") final int responseMinValue,
			@RequestParam(name = "responseMaxValue", required = false, defaultValue = "-1") final int responseMaxValue)
	{
		assertLoggedIn();

		setEnabled(enabled);

		if (responseMinValue > 0)
		{
			DummyDeviceConfigPool.setResponseMinValue(responseMinValue);
		}
		if (responseMaxValue > 0)
		{
			DummyDeviceConfigPool.setResponseMaxValue(responseMaxValue);
		}

		return getConfigInfo();
	}

	private void setEnabled(final boolean enabled)
	{
		DummyDeviceConfigPool.setEnabled(enabled);
		Services.get(IDevicesHubFactory.class).cacheReset();
	}

	private Map<String, Object> getConfigInfo()
	{
		return ImmutableMap.<String, Object> builder()
				.put("enabled", DummyDeviceConfigPool.isEnabled())
				.put("responseMinValue", DummyDeviceConfigPool.getResponseMinValue().doubleValue())
				.put("responseMaxValue", DummyDeviceConfigPool.getResponseMaxValue().doubleValue())
				.build();
	}

	@GetMapping("/addOrUpdate")
	public void addScale(
			@RequestParam("deviceName") final String deviceName,
			@RequestParam("attributes") final String attributesStr,
			@RequestParam(name = "onlyForWarehouseIds", required = false) final String onlyForWarehouseIdsStr)
	{
		assertLoggedIn();

		final List<AttributeCode> attributes = CollectionUtils.ofCommaSeparatedList(attributesStr, AttributeCode::ofString);
		final List<WarehouseId> onlyForWarehouseIds = RepoIdAwares.ofCommaSeparatedList(onlyForWarehouseIdsStr, WarehouseId.class);

		DummyDeviceConfigPool.addDevice(DummyDeviceAddRequest.builder()
				.deviceName(deviceName)
				.assignedAttributeCodes(attributes)
				.onlyWarehouseIds(onlyForWarehouseIds)
				.build());

		setEnabled(true);
	}

	@GetMapping("/removeDevice")
	public void addScale(
			@RequestParam("deviceName") final String deviceName)
	{
		assertLoggedIn();

		DummyDeviceConfigPool.removeDeviceByName(deviceName);
	}
}
