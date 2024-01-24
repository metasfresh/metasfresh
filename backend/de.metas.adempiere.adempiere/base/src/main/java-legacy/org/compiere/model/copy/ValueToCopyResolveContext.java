package org.compiere.model.copy;

import de.metas.common.util.time.SystemTime;
import de.metas.user.api.IUserDAO;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;
import org.adempiere.ad.element.api.AdWindowId;
import org.compiere.model.PO;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.Optional;

@Value
@Builder
public class ValueToCopyResolveContext
{
	@NonNull PO to;
	@NonNull PO from;
	@NonNull String columnName;

	@Nullable @With PO parentPO;
	@Nullable @With AdWindowId adWindowId;

	public Object getFromValue() {return from.get_Value(columnName);}

	public String getFromValueAsString() {return from.get_ValueAsString(columnName);}

	public int getToFieldLength() {return to.getPOInfo().getFieldLength(columnName);}

	public boolean isToColumnCalculated() {return to.getPOInfo().isCalculated(columnName);}

	public Optional<String> getLoggedUserName()
	{
		final IUserDAO userDAO = Services.get(IUserDAO.class);
		return Env.getLoggedUserIdIfExists().map(userDAO::retrieveUserFullName);
	}

	public ZonedDateTime getTimestamp() {return SystemTime.asZonedDateTime();}
}
