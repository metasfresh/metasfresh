package de.metas.printing.api;

import de.metas.printing.model.I_C_Print_Job_Instructions;
import de.metas.printing.model.I_C_Print_Job_Instructions_v;
import de.metas.util.ISingletonService;

/*
 * #%L
 * de.metas.printing.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public interface IPrintJobDAO extends ISingletonService
{

	/**
	 * @param pji
	 * @return the (view) entry of <code>I_C_Print_Job_Instructions_v</code> that is linked with the given <code>I_C_Print_Job_Instructions</code> if any is found; null otherwise
	 *         Note: there should only be one I_C_Print_Job_Instructions_v for the given pji
	 */
	I_C_Print_Job_Instructions_v retrieveC_Print_Job_Instructions_Info(I_C_Print_Job_Instructions pji);

}
