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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.adempiere.util.Check;

import de.metas.materialtracking.IMaterialTrackingQuery;

/* package */class MaterialTrackingQuery implements IMaterialTrackingQuery
{
	private int productId = -1;
	private int bpartnerId = -1;
	private Boolean processed = null;
	private Boolean completeFlatrateTerm = null;
	private List<?> withLinkedDocuments = null;
	private OnMoreThanOneFound onMoreThanOneFound = OnMoreThanOneFound.ThrowException;

	@Override
	public String toString()
	{
		return "MaterialTrackingQuery ["
				+ "productId=" + productId
				+ ", bpartnerId=" + bpartnerId
				+ ", processed=" + processed
				+ ", completeFlatrateTerm" + completeFlatrateTerm
				+ ", withLinkedDocuments=" + withLinkedDocuments
				+ ", onMoreThanOneFound=" + onMoreThanOneFound
				+ "]";
	}

	@Override
	public int getC_BPartner_ID()
	{
		return bpartnerId;
	}

	@Override
	public void setC_BPartner_ID(final int bpartnerId)
	{
		this.bpartnerId = bpartnerId;
	}

	@Override
	public int getM_Product_ID()
	{
		return productId;
	}

	@Override
	public void setM_Product_ID(final int productId)
	{
		this.productId = productId;
	}

	@Override
	public Boolean getProcessed()
	{
		return processed;
	}

	@Override
	public void setProcessed(final Boolean processed)
	{
		this.processed = processed;
	}

	@Override
	public void setWithLinkedDocuments(final List<?> linkedModels)
	{
		if (linkedModels == null || linkedModels.isEmpty())
		{
			withLinkedDocuments = Collections.emptyList();
		}
		else
		{
			withLinkedDocuments = new ArrayList<>(linkedModels);
		}
	}

	@Override
	public List<?> getWithLinkedDocuments()
	{
		return withLinkedDocuments;
	}

	@Override
	public void setOnMoreThanOneFound(final OnMoreThanOneFound onMoreThanOneFound)
	{
		Check.assumeNotNull(onMoreThanOneFound, "onMoreThanOneFound not null");
		this.onMoreThanOneFound = onMoreThanOneFound;
	}

	@Override
	public OnMoreThanOneFound getOnMoreThanOneFound()
	{
		return onMoreThanOneFound;
	}

	@Override
	public void setCompleteFlatrateTerm(Boolean completeFlatrateTerm)
	{
		this.completeFlatrateTerm = completeFlatrateTerm;
		
	}

	@Override
	public Boolean getCompleteFlatrateTerm()
	{
		return completeFlatrateTerm;
	}
}
