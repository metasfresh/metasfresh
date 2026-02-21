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

import de.metas.common.util.time.SystemTime;
import de.metas.document.engine.DocStatus;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.IPPOrderBOMDAO;
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
import de.metas.quantity.Quantitys;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UOMConversionContext;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.compiere.model.I_C_UOM;
import org.eevolution.api.IPPOrderDAO;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;

import javax.annotation.Nullable;
import java.math.BigDecimal;

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
	private IssuingToleranceValueType issuingTolerance_ValueType;

	@Param(parameterName = I_PP_Order_BOMLine.COLUMNNAME_IssuingTolerance_Qty)
	private BigDecimal issuingTolerance_Qty;

	@Param(parameterName = I_PP_Order_BOMLine.COLUMNNAME_IssuingTolerance_UOM_ID)
	private UomId issuingTolerance_UOM_ID;

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

		final IssuingToleranceSpec issuingToleranceSpec = getIssuingToleranceSpec();

		//
		// Validate new Issuing Tolerance
		final I_C_UOM uom = ppOrderBOMBL.getBOMLineUOM(bomLine);
		if (issuingToleranceSpec != null)
		{
			final UOMConversionContext uomConversionContext = UOMConversionContext.of(ProductId.ofRepoId(bomLine.getM_Product_ID()));
			// this will throw an exception if the conversion is not possible
			issuingToleranceSpec.convertQty(qty -> uomConversionService.convertQuantityTo(qty, uomConversionContext, uom));
		}

		// Update tolerance spec via BL
		ppOrderBOMBL.updateIssuingToleranceSpec(bomLine, issuingToleranceSpec);

		// Update audit fields
		bomLine.setToleranceChanged(SystemTime.asTimestamp());
		bomLine.setToleranceChangedBy_ID(getAD_User_ID());

		// Save all changes
		ppOrderBOMDAO.save(bomLine);

		return MSG_OK;
	}

	@Nullable
	private IssuingToleranceSpec getIssuingToleranceSpec()
	{
		IssuingToleranceSpec issuingToleranceSpec = null;

		if (isEnforceIssuingTolerance)
		{
			Check.assumeNotNull(issuingTolerance_ValueType, "issuingTolerance_ValueType not null");
			if (issuingTolerance_ValueType.isQuantity())
			{
				issuingToleranceSpec = IssuingToleranceSpec.ofQuantity(Quantitys.of(issuingTolerance_Qty, issuingTolerance_UOM_ID));
			}
			else if (issuingTolerance_ValueType.isPercentage())
			{
				issuingToleranceSpec = IssuingToleranceSpec.ofPercent(Percent.of(issuingTolerance_Perc));
			}
		}
		return issuingToleranceSpec;
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
