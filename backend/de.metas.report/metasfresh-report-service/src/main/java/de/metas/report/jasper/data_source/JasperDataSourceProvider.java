package de.metas.report.jasper.data_source;

import de.metas.process.ProcessType;
import de.metas.report.server.ReportContext;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

@Service
public class JasperDataSourceProvider
{
	private final SqlDataSource sqlDataSource;
	private final JsonDataSource jsonDataSource;
	private final RemoteRestAPIDataSource remoteRestAPIDataSource;

	public JasperDataSourceProvider(
			@NonNull final SqlDataSource sqlDataSource,
			@NonNull final JsonDataSource jsonDataSource,
			@NonNull final RemoteRestAPIDataSource remoteRestAPIDataSource
	)
	{
		this.sqlDataSource = sqlDataSource;
		this.jsonDataSource = jsonDataSource;
		this.remoteRestAPIDataSource = remoteRestAPIDataSource;
	}

	public ReportDataSource getDataSource(@NonNull final ReportContext reportContext)
	{
		final ProcessType reportType = reportContext.getType();
		if (reportType == ProcessType.JasperReportsSQL)
		{
			return sqlDataSource;
		}
		else if (reportType == ProcessType.JasperReportsJSON)
		{
			final String jsonPath = StringUtils.trimBlankToNull(reportContext.getJSONPath());
			if (jsonPath == null || "-".equals(jsonPath))
			{
				return jsonDataSource;
			}
			else
			{
				return remoteRestAPIDataSource;
			}
		}
		else
		{
			throw new AdempiereException("Cannot determine data source for " + reportContext);
		}
	}
}
