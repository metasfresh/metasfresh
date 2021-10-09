package org.adempiere.ad.modelvalidator;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.compiere.model.ModelValidator;

public enum ModelChangeType implements TimingType
{
	BEFORE_NEW(ModelValidator.TYPE_BEFORE_NEW), AFTER_NEW(ModelValidator.TYPE_AFTER_NEW),

	BEFORE_CHANGE(ModelValidator.TYPE_BEFORE_CHANGE), AFTER_CHANGE(ModelValidator.TYPE_AFTER_CHANGE),

	BEFORE_DELETE(ModelValidator.TYPE_BEFORE_DELETE), AFTER_DELETE(ModelValidator.TYPE_AFTER_DELETE),

	AFTER_NEW_REPLICATION(ModelValidator.TYPE_AFTER_NEW_REPLICATION), AFTER_CHANGE_REPLICATION(ModelValidator.TYPE_AFTER_CHANGE_REPLICATION), BEFORE_DELETE_REPLICATION(ModelValidator.TYPE_BEFORE_DELETE_REPLICATION),

	BEFORE_SAVE_TRX(ModelValidator.TYPE_BEFORE_SAVE_TRX); // metas: tsa: 02380

	//
	// Implementation
	//
	private final int changeType;

	ModelChangeType(final int changeType)
	{
		this.changeType = changeType;
	}

	@Override
	public int toInt()
	{
		return changeType;
	}

	public static ModelChangeType valueOf(final int changeType)
	{
		final ModelChangeType[] values = values();
		for (final ModelChangeType value : values)
		{
			if (changeType == value.changeType)
			{
				return value;
			}
		}

		throw new IllegalArgumentException("No enum constant found for changeType=" + changeType + " in " + values);
	}

	public static boolean isNew(final ModelChangeType changeType)
	{
		return changeType != null && changeType.isNew();
	}

	public boolean isNew()
	{
		return this == BEFORE_NEW || this == AFTER_NEW;
	}

	public boolean isChange()
	{
		return this == BEFORE_CHANGE || this == AFTER_CHANGE;
	}

	public boolean isNewOrChange()
	{
		return isNew() || isChange();
	}

	public boolean isChangeOrDelete()
	{
		return isChange() || isDelete();
	}

	public boolean isDelete()
	{
		return this == BEFORE_DELETE || this == AFTER_DELETE;
	}

	public boolean isBefore()
	{
		return this == BEFORE_NEW
				|| this == BEFORE_CHANGE
				|| this == BEFORE_DELETE
				|| this == BEFORE_DELETE_REPLICATION
				|| this == BEFORE_SAVE_TRX;
	}

	public boolean isAfter()
	{
		return this == AFTER_NEW
				|| this == AFTER_NEW_REPLICATION
				|| this == AFTER_CHANGE
				|| this == AFTER_CHANGE_REPLICATION
				|| this == AFTER_DELETE;
	}

	public boolean isBeforeSaveTrx()
	{
		return this == BEFORE_SAVE_TRX;
	}

	public static boolean isBeforeSaveTrx(final TimingType timingType)
	{
		return timingType instanceof ModelChangeType
				? ((ModelChangeType)timingType).isBeforeSaveTrx()
				: false;
	}
}
