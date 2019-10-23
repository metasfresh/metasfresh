package de.metas.print;

import de.metas.util.ISingletonService;

/**
 * This service allows it modules to register jasper services for different use cases
 *
 *
 */
public interface IPrintServiceRegistry extends ISingletonService
{
	/**
	 * Throws an exception is no service is registered.
	 */
	IPrintService getPrintService();

	/**
	 * @return the jasper service that was previously installed, or <code>null</code>.
	 */
	IPrintService registerJasperService(IPrintService jasperService);

	boolean isServiceRegistered();
}
