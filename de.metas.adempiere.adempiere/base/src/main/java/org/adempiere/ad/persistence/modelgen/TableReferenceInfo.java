package org.adempiere.ad.persistence.modelgen;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.adempiere.util.lang.ObjectUtils;

/**
 * AD_Reference/AD_Ref_Table related meta data.
 * 
 * @author tsa
 */
class TableReferenceInfo
{
	private final String refTableName;
	private final int refDisplayType;
	private final String entityType;
	private final boolean isKey;
	private final int keyReferenceValueId;

	public TableReferenceInfo(final String refTableName
			, final int refDisplayType
			, final String entityType
			, final boolean isKey
			, final int keyReferenceValueId)
	{
		super();
		this.refTableName = refTableName;
		this.refDisplayType = refDisplayType;
		this.entityType = entityType;
		this.isKey = isKey;
		this.keyReferenceValueId = keyReferenceValueId;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	/**
	 * @return AD_Ref_Table.AD_Table_ID.TableName
	 */
	public String getRefTableName()
	{
		return refTableName;
	}

	/**
	 * 
	 * @return AD_Ref_Table.AD_Key.AD_Reference_ID
	 */
	public int getRefDisplayType()
	{
		return refDisplayType;
	}

	/**
	 * 
	 * @return AD_Ref_Table.AD_Table_ID.EntityType
	 */
	public String getEntityType()
	{
		return entityType;
	}

	/**
	 * 
	 * @return AD_Ref_Table.AD_Key.IsKey
	 */
	public boolean isKey()
	{
		return isKey;
	}

	/**
	 * 
	 * @return AD_Ref_Table.AD_Key.AD_Reference_Value_ID
	 */
	public int getKeyReferenceValueId()
	{
		return keyReferenceValueId;
	}

}
