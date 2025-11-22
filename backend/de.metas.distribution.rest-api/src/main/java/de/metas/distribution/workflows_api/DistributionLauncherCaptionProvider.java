package de.metas.distribution.workflows_api;

import de.metas.common.util.pair.ImmutablePair;
import de.metas.distribution.config.DistributionJobCaptionFormat;
import de.metas.distribution.config.DistributionJobCaptionFormatItem;
import de.metas.distribution.config.MobileUIDistributionConfigRepository;
import de.metas.distribution.service.external.DistributionProductService;
import de.metas.distribution.service.external.DistributionSourceDocService;
import de.metas.distribution.service.external.DistributionWarehouseService;
import de.metas.gs1.GTIN;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStringBuilder;
import de.metas.i18n.TranslatableStrings;
import de.metas.organization.IOrgDAO;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import de.metas.workflow.rest_api.model.WorkflowLauncherCaption;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.warehouse.LocatorId;
import org.eevolution.model.X_DD_Order;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DistributionLauncherCaptionProvider
{
	@NonNull private final MobileUIDistributionConfigRepository configRepository;
	@NonNull private final DistributionWarehouseService warehouseService;
	@NonNull private final DistributionProductService productService;
	@NonNull private final DistributionSourceDocService sourceDocService;
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	private static final String CAPTION_SEPARATOR = " | ";

	public WorkflowLauncherCaption compute(@NonNull final DDOrderReference ddOrderReference)
	{
		final DistributionJobCaptionFormat format = configRepository.getConfig().getCaptionFormat();
		
		final TranslatableStringBuilder captionBuilder = TranslatableStrings.builder();

		for (final DistributionJobCaptionFormatItem formatItem : format.getItems())
		{
			final ITranslatableString captionItem = computeItem(ddOrderReference, formatItem);
			if (TranslatableStrings.isBlank(captionItem))
			{
				continue;
			}

			if (!captionBuilder.isEmpty())
			{
				captionBuilder.append(CAPTION_SEPARATOR);
			}
			captionBuilder.append(captionItem);
		}

		return WorkflowLauncherCaption.of(captionBuilder.build());
	}

	private ITranslatableString computeItem(@NonNull final DDOrderReference ddOrderReference, @NonNull final DistributionJobCaptionFormatItem formatItem)
	{
		switch (formatItem.getField())
		{
			case LocatorFrom:
				return extractLocatorFrom(ddOrderReference);
			case LocatorTo:
				return extractLocatorTo(ddOrderReference);
			case WarehouseFrom:
				return extractWarehouseFrom(ddOrderReference);
			case WarehouseTo:
				return extractWarehouseTo(ddOrderReference);
			case Plant:
				return extractPlant(ddOrderReference);
			case PickDate:
				return extractPickDate(ddOrderReference);
			case Qty:
				return extractQty(ddOrderReference);
			case ProductGTIN:
				return extractGTIN(ddOrderReference);
			case ProductValueAndName:
				return extractProductValueAndName(ddOrderReference);
			case SourceDoc:
				return extractSourceDoc(ddOrderReference);
			case Priority:
				return extractPriority(ddOrderReference);
			default:
				return TranslatableStrings.empty();
		}
	}

	private ITranslatableString extractLocatorFrom(final @NotNull DDOrderReference ddOrderReference)
	{
		final LocatorId fromLocatorId = ddOrderReference.getFromLocatorId();
		return fromLocatorId != null
				? TranslatableStrings.anyLanguage(warehouseService.getLocatorName(fromLocatorId))
				: TranslatableStrings.empty();
	}

	private ITranslatableString extractLocatorTo(final @NotNull DDOrderReference ddOrderReference)
	{
		final LocatorId toLocatorId = ddOrderReference.getToLocatorId();
		return toLocatorId != null
				? TranslatableStrings.anyLanguage(warehouseService.getLocatorName(toLocatorId))
				: TranslatableStrings.empty();
	}

	private static ITranslatableString extractPriority(final @NotNull DDOrderReference ddOrderReference)
	{
		final String priority = StringUtils.trimBlankToNull(ddOrderReference.getPriority());
		return priority != null
				? TranslatableStrings.adRefList(X_DD_Order.PRIORITYRULE_AD_Reference_ID, priority)
				: TranslatableStrings.empty();
	}

	private ITranslatableString extractWarehouseFrom(final @NotNull DDOrderReference ddOrderReference)
	{
		return TranslatableStrings.anyLanguage(warehouseService.getWarehouseName(ddOrderReference.getFromWarehouseId()));
	}

	private ITranslatableString extractWarehouseTo(final @NotNull DDOrderReference ddOrderReference)
	{
		return TranslatableStrings.anyLanguage(warehouseService.getWarehouseName(ddOrderReference.getToWarehouseId()));
	}

	private ITranslatableString extractPickDate(final @NotNull DDOrderReference ddOrderReference)
	{
		return TranslatableStrings.dateAndTime(ddOrderReference.getDisplayDate().toZonedDateTime(orgDAO::getTimeZone));
	}

	private ITranslatableString extractPlant(final @NotNull DDOrderReference ddOrderReference)
	{
		final ResourceId plantId = ddOrderReference.getPlantId();
		return plantId != null
				? TranslatableStrings.anyLanguage(sourceDocService.getPlantName(plantId))
				: TranslatableStrings.empty();
	}

	private static ITranslatableString extractQty(final @NotNull DDOrderReference ddOrderReference)
	{
		final Quantity qty = ddOrderReference.getQty();
		return qty != null
				? TranslatableStrings.builder().appendQty(qty.toBigDecimal(), qty.getUOMSymbol()).build()
				: TranslatableStrings.empty();
	}

	private ITranslatableString extractProductValueAndName(final @NotNull DDOrderReference ddOrderReference)
	{
		final ProductId productId = ddOrderReference.getProductId();
		return productId != null
				? TranslatableStrings.anyLanguage(productService.getProductValueAndName(productId))
				: TranslatableStrings.empty();
	}

	private @NotNull ITranslatableString extractGTIN(final @NotNull DDOrderReference ddOrderReference)
	{
		return Optional.ofNullable(ddOrderReference.getProductId())
				.flatMap(productService::getGTIN)
				.map(GTIN::getAsString)
				.map(TranslatableStrings::anyLanguage)
				.orElse(TranslatableStrings.empty());
	}

	@NonNull
	private ITranslatableString extractSourceDoc(@NonNull final DDOrderReference ddOrderReference)
	{
		ImmutablePair<ITranslatableString, String> documentTypeAndNo;
		if (ddOrderReference.getSalesOrderId() != null)
		{
			documentTypeAndNo = sourceDocService.getDocumentTypeAndName(ddOrderReference.getSalesOrderId());
		}
		else if (ddOrderReference.getPpOrderId() != null)
		{
			documentTypeAndNo = sourceDocService.getDocumentTypeAndName(ddOrderReference.getPpOrderId());
		}
		else
		{
			return TranslatableStrings.empty();
		}

		return TranslatableStrings.builder()
				.append(documentTypeAndNo.getLeft())
				.append(" ")
				.append(documentTypeAndNo.getRight())
				.build();
	}

}
