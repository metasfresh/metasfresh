package de.metas.acct.gldistribution;

import org.compiere.model.I_GL_Distribution;

import de.metas.util.ISingletonService;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public interface IGLDistributionBL extends ISingletonService
{
	/**
	 * Validates given {@link I_GL_Distribution}, sets PercentTotal, IsValid.
	 * 
	 * @param glDistribution
	 * @throws GLDistributionNotValidException in case it's not valid.
	 */
	void validate(I_GL_Distribution glDistribution) throws GLDistributionNotValidException;
}
