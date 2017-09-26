package org.compiere.process;

import java.io.File;
import java.math.BigDecimal;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Client;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.slf4j.Logger;

import de.metas.document.engine.IDocActionBL;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class DocActionWrapper implements DocAction
{
	public static DocAction wrap(@NonNull final Object model)
	{
		if(model instanceof DocAction)
		{
			return (DocAction)model;
		}
		
		final DocActionFields docActionModel = InterfaceWrapperHelper.create(model, DocActionFields.class);
		
		return new DocActionWrapper(docActionModel);
	}

	private final DocActionFields docActionModel;
	private String m_processMsg;
	private boolean m_justPrepared;
	
	private DocActionWrapper(DocActionFields docActionModel)
	{
		this.docActionModel = docActionModel;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setDocStatus(String newStatus)
	{
		docActionModel.setDocStatus(newStatus);
	}

	@Override
	public String getDocStatus()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean processIt(String action) throws Exception
	{
		m_processMsg = null;
		return Services.get(IDocActionBL.class).processIt(this, action);
	}

	@Override
	public boolean unlockIt()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean invalidateIt()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String prepareIt()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean approveIt()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean rejectIt()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String completeIt()
	{
		// Re-Check
		if (!m_justPrepared)
		{
			String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
				return status;
		}

		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);
		
		// TODO: actual BL
		
		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		
		docActionModel.setProcessed(true);
		return DocAction.STATUS_Completed;
	}

	@Override
	public boolean voidIt()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean closeIt()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reverseCorrectIt()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reverseAccrualIt()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reActivateIt()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getSummary()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDocumentInfo()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDocumentNo()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File createPDF()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProcessMsg()
	{
		return m_processMsg;
	}

	@Override
	public int getDoc_User_ID()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getC_Currency_ID()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BigDecimal getApprovalAmt()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getAD_Client_ID()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public I_AD_Client getAD_Client()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getAD_Org_ID()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getDocAction()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean save()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Properties getCtx()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int get_ID()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int get_Table_ID()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Logger get_Logger()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String get_TrxName()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void set_TrxName(String trxName)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isActive()
	{
		// TODO Auto-generated method stub
		return false;
	}

}
