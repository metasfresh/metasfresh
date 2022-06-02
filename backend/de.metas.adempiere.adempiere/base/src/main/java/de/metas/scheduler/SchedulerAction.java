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

package de.metas.scheduler;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;

public enum SchedulerAction implements ReferenceListAwareEnum
{
	// AD_Reference_ID = 541532
	ENABLE("E"),
	DISABLE("D"),
	RESTART("R"),
	RUN_ONCE("O")
	;

	private static final ReferenceListAwareEnums.ValuesIndex<SchedulerAction> index = ReferenceListAwareEnums.index(values());

	@Getter
	private final String code;

	SchedulerAction(@NonNull final String code)
	{
		this.code = code;
	}

	public static SchedulerAction ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}
}