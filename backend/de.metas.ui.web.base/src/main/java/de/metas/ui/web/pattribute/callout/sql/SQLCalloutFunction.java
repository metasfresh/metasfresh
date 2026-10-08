package de.metas.ui.web.pattribute.callout.sql;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class SQLCalloutFunction
{
	@NonNull String schema;
	@NonNull String name;

	@Override
	@Deprecated
	public String toString() {return getNameFQ();}

	public String getNameFQ() {return schema + "." + name;}
}
