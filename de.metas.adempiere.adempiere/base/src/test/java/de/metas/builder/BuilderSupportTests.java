package de.metas.builder;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BuilderSupportTests
{
	@Test
	public void addLineTest()
	{
		final CustomBuilder  builder = new CustomBuilder();
		
		final BuilderSupport<CustomLineBuilder> builderSupport = new BuilderSupport<BuilderSupportTests.CustomLineBuilder>(builder);
		final ILineBuilder lineBuilder = builderSupport.addLine(CustomLineBuilder.class);
		
		assertThat(lineBuilder, instanceOf(CustomLineBuilder.class));
		assertTrue(((CustomLineBuilder)lineBuilder).parent == builder);
	}
	
	public static class CustomBuilder implements IBuilder
	{
		
	};
	
	public static class CustomLineBuilder implements ILineBuilder
	{
		final IBuilder parent;
		
		public CustomLineBuilder(CustomBuilder parent)
		{
			this.parent=parent;
		}
	};
}
