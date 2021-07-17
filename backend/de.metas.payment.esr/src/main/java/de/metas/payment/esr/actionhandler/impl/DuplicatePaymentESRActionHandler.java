package de.metas.payment.esr.actionhandler.impl;

import java.util.Set;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.payment.PaymentId;
import de.metas.payment.esr.api.IESRImportDAO;
import de.metas.payment.esr.api.impl.ESRImportBL;
import de.metas.payment.esr.model.I_ESR_ImportLine;
import de.metas.util.Services;

/**
 * Handler for {@link de.metas.payment.esr.model.X_ESR_ImportLine#ESR_PAYMENT_ACTION_Duplicate_Payment}. This handler links esr line with existent payment
 * 
 */
public class DuplicatePaymentESRActionHandler extends AbstractESRActionHandler
{

	private static final transient Logger logger = LogManager.getLogger(ESRImportBL.class);
	private final IESRImportDAO esrImportDAO = Services.get(IESRImportDAO.class);

	@Override
	public boolean process(final I_ESR_ImportLine line, final String message)
	{
		super.process(line, message);

		final Set<PaymentId> existentPaymentIds = esrImportDAO.findExistentPaymentIds(line);
		
		if (existentPaymentIds.isEmpty())
		{
			logger.warn("No payment found for line : " + line.getESR_ImportLine_ID());
			return false;
		}
		else
		{
			return true;
		}
	}

}
