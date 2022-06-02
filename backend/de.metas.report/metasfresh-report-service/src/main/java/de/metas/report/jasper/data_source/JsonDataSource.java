package de.metas.report.jasper.data_source;

import com.google.common.collect.ImmutableMap;
import de.metas.process.ProcessParams;
import de.metas.report.server.ReportConstants;
import de.metas.report.server.ReportContext;
import de.metas.util.StringUtils;
import lombok.NonNull;
import net.sf.jasperreports.engine.query.JsonQLQueryExecuterFactory;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
public class JsonDataSource implements ReportDataSource
{
	@Override
	public Map<String, Object> createJRParameters(@NonNull final ReportContext reportContext)
	{
		final ProcessParams processParams = ProcessParams.of(reportContext.getProcessInfoParameters());

		return ImmutableMap.of(
				JsonQLQueryExecuterFactory.JSON_INPUT_STREAM,
				extractJsonDataInputStream(processParams));
	}

	@NonNull
	private static InputStream extractJsonDataInputStream(final ProcessParams processParams)
	{
		final String jsonData = StringUtils.trimBlankToNull(processParams.getParameterAsString(ReportConstants.REPORT_PARAM_JSON_DATA));
		if (jsonData == null)
		{
			throw new AdempiereException("Process parameter `" + ReportConstants.REPORT_PARAM_JSON_DATA + "` is not set or it's blank");
		}

		//
		// We must provide the json as parameter input stream
		// See https://stackoverflow.com/questions/33300592/how-to-fill-report-using-json-datasource-without-getting-null-values/33301039
		// See http://jasperreports.sourceforge.net/sample.reference/jsondatasource/
		return new ByteArrayInputStream(jsonData.getBytes(StandardCharsets.UTF_8));
	}
}
