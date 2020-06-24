package de.metas.device.adempiere;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;

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

public class AttributesDevicesHubTest
{
	@Nested
	public class extractDeviceDisplayNameCommonPrefix
	{
		@Test
		public void standardCases()
		{
			Assertions.assertEquals("", AttributesDevicesHub.extractDeviceDisplayNameCommonPrefix(ImmutableList.of("mettler1")));
			Assertions.assertEquals("mettler", AttributesDevicesHub.extractDeviceDisplayNameCommonPrefix(ImmutableList.of("mettler1", "mettler2")));
			Assertions.assertEquals("mettler", AttributesDevicesHub.extractDeviceDisplayNameCommonPrefix(ImmutableList.of("mettler1", "mettler2", "mettler3")));
			Assertions.assertEquals("", AttributesDevicesHub.extractDeviceDisplayNameCommonPrefix(ImmutableList.of("1mettler", "2mettler", "3mettler")));
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
				assertThatThrownBy(() -> AttributesDevicesHub.createDeviceDisplayName("", null))
						.hasMessageContaining("deviceName");
			}
		}

		@Nested
		public class empty_DeviceName
		{
			@Test
			public void empty_deviceDisplayNameCommonPrefix()
			{
				assertThatThrownBy(() -> AttributesDevicesHub.createDeviceDisplayName("", ""))
						.hasMessageContaining("deviceName is not empty");
			}
		}

		@Test
		public void deviceName_sameAs_deviceDisplayNameCommonPrefix()
		{
			Assertions.assertEquals("mettler", AttributesDevicesHub.createDeviceDisplayName("mettler", "mettler"));
		}

		@Test
		public void standardCases()
		{
			Assertions.assertEquals("m", AttributesDevicesHub.createDeviceDisplayName("", "mettler1"));
			Assertions.assertEquals("m", AttributesDevicesHub.createDeviceDisplayName(null, "mettler1"));
			Assertions.assertEquals("mettler1", AttributesDevicesHub.createDeviceDisplayName("mettler", "mettler1"));
			Assertions.assertEquals("mettler1", AttributesDevicesHub.createDeviceDisplayName("mettler", "mettler11111"));
		}
	}
}
