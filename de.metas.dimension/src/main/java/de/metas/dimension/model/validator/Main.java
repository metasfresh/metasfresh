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


import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;

/**
 * Module activator
 *
 * @author ts
 *
 */
public class Main extends AbstractModuleInterceptor
{
	@Override
	protected void registerInterceptors(final IModelValidationEngine engine)
	{
		engine.addModelValidator(new de.metas.dimension.model.validator.AD_Column());
		engine.addModelValidator(new de.metas.dimension.model.validator.DIM_Dimension_Spec());
		engine.addModelValidator(new de.metas.dimension.model.validator.DIM_Dimension_Spec_Attribute());
	}
}
