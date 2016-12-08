package de.metas.elasticsearch.denormalizers.impl;

import java.io.IOException;
import java.util.Map;

import org.elasticsearch.common.xcontent.XContentBuilder;

import com.google.common.collect.ImmutableMap;

import de.metas.elasticsearch.denormalizers.IESDenormalizer;
import de.metas.elasticsearch.types.ESDataType;
import de.metas.elasticsearch.types.ESIndexType;

/*
 * #%L
 * de.metas.business
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public final class AD_Ref_List_Denormalizer implements IESDenormalizer
{
	public static final AD_Ref_List_Denormalizer of(final int AD_Reference_ID)
	{
		// TODO: use AD_Reference_ID to extract the AD_Ref_List.Name
		return instance;
	}

	private static final transient AD_Ref_List_Denormalizer instance = new AD_Ref_List_Denormalizer();

	private static final String FIELDNAME_Value = "value";

	private AD_Ref_List_Denormalizer()
	{
		super();
	}

	@Override
	public void appendMapping(final Object builderObj, final String fieldName) throws IOException
	{
		final XContentBuilder builder = ESDenormalizerHelper.extractXContentBuilder(builderObj);
		//@formatter:off
		builder.startObject(fieldName)
				.startObject("properties")
					.startObject(FIELDNAME_Value)
						.field("type", ESDataType.String.getEsTypeAsString())
						.field("index", ESIndexType.NotAnalyzed.getEsTypeAsString())
					.endObject()
				.endObject()
			.endObject();
		//@formatter:on
	}

	@Override
	public Map<String, Object> denormalize(final Object valueObj)
	{
		final ImmutableMap.Builder<String, Object> result = ImmutableMap.builder();

		final String value = valueObj == null ? null : valueObj.toString();
		if (value != null)
		{
			result.put(FIELDNAME_Value, value);
		}

		return result.build();
	}
}
