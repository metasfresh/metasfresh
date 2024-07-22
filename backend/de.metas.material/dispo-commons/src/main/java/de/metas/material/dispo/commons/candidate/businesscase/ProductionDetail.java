package de.metas.material.dispo.commons.candidate.businesscase;

import de.metas.document.engine.DocStatus;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.event.pporder.PPOrderRef;
import de.metas.product.ResourceId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eevolution.api.PPOrderAndBOMLineId;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/*
 * #%L
 * metasfresh-material-dispo
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
@Value
public class ProductionDetail implements BusinessCaseDetail
{
	public static ProductionDetail castOrNull(@Nullable final BusinessCaseDetail businessCaseDetail)
	{
		return businessCaseDetail instanceof ProductionDetail ? cast(businessCaseDetail) : null;
	}

	public static ProductionDetail cast(@NonNull final BusinessCaseDetail businessCaseDetail)
	{
		return (ProductionDetail)businessCaseDetail;
	}

	ResourceId plantId;
	@Nullable ResourceId workstationId;

	int productPlanningId;

	int productBomLineId;

	String description;

	@Nullable PPOrderRef ppOrderRef;

	DocStatus ppOrderDocStatus;

	Flag advised;

	Flag pickDirectlyIfFeasible;

	BigDecimal qty;

	@Builder(toBuilder = true)
	private ProductionDetail(
			final ResourceId plantId,
			@Nullable final ResourceId workstationId,
			final int productPlanningId,
			final int productBomLineId,
			final String description,
			@Nullable final PPOrderRef ppOrderRef,
			final DocStatus ppOrderDocStatus,
			@NonNull final Flag advised,
			@NonNull final Flag pickDirectlyIfFeasible,
			@NonNull final BigDecimal qty)
	{
		this.advised = advised;
		this.pickDirectlyIfFeasible = pickDirectlyIfFeasible;

		final boolean detailIsAboutPPOrderHeader = productBomLineId <= 0;
		if (advised.isTrue() && detailIsAboutPPOrderHeader)
		{
			// plantId needs to be available when using this productionDetail to request a ppOrder being created
			Check.errorIf(plantId == null, "Parameter plantId needs to set for and advised PPOrder 'Header' productionDetail");
		}

		this.plantId = plantId;
		this.workstationId = workstationId;

		this.productPlanningId = productPlanningId;
		this.productBomLineId = productBomLineId;
		this.description = description;
		this.ppOrderRef = ppOrderRef;
		this.ppOrderDocStatus = ppOrderDocStatus;
		this.qty = qty;
	}

	@Override
	public CandidateBusinessCase getCandidateBusinessCase()
	{
		return CandidateBusinessCase.PRODUCTION;
	}

	public int getPpOrderCandidateId() {return ppOrderRef != null ? ppOrderRef.getPpOrderCandidateId() : -1;}

	public int getPpOrderLineCandidateId() {return ppOrderRef != null ? ppOrderRef.getPpOrderLineCandidateId() : -1;}

	@Nullable
	public PPOrderId getPpOrderId() {return ppOrderRef != null ? ppOrderRef.getPpOrderId() : null;}

	@Nullable
	public PPOrderBOMLineId getPpOrderBOMLineId() {return ppOrderRef != null ? ppOrderRef.getPpOrderBOMLineId() : null;}

	@Nullable
	public PPOrderAndBOMLineId getPpOrderAndBOMLineId() {return ppOrderRef != null ? ppOrderRef.getPpOrderAndBOMLineId() : null;}

	public boolean isFinishedGoods()
	{
		return this.ppOrderRef != null && this.ppOrderRef.isFinishedGoods();
	}

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	public boolean isBOMLine()
	{
		return this.ppOrderRef != null && this.ppOrderRef.isBOMLine();
	}
}
