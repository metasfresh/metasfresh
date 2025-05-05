/*
 * #%L
 * de.metas.ui.web.base
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

package de.metas.ui.web.project.step;

import com.google.common.collect.ImmutableList;
import de.metas.i18n.ITranslatableString;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.ui.web.document.filter.provider.NullDocumentFilterDescriptorsProvider;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewRowOverrides;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.template.AbstractCustomView;
import de.metas.ui.web.window.datatypes.DocumentId;
import lombok.Builder;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.List;

public class ResolveReservationView extends AbstractCustomView<WOProjectStepResourceRow> implements IViewRowOverrides
{
	private final ImmutableList<RelatedProcessDescriptor> processes;

	@Nullable
	public static ResolveReservationView cast(@Nullable final IView view)
	{
		return (ResolveReservationView)view;
	}

	@Builder
	public ResolveReservationView(
			@NonNull final ViewId viewId,
			@NonNull final WOProjectStepResourceRows rows,
			@Nullable final ITranslatableString description,
			@Nullable final List<RelatedProcessDescriptor> processes)
	{
		super(viewId, description, rows, NullDocumentFilterDescriptorsProvider.instance);

		this.processes = processes != null ? ImmutableList.copyOf(processes) : ImmutableList.of();
	}

	@Nullable
	@Override
	public String getTableNameOrNull(@Nullable final DocumentId documentId)
	{
		return null;
	}

	@Override
	public List<RelatedProcessDescriptor> getAdditionalRelatedProcessDescriptors()
	{
		return processes;
	}

	@Override
	protected WOProjectStepResourceRows getRowsData()
	{
		return WOProjectStepResourceRows.cast(super.getRowsData());
	}
}
