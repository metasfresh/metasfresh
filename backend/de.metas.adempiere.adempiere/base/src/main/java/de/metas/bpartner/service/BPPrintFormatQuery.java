/**
 *
 */
package de.metas.bpartner.service;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.document.DocTypeId;
import de.metas.report.PrintFormatId;
import lombok.Builder;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * @author metas-dev <dev@metasfresh.com>
 *
 */

@Value
@Builder(toBuilder = true)
public class BPPrintFormatQuery
{
	@NonNull
	BPartnerId bpartnerId;

	@Nullable
	DocTypeId docTypeId;

	@Nullable
	AdTableId adTableId;

	@Nullable
	PrintFormatId printFormatId;

	@Nullable
	BPartnerLocationId bPartnerLocationId;

	@Builder.Default
	boolean onlyCopiesGreaterZero = false;
}