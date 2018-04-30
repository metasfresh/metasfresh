package de.metas.adempiere.addon.standard;

import org.adempiere.db.IDBService;
import org.adempiere.db.IDatabaseBL;
import org.adempiere.db.impl.DatabaseBL;
import org.adempiere.inout.replenish.service.IReplenishForFutureQty;
import org.adempiere.inout.replenish.service.ReplenishForFutureQty;
import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.invoice.service.IInvoiceDAO;
import org.adempiere.invoice.service.impl.InvoiceBL;
import org.adempiere.invoice.service.impl.InvoiceDAO;
import org.adempiere.misc.service.IClientOrgPA;
import org.adempiere.misc.service.IPOService;
import org.adempiere.misc.service.ITablePA;
import org.adempiere.misc.service.impl.ClientOrgPA;
import org.adempiere.misc.service.impl.POService;
import org.adempiere.misc.service.impl.TablePA;
import org.adempiere.process.event.IProcessEventSupport;
import org.adempiere.process.event.impl.ProcessEventSupport;
import org.adempiere.processing.service.IProcessingService;
import org.adempiere.processing.service.impl.ProcessingService;
import org.adempiere.util.Services;

import de.metas.adempiere.addon.IAddOn;
import de.metas.adempiere.addon.IAddonService;
import de.metas.adempiere.addon.impl.AddonService;
import de.metas.adempiere.service.AppDictionaryBL;
import de.metas.adempiere.service.IAppDictionaryBL;
import de.metas.adempiere.service.ICalendarDAO;
import de.metas.adempiere.service.IGlobalLockSystem;
import de.metas.adempiere.service.IParameterBL;
import de.metas.adempiere.service.IPrinterRoutingBL;
import de.metas.adempiere.service.ISweepTableBL;
import de.metas.adempiere.service.ITableColumnPathBL;
import de.metas.adempiere.service.impl.CalendarDAO;
import de.metas.adempiere.service.impl.GlobalLockSystem;
import de.metas.adempiere.service.impl.ParameterBL;
import de.metas.adempiere.service.impl.PrinterRoutingBL;
import de.metas.adempiere.service.impl.SweepTableBL;
import de.metas.adempiere.service.impl.TableColumnPathBL;
import de.metas.order.IOrderBL;
import de.metas.order.impl.OrderBL;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.pricing.service.impl.PriceListDAO;

/**
 * <b>IMPORTANT</p>: this class is old. Most of the stuff done in here is obsolete and could be removed.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class StandardServices implements IAddOn
{

	@Override
	public void beforeConnection()
	{
		// Services related to inOut
		Services.registerService(IReplenishForFutureQty.class, new ReplenishForFutureQty());

		// Services related to invoice
		Services.registerService(IInvoiceBL.class, new InvoiceBL());
		Services.registerService(IInvoiceDAO.class, new InvoiceDAO());

		//
		// misc services
		Services.registerService(IAddonService.class, new AddonService());
		Services.registerService(IClientOrgPA.class, new ClientOrgPA());
		Services.registerService(IDatabaseBL.class, new DatabaseBL());
		Services.registerService(IDBService.class, new org.adempiere.db.impl.DBService());
		Services.registerService(IOrderBL.class, new OrderBL());
		Services.registerService(IParameterBL.class, new ParameterBL());
		Services.registerService(ICalendarDAO.class, new CalendarDAO());
		Services.registerService(IPOService.class, new POService());
		Services.registerService(IPriceListDAO.class, new PriceListDAO());
		Services.registerService(IProcessEventSupport.class, new ProcessEventSupport());
		Services.registerService(IProcessingService.class, ProcessingService.get());

		Services.registerService(ISweepTableBL.class, new SweepTableBL());
		Services.registerService(ITablePA.class, new TablePA());

		Services.registerService(IGlobalLockSystem.class, new GlobalLockSystem());
		Services.registerService(IAppDictionaryBL.class, new AppDictionaryBL());
		Services.registerService(ITableColumnPathBL.class, new TableColumnPathBL());

		// us316: Printer Routing Service
		// NOTE: we need to register this service here because we need it before any database connection
		Services.registerService(IPrinterRoutingBL.class, new PrinterRoutingBL());
	}
}
