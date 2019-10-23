package org.adempiere.ad.trx.api;

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


import org.adempiere.ad.trx.exceptions.TrxNotFoundException;

/**
 * Specifies how we shall handle the case when no {@link ITrx} was found for a given transaction name (trxName).
 * 
 * @author tsa
 *
 */
public enum OnTrxMissingPolicy
{
	/**
	 * Create a new transaction in case transaction does not exist
	 */
	CreateNew,
	/**
	 * Throw {@link TrxNotFoundException} in case the transaction does not exist
	 */
	Fail,
	/**
	 * Return {@link ITrx#TRX_None} in case the transaction does not exist
	 */
	ReturnTrxNone,
}
