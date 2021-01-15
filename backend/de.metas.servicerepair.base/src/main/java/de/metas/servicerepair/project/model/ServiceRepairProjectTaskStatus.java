/*
 * #%L
 * de.metas.servicerepair.base
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

package de.metas.servicerepair.project.model;

import de.metas.servicerepair.repository.model.X_C_Project_Repair_Task;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
public enum ServiceRepairProjectTaskStatus implements ReferenceListAwareEnum
{
	NOT_STARTED(X_C_Project_Repair_Task.STATUS_NotStarted),
	IN_PROGRESS(X_C_Project_Repair_Task.STATUS_InProgress),
	COMPLETED(X_C_Project_Repair_Task.STATUS_Completed);

	@Getter
	private final String code;

	private static final ReferenceListAwareEnums.ValuesIndex<ServiceRepairProjectTaskStatus> index = ReferenceListAwareEnums.index(values());

	public static ServiceRepairProjectTaskStatus ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}
}
