package de.metas.payment.sumup.webui.process;

import com.google.common.collect.ImmutableSet;
import de.metas.payment.sumup.SumUpService;
import de.metas.payment.sumup.SumUpTransactionId;
import de.metas.payment.sumup.repository.BulkUpdateByQueryResult;
import de.metas.payment.sumup.repository.SumUpTransactionQuery;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.util.Env;

import java.util.Set;

public class SUMUP_Transaction_UpdateSelectedRows extends ViewBasedProcessTemplate implements IProcessPrecondition
{
	@NonNull private final SumUpService sumUpService = SpringContextHolder.instance.getBean(SumUpService.class);

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (getSelectedRowIds().isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection().toInternal();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	@RunOutOfTrx
	protected String doIt()
	{
		final Set<SumUpTransactionId> ids = getSelectedIdsAsSet();
		if (ids.isEmpty())
		{
			throw new AdempiereException("@NoSelection@");
		}

		final BulkUpdateByQueryResult result = sumUpService.bulkUpdateTransactions(SumUpTransactionQuery.ofLocalIds(ids), true);
		return result.getSummary().translate(Env.getADLanguageOrBaseLanguage());
	}

	private Set<SumUpTransactionId> getSelectedIdsAsSet()
	{
		final DocumentIdsSelection selectedRowIds = getSelectedRowIds();
		if (selectedRowIds.isEmpty())
		{
			return ImmutableSet.of();
		}
		else if (selectedRowIds.isAll())
		{
			return getView().streamByIds(selectedRowIds)
					.map(row -> row.getId().toId(SumUpTransactionId::ofRepoId))
					.collect(ImmutableSet.toImmutableSet());
		}
		else
		{
			return selectedRowIds.toIds(SumUpTransactionId::ofRepoId);
		}
	}

}
