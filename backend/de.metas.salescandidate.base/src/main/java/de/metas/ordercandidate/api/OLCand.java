package de.metas.ordercandidate.api;

import com.google.common.base.MoreObjects;
import de.metas.async.AsyncBatchId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.BPartnerInfo;
import de.metas.document.DocTypeId;
import de.metas.freighcost.FreightCostRule;
import de.metas.order.DeliveryRule;
import de.metas.order.DeliveryViaRule;
import de.metas.order.InvoiceRule;
import de.metas.order.OrderLineGroup;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.payment.PaymentRule;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.pricing.InvoicableQtyBasedOn;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.attributebased.IProductPriceAware;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.shipping.ShipperId;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Optional;

/*
 * #%L
 * de.metas.swat.base
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

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public final class OLCand implements IProductPriceAware
{
	private final IOLCandEffectiveValuesBL olCandEffectiveValuesBL;

	private final I_C_OLCand olCandRecord;

	@Getter
	@Setter
	private LocalDate dateDoc;

	private final BPartnerInfo bpartnerInfo;

	@Getter
	private final BPartnerInfo billBPartnerInfo;

	@Getter
	private final Optional<BPartnerInfo> dropShipBPartnerInfo;

	@Getter
	private final Optional<BPartnerInfo> handOverBPartnerInfo;

	@Getter
	private final PricingSystemId pricingSystemId;

	@Getter
	private final DeliveryRule deliveryRule;

	@Getter
	private final DeliveryViaRule deliveryViaRule;

	@Getter
	private final ShipperId shipperId;

	@Getter
	private final String externalLineId;

	@Getter
	private final String externalHeaderId;

	@Getter
	private final FreightCostRule freightCostRule;

	@Getter
	private final PaymentRule paymentRule;

	@Getter
	private final PaymentTermId paymentTermId;

	@Getter
	private final InvoiceRule invoiceRule;

	@Getter
	private final BPartnerId salesRepId;

	@Getter
	private final Quantity qty;

	@Getter
	@Nullable
	private final Quantity qtyItemCapacityEff;

	@Getter
	private final DocTypeId orderDocTypeId;

	@Getter
	private final OrderLineGroup orderLineGroup;

	@Getter
	private final AsyncBatchId asyncBatchId;

	@Getter
	private final BigDecimal qtyShipped;

	@Getter
	private final AssignSalesRepRule assignSalesRepRule;

	@Getter
	private final BPartnerId salesRepInternalId;

	@Getter
	private final String bpartnerName;

	@Getter
	private final String phone;

	@Getter
	private final String email;

	@Builder
	private OLCand(
			@NonNull final IOLCandEffectiveValuesBL olCandEffectiveValuesBL,
			@NonNull final I_C_OLCand olCandRecord,
			//
			@Nullable final DeliveryRule deliveryRule,
			@Nullable final DeliveryViaRule deliveryViaRule,
			@Nullable final FreightCostRule freightCostRule,
			@Nullable final InvoiceRule invoiceRule,
			@Nullable final PaymentRule paymentRule,
			@Nullable final PaymentTermId paymentTermId,
			@Nullable final PricingSystemId pricingSystemId,
			@Nullable final ShipperId shipperId,
			@Nullable final DocTypeId orderDocTypeId,
			@Nullable final BPartnerId salesRepId,
			@Nullable final OrderLineGroup orderLineGroup,
			@Nullable final AsyncBatchId asyncBatchId,
			@Nullable final BigDecimal qtyShipped,
			@Nullable final Quantity qtyItemCapacityEff,
			@NonNull final AssignSalesRepRule assignSalesRepRule,
			@Nullable final BPartnerId salesRepInternalId,
			@Nullable final String bpartnerName,
			@Nullable final String phone,
			@Nullable final String email)
	{
		this.olCandEffectiveValuesBL = olCandEffectiveValuesBL;

		this.olCandRecord = olCandRecord;

		this.dateDoc = TimeUtil.asLocalDate(olCandRecord.getDateOrdered());

		this.bpartnerInfo = olCandEffectiveValuesBL.getBuyerPartnerInfo(olCandRecord);
		this.billBPartnerInfo = olCandEffectiveValuesBL.getBillToPartnerInfo(olCandRecord);
		this.dropShipBPartnerInfo = olCandEffectiveValuesBL.getDropShipPartnerInfo(olCandRecord);
		this.handOverBPartnerInfo = olCandEffectiveValuesBL.getHandOverPartnerInfo(olCandRecord);

		this.externalLineId = olCandRecord.getExternalLineId();
		this.externalHeaderId = olCandRecord.getExternalHeaderId();

		this.deliveryRule = deliveryRule;
		this.deliveryViaRule = deliveryViaRule;
		this.freightCostRule = freightCostRule;
		this.invoiceRule = invoiceRule;
		this.paymentRule = paymentRule;
		this.paymentTermId = paymentTermId;
		this.pricingSystemId = pricingSystemId;

		this.qty = Quantitys.create(
				olCandRecord.getQtyEntered(),
				this.olCandEffectiveValuesBL.getEffectiveUomId(olCandRecord));

		this.qtyItemCapacityEff = qtyItemCapacityEff;

		this.shipperId = shipperId;

		this.salesRepId = salesRepId;

		this.orderDocTypeId = orderDocTypeId;
		this.orderLineGroup = orderLineGroup;

		this.asyncBatchId = asyncBatchId;
		this.qtyShipped = qtyShipped;

		this.assignSalesRepRule = assignSalesRepRule;
		this.salesRepInternalId = salesRepInternalId;

		this.bpartnerName = bpartnerName;
		this.email = email;
		this.phone = phone;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this).addValue(olCandRecord).toString();
	}

	public I_C_OLCand unbox()
	{
		return olCandRecord;
	}

	public int getId()
	{
		return olCandRecord.getC_OLCand_ID();
	}

	public TableRecordReference toTableRecordReference()
	{
		return TableRecordReference.of(I_C_OLCand.Table_Name, getId());
	}

	public int getAD_Client_ID()
	{
		return olCandRecord.getAD_Client_ID();
	}

	public int getAD_Org_ID()
	{
		return olCandRecord.getAD_Org_ID();
	}

	public int getC_Charge_ID()
	{
		return olCandRecord.getC_Charge_ID();
	}

	public int getM_Product_ID()
	{
		return ProductId.toRepoId(olCandEffectiveValuesBL.getM_Product_Effective_ID(olCandRecord));
	}

	public int getM_AttributeSet_ID()
	{
		return olCandRecord.getM_AttributeSet_ID();
	}

	public int getM_AttributeSetInstance_ID()
	{
		return olCandRecord.getM_AttributeSetInstance_ID();
	}

	public WarehouseId getWarehouseId()
	{
		return WarehouseId.ofRepoIdOrNull(olCandRecord.getM_Warehouse_ID());
	}

	public WarehouseId getWarehouseDestId()
	{
		return WarehouseId.ofRepoIdOrNull(olCandRecord.getM_Warehouse_Dest_ID());
	}

	public boolean isManualPrice()
	{
		return olCandRecord.isManualPrice();
	}

	public BigDecimal getPriceActual()
	{
		return olCandRecord.getPriceActual();
	}

	public boolean isManualDiscount()
	{
		return olCandRecord.isManualDiscount();
	}

	public BigDecimal getDiscount()
	{
		return olCandRecord.getDiscount();
	}

	public int getC_Currency_ID()
	{
		return olCandRecord.getC_Currency_ID();
	}

	public String getProductDescription()
	{
		return olCandRecord.getProductDescription();
	}

	public int getLine()
	{
		return olCandRecord.getLine();
	}

	public boolean isProcessed()
	{
		return olCandRecord.isProcessed();
	}

	public void setProcessed()
	{
		olCandRecord.setProcessed(true);
	}

	public boolean isError()
	{
		return olCandRecord.isError();
	}

	public void setGroupingError(final String errorMsg)
	{
		olCandRecord.setProcessed(false);
		olCandRecord.setIsGroupingError(true);
		olCandRecord.setGroupingErrorMessage(errorMsg);
	}

	public void setError(final String errorMsg, final int adNoteId)
	{
		olCandRecord.setIsError(true);
		olCandRecord.setErrorMsg(errorMsg);
		olCandRecord.setAD_Note_ID(adNoteId);
	}

	public String getPOReference()
	{
		return olCandRecord.getPOReference();
	}

	public ZonedDateTime getDatePromised()
	{
		return olCandEffectiveValuesBL.getDatePromised_Effective(olCandRecord);
	}

	public int getAD_InputDataSource_ID()
	{
		return olCandRecord.getAD_InputDataSource_ID();
	}

	public int getAD_DataDestination_ID()
	{
		return olCandRecord.getAD_DataDestination_ID();
	}

	public boolean isImportedWithIssues()
	{
		final org.adempiere.process.rpl.model.I_C_OLCand rplCandidate = InterfaceWrapperHelper.create(olCandRecord, org.adempiere.process.rpl.model.I_C_OLCand.class);
		return rplCandidate.isImportedWithIssues();
	}

	// FIXME hardcoded (08691)
	public Object getValueByColumn(@NonNull final OLCandAggregationColumn column)
	{
		final String olCandColumnName = column.getColumnName();

		if (olCandColumnName.equals(I_C_OLCand.COLUMNNAME_Bill_BPartner_ID))
		{
			return getBillBPartnerInfo().getBpartnerId();
		}
		else if (olCandColumnName.equals(I_C_OLCand.COLUMNNAME_Bill_Location_ID))
		{
			return getBillBPartnerInfo().getBpartnerLocationId();
		}
		else if (olCandColumnName.equals(I_C_OLCand.COLUMNNAME_Bill_Location_Value_ID))
		{
			return getBillBPartnerInfo().getLocationId();
		}
		else if (olCandColumnName.equals(I_C_OLCand.COLUMNNAME_Bill_User_ID))
		{
			return getBillBPartnerInfo().getContactId();
		}
		else if (olCandColumnName.equals(I_C_OLCand.COLUMNNAME_DropShip_BPartner_ID))
		{
			return dropShipBPartnerInfo.orElse(bpartnerInfo).getBpartnerId();
		}
		else if (olCandColumnName.equals(I_C_OLCand.COLUMNNAME_DropShip_Location_ID))
		{
			return dropShipBPartnerInfo.orElse(bpartnerInfo).getBpartnerLocationId();
		}
		else if (olCandColumnName.equals(I_C_OLCand.COLUMNNAME_DropShip_Location_Value_ID))
		{
			return dropShipBPartnerInfo.orElse(bpartnerInfo).getLocationId();
		}
		else if (olCandColumnName.equals(I_C_OLCand.COLUMNNAME_M_PricingSystem_ID))
		{
			return getPricingSystemId();
		}
		else if (olCandColumnName.equals(I_C_OLCand.COLUMNNAME_DateOrdered))
		{
			return getDateDoc();
		}
		else if (olCandColumnName.equals(I_C_OLCand.COLUMNNAME_DatePromised_Effective))
		{
			return getDatePromised();
		}
		else if (olCandColumnName.equals(I_C_OLCand.COLUMNNAME_BPartnerName))
		{
			return getBpartnerName();
		}
		else if (olCandColumnName.equals(I_C_OLCand.COLUMNNAME_EMail))
		{
			return getEmail();
		}
		else if (olCandColumnName.equals(I_C_OLCand.COLUMNNAME_Phone))
		{
			return getPhone();
		}
		else
		{
			return InterfaceWrapperHelper.getValueByColumnId(olCandRecord, column.getAdColumnId());
		}
	}

	@Override
	public int getM_ProductPrice_ID()
	{
		return olCandRecord.getM_ProductPrice_ID();
	}

	@Override
	public boolean isExplicitProductPriceAttribute()
	{
		return olCandRecord.isExplicitProductPriceAttribute();
	}

	public int getFlatrateConditionsId()
	{
		return olCandRecord.getC_Flatrate_Conditions_ID();
	}

	public int getHUPIProductItemId()
	{
		if (olCandRecord.getM_HU_PI_Item_Product_Override_ID() > 0)
		{
			return olCandRecord.getM_HU_PI_Item_Product_Override_ID();
		}
		return olCandRecord.getM_HU_PI_Item_Product_ID();
	}

	public InvoicableQtyBasedOn getInvoicableQtyBasedOn()
	{
		return InvoicableQtyBasedOn.ofNullableCodeOrNominal(olCandRecord.getInvoicableQtyBasedOn());
	}

	public LocalDate getPresetDateInvoiced()
	{
		return TimeUtil.asLocalDate(olCandRecord.getPresetDateInvoiced());
	}

	public LocalDate getPresetDateShipped()
	{
		return TimeUtil.asLocalDate(olCandRecord.getPresetDateShipped());
	}

	public BPartnerInfo getBPartnerInfo()
	{
		return bpartnerInfo;
	}

	public boolean isAssignToBatch(@NonNull final AsyncBatchId asyncBatchIdCandidate)
	{
		if (this.asyncBatchId == null)
		{
			return false;
		}

		return asyncBatchId.getRepoId() == asyncBatchIdCandidate.getRepoId();
	}
}
