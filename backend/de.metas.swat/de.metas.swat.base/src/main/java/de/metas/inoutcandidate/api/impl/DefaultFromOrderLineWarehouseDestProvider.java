package de.metas.inoutcandidate.api.impl;

import de.metas.inoutcandidate.spi.IReceiptScheduleWarehouseDestProvider;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.IProductPlanningDAO.ProductPlanningQuery;
import de.metas.material.planning.ProductPlanning;
import de.metas.material.planning.ddorder.DistributionNetwork;
import de.metas.material.planning.ddorder.DistributionNetworkLine;
import de.metas.material.planning.ddorder.DistributionNetworkRepository;
import de.metas.organization.OrgId;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.util.Services;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * Default destination warehouse provider.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
/* package */final class DefaultFromOrderLineWarehouseDestProvider implements IReceiptScheduleWarehouseDestProvider
{
	public static final DefaultFromOrderLineWarehouseDestProvider instance = new DefaultFromOrderLineWarehouseDestProvider();

	private DefaultFromOrderLineWarehouseDestProvider()
	{
		super();
	}

	@Override
	public Optional<WarehouseId> getWarehouseDest(final IContext context)
	{
		final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
		//
		// Try to retrieve destination warehouse from planning
		// see: http://dewiki908/mediawiki/index.php/07058_Destination_Warehouse_Wareneingang_%28102083181965%29#Development_infrastructure
		final WarehouseId distributionNetworkWarehouseDestination = getDistributionNetworkWarehouseDestination(context);
		if (distributionNetworkWarehouseDestination != null)
		{
			return Optional.of(distributionNetworkWarehouseDestination);
		}

		//
		// Fallback if no planning destination warehouse was found
		// see: http://dewiki908/mediawiki/index.php/05940_Wareneingang_Lagerumbuchung
		final I_M_Product product = Services.get(IProductDAO.class).getById(context.getM_Product_ID());
		final LocatorId locatorId = warehouseDAO.getLocatorIdByRepoIdOrNull(product.getM_Locator_ID());
		final I_M_Locator locator = locatorId == null ? null : warehouseDAO.getLocatorById(locatorId);
		if (locator != null && locator.getM_Locator_ID() > 0)
		{
			return WarehouseId.optionalOfRepoId(locator.getM_Warehouse_ID());
		}

		//
		// We don't have anything to match
		return Optional.empty();
	}

	/**
	 * @return first planning-distribution (i.e. with lowest <code>PriorityNo</code>) network-warehouse destination found for product (no warehouse / source filter).
	 */
	@Nullable
	private WarehouseId getDistributionNetworkWarehouseDestination(final IContext context)
	{
		final int attributeSetInstanceId = context.getM_AttributeSetInstance() == null
				? AttributeConstants.M_AttributeSetInstance_ID_None
				: context.getM_AttributeSetInstance().getM_AttributeSetInstance_ID();

		final IProductPlanningDAO productPlanningDAO = Services.get(IProductPlanningDAO.class);
		final ProductPlanningQuery query = ProductPlanningQuery.builder()
				.orgId(OrgId.ofRepoId(context.getAD_Org_ID()))
				.productId(ProductId.ofRepoId(context.getM_Product_ID()))
				.attributeSetInstanceId(AttributeSetInstanceId.ofRepoId(attributeSetInstanceId))
				// no warehouse, no plant
				.build();

		final ProductPlanning productPlanning = productPlanningDAO.find(query).orElse(null);
		if (productPlanning == null)
		{
			return null;
		}

		if (productPlanning.getDistributionNetworkId() == null)
		{
			return null;
		}

		final DistributionNetworkRepository distributionNetworkRepository = SpringContextHolder.instance.getBean(DistributionNetworkRepository.class);
		final DistributionNetwork distributionNetwork = distributionNetworkRepository.getById(productPlanning.getDistributionNetworkId());
		final List<DistributionNetworkLine> distributionNetworkLines = distributionNetwork.getLinesBySourceWarehouse(WarehouseId.ofRepoId(context.getM_Warehouse_ID()));

		if (distributionNetworkLines.isEmpty())
		{
			return null;
		}

		// the lines are ordered by PriorityNo, M_Shipper_ID
		final DistributionNetworkLine firstFoundDistributionNetworkLine = distributionNetworkLines.get(0);
		return firstFoundDistributionNetworkLine.getTargetWarehouseId();
	}

}
