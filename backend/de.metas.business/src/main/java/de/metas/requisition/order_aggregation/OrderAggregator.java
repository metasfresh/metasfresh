package de.metas.requisition.order_aggregation;

import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
class OrderAggregator
{
	@NonNull private final IProductBL productBL;

	@NonNull private final OrderKey key;
	@NonNull private final MOrder order;
	private OrderLineAggregator currentLine = null;
	private final HashSet<Optional<UserId>> requestorIds = new HashSet<>();

	@Builder
	private OrderAggregator(
			final @NonNull IProductBL productBL,
			final @NonNull OrderKey key,
			final @NonNull MOrder order)
	{
		this.productBL = productBL;
		this.key = key;
		this.order = order;
	}

	public void save()
	{
		if (currentLine != null)
		{
			currentLine.save();
		}

		InterfaceWrapperHelper.refresh(order);

		final UserId singleRequestorId = requestorIds.size() == 1
				? requestorIds.iterator().next().orElse(null)
				: null;
		order.setRequestor_ID(UserId.toRepoId(singleRequestorId));

		order.saveEx();
	}

	public boolean isMatching(@NonNull final OrderKey key) {return this.key.equals(key);}

	public void aggregate(@NonNull final OrderCandidate candidate)
	{
		final OrderLineAggregator orderLine = getCreateLine(candidate);
		orderLine.addQty(candidate.getQty());

		addRequestorId(candidate.getRequestorId());

		candidate.markAsAggregated(orderLine.getOrderLineId());
	}

	private OrderLineAggregator getCreateLine(final OrderCandidate candidate)
	{
		final OrderLineKey lineKey = extractOrderLineKey(candidate);
		if (currentLine != null && !currentLine.isMatching(lineKey))
		{
			currentLine.save();
			currentLine = null;
		}

		if (currentLine == null)
		{
			this.currentLine = createLine(candidate);
		}

		return currentLine;
	}

	private OrderLineAggregator createLine(final OrderCandidate candidate)
	{
		final OrderLineKey lineKey = extractOrderLineKey(candidate);

		//noinspection deprecation
		MOrderLine orderLine = new MOrderLine(order);
		orderLine.setDatePromised(Timestamp.from(key.getDatePromised()));

		final ProductId productId = lineKey.getProductId();
		final AttributeSetInstanceId attributeSetInstanceId = lineKey.getAttributeSetInstanceId();
		if (productId != null)
		{
			final UomId uomId = productBL.getStockUOMId(productId);
			orderLine.setM_Product_ID(productId.getRepoId());
			orderLine.setC_UOM_ID(uomId.getRepoId());
			orderLine.setM_AttributeSetInstance_ID(attributeSetInstanceId.getRepoId());
		}
		else
		{
			orderLine.setC_Charge_ID(candidate.getC_Charge_ID());
			orderLine.setPriceActual(candidate.getPriceActual());
		}
		orderLine.setAD_Org_ID(candidate.getOrgId().getRepoId());

		orderLine.setM_Warehouse_ID(order.getM_Warehouse_ID()); // task 05914 : warehouse is mandatory
		orderLine.saveEx();

		return OrderLineAggregator.builder()
				.key(lineKey)
				.orderLine(orderLine)
				.build();
	}

	private static OrderLineKey extractOrderLineKey(final OrderCandidate candidate)
	{
		return OrderLineKey.builder()
				.productId(candidate.getProductId())
				.attributeSetInstanceId(candidate.getAttributeSetInstanceId())
				.uniqueIdentifier(candidate.getC_Charge_ID() > 0 ? UUID.randomUUID() : null)
				.build();
	}

	private void addRequestorId(@NonNull final Optional<UserId> requestorId)
	{
		requestorIds.add(requestorId);
	}

}
