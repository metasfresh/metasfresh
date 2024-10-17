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
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

@Service
public class DocOutboundConfigService
{
	private final DocOutboundConfigRepository docOutboundConfigRepository;

	public DocOutboundConfigService(@NonNull DocOutboundConfigRepository docOutboundConfigRepository)
	{
		this.docOutboundConfigRepository = docOutboundConfigRepository;
	}

	@Nullable
	public DocOutboundConfigCC retrieveDocOutboundConfigCCByPrintFormatId(@NonNull final DocOutboundConfigId docOutboundConfigId, @NonNull PrintFormatId printFormatId)
	{
		final DocOutboundConfig config = docOutboundConfigRepository.getById(docOutboundConfigId);
		return config.getLines()
				.stream()
				.filter(configCC -> configCC.getPrintFormatId().getRepoId() == printFormatId.getRepoId())
				.findFirst()
				.map(configCC -> configCC)
				.orElse(null);

	}

}
