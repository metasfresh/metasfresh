/*
 * #%L
 * metasfresh-material-dispo-service
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.material.dispo.service.interceptor;

import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.stockcandidate.StockCandidateChangedEvent;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.ModelValidator;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Component;

@Interceptor(I_MD_Candidate.class)
@Component
public class MD_Candidate
{
	private final PostMaterialEventService materialEventService;

	public MD_Candidate(@NonNull final PostMaterialEventService materialEventService)
	{
		this.materialEventService = materialEventService;
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_NEW }, ifColumnsChanged = I_MD_Candidate.COLUMNNAME_Qty)
	public void fireStockChangedEvent(@NonNull final I_MD_Candidate candidate)
	{
		if (!CandidateType.STOCK.getCode().equals(candidate.getMD_Candidate_Type()))
		{
			return;
		}

		final EventDescriptor eventDescriptor = EventDescriptor.ofClientAndOrg(candidate.getAD_Client_ID(), candidate.getAD_Org_ID());
		final ProductDescriptor productDescriptor = ProductDescriptor.forProductAndAttributes(candidate.getM_Product_ID(), AttributesKey.ofString(candidate.getStorageAttributesKey()));

		final MaterialDescriptor materialDescriptor = MaterialDescriptor.builder()
				.date(TimeUtil.asInstant(candidate.getDateProjected()))
				.productDescriptor(productDescriptor)
				.warehouseId(WarehouseId.ofRepoId(candidate.getM_Warehouse_ID()))
				.quantity(candidate.getQty())
				.build();

		final StockCandidateChangedEvent stockCandidateChangedEvent = StockCandidateChangedEvent.builder()
				.eventDescriptor(eventDescriptor)
				.materialDescriptor(materialDescriptor)
				.build();

		materialEventService.postEventAfterNextCommit(stockCandidateChangedEvent);
	}
}
