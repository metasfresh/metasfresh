/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.organization.interceptors;

import de.metas.organization.impl.FiscalRepresentationBL;
import de.metas.util.Services;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Fiscal_Representation;
import org.compiere.model.ModelValidator;

@Validator(I_C_Fiscal_Representation.class)
@Callout(I_C_Fiscal_Representation.class)
public class C_Fiscal_Representation
{
	private final FiscalRepresentationBL fiscalRepresentationBL = SpringContextHolder.instance.getBean(FiscalRepresentationBL.class);

	@Init
	public void registerCallout()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	@ModelChange(timings = {
			ModelValidator.TYPE_BEFORE_CHANGE
	}, ifColumnsChanged = {
			I_C_Fiscal_Representation.COLUMNNAME_ValidFrom
	})
	public void addFiscalRepresentationValidFromTo(final I_C_Fiscal_Representation fiscalRep)
	{
		if (fiscalRep.getValidFrom() != null && fiscalRep.getValidTo() != null && !fiscalRepresentationBL.isValidFromDate(fiscalRep))
		{
			fiscalRepresentationBL.updateValidFrom(fiscalRep);
		}
	}

	@ModelChange(timings = {
			ModelValidator.TYPE_BEFORE_CHANGE
	}, ifColumnsChanged = {
			I_C_Fiscal_Representation.COLUMNNAME_ValidTo
	})
	public void addFiscalRepresentationValidTo(final I_C_Fiscal_Representation fiscalRep)
	{
		if (fiscalRep.getValidTo() != null && fiscalRep.getValidFrom() != null && !fiscalRepresentationBL.isValidToDate(fiscalRep))
		{
			fiscalRepresentationBL.updateValidTo(fiscalRep);
		}
	}

	@ModelChange(timings = {
			ModelValidator.TYPE_BEFORE_CHANGE
	}, ifColumnsChanged = {
			I_C_Fiscal_Representation.COLUMNNAME_C_BPartner_Location_ID
	})
	public void addFiscalRepresentationLocationId(final I_C_Fiscal_Representation fiscalRep)
	{
		fiscalRepresentationBL.updateCountryId(fiscalRep);
	}

	@CalloutMethod(columnNames = { I_C_Fiscal_Representation.COLUMNNAME_ValidFrom })
	public void updateFiscalRepresentationValidFrom(final I_C_Fiscal_Representation fiscalRep)
	{
		if (fiscalRep.getValidTo() != null && fiscalRep.getValidFrom() != null && !fiscalRepresentationBL.isValidFromDate(fiscalRep))
		{
			fiscalRepresentationBL.updateValidFrom(fiscalRep);
		}
	}

	@CalloutMethod(columnNames = { I_C_Fiscal_Representation.COLUMNNAME_C_BPartner_Location_ID })
	public void updateFiscalRepresentationLocationId(final I_C_Fiscal_Representation fiscalRep)
	{
		fiscalRepresentationBL.updateCountryId(fiscalRep);
	}

	@CalloutMethod(columnNames = { I_C_Fiscal_Representation.COLUMNNAME_ValidTo })
	public void updateFiscalRepresentationValidTo(final I_C_Fiscal_Representation fiscalRep)
	{
		if (fiscalRep.getValidTo() != null && fiscalRep.getValidFrom() != null && !fiscalRepresentationBL.isValidToDate(fiscalRep))
		{
			fiscalRepresentationBL.updateValidTo(fiscalRep);
		}
	}

	@CalloutMethod(columnNames = { I_C_Fiscal_Representation.COLUMNNAME_C_BPartner_Representative_ID })
	public void updateFiscalRepresentationPartner(final I_C_Fiscal_Representation fiscalRep)
	{
		fiscalRep.setC_BPartner_Location_ID(-1);
	}

}

