/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.workplace;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import de.metas.cache.CCache;
import de.metas.externalsystem.ExternalSystemId;
import de.metas.inout.PriorityRule;
import de.metas.order.OrderPickingType;
import de.metas.picking.api.PickingSlotId;

import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.shipping.CarrierProductId;
import de.metas.util.Services;
import de.metas.util.lang.SeqNo;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.Adempiere;
import org.compiere.model.I_C_Workplace;
import org.compiere.model.I_C_Workplace_Carrier_Product;
import org.compiere.model.I_C_Workplace_ExternalSystem;
import org.compiere.model.I_C_Workplace_Product;
import org.compiere.model.I_C_Workplace_ProductCategory;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Repository
public class WorkplaceRepository
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final CCache<Integer, WorkplacesMap> cache = CCache.<Integer, WorkplacesMap>builder()
			.tableName(I_C_Workplace.Table_Name)
			.additionalTableNameToResetFor(I_C_Workplace_Product.Table_Name)
			.additionalTableNameToResetFor(I_C_Workplace_ProductCategory.Table_Name)
			.additionalTableNameToResetFor(I_C_Workplace_Carrier_Product.Table_Name)
			.additionalTableNameToResetFor(I_C_Workplace_ExternalSystem.Table_Name)
			.build();

	public static WorkplaceRepository newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		return new WorkplaceRepository();
	}


	@NonNull
	public Workplace getById(@NonNull final WorkplaceId id)
	{
		return getMap().getById(id);
	}

	@NonNull
	public Collection<Workplace> getByIds(final Collection<WorkplaceId> ids)
	{
		return getMap().getByIds(ids);
	}

	public boolean isAnyWorkplaceActive()
	{
		return !getMap().isEmpty();
	}

	public List<Workplace> getAllActive() {return getMap().getAllActive();}

	public Workplace create(@NonNull final WorkplaceCreateRequest request)
	{
		if (request.getPickFromLocatorId() != null && !WarehouseId.equals(request.getWarehouseId(), request.getPickFromLocatorId().getWarehouseId()))
		{
			throw new AdempiereException("PickFromLocatorId and WarehouseId must be from the same warehouse")
					.setParameter("request", request);
		}

		final I_C_Workplace record = InterfaceWrapperHelper.newInstance(I_C_Workplace.class);
		record.setName(request.getName());
		record.setM_Warehouse_ID(request.getWarehouseId().getRepoId());
		record.setPickFrom_Locator_ID(LocatorId.toRepoId(request.getPickFromLocatorId()));
		record.setM_PickingSlot_ID(PickingSlotId.toRepoId(request.getPickingSlotId()));
		record.setMaxPickingJobs(request.getMaxPickingJobs());

		final SeqNo seqNo = request.getSeqNo() != null ? request.getSeqNo() : getMap().getNextSeqNo();
		record.setSeqNo(seqNo.toInt());

		if(request.getOrderPickingType() != null)
		{
			record.setOrderPickingType(request.getOrderPickingType().getCode());
		}

		InterfaceWrapperHelper.save(record);
		
		if(!request.getProductIds().isEmpty())
		{
			request.getProductIds().forEach(productId -> createProducts(productId, record));
		}

		if(!request.getProductCategoryIds().isEmpty())
		{
			request.getProductCategoryIds().forEach(categoryId -> createProductCategory(categoryId, record));
		}

		if(!request.getCarrierProductIds().isEmpty())
		{
			request.getCarrierProductIds().forEach(carrierProductId -> createCarrierProduct(carrierProductId, record));
		}

		if(!request.getExternalSystemIds().isEmpty())
		{
			request.getExternalSystemIds().forEach(externalSystemId -> createExternalSystem(externalSystemId, record));
		}

		return getById(WorkplaceId.ofRepoId(record.getC_Workplace_ID()));
	}
	
	private void createProducts(@NonNull final ProductId productId, @NonNull final I_C_Workplace workplace)
	{
		final I_C_Workplace_Product product = InterfaceWrapperHelper.newInstance(I_C_Workplace_Product.class);
		product.setM_Product_ID(productId.getRepoId());
		product.setC_Workplace_ID(workplace.getC_Workplace_ID());
		product.setAD_Org_ID(workplace.getAD_Org_ID());
		InterfaceWrapperHelper.save(product);
	}

	private void createProductCategory(@NonNull final ProductCategoryId productCategoryId, @NonNull final I_C_Workplace workplace)
	{
		final I_C_Workplace_ProductCategory record = InterfaceWrapperHelper.newInstance(I_C_Workplace_ProductCategory.class);
		record.setM_Product_Category_ID(productCategoryId.getRepoId());
		record.setC_Workplace_ID(workplace.getC_Workplace_ID());
		record.setAD_Org_ID(workplace.getAD_Org_ID());
		InterfaceWrapperHelper.save(record);
	}

	private void createCarrierProduct(@NonNull final CarrierProductId carrierProductId, @NonNull final I_C_Workplace workplace)
	{
		final I_C_Workplace_Carrier_Product record = InterfaceWrapperHelper.newInstance(I_C_Workplace_Carrier_Product.class);
		record.setCarrier_Product_ID(carrierProductId.getRepoId());
		record.setC_Workplace_ID(workplace.getC_Workplace_ID());
		record.setAD_Org_ID(workplace.getAD_Org_ID());
		InterfaceWrapperHelper.save(record);
	}

	private void createExternalSystem(@NonNull final ExternalSystemId externalSystemId, @NonNull final I_C_Workplace workplace)
	{
		final I_C_Workplace_ExternalSystem record = InterfaceWrapperHelper.newInstance(I_C_Workplace_ExternalSystem.class);
		record.setExternalSystem_ID(externalSystemId.getRepoId());
		record.setC_Workplace_ID(workplace.getC_Workplace_ID());
		record.setAD_Org_ID(workplace.getAD_Org_ID());
		InterfaceWrapperHelper.save(record);
	}

	@NonNull
	private WorkplacesMap getMap()
	{
		return cache.getOrLoadNonNull(0, this::retrieveMap);
	}

	@NonNull
	private WorkplacesMap retrieveMap()
	{
		final ImmutableList<I_C_Workplace> workplaceRecords = queryBL.createQueryBuilder(I_C_Workplace.class)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_C_Workplace.COLUMN_SeqNo)
				.create()
				.stream().collect(ImmutableList.toImmutableList());

		if (workplaceRecords.isEmpty())
		{
			return new WorkplacesMap(ImmutableList.of());
		}

		final Set<WorkplaceId> workplaceIds = workplaceRecords.stream()
				.map(record -> WorkplaceId.ofRepoId(record.getC_Workplace_ID()))
				.collect(ImmutableSet.toImmutableSet());

		final Multimap<WorkplaceId, ProductId> productsByWorkplace = loadProducts(workplaceIds);
		final Multimap<WorkplaceId, ProductCategoryId> categoriesByWorkplace = loadProductCategories(workplaceIds);
		final Multimap<WorkplaceId, CarrierProductId> carrierProductsByWorkplace = loadCarrierProducts(workplaceIds);
		final Multimap<WorkplaceId, ExternalSystemId> externalSystemsByWorkplace = loadExternalSystems(workplaceIds);

		final ImmutableList<Workplace> list = workplaceRecords.stream()
				.map(record -> fromRecord(
						record,
						productsByWorkplace,
						categoriesByWorkplace,
						carrierProductsByWorkplace,
						externalSystemsByWorkplace
				))
				.collect(ImmutableList.toImmutableList());

		return new WorkplacesMap(list);
	}

	private Multimap<WorkplaceId, ProductId> loadProducts(@NonNull final Set<WorkplaceId> workplaceIds)
	{
		return queryBL.createQueryBuilder(I_C_Workplace_Product.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_Workplace_Product.COLUMNNAME_C_Workplace_ID, workplaceIds)
				.create()
				.stream()
				.collect(Multimaps.toMultimap(
						record -> WorkplaceId.ofRepoId(record.getC_Workplace_ID()),
						record -> ProductId.ofRepoId(record.getM_Product_ID()),
						HashMultimap::create
				));
	}

	private Multimap<WorkplaceId, ProductCategoryId> loadProductCategories(@NonNull final Set<WorkplaceId> workplaceIds)
	{
		return queryBL.createQueryBuilder(I_C_Workplace_ProductCategory.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_Workplace_ProductCategory.COLUMNNAME_C_Workplace_ID, workplaceIds)
				.create()
				.stream()
				.collect(Multimaps.toMultimap(
						record -> WorkplaceId.ofRepoId(record.getC_Workplace_ID()),
						record -> ProductCategoryId.ofRepoId(record.getM_Product_Category_ID()),
						HashMultimap::create
				));
	}

	private Multimap<WorkplaceId, CarrierProductId> loadCarrierProducts(@NonNull final Set<WorkplaceId> workplaceIds)
	{
		return queryBL.createQueryBuilder(I_C_Workplace_Carrier_Product.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_Workplace_Carrier_Product.COLUMNNAME_C_Workplace_ID, workplaceIds)
				.create()
				.stream()
				.collect(Multimaps.toMultimap(
						record -> WorkplaceId.ofRepoId(record.getC_Workplace_ID()),
						record -> CarrierProductId.ofRepoId(record.getCarrier_Product_ID()),
						HashMultimap::create
				));
	}

	private Multimap<WorkplaceId, ExternalSystemId> loadExternalSystems(@NonNull final Set<WorkplaceId> workplaceIds)
	{
		return queryBL.createQueryBuilder(I_C_Workplace_ExternalSystem.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_Workplace_ExternalSystem.COLUMNNAME_C_Workplace_ID, workplaceIds)
				.create()
				.stream()
				.collect(Multimaps.toMultimap(
						record -> WorkplaceId.ofRepoId(record.getC_Workplace_ID()),
						record -> ExternalSystemId.ofRepoId(record.getExternalSystem_ID()),
						HashMultimap::create
				));
	}

	@NonNull
	private static Workplace fromRecord(
			@NonNull final I_C_Workplace record,
			@NonNull final Multimap<WorkplaceId, ProductId> productsByWorkplace,
			@NonNull final Multimap<WorkplaceId, ProductCategoryId> categoriesByWorkplace,
			@NonNull final Multimap<WorkplaceId, CarrierProductId> carrierProductsByWorkplace,
			@NonNull final Multimap<WorkplaceId, ExternalSystemId> externalSystemsByWorkplace)
	{
		final WarehouseId warehouseId = WarehouseId.ofRepoId(record.getM_Warehouse_ID());
		final WorkplaceId workplaceId = WorkplaceId.ofRepoId(record.getC_Workplace_ID());

		return Workplace.builder()
				.id(workplaceId)
				.name(record.getName())
				.warehouseId(warehouseId)
				.pickFromLocatorId(LocatorId.ofRepoIdOrNull(warehouseId, record.getPickFrom_Locator_ID()))
				.pickingSlotId(PickingSlotId.ofRepoIdOrNull(record.getM_PickingSlot_ID()))
				.seqNo(SeqNo.ofInt(record.getSeqNo()))
				.priorityRule(PriorityRule.ofNullableCode(record.getPriorityRule()))
				.orderPickingType(OrderPickingType.ofNullableCode(record.getOrderPickingType()))
				.maxPickingJobs(record.getMaxPickingJobs())
				// Add child collections
				.productIds(ImmutableSet.copyOf(productsByWorkplace.get(workplaceId)))
				.productCategoryIds(ImmutableSet.copyOf(categoriesByWorkplace.get(workplaceId)))
				.carrierProductIds(ImmutableSet.copyOf(carrierProductsByWorkplace.get(workplaceId)))
				.externalSystemIds(ImmutableSet.copyOf(externalSystemsByWorkplace.get(workplaceId)))
				.build();
	}

	//
	//
	//
	//
	//

	private static class WorkplacesMap
	{
		private final ImmutableMap<WorkplaceId, Workplace> byId;
		@Getter private final ImmutableList<Workplace> allActive;

		WorkplacesMap(final List<Workplace> list)
		{
			this.byId = Maps.uniqueIndex(list, Workplace::getId);
			this.allActive = ImmutableList.copyOf(list);
		}

		@NonNull
		public Workplace getById(final WorkplaceId id)
		{
			final Workplace workplace = byId.get(id);
			if (workplace == null)
			{
				throw new AdempiereException("No workplace found for " + id);
			}
			return workplace;
		}

		public Collection<Workplace> getByIds(final Collection<WorkplaceId> ids)
		{
			if (ids.isEmpty())
			{
				return ImmutableList.of();
			}

			return ids.stream()
					.map(this::getById)
					.collect(ImmutableList.toImmutableList());
		}

		public boolean isEmpty()
		{
			return byId.isEmpty();
		}

		public SeqNo getNextSeqNo()
		{
			final Workplace workplace = getAllActive().stream().max(Comparator.comparing(v -> v.getSeqNo().toInt())).orElse(null);
			return workplace != null ? workplace.getSeqNo().next() : SeqNo.ofInt(10);
		}
	}
}
