/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

/**
 *
 */
package de.metas.product.impexp.interceptor;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.impl.CreateTUPackingInstructionsRequest;
import de.metas.impexp.processing.IImportInterceptor;
import de.metas.impexp.processing.IImportProcess;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_I_Product;

import java.math.BigDecimal;

/**
 * @author metas-dev <dev@metasfresh.com>
 */
public class ImportProductInterceptor implements IImportInterceptor
{
	@NonNull private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	@NonNull private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

	@Override
	public void onImport(IImportProcess<?> process, Object importModel, Object targetModel, int timing)
	{
		if (timing != IImportInterceptor.TIMING_AFTER_IMPORT)
		{
			return;
		}

		final I_I_Product iproduct = InterfaceWrapperHelper.create(importModel, I_I_Product.class);
		createTUPackingInstructions(iproduct);
	}



	private void createTUPackingInstructions(@NonNull final I_I_Product importRecord)
	{
		final ProductId productId = ProductId.ofRepoIdOrNull(importRecord.getM_Product_ID());
		if (productId == null)
		{
			return;
		}

		final String piName = StringUtils.trimBlankToNull(importRecord.getM_HU_PI_Value());
		if (piName == null)
		{
			return;
		}

		if (BigDecimal.ONE.compareTo(importRecord.getQtyCU()) == 0 )
		{
			return;
		}

		handlingUnitsDAO.createTUPackingInstructions(
				CreateTUPackingInstructionsRequest.builder()
						.name(piName)
						.productId(productId)
						.bpartnerId(BPartnerId.ofRepoIdOrNull(importRecord.getC_BPartner_ID()))
						.qtyCU(extractQtyCU(importRecord))
						.isDefault(importRecord.isDefaultPacking())
						.build()
		);
	}

	@NonNull
	private Quantity extractQtyCU(final I_I_Product importRecord)
	{
		final UomId uomId = UomId.ofRepoIdOrNull(importRecord.getQtyCU_UOM_ID());
		if (uomId == null)
		{
			throw new FillMandatoryException(I_I_Product.COLUMNNAME_QtyCU_UOM_ID);
		}
		final I_C_UOM uom = uomDAO.getById(uomId);

		if (InterfaceWrapperHelper.isNull(importRecord, I_I_Product.COLUMNNAME_QtyCU))
		{
			return Quantity.infinite(uom);
		}

		final BigDecimal qtyBD = importRecord.getQtyCU();
		if (qtyBD.signum() <= 0)
		{
			throw new AdempiereException("QtyCU must be > 0");
		}

		return Quantity.of(qtyBD, uom);
	}
}
