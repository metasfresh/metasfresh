package de.metas.resource.process;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.global_qrcodes.PrintableQRCode;
import de.metas.global_qrcodes.service.GlobalQRCodeService;
import de.metas.global_qrcodes.service.QRCodePDFResource;
import de.metas.product.ResourceId;
import de.metas.resource.Resource;
import de.metas.resource.ResourceService;
import de.metas.resource.qrcode.ResourceQRCode;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ResourceQRCodePrintService
{
	private final ResourceService resourceService;
	private final GlobalQRCodeService globalQRCodeService;

	public ResourceQRCodePrintService(
			final @NonNull ResourceService resourceService,
			final @NonNull GlobalQRCodeService globalQRCodeService)
	{
		this.resourceService = resourceService;
		this.globalQRCodeService = globalQRCodeService;
	}

	public QRCodePDFResource createPDF(@NonNull final ResourceId resourceId)
	{
		return createPDF(ImmutableSet.of(resourceId));
	}

	public QRCodePDFResource createPDF(@NonNull final Set<ResourceId> resourceIds)
	{
		final ImmutableList<PrintableQRCode> printableQRCodes = resourceService.getResourcesByIds(resourceIds)
				.stream()
				.map(ResourceQRCode::ofResource)
				.map(ResourceQRCode::toPrintableQRCode)
				.collect(ImmutableList.toImmutableList());

		return globalQRCodeService.createPDF(printableQRCodes);
	}
}
