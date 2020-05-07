package de.metas.ui.web.quickinput.orderline;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_AttributeSetInstance;
import org.eevolution.api.BOMUse;
import org.eevolution.api.IProductBOMBL;
import org.eevolution.api.IProductBOMDAO;
import org.eevolution.api.ProductBOMLineId;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMLine;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.adempiere.callout.OrderFastInput;
import de.metas.adempiere.gui.search.HUPackingAwareCopy.ASICopyMode;
import de.metas.adempiere.gui.search.IHUPackingAwareBL;
import de.metas.adempiere.gui.search.impl.OrderLineHUPackingAware;
import de.metas.adempiere.gui.search.impl.PlainHUPackingAware;
import de.metas.adempiere.model.I_C_Order;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.ShipmentAllocationBestBeforePolicy;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.lang.SOTrx;
import de.metas.logging.LogManager;
import de.metas.order.IOrderLineInputValidator;
import de.metas.order.OrderId;
import de.metas.order.OrderLineInputValidatorResults;
import de.metas.order.compensationGroup.GroupCreateRequest;
import de.metas.order.compensationGroup.GroupId;
import de.metas.order.compensationGroup.OrderGroupRepository;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.ui.web.quickinput.IQuickInputProcessor;
import de.metas.ui.web.quickinput.QuickInput;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.descriptor.sql.ProductLookupDescriptor;
import de.metas.ui.web.window.descriptor.sql.ProductLookupDescriptor.ProductAndAttributes;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class OrderLineQuickInputProcessor implements IQuickInputProcessor
{
	// services
	private static final transient Logger logger = LogManager.getLogger(OrderLineQuickInputProcessor.class);
	private final IHUPackingAwareBL huPackingAwareBL = Services.get(IHUPackingAwareBL.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IAttributeSetInstanceBL asiBL = Services.get(IAttributeSetInstanceBL.class);
	private final IProductBOMDAO bomsRepo = Services.get(IProductBOMDAO.class);
	private final IProductBOMBL bomsService = Services.get(IProductBOMBL.class);

	private final OrderGroupRepository orderGroupsRepo = SpringContextHolder.instance.getBean(OrderGroupRepository.class);
	private final Collection<IOrderLineInputValidator> validators = SpringContextHolder.instance.getBeansOfType(IOrderLineInputValidator.class);

	@Override
	public Set<DocumentId> process(final QuickInput quickInput)
	{
		final OrderLineCandidate initialCandidate = toOrderLineCandidate(quickInput);
		validateInput(initialCandidate);

		final List<OrderLineCandidate> candidates = explodePhantomBOM(initialCandidate);

		final I_C_Order order = quickInput.getRootDocumentAs(I_C_Order.class);
		final Properties ctx = InterfaceWrapperHelper.getCtx(order);

		final ImmutableSet.Builder<DocumentId> documentIds = ImmutableSet.builder();
		for (final OrderLineCandidate candidate : candidates)
		{
			final I_C_OrderLine newOrderLine = OrderFastInput.addOrderLine(ctx, order, orderLine -> updateOrderLine(orderLine, candidate));
			final int newOrderLineId = newOrderLine.getC_OrderLine_ID();
			final DocumentId documentId = DocumentId.of(newOrderLineId);
			documentIds.add(documentId);
		}

		return documentIds.build();
	}

	private void validateInput(final OrderLineCandidate candidate)
	{
		final BPartnerId bpartnerId = candidate.getBpartnerId();
		final ProductId productId = candidate.getProductId();
		final SOTrx soTrx = candidate.getSoTrx();

		final ArrayList<ITranslatableString> validationErrorMessages = new ArrayList<>();
		for (final IOrderLineInputValidator validator : validators)
		{
			final OrderLineInputValidatorResults validationResults = validator.validate(bpartnerId, productId, soTrx);
			if (validationResults != null
					&& !validationResults.isValid()
					&& validationResults.getErrorMessage() != null)
			{
				validationErrorMessages.add(validationResults.getErrorMessage());
			}
		}

		if (!validationErrorMessages.isEmpty())
		{
			throw new AdempiereException(TranslatableStrings.joinList("\n", validationErrorMessages));
		}
	}

	private OrderLineCandidate toOrderLineCandidate(final QuickInput quickInput)
	{
		final IOrderLineQuickInput orderLineQuickInput = quickInput.getQuickInputDocumentAs(IOrderLineQuickInput.class);

		// Validate quick input:
		final BigDecimal quickInputQty = orderLineQuickInput.getQty();
		if (quickInputQty == null || quickInputQty.signum() <= 0)
		{
			logger.warn("Invalid Qty={} for {}", quickInputQty, orderLineQuickInput);
			throw new AdempiereException("Qty shall be greather than zero"); // TODO trl
		}

		final I_C_Order order = quickInput.getRootDocumentAs(I_C_Order.class);
		final OrderId orderId = OrderId.ofRepoId(order.getC_Order_ID());
		final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(order.getC_BPartner_ID());

		final ProductAndAttributes productAndAttributes = ProductLookupDescriptor.toProductAndAttributes(orderLineQuickInput.getM_Product_ID());
		final UomId uomId = productBL.getStockUOMId(productAndAttributes.getProductId());

		return OrderLineCandidate.builder()
				.orderId(orderId)
				.productId(productAndAttributes.getProductId())
				.attributes(productAndAttributes.getAttributes())
				.uomId(uomId)
				.piItemProductId(HUPIItemProductId.ofRepoIdOrNull(orderLineQuickInput.getM_HU_PI_Item_Product_ID()))
				.qty(quickInputQty)
				.bestBeforePolicy(ShipmentAllocationBestBeforePolicy.ofNullableCode(orderLineQuickInput.getShipmentAllocation_BestBefore_Policy()))
				.bpartnerId(bpartnerId)
				.soTrx(SOTrx.ofBoolean(order.isSOTrx()))
				.build();
	}

	private List<OrderLineCandidate> explodePhantomBOM(final OrderLineCandidate initialCandidate)
	{
		final ProductId bomProductId = initialCandidate.getProductId();
		final I_PP_Product_BOM bom = bomsRepo.getDefaultBOMByProductId(bomProductId).orElse(null);
		if (bom == null)
		{
			return ImmutableList.of(initialCandidate);
		}

		final BOMUse bomUse = BOMUse.ofNullableCode(bom.getBOMUse());
		if (!BOMUse.Phantom.equals(bomUse))
		{
			return ImmutableList.of(initialCandidate);
		}

		GroupId compensationGroupId = null;

		final ArrayList<OrderLineCandidate> result = new ArrayList<>();
		final List<I_PP_Product_BOMLine> bomLines = bomsRepo.retrieveLines(bom);
		for (final I_PP_Product_BOMLine bomLine : bomLines)
		{
			final ProductBOMLineId bomLineId = ProductBOMLineId.ofRepoId(bomLine.getPP_Product_BOMLine_ID());
			final ProductId bomLineProductId = ProductId.ofRepoId(bomLine.getM_Product_ID());
			final BigDecimal bomLineQty = bomsService.computeQtyRequired(bomLine, bomProductId, initialCandidate.getQty());

			final AttributeSetInstanceId bomLineAsiId = AttributeSetInstanceId.ofRepoIdOrNone(bomLine.getM_AttributeSetInstance_ID());
			final ImmutableAttributeSet attributes = asiBL.getImmutableAttributeSetById(bomLineAsiId);

			if (compensationGroupId == null)
			{
				compensationGroupId = orderGroupsRepo.createNewGroupId(GroupCreateRequest.builder()
						.orderId(initialCandidate.getOrderId())
						.name(productBL.getProductName(bomProductId))
						.build());
			}

			result.add(initialCandidate.toBuilder()
					.productId(bomLineProductId)
					.attributes(attributes)
					.qty(bomLineQty)
					.compensationGroupId(compensationGroupId)
					.explodedFromBOMLineId(bomLineId)
					.build());
		}

		return result;
	}

	private void updateOrderLine(final I_C_OrderLine to, final OrderLineCandidate candidate)
	{
		final PlainHUPackingAware fromPackingInfo = createQuickInputPackingAware(candidate);
		huPackingAwareBL.prepareCopyFrom(fromPackingInfo)
				.overridePartner(false)
				.asiCopyMode(ASICopyMode.CopyID) // because we just created the ASI
				.copyTo(OrderLineHUPackingAware.of(to));

		if (candidate.getBestBeforePolicy() != null)
		{
			to.setShipmentAllocation_BestBefore_Policy(candidate.getBestBeforePolicy().getCode());
		}

		if (candidate.getCompensationGroupId() != null)
		{
			to.setC_Order_CompensationGroup_ID(candidate.getCompensationGroupId().getOrderCompensationGroupId());
		}

		if (candidate.getExplodedFromBOMLineId() != null)
		{
			to.setExplodedFrom_BOMLine_ID(candidate.getExplodedFromBOMLineId().getRepoId());
		}
	}

	private PlainHUPackingAware createQuickInputPackingAware(@NonNull final OrderLineCandidate candidate)
	{
		final PlainHUPackingAware huPackingAware = new PlainHUPackingAware();
		huPackingAware.setBpartnerId(candidate.getBpartnerId());
		huPackingAware.setInDispute(false);
		huPackingAware.setProductId(candidate.getProductId());
		huPackingAware.setUomId(candidate.getUomId());
		huPackingAware.setAsiId(createASI(candidate.getProductId(), candidate.getAttributes()));
		huPackingAware.setPiItemProductId(candidate.getPiItemProductId());

		//
		huPackingAwareBL.computeAndSetQtysForNewHuPackingAware(huPackingAware, candidate.getQty());

		//
		// Validate:
		if (huPackingAware.getQty() == null || huPackingAware.getQty().signum() <= 0)
		{
			logger.warn("Invalid Qty={} for {}", huPackingAware.getQty(), huPackingAware);
			throw new AdempiereException("Qty shall be greather than zero"); // TODO trl
		}
		if (huPackingAware.getQtyTU() == null || huPackingAware.getQtyTU().signum() <= 0)
		{
			logger.warn("Invalid QtyTU={} for {}", huPackingAware.getQtyTU(), huPackingAware);
			throw new AdempiereException("QtyTU shall be greather than zero"); // TODO trl
		}

		return huPackingAware;
	}

	private AttributeSetInstanceId createASI(
			@NonNull final ProductId productId,
			@NonNull final ImmutableAttributeSet attributes)
	{
		if (attributes.isEmpty())
		{
			return null;
		}

		final I_M_AttributeSetInstance asi = asiBL.createASIWithASFromProductAndInsertAttributeSet(
				productId,
				attributes);

		return AttributeSetInstanceId.ofRepoId(asi.getM_AttributeSetInstance_ID());
	}

	@Value
	@Builder(toBuilder = true)
	private static class OrderLineCandidate
	{
		@NonNull
		OrderId orderId;

		@NonNull
		ProductId productId;

		@NonNull
		ImmutableAttributeSet attributes;

		@NonNull
		UomId uomId;

		@Nullable
		HUPIItemProductId piItemProductId;

		@NonNull
		BigDecimal qty;

		@Nullable
		ShipmentAllocationBestBeforePolicy bestBeforePolicy;

		@Nullable
		BPartnerId bpartnerId;

		@NonNull
		SOTrx soTrx;

		@Nullable
		GroupId compensationGroupId;

		@Nullable
		ProductBOMLineId explodedFromBOMLineId;
	}
}
