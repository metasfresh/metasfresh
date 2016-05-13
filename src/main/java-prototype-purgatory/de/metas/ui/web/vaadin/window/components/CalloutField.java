package de.metas.ui.web.vaadin.window.components;

import java.util.Properties;

import org.adempiere.ad.callout.api.ICalloutExecutor;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.util.Services;
import org.compiere.util.Env;

import com.vaadin.ui.Field;

import de.metas.ui.web.vaadin.util.FieldGroupModelWrapper;
import de.metas.ui.web.vaadin.window.descriptor.DataFieldPropertyDescriptor;

/*
 * #%L
 * de.metas.ui.web.vaadin
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

public class CalloutField implements ICalloutField
{
	private final CalloutFieldDependenciesBinder binder;
	private final Field<?> field;
	private final DataFieldPropertyDescriptor descriptor;
	private int windowNo;
	private int tabNo;

	public CalloutField(CalloutFieldDependenciesBinder binder, final Field<?> field, final DataFieldPropertyDescriptor descriptor)
	{
		super();
		this.binder = binder;
		this.field = field;
		this.descriptor = descriptor;
	}

	@Override
	public boolean isTriggerCalloutAllowed()
	{
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Properties getCtx()
	{
		// TODO Auto-generated method stub
		return Env.getCtx();
	}

	@Override
	public int getAD_Table_ID()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getTableName()
	{
		// TODO Auto-generated method stub
		final int adTableId = getAD_Table_ID();
		return adTableId <= 0 ? null : Services.get(IADTableDAO.class).retrieveTableName(adTableId);
	}

	@Override
	public int getAD_Column_ID()
	{
		return descriptor.getAD_Column_ID();
	}

	@Override
	public Object getValue()
	{
		return field.getValue();
	}

	@Override
	public Object getOldValue()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getColumnName()
	{
		return descriptor.getColumnName();
	}

	@Override
	public <T> T getModel(Class<T> modelClass)
	{
		final IFieldGroup fieldGroup = binder.getFieldGroup();
		return FieldGroupModelWrapper.wrap(fieldGroup, modelClass);
	}

	@Override
	public int getWindowNo()
	{
		return windowNo;
	}

	@Override
	public int getTabNo()
	{
		return tabNo;
	}

	@Override
	public boolean isRecordCopyingMode()
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean isRecordCopyingModeIncludingDetails()
	{
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public ICalloutExecutor getCurrentCalloutExecutor()
	{
		return binder.getCalloutExecutor();
	}

	@Override
	public void fireDataStatusEEvent(String AD_Message, String info, boolean isError)
	{
		// TODO Auto-generated method stub
		
	}

}
