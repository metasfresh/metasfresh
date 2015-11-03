package de.metas.fresh.mrp_productinfo.impl;

import java.sql.Timestamp;

import org.adempiere.mm.attributes.api.IAttributeSetInstanceAware;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAwareFactoryService;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ObjectUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_MovementLine;
import org.compiere.model.I_M_Product;

import de.metas.fresh.model.I_Fresh_QtyOnHand;
import de.metas.fresh.model.I_Fresh_QtyOnHand_Line;
import de.metas.fresh.mrp_productinfo.IMRPProductInfoSelector;
import de.metas.fresh.mrp_productinfo.IMRPProductInfoSelectorFactory;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class MRPProductInfoSelectorFactory implements IMRPProductInfoSelectorFactory
{

	@Override
	public IMRPProductInfoSelector createOrNull(final Object model)
	{
		if (model == null)
		{
			return null;
		}

		if (InterfaceWrapperHelper.isInstanceOf(model, I_C_OrderLine.class))
		{
			// note: we are interested in any DocStatus, because e.g. the order might have been reactivated
			final I_C_OrderLine orderLine = InterfaceWrapperHelper.create(model, I_C_OrderLine.class);
			final IAttributeSetInstanceAware asiAware = Services.get(IAttributeSetInstanceAwareFactoryService.class).createOrNull(model);

			final Timestamp date = getDateForOrderLine(orderLine);
			return new MRPProdcutInfoSelector(asiAware, date, model);
		}
		else if (InterfaceWrapperHelper.isInstanceOf(model, I_M_MovementLine.class))
		{
			final I_M_MovementLine movementLine = InterfaceWrapperHelper.create(model, I_M_MovementLine.class);
			final IAttributeSetInstanceAware asiAware = Services.get(IAttributeSetInstanceAwareFactoryService.class).createOrNull(model);

			final Timestamp date = movementLine.getM_Movement().getMovementDate();
			return new MRPProdcutInfoSelector(asiAware, date, model);
		}
		else if (InterfaceWrapperHelper.isInstanceOf(model, I_M_InOutLine.class))
		{
			final I_M_InOutLine inOutLine = InterfaceWrapperHelper.create(model, I_M_InOutLine.class);
			final IAttributeSetInstanceAware asiAware = Services.get(IAttributeSetInstanceAwareFactoryService.class).createOrNull(model);

			final Timestamp date = inOutLine.getM_InOut().getMovementDate();
			return new MRPProdcutInfoSelector(asiAware, date, model);
		}
		else if (InterfaceWrapperHelper.isInstanceOf(model, I_Fresh_QtyOnHand_Line.class))
		{
			final I_Fresh_QtyOnHand_Line onHandLine = InterfaceWrapperHelper.create(model, I_Fresh_QtyOnHand_Line.class);
			if (!onHandLine.isActive())
			{
				return null;
			}
			final I_Fresh_QtyOnHand qtyOnHand = onHandLine.getFresh_QtyOnHand();
			if (!qtyOnHand.isActive())
			{
				return null;
			}
			final IAttributeSetInstanceAware asiAware = Services.get(IAttributeSetInstanceAwareFactoryService.class).createOrNull(model);

			final Timestamp date = qtyOnHand.getDateDoc();
			return new MRPProdcutInfoSelector(asiAware, date, model);
		}

		return null;
	}

	private Timestamp getDateForOrderLine(final I_C_OrderLine orderLine)
	{
		final I_M_ShipmentSchedule shipmentSched = Services.get(IShipmentSchedulePA.class).retrieveForOrderLine(orderLine);
		if (shipmentSched != null)
		{
			final Timestamp date = Services.get(IShipmentScheduleEffectiveBL.class).getPreparationDate(shipmentSched);
			if (date != null) // don't blindly assume that the sched is already initialized
			{
				return date;
			}
		}

		final Timestamp date;
		final I_C_Order order = orderLine.getC_Order();
		if (order.getPreparationDate() != null)
		{
			date = order.getPreparationDate();
		}
		else
		{
			date = order.getDatePromised();
		}
		Check.errorIf(date == null, "Unable to obtain a date for order line {0}", orderLine);
		return date;
	}

	static class MRPProdcutInfoSelector implements IMRPProductInfoSelector
	{
		private final IAttributeSetInstanceAware asiAware;
		private final Timestamp date;
		private final Object model;

		private MRPProdcutInfoSelector(final IAttributeSetInstanceAware asiAware,
				final Timestamp date,
				final Object model)
		{
			Check.assumeNotNull(asiAware, "Param 'asiAware' not null");
			Check.assumeNotNull(date, "Param 'date' not null");
			Check.assumeNotNull(model, "Param 'model' not null");

			this.asiAware = asiAware;
			this.date = date;
			this.model = model;
		}

		@Override
		public I_M_Product getM_Product()
		{
			return asiAware.getM_Product();
		}

		@Override
		public int getM_Product_ID()
		{
			return asiAware.getM_Product_ID();
		}

		@Override
		public I_M_AttributeSetInstance getM_AttributeSetInstance()
		{
			return asiAware.getM_AttributeSetInstance();
		}

		@Override
		public int getM_AttributeSetInstance_ID()
		{
			return asiAware.getM_AttributeSetInstance_ID();
		}

		@Override
		public void setM_AttributeSetInstance(final I_M_AttributeSetInstance asi)
		{
			asiAware.setM_AttributeSetInstance(asi);
		}

		@Override
		public Timestamp getDate()
		{
			return date;
		}

		@Override
		public Object getModel()
		{
			return model;
		}

		@Override
		public int compareTo(final IMRPProductInfoSelector o)
		{
			final CompareToBuilder append =
					new CompareToBuilder()
							.append(o.getM_Product_ID(), getM_Product_ID())
							.append(o.getM_AttributeSetInstance_ID(), getM_AttributeSetInstance_ID())
							.append(o.getDate(), getDate());
			return append.build();
		}

		@Override
		public String toString()
		{
			return toStringForDebugging();
		}

		private String toStringForDebugging()
		{
			return ObjectUtils.toString(this);
		}
		
		@Override
		public String toStringForRegularLogging()
		{
			return InterfaceWrapperHelper.getModelTableName(getModel())
					+ "[Date=" + getDate()
					+ ",M_Product_ID=" + getM_Product_ID()
					+ ",M_AttributeSetInstance_ID=" + getM_AttributeSetInstance_ID() 
					+ "]";
		}

	}
}