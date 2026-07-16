package de.metas.warehouse.process;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.global_qrcodes.PrintableQRCode;
import de.metas.global_qrcodes.service.GlobalQRCodeService;
import de.metas.global_qrcodes.service.QRCodePDFResource;
import de.metas.handlingunits.model.I_M_Warehouse;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.adempiere.warehouse.qrcode.LocatorQRCode;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Locator;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

public class M_Warehouse_PrintLocatorQRCodes extends JavaProcess implements IProcessDefaultParametersProvider
{
	// services
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	private final GlobalQRCodeService globalQRCodeService = SpringContextHolder.instance.getBean(GlobalQRCodeService.class);

	private static final String PARAM_M_Locator_ID = "M_Locator_ID";
	@Param(parameterName = PARAM_M_Locator_ID)
	private int p_M_Locator_ID = -1;

	private static final String PARAM_RenderedQRCode = "RenderedQRCode";

	@Override
	public @Nullable Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		final String parameterName = parameter.getColumnName();
		if (PARAM_M_Locator_ID.equals(parameterName))
		{
			return LocatorId.toRepoId(getSingleSelectedLocatorIdOrNull());
		}
		else if (PARAM_RenderedQRCode.equals(parameterName))
		{
			final LocatorId locatorId = getLocatorIdParameter();
			return locatorId != null
					? getQRCode(locatorId).toGlobalQRCodeJsonString()
					: null;
		}

		return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
	}

	@Override
	protected String doIt()
	{
		final ImmutableList<PrintableQRCode> qrCodes = getLocatorsToPrint()
				.stream()
				.map(LocatorQRCode::ofLocator)
				.map(LocatorQRCode::toPrintableQRCode)
				.collect(ImmutableList.toImmutableList());

		final QRCodePDFResource pdf = globalQRCodeService.createPDF(qrCodes);
		getResult().setReportData(pdf, pdf.getFilename(), pdf.getContentType());

		// getSelectedLocatorIds()

		return MSG_OK;
	}

	private List<I_M_Locator> getLocatorsToPrint()
	{
		final LocatorId locatorId = getLocatorIdParameter();
		if (locatorId != null)
		{
			return ImmutableList.of(warehouseDAO.getLocatorById(locatorId));
		}

		final Set<LocatorId> selectedLocatorIds = getSelectedLocatorIds();
		if (selectedLocatorIds.isEmpty())
		{
			final WarehouseId warehouseId = getWarehouseId();
			return warehouseDAO.getLocators(warehouseId);
		}
		else
		{
			return warehouseDAO.getLocatorByIds(selectedLocatorIds);
		}
	}

	private WarehouseId getWarehouseId()
	{
		return getRecordIdAssumingTableName(I_M_Warehouse.Table_Name, WarehouseId::ofRepoId);
	}

	@Nullable
	private LocatorId getLocatorIdParameter()
	{
		return LocatorId.ofRepoIdOrNull(getWarehouseId(), p_M_Locator_ID);
	}

	private Set<LocatorId> getSelectedLocatorIds()
	{
		final WarehouseId warehouseId = getWarehouseId();
		return getSelectedIncludedRecordIds(I_M_Locator.class)
				.stream()
				.map(locatorRepoId -> LocatorId.ofRepoId(warehouseId, locatorRepoId))
				.collect(ImmutableSet.toImmutableSet());
	}

	@Nullable
	private LocatorId getSingleSelectedLocatorIdOrNull()
	{
		final Set<LocatorId> selectedIncludedLocatorIds = getSelectedLocatorIds();
		return selectedIncludedLocatorIds.size() == 1
				? selectedIncludedLocatorIds.iterator().next()
				: null;
	}

	private LocatorQRCode getQRCode(@NonNull final LocatorId locatorId)
	{
		return LocatorQRCode.ofLocator(warehouseDAO.getLocatorById(locatorId));
	}
}
