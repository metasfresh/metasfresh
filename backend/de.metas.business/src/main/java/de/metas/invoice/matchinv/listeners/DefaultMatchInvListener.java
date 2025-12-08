package de.metas.invoice.matchinv.listeners;

import de.metas.invoice.matchinv.MatchInv;
import de.metas.invoice.matchinv.MatchInvCostPart;
import de.metas.invoice.matchinv.MatchInvType;
import de.metas.invoice.matchinv.service.MatchInvoiceService;
import de.metas.order.IMatchPOBL;
import de.metas.order.costs.OrderCostService;
import de.metas.util.Services;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class DefaultMatchInvListener implements MatchInvListener
{
	private final IMatchPOBL matchPOBL = Services.get(IMatchPOBL.class);
	private final MatchInvoiceService matchInvoiceService;
	private final OrderCostService orderCostService;

	DefaultMatchInvListener(
			@NonNull final MatchInvoiceService matchInvoiceService,
			@NonNull final OrderCostService orderCostService)
	{
		this.matchInvoiceService = matchInvoiceService;
		this.orderCostService = orderCostService;
	}

	@Override
	public void onAfterCreated(@NonNull final MatchInv matchInv)
	{
		if (matchInv.getType().isCost())
		{
			final MatchInvCostPart matchInvCost = matchInv.getCostPartNotNull();
			orderCostService.updateInOutCostById(
					matchInvCost.getInoutCostId(),
					inoutCost -> inoutCost.addCostAmountInvoiced(matchInvCost.getCostAmountInOut()));
		}
	}

	@Override
	public void onAfterDeleted(@NonNull final List<MatchInv> matchInvs)
	{
		for (final MatchInv matchInv : matchInvs)
		{
			final MatchInvType type = matchInv.getType();
			if (type.isMaterial())
			{
				clearInvoiceLineFromMatchPOs(matchInv);
			}
			else if (type.isCost())
			{
				final MatchInvCostPart matchInvCost = matchInv.getCostPartNotNull();
				orderCostService.updateInOutCostById(
						matchInvCost.getInoutCostId(),
						inoutCost -> inoutCost.addCostAmountInvoiced(matchInvCost.getCostAmountInOut().negate()));
			}
		}

		matchInvs.forEach(this::clearInvoiceLineFromMatchPOs);
	}

	private void clearInvoiceLineFromMatchPOs(@NonNull final MatchInv matchInv)
	{
		matchInvoiceService.getOrderLineId(matchInv)
				.ifPresent(orderLineId -> matchPOBL.unlink(orderLineId, matchInv.getInvoiceAndLineId()));
	}

}
