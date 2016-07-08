package de.metas.acct;

import java.util.Iterator;
import java.util.Properties;

import org.adempiere.util.lang.IAutoCloseable;

import de.metas.acct.model.I_Fact_Acct_Log;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * An iterable and closeable stream of {@link I_Fact_Acct_Log}s.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IFactAcctLogIterable extends Iterable<I_Fact_Acct_Log>, IAutoCloseable
{
	Properties getCtx();

	String getProcessingTag();

	/**
	 * Delete all logs from this stream.
	 */
	void deleteAll();

	/**
	 * Release (i.e. un-tag) all logs from this stream.
	 */
	@Override
	void close();

	@Override
	Iterator<I_Fact_Acct_Log> iterator();
}
