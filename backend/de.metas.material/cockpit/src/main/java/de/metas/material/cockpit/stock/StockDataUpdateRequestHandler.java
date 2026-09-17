package de.metas.material.cockpit.stock;

import de.metas.material.cockpit.model.I_MD_Stock;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.stock.StockChangedEvent;
import de.metas.material.event.stock.StockChangedEvent.StockChangeDetails;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.DBUniqueConstraintException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.keys.AttributesKeys;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.IQuery;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

/*
 * #%L
 * metasfresh-material-cockpit
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

@Component
public class StockDataUpdateRequestHandler
{
	private final PostMaterialEventService postMaterialEventService;

	public StockDataUpdateRequestHandler(
			@NonNull final PostMaterialEventService postMaterialEventService)
	{
		this.postMaterialEventService = postMaterialEventService;
	}

	public void handleDataUpdateRequest(@NonNull final StockDataUpdateRequest dataUpdateRequest)
	{
		final I_MD_Stock dataRecord = retrieveOrCreateDataRecord(dataUpdateRequest.getIdentifier());

		final BigDecimal qtyOnHandOld = dataRecord.getQtyOnHand();

		final BigDecimal qtyOnHandToAdd = dataUpdateRequest.getOnHandQtyChange();
		final BigDecimal qtyOnHandNew = NumberUtils.stripTrailingDecimalZeros(dataRecord.getQtyOnHand().add(qtyOnHandToAdd));
		dataRecord.setQtyOnHand(qtyOnHandNew);
		save(dataRecord);

		fireStockChangedEvent(dataRecord, qtyOnHandOld, dataUpdateRequest.getSourceInfo());
	}

	/**
	 * Idempotently set {@code MD_Stock.QtyOnHand} to an absolute target (the HU-derived truth) — the
	 * reset semantics used by {@code MD_Stock_Update_From_M_HUs}. Unlike {@link #handleDataUpdateRequest}
	 * (which ADDS a delta, correct for the transaction-event path), this SETS the value, so overlapping
	 * concurrent reset runs all converge to the same truth instead of compounding into a runaway.
	 */
	public void handleResetToQtyOnHand(
			@NonNull final StockDataRecordIdentifier identifier,
			@NonNull final BigDecimal targetQtyOnHand,
			@NonNull final StockChangeSourceInfo sourceInfo)
	{
		final BigDecimal qtyOnHandNew = NumberUtils.stripTrailingDecimalZeros(targetQtyOnHand);

		final I_MD_Stock dataRecord = retrieveOrCreateDataRecord(identifier);
		final BigDecimal qtyOnHandOld = dataRecord.getQtyOnHand();

		// Idempotent: setting to the same truth twice (overlapping reset runs) is a no-op the second time.
		if (qtyOnHandOld.compareTo(qtyOnHandNew) == 0)
		{
			return;
		}

		dataRecord.setQtyOnHand(qtyOnHandNew);
		save(dataRecord);

		fireStockChangedEvent(dataRecord, qtyOnHandOld, sourceInfo);
	}

	private I_MD_Stock retrieveOrCreateDataRecord(@NonNull final StockDataRecordIdentifier identifier)
	{
		final IQuery<I_MD_Stock> query = createQueryForIdentifier(identifier);

		final I_MD_Stock existingDataRecord = query.firstOnly(I_MD_Stock.class);
		if (existingDataRecord != null)
		{
			return existingDataRecord;
		}

		final I_MD_Stock newDataRecord = newInstance(I_MD_Stock.class);
		InterfaceWrapperHelper.setValue(newDataRecord, I_MD_Stock.COLUMNNAME_AD_Client_ID, identifier.getClientId().getRepoId());

		newDataRecord.setAD_Org_ID(identifier.getOrgId().getRepoId());
		newDataRecord.setM_Product_ID(identifier.getProductId().getRepoId());
		newDataRecord.setAttributesKey(identifier.getStorageAttributesKey().getAsString());
		newDataRecord.setM_Warehouse_ID(identifier.getWarehouseId().getRepoId());

		try
		{
			save(newDataRecord);
			return newDataRecord;
		}
		catch (final DBUniqueConstraintException e)
		{
			// meanwhile another thread created the same bucket; return the existing one.
			// The event dispatch path does not retry on a bare exception, so this in-code fallback
			// is the only guarantee against a lost update on a create-create race.
			final I_MD_Stock winner = query.firstOnly(I_MD_Stock.class);
			if (winner != null)
			{
				return winner;
			}
			throw e;
		}
	}

	private IQuery<I_MD_Stock> createQueryForIdentifier(@NonNull final StockDataRecordIdentifier identifier)
	{
		final AttributesKey attributesKey = identifier.getStorageAttributesKey();
		attributesKey.assertNotAllOrOther();

		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_MD_Stock.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MD_Stock.COLUMNNAME_AD_Client_ID, identifier.getClientId())
				.addEqualsFilter(I_MD_Stock.COLUMNNAME_AD_Org_ID, identifier.getOrgId())
				.addEqualsFilter(I_MD_Stock.COLUMNNAME_M_Product_ID, identifier.getProductId())
				.addEqualsFilter(I_MD_Stock.COLUMN_AttributesKey, attributesKey.getAsString())
				.addEqualsFilter(I_MD_Stock.COLUMNNAME_M_Warehouse_ID, identifier.getWarehouseId())
				.create();
	}

	private void fireStockChangedEvent(
			@NonNull final I_MD_Stock dataRecord,
			@NonNull final BigDecimal qtyOnHandOld,
			@NonNull final StockChangeSourceInfo stockChangeSourceInfo)
	{
		final BigDecimal qtyOnHandNew = dataRecord.getQtyOnHand();
		if (qtyOnHandOld.compareTo(qtyOnHandNew) == 0)
		{
			return;
		}

		// there should be no empty parts, but let's just make sure..
		final AttributesKey attributesKey = AttributesKeys.pruneEmptyParts(AttributesKey.ofString(dataRecord.getAttributesKey()));
		final AttributeSetInstanceId asiId = AttributesKeys.createAttributeSetInstanceFromAttributesKey(attributesKey);

		final EventDescriptor eventDescriptor = EventDescriptor
				.ofClientAndOrg(
						dataRecord.getAD_Client_ID(),
						dataRecord.getAD_Org_ID());

		final ProductDescriptor productDescriptor = ProductDescriptor
				.forProductAndAttributes(
						dataRecord.getM_Product_ID(),
						attributesKey,
						asiId.getRepoId());

		final StockChangeDetails details = StockChangeDetails
				.builder()
				.transactionId(stockChangeSourceInfo.getTransactionId())
				.resetStockPInstanceId(stockChangeSourceInfo.getResetStockAdPinstanceId())
				.stockId(dataRecord.getMD_Stock_ID())
				.build();

		final StockChangedEvent event = StockChangedEvent
				.builder()
				.eventDescriptor(eventDescriptor)
				.productDescriptor(productDescriptor)
				.warehouseId(WarehouseId.ofRepoId(dataRecord.getM_Warehouse_ID()))
				.qtyOnHand(qtyOnHandNew)
				.qtyOnHandOld(qtyOnHandOld)
				.stockChangeDetails(details)
				.changeDate(TimeUtil.asInstant(dataRecord.getUpdated()))
				.build();

		// the event is about an I_MD_Stock record. better wait until it was committed to DB
		postMaterialEventService.enqueueEventAfterNextCommit(event);
	}
}
