package de.metas.ordercandidate.api;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.BPartnerInfo;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.time.SystemTime;
import de.metas.document.DocTypeId;
import de.metas.impex.InputDataSourceId;
import de.metas.impex.api.IInputDataSourceDAO;
import de.metas.location.LocationId;
import de.metas.order.OrderLineGroup;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentRule;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.shipping.ShipperId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Repository
public class OLCandRepository
{
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	public List<OLCand> create(@NonNull final List<OLCandCreateRequest> requests)
	{
		Check.assumeNotEmpty(requests, "requests is not empty");

		final OLCandFactory olCandFactory = new OLCandFactory();

		final ITrxManager trxManager = Services.get(ITrxManager.class);
		return trxManager.callInThreadInheritedTrx(() -> requests.stream()
				.map(this::createAndSaveOLCandRecord)
				.map(olCandFactory::toOLCand)
				.collect(ImmutableList.toImmutableList()));
	}

	public OLCand create(@NonNull final OLCandCreateRequest request)
	{
		Check.assumeNotNull(request, "request is not null");

		final OLCandFactory olCandFactory = new OLCandFactory();

		final ITrxManager trxManager = Services.get(ITrxManager.class);
		return trxManager.callInThreadInheritedTrx(() -> {
			final I_C_OLCand olCandRecord = createAndSaveOLCandRecord(request);
			return olCandFactory.toOLCand(olCandRecord);
		});
	}

	private I_C_OLCand createAndSaveOLCandRecord(@NonNull final OLCandCreateRequest request)
	{
		final I_C_OLCand olCandPO = newInstance(I_C_OLCand.class);

		if (request.getOrgId() != null)
		{
			olCandPO.setAD_Org_ID(request.getOrgId().getRepoId());
		}
		final OrgId orgId = OrgId.ofRepoIdOrAny(olCandPO.getAD_Org_ID());
		final ZoneId timeZone = orgDAO.getTimeZone(orgId);

		{
			final BPartnerInfo bpartner = request.getBpartner();
			final BPartnerLocationId bpartnerLocationId = bpartner.getBpartnerLocationId();
			if (bpartnerLocationId == null)
			{
				throw new AdempiereException("Given OLCandCreateRequest has no BpartnerLocationId")
						.appendParametersToMessage()
						.setParameter("OLCandCreateRequest", request);
			}

			olCandPO.setC_BPartner_ID(BPartnerId.toRepoId(bpartner.getBpartnerId()));
			olCandPO.setC_BPartner_Location_ID(BPartnerLocationId.toRepoId(bpartnerLocationId));
			olCandPO.setC_BPartner_Location_Value_ID(LocationId.toRepoId(bpartner.getLocationId()));
			olCandPO.setAD_User_ID(BPartnerContactId.toRepoId(bpartner.getContactId()));
		}

		if (request.getBillBPartner() != null)
		{
			final BPartnerInfo bpartner = request.getBillBPartner();
			olCandPO.setBill_BPartner_ID(BPartnerId.toRepoId(bpartner.getBpartnerId()));
			olCandPO.setBill_Location_ID(BPartnerLocationId.toRepoId(bpartner.getBpartnerLocationId()));
			olCandPO.setBill_Location_Value_ID(LocationId.toRepoId(bpartner.getLocationId()));
			olCandPO.setBill_User_ID(BPartnerContactId.toRepoId(bpartner.getContactId()));
		}

		if (request.getDropShipBPartner() != null)
		{
			final BPartnerInfo bpartner = request.getDropShipBPartner();
			olCandPO.setDropShip_BPartner_ID(BPartnerId.toRepoId(bpartner.getBpartnerId()));
			olCandPO.setDropShip_Location_ID(BPartnerLocationId.toRepoId(bpartner.getBpartnerLocationId()));
			olCandPO.setDropShip_Location_Value_ID(LocationId.toRepoId(bpartner.getLocationId()));
			olCandPO.setDropShip_User_ID(BPartnerContactId.toRepoId(bpartner.getContactId()));
		}

		if (request.getHandOverBPartner() != null)
		{
			final BPartnerInfo bpartner = request.getHandOverBPartner();
			olCandPO.setHandOver_Partner_ID(BPartnerId.toRepoId(bpartner.getBpartnerId()));
			olCandPO.setHandOver_Location_ID(BPartnerLocationId.toRepoId(bpartner.getBpartnerLocationId()));
			olCandPO.setHandOver_Location_Value_ID(LocationId.toRepoId(bpartner.getLocationId()));
			olCandPO.setHandOver_User_ID(BPartnerContactId.toRepoId(bpartner.getContactId()));
		}

		if (!Check.isEmpty(request.getPoReference(), true))
		{
			olCandPO.setPOReference(request.getPoReference());
		}

		olCandPO.setDateCandidate(CoalesceUtil.coalesce(TimeUtil.asTimestamp(request.getDateCandidate()), SystemTime.asDayTimestamp()));
		olCandPO.setDateOrdered(TimeUtil.asTimestamp(request.getDateOrdered()));
		olCandPO.setDatePromised(TimeUtil.asTimestamp(request.getDateRequired()
															  .atTime(LocalTime.MAX)
															  .atZone(timeZone)));

		olCandPO.setPresetDateInvoiced(TimeUtil.asTimestamp(request.getPresetDateInvoiced()));
		olCandPO.setC_DocTypeInvoice_ID(DocTypeId.toRepoId(request.getDocTypeInvoiceId()));

		if (request.getDocTypeOrderId() != null)
		{
			olCandPO.setC_DocTypeOrder_ID(DocTypeId.toRepoId(request.getDocTypeOrderId()));
		}

		olCandPO.setPresetDateShipped(TimeUtil.asTimestamp(request.getPresetDateShipped()));

		olCandPO.setC_Flatrate_Conditions_ID(request.getFlatrateConditionsId());

		olCandPO.setM_Product_ID(request.getProductId().getRepoId());
		olCandPO.setProductDescription(request.getProductDescription());
		olCandPO.setQtyEntered(request.getQty());
		olCandPO.setC_UOM_ID(request.getUomId().getRepoId());
		olCandPO.setM_HU_PI_Item_Product_ID(request.getHuPIItemProductId());

		if (request.getPricingSystemId() != null)
		{
			olCandPO.setM_PricingSystem_ID(request.getPricingSystemId().getRepoId());
		}

		if (request.getPrice() != null)
		{
			olCandPO.setIsManualPrice(true);
			olCandPO.setPriceEntered(request.getPrice());
			olCandPO.setC_Currency_ID(request.getCurrencyId().getRepoId());
		}

		if (request.getDiscount() != null)
		{
			olCandPO.setIsManualDiscount(true);
			olCandPO.setDiscount(request.getDiscount().toBigDecimal());
		}

		if (request.getWarehouseId() != null)
		{
			olCandPO.setM_Warehouse_ID(request.getWarehouseId().getRepoId());
		}

		if (request.getWarehouseDestId() != null)
		{
			olCandPO.setM_Warehouse_Dest_ID(request.getWarehouseDestId().getRepoId());
		}

		olCandPO.setAD_User_EnteredBy_ID(Env.getAD_User_ID());

		olCandPO.setAD_InputDataSource_ID(request.getDataSourceId().getRepoId());

		olCandPO.setAD_DataDestination_ID(request.getDataDestId().getRepoId());

		olCandPO.setExternalLineId(request.getExternalLineId());
		olCandPO.setExternalHeaderId(request.getExternalHeaderId());

		final ShipperId shipperId = request.getShipperId();
		if (shipperId != null)
		{
			olCandPO.setM_Shipper_ID(shipperId.getRepoId());
		}

		final BPartnerId salesRepId = request.getSalesRepId();
		if (salesRepId != null && !salesRepId.equals(BPartnerId.ofRepoId(olCandPO.getC_BPartner_ID())))
		{
			olCandPO.setC_BPartner_SalesRep_ID(salesRepId.getRepoId());
		}

		final PaymentRule paymentRule = request.getPaymentRule();
		if (paymentRule != null)
		{
			olCandPO.setPaymentRule(paymentRule.getCode());
		}

		final PaymentTermId paymentTermId = request.getPaymentTermId();
		if (paymentTermId != null)
		{
			olCandPO.setC_PaymentTerm_ID(paymentTermId.getRepoId());
		}
		final OrderLineGroup orderLineGroup = request.getOrderLineGroup();
		if (orderLineGroup != null)
		{
			olCandPO.setCompensationGroupKey(orderLineGroup.getGroupKey());
			olCandPO.setIsGroupCompensationLine(orderLineGroup.isGroupMainItem());

			Optional.ofNullable(orderLineGroup.getDiscount())
					.map(Percent::toBigDecimal)
					.ifPresent(olCandPO::setGroupCompensationDiscountPercentage);
		}

		olCandPO.setDescription(request.getDescription());
		olCandPO.setDeliveryRule(request.getDeliveryRule());
		olCandPO.setDeliveryViaRule(request.getDeliveryViaRule());
		olCandPO.setImportWarningMessage(request.getImportWarningMessage());
		if (request.getPrice() == null)
		{
			olCandPO.setIsManualPrice(Boolean.TRUE.equals(request.getIsManualPrice()));
		}

		if (request.getLine() != null)
		{
			olCandPO.setLine(request.getLine());
		}

		if (request.getAsyncBatchId() != null)
		{
			olCandPO.setC_Async_Batch_ID(request.getAsyncBatchId().getRepoId());
		}

		final org.adempiere.process.rpl.model.I_C_OLCand olCandWithIssuesInterface = InterfaceWrapperHelper.create(olCandPO, org.adempiere.process.rpl.model.I_C_OLCand.class);
		if (request.getIsImportedWithIssues() != null)
		{
			olCandWithIssuesInterface.setIsImportedWithIssues(request.getIsImportedWithIssues());
		}

		if (request.getQtyShipped() != null)
		{
			olCandWithIssuesInterface.setQtyShipped(request.getQtyShipped());
		}

		if (request.getQtyItemCapacity() != null)
		{
			olCandWithIssuesInterface.setQtyItemCapacity(request.getQtyItemCapacity());
		}

		saveRecord(olCandWithIssuesInterface);

		return olCandWithIssuesInterface;
	}

	public List<OLCand> getByQuery(@NonNull final OLCandQuery olCandQuery)
	{
		final OLCandFactory olCandFactory = new OLCandFactory();

		return toSqlQueryBuilder(olCandQuery)
				.create()
				.stream()
				.map(olCandFactory::toOLCand)
				.collect(ImmutableList.toImmutableList());
	}

	private IQueryBuilder<I_C_OLCand> toSqlQueryBuilder(@NonNull final OLCandQuery olCandQuery)
	{
		final IQueryBuilder<I_C_OLCand> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_OLCand.class)
				.addOnlyActiveRecordsFilter();

		if (olCandQuery.getExternalHeaderId() != null)
		{
			queryBuilder.addEqualsFilter(I_C_OLCand.COLUMN_ExternalHeaderId, olCandQuery.getExternalHeaderId());
		}
		if (olCandQuery.getInputDataSourceName() != null)
		{
			final InputDataSourceId inputDataSourceId = Services.get(IInputDataSourceDAO.class).retrieveInputDataSourceIdByInternalName(olCandQuery.getInputDataSourceName());
			queryBuilder.addEqualsFilter(I_C_OLCand.COLUMN_AD_InputDataSource_ID, inputDataSourceId);
		}
		if (olCandQuery.getExternalLineId() != null)
		{
			queryBuilder.addEqualsFilter(I_C_OLCand.COLUMNNAME_ExternalLineId, olCandQuery.getExternalLineId());
		}
		if (olCandQuery.getOrgId() != null)
		{
			queryBuilder.addEqualsFilter(I_C_OLCand.COLUMNNAME_AD_Org_ID, olCandQuery.getOrgId());
		}

		return queryBuilder;
	}
}
