package de.metas.cache.interceptor;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStringBuilder;
import de.metas.i18n.TranslatableStrings;
import org.adempiere.exceptions.AdempiereException;

import java.io.Serial;
import java.lang.annotation.Annotation;
import java.util.Arrays;

/**
 * Exception thrown when a value could not be retrieved from cache because of inconsistencies or because some parameters were not valid.
 * 
 * @author tsa
 *
 */
public class CacheGetException extends AdempiereException
{
	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = -2145287441754772885L;
	private Object targetObject;
	private Object[] methodArgs;
	private int parameterIndex;
	private Object parameter;
	private boolean parameterSet = false;
	private Class<? extends Annotation> annotationType = null;

	public CacheGetException(final String message)
	{
		// NOTE: don't try to translate the build message because it's not translatable
		super(TranslatableStrings.constant(message));
	}

	@Override
	protected ITranslatableString buildMessage()
	{
		final TranslatableStringBuilder message = TranslatableStrings.builder();
		message.append(super.buildMessage());

		if (targetObject != null)
		{
			message.append("\n Target object: ").append(targetObject.toString());
		}

		if (methodArgs != null)
		{
			message.append("\n Method Arguments: ").append(Arrays.asList(methodArgs).toString());
		}

		if (parameterSet)
		{
			message.append("\n Invalid parameter at index ").append(parameterIndex).append(": ").append(String.valueOf(parameter));
			if (parameter != null)
			{
				message.append(" (").append(parameter.getClass().toString()).append(")");
			}
		}

		if (annotationType != null)
		{
			message.append("\n Annotation: ").append(annotationType.toString());
		}

		return message.build();
	}

	public CacheGetException setTargetObject(final Object targetObject)
	{
		this.targetObject = targetObject;
		resetMessageBuilt();
		return this;
	}

	public CacheGetException setMethodArguments(final Object[] methodArgs)
	{
		this.methodArgs = methodArgs;
		resetMessageBuilt();
		return this;
	}

	public CacheGetException setInvalidParameter(final int parameterIndex, final Object parameter)
	{
		this.parameterIndex = parameterIndex;
		this.parameter = parameter;
		this.parameterSet = true;
		resetMessageBuilt();
		return this;
	}

	public CacheGetException setAnnotation(final Class<? extends Annotation> annotation)
	{
		this.annotationType = annotation;
		return this;
	}
	
	public CacheGetException addSuppressIfNotNull(final Throwable exception)
	{
		if(exception != null)
		{
			addSuppressed(exception);
		}
		return this;
	}
}
