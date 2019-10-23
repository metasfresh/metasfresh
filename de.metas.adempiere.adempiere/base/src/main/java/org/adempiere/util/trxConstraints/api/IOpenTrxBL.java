package org.adempiere.util.trxConstraints.api;

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
import org.adempiere.ad.trx.api.ITrxSavepoint;
import org.compiere.util.Trx;

import de.metas.util.ISingletonService;

/**
 * Service contains methods to be called from {@link Trx} on certain events to check against {@link ITrxConstraints}. Implementers of this service are responsible for the enforcement of the
 * transaction constraints
 * 
 * @see org.compiere.util.DB#getConstraints()
 * @see ITrxConstraints
 */
public interface IOpenTrxBL extends ISingletonService
{
	void onCommit(ITrx trx);

	void onClose(ITrx trx);

	void onNewTrx(ITrx trx);

	void onRollback(ITrx trx);

	void onSetSavepoint(ITrx trx, ITrxSavepoint savepoint);

	void onReleaseSavepoint(ITrx trx, ITrxSavepoint savepoint);

	/**
	 * If Trx with the the given trxName hasn't been closed yet, this method returns the stack trace of the trx creation or last commit/rollback.
	 */
	String getCreationStackTrace(String trxName);

	void onTimeOutChange(ITrx trx);
}
