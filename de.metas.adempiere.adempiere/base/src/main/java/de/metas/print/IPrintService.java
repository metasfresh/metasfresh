package de.metas.print;

import de.metas.process.ProcessInfo;
import de.metas.report.ExecuteReportStrategy.ExecuteReportResult;

/**
 *
 * Use {@link IPrintServiceRegistry} to get an instance.
 *
 */
public interface IPrintService
{

	/**
	 * A {@link ProcessInfo} parameter with this name can be used to specify the number of copies (1 means one printout) in case using {@link org.compiere.model.PrintInfo} is not feasible.
	 */
	String PARAM_PrintCopies = "PrintCopies";

	void print(ExecuteReportResult executeReportResult, ProcessInfo pi);
}
