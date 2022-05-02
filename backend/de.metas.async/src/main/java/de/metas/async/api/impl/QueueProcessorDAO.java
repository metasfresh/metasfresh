/*
 * #%L
 * de.metas.async
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.async.api.impl;

import de.metas.async.model.I_C_Queue_Processor;
import de.metas.async.model.I_C_Queue_Processor_Assign;
import de.metas.async.processor.QueuePackageProcessorId;
import de.metas.async.processor.QueueProcessorId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class QueueProcessorDAO
{
	public static QueueProcessorDAO getInstance()
	{
		if (Adempiere.isUnitTestMode())
		{
			return new QueueProcessorDAO();
		}
		else
		{
			return SpringContextHolder.instance.getBean(QueueProcessorDAO.class);
		}
	}

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	public Optional<I_C_Queue_Processor> getById(@NonNull final QueueProcessorId processorId)
	{
		return queryBL.createQueryBuilder(I_C_Queue_Processor.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Queue_Processor.COLUMNNAME_C_Queue_Processor_ID, processorId)
				.create()
				.firstOnlyOptional(I_C_Queue_Processor.class);
	}

	@NonNull
	public Optional<QueueProcessorId> getByPackageProcessorId(@NonNull final QueuePackageProcessorId packageProcessorId)
	{
		return queryBL.createQueryBuilder(I_C_Queue_Processor_Assign.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Queue_Processor_Assign.COLUMNNAME_C_Queue_PackageProcessor_ID, packageProcessorId)
				.andCollect(I_C_Queue_Processor.COLUMNNAME_C_Queue_Processor_ID, I_C_Queue_Processor.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstIdOnlyOptional(QueueProcessorId::ofRepoIdOrNull);
	}
}
