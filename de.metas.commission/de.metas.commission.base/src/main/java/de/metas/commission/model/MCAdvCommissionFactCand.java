package de.metas.commission.model;

/*
 * #%L
 * de.metas.commission.base
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


import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.processing.interfaces.IProcessablePO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_AD_Column;
import org.compiere.model.MBPartner;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import com.google.common.base.Optional;

import de.metas.commission.service.ICommissionFactCandBL;

/**
 * 
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php/Provisionsberechnung_%282009_0023_G106%29'>(2009 0023 G106)</a>"
 */
public class MCAdvCommissionFactCand extends X_C_AdvCommissionFactCand implements IProcessablePO
{

	@Override
	protected boolean beforeSave(final boolean newRecord)
	{
		if (getAD_Table_ID() == 0)
		{
			throw new FillMandatoryException(I_C_AdvCommissionFactCand.COLUMNNAME_AD_Table_ID);
		}
		if (getRecord_ID() == 0)
		{
			throw new FillMandatoryException(I_C_AdvCommissionFactCand.COLUMNNAME_Record_ID);
		}
		return true;
	}

	private static final Logger logger = LogManager.getLogger(MCAdvCommissionFactCand.class);

	private MCAdvComRelevantPOType currentRelPOType;

	/**
	 * 
	 */
	private static final long serialVersionUID = 7154767052807928463L;

	public MCAdvCommissionFactCand(final Properties ctx, final int C_AdvCommissionFactCandidate_ID, final String trxName)
	{
		super(ctx, C_AdvCommissionFactCandidate_ID, trxName);
	}

	public MCAdvCommissionFactCand(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	/**
	 * Creates a new commission fact candidate.
	 * 
	 * @param po the po the cand will refer to
	 * @param relevantPO the relevant po that causes this cand to be created
	 */
	private MCAdvCommissionFactCand(final Object po, final I_C_AdvCommissionRelevantPO relevantPO)
	{
		super(InterfaceWrapperHelper.getCtx(po, true), 0, InterfaceWrapperHelper.getTrxName(po));

		Check.assumeNotNull(relevantPO, "Param 'relevantPO' is not null; po={0}", po);

		if (InterfaceWrapperHelper.getId(po) <= 0)
		{
			throw new IllegalArgumentException("Can't create a reference to " + po + " with ID=0");
		}
		setTrxName(InterfaceWrapperHelper.getTrxName(po));

		setAD_Table_ID(MTable.getTable_ID(InterfaceWrapperHelper.getModelTableName(po)));
		setRecord_ID(InterfaceWrapperHelper.getId(po));
		setSeqNo(relevantPO.getSeqNo());
		setInfo(relevantPO.isInfo());
		setC_AdvCommissionRelevantPO_ID(relevantPO.getC_AdvCommissionRelevantPO_ID());
		setDateAcct(Services.get(ICommissionFactCandBL.class).retrieveDateDocOfReferencedPO(this));
		setIsImmediateProcessingDone(false);
		setIsSubsequentProcessingDone(false);
	}

	public static MCAdvCommissionFactCand createNoSave(final Object po, final I_C_AdvCommissionRelevantPO relevantPO)
	{
		Check.errorUnless(relevantPO != null, "relevantPO may not be null; po={0}", po);
		return new MCAdvCommissionFactCand(po, relevantPO);
	}

	public static MBPartner retrieveBPartnerOfPO(final Object po)
	{
		final MCAdvCommissionRelevantPO comRelevantPO = MCAdvCommissionRelevantPO.retrieveIfRelevant(po);
		Check.assume(comRelevantPO != null, po + " has a C_AdvCommissionRelevantPO record");

		return retrieveBPartner(comRelevantPO, po);
	}

	public MBPartner retrieveBPartner()
	{
		final I_C_AdvCommissionRelevantPO advCommissionRelevantPO = getC_AdvCommissionRelevantPO();
		return retrieveBPartner(advCommissionRelevantPO, Services.get(ICommissionFactCandBL.class).retrievePO(this));
	}

	@Cached
	private static MBPartner retrieveBPartner(final I_C_AdvCommissionRelevantPO advCommissionRelevantPO, final Object po)
	{

		if (advCommissionRelevantPO.getBPartnerColumn_ID() <= 0)
		{
			MCAdvCommissionFactCand.logger.debug(advCommissionRelevantPO + " has no BPartnerColumn_ID");
			return null;
		}

		final I_AD_Column bPartnerCol = advCommissionRelevantPO.getBPartnerColumn();
		final Optional<Integer> bPartnerId = InterfaceWrapperHelper.getValue(po, bPartnerCol.getColumnName());

		if (!bPartnerId.isPresent() || bPartnerId.get() <= 0)
		{
			MCAdvCommissionFactCand.logger.debug(bPartnerCol.getColumnName() + "=" + bPartnerId);
			return null;
		}

		final MBPartner bPartner = new MBPartner(InterfaceWrapperHelper.getCtx(po), bPartnerId.get(), InterfaceWrapperHelper.getTrxName(po));

		return bPartner;
	}

	public static MCAdvCommissionFactCand createOrUpdate(final PO po, final MCAdvCommissionRelevantPO relevantPO)
	{
		Check.assume(relevantPO != null, "Param 'relevantPO' is not null");

		final String whereClause =
				I_C_AdvCommissionFactCand.COLUMNNAME_AD_Table_ID + "=? AND "
						+ I_C_AdvCommissionFactCand.COLUMNNAME_Record_ID + "=? AND "
						+ I_C_AdvCommissionFactCand.COLUMNNAME_C_AdvCommissionRelevantPO_ID + "=? AND "
						+ I_C_AdvCommissionFactCand.COLUMNNAME_TrxName + "=?";

		final Object[] parameters = {
				po.get_Table_ID(),
				po.get_ID(),
				relevantPO.get_ID(),
				po.get_TrxName()
		};

		final List<MCAdvCommissionFactCand> existingCands =
				new Query(po.getCtx(), I_C_AdvCommissionFactCand.Table_Name, whereClause, po.get_TrxName())
						.setParameters(parameters)
						.list();

		Check.assume(existingCands.size() <= 1, "There is max 1 existing candidate; existingCands=" + existingCands);

		if (existingCands.isEmpty())
		{
			final MCAdvCommissionFactCand newCand = new MCAdvCommissionFactCand(po, relevantPO);
			newCand.saveEx();
			return newCand;
		}

		final MCAdvCommissionFactCand existingCand = existingCands.get(0);

		existingCand.setSeqNo(relevantPO.getSeqNo());
		existingCand.setInfo(relevantPO.isInfo());
		existingCand.setIsSubsequentProcessingDone(false);
		existingCand.setIsImmediateProcessingDone(false);
		existingCand.saveEx();

		return existingCand;
	}

	@Override
	public String toString()
	{
		final StringBuffer sb = new StringBuffer();
		sb.append("MAdvCommissionFactCandidate[Id=");
		sb.append(get_ID());
		sb.append(", DateAcct=");
		sb.append(getDateAcct());
		sb.append(", AD_Table_ID=");
		sb.append(getAD_Table_ID());
		sb.append(", Record_ID=");
		sb.append(getRecord_ID());
		sb.append(", C_AdvCommissionRelevantPO_ID=");
		sb.append(getC_AdvCommissionRelevantPO_ID());
		sb.append(", Info=");
		sb.append(isInfo());
		sb.append("]");

		return sb.toString();
	}

	public static List<MCAdvCommissionFactCand> retrieveProcessedForPO(final Object po, final boolean onlyImmediateProcessed)
	{
		final StringBuilder wc = new StringBuilder();
		wc.append(I_C_AdvCommissionFactCand.COLUMNNAME_AD_Table_ID + "=? AND "
				+ I_C_AdvCommissionFactCand.COLUMNNAME_Record_ID + "=? AND ");

		if (onlyImmediateProcessed)
		{
			wc.append(I_C_AdvCommissionFactCand.COLUMNNAME_IsImmediateProcessingDone + "='Y'");
		}
		else
		{
			wc.append(I_C_AdvCommissionFactCand.COLUMNNAME_IsSubsequentProcessingDone + "='Y'");
		}

		return new Query(InterfaceWrapperHelper.getCtx(po), I_C_AdvCommissionFactCand.Table_Name, wc.toString(), InterfaceWrapperHelper.getTrxName(po))
				.setParameters(MTable.getTable_ID(InterfaceWrapperHelper.getModelTableName(po)), InterfaceWrapperHelper.getId(po))
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.setOrderBy(I_C_AdvCommissionFactCand.COLUMNNAME_C_AdvCommissionFactCand_ID)
				.list();
	}

	/**
	 * 
	 * @param po
	 * @param cand if not null, only candidates with a C_AdvCommissionFactCand_ID lower than the given candidate's ID are returned
	 * @return
	 */
	public static List<MCAdvCommissionFactCand> retrieveForPO(final PO po, final MCAdvCommissionFactCand cand)
	{
		final String wc = I_C_AdvCommissionFactCand.COLUMNNAME_AD_Table_ID + "=? AND " + I_C_AdvCommissionFactCand.COLUMNNAME_Record_ID + "=? AND "
				+ I_C_AdvCommissionFactCand.COLUMNNAME_C_AdvCommissionFactCand_ID + "<?";

		final Object[] params = {
				po.get_Table_ID(),
				po.get_ID(),
				cand == null ? Integer.MAX_VALUE : cand.get_ID() };

		return new Query(po.getCtx(), I_C_AdvCommissionFactCand.Table_Name, wc, po.get_TrxName())
				.setParameters(params)
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.setOrderBy(I_C_AdvCommissionFactCand.COLUMNNAME_C_AdvCommissionFactCand_ID)
				.list();
	}

	@Override
	public boolean isProcessed()
	{
		return isImmediateProcessingDone() && isSubsequentProcessingDone();
	}

	@Override
	public void setProcessed(final boolean Processed)
	{
		throw new AdempiereException("Method not supported for " + MCAdvCommissionFactCand.class.getSimpleName());
	}

	public void setCurrentRelPOType(final MCAdvComRelevantPOType relPOType)
	{
		currentRelPOType = relPOType;
	}

	public MCAdvComRelevantPOType getCurrentRelPOType()
	{
		return currentRelPOType;
	}
}
