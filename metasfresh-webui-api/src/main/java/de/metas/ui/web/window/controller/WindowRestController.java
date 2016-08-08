package de.metas.ui.web.window.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;

import org.compiere.util.Env;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;

import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutDescriptor;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.DocumentCollection;
import de.metas.ui.web.window.model.DocumentField;
import de.metas.ui.web.window.model.DocumentId;
import de.metas.ui.web.window_old.shared.datatype.LookupValue;
import de.metas.ui.web.window_old.shared.datatype.NullValue;

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

@RestController
@RequestMapping(value = WindowRestController.ENDPOINT)
public class WindowRestController
{
	public static final String ENDPOINT = WebConfig.ENDPOINT_ROOT + "/window";

	private final DocumentCollection documentCollection = new DocumentCollection();

	private final void autologin()
	{
		// FIXME: only for testing
		final Properties ctx = Env.getCtx();
		Env.setContext(ctx, Env.CTXNAME_AD_Client_ID, 1000000);
		Env.setContext(ctx, Env.CTXNAME_AD_Org_ID, 1000000);
		Env.setContext(ctx, Env.CTXNAME_AD_Role_ID, 1000000);
		Env.setContext(ctx, Env.CTXNAME_AD_User_ID, 100);
		Env.setContext(ctx, Env.CTXNAME_AD_Language, "de_DE");
	}

	@RequestMapping(value = "/layout", method = RequestMethod.GET)
	public DocumentLayoutDescriptor layout(@RequestParam(name = "type", required = true) final int adWindowId)
	{
		autologin();

		return documentCollection.getDocumentDescriptorFactory()
				.getDocumentDescriptor(adWindowId)
				.getLayout();
	}

	@RequestMapping(value = "/data", method = RequestMethod.GET)
	public List<Map<String, Object>> data(@RequestParam(name = "type", required = true) final int adWindowId, @RequestParam(name = "id", defaultValue = DocumentId.NEW_ID_STRING) final String idStr)
	{
		final DocumentId documentId = DocumentId.of(idStr);
		final Document document = documentCollection.getDocument(adWindowId, documentId);

		return toJSON(document);
	}

	@RequestMapping(value = "/commit", method = RequestMethod.PATCH)
	public void commit(@RequestParam(name = "type", required = true) final int adWindowId, @RequestParam(name = "id", defaultValue = DocumentId.NEW_ID_STRING) final String idStr)
	{
		throw new UnsupportedOperationException("not implemented"); // TODO
	}

	private static List<Map<String, Object>> toJSON(final Document document)
	{
		final Collection<DocumentField> fields = document.getFields();
		final List<Map<String, Object>> list = new ArrayList<>(fields.size());
		for (final DocumentField field : fields)
		{
			final Map<String, Object> map = toJSONValue(field);
			list.add(map);
		}

		return list;
	}

	private static Map<String, Object> toJSONValue(final DocumentField field)
	{
		final Map<String, Object> map = new LinkedHashMap<>();
		final String name = field.getName();

		final Object value = field.getValue();
		final Object valueJSON = toJSONValue(value, field.getDescriptor());

		map.put("field", name);
		map.put("value", valueJSON);
		map.put("mandatory", String.valueOf(field.isMandatory()));
		map.put("readonly", String.valueOf(field.isReadonly()));
		map.put("displayed", String.valueOf(field.isDisplayed()));
		return map;
	}

	private static final Object toJSONValue(final Object value, final DocumentFieldDescriptor documentFieldDescriptor)
	{
		if (NullValue.isNull(value))
		{
			return null;
		}
		else if (value instanceof java.util.Date)
		{
			final java.util.Date valueDate = (java.util.Date)value;
			return toJSONValue(valueDate);
		}
		else if (value instanceof LookupValue)
		{
			final LookupValue lookupValue = (LookupValue)value;
			return toJSONValue(lookupValue);
		}
		else
		{
			return value.toString();
		}
	}

	private static String toJSONValue(final java.util.Date valueDate)
	{
		final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		final String valueStr = dateFormat.format(valueDate);
		return valueStr;
	}

	private static final Object toJSONValue(final LookupValue lookupValue)
	{
		final Object keyObj = lookupValue.getId();
		final String key = keyObj == null ? null : keyObj.toString();

		final String name = lookupValue.getDisplayName();

		final Map<String, String> json = ImmutableMap.of(key, name);
		return json;
	}
}
