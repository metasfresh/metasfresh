package de.metas.process;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface NestedParams
{
	ParamMapping[] value() default {};

	@Retention(RetentionPolicy.RUNTIME)
	@Repeatable(value = NestedParams.class)
	@interface ParamMapping
	{
		String externalParameterName();

		String internalParameterName();
	}
}
