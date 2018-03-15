package de.metas.ui.web.handlingunits;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.ImmutableList;

import de.metas.ui.web.view.ViewFactory;
import de.metas.ui.web.view.json.JSONViewDataType;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

@ViewFactory(windowId = WEBUI_HU_Constants.WEBUI_HU_Window_ID_String, viewTypes = { JSONViewDataType.grid, JSONViewDataType.includedView })
public class DefaultHUEditorViewFactory extends HUEditorViewFactoryTemplate
{
	@Autowired
	protected DefaultHUEditorViewFactory(final Optional<List<HUEditorViewCustomizer>> viewCustomizers)
	{
		super(viewCustomizers.orElse(ImmutableList.of()));
	}

}
