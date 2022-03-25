package de.metas.report.jasper.data_source;

import de.metas.report.server.ReportContext;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.sql.Connection;
import java.util.Map;
import java.util.Optional;

public interface ReportDataSource
{
	Map<String, Object> createJRParameters(@NonNull ReportContext reportContext);

	default Optional<Connection> createDBConnection(@NonNull ReportContext reportContext, @Nullable String troubleshooting_reportPath) {return Optional.empty();}
}
