/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.user.impexp;

import static de.metas.impexp.format.ImportTableDescriptor.COLUMNNAME_I_ErrorMsg;
import static de.metas.impexp.format.ImportTableDescriptor.COLUMNNAME_I_IsImported;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.adempiere.ad.persistence.TableModelLoader;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IMutable;
import org.apache.commons.lang3.RandomStringUtils;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_I_BPartner;
import org.compiere.model.I_I_User;
import org.compiere.model.PO;
import org.compiere.model.X_I_User;
import org.compiere.util.DB;
import org.slf4j.Logger;

import de.metas.adempiere.model.I_AD_Role;
import de.metas.impexp.processing.ImportRecordsSelection;
import de.metas.impexp.processing.SimpleImportProcessTemplate;
import de.metas.logging.LogManager;
import de.metas.security.IRoleDAO;
import de.metas.security.RoleId;
import de.metas.user.UserId;
import de.metas.user.api.IUserBL;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * Imports {@link I_I_User} records to {@link I_AD_User}.
 *
 * @author cg
 *
 */
public class ADUserImportProcess extends SimpleImportProcessTemplate<I_I_User>
{
	private static final Logger log = LogManager.getLogger(ADUserImportProcess.class);

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
		final ImportRecordsSelection selection = getImportRecordsSelection();

		final String sqlImportWhereClause = COLUMNNAME_I_IsImported + "<>" + DB.TO_BOOLEAN(true)
				+ "\n " + selection.toSqlWhereClause("i");
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
		{

			final StringBuilder sqlUpdateByValue = new StringBuilder("UPDATE " + I_I_User.Table_Name + " i " + "SET "
					+ I_I_User.COLUMNNAME_C_BPartner_ID + "=bp." + I_C_BPartner.COLUMNNAME_C_BPartner_ID
					+ " FROM " + I_C_BPartner.Table_Name + " bp"
					+ " WHERE i." + I_I_User.COLUMNNAME_C_BPartner_ID + " IS NULL "
					+ " AND ( bp." + I_C_BPartner.COLUMNNAME_Value + " =i." + I_I_User.COLUMNNAME_BPValue + ")"

					+ " AND bp." + I_C_BPartner.COLUMNNAME_AD_Client_ID + " = i." + I_I_User.COLUMNNAME_AD_Client_ID
					+ " AND bp." + I_C_BPartner.COLUMNNAME_IsActive + " ='Y' "
					+ " AND i." + I_I_User.COLUMNNAME_I_IsImported + " = 'N' AND ")

							.append(sqlImportWhereClause);

			final int resultsForValueUpdate = DB.executeUpdateEx(sqlUpdateByValue.toString(), trxName);
			log.debug("Set C_BPartner_ID for {} records", resultsForValueUpdate);

		}

		{

			final StringBuilder sqlUpdateByGlobalId = new StringBuilder("UPDATE " + I_I_User.Table_Name + " i " + "SET "
					+ I_I_User.COLUMNNAME_C_BPartner_ID + "=bp." + I_C_BPartner.COLUMNNAME_C_BPartner_ID
					+ " FROM " + I_C_BPartner.Table_Name + " bp"
					+ " WHERE i." + I_I_User.COLUMNNAME_C_BPartner_ID + " IS NULL "
					+ " AND ( bp." + I_C_BPartner.COLUMNNAME_GlobalId + "=i." + I_I_User.COLUMNNAME_GlobalId + ")"
					+ " AND bp." + I_C_BPartner.COLUMNNAME_GlobalId + " IS NOT NULL "
					+ " AND bp." + I_C_BPartner.COLUMNNAME_AD_Client_ID + " = i." + I_I_BPartner.COLUMNNAME_AD_Client_ID
					+ " AND bp." + I_C_BPartner.COLUMNNAME_IsActive + " ='Y' "
					+ " AND i." + I_I_User.COLUMNNAME_I_IsImported + " = 'N' AND ")

							.append(sqlImportWhereClause);

			final int resultsForGlobalIdUpdate = DB.executeUpdateEx(sqlUpdateByGlobalId.toString(), trxName);
			log.debug("Set C_BPartner_ID for {} records", resultsForGlobalIdUpdate);

		}

		// Flag missing BPartners
		markAsError("BPartner not found", I_I_User.COLUMNNAME_C_BPartner_ID + " IS NULL"
				+ "\n AND " + sqlImportWhereClause);
	}

	private void dbUpdateRoleIds(final String sqlImportWhereClause)
	{
		final String trxName = ITrx.TRXNAME_ThreadInherited;

		final String sqlSelectByValue = "select MIN(r." + I_AD_Role.COLUMNNAME_AD_Role_ID + ")"
				+ " from " + I_AD_Role.Table_Name + " r "
				+ " where r." + I_AD_Role.COLUMNNAME_Name + "=i." + I_I_User.COLUMNNAME_RoleName
				+ " and r." + I_AD_Role.COLUMNNAME_AD_Client_ID + "=i." + I_I_User.COLUMNNAME_AD_Client_ID;

		final String sql = "UPDATE " + I_I_User.Table_Name + " i "
				+ "\n SET " + I_I_User.COLUMNNAME_AD_Role_ID + "=(" + sqlSelectByValue + ")"
				+ "\n WHERE " + sqlImportWhereClause
				+ "\n AND i." + I_I_User.COLUMNNAME_AD_Role_ID + " IS NULL";

		final int no = DB.executeUpdateEx(sql, trxName);
		log.debug("Set R_RequestType_ID for {} records", no);
	}

	private final void markAsError(final String errorMsg, final String sqlWhereClause)
	{
		final String sql = "UPDATE " + I_I_User.Table_Name + " i "
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

	/*
	 * @param isInsertOnly ignored. This import is only for updates.
	 */
	@Override
	protected ImportRecordResult importRecord(final @NonNull IMutable<Object> state,
			final @NonNull I_I_User importRecord,
			final boolean isInsertOnly) throws Exception
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
		// set data from the other fields
		setUserFieldsAndSave(user, importRecord);

		//
		// Assign Role
		final RoleId roleId = RoleId.ofRepoIdOrNull(importRecord.getAD_Role_ID());

		if (roleId != null)
		{
			final UserId userId = UserId.ofRepoId(user.getAD_User_ID());
			Services.get(IRoleDAO.class).createUserRoleAssignmentIfMissing(userId, roleId);
		}
		//
		// Link back the request to current import record
		importRecord.setAD_User_ID(user.getAD_User_ID());
		//
		return ImportRecordResult.Inserted;
	}

	private void setUserFieldsAndSave(@NonNull final I_AD_User user, @NonNull final I_I_User importRecord)
	{
		user.setFirstname(importRecord.getFirstname());
		user.setLastname(importRecord.getLastname());
		// set value after we set first name and last name
		user.setValue(importRecord.getUserValue());
		user.setEMail(importRecord.getEMail());

		user.setIsNewsletter(importRecord.isNewsletter());
		user.setPhone(importRecord.getPhone());
		user.setFax(importRecord.getFax());
		user.setMobilePhone(importRecord.getMobilePhone());
		// user.gen

		final de.metas.adempiere.model.I_AD_User loginUser = InterfaceWrapperHelper.create(user, de.metas.adempiere.model.I_AD_User.class);
		loginUser.setLogin(importRecord.getLogin());
		loginUser.setIsSystemUser(importRecord.isSystemUser());

		final IUserBL userBL = Services.get(IUserBL.class);
		userBL.changePasswordAndSave(loginUser, RandomStringUtils.randomAlphanumeric(8));
	}

	@Override
	protected void markImported(final I_I_User importRecord)
	{
		importRecord.setI_IsImported(X_I_User.I_ISIMPORTED_Imported);
		importRecord.setProcessed(true);
		InterfaceWrapperHelper.save(importRecord);
	}
}
