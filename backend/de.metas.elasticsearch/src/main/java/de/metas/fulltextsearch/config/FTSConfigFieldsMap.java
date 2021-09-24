/*
 * #%L
 * de.metas.elasticsearch
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

package de.metas.fulltextsearch.config;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import java.util.List;

@EqualsAndHashCode
@ToString
public class FTSConfigFieldsMap
{
	@Getter
	private final ImmutableSet<ESFieldName> esFieldNames;

	private final ImmutableMap<FTSConfigFieldId, FTSConfigField> fieldsById;

	public FTSConfigFieldsMap(@NonNull final List<FTSConfigField> fields)
	{
		fieldsById = Maps.uniqueIndex(fields, FTSConfigField::getId);
		esFieldNames = fields.stream().map(FTSConfigField::getEsFieldName).collect(ImmutableSet.toImmutableSet());
	}

	public ESFieldName getEsFieldNameById(@NonNull final FTSConfigFieldId id)
	{
		final FTSConfigField field = fieldsById.get(id);
		if (field == null)
		{
			throw new AdempiereException("No field found for " + id + " in " + this);
		}
		return field.getEsFieldName();
	}
}
