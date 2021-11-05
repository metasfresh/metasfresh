/*
 * #%L
 * de.metas.salescandidate.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.ordercandidate.api.async;

import ch.qos.logback.classic.Level;
import de.metas.async.AsyncBatchId;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.logging.LogManager;
import de.metas.ordercandidate.api.IOLCandBL;
import de.metas.ordercandidate.api.OLCandProcessorDescriptor;
import de.metas.ordercandidate.api.OLCandProcessorRepository;
import de.metas.ordercandidate.model.I_C_OLCandAggAndOrder;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.slf4j.Logger;

import static de.metas.async.Async_Constants.C_OlCandProcessor_ID_Default;

public class C_OLCandToOrderWorkpackageProcessor extends WorkpackageProcessorAdapter
{
	public static final String OLCandProcessor_ID = I_C_OLCandAggAndOrder.COLUMNNAME_C_OLCandProcessor_ID;

	private final static Logger logger = LogManager.getLogger(C_OLCandToOrderWorkpackageProcessor.class);

	private final IOLCandBL olCandBL = Services.get(IOLCandBL.class);

	private final OLCandProcessorRepository olCandProcessorRepo = SpringContextHolder.instance.getBean(OLCandProcessorRepository.class);

	@Override
	public Result processWorkPackage(@NonNull final I_C_Queue_WorkPackage workPackage, @NonNull final String localTrxName)
	{
		final int olCandProcessorId = getParameters().getParameterAsInt(OLCandProcessor_ID, C_OlCandProcessor_ID_Default);

		final OLCandProcessorDescriptor olCandProcessorDescriptor = olCandProcessorRepo.getById(olCandProcessorId);

		try
		{
			olCandBL.process(olCandProcessorDescriptor, AsyncBatchId.ofRepoIdOrNull(workPackage.getC_Async_Batch_ID()));
		}
		catch (final Exception ex)
		{
			Loggables.withLogger(logger, Level.ERROR).addLog("@Error@: " + ex.getLocalizedMessage());
			Loggables.withLogger(logger, Level.ERROR).addLog("@Rollback@");
			throw AdempiereException.wrapIfNeeded(ex);
		}

		return Result.SUCCESS;
	}
}
