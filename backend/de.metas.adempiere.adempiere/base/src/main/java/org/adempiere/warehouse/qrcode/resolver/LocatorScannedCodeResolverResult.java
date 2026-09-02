package org.adempiere.warehouse.qrcode.resolver;

import com.google.common.collect.ImmutableList;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.qrcode.LocatorQRCode;

import javax.annotation.Nullable;
import java.util.List;

@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public final class LocatorScannedCodeResolverResult
{
	@Nullable LocatorQRCode locatorQRCode;
	@NonNull @Getter ImmutableList<LocatorNotResolvedReason> notResolvedReasons;

	private LocatorScannedCodeResolverResult(@NonNull LocatorQRCode locatorQRCode)
	{
		this.locatorQRCode = locatorQRCode;
		this.notResolvedReasons = ImmutableList.of();
	}

	private LocatorScannedCodeResolverResult(@NonNull final List<LocatorNotResolvedReason> notResolvedReasons)
	{
		this.locatorQRCode = null;
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

	public static LocatorScannedCodeResolverResult found(@NonNull final LocatorQRCode locatorQRCode)
	{
		return new LocatorScannedCodeResolverResult(locatorQRCode);
	}

	public boolean isFound() {return locatorQRCode != null;}

	@NonNull
	public LocatorId getLocatorId()
	{
		return getLocatorQRCode().getLocatorId();
	}

	@NonNull
	public LocatorQRCode getLocatorQRCode()
	{
		if (locatorQRCode == null)
		{
			throw AdempiereException.notFound()
					.setParameter("notResolvedReasons", notResolvedReasons);
		}

		return locatorQRCode;
	}
}
