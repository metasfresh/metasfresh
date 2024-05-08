/*
 * #%L
 * de.metas.manufacturing
 * %%
 * Copyright (C) 2022 metas GmbH
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

package org.eevolution.productioncandidate.model.interceptor;

import de.metas.common.util.EmptyUtil;
import de.metas.document.engine.DocStatus;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.compiere.model.ModelValidator;
import org.eevolution.api.IPPOrderDAO;
import org.eevolution.api.IProductBOMDAO;
import org.eevolution.api.ProductBOMId;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.productioncandidate.model.dao.PPOrderCandidateDAO;
import org.eevolution.productioncandidate.service.PPOrderCandidateService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Interceptor(I_PP_Product_BOM.class)
public class PP_Product_BOM
{
	private final PPOrderCandidateDAO ppOrderCandidateDAO;

	private final IProductBOMDAO productBOMDAO = Services.get(IProductBOMDAO.class);
	private final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
	private final IPPOrderDAO ppOrderDAO = Services.get(IPPOrderDAO.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	public PP_Product_BOM(@NonNull final PPOrderCandidateDAO ppOrderCandidateDAO)
	{
		this.ppOrderCandidateDAO = ppOrderCandidateDAO;
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_COMPLETE })
	public void onComplete(@NonNull final I_PP_Product_BOM productBOMRecord)
	{
		final Optional<I_PP_Product_BOM> previousBOMVersion = productBOMDAO.getPreviousVersion(productBOMRecord, DocStatus.Completed);

		if (!previousBOMVersion.isPresent())
		{
			return;
		}

		if (!shouldUpdateExistingPPOrderCandidates(productBOMRecord, previousBOMVersion.get()))
		{
			return;
		}

		final ProductBOMId previousBOMVersionID = ProductBOMId.ofRepoId(previousBOMVersion.get().getPP_Product_BOM_ID());

		trxManager.runAfterCommit(() -> updateBOMOnMatchingOrderCandidates(previousBOMVersionID, productBOMRecord));
	}

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_REACTIVATE })
	public void onReactivate(@NonNull final I_PP_Product_BOM productBOMRecord)
	{
		final ProductBOMId productBOMId = ProductBOMId.ofRepoId(productBOMRecord.getPP_Product_BOM_ID());

		if (isBOMInUse(productBOMId))
		{
			throw new AdempiereException("Product BOM is already in use (linked to a manufacturing order or candidate). It cannot be reactivated!")
					.appendParametersToMessage()
					.setParameter("productBOMId", productBOMId);
		}
	}

	private boolean isBOMInUse(@NonNull final ProductBOMId productBOMId)
	{
		return !EmptyUtil.isEmpty(ppOrderCandidateDAO.getByProductBOMId(productBOMId))
				|| !EmptyUtil.isEmpty(ppOrderDAO.getByProductBOMId(productBOMId));
	}

	private void updateBOMOnMatchingOrderCandidates(
			@NonNull final ProductBOMId previousBOMVersionID,
			@NonNull final I_PP_Product_BOM productBOMRecord)
	{
		ppOrderCandidateDAO.getByProductBOMId(previousBOMVersionID)
				.stream()
				.filter(ppOrderCandidate -> PPOrderCandidateService.canAssignBOMVersion(ppOrderCandidate, productBOMRecord))
				.peek(ppOrderCandidate -> ppOrderCandidate.setPP_Product_BOM_ID(productBOMRecord.getPP_Product_BOM_ID()))
				.forEach(ppOrderCandidateDAO::save);
	}

	private boolean shouldUpdateExistingPPOrderCandidates(@NonNull final I_PP_Product_BOM currentVersion, @NonNull final I_PP_Product_BOM oldVersion)
	{
		final AttributeSetInstanceId orderLineCandidateASIId = AttributeSetInstanceId.ofRepoIdOrNull(currentVersion.getM_AttributeSetInstance_ID());
		final AttributeSetInstanceId productBOMLineASIId = AttributeSetInstanceId.ofRepoIdOrNull(oldVersion.getM_AttributeSetInstance_ID());

		return attributeDAO.nullSafeASIEquals(orderLineCandidateASIId, productBOMLineASIId);
	}
}