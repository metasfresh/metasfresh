package de.metas.ui.web.quickinput;

import de.metas.ui.web.window.descriptor.WidgetSize;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QuickInputConstantsTest
{
	@Test
	void parse_maps_all_supported_codes()
	{
		assertThat(QuickInputConstants.parseProductFieldWidgetSize("S")).isEqualTo(WidgetSize.Small);
		assertThat(QuickInputConstants.parseProductFieldWidgetSize("M")).isEqualTo(WidgetSize.Medium);
		assertThat(QuickInputConstants.parseProductFieldWidgetSize("L")).isEqualTo(WidgetSize.Large);
		assertThat(QuickInputConstants.parseProductFieldWidgetSize("XL")).isEqualTo(WidgetSize.ExtraLarge);
		assertThat(QuickInputConstants.parseProductFieldWidgetSize("XXL")).isEqualTo(WidgetSize.XXL);
	}

	@Test
	void parse_treats_blank_null_and_dash_as_default()
	{
		assertThat(QuickInputConstants.parseProductFieldWidgetSize(null)).isNull();
		assertThat(QuickInputConstants.parseProductFieldWidgetSize("")).isNull();
		assertThat(QuickInputConstants.parseProductFieldWidgetSize("   ")).isNull();
		assertThat(QuickInputConstants.parseProductFieldWidgetSize("-")).isNull();
		assertThat(QuickInputConstants.parseProductFieldWidgetSize(" - ")).isNull();
	}

	@Test
	void parse_unknown_value_falls_back_to_default()
	{
		// A cosmetic, default-off setting must never break batch entry on a typo: an unknown or
		// wrong-case value degrades to Default width (null), it does NOT throw. See
		// QuickInputConstants.parseProductFieldWidgetSize for the blast-radius rationale.
		assertThat(QuickInputConstants.parseProductFieldWidgetSize("HUGE")).isNull();
		assertThat(QuickInputConstants.parseProductFieldWidgetSize("l")).isNull();     // case-sensitive: lowercase is not a code
		assertThat(QuickInputConstants.parseProductFieldWidgetSize("Large")).isNull();
		assertThat(QuickInputConstants.parseProductFieldWidgetSize("30em")).isNull();
	}
}
