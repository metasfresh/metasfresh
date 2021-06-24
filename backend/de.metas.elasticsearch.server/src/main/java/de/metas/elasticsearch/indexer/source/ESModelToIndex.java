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

package de.metas.elasticsearch.indexer.source;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.PO;

import javax.annotation.Nullable;

@EqualsAndHashCode
@ToString
public class ESModelToIndex
{
	public static ESModelToIndex ofObject(@NonNull final Object object)
	{
		return object instanceof ESModelToIndex
				? (ESModelToIndex)object
				: new ESModelToIndex(object);
	}

	public static ESModelToIndex ofPO(@NonNull final PO po)
	{
		return new ESModelToIndex(po);
	}

	@NonNull Object object;

	private ESModelToIndex(@NonNull final Object object)
	{
		this.object = object;
	}

	public PO getPO()
	{
		return InterfaceWrapperHelper.getPO(object);
	}

	@Nullable
	public Object getFieldValue(@NonNull final String columnName)
	{
		return getPO().get_Value(columnName);
	}

	@Nullable
	public ESModelToIndex getFieldValueAsModel(
			@NonNull final String columnName,
			@NonNull final String modelValueTableName)
	{
		final PO po = getPO();
		final Object valueAsModel = po.get_ValueAsPO(columnName, modelValueTableName);
		return valueAsModel != null ? ofObject(valueAsModel) : null;
	}
}
