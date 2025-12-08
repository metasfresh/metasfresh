package de.metas.cache.model;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

import lombok.NonNull;
import org.adempiere.ad.modelvalidator.ModelChangeType;

public enum ModelCacheInvalidationTiming
{
	BEFORE_NEW,
	AFTER_NEW,
	BEFORE_CHANGE,
	AFTER_CHANGE,
	AFTER_DELETE,
	;

	public static ModelCacheInvalidationTiming ofModelChangeType(@NonNull final ModelChangeType modelChangeType, final ModelCacheInvalidationTiming defaultValue)
	{
		switch (modelChangeType)
		{
			case BEFORE_NEW:
				return BEFORE_NEW;
			case AFTER_NEW:
				return AFTER_NEW;
			case BEFORE_CHANGE:
				return BEFORE_CHANGE;
			case AFTER_CHANGE:
				return AFTER_CHANGE;
			case BEFORE_DELETE:
				return defaultValue;
			case AFTER_DELETE:
				return AFTER_DELETE;
			case AFTER_NEW_REPLICATION:
				return defaultValue;
			case AFTER_CHANGE_REPLICATION:
				return defaultValue;
			case BEFORE_DELETE_REPLICATION:
				return defaultValue;
			case BEFORE_SAVE_TRX:
				return defaultValue;
			default:
				return defaultValue;
		}
	}

	public boolean isBefore() {return this == BEFORE_NEW || this == BEFORE_CHANGE;}

	public boolean isAfter() {return !isBefore();}

	public boolean isNew() {return this == BEFORE_NEW || this == AFTER_NEW;}

	public boolean isAfterNew() {return this == AFTER_NEW;}

	public boolean isChange() {return this == BEFORE_CHANGE || this == AFTER_CHANGE;}

	public boolean isDelete() {return this == AFTER_DELETE;}

	public boolean isAfterDelete() {return this == AFTER_DELETE;}
}