package de.metas.report.jasper.data_source;

import com.google.common.collect.ImmutableMap;
import de.metas.report.server.ReportContext;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class JsonDataSource implements ReportDataSource
{
	@Override
	public Map<String, Object> createJRParameters(@NonNull final ReportContext reportContext)
	{
		return ImmutableMap.of();
	}
}
