package de.metas.fresh.mrp_productinfo.model.validator;

/*
 * #%L
 * de.metas.edi
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.List;

import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.ModelValidator;

import de.metas.fresh.mrp_productinfo.async.spi.impl.UpdateMRPProductInfoTableWorkPackageProcessor;
import de.metas.inout.IInOutDAO;
import de.metas.util.Services;

@Interceptor(I_M_InOut.class)
public class M_InOut
{
	public static final M_InOut INSTANCE = new M_InOut();

	private M_InOut()
	{
	}

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_COMPLETE,
			ModelValidator.TIMING_BEFORE_CLOSE,
			ModelValidator.TIMING_BEFORE_REACTIVATE,
			// yes, the following won't actually occur, but still, be on the safe side
			ModelValidator.TIMING_BEFORE_VOID,
			ModelValidator.TIMING_BEFORE_REVERSEACCRUAL,
			ModelValidator.TIMING_BEFORE_REVERSECORRECT })
	public void enqueueMaterialTransaction(final I_M_InOut inOut)
	{
		// note that we need to evaluate both incoming and outgoing inouts
		final List<I_M_InOutLine> iols = Services.get(IInOutDAO.class).retrieveLines(inOut);
		for (final I_M_InOutLine iol : iols)
		{
			UpdateMRPProductInfoTableWorkPackageProcessor.schedule(iol);
		}
	}
}
