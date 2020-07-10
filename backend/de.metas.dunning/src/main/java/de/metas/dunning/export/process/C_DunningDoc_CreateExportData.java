package de.metas.dunning.export.process;

import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.compiere.SpringContextHolder;

import com.google.common.collect.ImmutableList;

import de.metas.dunning.DunningDocId;
import de.metas.dunning.export.DunningExportService;
import de.metas.dunning.model.I_C_DunningDoc;
import de.metas.process.JavaProcess;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.dunning
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class C_DunningDoc_CreateExportData extends JavaProcess
{
	private final transient DunningExportService dunningExportService = SpringContextHolder.instance.getBean(DunningExportService.class);

	@Override
	protected String doIt() throws Exception
	{
		final IQueryFilter<I_C_DunningDoc> queryFilter = getProcessInfo()
				.getQueryFilterOrElse(ConstantQueryFilter.of(false));

		final ImmutableList<DunningDocId> dunningDocIdsToExport = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_DunningDoc.class)
				.filter(queryFilter)
				.create()
				.listIds()
				.stream()
				.map(DunningDocId::ofRepoId)
				.collect(ImmutableList.toImmutableList());

		dunningExportService.exportDunnings(dunningDocIdsToExport);

		return MSG_OK;
	}

}
