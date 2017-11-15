package de.metas.ui.web.order.sales.purchasePlanning.view;

import java.util.List;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.base.Splitter;

import de.metas.ui.web.window.datatypes.DocumentId;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

@ToString
@EqualsAndHashCode
public final class PurchaseRowId
{
	public static PurchaseRowId groupId(final int salesOrderLineId)
	{
		final int vendorBPartnerId = -1;
		final DocumentId documentId = null;
		return new PurchaseRowId(salesOrderLineId, vendorBPartnerId, documentId);
	}

	public static PurchaseRowId lineId(final int salesOrderLineId, final int vendorBPartnerId)
	{
		final DocumentId documentId = null;
		return new PurchaseRowId(salesOrderLineId, vendorBPartnerId, documentId);
	}

	public static PurchaseRowId fromDocumentId(final DocumentId documentId)
	{
		return fromJson(documentId.toJson(), documentId);
	}

	public static final PurchaseRowId fromJson(final String json)
	{
		final DocumentId documentId = null;
		return fromJson(json, documentId);
	}

	private static final PurchaseRowId fromJson(final String json, final DocumentId documentId)
	{
		final List<String> parts = PARTS_SPLITTER.splitToList(json);
		final int partsCount = parts.size();
		if (partsCount < 1 || partsCount > 2)
		{
			throw new AdempiereException("Invalid format: " + json);
		}

		try
		{
			final int salesOrderLineId = Integer.parseInt(parts.get(0));
			final int vendorBPartnerId = partsCount == 2 ? Integer.parseInt(parts.get(1)) : -1;
			return new PurchaseRowId(salesOrderLineId, vendorBPartnerId, documentId);
		}
		catch (Exception ex)
		{
			throw new AdempiereException("Cannot convert '" + json + "' to " + PurchaseRowId.class, ex);
		}
	}

	private static final String PARTS_SEPARATOR = "_";
	private static final Splitter PARTS_SPLITTER = Splitter.on(PARTS_SEPARATOR).omitEmptyStrings();

	private final int salesOrderLineId;
	private final int vendorBPartnerId;

	private transient DocumentId _documentId; // lazy

	private PurchaseRowId(final int salesOrderLineId, final int vendorBPartnerId, final DocumentId documentId)
	{
		if (salesOrderLineId <= 0)
		{
			throw new AdempiereException("Invalid salesOrderLineId: " + salesOrderLineId);
		}
		this.salesOrderLineId = salesOrderLineId;
		this.vendorBPartnerId = vendorBPartnerId > 0 ? vendorBPartnerId : -1;
		this._documentId = documentId;
	}

	public DocumentId toDocumentId()
	{
		if (_documentId == null)
		{
			if (vendorBPartnerId > 0)
			{
				_documentId = DocumentId.ofString(salesOrderLineId + PARTS_SEPARATOR + vendorBPartnerId);
			}
			else
			{
				_documentId = DocumentId.of(salesOrderLineId);
			}
		}
		return _documentId;
	}

	public PurchaseRowId toGroupRowId()
	{
		if (vendorBPartnerId <= 0)
		{
			return this;
		}
		else
		{
			return new PurchaseRowId(salesOrderLineId, -1, null);
		}
	}
	
	public boolean isGroupRowId()
	{
		return vendorBPartnerId <= 0;
	}
}
