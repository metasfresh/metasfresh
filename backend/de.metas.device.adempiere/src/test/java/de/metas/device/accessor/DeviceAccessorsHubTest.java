package de.metas.device.accessor;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

/*
 * #%L
 * de.metas.device.adempiere
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class DeviceAccessorsHubTest
{
	@Nested
	public class extractDeviceDisplayNameCommonPrefix
	{
		@Test
		public void standardCases()
		{
			Assertions.assertEquals("", DeviceAccessorsHub.extractDeviceDisplayNameCommonPrefix(ImmutableList.of("mettler1")));
			Assertions.assertEquals("mettler", DeviceAccessorsHub.extractDeviceDisplayNameCommonPrefix(ImmutableList.of("mettler1", "mettler2")));
			Assertions.assertEquals("mettler", DeviceAccessorsHub.extractDeviceDisplayNameCommonPrefix(ImmutableList.of("mettler1", "mettler2", "mettler3")));
			Assertions.assertEquals("", DeviceAccessorsHub.extractDeviceDisplayNameCommonPrefix(ImmutableList.of("1mettler", "2mettler", "3mettler")));
		}
	}

	@Nested
	public class createDeviceDisplayName
	{
		@Nested
		public class null_DeviceName
		{
			@Test
			public void emptyDeviceDisplayNameCommonPrefix()
			{
				//noinspection ConstantConditions
				assertThatThrownBy(() -> DeviceAccessorsHub.createDeviceDisplayName("", null))
						.hasMessageContaining("deviceName");
			}
		}

		@Nested
		public class empty_DeviceName
		{
			@Test
			public void empty_deviceDisplayNameCommonPrefix()
			{
				assertThatThrownBy(() -> DeviceAccessorsHub.createDeviceDisplayName("", ""))
						.hasMessageContaining("deviceName is not empty");
			}
		}

		@Test
		public void deviceName_sameAs_deviceDisplayNameCommonPrefix()
		{
			Assertions.assertEquals("mettler", DeviceAccessorsHub.createDeviceDisplayName("mettler", "mettler"));
		}

		@Test
		public void standardCases()
		{
			Assertions.assertEquals("m", DeviceAccessorsHub.createDeviceDisplayName("", "mettler1"));
			Assertions.assertEquals("m", DeviceAccessorsHub.createDeviceDisplayName(null, "mettler1"));
			Assertions.assertEquals("mettler1", DeviceAccessorsHub.createDeviceDisplayName("mettler", "mettler1"));
			Assertions.assertEquals("mettler1", DeviceAccessorsHub.createDeviceDisplayName("mettler", "mettler11111"));
		}
	}
}
