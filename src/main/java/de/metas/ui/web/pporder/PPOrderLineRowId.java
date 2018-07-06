package de.metas.ui.web.pporder;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.base.Preconditions;

import de.metas.ui.web.window.datatypes.DocumentId;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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
public class PPOrderLineRowId
{

	@Getter
	private final int ppOrderBomLineId;

	@Getter
	private final int ppOrderId;

	@Getter
	private final int huSourceId;

	@Getter
	private final int issuedOrReceivedHUId;

	@Getter
	private final PPOrderLineRowType type;

	@Getter
	private final DocumentId parentRowId;

	private transient DocumentId _documentId; // lazy

	@Builder
	private PPOrderLineRowId(
			final PPOrderLineRowType type,
			final DocumentId parentRowId,
			final int ppOrderBomLineId,
			final int ppOrderId,
			final int huSourceId,
			final int issuedOrReceivedHUId)
	{
		this.type = type;
		this.parentRowId = parentRowId;
		this.ppOrderBomLineId = ppOrderBomLineId > 0 ? ppOrderBomLineId : -1;
		this.ppOrderId = ppOrderId > 0 ? ppOrderId : -1;
		this.huSourceId = huSourceId > 0 ? huSourceId : -1;
		this.issuedOrReceivedHUId = issuedOrReceivedHUId > 0 ? issuedOrReceivedHUId : -1;
	}

	static final String PARTS_SEPARATOR = "-";

	public DocumentId toDocumentId()
	{
		DocumentId id = _documentId;
		if (id == null)
		{
			final StringBuilder sb = new StringBuilder();
			sb.append(type.getCode());

			if (type == PPOrderLineRowType.IssuedOrReceivedHU)
			{
				sb.append(PARTS_SEPARATOR).append(issuedOrReceivedHUId);
			}
			else if (type == PPOrderLineRowType.PP_Order)
			{
				sb.append(PARTS_SEPARATOR).append(ppOrderId);
			}
			else if (type == PPOrderLineRowType.PP_OrderBomLine)
			{
				sb.append(PARTS_SEPARATOR).append(ppOrderBomLineId);
			}
			else if (type == PPOrderLineRowType.Source_HU)
			{
				sb.append(PARTS_SEPARATOR).append(huSourceId);
			}
			else
			{
				throw new AdempiereException("Type " + type + " is not supported");
			}

			id = DocumentId.ofString(sb.toString());
		}
		return id;
	}

	public static final PPOrderLineRowId ofPPOrderBomLineId(int ppOrderBomLineId)
	{
		Preconditions.checkArgument(ppOrderBomLineId > 0, "ppOrderBomLineId > 0");
		return new PPOrderLineRowId(PPOrderLineRowType.PP_OrderBomLine, null, ppOrderBomLineId, -1, -1, -1);
	}

	public static PPOrderLineRowId ofPPOrderId(int ppOrderId)
	{
		Preconditions.checkArgument(ppOrderId > 0, "ppOrderId > 0");
		return new PPOrderLineRowId(PPOrderLineRowType.PP_Order, null, -1, ppOrderId, -1, -1);
	}

	public static PPOrderLineRowId ofIssuedOrReceivedHU(@NonNull DocumentId parentRowId, int huId)
	{
		Preconditions.checkArgument(huId > 0, "huId > 0");
		return new PPOrderLineRowId(PPOrderLineRowType.IssuedOrReceivedHU, parentRowId, -1, -1, -1, huId);
	}

	public static PPOrderLineRowId ofSourceHU(@NonNull DocumentId parentRowId, int sourceHuId)
	{
		Preconditions.checkArgument(sourceHuId > 0, "huId > 0");
		return new PPOrderLineRowId(PPOrderLineRowType.Source_HU, parentRowId, -1, -1, sourceHuId, -1);
	}

}
