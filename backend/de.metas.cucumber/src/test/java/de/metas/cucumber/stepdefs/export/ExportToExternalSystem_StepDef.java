/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.cucumber.stepdefs.export;

import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.externalsystem.export.ExportToExternalSystemService;
import de.metas.externalsystem.rabbitmqhttp.ExportBPartnerToRabbitMQService;
import de.metas.externalsystem.rabbitmqhttp.ExportExternalReferenceToRabbitMQService;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;

public class ExportToExternalSystem_StepDef
{
	@Given("^there are no pending items for export to external system via: '(.*)'$")
	public void there_are_no_pending_items_for_export_to_external_system(@NonNull final String exportToExternalSystemBeanName) throws InterruptedException
	{
		final ExportToExternalSystemService exportToExternalSystemService;

		switch (exportToExternalSystemBeanName)
		{
			case "ExportExternalReferenceToRabbitMQService":
				exportToExternalSystemService = SpringContextHolder.instance.getBean(ExportExternalReferenceToRabbitMQService.class);
				break;
			case "ExportBPartnerToRabbitMQService":
				exportToExternalSystemService = SpringContextHolder.instance.getBean(ExportBPartnerToRabbitMQService.class);
				break;
			default:
				throw new AdempiereException("Unsupported bean name:" + exportToExternalSystemBeanName);
		}

		StepDefUtil.tryAndWait(10, 500, () -> exportToExternalSystemService.getCurrentPendingItems() <= 0);
	}
}
