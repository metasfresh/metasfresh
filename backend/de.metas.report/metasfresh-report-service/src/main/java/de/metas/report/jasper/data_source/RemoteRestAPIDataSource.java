/**
 *
 */
package de.metas.report.jasper.data_source;

import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.logging.LogManager;
import de.metas.process.ProcessParams;
import de.metas.report.server.ReportConstants;
import de.metas.report.server.ReportContext;
import de.metas.security.RoleId;
import de.metas.security.UserAuthToken;
import de.metas.security.UserAuthTokenRepository;
import de.metas.user.UserId;
import de.metas.util.FileUtil;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import net.sf.jasperreports.engine.query.JsonQLQueryExecuterFactory;
import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IExpressionFactory;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
@Component
public class RemoteRestAPIDataSource implements ReportDataSource
{
	private static final Logger logger = LogManager.getLogger(RemoteRestAPIDataSource.class);
	private final IExpressionFactory expressionFactory = Services.get(IExpressionFactory.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final UserAuthTokenRepository userAuthTokenRepo;

	private static final String SYSCONFIG_JSON_API_URL = "API_URL";
	private static final AdMessageKey MSG_URLNotValid = AdMessageKey.of("URLnotValid");
	private static final String PARAM_SQL_VALUE = "SQL_VALUE";

	public RemoteRestAPIDataSource(
			@NonNull final UserAuthTokenRepository userAuthTokenRepo)
	{
		this.userAuthTokenRepo = userAuthTokenRepo;
	}

	@Override
	public Map<String, Object> createJRParameters(@NonNull final ReportContext reportContext)
	{
		final HashMap<String, Object> jrParameters = new HashMap<>();

		final String sql_value = retrieveJsonSqlValue(reportContext);
		if (sql_value != null)
		{
			jrParameters.put(PARAM_SQL_VALUE, sql_value);
		}

		final String json = callRemoteAPIAndGetJsonString(reportContext);

		//
		// We must provide the json as parameter input stream
		// See https://stackoverflow.com/questions/33300592/how-to-fill-report-using-json-datasource-without-getting-null-values/33301039
		// See http://jasperreports.sourceforge.net/sample.reference/jsondatasource/
		jrParameters.put(JsonQLQueryExecuterFactory.JSON_INPUT_STREAM, new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8)));
		jrParameters.put("debug." + ReportConstants.REPORT_PARAM_JSON_DATA, json); // putting it only for debug reasons

		return jrParameters;
	}

	private UserAuthToken getUserAuthToken()
	{
		return userAuthTokenRepo.retrieveByUserId(UserId.JSON_REPORTS, RoleId.JSON_REPORTS);
	}

	private String callRemoteAPIAndGetJsonString(@NonNull final ReportContext reportContext)
	{
		final URL apiUrl = getAPIUrl(reportContext);
		final UserAuthToken token = getUserAuthToken();

		try
		{
			final HttpURLConnection conn = (HttpURLConnection)apiUrl.openConnection();
			conn.setRequestProperty("Authorization", token.getAuthToken());
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestMethod("GET");

			final ByteArrayOutputStream baos = new ByteArrayOutputStream();
			FileUtil.copy(conn.getInputStream(), baos);
			final byte[] data = baos.toByteArray();
			return new String(data, StandardCharsets.UTF_8);
		}
		catch (final IOException e)
		{
			final ITranslatableString errorMsg = msgBL.getTranslatableMsgText(MSG_URLNotValid);
			throw new AdempiereException(errorMsg, e)
					.setParameter("apiUrl", apiUrl)
					.setParameter("token", token);
		}
	}

	private URL getAPIUrl(@NonNull final ReportContext reportContext)
	{
		final String apiBaseURL = getAPIBaseUrl();
		final String apiPath = getAPIPath(reportContext);

		try
		{
			return new URL(apiBaseURL + apiPath);
		}
		catch (final MalformedURLException e)
		{
			final ITranslatableString errorMsg = msgBL.getTranslatableMsgText(MSG_URLNotValid);
			throw new AdempiereException(errorMsg, e);
		}
	}

	private String getAPIBaseUrl()
	{
		final String url = StringUtils.trimBlankToNull(sysConfigBL.getValue(SYSCONFIG_JSON_API_URL, ""));
		if (url == null || "-".equals(url))
		{
			logger.warn("Sysconfig {} is not configured. Report will not work", SYSCONFIG_JSON_API_URL);
			throw new AdempiereException(MSG_URLNotValid)
					.appendParametersToMessage()
					.setParameter("reason", "Sysconfig `" + SYSCONFIG_JSON_API_URL + "` is not configured");
		}

		return url;
	}

	private String getAPIPath(final @NonNull ReportContext reportContext)
	{
		final String path = StringUtils.trimBlankToNull(reportContext.getJSONPath());
		if (path == null || "-".equals(path))
		{
			throw new AdempiereException(MSG_URLNotValid)
					.appendParametersToMessage()
					.setParameter("reason", "JSONPath is not set");
		}

		final IStringExpression pathExpression = expressionFactory.compile(path, IStringExpression.class);
		final Evaluatee evalCtx = createEvalCtx(reportContext);
		return pathExpression.evaluate(evalCtx, OnVariableNotFound.Fail);
	}

	@Nullable
	private String retrieveJsonSqlValue(@NonNull final ReportContext reportContext)
	{
		//
		// Get SQL
		final String sql = StringUtils.trimBlankToNull(reportContext.getSQLStatement());
		if (sql == null)
		{
			return null;
		}

		// Parse the SQL Statement
		final IStringExpression sqlExpression = expressionFactory.compile(sql, IStringExpression.class);
		final Evaluatee evalCtx = createEvalCtx(reportContext);
		final String sqlFinal = sqlExpression.evaluate(evalCtx, OnVariableNotFound.Fail);

		return DB.getSQLValueStringEx(ITrx.TRXNAME_ThreadInherited, sqlFinal);
	}

	private static Evaluatee createEvalCtx(@NonNull final ReportContext reportContext)
	{
		final ArrayList<Evaluatee> contexts = new ArrayList<>();

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
}
