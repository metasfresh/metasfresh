package de.metas.testsupport;

import javax.annotation.Nullable;

import org.adempiere.model.InterfaceWrapperHelper;
import org.assertj.core.api.AbstractAssert;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public class ModelAssert extends AbstractAssert<ModelAssert, Object>
{
	public ModelAssert(@Nullable final Object actual)
	{
		super(actual, ModelAssert.class);
	}

	public static ModelAssert assertThat(@Nullable final Object actual)
	{
		return new ModelAssert(actual);
	}

	public ModelAssert hasSameIdAs(final Object otherModel)
	{
		final int otherId = InterfaceWrapperHelper.getId(otherModel);
		final int actualId = InterfaceWrapperHelper.getId(actual);
		if (otherId != actualId)
		{
			failWithMessage("Expected model <%s> to have the same ID as given model <%s>, hut hasn't.", actual, otherModel);
		}

		return this;
	}
}
