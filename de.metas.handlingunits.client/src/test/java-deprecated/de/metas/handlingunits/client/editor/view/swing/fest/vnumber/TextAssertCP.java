/*
 * Created on Jun 20, 2009
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 * Copyright @2009-2010 the original author or authors.
 */
package de.metas.handlingunits.client.editor.view.swing.fest.vnumber;

/*
 * #%L
 * de.metas.handlingunits.client
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.regex.Pattern;

import org.fest.assertions.Assert;
import org.fest.assertions.AssertExtension;
import org.fest.assertions.Description;
import org.fest.util.Strings;

/**
 * copy-paste from: org.fest.swing.driver.TextAssert
 * 
 * Understands assertion methods for text.
 * 
 * @author Alex Ruiz
 */
public class TextAssertCP extends Assert implements AssertExtension
{
	private final String actual;

	public static TextAssertCP assertThat(final String s)
	{
		return new TextAssertCP(s);
	}

	public static TextAssertCP verifyThat(final String s)
	{
		return new TextAssertCP(s);
	}

	public TextAssertCP(final String actual)
	{
		super();
		this.actual = actual;
	}

	@SuppressWarnings("PMD.ShortMethodName")
	public TextAssertCP as(final String description)
	{
		description(description);
		return this;
	}

	@SuppressWarnings("PMD.ShortMethodName")
	public TextAssertCP as(final Description description)
	{
		description(description);
		return this;
	}

	public TextAssertCP isEqualOrMatches(final String s)
	{
		if (org.fest.swing.util.Strings.areEqualOrMatch(s, actual))
		{
			return this;
		}
		throw failure(Strings.concat(
				"actual value:<", Strings.quote(actual), "> is not equal to or does not match pattern:<", Strings.quote(s), ">"));
	}

	public TextAssertCP matches(final Pattern pattern)
	{
		if (org.fest.swing.util.Strings.match(pattern, actual))
		{
			return this;
		}
		throw failure(Strings.concat(
				"actual value:<", Strings.quote(actual), "> does not match pattern:<", Strings.quote(pattern.pattern()), ">"));
	}
}
