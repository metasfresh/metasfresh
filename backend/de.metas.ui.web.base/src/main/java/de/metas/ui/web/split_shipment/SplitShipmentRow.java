package de.metas.ui.web.split_shipment;

import com.google.common.collect.ImmutableMap;
import de.metas.inoutcandidate.split.ShipmentScheduleSplitId;
import de.metas.quantity.Quantity;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValues;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValuesHolder;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.ViewEditorRenderMode;
import de.metas.ui.web.window.descriptor.WidgetSize;
import de.metas.util.Check;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

@ToString
public class SplitShipmentRow implements IViewRow
{
	public static SplitShipmentRow cast(final IViewRow row) {return (SplitShipmentRow)row;}

	public static final String FIELD_DeliveryDate = "DeliveryDate";
	@ViewColumn(seqNo = 10, widgetType = DocumentFieldWidgetType.LocalDate, widgetSize = WidgetSize.Small, fieldName = FIELD_DeliveryDate)
	@Nullable @Getter private final LocalDate deliveryDate;

	public static final String FIELD_QtyToDeliver = "QtyToDeliver";
	@ViewColumn(seqNo = 20, widgetType = DocumentFieldWidgetType.Quantity, widgetSize = WidgetSize.Small, fieldName = FIELD_QtyToDeliver)
	@NonNull @Getter private final Quantity qtyToDeliver;

	public static final String FIELD_UserElementNumber1 = "UserElementNumber1";
	@ViewColumn(seqNo = 30, widgetType = DocumentFieldWidgetType.Number, widgetSize = WidgetSize.Small, fieldName = FIELD_UserElementNumber1)
	@Nullable @Getter private final BigDecimal userElementNumber1;

	public static final String FIELD_UserElementNumber2 = "UserElementNumber2";
	@ViewColumn(seqNo = 40, widgetType = DocumentFieldWidgetType.Number, widgetSize = WidgetSize.Small, fieldName = FIELD_UserElementNumber2)
	@Nullable @Getter private final BigDecimal userElementNumber2;

	private final boolean readonly;

	@NonNull private final DocumentId rowId;
	@Nullable @Getter private final ShipmentScheduleSplitId shipmentScheduleSplitId;

	private final ViewRowFieldNameAndJsonValuesHolder<SplitShipmentRow> values;
	private static final ImmutableMap<String, ViewEditorRenderMode> EDITABLE = ImmutableMap.<String, ViewEditorRenderMode>builder()
			.put(FIELD_DeliveryDate, ViewEditorRenderMode.ALWAYS)
			.put(FIELD_QtyToDeliver, ViewEditorRenderMode.ALWAYS)
			.put(FIELD_UserElementNumber1, ViewEditorRenderMode.ALWAYS)
			.put(FIELD_UserElementNumber2, ViewEditorRenderMode.ALWAYS)
			.build();
	private static final ImmutableMap<String, ViewEditorRenderMode> READONLY = ImmutableMap.<String, ViewEditorRenderMode>builder()
			.put(FIELD_DeliveryDate, ViewEditorRenderMode.ALWAYS)
			.put(FIELD_QtyToDeliver, ViewEditorRenderMode.ALWAYS)
			.put(FIELD_UserElementNumber1, ViewEditorRenderMode.ALWAYS)
			.put(FIELD_UserElementNumber2, ViewEditorRenderMode.ALWAYS)
			.build();

	@Builder(toBuilder = true)
	private SplitShipmentRow(
			@Nullable final ShipmentScheduleSplitId shipmentScheduleSplitId,
			final boolean readonly,
			@NonNull final DocumentId rowId,
			//
			@Nullable final LocalDate deliveryDate,
			@NonNull final Quantity qtyToDeliver,
			@Nullable final BigDecimal userElementNumber1,
			@Nullable final BigDecimal userElementNumber2)
	{
		this.deliveryDate = deliveryDate;
		this.qtyToDeliver = qtyToDeliver;
		this.userElementNumber1 = userElementNumber1;
		this.userElementNumber2 = userElementNumber2;

		this.readonly = readonly;
		this.rowId = rowId;
		this.shipmentScheduleSplitId = shipmentScheduleSplitId;

		this.values = ViewRowFieldNameAndJsonValuesHolder.newInstance(SplitShipmentRow.class);
	}

	@Override
	public DocumentId getId() {return rowId;}

	@Override
	public boolean isProcessed() {return readonly;}

	@Nullable
	@Override
	public DocumentPath getDocumentPath() {return null;}

	@Override
	public Set<String> getFieldNames() {return values.getFieldNames();}

	@Override
	public ViewRowFieldNameAndJsonValues getFieldNameAndJsonValues() {return values.get(this);}

	@Override
	public Map<String, ViewEditorRenderMode> getViewEditorRenderModeByFieldName() {return readonly ? READONLY : EDITABLE;}

	public boolean isNewLine()
	{
		return shipmentScheduleSplitId == null;
	}

	public boolean isSaveable()
	{
		return deliveryDate != null 
				&& qtyToDeliver.signum() != 0 
				&& userElementNumber1 !=null
				&& userElementNumber2 !=null;
	}

	public boolean isDeletable()
	{
		return !isProcessed();
	}

	@NonNull
	public LocalDate getDeliveryDateNotNull()
	{
		return Check.assumeNotNull(getDeliveryDate(), "expected deliveryDate to be set: {}", this);
	}

	public SplitShipmentRow withChanges(final List<JSONDocumentChangedEvent> fieldChangeRequests)
	{
		assertEditable();

		final SplitShipmentRowBuilder builder = toBuilder();
		for (final JSONDocumentChangedEvent request : fieldChangeRequests)
		{
			final String field = request.getPath();
			switch (field)
			{
				case FIELD_DeliveryDate -> builder.deliveryDate(request.getValueAsLocalDate());
				case FIELD_QtyToDeliver -> builder.qtyToDeliver(Quantity.of(request.getValueAsBigDecimal(BigDecimal.ZERO), this.qtyToDeliver.getUOM()));
				case FIELD_UserElementNumber1 -> builder.userElementNumber1(request.getValueAsBigDecimal());
				case FIELD_UserElementNumber2 -> builder.userElementNumber2(request.getValueAsBigDecimal());
				default -> throw new AdempiereException("Unknown field: " + request.getPath());
			}
		}

		final SplitShipmentRow changedRow = builder.build();
		changedRow.assertValid();
		return changedRow;
	}

	private void assertValid()
	{
		if (qtyToDeliver.signum() < 0)
		{
			throw new AdempiereException("@Qty@ < 0");
		}
	}

	private void assertEditable()
	{
		if (readonly)
		{
			throw new AdempiereException("@Processed@");
		}
	}

	public boolean isEligibleForProcessing()
	{
		return !readonly && shipmentScheduleSplitId != null;
	}
}
