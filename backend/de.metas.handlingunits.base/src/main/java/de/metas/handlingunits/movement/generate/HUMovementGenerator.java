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

package de.metas.handlingunits.movement.generate;

import com.google.common.collect.ImmutableList;
import de.metas.document.DocTypeId;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.freighcost.FreightCostRule;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.allocation.IHUContextProcessor;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_MovementLine;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.interfaces.I_M_Movement;
import de.metas.order.DeliveryRule;
import de.metas.order.DeliveryViaRule;
import de.metas.organization.ClientAndOrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.project.ProjectId;
import de.metas.shipping.ShipperId;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mmovement.MovementAndLineId;
import org.adempiere.mmovement.api.IMovementBL;
import org.adempiere.mmovement.api.IMovementDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.warehouse.LocatorId;
import org.compiere.model.I_C_UOM;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Generate {@link I_M_Movement} to move given {@link I_M_HU}s
 */
public class HUMovementGenerator
{
	// services
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);
	private final IHUAssignmentBL huAssignmentBL = Services.get(IHUAssignmentBL.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	private final IMovementBL movementBL = Services.get(IMovementBL.class);
	private final IMovementDAO movementDAO = Services.get(IMovementDAO.class);

	//
	// Parameters
	private final HUMovementGenerateRequest request;

	//
	// Status
	private HuIdsWithPackingMaterialsTransferred huIdsWithPackingMaterialsTransferred = new HuIdsWithPackingMaterialsTransferred();
	private final HashMap<HuId, I_M_HU> husCache = new HashMap<>();
	private final List<I_M_HU> _husMoved = new ArrayList<>();
	private LocatorId locatorFromId;
	private I_M_Movement movementHeader;
	private final LinkedHashMap<ProductId, I_M_MovementLine> movementLines = new LinkedHashMap<>();
	private boolean executed;

	public HUMovementGenerator(@NonNull final HUMovementGenerateRequest request)
	{
		this.request = request;
	}

	public HUMovementGenerator considerPreloadedHUs(@NonNull final Collection<I_M_HU> hus)
	{
		hus.forEach(this::considerPreloadedHU);
		return this;
	}

	public HUMovementGenerator considerPreloadedHU(@NonNull final I_M_HU hu)
	{
		husCache.put(HuId.ofRepoId(hu.getM_HU_ID()), hu);
		return this;
	}

	public HUMovementGenerator sharedHUIdsWithPackingMaterialsTransferred(@NonNull final HuIdsWithPackingMaterialsTransferred sharedHUIdsWithPackingMaterialsTransferred)
	{
		this.huIdsWithPackingMaterialsTransferred = sharedHUIdsWithPackingMaterialsTransferred;
		return this;
	}

	private Collection<I_M_HU> getHUsByIds(@NonNull final Collection<HuId> huIds)
	{
		return CollectionUtils.getAllOrLoad(
				husCache,
				huIds,
				handlingUnitsBL::getByIdsReturningMap);
	}

	private ImmutableList<MovementAndLineId> getMovementAndLineIds()
	{
		return movementLines.values()
				.stream()
				.map(movementLine -> MovementAndLineId.ofRepoId(movementLine.getM_Movement_ID(), movementLine.getM_MovementLine_ID()))
				.collect(ImmutableList.toImmutableList());
	}

	final List<I_M_HU> getHUsMoved()
	{
		return _husMoved;
	}

	private void addHUMoved(final I_M_HU hu)
	{
		_husMoved.add(hu);
	}

	private void markExecuted()
	{
		assertNotExecuted();
		executed = true;
	}

	private void assertNotExecuted()
	{
		if (executed)
		{
			throw new AdempiereException("Already executed");
		}
	}

	/**
	 * Create and process the movement. Note that this BL only creates lines for the goods within the HUs,
	 * but there is a model interceptor that creates the packing material lines was soon as the M_Movement is prepared.
	 *
	 * @return movement
	 */
	public HUMovementGeneratorResult createMovement()
	{
		markExecuted();

		huTrxBL.createHUContextProcessorExecutor(PlainContextAware.newWithThreadInheritedTrx())
				.run(huContext -> {
					createMovement0(huContext);
					return IHUContextProcessor.NULL_RESULT; // we don't care about the result
				});

		//
		// Get the generated movement (if any)
		final I_M_Movement movement = getMovementHeaderOrNull();
		if (movement != null)
		{
			return HUMovementGeneratorResult.builder()
					.movements(ImmutableList.of(movement))
					.movementLineIds(getMovementAndLineIds())
					.husMoved(getHUsMoved())
					.build();
		}
		else
		{
			return HUMovementGeneratorResult.EMPTY;
		}
	}

	private void createMovement0(@NonNull final IHUContext huContext)
	{
		//
		// Get the HUs to move
		final Collection<I_M_HU> husToMove = getHUsByIds(request.getHuIdsToMove());
		if (Check.isEmpty(husToMove))
		{
			throw new HUException("@NoSelection@ (@M_HU_ID@)");
		}

		assertTopLevelHUs(husToMove);

		this.locatorFromId = determineFromLocatorId(husToMove);
		if (LocatorId.equals(locatorFromId, request.getToLocatorId()))
		{
			return;
		}

		//
		// Iterate the HUs to move and create the movement lines for them
		final IHUStorageFactory huStorageFactory = huContext.getHUStorageFactory();
		for (final I_M_HU hu : husToMove)
		{
			//
			// Iterate the product storages of this HU and create/update the movement lines
			final IHUStorage huStorage = huStorageFactory.getStorage(hu);
			final List<IHUProductStorage> productStorages = huStorage.getProductStorages();
			if (!productStorages.isEmpty())
			{
				for (final IHUProductStorage productStorage : productStorages)
				{
					addOrUpdateMovementLine(productStorage);
				}

				addHUMoved(hu);
			}
		}

		//
		// Complete the movement (if any)
		final I_M_Movement movement = getMovementHeaderOrNull();
		if (movement != null)
		{
			documentBL.processEx(movement, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
		}
	}

	private LocatorId determineFromLocatorId(final Collection<I_M_HU> husToMove)
	{
		LocatorId locatorFromId = request.getFromLocatorId();

		for (final I_M_HU hu : husToMove)
		{
			final LocatorId huLocatorId = IHandlingUnitsBL.extractLocatorId(hu);
			if (locatorFromId == null)
			{
				locatorFromId = huLocatorId;
			}
			else if (!LocatorId.equals(locatorFromId, huLocatorId))
			{
				throw new HUException("HU's locator does not match movement's locator from."
						+ "\n Movement Locator From: " + locatorFromId
						+ "\n HU's Locator: " + huLocatorId);
			}
		}

		return locatorFromId;
	}

	private I_M_Movement getOrCreateMovementHeader()
	{
		if (movementHeader == null)
		{
			movementHeader = createMovementHeader();
		}
		return movementHeader;
	}

	private I_M_Movement createMovementHeader()
	{
		final I_M_Movement movement = InterfaceWrapperHelper.newInstance(I_M_Movement.class);
		movement.setDocStatus(IDocument.STATUS_Drafted);
		movement.setDocAction(IDocument.ACTION_Complete);

		//
		// Org and Document Type
		final ClientAndOrgId clientAndOrgId = request.getClientAndOrgId();
		if (clientAndOrgId != null)
		{
			movement.setAD_Org_ID(clientAndOrgId.getOrgId().getRepoId());
			final DocTypeId docTypeId = movementBL.getDocTypeId(clientAndOrgId);
			movement.setC_DocType_ID(docTypeId.getRepoId());
		}

		//
		// Reference
		if (request.getDdOrderLineId() != null)
		{
			movement.setDD_Order_ID(request.getDdOrderLineId().getDdOrderId().getRepoId());
		}
		movement.setPOReference(request.getPoReference());
		movement.setDescription(request.getDescription());

		//
		// Internal contact user
		movement.setSalesRep_ID(UserId.toRepoId(request.getSalesRepId()));

		//
		// BPartner (i.e. shipper BP)
		if (request.getBpartnerAndLocationId() != null)
		{
			movement.setC_BPartner_ID(request.getBpartnerAndLocationId().getBpartnerId().getRepoId());
			movement.setC_BPartner_Location_ID(request.getBpartnerAndLocationId().getRepoId());
		}
		if (request.getBpartnerContactId() != null)
		{
			movement.setAD_User_ID(request.getBpartnerContactId().getRepoId());
		}

		//
		// Shipper
		movement.setM_Shipper_ID(ShipperId.toRepoId(request.getShipperId()));
		movement.setFreightCostRule(FreightCostRule.toCodeOrNull(request.getFreightCostRule()));
		movement.setFreightAmt(request.getFreightAmt());

		//
		// Delivery Rules & Priority
		movement.setDeliveryRule(DeliveryRule.toCodeOrNull(request.getDeliveryRule()));
		movement.setDeliveryViaRule(DeliveryViaRule.toCodeOrNull(request.getDeliveryViaRule()));
		movement.setPriorityRule(request.getPriorityRule());

		//
		// Dates
		movement.setMovementDate(Timestamp.from(request.getMovementDate()));

		//
		// Dimensions
		// 07689: This set of the activity is harmless, even though this column is currently hidden
		if (request.getDimensionFields() != null)
		{
			movement.setC_Activity_ID(ActivityId.toRepoId(request.getDimensionFields().getActivityId()));
			movement.setC_Campaign_ID(request.getDimensionFields().getCampaignId());
			movement.setC_Project_ID(ProjectId.toRepoId(request.getDimensionFields().getProjectId()));
			movement.setUser1_ID(request.getDimensionFields().getUser1_ID());
			movement.setUser2_ID(request.getDimensionFields().getUser2_ID());
		}

		movementBL.save(movement);

		return movement;
	}

	@Nullable
	private I_M_Movement getMovementHeaderOrNull()
	{
		return movementHeader;
	}

	private void addOrUpdateMovementLine(@NonNull final IHUProductStorage productStorage)
	{
		// Skip it if product storage is empty
		if (productStorage.isEmpty())
		{
			return;
		}

		final ProductId productId = productStorage.getProductId();
		final I_M_MovementLine movementLine = getOrCreateMovementLine(productId);

		final I_C_UOM productUOM = productBL.getStockUOM(productId);
		final BigDecimal qtyToMove = productStorage.getQty(productUOM).toBigDecimal();

		//
		// Adjust movement line's qty to move
		final BigDecimal movementLine_Qty_Old = movementLine.getMovementQty();
		final BigDecimal movementLine_Qty_New = movementLine_Qty_Old.add(qtyToMove);
		movementLine.setMovementQty(movementLine_Qty_New);

		// Make sure movement line it's saved
		movementDAO.save(movementLine);

		// Assign the HU to movement line
		{
			final I_M_HU hu = productStorage.getM_HU();
			final boolean isTransferPackingMaterials = huIdsWithPackingMaterialsTransferred.addHuId(productStorage.getHuId());
			huAssignmentBL.assignHU(movementLine, hu, isTransferPackingMaterials, ITrx.TRXNAME_ThreadInherited);
		}
	}

	private I_M_MovementLine getOrCreateMovementLine(final ProductId productId)
	{
		return movementLines.computeIfAbsent(productId, this::newMovementLine);
	}

	@NonNull
	private I_M_MovementLine newMovementLine(final ProductId productId)
	{
		final I_M_Movement movement = getOrCreateMovementHeader();
		I_M_MovementLine movementLine = InterfaceWrapperHelper.newInstance(I_M_MovementLine.class, movement);
		movementLine.setAD_Org_ID(movement.getAD_Org_ID());
		movementLine.setM_Movement_ID(movement.getM_Movement_ID());

		movementLine.setIsPackagingMaterial(false);

		movementLine.setM_Product_ID(productId.getRepoId());

		movementLine.setM_Locator_ID(locatorFromId.getRepoId());
		movementLine.setM_LocatorTo_ID(request.getToLocatorId().getRepoId());

		//
		// Reference
		if (request.getDdOrderLineId() != null)
		{
			movementLine.setDD_OrderLine_ID(request.getDdOrderLineId().getDdOrderLineId().getRepoId());
		}

		// NOTE: we are not saving the movement line
		return movementLine;
	}

	private void assertTopLevelHUs(final Collection<I_M_HU> hus)
	{
		for (final I_M_HU hu : hus)
		{
			if (!handlingUnitsBL.isTopLevel(hu))
			{
				throw new HUException("@M_HU_ID@ @TopLevel@=@N@: " + hu);
			}
		}
	}
}

