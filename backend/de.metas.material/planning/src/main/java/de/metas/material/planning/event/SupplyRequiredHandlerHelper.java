/*
 * #%L
 * metasfresh-material-planning
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.material.planning.event;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.Profiles;
import de.metas.logging.LogManager;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.IProductPlanningDAO.ProductPlanningQuery;
import de.metas.material.planning.MaterialPlanningContext;
import de.metas.material.planning.PlanningUsage;
import de.metas.material.planning.ProductPlanning;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_M_Warehouse;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Stream;

@Service
@Profile(Profiles.PROFILE_App) // we want only one component to bother itself with SupplyRequiredEvents
@RequiredArgsConstructor
public class SupplyRequiredHandlerHelper
{
	private static final Logger logger = LogManager.getLogger(SupplyRequiredHandlerHelper.class);
	@NonNull private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	@NonNull private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	@NonNull private final IProductPlanningDAO productPlanningDAO = Services.get(IProductPlanningDAO.class);

	@Nullable
	protected MaterialPlanningContext createContextOrNull(@NonNull final SupplyRequiredDescriptor supplyRequiredDescriptor)
	{
		final ResolvedRequest resolved = resolveRequest(supplyRequiredDescriptor);
		if (resolved == null)
		{
			return null;
		}

		final ProductPlanning productPlanning = productPlanningDAO.find(resolved.productPlanningQuery).orElse(null);
		if (productPlanning == null)
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("No PP_Product_Planning record found => nothing to do; query={}", resolved.productPlanningQuery);
			return null;
		}

		return resolved.buildContext(productPlanning, orgDAO);
	}

	/**
	 * Builds one {@link MaterialPlanningContext} per {@link PlanningUsage} for which a matching
	 * {@link ProductPlanning} row exists. Within each usage, the "best" row is selected using the
	 * DAO ordering (SeqNo -> IsAttributeDependant -> specificity -- see {@code ProductPlanningDAO.toSql}).
	 *
	 * A single PP row that matches multiple usages naturally wins all of them; a customer with
	 * two mfg rows gets exactly one manufacturing context (no duplicates).
	 *
	 * @return empty map if the supply cannot be planned at all (no plant, or no matching planning)
	 */
	@NonNull
	public Map<PlanningUsage, MaterialPlanningContext> createContextsByUsage(@NonNull final SupplyRequiredDescriptor supplyRequiredDescriptor)
	{
		final ResolvedRequest resolved = resolveRequest(supplyRequiredDescriptor);
		if (resolved == null)
		{
			return ImmutableMap.of();
		}

		final ImmutableList<ProductPlanning> all;
		try (final Stream<ProductPlanning> stream = productPlanningDAO.query(resolved.productPlanningQuery))
		{
			all = stream.collect(ImmutableList.toImmutableList()); // DAO-ordered
		}
		if (all.isEmpty())
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("No PP_Product_Planning record found => nothing to do; query={}", resolved.productPlanningQuery);
			return ImmutableMap.of();
		}

		final EnumMap<PlanningUsage, MaterialPlanningContext> byUsage = new EnumMap<>(PlanningUsage.class);
		for (final PlanningUsage usage : PlanningUsage.values())
		{
			all.stream()
					.filter(usage::matches)
					.findFirst() // DAO-ordered => first hit is the best match for this usage
					.map(pp -> resolved.buildContext(pp, orgDAO))
					.ifPresent(ctx -> byUsage.put(usage, ctx));
		}
		return byUsage;
	}

	@Nullable
	private ResolvedRequest resolveRequest(@NonNull final SupplyRequiredDescriptor supplyRequiredDescriptor)
	{
		final OrgId orgId = supplyRequiredDescriptor.getOrgId();

		final WarehouseId warehouseId = supplyRequiredDescriptor.getWarehouseId();
		final I_M_Warehouse warehouse = warehouseDAO.getById(warehouseId);

		final ProductId productId = ProductId.ofRepoId(supplyRequiredDescriptor.getProductId());
		final AttributeSetInstanceId attributeSetInstanceId = AttributeSetInstanceId.ofRepoIdOrNone(supplyRequiredDescriptor.getAttributeSetInstanceId());
		final ResourceId plantId = productPlanningDAO.findPlantIfExists(orgId, warehouse, productId, attributeSetInstanceId).orElse(null);
		if (plantId == null)
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("No plant found for {}, {}, {}, {}", orgId, warehouse, productId, attributeSetInstanceId);
			return null;
		}

		final ProductPlanningQuery productPlanningQuery = ProductPlanningQuery.builder()
				.orgId(orgId)
				.warehouseId(warehouseId)
				.plantId(plantId)
				.productId(productId)
				.includeWithNullProductId(false)
				.attributeSetInstanceId(attributeSetInstanceId)
				.build();

		return new ResolvedRequest(orgId, warehouseId, productId, attributeSetInstanceId, plantId, productPlanningQuery);
	}

	@RequiredArgsConstructor
	private static class ResolvedRequest
	{
		@NonNull final OrgId orgId;
		@NonNull final WarehouseId warehouseId;
		@NonNull final ProductId productId;
		@NonNull final AttributeSetInstanceId attributeSetInstanceId;
		@NonNull final ResourceId plantId;
		@NonNull final ProductPlanningQuery productPlanningQuery;

		@NonNull
		MaterialPlanningContext buildContext(
				@NonNull final ProductPlanning productPlanning,
				@NonNull final IOrgDAO orgDAO)
		{
			final I_AD_Org org = orgDAO.getById(orgId);

			return MaterialPlanningContext.builder()
					.productId(productId)
					.attributeSetInstanceId(attributeSetInstanceId)
					.warehouseId(warehouseId)
					.productPlanning(productPlanning)
					.plantId(plantId)
					.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(org.getAD_Client_ID(), org.getAD_Org_ID()))
					.build();
		}
	}
}