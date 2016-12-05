package de.metas.ui.web.process.json;

import java.io.Serializable;
import java.util.List;

import org.adempiere.util.GuavaCollectors;

import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.ui.web.process.ProcessInstance;
import de.metas.ui.web.window.datatypes.json.JSONDocumentField;
import de.metas.ui.web.window.datatypes.json.JSONOptions;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
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

@SuppressWarnings("serial")
public class JSONProcessInstance implements Serializable
{
	public static JSONProcessInstance of(final ProcessInstance pinstance, final JSONOptions jsonOpts)
	{
		return new JSONProcessInstance(pinstance, jsonOpts);
	}

	@JsonProperty("pinstanceId")
	private final int pinstanceId;
	@JsonProperty("parameters")
	private final List<JSONDocumentField> parameters;

	private JSONProcessInstance(final ProcessInstance pinstance, final JSONOptions jsonOpts)
	{
		super();
		pinstanceId = pinstance.getAD_PInstance_ID();

		parameters = pinstance.getParameters()
				.stream()
				.map(JSONDocumentField::ofDocumentField)
				.collect(GuavaCollectors.toImmutableList());
	}
}
