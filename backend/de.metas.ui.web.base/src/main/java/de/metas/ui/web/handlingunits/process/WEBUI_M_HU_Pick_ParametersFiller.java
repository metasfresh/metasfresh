package de.metas.ui.web.handlingunits.process;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.util.List;
import java.util.Set;

import org.adempiere.ad.validationRule.IValidationRule;
import org.adempiere.ad.validationRule.IValidationRuleFactory;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_BPartner;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;

import com.google.common.collect.ImmutableSet;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.order.OrderLineId;
import de.metas.picking.api.IPickingSlotDAO;
import de.metas.picking.api.PickingSlotQuery;
import de.metas.picking.model.I_M_PickingSlot;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.product.ProductId;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.descriptor.LookupDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlLookupDescriptor;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceContext;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

final class WEBUI_M_HU_Pick_ParametersFiller
{
	public static final String PARAM_M_PickingSlot_ID = I_M_PickingSlot.COLUMNNAME_M_PickingSlot_ID;
	public static final String PARAM_M_ShipmentSchedule_ID = I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID;

	private final OrderLineId salesOrderLineId;
	private final LookupDataSource shipmentScheduleDataSource;
	private final ShipmentScheduleId shipmentScheduleId;

	@Builder(builderClassName = "DefaultFillerBuilder", builderMethodName = "defaultFillerBuilder")
	private WEBUI_M_HU_Pick_ParametersFiller(
			final HuId huId,
			final OrderLineId salesOrderLineId)
	{
		this.salesOrderLineId = salesOrderLineId;
		this.shipmentScheduleDataSource = createShipmentScheduleDataSource(huId);
		this.shipmentScheduleId = null;
	}

	@Builder(builderClassName = "PickingSlotFillerBuilder", builderMethodName = "pickingSlotFillerBuilder")
	private WEBUI_M_HU_Pick_ParametersFiller(final ShipmentScheduleId shipmentScheduleId)
	{
		this.salesOrderLineId = null;
		this.shipmentScheduleDataSource = null;
		this.shipmentScheduleId = shipmentScheduleId;
	}

	public LookupValuesList getShipmentScheduleValues(final LookupDataSourceContext context)
	{
		final LookupValuesList result = shipmentScheduleDataSource.findEntities(context, context.getFilter(), context.getOffset(0), context.getLimit(10));
		return result;
	}

	private static LookupDataSource createShipmentScheduleDataSource(final HuId huId)
	{
		final LookupDescriptor lookupDescriptor = SqlLookupDescriptor.builder()
				.setCtxTableName(null)
				.setCtxColumnName(I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID)
				.setDisplayType(DisplayType.Search)
				.addValidationRule(createShipmentSchedulesValidationRule(huId))
				.buildForDefaultScope();
		return LookupDataSourceFactory.instance.createLookupDataSource(lookupDescriptor);
	}

	private static IValidationRule createShipmentSchedulesValidationRule(final HuId huId)
	{
		final StringBuilder sqlWhereClause = new StringBuilder();
		sqlWhereClause.append(I_M_ShipmentSchedule.COLUMNNAME_Processed).append("=").append(DB.TO_BOOLEAN(false));

		final ProductId productId = getSingleProductId(huId);
		sqlWhereClause.append(" AND ")
				.append(I_M_ShipmentSchedule.COLUMNNAME_M_Product_ID)
				.append("=")
				.append(productId.getRepoId());

		final I_M_HU hu = load(huId, I_M_HU.class);
		if (hu.getC_BPartner_ID() > 0)
		{
			sqlWhereClause
					.append(" AND COALESCE(")
					.append(I_M_ShipmentSchedule.COLUMNNAME_C_BPartner_Override_ID)
					.append(", ")
					.append(I_M_ShipmentSchedule.COLUMNNAME_C_BPartner_ID)
					.append(") = ")
					.append(hu.getC_BPartner_ID());
		}
		return Services.get(IValidationRuleFactory.class).createSQLValidationRule(sqlWhereClause.toString());
	}

	private static ProductId getSingleProductId(final HuId huId)
	{
		final I_M_HU hu = Services.get(IHandlingUnitsDAO.class).getById(huId);
		final Set<ProductId> productIds = Services.get(IHUContextFactory.class)
				.createMutableHUContext()
				.getHUStorageFactory()
				.getStorage(hu)
				.getProductStorages()
				.stream()
				.map(IHUProductStorage::getProductId)
				.distinct()
				.collect(ImmutableSet.toImmutableSet());

		if (productIds.isEmpty())
		{
			throw new AdempiereException("Empty HUs are not allowed");
		}
		if (productIds.size() > 1)
		{
			throw new AdempiereException("Multi product HUs are not allowed");
		}

		return productIds.iterator().next();
	}

	public LookupValuesList getPickingSlotValues(@NonNull final LookupDataSourceContext context)
	{
		if (shipmentScheduleId == null)
		{
			return LookupValuesList.EMPTY;
		}

		final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);
		final I_M_ShipmentSchedule shipmentSchedule = Services.get(IShipmentSchedulePA.class).getById(shipmentScheduleId);

		final PickingSlotQuery pickingSlotQuery = PickingSlotQuery.builder()
				.availableForBPartnerId(shipmentScheduleEffectiveBL.getBPartnerId(shipmentSchedule))
				.availableForBPartnerLocationId(shipmentScheduleEffectiveBL.getBPartnerLocationId(shipmentSchedule))
				.build();
		final List<I_M_PickingSlot> availablePickingSlots = Services.get(IPickingSlotDAO.class).retrievePickingSlots(pickingSlotQuery);

		return availablePickingSlots.stream()
				.map(pickingSlot -> IntegerLookupValue.of(pickingSlot.getM_PickingSlot_ID(), createPickingSlotLabel(pickingSlot)))
				.collect(LookupValuesList.collect());
	}

	private String createPickingSlotLabel(@NonNull final I_M_PickingSlot pickingslot)
	{
		final StringBuilder result = new StringBuilder(pickingslot.getPickingSlot());
		
		final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(pickingslot.getC_BPartner_ID());
		if (bpartnerId != null)
		{
			final IBPartnerDAO bpartnersRepo = Services.get(IBPartnerDAO.class);
			final I_C_BPartner bpartner = bpartnersRepo.getById(bpartnerId);
			result.append("_").append(bpartner.getName()).append("_").append(bpartner.getValue());
		}
		return result.toString();
	}

	public Object getDefaultValue(final IProcessDefaultParameter parameter)
	{
		if (PARAM_M_ShipmentSchedule_ID.equals(parameter.getColumnName()))
		{
			final ShipmentScheduleId defaultShipmentScheduleId = getDefaultShipmentScheduleId();
			if (defaultShipmentScheduleId != null)
			{
				return defaultShipmentScheduleId.getRepoId();
			}
		}

		// Fallback
		return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
	}

	private ShipmentScheduleId getDefaultShipmentScheduleId()
	{
		if (salesOrderLineId == null)
		{
			return null;
		}

		return Services.get(IShipmentSchedulePA.class).getShipmentScheduleIdByOrderLineId(salesOrderLineId);
	}

}
