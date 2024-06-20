package de.metas.material.maturing;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.product.ProductId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.util.List;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

final class MaturingConfigMap
{
	private final ImmutableMap<MaturingConfigLineId, MaturingConfigLine> byId;
	private final ImmutableListMultimap<ProductId, MaturingConfigLine> byMaturedProductId;
	private final ImmutableListMultimap<ProductId, MaturingConfigLine> byFromProductId;

	public MaturingConfigMap(@NonNull final List<MaturingConfigLine> maturingConfigLines)
	{
		byId = Maps.uniqueIndex(maturingConfigLines, MaturingConfigLine::getId);

		byMaturedProductId = maturingConfigLines.stream()
				.collect(ImmutableListMultimap.toImmutableListMultimap(MaturingConfigLine::getMaturedProductId, line -> line));

		byFromProductId = maturingConfigLines.stream()
				.collect(ImmutableListMultimap.toImmutableListMultimap(MaturingConfigLine::getFromProductId, line -> line));
	}

	@NonNull
	public MaturingConfigLine getById(@NonNull final MaturingConfigLineId id)
	{
		final MaturingConfigLine line = byId.get(id);
		if (line == null)
		{
			throw new AdempiereException("@NotFound@ @M_MaturingConfig_Line_ID@: " + id);
		}
		return line;
	}

	public List<MaturingConfigLine> getByMaturedProductId(@NonNull final ProductId maturedProductId)
	{
		return byMaturedProductId.get(maturedProductId);
	}

	@NonNull
	public List<MaturingConfigLine> getByFromProductId(@NonNull final ProductId maturedProductId)
	{
		return byFromProductId.get(maturedProductId);
	}
}
