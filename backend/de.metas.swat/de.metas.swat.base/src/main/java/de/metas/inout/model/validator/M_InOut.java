package de.metas.inout.model.validator;

import de.metas.document.location.IDocumentLocationBL;
import de.metas.event.IEventBusFactory;
import de.metas.inout.IInOutBL;
import de.metas.inout.IInOutDAO;
import de.metas.inout.api.IInOutMovementBL;
import de.metas.inout.api.IMaterialBalanceDetailBL;
import de.metas.inout.api.IMaterialBalanceDetailDAO;
import de.metas.inout.event.InOutUserNotificationsProducer;
import de.metas.inout.event.ReturnInOutUserNotificationsProducer;
import de.metas.inout.location.InOutLocationsUpdater;
import de.metas.inout.model.I_M_InOut;
import de.metas.logging.TableRecordMDC;
import de.metas.request.service.async.spi.impl.C_Request_CreateFromInout_Async;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_MatchInv;
import org.compiere.model.ModelValidator;
import org.slf4j.MDC.MDCCloseable;

import java.util.List;

@Interceptor(I_M_InOut.class)
public class M_InOut
{
	private final IInOutBL inoutBL = Services.get(IInOutBL.class);
	private final IDocumentLocationBL documentLocationBL = SpringContextHolder.instance.getBean(IDocumentLocationBL.class);

	@Init
	public void onInit()
	{
		// Setup event bus topics on which swing client notification listener shall subscribe
		Services.get(IEventBusFactory.class).addAvailableUserNotificationsTopic(InOutUserNotificationsProducer.EVENTBUS_TOPIC);
		Services.get(IEventBusFactory.class).addAvailableUserNotificationsTopic(ReturnInOutUserNotificationsProducer.EVENTBUS_TOPIC);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void beforeSave_updateCapturedLocationsAndRenderedAddresses(final I_M_InOut inoutRecord)
	{
		try (final MDCCloseable ignored = TableRecordMDC.putTableRecordReference(inoutRecord))
		{
			InOutLocationsUpdater.builder()
					.documentLocationBL(documentLocationBL)
					.record(inoutRecord)
					.build()
					.updateAllIfNeeded();
		}
	}

	/**
	 * Reverse linked movements.
	 */
	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_REVERSECORRECT, ModelValidator.TIMING_BEFORE_REVERSEACCRUAL, ModelValidator.TIMING_BEFORE_VOID, ModelValidator.TIMING_BEFORE_REACTIVATE })
	public void reverseMovements(final I_M_InOut inoutRecord)
	{
		try (final MDCCloseable ignored = TableRecordMDC.putTableRecordReference(inoutRecord))
		{
			final IInOutMovementBL inoutMovementBL = Services.get(IInOutMovementBL.class);
			inoutMovementBL.reverseMovements(inoutRecord);
		}
	}

	/**
	 * Reverse {@link I_M_MatchInv} assignments.
	 */
	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_REVERSECORRECT, ModelValidator.TIMING_BEFORE_REVERSEACCRUAL, ModelValidator.TIMING_BEFORE_VOID, ModelValidator.TIMING_BEFORE_REACTIVATE })
	public void removeMatchInvAssignments(final I_M_InOut inoutRecord)
	{
		try (final MDCCloseable ignored = TableRecordMDC.putTableRecordReference(inoutRecord))
		{
			inoutBL.deleteMatchInvs(inoutRecord); // task 08531
		}
	}

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_COMPLETE })
	public void addInoutToBalance(final I_M_InOut inoutRecord)
	{
		try (final MDCCloseable ignored = TableRecordMDC.putTableRecordReference(inoutRecord))
		{
			final boolean isReversal = inoutBL.isReversal(inoutRecord);

			// do nothing in case of reversal
			if (!isReversal)
			{
				final IMaterialBalanceDetailBL materialBalanceDetailBL = Services.get(IMaterialBalanceDetailBL.class);
				materialBalanceDetailBL.addInOutToBalance(inoutRecord);
			}
		}
	}

	@DocValidate(timings = {
			ModelValidator.TIMING_BEFORE_REACTIVATE,
			ModelValidator.TIMING_BEFORE_REVERSECORRECT,
			ModelValidator.TIMING_BEFORE_VOID,
			ModelValidator.TIMING_AFTER_REVERSEACCRUAL,
			ModelValidator.TIMING_AFTER_CLOSE
	})
	public void removeInoutFromBalance(final I_M_InOut inoutRecord)
	{
		try (final MDCCloseable ignored = TableRecordMDC.putTableRecordReference(inoutRecord))
		{
			final IMaterialBalanceDetailDAO materialBalanceDetailDAO = Services.get(IMaterialBalanceDetailDAO.class);
			materialBalanceDetailDAO.removeInOutFromBalance(inoutRecord);
		}
	}

	/**
	 * After an inout is completed, check if it contains lines with quality discount percent.
	 * In case it does, create a request for each line that has a discount percent and fill it with the information from the line and the inout.
	 */
	@DocValidate(timings = ModelValidator.TIMING_AFTER_COMPLETE)
	public void onComplete_QualityIssues(final I_M_InOut inoutRecord)
	{
		try (final MDCCloseable ignored = TableRecordMDC.putTableRecordReference(inoutRecord))
		{
			// retrieve all lines with issues (quality discount percent)
			final List<Integer> linesWithQualityIssues = Services.get(IInOutDAO.class).retrieveLineIdsWithQualityDiscount(inoutRecord);

			if (linesWithQualityIssues.isEmpty())
			{
				// nothing to do
				return;
			}

			// In case there are lines with issues, trigger the request creation for them.
			// Note: The request creation will be done async
			C_Request_CreateFromInout_Async.createWorkpackage(linesWithQualityIssues);
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_NEW })
	public void cacheResetShipmentStatistics(final I_M_InOut inoutRecord)
	{
		try (final MDCCloseable ignored = TableRecordMDC.putTableRecordReference(inoutRecord))
		{
			inoutBL.invalidateStatistics(inoutRecord);
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_M_InOut.COLUMNNAME_C_DocType_ID})
	public void beforeSave_updateDescriptionAndDescriptionBottom(final I_M_InOut inoutRecord)
	{
		inoutBL.updateDescriptionAndDescriptionBottomFromDocType(inoutRecord);
	}
}
