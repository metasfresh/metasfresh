package de.metas.handlingunits.qrcodes.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.global_qrcodes.service.GlobalQRCodeService;
import de.metas.global_qrcodes.service.QRCodePDFResource;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.HUQRCodeAssignment;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HUQRCodesService
{
	@NonNull private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	@NonNull private final HUQRCodesRepository huQRCodesRepository;
	@NonNull private final GlobalQRCodeService globalQRCodeService;

	private static final String SYSCONFIG_GenerateQRCodeIfMissing = "de.metas.handlingunits.qrcodes.GenerateQRCodeIfMissing";

	public HUQRCodesService(
			final @NonNull HUQRCodesRepository huQRCodesRepository,
			final @NonNull GlobalQRCodeService globalQRCodeService)
	{
		this.huQRCodesRepository = huQRCodesRepository;
		this.globalQRCodeService = globalQRCodeService;
	}

	public List<HUQRCode> generate(HUQRCodeGenerateRequest request)
	{
		return HUQRCodeGenerateCommand.builder()
				.request(request)
				.build()
				.execute();
	}

	public HUQRCodeGenerateForExistingHUsResult generateForExistingHUs(HUQRCodeGenerateForExistingHUsRequest request)
	{
		return HUQRCodeGenerateForExistingHUsCommand.builder()
				.huQRCodesRepository(huQRCodesRepository)
				.request(request)
				.build()
				.execute();
	}

	public QRCodePDFResource createPDF(@NonNull final List<HUQRCode> qrCodes)
	{
		return globalQRCodeService.createPDF(
				qrCodes.stream()
						.map(HUQRCode::toPrintableQRCode)
						.collect(ImmutableList.toImmutableList()));
	}

	public void print(@NonNull final List<HUQRCode> qrCodes)
	{
		print(createPDF(qrCodes));
	}

	public void print(@NonNull final QRCodePDFResource pdf)
	{
		globalQRCodeService.print(pdf);
	}

	public HuId getHuIdByQRCode(@NonNull final String qrCodeString)
	{
		return getHuIdByQRCodeIfExists(qrCodeString)
				.orElseThrow(() -> new AdempiereException("No HU attached to QR Code " + qrCodeString));
	}

	public Optional<HuId> getHuIdByQRCodeIfExists(@NonNull final String qrCodeString)
	{
		final HUQRCode qrCode = HUQRCode.fromGlobalQRCodeJsonString(qrCodeString);
		return getHuIdByQRCodeIfExists(qrCode);
	}

	public Optional<HuId> getHuIdByQRCodeIfExists(@NonNull final HUQRCode qrCode)
	{
		return getHUAssignmentByQRCode(qrCode)
				.map(HUQRCodeAssignment::getHuId);
	}

	public Optional<HUQRCodeAssignment> getHUAssignmentByQRCode(@NonNull final HUQRCode huQRCode)
	{
		return huQRCodesRepository.getHUAssignmentByQRCode(huQRCode);
	}

	public HUQRCode getQRCodeByHuId(@NonNull final HuId huId)
	{
		final HUQRCode existingQRCode = getFirstQRCodeByHuIdIfExists(huId).orElse(null);
		if (existingQRCode != null)
		{
			return existingQRCode;
		}
		else if (sysConfigBL.getBooleanValue(SYSCONFIG_GenerateQRCodeIfMissing, true))
		{
			return generateForExistingHUs(
					HUQRCodeGenerateForExistingHUsRequest.builder()
							.huIds(ImmutableSet.of(huId))
							.build())
					.getSingleQRCode(huId);
		}
		else
		{
			throw new AdempiereException("No QR Code attached to HU " + huId.getRepoId());
		}
	}

	public Optional<HUQRCode> getFirstQRCodeByHuIdIfExists(@NonNull final HuId huId)
	{
		return huQRCodesRepository.getFirstQRCodeByHuId(huId);
	}

	public void assign(@NonNull HUQRCode qrCode, @NonNull HuId huId)
	{
		huQRCodesRepository.assign(qrCode, huId);
	}
}
