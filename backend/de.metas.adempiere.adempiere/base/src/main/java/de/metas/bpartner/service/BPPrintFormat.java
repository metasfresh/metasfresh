/**
 *
 */
package de.metas.bpartner.service;

import de.metas.bpartner.BPartnerId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

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

@Value
@Builder
public class BPPrintFormat
{
	@Getter
	private final BPartnerId bpartnerId;

	@Getter
	private final int docTypeId;

	@Getter
	private final int adTableId;

	@Getter
	private final int printFormatId;

	@Getter
	private final int bpPrintFormatId;

	@Builder(toBuilder = true)
	private BPPrintFormat(@NonNull final BPartnerId bpartnerId,
			final int docTypeId,
			final int adTableId,
			final int printFormatId,
			final int bpPrintFormatId)
	{
		Check.assume(docTypeId > 0, "docTypeId shall be > 0");
		Check.assume(adTableId > 0, "adTableId shall be > 0");
		Check.assume(printFormatId > 0, "printFormatId shall be > 0");

		this.bpartnerId = bpartnerId;
		this.docTypeId = docTypeId;
		this.adTableId = adTableId;
		this.printFormatId = printFormatId;
		this.bpPrintFormatId = bpPrintFormatId;
	}
}
