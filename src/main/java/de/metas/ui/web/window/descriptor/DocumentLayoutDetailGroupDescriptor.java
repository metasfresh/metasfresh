package de.metas.ui.web.window.descriptor;

import static org.compiere.util.Util.coalesce;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.ImmutableTranslatableString;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Value
public class DocumentLayoutDetailGroupDescriptor
{
	ITranslatableString caption;

	ITranslatableString description;

	String internalName;

	DetailGroupId detailGroupId;

	boolean displayTabHeaderIfSingle;

	List<DocumentLayoutDetailDescriptor> details;

	@Builder
	private DocumentLayoutDetailGroupDescriptor(
			@NonNull final ITranslatableString caption,
			@Nullable final ITranslatableString description,
			@NonNull final String internalName,
			@NonNull final DetailGroupId detailGroupId,
			final boolean displayTabHeaderIfSingle,
			@Singular final List<DocumentLayoutDetailDescriptor> details)
	{
		this.caption = caption;
		this.description = coalesce(description, ImmutableTranslatableString.empty());
		this.internalName = internalName;
		this.detailGroupId = detailGroupId;
		this.displayTabHeaderIfSingle = displayTabHeaderIfSingle;

		this.details = details
				.stream()
				.filter(Predicates.notNull())
				.filter(Predicates.not(DocumentLayoutDetailDescriptor::isEmpty))
				.collect(ImmutableList.toImmutableList());
	}

	public static DocumentLayoutDetailGroupDescriptor ofSingleLayoutDetail(@NonNull final DocumentLayoutDetailDescriptor layoutDetail)
	{
		final DetailGroupId detailGroupId = DetailGroupId
				.fromTabGroupId(layoutDetail.getDetailId().getIntValue());

		return builder()
				.displayTabHeaderIfSingle(false)
				.caption(layoutDetail.getGridLayout().getCaption())
				.description(layoutDetail.getGridLayout().getDescription())
				.internalName(layoutDetail.getInternalName())
				.detailGroupId(detailGroupId)
				.detail(layoutDetail)
				.build();
	}
}
