package org.adempiere.archive.api;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.adempiere.archive.spi.IArchiveStorage;
import org.adempiere.service.ClientId;
import org.compiere.model.I_AD_Archive;

import java.util.Properties;

/**
 * Factory helper which creates instances of {@link IArchiveStorage} based on given context or given archive.
 *
 * @author tsa
 */
public interface IArchiveStorageFactory extends ISingletonService
{
	/**
	 * Default Archive Storage for context's tenant(AD_Client_ID).
	 */
	IArchiveStorage getArchiveStorage(final Properties ctx);

	/**
	 * Archive Storage used for given <code>archive</code>.
	 */
	IArchiveStorage getArchiveStorage(final I_AD_Archive archive);

	IArchiveStorage getArchiveStorage(@NonNull ClientId adClientId);
}
