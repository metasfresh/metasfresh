package de.metas.cache.model;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.persistence.ModelDynAttributeAccessor;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.PO;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

@EqualsAndHashCode
@ToString
public class POCacheSourceModel implements ICacheSourceModel
{
	public static void setRootRecordReference(@NonNull final PO po, @Nullable final TableRecordReference rootRecordReference)
	{
		ATTR_RootRecordReference.setValue(po, rootRecordReference);
	}

	private static final ModelDynAttributeAccessor<PO, TableRecordReference> //
			ATTR_RootRecordReference = new ModelDynAttributeAccessor<>(ModelCacheInvalidationService.class.getName(), "RootRecordReference", TableRecordReference.class);

	private final PO po;

	POCacheSourceModel(@NonNull final PO po)
	{
		this.po = po;
	}

	@Override
	public String getTableName()
	{
		return po.get_TableName();
	}

	@Override
	public int getRecordId()
	{
		return po.get_ID();
	}

	@Override
	public TableRecordReference getRootRecordReferenceOrNull()
	{
		return ATTR_RootRecordReference.getValue(po);
	}

	@Override
	public Integer getValueAsInt(final String columnName, final Integer defaultValue)
	{
		return po.get_ValueAsInt(columnName, defaultValue);
	}

	@Override
	public boolean isValueChanged(final String columnName)
	{
		return po.is_ValueChanged(columnName);
	}
}
