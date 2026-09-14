package de.metas.ui.web.quickinput;

import de.metas.ui.web.window.descriptor.WidgetSize;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
	void parse_rejects_unknown_value()
	{
		assertThatThrownBy(() -> QuickInputConstants.parseProductFieldWidgetSize("HUGE"))
				.isInstanceOf(NoSuchElementException.class);
	}
}
