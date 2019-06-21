/**
 *
 */
package de.metas.adempiere.report.jasper;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IExpressionFactory;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;
import org.springframework.stereotype.Service;

import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import net.sf.jasperreports.engine.JRException;

/*
 * #%L
 * report-service
 * %%
 * Copyright (C) 2019 metas GmbH
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
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Service
public class JsonDataSourceService
{
	private static final String MSG_URLnotValid = "URLnotValid";

	private final JsonDataSourceRepository repository;

	public JsonDataSourceService(@NonNull final JsonDataSourceRepository repository)
	{
		this.repository = repository;
	}

	public InputStream getInputStream(@NonNull final JsonDataSourceRequest request) throws JRException
	{
		final InputStream is = getURLInputStream(getJasperJSONURL(request), request.getToken());
		return is;
	}

	private static InputStream getURLInputStream(@NonNull final String reportURL, @NonNull final String token)
	{
		try
		{
			final URL url = new URL(reportURL);
			final HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestProperty("Authorization", token);
			conn.setRequestMethod("GET");
			return conn.getInputStream();
		}
		catch (final IOException e)
		{
			final ITranslatableString errorMsg = Services.get(IMsgBL.class).getTranslatableMsgText(MSG_URLnotValid);
			throw new AdempiereException(errorMsg);
		}
	}

	private String getJasperJSONURL(@NonNull final JsonDataSourceRequest request)
	{
		String url = repository.getAPI_URL();
		if (url == null)
		{
			return null;
		}

		final String path = request.getJSONPath();
		if (Check.isEmpty(path, true) || "-".equals(path))
		{
			return null;
		}

		// parse variables TODO
		final IStringExpression pathExpression = Services.get(IExpressionFactory.class).compile(path, IStringExpression.class);
		final Evaluatee evalCtx = Evaluatees.ofCtx(Env.getCtx());
		final String evaluatedPath = pathExpression.evaluate(evalCtx, OnVariableNotFound.ReturnNoResult);

		if (pathExpression.isNoResult(evaluatedPath))
		{
			// expression could not be evaluated
			return null;
		}

		url = url + evaluatedPath;

		return url;
	}
}
