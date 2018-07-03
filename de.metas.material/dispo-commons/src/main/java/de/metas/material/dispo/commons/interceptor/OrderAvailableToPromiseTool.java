package de.metas.material.dispo.commons.interceptor;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.model.I_C_Order;
import org.compiere.util.TimeUtil;

import de.metas.material.dispo.commons.model.I_C_OrderLine;
import de.metas.material.dispo.commons.repository.AvailableToPromiseQuery;
import de.metas.material.dispo.commons.repository.AvailableToPromiseRepository;
import de.metas.material.event.ModelProductDescriptorExtractor;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.ProductDescriptor;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-material-dispo-commons
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

public class OrderAvailableToPromiseTool
{
	public static void updateOrderLineRecords(@NonNull final I_C_Order orderRecord)
	{
		final Stream<I_C_OrderLine> orderLineRecords = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_OrderLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_OrderLine.COLUMNNAME_C_Order_ID, orderRecord.getC_Order_ID())
				.create()
				.stream();

		orderLineRecords.forEach(orderLineRecord -> {
			updateOrderLineRecordAndDoNotSave(orderLineRecord);
			saveRecord(orderLineRecord);
		});
		
	}

	public static void resetQtyAvailableToPromise(@NonNull final I_C_Order orderRecord)
	{
		final Stream<I_C_OrderLine> orderLineRecords = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_OrderLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_OrderLine.COLUMNNAME_C_Order_ID, orderRecord.getC_Order_ID())
				.create()
				.stream();

		orderLineRecords.forEach(orderLineRecord -> {
			orderLineRecord.setQty_AvailableToPromise(BigDecimal.ZERO);
			saveRecord(orderLineRecord);

		});
	}

	public static void updateOrderLineRecordAndDoNotSave(@NonNull final I_C_OrderLine orderLineRecord)
	{
		// we use the date at which the order needs to be ready for shipping
		final LocalDateTime preparationDate = TimeUtil.asLocalDateTime(orderLineRecord.getC_Order().getPreparationDate());

		final OrderLineKey orderLineKey = OrderLineKey.forOrderLineRecord(orderLineRecord);

		final AvailableToPromiseQuery query = createSingleQuery(preparationDate, orderLineKey);

		final AvailableToPromiseRepository stockRepository = Adempiere.getBean(AvailableToPromiseRepository.class);
		final BigDecimal result = stockRepository.retrieveAvailableStockQtySum(query);

		orderLineRecord.setQty_AvailableToPromise(result);
	}

	private static AvailableToPromiseQuery createSingleQuery(
			@NonNull final LocalDateTime preparationDate,
			@NonNull final OrderLineKey orderLineKey)
	{
		final AvailableToPromiseQuery query = AvailableToPromiseQuery
				.builder()
				.productId(orderLineKey.getProductId())
				.storageAttributesKey(orderLineKey.getAttributesKey())
				.bpartnerId(orderLineKey.getBpartnerId())
				.date(preparationDate)
				.build();
		return query;
	}

	@Value
	private static class OrderLineKey
	{
		int productId;
		AttributesKey attributesKey;
		int bpartnerId;

		private static OrderLineKey forOrderLineRecord(@NonNull final I_C_OrderLine orderLineRecord)
		{
			final ModelProductDescriptorExtractor productDescriptorFactory = Adempiere.getBean(ModelProductDescriptorExtractor.class);
			final ProductDescriptor productDescriptor = productDescriptorFactory.createProductDescriptor(orderLineRecord, AttributesKey.ALL);

			return new OrderLineKey(
					productDescriptor.getProductId(),
					productDescriptor.getStorageAttributesKey(),
					orderLineRecord.getC_BPartner_ID() // this column is mandatory and always > 0.
			);
		}

		private OrderLineKey(
				final int productId,
				@NonNull final AttributesKey attributesKey,
				final int bpartnerId)
		{
			this.productId = productId;
			this.attributesKey = attributesKey;
			this.bpartnerId = bpartnerId;
		}
	}
}
