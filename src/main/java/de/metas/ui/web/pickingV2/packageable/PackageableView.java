package de.metas.ui.web.pickingV2.packageable;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;

import de.metas.i18n.ITranslatableString;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.template.AbstractCustomView;
import de.metas.ui.web.window.datatypes.DocumentId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class PackageableView extends AbstractCustomView<PackageableRow>
{
	public static PackageableView cast(final IView view)
	{
		return (PackageableView)view;
	}

	private final ImmutableList<RelatedProcessDescriptor> relatedProcessDescriptors;

	@Builder
	private PackageableView(
			@NonNull final ViewId viewId,
			@Nullable final ITranslatableString description,
			@NonNull final PackageableRowsData rowsData,
			@NonNull @Singular final ImmutableList<RelatedProcessDescriptor> relatedProcessDescriptors,
			@NonNull final DocumentFilterDescriptorsProvider viewFilterDescriptors)
	{
		super(viewId, description, rowsData, viewFilterDescriptors);
		this.relatedProcessDescriptors = relatedProcessDescriptors;
	}

	@Override
	public String getTableNameOrNull(final DocumentId documentId)
	{
		return null;
	}

	@Override
	protected PackageableRowsData getRowsData()
	{
		return PackageableRowsData.cast(super.getRowsData());
	}

	@Override
	public DocumentFilterList getStickyFilters()
	{
		return getRowsData().getStickyFilters();
	}

	@Override
	public DocumentFilterList getFilters()
	{
		return getRowsData().getFilters();
	}

	@Override
	public List<RelatedProcessDescriptor> getAdditionalRelatedProcessDescriptors()
	{
		return relatedProcessDescriptors;
	}
}
