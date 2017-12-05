package de.metas.handlingunits.trace.interceptor;

import java.util.List;

import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.mmovement.api.IMovementDAO;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.model.I_M_MovementLine;
import org.compiere.model.ModelValidator;

import de.metas.handlingunits.trace.HUTraceEventsService;
import de.metas.interfaces.I_M_Movement;
import lombok.NonNull;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2017 metas GmbH
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
@Interceptor(I_M_Movement.class)
/* package */ class M_Movement
{

	@DocValidate(timings = {
			ModelValidator.TIMING_AFTER_CLOSE,
			ModelValidator.TIMING_AFTER_COMPLETE,
			ModelValidator.TIMING_AFTER_REACTIVATE,
			ModelValidator.TIMING_AFTER_REVERSEACCRUAL,
			ModelValidator.TIMING_AFTER_REVERSECORRECT,
			ModelValidator.TIMING_AFTER_UNCLOSE,
			ModelValidator.TIMING_AFTER_VOID
	}, afterCommit = true)
	public void addTraceEvent(@NonNull final I_M_Movement movement)
	{
		Services.get(ITrxManager.class).run(() -> addTraceEvent0(movement));
	}

	private void addTraceEvent0(@NonNull final I_M_Movement movement)
	{
		final List<I_M_MovementLine> movementLines = Services.get(IMovementDAO.class).retrieveLines(movement);

		final HUTraceEventsService huTraceEventsService = Adempiere.getBean(HUTraceEventsService.class);
		huTraceEventsService.createAndAddFor(movement, movementLines);
	}
}
