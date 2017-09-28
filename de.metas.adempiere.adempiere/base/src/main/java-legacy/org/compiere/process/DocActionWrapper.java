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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class DocActionWrapper implements DocAction
{
	public static DocAction wrap(@NonNull final Object model)
	{
		if (model instanceof DocAction)
		{
			return (DocAction)model;
		}

		final DocActionFields docActionModel = InterfaceWrapperHelper.create(model, DocActionFields.class);

		return new DocActionWrapper(docActionModel);
	}

	private final DocActionFields docActionModel;
	private final DocActionHandler docActionHandler;

	private String m_processMsg;
	private boolean m_justPrepared;

	private DocActionWrapper(@NonNull DocActionFields docActionModel)
	{
		this.docActionModel = docActionModel;
		this.docActionHandler = null; // TODO
	}

	@Override
	public void setDocStatus(String newStatus)
	{
		docActionModel.setDocStatus(newStatus);
	}

	@Override
	public String getDocStatus()
	{
		return docActionModel.getDocStatus();
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
		docActionModel.setProcessing(false);
		return true;
	}

	@Override
	public boolean invalidateIt()
	{
		docActionModel.setDocAction(DocAction.ACTION_Prepare);
		return true;
	}

	@Override
	public String prepareIt()
	{
		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		final String newDocStatus = docActionHandler.prepareIt(docActionModel);
		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);

		return newDocStatus;
	}

	@Override
	public boolean approveIt()
	{
		docActionHandler.approveIt(docActionModel);
		return true;
	}

	@Override
	public boolean rejectIt()
	{
		docActionHandler.rejectIt(docActionModel);
		return true;
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

		final String newDocStatus = docActionHandler.completeIt(docActionModel);

		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);

		docActionModel.setProcessed(true);
		return newDocStatus;
	}

	@Override
	public boolean voidIt()
	{
		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_VOID);
		docActionHandler.voidIt(docActionModel);
		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_VOID);
		return true;
	}

	@Override
	public boolean closeIt()
	{
		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_CLOSE);
		docActionHandler.closeIt(docActionModel);
		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_CLOSE);
		return true;
	}

	@Override
	public boolean reverseCorrectIt()
	{
		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REVERSECORRECT);
		docActionHandler.reverseCorrectIt(docActionModel);
		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSECORRECT);
		return true;
	}

	@Override
	public boolean reverseAccrualIt()
	{
		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REVERSEACCRUAL);
		docActionHandler.reverseAccrualIt(docActionModel);
		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
		return true;
	}

	@Override
	public boolean reActivateIt()
	{
		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REACTIVATE);
		docActionHandler.reactivateIt(docActionModel);
		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REACTIVATE);
		return true;
	}

	@Override
	public String getSummary()
	{
		return docActionHandler.getSummary(docActionModel);
	}

	@Override
	public String getDocumentInfo()
	{
		return docActionHandler.getDocumentInfo(docActionModel);
	}

	@Override
	public String getDocumentNo()
	{
		return docActionModel.getDocumentNo();
	}

	@Override
	public File createPDF()
	{
		return docActionHandler.createPDF(docActionModel);
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
