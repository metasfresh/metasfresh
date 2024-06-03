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

package de.metas.contracts.modular;

import de.metas.contracts.modular.settings.ModularContractTypeId;
import de.metas.i18n.AdMessageKey;

public class ModularContract_Constants
{
	public static final AdMessageKey MSG_ERROR_DOC_ACTION_NOT_ALLOWED = AdMessageKey.of("de.metas.contracts.DocActionNotAllowed");
	public static final AdMessageKey MSG_ERROR_DOC_ACTION_UNSUPPORTED = AdMessageKey.of("de.metas.contracts.DocActionUnsupported");

	public static final AdMessageKey MSG_REACTIVATE_NOT_ALLOWED = AdMessageKey.of("de.metas.contracts.modular.impl.ReactivateNotAllowed");
	public static final AdMessageKey MSG_ERROR_PROCESSED_LOGS_CANNOT_BE_RECOMPUTED = AdMessageKey.of("de.metas.contracts.modular.PROCESSED_LOGS_EXISTS");

	public static final ModularContractTypeId CONTRACT_MODULE_TYPE_INFORMATIVE_LOGS_ID = ModularContractTypeId.ofRepoId(540008);
	public static final ModularContractTypeId CONTRACT_MODULE_TYPE_DefinitiveInvoiceRawProduct =  ModularContractTypeId.ofRepoId(540009);
	public static final ModularContractTypeId CONTRACT_MODULE_TYPE_DefinitiveInvoiceProcessedProduct =  ModularContractTypeId.ofRepoId(540010);
}
