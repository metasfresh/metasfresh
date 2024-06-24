/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.cucumber.stepdefs.billofmaterial;

import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataGetIdAware;
import lombok.NonNull;
import org.eevolution.api.ProductBOMId;
import org.eevolution.model.I_PP_Product_BOM;

public class PP_Product_BOM_StepDefData extends StepDefData<I_PP_Product_BOM>
		implements StepDefDataGetIdAware<ProductBOMId, I_PP_Product_BOM>
{
	public PP_Product_BOM_StepDefData()
	{
		super(I_PP_Product_BOM.class);
	}

	@Override
	public ProductBOMId extractIdFromRecord(@NonNull final I_PP_Product_BOM record)
	{
		return ProductBOMId.ofRepoId(record.getPP_Product_BOM_ID());
	}
}
