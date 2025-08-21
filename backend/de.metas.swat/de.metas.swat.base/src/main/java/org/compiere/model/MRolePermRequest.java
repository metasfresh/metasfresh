/**
 *
 */
package org.compiere.model;

import java.io.Serial;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;

import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.exceptions.DBForeignKeyConstraintException;
import org.adempiere.service.IRolePermLoggingBL.NoSuchForeignKeyException;
import org.adempiere.service.ISysConfigBL;
import org.compiere.util.Env;

import de.metas.document.DocTypeId;
import de.metas.organization.OrgId;
import de.metas.security.RoleId;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * @author teo_sarca
 *
 */
public class MRolePermRequest extends X_AD_Role_PermRequest
{
	@Serial
	private static final long serialVersionUID = 7261084366898196463L;

	private static final String SYSCONFIG_PermLogLevel = "org.compiere.model.MRolePermRequest" + ".PermLogLevel";

	// private static final int PERMLOGLEVEL_AD_Reference_ID = 53356;
	private static final String PERMLOGLEVEL_Disabled = "D";
	// private static final String PERMLOGLEVEL_NotGranted = "E";
	private static final String PERMLOGLEVEL_Full = "F";

	private static final String DEFAULT_PermLogLevel = PERMLOGLEVEL_Disabled;

	public MRolePermRequest(final Properties ctx, final int AD_Role_PermRequest_ID, final String trxName)
	{
		super(ctx, AD_Role_PermRequest_ID, trxName);
	}

	public MRolePermRequest(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	public static String getPermLogLevel()
	{
		final int AD_Client_ID = Env.getAD_Client_ID(Env.getCtx());
		return Services.get(ISysConfigBL.class).getValue(SYSCONFIG_PermLogLevel, DEFAULT_PermLogLevel, AD_Client_ID);
	}

	public static void setPermLogLevel(final String permLogLevel)
	{
		final Properties ctx = Env.getCtx();

		Services.get(ISysConfigBL.class).setValue(SYSCONFIG_PermLogLevel, permLogLevel, Env.getClientId(), OrgId.ANY);
		Env.setContext(ctx, "P|" + SYSCONFIG_PermLogLevel, permLogLevel);
	}

	public static void logWindowAccess(final RoleId roleId, final int id, final Boolean access)
	{
		logAccess(roleId, COLUMNNAME_AD_Window_ID, id, null, null, access, null);
	}

	public static void logWindowAccess(final RoleId roleId, final AdWindowId id, final Boolean access, final String description)
	{
		logAccess(roleId, COLUMNNAME_AD_Window_ID, id.getRepoId(), null, null, access, description);
	}

	public static void logFormAccess(final RoleId roleId, final int id, final Boolean access)
	{
		logAccess(roleId, COLUMNNAME_AD_Form_ID, id, null, null, access, null);
	}

	public static void logProcessAccess(final RoleId roleId, final int id, final Boolean access)
	{
		logAccess(roleId, COLUMNNAME_AD_Process_ID, id, null, null, access, null);
	}

	public static void logTaskAccess(final RoleId roleId, final int id, final Boolean access)
	{
		logAccess(roleId, COLUMNNAME_AD_Task_ID, id, null, null, access, null);
	}

	public static void logWorkflowAccess(final RoleId roleId, final int id, final Boolean access)
	{
		logAccess(roleId, COLUMNNAME_AD_Workflow_ID, id, null, null, access, null);
	}

	public static void logDocActionAccess(final RoleId roleId, final DocTypeId docTypeId, final String docAction, final Boolean access)
	{
		logAccess(roleId, COLUMNNAME_C_DocType_ID, docTypeId.getRepoId(), COLUMNNAME_DocAction, docAction, access, null);
	}

	private static void logAccess(
			@NonNull final RoleId roleId,
			final String type,
			final Object value,
			final String type2,
			final Object value2,
			final Boolean access,
			final String description)
	{
		final String permLogLevel = getPermLogLevel();

		if (PERMLOGLEVEL_Disabled.equals(permLogLevel))
		{
			return;
		}

		final boolean isPermissionGranted = access != null;
		if (isPermissionGranted && !PERMLOGLEVEL_Full.equals(permLogLevel))
		{
			// Permission granted, and there is no need to log all requests => nothing to do
			return;
		}
		if (value instanceof Integer integer && integer.intValue() <= 0)
		{
			// Skip invalid permissions request
			return;
		}

		final Properties ctx = Env.getCtx();
		final String trxName = null;
		final boolean isReadWrite = access != null && access.booleanValue() == true;

		final ArrayList<Object> params = new ArrayList<>();
		final StringBuilder whereClause = new StringBuilder(COLUMNNAME_AD_Role_ID + "=? AND " + type + "=?");
		params.add(roleId);
		params.add(value);
		if (type2 != null)
		{
			whereClause.append(" AND " + type2 + "=?");
			params.add(value2);
		}
		MRolePermRequest req = new Query(ctx, Table_Name, whereClause.toString(), trxName)
				.setParameters(params)
				.setOrderBy(COLUMNNAME_AD_Role_PermRequest_ID + " DESC")
				.first();
		if (req == null)
		{
			req = new MRolePermRequest(ctx, 0, trxName);
			req.setAD_Role_ID(roleId.getRepoId());
			req.set_ValueOfColumn(type, value);
			if (type2 != null)
			{
				req.set_ValueOfColumn(type2, value2);
			}
		}

		req.setIsActive(true);
		req.setIsReadWrite(isReadWrite);
		req.setIsPermissionGranted(isPermissionGranted);
		req.setDescription(description);

		savePermissionRequestAndHandleException(type, value, req);
	}

	private static void savePermissionRequestAndHandleException(
			@NonNull final String type,
			@NonNull final Object value,
			@NonNull final MRolePermRequest req)
	{
		try
		{
			req.saveEx();
		}
		catch (final DBForeignKeyConstraintException e)
		{
			throw new NoSuchForeignKeyException(type + "=" + value + " is not a valid foreign key", e);
		}
	}
}
