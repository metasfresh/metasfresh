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

package de.metas.ui.web.ddorder;

import com.google.common.collect.ImmutableList;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.handlingunits.HUEditorViewFactoryTemplate;
import de.metas.ui.web.view.ViewFactory;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.descriptor.annotation.ViewColumnHelper.ClassViewColumnOverrides;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.MediaType;
import de.metas.ui.web.window.datatypes.WindowId;

@ViewFactory(windowId = HUsToMoveViewFactory.WINDOW_ID_STRING, viewTypes = { JSONViewDataType.grid, JSONViewDataType.includedView })
public class HUsToMoveViewFactory extends HUEditorViewFactoryTemplate
{
	public static final String WINDOW_ID_STRING = "husToMove";
	public static final WindowId WINDOW_ID = WindowId.fromJson(WINDOW_ID_STRING);

	public HUsToMoveViewFactory()
	{
		super(ImmutableList.of(HUsToMoveHUEditorViewCustomizer.instance));
	}

	@Override
	protected void customizeViewLayout(final ViewLayout.Builder viewLayoutBuilder, final JSONViewDataType viewDataType)
	{
		viewLayoutBuilder
				.clearElements()
				.addElementsFromViewRowClassAndFieldNames(HUEditorRow.class,
						viewDataType,
						ClassViewColumnOverrides.builder(HUEditorRow.FIELDNAME_HUCode).restrictToMediaType(MediaType.SCREEN).build(),
						ClassViewColumnOverrides.ofFieldName(HUEditorRow.FIELDNAME_Product),
						ClassViewColumnOverrides.ofFieldName(HUEditorRow.FIELDNAME_Locator),
						ClassViewColumnOverrides.ofFieldName(HUEditorRow.FIELDNAME_QtyCU),
						ClassViewColumnOverrides.ofFieldName(HUEditorRow.FIELDNAME_UOM),
						ClassViewColumnOverrides.builder(HUEditorRow.FIELDNAME_HU_UnitType).restrictToMediaType(MediaType.SCREEN).build(),
						ClassViewColumnOverrides.builder(HUEditorRow.FIELDNAME_PackingInfo).restrictToMediaType(MediaType.SCREEN).build(),
						ClassViewColumnOverrides.builder(HUEditorRow.FIELDNAME_HUStatus).restrictToMediaType(MediaType.SCREEN).build());
	}
}
