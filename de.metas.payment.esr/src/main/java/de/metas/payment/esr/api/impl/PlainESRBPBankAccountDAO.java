package de.metas.payment.esr.api.impl;

/*
 * #%L
 * de.metas.payment.esr
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


import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.util.comparator.AccessorComparator;
import org.adempiere.util.comparator.ComparableComparator;
import org.compiere.model.I_AD_Org;
import de.metas.payment.esr.model.I_C_BP_BankAccount;
import de.metas.util.TypedAccessor;

public class PlainESRBPBankAccountDAO extends AbstractBPBankAccountDAO
{	
	private static final transient Logger logger = LogManager.getLogger(PlainESRBPBankAccountDAO.class);
	
	private final POJOLookupMap db = POJOLookupMap.get();

	public POJOLookupMap getDB()
	{
		return db;
	}

	@Override
	public List<I_C_BP_BankAccount> fetchOrgEsrAccounts(final I_AD_Org org)
	{
		final List<I_C_BP_BankAccount> result =  db.getRecords(I_C_BP_BankAccount.class, new IQueryFilter<I_C_BP_BankAccount>()
		{

			@Override
			public boolean accept(I_C_BP_BankAccount pojo)
			{
				if ( pojo == null )
				{
					logger.warn("The associated C_BPartner for org "+ org + " has no ESR account");
					return false;
				}
			
				if (!pojo.isEsrAccount())
				{
					return false;
				}
				
				if(pojo.getAD_Org_ID() != org.getAD_Org_ID())
				{
					return false;
				}

				if (!pojo.isActive())
				{
					return false;
				}
				
				// could the pojo be null?

				return true;
			}

		});
		
		Collections.sort(result, new AccessorComparator<I_C_BP_BankAccount, Boolean>(
				new ComparableComparator<Boolean>(),
				new TypedAccessor<Boolean>()
				{

					@Override
					public Boolean getValue(Object o)
					{
						return ((I_C_BP_BankAccount)o).isDefaultESR();
					}
				}));

		Collections.reverse(result);
		
		return result;
	}
}
