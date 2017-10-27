package de.metas.material.dispo;

import de.metas.material.dispo.candidate.DemandDetail;
import de.metas.material.dispo.candidate.DistributionDetail;
import de.metas.material.dispo.candidate.ProductionDetail;
import de.metas.material.dispo.model.X_MD_Candidate;
import de.metas.material.event.MaterialDescriptor;

/*
 * #%L
 * metasfresh-material-dispo-commons
 * %%
 * Copyright (C) 2017 metas GmbH
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

// TODO remove
public interface CandidateSpecification
{
	/**
	 * Please keep in sync with the values of {@link X_MD_Candidate#MD_CANDIDATE_TYPE_AD_Reference_ID}
	 */
	public enum Type
	{
		DEMAND, SUPPLY, STOCK, STOCK_UP, UNRELATED_INCREASE, UNRELATED_DECREASE
	};

	/**
	 * Please keep in sync with the values of {@link X_MD_Candidate#MD_CANDIDATE_SUBTYPE_AD_Reference_ID}
	 */
	public enum SubType
	{
		DISTRIBUTION, PRODUCTION, RECEIPT, SHIPMENT, FORECAST
	};

	/**
	 * Please keep in sync with the values of {@link X_MD_Candidate#MD_CANDIDATE_STATUS_AD_Reference_ID}
	 */
	public enum Status
	{
		doc_planned, doc_created, doc_completed, doc_closed, unexpected
	}

	int getOrgId();

	MaterialDescriptor getMaterialDescr();

	Type getType();

	/**
	 * Should be {@code null} for stock candidates.
	 */
	SubType getSubType();

	Status getStatus();

	int getId();

	/**
	 * A supply candidate has a stock candidate as its parent. A demand candidate has a stock candidate as its child.
	 */
	int getParentId();

	/**
	 * A supply candidate and its corresponding demand candidate are associated by a common group id.
	 */
	int getGroupId();

	int getSeqNo();

	/**
	 * Used for additional infos if this candidate relates to particular demand
	 */
	DemandDetail getDemandDetail();

	/**
	 * Used for additional infos if this candidate has the sub type {@link SubType#PRODUCTION}.
	 */
	ProductionDetail getProductionDetail();

	/**
	 * Used for additional infos if this candidate has the sub type {@link SubType#DISTRIBUTION}.
	 */
	DistributionDetail getDistributionDetail();
}
