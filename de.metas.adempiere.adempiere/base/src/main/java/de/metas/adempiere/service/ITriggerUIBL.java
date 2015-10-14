/**
 * 
 */
package de.metas.adempiere.service;

/*
 * #%L
 * ADempiere ERP - Base
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


import java.util.List;

import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.callout.api.ICalloutInstance;
import org.adempiere.model.I_AD_TriggerUI;
import org.adempiere.model.I_AD_TriggerUI_Action;
import org.adempiere.model.I_AD_TriggerUI_Criteria;
import org.adempiere.util.ISingletonService;
import org.compiere.model.GridField;

/**
 * @author tsa
 * 
 */
public interface ITriggerUIBL extends ISingletonService
{
	public ICalloutInstance getCalloutInstance(ICalloutField field);

	/**
	 * Create model validator to be used for initializing this module
	 * 
	 * @return
	 */
	public org.compiere.model.ModelValidator createModelValidator();

	public void removeDependencies(I_AD_TriggerUI trigger);

	public void validate(I_AD_TriggerUI_Criteria criteria);

	public void validate(I_AD_TriggerUI_Action action);

	public List<I_AD_TriggerUI> retrieveForGridField(GridField gridField);

	public void trigger(GridField gridField);
}
