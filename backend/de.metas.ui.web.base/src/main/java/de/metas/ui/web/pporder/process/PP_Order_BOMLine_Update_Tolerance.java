/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.ui.web.pporder.process;

import de.metas.document.engine.DocStatus;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import de.metas.material.planning.pporder.impl.PPOrderBOMBL;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.product.IssuingToleranceSpec;
import de.metas.product.IssuingToleranceValueType;
import de.metas.product.ProductId;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UOMConversionContext;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_UOM;
import org.eevolution.api.IPPOrderDAO;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import static org.eevolution.model.I_PP_Order_BOMLine.COLUMNNAME_IsEnforceIssuingTolerance;

public class PP_Order_BOMLine_Update_Tolerance extends JavaProcess implements IProcessPrecondition, IProcessDefaultParametersProvider
{
	private final IPPOrderDAO ppOrderDAO = Services.get(IPPOrderDAO.class);
	private final IPPOrderBOMBL ppOrderBOMBL = Services.get(IPPOrderBOMBL.class);
	private final IPPOrderBOMDAO ppOrderBOMDAO = Services.get(IPPOrderBOMDAO.class);
	private final IUOMConversionBL uomConversionService = Services.get(IUOMConversionBL.class);

	@Param(parameterName = COLUMNNAME_IsEnforceIssuingTolerance, mandatory = true)
	private boolean isEnforceIssuingTolerance;

	@Param(parameterName = I_PP_Order_BOMLine.COLUMNNAME_IssuingTolerance_ValueType)
	private String issuingTolerance_ValueType;

	@Param(parameterName = I_PP_Order_BOMLine.COLUMNNAME_IssuingTolerance_Qty)
	private BigDecimal issuingTolerance_Qty;

	@Param(parameterName = I_PP_Order_BOMLine.COLUMNNAME_IssuingTolerance_UOM_ID)
	private int issuingTolerance_UOM_ID;

	@Param(parameterName = I_PP_Order_BOMLine.COLUMNNAME_IssuingTolerance_Perc)
	private BigDecimal issuingTolerance_Perc;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		final PPOrderBOMLineId bomLineId = PPOrderBOMLineId.ofRepoId(context.getSingleSelectedRecordId());
		final I_PP_Order_BOMLine bomLine = ppOrderBOMDAO.getOrderBOMLineById(bomLineId);
		final PPOrderId ppOrderId = PPOrderId.ofRepoId(bomLine.getPP_Order_ID());
		final I_PP_Order ppOrder = ppOrderDAO.getById(ppOrderId);

		// Check if PP_Order is Completed but not Closed
		final DocStatus docStatus = DocStatus.ofCode(ppOrder.getDocStatus());
		if (!docStatus.isCompleted())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("PP_Order must be Completed");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final I_PP_Order_BOMLine bomLine = getBomLine();

		// Update tolerance fields
		bomLine.setIsEnforceIssuingTolerance(isEnforceIssuingTolerance);

		if (isEnforceIssuingTolerance)
		{
			validateToleranceParameters();

			bomLine.setIssuingTolerance_ValueType(issuingTolerance_ValueType);

			if ("Q".equals(issuingTolerance_ValueType))
			{
				bomLine.setIssuingTolerance_Qty(issuingTolerance_Qty);
				bomLine.setIssuingTolerance_UOM_ID(issuingTolerance_UOM_ID);
				bomLine.setIssuingTolerance_Perc(null);
			}
			else if ("P".equals(issuingTolerance_ValueType))
			{
				bomLine.setIssuingTolerance_Perc(issuingTolerance_Perc);
				bomLine.setIssuingTolerance_Qty(null);
				bomLine.setIssuingTolerance_UOM_ID(-1);
			}
		}
		else
		{
			// Clear tolerance values if enforcement is disabled
			bomLine.setIssuingTolerance_ValueType(null);
			bomLine.setIssuingTolerance_Qty(null);
			bomLine.setIssuingTolerance_UOM_ID(-1);
			bomLine.setIssuingTolerance_Perc(null);
		}

		// Update audit fields
		bomLine.setToleranceChanged(new Timestamp(System.currentTimeMillis()));
		bomLine.setToleranceChangedBy_ID(getAD_User_ID());

		//
		// Validate Issuing Tolerance
		final I_C_UOM uom = ppOrderBOMBL.getBOMLineUOM(bomLine);
		final IssuingToleranceSpec issuingToleranceSpec = PPOrderBOMBL.extractIssuingToleranceSpec(bomLine).orElse(null);
		if (issuingToleranceSpec != null)
		{
			final UOMConversionContext uomConversionContext = UOMConversionContext.of(ProductId.ofRepoId(bomLine.getM_Product_ID()));
			// this will throw an exception if the conversion is not possible
			issuingToleranceSpec.convertQty(qty -> uomConversionService.convertQuantityTo(qty, uomConversionContext, uom));
		}

		ppOrderBOMDAO.save(bomLine);

		return MSG_OK;
	}

	private void validateToleranceParameters()
	{
		final IssuingToleranceValueType issuingToleranceValueType = IssuingToleranceValueType.ofNullableCode(issuingTolerance_ValueType);
		if (issuingToleranceValueType == null)
		{
			throw new AdempiereException("Tolerance Value Type is required when enforcement is enabled");
		}

		if (issuingToleranceValueType.isQuantity())
		{
			if (issuingTolerance_Qty == null || issuingTolerance_Qty.signum() <= 0)
			{
				throw new AdempiereException("Tolerance Quantity must be greater than zero");
			}
			if (issuingTolerance_UOM_ID <= 0)
			{
				throw new AdempiereException("Tolerance UOM is required for Quantity type");
			}
		}
		else if (issuingToleranceValueType.isPercentage())
		{
			if (issuingTolerance_Perc == null || issuingTolerance_Perc.signum() <= 0)
			{
				throw new AdempiereException("Tolerance Percentage must be greater than zero");
			}
			if (issuingTolerance_Perc.compareTo(new BigDecimal("100")) > 0)
			{
				throw new AdempiereException("Tolerance Percentage cannot exceed 100%");
			}
		}
	}

	@Nullable
	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		final I_PP_Order_BOMLine bomLine = getBomLine();

		switch (parameter.getColumnName())
		{
			case COLUMNNAME_IsEnforceIssuingTolerance:
				return bomLine.isEnforceIssuingTolerance();
			case I_PP_Order_BOMLine.COLUMNNAME_IssuingTolerance_ValueType:
				return bomLine.getIssuingTolerance_ValueType();
			case I_PP_Order_BOMLine.COLUMNNAME_IssuingTolerance_Qty:
				return bomLine.getIssuingTolerance_Qty();
			case I_PP_Order_BOMLine.COLUMNNAME_IssuingTolerance_UOM_ID:
				return bomLine.getIssuingTolerance_UOM_ID();
			case I_PP_Order_BOMLine.COLUMNNAME_IssuingTolerance_Perc:
				return bomLine.getIssuingTolerance_Perc();
			default:
				return null;
		}
	}

	private I_PP_Order_BOMLine getBomLine()
	{
		final PPOrderBOMLineId bomLineId = PPOrderBOMLineId.ofRepoId(getRecord_ID());
		return ppOrderBOMDAO.getOrderBOMLineById(bomLineId);
	}
}
