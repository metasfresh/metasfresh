/*
 * #%L
 * de.metas.externalsystem
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

package de.metas.externalsystem.leichmehl;

import de.metas.common.util.CoalesceUtil;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class ExternalSystemLeichMehlPluFileConfig
{
	private final static String DEFAULT_REPLACEMENT_REGEX = ".*";

	@NonNull
	ExternalSystemLeichMehlPluFileConfigId id;

	@NonNull
	LeichMehlPluFileConfigGroupId leichMehlPluFileConfigGroupId;

	@NonNull
	String targetFieldName;

	@NonNull
	TargetFieldType targetFieldType;

	@NonNull
	String replaceRegExp;

	@NonNull
	String replacement;

	@NonNull
	ReplacementSource replacementSource;

	@Builder
	public ExternalSystemLeichMehlPluFileConfig(
			@NonNull final ExternalSystemLeichMehlPluFileConfigId id,
			@NonNull final LeichMehlPluFileConfigGroupId leichMehlPluFileConfigGroupId,
			@NonNull final String targetFieldName,
			@NonNull final TargetFieldType targetFieldType,
			@Nullable final String replaceRegExp,
			@NonNull final String replacement,
			@NonNull final ReplacementSource replacementSource)
	{
		this.replaceRegExp = CoalesceUtil.coalesceNotNull(replaceRegExp, DEFAULT_REPLACEMENT_REGEX);

		this.id = id;
		this.leichMehlPluFileConfigGroupId = leichMehlPluFileConfigGroupId;
		this.targetFieldName = targetFieldName;
		this.targetFieldType = targetFieldType;
		this.replacement = replacement;
		this.replacementSource = replacementSource;
	}
}
