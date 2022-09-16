/*
 * #%L
 * de.metas.serviceprovider.base
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

package de.metas.serviceprovider.external.label;

import com.google.common.collect.ImmutableList;
import de.metas.serviceprovider.github.GithubImporterConstants;
import de.metas.serviceprovider.issue.IssueId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Value
public class LabelCollection
{
	@NonNull
	IssueId issueId;

	@NonNull
	ImmutableList<IssueLabel> issueLabelList;

	@Builder
	public LabelCollection(
			@NonNull final IssueId issueId,
			@NonNull final ImmutableList<IssueLabel> issueLabelList)
	{
		this.issueId = issueId;
		this.issueLabelList = issueLabelList;
	}

	@NonNull
	public Stream<IssueLabel> streamByType(@NonNull final List<GithubImporterConstants.LabelType> labelTypes)
	{
		return issueLabelList.stream()
				.filter(label -> labelTypes.stream().anyMatch(label::matchesType));
	}

	@NonNull
	public ImmutableList<IssueLabel> filter(@NonNull final Predicate<IssueLabel> filter)
	{
		return issueLabelList
				.stream()
				.filter(filter)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	public LabelCollection putLabelByType(@NonNull final IssueLabel labelToAdd, @NonNull final GithubImporterConstants.LabelType labelType)
	{
		final ImmutableList<IssueLabel> updatedLabelList = Stream.concat(Stream.of(labelToAdd), issueLabelList.stream())
				.filter(label -> !label.matchesType(labelType) || label.getValue().equals(labelToAdd.getValue()))
				.collect(ImmutableList.toImmutableList());

		return LabelCollection.builder()
				.issueId(issueId)
				.issueLabelList(updatedLabelList)
				.build();
	}

	public boolean hasLabel(@NonNull final IssueLabel issueLabel)
	{
		return issueLabelList.stream().anyMatch(label -> label.getValue().equals(issueLabel.getValue()));
	}
}
