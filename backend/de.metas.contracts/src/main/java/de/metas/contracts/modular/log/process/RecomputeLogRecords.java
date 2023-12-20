/*
 * #%L
 * de.metas.contracts
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

package de.metas.contracts.modular.log.process;

import de.metas.contracts.modular.log.LogsRecomputationService;
import de.metas.process.JavaProcess;
import de.metas.process.RunOutOfTrx;
import org.compiere.SpringContextHolder;

public class RecomputeLogRecords extends JavaProcess
{
	private final LogsRecomputationService logsRecomputationService = SpringContextHolder.instance.getBean(LogsRecomputationService.class);

	@Override
	@RunOutOfTrx
	protected String doIt() throws Exception
	{
		logsRecomputationService.recomputeLogs(getProcessInfo().getQueryFilterOrElseFalse());

		return MSG_OK;
	}
}
