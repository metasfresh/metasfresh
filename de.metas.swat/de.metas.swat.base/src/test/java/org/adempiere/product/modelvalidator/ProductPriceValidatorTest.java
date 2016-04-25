package org.adempiere.product.modelvalidator;

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


import static org.adempiere.test.UnitTestTools.mock;
import static org.easymock.classextension.EasyMock.replay;
import static org.easymock.classextension.EasyMock.verify;

import java.util.HashMap;
import java.util.Map;

import org.compiere.model.I_M_ProductPrice;
import org.compiere.model.ModelValidationEngine;
import org.junit.Test;

import de.metas.product.modelvalidator.MProductPriceValidator;

public class ProductPriceValidatorTest
{

	/**
	 * Checks if the validator registers itself for the right tables.
	 */
	@Test
	public void initialize()
	{
		final Map<String, Object> mocks = new HashMap<String, Object>();

		final ModelValidationEngine engine = mock(ModelValidationEngine.class, "engine", mocks);

		final MProductPriceValidator productPriceValidator = new MProductPriceValidator();

		engine.addModelChange(I_M_ProductPrice.Table_Name, productPriceValidator);

		replay(mocks.values().toArray());
		productPriceValidator.initialize(engine, null);
		verify(mocks.values().toArray());
	}
}
