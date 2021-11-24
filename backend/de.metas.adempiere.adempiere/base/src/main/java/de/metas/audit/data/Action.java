/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.audit.data;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.X_Data_Export_Audit_Log;

public enum Action implements ReferenceListAwareEnum
{
	Standalone(X_Data_Export_Audit_Log.DATA_EXPORT_ACTION_Exported_Standalone),
	AlongWithParent(X_Data_Export_Audit_Log.DATA_EXPORT_ACTION_Exported_AlongWithParent),
	AssignedToParent(X_Data_Export_Audit_Log.DATA_EXPORT_ACTION_AssignedToParent),
	Deleted(X_Data_Export_Audit_Log.DATA_EXPORT_ACTION_Deleted);

	@Getter
	private final String code;

	Action(@NonNull final String code)
	{
		this.code = code;
	}

	@NonNull
	public static Action ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	private static final ReferenceListAwareEnums.ValuesIndex<Action> index = ReferenceListAwareEnums.index(values());
}
