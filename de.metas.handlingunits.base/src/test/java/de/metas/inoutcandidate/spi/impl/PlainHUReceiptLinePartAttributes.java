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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.util.UUID;

import de.metas.inout.model.I_M_QualityNote;

public class PlainHUReceiptLinePartAttributes implements IHUReceiptLinePartAttributes
{
	private String id;
	private BigDecimal QualityDiscountPercent = BigDecimal.ZERO;
	private String QualityNoticeDisplayName;
	private int SubProducer_BPartner_ID;
	private String AttributeStorageAggregationKey;

	public PlainHUReceiptLinePartAttributes()
	{
		super();
		id = UUID.randomUUID().toString();
	}

	@Override
	public String toString()
	{
		return "PlainHUReceiptLinePartAttributes ["
				+ "id=" + id
				+ ", QualityDiscountPercent=" + QualityDiscountPercent
				+ ", QualityNoticeDisplayName=" + QualityNoticeDisplayName
				+ ", SubProducer_BPartner_ID=" + SubProducer_BPartner_ID
				+ ", AttributeStorageAggregationKey=" + AttributeStorageAggregationKey
				+ "]";
	}

	@Override
	public String getId()
	{
		return id;
	}

	public void setId(final String id)
	{
		this.id = id;
	}

	@Override
	public BigDecimal getQualityDiscountPercent()
	{
		return QualityDiscountPercent;
	}

	public void setQualityDiscountPercent(final BigDecimal qualityDiscountPercent)
	{
		QualityDiscountPercent = qualityDiscountPercent;
	}

	@Override
	public String getQualityNoticeDisplayName()
	{
		return QualityNoticeDisplayName;
	}

	public void setQualityNoticeDisplayName(final String qualityNoticeDisplayName)
	{
		QualityNoticeDisplayName = qualityNoticeDisplayName;
	}

	@Override
	public int getSubProducer_BPartner_ID()
	{
		return SubProducer_BPartner_ID;
	}

	public void setSubProducer_BPartner_ID(final int subProducer_BPartner_ID)
	{
		SubProducer_BPartner_ID = subProducer_BPartner_ID;
	}

	@Override
	public String getAttributeStorageAggregationKey()
	{
		return AttributeStorageAggregationKey;
	}

	public void setAttributeStorageAggregationKey(final String attributeStorageAggregationKey)
	{
		AttributeStorageAggregationKey = attributeStorageAggregationKey;
	}

	@Override
	public I_M_QualityNote getQualityNote()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
