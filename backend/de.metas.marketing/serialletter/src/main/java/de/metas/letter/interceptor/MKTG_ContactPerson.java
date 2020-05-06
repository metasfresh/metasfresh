package de.metas.letter.interceptor;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.springframework.stereotype.Component;

import de.metas.marketing.base.model.I_MKTG_ContactPerson;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * marketing-serialletter
 * %%
 * Copyright (C) 2018 metas GmbH
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
 * Note: we need no MI because what would be that MI's job is done when {@link de.metas.marketing.base.model.ContactPerson}'s are loaded and stored in the repository
 * @author metas-dev <dev@metasfresh.com>
 */
@Callout(I_MKTG_ContactPerson.class)
@Component
public class MKTG_ContactPerson
{
	public MKTG_ContactPerson()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	@CalloutMethod(columnNames = I_MKTG_ContactPerson.COLUMNNAME_C_BPartner_ID, skipIfCopying = true)
	public void resetBPartnerLocation(@NonNull final I_MKTG_ContactPerson contactPersonRecord)
	{
		if (contactPersonRecord.getC_BPartner_ID() > 0)
		{
			return;
		}
		contactPersonRecord.setC_BPartner_Location(null);
		contactPersonRecord.setC_Location(null);
	}

	@CalloutMethod(columnNames = I_MKTG_ContactPerson.COLUMNNAME_C_BPartner_Location_ID, skipIfCopying = true)
	public void resetLocation(@NonNull final I_MKTG_ContactPerson contactPersonRecord)
	{
		if (contactPersonRecord.getC_BPartner_Location_ID() <= 0)
		{
			contactPersonRecord.setC_Location(null);
		}
		else
		{
			contactPersonRecord.setC_Location_ID(contactPersonRecord.getC_BPartner_Location().getC_Location_ID());
		}
	}
}
