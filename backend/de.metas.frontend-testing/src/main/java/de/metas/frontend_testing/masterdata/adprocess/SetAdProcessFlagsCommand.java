package de.metas.frontend_testing.masterdata.adprocess;

import de.metas.process.IADProcessDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_AD_Process;

import java.util.List;

/**
 * Sets flag columns on {@code AD_Process} records matched by a {@code JasperReport} substring.
 * <p>
 * Equivalent to the cucumber step {@code "set IsPdfA3Output for AD_Process with JasperReport containing:"}
 * implemented in {@code AD_Process_Create_StepDef}. Allows a Playwright E2E test to request the same
 * prerequisite — that the sales-invoice report process has {@code IsPdfA3Output=Y} — through the
 * frontend-testing masterdata API without depending on the cucumber step infrastructure.
 */
@Builder
public class SetAdProcessFlagsCommand
{
	@NonNull private final JsonSetAdProcessFlagsRequest request;

	public void execute()
	{
		final String jasperReportSubstring = request.getJasperReportSubstring();
		if (Check.isBlank(jasperReportSubstring))
		{
			throw new AdempiereException("jasperReportSubstring must not be blank");
		}

		final List<I_AD_Process> processes = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Process.class)
				.addStringLikeFilter(I_AD_Process.COLUMNNAME_JasperReport, jasperReportSubstring, true)
				.create()
				.list();

		if (processes.isEmpty())
		{
			throw new AdempiereException("No AD_Process found with JasperReport containing: " + jasperReportSubstring);
		}

		final Boolean isPdfA3Output = request.getIsPdfA3Output();

		for (final I_AD_Process process : processes)
		{
			if (isPdfA3Output != null)
			{
				process.setIsPdfA3Output(isPdfA3Output);
			}
			Services.get(IADProcessDAO.class).save(process);
		}
	}
}
