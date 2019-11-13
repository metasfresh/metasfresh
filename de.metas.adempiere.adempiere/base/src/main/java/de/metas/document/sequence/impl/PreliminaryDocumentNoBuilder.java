package de.metas.document.sequence.impl;

import java.util.Date;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_DocType;
import org.compiere.model.MSequence;
import org.compiere.util.Env;

import de.metas.document.DocTypeSequenceMap;
import de.metas.document.DocumentNoBuilderException;
import de.metas.document.DocumentSequenceInfo;
import de.metas.document.IDocumentSequenceDAO;
import de.metas.document.sequence.DocSequenceId;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.CoalesceUtil;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/* package */final class PreliminaryDocumentNoBuilder implements IPreliminaryDocumentNoBuilder
{
	// services
	private final transient IDocumentSequenceDAO documentSequenceDAO = Services.get(IDocumentSequenceDAO.class);

	private static final String DOCSUBTYPE_NONE = "--";

	//
	// Parameters
	private I_C_DocType _newDocType;
	private int _oldDocType_ID = -1;
	private I_C_DocType _oldDocType; // lazy
	private String _oldDocumentNo = null;
	private Object _documentModel;
	private ClientId _adClientId; // lazy
	private OrgId _adOrgId; // lazy

	//
	// State
	private final AtomicBoolean _built = new AtomicBoolean(false);
	private DocSequenceId _oldSequence_ID = null;  // lazy

	/* package */ PreliminaryDocumentNoBuilder()
	{
		super();
	}

	@Override
	public IDocumentNoInfo buildOrNull()
	{
		// Check if already built
		if (_built.getAndSet(true))
		{
			throw new IllegalStateException("Already built");
		}

		try
		{
			return buildOrNull0();
		}
		catch (final Exception ex)
		{
			throw DocumentNoBuilderException.wrapIfNeeded(ex);
		}
	}

	private IDocumentNoInfo buildOrNull0()
	{
		final I_C_DocType newDocType = getNewDocType();
		if (newDocType == null)
		{
			return null;
		}

		final String docBaseType = newDocType.getDocBaseType();
		final String docSubType = CoalesceUtil.coalesce(newDocType.getDocSubType(), DOCSUBTYPE_NONE);
		final boolean isSOTrx = newDocType.isSOTrx();
		final boolean hasChanges = newDocType.isHasCharges();

		// DocumentNo
		final String newDocumentNo;
		final boolean isDocNoControlled = newDocType.isDocNoControlled();
		if (isDocNoControlled)
		{
			final DocTypeSequenceMap newDocTypeSequenceMap = documentSequenceDAO.retrieveDocTypeSequenceMap(newDocType);
			final DocSequenceId newDocSequenceId = newDocTypeSequenceMap.getDocNoSequenceId(getClientId(), getOrgId());
			final boolean isNewDocumentNo = isNewDocumentNo() || !DocSequenceId.equals(newDocSequenceId, getOldSequenceId());

			if (isNewDocumentNo)
			{
				newDocumentNo = retrieveCurrentDocumentNo(newDocSequenceId);
			}
			else
			{
				// keep the old documentNo
				newDocumentNo = getOldDocumentNo();
			}
		}
		else
		{
			newDocumentNo = null;
		}

		return DocumentNoInfo.builder()
				.setDocumentNo(newDocumentNo)
				.setDocBaseType(docBaseType)
				.setDocSubType(docSubType)
				.setIsSOTrx(isSOTrx)
				.setHasChanges(hasChanges)
				.setDocNoControlled(isDocNoControlled)
				.build();
	}

	private String retrieveCurrentDocumentNo(final DocSequenceId docSequenceId)
	{
		final ClientId adClientId = getClientId();
		if (MSequence.isAdempiereSys(adClientId.getRepoId()))
		{
			final String documentNo = documentSequenceDAO.retrieveDocumentNoSys(docSequenceId.getRepoId());
			return IPreliminaryDocumentNoBuilder.withPreliminaryMarkers(documentNo);
		}
		else
		{
			final DocumentSequenceInfo newDocumentSeqInfo = documentSequenceDAO.retriveDocumentSequenceInfo(docSequenceId);
			final boolean isStartNewYear = newDocumentSeqInfo != null && newDocumentSeqInfo.isStartNewYear();
			if (isStartNewYear)
			{
				final String dateColumnName = newDocumentSeqInfo.getDateColumn();
				final Date date = getDocumentDate(dateColumnName);
				final String documentNo = documentSequenceDAO.retrieveDocumentNoByYear(docSequenceId.getRepoId(), date);
				return IPreliminaryDocumentNoBuilder.withPreliminaryMarkers(documentNo);
			}
			else
			{
				final String documentNo = documentSequenceDAO.retrieveDocumentNo(docSequenceId.getRepoId());
				return IPreliminaryDocumentNoBuilder.withPreliminaryMarkers(documentNo);
			}
		}
	}

	private void assertNotBuilt()
	{
		Check.assume(!_built.get(), "not already built");
	}

	private DocSequenceId getOldSequenceId()
	{
		if (_oldSequence_ID == null)
		{
			_oldSequence_ID = retrieveOldSequenceId();
		}
		return _oldSequence_ID;
	}

	private DocSequenceId retrieveOldSequenceId()
	{
		final boolean isNewDocumentNo = isNewDocumentNo();
		if (isNewDocumentNo)
		{
			return null;
		}

		final I_C_DocType oldDocType = getOldDocType();
		if (oldDocType == null)
		{
			return null;
		}

		final DocTypeSequenceMap oldDocTypeSequenceMap = documentSequenceDAO.retrieveDocTypeSequenceMap(oldDocType);
		return oldDocTypeSequenceMap.getDocNoSequenceId(getClientId(), getOrgId());
	}

	private Properties getCtx()
	{
		return Env.getCtx();
	}

	private ClientId getClientId()
	{
		if (_adClientId == null)
		{
			_adClientId = extractClientId();
		}
		return _adClientId;
	}

	private ClientId extractClientId()
	{
		final Object documentModel = getDocumentModel();
		if (documentModel != null)
		{
			final Integer adClientId = InterfaceWrapperHelper.getValueOrNull(documentModel, "AD_Client_ID");
			if (adClientId != null)
			{
				return ClientId.ofRepoId(adClientId);
			}
		}

		// shall not happen
		throw new DocumentNoBuilderException("Could not get AD_Client_ID");
	}

	private OrgId getOrgId()
	{
		if (_adOrgId == null)
		{
			_adOrgId = extractOrgId();
		}
		return _adOrgId;
	}

	private OrgId extractOrgId()
	{
		final Object documentModel = getDocumentModel();
		if (documentModel != null)
		{
			final Integer adOrgId = InterfaceWrapperHelper.getValueOrNull(documentModel, "AD_Org_ID");
			if (adOrgId != null)
			{
				return OrgId.ofRepoId(adOrgId);
			}
			else
			{
				// return Org=* to cover the user case when user clears (temporary) the Org field.
				return OrgId.ANY;
			}
		}

		// shall not happen
		throw new DocumentNoBuilderException("Could not get AD_Org_ID");
	}

	@Override
	public IPreliminaryDocumentNoBuilder setNewDocType(final I_C_DocType newDocType)
	{
		assertNotBuilt();
		_newDocType = newDocType;
		return this;
	}

	private I_C_DocType getNewDocType()
	{
		return _newDocType;
	}

	@Override
	public IPreliminaryDocumentNoBuilder setOldDocType_ID(final int oldDocType_ID)
	{
		assertNotBuilt();
		_oldDocType_ID = oldDocType_ID > 0 ? oldDocType_ID : -1;
		return this;
	}

	private I_C_DocType getOldDocType()
	{
		if (_oldDocType == null)
		{
			final int oldDocTypeId = _oldDocType_ID;
			if (oldDocTypeId > 0)
			{
				_oldDocType = InterfaceWrapperHelper.create(getCtx(), oldDocTypeId, I_C_DocType.class, ITrx.TRXNAME_None);
			}
		}
		return _oldDocType;
	}

	@Override
	public IPreliminaryDocumentNoBuilder setOldDocumentNo(final String oldDocumentNo)
	{
		assertNotBuilt();
		_oldDocumentNo = oldDocumentNo;
		return this;
	}

	private String getOldDocumentNo()
	{
		return _oldDocumentNo;
	}

	private boolean isNewDocumentNo()
	{
		final String oldDocumentNo = getOldDocumentNo();
		if (oldDocumentNo == null)
		{
			return true;
		}
		if (IPreliminaryDocumentNoBuilder.hasPreliminaryMarkers(oldDocumentNo))
		{
			return true;
		}

		return false;
	}

	@Override
	public IPreliminaryDocumentNoBuilder setDocumentModel(final Object documentModel)
	{
		_documentModel = documentModel;
		return this;
	}

	private Object getDocumentModel()
	{
		Check.assumeNotNull(_documentModel, "_documentModel not null");
		return _documentModel;
	}

	private java.util.Date getDocumentDate(final String dateColumnName)
	{
		final Object documentModel = getDocumentModel();
		final Optional<java.util.Date> date = InterfaceWrapperHelper.getValue(documentModel, dateColumnName);
		return date.orElse(null);
	}
}
