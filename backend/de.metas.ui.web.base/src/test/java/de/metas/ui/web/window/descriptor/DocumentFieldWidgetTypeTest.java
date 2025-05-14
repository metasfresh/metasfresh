package de.metas.ui.web.window.descriptor;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DocumentFieldWidgetTypeTest
{
	@Test
	void isBigDecimal()
	{
		for (DocumentFieldWidgetType value : DocumentFieldWidgetType.values())
		{
			if (value == DocumentFieldWidgetType.Number
					|| value == DocumentFieldWidgetType.Amount
					|| value == DocumentFieldWidgetType.Quantity
					|| value == DocumentFieldWidgetType.CostPrice)
			{
				assertThat(value.isBigDecimal()).isTrue();
			}
			else if (value == DocumentFieldWidgetType.Integer)
			{
				assertThat(value.isBigDecimal()).isFalse();
			}
			else
			{
				assertThat(value.isBigDecimal()).isFalse();
			}
		}

	}

}