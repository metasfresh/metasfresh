package de.metas.fresh.ordercheckup.model.validator;

/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;

import de.metas.fresh.model.I_C_Order_MFGWarehouse_ReportLine;
import de.metas.util.Check;

@Interceptor(I_C_Order_MFGWarehouse_ReportLine.class)
public class C_Order_MFGWarehouse_ReportLine
{
	public static final transient C_Order_MFGWarehouse_ReportLine instance = new C_Order_MFGWarehouse_ReportLine();

	private C_Order_MFGWarehouse_ReportLine()
	{
		super();
	}

	/**
	 * We used to have the IsParentLine='Y' for the AD_Column of C_Order_MFGWarehouse_ReportLine.C_OrderLine_ID. <br>
	 * That led to C_Order_MFGWarehouse_ReportLines being deep-copied ("copy-with-details") together with C_OrderLines, which was absolutely not what we wanted.<br>
	 * This interceptor prevents this.
	 * 
	 * @param line
	 */
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void validate(final I_C_Order_MFGWarehouse_ReportLine line)
	{
		final int lineOrderId = line.getC_OrderLine().getC_Order_ID();
		final int headerOrderId = line.getC_Order_MFGWarehouse_Report().getC_Order_ID();
		Check.errorIf(
				lineOrderId != headerOrderId,
				"line.getC_OrderLine().getC_Order_ID() = {}, but line.getC_Order_MFGWarehouse_Report().getC_Order_ID() = {}. "
						+ "Make sure that the AD_Column of C_Order_MFGWarehouse_ReportLine.C_OrderLine_ID has IsParentLine='N'",
				lineOrderId, headerOrderId);

	}

}
