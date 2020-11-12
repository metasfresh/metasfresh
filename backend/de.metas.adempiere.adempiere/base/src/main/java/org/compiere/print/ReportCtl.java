package org.compiere.print;

import de.metas.logging.LogManager;
import lombok.NonNull;
import org.slf4j.Logger;

/**
 * Report Controller.
 * Changes by metas: start method now also provides the
 * process info with the table id (not only record id)
 *
 * @author Jorg Janke
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 * <li>FR [ 1866739 ] ReportCtl: use printformat from the transient/serializable
 */
@Deprecated // TODO delete me
public final class ReportCtl
{
	private static final Logger logger = LogManager.getLogger(ReportCtl.class);

	private static ReportViewerProvider defaultReportEngineViewerProvider = re -> logger.warn("No {} registered to display {}", ReportViewerProvider.class, re);

	public static void preview(final ReportEngine re)
	{
		defaultReportEngineViewerProvider.openViewer(re);
	}

	public static void setDefaultReportEngineReportViewerProvider(@NonNull final ReportViewerProvider reportEngineViewerProvider)
	{
		defaultReportEngineViewerProvider = reportEngineViewerProvider;
	}
}
