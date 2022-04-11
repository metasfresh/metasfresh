/*
 * #%L
 * de.metas.ui.web.base
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

package de.metas.ui.web.simulation;

import de.metas.i18n.ITranslatableString;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.simulation.DeactivateAllSimulatedCandidatesEvent;
import de.metas.ui.web.document.filter.provider.NullDocumentFilterDescriptorsProvider;
import de.metas.ui.web.view.IEditableView;
import de.metas.ui.web.view.ViewCloseAction;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.template.AbstractCustomView;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.LookupValuesPage;
import lombok.Builder;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.compiere.util.Env;

import javax.annotation.Nullable;

public class ProductionSimulationView extends AbstractCustomView<ProductionSimulationRow> implements IEditableView
{
	private final PostMaterialEventService postMaterialEventService = SpringContextHolder.instance.getBean(PostMaterialEventService.class);

	@Builder
	public ProductionSimulationView(
			@NonNull final ViewId viewId,
			@Nullable final ITranslatableString description,
			@NonNull final ProductionSimulationRows rows)
	{
		super(viewId, description, rows, NullDocumentFilterDescriptorsProvider.instance);
	}

	@Override
	public LookupValuesPage getFieldTypeahead(final RowEditingContext ctx, final String fieldName, final String query)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public LookupValuesList getFieldDropdown(final RowEditingContext ctx, final String fieldName)
	{
		throw new UnsupportedOperationException();
	}

	@Nullable
	@Override
	public String getTableNameOrNull(@Nullable final DocumentId documentId)
	{
		return null;
	}

	@Override
	public void close(final ViewCloseAction closeAction)
	{
		postMaterialEventService.postEventNow(DeactivateAllSimulatedCandidatesEvent.builder()
													  .eventDescriptor(EventDescriptor.ofClientAndOrg(Env.getClientId(), Env.getOrgId()))
													  .build());
	}

	@Override
	protected ProductionSimulationRows getRowsData()
	{
		return ProductionSimulationRows.cast(super.getRowsData());
	}
}