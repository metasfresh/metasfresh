package de.metas.ui.web.devices.providers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.lang.reflect.Method;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.adempiere.exceptions.AdempiereException;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;

import de.metas.i18n.TranslatableStrings;
import de.metas.ui.web.devices.DeviceDescriptor;
import de.metas.ui.web.devices.DeviceDescriptorsList;

/*
 * #%L
 * metasfresh-webui-api
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

public class MethodDeviceDescriptorsProviderTest
{
	public static class TestObject
	{
		private Supplier<DeviceDescriptorsList> devicesSupplier;

		public DeviceDescriptorsList getDevicesPublicMethod()
		{
			return devicesSupplier.get();
		}

		public DeviceDescriptorsList getDevicesPrivateMethod()
		{
			return devicesSupplier.get();
		}

		public Object getDevicesInvalidReturnType()
		{
			throw new UnsupportedOperationException("shall not be invoked");
		}

		public DeviceDescriptorsList getDevicesInvalidArguments(int someArg)
		{
			throw new UnsupportedOperationException("shall not be invoked");
		}

	}

	private DeviceDescriptor createDeviceDescriptor(final String deviceId)
	{
		return DeviceDescriptor.builder()
				.deviceId(deviceId)
				.caption(TranslatableStrings.anyLanguage("caption_" + deviceId))
				.websocketEndpoint("websocketEndpoint_" + deviceId)
				.build();
	}

	private DeviceDescriptorsList createDeviceDescriptorsList(final String... deviceIds)
	{
		return DeviceDescriptorsList.ofList(
				Stream.of(deviceIds)
						.map(this::createDeviceDescriptor)
						.collect(ImmutableList.toImmutableList()));
	}

	@Test
	public void getDevicesPublicMethod() throws Exception
	{
		final Method method = TestObject.class.getDeclaredMethod("getDevicesPublicMethod");

		final TestObject obj = new TestObject();
		obj.devicesSupplier = () -> createDeviceDescriptorsList("1", "2", "3");

		final MethodDeviceDescriptorsProvider provider = MethodDeviceDescriptorsProvider.ofMethod(method, () -> obj);
		assertThat(provider.getDeviceDescriptors())
				.isEqualTo(createDeviceDescriptorsList("1", "2", "3"));
	}

	@Test
	public void getDevicesPublicMethodThrowingAdempiereException() throws Exception
	{
		final Method method = TestObject.class.getDeclaredMethod("getDevicesPublicMethod");

		final AdempiereException exception = new AdempiereException("DUMMY");
		final TestObject obj = new TestObject();
		obj.devicesSupplier = () -> {
			throw exception;
		};

		final MethodDeviceDescriptorsProvider provider = MethodDeviceDescriptorsProvider.ofMethod(method, () -> obj);

		assertThatThrownBy(() -> provider.getDeviceDescriptors())
				.isSameAs(exception);
	}

	@Test
	public void getDevicesPrivateMethod() throws Exception
	{
		final Method method = TestObject.class.getDeclaredMethod("getDevicesPrivateMethod");

		final TestObject obj = new TestObject();
		obj.devicesSupplier = () -> createDeviceDescriptorsList("1", "2", "3");

		final MethodDeviceDescriptorsProvider provider = MethodDeviceDescriptorsProvider.ofMethod(method, () -> obj);
		assertThat(provider.getDeviceDescriptors())
				.isEqualTo(createDeviceDescriptorsList("1", "2", "3"));
	}

	@Test
	public void getDevicesInvalidReturnType() throws Exception
	{
		final Method method = TestObject.class.getDeclaredMethod("getDevicesInvalidReturnType");

		assertThatThrownBy(() -> MethodDeviceDescriptorsProvider.ofMethod(method, Object::new))
				.hasMessageStartingWith("Method's return type shall be ");
	}

	@Test
	public void getDevicesInvalidArguments() throws Exception
	{
		final Method method = TestObject.class.getDeclaredMethod("getDevicesInvalidArguments", int.class);

		assertThatThrownBy(() -> MethodDeviceDescriptorsProvider.ofMethod(method, Object::new))
				.hasMessageStartingWith("Method shall have no arguments: ");
	}
}
