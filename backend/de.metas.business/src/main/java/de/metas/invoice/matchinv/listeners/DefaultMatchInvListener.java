package de.metas.invoice.matchinv.listeners;

import de.metas.invoice.matchinv.MatchInv;
import de.metas.invoice.matchinv.service.MatchInvoiceService;
import de.metas.order.IMatchPOBL;
import de.metas.util.Services;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class DefaultMatchInvListener implements MatchInvListener
{
	private final IMatchPOBL matchPOBL = Services.get(IMatchPOBL.class);
	private final MatchInvoiceService matchInvoiceService;

	DefaultMatchInvListener(final MatchInvoiceService matchInvoiceService) {this.matchInvoiceService = matchInvoiceService;}

	@Override
	public void onAfterCreated(final MatchInv matchInv) {}

	@Override
	public void onAfterDeleted(@NonNull final List<MatchInv> matchInvs)
	{
		matchInvs.forEach(this::clearInvoiceLineFromMatchPOs);
	}

	private void clearInvoiceLineFromMatchPOs(@NonNull final MatchInv matchInv)
	{
		matchInvoiceService.getOrderLineId(matchInv)
				.ifPresent(orderLineId -> matchPOBL.unlink(orderLineId, matchInv.getInvoiceLineId()));
	}

}
