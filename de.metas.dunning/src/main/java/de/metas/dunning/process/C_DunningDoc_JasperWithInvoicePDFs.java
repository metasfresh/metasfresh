/**
 *
 */
package de.metas.dunning.process;

import java.util.List;

import org.adempiere.archive.api.IArchiveDAO;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Archive;
import org.compiere.util.Env;

import de.metas.dunning.model.I_C_DunningDoc;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.report.ExecuteReportStrategy;
import de.metas.report.ReportStarter;
import lombok.NonNull;

/**
 * Concatenates dunning pdf with invoices pdfs
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public class C_DunningDoc_JasperWithInvoicePDFs extends ReportStarter implements IProcessPrecondition
{
	private static final String C_DUNNING_DOC_REPORT_AD_PROCESS_ID = "de.metas.dunning.C_DunningDoc.Report.AD_Process_ID";

	private final static String MSG_NOARCHIVE = "ArchivedNone";

	private final transient IArchiveDAO archiveDAO = Services.get(IArchiveDAO.class);
	private final transient ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(
			@NonNull final IProcessPreconditionsContext context)
	{
		final List<I_AD_Archive> archives = archiveDAO.retrieveLastArchives(getCtx(), TableRecordReference.of(context.getSelectedModel(I_C_DunningDoc.class)), 1);
		if (archives.isEmpty())
		{
			return ProcessPreconditionsResolution.reject(msgBL.translatable(MSG_NOARCHIVE));
		}

		return ProcessPreconditionsResolution
				.acceptIf(I_C_DunningDoc.Table_Name.equals(context.getTableName()));
	}

	@Override
	protected ExecuteReportStrategy getExecuteReportStrategy()
	{
		final int dunningDocReportProcessId = sysConfigBL
				.getIntValue(
						C_DUNNING_DOC_REPORT_AD_PROCESS_ID,
						-1,
						Env.getAD_Client_ID(),
						Env.getAD_Org_ID(Env.getCtx()));

		return new C_DunningDoc_JasperWithInvoicePDFsStrategy(dunningDocReportProcessId);
	}
}
