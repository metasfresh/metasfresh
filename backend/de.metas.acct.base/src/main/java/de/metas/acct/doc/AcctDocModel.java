package de.metas.acct.doc;

import de.metas.document.engine.DocStatus;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import de.metas.user.UserId;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.acct.PostingStatus;

import javax.annotation.Nullable;
import java.time.ZoneId;
import java.util.function.Function;
import java.util.function.IntFunction;

public interface AcctDocModel
{
	ClientId getClientId();

	OrgId getOrgId();

	UserId getUpdatedBy();

	@Nullable
	DocStatus getDocStatus();

	<T> T unboxAs(@NonNull Class<T> modelClass);

	Object unbox();

	String getTableName();

	int getId();

	TableRecordReference getRecordRef();

	boolean isProcessing();

	boolean isProcessed();

	boolean isActive();

	PostingStatus getPostingStatus();

	boolean hasColumnName(String columnName);

	int getValueAsIntOrZero(String columnName);

	@Nullable
	<T extends RepoIdAware> T getValueAsIdOrNull(String columnName, IntFunction<T> idOrNullMapper);

	@Nullable
	LocalDateAndOrgId getValueAsLocalDateOrNull(@NonNull String columnName, @NonNull Function<OrgId, ZoneId> timeZoneMapper);

	@Nullable
	Boolean getValueAsBooleanOrNull(@NonNull String columnName);

	@Nullable
	String getValueAsString(@NonNull String columnName);
}
