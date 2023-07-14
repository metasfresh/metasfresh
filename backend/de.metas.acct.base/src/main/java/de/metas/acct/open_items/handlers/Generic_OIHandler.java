package de.metas.acct.open_items.handlers;

import com.google.common.collect.ImmutableSet;
import de.metas.acct.AccountConceptualName;
import de.metas.acct.open_items.FAOpenItemKey;
import de.metas.acct.open_items.FAOpenItemTrxInfo;
import de.metas.acct.open_items.FAOpenItemTrxInfoComputeRequest;
import de.metas.acct.open_items.FAOpenItemsHandler;
import de.metas.elementvalue.ElementValue;
import de.metas.elementvalue.ElementValueService;
import lombok.NonNull;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_M_MatchInv;

import java.util.Optional;
import java.util.Set;

//@Component // IMPORTANT: don't make it a spring component
public class Generic_OIHandler implements FAOpenItemsHandler
{
	private final ElementValueService elementValueService;

	public Generic_OIHandler(final ElementValueService elementValueService)
	{
		this.elementValueService = elementValueService;
	}

	@Override
	public @NonNull Set<AccountConceptualName> getHandledAccountConceptualNames()
	{
		// shall not be called
		return ImmutableSet.of();
	}

	@Override
	public Optional<FAOpenItemTrxInfo> computeTrxInfo(final FAOpenItemTrxInfoComputeRequest request)
	{
		final ElementValue elementValue = elementValueService.getById(request.getElementValueId());
		if (elementValue.isOpenItem())
		{

			final FAOpenItemKey key = FAOpenItemKey.ofTableRecordLineAndSubLineId(
					request.getTableName(),
					request.getRecordId(),
					request.getLineId(),
					request.getSubLineId());

			final FAOpenItemTrxInfo trxInfo = isClearing(request.getTableName())
					? FAOpenItemTrxInfo.clearing(key)
					: FAOpenItemTrxInfo.opening(key);

			return Optional.of(trxInfo);
		}
		else
		{
			return Optional.empty();
		}
	}

	private static boolean isClearing(final String tableName)
	{
		return I_C_AllocationHdr.Table_Name.equals(tableName)
				|| I_M_MatchInv.Table_Name.equals(tableName);
	}
}
