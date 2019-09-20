package de.metas.contracts.commission.salesorder.interceptor;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Component
@Callout(I_C_Order.class)
@Interceptor(I_C_Order.class)
public class C_Order
{
	private static final String MSG_CUSTOMER_NEEDS_SALES_PARTNER = "de.metas.contracts.commission.salesorder.CustomerNeedsSalesPartner";

	public C_Order()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	@CalloutMethod(columnNames = I_C_Order.COLUMNNAME_Bill_BPartner_ID)
	public void updateSalesPartnerInOrder(@NonNull final I_C_Order orderRecord)
	{
		if (!orderRecord.isSOTrx())
		{
			return;
		}
		if (orderRecord.getBill_BPartner_ID() <= 0)
		{
			return;
		}

		final I_C_BPartner billBPartnerRecord = loadOutOfTrx(orderRecord.getBill_BPartner_ID(), I_C_BPartner.class);
		orderRecord.setC_BPartner_SalesRep_ID(billBPartnerRecord.getC_BPartner_SalesRep_ID());
	}

	@CalloutMethod(columnNames = I_C_Order.COLUMNNAME_C_BPartner_SalesRep_ID)
	public void updateSalesPartnerInCustomer(@NonNull final I_C_Order orderRecord)
	{
		if (!orderRecord.isSOTrx())
		{
			return;
		}
		if (orderRecord.getBill_BPartner_ID() <= 0)
		{
			return;
		}
		if (orderRecord.getC_BPartner_SalesRep_ID() <= 0)
		{
			return;
		}

		final I_C_BPartner billBPartnerRecord = loadOutOfTrx(orderRecord.getBill_BPartner_ID(), I_C_BPartner.class);
		billBPartnerRecord.setC_BPartner_SalesRep_ID(orderRecord.getC_BPartner_SalesRep_ID());
		saveRecord(billBPartnerRecord);
	}

	@DocValidate(timings = ModelValidator.TIMING_BEFORE_COMPLETE)
	public void preventCompleteIfMissingSalesPartner(@NonNull final I_C_Order orderRecord)
	{
		if (!orderRecord.isSOTrx())
		{
			return;
		}
		if (orderRecord.getBill_BPartner_ID() <= 0)
		{
			return;
		}

		final I_C_BPartner billBPartnerRecord = loadOutOfTrx(orderRecord.getBill_BPartner_ID(), I_C_BPartner.class);
		if (!billBPartnerRecord.isSalesPartnerRequired())
		{
			return;
		}

		if (orderRecord.getC_BPartner_SalesRep_ID() > 0)
		{
			return;
		}

		final IMsgBL msgBL = Services.get(IMsgBL.class);
		final ITranslatableString message = msgBL.getTranslatableMsgText(MSG_CUSTOMER_NEEDS_SALES_PARTNER, billBPartnerRecord);
		throw new AdempiereException(message);
	}
}
