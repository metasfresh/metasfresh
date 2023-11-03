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

package de.metas.fulltextsearch.indexer.queue;

import de.metas.elasticsearch.model.X_ES_FTS_Index_Queue;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;

@AllArgsConstructor
@Getter
public enum ModelToIndexEventType implements ReferenceListAwareEnum
{
	MODEL_CREATED_OR_UPDATED(X_ES_FTS_Index_Queue.EVENTTYPE_Update),
	MODEL_REMOVED(X_ES_FTS_Index_Queue.EVENTTYPE_Delete),
	DELETE_INDEX_REQUEST(X_ES_FTS_Index_Queue.EVENTTYPE_DeleteIndex),
	;

	private static final ReferenceListAwareEnums.ValuesIndex<ModelToIndexEventType> index = ReferenceListAwareEnums.index(values());

	final String code;

	public static ModelToIndexEventType ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	@Nullable
	public static ModelToIndexEventType ofNullableCode(@Nullable final String code)
	{
		return index.ofNullableCode(code);
	}

	public boolean isDeleteIndexRequest() {return this.equals(DELETE_INDEX_REQUEST);}
}
