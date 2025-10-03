package org.adempiere.warehouse.qrcode.resolver;

import com.google.common.collect.ImmutableList;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.LocatorId;

import javax.annotation.Nullable;
import java.util.List;

@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public final class LocatorScannedCodeResolverResult
{
	@Nullable LocatorId locatorId;
	@NonNull @Getter ImmutableList<LocatorNotResolvedReason> notResolvedReasons;

	private LocatorScannedCodeResolverResult(@NonNull final LocatorId locatorId)
	{
		this.locatorId = locatorId;
		this.notResolvedReasons = ImmutableList.of();
	}

	private LocatorScannedCodeResolverResult(@NonNull final List<LocatorNotResolvedReason> notResolvedReasons)
	{
		this.locatorId = null;
		this.notResolvedReasons = ImmutableList.copyOf(notResolvedReasons);
	}

	public static LocatorScannedCodeResolverResult notFound(@NonNull final LocatorGlobalQRCodeResolverKey resolver, @NonNull final String reason)
	{
		return new LocatorScannedCodeResolverResult(ImmutableList.of(LocatorNotResolvedReason.of(resolver, reason)));
	}

	public static LocatorScannedCodeResolverResult notFound(@NonNull final List<LocatorNotResolvedReason> notFoundReasons)
	{
		return new LocatorScannedCodeResolverResult(notFoundReasons);
	}

	public static LocatorScannedCodeResolverResult found(@NonNull final LocatorId locatorId)
	{
		return new LocatorScannedCodeResolverResult(locatorId);
	}

	public boolean isFound() {return locatorId != null;}

	@NonNull
	public LocatorId getLocatorId()
	{
		if (locatorId == null)
		{
			throw AdempiereException.notFound()
					.setParameter("notResolvedReasons", notResolvedReasons);
		}

		return locatorId;
	}
}
