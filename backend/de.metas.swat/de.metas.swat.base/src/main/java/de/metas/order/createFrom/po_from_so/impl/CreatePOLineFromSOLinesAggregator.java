package de.metas.order.createFrom.po_from_so.impl;

import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.acct.vatcode.IVATCodeDAO;
import de.metas.acct.vatcode.VATCode;
import de.metas.acct.vatcode.VATCodeMatchingRequest;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.time.SystemTime;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderDAO;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.order.createFrom.po_from_so.IC_Order_CreatePOFromSOsBL;
import de.metas.order.createFrom.po_from_so.PurchaseTypeEnum;
import de.metas.order.location.adapter.OrderLineDocumentLocationAdapterFactory;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.tax.api.TaxId;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import de.metas.util.collections.MapReduceAggregator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IModelAttributeSetInstanceListener;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_AttributeSetInstance;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.create;

/*
 * #%L
 * de.metas.swat.base
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

/**
 * Creates purchase order lines for sales order lines. One instance of this aggregator is called with sales order lines that all belong to the same purchase order.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
class CreatePOLineFromSOLinesAggregator extends MapReduceAggregator<I_C_OrderLine, I_C_OrderLine>
{
	private final transient IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
	private final transient IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
	private final transient IOrderBL orderBL = Services.get(IOrderBL.class);
	private final transient IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final transient IAcctSchemaDAO acctSchemaDAO = Services.get(IAcctSchemaDAO.class);
	private final transient IVATCodeDAO vatCodeDAO = Services.get(IVATCodeDAO.class);

	@Getter(AccessLevel.PACKAGE)
	private final I_C_Order purchaseOrder;

	private final String purchaseQtySource;

	@NonNull
	private final PurchaseTypeEnum purchaseType;

	@Nullable
	private final TaxId taxId;

	private final IdentityHashMap<I_C_OrderLine, ArrayList<I_C_OrderLine>> purchaseOrderLine2saleOrderLines = new IdentityHashMap<>();

	/**
	 * @param purchaseQtySource column name of the sales order line column to get the qty from. Can be either can be either QtyOrdered or QtyReserved.
	 * @param taxId             manual tax to use when creating order lines
	 */
	/* package */
	public CreatePOLineFromSOLinesAggregator(
			final I_C_Order purchaseOrder,
			final String purchaseQtySource,
			@NonNull final PurchaseTypeEnum purchaseType,
			@Nullable final TaxId taxId)

	{
		this.purchaseOrder = purchaseOrder;

		this.purchaseQtySource = purchaseQtySource;

		this.purchaseType = purchaseType;
		this.taxId = taxId;
	}

	@Override
	protected I_C_OrderLine createGroup(final Object itemHashKey, final I_C_OrderLine salesOrderLine)
	{
		final I_C_OrderLine purchaseOrderLine;
		try
		{
			purchaseOrderLine = createPurchaseOrderLine(salesOrderLine);
			Services.get(IC_Order_CreatePOFromSOsBL.class)
					.getCompositeListener()
					.afterPurchaseOrderLineCreatedBeforeSave(purchaseOrderLine, salesOrderLine);
		}
		catch (final Throwable t)
		{
			Loggables.addLog("@Error@: " + t);
			throw AdempiereException.wrapIfNeeded(t);
		}

		purchaseOrderLine2saleOrderLines.put(purchaseOrderLine, new ArrayList<>());
		return purchaseOrderLine;

	}

	private I_C_OrderLine createPurchaseOrderLine(final I_C_OrderLine salesOrderLine)
	{
		final I_M_AttributeSetInstance poASI;
		if (salesOrderLine.getM_AttributeSetInstance_ID() > 0)
		{
			final I_M_AttributeSetInstance soASI = salesOrderLine.getM_AttributeSetInstance();
			poASI = attributeDAO.copy(soASI);
		}
		else
		{
			poASI = null;
		}

		final I_C_OrderLine purchaseOrderLine = orderLineBL.createOrderLine(purchaseOrder);

		if (taxId != null)
		{
			final int taxIdRepoId = taxId.getRepoId();
			purchaseOrderLine.setC_Tax_ID(taxIdRepoId);

			//TODO delete after C_VAT_Code_ID is removed from C_OrderLine
			final AcctSchema acctSchema = acctSchemaDAO.getByClientAndOrg(ClientId.ofRepoId(salesOrderLine.getAD_Client_ID()), OrgId.ofRepoId(salesOrderLine.getAD_Org_ID()));
			final Optional<VATCode> vatCode = vatCodeDAO.findVATCode(VATCodeMatchingRequest.builder()
					.setC_AcctSchema_ID(acctSchema.getId().getRepoId())
					.setIsSOTrx(false)
					.setC_Tax_ID(taxIdRepoId)
					.setDate(SystemTime.asDate())
					.build());
			vatCode.ifPresent((id) -> purchaseOrderLine.setC_VAT_Code_ID(id.getVatCodeId().getRepoId()));
		}

		purchaseOrderLine.setC_Charge_ID(salesOrderLine.getC_Charge_ID());

		final ProductId productId = ProductId.ofRepoIdOrNull(salesOrderLine.getM_Product_ID());
		if (productId != null)
		{
			purchaseOrderLine.setM_Product_ID(productId.getRepoId());
			// we use the product's uom, i.e. the internal stocking uom, because
			// 1. we can assume to have an UOM conversion from any sales order line's UOM.
			// 2. that way we can use the "internal-UOMs" Qty also for QtyEntered in addItemToGroup()
			final UomId uomId = Services.get(IProductBL.class).getStockUOMId(productId);
			purchaseOrderLine.setC_UOM_ID(uomId.getRepoId());
		}

		purchaseOrderLine.setQtyEntered(BigDecimal.ZERO);

		purchaseOrderLine.setDescription(salesOrderLine.getDescription());

		final Timestamp datePromised = CoalesceUtil.coalesceSuppliers(
				salesOrderLine::getDatePromised,
				() -> salesOrderLine.getC_Order().getDatePromised());
		purchaseOrderLine.setDatePromised(datePromised);

		if (PurchaseTypeEnum.MEDIATED.equals(purchaseType))
		{
			copyBPartnerAndLocationDetailsFromSalesToPurchaseOrderLine(salesOrderLine, purchaseOrderLine);
		}
		else
		{
			OrderLineDocumentLocationAdapterFactory.locationAdapter(purchaseOrderLine).setFromOrderHeader(purchaseOrder);
		}

		copyUserIdFromSalesToPurchaseOrderLine(salesOrderLine, purchaseOrderLine);

		purchaseOrderLine.setM_AttributeSetInstance(poASI);
		IModelAttributeSetInstanceListener.DYNATTR_DisableASIUpdateOnModelChange.setValue(purchaseOrderLine, true); // (08091)

		return purchaseOrderLine;
	}

	private void copyBPartnerAndLocationDetailsFromSalesToPurchaseOrderLine(
			@NonNull final I_C_OrderLine salesOrderLine,
			@NonNull final I_C_OrderLine purchaseOrderLine)
	{
		purchaseOrderLine.setC_BPartner_ID(salesOrderLine.getC_BPartner_ID());
		purchaseOrderLine.setC_BPartner_Location_ID(salesOrderLine.getC_BPartner_Location_ID());
		purchaseOrderLine.setC_BPartner_Location_Value_ID(salesOrderLine.getC_BPartner_Location_Value_ID());
		purchaseOrderLine.setBPartnerAddress(salesOrderLine.getBPartnerAddress());
	}

	private void copyUserIdFromSalesToPurchaseOrderLine(
			@NonNull final I_C_OrderLine salesOrderLine,
			@NonNull final I_C_OrderLine purchaseOrderLine)
	{
		final int userID = create(salesOrderLine, de.metas.interfaces.I_C_OrderLine.class).getAD_User_ID();
		create(purchaseOrderLine, de.metas.interfaces.I_C_OrderLine.class).setAD_User_ID(userID);
	}

	@Override
	protected void closeGroup(final I_C_OrderLine purchaseOrderLine)
	{
		final List<I_C_OrderLine> salesOrderLines = purchaseOrderLine2saleOrderLines.get(purchaseOrderLine);

		final OrderId singleSalesOrderId = extractSingleOrderIdOrNull(salesOrderLines);
		purchaseOrderLine.setC_OrderSO_ID(OrderId.toRepoId(singleSalesOrderId));
		InterfaceWrapperHelper.save(purchaseOrderLine);

		final HashSet<OrderId> salesOrdersToBeClosed = new HashSet<>();
		for (final I_C_OrderLine salesOrderLine : salesOrderLines)
		{
			orderDAO.allocatePOLineToSOLine(
					OrderAndLineId.ofRepoIds(purchaseOrderLine.getC_Order_ID(), purchaseOrderLine.getC_OrderLine_ID()),
					OrderAndLineId.ofRepoIds(salesOrderLine.getC_Order_ID(), salesOrderLine.getC_OrderLine_ID()));

			salesOrdersToBeClosed.add(OrderId.ofRepoId(salesOrderLine.getC_Order_ID()));
		}

		if (PurchaseTypeEnum.MEDIATED.equals(purchaseType))
		{
			salesOrdersToBeClosed.forEach(orderBL::closeOrder);
		}
	}

	@Override
	protected void addItemToGroup(final I_C_OrderLine purchaseOrderLine, final I_C_OrderLine salesOrderLine)
	{
		final BigDecimal oldQtyEntered = purchaseOrderLine.getQtyEntered();

		// the purchase order line's UOM is the internal stocking UOM, so we don't need to convert from qtyOrdered/qtyReserved to qtyEntered.
		final BigDecimal purchaseQty;
		if (I_C_OrderLine.COLUMNNAME_QtyOrdered.equals(purchaseQtySource))
		{
			purchaseQty = PurchaseTypeEnum.MEDIATED.equals(purchaseType)
					? salesOrderLine.getQtyOrdered().subtract(salesOrderLine.getQtyDelivered())
					: salesOrderLine.getQtyOrdered();
		}
		else if (I_C_OrderLine.COLUMNNAME_QtyReserved.equals(purchaseQtySource))
		{
			purchaseQty = salesOrderLine.getQtyReserved();
		}
		else
		{
			Check.errorIf(true, "Unsupported purchaseQtySource={}", purchaseQtySource);
			purchaseQty = null; // won't be reached
		}

		final BigDecimal newQtyEntered = oldQtyEntered.add(purchaseQty);

		// setting QtyEntered, because qtyOrdered will be set from qtyEntered by a model interceptor
		purchaseOrderLine.setQtyEntered(newQtyEntered);

		purchaseOrderLine2saleOrderLines.get(purchaseOrderLine).add(salesOrderLine); // no NPE, because the list for this key was added in createGroup()
	}

	@Nullable
	private static OrderId extractSingleOrderIdOrNull(final List<I_C_OrderLine> orderLines)
	{
		return CollectionUtils.extractSingleElementOrDefault(
				orderLines,
				orderLine -> OrderId.ofRepoId(orderLine.getC_Order_ID()),
				null);
	}
}
