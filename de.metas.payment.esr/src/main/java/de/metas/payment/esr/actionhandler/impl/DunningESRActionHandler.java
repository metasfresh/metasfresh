package de.metas.payment.esr.actionhandler.impl;

/*
 * #%L
 * de.metas.payment.esr
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


import de.metas.payment.esr.model.I_ESR_ImportLine;

/**
 * Handler for {@link de.metas.payment.esr.model.X_ESR_ImportLine#ESR_PAYMENT_ACTION_Keep_For_Dunning}. In the case of dunning, we do nothing. The ADmpiere dunning module will do everything in that
 * regard.
 * <p>
 * Note that this action actually only makes sense in the case of an underpayment.
 * 
 */
public class DunningESRActionHandler extends AbstractESRActionHandler
{
	@Override
	public boolean process(I_ESR_ImportLine line, String message)
	{
		super.process(line, message);

		// 04194 : In the case of dunning actions, do nothing.
		return true;
	}

}
