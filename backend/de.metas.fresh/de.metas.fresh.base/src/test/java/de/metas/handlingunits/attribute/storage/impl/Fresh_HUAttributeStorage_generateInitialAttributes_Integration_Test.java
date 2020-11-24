package de.metas.handlingunits.attribute.storage.impl;

/*
 * #%L
 * de.metas.fresh.base
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

import org.adempiere.mm.attributes.spi.IAttributeValueGenerator;

import de.metas.vertical.pharma.securpharm.attribute.HUScannedAttributeHandler;
import de.metas.vertical.pharma.securpharm.attribute.SecurPharmAttributesStatus;
import org.junit.jupiter.api.Test;

/**
 * Runs {@link HUAttributeStorage_generateInitialAttributes_Integration_Test} using endcustomer project's classpath,
 * because we want to test ALL {@link IAttributeValueGenerator}.
 * 
 * @author tsa
 *
 */
public class Fresh_HUAttributeStorage_generateInitialAttributes_Integration_Test extends HUAttributeStorage_generateInitialAttributes_Integration_Test
{
	@Override
	protected void initialize()
	{
		registerEnum(HUScannedAttributeHandler.class, SecurPharmAttributesStatus.class);
		super.initialize();
	}

	@Override
	@Test
	public void test()
	{
		test(HUScannedAttributeHandler.class);
	}

}
