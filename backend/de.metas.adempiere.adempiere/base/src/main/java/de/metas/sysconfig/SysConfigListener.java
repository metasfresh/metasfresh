package de.metas.sysconfig;

@SuppressWarnings("unused")
public interface SysConfigListener
{
	default void assertValidSysConfigValue(String sysconfigName, String value) {}

	default void onSysConfigValueChanged(String sysconfigName, String newValue) {}
}
