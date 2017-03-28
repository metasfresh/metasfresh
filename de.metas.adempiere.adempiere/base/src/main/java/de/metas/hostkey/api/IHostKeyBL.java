package de.metas.hostkey.api;

import org.adempiere.util.ISingletonService;

import de.metas.hostkey.spi.IHostKeyStorage;

/**
 * Service used for manipulating HostKey
 * 
 * @author tsa
 * 
 */
public interface IHostKeyBL extends ISingletonService
{
	/**
	 * Sets the storage to be used when getting the hostkey
	 * 
	 * @param hostKeyStorage
	 */
	void setHostKeyStorage(IHostKeyStorage hostKeyStorage);

	/**
	 * Get HostKey from configured storage (see {@link #setHostKeyStorage(IHostKeyStorage)}).
	 * 
	 * @return
	 */
	String getCreateHostKey();

	/**
	 * Generates a new unique HostKey.
	 * 
	 * @return newly generated host key
	 */
	String generateHostKey();
}
