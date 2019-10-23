package de.metas.contracts.commission.interceptor;

import static de.metas.util.Check.isEmpty;
import static de.metas.util.lang.CoalesceUtil.firstGreaterThanZero;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.util.Optional;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
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
	private static final String MSG_CUSTOMER_NEEDS_SALES_PARTNER = "de.metas.contracts.commission.salesOrder.MissingSalesPartner";

	private final IBPartnerDAO bpartnerDAO;

	public C_Order()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);

		bpartnerDAO = Services.get(IBPartnerDAO.class);
	}

	@CalloutMethod(columnNames = { I_C_Order.COLUMNNAME_C_BPartner_ID, I_C_Order.COLUMNNAME_Bill_BPartner_ID })
	public void updateSalesPartnerFromBillPartner(@NonNull final I_C_Order orderRecord)
	{
		if (!orderRecord.isSOTrx())
		{
			return;
		}

		final BPartnerId effectiveBillPartnerId = extractEffectiveBillPartnerId(orderRecord);
		if (effectiveBillPartnerId == null)
		{
			return; // no customer whose salesrep data we can use to update this order
		}

		final I_C_BPartner billBPartnerRecord = bpartnerDAO.getById(effectiveBillPartnerId);
		orderRecord.setIsSalesPartnerRequired(billBPartnerRecord.isSalesPartnerRequired());

		final BPartnerId salesBPartnerId = BPartnerId.ofRepoIdOrNull(billBPartnerRecord.getC_BPartner_SalesRep_ID());
		orderRecord.setC_BPartner_SalesRep_ID(BPartnerId.toRepoId(salesBPartnerId));

		if (salesBPartnerId == null)
		{
			orderRecord.setSalesPartnerCode(null);
			return;
		}

		final I_C_BPartner salesBPartnerRecord = bpartnerDAO.getById(salesBPartnerId);
		orderRecord.setSalesPartnerCode(salesBPartnerRecord.getSalesPartnerCode());
	}

	@CalloutMethod(columnNames = I_C_Order.COLUMNNAME_SalesPartnerCode)
	public void updateSalesPartnerFromCode(@NonNull final I_C_Order orderRecord)
	{
		final String salesPartnerCode = orderRecord.getSalesPartnerCode();
		if (isEmpty(salesPartnerCode, true))
		{
			orderRecord.setC_BPartner_SalesRep_ID(-1);
			return;
		}

		final Optional<BPartnerId> salesPartnerId = bpartnerDAO.getBPartnerIdBySalesPartnerCode(salesPartnerCode);
		if (salesPartnerId.isPresent())
		{
			orderRecord.setC_BPartner_SalesRep_ID(salesPartnerId.get().getRepoId());
		}
	}

	@CalloutMethod(columnNames = I_C_Order.COLUMNNAME_C_BPartner_SalesRep_ID)
	public void updateSalesPartnerInOrder(@NonNull final I_C_Order orderRecord)
	{
		if (!orderRecord.isSOTrx())
		{
			return;
		}

		final BPartnerId effectiveBillPartnerId = extractEffectiveBillPartnerId(orderRecord);
		if (effectiveBillPartnerId == null)
		{
			return; // no customer whose mater data we we could update
		}

		final BPartnerId salesBPartnerId = BPartnerId.ofRepoIdOrNull(orderRecord.getC_BPartner_SalesRep_ID());
		if (salesBPartnerId == null)
		{
			orderRecord.setSalesPartnerCode(null);
			return;
		}

		final I_C_BPartner salesBPartnerRecord = bpartnerDAO.getById(salesBPartnerId);
		orderRecord.setSalesPartnerCode(salesBPartnerRecord.getSalesPartnerCode());
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = I_C_Order.COLUMNNAME_C_BPartner_SalesRep_ID)
	public void updateSalesPartnerInCustomerMaterdata(@NonNull final I_C_Order orderRecord)
	{
		if (!orderRecord.isSOTrx())
		{
			return;
		}

		final BPartnerId effectiveBillPartnerId = extractEffectiveBillPartnerId(orderRecord);
		if (effectiveBillPartnerId == null)
		{
			return; // no customer whose mater data we we could update
		}

		final BPartnerId salesBPartnerId = BPartnerId.ofRepoIdOrNull(orderRecord.getC_BPartner_SalesRep_ID());
		if (salesBPartnerId == null)
		{
			return; // leave the master data untouched
		}

		final I_C_BPartner billBPartnerRecord = bpartnerDAO.getById(effectiveBillPartnerId);
		billBPartnerRecord.setC_BPartner_SalesRep_ID(orderRecord.getC_BPartner_SalesRep_ID());
		saveRecord(billBPartnerRecord);
	}

	@DocValidate(timings = ModelValidator.TIMING_BEFORE_COMPLETE)
	public void preventCompleteIfMissingSalesPartner(@NonNull final I_C_Order orderRecord)
	{
		if (!orderRecord.isSOTrx())
		{
			return; // nothing to do for purchase orders
		}

		if (orderRecord.getC_BPartner_SalesRep_ID() > 0)
		{
			return; // having specified a sales partner is never wrong
		}

		final BPartnerId effectiveBillPartnerId = extractEffectiveBillPartnerId(orderRecord);
		if (effectiveBillPartnerId == null)
		{
			return;
		}

		final I_C_BPartner bpartnerRecord = bpartnerDAO.getById(effectiveBillPartnerId);
		if (!bpartnerRecord.isSalesPartnerRequired())
		{
			return; // doesn't need to have a sales partner
		}

		final IMsgBL msgBL = Services.get(IMsgBL.class);
		final ITranslatableString message = msgBL.getTranslatableMsgText(MSG_CUSTOMER_NEEDS_SALES_PARTNER);
		throw new AdempiereException(message).markAsUserValidationError();
	}

	private BPartnerId extractEffectiveBillPartnerId(@NonNull final I_C_Order orderRecord)
	{
		return BPartnerId.ofRepoIdOrNull(firstGreaterThanZero(
				orderRecord.getBill_BPartner_ID(),
				orderRecord.getC_BPartner_ID()));
	}
}
