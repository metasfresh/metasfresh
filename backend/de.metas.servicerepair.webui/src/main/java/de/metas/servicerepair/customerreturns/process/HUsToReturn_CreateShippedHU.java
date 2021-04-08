package de.metas.servicerepair.customerreturns.process;

import com.google.common.base.Suppliers;
import de.metas.bpartner.BPartnerId;
import de.metas.common.util.time.SystemTime;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.allocation.IHUProducerAllocationDestination;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.GenericAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.allocation.impl.HUProducerDestination;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.storage.impl.PlainProductStorage;
import de.metas.inout.IInOutBL;
import de.metas.inout.InOutId;
import de.metas.process.IADPInstanceDAO;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessParametersCallout;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.servicerepair.customerreturns.AlreadyShippedHUsInPreviousSystem;
import de.metas.servicerepair.customerreturns.HUsToReturnViewContext;
import de.metas.servicerepair.customerreturns.RepairCustomerReturnsService;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_PInstance;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_InOut;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

public class HUsToReturn_CreateShippedHU
		extends HUsToReturnViewBasedProcess
		implements IProcessPrecondition, IProcessDefaultParametersProvider, IProcessParametersCallout
{
	private final RepairCustomerReturnsService repairCustomerReturnsService = SpringContextHolder.instance.getBean(RepairCustomerReturnsService.class);
	private final IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);
	private final IInOutBL inoutBL = Services.get(IInOutBL.class);
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	private final IProductBL productBL = Services.get(IProductBL.class);

	//
	// parameters
	private static final String PARAM_SerialNo = "SerialNo";
	@Param(parameterName = PARAM_SerialNo, mandatory = true)
	private String serialNo;

	public static final String PARAM_M_Product_ID = "M_Product_ID";
	@Param(parameterName = PARAM_M_Product_ID, mandatory = true)
	private ProductId productId;

	public static final String PARAM_WarrantyStartDate = "WarrantyStartDate";
	@Param(parameterName = PARAM_WarrantyStartDate)
	@Nullable private LocalDate warrantyStartDate;

	public static final String PARAM_C_BPartner_Customer_ID = "C_BPartner_Customer_ID";
	@Param(parameterName = PARAM_C_BPartner_Customer_ID)
	@Nullable private BPartnerId customerId;

	//
	// state
	private final Supplier<AlreadyShippedHUsInPreviousSystem> defaultParamsSuppler = Suppliers.memoize(this::computeDefaultParamsOrNull);
	private I_M_InOut _customerReturns; // lazy

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (getView().size() > 0)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Available only when there are no rows");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Nullable
	@Override
	public Object getParameterDefaultValue(@NonNull final IProcessDefaultParameter parameter)
	{
		final AlreadyShippedHUsInPreviousSystem defaultParams = defaultParamsSuppler.get();
		if(defaultParams != null)
		{
			final String columnName = parameter.getColumnName();
			if (PARAM_SerialNo.equals(columnName))
			{
				return defaultParams.getSerialNo();
			}
			else if(PARAM_M_Product_ID.equals(columnName))
			{
				return defaultParams.getProductId();
			}
			else if(PARAM_WarrantyStartDate.equals(columnName))
			{
				return defaultParams.getWarrantyStartDate();
			}
			else if(PARAM_C_BPartner_Customer_ID.equals(columnName))
			{
				return defaultParams.getCustomerId();
			}
		}

		return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
	}

	@Nullable
	private AlreadyShippedHUsInPreviousSystem computeDefaultParamsOrNull()
	{
		return getSerialNoFromViewFilters()
				.flatMap(repairCustomerReturnsService::searchAlreadyShippedHUsInPreviousSystemRepositoryBySerialNo)
				.orElse(null);
	}

	private Optional<String> getSerialNoFromViewFilters()
	{
		return getView()
				.getFilters()
				.stream()
				.map(filter -> filter.getParameterOrNull(PARAM_SerialNo))
				.filter(Objects::nonNull)
				.map(filterParam -> StringUtils.trimBlankToNull(filterParam.getValueAsString()))
				.filter(Objects::nonNull)
				.findFirst();
	}

	@Override
	public void onParameterChanged(final String parameterName)
	{
		if (PARAM_SerialNo.equals(parameterName))
		{
			final AlreadyShippedHUsInPreviousSystem result = repairCustomerReturnsService
					.searchAlreadyShippedHUsInPreviousSystemRepositoryBySerialNo(serialNo)
					.orElse(null);
			if (result != null)
			{
				productId = result.getProductId();
				customerId = result.getCustomerId();
				warrantyStartDate = result.getWarrantyStartDate();
			}
		}
	}

	@Override
	@RunOutOfTrx
	protected String doIt()
	{
		final HuId huId = huTrxBL.createHUContextProcessorExecutor().call(this::createHU);
		getView().addHUIdAndInvalidate(huId);
		return MSG_OK;
	}

	private HuId createHU(@NonNull final IHUContext huContext)
	{
		final I_C_UOM productStockingUOM = productBL.getStockUOM(productId);
		// final I_M_InOut referenceModel = getCustomerReturns(); // using customer returns just because we need to use a referenced model
		final I_AD_PInstance referencedModel = Services.get(IADPInstanceDAO.class).getById(getPinstanceId());

		final IHUProducerAllocationDestination destination;
		HULoader.builder()
				.source(new GenericAllocationSourceDestination(
						new PlainProductStorage(productId, Quantity.of(1, productStockingUOM)),
						referencedModel
				))
				.destination(destination = HUProducerDestination.ofVirtualPI()
						.setHUStatus(X_M_HU.HUSTATUS_Shipped)
						.setBPartnerId(customerId)
						.setLocatorId(getLocatorId()))
				.load(AllocationUtils.builder()
						.setHUContext(huContext)
						.setProduct(productId)
						.setQuantity(Quantity.of(1, productStockingUOM))
						.setDate(SystemTime.asZonedDateTime())
						.setForceQtyAllocation(true)
						.setFromReferencedModel(referencedModel)
						.create());

		final I_M_HU vhu = destination.getSingleCreatedHU()
				.orElseThrow(() -> new AdempiereException("No HU created"));

		final IAttributeStorage vhuAttributes = huContext.getHUAttributeStorageFactory()
				.getAttributeStorage(vhu);
		vhuAttributes.setValue(AttributeConstants.ATTR_SerialNo, serialNo);
		if (vhuAttributes.hasAttribute(AttributeConstants.WarrantyStartDate))
		{
			vhuAttributes.setValue(AttributeConstants.WarrantyStartDate, warrantyStartDate);
		}
		vhuAttributes.saveChangesIfNeeded();

		return HuId.ofRepoId(vhu.getM_HU_ID());
	}

	private LocatorId getLocatorId()
	{
		final I_M_InOut customerReturns = getCustomerReturns();
		final WarehouseId warehouseId = WarehouseId.ofRepoId(customerReturns.getM_Warehouse_ID());
		return warehouseBL.getDefaultLocatorId(warehouseId);
	}

	private I_M_InOut getCustomerReturns()
	{
		I_M_InOut customerReturns = this._customerReturns;
		if (customerReturns == null)
		{
			final HUsToReturnViewContext husToReturnViewContext = getHUsToReturnViewContext();
			final InOutId customerReturnsId = husToReturnViewContext.getCustomerReturnsId();
			customerReturns = this._customerReturns = inoutBL.getById(customerReturnsId);
		}
		return customerReturns;
	}
}
