package org.eevolution.model;

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

import de.metas.cache.CacheMgt;
import de.metas.cache.model.IModelCacheService;
import de.metas.distribution.ddorder.lowlevel.DDOrderLowLevelService;
import de.metas.distribution.ddorder.lowlevel.interceptor.DDOrderLowLevelInterceptors;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.PPOrderPojoConverter;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.compiere.SpringContextHolder;
import org.compiere.util.Env;
import org.eevolution.api.impl.ProductBOMService;
import org.eevolution.api.impl.ProductBOMVersionsDAO;

/**
 * Libero Validator
 *
 * @author Victor Perez
 * @author Trifon Trifonov
 * <li>[ 2270421 ] Can not complete Shipment (Customer)</li>
 * @author Teo Sarca, www.arhipac.ro
 */
public final class LiberoValidator extends AbstractModuleInterceptor
{
	/**
	 * Context variable which says if libero manufacturing is enabled
	 */
	public static final String CTX_IsLiberoEnabled = "#IsLiberoEnabled";

	private final PPOrderPojoConverter ppOrderConverter;
	private final PostMaterialEventService materialEventService;
	private final IDocumentNoBuilderFactory documentNoBuilderFactory;
	private final IPPOrderBOMBL ppOrderBOMBL;
	private final DDOrderLowLevelService ddOrderLowLevelService;
	private final ProductBOMVersionsDAO bomVersionsDAO;
	private final ProductBOMService productBOMService;

	@SuppressWarnings("unused")
	public LiberoValidator()
	{
		this(SpringContextHolder.instance.getBean(PPOrderPojoConverter.class),
			 SpringContextHolder.instance.getBean(PostMaterialEventService.class),
			 SpringContextHolder.instance.getBean(IDocumentNoBuilderFactory.class),
			 SpringContextHolder.instance.getBean(IPPOrderBOMBL.class),
			 SpringContextHolder.instance.getBean(DDOrderLowLevelService.class),
			 SpringContextHolder.instance.getBean(ProductBOMVersionsDAO.class),
			 SpringContextHolder.instance.getBean(ProductBOMService.class));
	}

	public LiberoValidator(
			@NonNull final PPOrderPojoConverter ppOrderConverter,
			@NonNull final PostMaterialEventService materialEventService,
			@NonNull final IDocumentNoBuilderFactory documentNoBuilderFactory,
			@NonNull final IPPOrderBOMBL ppOrderBOMBL,
			@NonNull final DDOrderLowLevelService ddOrderLowLevelService,
			@NonNull final ProductBOMVersionsDAO bomVersionsDAO,
			@NonNull final ProductBOMService productBOMService)
	{
		this.ppOrderConverter = ppOrderConverter;
		this.materialEventService = materialEventService;
		this.documentNoBuilderFactory = documentNoBuilderFactory;
		this.ppOrderBOMBL = ppOrderBOMBL;
		this.ddOrderLowLevelService = ddOrderLowLevelService;
		this.bomVersionsDAO = bomVersionsDAO;
		this.productBOMService = productBOMService;
	}

	@Override
	protected void registerInterceptors(final IModelValidationEngine engine)
	{
		//
		// Master data
		engine.addModelValidator(new org.eevolution.model.validator.PP_Product_BOM(bomVersionsDAO, productBOMService));
		engine.addModelValidator(new org.eevolution.model.validator.PP_Product_BOMLine());
		engine.addModelValidator(new org.eevolution.model.validator.PP_Product_Planning(bomVersionsDAO, productBOMService));

		// PP_Order related
		engine.addModelValidator(new org.eevolution.model.validator.PP_Order(
				ppOrderConverter,
				materialEventService,
				documentNoBuilderFactory,
				ppOrderBOMBL,
				bomVersionsDAO));
		engine.addModelValidator(new org.eevolution.model.validator.PP_Order_PostMaterialEvent(ppOrderConverter, materialEventService)); // gh #523
		engine.addModelValidator(new org.eevolution.model.validator.PP_Order_BOM());
		engine.addModelValidator(new org.eevolution.model.validator.PP_Order_BOMLine());
		engine.addModelValidator(new org.eevolution.model.validator.PP_Order_Node_Product());
		engine.addModelValidator(new org.eevolution.model.validator.PP_Cost_Collector());

		//
		// DRP
		engine.addModelValidator(new DDOrderLowLevelInterceptors(ddOrderLowLevelService, documentNoBuilderFactory));

		//
		// Forecast
		engine.addModelValidator(new org.eevolution.model.validator.M_Forecast(ddOrderLowLevelService));
	}

	@Override
	protected void setupCaching(final IModelCacheService cachingService)
	{
		// cachingService.addTableCacheConfigIfAbsent(I_S_Resource.class); // not needed anymore because we have a dedicated cache for S_Resource
		// cachingService.addTableCacheConfigIfAbsent(I_S_ResourceType.class); // not needed anymore because we have a dedicated cache for S_ResourceType

		CacheMgt.get().enableRemoteCacheInvalidationForTableName(I_PP_Order.Table_Name);
	}

	@Override
	public void onUserLogin(
			final int AD_Org_ID,
			final int AD_Role_ID,
			final int AD_User_ID)
	{
		Env.setContext(Env.getCtx(), CTX_IsLiberoEnabled, true);
	}
}    // LiberoValidator
