package de.metas.inoutcandidate.api.impl;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import de.metas.bpartner.BPartnerContactId;
import de.metas.common.util.time.SystemTime;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Order;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.interfaces.I_C_BPartner;
import de.metas.logging.LogManager;
import de.metas.order.DeliveryRule;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.common.util.CoalesceUtil;
import lombok.NonNull;

import javax.annotation.Nullable;

public class ShipmentScheduleEffectiveBL implements IShipmentScheduleEffectiveBL
{
	private static final Logger logger = LogManager.getLogger(ShipmentScheduleEffectiveBL.class);

	@Override
	public I_C_BPartner_Location getBPartnerLocation(@NonNull final I_M_ShipmentSchedule sched)
	{
		final BPartnerLocationId locationId = getBPartnerLocationId(sched);
		return Services.get(IBPartnerDAO.class).getBPartnerLocationByIdEvenInactive(locationId);
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
		return Services.get(IWarehouseBL.class).getDefaultLocatorId(warehouseId);
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
	public I_C_BPartner getBPartner(final I_M_ShipmentSchedule sched)
	{
		final BPartnerId bpartnerId = getBPartnerId(sched);
		return Services.get(IBPartnerDAO.class).getById(bpartnerId, I_C_BPartner.class);
	}

	@Override
	public BPartnerLocationId getBPartnerLocationId(final I_M_ShipmentSchedule sched)
	{
		final int bpartnerId = sched.getC_BPartner_ID();
		final int bpLocationId = sched.getC_BPartner_Location_ID();

		final int bpartnerIdOverride = sched.getC_BPartner_Override_ID();
		final int bpLocationIdOverride = sched.getC_BP_Location_Override_ID();

		if (bpartnerIdOverride > 0)
		{
			if (bpLocationIdOverride > 0)
			{
				return BPartnerLocationId.ofRepoId(bpartnerIdOverride, bpLocationIdOverride);
			}
			else if (bpartnerId == bpartnerIdOverride)
			{
				return BPartnerLocationId.ofRepoId(bpartnerIdOverride, bpLocationId);
			}
			else
			{
				logger.warn("C_BPartner_ID and C_BPartner_Override_ID are not matching. Returning standard location."
						+ "\n BPartner/Location={}/{}"
						+ "\n BPartner/Location Override={}/{}", bpartnerId, bpLocationId, bpartnerIdOverride, bpLocationIdOverride);
				return BPartnerLocationId.ofRepoId(bpartnerId, bpLocationId);
			}
		}
		else
		{
			if (bpLocationIdOverride > 0)
			{
				return BPartnerLocationId.ofRepoId(bpartnerId, bpLocationIdOverride);
			}
			else
			{
				return BPartnerLocationId.ofRepoId(bpartnerId, bpLocationId);
			}
		}
	}

	@Nullable
	@Override
	public BPartnerContactId getBPartnerContactId(final @NonNull I_M_ShipmentSchedule sched)
	{
		final int adUserIdRepo = sched.getAD_User_Override_ID() > 0 ? sched.getAD_User_Override_ID() : sched.getAD_User_ID();
		final int cBPartnerIdRepo = sched.getC_BPartner_Override_ID() > 0 ? sched.getC_BPartner_Override_ID() : sched.getC_BPartner_ID();
		return BPartnerContactId.ofRepoIdOrNull(cBPartnerIdRepo, adUserIdRepo);
	}

	@Deprecated
	@Override
	public org.compiere.model.I_AD_User getBPartnerContact(final I_M_ShipmentSchedule sched)
	{
		final org.compiere.model.I_AD_User user = sched.getAD_User_Override_ID() <= 0 ? InterfaceWrapperHelper.loadOutOfTrx(sched.getAD_User_ID(), I_AD_User.class) : InterfaceWrapperHelper.loadOutOfTrx(sched.getAD_User_Override_ID(), I_AD_User.class);
		return user;
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
		return TimeUtil.asZonedDateTime(
				CoalesceUtil.coalesceSuppliers(
						sched::getDeliveryDate_Override,
						sched::getDeliveryDate));
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
