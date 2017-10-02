package de.metas.document.engine;

import java.io.File;
import java.math.BigDecimal;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
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
	public static DocAction wrapModelUsingHandler(@NonNull final Object model, final DocActionHandler handler)
	{
		final DocActionFields docActionModel = InterfaceWrapperHelper.create(model, DocActionFields.class);
		return new DocActionWrapper(docActionModel, handler);
	}

	private static final Logger logger = LogManager.getLogger(DocActionWrapper.class);

	private final DocActionFields model;
	private final DocActionHandler handler;

	private String m_processMsg;
	private boolean m_justPrepared;

	private DocActionWrapper(@NonNull final DocActionFields docActionModel, @NonNull final DocActionHandler handler)
	{
		this.model = docActionModel;
		this.handler = handler;
	}

	@Override
	public void setDocStatus(final String newStatus)
	{
		model.setDocStatus(newStatus);
	}

	@Override
	public String getDocStatus()
	{
		return model.getDocStatus();
	}

	@Override
	public boolean processIt(final String action) throws Exception
	{
		m_processMsg = null;
		return Services.get(IDocActionBL.class).processIt(this, action);
	}

	@Override
	public boolean unlockIt()
	{
		model.setProcessing(false);
		return true;
	}

	@Override
	public boolean invalidateIt()
	{
		model.setDocAction(DocAction.ACTION_Prepare);
		return true;
	}

	@Override
	public boolean approveIt()
	{
		handler.approveIt(model);
		return true;
	}

	@Override
	public boolean rejectIt()
	{
		handler.rejectIt(model);
		return true;
	}

	@Override
	public String prepareIt()
	{
		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_PREPARE);
		final String newDocStatus = handler.prepareIt(model);
		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_PREPARE);

		return newDocStatus;
	}

	@Override
	public String completeIt()
	{
		// Re-Check
		if (!m_justPrepared)
		{
			final String status = prepareIt();
			if (!DocAction.STATUS_InProgress.equals(status))
			{
				return status;
			}
		}

		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_COMPLETE);

		final String newDocStatus = handler.completeIt(model);
		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_COMPLETE);
		model.setProcessed(true);
		return newDocStatus;
	}

	@Override
	public boolean voidIt()
	{
		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_VOID);
		handler.voidIt(model);
		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_VOID);
		return true;
	}

	@Override
	public boolean closeIt()
	{
		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_CLOSE);
		handler.closeIt(model);
		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_CLOSE);
		return true;
	}

	@Override
	public boolean reverseCorrectIt()
	{
		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REVERSECORRECT);
		handler.reverseCorrectIt(model);
		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSECORRECT);
		return true;
	}

	@Override
	public boolean reverseAccrualIt()
	{
		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REVERSEACCRUAL);
		handler.reverseAccrualIt(model);
		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
		return true;
	}

	@Override
	public boolean reActivateIt()
	{
		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_BEFORE_REACTIVATE);
		handler.reactivateIt(model);
		ModelValidationEngine.get().fireDocValidate(this, ModelValidator.TIMING_AFTER_REACTIVATE);
		return true;
	}

	@Override
	public String getSummary()
	{
		return handler.getSummary(model);
	}

	@Override
	public String getDocumentInfo()
	{
		return handler.getDocumentInfo(model);
	}

	@Override
	public String getDocumentNo()
	{
		return model.getDocumentNo();
	}

	@Override
	public File createPDF()
	{
		return handler.createPDF(model);
	}

	@Override
	public String getProcessMsg()
	{
		return m_processMsg;
	}

	@Override
	public int getDoc_User_ID()
	{
		return handler.getDoc_User_ID(model);
	}

	@Override
	public int getC_Currency_ID()
	{
		return handler.getC_Currency_ID(model);
	}

	@Override
	public BigDecimal getApprovalAmt()
	{
		return handler.getApprovalAmt(model);
	}

	@Override
	public int getAD_Client_ID()
	{
		return model.getAD_Client_ID();
	}

	@Override
	public int getAD_Org_ID()
	{
		return model.getAD_Org_ID();
	}

	@Override
	public String getDocAction()
	{
		return model.getDocAction();
	}

	@Override
	public boolean save()
	{
		InterfaceWrapperHelper.save(model);
		return true;
	}

	@Override
	public Properties getCtx()
	{
		return InterfaceWrapperHelper.getCtx(model);
	}

	@Override
	public int get_ID()
	{
		return InterfaceWrapperHelper.getId(model);
	}

	@Override
	public int get_Table_ID()
	{
		return InterfaceWrapperHelper.getModelTableId(model);
	}

	@Override
	public Logger get_Logger()
	{
		return logger;
	}

	@Override
	public String get_TrxName()
	{
		return InterfaceWrapperHelper.getTrxName(model);
	}

	@Override
	public void set_TrxName(final String trxName)
	{
		InterfaceWrapperHelper.setTrxName(model, trxName);
	}

	@Override
	public boolean isActive()
	{
		return model.isActive();
	}
}
