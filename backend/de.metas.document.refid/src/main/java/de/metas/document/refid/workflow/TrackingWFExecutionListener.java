package de.metas.document.refid.workflow;

/*
 * #%L
 * de.metas.document.refid
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


import org.adempiere.util.Services;

import de.metas.document.refid.api.IReferenceNoBL;
import de.metas.workflow.api.IWFExecutionListener;

/**
 * Responsibilities: assign <code>toModel</code> to same reference numbers as it's <code>fromModel</code> assigned
 * 
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/03745_Implement_case_number_tracking_%282013010310000039%29
 */
public class TrackingWFExecutionListener implements IWFExecutionListener
{
	@Override
	public void onActivityPerformed(Object fromModel, Object toModel)
	{
		Services.get(IReferenceNoBL.class).linkOnSameReferenceNo(fromModel, toModel);
	}
}
