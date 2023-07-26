package de.metas.material.event;

import de.metas.async.QueueWorkPackageId;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.util.Check;
import org.compiere.SpringContextHolder;

/*
 * #%L
 * metasfresh-material-event
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

public class MaterialEventWorkpackageProcessor extends WorkpackageProcessorAdapter
{
	public static final String PARAM_Event = "Event";
	private final PostMaterialEventService postMaterialEventService = SpringContextHolder.instance.getBean(PostMaterialEventService.class);

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workpackage, final String localTrxName)
	{
		final MaterialEvent event = getMaterialEvent();
		postMaterialEventService.postEventNow(event, QueueWorkPackageId.ofRepoId(workpackage.getC_Queue_WorkPackage_ID()));
		return Result.SUCCESS;
	}

	private MaterialEvent getMaterialEvent()
	{
		final String eventObject = getParameters().getParameterAsString(PARAM_Event);
		Check.assumeNotNull(eventObject, "Parameter {} shall not be null", PARAM_Event);
		return JacksonMaterialEventSerializer.instance.fromString(eventObject);
	}
}
