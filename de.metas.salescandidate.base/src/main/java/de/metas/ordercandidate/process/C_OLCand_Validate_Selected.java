package de.metas.ordercandidate.process;

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


import java.util.Iterator;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.apache.commons.collections4.IteratorUtils;

import de.metas.i18n.IMsgBL;
import de.metas.ordercandidate.api.IOLCandValidatorBL;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.process.JavaProcess;

public class C_OLCand_Validate_Selected extends JavaProcess
{

	//
	// services
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);
	final IOLCandValidatorBL olCandValdiatorBL = Services.get(IOLCandValidatorBL.class);

	@Override
	protected void prepare()
	{
		// do nothing
	}

	@Override
	protected String doIt() throws Exception
	{
		final IQueryFilter<I_C_OLCand> queryFilter = getProcessInfo().getQueryFilter();

		final IQueryBuilder<I_C_OLCand> queryBuilder = queryBL.createQueryBuilder(I_C_OLCand.class, getCtx(), get_TrxName())
				.addEqualsFilter(I_C_OLCand.COLUMNNAME_Processed, false) // already processed records shall not be validated
				.filter(queryFilter);

		final Iterator<I_C_OLCand> selectedCands = queryBuilder
				.create()
				.iterate(I_C_OLCand.class); // working with a iterator, because the there might be *a lot* of C_OLCands, and the issue-solver that we use in the endcustomer.project also iterates.

		olCandValdiatorBL.setValidationProcessInProgress(true); // avoid the InterfaceWrapperHelper.save to trigger another validation from a MV.
		try
		{
			int candidatesWithError = 0;
			for (final I_C_OLCand olCand : IteratorUtils.asIterable(selectedCands))
			{
				olCandValdiatorBL.validate(olCand);

				if (olCand.isError())
				{
					candidatesWithError++;
				}
				InterfaceWrapperHelper.save(olCand);
			}
			return msgBL.getMsg(getCtx(), IOLCandValidatorBL.MSG_ERRORS_FOUND, new Object[] { candidatesWithError });
		}
		finally
		{
			olCandValdiatorBL.setValidationProcessInProgress(false);
		}
	}

}
