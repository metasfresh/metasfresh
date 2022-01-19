package de.metas.handlingunits.qrcodes.service;

import de.metas.handlingunits.qrcodes.model.HUQRCode;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HUQRCodesService
{
	public List<HUQRCode> generate(HUQRCodeGenerateRequest request)
	{
		return HUQRCodeGenerateCommand.builder()
				.request(request)
				.build()
				.execute();
	}

	public void print(final List<HUQRCode> qrCodes)
	{
		createPDF(qrCodes, true);
	}

	public Resource createPDF(final List<HUQRCode> qrCodes, final boolean sendToPrinter)
	{
		return HUQRCodeCreatePDFCommand.builder()
				.qrCodes(qrCodes)
				.sendToPrinter(sendToPrinter)
				.build()
				.execute();
	}
}
