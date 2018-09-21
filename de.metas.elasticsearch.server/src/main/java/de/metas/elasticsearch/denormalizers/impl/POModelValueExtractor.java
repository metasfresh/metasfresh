package de.metas.elasticsearch.denormalizers.impl;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.PO;

import com.google.common.base.MoreObjects;

import de.metas.util.Check;

/*
 * #%L
 * de.metas.elasticsearch.server
 * %%
 * Copyright (C) 2018 metas GmbH
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

final class POModelValueExtractor implements IESModelValueExtractor
{
	public static final POModelValueExtractor of(final String modelValueTableName)
	{
		return new POModelValueExtractor(modelValueTableName);
	}

	private final String modelValueTableName;

	private POModelValueExtractor(final String modelValueTableName)
	{
		Check.assumeNotEmpty(modelValueTableName, "modelValueTableName is not empty");
		this.modelValueTableName = modelValueTableName;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper("extractor")
				.addValue(modelValueTableName)
				.toString();
	}

	@Override
	public Object extractValue(final Object model, final String columnName)
	{
		final PO po = InterfaceWrapperHelper.getPO(model);
		final Object valueAsModel = po.get_ValueAsPO(columnName, modelValueTableName);
		return valueAsModel;
	}
}