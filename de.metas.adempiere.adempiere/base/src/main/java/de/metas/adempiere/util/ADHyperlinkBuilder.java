package de.metas.adempiere.util;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.lang.ITableRecordReference;
import org.apache.commons.lang3.StringEscapeUtils;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.document.engine.IDocumentBL;
import de.metas.logging.LogManager;

public class ADHyperlinkBuilder
{
	public static String PROTOCOL = "http";
	public static String HOST = "adempiere";

	private static final transient Logger logger = LogManager.getLogger(ADHyperlinkBuilder.class);

	public String createShowWindowHTML(final ITableRecordReference recordRef, final int adWindowId)
	{
		if (recordRef == null)
		{
			return "";
		}

		// Retrieve the record
		Object record;
		try
		{
			final IContextAware context = PlainContextAware.newOutOfTrx(Env.getCtx());
			record = recordRef.getModel(context);
		}
		catch (Exception e)
		{
			logger.info("Failed retrieving record for " + recordRef, e);
			return "<" + recordRef.getRecord_ID() + ">";
		}

		String documentNo = Services.get(IDocumentBL.class).getDocumentNo(record);
		final String tableName = InterfaceWrapperHelper.getModelTableName(record);
		final int recordId = InterfaceWrapperHelper.getId(record);
		return createShowWindowHTML(documentNo, tableName, recordId, adWindowId);
	}

	public String createShowWindowHTML(String text, String tableName, int recordId, final int adWindowId)
	{
		Check.assumeNotEmpty(tableName, "tableName not empty");
		final String keyColumnName = InterfaceWrapperHelper.getKeyColumnName(tableName);
		final String whereClause = keyColumnName + "=" + recordId;
		return createShowWindowHTML(text, tableName, adWindowId, whereClause);
	}

	public String createShowWindowHTML(String text, String tableName, final int adWindowId, String whereClause)
	{
		final int AD_Table_ID = Services.get(IADTableDAO.class).retrieveTableId(tableName);
		final Map<String, String> params = new LinkedHashMap<>(); // we use LinkedHashMap because we need a predictable order; tests are depending on this.
		params.put("AD_Table_ID", Integer.toString(AD_Table_ID));
		params.put("WhereClause", whereClause);
		if(adWindowId > 0)
		{
			params.put("AD_Window_ID", Integer.toString(adWindowId));
		}

		final ADHyperlink link = new ADHyperlink(ADHyperlink.Action.ShowWindow, params);
		final URI uri = toURI(link);

		return "<a href=\"" + uri.toString() + "\">"
				+ StringEscapeUtils.escapeHtml4(text)
				+ "</a>";
	}

	public ADHyperlink getADHyperlink(String uri)
	{
		try
		{
			return getADHyperlink(new URI(uri));
		}
		catch (URISyntaxException e)
		{
			throw new AdempiereException(e);
		}

	}

	public ADHyperlink getADHyperlink(final URI uri)
	{
		String protocol = uri.getScheme();
		if (!PROTOCOL.equals(protocol))
			return null;
		String host = uri.getHost();
		if (!HOST.equals(host))
			return null;
		String actionStr = uri.getPath();
		if (actionStr.startsWith("/"))
			actionStr = actionStr.substring(1);
		ADHyperlink.Action action = ADHyperlink.Action.valueOf(actionStr);
		Map<String, String> params = decodeParams(uri.getQuery());
		return new ADHyperlink(action, params);
	}

	private String encodeParams(Map<String, String> params)
	{
		StringBuilder urlParams = new StringBuilder();
		if (params != null && !params.isEmpty())
		{
			for (Map.Entry<String, String> e : params.entrySet())
			{
				if (urlParams.length() > 0)
				{
					// We need to use %26 instead of "&" because of org.compiere.process.ProcessInfo.getSummary(),
					// which strips ampersands
					urlParams.append("%26");
				}
				urlParams.append(encode(e.getKey()));
				urlParams.append("=");
				urlParams.append(encode(e.getValue()));
			}
		}
		return urlParams.toString();
	}

	private Map<String, String> decodeParams(String query)
	{
		Map<String, String> params = new HashMap<String, String>();
		if (Check.isEmpty(query, true))
			return params;
		for (String param : query.split("&"))
		{
			final String key;
			final String value;
			final int idx = param.indexOf("=");
			if (idx < 0)
			{
				key = param;
				value = null;
			}
			else
			{
				key = param.substring(0, idx);
				value = param.substring(idx + 1);
			}
			params.put(key, value);
		}
		return params;
	}

	private URI toURI(ADHyperlink link)
	{
		String urlParams = encodeParams(link.getParameters());
		String urlStr = PROTOCOL + "://" + HOST + "/" + link.getAction().toString() + "?" + urlParams;
		try
		{
			return new URI(urlStr);
		}
		catch (URISyntaxException e)
		{
			throw new AdempiereException(e);
		}
	}

	private String encode(String s)
	{
		try
		{
			return URLEncoder.encode(s, "UTF-8");
		}
		catch (UnsupportedEncodingException e)
		{
			throw new AdempiereException(e);
		}
	}

	private String decode(String s)
	{
		try
		{
			return URLDecoder.decode(s, "UTF-8");
		}
		catch (UnsupportedEncodingException e)
		{
			throw new AdempiereException(e);
		}
	}
}
