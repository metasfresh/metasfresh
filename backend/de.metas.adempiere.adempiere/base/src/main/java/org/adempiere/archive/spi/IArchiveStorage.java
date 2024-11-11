package org.adempiere.archive.spi;

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

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Archive;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * Archive Storage (e.g. database, filesystem etc)
 *
 * @author tsa
 */
public interface IArchiveStorage
{
	default I_AD_Archive newArchive() {return newArchive(Env.getCtx());}

	default I_AD_Archive newArchive(final Properties ctx)
	{
		return InterfaceWrapperHelper.create(ctx, I_AD_Archive.class, ITrx.TRXNAME_ThreadInherited);
	}

	@Nullable
	byte[] getBinaryData(I_AD_Archive archive);

	@Nullable
	default InputStream getBinaryDataAsStream(final I_AD_Archive archive)
	{
		final byte[] inflatedData = getBinaryData(archive);
		if (inflatedData == null)
		{
			return null;
		}
		return new ByteArrayInputStream(inflatedData);
	}

	void setBinaryData(I_AD_Archive archive, byte[] data);
}
