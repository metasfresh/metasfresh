package de.metas.edi.process;

import java.util.List;

import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;

import de.metas.edi.api.IDesadvBL;
import de.metas.esb.edi.model.I_EDI_DesadvLine_Pack;
import de.metas.process.JavaProcess;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2019 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class EDI_DesadvLine_Pack_PrintSSCCLabels extends JavaProcess
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IDesadvBL desadvBL = Services.get(IDesadvBL.class);

	@Override
	protected String doIt() throws Exception
	{
		final IQueryFilter<I_EDI_DesadvLine_Pack> selectedRecordsFilter = getProcessInfo()
				.getQueryFilterOrElse(ConstantQueryFilter.of(false));

		final List<Integer> list = queryBL
				.createQueryBuilder(I_EDI_DesadvLine_Pack.class)
				.filter(selectedRecordsFilter)
				.create()
				.listIds();

		desadvBL.printSSCC18_Labels(getCtx(), list);
		return MSG_OK;
	}
}
