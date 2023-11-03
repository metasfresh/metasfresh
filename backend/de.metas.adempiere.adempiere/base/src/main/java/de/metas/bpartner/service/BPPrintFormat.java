/**
 *
 */
package de.metas.bpartner.service;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.document.DocTypeId;
import de.metas.report.PrintCopies;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.table.api.AdTableId;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

/**
 * @author metas-dev <dev@metasfresh.com>
 *
 */

@Getter
@Value
@Builder
public class BPPrintFormat
{
	@NonNull
	BPartnerId bpartnerId;

	@Nullable
	DocTypeId docTypeId;

	@Nullable
	AdTableId adTableId;

	int printFormatId;

	int bpPrintFormatId;

	@Nullable
	BPartnerLocationId bPartnerLocationId;

	@Nullable
	PrintCopies printCopies;

	@Builder(toBuilder = true)
	private BPPrintFormat(
			@NonNull final BPartnerId bpartnerId,
			@Nullable final DocTypeId docTypeId,
			@Nullable final AdTableId adTableId,
			final int printFormatId,
			final int bpPrintFormatId,
			@Nullable final BPartnerLocationId bPartnerLocationId,
			@Nullable final PrintCopies printCopies)
	{
		this.bpartnerId = bpartnerId;
		this.docTypeId = docTypeId;
		this.adTableId = adTableId;
		this.printFormatId = printFormatId;
		this.bpPrintFormatId = bpPrintFormatId;
		this.bPartnerLocationId = bPartnerLocationId;
		this.printCopies = printCopies;
	}
}
