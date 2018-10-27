package de.metas.vertical.pharma.vendor.gateway.msv3.purchaseOrder;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.util.Properties;

import org.adempiere.ad.service.ISequenceDAO;
import org.compiere.model.I_AD_Sequence;
import org.compiere.model.MSequence;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import de.metas.util.Services;
import de.metas.vertical.pharma.msv3.protocol.order.SupportIDType;

/*
 * #%L
 * metasfresh-pharma.vendor.gateway.msv3
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

@Service
public class ADSequenceBasedSupportIdProvider implements SupportIdProvider
{
	private static final String MSV3_SUPPORT_ID_SEQUENCE = "MSV3_SupportId";

	@Override
	public SupportIDType getNextSupportId()
	{
		final int supportId = MSequence.getNextID(Env.CTXVALUE_AD_Client_ID_System, MSV3_SUPPORT_ID_SEQUENCE);
		if (supportId <= SupportIDType.MAX_VALUE)
		{
			return SupportIDType.of(supportId);
		}
		// Reset the sequence and return 1
		else
		{
			final Properties sysContext = Env.createSysContext(Env.getCtx());
			final I_AD_Sequence tableSequence = Services.get(ISequenceDAO.class).retrieveTableSequenceOrNull(
					sysContext, MSV3_SUPPORT_ID_SEQUENCE);

			tableSequence.setCurrentNext(2);
			tableSequence.setCurrentNextSys(2);
			saveRecord(tableSequence);

			return SupportIDType.of(1);
		}
	}

}
