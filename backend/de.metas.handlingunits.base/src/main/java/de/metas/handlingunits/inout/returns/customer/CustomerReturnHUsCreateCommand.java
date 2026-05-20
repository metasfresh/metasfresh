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

package de.metas.handlingunits.inout.returns.customer;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.common.util.time.SystemTime;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.impl.CopyHUsResponse;
import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.inout.IHUInOutBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.IAllocationDestination;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.GenericAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HUListAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_InOut;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.storage.impl.PlainProductStorage;
import de.metas.handlingunits.trace.HUAccessService;
import de.metas.handlingunits.trace.HUTraceEventsService;
import de.metas.handlingunits.trace.HUTraceForReturnedQtyRequest;
import de.metas.inout.InOutId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_UOM;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import javax.annotation.Nullable;


public class CustomerReturnHUsCreateCommand
{
	// services
	private final IHUPIItemProductDAO hupiItemProductDAO = Services.get(IHUPIItemProductDAO.class);
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final IUOMDAO uomDao = Services.get(IUOMDAO.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IWarehouseDAO warehousesRepo = Services.get(IWarehouseDAO.class);
	private final IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);
	private final IHUAssignmentBL huAssignmentBL = Services.get(IHUAssignmentBL.class);
	private final IHUInOutBL huInOutBL = Services.get(IHUInOutBL.class);
	private final SpringContextHolder.Lazy<HUTraceEventsService> huTraceEventsServiceLazy = SpringContextHolder.lazyBean(HUTraceEventsService.class);
	private final SpringContextHolder.Lazy<HUAccessService> huAccessServiceLazy = SpringContextHolder.lazyBean(HUAccessService.class);

	private final I_M_InOutLine returnLine;
	private final boolean isOnlyCreateCUs;
	@NonNull private final ImmutableList<I_M_HU> originHUsForCopy;

	@Builder
	private CustomerReturnHUsCreateCommand(
			@NonNull final I_M_InOutLine returnLine,
			final boolean isOnlyCreateCUs,
			@Nullable final List<I_M_HU> originHUsForCopy)
	{
		this.returnLine = returnLine;
		this.isOnlyCreateCUs = isOnlyCreateCUs;
		this.originHUsForCopy = originHUsForCopy != null
				? ImmutableList.copyOf(originHUsForCopy)
				: ImmutableList.of();
	}

	public List<I_M_HU> execute()
	{
		return createHUsForReturnLine();
	}


	private List<I_M_HU> createHUsForReturnLine()
	{
		final List<I_M_HU> createdHUs;
		if (!originHUsForCopy.isEmpty())
		{
			createdHUs = copyOriginHUs(originHUsForCopy);
		}
		else
		{
			final HUPIItemProductId hupiItemProductId = HUPIItemProductId.ofRepoIdOrNull(returnLine.getM_HU_PI_Item_Product_ID());
			if (isOnlyCreateCUs || hupiItemProductId == null || hupiItemProductId.isVirtualHU())
			{
				createdHUs = ImmutableList.of(createCUs());
			}
			else
			{
				createdHUs = createLUTUs();
			}
		}

		// huInOutBL.setAssignedHandlingUnits(returnLine, createdHUs) sets isTransferPackingMaterials true
		// this then fails in de.metas.handlingunits.model.validator.M_HU_Assignment.assertOnlyOneAssignmentPerInOut
		// after HUAssignments are copied in de.metas.handlingunits.model.validator.M_InOut.destroyHandlingUnitsForReversedInboundMovements
		createdHUs.forEach(createdHU -> huAssignmentBL.assignHU(returnLine, createdHU, false, ITrx.TRXNAME_ThreadInherited));

		if (!originHUsForCopy.isEmpty())
		{
			createTraceRecordsForCopiedHUs(createdHUs);
		}

		return createdHUs;
	}

	private void createTraceRecordsForCopiedHUs(@NonNull final List<I_M_HU> copiedTopLevelHUs)
	{
		final InOutId customerReturnId = InOutId.ofRepoId(returnLine.getM_InOut_ID());
		final I_M_InOut returnHeader = huInOutBL.getById(customerReturnId, I_M_InOut.class);
		final OrgId orgId = OrgId.ofRepoId(returnLine.getAD_Org_ID());
		final Instant movementDate = returnHeader.getMovementDate().toInstant();

		for (final I_M_HU copiedTopLevelHU : copiedTopLevelHUs)
		{
			final HuId topLevelReturnedHUId = HuId.ofRepoId(copiedTopLevelHU.getM_HU_ID());

			for (final I_M_HU copiedVHU : handlingUnitsBL.getVHUs(copiedTopLevelHU))
			{
				final HuId sourceVhuId = HuId.ofRepoId(copiedVHU.getClonedFrom_HU_ID());

				huAccessServiceLazy.get().retrieveProductAndQty(copiedVHU).ifPresent(productAndQty -> {
					final HUTraceForReturnedQtyRequest request = HUTraceForReturnedQtyRequest.builder()
							.returnedVirtualHU(copiedVHU)
							.topLevelReturnedHUId(topLevelReturnedHUId)
							.sourceShippedVHUIds(ImmutableSet.of(sourceVhuId))
							.customerReturnId(customerReturnId)
							.docStatus(returnHeader.getDocStatus())
							.eventTime(movementDate)
							.orgId(orgId)
							.productId(productAndQty.getLeft())
							.qty(productAndQty.getRight())
							.build();

					huTraceEventsServiceLazy.get().createAndAddFor(request);
				});
			}
		}
	}

	private List<I_M_HU> createLUTUs()
	{
		final IContextAware contextProvider = InterfaceWrapperHelper.getContextAware(returnLine);
		return CustomerReturnLineHUGenerator.newInstance(contextProvider)
				.setIHUTrxListeners(ImmutableList.of(CreateReturnedHUsTrxListener.instance))
				.addM_InOutLine(returnLine)
				.generate();
	}

	private I_M_HU createCUs()
	{
		final IContextAware contextProvider = InterfaceWrapperHelper.getContextAware(returnLine);
		final IMutableHUContext huContext = handlingUnitsBL.createMutableHUContextForProcessing(contextProvider);
		huContext.getTrxListeners().addListener(CreateReturnedHUsTrxListener.instance);

		final ProductId productId = ProductId.ofRepoId(returnLine.getM_Product_ID());
		final Quantity qtyEntered = Quantity.of(returnLine.getQtyEntered(), uomDao.getById(returnLine.getC_UOM_ID()));

		final IAllocationRequest request = AllocationUtils.createQtyRequest(
				huContext,
				productId,
				qtyEntered,
				SystemTime.asZonedDateTime(),
				returnLine,
				true);

		final IAllocationSource source = createAllocationSource();

		final LocatorId locatorId = warehousesRepo.getLocatorIdByRepoId(returnLine.getM_Locator_ID());
		final I_M_HU returnCU = initializeCU(locatorId);
		final IAllocationDestination destination = HUListAllocationSourceDestination.of(returnCU);

		HULoader.of(source, destination)
				.setAllowPartialUnloads(false)
				.setAllowPartialLoads(true)
				.load(request);

		return returnCU;
	}

	private I_M_HU initializeCU(@NonNull final LocatorId locatorId)
	{
		final I_M_HU_PI_Item_Product piItemProduct = hupiItemProductDAO.getRecordById(HUPIItemProductId.VIRTUAL_HU);
		final I_M_HU_PI huPI = handlingUnitsDAO.getPackingInstructionById(HuPackingInstructionsId.VIRTUAL);

		return huTrxBL.createHUContextProcessorExecutor()
				.call(huContext -> handlingUnitsDAO.createHUBuilder(huContext)
						.setM_HU_Item_Parent(null) // no parent
						.setM_HU_PI_Item_Product(piItemProduct)
						.setLocatorId(locatorId)
						.setHUStatus(X_M_HU.HUSTATUS_Planning) //will change to active when completing the return
						.create(huPI));
	}

	private IAllocationSource createAllocationSource()
	{
		final ProductId productId = ProductId.ofRepoId(returnLine.getM_Product_ID());
		final I_C_UOM uom = uomDao.getById(returnLine.getC_UOM_ID());
		final BigDecimal qty = returnLine.getQtyEntered();

		final PlainProductStorage productStorage = new PlainProductStorage(productId, uom, qty);

		return new GenericAllocationSourceDestination(productStorage, returnLine);
	}

	private List<I_M_HU> copyOriginHUs(@NonNull final List<I_M_HU> originHUs)
	{
		final LocatorId returnLocatorId = warehousesRepo.getLocatorIdByRepoId(returnLine.getM_Locator_ID());

		final ImmutableList<HuId> originHuIds = originHUs.stream()
				.map(hu -> HuId.ofRepoId(hu.getM_HU_ID()))
				.collect(ImmutableList.toImmutableList());

		final CopyHUsResponse copyResponse = handlingUnitsBL.copyAsPlannedHUs()
				.huIdsToCopy(originHuIds)
				.targetLocatorId(returnLocatorId)
				.build()
				.execute();

		return copyResponse.getItems().stream()
				.map(CopyHUsResponse.CopyHUsResponseItem::getNewHU)
				.collect(ImmutableList.toImmutableList());
	}
}
