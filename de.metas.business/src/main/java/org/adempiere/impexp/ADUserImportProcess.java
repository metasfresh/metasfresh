package org.adempiere.impexp;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.adempiere.ad.persistence.TableModelLoader;
import org.adempiere.ad.security.IRoleDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IMutable;
import org.apache.commons.lang3.RandomStringUtils;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_I_User;
import org.compiere.model.PO;
import org.compiere.model.X_I_User;
import org.compiere.util.DB;

import de.metas.adempiere.model.I_AD_Role;
import lombok.NonNull;

/**
 * Imports {@link I_I_User} records to {@link I_AD_User}.
 *
 * @author cg
 *
 */
public class ADUserImportProcess extends AbstractImportProcess<I_I_User>
{

	@Override
	public Class<I_I_User> getImportModelClass()
	{
		return I_I_User.class;
	}

	@Override
	public String getImportTableName()
	{
		return I_I_User.Table_Name;
	}

	@Override
	protected String getTargetTableName()
	{
		return I_AD_User.Table_Name;
	}

	@Override
	protected String getImportOrderBySql()
	{
		return I_I_User.COLUMNNAME_BPValue;
	}

	@Override
	protected void updateAndValidateImportRecords()
	{
		final String sqlImportWhereClause = COLUMNNAME_I_IsImported + "<>" + DB.TO_BOOLEAN(true)
				+ "\n " + getWhereClause();
		//
		// Update C_BPartner_ID by value
		{
			dbUpdateBPartnerIds(sqlImportWhereClause);
		}
		//
		// Update AD_Role_ID by Name
		{
			dbUpdateRoleIds(sqlImportWhereClause);
		}
	}

	private void dbUpdateBPartnerIds(final String sqlImportWhereClause)
	{
		final String trxName = ITrx.TRXNAME_ThreadInherited;

		final String sqlSelectByValue = "select MIN(bp." + I_C_BPartner.COLUMNNAME_C_BPartner_ID + ")"
				+ " from " + I_C_BPartner.Table_Name + " bp "
				+ " where bp." + I_C_BPartner.COLUMNNAME_Value + "=i." + I_I_User.COLUMNNAME_BPValue
				+ " and bp." + I_C_BPartner.COLUMNNAME_AD_Client_ID + "=i." + I_I_User.COLUMNNAME_AD_Client_ID;

		final String sql = "UPDATE " + I_I_User.Table_Name + " i "
				+ "\n SET " + I_I_User.COLUMNNAME_C_BPartner_ID + "=(" + sqlSelectByValue + ")"
				+ "\n WHERE " + sqlImportWhereClause
				+ "\n AND i." + I_I_User.COLUMNNAME_C_BPartner_ID + " IS NULL";

		final int no = DB.executeUpdateEx(sql, trxName);
		log.debug("Set C_BPartner_ID for {} records", no);
		//
		// Flag missing BPartners
		markAsError("BPartner not found", I_I_User.COLUMNNAME_C_BPartner_ID + " IS NULL"
				+ "\n AND " + sqlImportWhereClause);
	}

	private void dbUpdateRoleIds(final String sqlImportWhereClause)
	{
		final String trxName = ITrx.TRXNAME_ThreadInherited;

		final String sqlSelectByValue = "select MIN(r." + I_AD_Role.COLUMNNAME_AD_Role_ID + ")"
				+ " from " + I_AD_Role.Table_Name + " r "
				+ " where r." + I_AD_Role.COLUMNNAME_Name + "=u." + I_I_User.COLUMNNAME_RoleName
				+ " and r." + I_AD_Role.COLUMNNAME_AD_Client_ID + "=u." + I_I_User.COLUMNNAME_AD_Client_ID;

		final String sql = "UPDATE " + I_I_User.Table_Name + " u "
				+ "\n SET " + I_I_User.COLUMNNAME_AD_Role_ID + "=(" + sqlSelectByValue + ")"
				+ "\n WHERE " + sqlImportWhereClause
				+ "\n AND u." + I_I_User.COLUMNNAME_AD_Role_ID + " IS NULL";

		final int no = DB.executeUpdateEx(sql, trxName);
		log.debug("Set R_RequestType_ID for {} records", no);
		//
		// Flag missing AD_Role_ID
		markAsError("Role not found", I_I_User.COLUMNNAME_AD_Role_ID + " IS NULL"
				+ "\n AND " + sqlImportWhereClause);
	}

	private final void markAsError(final String errorMsg, final String sqlWhereClause)
	{
		final String sql = "UPDATE " + I_I_User.Table_Name
				+ "\n SET " + COLUMNNAME_I_IsImported + "=?, " + COLUMNNAME_I_ErrorMsg + "=" + COLUMNNAME_I_ErrorMsg + "||? "
				+ "\n WHERE " + sqlWhereClause;
		final Object[] sqlParams = new Object[] { X_I_User.I_ISIMPORTED_ImportFailed, errorMsg + "; " };
		DB.executeUpdateEx(sql, sqlParams, ITrx.TRXNAME_ThreadInherited);

	}

	@Override
	protected I_I_User retrieveImportRecord(final Properties ctx, final ResultSet rs) throws SQLException
	{
		final PO po = TableModelLoader.instance.getPO(ctx, I_I_User.Table_Name, rs, ITrx.TRXNAME_ThreadInherited);
		return InterfaceWrapperHelper.create(po, I_I_User.class);
	}

	@Override
	protected ImportRecordResult importRecord(final IMutable<Object> state, final I_I_User importRecord) throws Exception
	{
		//
		// Create a new user
		final I_AD_User user = InterfaceWrapperHelper.newInstance(I_AD_User.class, importRecord);
		user.setAD_Org_ID(importRecord.getAD_Org_ID());
		//
		// BPartner
		{
			int bpartnerId = importRecord.getC_BPartner_ID();
			if (bpartnerId <= 0)
			{
				throw new AdempiereException("BPartner not found");
			}
			user.setC_BPartner_ID(bpartnerId);
		}
		//
		// check role
		int roleId = importRecord.getAD_Role_ID();
		if (roleId <= 0)
		{
			throw new AdempiereException("Role not found");
		}
		//
		// set data from the other fields
		setUserFields(user, importRecord);
		//
		InterfaceWrapperHelper.save(user);
		//
		// Assign Role
		{
			Services.get(IRoleDAO.class).createUserRoleAssignmentIfMissing(user.getAD_User_ID(), roleId);
		}
		//
		// Link back the request to current import record
		importRecord.setAD_User(user);
		//
		return ImportRecordResult.Inserted;
	}
	
	private void setUserFields(@NonNull final I_AD_User user, @NonNull final I_I_User importRecord)
	{
		user.setFirstname(importRecord.getFirstname());
		user.setLastname(importRecord.getLastname());
		// set value after we set first name and last name
		user.setValue(importRecord.getValue());
		user.setEMail(importRecord.getEMail());
		final de.metas.adempiere.model.I_AD_User loginUser = InterfaceWrapperHelper.create(user, de.metas.adempiere.model.I_AD_User.class);
		loginUser.setLogin(importRecord.getLogin());
		loginUser.setPassword(RandomStringUtils.randomAlphanumeric(8));
		loginUser.setIsSystemUser(importRecord.isSystemUser());
	}

	@Override
	protected void markImported(final I_I_User importRecord)
	{
		importRecord.setI_IsImported(X_I_User.I_ISIMPORTED_Imported);
		importRecord.setProcessed(true);
		InterfaceWrapperHelper.save(importRecord);
	}
}
