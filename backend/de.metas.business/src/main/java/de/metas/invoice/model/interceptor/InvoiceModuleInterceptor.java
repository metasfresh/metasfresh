package de.metas.invoice.model.interceptor;

import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.util.Services;

/**
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class InvoiceModuleInterceptor extends AbstractModuleInterceptor
{
	public static final InvoiceModuleInterceptor INSTANCE= new InvoiceModuleInterceptor();

	private InvoiceModuleInterceptor()
	{
	}

	@Override
	protected void onAfterInit()
	{
		// use full class names to make it easier for the dev understanding what module this is about
		Services.get(de.metas.dlm.coordinator.ICoordinatorService.class).registerInspector(de.metas.invoice.dlm.InvoicePaidInspector.INSTANCE);
	}



}
