package de.metas.hostkey.spi.impl;

import org.compiere.util.Ini;

import de.metas.hostkey.spi.IHostKeyStorage;

/**
 * {@link IHostKeyStorage} implementations which stores the HostKey in {@link Ini}'s property file.
 *
 * @author tsa
 *
 */
public class IniBasedHostKeyStorage implements IHostKeyStorage
{
	private static final String PARAM_HostKey = "HostKey";

	@Override
	public void setHostKey(final String hostkey)
	{
		Ini.setProperty(PARAM_HostKey, hostkey);
		Ini.saveProperties();
	}

	@Override
	public String getHostKey()
	{
		return Ini.getProperty(PARAM_HostKey);
	}
}
