/**
 *
 */
package de.metas.adempiere.report.jasper;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_AD_Process;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.report.engine.ReportContext;
import de.metas.security.RoleId;
import de.metas.security.UserAuthToken;
import de.metas.security.UserAuthTokenRepository;
import de.metas.user.UserId;
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

	private final UserAuthTokenRepository userAuthTokenRepo;
	private final JsonDataSourceRepository repository;

	public JsonDataSourceService(@NonNull final JsonDataSourceRepository repository, @NonNull final UserAuthTokenRepository userAuthTokenRepo)
	{
		this.repository = repository;
		this.userAuthTokenRepo =userAuthTokenRepo;
	}

	public InputStream getInputStream(@NonNull final ReportContext reportContext) throws JRException
	{
		//
		// get authorization
		final UserId userId = Env.getLoggedUserId();
		final RoleId roleId = Env.getLoggedRoleId();
		final UserAuthToken token = userAuthTokenRepo.retrieveByUserId(userId, roleId);

		//
		// create the json data source
		final JsonDataSourceRequest request = JsonDataSourceRequest.builder()
				.type(reportContext.getType())
				.sql(reportContext.getSQLStatement())
				.JSONPath(reportContext.getJSONPath())
				.authenticationToken(token.getAuthToken())
				.build();

		final InputStream is = getURLInputStream(getJasperJsonURL(request), request.getAuthenticationToken());
		return is;
	}

	private static InputStream getURLInputStream(@NonNull final URL reportURL, @NonNull final String token)
	{
		try
		{
			final HttpURLConnection conn = (HttpURLConnection)reportURL.openConnection();
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

	private URL getJasperJsonURL(@NonNull final JsonDataSourceRequest request)
	{
		String url = repository.getAPI_URL();
		final String path = request.getJSONPath();
		final int recordId = request.getRecordId();
		if (url == null || (Check.isEmpty(path, true) || "-".equals(path)) || (recordId  <= 0 ))
		{
			final ITranslatableString errorMsg = Services.get(IMsgBL.class).getTranslatableMsgText(MSG_URLnotValid);
			throw new AdempiereException(errorMsg);
		}

		url = url + recordId;

		URL reportURL;
		try
		{
			reportURL = new URL(url);
		}
		catch (final MalformedURLException e)
		{
			final ITranslatableString errorMsg = Services.get(IMsgBL.class).getTranslatableMsgText(MSG_URLnotValid);
			throw new AdempiereException(errorMsg);
		}

		return reportURL;
	}

	public String retrieveJSON_SQL_Value(@NonNull final ReportContext reportContext)
	{
		return repository.retrieveSQLValue(reportContext);
	}

	public boolean isJasperJSONReport(final ReportContext reportContext)
	{
		return X_AD_Process.TYPE_JasperReportsJSON.equals(reportContext.getType());
	}
}
