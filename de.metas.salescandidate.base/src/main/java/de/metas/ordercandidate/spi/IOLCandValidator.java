package de.metas.ordercandidate.spi;

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

import de.metas.ordercandidate.model.I_C_OLCand;

/**
 * General note: implementations do not just set the error flag, but also a lot of other data.
 *
 *
 */
public interface IOLCandValidator
{
	/**
	 * Validate the given <code>olCand</code>.
	 * <p>
	 * Change {@link I_C_OLCand#COLUMN_IsError IsError} and {@link I_C_OLCand#COLUMN_ErrorMsg ErrorMsg} accordingly, but <b>do not</b> save.
	 *
	 * @param olCand
	 * @return <code>true</code> if the validation was successful
	 */
	boolean validate(I_C_OLCand olCand);
}
