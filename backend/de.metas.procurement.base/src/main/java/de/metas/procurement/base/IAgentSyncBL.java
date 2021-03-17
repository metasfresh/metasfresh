package de.metas.procurement.base;

import de.metas.common.procurement.sync.IAgentSync;
import de.metas.util.ISingletonService;

/*
 * #%L
 * de.metas.procurement.base
 * %%
 * Copyright (C) 2016 metas GmbH
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
 * The implementation of this interface just sends the respective request objects to the procurement-webui using {@link de.metas.procurement.base.rabbitmq.SenderToProcurementWeb}.
 */
public interface IAgentSyncBL extends IAgentSync, ISingletonService
{
}
