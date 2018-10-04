package de.metas.dimension.model.validator;

/*
 * #%L
 * de.metas.dimension
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


import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;

import de.metas.dimension.IDimensionspecDAO;
import de.metas.dimension.model.I_AD_Column;
import de.metas.util.Services;

@Interceptor(I_AD_Column.class)
public class AD_Column
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_DELETE })
	public void validate(final I_AD_Column column)
	{
		Services.get(IDimensionspecDAO.class).deleteAllAssociations(column);
	}

}
