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
import de.metas.distribution.mobileui.external_services.sourcedoc.PlantInfo;
import de.metas.distribution.mobileui.external_services.warehouse.DistributionWarehouseService;
import de.metas.distribution.mobileui.external_services.warehouse.LocatorInfo;
import de.metas.distribution.mobileui.external_services.warehouse.WarehouseInfo;
import de.metas.distribution.mobileui.job.model.DDOrderReference;
import de.metas.distribution.mobileui.job.model.DistributionJob;
import de.metas.gs1.GTIN;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.order.OrderId;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.quantity.Quantity;
import de.metas.util.StringUtils;
import de.metas.util.lang.SeqNo;
import de.metas.workflow.rest_api.model.WorkflowLauncherCaption;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.X_DD_Order;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
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
		return compute(toContext(ddOrderReference));
	}

	private WorkflowLauncherCaption compute(@NonNull final Context context)
	{
		final MobileUIDistributionConfig config = configRepository.getConfig();
		final DistributionJobCaptionFormat format = config.getCaptionFormat();

		@NonNull final ArrayList<String> fieldsInOrder = new ArrayList<>();
		@NonNull final HashMap<String, ITranslatableString> fieldValues = new HashMap<>();
		@NonNull final HashMap<String, Comparable<?>> comparingKeys = new HashMap<>();

		for (final DistributionJobCaptionFormatItem formatItem : format.getItems())
		{
			final DistributionJobCaptionField field = formatItem.getField();
			final ITranslatableString captionItem = computeItem(context, field);
			fieldsInOrder.add(field.getCode());
			fieldValues.put(field.getCode(), captionItem);
		}

		final DistributionJobSorting sorting = config.getSorting();
		for (DistributionJobSortingItem item : sorting.getItems())
		{
			final DistributionJobSortingField field = item.getField();

			if (!fieldValues.containsKey(field.getCode()))
			{
				final ITranslatableString captionItem = computeItem(context, field);
				fieldValues.put(field.getCode(), captionItem);
			}

			final Comparable<?> comparableKey = computeComparableKey(context, field);
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

	public ITranslatableString computeItem(@NonNull final DistributionJob job, @NonNull final DistributionJobCaptionField field)
	{
		final Context context = toContext(job);
		return computeItem(context, field);
	}

	private ITranslatableString computeItem(@NonNull final Context context, @NonNull final DistributionJobCaptionField field)
	{
		switch (field)
		{
			case LocatorFrom:
				return extractLocatorFrom(context);
			case LocatorTo:
				return extractLocatorTo(context);
			case WarehouseFrom:
				return extractPickFromWarehouse(context);
			case WarehouseTo:
				return extractDropToWarehouse(context);
			case Plant:
				return extractPlant(context);
			case PickDate:
				return TranslatableStrings.dateAndTime(context.getDisplayDate());
			case Qty:
				return extractQty(context);
			case ProductGTIN:
				return extractGTIN(context);
			case ProductValueAndName:
				return extractProductValueAndName(context);
			case SourceDoc:
				return extractSourceDoc(context);
			case Priority:
				return extractPriority(context);
			case PickingInstruction:
				return extractPickingInstruction(context);
			default:
				return TranslatableStrings.empty();
		}
	}

	@NonNull
	private ITranslatableString computeItem(final @NonNull Context context, final DistributionJobSortingField field)
	{
		switch (field)
		{
			case Priority:
				return extractPriority(context);
			case LocatorPriority:
				return TranslatableStrings.number(context.getFromLocatorPriorityNo());
			case DatePromised:
				return TranslatableStrings.dateAndTime(context.getDisplayDate());
			case SeqNo:
				return TranslatableStrings.number(context.getSeqNo().toInt());
			default:
				return TranslatableStrings.empty();
		}
	}

	private Comparable<?> computeComparableKey(@NonNull final Context context, @NonNull final DistributionJobSortingField field)
	{
		if (field == DistributionJobSortingField.SeqNo)
		{
			return context.getSeqNo();
		}
		else if (field == DistributionJobSortingField.LocatorPriority)
		{
			return context.getFromLocatorPriorityNo();
		}
		else
		{
			return null;
		}
	}

	private ITranslatableString extractLocatorFrom(@NonNull final Context context)
	{
		final LocatorInfo fromLocator = context.getFromLocator();
		return fromLocator != null
				? TranslatableStrings.anyLanguage(fromLocator.getCaption())
				: TranslatableStrings.empty();
	}

	private ITranslatableString extractLocatorTo(@NonNull final Context context)
	{
		final LocatorId toLocatorId = context.getToLocatorId();
		return toLocatorId != null
				? TranslatableStrings.anyLanguage(warehouseService.getLocatorName(toLocatorId))
				: TranslatableStrings.empty();
	}

	@NonNull
	private static ITranslatableString extractPriority(@NonNull final Context context)
	{
		final String priority = StringUtils.trimBlankToNull(context.getPriority());
		return priority != null
				? TranslatableStrings.adRefList(X_DD_Order.PRIORITYRULE_AD_Reference_ID, priority)
				: TranslatableStrings.empty();
	}

	private ITranslatableString extractPickFromWarehouse(@NonNull final Context context)
	{
		if (context.getPickFromWarehouse() != null)
		{
			return TranslatableStrings.anyLanguage(context.getPickFromWarehouse().getCaption());
		}
		else if (context.getPickFromWarehouseId() != null)
		{
			return TranslatableStrings.anyLanguage(warehouseService.getWarehouseName(context.getPickFromWarehouseId()));
		}
		else
		{
			return TranslatableStrings.empty();
		}
	}

	private ITranslatableString extractDropToWarehouse(@NonNull final Context context)
	{
		if (context.getDropToWarehouse() != null)
		{
			return TranslatableStrings.anyLanguage(context.getDropToWarehouse().getCaption());
		}
		else if (context.getDropToWarehouseId() != null)
		{
			return TranslatableStrings.anyLanguage(warehouseService.getWarehouseName(context.getDropToWarehouseId()));
		}
		else
		{
			return TranslatableStrings.empty();
		}
	}

	private ITranslatableString extractPlant(@NonNull final Context context)
	{
		if (context.getPlant() != null)
		{
			return TranslatableStrings.anyLanguage(context.getPlant().getCaption());
		}
		else if (context.getPlantId() != null)
		{
			return TranslatableStrings.anyLanguage(sourceDocService.getPlantName(context.getPlantId()));
		}
		else
		{
			return TranslatableStrings.empty();
		}
	}

	private static ITranslatableString extractQty(@NonNull final Context context)
	{
		final Quantity qty = context.getQty();
		return qty != null
				? TranslatableStrings.builder().appendQty(qty.toBigDecimal(), qty.getUOMSymbol()).build()
				: TranslatableStrings.empty();
	}

	private ITranslatableString extractProductValueAndName(@NonNull final Context context)
	{
		final ProductId productId = context.getProductId();
		return productId != null
				? TranslatableStrings.anyLanguage(productService.getProductValueAndName(productId))
				: TranslatableStrings.empty();
	}

	private @NotNull ITranslatableString extractGTIN(@NonNull final Context context)
	{
		return Optional.ofNullable(context.getProductId())
				.flatMap(productService::getGTIN)
				.map(GTIN::getAsString)
				.map(TranslatableStrings::anyLanguage)
				.orElse(TranslatableStrings.empty());
	}

	@NonNull
	private ITranslatableString extractSourceDoc(@NonNull final Context context)
	{
		ImmutablePair<ITranslatableString, String> documentTypeAndNo;
		if (context.getSalesOrderId() != null)
		{
			documentTypeAndNo = sourceDocService.getDocumentTypeAndName(context.getSalesOrderId());
		}
		else if (context.getManufacturingOrderId() != null)
		{
			documentTypeAndNo = sourceDocService.getDocumentTypeAndName(context.getManufacturingOrderId());
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
	private ITranslatableString extractPickingInstruction(@NonNull final Context context)
	{
		final ITranslatableString pickingInstruction = context.getPickingInstruction();
		return pickingInstruction != null ? pickingInstruction : TranslatableStrings.empty();
	}

	private Context toContext(@NonNull DDOrderReference ddOrderReference)
	{
		final LocatorId fromLocatorId = ddOrderReference.getFromLocatorId();
		final LocatorInfo fromLocator = fromLocatorId != null
				? warehouseService.getLocatorInfoById(fromLocatorId)
				: null;

		return Context.builder()
				.seqNo(ddOrderReference.getSeqNo())
				.displayDate(ddOrderReference.getDisplayDate())
				.pickingInstruction(ddOrderReference.getPickingInstruction())
				.pickFromWarehouseId(ddOrderReference.getFromWarehouseId())
				.fromLocator(fromLocator)
				.dropToWarehouseId(ddOrderReference.getToWarehouseId())
				.toLocatorId(ddOrderReference.getToLocatorId())
				.manufacturingOrderId(ddOrderReference.getPpOrderId())
				.salesOrderId(ddOrderReference.getSalesOrderId())
				.productId(ddOrderReference.getProductId())
				.qty(ddOrderReference.getQty())
				.plantId(ddOrderReference.getPlantId())
				.priority(ddOrderReference.getPriority())
				.build();
	}

	private Context toContext(@NonNull DistributionJob job)
	{
		final LocatorId fromLocatorId = job.getSinglePickFromLocatorIdOrNull();
		final LocatorInfo fromLocator = fromLocatorId != null
				? warehouseService.getLocatorInfoById(fromLocatorId)
				: null;

		return Context.builder()
				.seqNo(job.getSeqNo())
				.displayDate(job.getPickDate())
				.pickingInstruction(job.getPickingInstruction())
				.pickFromWarehouse(job.getPickFromWarehouse())
				.fromLocator(fromLocator)
				.dropToWarehouse(job.getDropToWarehouse())
				.toLocatorId(job.getSingleDropToLocatorIdOrNull())
				.manufacturingOrderId(job.getManufacturingOrderRef() != null ? job.getManufacturingOrderRef().getId() : null)
				.salesOrderId(job.getSalesOrderRef() != null ? job.getSalesOrderRef().getId() : null)
				.productId(job.getSingleProductIdOrNull())
				.qty(job.getSingleUnitQuantityOrNull())
				.plant(job.getPlantInfo())
				.priority(job.getPriority())
				.build();
	}

	//
	//
	//
	//
	//

	@Value
	@Builder
	private static class Context
	{
		// @NonNull DDOrderId ddOrderId;
		// @NonNull String documentNo;
		@NonNull SeqNo seqNo;
		// @NonNull ZonedDateTime datePromised;
		// @Nullable ZonedDateTime pickDate;
		@NonNull ZonedDateTime displayDate;
		@Nullable ITranslatableString pickingInstruction;
		@Nullable WarehouseInfo pickFromWarehouse;
		@Nullable WarehouseId pickFromWarehouseId;
		@Nullable LocatorInfo fromLocator;
		@Nullable WarehouseInfo dropToWarehouse;
		@Nullable WarehouseId dropToWarehouseId;
		@Nullable LocatorId toLocatorId;
		@Nullable PPOrderId manufacturingOrderId;
		@Nullable OrderId salesOrderId;
		@Nullable ProductId productId;
		@Nullable Quantity qty;
		@Nullable PlantInfo plant;
		@Nullable ResourceId plantId;
		@Nullable String priority;

		public int getFromLocatorPriorityNo()
		{
			return fromLocator != null ? fromLocator.getPriorityNo() : Integer.MAX_VALUE;
		}
	}
}
