/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.handlingunits.movement.api.impl;

import com.google.common.collect.ImmutableSetMultimap;
import de.metas.common.util.time.SystemTime;
import de.metas.handlingunits.HUContextDateTrxProvider.ITemporaryDateTrx;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHUStatusBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_MovementLine;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.movement.api.IHUMovementBL;
import de.metas.handlingunits.movement.generate.HUMovementGenerateRequest;
import de.metas.handlingunits.movement.generate.HUMovementGenerator;
import de.metas.handlingunits.movement.generate.HUMovementGeneratorResult;
import de.metas.handlingunits.sourcehu.SourceHUsService;
import de.metas.interfaces.I_M_Movement;
import de.metas.organization.OrgId;
import de.metas.product.IProductActivityProvider;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mmovement.api.IMovementDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class HUMovementBL implements IHUMovementBL
{
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final IHUAssignmentBL huAssignmentBL = Services.get(IHUAssignmentBL.class);
	private final IHUAssignmentDAO huAssignmentDAO = Services.get(IHUAssignmentDAO.class);
	private final IMovementDAO movementDAO = Services.get(IMovementDAO.class);
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	private final IWarehouseDAO warehousesDAO = Services.get(IWarehouseDAO.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);
	private final IHUStatusBL huStatusBL = Services.get(IHUStatusBL.class);

	@Override
	public void createPackingMaterialMovementLines(final I_M_Movement movement)
	{
		HUPackingMaterialMovementLineAggregateBuilder.newInstance().setM_Movement(movement)
				.createPackingMaterialMovementLines();
	}

	@Override
	public void setPackingMaterialCActivity(final I_M_MovementLine movementLine)
	{
		final ClientId clientId = ClientId.ofRepoId(movementLine.getAD_Client_ID());
		final OrgId orgId = OrgId.ofRepoId(movementLine.getAD_Org_ID());
		final ProductId productId = ProductId.ofRepoId(movementLine.getM_Product_ID());

		final IProductActivityProvider productActivityProvider = Services.get(IProductActivityProvider.class);
		final ActivityId productActivityId = productActivityProvider.getActivityForAcct(clientId, orgId, productId);

		movementLine.setC_ActivityFrom_ID(ActivityId.toRepoId(productActivityId));
		movementLine.setC_Activity_ID(ActivityId.toRepoId(productActivityId));

		InterfaceWrapperHelper.save(movementLine);
	}

	@Nullable
	@Override
	public LocatorId getDirectMoveLocatorId()
	{
		//
		// Get the M_Warehouse_ID
		final Properties ctx = Env.getCtx();
		final int adClientId = Env.getAD_Client_ID(ctx);
		final int adOrgId = Env.getAD_Org_ID(ctx);
		final WarehouseId directMoveWarehouseId = WarehouseId.optionalOfRepoId(
						sysConfigBL.getIntValue(
								SYSCONFIG_DirectMove_Warehouse_ID, -1, // defaultValue,
								adClientId, // AD_Client_ID
								adOrgId // AD_Org_ID
						))
				.orElseThrow(() -> new AdempiereException("Missing AD_SysConfig record with Name = " + SYSCONFIG_DirectMove_Warehouse_ID + " for AD_Client_ID=" + adClientId + " and AD_Org_ID=" + adOrgId));

		return warehouseBL.getOrCreateDefaultLocatorId(directMoveWarehouseId);
	}

	@Override
	public HUMovementGeneratorResult moveHUsToWarehouse(@NonNull final Collection<I_M_HU> hus, @NonNull final WarehouseId warehouseToId)
	{
		final LocatorId locatorToId = warehouseBL.getOrCreateDefaultLocatorId(warehouseToId);
		return moveHUsToLocator(hus, locatorToId);
	}

	@Override
	public void moveHUs(@NonNull final HUMovementGenerateRequest request)
	{
		new HUMovementGenerator(request).createMovement();
	}

	@Override
	public HUMovementGeneratorResult moveHUsToLocator(@NonNull final Collection<I_M_HU> hus, @NonNull final LocatorId toLocatorId)
	{
		if (hus.isEmpty())
		{
			throw new AdempiereException("@NoSelection@ @M_HU_ID@");
		}

		HUMovementGeneratorResult result = HUMovementGeneratorResult.EMPTY;

		final ImmutableSetMultimap<LocatorId, HuId> huIdsByLocatorId = hus.stream()
				.collect(ImmutableSetMultimap.toImmutableSetMultimap(
						IHandlingUnitsBL::extractLocatorId,
						hu -> HuId.ofRepoId(hu.getM_HU_ID())));
		for (final LocatorId fromLocatorId : huIdsByLocatorId.keySet())
		{
			// Skip HUs that are already at target locator
			if (LocatorId.equals(fromLocatorId, toLocatorId))
			{
				continue;
			}

			final HUMovementGenerateRequest request = HUMovementGenerateRequest.builder()
					.fromLocatorId(fromLocatorId)
					.toLocatorId(toLocatorId)
					.huIdsToMove(huIdsByLocatorId.get(fromLocatorId))
					.movementDate(SystemTime.asInstant())
					.build();

			final HUMovementGeneratorResult currentResult = new HUMovementGenerator(request)
					.considerPreloadedHUs(hus)
					.createMovement();

			result = result.combine(currentResult);
		}

		return result;
	}

	@Override
	public void assignHU(final org.compiere.model.I_M_MovementLine movementLine, final I_M_HU hu,
						 final boolean isTransferPackingMaterials, final String trxName)
	{
		huAssignmentBL.assignHU(movementLine, hu, isTransferPackingMaterials, trxName);
	}

	@Override
	public void setAssignedHandlingUnits(final org.compiere.model.I_M_MovementLine movementLine, final List<I_M_HU> hus)
	{
		huAssignmentBL.setAssignedHandlingUnits(movementLine, hus);
	}

	@Override
	public void moveHandlingUnits(final I_M_Movement movement, final boolean doReversal)
	{
		final Date movementDate = movement.getMovementDate();

		try (final ITemporaryDateTrx ignored = IHUContext.DateTrxProvider.temporarySet(movementDate))
		{
			for (final I_M_MovementLine movementLine : movementDAO.retrieveLines(movement, I_M_MovementLine.class))
			{
				moveHandlingUnits(movementLine, doReversal);
			}
		}
	}

	private void moveHandlingUnits(final I_M_MovementLine movementLine, final boolean doReversal)
	{
		final I_M_Locator locatorFrom;
		final I_M_Locator locatorTo;
		if (!doReversal)
		{
			locatorFrom = movementLine.getM_Locator();
			locatorTo = movementLine.getM_LocatorTo();
		}
		else
		{
			locatorFrom = movementLine.getM_LocatorTo();
			locatorTo = movementLine.getM_Locator();
		}

		final List<I_M_HU> hus = huAssignmentDAO.retrieveTopLevelHUsForModel(movementLine);
		for (final I_M_HU hu : hus)
		{
			final int huLocatorId = hu.getM_Locator_ID();
			Check.assume(huLocatorId > 0
							&& (huLocatorId == locatorFrom.getM_Locator_ID() || huLocatorId == locatorTo.getM_Locator_ID()),
					"HU Locator was supposed to be {} or {}, but was {}", locatorFrom, locatorTo, huLocatorId);

			moveHandlingUnit(hu, LocatorId.ofRecord(locatorTo));
		}
	}

	private void moveHandlingUnit(final I_M_HU hu, final LocatorId locatorToId)
	{
		final SourceHUsService sourceHuService = SourceHUsService.get();

		//
		// Make sure HU's current locator is the locator from which we need to move
		// final int huLocatorIdOld = hu.getM_Locator_ID();
		final LocatorId locatorFromId = IHandlingUnitsBL.extractLocatorId(hu);

		// If already moved, then do nothing.
		if (Objects.equals(locatorFromId, locatorToId))
		{
			return;
		}

		final HuId huId = HuId.ofRepoId(hu.getM_HU_ID());

		final boolean isSourceHU = sourceHuService.isSourceHu(huId);

		if (isSourceHU)
		{
			sourceHuService.deleteSourceHuMarker(huId);
		}

		//
		// Update HU's Locator
		// FIXME: refactor this and have a common way of setting HU's locator
		hu.setM_Locator_ID(locatorToId.getRepoId());

		// Activate HU (not needed, but we want to be sure)
		// (even if we do reversals)

		// NOTE: as far as we know, HUContext won't be used by setHUStatus, because the
		// status "active" doesn't
		// trigger a movement to/from empties warehouse. In this case a movement is
		// already created from a lager to another.
		// So no HU leftovers.
		final IContextAware contextProvider = InterfaceWrapperHelper.getContextAware(hu);
		final IMutableHUContext huContext = huContextFactory.createMutableHUContext(contextProvider);

		huStatusBL.setHUStatus(huContext, hu, X_M_HU.HUSTATUS_Active);

		// Save changed HU
		handlingUnitsDAO.saveHU(hu);

		final I_M_Locator locatorTo = warehousesDAO.getLocatorById(locatorToId);

		final I_M_Warehouse warehouseTo = warehousesDAO.getById(WarehouseId.ofRepoId(locatorTo.getM_Warehouse_ID()));
		if (warehouseTo.isReceiveAsSourceHU())
		{
			sourceHuService.addSourceHuMarker(huId);
		}
	}
}
