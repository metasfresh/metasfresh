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
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.handlingunits.IHUWarehouseDAO;
import de.metas.handlingunits.inout.IHUInOutBL;
import de.metas.handlingunits.inout.returns.ReturnedGoodsWarehouseType;
import de.metas.handlingunits.model.I_C_Order;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_InOut;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.inout.InOutId;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
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
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
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
	private final IWarehouseDAO warehousesRepo = Services.get(IWarehouseDAO.class);
	private final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
	private final IHUInOutBL huInOutBL = Services.get(IHUInOutBL.class);
	private final IDocumentBL docActionBL = Services.get(IDocumentBL.class);
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
		return CustomerReturnHUsCreateCommand.builder()
				.returnLine(returnLine)
				.build()
				.execute();
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
