package de.metas.report.jasper.client.process;

import de.metas.report.ExecuteReportStrategy;
import de.metas.report.ReportStarter;

public class JasperReportStarter extends ReportStarter
{
	@Override
	protected ExecuteReportStrategy getExecuteReportStrategy()
	{
		return new JasperExecuteReportStrategy();
	}
}
