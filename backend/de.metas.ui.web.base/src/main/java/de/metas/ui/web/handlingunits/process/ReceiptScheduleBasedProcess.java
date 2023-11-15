package de.metas.ui.web.handlingunits.process;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.blockstatus.BPartnerBlockStatusService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.i18n.AdMessageKey;
import de.metas.inoutcandidate.api.IReceiptScheduleBL;
import de.metas.process.IProcessPrecondition;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.receiptSchedule.HUsToReceiveViewFactory;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;

import java.util.Collection;
import java.util.List;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

public abstract class ReceiptScheduleBasedProcess extends JavaProcess implements IProcessPrecondition
{
	private static final AdMessageKey MSG_HU_WITH_BLOCKED_PARTNER = AdMessageKey.of("CannotReceiveHUWithBlockedPartner");

	protected final IReceiptScheduleBL receiptScheduleBL = Services.get(IReceiptScheduleBL.class);
	protected final BPartnerBlockStatusService bPartnerBlockStatusService = SpringContextHolder.instance.getBean(BPartnerBlockStatusService.class);

	protected final void openHUsToReceive(final Collection<I_M_HU> hus)
	{
		getResult().setRecordsToOpen(TableRecordReference.ofCollection(hus), HUsToReceiveViewFactory.WINDOW_ID_STRING);
	}

	protected ProcessPreconditionsResolution checkEligibleForReceivingHUs(@NonNull final List<I_M_ReceiptSchedule> receiptSchedules)
	{
		return receiptSchedules.stream()
				.map(this::checkEligibleForReceivingHUs)
				.filter(ProcessPreconditionsResolution::isRejected)
				.findFirst()
				.orElseGet(ProcessPreconditionsResolution::accept);
	}

	protected ProcessPreconditionsResolution checkEligibleForReceivingHUs(@NonNull final I_M_ReceiptSchedule receiptSchedule)
	{
		// Receipt schedule shall not be already closed
		if (receiptScheduleBL.isClosed(receiptSchedule))
		{
			return ProcessPreconditionsResolution.reject("receipt schedule closed");
		}

		// Receipt schedule shall not be about packing materials
		if (receiptSchedule.isPackagingMaterial())
		{
			return ProcessPreconditionsResolution.reject("not applying for packing materials");
		}

		if (bPartnerBlockStatusService.isBPartnerBlocked(BPartnerId.ofRepoId(receiptSchedule.getC_BPartner_ID())))
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_HU_WITH_BLOCKED_PARTNER));
		}

		return ProcessPreconditionsResolution.accept();
	}

	protected ProcessPreconditionsResolution checkSingleBPartner(@NonNull final List<I_M_ReceiptSchedule> receiptSchedules)
	{
		//
		// If more than one line selected, make sure the lines make sense together
		// * enforce same BPartner (task https://github.com/metasfresh/metasfresh-webui/issues/207)
		if (receiptSchedules.size() > 1)
		{
			final long bpartnersCount = receiptSchedules
					.stream()
					.map(receiptScheduleBL::getBPartnerEffectiveId)
					.distinct()
					.count();
			if (bpartnersCount != 1)
			{
				return ProcessPreconditionsResolution.rejectWithInternalReason("select only one BPartner");
			}
		}

		return ProcessPreconditionsResolution.accept();
	}
}
