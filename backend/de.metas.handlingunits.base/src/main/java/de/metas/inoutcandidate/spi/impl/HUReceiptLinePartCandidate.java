package de.metas.inoutcandidate.spi.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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

import de.metas.handlingunits.model.I_M_ReceiptSchedule_Alloc;
import de.metas.inout.model.I_M_QualityNote;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.uom.UOMConversionContext;
import de.metas.uom.UomId;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.adempiere.util.lang.ObjectUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * It's a part of an {@link HUReceiptLineCandidate}.
 *
 * @author tsa
 *
 */
/* package */class HUReceiptLinePartCandidate
{
	//
	// Params
	private final HUReceiptLinePartAttributes _attributes;

	//
	// Aggregated values
	private boolean _stale = true;

	/**
	 * Keep the M_QualityNote linked to the candidate
	 */
	private I_M_QualityNote _qualityNote = null;
	private ReceiptQty _qtyAndQuality = null;
	private Quantity _qty;
	private int _subProducerBPartnerId = -1;
	private Object _attributeStorageAggregationKey = null;
	//
	private final List<I_M_ReceiptSchedule_Alloc> receiptScheduleAllocs = new ArrayList<I_M_ReceiptSchedule_Alloc>();
	private final transient List<I_M_ReceiptSchedule_Alloc> receiptScheduleAllocsRO = Collections.unmodifiableList(receiptScheduleAllocs);

	private final ProductId productId;

	/**
	 * @param uomId ID of he receipt-line's UOM; not the catch-UOM
	 */
	public HUReceiptLinePartCandidate(
			@NonNull final HUReceiptLinePartAttributes attributes,
			@NonNull final ProductId productId,
			@NonNull final UomId uomId)
	{
		_attributes = attributes;

		this.productId = productId;

		_stale = true; // stale by default
		_qty = Quantitys.zero(uomId);
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	public void add(@NonNull final I_M_ReceiptSchedule_Alloc rsa)
	{
		final BigDecimal rsaQty = rsa.getHU_QtyAllocated();
		final Quantity huAllocatedQty = Quantitys.of(rsaQty, UomId.ofRepoId(rsa.getM_ReceiptSchedule().getC_UOM_ID()));

		_qty = Quantitys.add(UOMConversionContext.of(productId), _qty, huAllocatedQty);

		receiptScheduleAllocs.add(rsa);
		_stale = true;
	}

	/**
	 * @return true if added
	 */
	public boolean add(@NonNull final HUReceiptLinePartCandidate receiptLinePart)
	{
		if (!canAdd(receiptLinePart))
		{
			return false;
		}

		for (final I_M_ReceiptSchedule_Alloc rsa : receiptLinePart.getReceiptScheduleAllocs())
		{
			add(rsa);
		}

		return true;
	}

	public boolean canAdd(@NonNull final HUReceiptLinePartCandidate receiptLinePart)
	{
		// Cannot add to it self
		if (equals(receiptLinePart))
		{
			return false;
		}

		//
		// Shall be precisely the same attributes (i.e. M_HU_ID)
		if (!Objects.equals(getAttributes().getId(), receiptLinePart.getAttributes().getId()))
		{
			return false;
		}

		return true;
	}

	/**
	 * @return collected {@link I_M_ReceiptSchedule_Alloc}s
	 */
	public List<I_M_ReceiptSchedule_Alloc> getReceiptScheduleAllocs()
	{
		return receiptScheduleAllocsRO;
	}

	private final void updateIfStale()
	{
		if (!_stale)
		{
			return;
		}

		final HUReceiptLinePartAttributes attributes = getAttributes();

		//
		// Qty & Quality
		final Percent qualityDiscountPercent = Percent.of(attributes.getQualityDiscountPercent());
		final ReceiptQty qtyAndQuality;
		final Optional<Quantity> weight = attributes.getWeight();
		if (weight.isPresent())
		{
			qtyAndQuality = ReceiptQty.newWithCatchWeight(productId, weight.get().getUomId());

			final StockQtyAndUOMQty stockAndCatchQty = StockQtyAndUOMQtys.createConvert(_qty, productId, weight.get());
			qtyAndQuality.addQtyAndQualityDiscountPercent(stockAndCatchQty, qualityDiscountPercent);
		}
		else
		{
			qtyAndQuality = ReceiptQty.newWithoutCatchWeight(productId);
			qtyAndQuality.addQtyAndQualityDiscountPercent(_qty, qualityDiscountPercent);
		}
		I_M_QualityNote qualityNote = null;


		//
		// Quality Notice (only if we have a discount percentage)
		if (qualityDiscountPercent.signum() != 0)
		{
			qualityNote = attributes.getQualityNote();
			final String qualityNoticeDisplayName = attributes.getQualityNoticeDisplayName();
			qtyAndQuality.addQualityNotices(QualityNoticesCollection.valueOfQualityNote(qualityNoticeDisplayName));
		}

		//
		// Update values
		if (_qualityNote == null)
		{
			// set the quality note only if it was not set before. Only the first one is needed
			_qualityNote = qualityNote;
		}
		_qtyAndQuality = qtyAndQuality;
		_subProducerBPartnerId = attributes.getSubProducer_BPartner_ID();
		_attributeStorageAggregationKey = attributes.getAttributeStorageAggregationKey();
		_stale = false; // not stale anymore
	}

	/**
	 * @return part attributes; never return null
	 */
	// package level access for testing purposes
	HUReceiptLinePartAttributes getAttributes()
	{
		return _attributes;
	}

	/**
	 * @return qty & quality; never returns null
	 */
	public final ReceiptQty getQtyAndQuality()
	{
		updateIfStale();
		return _qtyAndQuality;
	}

	public int getSubProducer_BPartner_ID()
	{
		updateIfStale();
		return _subProducerBPartnerId;
	}

	public Object getAttributeStorageAggregationKey()
	{
		updateIfStale();
		return _attributeStorageAggregationKey;
	}

	/**
	 * Get the quality note linked with the part candidate
	 *
	 * @return
	 */
	public I_M_QualityNote getQualityNote()
	{
		return _qualityNote;
	}

}
