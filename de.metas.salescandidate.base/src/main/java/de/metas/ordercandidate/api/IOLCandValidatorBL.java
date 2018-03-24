package de.metas.ordercandidate.api;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.adempiere.util.ISingletonService;

import de.metas.ordercandidate.model.I_C_OLCand;

public interface IOLCandValidatorBL extends ISingletonService
{
	/**
	 * AD_Message to be used by users of this implementation.
	 */
	public static final String MSG_ERRORS_FOUND = "de.metas.ordercandidate.spi.impl.OLCandPriceValidator.FoundErrors";

	boolean validate(I_C_OLCand olCand);

	/**
	 * 
	 * @return a thread-local variable that indicates if the current thread is currently validating olCands. Can be used to avoid unnecessary calls to the validation API (from MVs).
	 */
	boolean isValidationProcessInProgress();

	/**
	 * See {@link #isValidationProcessInProgress()}.
	 * 
	 * @param value
	 * @return
	 */
	boolean setValidationProcessInProgress(boolean value);

}
