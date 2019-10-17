package de.metas.materialtracking.impl;

/*
 * #%L
 * de.metas.materialtracking
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.metas.materialtracking.IMaterialTrackingQuery;
import de.metas.util.Check;

/* package */class MaterialTrackingQuery implements IMaterialTrackingQuery
{
	private int productId = -1;
	private int bpartnerId = -1;
	private String lot = null;
	private Boolean processed = null;
	private Boolean completeFlatrateTerm = null;
	private List<?> withLinkedDocuments = null;
	private OnMoreThanOneFound onMoreThanOneFound = OnMoreThanOneFound.ThrowException;
	private boolean returnReadOnlyRecords;

	@Override
	public String toString()
	{
		return "MaterialTrackingQuery ["
				+ "productId=" + productId
				+ ", bpartnerId=" + bpartnerId
				+ ", processed=" + processed
				+ ", completeFlatrateTerm" + completeFlatrateTerm
				+ ", withLinkedDocuments=" + withLinkedDocuments
				+ ", returnReadOnlyRecords=" + returnReadOnlyRecords
				+ ", onMoreThanOneFound=" + onMoreThanOneFound
				+ "]";
	}

	@Override
	public int getC_BPartner_ID()
	{
		return bpartnerId;
	}

	@Override
	public IMaterialTrackingQuery setC_BPartner_ID(final int bpartnerId)
	{
		this.bpartnerId = bpartnerId;
		return this;
	}

	@Override
	public int getM_Product_ID()
	{
		return productId;
	}

	@Override
	public IMaterialTrackingQuery setM_Product_ID(final int productId)
	{
		this.productId = productId;
		return this;
	}

	@Override
	public Boolean getProcessed()
	{
		return processed;
	}

	@Override
	public IMaterialTrackingQuery setProcessed(final Boolean processed)
	{
		this.processed = processed;
		return this;
	}

	@Override
	public IMaterialTrackingQuery setWithLinkedDocuments(final List<?> linkedModels)
	{
		if (linkedModels == null || linkedModels.isEmpty())
		{
			withLinkedDocuments = Collections.emptyList();
		}
		else
		{
			withLinkedDocuments = new ArrayList<>(linkedModels);
		}
		return this;
	}

	@Override
	public List<?> getWithLinkedDocuments()
	{
		return withLinkedDocuments;
	}

	@Override
	public IMaterialTrackingQuery setOnMoreThanOneFound(final OnMoreThanOneFound onMoreThanOneFound)
	{
		Check.assumeNotNull(onMoreThanOneFound, "onMoreThanOneFound not null");
		this.onMoreThanOneFound = onMoreThanOneFound;
		return this;
	}

	@Override
	public OnMoreThanOneFound getOnMoreThanOneFound()
	{
		return onMoreThanOneFound;
	}

	@Override
	public IMaterialTrackingQuery setCompleteFlatrateTerm(final Boolean completeFlatrateTerm)
	{
		this.completeFlatrateTerm = completeFlatrateTerm;
		return this;
	}

	@Override
	public Boolean getCompleteFlatrateTerm()
	{
		return completeFlatrateTerm;
	}

	@Override
	public IMaterialTrackingQuery setLot(final String lot)
	{
		this.lot = lot;
		return this;
	}

	@Override
	public String getLot()
	{
		return lot;
	}

	@Override
	public IMaterialTrackingQuery setReturnReadOnlyRecords(boolean returnReadOnlyRecords)
	{
		this.returnReadOnlyRecords = returnReadOnlyRecords;
		return this;
	}

	@Override
	public boolean isReturnReadOnlyRecords()
	{
		return returnReadOnlyRecords;
	}
}
