package org.compiere.report;

import org.compiere.model.PrintInfo;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.print.IPrintService;
import de.metas.process.ProcessInfo;
import de.metas.process.ProcessInfoParameter;
import lombok.NonNull;

public abstract class AbstractPrintService implements IPrintService
{
	private final transient Logger logger = LogManager.getLogger(getClass());

	protected PrintInfo extractPrintInfo(@NonNull final ProcessInfo pi)
	{
		// make sure that we never have zero copies. Apparently ADempiere
		// thinks of "copies" as the number of printouts _additional_ to the
		// original document while the java printing API thinks of copies as
		// the absolute number of printouts and thus doesn't accept any
		// number <=0.
		int numberOfPrintouts = 1;
		PrintInfo printInfo = null;

		if (pi.getParameter() != null)
		{
			for (final ProcessInfoParameter param : pi.getParameter())
			{
				final String parameterName = param.getParameterName();
				final Object objParam = param.getParameter();

				if (objParam == null)
				{
					continue;
				}

				if (objParam instanceof PrintInfo)
				{
					printInfo = (PrintInfo)objParam;
					numberOfPrintouts = printInfo.getCopies();

					if (numberOfPrintouts <= 0)
					{
						logger.debug("Setting numberOfPrintouts from 0 (specified by printInfo) to 1");
						numberOfPrintouts = 1;
					}
					break;
				}
				else if (PARAM_PrintCopies.equals(parameterName))
				{
					numberOfPrintouts = param.getParameterAsInt();
					if (numberOfPrintouts <= 0)
					{
						logger.debug("Setting numberOfPrintouts from 0 (specified by " + PARAM_PrintCopies + ") to 1");
						numberOfPrintouts = 1;
					}
				}
			}
		}

		//
		// Do a copy of found print info, or create a new one
		if (printInfo == null)
		{
			printInfo = new PrintInfo(pi);
		}
		else
		{
			printInfo = new PrintInfo(printInfo);
		}

		// Update printInfo
		printInfo.setCopies(numberOfPrintouts < 1 ? 1 : numberOfPrintouts);

		return printInfo;
	}

}
