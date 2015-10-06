package org.adempiere.db;

/*
 * #%L
 * de.metas.swat.base
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


import org.adempiere.util.Services;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public abstract class TrxTemplate {

	private final static Log logger = LogFactory.getLog(TrxTemplate.class);

	public void insertTrx(final String trxPrefix) {

		final IDBService trxService = Services.get(IDBService.class);
		final String trxName = trxService.createTrx(trxPrefix);

		try {
			doIt(trxName);
			trxService.commitTrx(trxName);

		} catch (RuntimeException e) {
			
			logger.warn("Caught '" + e.getClass().getSimpleName()
					+ ", doing rollBack. Msg:" + e.getMessage());
			
			trxService.rollBackTrx(trxName);
			throw e;
		} finally {
			trxService.closeTrx(trxName);
		}

	}

	protected abstract void doIt(final String trxName);
}
