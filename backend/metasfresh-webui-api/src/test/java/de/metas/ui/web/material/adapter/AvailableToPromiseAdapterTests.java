package de.metas.ui.web.material.adapter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import de.metas.material.event.commons.AttributesKey;
import de.metas.ui.web.material.adapter.AvailableToPromiseResultForWebui.Group;
import de.metas.ui.web.material.adapter.AvailableToPromiseResultForWebui.Group.Type;

/*
 * #%L
 * metasfresh-material-dispo-commons
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
 * #L%t
 */

public class AvailableToPromiseAdapterTests
{
	@Test
	public void extractGroupType_all()
	{
		final Group.Type type = AvailableToPromiseAdapter.extractGroupType(AttributesKey.ALL);
		assertThat(type).isSameAs(Type.ALL_STORAGE_KEYS);
	}

	@Test
	public void extractGroupType_other()
	{
		final Group.Type type = AvailableToPromiseAdapter.extractGroupType(AttributesKey.OTHER);
		assertThat(type).isSameAs(Type.OTHER_STORAGE_KEYS);
	}

	@Test
	public void extractGroupType_attributeSet()
	{
		final AttributesKey attributesKey = AttributesKey.ofAttributeValueIds(12345);
		final Group.Type type = AvailableToPromiseAdapter.extractGroupType(attributesKey);
		assertThat(type).isSameAs(Type.ATTRIBUTE_SET);
	}
}
