package de.metas.ui.web.view;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableMap;

import de.metas.ui.web.handlingunits.HUDocumentViewSelectionFactory;
import de.metas.ui.web.handlingunits.WEBUI_HU_Constants;
import de.metas.ui.web.view.json.JSONCreateDocumentViewRequest;
import de.metas.ui.web.view.json.JSONDocumentViewLayout;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.ui.web.window.datatypes.json.JSONViewDataType;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Service
@Primary
public class CompositeDocumentViewSelectionFactory implements IDocumentViewSelectionFactory
{
	private Map<Integer, IDocumentViewSelectionFactory> factoriesByWindowId = ImmutableMap.of();

	@Autowired
	private SqlDocumentViewSelectionFactory defaultFactory;
	@Autowired
	private HUDocumentViewSelectionFactory huViewFactory;

	public CompositeDocumentViewSelectionFactory()
	{
		super();
	}

	@PostConstruct
	private void init()
	{
		factoriesByWindowId = ImmutableMap.<Integer, IDocumentViewSelectionFactory> builder()
				.put(WEBUI_HU_Constants.WEBUI_HU_Window_ID, huViewFactory)
				.build();
	}

	private final IDocumentViewSelectionFactory getFactory(final int adWindowId)
	{
		return factoriesByWindowId.getOrDefault(adWindowId, defaultFactory);
	}

	@Override
	public JSONDocumentViewLayout getViewLayout(final int adWindowId, final JSONViewDataType viewDataType, final JSONOptions jsonOpts)
	{
		return getFactory(adWindowId).getViewLayout(adWindowId, viewDataType, jsonOpts);
	}

	@Override
	public IDocumentViewSelection createView(final JSONCreateDocumentViewRequest jsonRequest)
	{
		return getFactory(jsonRequest.getAD_Window_ID()).createView(jsonRequest);
	}
}
