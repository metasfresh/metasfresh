package org.adempiere.mm.attributes.api.impl;

import static org.assertj.core.api.Assertions.assertThat;

import org.compiere.Adempiere;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.metas.StartupListener;
import de.metas.material.event.ProductDescriptorFactory;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2017 metas GmbH
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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { StartupListener.class, ProductDescriptorFromAttributeSetInstanceFactory.class })
public class ProductDescriptorFromAttributeSetInstanceFactoryTest
{
	@Test
	public void productDescriptorFactory_bean_is_a_ProductDescriptorFromAttributeSetInstanceFactory()
	{
		final ProductDescriptorFactory productDescriptor = Adempiere.getBean(ProductDescriptorFactory.class);
		assertThat(productDescriptor).isInstanceOf(ProductDescriptorFromAttributeSetInstanceFactory.class);
	}
}
