package de.metas.adempiere.addon.standard;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.adempiere.bpartner.service.IBPartnerBL;
import org.adempiere.bpartner.service.IBPartnerDAO;
import org.adempiere.bpartner.service.impl.BPartnerBL;
import org.adempiere.bpartner.service.impl.BPartnerDAO;
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
import org.adempiere.pricing.api.IPriceListDAO;
import org.adempiere.pricing.api.impl.PriceListDAO;
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
import de.metas.adempiere.service.IOrderBL;
import de.metas.adempiere.service.IPackageInfoService;
import de.metas.adempiere.service.IParameterBL;
import de.metas.adempiere.service.IPrinterRoutingBL;
import de.metas.adempiere.service.ISweepTableBL;
import de.metas.adempiere.service.ITableColumnPathBL;
import de.metas.adempiere.service.impl.CalendarDAO;
import de.metas.adempiere.service.impl.GlobalLockSystem;
import de.metas.adempiere.service.impl.OrderBL;
import de.metas.adempiere.service.impl.ParameterBL;
import de.metas.adempiere.service.impl.PrinterRoutingBL;
import de.metas.adempiere.service.impl.SweepTableBL;
import de.metas.adempiere.service.impl.TableColumnPathBL;
import de.metas.dpd.service.RoutingService;

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
		Services.registerService(IPackageInfoService.class, new RoutingService());
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
