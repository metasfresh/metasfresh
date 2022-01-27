package de.metas.handlingunits.qrcodes.service;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.HUQRCodeAssignment;
import de.metas.handlingunits.qrcodes.model.HUQRCodePackingInfo;
import de.metas.handlingunits.qrcodes.model.HUQRCodeProductInfo;
import de.metas.handlingunits.qrcodes.model.HUQRCodeUniqueId;
import de.metas.handlingunits.qrcodes.model.HUQRCodeUnitType;
import de.metas.handlingunits.qrcodes.model.json.HUQRCodeJsonConverter;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_M_Product;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class HUQRCodesService
{
	@NonNull private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	@NonNull private final HUQRCodesRepository huQRCodesRepository;

	private static final String SYSCONFIG_GenerateQRCodeIfMissing = "de.metas.handlingunits.qrcodes.GenerateQRCodeIfMissing";

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

	public HuId getHuIdByQRCode(@NonNull final String qrCodeString)
	{
		return getHuIdByQRCodeIfExists(qrCodeString)
				.orElseThrow(() -> new AdempiereException("No HU attached to QR Code " + qrCodeString));
	}

	public Optional<HuId> getHuIdByQRCodeIfExists(@NonNull final String qrCodeString)
	{
		final HUQRCode qrCode = HUQRCodeJsonConverter.fromQRCodeString(qrCodeString);
		return getHuIdByQRCodeIfExists(qrCode);
	}

	public Optional<HuId> getHuIdByQRCodeIfExists(@NonNull final HUQRCode qrCode)
	{
		return getHUAssignmentByQRCode(qrCode).map(HUQRCodeAssignment::getHuId);
	}

	public Optional<HUQRCodeAssignment> getHUAssignmentByQRCode(@NonNull final HUQRCode huQRCode)
	{
		return huQRCodesRepository.getHUAssignmentByQRCode(huQRCode);
	}

	public HUQRCode getQRCodeByHuId(@NonNull final HuId huId)
	{
		return getQRCodeByHuIdIfExists(huId)
				.orElseThrow(() -> new AdempiereException("No QR Code attacjed to HU " + huId.getRepoId()));
	}

	public Optional<HUQRCode> getQRCodeByHuIdIfExists(@NonNull final HuId huId)
	{
		final Optional<HUQRCode> optionalQRCode = huQRCodesRepository.getQRCodeByHuId(huId);

		if (optionalQRCode.isPresent())
		{
			return optionalQRCode;
		}
		else if (sysConfigBL.getBooleanValue(SYSCONFIG_GenerateQRCodeIfMissing, true))
		{
			return Optional.of(generateQRCodeForExistingHU(huId));
		}
		else
		{
			return Optional.empty();
		}
	}

	private HUQRCode generateQRCodeForExistingHU(@NonNull final HuId huId)
	{
		final I_M_HU hu = handlingUnitsBL.getById(huId);
		final I_M_HU_PI_Version piVersion = handlingUnitsBL.getPIVersion(hu);
		final I_M_HU_PI pi = handlingUnitsBL.getPI(piVersion);

		final ProductId productId = handlingUnitsBL.getStorageFactory().getStorage(hu).getSingleProductIdOrNull();
		if (productId == null)
		{
			throw new AdempiereException("Only single product storages are supported");
		}

		final I_M_Product product = productBL.getById(productId);

		final HUQRCode huQRCode = HUQRCode.builder()
				.id(HUQRCodeUniqueId.ofUUID(UUID.randomUUID()))
				.packingInfo(HUQRCodePackingInfo.builder()
						.huUnitType(HUQRCodeUnitType.ofCode(piVersion.getHU_UnitType()))
						.packingInstructionsId(HuPackingInstructionsId.ofRepoId(piVersion.getM_HU_PI_ID()))
						.caption(pi.getName())
						.build())
				.product(HUQRCodeProductInfo.builder()
						.id(productId)
						.code(product.getValue())
						.name(product.getName())
						.build())
				.attributes(ImmutableList.of())
				.build();

		huQRCodesRepository.createNew(huQRCode, HUQRCodeAssignment.ofHuId(huId, huQRCode.getId()));

		return huQRCode;
	}

}
