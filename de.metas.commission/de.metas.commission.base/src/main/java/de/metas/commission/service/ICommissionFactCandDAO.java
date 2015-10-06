package de.metas.commission.service;

/*
 * #%L
 * de.metas.commission.base
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


import java.util.Properties;

import org.adempiere.util.ISingletonService;
import org.compiere.model.PO;

import de.metas.commission.model.I_C_AdvCommissionFactCand;
import de.metas.commission.model.MCAdvCommissionFactCand;

public interface ICommissionFactCandDAO extends ISingletonService
{

	/**
	 * Returns the commission fact candidate with the lowest DateAcct and the lowest SeqNo value and <code>IsSubsequentProcessingDone='N' AND IsError='N'</code>. If there is no such fact, it returns
	 * <code>null</code>.
	 * 
	 * @return
	 */
	MCAdvCommissionFactCand retrieveNext(Properties ctx, String trxName);

	PO retrievePOFromDB(I_C_AdvCommissionFactCand cand);
}
