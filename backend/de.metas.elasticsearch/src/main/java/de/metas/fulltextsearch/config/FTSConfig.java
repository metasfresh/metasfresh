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

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class FTSConfig
{
	@NonNull FTSConfigId id;

	@Getter(AccessLevel.NONE)
	@NonNull FTSConfigFieldsMap fields;

	@NonNull String esIndexName;
	@NonNull ESCommand createIndexCommand;
	@NonNull ESDocumentToIndexTemplate documentToIndexTemplate;
	@NonNull ESQueryTemplate queryCommand;

	public ESFieldName getEsFieldNameById(@NonNull final FTSConfigFieldId id)
	{
		return fields.getEsFieldNameById(id);
	}
}
