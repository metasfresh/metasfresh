/*
 * #%L
 * de.metas.printing.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.printing.spi.impl;

import de.metas.audit.data.ExternalSystemParentConfigId;
import de.metas.printing.IPrintingHandler;
import de.metas.printing.PrintingClientRequest;
import de.metas.printing.model.I_C_Printing_Queue;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ExternalSystemsPrintingNotifier
{
	private final List<IPrintingHandler> handlerList;

	public void notifyExternalSystemsIfNeeded(@NonNull final I_C_Printing_Queue printingQueueRecord)
	{
		final PrintingClientRequest request = PrintingClientRequest.builder()
				.printingQueueId(printingQueueRecord.getC_Printing_Queue_ID())
				.build();
		handlerList.forEach(handler -> handler.notify(request));
	}

	public String getTargetDirectory(@NonNull final ExternalSystemParentConfigId id)
	{
		final List<String> list = new ArrayList<>();
		handlerList.forEach(handler -> list.add(handler.getTargetDirectory(id)));
		return list.get(0);
	}

}
