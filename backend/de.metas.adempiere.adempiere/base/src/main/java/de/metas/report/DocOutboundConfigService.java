/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.report;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.table.api.AdTableId;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class DocOutboundConfigService
{
	@NonNull
	private final DocOutboundConfigRepository docOutboundConfigRepository;

	public DocOutboundConfig getById(@NonNull final DocOutboundConfigId id)
	{
		return docOutboundConfigRepository.getById(id);
	}

	@Nullable
	public DocOutboundConfig getByTableId(@NonNull final Properties ctx, @NonNull final AdTableId adTableId)
	{
		return docOutboundConfigRepository.retrieveConfig(ctx, adTableId);
	}

	public Collection<PrintFormatId> getAllPrintFormatIds(final DocOutboundConfigId docOutboundConfigId)
	{
		return docOutboundConfigRepository.retrieveAllPrintFormatIds(docOutboundConfigId);
	}
}
