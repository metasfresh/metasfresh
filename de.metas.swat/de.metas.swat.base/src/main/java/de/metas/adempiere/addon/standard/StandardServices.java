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
import org.adempiere.document.service.IDocActionBL;
import org.adempiere.document.service.IDocumentPA;
import org.adempiere.document.service.IRecurringBL;
import org.adempiere.document.service.IRecurringPA;
import org.adempiere.document.service.impl.DocActionBL;
import org.adempiere.document.service.impl.DocumentPA;
import org.adempiere.document.service.impl.RecurringBL;
import org.adempiere.document.service.impl.RecurringPA;
import org.adempiere.inout.replenish.service.IReplenishForFutureQty;
import org.adempiere.inout.replenish.service.ReplenishForFutureQty;
import org.adempiere.inout.service.IInOutPA;
import org.adempiere.inout.service.impl.InOutPA;
import org.adempiere.inout.shipment.IShipmentBL;
import org.adempiere.inout.shipment.impl.ShipmentBL;
import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.invoice.service.IInvoiceDAO;
import org.adempiere.invoice.service.impl.InvoiceBL;
import org.adempiere.invoice.service.impl.InvoiceDAO;
import org.adempiere.misc.service.IClientOrgPA;
import org.adempiere.misc.service.IPOService;
import org.adempiere.misc.service.IPrintPA;
import org.adempiere.misc.service.IProcessPA;
import org.adempiere.misc.service.ITablePA;
import org.adempiere.misc.service.impl.ClientOrgPA;
import org.adempiere.misc.service.impl.POService;
import org.adempiere.misc.service.impl.PrintPA;
import org.adempiere.misc.service.impl.ProcessPA;
import org.adempiere.misc.service.impl.TablePA;
import org.adempiere.order.service.IOrderPA;
import org.adempiere.order.service.impl.OrderPA;
import org.adempiere.pricing.api.IPriceListDAO;
import org.adempiere.pricing.api.impl.PriceListDAO;
import org.adempiere.process.event.IProcessEventSupport;
import org.adempiere.process.event.impl.ProcessEventSupport;
import org.adempiere.processing.service.IProcessingService;
import org.adempiere.processing.service.impl.ProcessingService;
import org.adempiere.product.service.IProductBL;
import org.adempiere.product.service.IProductPA;
import org.adempiere.product.service.IStoragePA;
import org.adempiere.product.service.impl.ProductBL;
import org.adempiere.product.service.impl.ProductPA;
import org.adempiere.product.service.impl.StoragePA;
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
import de.metas.adempiere.service.IVariableParserBL;
import de.metas.adempiere.service.impl.CalendarDAO;
import de.metas.adempiere.service.impl.GlobalLockSystem;
import de.metas.adempiere.service.impl.OrderBL;
import de.metas.adempiere.service.impl.ParameterBL;
import de.metas.adempiere.service.impl.PrinterRoutingBL;
import de.metas.adempiere.service.impl.SweepTableBL;
import de.metas.adempiere.service.impl.TableColumnPathBL;
import de.metas.adempiere.service.impl.VariableParserBL;
import de.metas.dpd.service.RoutingService;
import de.metas.purchasing.service.IPurchaseScheduleBL;
import de.metas.purchasing.service.IPurchaseSchedulePA;
import de.metas.purchasing.service.impl.PurchaseScheduleBL;
import de.metas.purchasing.service.impl.PurchaseSchedulePA;
import de.metas.tax.api.impl.TaxBL;

public class StandardServices implements IAddOn
{

	@Override
	public void initAddon()
	{
		// Services for reporting and archiving
		// Services.registerService(IJasperService.class, new JasperService()); // let it register on demand; maybe other modules will register something different

		// Services related to inOut
		Services.registerService(IInOutPA.class, new InOutPA());
		Services.registerService(IPurchaseScheduleBL.class, new PurchaseScheduleBL());
		Services.registerService(IPurchaseSchedulePA.class, new PurchaseSchedulePA());
		Services.registerService(IReplenishForFutureQty.class, new ReplenishForFutureQty());
		Services.registerService(IShipmentBL.class, new ShipmentBL());

		// Services related to invoice
		Services.registerService(IInvoiceBL.class, new InvoiceBL());
		Services.registerService(IInvoiceDAO.class, new InvoiceDAO());
		// Services related to documents
		Services.registerService(IDocActionBL.class, new DocActionBL());
		Services.registerService(IDocumentPA.class, new DocumentPA());
		Services.registerService(IRecurringBL.class, new RecurringBL());
		Services.registerService(IRecurringPA.class, new RecurringPA());

		//
		// misc services
		Services.registerService(IAddonService.class, new AddonService());
		Services.registerService(IBPartnerBL.class, new BPartnerBL());
		Services.registerService(IBPartnerDAO.class, new BPartnerDAO());
		Services.registerService(IClientOrgPA.class, new ClientOrgPA());
		Services.registerService(IDatabaseBL.class, new DatabaseBL());
		Services.registerService(IDBService.class, new org.adempiere.db.impl.DBService());
		Services.registerService(IPackageInfoService.class, new RoutingService());
		Services.registerService(IOrderBL.class, new OrderBL());
		Services.registerService(IOrderPA.class, new OrderPA());
		Services.registerService(IParameterBL.class, new ParameterBL());
		Services.registerService(ICalendarDAO.class, new CalendarDAO());
		Services.registerService(IPOService.class, new POService());
		Services.registerService(IPriceListDAO.class, new PriceListDAO());
		Services.registerService(IPrintPA.class, new PrintPA());
		Services.registerService(IProcessEventSupport.class, new ProcessEventSupport());
		Services.registerService(IProcessingService.class, ProcessingService.get());
		Services.registerService(IProcessPA.class, new ProcessPA());
		Services.registerService(IProductBL.class, new ProductBL());
		Services.registerService(IProductPA.class, new ProductPA());
		Services.registerService(IStoragePA.class, new StoragePA());
		Services.registerService(ISweepTableBL.class, new SweepTableBL());
		Services.registerService(ITablePA.class, new TablePA());

		// need to manually register it here, for both APIs so it takes precedence over org.adempiere.tax.api.impl.TaxBL
		final TaxBL taxBL = new de.metas.tax.api.impl.TaxBL();
		Services.registerService(de.metas.tax.api.ITaxBL.class, taxBL);
		Services.registerService(org.adempiere.tax.api.ITaxBL.class, taxBL); // replace ADempiere original Tax functionality

		Services.registerService(IGlobalLockSystem.class, new GlobalLockSystem());
		Services.registerService(IAppDictionaryBL.class, new AppDictionaryBL());
		Services.registerService(ITableColumnPathBL.class, new TableColumnPathBL());
		Services.registerService(IVariableParserBL.class, new VariableParserBL());

		// us316: Printer Routing Service
		// NOTE: we need to register this service here because we need it before any database connection
		Services.registerService(IPrinterRoutingBL.class, new PrinterRoutingBL());
	}
}
