package de.metas.material.planning.impl;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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

import com.google.common.annotations.VisibleForTesting;
import de.metas.material.planning.IMaterialPlanningContext;
import de.metas.material.planning.ProductPlanning;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import lombok.Data;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.service.ClientId;
import org.adempiere.warehouse.WarehouseId;
import org.eevolution.model.I_PP_Product_Planning;

import javax.annotation.Nullable;
import java.util.Properties;

/**
 * It's possible to create instances of this class directly (intended for testing), but generally, please use a factory to do it.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@Data
@VisibleForTesting
public final class MaterialPlanningContext implements IMaterialPlanningContext
{
	private Properties ctx;
	private String trxName;
	@NonNull private ClientId clientId = ClientId.SYSTEM;
	@NonNull private OrgId orgId = OrgId.ANY;  // i.e. all organizations
	private ProductId productId;
	private ProductPlanning productPlanning;
	@Nullable private ResourceId plantId;
	@Nullable private WarehouseId warehouseId;
	@NonNull private AttributeSetInstanceId attributeSetInstanceId = AttributeSetInstanceId.NONE;
	@Nullable private ProductPlanning ppOrderProductPlanning;

	@VisibleForTesting
	public MaterialPlanningContext()
	{
	}

	/**
	 * Returns <code>false</code> because we are going to work a lot on MRP, so we should do it correctly from the start.
	 */
	@Override
	public boolean isAllowThreadInherited()
	{
		return false;
	}

	@Override
	public void assertContextConsistent()
	{
		final ProductId contextProductId = getProductId();
		final ProductId productPlanningProductId = getProductPlanning().getProductId();

		if (!ProductId.equals(contextProductId, productPlanningProductId))
		{
			final String message = String.format("The given IMaterialPlanningContext has M_Product_ID=%s, but its included PP_Product_Planning has M_Product_ID=%s",
					contextProductId, productPlanningProductId);
			throw new AdempiereException(message)
					.appendParametersToMessage()
					.setParameter("IMaterialPlanningContext", this)
					.setParameter("IMaterialPlanningContext.M_Product_ID", contextProductId)
					.setParameter("IMaterialPlanningContext.ProductPlanning", getProductPlanning())
					.setParameter("IMaterialPlanningContext.ProductPlanning.M_Product_ID", productPlanningProductId);
		}
	}
}
