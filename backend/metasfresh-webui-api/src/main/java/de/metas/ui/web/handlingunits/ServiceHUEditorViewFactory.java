/*
 * #%L
 * metasfresh-webui-api
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

package de.metas.ui.web.handlingunits;

import com.google.common.collect.ImmutableList;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.view.SqlViewFactory;
import de.metas.ui.web.view.ViewFactory;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.descriptor.annotation.ViewColumnHelper;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.MediaType;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@ViewFactory(windowId = WEBUI_HU_Constants.WEBUI_SERVICE_HU_Window_ID_String, viewTypes = { JSONViewDataType.grid, JSONViewDataType.includedView })
public class ServiceHUEditorViewFactory extends HUEditorViewFactoryTemplate
{

	@Autowired
	protected ServiceHUEditorViewFactory(final Optional<List<HUEditorViewCustomizer>> viewCustomizers)
	{
		super(viewCustomizers.orElse(ImmutableList.of()));
	}

	@Override
	protected void customizeHUEditorView(final HUEditorViewBuilder huViewBuilder)
	{
		if (huViewBuilder.isUseAutoFilters() && huViewBuilder.getFilters().isEmpty())
		{
			final List<DocumentFilter> autoFilters = SqlViewFactory.createAutoFilters(huViewBuilder.getFilterDescriptors().getAll());
			huViewBuilder.setFilters(DocumentFilterList.ofList(autoFilters));
		}
	}

	protected void customizeViewLayout(
			@NonNull final ViewLayout.Builder viewLayoutBuilder,
			final JSONViewDataType viewDataType)
	{
		viewLayoutBuilder
				.clearElements()
				.addElementsFromViewRowClassAndFieldNames(HUEditorRow.class,
						viewDataType,
						ViewColumnHelper.ClassViewColumnOverrides.builder(HUEditorRow.FIELDNAME_HUCode).restrictToMediaType(MediaType.SCREEN).build(),
						ViewColumnHelper.ClassViewColumnOverrides.ofFieldName(HUEditorRow.FIELDNAME_Locator),
						ViewColumnHelper.ClassViewColumnOverrides.ofFieldName(HUEditorRow.FIELDNAME_Product),
						ViewColumnHelper.ClassViewColumnOverrides.builder(HUEditorRow.FIELDNAME_PackingInfo).restrictToMediaType(MediaType.SCREEN).build(),
						ViewColumnHelper.ClassViewColumnOverrides.ofFieldName(HUEditorRow.FIELDNAME_QtyCU),
						ViewColumnHelper.ClassViewColumnOverrides.ofFieldName(HUEditorRow.FIELDNAME_UOM),
						ViewColumnHelper.ClassViewColumnOverrides.ofFieldName(HUEditorRow.FIELDNAME_SerialNo),
						ViewColumnHelper.ClassViewColumnOverrides.ofFieldName(HUEditorRow.FIELDNAME_ServiceContract),
						ViewColumnHelper.ClassViewColumnOverrides.builder(HUEditorRow.FIELDNAME_HUStatus).restrictToMediaType(MediaType.SCREEN).build());
	}

	/**
	 * This view is not configuration dependent always should be false to execute the customizeViewLayout method
	 * @return
	 */
	protected boolean isAlwaysUseSameLayout()
	{
		return false;
	}
}
