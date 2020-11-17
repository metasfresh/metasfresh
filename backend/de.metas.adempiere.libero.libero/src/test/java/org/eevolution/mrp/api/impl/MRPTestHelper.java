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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.api.impl.ModelProductDescriptorExtractorUsingAttributeSetInstanceFactory;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_Message;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_WF_Node;
import org.compiere.model.I_AD_Workflow;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.compiere.model.I_M_Shipper;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.compiere.model.I_S_ResourceType;
import org.compiere.model.X_AD_Workflow;
import org.compiere.model.X_C_DocType;
import org.compiere.model.X_S_Resource;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.eevolution.LiberoConstants;
import org.eevolution.api.IDDOrderDAO;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.IPPOrderDAO;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.X_DD_Order;
import org.eevolution.model.X_PP_MRP;
import org.eevolution.mrp.api.IMRPBL;
import org.eevolution.mrp.api.IMRPDAO;
import org.eevolution.mrp.expectations.MRPExpectation;
import org.eevolution.util.DDNetworkBuilder;
import org.eevolution.util.PPProductPlanningBuilder;
import org.eevolution.util.ProductBOMBuilder;
import org.junit.Assume;
import org.slf4j.Logger;

import ch.qos.logback.classic.Level;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.document.engine.impl.PlainDocumentBL;
import de.metas.event.impl.PlainEventBusFactory;
import de.metas.logging.LogManager;
import de.metas.material.event.ModelProductDescriptorExtractor;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.eventbus.MaterialEventConverter;
import de.metas.material.event.eventbus.MetasfreshEventBusService;
import de.metas.material.planning.DurationUnitCodeUtils;
import de.metas.material.planning.ErrorCodes;
import de.metas.material.planning.IMaterialPlanningContext;
import de.metas.material.planning.IMutableMRPContext;
import de.metas.material.planning.impl.MRPContext;
import de.metas.material.planning.pporder.PPOrderPojoConverter;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.organization.OrgInfoUpdateRequest;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.uom.CreateUOMConversionRequest;
import de.metas.uom.IUOMConversionDAO;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import de.metas.util.time.SystemTime;

public class MRPTestHelper
{
	//
	// Context
	private ZonedDateTime _today = ZonedDateTime.now();
	public Properties ctx;
	private String trxName;
	public final IContextAware contextProvider = new IContextAware()
	{
		@Override
		public Properties getCtx()
		{
			return ctx;
		}

		@Override
		public String getTrxName()
		{
			return trxName;
		}

		@Override
		public boolean isAllowThreadInherited()
		{
			return false;
		}
	};

	//
	// Services
	private PlainMRPDAO mrpDAO;
	public IQueryBL queryBL;
	public PlainDocumentBL docActionBL;

	//
	// Master Data
	public I_AD_Client adClient;
	public I_AD_Org adOrg01;
	//
	public I_C_UOM uomKg;
	public I_C_UOM uomEach;
	public I_M_Shipper shipperDefault;
	//
	public I_S_ResourceType resourceType_Plants;
	public I_S_ResourceType resourceType_Workcenters;
	public final I_S_Resource plant_any = null;
	//
	public I_AD_Workflow workflow_Standard;
	//
	public I_C_BP_Group bpGroupDefault;
	public I_M_Product_Category productCategoryDefault;

	//
	// Plants & Warehouses
	public I_S_Resource plant01;
	public I_S_Resource plant02;
	public I_M_Warehouse warehouse_plant01;
	public LocatorId warehouse_plant01_locatorId;
	public I_M_Warehouse warehouse_plant02;
	public I_M_Warehouse warehouse_picking01;
	public I_M_Warehouse warehouse_rawMaterials01;
	public LocatorId warehouse_rawMaterials01_locatorId;

	public MRPTestHelper()
	{
		this(true);
	}

	public MRPTestHelper(final boolean initEnvironment)
	{
		init(initEnvironment);
	}

	private void init(final boolean initEnvironment)
	{
		if (initEnvironment)
		{
			AdempiereTestHelper.get().init();
		}

		// POJOWrapper.setDefaultStrictValues(true);
		this.mrpDAO = (PlainMRPDAO)Services.get(IMRPDAO.class);
		this.queryBL = Services.get(IQueryBL.class);
		this.docActionBL = (PlainDocumentBL)Services.get(IDocumentBL.class);

		setupContext(initEnvironment);
		setupMRPExecutorService();

		registerModelValidators();

		createMasterData();
	}

	private void setupContext(final boolean initEnvironment)
	{
		ctx = Env.getCtx();
		trxName = ITrx.TRXNAME_None;

		//
		// Setup context: #AD_Client_ID
		int adClientId = Env.getAD_Client_ID(ctx);
		if (adClientId <= 0)
		{
			adClient = InterfaceWrapperHelper.create(ctx, I_AD_Client.class, ITrx.TRXNAME_None);
			InterfaceWrapperHelper.save(adClient);
			adClientId = adClient.getAD_Client_ID();
			Env.setContext(ctx, Env.CTXNAME_AD_Client_ID, adClientId);
		}
		else
		{
			adClient = InterfaceWrapperHelper.create(ctx, adClientId, I_AD_Client.class, ITrx.TRXNAME_None);
		}

		int adOrgId = Env.getAD_Org_ID(ctx);
		if (adOrgId <= 0)
		{
			adOrg01 = createOrg("Org01");
			adOrgId = adOrg01.getAD_Org_ID();
			Env.setContext(ctx, Env.CTXNAME_AD_Org_ID, adOrgId);
		}
		else
		{
			adOrg01 = InterfaceWrapperHelper.create(ctx, adOrgId, I_AD_Org.class, ITrx.TRXNAME_None);
		}

		SystemTime.setTimeSource(() -> _today.toInstant().toEpochMilli());
	}

	private void setupMRPExecutorService()
	{
		LogManager.setLoggerLevel(getMRPLogger(), Level.INFO);
	}

	private void createMasterData()
	{
		this.uomEach = createUOM("Ea");
		this.uomKg = createUOM("Kg");

		this.shipperDefault = createShipper("shipperDefault");

		this.resourceType_Plants = createResourceType("Plants");
		this.resourceType_Workcenters = createResourceType("Workcenters");

		final I_S_Resource workcenter1 = createResource("workcenter1", X_S_Resource.MANUFACTURINGRESOURCETYPE_WorkCenter, resourceType_Workcenters);
		final ResourceId workcenter1Id = ResourceId.ofRepoId(workcenter1.getS_Resource_ID());

		this.workflow_Standard = createWorkflow("Standard_MFG");
		createWorkflowNode(workflow_Standard, "Start", true, workcenter1Id);

		this.productCategoryDefault = createProductCategory("Default");
		this.bpGroupDefault = createBPGroup("Default");

		createDocType(X_C_DocType.DOCBASETYPE_DistributionOrder);
		createDocType(X_C_DocType.DOCBASETYPE_ManufacturingOrder);
		createDocType(X_C_DocType.DOCBASETYPE_ManufacturingCostCollector);

		createMRPMessage(ErrorCodes.MRP_ERROR_999_Unknown);

		createMRPMessage(ErrorCodes.ERR_DRP_010_InTransitWarehouseNotFound);
		createMRPMessage(ErrorCodes.ERR_DRP_060_NoSourceOfSupply);

		createMasterData_WarehouseAndPlants();
	}

	private void createMasterData_WarehouseAndPlants()
	{
		this.plant01 = createResource("Plant01", X_S_Resource.MANUFACTURINGRESOURCETYPE_Plant, resourceType_Plants);
		this.warehouse_plant01 = createWarehouse("Plant01_Warehouse01", adOrg01);
		this.warehouse_plant01_locatorId = getDefaultLocatorId(warehouse_plant01);

		this.plant02 = createResource("Plant02", X_S_Resource.MANUFACTURINGRESOURCETYPE_Plant, resourceType_Plants);
		this.warehouse_plant02 = createWarehouse("Plant02_Warehouse01", adOrg01);

		this.warehouse_picking01 = createWarehouse("Picking_Warehouse01", adOrg01);

		// Raw Materials Warehouses
		// NOTE: we are adding them last because of MRP bug regarding how warehouses are iterated and DRP
		this.warehouse_rawMaterials01 = createWarehouse("RawMaterials_Warehouse01", adOrg01);
		this.warehouse_rawMaterials01_locatorId = getDefaultLocatorId(warehouse_rawMaterials01);
	}

	private void registerModelValidators()
	{
		// FIXME: workaround to bypass org.adempiere.document.service.impl.PlainDocActionBL.isDocumentTable(String) failure
		PlainDocumentBL.isDocumentTableResponse = false;

		final I_AD_Client client = null;
		final IModelInterceptorRegistry modelInterceptorRegistry = Services.get(IModelInterceptorRegistry.class);

		modelInterceptorRegistry.addModelInterceptor(new org.compiere.wf.model.validator.AD_Workflow(), client);

		modelInterceptorRegistry.addModelInterceptor(createLiberoValidator(), client);
	}

	private org.eevolution.model.LiberoValidator createLiberoValidator()
	{
		final ModelProductDescriptorExtractor productDescriptorFactory = new ModelProductDescriptorExtractorUsingAttributeSetInstanceFactory();
		final PPOrderPojoConverter ppOrderConverter = new PPOrderPojoConverter(productDescriptorFactory);

		final MaterialEventConverter materialEventConverter = new MaterialEventConverter();
		final MetasfreshEventBusService materialEventService = MetasfreshEventBusService.createLocalServiceThatIsReadyToUse(
				materialEventConverter,
				PlainEventBusFactory.newInstance());
		final PostMaterialEventService postMaterialEventService = new PostMaterialEventService(materialEventService);

		return new org.eevolution.model.LiberoValidator(ppOrderConverter, postMaterialEventService);
	}

	public Timestamp getToday()
	{
		return TimeUtil.asTimestamp(_today);
	}

	public void setToday(int year, int month, int day)
	{
		this._today = LocalDate.of(year, month, day)
				.atStartOfDay()
				.atZone(ZoneId.systemDefault());
	}

	public IMutableMRPContext createMutableMRPContext()
	{
		final IMutableMRPContext mrpContext = new MRPContext();
		mrpContext.setCtx(ctx);
		mrpContext.setTrxName(ITrx.TRXNAME_None);

		// mrpContext.setPlanner_User_ID(p_Planner_ID);
		mrpContext.setDate(getToday());
		mrpContext.setAD_Client_ID(adClient.getAD_Client_ID());

		return mrpContext;
	}

	public I_AD_Org createOrg(final String name)
	{
		final I_AD_Org org = InterfaceWrapperHelper.create(ctx, I_AD_Org.class, ITrx.TRXNAME_None);
		org.setValue(name);
		org.setName(name);
		InterfaceWrapperHelper.save(org);

		//
		// InTransit Warehouse
		final I_M_Warehouse warehouseInTransit = createWarehouse(name + "_InTransit", org);
		warehouseInTransit.setIsInTransit(true);
		InterfaceWrapperHelper.save(warehouseInTransit);

		//
		// BP Org Link
		final I_C_BPartner bpartner = createBPartner("BPOrg_" + name);
		// bpartner.setAD_OrgBP_ID(String.valueOf(org.getAD_Org_ID()));
		bpartner.setAD_OrgBP_ID(org.getAD_Org_ID());
		InterfaceWrapperHelper.save(bpartner);
		//
		final BPartnerLocationId bpLocationId = createBPLocation(bpartner);

		Services.get(IOrgDAO.class).createOrUpdateOrgInfo(OrgInfoUpdateRequest.builder()
				.orgId(OrgId.ofRepoId(org.getAD_Org_ID()))
				.orgBPartnerLocationId(Optional.of(bpLocationId))
				.build());

		return org;
	}

	public I_S_ResourceType createResourceType(final String name)
	{
		final I_S_ResourceType resourceType = InterfaceWrapperHelper.newInstance(I_S_ResourceType.class, contextProvider);
		resourceType.setIsActive(true);
		resourceType.setName(name);
		resourceType.setValue(name);
		resourceType.setIsDateSlot(false);
		resourceType.setIsTimeSlot(false);
		InterfaceWrapperHelper.save(resourceType);

		return resourceType;
	}

	public I_S_Resource createResource(String name, String manufacturingResourceType, final I_S_ResourceType resourceType)
	{
		final I_S_Resource resource = InterfaceWrapperHelper.newInstance(I_S_Resource.class, contextProvider);
		resource.setIsManufacturingResource(true);
		resource.setManufacturingResourceType(manufacturingResourceType);
		resource.setIsAvailable(true);
		resource.setName(name);
		resource.setValue(name);
		resource.setPlanningHorizon(365);
		resource.setS_ResourceType_ID(resourceType.getS_ResourceType_ID());
		InterfaceWrapperHelper.save(resource);

		return resource;
	}

	public I_M_Warehouse createWarehouse(final String name, final I_AD_Org org)
	{
		final I_S_Resource plant = null;
		return createWarehouse(name, org, plant);
	}

	public I_M_Warehouse createWarehouse(final String name, final I_AD_Org org, final I_S_Resource plant)
	{
		final I_M_Warehouse warehouse = InterfaceWrapperHelper.newInstance(I_M_Warehouse.class, contextProvider);
		warehouse.setAD_Org_ID(org.getAD_Org_ID());
		warehouse.setValue(name);
		warehouse.setName(name);
		warehouse.setIsInTransit(false);
		warehouse.setPP_Plant(plant);
		InterfaceWrapperHelper.save(warehouse);

		return warehouse;
	}

	private static LocatorId getDefaultLocatorId(final I_M_Warehouse warehouse)
	{
		return Services.get(IWarehouseBL.class).getDefaultLocatorId(WarehouseId.ofRepoId(warehouse.getM_Warehouse_ID()));
	}

	public I_C_UOM createUOM(final String name)
	{
		return createUOM(name, 2);
	}

	public I_C_UOM createUOM(final String name, final int stdPrecision)
	{
		final I_C_UOM uom = InterfaceWrapperHelper.newInstance(I_C_UOM.class, contextProvider);
		uom.setName(name);
		uom.setUOMSymbol(name);
		uom.setX12DE355(name);
		uom.setStdPrecision(stdPrecision);
		uom.setCostingPrecision(0);
		InterfaceWrapperHelper.save(uom);
		return uom;
	}

	public void createUOMConversion(final CreateUOMConversionRequest request)
	{
		final IUOMConversionDAO uomConversionDAO = Services.get(IUOMConversionDAO.class);
		uomConversionDAO.createUOMConversion(request);
	}

	public I_M_Product createProduct(final String name)
	{
		return createProduct(name, uomEach);
	}

	public I_M_Product createProduct(final String name, final I_C_UOM uom)
	{
		final I_M_Product product = InterfaceWrapperHelper.newInstance(I_M_Product.class, contextProvider);
		product.setValue(name);
		product.setName(name);
		product.setM_Product_Category_ID(productCategoryDefault == null ? -1 : productCategoryDefault.getM_Product_Category_ID());
		product.setC_UOM_ID(uom.getC_UOM_ID());
		product.setLowLevel(0);
		InterfaceWrapperHelper.save(product);

		return product;
	}

	public I_M_Product_Category createProductCategory(final String name)
	{
		final I_M_Product_Category productCategory = InterfaceWrapperHelper.newInstance(I_M_Product_Category.class, contextProvider);
		productCategory.setValue(name);
		productCategory.setName(name);
		InterfaceWrapperHelper.save(productCategory);

		return productCategory;
	}

	/**
	 * Creates a dummy MRP firm demand.
	 *
	 * @param product
	 * @param qty
	 * @param date
	 * @param plant
	 * @param warehouse
	 * @return
	 * @see #createMRPDemand(I_M_Product, BigDecimal, Date, I_S_Resource, I_M_Warehouse, I_C_BPartner)
	 */
	public I_PP_MRP createMRPDemand(
			final I_M_Product product, final BigDecimal qty,
			final Date date,
			final I_S_Resource plant,
			final I_M_Warehouse warehouse)
	{
		final I_C_BPartner bpartner = null;
		return createMRPDemand(product, qty, date, plant, warehouse, bpartner);
	}

	/**
	 * Creates a dummy MRP firm demand.
	 *
	 * NOTE:
	 * <ul>
	 * <li>DocStatus will be Completed
	 * <li>OrderType will be SalesOrder
	 * </ul>
	 *
	 * @param product
	 * @param qty
	 * @param date
	 * @param plant
	 * @param warehouse
	 * @return demand MRP record
	 */
	public I_PP_MRP createMRPDemand(
			final I_M_Product product, final BigDecimal qty,
			final Date date,
			final I_S_Resource plant,
			final I_M_Warehouse warehouse,
			final I_C_BPartner bpartner)
	{
		final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

		final I_PP_MRP mrp = InterfaceWrapperHelper.newInstance(I_PP_MRP.class, contextProvider);
		mrp.setAD_Org_ID(warehouse.getAD_Org_ID());
		mrp.setS_Resource(plant);
		mrp.setM_Warehouse(warehouse);
		mrp.setC_BPartner(bpartner);
		//
		final Timestamp dateTS = TimeUtil.asTimestamp(date);
		mrp.setDatePromised(dateTS);
		mrp.setDateStartSchedule(dateTS);
		mrp.setDateFinishSchedule(dateTS);
		//
		mrp.setTypeMRP(X_PP_MRP.TYPEMRP_Demand);
		mrp.setDocStatus(X_PP_MRP.DOCSTATUS_Completed); // Firm Demand
		mrp.setOrderType(X_PP_MRP.ORDERTYPE_SalesOrder); // Sales Order
		mrp.setIsAvailable(true);
		//
		mrp.setM_Product(product);

		final I_C_UOM uom = uomDAO.getById(product.getC_UOM_ID());

		Services.get(IMRPBL.class).setQty(mrp, qty, qty, uom);
		//
		// mrp.setC_BPartner(C_BPartner);

		InterfaceWrapperHelper.save(mrp);

		return mrp;
	}

	private void createMRPMessage(final String code)
	{
		final I_AD_Message message = InterfaceWrapperHelper.newInstance(I_AD_Message.class, contextProvider);
		InterfaceWrapperHelper.setValue(message, "AD_Client_ID", 0);
		message.setAD_Org_ID(0);
		message.setValue(code);
		message.setMsgText(code);
		InterfaceWrapperHelper.save(message);
	}

	public PPProductPlanningBuilder newProductPlanning()
	{
		return new PPProductPlanningBuilder()
				.setContext(contextProvider);
	}

	public I_M_Shipper createShipper(final String name)
	{
		final I_C_BPartner bpartner = createBPartner(name);
		return createShipper(bpartner);
	}

	public I_M_Shipper createShipper(final I_C_BPartner bpartner)
	{
		final I_M_Shipper shipper = InterfaceWrapperHelper.newInstance(I_M_Shipper.class, contextProvider);
		shipper.setName(bpartner.getName());
		shipper.setC_BPartner_ID(bpartner.getC_BPartner_ID());

		InterfaceWrapperHelper.save(shipper);
		return shipper;
	}

	public I_C_BP_Group createBPGroup(final String name)
	{
		final I_C_BP_Group bpGroup = InterfaceWrapperHelper.newInstance(I_C_BP_Group.class, contextProvider);
		bpGroup.setValue(name);
		bpGroup.setName(name);
		InterfaceWrapperHelper.save(bpGroup);
		return bpGroup;
	}

	public I_C_BPartner createBPartner(final String name)
	{
		final I_C_BPartner bpartner = InterfaceWrapperHelper.newInstance(I_C_BPartner.class, contextProvider);
		bpartner.setValue(name);
		bpartner.setName(name);
		bpartner.setIsCustomer(true);
		bpartner.setIsVendor(true);
		bpartner.setAD_OrgBP_ID(-1);
		bpartner.setC_BP_Group_ID(bpGroupDefault == null ? -1 : bpGroupDefault.getC_BP_Group_ID());

		InterfaceWrapperHelper.save(bpartner);
		return bpartner;
	}

	private BPartnerLocationId createBPLocation(final I_C_BPartner bpartner)
	{
		final I_C_BPartner_Location bpLocation = InterfaceWrapperHelper.newInstance(I_C_BPartner_Location.class, bpartner);
		bpLocation.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		bpLocation.setIsBillToDefault(true);
		bpLocation.setIsBillTo(true);
		bpLocation.setIsShipToDefault(true);
		bpLocation.setIsShipTo(true);
		InterfaceWrapperHelper.save(bpLocation);
		return BPartnerLocationId.ofRepoId(bpLocation.getC_BPartner_ID(), bpLocation.getC_BPartner_Location_ID());
	}

	public DDNetworkBuilder newDDNetwork()
	{
		return new DDNetworkBuilder()
				.setContext(contextProvider)
				.shipper(shipperDefault);
	}

	private I_C_DocType createDocType(final String docBaseType)
	{
		final I_C_DocType docType = InterfaceWrapperHelper.newInstance(I_C_DocType.class, contextProvider);
		docType.setAD_Org_ID(adOrg01.getAD_Org_ID());
		docType.setDocBaseType(docBaseType);
		docType.setName(docBaseType);

		InterfaceWrapperHelper.save(docType);
		return docType;
	}

	public void dumpMRPRecords()
	{
		MRPTracer.dumpMRPRecords();
	}

	public void dumpMRPRecords(final String message)
	{
		MRPTracer.dumpMRPRecords(message);
	}

	public MRPExpectation<Object> newMRPExpectation()
	{
		final MRPExpectation<Object> e = MRPExpectation.newMRPExpectation();
		e.setContext(this.contextProvider);
		return e;
	}

	public ProductBOMBuilder newProductBOM()
	{
		return new ProductBOMBuilder()
				.setContext(contextProvider);
	}

	public I_AD_Workflow createWorkflow(final String name)
	{
		final I_AD_Workflow wf = InterfaceWrapperHelper.newInstance(I_AD_Workflow.class, contextProvider);
		wf.setValue(name);
		wf.setName(name);
		wf.setWorkflowType(X_AD_Workflow.WORKFLOWTYPE_Manufacturing);
		wf.setDurationUnit(DurationUnitCodeUtils.toDurationUnitCode(ChronoUnit.HOURS));
		InterfaceWrapperHelper.save(wf);
		return wf;
	}

	public I_AD_WF_Node createWorkflowNode(
			final I_AD_Workflow wf,
			final String nodeName,
			final boolean startNode,
			final ResourceId resourceId)
	{
		final I_AD_WF_Node node = InterfaceWrapperHelper.newInstance(I_AD_WF_Node.class, contextProvider);
		node.setAD_Workflow_ID(wf.getAD_Workflow_ID());
		node.setValue(nodeName);
		node.setName(nodeName);
		node.setS_Resource_ID(ResourceId.toRepoId(resourceId));
		InterfaceWrapperHelper.save(node);

		if (startNode)
		{
			wf.setAD_WF_Node_ID(node.getAD_WF_Node_ID());
			InterfaceWrapperHelper.save(wf);
		}

		return node;
	}

	public final Logger getMRPLogger()
	{
		return LogManager.getLogger(IMaterialPlanningContext.LOGGERNAME);
	}

	public void completeAllMRPDocuments()
	{
		completeMRPDocuments(I_PP_Order.class);
		completeMRPDocuments(I_DD_Order.class);
	}

	/**
	 *
	 * @param documentClass
	 * @return completed documents
	 */
	public <T> List<T> completeMRPDocuments(final Class<T> documentClass)
	{
		final List<T> documentModels = queryBL.createQueryBuilder(documentClass, contextProvider)
				.addOnlyActiveRecordsFilter()
				.addNotEqualsFilter(I_DD_Order.COLUMNNAME_DocStatus, X_DD_Order.DOCSTATUS_Completed)
				.create()
				.list();
		final List<T> documentModelsCompleted = new ArrayList<>(documentModels.size());
		for (final T documentModel : documentModels)
		{
			final IDocument document = docActionBL.getDocument(documentModel);
			docActionBL.processEx(document, IDocument.ACTION_Complete, IDocument.STATUS_Completed);

			documentModelsCompleted.add(documentModel);
		}

		return documentModelsCompleted;
	}

	/**
	 * Simulate receiving from an manufacturing order
	 *
	 * @param ppOrder
	 * @param qtyToReceive
	 */
	public void receiveFromPPOrder(final I_PP_Order ppOrder, final BigDecimal qtyToReceive)
	{
		//
		// Receive from MO: i.e. increase QtyDelivered
		final BigDecimal qtyDeliveredOld = ppOrder.getQtyDelivered();
		final BigDecimal qtyDelivered = qtyDeliveredOld.add(qtyToReceive);
		ppOrder.setQtyDelivered(qtyDelivered);
		InterfaceWrapperHelper.save(ppOrder);

		//
		// Add received qty to QtyOnHand
		final WarehouseId warehouseId = WarehouseId.ofRepoId(ppOrder.getM_Warehouse_ID());
		final I_M_Warehouse warehouse = Services.get(IWarehouseDAO.class).getById(warehouseId);

		final ProductId productId = ProductId.ofRepoId(ppOrder.getM_Product_ID());
		final I_M_Product product = Services.get(IProductDAO.class).getById(productId);

		mrpDAO.addQtyOnHand(warehouse, product, qtyToReceive);
	}

	public I_DD_Order retrieveSingleForwardDDOrder(final I_PP_Order ppOrder)
	{
		return Services.get(IDDOrderDAO.class)
				.retrieveForwardDDOrderLinesQuery(ppOrder)
				.andCollect(I_DD_OrderLine.COLUMN_DD_Order_ID)
				.create()
				.firstOnly(I_DD_Order.class);
	}

	public final void assumeMRP_POQ_Enabled()
	{
		Assume.assumeFalse("Skip this test because MRP_POQ_Disabled", LiberoConstants.isMRP_POQ_Disabled());
	}

	public I_PP_Order createPP_Order(final I_PP_Product_BOM productBOM, final String qtyOrderedStr, final I_C_UOM uom)
	{
		final I_PP_Order ppOrder = InterfaceWrapperHelper.newInstance(I_PP_Order.class, contextProvider);
		ppOrder.setAD_Org_ID(this.adOrg01.getAD_Org_ID());

		setCommonProperties(ppOrder);

		ppOrder.setM_Product_ID(productBOM.getM_Product_ID());
		ppOrder.setPP_Product_BOM_ID(productBOM.getPP_Product_BOM_ID());
		ppOrder.setAD_Workflow(this.workflow_Standard);
		ppOrder.setM_Warehouse_ID(this.warehouse_plant01.getM_Warehouse_ID());
		ppOrder.setS_Resource(this.plant01);
		ppOrder.setQtyOrdered(new BigDecimal(qtyOrderedStr));
		ppOrder.setDatePromised(getToday());
		ppOrder.setDocStatus(IDocument.STATUS_Drafted);
		ppOrder.setDocAction(IDocument.ACTION_Complete);
		ppOrder.setC_UOM_ID(uom.getC_UOM_ID());
		Services.get(IPPOrderDAO.class).save(ppOrder);

		return ppOrder;
	}

	private void setCommonProperties(final I_PP_Order ppOrder)
	{
		Services.get(IPPOrderBL.class).setDocType(ppOrder, X_C_DocType.DOCBASETYPE_ManufacturingOrder, null);

		// required to avoid an NPE when building the lightweight PPOrder pojo
		final Timestamp t1 = SystemTime.asTimestamp();
		ppOrder.setDateOrdered(t1);
		ppOrder.setDateStartSchedule(t1);
	}

}
