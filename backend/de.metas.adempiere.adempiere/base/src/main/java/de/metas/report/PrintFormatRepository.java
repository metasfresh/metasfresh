/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.report;

import com.google.common.collect.ImmutableList;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_PrintFormat;
import org.springframework.stereotype.Repository;

import de.metas.cache.CCache;
import de.metas.process.AdProcessId;
import lombok.NonNull;

@Repository
public class PrintFormatRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<PrintFormatId, PrintFormat> cacheById = CCache.<PrintFormatId, PrintFormat> builder()
			.tableName(I_AD_PrintFormat.Table_Name)
			.build();

	public PrintFormat getById(@NonNull final PrintFormatId printFormatId)
	{
		return cacheById.getOrLoad(printFormatId, this::retrieveById);
	}

	/**
	 * @return the ID of the single active {@code AD_PrintFormat} with the given name; fails if there is none or more than one.
	 */
	@NonNull
	public PrintFormatId getIdByName(@NonNull final String name)
	{
		final ImmutableList<PrintFormatId> printFormatIds = queryBL.createQueryBuilderOutOfTrx(I_AD_PrintFormat.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_PrintFormat.COLUMNNAME_Name, name)
				.create()
				.listIds(PrintFormatId::ofRepoId);
		if (printFormatIds.size() != 1)
		{
			throw new AdempiereException("Expected exactly one active AD_PrintFormat with Name=" + name + " but found " + printFormatIds.size())
					.appendParametersToMessage()
					.setParameter("printFormatIds", printFormatIds);
		}
		return printFormatIds.get(0);
	}

	private PrintFormat retrieveById(@NonNull final PrintFormatId printFormatId)
	{
		final I_AD_PrintFormat record = InterfaceWrapperHelper.loadOutOfTrx(printFormatId, I_AD_PrintFormat.class);
		return PrintFormat.builder()
				.id(printFormatId)
				.reportProcessId(AdProcessId.ofRepoIdOrNull(record.getJasperProcess_ID()))
				.build();
	}
}
