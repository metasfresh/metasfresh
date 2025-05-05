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

package de.metas.contracts.modular.workpackage;

import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeBL;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.Language;
import de.metas.i18n.TranslatableStrings;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ModularContractLogHandlerHelper
{
	private final AdMessageKey MSG_ON_COMPLETE_LOG_DESCRIPTION = AdMessageKey.of("de.metas.contracts.modular.workpackage.IModularContractLogHandler.CompleteLogDescription");
	private final AdMessageKey MSG_ON_REVERSE_LOG_DESCRIPTION = AdMessageKey.of("de.metas.contracts.modular.workpackage.ModularContractLogHandlerHelper.ReverseLogDescription");

	@NonNull
	public String getOnCompleteDescription(
			@NonNull final DocTypeId docTypeId,
			@NonNull final ProductId productId,
			@NonNull final Quantity quantity)
	{
		return TranslatableStrings.adMessage(
						MSG_ON_COMPLETE_LOG_DESCRIPTION,
						Services.get(IDocTypeBL.class).getNameById(docTypeId),
						Services.get(IProductBL.class).getProductValueAndName(productId),
						quantity.toString())
				.translate(Language.getBaseAD_Language());
	}

	@NonNull
	public String getOnReverseDescription(
			@NonNull final DocTypeId docTypeId,
			@NonNull final ProductId productId,
			@NonNull final Quantity quantity)
	{
		return TranslatableStrings.adMessage(
						MSG_ON_REVERSE_LOG_DESCRIPTION,
						Services.get(IDocTypeBL.class).getNameById(docTypeId),
						Services.get(IProductBL.class).getProductValueAndName(productId),
						quantity.toString())
				.translate(Language.getBaseAD_Language());
	}
}
