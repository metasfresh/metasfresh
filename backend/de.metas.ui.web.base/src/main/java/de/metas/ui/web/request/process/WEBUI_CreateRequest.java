/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.ui.web.request.process;

import ch.qos.logback.classic.Level;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.common.util.time.SystemTime;
import de.metas.document.references.zoom_into.RecordWindowFinder;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.logging.LogManager;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessExecutionResult.RecordsToOpen.OpenTarget;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.request.api.IRequestDAO;
import de.metas.request.api.IRequestTypeDAO;
import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_R_Request;
import org.slf4j.Logger;

import java.sql.Timestamp;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.save;

public class WEBUI_CreateRequest extends JavaProcess implements IProcessPrecondition
{
	private static Logger logger = LogManager.getLogger(WEBUI_CreateRequest.class);

	private final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);
	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	private final IUserDAO userDAO = Services.get(IUserDAO.class);
	private final IRequestDAO requestDAO = Services.get(IRequestDAO.class);
	private final IRequestTypeDAO requestTypeDAO = Services.get(IRequestTypeDAO.class);
	private final Optional<AdWindowId> requestWindowId = RecordWindowFinder.findAdWindowId(I_R_Request.Table_Name);

	public WEBUI_CreateRequest()
	{
		SpringContextHolder.instance.autowire(this);
	}

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (!requestWindowId.isPresent())
		{
			final ILoggable loggable = Loggables.withLogger(logger, Level.WARN);
			loggable.addLog("No default window for the R_Request table");
			return ProcessPreconditionsResolution.rejectWithInternalReason("No default window for the R_Request table!");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	@RunOutOfTrx
	protected String doIt() throws Exception
	{
		final String tableName = getTableName();
		final I_R_Request request;
		if (I_C_BPartner.Table_Name.equals(tableName))
		{
			final I_C_BPartner bPartner = bPartnerDAO.getById(getProcessInfo().getRecord_ID());
			request = createRequestFromBPartner(bPartner);
		}
		else if (I_M_InOut.Table_Name.equals(tableName))
		{
			final I_M_InOut shipment = inOutDAO.getById(InOutId.ofRepoId(getProcessInfo().getRecord_ID()));
			request = createRequestFromShipment(shipment);
		}
		else if (I_AD_User.Table_Name.equals(tableName))
		{
			final I_AD_User user = userDAO.getById(UserId.ofRepoId(getProcessInfo().getRecord_ID()));
			request = createRequestFromUser(user);
		}
		else
		{
			throw new IllegalStateException("Not supported: " + tableName);
		}
		save(request);
		getResult().setRecordToOpen(TableRecordReference.of(request), requestWindowId.get().getRepoId(), OpenTarget.SingleDocumentModal);

		return MSG_OK;
	}

	private I_R_Request createEmptyRequest()
	{
		final I_R_Request request = requestDAO.createEmptyRequest();
		request.setR_RequestType_ID(requestTypeDAO.retrieveDefaultRequestTypeIdOrFirstActive().getRepoId());
		final Timestamp date = SystemTime.asDayTimestamp();
		request.setDateTrx(date);
		request.setStartTime(date);
		return request;
	}

	private I_R_Request createRequestFromBPartner(final I_C_BPartner bpartner)
	{
		final I_AD_User defaultContact = Services.get(IBPartnerDAO.class).retrieveDefaultContactOrNull(bpartner, I_AD_User.class);

		final I_R_Request request = createEmptyRequest();

		request.setSalesRep_ID(getAD_User_ID());
		request.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		if (defaultContact != null)
		{
			request.setAD_User_ID(defaultContact.getAD_User_ID());
		}

		return request;
	}

	private I_R_Request createRequestFromShipment(final I_M_InOut shipment)
	{

		final I_C_BPartner bPartner = bPartnerDAO.getById(shipment.getC_BPartner_ID());
		final I_AD_User defaultContact = Services.get(IBPartnerDAO.class).retrieveDefaultContactOrNull(bPartner, I_AD_User.class);
		final I_R_Request request = createEmptyRequest();

		request.setSalesRep_ID(getAD_User_ID());
		request.setC_BPartner_ID(shipment.getC_BPartner_ID());
		request.setM_InOut_ID(shipment.getM_InOut_ID());
		request.setDateDelivered(shipment.getMovementDate());
		if (defaultContact != null)
		{
			request.setAD_User_ID(defaultContact.getAD_User_ID());
		}

		return request;
	}

	private I_R_Request createRequestFromUser(final I_AD_User user)
	{
		final I_R_Request request = createEmptyRequest();

		request.setAD_User_ID(user.getAD_User_ID());
		request.setC_BPartner_ID(user.getC_BPartner_ID());

		return request;
	}

}
