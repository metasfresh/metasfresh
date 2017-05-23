/**
 * 
 */
package de.metas.payment.esr.api.impl;

import java.util.ArrayList;
import java.util.List;

import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Invoice;

import de.metas.payment.esr.api.IESRLineMatcherHandlersService;
import de.metas.payment.esr.model.I_ESR_ImportLine;
import de.metas.payment.esr.spi.IESRLineMatchHandler;

/**
 * @author cg
 *
 */
public class ESRLineMatcherHandlersService implements IESRLineMatcherHandlersService
{

	private final List<IESRLineMatchHandler> listenersList = new ArrayList<IESRLineMatchHandler>();

	@Override
	public void registerESRLineListener(IESRLineMatchHandler l)
	{
		if (listenersList.contains(l))
		{
			throw new IllegalStateException(l + " has already been added");
		}
		listenersList.add(l);
	}

	@Override
	public boolean applyESRMatchingBPartnerOfTheInvoice(final I_C_Invoice invoice, final I_ESR_ImportLine esrLine)
	{
		for (final IESRLineMatchHandler l : listenersList)
		{
			boolean match = l.matchBPartnerOfInvoice(invoice, esrLine);
			if (!match)
			{
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean applyESRMatchingBPartner(I_C_BPartner bpartner, I_ESR_ImportLine esrLine)
	{
		for (final IESRLineMatchHandler l : listenersList)
		{
			boolean match = l.matchBPartner(bpartner, esrLine);
			if (!match)
			{
				return false;
			}
		}

		return true;
	}

}
