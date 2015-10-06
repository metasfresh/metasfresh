package de.metas.flatrate.process;

/*
 * #%L
 * de.metas.contracts
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


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.logging.Level;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.apache.commons.collections4.IteratorUtils;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.TrxRunnable;

import de.metas.flatrate.api.IFlatrateBL;
import de.metas.flatrate.model.I_C_Flatrate_Conditions;
import de.metas.flatrate.model.I_C_Flatrate_Term;

public class C_FlatrateTerm_Create_For_BPartners extends SvrProcess
{

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);

	private int p_flatrateconditionsID;

	private Timestamp p_startDate;

	private int p_adUserInChargeId;

	@Override
	protected void prepare()
	{
		final ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++)
		{
			final String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals(I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Conditions_ID))
			{
				p_flatrateconditionsID = ((BigDecimal)para[i].getParameter()).intValue();
			}
			else if (name.equals(I_C_Flatrate_Term.COLUMNNAME_AD_User_InCharge_ID))
			{
				p_adUserInChargeId = ((BigDecimal)para[i].getParameter()).intValue();
			}
			else if (name.equals(I_C_Flatrate_Term.COLUMNNAME_StartDate))
			{
				p_startDate = (Timestamp)para[i].getParameter();
			}
			else
			{
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
			}
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		final IQueryFilter<I_C_BPartner> selectedPartners = getProcessInfo().getQueryFilter();

// @formatter:off
// 		we also select partners that are neither Vendors nor Customers. We leave it to the business logic to decide if each partner can get a term or not	
//		final ICompositeQueryFilter<I_C_BPartner> customerOrVendorFilter = Services.get(IQueryBL.class).createCompositeQueryFilter(I_C_BPartner.class)
//				.setJoinOr()
//				.addEqualsFilter(I_C_BPartner.COLUMNNAME_IsCustomer, true)
//				.addEqualsFilter(I_C_BPartner.COLUMNNAME_IsVendor, true);
// @formatter:on

		final IQueryBuilder<I_C_BPartner> queryBuilder = queryBL.createQueryBuilder(I_C_BPartner.class);

		// ordering by value to make it easier for the user to browse the logging.
		final IQueryOrderBy orderBy = queryBuilder.orderBy().addColumn(I_C_BPartner.COLUMNNAME_Value).createQueryOrderBy();

		final Iterator<I_C_BPartner> it = queryBuilder
				.setContext(getCtx(), ITrx.TRXNAME_ThreadInherited)
				.filter(selectedPartners)
				// .filter(customerOrVendorFilter)
				.addOnlyActiveRecordsFilter()
				.create()
				.setOrderBy(orderBy)
				.setOption(IQuery.OPTION_GuaranteedIteratorRequired, true)
				.setOption(IQuery.OPTION_IteratorBufferSize, 500)
				.iterate(I_C_BPartner.class);

		final I_C_Flatrate_Conditions conditions = InterfaceWrapperHelper.create(getCtx(), p_flatrateconditionsID, I_C_Flatrate_Conditions.class, ITrx.TRXNAME_None);

		final I_AD_User userInCharge;
		if (p_adUserInChargeId > 0)
		{
			userInCharge = InterfaceWrapperHelper.create(getCtx(), p_adUserInChargeId, I_AD_User.class, get_TrxName());
		}
		else
		{
			userInCharge = null;
		}

		for (final I_C_BPartner bp : IteratorUtils.asIterable(it))
		{
			// create each term in its own transaction
			Services.get(ITrxManager.class).run(new TrxRunnable()
			{
				@Override
				public void run(String localTrxName) throws Exception
				{
					// no need to set 'localTrxName' to out bp, because we loaded the bp with ITrx.TRXNAME_ThreadInherited
					flatrateBL.createTerm(bp, conditions, p_startDate, userInCharge,
							C_FlatrateTerm_Create_For_BPartners.this // the loggable
							);
				}
			});
		}
		return "@Success@";
	}
}
