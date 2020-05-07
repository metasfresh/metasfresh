package org.adempiere.processing.service;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.util.Properties;

import org.adempiere.processing.exception.ProcessingException;
import org.adempiere.processing.interfaces.IProcessablePO;
import org.adempiere.processing.model.MADProcessablePO;
import org.adempiere.util.ISingletonService;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;

import de.metas.process.JavaProcess;

/**
 * Singleton with generic methods for deferred processing of POs
 * 
 * @author ts
 * 
 */
public interface IProcessingService extends ISingletonService
{
	public static final int NO_AD_PINSTANCE_ID = 0;

	/**
	 * 
	 * @throws InvokeCommissionTypeException
	 */
	void handleProcessingException(
				Properties ctx,
				IProcessablePO poToProcess,
				ProcessingException e,
				String trxName);

	/**
	 * Performs the "subsequent" processing of the po that is referred to by the given 'processablePO'. Basically, this
	 * means that a modelChangeEvent is fired with type {@link ModelValidator#TYPE_SUBSEQUENT}. If the model
	 * validator(s) return an error message or thow an exception, an AD_Issue is created and for the given
	 * processablePO, and the processablePO is flagged with IsError='Y'.
	 * 
	 * @param processablePO
	 * @param parent
	 *            optional. if not null, addLog() as well as the AD_PInstance_ID of the given process will be used for
	 *            error logging
	 * @see ModelValidationEngine#fireModelChange(org.compiere.model.PO, int)
	 * @see ModelValidator#TYPE_SUBSEQUENT
	 */
	void process(MADProcessablePO processablePO, JavaProcess parent);

}
