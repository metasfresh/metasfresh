package de.metas.printing.api.impl;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryOrderBy;

import de.metas.printing.api.IPrintJobDAO;
import de.metas.printing.model.I_C_Print_Job_Instructions;
import de.metas.printing.model.I_C_Print_Job_Instructions_v;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.printing.base
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

public class PrintJobDAO implements IPrintJobDAO
{

	@Override
	public I_C_Print_Job_Instructions_v retrieveC_Print_Job_Instructions_Info(final I_C_Print_Job_Instructions pji)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryOrderBy orderBy = Services.get(IQueryBL.class).createQueryOrderByBuilder(I_C_Print_Job_Instructions_v.class)
				.addColumn(I_C_Print_Job_Instructions_v.COLUMNNAME_C_Print_Job_Instructions_ID)
				.createQueryOrderBy();

		return queryBL.createQueryBuilder(I_C_Print_Job_Instructions_v.class, pji)
				.addEqualsFilter(I_C_Print_Job_Instructions_v.COLUMNNAME_C_Print_Job_Instructions_ID, pji.getC_Print_Job_Instructions_ID())
				.create()
				.setOrderBy(orderBy)

				.first(I_C_Print_Job_Instructions_v.class); // there should not be more than one

	}

}
