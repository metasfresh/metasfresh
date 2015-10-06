package de.metas.hostkey.api;

/*
 * #%L
 * ADempiere ERP - Base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.adempiere.exceptions.AdempiereException;
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
	String getHostKey();

	/**
	 * Gets/creates host key
	 * 
	 * @param hostkeyStorage
	 * @throws AdempiereException if any error occur
	 */
	String getCreateHostKey(IHostKeyStorage hostkeyStorage);

	/**
	 * Generates a new unique HostKey.
	 * 
	 * @return newly generated host key
	 */
	String generateHostKey();
}
