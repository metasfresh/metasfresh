package org.adempiere.warehouse.qrcode.resolver;

import lombok.NonNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class LocatorNotResolvedReason
{
	@NonNull LocatorGlobalQRCodeResolverKey resolver;
	@NonNull String message;

	@Override
	@Deprecated
	public String toString() {return getSummary();}

	public String getSummary() {return resolver.toJson() + ": " + message;}
}
