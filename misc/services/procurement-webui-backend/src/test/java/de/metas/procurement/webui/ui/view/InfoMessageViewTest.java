package de.metas.procurement.webui.ui.view;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

/*
 * #%L
 * metasfresh-procurement-webui
 * %%
 * Copyright (C) 2019 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class InfoMessageViewTest
{
	@Test
	public void test_replaceNewLineWithHtmlBreak()
	{
		assertThat(InfoMessageView.replaceNewLineWithHtmlBreak(null))
				.isNull();

		assertThat(InfoMessageView.replaceNewLineWithHtmlBreak(""))
				.isEmpty();

		assertThat(InfoMessageView.replaceNewLineWithHtmlBreak("text"))
				.isEqualTo("text");

		assertThat(InfoMessageView.replaceNewLineWithHtmlBreak("line1\r\nline2"))
				.isEqualTo("line1<br/>line2");
		assertThat(InfoMessageView.replaceNewLineWithHtmlBreak("line1\nline2"))
				.isEqualTo("line1<br/>line2");
	}
}
