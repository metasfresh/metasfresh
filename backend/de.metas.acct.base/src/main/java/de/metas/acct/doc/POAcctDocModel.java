package de.metas.acct.doc;

import de.metas.document.engine.DocStatus;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import de.metas.user.UserId;
import de.metas.util.NumberUtils;
import de.metas.util.lang.RepoIdAware;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.acct.PostingStatus;
import org.compiere.model.PO;
import org.compiere.util.DisplayType;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.function.Function;
import java.util.function.IntFunction;

@EqualsAndHashCode
public class POAcctDocModel implements AcctDocModel
{
	private final PO po;

	public POAcctDocModel(@NonNull final PO po)
	{
		this.po = po;
	}

	@Override
	public String toString() {return po.toString();}

	@Override
	public <T> T unboxAs(@NonNull final Class<T> modelClass)
	{
		return InterfaceWrapperHelper.create(po, modelClass);
	}

	@Override
	public Object unbox() {return po;}

	@Override
	public final String getTableName() {return po.get_TableName();}

	@Override
	public final int getId() {return po.get_ID();}

	@Override
	public TableRecordReference getRecordRef() {return TableRecordReference.of(po.get_TableName(), po.get_ID());}

	@Override
	public ClientId getClientId()
	{
		return ClientId.ofRepoId(po.getAD_Client_ID());
	}

	@Override
	public OrgId getOrgId() {return OrgId.ofRepoId(po.getAD_Org_ID());}

	@Override
	public UserId getUpdatedBy() {return UserId.ofRepoIdOrSystem(po.getUpdatedBy());}

	@Override
	@Nullable
	public DocStatus getDocStatus()
	{
		final int index = po.get_ColumnIndex("DocStatus");
		return index >= 0
				? DocStatus.ofNullableCodeOrUnknown((String)po.get_Value(index))
				: null;
	}

	@Override
	public boolean isProcessing() {return po.get_ValueAsBoolean("Processing");}

	@Override
	public boolean isProcessed() {return po.get_ValueAsBoolean("Processed");}

	@Override
	public boolean isActive() {return po.isActive();}

	@Override
	public PostingStatus getPostingStatus() {return PostingStatus.ofNullableCode(po.get_ValueAsString("Posted"));}

	@Override
	public boolean hasColumnName(final String columnName) {return po.getPOInfo().hasColumnName(columnName);}

	@Override
	public int getValueAsIntOrZero(final String columnName)
	{
		final int index = po.get_ColumnIndex(columnName);
		if (index != -1)
		{
			final Integer ii = (Integer)po.get_Value(index);
			if (ii != null)
			{
				return ii;
			}
		}
		return 0;
	}

	@Override
	@Nullable
	public <T extends RepoIdAware> T getValueAsIdOrNull(final String columnName, final IntFunction<T> idOrNullMapper)
	{
		final int index = po.get_ColumnIndex(columnName);
		if (index < 0)
		{
			return null;
		}

		final Object valueObj = po.get_Value(index);
		final Integer valueInt = NumberUtils.asInteger(valueObj, null);
		if (valueInt == null)
		{
			return null;
		}

		return idOrNullMapper.apply(valueInt);
	}

	@Override
	@Nullable
	public LocalDateAndOrgId getValueAsLocalDateOrNull(@NonNull final String columnName, @NonNull final Function<OrgId, ZoneId> timeZoneMapper)
	{
		final int index = po.get_ColumnIndex(columnName);
		if (index != -1)
		{
			final Timestamp ts = po.get_ValueAsTimestamp(index);
			if (ts != null)
			{
				final OrgId orgId = OrgId.ofRepoId(po.getAD_Org_ID());
				return LocalDateAndOrgId.ofTimestamp(ts, orgId, timeZoneMapper);
			}
		}

		return null;
	}

	@Override
	@Nullable
	public Boolean getValueAsBooleanOrNull(@NonNull final String columnName)
	{
		final int index = po.get_ColumnIndex(columnName);
		if (index != -1)
		{
			final Object valueObj = po.get_Value(index);
			return DisplayType.toBoolean(valueObj, null);
		}

		return null;
	}

	@Override
	@Nullable
	public String getValueAsString(@NonNull final String columnName)
	{
		final int index = po.get_ColumnIndex(columnName);
		if (index != -1)
		{
			final Object valueObj = po.get_Value(index);
			return valueObj != null ? valueObj.toString() : null;
		}

		return null;
	}

}
