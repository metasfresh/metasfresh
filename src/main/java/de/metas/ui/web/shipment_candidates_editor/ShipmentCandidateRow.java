package de.metas.ui.web.shipment_candidates_editor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Set;

import javax.annotation.Nullable;

import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.util.ASIEditingInfo;
import org.adempiere.mm.attributes.util.ASIEditingInfo.WindowType;

import de.metas.inoutcandidate.api.ShipmentScheduleId;
import de.metas.lang.SOTrx;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.ui.web.pattribute.WebuiASIEditingInfo;
import de.metas.ui.web.pattribute.WebuiASIEditingInfoAware;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValues;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValuesHolder;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.ViewEditorRenderMode;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class ShipmentCandidateRow implements IViewRow, WebuiASIEditingInfoAware
{
	@ViewColumn(seqNo = 10, widgetType = DocumentFieldWidgetType.Lookup, captionKey = "C_OrderSO_ID")
	private final LookupValue salesOrder;

	@ViewColumn(seqNo = 20, widgetType = DocumentFieldWidgetType.Lookup, captionKey = "C_BPartner_Customer_ID")
	private final LookupValue customer;

	@ViewColumn(seqNo = 30, widgetType = DocumentFieldWidgetType.Lookup, captionKey = "M_Warehouse_ID")
	private final LookupValue warehouse;

	@ViewColumn(seqNo = 40, widgetType = DocumentFieldWidgetType.Lookup, captionKey = "M_Product_ID")
	private final LookupValue product;

	@ViewColumn(seqNo = 50, widgetType = DocumentFieldWidgetType.ZonedDateTime, captionKey = "PreparationDate")
	private final ZonedDateTime preparationDate;

	public static final String FIELD_qtyToDeliver = "qtyToDeliver";
	@ViewColumn(seqNo = 60, widgetType = DocumentFieldWidgetType.Quantity, fieldName = FIELD_qtyToDeliver, captionKey = "QtyToDeliver", editor = ViewEditorRenderMode.ALWAYS)
	private final BigDecimal qtyToDeliver;

	public static final String FIELD_asi = "asi";
	@ViewColumn(seqNo = 70, widgetType = DocumentFieldWidgetType.ProductAttributes, fieldName = FIELD_asi, captionKey = "M_AttributeSetInstance_ID", editor = ViewEditorRenderMode.ALWAYS)
	private final LookupValue asi;

	private final ShipmentScheduleId shipmentScheduleId;
	private final DocumentId rowId;
	private final Quantity qtyToDeliverInitial;

	private final ViewRowFieldNameAndJsonValuesHolder<ShipmentCandidateRow> values;

	@Builder(toBuilder = true)
	private ShipmentCandidateRow(
			@NonNull final ShipmentScheduleId shipmentScheduleId,
			@Nullable final LookupValue salesOrder,
			@NonNull final LookupValue customer,
			@NonNull final LookupValue warehouse,
			@NonNull final LookupValue product,
			@NonNull final ZonedDateTime preparationDate,
			@NonNull final Quantity qtyToDeliverInitial,
			@NonNull final BigDecimal qtyToDeliver,
			@NonNull final LookupValue asi)
	{
		this.salesOrder = salesOrder;
		this.customer = customer;
		this.warehouse = warehouse;
		this.product = product;
		this.preparationDate = preparationDate;
		this.qtyToDeliverInitial = qtyToDeliverInitial;
		this.qtyToDeliver = qtyToDeliver;
		this.asi = asi;

		this.shipmentScheduleId = shipmentScheduleId;
		rowId = DocumentId.of(shipmentScheduleId);

		values = ViewRowFieldNameAndJsonValuesHolder.newInstance(ShipmentCandidateRow.class);
	}

	@Override
	public DocumentId getId()
	{
		return rowId;
	}

	@Override
	public boolean isProcessed()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public DocumentPath getDocumentPath()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> getFieldNames()
	{
		return values.getFieldNames();
	}

	@Override
	public ViewRowFieldNameAndJsonValues getFieldNameAndJsonValues()
	{
		return values.get(this);
	}

	public ShipmentCandidateRow withChanges(@NonNull final ShipmentCandidateRowUserChangeRequest userChanges)
	{
		final ShipmentCandidateRowBuilder builder = toBuilder();

		if (userChanges.getQtyToDeliver() != null)
		{
			builder.qtyToDeliver(userChanges.getQtyToDeliver());
		}
		else if (userChanges.getAsi() != null)
		{
			builder.asi(userChanges.getAsi());
		}

		return builder.build();
	}

	@Override
	public WebuiASIEditingInfo getWebuiASIEditingInfo(@NonNull final AttributeSetInstanceId asiId)
	{
		final ProductId productId = product.getIdAs(ProductId::ofRepoIdOrNull);

		final ASIEditingInfo info = ASIEditingInfo.builder()
				.type(WindowType.Regular)
				.productId(productId)
				.attributeSetInstanceId(asiId)
				.callerTableName(null)
				.callerColumnId(-1)
				.soTrx(SOTrx.SALES)
				.build();

		return WebuiASIEditingInfo.builder(info).build();
	}
}
