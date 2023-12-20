package org.eevolution.model.validator;

import com.google.common.annotations.VisibleForTesting;
import de.metas.i18n.AdMessageKey;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.planning.ProductPlanning;
import de.metas.material.planning.impl.ProductPlanningDAO;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.keys.AttributesKeys;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;
import org.eevolution.api.IProductBOMDAO;
import org.eevolution.api.PPOrderDocBaseType;
import org.eevolution.api.ProductBOMVersionsId;
import org.eevolution.api.impl.ProductBOMService;
import org.eevolution.api.impl.ProductBOMVersionsDAO;
import org.eevolution.model.I_PP_Product_BOMVersions;
import org.eevolution.model.I_PP_Product_Planning;

import java.util.Arrays;
import java.util.Optional;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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

@Interceptor(I_PP_Product_Planning.class)
public class PP_Product_Planning
{
	private final IProductBOMDAO bomDAO = Services.get(IProductBOMDAO.class);

	private final ProductBOMVersionsDAO bomVersionsDAO;
	private final ProductBOMService productBOMService;

	public PP_Product_Planning(
			@NonNull final ProductBOMVersionsDAO bomVersionsDAO,
			@NonNull final ProductBOMService productBOMService)
	{
		this.bomVersionsDAO = bomVersionsDAO;
		this.productBOMService = productBOMService;
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void beforeSave(@NonNull final I_PP_Product_Planning productPlanning)
	{
		if (InterfaceWrapperHelper.isValueChanged(productPlanning, I_PP_Product_Planning.COLUMNNAME_M_AttributeSetInstance_ID))
		{
			updateStorageAttributesKey(productPlanning);
		}
	}

	@VisibleForTesting
	void updateStorageAttributesKey(@NonNull final I_PP_Product_Planning record)
	{
		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNone(record.getM_AttributeSetInstance_ID());
		final AttributesKey attributesKey = AttributesKeys.createAttributesKeyFromASIStorageAttributes(asiId).orElse(AttributesKey.NONE);
		record.setStorageAttributesKey(attributesKey.getAsString());
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE,
			ifColumnsChanged = { I_PP_Product_Planning.COLUMNNAME_M_Product_ID, I_PP_Product_Planning.COLUMNNAME_PP_Product_BOMVersions_ID })
	public void validateBOMVersionsAndProductId(final I_PP_Product_Planning record)
	{
		final ProductPlanning productPlanning = ProductPlanningDAO.fromRecord(record);

		final ProductId productId = productPlanning.getProductId();
		if (productId == null)
		{
			return;
		}

		Optional.ofNullable(productPlanning.getBomVersionsId())
				.map(bomVersionsDAO::getBOMVersions)
				.ifPresent(bomVersions -> {
					if (bomVersions.getM_Product_ID() != productId.getRepoId())
					{
						throw new AdempiereException(AdMessageKey.of("PP_Product_Planning_BOM_Doesnt_Match"))
								.appendParametersToMessage()
								.setParameter("bomVersion", bomVersions)
								.setParameter("productPlanning", productPlanning)
								.markAsUserValidationError();
					}
				});
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE,
			ifColumnsChanged = {I_PP_Product_Planning.COLUMNNAME_PP_Product_BOMVersions_ID})
	public void updateProductFromBomVersions(final I_PP_Product_Planning planning)
	{
		final  ProductBOMVersionsId bomVersionsId = ProductBOMVersionsId.ofRepoIdOrNull(planning.getPP_Product_BOMVersions_ID());
		if (bomVersionsId == null)
		{
			return; // nothing to do
		}
		final I_PP_Product_BOMVersions bomVersions = bomVersionsDAO.getBOMVersions(bomVersionsId);
		planning.setM_Product_ID(bomVersions.getM_Product_ID());
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = {
					I_PP_Product_Planning.COLUMNNAME_PP_Product_BOMVersions_ID,
					I_PP_Product_Planning.COLUMNNAME_IsAttributeDependant,
					I_PP_Product_Planning.COLUMNNAME_M_AttributeSetInstance_ID,
			})
	public void validateProductBOMASI(@NonNull final I_PP_Product_Planning record)
	{
		final ProductPlanning productPlanning = ProductPlanningDAO.fromRecord(record);

		if (!productPlanning.isAttributeDependant())
		{
			return;
		}

		final ProductBOMVersionsId bomVersionsId = productPlanning.getBomVersionsId();
		if (bomVersionsId == null)
		{
			return;
		}

		Arrays.stream(PPOrderDocBaseType.values())
				.map(docType -> bomDAO.getLatestBOMRecordByVersionAndType(bomVersionsId, docType.getBOMTypes()))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.forEach(bom -> productBOMService.verifyBOMAssignment(productPlanning, bom));
	}
}
