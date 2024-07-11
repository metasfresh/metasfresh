package de.metas.adempiere.addon.standard;

import de.metas.adempiere.addon.IAddOn;
import de.metas.adempiere.service.AppDictionaryBL;
import de.metas.adempiere.service.IAppDictionaryBL;
import de.metas.adempiere.service.IParameterBL;
import de.metas.adempiere.service.IPrinterRoutingBL;
import de.metas.adempiere.service.ISweepTableBL;
import de.metas.adempiere.service.ITableColumnPathBL;
import de.metas.adempiere.service.impl.ParameterBL;
import de.metas.adempiere.service.impl.PrinterRoutingBL;
import de.metas.adempiere.service.impl.SweepTableBL;
import de.metas.adempiere.service.impl.TableColumnPathBL;
import de.metas.calendar.ICalendarDAO;
import de.metas.calendar.impl.CalendarDAO;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.invoice.service.impl.InvoiceBL;
import de.metas.invoice.service.impl.InvoiceDAO;
import de.metas.order.IOrderBL;
import de.metas.order.impl.OrderBL;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.pricing.service.impl.PriceListDAO;
import de.metas.util.Services;
import org.adempiere.inout.replenish.service.IReplenishForFutureQty;
import org.adempiere.inout.replenish.service.ReplenishForFutureQty;
import org.adempiere.misc.service.IPOService;
import org.adempiere.misc.service.impl.POService;

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
		Services.registerService(IOrderBL.class, new OrderBL());
		Services.registerService(IParameterBL.class, new ParameterBL());
		Services.registerService(ICalendarDAO.class, new CalendarDAO());
		Services.registerService(IPOService.class, new POService());
		Services.registerService(IPriceListDAO.class, new PriceListDAO());

		Services.registerService(ISweepTableBL.class, new SweepTableBL());

		Services.registerService(IAppDictionaryBL.class, new AppDictionaryBL());
		Services.registerService(ITableColumnPathBL.class, new TableColumnPathBL());

		// us316: Printer Routing Service
		// NOTE: we need to register this service here because we need it before any database connection
		Services.registerService(IPrinterRoutingBL.class, new PrinterRoutingBL());
	}
}
