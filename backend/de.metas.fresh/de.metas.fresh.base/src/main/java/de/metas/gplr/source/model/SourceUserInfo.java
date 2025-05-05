package de.metas.gplr.source.model;

import de.metas.user.api.IUserBL;
import lombok.Builder;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class SourceUserInfo
{
	@Nullable String firstName;
	@Nullable String lastName;

	public String toString() {return getName();}

	public String getName() {return IUserBL.buildContactName(firstName, lastName);}

}
