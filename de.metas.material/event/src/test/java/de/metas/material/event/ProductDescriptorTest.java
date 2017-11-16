package de.metas.material.event;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import de.metas.material.event.commons.ProductDescriptor;

/*
 * #%L
 * metasfresh-material-event
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class ProductDescriptorTest
{

	@Test
	public void forProductIdOnly()
	{
		final ProductDescriptor forProductIdOnly = ProductDescriptor.forProductIdOnly(30);

		assertThat(forProductIdOnly.getProductId()).isEqualTo(30);
		assertThat(forProductIdOnly.getAttributeSetInstanceId()).isLessThanOrEqualTo(-1);
		assertThat(forProductIdOnly.getStorageAttributesKey())
				.isSameAs(ProductDescriptor.STORAGE_ATTRIBUTES_KEY_UNSPECIFIED);
	}

}
