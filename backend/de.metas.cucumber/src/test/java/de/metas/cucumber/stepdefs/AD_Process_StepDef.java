package de.metas.cucumber.stepdefs;

import com.google.common.collect.ImmutableSet;
import de.metas.logging.LogManager;
import de.metas.process.AdProcessId;
import de.metas.process.ProcessClassInfo;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_AD_Process;
import org.slf4j.Logger;

import java.util.concurrent.atomic.AtomicInteger;

public class AD_Process_StepDef
{
	private static final Logger logger = LogManager.getLogger(AD_Process_StepDef.class);

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@And("validate all AD_Processes, except:")
	public void validateAD_Processes(@NonNull final DataTable dataTable)
	{
		final ImmutableSet<AdProcessId> adProcessIdsToSkip = getAdProcessIdsToSkip(dataTable);
		final AtomicInteger countTotal = new AtomicInteger();

		final SoftAssertions softly = new SoftAssertions();

		queryBL.createQueryBuilder(I_AD_Process.class)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_AD_Process.COLUMNNAME_AD_Process_ID)
				.forEach(adProcess -> {
					final String processSummary = getSummary(adProcess);

					try
					{
						validateAD_Processes(adProcess);
						logger.info("{} is OK", processSummary);
					}
					catch (Exception ex)
					{
						final String errmsg = processSummary + " has error: " + ex.getLocalizedMessage();
						logger.info(errmsg, ex);
						softly.fail(errmsg);
					}
					finally
					{
						countTotal.incrementAndGet();
					}
				});

		logger.info("{} AD_Process(es) were checked", countTotal);
		softly.assertAll();
	}

	private void validateAD_Processes(final I_AD_Process adProcess)
	{
		final String className = StringUtils.trimBlankToNull(adProcess.getClassname());
		if (className == null)
		{
			return;
		}

		final ProcessClassInfo processClassInfo = ProcessClassInfo.ofClassname(className);
		if (ProcessClassInfo.isNull(processClassInfo))
		{
			throw new AdempiereException("Failed getting ProcessClassInfo for " + className);
		}
	}

	private static String getSummary(@NonNull final I_AD_Process adProcess)
	{
		return adProcess.getValue() + "/" + adProcess.getAD_Process_ID() + "/" + adProcess.getClassname();
	}

	private static ImmutableSet<AdProcessId> getAdProcessIdsToSkip(@NonNull final DataTable dataTable)
	{
		return DataTableRow.toRows(dataTable)
				.stream()
				.map(row -> AdProcessId.ofRepoId(row.getAsInt("AD_Process_ID")))
				.collect(ImmutableSet.toImmutableSet());
	}
}
