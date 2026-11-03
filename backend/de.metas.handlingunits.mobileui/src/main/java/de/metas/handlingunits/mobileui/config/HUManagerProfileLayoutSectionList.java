package de.metas.handlingunits.mobileui.config;

import com.google.common.collect.ImmutableList;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collector;

@EqualsAndHashCode(doNotUseGetters = true)
@ToString(of = "list")
public class HUManagerProfileLayoutSectionList
{
	public static final HUManagerProfileLayoutSectionList DEFAULT = new HUManagerProfileLayoutSectionList(ImmutableList.of(
			HUManagerProfileLayoutSection.DisplayName,
			HUManagerProfileLayoutSection.QRCode,
			HUManagerProfileLayoutSection.Locator,
			HUManagerProfileLayoutSection.HUStatus,
			HUManagerProfileLayoutSection.Product,
			HUManagerProfileLayoutSection.Qty,
			HUManagerProfileLayoutSection.ClearanceStatus,
			HUManagerProfileLayoutSection.Attributes
	));

	@NonNull private final ImmutableList<HUManagerProfileLayoutSection> list;
	@NonNull private final transient ImmutableList<String> asStringList;

	private HUManagerProfileLayoutSectionList(@NonNull final ImmutableList<HUManagerProfileLayoutSection> list)
	{
		this.list = Check.assumeNotEmpty(list, "list is not empty");
		this.asStringList = list.stream()
				.map(HUManagerProfileLayoutSection::getCode)
				.collect(ImmutableList.toImmutableList());
	}

	public static Collector<HUManagerProfileLayoutSection, ?, HUManagerProfileLayoutSectionList> collectOrDefault()
	{
		return GuavaCollectors.collectUsingListAccumulator(list -> list.isEmpty()
				? DEFAULT
				: new HUManagerProfileLayoutSectionList(ImmutableList.copyOf(list)));
	}

	public List<String> toStringList() {return asStringList;}
}
