/*
 * #%L
 * de.metas.business
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

package de.metas.project;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.X_C_ProjectType;

import javax.annotation.Nullable;

@SuppressWarnings("BooleanMethodIsAlwaysInverted")
public enum ProjectCategory implements ReferenceListAwareEnum
{
	General(X_C_ProjectType.PROJECTCATEGORY_General),
	AssertProject(X_C_ProjectType.PROJECTCATEGORY_AssetProject),
	WorkOrderJob(X_C_ProjectType.PROJECTCATEGORY_WorkOrderJob),
	ServiceChargeProject(X_C_ProjectType.PROJECTCATEGORY_ServiceChargeProject),
	ServiceOrRepair(X_C_ProjectType.PROJECTCATEGORY_ServiceOrRepair),
	Budget(X_C_ProjectType.PROJECTCATEGORY_Budget),
	;

	private static final ReferenceListAwareEnums.ValuesIndex<ProjectCategory> index = ReferenceListAwareEnums.index(values());

	@Getter
	private final String code;

	ProjectCategory(@NonNull final String code)
	{
		this.code = code;
	}

	public static ProjectCategory ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	public static ProjectCategory ofNullableCodeOrGeneral(@Nullable final String code)
	{
		final ProjectCategory type = index.ofNullableCode(code);
		return type != null ? type : ProjectCategory.General;
	}

	@Nullable
	public static ProjectCategory ofNullableCode(@Nullable final String code)
	{
		return index.ofNullableCode(code);
	}

	public boolean isServiceOrRepair()
	{
		return ServiceOrRepair.equals(this);
	}

	public boolean isBudget() {return Budget.equals(this);}

	public boolean isWorkOrder()
	{
		return WorkOrderJob.equals(this);
	}
}
