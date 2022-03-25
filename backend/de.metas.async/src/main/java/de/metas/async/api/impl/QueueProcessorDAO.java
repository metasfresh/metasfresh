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

import de.metas.async.api.IQueueDAO;
import de.metas.async.model.I_C_Queue_PackageProcessor;
import de.metas.async.model.I_C_Queue_Processor;
import de.metas.async.model.I_C_Queue_Processor_Assign;
import de.metas.async.processor.QueuePackageProcessorId;
import de.metas.async.processor.QueueProcessorId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
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
	private final IQueueDAO queueDAO = Services.get(IQueueDAO.class);

	@NonNull
	public Optional<QueueProcessorId> getProcessorForPackage(@NonNull final I_C_Queue_PackageProcessor packageProcessor)
	{
		final Optional<QueueProcessorId> queueProcessorId = queryBL.createQueryBuilder(I_C_Queue_Processor_Assign.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Queue_PackageProcessor.COLUMNNAME_C_Queue_PackageProcessor_ID, packageProcessor.getC_Queue_PackageProcessor_ID())
				.andCollect(I_C_Queue_Processor_Assign.COLUMN_C_Queue_Processor_ID)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnlyOptional(I_C_Queue_Processor.class)
				.map(I_C_Queue_Processor::getC_Queue_Processor_ID)
				.map(QueueProcessorId::ofRepoId);

		if (queueProcessorId.isPresent())
		{
			return queueProcessorId;
		}

		if (queueDAO.isAutocreateWorkpackageProcessorRecordForClassname())
		{
			return Optional.of(autoGenerateQueueProcessorForPackage(packageProcessor));
		}

		return Optional.empty();
	}

	@NonNull
	public I_C_Queue_PackageProcessor getPackageProcessor(@NonNull final QueuePackageProcessorId queuePackageProcessorId)
	{
		final I_C_Queue_PackageProcessor packageProcessor = InterfaceWrapperHelper.load(queuePackageProcessorId, I_C_Queue_PackageProcessor.class);

		Check.assumeNotNull(packageProcessor, "There must be a C_Queue_PackageProcessor record for QueuePackageProcessorId: {}", queuePackageProcessorId);

		return packageProcessor;
	}

	/**
	 * Important! The is a test-convenience method and it should not be used in other purposes!
	 * It auto generates {@link I_C_Queue_Processor} & {@link I_C_Queue_Processor_Assign} for the given {@link I_C_Queue_PackageProcessor}
	 * for easy testing.
	 */
	@NonNull
	private QueueProcessorId autoGenerateQueueProcessorForPackage(@NonNull final I_C_Queue_PackageProcessor packageProcessor)
	{
		Adempiere.assertUnitTestMode();

		final I_C_Queue_Processor queueProcessor = InterfaceWrapperHelper.newInstance(I_C_Queue_Processor.class);
		queueProcessor.setName("Auto-generated for " + packageProcessor.getClass().getCanonicalName());
		queueProcessor.setKeepAliveTimeMillis(1000);
		queueProcessor.setIsActive(true);
		queueProcessor.setPoolSize(1);

		InterfaceWrapperHelper.saveRecord(queueProcessor);

		final I_C_Queue_Processor_Assign assignPackageProcessor = InterfaceWrapperHelper.newInstance(I_C_Queue_Processor_Assign.class);
		assignPackageProcessor.setC_Queue_PackageProcessor_ID(packageProcessor.getC_Queue_PackageProcessor_ID());
		assignPackageProcessor.setC_Queue_Processor_ID(queueProcessor.getC_Queue_Processor_ID());
		assignPackageProcessor.setIsActive(true);

		InterfaceWrapperHelper.saveRecord(assignPackageProcessor);

		return QueueProcessorId.ofRepoId(queueProcessor.getC_Queue_Processor_ID());
	}
}
