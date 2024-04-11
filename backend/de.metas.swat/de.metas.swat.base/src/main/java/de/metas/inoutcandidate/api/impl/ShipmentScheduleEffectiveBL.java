/*
 * #%L
 * de.metas.swat.base
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

package de.metas.inoutcandidate.api.impl;

import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.time.SystemTime;
import de.metas.document.location.DocumentLocation;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.interfaces.I_C_BPartner;
import de.metas.location.LocationId;
import de.metas.order.DeliveryRule;
import de.metas.product.IProductBL;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_UOM;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Optional;

public class ShipmentScheduleEffectiveBL implements IShipmentScheduleEffectiveBL
{
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);

	@Override
	public I_C_BPartner_Location getBPartnerLocation(@NonNull final I_M_ShipmentSchedule sched)
	{
		final BPartnerLocationId locationId = getBPartnerLocationId(sched);
		return bpartnerDAO.getBPartnerLocationByIdEvenInactive(locationId);
	}

	@Override
	public BPartnerId getBPartnerId(@NonNull final I_M_ShipmentSchedule sched)
	{
		if (sched.getC_BPartner_Override_ID() <= 0)
		{
			return BPartnerId.ofRepoId(sched.getC_BPartner_ID());
		}
		else
		{
			return BPartnerId.ofRepoId(sched.getC_BPartner_Override_ID());
		}
	}

	@Override
	public String getBPartnerAddress(@NonNull final I_M_ShipmentSchedule sched)
	{
		return CoalesceUtil.firstNotBlank(
				sched::getBPartnerAddress_Override,
				sched::getBPartnerAddress);
	}

	@Override
	public DeliveryRule getDeliveryRule(@NonNull final I_M_ShipmentSchedule sched)
	{
		final String deliveryRuleCode = Check.isEmpty(sched.getDeliveryRule_Override(), true) ? sched.getDeliveryRule() : sched.getDeliveryRule_Override();
		return DeliveryRule.ofCode(deliveryRuleCode);
	}

	@Override
	public WarehouseId getWarehouseId(@NonNull final I_M_ShipmentSchedule sched)
	{
		if (!InterfaceWrapperHelper.isNull(sched, I_M_ShipmentSchedule.COLUMNNAME_M_Warehouse_Override_ID))
		{
			return WarehouseId.ofRepoId(sched.getM_Warehouse_Override_ID());
		}
		return WarehouseId.ofRepoId(sched.getM_Warehouse_ID());
	}

	@Override
	public LocatorId getDefaultLocatorId(final I_M_ShipmentSchedule sched)
	{
		final WarehouseId warehouseId = getWarehouseId(sched);
		return warehouseBL.getOrCreateDefaultLocatorId(warehouseId);
	}

	@Override
	public BigDecimal getQtyToDeliverBD(@NonNull final I_M_ShipmentSchedule sched)
	{
		if (!InterfaceWrapperHelper.isNull(sched, I_M_ShipmentSchedule.COLUMNNAME_QtyToDeliver_Override))
		{
			return sched.getQtyToDeliver_Override();
		}
		return sched.getQtyToDeliver();
	}

	@Override
	public Quantity getQtyOnHand(@NonNull final I_M_ShipmentSchedule sched)
	{
		final I_C_UOM uom = productBL.getStockUOM(sched.getM_Product_ID());
		return Quantity.of(sched.getQtyOnHand(), uom);
	}

	@Override
	public I_C_BPartner getBPartner(final I_M_ShipmentSchedule sched)
	{
		final BPartnerId bpartnerId = getBPartnerId(sched);
		return bpartnerDAO.getById(bpartnerId, I_C_BPartner.class);
	}

	@Override
	public BPartnerLocationId getBPartnerLocationId(final I_M_ShipmentSchedule sched)
	{
		return getDocumentLocation(sched).getBpartnerLocationId();
	}

	@Nullable
	@Override
	public BPartnerContactId getBPartnerContactId(final @NonNull I_M_ShipmentSchedule sched)
	{
		return getDocumentLocation(sched).getContactId();
	}

	@Override
	public DocumentLocation getDocumentLocation(@NonNull final I_M_ShipmentSchedule sched)
	{
		final BPartnerId bpartnerOverrideId = BPartnerId.ofRepoIdOrNull(sched.getC_BPartner_Override_ID());
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(sched.getC_BPartner_ID());

		final BPartnerLocationId bpLocationOverrideId = BPartnerLocationId.ofRepoIdOrNull(bpartnerOverrideId, sched.getC_BP_Location_Override_ID());
		if (bpLocationOverrideId != null)
		{
			final BPartnerContactId contactId;
			if (sched.getAD_User_Override_ID() > 0)
			{
				contactId = BPartnerContactId.ofRepoId(bpartnerOverrideId, sched.getAD_User_Override_ID());
			}
			else if (sched.getAD_User_ID() > 0 && sched.getC_BPartner_ID() == bpLocationOverrideId.getBpartnerId().getRepoId())
			{
				contactId = BPartnerContactId.ofRepoId(bpartnerOverrideId, sched.getAD_User_ID());
			}
			else
			{
				contactId = null;
			}

			return DocumentLocation.builder()
					.bpartnerId(bpLocationOverrideId.getBpartnerId())
					.bpartnerLocationId(bpLocationOverrideId)
					.locationId(LocationId.ofRepoIdOrNull(sched.getC_BP_Location_Override_Value_ID()))
					.bpartnerAddress(sched.getBPartnerAddress_Override())
					.contactId(contactId)
					.build();
		}
		else
		{
			final BPartnerContactId contactId;
			if (sched.getAD_User_Override_ID() > 0 && BPartnerId.equals(bpartnerId, bpartnerOverrideId))
			{
				contactId = BPartnerContactId.ofRepoId(bpartnerId, sched.getAD_User_Override_ID());
			}
			else
			{
				contactId = BPartnerContactId.ofRepoIdOrNull(bpartnerId, sched.getAD_User_ID());
			}

			return DocumentLocation.builder()
					.bpartnerId(bpartnerId)
					.bpartnerLocationId(BPartnerLocationId.ofRepoId(bpartnerId, sched.getC_BPartner_Location_ID()))
					.locationId(LocationId.ofRepoIdOrNull(sched.getC_BPartner_Location_Value_ID()))
					.bpartnerAddress(sched.getBPartnerAddress())
					.contactId(contactId)
					.build();
		}
	}

	@Deprecated
	@Override
	public org.compiere.model.I_AD_User getBPartnerContact(final I_M_ShipmentSchedule sched)
	{
		return sched.getAD_User_Override_ID() <= 0 ? InterfaceWrapperHelper.loadOutOfTrx(sched.getAD_User_ID(), I_AD_User.class) : InterfaceWrapperHelper.loadOutOfTrx(sched.getAD_User_Override_ID(), I_AD_User.class);
	}

	@Override
	public BigDecimal computeQtyOrdered(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		if (shipmentSchedule.isClosed())
		{
			return shipmentSchedule.getQtyDelivered();
		}

		final boolean hasQtyOrderedOverride = !InterfaceWrapperHelper.isNull(shipmentSchedule, I_M_ShipmentSchedule.COLUMNNAME_QtyOrdered_Override);
		if (hasQtyOrderedOverride)
		{
			return shipmentSchedule.getQtyOrdered_Override();
		}

		return shipmentSchedule.getQtyOrdered_Calculated();
	}

	@Override
	public ZonedDateTime getDeliveryDate(final I_M_ShipmentSchedule sched)
	{
		return getDeliveryDateAsTimestamp(sched)
				.map(TimeUtil::asZonedDateTime)
				.orElse(null);
	}

	@NonNull
	public static Optional<LocalDate> getDeliveryDateAsLocalDate(final I_M_ShipmentSchedule sched)
	{
		return getDeliveryDateAsTimestamp(sched)
				.map(ts -> ts.toLocalDateTime().toLocalDate());
	}

	@NonNull
	public static Optional<Timestamp> getDeliveryDateAsTimestamp(final I_M_ShipmentSchedule sched)
	{
		return CoalesceUtil.optionalOfFirstNonNullSupplied(sched::getDeliveryDate_Override, sched::getDeliveryDate);
	}

	@Override
	public ZonedDateTime getPreparationDate(final I_M_ShipmentSchedule sched)
	{
		final ZonedDateTime preparationDateOverride = TimeUtil.asZonedDateTime(sched.getPreparationDate_Override());
		if (preparationDateOverride != null)
		{
			return preparationDateOverride;
		}

		if (sched.getPreparationDate() != null)
		{
			return TimeUtil.asZonedDateTime(sched.getPreparationDate());
		}

		if (sched.getC_Order_ID() > 0)
		{
			final I_C_Order order = sched.getC_Order();
			return CoalesceUtil.coalesceSuppliers(
					() -> TimeUtil.asZonedDateTime(order.getPreparationDate()),
					() -> TimeUtil.asZonedDateTime(order.getDatePromised()));
		}

		return SystemTime.asZonedDateTime();
	}
}
