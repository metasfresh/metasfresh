package de.metas.acct.open_items.handlers;

import de.metas.acct.AccountConceptualName;
import de.metas.acct.open_items.FAOpenItemKey;
import de.metas.acct.open_items.FAOpenItemTrxInfo;
import de.metas.acct.open_items.FAOpenItemTrxInfoComputeRequest;
import de.metas.acct.open_items.FAOpenItemsHandler;
import de.metas.elementvalue.ElementValue;
import de.metas.elementvalue.ElementValueService;
import lombok.NonNull;

import java.util.Optional;

//@Component // IMPORTANT: don't make it a spring component
public class Generic_OIHandler implements FAOpenItemsHandler
{
	private final ElementValueService elementValueService;

	public Generic_OIHandler(final ElementValueService elementValueService)
	{
		this.elementValueService = elementValueService;
	}

	@Override
	public @NonNull AccountConceptualName getHandledAccountConceptualName()
	{
		throw new IllegalStateException("Do not call this method");
	}

	@Override
	public Optional<FAOpenItemTrxInfo> computeTrxInfo(final FAOpenItemTrxInfoComputeRequest request)
	{
		final ElementValue elementValue = elementValueService.getById(request.getElementValueId());
		if (elementValue.isOpenItem())
		{

			return Optional.of(
					FAOpenItemTrxInfo.openItem(FAOpenItemKey.ofTableRecordLineAndSubLineId(
							request.getTableName(),
							request.getRecordId(),
							request.getLineId(),
							request.getSubLineId()))
			);
		}
		else
		{
			return Optional.empty();
		}
	}
}
