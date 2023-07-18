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
import de.metas.printing.HardwarePrinter;
import de.metas.printing.HardwarePrinterId;
import de.metas.printing.HardwarePrinterRepository;
import de.metas.printing.IPrintingHandler;
import de.metas.printing.OutputType;
import de.metas.printing.PrintRequest;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ExternalSystemsPrintingNotifier
{
	private final HardwarePrinterRepository hardwarePrinterRepository;
	private final List<IPrintingHandler> handlerList;

	public void notifyExternalSystemsIfNeeded(@NonNull final HardwarePrinterId hardwarePrinterId, @NonNull final String transactionId)
	{
		final HardwarePrinter printer = hardwarePrinterRepository.getById(hardwarePrinterId);
		final ExternalSystemParentConfigId externalSystemParentConfigId = printer.getExternalSystemParentConfigId();
		if (OutputType.Queue.equals(printer.getOutputType()) && externalSystemParentConfigId != null)
		{
			final PrintRequest request = PrintRequest.builder()
					.id(externalSystemParentConfigId)
					.transactionId(transactionId)
					.build();
			handlerList.forEach(handler -> handler.notify(request));
		}
	}

}
