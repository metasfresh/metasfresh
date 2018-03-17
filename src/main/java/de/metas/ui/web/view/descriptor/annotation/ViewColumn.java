package de.metas.ui.web.view.descriptor.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.MediaType;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.ViewEditorRenderMode;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewColumn
{
	/**
	 * Field name. If missing or empty, the {@link Field#getName()} will be used.
	 */
	String fieldName() default "";

	DocumentFieldWidgetType widgetType();

	/**
	 * Column's caption identified by AD_Message/AD_Element.
	 */
	String captionKey() default "";

	/** true if user is allowed to sort by this column */
	boolean sorting() default true;

	/**
	 * Column layout profiles.
	 *
	 * If empty, the column won't be displayed in any of {@link JSONViewDataType} profiles.
	 */
	ViewColumnLayout[] layouts() default {};

	ViewEditorRenderMode editor() default ViewEditorRenderMode.NEVER;

	MediaType[] restrictToMediaTypes() default {};

	@Target({ ElementType.FIELD })
	@Retention(RetentionPolicy.RUNTIME)
	public static @interface ViewColumnLayout
	{
		JSONViewDataType when();

		/**
		 * If <code>true</code> if the column shall be displayed by default.
		 * If <code>false</code> the column will be displayed only on demand, when it was explicitly specified.
		 */
		boolean displayed() default true;

		/** Display sequence number */
		int seqNo();
	}
}
