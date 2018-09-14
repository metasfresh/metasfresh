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

	/**
	 *
	 * @param jasperPrint the document that will be printed
	 * @param pi The process parameters that are used to find the correct printer (printer routing) and to store the printout's doc exchange record. Also this method tries to extract the number of
	 *            copies by
	 *            <ul>
	 *            <li>Checking if any process parameter is <code>instanceof </code> {@link org.compiere.model.PrintInfo}. If that is the case, then that printinfo's value is used
	 *            <li>Checking if any process parameter has {@link #PARAM_PrintCopies} as it's name (equals). If that is the case, then that parameters value is used.
	 *            <p>
	 *            <b>IMPORTANT:</b> a {@link org.compiere.model.PrintInfo} parameter takes precedence over a {@link #PARAM_PrintCopies} parameter.
	 *            </ul>
	 *            If none of these two ways work, the method will print one copy (i.e. one printout).
	 * @param displayDialog if true, a dialog for printer selection is displayed before printing.
	 */
	void print(ExecuteReportResult jasperPrint, ProcessInfo pi, boolean displayDialog);
}
