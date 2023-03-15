package de.metas.device.config;

@FunctionalInterface
public interface IDeviceParameterValueSupplier
{
	String getDeviceParamValue(final String deviceName, final String parameterName, final String defaultValue);
}
