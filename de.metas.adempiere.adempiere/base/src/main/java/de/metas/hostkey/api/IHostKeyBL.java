package de.metas.hostkey.api;

import java.util.function.Supplier;

import de.metas.hostkey.spi.IHostKeyStorage;
import de.metas.util.ISingletonService;

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
	void setHostKeyStorage(Supplier<IHostKeyStorage> hostKeyStorage);

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
