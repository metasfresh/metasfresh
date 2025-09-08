package de.metas.resource.process;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.global_qrcodes.PrintableQRCode;
import de.metas.global_qrcodes.service.GlobalQRCodeService;
import de.metas.global_qrcodes.service.QRCodePDFResource;
import de.metas.material.planning.IResourceDAO;
import de.metas.product.ResourceId;
import de.metas.resource.qrcode.ResourceQRCode;
import de.metas.util.Services;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ResourceQRCodePrintService
{
	private final IResourceDAO resourceDAO = Services.get(IResourceDAO.class);
	private final GlobalQRCodeService globalQRCodeService;

	public ResourceQRCodePrintService(final @NonNull GlobalQRCodeService globalQRCodeService)
	{
		this.globalQRCodeService = globalQRCodeService;
	}

	public QRCodePDFResource createPDF(@NonNull final ResourceId resourceId)
	{
		return createPDF(ImmutableSet.of(resourceId));
	}

	public QRCodePDFResource createPDF(@NonNull final Set<ResourceId> resourceIds)
	{
		final ImmutableList<PrintableQRCode> printableQRCodes = resourceDAO.getByIds(resourceIds)
				.stream()
				.map(ResourceQRCode::ofResource)
				.map(ResourceQRCode::toPrintableQRCode)
				.collect(ImmutableList.toImmutableList());

		return globalQRCodeService.createPDF(printableQRCodes);
	}
}
