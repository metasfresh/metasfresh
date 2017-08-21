/**
 * 
 */
package de.metas.payment.esr.api.impl;

import java.util.ArrayList;
import java.util.List;

import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Invoice;

import de.metas.payment.esr.api.IESRLineHandlersService;
import de.metas.payment.esr.model.I_ESR_ImportLine;
import de.metas.payment.esr.spi.IESRLineHandler;
import lombok.NonNull;

/**
 * @author cg
 *
 */
public class ESRLineHandlersService implements IESRLineHandlersService
{

	private final List<IESRLineHandler> handlers = new ArrayList<IESRLineHandler>();

	@Override
	public void registerESRLineListener(IESRLineHandler l)
	{
		if (handlers.contains(l))
		{
			throw new IllegalStateException(l + " has already been added");
		}
		handlers.add(l);
	}

	@Override
	public boolean applyESRMatchingBPartnerOfTheInvoice(final I_C_Invoice invoice, final I_ESR_ImportLine esrLine)
	{
		for (final IESRLineHandler h : handlers)
		{
			boolean match = h.matchBPartnerOfInvoice(invoice, esrLine);
			if (!match)
			{
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean applyESRMatchingBPartner(
			@NonNull final I_C_BPartner bPartner, 
			@NonNull final I_ESR_ImportLine esrLine)
	{
		for (final IESRLineHandler l : handlers)
		{
			boolean match = l.matchBPartner(bPartner, esrLine);
			if (!match)
			{
				return false;
			}
		}

		return true;
	}

}
