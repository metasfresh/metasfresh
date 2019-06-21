/**
 *
 */
package de.metas.adempiere.report.jasper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.X_AD_Process;
import org.compiere.util.DB;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import net.sf.jasperreports.engine.JRResultSetDataSource;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Repository
public class JsonDataSourceRepository
{
	private static final String SYSCONFIG_RESTAPI_URL = "API_URL";
	private final transient Logger log = LogManager.getLogger(JsonDataSourceRepository.class);


	public String getAPI_URL()
	{
		final String url = Services.get(ISysConfigBL.class).getValue(SYSCONFIG_RESTAPI_URL, "");
		if (Check.isEmpty(url, true) || "-".equals(url))
		{
			log.warn("{} is not configured. JAsper JSOn reports will not work", SYSCONFIG_RESTAPI_URL);
			return null;
		}

		return url.trim();
	}

	public JRResultSetDataSource  retrieveResultSetDataSourceIfNeeded(@NonNull final JsonDataSourceRequest request)
	{
		//
		// Get SQL
		final String sql = request.getSql();
		if (!X_AD_Process.TYPE_JasperReportsJSON.equals(request.getType()) || Check.isEmpty(sql, true))
		{
			return null;
		}

		// TODO
		// Parse the SQL Statement
		//		final IStringExpression sqlExpression = Services.get(IExpressionFactory.class).compile(sql, IStringExpression.class);
		//		final Evaluatee evalCtx = createEvaluationContext(reportContext);


		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_ThreadInherited);
			rs = pstmt.executeQuery();

			//
			// Create & return the data source
			return new JRResultSetDataSource(rs);
		}
		catch (final SQLException e)
		{
			throw new DBException(e, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
	}
}
