/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.order.createFrom;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.Adempiere;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.compiere.model.X_C_Order;
import org.compiere.model.X_M_Product;
import org.compiere.util.TimeUtil;
import org.eevolution.api.BOMComponentType;
import org.eevolution.api.BOMCreateRequest;
import org.eevolution.api.BOMType;
import org.eevolution.api.BOMUse;
import org.eevolution.api.IProductBOMDAO;
import org.eevolution.api.ProductBOMId;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ListMultimap;

import de.metas.document.DocTypeId;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.i18n.AdMessageKey;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderDAO;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderId;
import de.metas.order.compensationGroup.GroupId;
import de.metas.order.compensationGroup.OrderGroupCompensationUtils;
import de.metas.order.compensationGroup.OrderGroupInfo;
import de.metas.order.compensationGroup.OrderGroupRepository;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.service.AddProductPriceRequest;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.product.CreateProductRequest;
import de.metas.product.IProductDAO;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.product.ProductPlanningSchemaSelector;
import de.metas.quantity.Quantity;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NonNull;
import lombok.Value;

public final class CreateSalesOrderAndBOMsFromQuotationCommand
{
	private static final AdMessageKey MSG_NoQuotationGrouppingProductFoundForLines = AdMessageKey.of("NoQuotationGrouppingProductFoundForLines");
	private static final AdMessageKey MSG_MoreThanOneQuotationGrouppingProductLinesFound = AdMessageKey.of("MoreThanOneQuotationGrouppingProductLinesFound");

	//
	// Services
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IDocumentBL documentsBL = Services.get(IDocumentBL.class);
	private final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
	private final IProductDAO productsRepo = Services.get(IProductDAO.class);
	private final IProductBOMDAO bomsRepo = Services.get(IProductBOMDAO.class);
	private final IUOMDAO uomsRepo = Services.get(IUOMDAO.class);
	private final IProductPlanningDAO productPlanningsRepo = Services.get(IProductPlanningDAO.class);
	private final IPriceListDAO priceListsRepo = Services.get(IPriceListDAO.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IOrderDAO ordersRepo = Services.get(IOrderDAO.class);
	private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
	private final OrderGroupRepository orderGroupsRepo = Adempiere.getBean(OrderGroupRepository.class);

	//
	// Parameters
	private final OrderId fromQuotationId;
	private final DocTypeId salesOrderDocTypeId;
	private final LocalDate salesOrderDateOrdered;
	private final String salesOrderPOReference;
	private final boolean doCompleteSalesOrder;

	@Builder
	private CreateSalesOrderAndBOMsFromQuotationCommand(
			@NonNull final OrderId fromQuotationId,
			@Nullable final DocTypeId docTypeId,
			@NonNull final LocalDate dateOrdered,
			@Nullable final String poReference,
			final boolean completeIt)
	{
		this.fromQuotationId = fromQuotationId;

		this.salesOrderDocTypeId = docTypeId;
		this.salesOrderDateOrdered = dateOrdered;
		this.salesOrderPOReference = poReference;

		this.doCompleteSalesOrder = completeIt;
	}

	public I_C_Order execute()
	{
		trxManager.assertThreadInheritedTrxNotExists();

		final I_C_Order fromQuotation = ordersRepo.getById(fromQuotationId);

		final SalesOrderCandidate salesOrderCandidate = createSalesOrderCandidate(fromQuotation);

		createMasterData(salesOrderCandidate);

		return createSalesOrder(salesOrderCandidate);
	}

	private SalesOrderCandidate createSalesOrderCandidate(final I_C_Order fromQuotation)
	{
		final OrderId orderId = OrderId.ofRepoId(fromQuotation.getC_Order_ID());
		final List<I_C_OrderLine> allOrderLines = ordersRepo.retrieveOrderLines(orderId)
				.stream()
				.filter(line -> line.isActive() && !line.isPackagingMaterial())
				.sorted(Comparator.comparing(I_C_OrderLine::getLine))
				.collect(ImmutableList.toImmutableList());

		final ListMultimap<GroupId, I_C_OrderLine> orderLinesByGroupId = allOrderLines
				.stream()
				.filter(OrderGroupCompensationUtils::isInGroup)
				.collect(ImmutableListMultimap.toImmutableListMultimap(OrderGroupRepository::extractGroupId, Function.identity()));

		final ImmutableList<I_C_OrderLine> notGroupedOrderLines = allOrderLines
				.stream()
				.filter(OrderGroupCompensationUtils::isNotInGroup)
				.collect(ImmutableList.toImmutableList());

		final ImmutableList<SalesOrderLineCandidate> lines = orderLinesByGroupId
				.asMap()
				.values()
				.stream()
				.map(this::createLineCandidate)
				.collect(ImmutableList.toImmutableList());

		return SalesOrderCandidate.builder()
				.priceListId(PriceListId.ofRepoId(fromQuotation.getM_PriceList_ID()))
				.datePromised(TimeUtil.asZonedDateTime(fromQuotation.getDatePromised()))
				.fromQuotation(fromQuotation)
				//
				.lines(lines)
				.otherQuotationLinesToCopyDirectly(notGroupedOrderLines)
				//
				.build();
	}

	private SalesOrderLineCandidate createLineCandidate(@NonNull final Collection<I_C_OrderLine> fromQuotationLines)
	{
		if (fromQuotationLines.size() < 2)
		{
			throw new AdempiereException("At least 2 quotation lines expected but got: " + fromQuotationLines);
		}

		final I_C_OrderLine mainQuotationLine = findMainQuotationLine(fromQuotationLines);
		final ImmutableList<I_C_OrderLine> additionalQuotationLines = fromQuotationLines.stream()
				.filter(line -> line.getC_OrderLine_ID() != mainQuotationLine.getC_OrderLine_ID())
				.filter(line -> !line.isGroupCompensationLine()) // skip compensation lines when creating BOM Lines
				.collect(ImmutableList.toImmutableList());

		final GroupId groupId = OrderGroupRepository.extractSingleGroupId(fromQuotationLines);
		final OrderGroupInfo groupInfo = orderGroupsRepo.getGroupInfoById(groupId);
		final ProductId existingBOMProductId = groupInfo.getBomId()
				.map(bomsRepo::getBOMProductId)
				.orElse(null);

		final BigDecimal price;
		if (mainQuotationLine.isGroupCompensationLine())
		{
			price = mainQuotationLine.getGroupCompensationBaseAmt();
		}
		else
		{
			price = mainQuotationLine.getPriceEntered();
		}

		return SalesOrderLineCandidate.builder()
				.quotationGroupId(groupInfo.getGroupId())
				.quotationGroupName(groupInfo.getName())
				.bomProductId(existingBOMProductId)
				//
				.orgId(OrgId.ofRepoId(mainQuotationLine.getAD_Org_ID()))
				.quotationTemplateProductId(ProductId.ofRepoId(mainQuotationLine.getM_Product_ID()))
				.price(price)
				.qty(mainQuotationLine.getQtyEntered())
				.uomId(UomId.ofRepoId(mainQuotationLine.getC_UOM_ID()))
				.taxCategoryId(TaxCategoryId.ofRepoId(mainQuotationLine.getC_TaxCategory_ID()))
				//
				.additionalQuotationLines(additionalQuotationLines)
				.build();
	}

	private I_C_OrderLine findMainQuotationLine(final Collection<I_C_OrderLine> quotationLines)
	{
		final List<I_C_OrderLine> quotationGroupProductLines = quotationLines.stream()
				.filter(this::isMainQuotationLine)
				.collect(ImmutableList.toImmutableList());
		if (quotationGroupProductLines.isEmpty())
		{
			throw new AdempiereException(MSG_NoQuotationGrouppingProductFoundForLines, extractLineNosAsString(quotationLines));
		}
		else if (quotationGroupProductLines.size() == 1)
		{
			return quotationGroupProductLines.get(0);
		}
		else
		{
			throw new AdempiereException(MSG_MoreThanOneQuotationGrouppingProductLinesFound, extractLineNosAsString(quotationGroupProductLines));
		}
	}

	private boolean isMainQuotationLine(final I_C_OrderLine orderLine)
	{
		final ProductId productId = ProductId.ofRepoIdOrNull(orderLine.getM_Product_ID());
		if (productId == null)
		{
			return false;
		}

		final I_M_Product product = productsRepo.getById(productId);
		return product.isQuotationGroupping();
	}

	private static String extractLineNosAsString(final Collection<I_C_OrderLine> lines)
	{
		return lines.stream()
				.map(I_C_OrderLine::getLine)
				.sorted()
				.map(String::valueOf)
				.collect(Collectors.joining(", "));
	}

	private void createMasterData(final SalesOrderCandidate candidate)
	{
		trxManager.assertThreadInheritedTrxNotExists();

		final ImmutableList<SalesOrderLineCandidate> lines = candidate.getLines();
		trxManager.runInThreadInheritedTrx(() -> lines.forEach(line -> createMasterDataInTrx(candidate, line)));
	}

	private void createMasterDataInTrx(final SalesOrderCandidate candidate, final SalesOrderLineCandidate line)
	{
		//
		// Create Product & BOM if needed
		if (line.getBomProductId() == null)
		{
			final ProductId bomProductId = createBOM(line);
			line.setBomProductId(bomProductId);
			line.setAddProductToPriceList(true);
		}

		//
		// Add Product to price list
		if (line.isAddProductToPriceList())
		{
			final PriceListId priceListId = candidate.getPriceListId();
			final ZonedDateTime priceDate = candidate.getDatePromised();
			final PriceListVersionId priceListVersionId = priceListsRepo.retrievePriceListVersionId(priceListId, priceDate);

			priceListsRepo.addProductPrice(AddProductPriceRequest.builder()
					.priceListVersionId(priceListVersionId)
					.productId(line.getBomProductId())
					.uomId(line.getUomId())
					.priceStd(line.getPrice())
					.taxCategoryId(line.getTaxCategoryId())
					.build());

			line.setAddProductToPriceList(false);
		}
	}

	private BOMCreateRequest.BOMLine toBOMLineCreateRequest(
			@NonNull final I_C_OrderLine quotationLine,
			@NonNull final BigDecimal qtyOfBOMProduct)
	{
		final I_C_UOM uom = uomsRepo.getById(quotationLine.getC_UOM_ID());
		final Quantity qty = Quantity.of(quotationLine.getQtyEntered(), uom)
				.divide(qtyOfBOMProduct);

		return BOMCreateRequest.BOMLine.builder()
				.componentType(BOMComponentType.Component)
				.productId(ProductId.ofRepoId(quotationLine.getM_Product_ID()))
				.qty(qty)
				.build();
	}

	private ProductId createBOM(final SalesOrderLineCandidate candidate)
	{
		final ImmutableList<I_C_OrderLine> additionalQuotationLines = candidate.getAdditionalQuotationLines();

		final ProductCategoryId productCategoryId = findProductCategoryForNewBOMProduct(candidate);

		final I_M_Product bomProduct = productsRepo.createProduct(CreateProductRequest.builder()
				.orgId(candidate.getOrgId())
				// .productValue(null) // shall use the standard codification
				.productName(candidate.getQuotationGroupName())
				.productCategoryId(productCategoryId)
				.productType(X_M_Product.PRODUCTTYPE_Item)
				.uomId(candidate.getUomId())
				.purchased(false)
				.sold(false)
				.bomVerified(true)
				.planningSchemaSelector(ProductPlanningSchemaSelector.GENERATE_QUOTATION_BOM_PRODUCT)
				.build());

		//
		// Create BOM
		final ProductId bomProductId = ProductId.ofRepoId(bomProduct.getM_Product_ID());
		final UomId bomProductUomId = UomId.ofRepoId(bomProduct.getC_UOM_ID());
		final ProductBOMId bomId = bomsRepo.createBOM(BOMCreateRequest.builder()
				.orgId(candidate.getOrgId())
				.productId(bomProductId)
				.productValue(bomProduct.getValue())
				.productName(bomProduct.getName())
				.uomId(bomProductUomId)
				.bomUse(BOMUse.Manufacturing)
				.bomType(BOMType.MakeToOrder)
				.lines(additionalQuotationLines
						.stream()
						.map(quotationLine -> toBOMLineCreateRequest(quotationLine, candidate.getQty()))
						.collect(ImmutableList.toImmutableList()))
				.build());

		orderGroupsRepo.setGroupProductBOMId(candidate.getQuotationGroupId(), bomId);

		productPlanningsRepo.setProductBOMIdIfAbsent(bomProductId, bomId);

		return bomProductId;
	}

	private ProductCategoryId findProductCategoryForNewBOMProduct(@NonNull final SalesOrderLineCandidate candidate)
	{
		final ProductId templateProductId = candidate.getQuotationTemplateProductId();
		final I_M_Product templateProduct = productsRepo.getById(templateProductId);
		final ProductCategoryId productCategoryId = ProductCategoryId.ofRepoId(templateProduct.getM_Product_Category_ID());
		final I_M_Product_Category productCategory = productsRepo.getProductCategoryById(productCategoryId);

		final ProductCategoryId parentProductCategoryId = ProductCategoryId.ofRepoIdOrNull(productCategory.getM_Product_Category_Parent_ID());
		if (parentProductCategoryId == null)
		{
			throw new AdempiereException("@NotFound@ @M_Product_Category_Parent_ID@ (@ProductName@: " + templateProduct.getName() + ")");
		}

		return parentProductCategoryId;
	}

	private I_C_Order createSalesOrder(@NonNull final SalesOrderCandidate salesOrderCandidate)
	{
		trxManager.assertThreadInheritedTrxNotExists();
		return trxManager.callInThreadInheritedTrx(() -> createSalesOrderInTrx(salesOrderCandidate));
	}

	private I_C_Order createSalesOrderInTrx(@NonNull final SalesOrderCandidate salesOrderCandidate)
	{
		final I_C_Order newSalesOrder = createSalesOrderHeader(salesOrderCandidate);

		salesOrderCandidate
				.getLines()
				.forEach(lineCandidate -> createLineFromCandidate(newSalesOrder, lineCandidate));

		salesOrderCandidate
				.getOtherQuotationLinesToCopyDirectly()
				.forEach(quotationLine -> copyQuotationLineToSalesOrder(newSalesOrder, quotationLine));

		if (doCompleteSalesOrder)
		{
			documentsBL.processEx(newSalesOrder, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
		}

		return newSalesOrder;
	}

	private I_C_Order createSalesOrderHeader(@NonNull final SalesOrderCandidate salesOrderCandidate)
	{
		final I_C_Order fromQuotation = salesOrderCandidate.getFromQuotation();

		final I_C_Order newSalesOrder = InterfaceWrapperHelper.copy()
				.setFrom(fromQuotation)
				.setSkipCalculatedColumns(true)
				.copyToNew(I_C_Order.class);
		newSalesOrder.setDocStatus(DocStatus.Drafted.getCode());
		newSalesOrder.setDocAction(X_C_Order.DOCACTION_Complete);

		newSalesOrder.setDateOrdered(TimeUtil.asTimestamp(salesOrderDateOrdered));
		newSalesOrder.setDatePromised(TimeUtil.asTimestamp(salesOrderCandidate.getDatePromised()));
		newSalesOrder.setM_PriceList_ID(salesOrderCandidate.getPriceListId().getRepoId());

		if (salesOrderDocTypeId != null)
		{
			orderBL.setDocTypeTargetIdAndUpdateDescription(newSalesOrder, salesOrderDocTypeId);
			newSalesOrder.setC_DocType_ID(salesOrderDocTypeId.getRepoId());
		}
		if (salesOrderPOReference != null)
		{
			newSalesOrder.setPOReference(salesOrderPOReference);
		}

		// Link new Sales Order -> Quotation
		newSalesOrder.setRef_Proposal_ID(fromQuotation.getC_Order_ID());
		saveRecord(newSalesOrder);

		// Link Quotation -> new Sales Order
		fromQuotation.setRef_Order_ID(newSalesOrder.getC_Order_ID());
		saveRecord(fromQuotation);

		return newSalesOrder;
	}

	private void createLineFromCandidate(final I_C_Order newOrder, final SalesOrderLineCandidate candidate)
	{
		final ProductId productId = candidate.getBomProductId();
		Check.assumeNotNull(productId, "Parameter productId is not null");

		final BigDecimal price = candidate.getPrice();
		final BigDecimal qty = candidate.getQty();
		final UomId uomId = candidate.getUomId();

		final I_C_OrderLine salesOrderLine = newInstance(I_C_OrderLine.class);
		salesOrderLine.setC_Order_ID(newOrder.getC_Order_ID());
		orderLineBL.setOrder(salesOrderLine, newOrder);
		//
		salesOrderLine.setM_Product_ID(productId.getRepoId());
		salesOrderLine.setM_AttributeSetInstance_ID(AttributeSetInstanceId.NONE.getRepoId());
		//
		salesOrderLine.setC_UOM_ID(uomId.getRepoId());
		salesOrderLine.setQtyEntered(qty);
		//
		salesOrderLine.setIsManualPrice(true);
		salesOrderLine.setPriceEntered(price);
		salesOrderLine.setPriceActual(price);

		//
		saveRecord(salesOrderLine);
	}

	private void copyQuotationLineToSalesOrder(final I_C_Order newOrder, final I_C_OrderLine fromQuotationLine)
	{
		final I_C_OrderLine salesOrderLine = InterfaceWrapperHelper.copy()
				.setFrom(fromQuotationLine)
				.setSkipCalculatedColumns(true)
				.copyToNew(I_C_OrderLine.class);

		salesOrderLine.setC_Order_ID(newOrder.getC_Order_ID());
		orderLineBL.setOrder(salesOrderLine, newOrder);

		attributeSetInstanceBL.cloneASI(salesOrderLine, fromQuotationLine);

		saveRecord(salesOrderLine);
	}

	@Value
	@Builder
	private static class SalesOrderCandidate
	{
		@NonNull PriceListId priceListId;

		@NonNull ZonedDateTime datePromised;

		@NonNull I_C_Order fromQuotation;

		@NonNull ImmutableList<SalesOrderLineCandidate> lines;
		@NonNull ImmutableList<I_C_OrderLine> otherQuotationLinesToCopyDirectly;
	}

	@Data
	@Builder
	private static class SalesOrderLineCandidate
	{
		@Nullable
		private ProductId bomProductId;
		private boolean addProductToPriceList;

		//
		// Quotation info:
		@NonNull
		private final GroupId quotationGroupId;
		@NonNull
		private final String quotationGroupName;
		@NonNull
		final OrgId orgId;
		@NonNull
		final ProductId quotationTemplateProductId;
		@NonNull
		final BigDecimal price;
		@NonNull
		final BigDecimal qty;
		@NonNull
		final UomId uomId;
		@NonNull
		final TaxCategoryId taxCategoryId;

		//
		// Additional quotation lines to copy directly
		@NonNull
		@Default
		private final ImmutableList<I_C_OrderLine> additionalQuotationLines = ImmutableList.of();
	}
}
