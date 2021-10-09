/*
 * #%L
 * de.metas.manufacturing
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

package de.metas.manufacturing.generatedcomponents;

import com.google.common.collect.ImmutableMap;
import de.metas.document.sequence.DocSequenceId;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.ToString;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Set;

@EqualsAndHashCode
@ToString
class ComponentGeneratorParams
{
	public static final ComponentGeneratorParams EMPTY = builder().build();

	@Getter
	private final Optional<DocSequenceId> sequenceId;

	private final ImmutableMap<String, String> parameters;

	@Builder
	private ComponentGeneratorParams(
			@Nullable final DocSequenceId sequenceId,
			@NonNull @Singular final ImmutableMap<String, String> parameters)
	{
		this.sequenceId = Optional.ofNullable(sequenceId);
		this.parameters = parameters;
	}

	public Set<String> getParameterNames() { return parameters.keySet(); }

	public String getValue(@NonNull final String parameterName)
	{
		return parameters.get(parameterName);
	}
}
