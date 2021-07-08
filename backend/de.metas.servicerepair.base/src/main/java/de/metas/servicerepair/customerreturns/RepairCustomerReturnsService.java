/*
 * #%L
 * metasfresh-webui-api
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

package de.metas.servicerepair.customerreturns;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Maps;
import de.metas.common.util.time.SystemTime;
import de.metas.document.DocBaseAndSubType;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeDAO;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.allocation.IHUProducerAllocationDestination;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.GenericAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.allocation.impl.HUProducerDestination;
import de.metas.handlingunits.inout.IHUInOutBL;
import de.metas.handlingunits.inout.returns.ReturnsServiceFacade;
import de.metas.handlingunits.inout.returns.customer.CreateCustomerReturnLineReq;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.storage.impl.PlainProductStorage;
import de.metas.inout.IInOutBL;
import de.metas.inout.InOutAndLineId;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.X_C_DocType;
import org.compiere.util.TimeUtil;
import org.eevolution.api.BOMType;
import org.eevolution.api.IProductBOMBL;
import org.eevolution.api.QtyCalculationsBOM;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
public class RepairCustomerReturnsService
{
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IInOutBL inoutBL = Services.get(IInOutBL.class);
	private final IHUInOutBL huInoutBL = Services.get(IHUInOutBL.class);
	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	private final IProductBOMBL productBOMBL = Services.get(IProductBOMBL.class);
	private final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final ReturnsServiceFacade returnsServiceFacade;
	private final AlreadyShippedHUsInPreviousSystemRepository alreadyShippedHUsInPreviousSystemRepository;

	private final DocBaseAndSubType DOCBASEANDSUBTYPE_ServiceRepair = DocBaseAndSubType.of(X_C_DocType.DOCBASETYPE_MaterialReceipt, X_C_DocType.DOCSUBTYPE_SR);

	RepairCustomerReturnsService(
			@NonNull final ReturnsServiceFacade returnsServiceFacade,
			@NonNull final AlreadyShippedHUsInPreviousSystemRepository alreadyShippedHUsInPreviousSystemRepository)
	{
		this.returnsServiceFacade = returnsServiceFacade;
		this.alreadyShippedHUsInPreviousSystemRepository = alreadyShippedHUsInPreviousSystemRepository;
	}

	public boolean isRepairCustomerReturns(@NonNull final I_M_InOut inout)
	{
		final DocBaseAndSubType docBaseAndSubType = DocTypeId.optionalOfRepoId(inout.getC_DocType_ID())
				.map(docTypeDAO::getDocBaseAndSubTypeById)
				.orElse(null);

		return DOCBASEANDSUBTYPE_ServiceRepair.equals(docBaseAndSubType);
	}

	@Builder(builderMethodName = "prepareAddSparePartsToCustomerReturn", builderClassName = "$AddSparePartsToCustomerReturnBuilder")
	private void addSparePartsToCustomerReturn(
			@NonNull final InOutId customerReturnId,
			@NonNull final ProductId productId,
			@NonNull final Quantity qtyReturned)
	{
		if (qtyReturned.signum() <= 0)
		{
			throw new AdempiereException("@QtyReturned@ <= 0");
		}

		final I_M_InOutLine customerReturnLine = returnsServiceFacade.createCustomerReturnLine(
				CreateCustomerReturnLineReq.builder()
						.headerId(customerReturnId)
						.productId(productId)
						.qtyReturned(qtyReturned)
						.build());

		createAndAssignPlanningVHU(customerReturnLine, productId, qtyReturned);
	}

	private void createAndAssignPlanningVHU(
			@NonNull final I_M_InOutLine customerReturnLine,
			@NonNull final ProductId productId,
			@NonNull final Quantity qtyReturned)
	{
		final IHUProducerAllocationDestination destination;

		HULoader.builder()
				.source(new GenericAllocationSourceDestination(
						new PlainProductStorage(productId, qtyReturned),
						customerReturnLine))
				.destination(destination = HUProducerDestination.ofVirtualPI()
						.setHUStatus(X_M_HU.HUSTATUS_Planning))
				.load(AllocationUtils.builder()
						.setHUContext(handlingUnitsBL.createMutableHUContextForProcessing())
						.setProduct(productId)
						.setQuantity(qtyReturned)
						.setDate(SystemTime.asZonedDateTime())
						.setFromReferencedModel(customerReturnLine)
						.setForceQtyAllocation(true)
						.create());

		final I_M_HU vhu = destination.getSingleCreatedHU().orElseThrow(() -> new AdempiereException("No VHU created"));

		returnsServiceFacade.assignHandlingUnitToHeaderAndLine(customerReturnLine, vhu);
	}

	@Builder(builderMethodName = "prepareCloneHUAndCreateCustomerReturnLine", builderClassName = "$CloneHUAndCreateCustomerReturnLineBuilder")
	public void cloneHUAndCreateCustomerReturnLine(
			@NonNull final InOutId customerReturnId,
			@NonNull final ProductId productId,
			@NonNull final HuId cloneFromHuId,
			@NonNull final Quantity qtyReturned)
	{
		final I_M_HU clonedPlaningHU = handlingUnitsBL.copyAsPlannedHU(cloneFromHuId);

		final ImmutableAttributeSet attributes = handlingUnitsBL.getImmutableAttributeSet(clonedPlaningHU);
		final I_M_AttributeSetInstance asi = attributeSetInstanceBL.createASIWithASFromProductAndInsertAttributeSet(productId, attributes);
		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoId(asi.getM_AttributeSetInstance_ID());

		final LocalDate returnDate = getReturnDate(customerReturnId);

		final I_M_InOutLine customerReturnLine = returnsServiceFacade.createCustomerReturnLine(
				CreateCustomerReturnLineReq.builder()
						.headerId(customerReturnId)
						.productId(productId)
						.attributeSetInstanceId(asiId)
						.warrantyCase(isWarrantyCase(productId, attributes, returnDate))
						.qtyReturned(qtyReturned)
						.build());

		returnsServiceFacade.assignHandlingUnitToHeaderAndLine(customerReturnLine, clonedPlaningHU);
	}

	private LocalDate getReturnDate(@NonNull final InOutId customerReturnId)
	{
		final I_M_InOut customerReturn = inoutBL.getById(customerReturnId);
		final ZoneId timeZone = orgDAO.getTimeZone(OrgId.ofRepoId(customerReturn.getAD_Org_ID()));
		return TimeUtil.asLocalDate(customerReturn.getMovementDate(), timeZone);
	}

	@VisibleForTesting
	boolean isWarrantyCase(
			@NonNull final ProductId productId,
			@NonNull final ImmutableAttributeSet attributes,
			@NonNull final LocalDate today)
	{
		if (!attributes.hasAttribute(AttributeConstants.WarrantyStartDate))
		{
			return false;
		}

		final LocalDate warrantyStartDate = attributes.getValueAsLocalDate(AttributeConstants.WarrantyStartDate);
		if (warrantyStartDate == null)
		{
			return false;
		}

		final int guaranteeDays = productDAO.getProductGuaranteeDaysMinFallbackProductCategory(productId);
		final LocalDate warrantyEndDate = warrantyStartDate.plusDays(guaranteeDays);

		return today.isBefore(warrantyEndDate) || today.equals(warrantyEndDate);
	}

	public SparePartsReturnCalculation getSparePartsCalculation(final InOutId customerReturnId)
	{
		final List<I_M_InOutLine> customerReturnLines = getCustomerReturnLines(customerReturnId);

		final ImmutableMap<ProductId, QtyCalculationsBOM> sparePartsBOMs = Maps.uniqueIndex(
				productBOMBL.getQtyCalculationBOMs(extractProductIds(customerReturnLines), BOMType.PreviousSpare),
				QtyCalculationsBOM::getBomProductId);

		final ImmutableSetMultimap<InOutLineId, HuId> vhuIdsByCustomerReturnLineId = huInoutBL.getHUIdsByInOutLineIds(extractInOutLineIds(customerReturnLines));

		final SparePartsReturnCalculation.SparePartsReturnCalculationBuilder resultBuilder = SparePartsReturnCalculation.builder();
		for (final I_M_InOutLine customerReturnLine : customerReturnLines)
		{
			final ProductId productId = ProductId.ofRepoId(customerReturnLine.getM_Product_ID());
			final Quantity qtyReturned = Quantity.of(customerReturnLine.getQtyEntered(), uomDAO.getById(customerReturnLine.getC_UOM_ID()));
			final InOutAndLineId customerReturnLineId = InOutAndLineId.ofRepoId(customerReturnLine.getM_InOut_ID(), customerReturnLine.getM_InOutLine_ID());

			final ImmutableSet<HuId> vhuIds = vhuIdsByCustomerReturnLineId.get(customerReturnLineId.getInOutLineId());

			final QtyCalculationsBOM sparePartsBOM = sparePartsBOMs.get(productId);
			if (sparePartsBOM != null)
			{
				final HuId repairVhuId = CollectionUtils.singleElement(vhuIds);

				resultBuilder.finishedGood(SparePartsReturnCalculation.FinishedGoodToRepair.builder()
						.sparePartsBOM(sparePartsBOM)
						.asiId(AttributeSetInstanceId.ofRepoIdOrNone(customerReturnLine.getM_AttributeSetInstance_ID()))
						.warrantyCase(WarrantyCase.ofBoolean(customerReturnLine.isWarrantyCase()))
						.qty(qtyReturned)
						.customerReturnLineId(customerReturnLineId)
						.repairVhuId(repairVhuId)
						.build());
			}
			else
			{
				final HuId sparePartsVhuId = CollectionUtils.singleElement(vhuIds);

				resultBuilder.sparePart(SparePartsReturnCalculation.SparePart.builder()
						.sparePartId(productId)
						.qty(qtyReturned)
						.customerReturnLineId(customerReturnLineId)
						.sparePartsVhuId(sparePartsVhuId)
						.build());
			}
		}

		return resultBuilder.build();
	}

	private List<I_M_InOutLine> getCustomerReturnLines(final InOutId customerReturnId)
	{
		return inoutBL.getLines(customerReturnId)
				.stream()
				.filter(customerReturnLine -> ProductId.ofRepoIdOrNull(customerReturnLine.getM_Product_ID()) != null)
				.collect(ImmutableList.toImmutableList());
	}

	private static ImmutableSet<ProductId> extractProductIds(final List<I_M_InOutLine> customerReturnLines)
	{
		return customerReturnLines.stream()
				.map(customerReturnLine -> ProductId.ofRepoId(customerReturnLine.getM_Product_ID()))
				.collect(ImmutableSet.toImmutableSet());
	}

	private static ImmutableSet<InOutLineId> extractInOutLineIds(final List<I_M_InOutLine> customerReturnLines)
	{
		return customerReturnLines.stream()
				.map(customerReturnLine -> InOutLineId.ofRepoId(customerReturnLine.getM_InOutLine_ID()))
				.collect(ImmutableSet.toImmutableSet());
	}

	public Optional<AlreadyShippedHUsInPreviousSystem> searchAlreadyShippedHUsInPreviousSystemRepositoryBySerialNo(@Nullable final String serialNo)
	{
		return alreadyShippedHUsInPreviousSystemRepository.getBySerialNo(serialNo);
	}
}
