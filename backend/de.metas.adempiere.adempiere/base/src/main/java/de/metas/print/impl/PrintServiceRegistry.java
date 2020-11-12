package de.metas.print.impl;

import de.metas.print.IPrintService;
import de.metas.print.IPrintServiceRegistry;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

public class PrintServiceRegistry implements IPrintServiceRegistry
{
	private IPrintService service;

	@Override
	public IPrintService getPrintService()
	{
		final IPrintService service = this.service;
		if (service == null)
		{
			throw new AdempiereException("Missing " + IPrintService.class + " implementation");
		}
		return service;
	}

	@Override
	public IPrintService registerJasperService(@NonNull final IPrintService newService)
	{
		final IPrintService oldService = this.service;
		this.service = newService;
		return oldService;
	}
}
