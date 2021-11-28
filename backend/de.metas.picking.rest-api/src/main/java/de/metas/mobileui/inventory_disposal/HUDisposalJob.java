package de.metas.mobileui.inventory_disposal;

import de.metas.handlingunits.HUBarcode;
import de.metas.handlingunits.HuId;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
public class HUDisposalJob
{
	@NonNull HuId huId;
	@NonNull HUBarcode huBarcode;
	@NonNull String huDisplayName;

	@NonNull UserId invokerId;

	@NonNull ITranslatableString launcherCaption; // computed

	@Builder
	private HUDisposalJob(
			final @NonNull HuId huId,
			final @NonNull String huDisplayName,
			final @NonNull HUBarcode huBarcode,
			final @NonNull UserId invokerId
	)
	{
		this.huId = huId;
		this.huDisplayName = huDisplayName;
		this.huBarcode = huBarcode;
		this.invokerId = invokerId;

		this.launcherCaption = TranslatableStrings.builder()
				.append(huBarcode.getAsString())
				.append(" | ")
				.append(huDisplayName)
				.build();
	}
}
