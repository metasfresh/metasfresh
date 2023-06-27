package de.metas.procurement.base.model.interceptor;

import de.metas.procurement.base.IPMMContractsDAO;
import de.metas.procurement.base.IWebuiPush;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.ModelValidator;

/*
 * #%L
 * de.metas.procurement.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

@Interceptor(I_C_BPartner.class)
public class C_BPartner
{
	public static final transient C_BPartner instance = new C_BPartner();

	private C_BPartner()
	{
	}

	/**
	 * Make sure an partner with currently running contracts always has <code>IsVendor='Y'</code>.
	 *
	 * @param bpartner
	 */
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = I_C_BPartner.COLUMNNAME_IsVendor)
	public void preventDisablingVendorFlag(final I_C_BPartner bpartner)
	{
		// Do nothing if vendor flag is set
		if (bpartner.isVendor())
		{
			return;
		}

		if (Services.get(IPMMContractsDAO.class).hasRunningContract(bpartner))
		{
			throw new AdempiereException("@C_BPartner_ID@ @IsVendor@=@N@: " + bpartner.getValue() + "_" + bpartner.getName());
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE //
	, ifColumnsChanged = {
			I_C_BPartner.COLUMNNAME_Name,
			I_C_BPartner.COLUMNNAME_AD_Language,
			I_C_BPartner.COLUMNNAME_IsVendor } //
			, afterCommit = true)
	public void pushToWebUI(@NonNull final I_C_BPartner bpartner)
	{
		Services.get(IWebuiPush.class).pushBPartnerAndUsers(bpartner);
	}
}
