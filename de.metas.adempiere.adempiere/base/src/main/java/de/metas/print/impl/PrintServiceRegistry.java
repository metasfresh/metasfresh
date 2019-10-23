package de.metas.print.impl;

import de.metas.print.IPrintService;
import de.metas.print.IPrintServiceRegistry;
import de.metas.util.Check;

public class PrintServiceRegistry implements IPrintServiceRegistry
{
	private IPrintService service;

	@Override
	public IPrintService getPrintService()
	{
		Check.errorIf(service == null, "Missing IJasperService implementation");
		return service;
	}

	@Override
	public IPrintService registerJasperService(final IPrintService jasperService)
	{
		final IPrintService oldService = this.service;
		this.service = jasperService;
		return oldService;
	}

	@Override
	public boolean isServiceRegistered()
	{
		return service != null;
	}
}
