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

package de.metas.serviceprovider.github.label;

import com.google.common.collect.ImmutableList;
import de.metas.issue.tracking.github.api.v3.model.Label;
import de.metas.serviceprovider.github.GithubImporterConstants;
import de.metas.util.Check;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;

@Service
public class LabelService
{
	@NonNull
	public ImmutableList<ProcessedLabel> processLabels(@Nullable final List<Label> labelList)
	{
		if (Check.isEmpty(labelList))
		{
			return ImmutableList.of();
		}

		return labelList.stream()
				.map(Label::getName)
				.map(this::processLabel)
				.collect(ImmutableList.toImmutableList());
	}

	private ProcessedLabel processLabel(@NonNull final String label)
	{
		GithubImporterConstants.LabelType currentLabelType;

		final Optional<ProcessedLabel> processedLabel = GithubImporterConstants.LabelType
				.streamKnownLabelTypes()
				.map(labelType -> buildProcessedLabel(labelType, label))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.findFirst();

		return processedLabel.orElseGet(() -> buildUnknownLabel(label));
	}

	@NonNull
	private Optional<ProcessedLabel> buildProcessedLabel(@NonNull final GithubImporterConstants.LabelType labelType, @NonNull final String label)
	{
		final Matcher matcher = labelType.getPattern().matcher(label);

		if (!matcher.matches())
		{
			return Optional.empty();
		}

		final String extractedValue = matcher.group(labelType.getGroupName());

		return Optional.of(ProcessedLabel.builder()
				.labelType(labelType)
				.label(label)
				.extractedValue(extractedValue)
				.build());
	}

	@NonNull
	private ProcessedLabel buildUnknownLabel(@NonNull final String label)
	{
		return ProcessedLabel.builder()
				.labelType(GithubImporterConstants.LabelType.UNKNOWN)
				.label(label)
				.extractedValue(label)
				.build();
	}
}

