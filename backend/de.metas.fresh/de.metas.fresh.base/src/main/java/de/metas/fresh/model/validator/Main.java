package de.metas.fresh.model.validator;

/*
 * #%L
 * de.metas.fresh.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.copy_with_details.CopyRecordFactory;
import de.metas.fresh.model.I_Fresh_QtyOnHand;
import de.metas.fresh.printing.spi.impl.C_Order_MFGWarehouse_Report_RecordTextProvider;
import de.metas.i18n.Language;
import de.metas.notification.INotificationBL;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.mm.attributes.api.IModelAttributeSetInstanceListenerService;
import org.adempiere.mm.attributes.countryattribute.impl.InOutCountryModelAttributeSetInstanceListener;
import org.adempiere.mm.attributes.countryattribute.impl.InOutLineCountryModelAttributeSetInstanceListener;
import org.adempiere.mm.attributes.countryattribute.impl.InvoiceCountryModelAttributeSetInstanceListener;
import org.adempiere.mm.attributes.countryattribute.impl.InvoiceLineCountryModelAttributeSetInstanceListener;
import org.adempiere.mm.attributes.countryattribute.impl.OrderCountryModelAttributeSetInstanceListener;
import org.adempiere.mm.attributes.countryattribute.impl.OrderLineCountryModelAttributeSetInstanceListener;
import org.adempiere.mm.attributes.listeners.adr.InOutADRModelAttributeSetInstanceListener;
import org.adempiere.mm.attributes.listeners.adr.InOutLineADRModelAttributeSetInstanceListener;
import org.adempiere.mm.attributes.listeners.adr.InvoiceADRModelAttributeSetInstanceListener;
import org.adempiere.mm.attributes.listeners.adr.InvoiceLineADRModelAttributeSetInstanceListener;
import org.adempiere.mm.attributes.listeners.adr.OrderADRModelAttributeSetInstanceListener;
import org.adempiere.mm.attributes.listeners.adr.OrderLineADRModelAttributeSetInstanceListener;
import org.adempiere.mm.attributes.listeners.adr.OrderLineAllocADRModelAttributeSetInstanceListener;
import org.adempiere.mm.attributes.listeners.adr.OrderLineLotNumberModelAttributeSetInstanceListener;
import org.adempiere.mm.attributes.listeners.age.AgeModelAttributeSetInstanceListener;
import org.adempiere.mm.attributes.listeners.expiry.OrderLineExpiryModelAttributeSetInstanceListener;
import org.adempiere.mm.attributes.listeners.expiry.OrderLineMonthsUntilExpiryModelASIListener;
import org.adempiere.mm.attributes.listeners.inAusLand.InOutInAusLandModelAttributeSetInstanceListener;
import org.adempiere.mm.attributes.listeners.inAusLand.InOutLineInAusLandModelAttributeSetInstanceListener;
import org.adempiere.mm.attributes.listeners.inAusLand.InvoiceInAusLandModelAttributeSetInstanceListener;
import org.adempiere.mm.attributes.listeners.inAusLand.InvoiceLineInAusLandModelAttributeSetInstanceListener;
import org.adempiere.mm.attributes.listeners.inAusLand.OrderInAusLandModelAttributeSetInstanceListener;
import org.adempiere.mm.attributes.listeners.inAusLand.OrderLineInAusLandModelAttributeSetInstanceListener;
import org.compiere.model.I_M_Product;

import java.text.DateFormat;

public class Main extends AbstractModuleInterceptor
{
	@Override
	protected void onAfterInit()
	{
		// Services
		final IModelAttributeSetInstanceListenerService modelAttributeSetInstanceListenerService = Services.get(IModelAttributeSetInstanceListenerService.class);

		modelAttributeSetInstanceListenerService.registerListener(new InOutLineCountryModelAttributeSetInstanceListener());
		modelAttributeSetInstanceListenerService.registerListener(new InOutCountryModelAttributeSetInstanceListener());

		modelAttributeSetInstanceListenerService.registerListener(new OrderLineCountryModelAttributeSetInstanceListener());
		modelAttributeSetInstanceListenerService.registerListener(new OrderCountryModelAttributeSetInstanceListener());

		modelAttributeSetInstanceListenerService.registerListener(new InvoiceLineCountryModelAttributeSetInstanceListener());
		modelAttributeSetInstanceListenerService.registerListener(new InvoiceCountryModelAttributeSetInstanceListener());

		// In/Ausland attribute
		modelAttributeSetInstanceListenerService.registerListener(new InOutLineInAusLandModelAttributeSetInstanceListener());
		modelAttributeSetInstanceListenerService.registerListener(new InOutInAusLandModelAttributeSetInstanceListener());

		modelAttributeSetInstanceListenerService.registerListener(new OrderLineInAusLandModelAttributeSetInstanceListener());
		modelAttributeSetInstanceListenerService.registerListener(new OrderInAusLandModelAttributeSetInstanceListener());

		modelAttributeSetInstanceListenerService.registerListener(new InvoiceLineInAusLandModelAttributeSetInstanceListener());
		modelAttributeSetInstanceListenerService.registerListener(new InvoiceInAusLandModelAttributeSetInstanceListener());

		// ADR attribute
		modelAttributeSetInstanceListenerService.registerListener(new InOutLineADRModelAttributeSetInstanceListener());
		modelAttributeSetInstanceListenerService.registerListener(new InOutADRModelAttributeSetInstanceListener());

		modelAttributeSetInstanceListenerService.registerListener(new OrderLineAllocADRModelAttributeSetInstanceListener());
		modelAttributeSetInstanceListenerService.registerListener(new OrderLineADRModelAttributeSetInstanceListener());
		modelAttributeSetInstanceListenerService.registerListener(new OrderADRModelAttributeSetInstanceListener());

		modelAttributeSetInstanceListenerService.registerListener(new InvoiceLineADRModelAttributeSetInstanceListener());
		modelAttributeSetInstanceListenerService.registerListener(new InvoiceADRModelAttributeSetInstanceListener());

		modelAttributeSetInstanceListenerService.registerListener(new OrderLineLotNumberModelAttributeSetInstanceListener());

		modelAttributeSetInstanceListenerService.registerListener(new OrderLineExpiryModelAttributeSetInstanceListener());

		modelAttributeSetInstanceListenerService.registerListener(new OrderLineMonthsUntilExpiryModelASIListener());

		modelAttributeSetInstanceListenerService.registerListener(new AgeModelAttributeSetInstanceListener());

		//
		// Setup Time Format (see 06148)
		Language.setDefaultTimeStyle(DateFormat.SHORT);

		//
		// Apply misc workarounds for GOLIVE
		apply_Fresh_GOLIVE_Workarounds();

		// task 09833
		// Register the Printing Info ctx provider for C_Order_MFGWarehouse_Report
		Services.get(INotificationBL.class).addCtxProvider(C_Order_MFGWarehouse_Report_RecordTextProvider.instance);
		
		//
		// register ProductPOCopyRecordSupport, which needs to know about many different tables
		CopyRecordFactory.enableForTableName(I_M_Product.Table_Name);
	}

	@Override
	protected void registerInterceptors(final IModelValidationEngine engine)
	{
		//
		// add model validators
		engine.addModelValidator(new C_Order());
		engine.addModelValidator(new C_Invoice_Candidate());
		engine.addModelValidator(new de.metas.fresh.freshQtyOnHand.model.validator.Fresh_QtyOnHand());
		engine.addModelValidator(new de.metas.fresh.freshQtyOnHand.model.validator.Fresh_QtyOnHand_Line());

		engine.addModelValidator(de.metas.fresh.material.interceptor.PMM_PurchaseCandidate.INSTANCE);

		// these two are now spring components
		// engine.addModelValidator(de.metas.fresh.ordercheckup.model.validator.C_Order.instance); // task 09028
		// engine.addModelValidator(de.metas.fresh.ordercheckup.model.validator.C_Order_MFGWarehouse_ReportLine.instance); // task 09028
	}

	private void apply_Fresh_GOLIVE_Workarounds()
	{
		// QtyOnHand manual bookings table (08287)
		{
			CopyRecordFactory.enableForTableName(I_Fresh_QtyOnHand.Table_Name);
		}
	}
}
