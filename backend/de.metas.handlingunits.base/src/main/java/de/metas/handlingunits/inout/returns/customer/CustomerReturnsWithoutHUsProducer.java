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
import de.metas.bpartner.BPartnerLocationId;
import de.metas.common.util.time.SystemTime;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.IHUWarehouseDAO;
import de.metas.handlingunits.IHandlingUnitsBL;
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
import de.metas.handlingunits.inout.IHUInOutBL;
import de.metas.handlingunits.inout.returns.ReturnedGoodsWarehouseType;
import de.metas.handlingunits.model.I_C_Order;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_InOut;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.storage.impl.PlainProductStorage;
import de.metas.inout.InOutId;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.CreateAttributeInstanceReq;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.mm.attributes.api.impl.AddAttributesRequest;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_C_UOM;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Service
public class CustomerReturnsWithoutHUsProducer
{
	// services
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final IHUWarehouseDAO huWarehouseDAO = Services.get(IHUWarehouseDAO.class);
	private final IHUPIItemProductDAO hupiItemProductDAO = Services.get(IHUPIItemProductDAO.class);
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final IUOMDAO uomDao = Services.get(IUOMDAO.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IWarehouseDAO warehousesRepo = Services.get(IWarehouseDAO.class);
	private final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
	private final IHUInOutBL huInOutBL = Services.get(IHUInOutBL.class);
	private final IDocumentBL docActionBL = Services.get(IDocumentBL.class);
	private final IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);

	private final CustomerReturnInOutRecordFactory customerReturnRepository;

	public CustomerReturnsWithoutHUsProducer(final CustomerReturnInOutRecordFactory customerReturnRepository)
	{
		this.customerReturnRepository = customerReturnRepository;
	}

	public List<InOutId> create(@NonNull final List<CustomerReturnLineCandidate> returnLineCandidates)
	{
		return trxManager.call(ITrx.TRXNAME_ThreadInherited, () -> this.createInTrx(returnLineCandidates));
	}

	private List<InOutId> createInTrx(@NonNull final List<CustomerReturnLineCandidate> returnLineCandidates)
	{
		return createCustomerReturns(returnLineCandidates);
	}

	private List<InOutId> createCustomerReturns(@NonNull final List<CustomerReturnLineCandidate> returnLineCandidateList)
	{
		final ImmutableList.Builder<InOutId> createdReturnIdCollector = ImmutableList.builder();

		//1. try to group them by CustomerReturnLineGroupingKey
		final Map<CustomerReturnLineGroupingKey, List<CustomerReturnLineCandidate>> returnLineCandidatesByGroupingKey = new HashMap<>();

		returnLineCandidateList
				.stream()
				.filter(candidate -> candidate.getOrderId() != null)
				.forEach(returnLineCandidate -> {
					final List<CustomerReturnLineCandidate> returnLineCandidates = new ArrayList<>();
					returnLineCandidates.add(returnLineCandidate);

					returnLineCandidatesByGroupingKey.merge(CustomerReturnLineGroupingKey.of(returnLineCandidate), returnLineCandidates, (oldList, newList) -> {
						oldList.addAll(newList);
						return oldList;
					});
				});

		//2. generate customer returns for the grouped lines
		returnLineCandidatesByGroupingKey.keySet()
				.forEach(groupingKey -> {
					final List<CustomerReturnLineCandidate> returnLineCandidates = returnLineCandidatesByGroupingKey.get(groupingKey);

					//2.1 create the customer return header
					final I_M_InOut returnHeader = customerReturnRepository.createCustomerReturnHeader(createCustomerReturnHeaderRequest(groupingKey));
					final InOutId returnHeaderId = InOutId.ofRepoId(returnHeader.getM_InOut_ID());

					//2.2 create customer return lines
					final Stream<I_M_InOutLine> returnLines = returnLineCandidates
							.stream()
							.map(returnLineCandidate -> createCustomerReturnLineRequest(returnLineCandidate, returnHeaderId))
							.map(this::createReturnLine);

					//2.3 generate HUs for those lines
					final List<I_M_HU> createdHUs = returnLines.map(this::createHUsForReturnLine)
							.flatMap(Collection::stream)
							.collect(ImmutableList.toImmutableList());

					huInOutBL.setAssignedHandlingUnits(returnHeader, createdHUs);

					createdReturnIdCollector.add(returnHeaderId);

					//2.4 complete the return
					docActionBL.processEx(returnHeader, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
				});

		return createdReturnIdCollector.build();
	}

	public I_M_InOutLine createReturnLine(@NonNull final CreateCustomerReturnLineReq request)
	{
		return customerReturnRepository.createReturnLine(request);
	}

	private int getReturnsDocTypeId(final String docBaseType, final boolean isSOTrx, final int adClientId, final int adOrgId)
	{
		final DocTypeQuery query = DocTypeQuery.builder()
				.docBaseType(docBaseType)
				.docSubType(DocTypeQuery.DOCSUBTYPE_NONE) // in the case of returns the docSubType is null
				.isSOTrx(isSOTrx)
				.adClientId(adClientId)
				.adOrgId(adOrgId)
				.build();
		return DocTypeId.toRepoId(docTypeDAO.getDocTypeIdOrNull(query));
	}

	private CreateCustomerReturnHeaderReq createCustomerReturnHeaderRequest(@NonNull final CustomerReturnLineGroupingKey customerReturnGroupingKey)
	{
		final I_C_Order originSalesOrder = customerReturnGroupingKey.getOrderId() != null
				? orderDAO.getById(customerReturnGroupingKey.getOrderId(), I_C_Order.class)
				: null;

		final WarehouseId targetWarehouseId = getWarehouseIdForReceivingReturnedGoods(customerReturnGroupingKey.getWarehouseType());

		return CreateCustomerReturnHeaderReq.builder()
				.orgId(customerReturnGroupingKey.getOrgId())
				.bPartnerLocationId(customerReturnGroupingKey.getBPartnerLocationId())
				.warehouseId(targetWarehouseId)
				.order(originSalesOrder)
				.dateReceived(customerReturnGroupingKey.getDateReceived())
				.movementDate(customerReturnGroupingKey.getMovementDate())
				.returnDocTypeIdProvider(this::getReturnsDocTypeId)
				.externalId(customerReturnGroupingKey.getExternalId())
				.externalResourceURL(customerReturnGroupingKey.getExternalResourceURL())
				.build();
	}

	private CreateCustomerReturnLineReq createCustomerReturnLineRequest(
			@NonNull final CustomerReturnLineCandidate returnLineCandidate,
			@NonNull final InOutId customerReturnId)
	{
		final AttributeSetInstanceId attributeSetInstanceId = createAttributeSetInstance(returnLineCandidate);

		return CreateCustomerReturnLineReq.builder()
				.headerId(customerReturnId)
				.originShipmentLineId(returnLineCandidate.getOriginalShipmentInOutLineId())
				.productId(returnLineCandidate.getProductId())
				.qtyReturned(returnLineCandidate.getReturnedQty())
				.attributeSetInstanceId(attributeSetInstanceId)
				.hupiItemProductId(returnLineCandidate.getHupiItemProductId())
				.qtyTU(returnLineCandidate.getReturnedTUQty())
				.build();
	}

	private List<I_M_HU> createHUsForReturnLine(@NonNull final I_M_InOutLine returnLine)
	{
		final List<I_M_HU> createdHUs;
		final HUPIItemProductId hupiItemProductId = HUPIItemProductId.ofRepoIdOrNull(returnLine.getM_HU_PI_Item_Product_ID());
		if (hupiItemProductId == null || hupiItemProductId.isVirtualHU())
		{
			createdHUs = ImmutableList.of(createCUs(returnLine));
		}
		else
		{
			createdHUs = createLUTUs(returnLine);
		}

		huInOutBL.setAssignedHandlingUnits(returnLine, createdHUs);

		return createdHUs;
	}

	private List<I_M_HU> createLUTUs(@NonNull final I_M_InOutLine returnLine)
	{
		final IContextAware contextProvider = InterfaceWrapperHelper.getContextAware(returnLine);
		return CustomerReturnLineHUGenerator.newInstance(contextProvider)
				.setIHUTrxListeners(ImmutableList.of(CreateReturnedHUsTrxListener.instance))
				.addM_InOutLine(returnLine)
				.generate();
	}

	private I_M_HU createCUs(@NonNull final I_M_InOutLine returnLine)
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
				returnLine, // referencedModel,
				true); //forceQtyAllocation

		final IAllocationSource source = createAllocationSource(returnLine);

		final LocatorId locatorId = warehousesRepo.getLocatorIdByRepoId(returnLine.getM_Locator_ID());
		final I_M_HU returnCU = initializeCU(locatorId);
		final IAllocationDestination destination = HUListAllocationSourceDestination.of(returnCU);

		// Execute transfer
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

	private IAllocationSource createAllocationSource(@NonNull final I_M_InOutLine returnLine)
	{
		final ProductId productId = ProductId.ofRepoId(returnLine.getM_Product_ID());
		final I_C_UOM uom = uomDao.getById(returnLine.getC_UOM_ID());
		final BigDecimal qty = returnLine.getQtyEntered();

		final PlainProductStorage productStorage = new PlainProductStorage(productId, uom, qty);

		return new GenericAllocationSourceDestination(productStorage, returnLine);
	}

	@Nullable
	private AttributeSetInstanceId createAttributeSetInstance(@NonNull final CustomerReturnLineCandidate customerReturnLineCandidate)
	{
		final List<CreateAttributeInstanceReq> attributes = customerReturnLineCandidate.getCreateAttributeInstanceReqs();

		if (attributes == null || attributes.isEmpty())
		{
			return null;
		}

		final AddAttributesRequest addAttributesRequest = AddAttributesRequest.builder()
				.productId(customerReturnLineCandidate.getProductId())
				.existingAttributeSetIdOrNone(AttributeSetInstanceId.NONE)
				.attributeInstanceBasicInfos(attributes)
				.build();

		return attributeSetInstanceBL.addAttributes(addAttributesRequest);
	}

	private WarehouseId getWarehouseIdForReceivingReturnedGoods(@NonNull final ReturnedGoodsWarehouseType warehouseType)
	{
		final WarehouseId targetWarehouseId;

		switch (warehouseType)
		{
			case QUARANTINE:
				targetWarehouseId = warehousesRepo.retrieveQuarantineWarehouseId();
				break;
			case QUALITY_ISSUE:
				targetWarehouseId = huWarehouseDAO.retrieveFirstQualityReturnWarehouseId();
				break;
			default:
				throw new AdempiereException("The given ReturnedGoodsWarehouseType is not supported!")
						.appendParametersToMessage()
						.setParameter("ReturnedGoodsWarehouseType", warehouseType);
		}

		return targetWarehouseId;
	}

	@Value
	@Builder
	private static class CustomerReturnLineGroupingKey
	{
		@NonNull
		OrgId orgId;

		@NonNull
		BPartnerLocationId bPartnerLocationId;

		@NonNull
		ReturnedGoodsWarehouseType warehouseType;

		@Nullable
		OrderId orderId;

		@Nullable
		LocalDate movementDate;

		@Nullable
		ZonedDateTime dateReceived;

		@Nullable
		String externalId;

		@Nullable
		String externalResourceURL;

		public static CustomerReturnLineGroupingKey of(@NonNull final CustomerReturnLineCandidate returnLineCandidate)
		{
			return CustomerReturnLineGroupingKey.builder()
					.orgId(returnLineCandidate.getOrgId())
					.bPartnerLocationId(returnLineCandidate.getBPartnerLocationId())
					.orderId(returnLineCandidate.getOrderId())
					.movementDate(returnLineCandidate.getMovementDate())
					.dateReceived(returnLineCandidate.getDateReceived())
					.externalId(returnLineCandidate.getExternalId())
					.externalResourceURL(returnLineCandidate.getExternalResourceURL())
					.warehouseType(returnLineCandidate.getReturnedGoodsWarehouseType())
					.build();
		}
	}
}
