package de.metas.ui.web.order.products_proposal.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.currency.Amount;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.ProductPriceId;
import de.metas.product.ProductId;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.order.products_proposal.campaign_price.CampaignPriceProvider;
import de.metas.ui.web.order.products_proposal.campaign_price.CampaignPriceProviders;
import de.metas.ui.web.order.products_proposal.filters.ProductsProposalViewFilter;
import de.metas.ui.web.order.products_proposal.model.ProductsProposalRowChangeRequest.RowUpdate;
import de.metas.ui.web.order.products_proposal.model.ProductsProposalRowChangeRequest.UserChange;
import de.metas.ui.web.order.products_proposal.service.Order;
import de.metas.ui.web.order.products_proposal.service.OrderLine;
import de.metas.ui.web.order.products_proposal.service.OrderProductProposalsService;
import de.metas.ui.web.view.IEditableView.RowEditingContext;
import de.metas.ui.web.view.ViewHeaderProperties;
import de.metas.ui.web.view.template.IEditableRowsData;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdIntSequence;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.util.GuavaCollectors;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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
public class ProductsProposalRowsData implements IEditableRowsData<ProductsProposalRow>
{
	private final DocumentIdIntSequence nextRowIdSequence;
	private final CampaignPriceProvider campaignPriceProvider;

	private final OrderProductProposalsService orderProductProposalsService;

	private ArrayList<DocumentId> rowIdsOrderedAndFiltered;
	private final ArrayList<DocumentId> rowIdsOrdered; // used to preserve the order
	private final HashMap<DocumentId, ProductsProposalRow> rowsById;

	@Getter
	private final Optional<PriceListVersionId> singlePriceListVersionId;
	@Getter
	private final Optional<PriceListVersionId> basePriceListVersionId;

	@Getter
	private final Optional<Order> order;
	@Getter
	private final Optional<BPartnerId> bpartnerId;
	@Getter
	private final SOTrx soTrx;

	@Getter
	private final CurrencyId currencyId;
	@Getter
	private final ViewHeaderProperties headerProperties;

	private Map<ProductPriceId, OrderLine> bestMatchingProductPriceIdToOrderLine;

	private ProductsProposalViewFilter filter = ProductsProposalViewFilter.ANY;

	@Builder
	private ProductsProposalRowsData(
			@NonNull final DocumentIdIntSequence nextRowIdSequence,
			@Nullable final CampaignPriceProvider campaignPriceProvider,
			@Nullable final OrderProductProposalsService orderProductProposalsService,
			//
			@Nullable final PriceListVersionId singlePriceListVersionId,
			@Nullable final PriceListVersionId basePriceListVersionId,
			@Nullable final Order order,
			@Nullable final BPartnerId bpartnerId,
			@NonNull final SOTrx soTrx,
			@Nullable final CurrencyId currencyId,
			//
			@Nullable final ViewHeaderProperties headerProperties,
			//
			@NonNull final List<ProductsProposalRow> rows)
	{
		this.nextRowIdSequence = nextRowIdSequence;
		this.campaignPriceProvider = campaignPriceProvider != null ? campaignPriceProvider : CampaignPriceProviders.none();
		this.orderProductProposalsService = orderProductProposalsService;

		this.singlePriceListVersionId = Optional.ofNullable(singlePriceListVersionId);
		this.basePriceListVersionId = Optional.ofNullable(basePriceListVersionId);
		this.order = Optional.ofNullable(order);
		this.bpartnerId = Optional.ofNullable(bpartnerId);
		this.soTrx = soTrx;

		this.currencyId = currencyId;
		this.headerProperties = headerProperties != null ? headerProperties : ViewHeaderProperties.EMPTY;

		rowIdsOrdered = rows.stream()
				.map(ProductsProposalRow::getId)
				.collect(Collectors.toCollection(ArrayList::new));
		rowIdsOrderedAndFiltered = new ArrayList<>(rowIdsOrdered);

		rowsById = rows.stream()
				.collect(GuavaCollectors.toMapByKey(HashMap::new, ProductsProposalRow::getId));

	}

	@Override
	public synchronized int size()
	{
		return rowIdsOrderedAndFiltered.size();
	}

	@Override
	public Map<DocumentId, ProductsProposalRow> getDocumentId2AllRows()
	{
		return getDocumentId2TopLevelRows();
	}

	@Override
	public synchronized ImmutableMap<DocumentId, ProductsProposalRow> getDocumentId2TopLevelRows()
	{
		return rowIdsOrderedAndFiltered.stream()
				.map(rowsById::get)
				.collect(GuavaCollectors.toImmutableMapByKey(ProductsProposalRow::getId));
	}

	@Override
	public synchronized ImmutableList<ProductsProposalRow> getTopLevelRows()
	{
		return rowIdsOrderedAndFiltered.stream()
				.map(rowsById::get)
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public ImmutableList<ProductsProposalRow> getAllRows()
	{
		return getTopLevelRows();
	}

	@Override
	public DocumentIdsSelection getDocumentIdsToInvalidate(final TableRecordReferenceSet recordRefs)
	{
		return DocumentIdsSelection.EMPTY;
	}

	@Override
	public void invalidateAll()
	{
	}

	@Override
	public void patchRow(final RowEditingContext ctx, final List<JSONDocumentChangedEvent> fieldChangeRequests)
	{
		final UserChange request = ProductsProposalRowActions.toUserChangeRequest(fieldChangeRequests);
		changeRow(ctx.getRowId(), row -> ProductsProposalRowReducers.reduce(row, request));
	}

	public void patchRow(@NonNull final DocumentId rowId, @NonNull final ProductsProposalRowChangeRequest request)
	{
		changeRow(rowId, row -> ProductsProposalRowReducers.reduce(row, request));
	}

	private synchronized void changeRow(@NonNull final DocumentId rowId, @NonNull final UnaryOperator<ProductsProposalRow> mapper)
	{
		if (!rowIdsOrderedAndFiltered.contains(rowId))
		{
			throw new EntityNotFoundException(rowId.toJson());
		}

		rowsById.compute(rowId, (key, oldRow) -> {
			if (oldRow == null)
			{
				throw new EntityNotFoundException(rowId.toJson());
			}

			return mapper.apply(oldRow);
		});
	}

	public synchronized Set<ProductId> getProductIds()
	{
		return rowsById.values()
				.stream()
				.map(ProductsProposalRow::getProductId)
				.collect(ImmutableSet.toImmutableSet());
	}

	private synchronized Optional<ProductsProposalRow> getRowByProductAndASI(@NonNull final ProductId productId, @NonNull final ProductASIDescription asiDescription)
	{
		return rowsById.values()
				.stream()
				.filter(row -> productId.equals(row.getProductId())
						&& asiDescription.equals(row.getAsiDescription()))
				.findFirst();
	}

	public void addOrUpdateRows(@NonNull final List<ProductsProposalRowAddRequest> requests)
	{
		final List<ProductPriceId> productPriceIds = requests.stream()
				.map(request -> request.getCopiedFromProductPriceId())
				.collect(ImmutableList.toImmutableList());
		if(order.isPresent() && orderProductProposalsService != null)
		{
			bestMatchingProductPriceIdToOrderLine = orderProductProposalsService.findBestMatchesForOrderLineFromProductPricesId(order.get(), productPriceIds);
		}
		else
		{
			bestMatchingProductPriceIdToOrderLine = ImmutableMap.of();
		}

		requests.forEach(this::addOrUpdateRow);
	}

	private synchronized void addOrUpdateRow(@NonNull final ProductsProposalRowAddRequest request)
	{
		final ProductsProposalRow existingRow = getRowByProductAndASI(request.getProductId(), request.getAsiDescription())
				.orElse(null);
		if (existingRow != null)
		{
			patchRow(existingRow.getId(), RowUpdate.builder()
					.price(createPrice(request.getProductId(), request.getPriceListPrice()))
					.lastShipmentDays(request.getLastShipmentDays())

					.copiedFromProductPriceId(request.getCopiedFromProductPriceId())
					.build());
		}
		else
		{
			addRow(createRow(request));
		}
	}

	private ProductProposalPrice createPrice(@NonNull final ProductId productId, @NonNull final Amount priceListPrice)
	{
		final ProductProposalCampaignPrice campaignPrice = campaignPriceProvider.getCampaignPrice(productId).orElse(null);

		return ProductProposalPrice.builder()
				.priceListPrice(priceListPrice)
				.campaignPrice(campaignPrice)
				.build();
	}

	private ProductsProposalRow createRow(final ProductsProposalRowAddRequest request)
	{
		return ProductsProposalRow.builder()
				.id(nextRowIdSequence.nextDocumentId())
				.product(request.getProduct())
				.asiDescription(request.getAsiDescription())
				.asiId(request.getAsiId())
				.price(createPrice(request.getProductId(), request.getPriceListPrice()))
				.packingMaterialId(request.getPackingMaterialId())
				.packingDescription(request.getPackingDescription())
				.lastShipmentDays(request.getLastShipmentDays())
				.copiedFromProductPriceId(request.getCopiedFromProductPriceId())
				.build()

				.withExistingOrderLine(bestMatchingProductPriceIdToOrderLine.get(request.getCopiedFromProductPriceId()));
	}

	private synchronized void addRow(final ProductsProposalRow row)
	{
		rowIdsOrderedAndFiltered.add(0, row.getId()); // add first
		rowIdsOrdered.add(0, row.getId()); // add first

		rowsById.put(row.getId(), row);
	}

	public synchronized void removeRowsByIds(@NonNull final Set<DocumentId> rowIds)
	{
		rowIdsOrderedAndFiltered.removeAll(rowIds);
		rowIdsOrdered.removeAll(rowIds);
		rowIds.forEach(rowsById::remove);
	}

	public synchronized ProductsProposalViewFilter getFilter()
	{
		return filter;
	}

	public synchronized void filter(@NonNull final ProductsProposalViewFilter filter)
	{
		if (Objects.equals(this.filter, filter))
		{
			return;
		}

		this.filter = filter;

		rowIdsOrderedAndFiltered = rowIdsOrdered
				.stream()
				.filter(rowId -> rowsById.get(rowId).isMatching(filter))
				.collect(Collectors.toCollection(ArrayList::new));
	}
}
