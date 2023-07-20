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

package de.metas.contracts;

import de.metas.contracts.model.I_ModCntr_Module;
import de.metas.contracts.order.model.I_C_OrderLine;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.compiere.model.I_C_BPartner;

import java.util.List;

public interface IModularContractDAO extends ISingletonService
{
	List<I_ModCntr_Module> getModulaContractsForOrderLine(
			@NonNull final I_C_OrderLine orderLine,
			@NonNull final I_C_BPartner bPartner);
}
