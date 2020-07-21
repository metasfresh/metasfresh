/*
 * #%L
 * de.metas.serviceprovider.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.serviceprovider.issue;

import com.google.common.collect.ImmutableList;
import de.metas.util.lang.ReferenceListAwareEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

import static de.metas.serviceprovider.model.X_S_Issue.STATUS_Closed;
import static de.metas.serviceprovider.model.X_S_Issue.STATUS_Delivered;
import static de.metas.serviceprovider.model.X_S_Issue.STATUS_InProgress;
import static de.metas.serviceprovider.model.X_S_Issue.STATUS_Invoiced;
import static de.metas.serviceprovider.model.X_S_Issue.STATUS_New;
import static de.metas.serviceprovider.model.X_S_Issue.STATUS_Pending;

@AllArgsConstructor
@Getter
public enum Status implements ReferenceListAwareEnum
{
	NEW(STATUS_New),
	IN_PROGRESS(STATUS_InProgress),
	CLOSED(STATUS_Closed),
	PENDING(STATUS_Pending),
	DELIVERED(STATUS_Delivered),
	INVOICED(STATUS_Invoiced);

	private final String code;

	public static Optional<Status> ofCodeOptional(@Nullable final String code)
	{
		return Arrays.stream(values())
				.filter(status -> status.getCode().equals(code))
				.findFirst();
	}

	public static Status ofCode(@NonNull final String code)
	{
		return Arrays.stream(values())
				.filter(status -> status.getCode().equals(code))
				.findFirst()
				.orElseThrow(() -> new AdempiereException("Unknown status!").appendParametersToMessage().setParameter("Code", code));
	}

	public static ImmutableList<String> listCodes()
	{
		return Stream.of(values()).map(Status::getCode).collect(ImmutableList.toImmutableList());
	}
}
