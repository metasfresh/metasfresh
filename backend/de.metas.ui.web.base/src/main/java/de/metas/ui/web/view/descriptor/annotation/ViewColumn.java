package de.metas.ui.web.view.descriptor.annotation;

import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.MediaType;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.ViewEditorRenderMode;
import de.metas.ui.web.window.descriptor.WidgetSize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

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

/**
 * Note: take a look at {@link ViewColumnHelper} to see how the annotation is processed.
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewColumn
{
	/**
	 * Field name. If missing or empty, the {@link Field#getName()} of the annotated field will be used.
	 */
	String fieldName() default "";

	DocumentFieldWidgetType widgetType();

	/**
	 * List AD_Reference_ID; to be used when {@link #widgetType()} is lookup
	 */
	int listReferenceId() default -1;

	/**
	 * Column's caption identified by AD_Message/AD_Element.
	 */
	String captionKey() default "";

	/**
	 * From where to fetch the caption's translation
	 */
	TranslationSource captionTranslationSource() default TranslationSource.DEFAULT;

	/**
	 * true if user is allowed to sort by this column
	 */
	boolean sorting() default true;

	/**
	 * Display sequence number.
	 * Overridden by {@link ViewColumnLayout#seqNo()}.
	 */
	int seqNo() default Integer.MIN_VALUE;

	ViewColumnLayout.Displayed displayed() default ViewColumnLayout.Displayed.TRUE;

	/**
	 * See {@link ViewColumnLayout.Displayed#SYSCONFIG}.
	 * If it evaluates to {@code null} or empty string, then go with {@link #defaultDisplaySysConfig()}.
	 */
	String displayedSysConfigPrefix() default "";

	boolean defaultDisplaySysConfig() default false;

	/**
	 * Column layout profiles.
	 * <p>
	 * If empty, and no defaults like {@link #seqNo()} were defined
	 * then the column won't be displayed in any of {@link JSONViewDataType} profiles.
	 */
	ViewColumnLayout[] layouts() default {};

	ViewEditorRenderMode editor() default ViewEditorRenderMode.NEVER;

	MediaType[] restrictToMediaTypes() default {};

	WidgetSize widgetSize() default WidgetSize.Default;

	/**
	 * If true the Zoom Into will be enabled for this field, if supported.
	 * When setting this flag, pls also make sure your view implements {@link de.metas.ui.web.view.IViewZoomIntoFieldSupport}.
	 */
	boolean zoomInto() default false;

	enum TranslationSource
	{
		/**
		 * Default (check AD_Message, AD_Element)
		 */
		DEFAULT,
		/**
		 * M_Attribute.Name
		 */
		ATTRIBUTE_NAME,

		// TODO: SYSCONFIG
	}

	@Target({ ElementType.FIELD })
	@Retention(RetentionPolicy.RUNTIME)
	@interface ViewColumnLayout
	{
		enum Displayed
		{
			/**
			 * The column shall be displayed by default.
			 */
			TRUE,

			/**
			 * The column will be displayed only on demand, when it was explicitly (programatically) specified.
			 */
			FALSE,

			/**
			 * The column shall <b>not</b> be displayed,<br>
			 * unless the sys-config with key = "{@link ViewColumnLayout#displayedSysConfigPrefix()}.fieldName" validates to {@code true}.
			 * The sysconfig may be overridden on client or org-level.
			 * If there is no sys-config that can be validated to a boolean, then {@link #defaultDisplaySysConfig()} is assumed.
			 */
			SYSCONFIG
		}

		JSONViewDataType when();

		Displayed displayed() default Displayed.TRUE;

		/**
		 * See {@link Displayed#SYSCONFIG}. If it evaluates to {@code null} or empty string, then go with {@link #defaultDisplaySysConfig()}.
		 */
		String displayedSysConfigPrefix() default "";

		boolean defaultDisplaySysConfig() default false;

		/**
		 * Display sequence number
		 */
		int seqNo();

	}
}
