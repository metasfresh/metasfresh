package de.metas.report.jasper.data_source;

import com.google.common.collect.ImmutableMap;
import de.metas.report.server.ReportContext;
import de.metas.security.IUserRolePermissions;
import de.metas.security.permissions.Access;
import lombok.NonNull;
import org.compiere.util.DB;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.sql.Connection;
import java.util.Map;
import java.util.Optional;

@Component
public class SqlDataSource implements ReportDataSource
{
	@Override
	public Map<String, Object> createJRParameters(@NonNull final ReportContext reportContext)
	{
		return ImmutableMap.of();
	}

	@Override
	public Optional<Connection> createDBConnection(
			@NonNull final ReportContext reportContext,
			@Nullable final String troubleshooting_reportPath)
	{
		Connection conn = null;
		try
		{

			final String sqlQueryInfo = buildSqlQueryInfo(reportContext, troubleshooting_reportPath);
			final String securityWhereClause = getSecurityWhereClause(reportContext);
			conn = DB.getConnectionRW();
			return Optional.of(new JasperJdbcConnection(conn, sqlQueryInfo, securityWhereClause));
		}
		catch (final Error | RuntimeException ex)
		{
			DB.close(conn);
			throw ex;
		}
	}

	@NonNull
	private static String buildSqlQueryInfo(final @NonNull ReportContext reportContext, final @Nullable String troubleshooting_reportPath)
	{
		return "jasper main report=" + troubleshooting_reportPath
				+ ", AD_PInstance_ID=" + reportContext.getPinstanceId();
	}

	@Nullable
	private static String getSecurityWhereClause(final @NonNull ReportContext reportContext)
	{
		final String securityWhereClause;
		if (reportContext.isApplySecuritySettings())
		{
			final IUserRolePermissions userRolePermissions = reportContext.getUserRolePermissions();
			final String tableName = reportContext.getTableNameOrNull();
			securityWhereClause = userRolePermissions.getOrgWhere(tableName, Access.READ).orElse(null);
		}
		else
		{
			securityWhereClause = null;
		}
		return securityWhereClause;
	}

}
