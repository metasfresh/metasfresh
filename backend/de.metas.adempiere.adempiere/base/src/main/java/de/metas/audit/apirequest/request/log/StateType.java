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

package de.metas.audit.apirequest.request.log;

import de.metas.util.Check;
import de.metas.util.lang.ReferenceListAwareEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Optional;

import static org.compiere.model.X_API_Request_Audit_Log.TYPE_Created;
import static org.compiere.model.X_API_Request_Audit_Log.TYPE_Updated;
import static org.compiere.model.X_API_Request_Audit_Log.TYPE_Deleted;


@AllArgsConstructor
@Getter
public enum StateType implements ReferenceListAwareEnum
{
	CREATED(TYPE_Created),
	UPDATED(TYPE_Updated),
	DELETED(TYPE_Deleted);

	private final String code;

	@NonNull
	public static StateType ofCode(@NonNull final String code)
	{
		return ofCodeOptional(code)
				.orElseThrow(() -> new AdempiereException("No StateType could be found for code!")
						.appendParametersToMessage()
						.setParameter("code", code));
	}

	@NonNull
	public static Optional<StateType> ofCodeOptional(@Nullable final String code)
	{
		if (Check.isBlank(code))
		{
			return Optional.empty();
		}

		return Arrays.stream(values())
				.filter(value -> value.getCode().equals(code))
				.findFirst();
	}
}