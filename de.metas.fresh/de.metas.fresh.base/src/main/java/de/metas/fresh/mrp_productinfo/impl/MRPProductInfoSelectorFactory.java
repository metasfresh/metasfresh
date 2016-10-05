package de.metas.fresh.mrp_productinfo.impl;

import java.sql.Timestamp;
import java.util.Map;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAware;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAwareFactoryService;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.ILoggable;
import org.adempiere.util.Services;
import org.adempiere.util.api.IParams;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.ObjectUtils;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_MovementLine;
import org.compiere.util.DB;

import com.google.common.collect.ImmutableMap;

import de.metas.fresh.model.I_Fresh_QtyOnHand;
import de.metas.fresh.model.I_Fresh_QtyOnHand_Line;
import de.metas.fresh.model.I_X_MRP_ProductInfo_Detail_MV;
import de.metas.fresh.mrp_productinfo.IMRPProductInfoSelector;
import de.metas.fresh.mrp_productinfo.IMRPProductInfoSelectorFactory;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.procurement.base.model.I_PMM_PurchaseCandidate;

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
			return new MRPProductInfoSelector(asiAware.getM_Product_ID(), asiAware.getM_AttributeSetInstance_ID(), date, model);
		}
		else if (InterfaceWrapperHelper.isInstanceOf(model, I_M_MovementLine.class))
		{
			final I_M_MovementLine movementLine = InterfaceWrapperHelper.create(model, I_M_MovementLine.class);
			final IAttributeSetInstanceAware asiAware = Services.get(IAttributeSetInstanceAwareFactoryService.class).createOrNull(model);

			final Timestamp date = movementLine.getM_Movement().getMovementDate();
			return new MRPProductInfoSelector(asiAware.getM_Product_ID(), asiAware.getM_AttributeSetInstance_ID(), date, model);
		}
		else if (InterfaceWrapperHelper.isInstanceOf(model, I_M_InOutLine.class))
		{
			final I_M_InOutLine inOutLine = InterfaceWrapperHelper.create(model, I_M_InOutLine.class);
			final IAttributeSetInstanceAware asiAware = Services.get(IAttributeSetInstanceAwareFactoryService.class).createOrNull(model);

			final Timestamp date = inOutLine.getM_InOut().getMovementDate();
			return new MRPProductInfoSelector(asiAware.getM_Product_ID(), asiAware.getM_AttributeSetInstance_ID(), date, model);
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
			return new MRPProductInfoSelector(asiAware.getM_Product_ID(), asiAware.getM_AttributeSetInstance_ID(), date, model);
		}
		else if (InterfaceWrapperHelper.isInstanceOf(model, I_PMM_PurchaseCandidate.class))
		{
			final I_PMM_PurchaseCandidate purchaseCandidate = InterfaceWrapperHelper.create(model, I_PMM_PurchaseCandidate.class);
			final IAttributeSetInstanceAware asiAware = Services.get(IAttributeSetInstanceAwareFactoryService.class).createOrNull(model);

			final Timestamp date = purchaseCandidate.getDatePromised();
			return new MRPProductInfoSelector(asiAware.getM_Product_ID(), asiAware.getM_AttributeSetInstance_ID(), date, model);
		}
		if (InterfaceWrapperHelper.isInstanceOf(model, I_X_MRP_ProductInfo_Detail_MV.class))
		{
			I_X_MRP_ProductInfo_Detail_MV detail = InterfaceWrapperHelper.create(model, I_X_MRP_ProductInfo_Detail_MV.class);
			return new MRPProductInfoSelector(detail.getM_Product_ID(), detail.getM_AttributeSetInstance_ID(), detail.getDateGeneral(), model);
		}

		return null;
	}

	private Timestamp getDateForOrderLine(final I_C_OrderLine orderLine)
	{
		final I_M_ShipmentSchedule shipmentSched = Services.get(IShipmentSchedulePA.class).retrieveForOrderLine(orderLine);
		if (shipmentSched != null)
		{
			final Timestamp date = Services.get(IShipmentScheduleEffectiveBL.class).getPreparationDate(shipmentSched);
			if (date != null)                // don't blindly assume that the sched is already initialized
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
		Check.errorIf(date == null, "Unable to obtain a date for order line {}", orderLine);
		return date;
	}

	@Override
	public IMRPProductInfoSelector createOrNull(final Object model, final IParams params)
	{
		Check.assumeNotNull(model, "param 'model' is not null");
		if (params == null)
		{
			return createOrNull(model);
		}

		final String productParamKey = MRPProductInfoSelector.mkProductParamKey(model);
		final int productID = params.getParameterAsInt(productParamKey);
		if (productID <= 0)
		{
			ILoggable.THREADLOCAL.getLoggable().addLog("Missing param with name={}; model={}; params={}; falling back to the model's *current* values", productParamKey, model, params);
			return createOrNull(model);
		}

		final String attributeSetInstanceParamKey = MRPProductInfoSelector.mkAttributeSetInstanceParamKey(model);
		final int asiID = params.getParameterAsInt(attributeSetInstanceParamKey);
		if (asiID < 0) // might be 0
		{
			ILoggable.THREADLOCAL.getLoggable().addLog("Missing param with name={}; model={}; params={}; falling back to the model's *current* values", attributeSetInstanceParamKey, model, params);
			return createOrNull(model);
		}

		final String dateParamKey = MRPProductInfoSelector.mkDateParamKey(model);
		final Timestamp date = params.getParameterAsTimestamp(dateParamKey);
		if (date == null)
		{
			ILoggable.THREADLOCAL.getLoggable().addLog("Missing param with name={}; model={}; params={}; falling back to the model's *current* values", dateParamKey, model, params);
			return createOrNull(model);
		}

		// if all values are OK, then use them. Otherwise they might not be consistent with each other
		return new MRPProductInfoSelector(productID, asiID, date, model);
	}

	/**
	 * Important: check out the {@link #hashCode()} and {@link #equals(Object)} implementation before using instances in hashsets etc.
	 *
	 * @author metas-dev <dev@metasfresh.com>
	 *
	 */
	static class MRPProductInfoSelector implements IMRPProductInfoSelector
	{
		private final int M_Product_ID;
		private final int M_AttributeSetInstance_ID;
		private String asiKey;
		private final Timestamp date;
		private final Object model;

		private MRPProductInfoSelector(
				final int M_Product_ID,
				final int M_AttributeSetInstance_ID,
				final Timestamp date,
				final Object model)
		{
			Check.assume(M_Product_ID > 0, "Param 'M_Product_ID' > 0");
			Check.assume(M_AttributeSetInstance_ID >= 0, "Param 'M_AttributeSetInstance_ID' > 0");
			Check.assumeNotNull(date, "Param 'date' not null");
			Check.assumeNotNull(model, "Param 'model' not null");

			this.M_Product_ID = M_Product_ID;
			this.M_AttributeSetInstance_ID = M_AttributeSetInstance_ID;
			this.date = date;
			this.model = model;
		}

		@Override
		public int getM_Product_ID()
		{
			return M_Product_ID;
		}

		@Override
		public int getM_AttributeSetInstance_ID()
		{
			return M_AttributeSetInstance_ID;
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
			final CompareToBuilder append = new CompareToBuilder()
					.append(o.getM_Product_ID(), getM_Product_ID())
					.append(o.getM_AttributeSetInstance_ID(), getM_AttributeSetInstance_ID())
					.append(o.getDate(), getDate());
			return append.build();
		}

		@Override
		public Map<String, Object> asMap()
		{
			return ImmutableMap.<String, Object> of(
					mkProductParamKey(model), getM_Product_ID(),
					mkAttributeSetInstanceParamKey(model), getM_AttributeSetInstance_ID(),
					mkDateParamKey(model), getDate());
		}

		private static String createParamPrefix(final Object model)
		{
			final ITableRecordReference record = TableRecordReference.of(model);

			final String prefix = record.getTableName() + "_" + record.getRecord_ID() + "_";
			return prefix;
		}

		private static String mkProductParamKey(final Object model)
		{
			final String prefix = createParamPrefix(model);
			return prefix + "M_Product_ID";
		}

		private static String mkAttributeSetInstanceParamKey(final Object model)
		{
			final String prefix = createParamPrefix(model);
			return prefix + "M_AttributeSetInstance_ID";
		}

		private static String mkDateParamKey(final Object model)
		{
			final String prefix = createParamPrefix(model);
			return prefix + "Date";
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

		@Override
		public int hashCode()
		{
			return new HashCodeBuilder().append(date).append(M_Product_ID).append(getASIKey()).build();
		}

		@Override
		public boolean equals(Object obj)
		{
			return obj instanceof MRPProductInfoSelector && hashCode() == obj.hashCode();
		}

		@Override
		public String getASIKey()
		{
			if (asiKey == null)
			{
				final int attributeSetInstanceID = getM_AttributeSetInstance_ID();
				asiKey = DB.getSQLValueString(ITrx.TRXNAME_None,
						"SELECT GenerateHUStorageASIKey(?, '')",               // important to get an empty string instead of NULL
						attributeSetInstanceID);
			}
			return asiKey;
		}

	}
}
