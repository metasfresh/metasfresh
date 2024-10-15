package de.metas.resource.process;

import de.metas.global_qrcodes.service.QRCodePDFResource;
import de.metas.process.JavaProcess;
import de.metas.product.ResourceId;
import org.compiere.SpringContextHolder;

public class S_Resource_PrintSingleQRCode extends JavaProcess
{
	private final ResourceQRCodePrintService resourceQRCodePrintService = SpringContextHolder.instance.getBean(ResourceQRCodePrintService.class);

	@Override
	protected String doIt()
	{
		final ResourceId resourceId = ResourceId.ofRepoId(getRecord_ID());
		final QRCodePDFResource pdf = resourceQRCodePrintService.createPDF(resourceId);
		getResult().setReportData(pdf, pdf.getFilename(), pdf.getContentType());

		return MSG_OK;
	}
}
