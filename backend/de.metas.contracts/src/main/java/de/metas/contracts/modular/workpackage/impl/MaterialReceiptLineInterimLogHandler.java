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

package de.metas.contracts.modular.workpackage.impl;

import de.metas.contracts.modular.IModularContractTypeHandler;
import de.metas.contracts.modular.impl.MaterialReceiptLineModularContractHandler;
import de.metas.contracts.modular.invgroup.interceptor.ModCntrInvoicingGroupRepository;
import lombok.NonNull;
import org.compiere.model.I_M_InOutLine;
import org.springframework.stereotype.Component;

@Component
class MaterialReceiptLineInterimLogHandler extends AbstractMaterialReceiptLogHandler
{
	@NonNull
	private final MaterialReceiptLineModularContractHandler contractHandler;

	public MaterialReceiptLineInterimLogHandler(
			@NonNull final ModCntrInvoicingGroupRepository modCntrInvoicingGroupRepository,
			@NonNull final MaterialReceiptLineModularContractHandler contractHandler)
	{
		super(modCntrInvoicingGroupRepository);
		this.contractHandler = contractHandler;
	}

	@Override
	public @NonNull IModularContractTypeHandler<I_M_InOutLine> getModularContractTypeHandler()
	{
		return contractHandler;
	}
}
