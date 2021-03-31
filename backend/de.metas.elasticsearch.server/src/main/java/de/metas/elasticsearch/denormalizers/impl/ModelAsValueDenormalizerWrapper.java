/*
 * #%L
 * de.metas.elasticsearch.server
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.elasticsearch.denormalizers.impl;

import de.metas.elasticsearch.denormalizers.IESValueDenormalizer;
import de.metas.elasticsearch.denormalizers.IESModelDenormalizer;
import de.metas.elasticsearch.indexer.source.ESModelToIndex;
import lombok.NonNull;
import lombok.ToString;
import org.elasticsearch.common.xcontent.XContentBuilder;

import javax.annotation.Nullable;
import java.io.IOException;

@ToString
final class ModelAsValueDenormalizerWrapper implements IESValueDenormalizer
{
	public static ModelAsValueDenormalizerWrapper of(@NonNull final IESModelDenormalizer modelDenormalizer)
	{
		return new ModelAsValueDenormalizerWrapper(modelDenormalizer);
	}

	private final IESModelDenormalizer modelDenormalizer;

	private ModelAsValueDenormalizerWrapper(final @NonNull IESModelDenormalizer modelDenormalizer) {this.modelDenormalizer = modelDenormalizer;}

	@Nullable
	@Override
	public Object denormalizeValue(@Nullable final Object value)
	{
		return value != null
				? modelDenormalizer.denormalizeModel(ESModelToIndex.ofObject(value))
				: null;
	}

	@Override
	public void appendMapping(final XContentBuilder builder, final String fieldName) throws IOException
	{
		modelDenormalizer.appendMapping(builder, fieldName);
	}
}
