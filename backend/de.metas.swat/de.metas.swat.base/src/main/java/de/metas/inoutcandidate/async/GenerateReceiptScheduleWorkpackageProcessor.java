/*
 * #%L
 * de.metas.swat.base
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

package de.metas.inoutcandidate.async;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.async.api.IQueueDAO;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.inoutcandidate.api.IReceiptScheduleProducerFactory;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.spi.IReceiptScheduleProducer;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;

import java.util.Collections;
import java.util.List;

/**
 * Process given models and creates {@link I_M_ReceiptSchedule} records.
 *
 * Input: models (I_C_Order)
 *
 * Output: {@link I_M_ReceiptSchedule}s
 */
public class GenerateReceiptScheduleWorkpackageProcessor implements IWorkpackageProcessor
{
	@Override
	public Result processWorkPackage(@NonNull final I_C_Queue_WorkPackage workpackage, final String localTrxName)
	{
		final IQueueDAO queueDAO = Services.get(IQueueDAO.class);

		// maybe the underlying order line was deleted meanwhile, after the order was reactivated. Using retrieveItemsSkipMissing() because we don't need to make a fuss about that.
		final List<Object> models = queueDAO.retrieveAllItemsSkipMissing(workpackage, Object.class);
		Loggables.addLog("Retrieved {} items for this work package", models.size());

		for (final Object model : models)
		{
			final String tableName = InterfaceWrapperHelper.getModelTableName(model);

			final IReceiptScheduleProducerFactory receiptScheduleProducerFactory = Services.get(IReceiptScheduleProducerFactory.class);
			final boolean async = false;
			final IReceiptScheduleProducer producer = receiptScheduleProducerFactory.createProducer(tableName, async);
			final List<I_M_ReceiptSchedule> previousSchedules = Collections.emptyList();
			producer.createOrUpdateReceiptSchedules(model, previousSchedules);
		}

		return Result.SUCCESS;
	}
}
