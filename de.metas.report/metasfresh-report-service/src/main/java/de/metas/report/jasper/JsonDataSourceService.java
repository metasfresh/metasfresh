/**
 *
 */
package de.metas.report.jasper;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IExpressionFactory;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;
import org.springframework.stereotype.Service;

import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.process.ProcessParams;
import de.metas.process.ProcessType;
import de.metas.report.server.ReportContext;
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
	private static final AdMessageKey MSG_URLnotValid = AdMessageKey.of("URLnotValid");

	private final UserAuthTokenRepository userAuthTokenRepo;
	private final JsonDataSourceRepository repository;

	public JsonDataSourceService(
			@NonNull final JsonDataSourceRepository repository,
			@NonNull final UserAuthTokenRepository userAuthTokenRepo)
	{
		this.repository = repository;
		this.userAuthTokenRepo = userAuthTokenRepo;
	}

	public InputStream getInputStream(@NonNull final ReportContext reportContext) throws JRException
	{
		//
		// get authorization
		final UserAuthToken token = getUserAuhToken();

		//
		// create the json data source
		final JsonDataSourceRequest request = JsonDataSourceRequest.builder()
				.reportContext(reportContext)
				.authenticationToken(token.getAuthToken())
				.build();

		final InputStream is = getURLInputStream(getJasperJsonURL(request), request.getAuthenticationToken());
		return is;
	}

	private UserAuthToken getUserAuhToken()
	{
		final UserAuthToken token = userAuthTokenRepo.retrieveByUserId(UserId.JSON_REPORTS, RoleId.JSON_REPORTS);

		if (token == null)
		{
			throw new AdempiereException("Invalid token (1)");
		}

		return token;
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
			throw new AdempiereException(errorMsg, e);
		}
	}

	private URL getJasperJsonURL(@NonNull final JsonDataSourceRequest request)
	{
		@NonNull
		final ReportContext reportContext = request.getReportContext();
		String url = repository.getAPIUrlOrNull();
		final String path = reportContext.getJSONPath();

		if (url == null || (Check.isEmpty(path, true) || "-".equals(path)))
		{
			final ITranslatableString errorMsg = Services.get(IMsgBL.class).getTranslatableMsgText(MSG_URLnotValid);
			throw new AdempiereException(errorMsg);
		}

		final IStringExpression sqlExpression = Services.get(IExpressionFactory.class).compile(path, IStringExpression.class);
		final Evaluatee evalCtx = getEvalContext(reportContext);
		final String finalPath = sqlExpression.evaluate(evalCtx, OnVariableNotFound.Fail);

		url = url + finalPath;

		URL reportURL;
		try
		{
			reportURL = new URL(url);
		}
		catch (final MalformedURLException e)
		{
			final ITranslatableString errorMsg = Services.get(IMsgBL.class).getTranslatableMsgText(MSG_URLnotValid);
			throw new AdempiereException(errorMsg, e);
		}

		return reportURL;
	}

	public String retrieveJsonSqlValue(@NonNull final ReportContext reportContext)
	{
		//
		// Get SQL
		final String sql = reportContext.getSQLStatement();

		if (Check.isEmpty(sql, true))
		{
			return null;
		}

		// Parse the SQL Statement
		final IStringExpression sqlExpression = Services.get(IExpressionFactory.class).compile(sql, IStringExpression.class);
		final Evaluatee evalCtx = getEvalContext(reportContext);
		final String sqlFinal = sqlExpression.evaluate(evalCtx, OnVariableNotFound.Fail);

		return repository.retrieveSQLValue(sqlFinal);
	}

	private Evaluatee getEvalContext(@NonNull final ReportContext reportContext)
	{
		final List<Evaluatee> contexts = new ArrayList<>();

		//
		// 1: Add process parameters
		contexts.add(Evaluatees.ofRangeAwareParams(new ProcessParams(reportContext.getProcessInfoParameters())));

		//
		// 2: underlying record
		final String recordTableName = reportContext.getTableNameOrNull();
		final int recordId = reportContext.getRecord_ID();
		if (recordTableName != null && recordId > 0)
		{
			final TableRecordReference recordRef = TableRecordReference.of(recordTableName, recordId);
			final Evaluatee evalCtx = Evaluatees.ofTableRecordReference(recordRef);
			if (evalCtx != null)
			{
				contexts.add(evalCtx);
			}
		}

		//
		// 3: global context
		contexts.add(Evaluatees.ofCtx(Env.getCtx()));

		return Evaluatees.compose(contexts);
	}

	public boolean isJasperJSONReport(final ReportContext reportContext)
	{
		final ProcessType type = reportContext.getType();
		return type.isJasperJson();
	}
}
