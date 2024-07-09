package org.eevolution.mrp.api.impl;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.common.util.time.SystemTime;
import de.metas.distribution.ddorder.lowlevel.DDOrderLowLevelDAO;
import de.metas.distribution.ddorder.lowlevel.DDOrderLowLevelService;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.document.engine.impl.PlainDocumentBL;
import de.metas.document.sequence.impl.DocumentNoBuilderFactory;
import de.metas.event.IEventBusFactory;
import de.metas.event.impl.PlainEventBusFactory;
import de.metas.material.event.MaterialEventObserver;
import de.metas.material.event.ModelProductDescriptorExtractor;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.eventbus.MaterialEventConverter;
import de.metas.material.event.eventbus.MetasfreshEventBusService;
import de.metas.material.planning.ErrorCodes;
import de.metas.material.planning.pporder.PPOrderPojoConverter;
import de.metas.material.planning.pporder.PPRoutingType;
import de.metas.material.planning.pporder.impl.PPOrderBOMBL;
import de.metas.material.replenish.ReplenishInfoRepository;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.organization.OrgInfoUpdateRequest;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.resource.ManufacturingResourceType;
import de.metas.resource.ResourceService;
import de.metas.uom.CreateUOMConversionRequest;
import de.metas.uom.IUOMConversionDAO;
import de.metas.util.Services;
import de.metas.workflow.WFDurationUnit;
import de.metas.workflow.interceptors.AD_Workflow;
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
import org.compiere.SpringContextHolder;
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
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.IPPOrderDAO;
import org.eevolution.api.PPOrderDocBaseType;
import org.eevolution.api.impl.ProductBOMService;
import org.eevolution.api.impl.ProductBOMVersionsDAO;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMVersions;
import org.eevolution.util.DDNetworkBuilder;
import org.eevolution.util.ProductBOMBuilder;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.Properties;

public class MRPTestHelper
{
	//
	// Context
	private final ZonedDateTime _today = ZonedDateTime.now();
	public Properties ctx;
	@Nullable private String trxName;
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
		this.queryBL = Services.get(IQueryBL.class);
		this.docActionBL = (PlainDocumentBL)Services.get(IDocumentBL.class);

		setupContext();

		registerModelValidators();

		createMasterData();
	}

	private void setupContext()
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

	private void createMasterData()
	{
		this.uomEach = createUOM("Ea");
		this.uomKg = createUOM("Kg");

		this.shipperDefault = createShipper("shipperDefault");

		this.resourceType_Plants = createResourceType("Plants");
		this.resourceType_Workcenters = createResourceType("Workcenters");

		final I_S_Resource workcenter1 = createResource("workcenter1", ManufacturingResourceType.WorkCenter, resourceType_Workcenters);
		final ResourceId workcenter1Id = ResourceId.ofRepoId(workcenter1.getS_Resource_ID());

		this.workflow_Standard = createWorkflow("Standard_MFG");
		createWorkflowNode(workflow_Standard, "Start", true, workcenter1Id);

		this.productCategoryDefault = createProductCategory("Default");
		this.bpGroupDefault = createBPGroup("Default");

		createDocType(X_C_DocType.DOCBASETYPE_DistributionOrder);
		createDocType(PPOrderDocBaseType.MANUFACTURING_ORDER.getCode());
		createDocType(X_C_DocType.DOCBASETYPE_ManufacturingCostCollector);

		createMRPMessage(ErrorCodes.MRP_ERROR_999_Unknown);

		createMRPMessage(ErrorCodes.ERR_DRP_010_InTransitWarehouseNotFound);
		createMRPMessage(ErrorCodes.ERR_DRP_060_NoSourceOfSupply);

		createMasterData_WarehouseAndPlants();
	}

	private void createMasterData_WarehouseAndPlants()
	{
		this.plant01 = createResource("Plant01", ManufacturingResourceType.Plant, resourceType_Plants);
		this.warehouse_plant01 = createWarehouse("Plant01_Warehouse01", adOrg01);
		this.warehouse_plant01_locatorId = getDefaultLocatorId(warehouse_plant01);

		this.plant02 = createResource("Plant02", ManufacturingResourceType.Plant, resourceType_Plants);
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

		SpringContextHolder.registerJUnitBean(IEventBusFactory.class, PlainEventBusFactory.newInstance());

		final IModelInterceptorRegistry modelInterceptorRegistry = Services.get(IModelInterceptorRegistry.class);

		modelInterceptorRegistry.addModelInterceptor(new AD_Workflow(), null);

		modelInterceptorRegistry.addModelInterceptor(createLiberoValidator(), null);
	}

	private org.eevolution.model.LiberoValidator createLiberoValidator()
	{
		final ModelProductDescriptorExtractor productDescriptorFactory = new ModelProductDescriptorExtractorUsingAttributeSetInstanceFactory();
		final PPOrderPojoConverter ppOrderConverter = new PPOrderPojoConverter(productDescriptorFactory, new ReplenishInfoRepository());

		final MaterialEventConverter materialEventConverter = new MaterialEventConverter();
		final MetasfreshEventBusService materialEventService = MetasfreshEventBusService.createLocalServiceThatIsReadyToUse(
				materialEventConverter,
				PlainEventBusFactory.newInstance(),
				new MaterialEventObserver());
		final PostMaterialEventService postMaterialEventService = new PostMaterialEventService(materialEventService);

		return new org.eevolution.model.LiberoValidator(
				ppOrderConverter,
				postMaterialEventService,
				new DocumentNoBuilderFactory(Optional.empty()),
				new PPOrderBOMBL(),
				new DDOrderLowLevelService(new DDOrderLowLevelDAO(), ResourceService.newInstanceForJUnitTesting()),
				new ProductBOMVersionsDAO(),
				new ProductBOMService(new ProductBOMVersionsDAO()));
	}

	public Timestamp getToday()
	{
		return TimeUtil.asTimestamp(_today);
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

	public I_S_Resource createResource(
			final String name,
			final ManufacturingResourceType manufacturingResourceType,
			final I_S_ResourceType resourceType)
	{
		final I_S_Resource resource = InterfaceWrapperHelper.newInstance(I_S_Resource.class, contextProvider);
		resource.setIsManufacturingResource(true);
		resource.setManufacturingResourceType(manufacturingResourceType.getCode());
		resource.setIsAvailable(true);
		resource.setName(name);
		resource.setValue(name);
		resource.setPlanningHorizon(365);
		resource.setS_ResourceType_ID(resourceType.getS_ResourceType_ID());
		InterfaceWrapperHelper.save(resource);

		return resource;
	}

	public I_M_Warehouse createWarehouse(
			final String name,
			final I_AD_Org org)
	{
		return createWarehouse(name, org, null);
	}

	public I_M_Warehouse createWarehouse(
			final String name,
			final I_AD_Org org,
			@Nullable final I_S_Resource plant)
	{
		final I_M_Warehouse warehouse = InterfaceWrapperHelper.newInstance(I_M_Warehouse.class, contextProvider);
		warehouse.setAD_Org_ID(org.getAD_Org_ID());
		warehouse.setValue(name);
		warehouse.setName(name);
		warehouse.setIsInTransit(false);
		warehouse.setPP_Plant_ID(plant != null ? plant.getS_Resource_ID() : -1);
		InterfaceWrapperHelper.save(warehouse);

		return warehouse;
	}

	private static LocatorId getDefaultLocatorId(final I_M_Warehouse warehouse)
	{
		return Services.get(IWarehouseBL.class).getOrCreateDefaultLocatorId(WarehouseId.ofRepoId(warehouse.getM_Warehouse_ID()));
	}

	public I_C_UOM createUOM(final String name)
	{
		return createUOM(name, 2);
	}

	public I_C_UOM createUOM(
			final String name,
			final int stdPrecision)
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

	public I_M_Product createProduct(
			final String name,
			final I_C_UOM uom)
	{
		final I_M_Product product = InterfaceWrapperHelper.newInstance(I_M_Product.class, contextProvider);
		product.setValue(name);
		product.setName(name);
		product.setM_Product_Category_ID(productCategoryDefault == null ? -1 : productCategoryDefault.getM_Product_Category_ID());
		product.setC_UOM_ID(uom.getC_UOM_ID());
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

	private void createMRPMessage(final String code)
	{
		final I_AD_Message message = InterfaceWrapperHelper.newInstance(I_AD_Message.class, contextProvider);
		InterfaceWrapperHelper.setValue(message, "AD_Client_ID", 0);
		message.setAD_Org_ID(0);
		message.setValue(code);
		message.setMsgText(code);
		InterfaceWrapperHelper.save(message);
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

	private void createDocType(final String docBaseType)
	{
		final I_C_DocType docType = InterfaceWrapperHelper.newInstance(I_C_DocType.class, contextProvider);
		docType.setAD_Org_ID(adOrg01.getAD_Org_ID());
		docType.setDocBaseType(docBaseType);
		docType.setName(docBaseType);

		InterfaceWrapperHelper.save(docType);
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
		wf.setWorkflowType(PPRoutingType.Manufacturing.getCode());
		wf.setDurationUnit(WFDurationUnit.Hour.getCode());
		InterfaceWrapperHelper.save(wf);
		return wf;
	}

	public void createWorkflowNode(
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

	}

	public I_PP_Order createPP_Order(
			final I_PP_Product_BOM productBOM,
			final String qtyOrderedStr,
			final I_C_UOM uom)
	{
		final I_PP_Order ppOrder = InterfaceWrapperHelper.newInstance(I_PP_Order.class, contextProvider);
		ppOrder.setAD_Org_ID(this.adOrg01.getAD_Org_ID());

		setCommonProperties(ppOrder);

		ppOrder.setM_Product_ID(productBOM.getM_Product_ID());
		ppOrder.setPP_Product_BOM_ID(productBOM.getPP_Product_BOM_ID());
		ppOrder.setAD_Workflow_ID(this.workflow_Standard.getAD_Workflow_ID());
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

	public I_PP_Product_BOMVersions createBOMVersions(final ProductId productId)
	{
		final I_PP_Product_BOMVersions bomVersions = InterfaceWrapperHelper.newInstance(I_PP_Product_BOMVersions.class, contextProvider);
		bomVersions.setM_Product_ID(productId.getRepoId());
		bomVersions.setName("BOM Versions");
		InterfaceWrapperHelper.save(bomVersions);
		return bomVersions;
	}

	private void setCommonProperties(final I_PP_Order ppOrder)
	{
		Services.get(IPPOrderBL.class).setDocType(ppOrder, PPOrderDocBaseType.MANUFACTURING_ORDER, null);

		// required to avoid an NPE when building the lightweight PPOrder pojo
		final Timestamp t1 = SystemTime.asTimestamp();
		ppOrder.setDateOrdered(t1);
		ppOrder.setDateStartSchedule(t1);
	}

}
