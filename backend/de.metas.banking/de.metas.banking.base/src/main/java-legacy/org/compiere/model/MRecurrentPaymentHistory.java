package org.compiere.model;

/*
 * #%L
 * de.metas.banking.base
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


import java.sql.ResultSet;
import java.util.Properties;

import de.metas.banking.model.X_C_RecurrentPaymentHistory;

public class MRecurrentPaymentHistory extends X_C_RecurrentPaymentHistory {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3359001266520811764L;

	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param C_RecurrentPaymentHistory_ID id
	 *	@param trxName transaction
	 */	
	public MRecurrentPaymentHistory (Properties ctx, int C_RecurrentPaymentHistory_ID, String trxName) {
		super (ctx, C_RecurrentPaymentHistory_ID, trxName);
	}	//	MRecurrentPayment

	/**
	 * 	Load Constructor
	 * 	@param ctx Current context
	 * 	@param rs result set
	 *	@param trxName transaction
	 */
	public MRecurrentPaymentHistory(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}	//	MRecurrentPayment
}
