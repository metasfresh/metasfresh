package de.metas.ui.web;

import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.text.MapFormat;

import com.google.common.collect.ImmutableMap;

import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

/**
 * Central place for building any frontend URL.
 * Used by BLs which are sending messages, mails etc.
 */
public class WebuiURLs
{
	public static final WebuiURLs newInstance()
	{
		return new WebuiURLs();
	}

	// services
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	public static final String PARAM_windowId = "windowId";
	public static final String PARAM_documentId = "recordId";
	public static final String PARAM_viewId = "viewId";

	private static final String SYSCONFIG_FRONTEND_URL = "webui.frontend.url";

	private static final String SYSCONFIG_DOCUMENT_PATH = "webui.frontend.url.document";
	private static final String DEFAULT_DOCUMENT_PATH = "/window/{" + PARAM_windowId + "}/{" + PARAM_documentId + "}";

	private static final String SYSCONFIG_VIEW_PATH = "webui.frontend.url.view";
	private static final String DEFAULT_VIEW_PATH = "/window/{" + PARAM_windowId + "}?viewId={" + PARAM_viewId + "}";

	public String getDocumentUrl(@NonNull final String windowId, @NonNull final String documentId)
	{
		final String url = getDocumentUrl();
		if (url == null)
		{
			return null;
		}

		return MapFormat.format(url, ImmutableMap.<String, Object> builder()
				.put(WebuiURLs.PARAM_windowId, windowId)
				.put(WebuiURLs.PARAM_documentId, documentId)
				.build());
	}

	/**
	 * @return e.g. https://webui/window/{windowId}/{documentId}
	 */
	public String getDocumentUrl()
	{
		final String url = getFrontendURL();
		if (url == null)
		{
			return null;
		}

		final String documentPath = sysConfigBL.getValue(SYSCONFIG_DOCUMENT_PATH, DEFAULT_DOCUMENT_PATH);
		return url + documentPath;
	}

	public String getViewUrl(final int adWindowId, @NonNull final String viewId)
	{
		Check.assumeGreaterThanZero(adWindowId, "adWindowId");
		return getViewUrl(String.valueOf(adWindowId), viewId);
	}

	public String getViewUrl(@NonNull final String windowId, @NonNull final String viewId)
	{
		final String url = getViewUrl();
		if (url == null)
		{
			return null;
		}

		return MapFormat.format(url, ImmutableMap.<String, Object> builder()
				.put(WebuiURLs.PARAM_windowId, windowId)
				.put(WebuiURLs.PARAM_viewId, viewId)
				.build());
	}

	/**
	 * @return e.g. https://webui/window/{windowId}?viewId={viewId}
	 */
	public String getViewUrl()
	{
		final String url = getFrontendURL();
		if (url == null)
		{
			return null;
		}

		final String documentPath = sysConfigBL.getValue(SYSCONFIG_VIEW_PATH, DEFAULT_VIEW_PATH);
		return url + documentPath;
	}

	/**
	 *
	 * @return e.g. https://webui
	 */
	private String getFrontendURL()
	{
		final String url = sysConfigBL.getValue(SYSCONFIG_FRONTEND_URL, "");
		if (Check.isEmpty(url, true) || "-".equals(url))
		{
			return null;
		}

		return url.trim();
	}
}
