package de.metas.ui.web;

import com.google.common.collect.ImmutableMap;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.text.MapFormat;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Map;

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
	public static WebuiURLs newInstance()
	{
		return new WebuiURLs();
	}

	// services
	private static final Logger logger = LogManager.getLogger(WebuiURLs.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	private static final String PARAM_windowId = "windowId";
	private static final String PARAM_documentId = "recordId";
	private static final String PARAM_viewId = "viewId";
	private static final String PARAM_ResetPasswordToken = "token";

	public static final String SYSCONFIG_IsCrossSiteUsageAllowed = "webui.frontend.allow-cross-site-usage";
	
	public static final String SYSCONFIG_FRONTEND_URL = "webui.frontend.url";
	private static final String SYSCONFIG_DOCUMENT_PATH = "webui.frontend.path.document";
	private static final String SYSCONFIG_VIEW_PATH = "webui.frontend.path.view";
	private static final String SYSCONFIG_RESET_PASSWORD_PATH = "webui.frontend.path.resetPassword";

	private static final Map<String, String> defaultsBySysConfigName = ImmutableMap.<String, String> builder()
			.put(SYSCONFIG_DOCUMENT_PATH, "/window/{" + PARAM_windowId + "}/{" + PARAM_documentId + "}")
			.put(SYSCONFIG_VIEW_PATH, "/window/{" + PARAM_windowId + "}?viewId={" + PARAM_viewId + "}")
			.put(SYSCONFIG_RESET_PASSWORD_PATH, "/resetPassword?token={" + PARAM_ResetPasswordToken + "}")
			.build();

	/**
	 *
	 * @return e.g. https://webui
	 */
	@Nullable
	public String getFrontendURL()
	{
		final String url = StringUtils.trimBlankToNull(sysConfigBL.getValue(SYSCONFIG_FRONTEND_URL, ""));
		if (url == null || "-".equals(url))
		{
			logger.warn("{} is not configured. Features like CORS, document links in emails etc will not work", SYSCONFIG_FRONTEND_URL);
			return null;
		}

		return url.trim();
	}

	@Nullable
	private String getFrontendURL(@NonNull final String pathSysConfigName, final Map<String, Object> params)
	{
		String url = getFrontendURL();
		if (url == null)
		{
			return null;
		}

		final String path = StringUtils.trimBlankToNull(sysConfigBL.getValue(pathSysConfigName, defaultsBySysConfigName.get(pathSysConfigName)));
		if (path == null || "-".equals(path))
		{
			return null;
		}

		url = url + path;

		if (params != null && !params.isEmpty())
		{
			url = MapFormat.format(url, params);
		}

		return url;
	}

	@Nullable
	public String getDocumentUrl(@NonNull final AdWindowId windowId, final int documentId)
	{
		return getDocumentUrl(String.valueOf(windowId.getRepoId()), String.valueOf(documentId));
	}

	@Nullable
	public String getDocumentUrl(@NonNull final String windowId, @NonNull final String documentId)
	{
		return getFrontendURL(SYSCONFIG_DOCUMENT_PATH, ImmutableMap.<String, Object> builder()
				.put(WebuiURLs.PARAM_windowId, windowId)
				.put(WebuiURLs.PARAM_documentId, documentId)
				.build());
	}

	@Nullable
	public String getViewUrl(@NonNull final AdWindowId adWindowId, @NonNull final String viewId)
	{
		return getViewUrl(String.valueOf(adWindowId.getRepoId()), viewId);
	}

	@Nullable
	public String getViewUrl(@NonNull final String windowId, @NonNull final String viewId)
	{
		return getFrontendURL(SYSCONFIG_VIEW_PATH, ImmutableMap.<String, Object> builder()
				.put(PARAM_windowId, windowId)
				.put(PARAM_viewId, viewId)
				.build());
	}

	@Nullable
	public String getResetPasswordUrl(final String token)
	{
		Check.assumeNotEmpty(token, "token is not empty");

		return getFrontendURL(SYSCONFIG_RESET_PASSWORD_PATH, ImmutableMap.<String, Object> builder()
				.put(PARAM_ResetPasswordToken, token)
				.build());
	}

	public boolean isCrossSiteUsageAllowed()
	{
		return sysConfigBL.getBooleanValue(SYSCONFIG_IsCrossSiteUsageAllowed, false);
	}
}
