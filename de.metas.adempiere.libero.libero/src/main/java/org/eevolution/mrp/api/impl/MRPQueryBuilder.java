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


import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.IQueryUpdater;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_Org;
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
import org.compiere.util.Env;
import org.eevolution.exceptions.LiberoException;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.model.I_PP_MRP_Alloc;
import org.eevolution.model.X_PP_MRP;
import org.eevolution.mrp.api.IMRPContext;
import org.eevolution.mrp.api.IMRPDAO;
import org.eevolution.mrp.api.IMRPQueryBuilder;
import org.eevolution.mrp.api.MRPFirmType;

/* package */class MRPQueryBuilder implements IMRPQueryBuilder
{
	// services
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final transient IMRPDAO mrpDAO = Services.get(IMRPDAO.class);

	private Object _contextProvider = null;
	private IMRPContext _mrpContext = null;

	//
	// Planning segment
	private Integer _adClientId = null;
	private Integer _adOrgId = null;
	private Integer _ppPlantId = null;
	private boolean _acceptNoPlant = true;
	private Integer _warehouseId = null;
	/**
	 * M_Product_ID
	 * 
	 * Default: -1 i.e. don't filter by product, don't take it from MRPContext
	 */
	private Integer _productId = -1;

	//
	// Other MRP records dependency filters
	private Set<Integer> _onlyPP_MRP_IDs = null;
	private int _enforced_PP_MRP_Demand_ID;

	private String _typeMRP = null;
	private int _productLLC = -1;
	private Date _datePromisedMax = null;
	private MRPFirmType _mrpFirmType = null;
	private boolean _qtyNotZero = false;
	private Boolean _mrpAvailable = null;
	private final Set<String> _orderTypes = new HashSet<>();
	private Object _referencedModel = null;
	private boolean _ppOrderBOMLine_Null = false;

	/**
	 * Filter only active records
	 * 
	 * Default: Yes
	 */
	private boolean _onlyActiveRecords = true;

	/**
	 * Shall we skip MRP records which are excluded by MRP_Exclude option (on BPartner, Product etc).
	 */
	private boolean _skipIfMRPExcluded = true;

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
		if (plantIds != null && !plantIds.isEmpty())
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
		// Filter by Product's Low Level Code (LLC)
		final int productLLC = getLowLevelCode();
		if (productLLC >= 0)
		{
			final IQuery<I_M_Product> productQuery = queryBL
					.createQueryBuilder(I_M_Product.class, contextProvider)
					.filter(new EqualsQueryFilter<I_M_Product>(I_M_Product.COLUMNNAME_LowLevel, productLLC))
					.create();
			filters.addInSubQueryFilter(I_PP_MRP.COLUMNNAME_M_Product_ID, I_M_Product.COLUMNNAME_M_Product_ID, productQuery);
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
		// Filter by specific PP_MRP_IDs
		final Set<Integer> mrpIds = getOnlyPP_MRP_IDs();
		if (mrpIds != null && !mrpIds.isEmpty())
		{
			filters.addInArrayOrAllFilter(I_PP_MRP.COLUMNNAME_PP_MRP_ID, mrpIds);
		}

		//
		// Filter by enforced MRP demand, i.e.
		// * accept only MRP demand records which have the enforced MRP Demand ID
		// * or accept only MRP supply records which are allocated to the enforced MRP Demand ID
		if (_enforced_PP_MRP_Demand_ID > 0)
		{
			final ICompositeQueryFilter<I_PP_MRP> filterMRPDemands = queryBL.createCompositeQueryFilter(I_PP_MRP.class)
					.setJoinAnd()
					.addEqualsFilter(I_PP_MRP.COLUMNNAME_TypeMRP, X_PP_MRP.TYPEMRP_Demand)
					.addEqualsFilter(I_PP_MRP.COLUMNNAME_PP_MRP_ID, _enforced_PP_MRP_Demand_ID);

			final IQuery<I_PP_MRP_Alloc> mrpSuppliesSubQuery = queryBL.createQueryBuilder(I_PP_MRP.class, contextProvider)
					.filter(filterMRPDemands)
					.andCollectChildren(I_PP_MRP_Alloc.COLUMN_PP_MRP_Demand_ID, I_PP_MRP_Alloc.class)
					.create();
			final ICompositeQueryFilter<I_PP_MRP> filterMRPSupplies = queryBL.createCompositeQueryFilter(I_PP_MRP.class)
					.setJoinAnd()
					.addEqualsFilter(I_PP_MRP.COLUMNNAME_TypeMRP, X_PP_MRP.TYPEMRP_Supply)
					.addInSubQueryFilter(I_PP_MRP.COLUMN_PP_MRP_ID,
							I_PP_MRP_Alloc.COLUMN_PP_MRP_Supply_ID,
							mrpSuppliesSubQuery);

			final ICompositeQueryFilter<I_PP_MRP> filterEnforcedMRPDemand = queryBL.createCompositeQueryFilter(I_PP_MRP.class)
					.setJoinOr()
					.addFilter(filterMRPDemands)
					.addFilter(filterMRPSupplies);
			filters.addFilter(filterEnforcedMRPDemand);
		}

		//
		// Apply MRP_Exclude filters
		if (_skipIfMRPExcluded)
		{
			final IQueryFilter<I_PP_MRP> skipIfMRPExcludedFilters = createQueryFilter_MRP_Exclude();
			filters.addFilter(skipIfMRPExcludedFilters);
		}

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
		final IQueryBuilder<I_PP_MRP> queryBuilder = queryBL.createQueryBuilder(I_PP_MRP.class, getContextProviderToUse())
				.filter(filters);

		return queryBuilder;
	}

	@Override
	public BigDecimal calculateQtySUM()
	{
		final BigDecimal qty = createQueryBuilder()
				// create query
				.create()
				// Sum UP the Qty
				.aggregate(I_PP_MRP.COLUMNNAME_Qty, IQuery.AGGREGATE_SUM, BigDecimal.class);

		if (qty == null)
		{
			return Env.ZERO;
		}
		return qty;
	}

	@Override
	public int deleteMRPRecords()
	{
		final IQueryBuilder<I_PP_MRP> mrpsQuery = createQueryBuilder();
		return mrpDAO.deleteMRP(mrpsQuery);
	}

	@Override
	public int updateMRPRecords(IQueryUpdater<I_PP_MRP> queryUpdater)
	{
		return createQueryBuilder()
				.create() // create query
				.updateDirectly(queryUpdater);
	}

	@Override
	public int updateMRPRecordsAndMarkAvailable()
	{
		// Updater
		final IQueryUpdater<I_PP_MRP> setAvailableUpdater = queryBL.createCompositeQueryUpdater(I_PP_MRP.class)
				.addSetColumnValue(I_PP_MRP.COLUMNNAME_IsAvailable, true);

		return updateMRPRecords(setAvailableUpdater);
	}

	@Override
	public final I_PP_MRP firstOnly()
	{
		return createQueryBuilder()
				.create()
				.firstOnly(I_PP_MRP.class);
	}

	@Override
	public final List<I_PP_MRP> list()
	{
		return createQueryBuilder()
				.create()
				.list(I_PP_MRP.class);
	}

	@Override
	public MRPQueryBuilder clear()
	{
		// _contextProvider = null;
		// _mrpContext = null;

		//
		// Planning segment
		_adClientId = null;
		_adOrgId = null;
		_ppPlantId = null;
		_acceptNoPlant = true;
		_warehouseId = null;
		_productId = -1;

		//
		// Other MRP records dependency filters
		_onlyPP_MRP_IDs = null;
		_enforced_PP_MRP_Demand_ID = -1;

		_typeMRP = null;
		_productLLC = -1;
		_datePromisedMax = null;
		_mrpFirmType = null;
		_qtyNotZero = false;
		_mrpAvailable = null;
		_orderTypes.clear();
		_referencedModel = null;
		_ppOrderBOMLine_Null = false;

		_onlyActiveRecords = false;

		_skipIfMRPExcluded = false;

		return this;
	}

	@Override
	public MRPQueryBuilder setMRPContext(final IMRPContext mrpContext)
	{
		this._mrpContext = mrpContext;
		return this;
	}

	@Override
	public MRPQueryBuilder setContextProvider(final Object contextProvider)
	{
		this._contextProvider = contextProvider;
		return this;
	}

	@Override
	public MRPQueryBuilder setContextProvider(final Properties ctx, final String trxName)
	{
		return setContextProvider(new PlainContextAware(ctx, trxName));
	}

	private IContextAware getContextProviderToUse()
	{
		if (_contextProvider != null)
		{
			return InterfaceWrapperHelper.getContextAware(_contextProvider);
		}

		Check.assumeNotNull(_mrpContext, LiberoException.class, "contextProvider shall be set");
		return _mrpContext;

	}

	@Override
	public MRPQueryBuilder setAD_Client_ID(Integer adClientId)
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
		else if (_mrpContext != null)
		{
			return _mrpContext.getAD_Client_ID();
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
		else if (_mrpContext != null)
		{
			final I_AD_Org org = _mrpContext.getAD_Org();
			if (org != null)
			{
				return org.getAD_Org_ID();
			}
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
		else if (_mrpContext != null)
		{
			final I_M_Warehouse warehouse = _mrpContext.getM_Warehouse();
			if (warehouse != null)
			{
				return warehouse.getM_Warehouse_ID();
			}
		}

		return -1;
	}

	@Override
	public MRPQueryBuilder setPP_Plant_ID(final Integer ppPlantId)
	{
		this._ppPlantId = ppPlantId;
		return this;
	}

	@Override
	public MRPQueryBuilder setAcceptWithoutPlant(final boolean acceptNoPlant)
	{
		this._acceptNoPlant = acceptNoPlant;
		return this;
	}

	private Set<Integer> getPP_Plant_IDs_ToUse()
	{
		int ppPlantId = -1;
		if (_ppPlantId != null)
		{
			ppPlantId = _ppPlantId;
		}
		else if (_mrpContext != null)
		{
			final I_S_Resource plant = _mrpContext.getPlant();
			if (plant != null)
			{
				ppPlantId = plant.getS_Resource_ID();
			}
		}

		if (ppPlantId <= 0)
		{
			return Collections.emptySet();
		}

		final Set<Integer> plantIds = new HashSet<>();
		plantIds.add(ppPlantId);
		if (_acceptNoPlant)
		{
			plantIds.add(null);
		}

		return plantIds;
	}

	@Override
	public MRPQueryBuilder setM_Product_ID(Integer productId)
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
		else if (_mrpContext != null)
		{
			return _mrpContext.getM_Product_ID();
		}

		return -1;
	}

	@Override
	public MRPQueryBuilder setLowLevelCode(final int productLLC)
	{
		this._productLLC = productLLC;
		return this;
	}

	public int getLowLevelCode()
	{
		return this._productLLC;
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

	@Override
	public MRPQueryBuilder setDatePromisedMax(final Date datePromisedMax)
	{
		this._datePromisedMax = datePromisedMax;
		return this;
	}

	public Date getDatePromisedMax()
	{
		return this._datePromisedMax;
	}

	@Override
	public MRPQueryBuilder setMRPFirmType(MRPFirmType mrpFirmType)
	{
		this._mrpFirmType = mrpFirmType;
		return this;
	}

	public MRPFirmType getMRPFirmType()
	{
		return this._mrpFirmType;
	}

	public boolean isQtyNotZero()
	{
		return _qtyNotZero;
	}

	@Override
	public MRPQueryBuilder setQtyNotZero(boolean qtyNotZero)
	{
		this._qtyNotZero = qtyNotZero;
		return this;
	}

	public Boolean getMRPAvailable()
	{
		return _mrpAvailable;
	}

	@Override
	public MRPQueryBuilder setMRPAvailable(Boolean mrpAvailable)
	{
		this._mrpAvailable = mrpAvailable;
		return this;
	}

	public boolean isOnlyActiveRecords()
	{
		return _onlyActiveRecords;
	}

	@Override
	public MRPQueryBuilder setOnlyActiveRecords(boolean onlyActiveRecords)
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

	@Override
	public IMRPQueryBuilder addOnlyOrderType(final String orderType)
	{
		this._orderTypes.add(orderType);
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
	public MRPQueryBuilder setPP_Order_BOMLine_Null()
	{
		this._ppOrderBOMLine_Null = true;
		return this;
	}

	@Override
	public IMRPQueryBuilder addOnlyPP_MRP_ID(final int mrpId)
	{
		return addOnlyPP_MRP_IDs(Collections.singleton(mrpId));
	}

	@Override
	public IMRPQueryBuilder addOnlyPP_MRP(final I_PP_MRP mrp)
	{
		return addOnlyPP_MRPs(Collections.singleton(mrp));
	}

	@Override
	public IMRPQueryBuilder addOnlyPP_MRPs(final Collection<I_PP_MRP> mrps)
	{
		if (mrps == null || mrps.isEmpty())
		{
			return this;
		}

		final Set<Integer> mrpIds = new HashSet<Integer>(mrps.size());
		for (final I_PP_MRP mrp : mrps)
		{
			if (mrp == null)
			{
				continue;
			}
			final int mrpId = mrp.getPP_MRP_ID();
			if (mrpId <= 0)
			{
				continue;
			}
			mrpIds.add(mrpId);
		}

		if (mrpIds.isEmpty())
		{
			return this;
		}

		return addOnlyPP_MRP_IDs(mrpIds);
	}

	@Override
	public IMRPQueryBuilder addOnlyPP_MRP_IDs(final Collection<Integer> mrpIds)
	{
		if (mrpIds == null || mrpIds.isEmpty())
		{
			return this;
		}

		if (_onlyPP_MRP_IDs == null)
		{
			_onlyPP_MRP_IDs = new HashSet<Integer>();
		}
		_onlyPP_MRP_IDs.addAll(mrpIds);
		return this;
	}

	private final Set<Integer> getOnlyPP_MRP_IDs()
	{
		return _onlyPP_MRP_IDs;
	}

	@Override
	public MRPQueryBuilder setEnforced_PP_MRP_Demand_ID(int mrpId)
	{
		this._enforced_PP_MRP_Demand_ID = mrpId;
		return null;
	}

	@Override
	public MRPQueryBuilder setSkipIfMRPExcluded(final boolean skipIfMRPExcluded)
	{
		this._skipIfMRPExcluded = skipIfMRPExcluded;
		return this;
	}
}
