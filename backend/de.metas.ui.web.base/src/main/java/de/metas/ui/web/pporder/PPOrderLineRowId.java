package de.metas.ui.web.pporder;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import de.metas.handlingunits.HuId;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.eevolution.api.PPOrderBOMLineId;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

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

	@Getter private final int recordId;
	@NonNull @Getter private final PPOrderLineRowType type;
	@Nullable @Getter private final DocumentId parentRowId;

	private transient DocumentId _documentId; // lazy

	@Builder
	private PPOrderLineRowId(
			@NonNull final PPOrderLineRowType type,
			@Nullable final DocumentId parentRowId,
			final int recordId)
	{
		Check.assumeGreaterThanZero(recordId, "recordId");

		this.type = type;
		this.parentRowId = parentRowId;
		this.recordId = recordId;
	}

	static final String PARTS_SEPARATOR = "-";
	private static final Splitter PARTS_SPLITTER = Splitter.on(PARTS_SEPARATOR).omitEmptyStrings();

	public DocumentId toDocumentId()
	{
		DocumentId documentId = this._documentId;
		if (documentId == null)
		{
			final String sb = type.getCode()
					+ PARTS_SEPARATOR + parentRowId
					+ PARTS_SEPARATOR + recordId;

			documentId = this._documentId = DocumentId.ofString(sb);
		}
		return documentId;
	}

	public static PPOrderLineRowId fromDocumentId(final DocumentId documentId)
	{
		final List<String> parts = PARTS_SPLITTER.splitToList(documentId.toJson());
		return fromStringPartsList(parts);
	}

	private static PPOrderLineRowId fromStringPartsList(final List<String> parts)
	{
		final int partsCount = parts.size();
		if (partsCount < 1)
		{
			throw new IllegalArgumentException("Invalid id: " + parts);
		}

		final PPOrderLineRowType type = PPOrderLineRowType.forCode(parts.get(0));
		final DocumentId parentRowId = !Check.isEmpty(parts.get(1), true) ? DocumentId.of(parts.get(1)) : null;
		final int recordId = Integer.parseInt(parts.get(2));

		return new PPOrderLineRowId(type, parentRowId, recordId);
	}

	public static PPOrderLineRowId ofPPOrderBomLineId(int ppOrderBomLineId)
	{
		Preconditions.checkArgument(ppOrderBomLineId > 0, "ppOrderBomLineId > 0");
		return new PPOrderLineRowId(PPOrderLineRowType.PP_OrderBomLine, null, ppOrderBomLineId);
	}

	public static PPOrderLineRowId ofPPOrderId(int ppOrderId)
	{
		Preconditions.checkArgument(ppOrderId > 0, "ppOrderId > 0");
		return new PPOrderLineRowId(PPOrderLineRowType.PP_Order, null, ppOrderId);
	}

	public static PPOrderLineRowId ofIssuedOrReceivedHU(@Nullable DocumentId parentRowId, @NonNull final HuId huId)
	{
		return new PPOrderLineRowId(PPOrderLineRowType.IssuedOrReceivedHU, parentRowId, huId.getRepoId());
	}

	public static PPOrderLineRowId ofSourceHU(@NonNull DocumentId parentRowId, @NonNull final HuId sourceHuId)
	{
		return new PPOrderLineRowId(PPOrderLineRowType.Source_HU, parentRowId, sourceHuId.getRepoId());
	}

	public Optional<PPOrderBOMLineId> getPPOrderBOMLineIdIfApplies()
	{
		return type.isPP_OrderBomLine() ? PPOrderBOMLineId.optionalOfRepoId(recordId) : Optional.empty();
	}
}
