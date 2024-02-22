package org.eevolution.mrp.api.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.compiere.model.X_C_BP_Group;
import org.compiere.model.X_C_BPartner;
import org.compiere.model.X_M_Product;
import org.compiere.model.X_M_Warehouse;
import org.compiere.model.X_S_Resource;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.mrp.api.IMRPQueryBuilder;
import org.eevolution.mrp.api.MRPFirmType;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* package */class MRPQueryBuilder implements IMRPQueryBuilder
{
	// services
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private Object _contextProvider = null;

	//
	// Planning segment
	private Integer _adClientId = null;
	private Integer _adOrgId = null;
	private Integer _ppPlantId = null;
	private Integer _warehouseId = null;
	/**
	 * M_Product_ID
	 *
	 * Default: -1 i.e. don't filter by product, don't take it from MRPContext
	 */
	private Integer _productId = -1;

	private String _typeMRP = null;
	private final Date _datePromisedMax = null;
	private final MRPFirmType _mrpFirmType = null;
	private final Boolean _mrpAvailable = null;
	private final Set<String> _orderTypes = new HashSet<>();
	private Object _referencedModel = null;
	private boolean _ppOrderBOMLine_Null = false;

	/**
	 * Filter only active records
	 *
	 * Default: Yes
	 */
	private boolean _onlyActiveRecords = true;

	public MRPQueryBuilder()
	{
		super();
	}

	private IQueryFilter<I_PP_MRP> createQueryFilter()
	{
		final IContextAware contextProvider = getContextProviderToUse();
		final ICompositeQueryFilter<I_PP_MRP> filters = queryBL.createCompositeQueryFilter(I_PP_MRP.class);

		//
		// Filter only Active records
		final boolean onlyActiveRecords = isOnlyActiveRecords();
		if (onlyActiveRecords)
		{
			filters.addOnlyActiveRecordsFilter();
		}

		//
		// Filter by AD_Client_ID
		final int adClientId = getAD_Client_ID_ToUse();
		if (adClientId >= 0)
		{
			filters.addEqualsFilter(I_PP_MRP.COLUMNNAME_AD_Client_ID, adClientId);
		}

		//
		// Filter by AD_Org_ID
		final int adOrgId = getAD_Org_ID_ToUse();
		if (adOrgId >= 0)
		{
			filters.addEqualsFilter(I_PP_MRP.COLUMNNAME_AD_Org_ID, adOrgId);
		}

		//
		// Filter by M_Warehouse_ID
		final int warehouseId = getM_Warehouse_ID_ToUse();
		if (warehouseId > 0)
		{
			filters.addEqualsFilter(I_PP_MRP.COLUMNNAME_M_Warehouse_ID, warehouseId);
		}

		//
		// Filter by Plant (only those lines which have our plant ID or don't have the plant ID set at all)
		// AIM: Clear separation on plant level (06594)
		final Set<Integer> plantIds = getPP_Plant_IDs_ToUse();
		if (!plantIds.isEmpty())
		{
			filters.addInArrayOrAllFilter(I_PP_MRP.COLUMNNAME_S_Resource_ID, plantIds);
		}

		//
		// Filter by M_Product_ID
		final int productId = getM_Product_ID_ToUse();
		if (productId > 0)
		{
			filters.addEqualsFilter(I_PP_MRP.COLUMNNAME_M_Product_ID, productId);
		}

		//
		// Filter by TypeMRP
		final String typeMRP = getTypeMRP();
		if (!Check.isEmpty(typeMRP, true))
		{
			filters.addEqualsFilter(I_PP_MRP.COLUMNNAME_TypeMRP, typeMRP);
		}

		//
		// Filter by MRPFirmType (i.e. DocStatus)
		final MRPFirmType mrpFirmType = getMRPFirmType();
		if (mrpFirmType != null)
		{
			final List<String> docStatuses = mrpFirmType.getDocStatuses();
			filters.addInArrayOrAllFilter(I_PP_MRP.COLUMNNAME_DocStatus, docStatuses);
		}

		//
		// Filter by OrderType (i.e. similar with DocBaseType)
		final Set<String> orderTypes = getOrderTypes();
		if (!Check.isEmpty(orderTypes))
		{
			filters.addInArrayOrAllFilter(I_PP_MRP.COLUMNNAME_OrderType, orderTypes);
		}

		//
		// Filter by DatePromised
		final Date datePromisedMax = getDatePromisedMax();
		if (datePromisedMax != null)
		{
			filters.addCompareFilter(I_PP_MRP.COLUMNNAME_DatePromised, Operator.LESS_OR_EQUAL, datePromisedMax);
		}

		//
		// Filter Non Zero Qty
		final boolean qtyNotZero = isQtyNotZero();
		if (qtyNotZero)
		{
			filters.addNotEqualsFilter(I_PP_MRP.COLUMNNAME_Qty, BigDecimal.ZERO);
		}

		//
		// Filter by IsAvailable flag
		final Boolean mrpAvailable = getMRPAvailable();
		if (mrpAvailable != null)
		{
			filters.addEqualsFilter(I_PP_MRP.COLUMNNAME_IsAvailable, mrpAvailable);
		}

		//
		// Filter by Referenced Model
		// i.e. Only those MRP records which are referencing our model
		final Object referencedModel = getReferencedModel();
		if (referencedModel != null)
		{
			final String modelTableName = InterfaceWrapperHelper.getModelTableName(referencedModel);
			final String modelKeyColumnName = modelTableName + "_ID";
			final int modelId = InterfaceWrapperHelper.getId(referencedModel);
			filters.addEqualsFilter(modelKeyColumnName, modelId);
		}

		//
		// Filter only those MRP lines where PP_Product_BOMLine_ID is not set
		if (_ppOrderBOMLine_Null)
		{
			filters.addEqualsFilter(I_PP_MRP.COLUMN_PP_Order_BOMLine_ID, null);
		}

		//
		// Apply MRP_Exclude filters
		//
		//Shall we skip MRP records which are excluded by MRP_Exclude option (on BPartner, Product etc).
		//
		final IQueryFilter<I_PP_MRP> skipIfMRPExcludedFilters = createQueryFilter_MRP_Exclude();
		filters.addFilter(skipIfMRPExcludedFilters);

		//
		// Return built filters
		return filters;
	}

	/**
	 * Called by {@link #createQueryFilter()} to create the MRP_Exclude filter.
	 *
	 * @return MRP_Exclude filter
	 */
	private IQueryFilter<I_PP_MRP> createQueryFilter_MRP_Exclude()
	{
		final IContextAware contextProvider = getContextProviderToUse();
		final ICompositeQueryFilter<I_PP_MRP> filters = queryBL.createCompositeQueryFilter(I_PP_MRP.class);

		// Exclude BPartners
		{
			final IQuery<I_C_BP_Group> excludedBPGroupsQuery = queryBL.createQueryBuilder(I_C_BP_Group.class, contextProvider)
					.addEqualsFilter(I_C_BP_Group.COLUMN_MRP_Exclude, X_C_BP_Group.MRP_EXCLUDE_Yes)
					.create();

			final IQuery<I_C_BPartner> excludedBPartnersQuery = queryBL.createQueryBuilder(I_C_BPartner.class, contextProvider)
					.setJoinOr()
					.addEqualsFilter(I_C_BPartner.COLUMN_MRP_Exclude, X_C_BPartner.MRP_EXCLUDE_Yes)
					.addInSubQueryFilter(I_C_BPartner.COLUMNNAME_C_BP_Group_ID, I_C_BP_Group.COLUMNNAME_C_BP_Group_ID, excludedBPGroupsQuery)
					.create();

			filters.addNotInSubQueryFilter(I_PP_MRP.COLUMNNAME_C_BPartner_ID, I_C_BPartner.COLUMNNAME_C_BPartner_ID, excludedBPartnersQuery);
		}
		// Exclude Products
		{
			final IQuery<I_M_Product_Category> excludedProductCategoriesQuery = queryBL.createQueryBuilder(I_M_Product_Category.class, contextProvider)
					.addEqualsFilter(I_M_Product_Category.COLUMN_MRP_Exclude, X_C_BP_Group.MRP_EXCLUDE_Yes)
					.create();

			final IQuery<I_M_Product> excludedProductsQuery = queryBL.createQueryBuilder(I_M_Product.class, contextProvider)
					.setJoinOr()
					.addEqualsFilter(I_M_Product.COLUMN_MRP_Exclude, X_M_Product.MRP_EXCLUDE_Yes)
					.addInSubQueryFilter(I_M_Product.COLUMNNAME_M_Product_Category_ID, I_M_Product_Category.COLUMNNAME_M_Product_Category_ID, excludedProductCategoriesQuery)
					.create();
			filters.addNotInSubQueryFilter(I_PP_MRP.COLUMNNAME_M_Product_ID, I_M_Product.COLUMNNAME_M_Product_ID, excludedProductsQuery);
		}
		// Exclude Warehouses
		{
			final IQuery<I_M_Warehouse> excludedWarehousesQuery = queryBL.createQueryBuilder(I_M_Warehouse.class, contextProvider)
					.addEqualsFilter(I_M_Warehouse.COLUMN_MRP_Exclude, X_M_Warehouse.MRP_EXCLUDE_Yes)
					.create();
			filters.addNotInSubQueryFilter(I_PP_MRP.COLUMNNAME_M_Warehouse_ID, I_M_Warehouse.COLUMNNAME_M_Warehouse_ID, excludedWarehousesQuery);
		}
		// Exclude Plants
		{
			final IQuery<I_S_Resource> excludedPlantsQuery = queryBL.createQueryBuilder(I_S_Resource.class, contextProvider)
					.addEqualsFilter(I_S_Resource.COLUMN_MRP_Exclude, X_S_Resource.MRP_EXCLUDE_Yes)
					.create();
			filters.addNotInSubQueryFilter(I_PP_MRP.COLUMNNAME_S_Resource_ID, I_S_Resource.COLUMNNAME_S_Resource_ID, excludedPlantsQuery);
		}

		return filters;
	}

	@Override
	public IQueryBuilder<I_PP_MRP> createQueryBuilder()
	{
		final IQueryFilter<I_PP_MRP> filters = createQueryFilter();

		return queryBL.createQueryBuilder(I_PP_MRP.class, getContextProviderToUse())
				.filter(filters);
	}

	@Override
	public MRPQueryBuilder setContextProvider(final Object contextProvider)
	{
		this._contextProvider = contextProvider;
		return this;
	}

	private IContextAware getContextProviderToUse()
	{
		if (_contextProvider != null)
		{
			return InterfaceWrapperHelper.getContextAware(_contextProvider);
		}

		return PlainContextAware.newWithThreadInheritedTrx();
	}

	@Override
	public MRPQueryBuilder setAD_Client_ID(final Integer adClientId)
	{
		this._adClientId = adClientId;
		return this;
	}

	private int getAD_Client_ID_ToUse()
	{
		if (_adClientId != null)
		{
			return _adClientId;
		}

		return -1;
	}

	@Override
	public MRPQueryBuilder setAD_Org_ID(final Integer adOrgId)
	{
		this._adOrgId = adOrgId;
		return this;
	}

	private int getAD_Org_ID_ToUse()
	{
		if (_adOrgId != null)
		{
			return _adOrgId;
		}

		return -1;
	}

	@Override
	public MRPQueryBuilder setM_Warehouse_ID(final Integer warehouseId)
	{
		this._warehouseId = warehouseId;
		return this;
	}

	private int getM_Warehouse_ID_ToUse()
	{
		if (_warehouseId != null)
		{
			return _warehouseId;
		}

		return -1;
	}

	@Override
	public MRPQueryBuilder setPP_Plant_ID(final Integer ppPlantId)
	{
		this._ppPlantId = ppPlantId;
		return this;
	}

	private Set<Integer> getPP_Plant_IDs_ToUse()
	{
		int ppPlantId = -1;
		if (_ppPlantId != null)
		{
			ppPlantId = _ppPlantId;
		}

		if (ppPlantId <= 0)
		{
			return Collections.emptySet();
		}

		final Set<Integer> plantIds = new HashSet<>();
		plantIds.add(ppPlantId);
		plantIds.add(null);

		return plantIds;
	}

	@Override
	public MRPQueryBuilder setM_Product_ID(final Integer productId)
	{
		this._productId = productId;
		return this;
	}

	private int getM_Product_ID_ToUse()
	{
		if (_productId != null)
		{
			return _productId;
		}

		return -1;
	}

	@Override
	public MRPQueryBuilder setTypeMRP(final String typeMRP)
	{
		this._typeMRP = typeMRP;
		return this;
	}

	public String getTypeMRP()
	{
		return _typeMRP;
	}

	@Nullable
	public Date getDatePromisedMax()
	{
		return this._datePromisedMax;
	}

	@Nullable
	public MRPFirmType getMRPFirmType()
	{
		return this._mrpFirmType;
	}

	public boolean isQtyNotZero()
	{
		return false;
	}

	@Nullable
	public Boolean getMRPAvailable()
	{
		return _mrpAvailable;
	}

	public boolean isOnlyActiveRecords()
	{
		return _onlyActiveRecords;
	}

	@Override
	public MRPQueryBuilder setOnlyActiveRecords(final boolean onlyActiveRecords)
	{
		this._onlyActiveRecords = onlyActiveRecords;
		return this;
	}

	@Override
	public MRPQueryBuilder setOrderType(final String orderType)
	{
		this._orderTypes.clear();
		if (orderType != null)
		{
			this._orderTypes.add(orderType);
		}
		return this;
	}

	private Set<String> getOrderTypes()
	{
		return this._orderTypes;
	}

	@Override
	public MRPQueryBuilder setReferencedModel(final Object referencedModel)
	{
		this._referencedModel = referencedModel;
		return this;
	}

	public Object getReferencedModel()
	{
		return this._referencedModel;
	}

	@Override
	public void setPP_Order_BOMLine_Null()
	{
		this._ppOrderBOMLine_Null = true;
	}
}
