/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.ui.web.order.attachmenteditor;

import com.google.common.collect.ImmutableList;
import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentLinksRequest;
import de.metas.attachments.AttachmentTags;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValues;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValuesHolder;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.ViewEditorRenderMode;
import de.metas.ui.web.window.descriptor.WidgetSize;
import de.metas.vertical.healthcare.alberta.model.I_Alberta_PrescriptionRequest;
import de.metas.vertical.healthcare.alberta.model.I_C_BPartner_AlbertaPatient;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_AttachmentEntry;
import org.compiere.model.I_C_Order;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static de.metas.attachments.AttachmentTags.TAGNAME_SEND_VIA_EMAIL;

public class OrderAttachmentRow implements IViewRow
{
	private static final String SYS_CONFIG_PREFIX = "de.metas.ui.web.order.attachmenteditor.OrderAttachmentRow.field";

	public static final String FIELD_AttachmentId = "AttachmentId";
	@Getter
	@ViewColumn(seqNo = 10, widgetType = DocumentFieldWidgetType.YesNo,
			widgetSize = WidgetSize.Small,
			captionKey = "AD_AttachmentEntry_ID",
			fieldName = FIELD_AttachmentId,
			editor = ViewEditorRenderMode.NEVER)
	private final Integer attachmentEntryId;
		
	public static final String FIELD_IsDirectlyAttachToPurchaseOrder = "IsDirectlyAttachToPurchaseOrder";
	@Getter
	@ViewColumn(seqNo = 15, widgetType = DocumentFieldWidgetType.YesNo,
			widgetSize = WidgetSize.Small,
			captionKey = FIELD_IsDirectlyAttachToPurchaseOrder,
			fieldName = FIELD_IsDirectlyAttachToPurchaseOrder,
			editor = ViewEditorRenderMode.NEVER)
	private final Boolean isDirectlyAttachToPurchaseOrder;

	public static final String FIELD_IsAttachToPurchaseOrder = "IsAttachToPurchaseOrder";
	@Getter
	@ViewColumn(seqNo = 20, widgetType = DocumentFieldWidgetType.YesNo,
			widgetSize = WidgetSize.Small,
			captionKey = "IsAttachToPurchaseOrder",
			fieldName = FIELD_IsAttachToPurchaseOrder,
			editor = ViewEditorRenderMode.ALWAYS)
	private final Boolean isAttachToPurchaseOrder;

	@ViewColumn(seqNo = 30, widgetType = DocumentFieldWidgetType.Lookup,
			widgetSize = WidgetSize.Small,
			captionKey = I_Alberta_PrescriptionRequest.COLUMNNAME_C_BPartner_Patient_ID,
			displayed = ViewColumn.ViewColumnLayout.Displayed.SYSCONFIG,
			displayedSysConfigPrefix = SYS_CONFIG_PREFIX,
			fieldName = I_Alberta_PrescriptionRequest.COLUMNNAME_C_BPartner_Patient_ID,
			editor = ViewEditorRenderMode.NEVER)
	@Getter
	private final LookupValue patient;

	@ViewColumn(seqNo = 40, widgetType = DocumentFieldWidgetType.Lookup,
			widgetSize = WidgetSize.Small,
			captionKey = I_C_BPartner_AlbertaPatient.COLUMNNAME_C_BPartner_Payer_ID,
			displayed = ViewColumn.ViewColumnLayout.Displayed.SYSCONFIG,
			displayedSysConfigPrefix = SYS_CONFIG_PREFIX,
			fieldName = I_C_BPartner_AlbertaPatient.COLUMNNAME_C_BPartner_Payer_ID,
			editor = ViewEditorRenderMode.NEVER)
	@Getter
	private final LookupValue payer;

	@ViewColumn(seqNo = 50, widgetType = DocumentFieldWidgetType.Lookup,
			widgetSize = WidgetSize.Small, captionKey = I_Alberta_PrescriptionRequest.COLUMNNAME_C_BPartner_Pharmacy_ID,
			displayed = ViewColumn.ViewColumnLayout.Displayed.SYSCONFIG,
			displayedSysConfigPrefix = SYS_CONFIG_PREFIX,
			fieldName = I_Alberta_PrescriptionRequest.COLUMNNAME_C_BPartner_Pharmacy_ID,
			editor = ViewEditorRenderMode.NEVER)
	@Getter
	private final LookupValue pharmacy;

	@ViewColumn(seqNo = 60, widgetType = DocumentFieldWidgetType.ZonedDateTime,
			widgetSize = WidgetSize.Small,
			captionKey = I_C_Order.COLUMNNAME_DatePromised,
			editor = ViewEditorRenderMode.NEVER)
	@Getter
	private final ZonedDateTime datePromised;

	@ViewColumn(seqNo = 70, widgetType = DocumentFieldWidgetType.Text,
			widgetSize = WidgetSize.Small,
			captionKey = I_AD_AttachmentEntry.COLUMNNAME_FileName,
			editor = ViewEditorRenderMode.NEVER)
	@Getter
	private final String filename;

	private final I_C_Order selectedPurchaseOrder;

	private final AttachmentEntry attachmentEntry;
	private final DocumentId rowId;

	private final ViewRowFieldNameAndJsonValuesHolder<OrderAttachmentRow> values;

	@Builder(toBuilder = true)
	private OrderAttachmentRow(
			@NonNull final Boolean isAttachToPurchaseOrder,
			@Nullable final LookupValue patient,
			@NonNull final I_C_Order selectedPurchaseOrder,
			@NonNull final AttachmentEntry attachmentEntry,
			@Nullable final LookupValue payer,
			@Nullable final LookupValue pharmacy,
			@Nullable final ZonedDateTime datePromised,
			@Nullable final String filename,
			@NonNull final DocumentId rowId)
	{
		this.attachmentEntryId = attachmentEntry.getId().getRepoId();
		this.isAttachToPurchaseOrder = isAttachToPurchaseOrder;
		this.isDirectlyAttachToPurchaseOrder = attachmentEntry.hasLinkToRecord(TableRecordReference.of(selectedPurchaseOrder));
		this.patient = patient;
		this.payer = payer;
		this.pharmacy = pharmacy;
		this.datePromised = datePromised;
		this.filename = filename;
		this.selectedPurchaseOrder = selectedPurchaseOrder;
		this.attachmentEntry = attachmentEntry;
		this.rowId = rowId;

		values = ViewRowFieldNameAndJsonValuesHolder.newInstance(OrderAttachmentRow.class);
	}

	@Override
	public DocumentId getId()
	{
		return rowId;
	}

	@Override
	public boolean isProcessed()
	{
		return false;
	}

	@Nullable
	@Override
	public DocumentPath getDocumentPath()
	{
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

	public OrderAttachmentRow withChanges(@NonNull final Boolean updatedIsAttachPurchaseOrderFlag)
	{
		final OrderAttachmentRow.OrderAttachmentRowBuilder rowBuilder = toBuilder();

		rowBuilder.isAttachToPurchaseOrder(updatedIsAttachPurchaseOrderFlag);

		return rowBuilder.build();
	}

	Optional<AttachmentLinksRequest> toAttachmentLinksRequest()
	{
		final Map<String, String> emailAttachmentTagAsMap = new HashMap<>();
		emailAttachmentTagAsMap.put(TAGNAME_SEND_VIA_EMAIL, Boolean.TRUE.toString());

		final AttachmentTags emailAttachmentTag = AttachmentTags.ofMap(emailAttachmentTagAsMap);

		final TableRecordReference purchaseOrderRecordRef = getPurchaseOrderRecordRef();

		if (isAttachToPurchaseOrder)
		{
			return Optional.of(AttachmentLinksRequest.builder()
									   .attachmentEntryId(attachmentEntry.getId())
									   .linksToAdd(ImmutableList.of(purchaseOrderRecordRef))
									   .tagsToAdd(emailAttachmentTag)
									   .build());
		}
		else if (isDirectlyAttachToPurchaseOrder)
		{
			return isAttachmentLinkedOnlyToPO()
					? Optional.of(AttachmentLinksRequest.builder()
										  .attachmentEntryId(attachmentEntry.getId())
										  .tagsToRemove(emailAttachmentTag)
										  .build())

					: Optional.of(AttachmentLinksRequest.builder()
										  .attachmentEntryId(attachmentEntry.getId())
										  .linksToRemove(ImmutableList.of(purchaseOrderRecordRef))
										  .tagsToRemove(emailAttachmentTag)
										  .build());
		}

		return Optional.empty();
	}

	private boolean isAttachmentLinkedOnlyToPO()
	{
		return attachmentEntry.getLinkedRecords().size() == 1
				&& attachmentEntry.getLinkedRecords().contains(getPurchaseOrderRecordRef());
	}

	private TableRecordReference getPurchaseOrderRecordRef()
	{
		return TableRecordReference.of(I_C_Order.Table_Name, selectedPurchaseOrder.getC_Order_ID());
	}
}
