package de.metas.warehouse.process;

import com.google.common.collect.ImmutableList;
import de.metas.global_qrcodes.PrintableQRCode;
import de.metas.global_qrcodes.service.GlobalQRCodeService;
import de.metas.global_qrcodes.service.QRCodePDFResource;
import de.metas.process.JavaProcess;
import de.metas.util.Services;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.adempiere.warehouse.qrcode.LocatorQRCode;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Locator;

import java.util.List;

public class M_Warehouse_PrintLocatorQRCodes extends JavaProcess
{
	// services
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	private final GlobalQRCodeService globalQRCodeService = SpringContextHolder.instance.getBean(GlobalQRCodeService.class);
	
	@Override
	protected String doIt()
	{
		final ImmutableList<PrintableQRCode> qrCodes = getSelectedLocators()
				.stream()
				.map(LocatorQRCode::ofLocator)
				.map(LocatorQRCode::toPrintableQRCode)
				.collect(ImmutableList.toImmutableList());

		final QRCodePDFResource pdf = globalQRCodeService.createPDF(qrCodes);
		getResult().setReportData(pdf, pdf.getFilename(), pdf.getContentType());

		return MSG_OK;
	}

	private List<I_M_Locator> getSelectedLocators()
	{
		final WarehouseId warehouseId = WarehouseId.ofRepoId(getRecord_ID());
		return warehouseDAO.getLocators(warehouseId);
	}
}
