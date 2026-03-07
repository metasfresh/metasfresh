package de.metas.distribution.mobileui.launchers;

import de.metas.common.util.pair.ImmutablePair;
import de.metas.distribution.mobileui.config.DistributionJobCaptionField;
import de.metas.distribution.mobileui.config.DistributionJobCaptionFormat;
import de.metas.distribution.mobileui.config.DistributionJobCaptionFormatItem;
import de.metas.distribution.mobileui.config.DistributionJobSorting;
import de.metas.distribution.mobileui.config.DistributionJobSortingField;
import de.metas.distribution.mobileui.config.DistributionJobSortingItem;
import de.metas.distribution.mobileui.config.MobileUIDistributionConfig;
import de.metas.distribution.mobileui.config.MobileUIDistributionConfigRepository;
import de.metas.distribution.mobileui.external_services.product.DistributionProductService;
import de.metas.distribution.mobileui.external_services.sourcedoc.DistributionSourceDocService;
import de.metas.distribution.mobileui.external_services.warehouse.DistributionWarehouseService;
import de.metas.distribution.mobileui.job.model.DDOrderReference;
import de.metas.gs1.GTIN;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.quantity.Quantity;
import de.metas.util.StringUtils;
import de.metas.workflow.rest_api.model.WorkflowLauncherCaption;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.warehouse.LocatorId;
import org.eevolution.model.X_DD_Order;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DistributionLauncherCaptionProvider
{
	@NonNull private final MobileUIDistributionConfigRepository configRepository;
	@NonNull private final DistributionWarehouseService warehouseService;
	@NonNull private final DistributionProductService productService;
	@NonNull private final DistributionSourceDocService sourceDocService;

	public WorkflowLauncherCaption compute(@NonNull final DDOrderReference ddOrderReference)
	{
		final MobileUIDistributionConfig config = configRepository.getConfig();
		final DistributionJobCaptionFormat format = config.getCaptionFormat();

		@NonNull final ArrayList<String> fieldsInOrder = new ArrayList<>();
		@NonNull final HashMap<String, ITranslatableString> fieldValues = new HashMap<>();
		@NonNull final HashMap<String, Comparable<?>> comparingKeys = new HashMap<>();

		for (final DistributionJobCaptionFormatItem formatItem : format.getItems())
		{
			final DistributionJobCaptionField field = formatItem.getField();
			final ITranslatableString captionItem = computeItem(ddOrderReference, field);
			fieldsInOrder.add(field.getCode());
			fieldValues.put(field.getCode(), captionItem);
		}

		final DistributionJobSorting sorting = config.getSorting();
		for (DistributionJobSortingItem item : sorting.getItems())
		{
			final DistributionJobSortingField field = item.getField();

			if (!fieldValues.containsKey(field.getCode()))
			{
				final ITranslatableString captionItem = computeItem(ddOrderReference, field);
				fieldValues.put(field.getCode(), captionItem);
			}

			final Comparable<?> comparableKey = computeComparableKey(ddOrderReference, field);
			if (comparableKey != null)
			{
				comparingKeys.put(field.getCode(), comparableKey);
			}

		}

		return WorkflowLauncherCaption.builder()
				.fieldsInOrder(fieldsInOrder)
				.fieldValues(fieldValues)
				.comparingKeys(comparingKeys)
				.build();
	}

	private ITranslatableString computeItem(@NonNull final DDOrderReference ddOrderReference, @NonNull final DistributionJobCaptionField field)
	{
		switch (field)
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
			case PickingInstruction:
				return extractPickingInstruction(ddOrderReference);
			default:
				return TranslatableStrings.empty();
		}
	}

	private ITranslatableString computeItem(final @NonNull DDOrderReference ddOrderReference, final DistributionJobSortingField field)
	{
		switch (field)
		{
			case Priority:
				return extractPriority(ddOrderReference);
			case DatePromised:
				return extractPickDate(ddOrderReference);
			case SeqNo:
				return TranslatableStrings.number(ddOrderReference.getSeqNo().toInt());
			default:
				return TranslatableStrings.empty();
		}
	}

	private Comparable<?> computeComparableKey(final @NonNull DDOrderReference ddOrderReference, final DistributionJobSortingField field)
	{
		if (Objects.requireNonNull(field) == DistributionJobSortingField.SeqNo)
		{
			return ddOrderReference.getSeqNo();
		}
		else
		{
			return null;
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
		return TranslatableStrings.dateAndTime(ddOrderReference.getDisplayDate());
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

	@NonNull
	private ITranslatableString extractPickingInstruction(final @NonNull DDOrderReference ddOrderReference)
	{
		final ITranslatableString pickingInstruction = ddOrderReference.getPickingInstruction();
		return pickingInstruction != null ? pickingInstruction : TranslatableStrings.empty();
	}
}
