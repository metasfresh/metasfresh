/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.bpartner.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.document.DocTypeId;
import de.metas.report.PrintFormatId;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.table.api.AdTableId;

import java.util.List;
import java.util.Optional;

@ToString
public class BPartnerPrintFormatMap
{
	public static BPartnerPrintFormatMap ofList(@NonNull final List<BPartnerPrintFormat> list)
	{
		return !list.isEmpty() ? new BPartnerPrintFormatMap(list) : EMPTY;
	}

	private static BPartnerPrintFormatMap EMPTY = new BPartnerPrintFormatMap(ImmutableList.of());

	private final ImmutableMap<DocTypeId, BPartnerPrintFormat> byDocTypeId;

	private BPartnerPrintFormatMap(@NonNull final List<BPartnerPrintFormat> list)
	{
		byDocTypeId = Maps.uniqueIndex(list, BPartnerPrintFormat::getDocTypeId);
	}

	public Optional<PrintFormatId> getPrintFormatIdByDocTypeId(@NonNull final DocTypeId docTypeId)
	{
		return Optional.ofNullable(byDocTypeId.get(docTypeId))
				.map(BPartnerPrintFormat::getPrintFormatId);
	}

	public Optional<PrintFormatId> getFirstByTableId(@NonNull final AdTableId tableId)
	{
		return byDocTypeId.values()
				.stream()
				.filter(item -> AdTableId.equals(item.getAdTableId(), tableId))
				.findFirst()
				.map(BPartnerPrintFormat::getPrintFormatId);
	}

}
