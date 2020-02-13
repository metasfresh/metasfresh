package de.metas.ui.web.window.datatypes.json;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import de.metas.ui.web.test.util.EnumTestUtils;
import de.metas.ui.web.window.descriptor.LayoutAlign;

/*
 * #%L
 * metasfresh-webui-api
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class JSONLayoutAlignTest
{
	@Test
	public void test_fromNullable_fullyCovered()
	{
		EnumTestUtils.assertMappingFullyCovered(LayoutAlign.values(), JSONLayoutAlign::fromNullable);
	}

	@Test
	public void test_fromNullable()
	{
		assertThat(JSONLayoutAlign.fromNullable((LayoutAlign)null)).isNull();
		assertThat(JSONLayoutAlign.fromNullable(LayoutAlign.Left)).isSameAs(JSONLayoutAlign.left);
		assertThat(JSONLayoutAlign.fromNullable(LayoutAlign.Center)).isSameAs(JSONLayoutAlign.center);
		assertThat(JSONLayoutAlign.fromNullable(LayoutAlign.Right)).isSameAs(JSONLayoutAlign.right);
		assertThat(JSONLayoutAlign.fromNullable(LayoutAlign.Justify)).isSameAs(JSONLayoutAlign.justify);
	}

}
