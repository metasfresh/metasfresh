package de.metas.handlingunits.process;

import de.metas.global_qrcodes.service.QRCodePDFResource;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodeGenerateRequest;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.product.ProductId;
import de.metas.util.Services;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Product;

import java.util.List;

public class GenerateHUQRCodes extends JavaProcess
{
	private final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
	private final HUQRCodesService huQRCodesService = SpringContextHolder.instance.getBean(HUQRCodesService.class);

	@Param(parameterName = I_M_HU_PI.COLUMNNAME_M_HU_PI_ID)
	private HuPackingInstructionsId huPackingInstructionsId;
	@Param(parameterName = I_M_Product.COLUMNNAME_M_Product_ID, mandatory = true)
	private ProductId productId;
	@Param(parameterName = "LabelsCount", mandatory = true)
	private int labelsCount;
	@Param(parameterName = "Lot")
	private String lotNo;

	@Override
	protected String doIt()
	{
		final List<HUQRCode> qrCodes = huQRCodesService.generate(
				HUQRCodeGenerateRequest.builder()
						.count(labelsCount)
						.huPackingInstructionsId(huPackingInstructionsId)
						.productId(productId)
						.lotNo(lotNo, attributeDAO::getAttributeIdByCode)
						.build());

		final QRCodePDFResource pdf = huQRCodesService.createPDF(qrCodes);
		getResult().setReportData(pdf);

		return MSG_OK;
	}
}
