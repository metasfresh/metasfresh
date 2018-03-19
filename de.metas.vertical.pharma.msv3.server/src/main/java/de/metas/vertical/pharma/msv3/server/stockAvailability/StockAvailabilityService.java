package de.metas.vertical.pharma.msv3.server.stockAvailability;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.vertical.pharma.msv3.protocol.stockAvailability.AvailabilityType;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilityQuery;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilityQueryItem;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilityResponse;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilityResponse.StockAvailabilityResponseBuilder;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilityResponseItem;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilityResponseItemPart;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilityResponseItemPartType;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilitySubstitution;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilitySubstitutionReason;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilitySubstitutionType;
import de.metas.vertical.pharma.msv3.protocol.types.PZN;
import de.metas.vertical.pharma.msv3.protocol.types.Quantity;
import de.metas.vertical.pharma.msv3.server.stockAvailability.sync.JsonStockAvailability;
import de.metas.vertical.pharma.msv3.server.stockAvailability.sync.JsonStockAvailabilityUpdateRequest;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-pharma.msv3.server
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

@Service
public class StockAvailabilityService
{
	@Autowired
	private JpaStockAvailabilityRepository stockAvailabilityRepo;

	public StockAvailabilityResponse checkAvailability(final StockAvailabilityQuery query)
	{
		final StockAvailabilityResponseBuilder responseBuilder = StockAvailabilityResponse.builder()
				.id(query.getId())
				.availabilityType(AvailabilityType.SPECIFIC);

		for (final StockAvailabilityQueryItem queryItem : query.getItems())
		{
			final Quantity qtyAvailable;

			final JpaStockAvailability jpaStockAvailability = stockAvailabilityRepo.findByPzn(queryItem.getPzn().getValueAsLong());
			if (jpaStockAvailability == null)
			{
				qtyAvailable = Quantity.ZERO;
			}
			else
			{
				qtyAvailable = queryItem.getQtyRequired().min(jpaStockAvailability.getQty());
			}

			responseBuilder.item(StockAvailabilityResponseItem.builder()
					.pzn(queryItem.getPzn())
					.qty(qtyAvailable)
					.build());
		}

		return responseBuilder.build();
	}

	private StockAvailabilityResponse createMockAnswer(final StockAvailabilityQuery query)
	{
		return StockAvailabilityResponse.builder()
				.id(query.getId())
				.availabilityType(AvailabilityType.SPECIFIC)
				.items(query.getItems().stream()
						.map(queryItem -> StockAvailabilityResponseItem.builder()
								.pzn(queryItem.getPzn())
								.qty(queryItem.getQtyRequired())
								.substitution(StockAvailabilitySubstitution.builder()
										.pzn(PZN.of(queryItem.getPzn().getValueAsLong() * 100 + 9))
										.reason(StockAvailabilitySubstitutionReason.OUT_OF_TRADE)
										.type(StockAvailabilitySubstitutionType.PROPOSAL)
										.build())
								.part(StockAvailabilityResponseItemPart.builder()
										.qty(queryItem.getQtyRequired())
										.type(StockAvailabilityResponseItemPartType.NORMAL)
										.deliveryDate(LocalDateTime.of(2018, 3, 20, 3, 00))
										.reason(StockAvailabilitySubstitutionReason.NO_INFO)
										// .tour(tour)
										// .tourDeviation(tourDeviation)
										.build())
								.build())
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	public void updateStockAvailability(@NonNull final JsonStockAvailabilityUpdateRequest request)
	{
		request.getItems().forEach(this::updateStockAvailability);
	}

	private void updateStockAvailability(@NonNull final JsonStockAvailability request)
	{
		JpaStockAvailability jpaStockAvailability = stockAvailabilityRepo.findByPzn(request.getPzn());
		if (jpaStockAvailability == null)
		{
			jpaStockAvailability = new JpaStockAvailability();
			jpaStockAvailability.setPzn(request.getPzn());
		}

		jpaStockAvailability.setQty(request.getQty());
		stockAvailabilityRepo.save(jpaStockAvailability);
	}
}
