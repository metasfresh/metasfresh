/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.bpartner;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GlnWithLabelTest
{

	@Test
	void ofString()
	{
		final GlnWithLabel glnWithLabel = GlnWithLabel.ofString("1234567890123_label");
		assertThat(glnWithLabel.getGln()).isEqualTo(GLN.ofString("1234567890123"));
		assertThat(glnWithLabel.getLabel()).isEqualTo("label");
	}

	@Test
	void ofString_with_underscored_Label()
	{
		final GlnWithLabel glnWithLabel = GlnWithLabel.ofString("1234567890123_label_2");
		assertThat(glnWithLabel.getGln()).isEqualTo(GLN.ofString("1234567890123"));
		assertThat(glnWithLabel.getLabel()).isEqualTo("label_2");
	}
	
	@Test
	void ofString_with_Null_Label()
	{
		final GlnWithLabel glnWithLabel = GlnWithLabel.ofString("1234567890123");
		assertThat(glnWithLabel.getGln()).isEqualTo(GLN.ofString("1234567890123"));
		assertThat(glnWithLabel.getLabel()).isNull();
	}

	@Test
	void ofGLN()
	{
		final GLN gln = GLN.ofString("1234567890123");
		final GlnWithLabel glnWithLabel = GlnWithLabel.ofGLN(gln, "label");

		assertThat(glnWithLabel.getGln()).isEqualTo(gln);
		assertThat(glnWithLabel.getLabel()).isEqualTo("label");
	}

	@Test
	void ofGLN_with_underscored_Label()
	{
		final GLN gln = GLN.ofString("1234567890123");
		final GlnWithLabel glnWithLabel = GlnWithLabel.ofGLN(gln, "label_2");

		assertThat(glnWithLabel.getGln()).isEqualTo(gln);
		assertThat(glnWithLabel.getLabel()).isEqualTo("label_2");
	}
	
	@Test
	void ofGLN_with_Null_Label()
	{
		final GLN gln = GLN.ofString("1234567890123");
		final GlnWithLabel glnWithLabel = GlnWithLabel.ofGLN(gln, null);

		assertThat(glnWithLabel.getGln()).isEqualTo(gln);
		assertThat(glnWithLabel.getLabel()).isNull();
	}
}