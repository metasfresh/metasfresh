package de.metas.commission.modelvalidator;

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


import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.MBPartner;
import org.compiere.model.MClient;
import org.compiere.model.MSysConfig;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.util.Msg;
import org.eevolution.model.MHREmployee;
import org.slf4j.Logger;

import de.metas.commission.exception.CommissionException;
import de.metas.commission.interfaces.I_C_BPartner;
import de.metas.commission.model.I_C_AdvComSystem;
import de.metas.commission.model.I_C_Sponsor;
import de.metas.commission.model.I_C_Sponsor_SalesRep;
import de.metas.commission.model.MCAdvComSystem;
import de.metas.commission.service.ISponsorBL;
import de.metas.commission.service.ISponsorDAO;
import de.metas.logging.LogManager;

/**
 * 
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php/Provisionsberechnung_%282009_0023_G106%29'>(2009 0023 G106)</a>"
 */
public final class SponsorValidator implements ModelValidator
{

	public static final String SYSCFG_ON_INVALID_HIERARCHY_CHANGE_DENY = "DENY";

	public static final String SYSCFG_ON_INVALID_HIERARCHY_CHANGE_WARN = "WARN";

	public static final String SYSCFG_ON_INVALID_HIERARCHY_CHANGE = "de.metas.commission.onInvalidHierarchyChange";

	public static final String SYSCONFIG_DEFAULT_PARENT_SPONSOR_NO = "de.metas.commission.DefaultParentSponsorNo";

	private static final Logger logger = LogManager.getLogger(SponsorValidator.class);

	public static final String MSG_INCONSISTENT_PARENT_SPONSOR_MISSING_1P = "SSR_InconsistentComSystem_Parent_Sponsor_Missing";

	private int ad_Client_ID = -1;

	@Override
	public int getAD_Client_ID()
	{
		return ad_Client_ID;
	}

	@Override
	public void initialize(final ModelValidationEngine engine, final MClient client)
	{
		if (client != null)
		{
			ad_Client_ID = client.getAD_Client_ID();
		}
		engine.addModelChange(I_C_Sponsor.Table_Name, this);
		engine.addModelChange(org.compiere.model.I_C_BPartner.Table_Name, this);
		engine.addModelChange(I_C_AdvComSystem.Table_Name, this);
	}

	@Override
	public String login(final int AD_Org_ID, final int AD_Role_ID, final int AD_User_ID)
	{
		// nothing to do
		return null;
	}

	@Override
	public String modelChange(final PO po, final int type)
	{
		if (I_C_Sponsor.Table_Name.equals(po.get_TableName()))
		{
			final I_C_Sponsor sponsor = InterfaceWrapperHelper.create(po, I_C_Sponsor.class);
			return sponsorChange(sponsor, type);
		}
		else if (po instanceof MBPartner)
		{
			return bPartnerChange((MBPartner)po, type);
		}
		else if (po instanceof MCAdvComSystem)
		{
			return comSystemChange((MCAdvComSystem)po, type);
		}
		return null;
	}

	private String comSystemChange(final MCAdvComSystem comSystem, final int type)
	{
		if (type == ModelValidator.TYPE_BEFORE_NEW)
		{
			final I_C_Sponsor rootSponsor = InterfaceWrapperHelper.newInstance(I_C_Sponsor.class, comSystem);
			rootSponsor.setSponsorNo(comSystem.getName());
			InterfaceWrapperHelper.save(rootSponsor);

			comSystem.setC_Sponsor_Root_ID(rootSponsor.getC_Sponsor_ID());
		}

		return null;
	}

	/**
	 * Implemented for US1026:  aenderung Verguetungsplan (2011010610000028), R01A06
	 * 
	 * @param sponsor
	 * @param type
	 * @return
	 */
	private String sponsorChange(final I_C_Sponsor sponsor, final int type)
	{
		if (type == ModelValidator.TYPE_BEFORE_NEW || type == ModelValidator.TYPE_BEFORE_CHANGE)
		{
			final PO po = InterfaceWrapperHelper.getPO(sponsor);
			final Integer objOldSalaryGroupId = (Integer)po.get_ValueOld(I_C_Sponsor.COLUMNNAME_C_AdvCommissionSalaryGroup_ID);
			final int oldSalaryGroupId = objOldSalaryGroupId == null ? 0 : objOldSalaryGroupId;

			if (po.is_ValueChanged(I_C_Sponsor.COLUMNNAME_IsManualRank))
			{
				Services.get(ISponsorBL.class).onIsManualRankChange(sponsor, true, oldSalaryGroupId);
			}
			else if (po.is_ValueChanged(I_C_Sponsor.COLUMNNAME_C_AdvCommissionSalaryGroup_ID) && sponsor.isManualRank())
			{
				Services.get(ISponsorBL.class).onManualRankChange(sponsor, false, oldSalaryGroupId);
			}
		}
		return null;
	}

	String bPartnerChange(final MBPartner bPartnerPO, final int type)
	{
		final I_C_BPartner bPartner = InterfaceWrapperHelper.create(bPartnerPO, I_C_BPartner.class);

		final Properties ctx = bPartnerPO.getCtx();
		final String trxName = bPartnerPO.get_TrxName();

		if (type == ModelValidator.TYPE_BEFORE_NEW)
		{
			// 02249: making sure that the bPartner has a C_Sponsor_Parent_ID (if one has been configured)
			int bPartnerParentSponsorId = bPartner.getC_Sponsor_Parent_ID();

			if (bPartnerParentSponsorId <= 0)
			{
				final String parentSponsorNo =
						MSysConfig.getValue(SponsorValidator.SYSCONFIG_DEFAULT_PARENT_SPONSOR_NO, "",
								bPartnerPO.getAD_Client_ID(),
								bPartnerPO.getAD_Org_ID());
				bPartnerParentSponsorId =
						new Query(ctx, I_C_Sponsor.Table_Name, I_C_Sponsor.COLUMNNAME_SponsorNo + "=?", trxName)
								.setParameters(parentSponsorNo)
								.setApplyAccessFilter(true)
								.firstId();

				SponsorValidator.logger.info("setting C_Sponsor_Parent_ID=" + bPartnerParentSponsorId + " for " + bPartnerPO);
				bPartner.setC_Sponsor_Parent_ID(bPartnerParentSponsorId);
			}
		}
		else if (type == ModelValidator.TYPE_AFTER_NEW)
		{
			Services.get(ISponsorDAO.class).createNewForCustomer(bPartnerPO);
		}
		else if (type == ModelValidator.TYPE_AFTER_CHANGE)
		{
			if (bPartnerPO.is_ValueChanged(I_C_BPartner.COLUMNNAME_C_Sponsor_Parent_ID))
			{
				final I_C_Sponsor sponsor = Services.get(ISponsorDAO.class).retrieveForBPartner(bPartner, true);

				if (!bPartner.isParentSponsorReadWrite())
				{
					// TODO -> AD_Message
					return "Error: Parent Sponsor of BPartner " + bPartnerPO.getValue() + " is read only";
				}

				final List<I_C_Sponsor_SalesRep> ssrs = Services.get(ISponsorDAO.class).retrieveParentLinksSSRs(sponsor);

				if (ssrs.isEmpty())
				{
					return Msg.getMsg(ctx, SponsorValidator.MSG_INCONSISTENT_PARENT_SPONSOR_MISSING_1P, new Object[] { bPartnerPO.getValue() });
				}
				else if (ssrs.size() > 1)
				{
					// TODO -> AD_Message
					return "Error: BPartner " + bPartnerPO.getValue() + " has " + ssrs.size() + " parent link ssrs";
				}

				final int newParentSponsorId = bPartnerPO.get_ValueAsInt(I_C_BPartner.COLUMNNAME_C_Sponsor_Parent_ID);

				ssrs.get(0).setC_Sponsor_Parent_ID(newParentSponsorId);
				InterfaceWrapperHelper.save(ssrs.get(0));
			}
			if (bPartnerPO.is_ValueChanged(org.compiere.model.I_C_BPartner.COLUMNNAME_IsSalesRep))
			{
				if (bPartnerPO.isSalesRep())
				{
					MHREmployee employee = MHREmployee.getActiveEmployee(ctx, bPartnerPO.get_ID(), trxName);

					if (employee == null)
					{
						employee = new MHREmployee(ctx, 0, trxName);
						employee.setC_BPartner_ID(bPartnerPO.get_ID());
					}
					final int payrollId = getId(bPartnerPO, "de.metas.commission.payroll.HR_Payroll_ID");

					final int jobId = getId(bPartnerPO, "de.metas.commission.payroll.HR_Job_ID");

					final int departmentId = getId(bPartnerPO, "de.metas.commission.payroll.HR_Department_ID");

					employee.setHR_Payroll_ID(payrollId);
					employee.setHR_Job_ID(jobId);
					employee.setHR_Department_ID(departmentId);
					employee.setIsActive(true);

					// use the oldest ssr validFrom date as employee start date
					Timestamp validFrom = null;
					final Timestamp now = SystemTime.asTimestamp();

					final List<I_C_Sponsor_SalesRep> salesRepLinks =
							Services.get(ISponsorDAO.class).retrieveSalesRepLinks(ctx,
									bPartnerPO.get_ID(),
									now,
									now,
									trxName);

					for (final I_C_Sponsor_SalesRep ssr : salesRepLinks)
					{
						if (validFrom == null || validFrom.after(ssr.getValidFrom()))
						{
							validFrom = ssr.getValidFrom();
						}
					}
					Check.assume(validFrom != null,
							"At time " + now + ", there is at least one valid C_SponsorSalesRep record  for " + bPartnerPO);
					employee.setStartDate(validFrom);
					employee.saveEx();
					SponsorValidator.logger.info("Created/Updated " + employee);
				}
			}
		}
		return null;
	}

	private int getId(final MBPartner bPartner, final String paramName)
	{
		final int payrollId =
				MSysConfig.getIntValue(paramName, -1, bPartner.getAD_Client_ID(), bPartner.getAD_Org_ID());

		if (payrollId == -1)
		{
			throw CommissionException.inconsistentConfig("Fehlender Wert für '"
					+ paramName + "' im System-Konfigurator", null);
		}

		return payrollId;
	}

	@Override
	public String docValidate(final PO po, final int timing)
	{
		// nothing to do
		return null;
	}

}
