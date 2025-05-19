package de.metas.payment.esr.exception;

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


import de.metas.payment.esr.model.I_ESR_Import;

import java.io.Serial;

public class ESRImportLockedException extends ESRException
{
	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = -4934880824804384245L;

	public ESRImportLockedException(final I_ESR_Import esrImport)
	{
		super("@Locked@: " + esrImport);
	}
}
