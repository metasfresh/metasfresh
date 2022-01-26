package de.metas.handlingunits.qrcodes.service;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import lombok.NonNull;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HUQRCodesService
{
	@NonNull private final HUQRCodesRepository huQRCodesRepository;

	public HUQRCodesService(final @NonNull HUQRCodesRepository huQRCodesRepository) {this.huQRCodesRepository = huQRCodesRepository;}

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

	public Optional<HuId> getHUIdByQRCode(@NonNull final HUQRCode huQRCode)
	{
		return huQRCodesRepository.getHUIdByQRCode(huQRCode);
	}
}
