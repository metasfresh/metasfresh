/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.project.workorder.step;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.X_C_Project_WO_Step;

import javax.annotation.Nullable;

@AllArgsConstructor
public enum WOStepStatus implements ReferenceListAwareEnum
{
	CREATED(X_C_Project_WO_Step.WOSTEPSTATUS_CREATED),
	RECEIVED(X_C_Project_WO_Step.WOSTEPSTATUS_RECEIVED),
	RELEASED(X_C_Project_WO_Step.WOSTEPSTATUS_RELEASED),
	EARMARKED(X_C_Project_WO_Step.WOSTEPSTATUS_EARMARKED),
	READYFORTESTING(X_C_Project_WO_Step.WOSTEPSTATUS_READYFORTESTING),
	INTESTING(X_C_Project_WO_Step.WOSTEPSTATUS_INTESTING),
	EXECUTED(X_C_Project_WO_Step.WOSTEPSTATUS_EXECUTED),
	READY(X_C_Project_WO_Step.WOSTEPSTATUS_READY),
	CANCELED(X_C_Project_WO_Step.WOSTEPSTATUS_CANCELED);

	@Getter
	@NonNull
	private final String code;

	@Nullable
	public static WOStepStatus ofNullableCode(@Nullable final String code)
	{
		return code != null ? ofCode(code) : null;
	}

	@NonNull
	public static WOStepStatus ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	private static final ReferenceListAwareEnums.ValuesIndex<WOStepStatus> index = ReferenceListAwareEnums.index(values());
}
