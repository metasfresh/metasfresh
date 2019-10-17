package de.metas.acct.api;

import java.util.Properties;

import org.compiere.model.I_C_Currency_Acct;

import de.metas.util.ISingletonService;

/*
 * #%L
 * de.metas.acct.base
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

public interface ICurrencyAcctDAO extends ISingletonService
{

	/**
	 * Gets currency accounting record.
	 * 
	 * @param ctx
	 * @param C_Currency_ID
	 * @param C_AcctSchema_ID
	 * @return currency accounting record or null
	 */
	I_C_Currency_Acct get(Properties ctx, int C_Currency_ID, int C_AcctSchema_ID);

}
