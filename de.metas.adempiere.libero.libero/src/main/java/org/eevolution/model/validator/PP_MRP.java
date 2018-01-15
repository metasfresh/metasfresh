package org.eevolution.model.validator;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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


import java.math.BigDecimal;
import java.sql.Timestamp;

import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidator;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.mrp.api.IMRPBL;
import org.eevolution.mrp.api.IMRPExecutorService;
import org.slf4j.Logger;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.material.planning.pporder.LiberoException;
import de.metas.logging.LogManager;

@Validator(I_PP_MRP.class)
public class PP_MRP
{
	private static final transient Logger logger = LogManager.getLogger(PP_MRP.class);

	/**
	 * Validates mandatory fields
	 * 
	 * @param mrp
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void validate(final I_PP_MRP mrp)
	{
		if (mrp.getAD_Client_ID() <= 0)
		{
			throw new FillMandatoryException(I_PP_MRP.COLUMNNAME_AD_Client_ID);
		}
		if (mrp.getAD_Org_ID() <= 0)
		{
			throw new FillMandatoryException(I_PP_MRP.COLUMNNAME_AD_Org_ID);
		}
		if (mrp.getDatePromised() == null)
		{
			throw new FillMandatoryException(I_PP_MRP.COLUMNNAME_DatePromised);
		}
		if (Check.isEmpty(mrp.getDocStatus(), true))
		{
			throw new FillMandatoryException(I_PP_MRP.COLUMNNAME_DocStatus);
		}
		
		final BigDecimal qtyRequired = mrp.getQtyRequiered();
		if (qtyRequired.signum() < 0)
		{
			mrp.setQtyRequiered(BigDecimal.ZERO);

			final LiberoException ex = new LiberoException("Negative QtyRequiered in MRP shall be avoided."
					+ "\n @QtyRequiered@: " + qtyRequired
					+ "\n @TypeMRP@: " + mrp.getTypeMRP()
					+ "\n @PP_MRP_ID@: " + mrp);
			logger.warn(ex.getLocalizedMessage() + " [ Set to ZERO ]", ex);
		}
					

		//
		// Check Qty signum
		final BigDecimal qty = mrp.getQty();
		if (qty.signum() < 0)
		{
			mrp.setQty(BigDecimal.ZERO);

			final LiberoException ex = new LiberoException("Negative quantities in MRP shall be avoided."
					+ "\n @Qty@: " + qty
					+ "\n @TypeMRP@: " + mrp.getTypeMRP()
					+ "\n @PP_MRP_ID@: " + mrp);
			logger.warn(ex.getLocalizedMessage() + " [ Set to ZERO ]", ex);
		}
	}

	/**
	 * Sets DateStartSchedule, DateFinishSchedule.
	 * 
	 * @param mrp
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void setDateSchedules(final I_PP_MRP mrp)
	{
		//
		// Check: if DateFinishSchedule is not set, use DatePromised
		Timestamp dateFinishSchedule = mrp.getDateFinishSchedule();
		if (dateFinishSchedule == null)
		{
			dateFinishSchedule = mrp.getDatePromised();
			mrp.setDateFinishSchedule(dateFinishSchedule);
		}

		//
		// Check: if DateStartSchedule is not set, use DateFinishSchedule
		Timestamp dateStartSchedule = mrp.getDateStartSchedule();
		if (dateStartSchedule == null)
		{
			dateStartSchedule = dateFinishSchedule;
			mrp.setDateStartSchedule(dateStartSchedule);
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW })
	public void notifyMRPExecutorService(final I_PP_MRP mrp)
	{
		Services.get(IMRPExecutorService.class).onMRPRecordBeforeCreate(mrp);
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW })
	public void createPP_MRP_Allocs(final I_PP_MRP mrp)
	{
		final IMRPBL mrpBL = Services.get(IMRPBL.class);

		if (!mrpBL.isSupply(mrp))
		{
			return;
		}
		final I_PP_MRP mrpSupply = mrp;

		mrpBL.createMRPAllocationsFromContext(mrpSupply);
	}
}
