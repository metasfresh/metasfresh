/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.contracts.modular.log.status;

import de.metas.contracts.model.X_ModCntr_Log_Status;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;

public enum ProcessingStatus implements ReferenceListAwareEnum
{
	ENQUEUED(X_ModCntr_Log_Status.PROCESSINGSTATUS_Enqueued),
	SUCCESSFULLY_PROCESSED(X_ModCntr_Log_Status.PROCESSINGSTATUS_SuccessfullyProcessed),
	ERRORED(X_ModCntr_Log_Status.PROCESSINGSTATUS_Errored);

	@Getter
	private final String code;

	ProcessingStatus(@NonNull final String code)
	{
		this.code = code;
	}

	@NonNull
	public static ProcessingStatus ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	private static final ReferenceListAwareEnums.ValuesIndex<ProcessingStatus> index = ReferenceListAwareEnums.index(values());
}
