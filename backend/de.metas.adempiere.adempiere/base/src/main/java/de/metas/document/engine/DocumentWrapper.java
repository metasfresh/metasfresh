package de.metas.document.engine;

import de.metas.organization.InstantAndOrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.DocTimingType;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.IModelWrapper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;

import java.io.File;
import java.math.BigDecimal;
import java.util.Properties;

// NOTE: IModelWrapper is required to be able to save the document (see de.metas.document.engine.impl.DocumentEngine.postIt(PostImmediate))
public class DocumentWrapper implements IDocument, IModelWrapper
{
	public static IDocument wrapModelUsingHandler(@NonNull final Object model, final DocumentHandler handler)
	{
		final DocumentTableFields docActionModel = InterfaceWrapperHelper.create(model, DocumentTableFields.class);
		return new DocumentWrapper(docActionModel, handler);
	}

	private final DocumentTableFields model;
	private final DocumentHandler handler;

	private String processMsg;
	private boolean justPrepared;

	private DocumentWrapper(@NonNull final DocumentTableFields docActionModel, @NonNull final DocumentHandler handler)
	{
		this.model = docActionModel;
		this.handler = handler;
	}

	private void fireDocValidateEvent(final int timing)
	{
		if (POJOWrapper.isHandled(model))
		{
			POJOLookupMap.get().fireDocumentChange(model, DocTimingType.valueOf(timing));
		}
		else
		{
			ModelValidationEngine.get().fireDocValidate(model, timing);
		}
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
	public boolean processIt(final String action)
	{
		processMsg = null;
		return Services.get(IDocumentBL.class).processIt(this, action);
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
		model.setDocAction(IDocument.ACTION_Prepare);
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
		fireDocValidateEvent(ModelValidator.TIMING_BEFORE_PREPARE);
		final String newDocStatus = handler.prepareIt(model);
		fireDocValidateEvent(ModelValidator.TIMING_AFTER_PREPARE);

		justPrepared = STATUS_InProgress.equals(newDocStatus);

		return newDocStatus;
	}

	@Override
	public String completeIt()
	{
		// Re-Check
		if (!justPrepared)
		{
			final String status = prepareIt();
			if (!IDocument.STATUS_InProgress.equals(status))
			{
				return status;
			}
		}

		fireDocValidateEvent(ModelValidator.TIMING_BEFORE_COMPLETE);
		final String newDocStatus = handler.completeIt(model);
		fireDocValidateEvent(ModelValidator.TIMING_AFTER_COMPLETE);
		model.setProcessed(true);
		return newDocStatus;
	}

	@Override
	public boolean voidIt()
	{
		fireDocValidateEvent(ModelValidator.TIMING_BEFORE_VOID);
		handler.voidIt(model);
		fireDocValidateEvent(ModelValidator.TIMING_AFTER_VOID);
		return true;
	}

	@Override
	public boolean closeIt()
	{
		fireDocValidateEvent(ModelValidator.TIMING_BEFORE_CLOSE);
		handler.closeIt(model);
		fireDocValidateEvent(ModelValidator.TIMING_AFTER_CLOSE);
		return true;
	}

	@Override
	public void unCloseIt()
	{
		fireDocValidateEvent(ModelValidator.TIMING_BEFORE_UNCLOSE);
		handler.unCloseIt(model);
		fireDocValidateEvent(ModelValidator.TIMING_AFTER_UNCLOSE);
	}

	@Override
	public boolean reverseCorrectIt()
	{
		fireDocValidateEvent(ModelValidator.TIMING_BEFORE_REVERSECORRECT);
		handler.reverseCorrectIt(model);
		fireDocValidateEvent(ModelValidator.TIMING_AFTER_REVERSECORRECT);
		return true;
	}

	@Override
	public boolean reverseAccrualIt()
	{
		fireDocValidateEvent(ModelValidator.TIMING_BEFORE_REVERSEACCRUAL);
		handler.reverseAccrualIt(model);
		fireDocValidateEvent(ModelValidator.TIMING_AFTER_REVERSEACCRUAL);
		return true;
	}

	@Override
	public boolean reActivateIt()
	{
		fireDocValidateEvent(ModelValidator.TIMING_BEFORE_REACTIVATE);
		handler.reactivateIt(model);
		fireDocValidateEvent(ModelValidator.TIMING_AFTER_REACTIVATE);
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
	public InstantAndOrgId getDocumentDate()
	{
		return handler.getDocumentDate(model);
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
		return processMsg;
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

	@Override
	public Object getModel()
	{
		return getDocumentModel();
	}

	@Override
	public Object getDocumentModel()
	{
		return model;
	}
}
