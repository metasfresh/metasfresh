package de.metas.ui.web.quickinput.orderline;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_AttributeSetInstance;
import org.slf4j.Logger;

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
import de.metas.order.OrderLineInputValidatorResults;
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

	private final Collection<IOrderLineInputValidator> validators = SpringContextHolder.instance.getBeansOfType(IOrderLineInputValidator.class);

	@Override
	public DocumentId process(final QuickInput quickInput)
	{
		final OrderLineCandidate candidate = toOrderLineCandidate(quickInput);
		validateInput(candidate);

		final I_C_Order order = quickInput.getRootDocumentAs(I_C_Order.class);
		final Properties ctx = InterfaceWrapperHelper.getCtx(order);
		final I_C_OrderLine newOrderLine = OrderFastInput.addOrderLine(ctx, order, orderLine -> updateOrderLine(orderLine, candidate));
		final int newOrderLineId = newOrderLine.getC_OrderLine_ID();
		return DocumentId.of(newOrderLineId);
	}

	private void validateInput(final OrderLineCandidate candidate)
	{
		final BPartnerId bpartnerId = candidate.getBpartnerId();
		final ProductId productId = candidate.getProductAndAttributes().getProductId();
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
		final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(order.getC_BPartner_ID());

		final ProductAndAttributes productAndAttributes = ProductLookupDescriptor.toProductAndAttributes(orderLineQuickInput.getM_Product_ID());
		final UomId uomId = productBL.getStockUOMId(productAndAttributes.getProductId());

		return OrderLineCandidate.builder()
				.productAndAttributes(productAndAttributes)
				.uomId(uomId)
				.piItemProductId(HUPIItemProductId.ofRepoIdOrNull(orderLineQuickInput.getM_HU_PI_Item_Product_ID()))
				.qty(quickInputQty)
				.bestBeforePolicy(ShipmentAllocationBestBeforePolicy.ofNullableCode(orderLineQuickInput.getShipmentAllocation_BestBefore_Policy()))
				.bpartnerId(bpartnerId)
				.soTrx(SOTrx.ofBoolean(order.isSOTrx()))
				.build();
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
	}

	private PlainHUPackingAware createQuickInputPackingAware(@NonNull final OrderLineCandidate candidate)
	{
		final ProductAndAttributes productAndAttributes = candidate.getProductAndAttributes();

		final PlainHUPackingAware huPackingAware = new PlainHUPackingAware();
		huPackingAware.setBpartnerId(candidate.getBpartnerId());
		huPackingAware.setInDispute(false);
		huPackingAware.setProductId(productAndAttributes.getProductId());
		huPackingAware.setUomId(candidate.getUomId());
		huPackingAware.setAsiId(createASI(productAndAttributes));
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

	private AttributeSetInstanceId createASI(final ProductAndAttributes productAndAttributes)
	{
		final ImmutableAttributeSet attributes = productAndAttributes.getAttributes();
		if (attributes.isEmpty())
		{
			return null;
		}

		final I_M_AttributeSetInstance asi = asiBL.createASIWithASFromProductAndInsertAttributeSet(
				productAndAttributes.getProductId(),
				attributes);

		return AttributeSetInstanceId.ofRepoId(asi.getM_AttributeSetInstance_ID());
	}

	@Value
	@Builder
	private static class OrderLineCandidate
	{
		@NonNull
		ProductAndAttributes productAndAttributes;

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
	}
}
