/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2022 metas GmbH
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

package org.adempiere.warehouse.interceptor;

import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.exceptions.BPartnerNoBillToAddressException;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.document.location.DocumentLocation;
import de.metas.order.location.adapter.OrderDocumentLocationAdapterFactory;
import de.metas.organization.OrgId;
import de.metas.user.User;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Callout(I_C_Order.class)
@Interceptor(I_C_Order.class)
@Component
public class C_Order
{
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	private final IBPartnerOrgBL bPartnerOrgBL = Services.get(IBPartnerOrgBL.class);
	private final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);

	public C_Order()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	@CalloutMethod(columnNames = I_C_Order.COLUMNNAME_M_Warehouse_ID)
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = I_C_Order.COLUMNNAME_M_Warehouse_ID)
	public void updateBillPartnerAndDropshipFlagFromWarehouse(@NonNull final I_C_Order order)
	{
		if (order.getM_Warehouse_ID() <= 0 || order.isSOTrx())
		{
			return; // nothing to do
		}

		final WarehouseId warehouseId = WarehouseId.ofRepoId(order.getM_Warehouse_ID());
		final OrgId orgId = OrgId.ofRepoId(order.getAD_Org_ID());

		final Optional<DocumentLocation> externalBPartnerBillToAddress = getBillingLocationDocumentForExternalBPartner(warehouseId, orgId);

		if (!externalBPartnerBillToAddress.isPresent())
		{
			final I_C_Order oldOrder = InterfaceWrapperHelper.createOld(order, I_C_Order.class);

			//dev-note: check if old warehouse was owned by an external partner and reset location
			if (oldOrder != null && oldOrder.getM_Warehouse_ID() > 0)
			{
				final I_M_Warehouse oldWarehouse = warehouseBL.getById(WarehouseId.ofRepoId(oldOrder.getM_Warehouse_ID()));

				if (oldWarehouse.isBPartnerInvoicesWithVendors())
				{
					final DocumentLocation bpartnerBillToLocation = getBillingDocumentForBPartnerId(BPartnerId.ofRepoId(order.getC_BPartner_ID()));

					OrderDocumentLocationAdapterFactory.billLocationAdapter(order)
							.setFrom(bpartnerBillToLocation);
				}
			}
		}
		else
		{
			OrderDocumentLocationAdapterFactory.billLocationAdapter(order)
					.setFrom(externalBPartnerBillToAddress.get());

			order.setIsDropShip(false);
		}
	}

	@NonNull
	private Optional<DocumentLocation> getBillingLocationDocumentForExternalBPartner(@NonNull final WarehouseId warehouseId, @NonNull final OrgId orgId)
	{
		final I_M_Warehouse warehousesRecord = warehouseBL.getById(warehouseId);

		if (!warehousesRecord.isBPartnerInvoicesWithVendors())
		{
			return Optional.empty();
		}

		final I_C_BPartner orgBPartner = bPartnerOrgBL.retrieveLinkedBPartner(orgId.getRepoId());

		if (warehousesRecord.getC_BPartner_ID() != orgBPartner.getC_BPartner_ID())
		{
			return Optional.of(warehouseBL.getBPartnerBillingLocationDocument(WarehouseId.ofRepoId(warehousesRecord.getM_Warehouse_ID())));
		}
		return Optional.empty();
	}

	@NonNull
	private DocumentLocation getBillingDocumentForBPartnerId(@NonNull final BPartnerId bPartnerId)
	{
		final I_C_BPartner bPartner = bPartnerDAO.getById(bPartnerId);

		final I_C_BPartner_Location billToLocation = bPartnerDAO.retrieveCurrentBillLocationOrNull(bPartnerId);
		if (billToLocation == null)
		{
			throw new BPartnerNoBillToAddressException(bPartner);
		}

		final BPartnerLocationId billLocationId = BPartnerLocationId.ofRepoId(billToLocation.getC_BPartner_ID(), billToLocation.getC_BPartner_Location_ID());

		final IBPartnerBL bpartnerBL = Services.get(IBPartnerBL.class);

		final User bpartnerBillToContact = bpartnerBL.retrieveContactOrNull(IBPartnerBL.RetrieveContactRequest.builder()
																			   .onlyActive(true)
																			   .contactType(IBPartnerBL.RetrieveContactRequest.ContactType.BILL_TO_DEFAULT)
																			   .bpartnerId(billLocationId.getBpartnerId())
																			   .bPartnerLocationId(billLocationId)
																			   .ifNotFound(IBPartnerBL.RetrieveContactRequest.IfNotFound.RETURN_NULL)
																			   .build());

		final BPartnerContactId billContactId = Optional.ofNullable(bpartnerBillToContact)
				.flatMap(User::getBPartnerContactId)
				.orElse(null);

		return DocumentLocation.builder()
				.bpartnerId(billLocationId.getBpartnerId())
				.bpartnerLocationId(billLocationId)
				.contactId(billContactId)
				.build();
	}

}
