package de.metas.ui.web.pporder;

import java.util.function.Function;

import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.util.Env;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Preconditions;

import de.metas.process.IADProcessDAO;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.ui.web.window.datatypes.WindowId;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Configuration
public class WebPPOrderConfig
{
	public static final String AD_WINDOW_ID_IssueReceipt_String = "540328"; // Manufacturing Issue/Receipt
	public static final WindowId AD_WINDOW_ID_IssueReceipt = WindowId.fromJson("540328"); // Manufacturing Issue/Receipt

	// NOTE: we need Adempiere as parameter to make sure it was initialized. Else the DAOs will fail.
	@Autowired
	public WebPPOrderConfig(final Adempiere adempiere_NOTUSED)
	{
		final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);

		//
		// Manufacturing Issue / Receipt
		{
			final Function<Class<?>, RelatedProcessDescriptor.Builder> newDescriptorBuilder = (processClass) -> {
				final int processId = adProcessDAO.retriveProcessIdByClassIfUnique(Env.getCtx(), processClass);
				Preconditions.checkArgument(processId > 0, "No AD_Process_ID foudn for %s", processClass);

				return RelatedProcessDescriptor.builder()
						.processId(processId)
						.windowId(AD_WINDOW_ID_IssueReceipt.toInt())
						.anyTable()
						.webuiQuickAction(true);
			};
			//
			adProcessDAO.registerTableProcess(newDescriptorBuilder.apply(de.metas.ui.web.pporder.process.WEBUI_PP_Order_Receipt.class).build());
		}
	}
}
