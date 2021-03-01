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

import de.metas.cache.CCache;
import de.metas.process.AdProcessId;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_PrintFormat;
import org.springframework.stereotype.Repository;

@Repository
public class PrintFormatRepository
{
	private final CCache<PrintFormatId, PrintFormat> cacheById = CCache.<PrintFormatId, PrintFormat>builder()
			.tableName(I_AD_PrintFormat.Table_Name)
			.build();

	public PrintFormat getById(@NonNull final PrintFormatId printFormatId)
	{
		return cacheById.getOrLoad(printFormatId, this::retrieveById);
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
