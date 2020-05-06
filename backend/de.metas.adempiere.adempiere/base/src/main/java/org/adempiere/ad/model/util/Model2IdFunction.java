package org.adempiere.ad.model.util;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import org.adempiere.model.InterfaceWrapperHelper;

import com.google.common.base.Function;

import de.metas.util.Check;

/**
 * Function which returns the ID of given model.
 *
 * NOTE: null models are not allowed.
 *
 * @author tsa
 *
 * @param <ModelType>
 */
public final class Model2IdFunction<ModelType> implements Function<ModelType, Integer>
{
	public static final transient Model2IdFunction<Object> instance = new Model2IdFunction<>();

	public static final <ModelType> Model2IdFunction<ModelType> getInstance()
	{
		@SuppressWarnings("unchecked")
		final Model2IdFunction<ModelType> instanceCasted = (Model2IdFunction<ModelType>)instance;
		return instanceCasted;
	}

	private Model2IdFunction()
	{
		super();
	}

	@Override
	public Integer apply(final ModelType model)
	{
		Check.assumeNotNull(model, "model not null");
		return InterfaceWrapperHelper.getId(model);
	}

}
