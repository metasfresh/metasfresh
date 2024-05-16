package de.metas.device.config;

import org.adempiere.mm.attributes.AttributeCode;

import java.util.Set;

@FunctionalInterface
public interface IDeviceRequestClassnamesSupplier
{
	Set<String> getDeviceRequestClassnames(final String deviceName, final AttributeCode attributeCode);
}
