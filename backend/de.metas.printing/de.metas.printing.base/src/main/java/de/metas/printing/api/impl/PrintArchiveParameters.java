/*
 * #%L
 * de.metas.printing.base
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

package de.metas.printing.api.impl;

import de.metas.printing.HardwarePrinterId;
import de.metas.printing.HardwareTrayId;
import de.metas.printing.PrintOutputFacade;
import de.metas.printing.model.I_AD_Archive;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.archive.ArchiveId;

import javax.annotation.Nullable;

@Value
@Builder

public class PrintArchiveParameters
{
	@NonNull I_AD_Archive archive;
	@NonNull PrintOutputFacade printOutputFacade;
	@Nullable
	HardwarePrinterId hwPrinterId;
	@Nullable
	HardwareTrayId hwTrayId;
	boolean enforceEnqueueToPrintQueue;
}
