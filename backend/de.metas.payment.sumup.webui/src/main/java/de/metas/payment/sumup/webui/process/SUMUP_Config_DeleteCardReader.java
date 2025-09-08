package de.metas.payment.sumup.webui.process;

import com.google.common.collect.ImmutableSet;
import de.metas.payment.sumup.SumUpCardReaderExternalId;
import de.metas.payment.sumup.SumUpConfigId;
import de.metas.payment.sumup.SumUpService;
import de.metas.payment.sumup.repository.model.I_SUMUP_CardReader;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import lombok.NonNull;
import org.compiere.SpringContextHolder;

import java.util.Set;

public class SUMUP_Config_DeleteCardReader extends JavaProcess implements IProcessPrecondition
{
	@NonNull private final SumUpService sumUpService = SpringContextHolder.instance.getBean(SumUpService.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (context.getSelectedIncludedRecords().isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection().toInternal();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final SumUpConfigId configId = SumUpConfigId.ofRepoId(getRecord_ID());
		final Set<SumUpCardReaderExternalId> ids = getCardReaderExternalIds();
		sumUpService.deleteCardReaders(configId, ids);

		return MSG_OK;
	}

	private Set<SumUpCardReaderExternalId> getCardReaderExternalIds()
	{
		return getSelectedIncludedRecords(I_SUMUP_CardReader.class)
				.stream()
				.map(record -> SumUpCardReaderExternalId.ofString(record.getExternalId()))
				.collect(ImmutableSet.toImmutableSet());
	}

}
