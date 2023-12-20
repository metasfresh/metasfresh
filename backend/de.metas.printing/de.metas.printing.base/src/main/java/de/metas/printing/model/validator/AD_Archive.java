package de.metas.printing.model.validator;

/*
 * #%L
 * de.metas.printing.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import de.metas.printing.PrintOutputFacade;
import de.metas.printing.api.IPrintingQueueBL;
import de.metas.printing.api.impl.PrintArchiveParameters;
import de.metas.printing.model.I_AD_Archive;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_AD_Archive.class)
@Component
public class AD_Archive
{
	private final PrintOutputFacade printOutputFacade;
	private final IPrintingQueueBL printingQueueBL = Services.get(IPrintingQueueBL.class);

	public AD_Archive(@NonNull final PrintOutputFacade printOutputFacade)
	{
		this.printOutputFacade = printOutputFacade;
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW,
			ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = I_AD_Archive.COLUMNNAME_C_Doc_Outbound_Config_ID)
	public void updateArchiveFlags(final I_AD_Archive archive)
	{
		printingQueueBL.updateArchiveFlagsFromConfig(archive);
	}

	/**
	 * If direct print is required for given {@link AD_Archive} then this method enqueues the archive to printing queue.
	 */
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE },
			ifColumnsChanged = {
					I_AD_Archive.COLUMNNAME_IsDirectProcessQueueItem,
					I_AD_Archive.COLUMNNAME_IsDirectEnqueue,
					I_AD_Archive.COLUMNNAME_C_Doc_Outbound_Config_ID,
					I_AD_Archive.COLUMNNAME_IsActive })
	public void printArchive(final I_AD_Archive archive)
	{
		final PrintArchiveParameters printArchiveParameters = PrintArchiveParameters.builder()
				.archive(archive)
				.printOutputFacade(printOutputFacade)
				.hwPrinterId(null)
				.hwTrayId(null)
				.enforceEnqueueToPrintQueue(false)
				.build();

		printingQueueBL.printArchive(printArchiveParameters);
	}
}
