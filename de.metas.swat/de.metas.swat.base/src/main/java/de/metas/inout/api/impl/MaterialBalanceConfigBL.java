package de.metas.inout.api.impl;

import java.util.List;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.concurrent.CopyOnWriteArrayList;

import com.google.common.collect.ImmutableList;

import de.metas.inout.api.IMaterialBalanceConfigBL;
import de.metas.inout.spi.IMaterialBalanceConfigMatcher;
import lombok.NonNull;

public class MaterialBalanceConfigBL implements IMaterialBalanceConfigBL
{

	private CopyOnWriteArrayList<IMaterialBalanceConfigMatcher> matchers = new CopyOnWriteArrayList<>();

	@Override
	public void addMaterialBalanceConfigMather(@NonNull final IMaterialBalanceConfigMatcher matcher)
	{
		matchers.add(matcher);
	}

	@Override
	public List<IMaterialBalanceConfigMatcher> retrieveMatchers()
	{
		return ImmutableList.copyOf(matchers);
	}

}
